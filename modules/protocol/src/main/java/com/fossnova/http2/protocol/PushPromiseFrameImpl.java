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

import org.fossnova.http2.protocol.PushPromiseFrame;

import java.nio.ByteBuffer;

/**
 * @author <a href="mailto:opalka.richard@gmail.com">Richard Opalka</a>
 */
final class PushPromiseFrameImpl extends AbstractFrameImpl implements PushPromiseFrame {
    private final int promisedStreamId;
    private final byte[] data;

    PushPromiseFrameImpl(final int payloadSize, final byte flags, final int streamId, final int promisedStreamId, final byte[] data) {
        super(payloadSize, FrameType.PUSH_PROMISE, flags, streamId);
        this.promisedStreamId = promisedStreamId;
        this.data = data;
    }

    @Override
    public int getPromisedStreamId() {
        return promisedStreamId;
    }

    @Override
    public byte[] getHeaderBlockFragment() {
        return data != null ? data.clone() : null;
    }

    void writeTo(final ByteBuffer buffer) {
        super.writeTo(buffer);
        final int padLength = getPayloadSize() - data.length;
        if ((getFlags() & FLAG_PADDED) != 0) {
            buffer.put((byte) padLength);
        }
        buffer.put((byte)(promisedStreamId >>> 24));
        buffer.put((byte)(promisedStreamId >>> 16));
        buffer.put((byte)(promisedStreamId >>> 8));
        buffer.put((byte)(promisedStreamId));
        if (data.length != 0) {
            buffer.put(data);
        }
        if (padLength != 0) {
            buffer.put(new byte[padLength]);
        }
    }

    static PushPromiseFrameImpl readFrom(final ByteBuffer buffer, final Builder builder) {
        // implementation
        int padLength = 0;
        if ((builder.flags & FLAG_PADDED) != 0) {
            padLength = 0x00_00_00_FF & buffer.get();
        }
        int promisedStreamId = 0xFF_00_00_00 & buffer.get() << 24;
        promisedStreamId |= 0x00_FF_00_00 & buffer.get() << 16;
        promisedStreamId |= 0x00_00_FF_00 & buffer.get() << 8;
        promisedStreamId |= 0x00_00_00_FF & buffer.get();
        builder.setPromisedStreamId(promisedStreamId);
        if (builder.payloadSize > padLength) {
            byte[] data = new byte[builder.payloadSize - padLength];
            buffer.get(data);
            builder.setHeaderBlockFragment(data);
        }
        if (padLength > 0) {
            buffer.get(new byte[padLength]);
        }

        return builder.build();
    }

    final static class Builder extends AbstractFrameImpl.Builder implements PushPromiseFrame.Builder {
        byte[] data = AbstractFrameImpl.EMPTY_ARRAY;
        int promisedStreamId;
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
        public void setPromisedStreamId(final int promisedStreamId) {
            // preconditions
            ensureThreadSafety();
            ensureNotBuilt();
            // validations
            if (validate) {
                ensure31BitsOnlySet(streamId);
                if (server) {
                    if (request) {
                        ensureClientStreamId(promisedStreamId);
                    } else {
                        ensureServerStreamId(promisedStreamId);
                    }
                } else {
                    if (request) {
                        ensureClientStreamId(promisedStreamId);
                    } else {
                        ensureServerStreamId(promisedStreamId);
                    }
                }
            }
            // implementation
            this.promisedStreamId = promisedStreamId;
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
        public PushPromiseFrameImpl build() {
            // preconditions
            ensureThreadSafety();
            ensureNotBuilt();
            // validation
            validateStreamId(streamId);
            if (promisedStreamId == 0) {
                throw new IllegalStateException();
            }
            if ((flags & FLAG_PADDED) != 0) {
                if (payloadSize < data.length || payloadSize - data.length >= 255) {
                    throw new IllegalStateException();
                }
            } else {
                if (payloadSize != data.length) {
                    throw new IllegalStateException();
                }
            }
            // implementation
            built = true;
            return new PushPromiseFrameImpl(payloadSize, (byte)flags, streamId, promisedStreamId, data);
        }

        @Override
        void validateFlags(final int flags) {
            if ((flags & ~(FLAG_END_HEADERS | FLAG_PADDED)) != 0) {
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
            if (payloadSize < 5) {
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
