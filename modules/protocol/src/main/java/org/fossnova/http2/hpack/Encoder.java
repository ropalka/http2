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

import org.fossnova.http2.HeaderName;

import static org.fossnova.http2.HeaderName.*;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * // TODO: javadoc
 * @author <a href="mailto:opalka.richard@gmail.com">Richard Opalka</a>
 */
public final class Encoder {

    public enum Instruction {
        INDEXED((byte) 0x80),
        SIZE_UPDATE((byte) 0x20),
        WITH_INDEXING((byte) 0x40),
        WITHOUT_INDEXING((byte) 0x0),
        NEVER_INDEXED((byte) 0x10);

        private final byte flag;

        Instruction(final byte flag) {
            this.flag = flag;
        }

        public static Instruction of(final byte b) {
            if ((b & INDEXED.flag) != 0) return INDEXED;
            if ((b & SIZE_UPDATE.flag) != 0) return SIZE_UPDATE;
            if ((b & WITH_INDEXING.flag) != 0) return WITH_INDEXING;
            if ((b & NEVER_INDEXED.flag) != 0) return NEVER_INDEXED;
            return WITHOUT_INDEXING;
        }

    }

    private static final Charset CHARSET = StandardCharsets.UTF_8;
    private static final byte[][] STATIC_TABLE_HEADER_NAMES = new byte[62][];
    private static final byte[][] STATIC_TABLE_HEADER_VALUES = new byte[17][];

    static {
        STATIC_TABLE_HEADER_NAMES[1] = AUTHORITY.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[2] = METHOD.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[3] = METHOD.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[4] = PATH.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[5] = PATH.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[6] = SCHEME.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[7] = SCHEME.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[8] = STATUS.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[9] = STATUS.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[10] = STATUS.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[11] = STATUS.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[12] = STATUS.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[13] = STATUS.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[14] = STATUS.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[15] = ACCEPT_CHARSET.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[16] = ACCEPT_ENCODING.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[17] = ACCEPT_LANGUAGE.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[18] = ACCEPT_RANGES.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[19] = ACCEPT.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[20] = ACCESS_CONTROL_ALLOW_ORIGIN.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[21] = AGE.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[22] = ALLOW.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[23] = AUTHORIZATION.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[24] = CACHE_CONTROL.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[25] = CONTENT_DISPOSITION.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[26] = CONTENT_ENCODING.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[27] = CONTENT_LANGUAGE.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[28] = CONTENT_LENGTH.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[29] = CONTENT_LOCATION.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[30] = CONTENT_RANGE.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[31] = CONTENT_TYPE.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[32] = COOKIE.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[33] = DATE.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[34] = ETAG.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[35] = EXPECT.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[36] = EXPIRES.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[37] = FROM.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[38] = HOST.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[39] = IF_MATCH.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[40] = IF_MODIFIED_SINCE.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[41] = IF_NONE_MATCH.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[42] = IF_RANGE.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[43] = IF_UNMODIFIED_SINCE.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[44] = LAST_MODIFIED.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[45] = LINK.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[46] = LOCATION.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[47] = MAX_FORWARDS.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[48] = PROXY_AUTHENTICATE.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[49] = PROXY_AUTHORIZATION.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[50] = RANGE.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[51] = REFERER.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[52] = REFRESH.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[53] = RETRY_AFTER.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[54] = SERVER.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[55] = SET_COOKIE.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[56] = STRICT_TRANSPORT_SECURITY.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[57] = TRANSFER_ENCODING.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[58] = USER_AGENT.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[59] = VARY.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[60] = VIA.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[61] = WWW_AUTHENTICATE.getLowerCaseName().getBytes(CHARSET);

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

    public void addHeader(final HeaderName name, final String value, final Instruction i) {
        // TODO: implement
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
