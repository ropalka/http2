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

/**
 * @author <a href="mailto:opalka.richard@gmail.com">Richard Opalka</a>
 */
final class Utils {

    private Utils() {
        // forbidden instantiation
    }

    private static final char[] TOKEN_EXTRAS = new char[] {
            '!', '#', '$', '%', '&', '\'', '*', '+', '-', '.', '^', '_', '`', '|', '~'
    };

    static void validateToken(final String token) {
        if (token == null || token.length() == 0) throw new IllegalArgumentException();
        for (int i = 0; i < token.length(); i++) {
            if (!isTokenChar(token.charAt(i))) {
                throw new IllegalArgumentException();
            }
        }
    }

    static boolean isAlphaChar(final char c) {
        return 'a' <= c && c <= 'z' || 'A' <= c && c <= 'Z';
    }

    static boolean isDigitChar(final char c) {
        return '0' <= c && c <= '9';
    }

    static boolean isTokenChar(final char c) {
        if (isAlphaChar(c)) return true;
        if (isDigitChar(c)) return true;
        for (int i = 0; i < TOKEN_EXTRAS.length; i++) {
            if (TOKEN_EXTRAS[i] == c) return true;
        }
        return false;
    }

}
