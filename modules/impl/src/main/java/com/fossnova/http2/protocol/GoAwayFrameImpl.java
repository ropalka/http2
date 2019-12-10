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
import org.fossnova.http2.protocol.Frame;
import org.fossnova.http2.protocol.GoAwayFrame;

import java.nio.ByteBuffer;

/**
 * @author <a href="mailto:opalka.richard@gmail.com">Richard Opalka</a>
 */
final class GoAwayFrameImpl extends AbstractFrameImpl implements GoAwayFrame {
    private final int lastStreamId;
    private final ErrorCode reason;
    private final byte[] debugData;

    GoAwayFrameImpl(final int payloadSize, final int lastStreamId, final ErrorCode reason, final byte[] debugData) {
        super(payloadSize, NO_FLAGS, FrameType.GOAWAY, 0);
        this.lastStreamId = lastStreamId;
        this.reason = reason;
        this.debugData = debugData;
    }

    @Override
    public final int getLastStreamId() {
        return lastStreamId;
    }

    @Override
    public final ErrorCode getErrorCode() {
        return reason;
    }

    @Override
    public final byte[] getAdditionalDebugData() {
        return debugData.clone();
    }

    void writeTo(final ByteBuffer buffer) {
        super.writeTo(buffer);
        buffer.put((byte)(lastStreamId << 24));
        buffer.put((byte)(lastStreamId << 16));
        buffer.put((byte)(lastStreamId << 8));
        buffer.put((byte)(lastStreamId));
        buffer.put((byte)(reason.getValue() << 24));
        buffer.put((byte)(reason.getValue() << 16));
        buffer.put((byte)(reason.getValue() << 8));
        buffer.put((byte)(reason.getValue()));
        if (debugData != null) {
            buffer.put(debugData);
        }
    }

    final static class Builder extends AbstractFrameImpl.Builder implements GoAwayFrame.Builder {
        final boolean serverSide;
        boolean built;
        int lastStreamId;
        ErrorCode reason;
        byte[] debugInfo;

        Builder(final boolean serverSide) {
            this.serverSide = serverSide;
        }

        @Override
        public void setLastStreamId(final int lastStreamId) {
            // preconditions
            ensureThreadSafety();
            ensureNotBuilt();
            if (serverSide) {
                ensureClientStreamId(lastStreamId);
            } else {
                ensureServerStreamId(lastStreamId);
            }
            // implementation
            this.lastStreamId = lastStreamId;
        }

        @Override
        public void setErrorCode(final ErrorCode reason) {
            // preconditions
            ensureThreadSafety();
            ensureNotBuilt();
            ensureNotNull(reason);
            // implementation
            this.reason = reason;
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
        public GoAwayFrame build() {
            // preconditions
            ensureThreadSafety();
            ensureNotBuilt();
            // implementation
            built = true;
            if ((reason == null) || lastStreamId == 0) {
                throw new IllegalStateException();
            }
            return new GoAwayFrameImpl(debugInfo == null ? 8 : (8 + debugInfo.length), lastStreamId, reason, debugInfo);
        }

        @Override
        int getAllowedFlags() {
            return Frame.NO_FLAGS;
        }

        private void ensureNotBuilt() {
            if (built) {
                throw new IllegalStateException();
            }
        }

    }
}
