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

import org.fossnova.http2.protocol.ContinuationFrame;

import java.nio.ByteBuffer;

/**
 * @author <a href="mailto:opalka.richard@gmail.com">Richard Opalka</a>
 */
final class ContinuationFrameImpl extends AbstractFrameImpl implements ContinuationFrame {
    private final byte[] headerBlockFragment;

    ContinuationFrameImpl(final int payloadSize, final byte flags, final int streamId, final byte[] headerBlockFragment) {
        super(payloadSize, FrameType.CONTINUATION, flags, streamId);
        this.headerBlockFragment = headerBlockFragment;
    }

    @Override
    public byte[] getHeaderBlockFragment() {
        return headerBlockFragment != null ? headerBlockFragment.clone() : null;
    }

    void writeTo(final ByteBuffer buffer) {
        super.writeTo(buffer);
        if (headerBlockFragment != null) {
            buffer.put(headerBlockFragment);
        }
    }

    static ContinuationFrameImpl readFrom(final ByteBuffer buffer, final Builder builder) {
        // implementation
        if (builder.payloadSize > 0) {
            byte[] headerBlockFragment = new byte[builder.payloadSize];
            buffer.get(headerBlockFragment);
            builder.setHeaderBlockFragment(headerBlockFragment);
        }

        return builder.build();
    }

    final static class Builder extends AbstractFrameImpl.Builder implements ContinuationFrame.Builder {
        byte[] headerBlockFragment;
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
        public void setHeaderBlockFragment(final byte[] headerBlockFragment) {
            // preconditions
            ensureThreadSafety();
            ensureNotBuilt();
            ensureNotNull(headerBlockFragment);
            // implementation
            this.headerBlockFragment = headerBlockFragment;
        }

        @Override
        public ContinuationFrameImpl build() {
            // preconditions
            ensureThreadSafety();
            ensureNotBuilt();
            // implementation
            validateStreamId(streamId);
            built = true;
            return new ContinuationFrameImpl(headerBlockFragment == null ? 0 : (headerBlockFragment.length), (byte)flags, streamId, headerBlockFragment);
        }

        @Override
        void validateFlags(final int flags) {
            if ((flags & ~ContinuationFrame.FLAG_END_HEADERS) != 0) {
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
