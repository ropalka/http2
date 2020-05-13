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

import java.nio.charset.StandardCharsets;

/**
 * @author <a href="mailto:ropalka@redhat.com">Richard Opalka</a>
 */
final class Http2ConnectionPreface {

    private static final String CR_LF = "\r\n";

    // TODO: Turn this class into 'factory' pattern to construct the following string only once
    private Http2ConnectionPreface() {
        // forbidden instantiation
    }

    static byte[] newHttp20ConnectionPreface() {
        final StringBuilder sb = new StringBuilder();
        sb.append("HTTP/2.0").append(CR_LF).append(CR_LF);
        sb.append("SM").append(CR_LF).append(CR_LF);
        return sb.toString().getBytes(StandardCharsets.US_ASCII);
    }

    static byte[] newUpgradeToHttp2Request(final String host) {
        final StringBuilder sb = new StringBuilder();
        sb.append("GET / HTTP/1.1").append(CR_LF);
        sb.append("Host: ").append(host).append(CR_LF);
        sb.append("Connection: Upgrade, HTTP2-Settings").append(CR_LF);
        sb.append("Upgrade: h2c").append(CR_LF);
        sb.append("HTTP2-Settings: ").append(CR_LF);
        sb.append(CR_LF);
        return sb.toString().getBytes(StandardCharsets.US_ASCII);
    }

}
