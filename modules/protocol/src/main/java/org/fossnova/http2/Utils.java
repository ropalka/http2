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

    private static final String OBS_FOLD = "\r\n '";
    private static final String SP = " ";
    private static final String HTAB = "\t";

    private static final char[] TOKEN_EXTRAS = new char[] {
            '!', '#', '$', '%', '&', '\'', '*', '+', '-', '.', '^', '_', '`', '|', '~'
    };

    private static final char[] SCHEME_EXTRAS = new char[] {
            '+', '-', '.'
    };

    /*
OWS = *( SP / HTAB )
header-field   = field-name ":" OWS field-value OWS
field-name     = token
field-value    = *( field-content / obs-fold )
field-content  = field-vchar [ 1*( SP / HTAB ) field-vchar ]
field-vchar    = VCHAR / obs-text
obs-fold       = CRLF 1*( SP / HTAB )
                    ; obsolete line folding
                    ; see Section 3.2.4
     */
    static String validateHeaderValue(final String headerValue) {
        // TODO: optimize this method
        // eliminate OWS first - TODO: don't use trim() as it ignores all chars before ' ' space char not just OWS chars
        String retVal = headerValue.trim();
        // eliminate obsolete line folding first - see Section 3.2.4
        retVal.replace(HTAB, SP);
        retVal = retVal.replace(OBS_FOLD, SP);
        // eliminate all double spaces
        while (retVal.indexOf(SP + SP) != -1) {
            retVal.replace(SP + SP, SP);
        }
        // finally validate chars
        for (int i = 0; i < retVal.length(); i++) {
            if (!isFieldVCHAR(retVal.charAt(i)) && retVal.charAt(i) != SP.charAt(0)) {
                throw new IllegalArgumentException(headerValue);
            }
        }
        return retVal;
    }

    private static boolean isFieldVCHAR(final char c) {
        return isVCHAR(c) || isObsText(c);
    }

    private static boolean isVCHAR(final char c) {
        return 0x21 <= c && c <= 0x7E;
    }

    private static boolean isObsText(final char c) {
        return 0x80 <= c && c <= 0xFF;
    }

    static void validateScheme(final String scheme) {
        if (scheme == null || scheme.length() == 0) throw new IllegalArgumentException();
        if (!isAlphaChar(scheme.charAt(0))) throw new IllegalArgumentException();
        for (int i = 1; i < scheme.length(); i++) {
            if (!isSchemeChar(scheme.charAt(i))) {
                throw new IllegalArgumentException();
            }
        }
    }

    static void validateHeaderName(final String fieldName) {
        validateToken(fieldName);
    }

    static void validateMethod(final String methodName) {
        validateToken(methodName);
    }

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

    static boolean isSchemeChar(final char c) {
        if (isAlphaChar(c)) return true;
        if (isDigitChar(c)) return true;
        for (int i = 0; i < SCHEME_EXTRAS.length; i++) {
            if (SCHEME_EXTRAS[i] == c) return true;
        }
        return false;
    }

}
