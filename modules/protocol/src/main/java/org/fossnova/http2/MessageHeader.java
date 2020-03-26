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

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * HTTP message headers as defined by
 * <a href="http://www.iana.org/assignments/message-headers/message-headers.xhtml">IANA</a> organization.
 *
 * @author <a href="mailto:ropalka@redhat.com">Richard Opalka</a>
 */
public final class MessageHeader {

    private static final Map<String, MessageHeader> KNOWN_HEADERS = new HashMap<>();

    private final String name;

    MessageHeader(final String name) {
        this.name = name;
        KNOWN_HEADERS.put(name.toLowerCase(Locale.US), this);
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * Creates specified message header.
     *
     * @param name header name
     * @return message header instance
     * @throws IllegalArgumentException if message header doesn't match HTTP's spec. <code>token</code> definition
     */
    public static MessageHeader of(final String name) {
        validateToken(name);
        MessageHeader retVal = KNOWN_HEADERS.get(name.toLowerCase());
        return retVal != null ? retVal : new MessageHeader(name);
    }

    private static final char[] TOKEN_EXTRAS = new char[] {
            '!', '#', '$', '%', '&', '\'', '*', '+', '-', '.', '^', '_', '`', '|', '~'
    };

    private static void validateToken(final String token) {
        if (token == null || token.length() == 0) throw new IllegalArgumentException();
        for (int i = 0; i < token.length(); i++) {
            if (!isTokenChar(token.charAt(i))) {
                throw new IllegalArgumentException();
            }
        }
    }

    private static boolean isAlphaChar(final char c) {
        return 'a' <= c && c <= 'z' || 'A' <= c && c <= 'Z';
    }

    private static boolean isDigitChar(final char c) {
        return '0' <= c && c <= '9';
    }

    private static boolean isTokenChar(final char c) {
        if (isAlphaChar(c)) return true;
        if (isDigitChar(c)) return true;
        for (int i = 0; i < TOKEN_EXTRAS.length; i++) {
            if (TOKEN_EXTRAS[i] == c) return true;
        }
        return false;
    }

}
