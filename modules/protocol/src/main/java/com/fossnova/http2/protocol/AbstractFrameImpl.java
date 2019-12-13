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

import static java.lang.Thread.currentThread;

import org.fossnova.http2.protocol.Frame;

import java.nio.ByteBuffer;
import java.util.ConcurrentModificationException;

/**
 * @author <a href="mailto:opalka.richard@gmail.com">Richard Opalka</a>
 */
abstract class AbstractFrameImpl implements Frame {

    private static final int FRAME_HEADER_SIZE = 9;
    static final byte[] EMPTY_ARRAY = new byte[0];
    private final byte flags;
    private final byte frameType;
    private final int payloadSize;
    private final int streamId;

    AbstractFrameImpl(final int payloadSize, final FrameType frameType, final byte flags, final int streamId) {
        this.payloadSize = payloadSize;
        this.frameType = frameType.getFrameId();
        this.flags = flags;
        this.streamId = streamId;
    }

    @Override
    public final int getPayloadSize() {
        return payloadSize;
    }

    @Override
    public final byte getFlags() {
        return flags;
    }

    @Override
    public final int getStreamId() {
        return streamId;
    }

    void writeTo(final ByteBuffer buffer) {
        if (buffer.capacity() < FRAME_HEADER_SIZE + payloadSize) {
            throw new IllegalArgumentException();
        }
        buffer.put((byte)(payloadSize >>> 16));
        buffer.put((byte)(payloadSize >>> 8));
        buffer.put((byte)(payloadSize));
        buffer.put(frameType);
        buffer.put(flags);
        buffer.put((byte)(streamId >>> 24));
        buffer.put((byte)(streamId >>> 16));
        buffer.put((byte)(streamId >>> 8));
        buffer.put((byte)(streamId));
    }

    static AbstractFrameImpl readFrom(final ByteBuffer buffer, final boolean server, final boolean validate) {
        if (buffer.capacity() < FRAME_HEADER_SIZE) {
            throw new IllegalArgumentException();
        }
        int payloadSize = 0x00_FF_00_00 & buffer.get() << 16;
        payloadSize |= 0x00_00_FF_00 & buffer.get() << 8;
        payloadSize |= 0x00_00_00_FF & buffer.get();
        final FrameType frameType = FrameType.of(buffer.get());
        byte flags = buffer.get();
        int streamId = 0xFF_00_00_00 & buffer.get() << 24;
        streamId |= 0x00_FF_00_00 & buffer.get() << 16;
        streamId |= 0x00_00_FF_00 & buffer.get() << 8;
        streamId |= 0x00_00_00_FF & buffer.get();

        if (frameType == FrameType.GOAWAY) {
            return GoAwayFrameImpl.readFrom(buffer, new GoAwayFrameImpl.Builder(server, server, validate, payloadSize, frameType, flags, streamId));
        } else if (frameType == FrameType.CONTINUATION) {
            return ContinuationFrameImpl.readFrom(buffer, new ContinuationFrameImpl.Builder(server, server, validate, payloadSize, frameType, flags, streamId));
        } else if (frameType == FrameType.DATA) {
            return DataFrameImpl.readFrom(buffer, new DataFrameImpl.Builder(server, server, validate, payloadSize, frameType, flags, streamId));
        } else if (frameType == FrameType.HEADERS) {
            return HeadersFrameImpl.readFrom(buffer, new HeadersFrameImpl.Builder(server, server, validate, payloadSize, frameType, flags, streamId));
        } else {
            throw new UnsupportedOperationException(); // TODO: implement
        }
    }

    abstract static class Builder implements Frame.Builder {
        final Thread installThread = currentThread();
        final boolean server;
        final boolean request;
        final boolean validate;
        int payloadSize;
        FrameType frameType;
        int flags;
        int streamId;

        Builder(final boolean server, final boolean request, final boolean validate) {
            this.server = server;
            this.request = request;
            this.validate = validate;
        }

        @Override
        public final Frame.Builder setFlags(final int flags) {
            // preconditions
            ensureThreadSafety();
            // validations
            if (validate) {
                validateFlags(flags);
            }
            // implementation
            this.flags = flags;
            return this;
        }

        @Override
        public final Frame.Builder setPayloadSize(final int length) {
            // preconditions
            ensureThreadSafety();
            // validations
            if (validate) {
                ensure24BitsOnlySet(length);
                validatePayloadSize(length);
            }
            // Connection.validateFrameLength(length); // TODO: do it here or in build() method? Anyway payload size configured in connection should only increase in time PRECONDITION (not clear from the spec)
            // implementation
            payloadSize = length;
            return this;
        }

        @Override
        public final Frame.Builder setStreamId(final int streamId) {
            // preconditions
            ensureThreadSafety();
            // validations
            if (validate) {
                validateStreamId(streamId);
            }
            // implementation
            this.streamId = streamId;
            return this;
        }

        final Frame.Builder setFrameType(final FrameType frameType) {
            // preconditions
            ensureThreadSafety();
            ensureNotNull(frameType);
            // implementation
            this.frameType = frameType;
            return this;
        }

        abstract void validateFlags(int flags);
        abstract void validateStreamId(int streamId);
        abstract void validatePayloadSize(int payloadSize);

        final void ensureThreadSafety() {
            if (currentThread() != installThread) {
                throw new ConcurrentModificationException();
            }
        }

        final void ensureNotNull(final Object o) {
            if (o == null) {
                throw new NullPointerException();
            }
        }

        final void ensure8BitsOnlySet(final int value) {
            if ((value & 0xFF_FF_FF_00) != 0) {
                throw new IllegalArgumentException();
            }
        }

        final void ensure24BitsOnlySet(final int value) {
            if ((value & 0xFF_00_00_00) != 0) {
                throw new IllegalArgumentException();
            }
        }

        final void ensure31BitsOnlySet(final int value) {
            if ((value & 0b10000000_00000000_00000000_00000000) != 0) {
                throw new IllegalArgumentException();
            }
        }

        final void ensureClientStreamId(final int value) {
            if (value <= 0 || value % 2 == 0) {
                throw new IllegalArgumentException();
            }
        }

        final void ensureServerStreamId(final int value) {
            if (value <= 0 || value % 2 == 1) {
                throw new IllegalArgumentException();
            }
        }
    }
}
