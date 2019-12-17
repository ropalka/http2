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

import org.fossnova.http2.protocol.SettingsFrame;

import java.nio.ByteBuffer;

/**
 * @author <a href="mailto:opalka.richard@gmail.com">Richard Opalka</a>
 */
final class SettingsFrameImpl extends AbstractFrameImpl implements SettingsFrame {
    private final int[] settings;

    SettingsFrameImpl(final int payloadSize, final byte flags, final int[] settings) {
        super(payloadSize, FrameType.SETTINGS, flags, 0);
        this.settings = settings;
    }

    @Override
    public int getParameter(final int paramId) {
        return (paramId < 1 || paramId > 6) ? 0 : settings[paramId - 1];
    }

    void writeTo(final ByteBuffer buffer) {
        super.writeTo(buffer);
        if (settings == null) return;
        // serialize SETTINGS_HEADER_TABLE_SIZE
        buffer.put((byte)(HEADER_TABLE_SIZE >>> 8));
        buffer.put((byte)(HEADER_TABLE_SIZE));
        buffer.put((byte)(settings[HEADER_TABLE_SIZE - 1] >>> 24));
        buffer.put((byte)(settings[HEADER_TABLE_SIZE - 1] >>> 16));
        buffer.put((byte)(settings[HEADER_TABLE_SIZE - 1] >>> 8));
        buffer.put((byte)(settings[HEADER_TABLE_SIZE - 1]));
        // serialize SETTINGS_ENABLE_PUSH
        buffer.put((byte)(ENABLE_PUSH >>> 8));
        buffer.put((byte)(ENABLE_PUSH));
        buffer.put((byte)(settings[ENABLE_PUSH - 1] >>> 24));
        buffer.put((byte)(settings[ENABLE_PUSH - 1] >>> 16));
        buffer.put((byte)(settings[ENABLE_PUSH - 1] >>> 8));
        buffer.put((byte)(settings[ENABLE_PUSH - 1]));
        // serialize SETTINGS_MAX_CONCURRENT_STREAMS
        buffer.put((byte)(MAX_CONCURRENT_STREAMS >>> 8));
        buffer.put((byte)(MAX_CONCURRENT_STREAMS));
        buffer.put((byte)(settings[MAX_CONCURRENT_STREAMS - 1] >>> 24));
        buffer.put((byte)(settings[MAX_CONCURRENT_STREAMS - 1] >>> 16));
        buffer.put((byte)(settings[MAX_CONCURRENT_STREAMS - 1] >>> 8));
        buffer.put((byte)(settings[MAX_CONCURRENT_STREAMS - 1]));
        // serialize SETTINGS_INITIAL_WINDOW_SIZE
        buffer.put((byte)(INITIAL_WINDOW_SIZE >>> 8));
        buffer.put((byte)(INITIAL_WINDOW_SIZE));
        buffer.put((byte)(settings[INITIAL_WINDOW_SIZE - 1] >>> 24));
        buffer.put((byte)(settings[INITIAL_WINDOW_SIZE - 1] >>> 16));
        buffer.put((byte)(settings[INITIAL_WINDOW_SIZE - 1] >>> 8));
        buffer.put((byte)(settings[INITIAL_WINDOW_SIZE - 1]));
        // serialize SETTINGS_MAX_FRAME_SIZE
        buffer.put((byte)(MAX_FRAME_SIZE >>> 8));
        buffer.put((byte)(MAX_FRAME_SIZE));
        buffer.put((byte)(settings[MAX_FRAME_SIZE - 1] >>> 24));
        buffer.put((byte)(settings[MAX_FRAME_SIZE - 1] >>> 16));
        buffer.put((byte)(settings[MAX_FRAME_SIZE - 1] >>> 8));
        buffer.put((byte)(settings[MAX_FRAME_SIZE - 1]));
        // serialize SETTINGS_MAX_HEADER_LIST_SIZE
        buffer.put((byte)(MAX_HEADER_LIST_SIZE >>> 8));
        buffer.put((byte)(MAX_HEADER_LIST_SIZE));
        buffer.put((byte)(settings[MAX_HEADER_LIST_SIZE - 1] >>> 24));
        buffer.put((byte)(settings[MAX_HEADER_LIST_SIZE - 1] >>> 16));
        buffer.put((byte)(settings[MAX_HEADER_LIST_SIZE - 1] >>> 8));
        buffer.put((byte)(settings[MAX_HEADER_LIST_SIZE - 1]));
    }

    static SettingsFrameImpl readFrom(final ByteBuffer buffer, final Builder builder) {
        // implementation
        int id, value;
        for (int i = 0; i < builder.payloadSize / 6; i++) {
            // settings name
            id = 0x00_00_FF_00 & buffer.get() << 8;
            id |= 0x00_00_00_FF & buffer.get();
            // settings value
            value = 0xFF_00_00_00 & buffer.get() << 24;
            value |= 0x00_FF_00_00 & buffer.get() << 16;
            value |= 0x00_00_FF_00 & buffer.get() << 8;
            value |= 0x00_00_00_FF & buffer.get();
            builder.setParameter(id, value);
        }
        return builder.build();
    }

    final static class Builder extends AbstractFrameImpl.Builder implements SettingsFrame.Builder {
        private static final int[] DEFAULT_VALUES = {
                DEFAULT_HEADER_TABLE_SIZE, DEFAULT_ENABLE_PUSH, DEFAULT_MAX_CONCURRENT_STREAMS, DEFAULT_INITIAL_WINDOW_SIZE, DEFAULT_MAX_FRAME_SIZE, DEFAULT_MAX_HEADER_LIST_SIZE
        };

        int[] values;
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
        public void setParameter(final int id, final int value) {
            // preconditions
            ensureThreadSafety();
            ensureNotBuilt();
            if (values == null) values = DEFAULT_VALUES.clone();
            if (id < 1 || id > 6) return;
            // validation
            if (validate) {
                if (id == HEADER_TABLE_SIZE) {
                    if (value < 0) throw new IllegalArgumentException();
                } else if (id == ENABLE_PUSH) {
                    if (value != 0 && value != 1) throw new IllegalArgumentException();
                } else if (id == MAX_CONCURRENT_STREAMS) {
                    if (value < 0) throw new IllegalArgumentException();
                } else if (id == INITIAL_WINDOW_SIZE) {
                    if (value < 0) throw new IllegalArgumentException();
                } else if (id == MAX_FRAME_SIZE) {
                    if (value < 1 << 14 || value > 1 << 24) throw new IllegalArgumentException();
                } else if (id == MAX_HEADER_LIST_SIZE) {
                    if (value < 0) throw new IllegalArgumentException();
                    // TODO: revisit this validation once header compression is implemented
                }
            }
            // implementation
            values[id - 1] = value;
        }

        @Override
        public SettingsFrameImpl build() {
            // preconditions
            ensureThreadSafety();
            ensureNotBuilt();
            // validation
            if (validate) {
                validateStreamId(streamId);
                if (((flags & FLAG_ACK) != 0) && values != null) {
                    throw new IllegalStateException();
                }
            }
            // implementation
            built = true;
            return new SettingsFrameImpl((flags & FLAG_ACK) != 0 ? 0 : 36, (byte)flags, values);
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
            if (payloadSize % 6 != 0) {
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
