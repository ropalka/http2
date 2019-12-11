/*
 * Copyright (c) 2012-2019, FOSS Nova Software foundation (FNSF),
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
package com.fossnova.http2.protocol;

import org.fossnova.http2.protocol.DataFrame;

import java.nio.ByteBuffer;

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

    void writeTo(final ByteBuffer buffer) {
        super.writeTo(buffer);
        final int padLength = getPayloadSize() - data.length;
        if (padLength > 0) {
            buffer.put((byte) padLength);
        }
        if (data.length != 0) {
            buffer.put(data);
        }
        if (padLength != 0) {
            buffer.put(new byte[padLength]);
        }
    }

    static DataFrameImpl readFrom(final ByteBuffer buffer, final Builder builder) {
        // implementation
        int padLength = 0;
        if ((builder.flags & DataFrame.FLAG_PADDED) != 0) {
            padLength = 0x00_00_00_FF & buffer.get();
        }
        if (builder.payloadSize > padLength) {
            byte[] data = new byte[builder.payloadSize - padLength];
            buffer.get(data);
            builder.setData(data);
        }
        if (padLength > 0) {
            buffer.get(new byte[padLength]);
        }

        return builder.build();
    }

    final static class Builder extends AbstractFrameImpl.Builder implements DataFrame.Builder {
        byte[] data = AbstractFrameImpl.EMPTY_ARRAY;
        boolean built;

        Builder(final boolean server, final boolean validate) {
            super(server, validate);
        }

        Builder(final boolean server, final boolean validate, final int payloadSize, final FrameType frameType, final byte flags, final int streamId) {
            super(server, validate);
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
