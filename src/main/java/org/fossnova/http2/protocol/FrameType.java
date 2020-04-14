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

/**
 * @author <a href="mailto:opalka.richard@gmail.com">Richard Opalka</a>
 */
enum FrameType {
    DATA((byte)0x0),
    HEADERS((byte)0x1),
    PRIORITY((byte)0x2),
    RST_STREAM((byte)0x3),
    SETTINGS((byte)0x4),
    PUSH_PROMISE((byte)0x5),
    PING((byte)0x6),
    GOAWAY((byte)0x7),
    WINDOW_UPDATE((byte)0x8),
    CONTINUATION((byte)0x9);

    private static final FrameType[] LOOKUP_TABLE = {
         DATA, HEADERS, PRIORITY, RST_STREAM, SETTINGS, PUSH_PROMISE, PING, GOAWAY, WINDOW_UPDATE, CONTINUATION
    };

    private final byte frameId;

    FrameType(final byte frameId) {
        this.frameId = frameId;
    }

    byte getFrameId() {
        return frameId;
    }

    public static FrameType of(final int value) {
        return 0x0 <= value && value <= 0x9 ? LOOKUP_TABLE[value] : null;
    }
}
