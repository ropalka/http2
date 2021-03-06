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

import static java.lang.Thread.currentThread;

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

    final byte[] writeHeader() {
        final byte[] buffer = new byte[FRAME_HEADER_SIZE];
        int i = 0;
        buffer[i++] = (byte)(payloadSize >>> 16);
        buffer[i++] = (byte)(payloadSize >>> 8);
        buffer[i++] = (byte)(payloadSize);
        buffer[i++] = frameType;
        buffer[i++] = flags;
        buffer[i++] = (byte)(streamId >>> 24);
        buffer[i++] = (byte)(streamId >>> 16);
        buffer[i++] = (byte)(streamId >>> 8);
        buffer[i++] = (byte)(streamId);
        return buffer;
    }

    abstract byte[] writePayload();

    static AbstractFrameImpl readFrom(final byte[] headerBuffer, final byte[] payloadBuffer, final boolean server, final boolean validate) {
        int i = 0;
        int payloadSize = 0x00_FF_00_00 & headerBuffer[i++] << 16;
        payloadSize |= 0x00_00_FF_00 & headerBuffer[i++] << 8;
        payloadSize |= 0x00_00_00_FF & headerBuffer[i++];
        final FrameType frameType = FrameType.of(headerBuffer[i++]);
        byte flags = headerBuffer[i++];
        int streamId = 0xFF_00_00_00 & headerBuffer[i++] << 24;
        streamId |= 0x00_FF_00_00 & headerBuffer[i++] << 16;
        streamId |= 0x00_00_FF_00 & headerBuffer[i++] << 8;
        streamId |= 0x00_00_00_FF & headerBuffer[i++];

        if (frameType == FrameType.GOAWAY) {
            return GoAwayFrameImpl.readFrom(payloadBuffer, new GoAwayFrameImpl.Builder(server, server, validate, payloadSize, frameType, flags, streamId));
        } else if (frameType == FrameType.CONTINUATION) {
            return ContinuationFrameImpl.readFrom(payloadBuffer, new ContinuationFrameImpl.Builder(server, server, validate, payloadSize, frameType, flags, streamId));
        } else if (frameType == FrameType.DATA) {
            return DataFrameImpl.readFrom(payloadBuffer, new DataFrameImpl.Builder(server, server, validate, payloadSize, frameType, flags, streamId));
        } else if (frameType == FrameType.HEADERS) {
            return HeadersFrameImpl.readFrom(payloadBuffer, new HeadersFrameImpl.Builder(server, server, validate, payloadSize, frameType, flags, streamId));
        } else if (frameType == FrameType.PING) {
            return PingFrameImpl.readFrom(payloadBuffer, new PingFrameImpl.Builder(server, server, validate, payloadSize, frameType, flags, streamId));
        } else if (frameType == FrameType.PRIORITY) {
            return PriorityFrameImpl.readFrom(payloadBuffer, new PriorityFrameImpl.Builder(server, server, validate, payloadSize, frameType, flags, streamId));
        } else if (frameType == FrameType.PUSH_PROMISE) {
            return PushPromiseFrameImpl.readFrom(payloadBuffer, new PushPromiseFrameImpl.Builder(server, server, validate, payloadSize, frameType, flags, streamId));
        } else if (frameType == FrameType.RST_STREAM) {
            return RstStreamFrameImpl.readFrom(payloadBuffer, new RstStreamFrameImpl.Builder(server, server, validate, payloadSize, frameType, flags, streamId));
        } else if (frameType == FrameType.WINDOW_UPDATE) {
            return WindowUpdateFrameImpl.readFrom(payloadBuffer, new WindowUpdateFrameImpl.Builder(server, server, validate, payloadSize, frameType, flags, streamId));
        } else if (frameType == FrameType.SETTINGS) {
            return SettingsFrameImpl.readFrom(payloadBuffer, new SettingsFrameImpl.Builder(server, server, validate, payloadSize, frameType, flags, streamId));
        } else {
            return null; // indicates unknown frame types
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
                ensure31BitsOnlySet(streamId);
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
