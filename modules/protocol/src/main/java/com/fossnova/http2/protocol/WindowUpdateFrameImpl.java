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

import org.fossnova.http2.protocol.WindowUpdateFrame;

import java.nio.ByteBuffer;

/**
 * @author <a href="mailto:opalka.richard@gmail.com">Richard Opalka</a>
 */
final class WindowUpdateFrameImpl extends AbstractFrameImpl implements WindowUpdateFrame {
    private final int windowInc;

    WindowUpdateFrameImpl(final int windowInc) {
        super(4, FrameType.WINDOW_UPDATE, NO_FLAGS, 0);
        this.windowInc = windowInc;
    }

    @Override
    public int getWindowSizeIncrement() {
        return windowInc;
    }

    void writeTo(final ByteBuffer buffer) {
        super.writeTo(buffer);
        buffer.put((byte)(windowInc >>> 24));
        buffer.put((byte)(windowInc >>> 16));
        buffer.put((byte)(windowInc >>> 8));
        buffer.put((byte)(windowInc));
    }

    static WindowUpdateFrameImpl readFrom(final ByteBuffer buffer, final Builder builder) {
        // implementation
        int windowInc = 0xFF_00_00_00 & buffer.get() << 24;
        windowInc |= 0x00_FF_00_00 & buffer.get() << 16;
        windowInc |= 0x00_00_FF_00 & buffer.get() << 8;
        windowInc |= 0x00_00_00_FF & buffer.get();
        builder.setWindowSizeIncrement(windowInc);
        return builder.build();
    }

    final static class Builder extends AbstractFrameImpl.Builder implements WindowUpdateFrame.Builder {
        int windowInc;
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
        public void setWindowSizeIncrement(final int windowInc) {
            // preconditions
            ensureThreadSafety();
            ensureNotBuilt();
            // validations
            ensure31BitsOnlySet(windowInc);
            // implementation
            this.windowInc = windowInc;
        }

        @Override
        public WindowUpdateFrameImpl build() {
            // preconditions
            ensureThreadSafety();
            ensureNotBuilt();
            // validation
            validateStreamId(streamId);
            if (windowInc <= 0) {
                throw new IllegalStateException();
            }
            // implementation
            built = true;
            return new WindowUpdateFrameImpl(windowInc);
        }

        @Override
        void validateFlags(final int flags) {
            if (flags != 0) {
                throw new IllegalArgumentException();
            }
        }

        @Override
        void validateStreamId(final int streamId) {
            ; // no validation
        }

        @Override
        void validatePayloadSize(final int payloadSize) {
            if (payloadSize != 4) {
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
