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
package org.fossnova.http2.hpack;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * // TODO: javadoc
 * @author <a href="mailto:opalka.richard@gmail.com">Richard Opalka</a>
 */
public final class Encoder {

    private static final Charset CHARSET = StandardCharsets.UTF_8;
    private static final byte[][] STATIC_TABLE_HEADER_NAMES = new byte[62][];
    private static final byte[][] STATIC_TABLE_HEADER_VALUES = new byte[17][];

    static {
        STATIC_TABLE_HEADER_NAMES[1] = ":authority".getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[2] = ":method".getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[3] = STATIC_TABLE_HEADER_NAMES[2];
        STATIC_TABLE_HEADER_NAMES[4] = ":path".getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[5] = STATIC_TABLE_HEADER_NAMES[4];
        STATIC_TABLE_HEADER_NAMES[6] = ":scheme".getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[7] = STATIC_TABLE_HEADER_NAMES[6];
        STATIC_TABLE_HEADER_NAMES[8] = ":status".getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[9] = STATIC_TABLE_HEADER_NAMES[8];
        STATIC_TABLE_HEADER_NAMES[10] = STATIC_TABLE_HEADER_NAMES[9];
        STATIC_TABLE_HEADER_NAMES[11] = STATIC_TABLE_HEADER_NAMES[10];
        STATIC_TABLE_HEADER_NAMES[12] = STATIC_TABLE_HEADER_NAMES[11];
        STATIC_TABLE_HEADER_NAMES[13] = STATIC_TABLE_HEADER_NAMES[12];
        STATIC_TABLE_HEADER_NAMES[14] = STATIC_TABLE_HEADER_NAMES[13];
        STATIC_TABLE_HEADER_NAMES[15] = "accept-charset".getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[16] = "accept-encoding".getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[17] = "accept-language".getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[18] = "accept-ranges".getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[19] = "accept".getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[20] = "access-control-allow-origin".getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[21] = "age".getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[22] = "allow".getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[23] = "authorization".getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[24] = "cache-control".getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[25] = "content-disposition".getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[26] = "content-encoding".getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[27] = "content-language".getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[28] = "content-length".getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[29] = "content-location".getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[30] = "content-range".getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[31] = "content-type".getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[32] = "cookie".getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[33] = "date".getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[34] = "etag".getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[35] = "expect".getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[36] = "expires".getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[37] = "from".getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[38] = "host".getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[39] = "if-match".getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[40] = "if-modified-since".getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[41] = "if-none-match".getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[42] = "if-range".getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[43] = "if-unmodified-since".getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[44] = "last-modified".getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[45] = "link".getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[46] = "location".getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[47] = "max-forwards".getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[48] = "proxy-authenticate".getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[49] = "proxy-authorization".getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[50] = "range".getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[51] = "referer".getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[52] = "refresh".getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[53] = "retry-after".getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[54] = "server".getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[55] = "set-cookie".getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[56] = "strict-transport-security".getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[57] = "transfer-encoding".getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[58] = "user-agent".getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[59] = "vary".getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[60] = "via".getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[61] = "www-authenticate".getBytes(CHARSET);

        STATIC_TABLE_HEADER_VALUES[1] = new byte[0];
        STATIC_TABLE_HEADER_VALUES[2] = "GET".getBytes(CHARSET);
        STATIC_TABLE_HEADER_VALUES[3] = "POST".getBytes(CHARSET);
        STATIC_TABLE_HEADER_VALUES[4] = "/".getBytes(CHARSET);
        STATIC_TABLE_HEADER_VALUES[5] = "/index.html".getBytes(CHARSET);
        STATIC_TABLE_HEADER_VALUES[6] = "http".getBytes(CHARSET);
        STATIC_TABLE_HEADER_VALUES[7] = "https".getBytes(CHARSET);
        STATIC_TABLE_HEADER_VALUES[8] = "200".getBytes(CHARSET);
        STATIC_TABLE_HEADER_VALUES[9] = "204".getBytes(CHARSET);
        STATIC_TABLE_HEADER_VALUES[10] = "206".getBytes(CHARSET);
        STATIC_TABLE_HEADER_VALUES[11] = "304".getBytes(CHARSET);
        STATIC_TABLE_HEADER_VALUES[12] = "400".getBytes(CHARSET);
        STATIC_TABLE_HEADER_VALUES[13] = "404".getBytes(CHARSET);
        STATIC_TABLE_HEADER_VALUES[14] = "500".getBytes(CHARSET);
        STATIC_TABLE_HEADER_VALUES[15] = new byte[0];
        STATIC_TABLE_HEADER_VALUES[16] = "gzip, deflate".getBytes(CHARSET);
    }

    private volatile int maxTableSize;

    private Encoder(final int maxTableSize) {
        this.maxTableSize = maxTableSize;
    }

    /**
     * Creates new encoder.
     *
     * @param maxTableSize maximum dynamic table size in octets
     * @return new Encoder instance
     */
    public static Encoder newInstance(final int maxTableSize) {
        return new Encoder(maxTableSize);
    }
}
