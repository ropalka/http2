/*
 * Copyright (c) 2012-2019, FOSS Nova Software foundation (FNSF;
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

public final class ErrorCode {
    public static final int NO_ERROR = 0x0;
    public static final int PROTOCOL_ERROR = 0x1;
    public static final int INTERNAL_ERROR = 0x2;
    public static final int FLOW_CONTROL_ERROR = 0x3;
    public static final int SETTINGS_TIMEOUT = 0x4;
    public static final int STREAM_CLOSED = 0x5;
    public static final int FRAME_SIZE_ERROR = 0x6;
    public static final int REFUSED_STREAM = 0x7;
    public static final int CANCEL = 0x8;
    public static final int COMPRESSION_ERROR = 0x9;
    public static final int CONNECT_ERROR = 0xa;
    public static final int ENHANCE_YOUR_CALM = 0xb;
    public static final int INADEQUATE_SECURITY = 0xc;
    public static final int HTTP_1_1_REQUIRED = 0xd;

    private ErrorCode() {
        // forbidden instantiation
    }
}
