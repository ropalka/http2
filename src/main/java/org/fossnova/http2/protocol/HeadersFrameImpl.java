/*
 * Copyright (c) 2012-2020, FOSS Nova Software foundation (FNSF),
 * and individual contributors as indicated by the @author tags.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.fossnova.http2.protocol;

/**
 * @author <a href="mailto:opalka.richard@gmail.com">Richard Opalka</a>
 */
final class HeadersFrameImpl extends AbstractFrameImpl implements HeadersFrame {
    private final boolean exclusive;
    private final int dependencyStreamId;
    private final int weight;
    private final byte[] data;

    HeadersFrameImpl(final int payloadSize, final byte flags, final int streamId, final boolean exclusive, final int dependencyStreamId, final int weight, final byte[] data) {
        super(payloadSize, FrameType.HEADERS, flags, streamId);
        this.exclusive = exclusive;
        this.dependencyStreamId = dependencyStreamId;
        this.weight = weight;
        this.data = data;
    }

    @Override
    public boolean isDependencyExclusive() {
        return exclusive;
    }

    @Override
    public int getDependencyStream() {
        return dependencyStreamId;
    }

    @Override
    public int getWeight() {
        return weight;
    }

    @Override
    public byte[] getHeaderBlockFragment() {
        return data != null ? data.clone() : null;
    }

    byte[] writePayload() {
        final int priorityFieldsLength = (getFlags() & FLAG_PRIORITY) != 0 ? 5 : 0;
        final int padLength = getPayloadSize() - data.length - priorityFieldsLength;
        final byte[] buffer = new byte[getPayloadSize()];
        int i = 0;
        if ((getFlags() & FLAG_PADDED) != 0) {
            buffer[i++] = (byte) padLength;
        }
        if (priorityFieldsLength != 0) {
            if (exclusive) {
                buffer[i++] = (byte)(0b00000000_00000000_00000000_10000000 | dependencyStreamId >>> 24);
            } else {
                buffer[i++] = (byte)(dependencyStreamId >>> 24);
            }
            buffer[i++] = (byte)(dependencyStreamId >>> 16);
            buffer[i++] = (byte)(dependencyStreamId >>> 8);
            buffer[i++] = (byte)(dependencyStreamId);
            buffer[i++] = (byte)weight;
        }
        if (data.length != 0) {
            System.arraycopy(data, 0, buffer, i, data.length);
        }
        return buffer;
    }

    static HeadersFrameImpl readFrom(final byte[] buffer, final Builder builder) {
        int i = 0;
        int padLength = 0;
        if ((builder.flags & FLAG_PADDED) != 0) {
            padLength = 0x00_00_00_FF & buffer[i++];
        }
        int priorityFieldsLength = 0;
        if ((builder.flags & FLAG_PRIORITY) != 0) {
            priorityFieldsLength = 5;
            int dependencyStreamId = 0xFF_00_00_00 & buffer[i++] << 24;
            boolean exclusive = (0b10000000_00000000_00000000_00000000 & dependencyStreamId) != 0;
            if (exclusive) {
                dependencyStreamId &= 0b01111111_11111111_11111111_11111111;
            }
            dependencyStreamId |= 0x00_FF_00_00 & buffer[i++] << 16;
            dependencyStreamId |= 0x00_00_FF_00 & buffer[i++] << 8;
            dependencyStreamId |= 0x00_00_00_FF & buffer[i++];
            int weight = 0x00_00_00_FF & buffer[i++];
            builder.setDependencyExclusive(exclusive);
            builder.setDependencyStream(dependencyStreamId);
            builder.setWeight(weight);
        }
        if (builder.payloadSize > (padLength + priorityFieldsLength)) {
            final byte[] data = new byte[builder.payloadSize - padLength - priorityFieldsLength];
            System.arraycopy(buffer, i, data, 0, data.length);
            builder.setHeaderBlockFragment(data);
        }

        return builder.build();
    }

    final static class Builder extends AbstractFrameImpl.Builder implements HeadersFrame.Builder {
        byte[] data = AbstractFrameImpl.EMPTY_ARRAY;
        boolean exclusive;
        int dependencyStreamId;
        int streamWeight;
        boolean built;

        Builder(final boolean server, final boolean request, final boolean validate) {
            super(server, request, validate);
        }

        Builder(final boolean server, final boolean request, final boolean validate, final int payloadSize, final FrameType frameType, final byte flags, final int streamId) {
            super(server, request, validate);
            super.setPayloadSize(payloadSize);
            super.setFrameType(frameType);
            super.setFlags(flags);
            super.setStreamId(streamId);
        }

        @Override
        public void setDependencyExclusive(final boolean exclusive) {
            // preconditions
            ensureThreadSafety();
            ensureNotBuilt();
            // implementation
            this.exclusive = exclusive;
        }

        @Override
        public void setDependencyStream(final int dependencyStreamId) {
            // preconditions
            ensureThreadSafety();
            ensureNotBuilt();
            ensure31BitsOnlySet(dependencyStreamId);
            // implementation
            this.dependencyStreamId = dependencyStreamId;
        }

        @Override
        public void setWeight(final int streamWeight) {
            // preconditions
            ensureThreadSafety();
            ensureNotBuilt();
            ensure8BitsOnlySet(streamWeight);
            // implementation
            this.streamWeight = streamWeight;
        }

        @Override
        public void setHeaderBlockFragment(final byte[] data) {
            // preconditions
            ensureThreadSafety();
            ensureNotBuilt();
            ensureNotNull(data);
            // implementation
            this.data = data;
        }

        @Override
        public HeadersFrameImpl build() {
            // preconditions
            ensureThreadSafety();
            ensureNotBuilt();
            // validation
            validateStreamId(streamId);
            if ((flags & FLAG_PRIORITY) == 0 && (exclusive || streamWeight > 0 || dependencyStreamId > 0)) {
                throw new IllegalStateException();
            }
            if ((flags & FLAG_PADDED) != 0) {
                if ((flags & FLAG_PRIORITY) != 0) {
                    if (payloadSize < data.length + 5 || payloadSize - data.length - 5 >= 255) {
                        throw new IllegalStateException();
                    }
                } else {
                    if (payloadSize < data.length || payloadSize - data.length >= 255) {
                        throw new IllegalStateException();
                    }
                }
            } else {
                if ((flags & FLAG_PRIORITY) != 0) {
                    if (payloadSize != (data.length + 5)) {
                        throw new IllegalStateException();
                    }
                } else {
                    if (payloadSize != data.length) {
                        throw new IllegalStateException();
                    }
                }
            }
            // implementation
            built = true;
            return new HeadersFrameImpl(payloadSize, (byte)flags, streamId, exclusive, dependencyStreamId, streamWeight, data);
        }

        @Override
        void validateFlags(final int flags) {
            if ((flags & ~(FLAG_END_STREAM | FLAG_END_HEADERS | FLAG_PADDED | FLAG_PRIORITY)) != 0) {
                throw new IllegalArgumentException();
            }
        }

        @Override
        void validateStreamId(final int streamId) {
            if (streamId == 0) {
                throw new IllegalArgumentException();
            }
        }

        @Override
        void validatePayloadSize(final int payloadSize) {
            ; // does nothing
        }

        private void ensureNotBuilt() {
            if (built) {
                throw new IllegalStateException();
            }
        }

    }
}
