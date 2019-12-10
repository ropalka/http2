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
package org.fossnova.http2.protocol;

public enum ErrorCode {
    NO_ERROR(0x0),
    PROTOCOL_ERROR(0x1),
    INTERNAL_ERROR(0x2),
    FLOW_CONTROL_ERROR(0x3),
    SETTINGS_TIMEOUT(0x4),
    STREAM_CLOSED(0x5),
    FRAME_SIZE_ERROR(0x6),
    REFUSED_STREAM(0x7),
    CANCEL(0x8),
    COMPRESSION_ERROR(0x9),
    CONNECT_ERROR(0xa),
    ENHANCE_YOUR_CALM(0xb),
    INADEQUATE_SECURITY(0xc),
    HTTP_1_1_REQUIRED(0xd);

    private static final ErrorCode[] LOOKUP_TABLE = {
        NO_ERROR, PROTOCOL_ERROR, INTERNAL_ERROR, FLOW_CONTROL_ERROR,
        SETTINGS_TIMEOUT, STREAM_CLOSED, FRAME_SIZE_ERROR, REFUSED_STREAM, CANCEL, COMPRESSION_ERROR,
        CONNECT_ERROR, ENHANCE_YOUR_CALM, INADEQUATE_SECURITY, HTTP_1_1_REQUIRED
    };

    private final int code;

    ErrorCode(final int code) {
        this.code = code;
    }

    public int getValue() {
        return code;
    }

    /**
     * Returns associated HTTP 2 error code for specified value, or <code>INTERNAL_ERROR</code> for unknown value.
     * @param value to get enum for.
     * @return enum associated with given value, or <code>INTERNAL_ERROR</code> for unknown value.
     */
    public static ErrorCode of(final int value) {
        return 0 <= value && value <= 14 ? LOOKUP_TABLE[value] : INTERNAL_ERROR;
    }
}
