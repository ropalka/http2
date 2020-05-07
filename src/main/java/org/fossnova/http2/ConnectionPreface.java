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
package org.fossnova.http2;

import java.nio.ByteBuffer;

/**
 * @author <a href="mailto:ropalka@redhat.com">Richard Opalka</a>
 */
public final class ConnectionPreface {

    private ConnectionPreface() {
        // forbidden instantiation
    }

    private static final byte[] CLIENT = new byte[] {
            0x50, 0x52, 0x49, 0x20, 0x2A,
            0x20, 0x48, 0x54, 0x54, 0x50,
            0x2F, 0x32, 0x2E, 0x30, 0x0D,
            0x0A, 0x0D, 0x0A, 0x53, 0x4D,
            0x0D, 0x0A, 0x0D, 0x0A
    };

    private static final byte[] SERVER = null;

    public static ByteBuffer getClientInitialBytes() {
        final byte[] retVal = new byte[CLIENT.length];
        System.arraycopy(CLIENT, 0, retVal, 0, CLIENT.length);
        return ByteBuffer.wrap(retVal);
    }

}
