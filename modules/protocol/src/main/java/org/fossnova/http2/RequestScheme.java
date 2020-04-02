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
import java.util.Map;

import static org.fossnova.http2.Utils.validateScheme;

/**
 * HTTP request schemes.
 *
 * @author <a href="mailto:opalka.richard@gmail.com">Richard Opalka</a>
 */
public final class RequestScheme {

    public static final RequestScheme HTTP = new RequestScheme("http");
    public static final RequestScheme HTTPS = new RequestScheme("https");
    private static final Map<String, RequestScheme> KNOWN_SCHEMES = new HashMap<>();
    private final String scheme;

    private RequestScheme(final String scheme) {
        this(scheme, true);
    }

    private RequestScheme(final String scheme, final boolean register) {
        validateScheme(scheme);
        this.scheme = scheme.toLowerCase();
        if (register) {
            KNOWN_SCHEMES.put(this.scheme, this);
        }
    }

    @Override
    public String toString() {
        return scheme;
    }

    /**
     * Creates specified request scheme.
     *
     * @param name scheme name
     * @return request scheme instance
     * @throws IllegalArgumentException if request scheme name doesn't match HTTP's spec. <code>scheme</code> definition
     */
    public static RequestScheme of(final String name) {
        validateScheme(name);
        final RequestScheme retVal = KNOWN_SCHEMES.get(name.toLowerCase());
        return retVal != null ? retVal : new RequestScheme(name, false);
    }

}
