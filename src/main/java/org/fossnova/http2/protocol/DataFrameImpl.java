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
final class DataFrameImpl extends AbstractFrameImpl implements DataFrame {
    private final byte[] data;

    DataFrameImpl(final int payloadSize, final byte flags, final int streamId, final byte[] data) {
        super(payloadSize, FrameType.DATA, flags, streamId);
        this.data = data;
    }

    @Override
    public byte[] getData() {
        return data != null ? data.clone() : null;
    }

    byte[] writePayload() {
        final byte[] buffer = new byte[getPayloadSize()];
        int i = 0;
        final int padLength = getPayloadSize() - data.length;
        if ((getFlags() & FLAG_PADDED) != 0) {
            buffer[i++] = (byte) padLength;
        }
        if (data.length != 0) {
            System.arraycopy(data, 0, buffer, i, data.length);
        }
        return buffer;
    }

    static DataFrameImpl readFrom(final byte[] buffer, final Builder builder) {
        int padLength = 0;
        int i = 0;
        if ((builder.flags & DataFrame.FLAG_PADDED) != 0) {
            padLength = 0x00_00_00_FF & buffer[i++];
        }
        if (builder.payloadSize > padLength) {
            final byte[] data = new byte[builder.payloadSize - padLength];
            System.arraycopy(buffer, i, data, 0, data.length);
            builder.setData(data);
        }

        return builder.build();
    }

    final static class Builder extends AbstractFrameImpl.Builder implements DataFrame.Builder {
        byte[] data = AbstractFrameImpl.EMPTY_ARRAY;
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
        public void setData(final byte[] data) {
            // preconditions
            ensureThreadSafety();
            ensureNotBuilt();
            ensureNotNull(data);
            // implementation
            this.data = data;
        }

        @Override
        public DataFrameImpl build() {
            // preconditions
            ensureThreadSafety();
            ensureNotBuilt();
            // validation
            validateStreamId(streamId);
            if ((flags & DataFrame.FLAG_PADDED) != 0) {
                if (payloadSize - data.length >= 255) {
                    throw new IllegalArgumentException();
                }
            } else {
                if (payloadSize != data.length) {
                    throw new IllegalArgumentException();
                }
            }
            // implementation
            built = true;
            return new DataFrameImpl(payloadSize, (byte)flags, streamId, data);
        }

        @Override
        void validateFlags(final int flags) {
            if ((flags & ~(DataFrame.FLAG_PADDED | DataFrame.FLAG_END_STREAM)) != 0) {
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
