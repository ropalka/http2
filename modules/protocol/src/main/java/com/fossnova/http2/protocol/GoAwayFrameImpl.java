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

import org.fossnova.http2.protocol.ErrorCode;
import org.fossnova.http2.protocol.GoAwayFrame;

import java.nio.ByteBuffer;

/**
 * @author <a href="mailto:opalka.richard@gmail.com">Richard Opalka</a>
 */
final class GoAwayFrameImpl extends AbstractFrameImpl implements GoAwayFrame {
    private final int lastStreamId;
    private final int errorCode;
    private final byte[] debugData;

    GoAwayFrameImpl(final int payloadSize, final int lastStreamId, final int errorCode, final byte[] debugData) {
        super(payloadSize, FrameType.GOAWAY, NO_FLAGS, 0);
        this.lastStreamId = lastStreamId;
        this.errorCode = errorCode;
        this.debugData = debugData;
    }

    @Override
    public final int getLastStreamId() {
        return lastStreamId;
    }

    @Override
    public final int getErrorCode() {
        return errorCode;
    }

    @Override
    public final byte[] getAdditionalDebugData() {
        return debugData != null ? debugData.clone() : null;
    }

    void writeTo(final ByteBuffer buffer) {
        super.writeTo(buffer);
        buffer.put((byte)(lastStreamId >>> 24));
        buffer.put((byte)(lastStreamId >>> 16));
        buffer.put((byte)(lastStreamId >>> 8));
        buffer.put((byte)(lastStreamId));
        buffer.put((byte)(errorCode >>> 24));
        buffer.put((byte)(errorCode >>> 16));
        buffer.put((byte)(errorCode >>> 8));
        buffer.put((byte)(errorCode));
        if (debugData != null) {
            buffer.put(debugData);
        }
    }

    static GoAwayFrameImpl readFrom(final ByteBuffer buffer, final Builder builder) {
        // implementation
        int lastStreamId = 0xFF_00_00_00 & buffer.get() << 24;
        lastStreamId |= 0x00_FF_00_00 & buffer.get() << 16;
        lastStreamId |= 0x00_00_FF_00 & buffer.get() << 8;
        lastStreamId |= 0x00_00_00_FF & buffer.get();
        builder.setLastStreamId(lastStreamId);

        int errorCode = 0xFF_00_00_00 & buffer.get() << 24;
        errorCode |= 0x00_FF_00_00 & buffer.get() << 16;
        errorCode |= 0x00_00_FF_00 & buffer.get() << 8;
        errorCode |= 0x00_00_00_FF & buffer.get();
        builder.setErrorCode(errorCode);

        if (builder.payloadSize > 8) {
            byte[] debugData = new byte[builder.payloadSize - 8];
            buffer.get(debugData);
            builder.setAdditionalDebugData(debugData);
        }

        return builder.build();
    }

    final static class Builder extends AbstractFrameImpl.Builder implements GoAwayFrame.Builder {
        int lastStreamId;
        int errorCode;
        byte[] debugInfo;
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
        public void setLastStreamId(final int lastStreamId) {
            // preconditions
            ensureThreadSafety();
            ensureNotBuilt();
            // validations
            if (validate) {
                if (server) {
                    if (request) {
                        ensureServerStreamId(lastStreamId);
                    } else {
                        ensureClientStreamId(lastStreamId);
                    }
                } else {
                    if (request) {
                        ensureServerStreamId(lastStreamId);
                    } else {
                        ensureClientStreamId(lastStreamId);
                    }
                }
            }
            // implementation
            this.lastStreamId = lastStreamId;
        }

        @Override
        public void setErrorCode(final int errorCode) {
            // preconditions
            ensureThreadSafety();
            ensureNotBuilt();
            // validations
            if (validate) {
                if (errorCode < 0) {
                    throw new IllegalArgumentException();
                }
            }
            // implementation
            this.errorCode = validate && errorCode > ErrorCode.HTTP_1_1_REQUIRED ? ErrorCode.INTERNAL_ERROR : errorCode;
        }

        @Override
        public void setAdditionalDebugData(final byte[] debugInfo) {
            // preconditions
            ensureThreadSafety();
            ensureNotBuilt();
            ensureNotNull(debugInfo);
            // implementation
            this.debugInfo = debugInfo;
        }

        @Override
        public GoAwayFrameImpl build() {
            // preconditions
            ensureThreadSafety();
            ensureNotBuilt();
            // validation
            if (lastStreamId == 0) {
                throw new IllegalStateException();
            }
            // implementation
            built = true;
            return new GoAwayFrameImpl(debugInfo == null ? 8 : (8 + debugInfo.length), lastStreamId, errorCode, debugInfo);
        }

        @Override
        void validateFlags(final int flags) {
            if (flags != 0) {
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
            if (payloadSize < 8) {
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
