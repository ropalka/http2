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
import static org.fossnova.http2.RequestMethod.*;
import static org.fossnova.http2.RequestScheme.*;

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
        int i = 1;
        STATIC_TABLE_HEADER_NAMES[i++] = AUTHORITY.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[i++] = METHOD.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[i++] = METHOD.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[i++] = PATH.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[i++] = PATH.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[i++] = SCHEME.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[i++] = SCHEME.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[i++] = STATUS.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[i++] = STATUS.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[i++] = STATUS.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[i++] = STATUS.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[i++] = STATUS.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[i++] = STATUS.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[i++] = STATUS.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[i++] = ACCEPT_CHARSET.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[i++] = ACCEPT_ENCODING.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[i++] = ACCEPT_LANGUAGE.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[i++] = ACCEPT_RANGES.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[i++] = ACCEPT.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[i++] = ACCESS_CONTROL_ALLOW_ORIGIN.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[i++] = AGE.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[i++] = ALLOW.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[i++] = AUTHORIZATION.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[i++] = CACHE_CONTROL.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[i++] = CONTENT_DISPOSITION.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[i++] = CONTENT_ENCODING.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[i++] = CONTENT_LANGUAGE.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[i++] = CONTENT_LENGTH.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[i++] = CONTENT_LOCATION.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[i++] = CONTENT_RANGE.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[i++] = CONTENT_TYPE.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[i++] = COOKIE.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[i++] = DATE.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[i++] = ETAG.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[i++] = EXPECT.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[i++] = EXPIRES.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[i++] = FROM.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[i++] = HOST.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[i++] = IF_MATCH.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[i++] = IF_MODIFIED_SINCE.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[i++] = IF_NONE_MATCH.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[i++] = IF_RANGE.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[i++] = IF_UNMODIFIED_SINCE.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[i++] = LAST_MODIFIED.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[i++] = LINK.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[i++] = LOCATION.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[i++] = MAX_FORWARDS.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[i++] = PROXY_AUTHENTICATE.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[i++] = PROXY_AUTHORIZATION.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[i++] = RANGE.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[i++] = REFERER.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[i++] = REFRESH.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[i++] = RETRY_AFTER.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[i++] = SERVER.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[i++] = SET_COOKIE.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[i++] = STRICT_TRANSPORT_SECURITY.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[i++] = TRANSFER_ENCODING.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[i++] = USER_AGENT.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[i++] = VARY.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[i++] = VIA.getLowerCaseName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_NAMES[i++] = WWW_AUTHENTICATE.getLowerCaseName().getBytes(CHARSET);
        i = 1;
        STATIC_TABLE_HEADER_VALUES[i++] = new byte[0];
        STATIC_TABLE_HEADER_VALUES[i++] = GET.getName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_VALUES[i++] = POST.getName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_VALUES[i++] = "/".getBytes(CHARSET);
        STATIC_TABLE_HEADER_VALUES[i++] = "/index.html".getBytes(CHARSET);
        STATIC_TABLE_HEADER_VALUES[i++] = HTTP.getName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_VALUES[i++] = HTTPS.getName().getBytes(CHARSET);
        STATIC_TABLE_HEADER_VALUES[i++] = "200".getBytes(CHARSET);
        STATIC_TABLE_HEADER_VALUES[i++] = "204".getBytes(CHARSET);
        STATIC_TABLE_HEADER_VALUES[i++] = "206".getBytes(CHARSET);
        STATIC_TABLE_HEADER_VALUES[i++] = "304".getBytes(CHARSET);
        STATIC_TABLE_HEADER_VALUES[i++] = "400".getBytes(CHARSET);
        STATIC_TABLE_HEADER_VALUES[i++] = "404".getBytes(CHARSET);
        STATIC_TABLE_HEADER_VALUES[i++] = "500".getBytes(CHARSET);
        STATIC_TABLE_HEADER_VALUES[i++] = new byte[0];
        STATIC_TABLE_HEADER_VALUES[i++] = "gzip, deflate".getBytes(CHARSET);
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
