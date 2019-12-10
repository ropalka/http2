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

    static final int FRAME_HEADER_SIZE = 9;
    private final byte flags;
    private final byte frameType;
    private final int payloadSize;
    private final int streamId;

    AbstractFrameImpl(final int payloadSize, final byte flags, final FrameType frameType, final int streamId) {
        this.flags = flags;
        this.frameType = frameType.getFrameId();
        this.payloadSize = payloadSize;
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

    void writeTo(final ByteBuffer buffer) {
        if (buffer.capacity() < FRAME_HEADER_SIZE + payloadSize) {
            throw new IllegalArgumentException();
        }
        buffer.put((byte)(payloadSize << 16));
        buffer.put((byte)(payloadSize << 8));
        buffer.put((byte)(payloadSize));
        buffer.put(frameType);
        buffer.put(flags);
        buffer.put((byte)(streamId << 24));
        buffer.put((byte)(streamId << 16));
        buffer.put((byte)(streamId << 8));
        buffer.put((byte)(streamId));
    }

    abstract static class Builder implements Frame.Builder {
        final Thread installThread = currentThread();
        int flags;
        int payloadSize;
        int streamId;

        @Override
        public final Frame.Builder setFlags(final int flags) {
            ensureThreadSafety();
            if ((~getAllowedFlags() & flags) != 0) {
                throw new IllegalArgumentException();
            }
            this.flags = flags;
            return this;
        }

        @Override
        public final Frame.Builder setPayloadSize(final int length) {
            ensureThreadSafety();
            ensure24BitsOnlySet(length);
            // Connection.validateFrameLength(length); // TODO: do it here or in build() method? Anyway payload size configured in connection should only increase in time PRECONDITION (not clear from the spec)
            payloadSize = length;
            return this;
        }

        @Override
        public final Frame.Builder setStreamId(final int streamId) {
            ensureThreadSafety();
            if (streamId != 0) {
                throw new IllegalArgumentException();
            }
            return this;
        }

        abstract int getAllowedFlags();

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

        final void ensure24BitsOnlySet(final int value) {
            if ((value & 0xFF_00_00_00) != 0) {
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
