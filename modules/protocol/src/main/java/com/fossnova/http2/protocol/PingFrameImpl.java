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
package com.fossnova.http2.protocol;

import org.fossnova.http2.protocol.PingFrame;

import java.nio.ByteBuffer;

/**
 * @author <a href="mailto:opalka.richard@gmail.com">Richard Opalka</a>
 */
final class PingFrameImpl extends AbstractFrameImpl implements PingFrame {
    private final long data;

    PingFrameImpl(final byte flags, final long data) {
        super(8, FrameType.PING, flags, 0);
        this.data = data;
    }

    @Override
    public long getOpaqueData() {
        return data;
    }

    void writeTo(final ByteBuffer buffer) {
        super.writeTo(buffer);
        buffer.put((byte)(data >>> 56));
        buffer.put((byte)(data >>> 48));
        buffer.put((byte)(data >>> 40));
        buffer.put((byte)(data >>> 32));
        buffer.put((byte)(data >>> 24));
        buffer.put((byte)(data >>> 16));
        buffer.put((byte)(data >>> 8));
        buffer.put((byte)(data));
    }

    static PingFrameImpl readFrom(final ByteBuffer buffer, final Builder builder) {
        // implementation
        long data = 0xFF_00_00_00_00_00_00_00L & (long)buffer.get() << 56;
        data |= 0x00_FF_00_00_00_00_00_00L & (long)buffer.get() << 48;
        data |= 0x00_00_FF_00_00_00_00_00L & (long)buffer.get() << 40;
        data |= 0x00_00_00_FF_00_00_00_00L & (long)buffer.get() << 32;
        data |= 0x00_00_00_00_FF_00_00_00L & (long)buffer.get() << 24;
        data |= 0x00_00_00_00_00_FF_00_00L & (long)buffer.get() << 16;
        data |= 0x00_00_00_00_00_00_FF_00L & (long)buffer.get() << 8;
        data |= 0x00_00_00_00_00_00_00_FFL & (long)buffer.get();
        builder.setOpaqueData(data);
        return builder.build();
    }

    final static class Builder extends AbstractFrameImpl.Builder implements PingFrame.Builder {
        long data;
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
        public void setOpaqueData(final long data) {
            // preconditions
            ensureThreadSafety();
            ensureNotBuilt();
            // implementation
            this.data = data;
        }

        @Override
        public PingFrameImpl build() {
            // preconditions
            ensureThreadSafety();
            ensureNotBuilt();
            // validation
            validateStreamId(streamId);
            // implementation
            built = true;
            return new PingFrameImpl((byte)flags, data);
        }

        @Override
        void validateFlags(final int flags) {
            if ((flags & ~FLAG_ACK) != 0) {
                throw new IllegalArgumentException();
            }
        }

        @Override
        void validateStreamId(final int streamId) {
            if (streamId != 0) {
                throw new IllegalArgumentException();
            }
        }

        @Override
        void validatePayloadSize(final int payloadSize) {
            if (payloadSize != 8) {
                throw new IllegalArgumentException();
            }
        }

        private void ensureNotBuilt() {
            if (built) {
                throw new IllegalStateException();
            }
        }

    }
}
