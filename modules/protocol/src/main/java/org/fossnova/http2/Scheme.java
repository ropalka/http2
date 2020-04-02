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
public final class Scheme {

    public static final Scheme HTTP = new Scheme("http");
    public static final Scheme HTTPS = new Scheme("https");
    private static final Map<String, Scheme> KNOWN_SCHEMES = new HashMap<>();
    private final String scheme;

    private Scheme(final String scheme) {
        this(scheme, true);
    }

    private Scheme(final String scheme, final boolean register) {
        validateScheme(scheme);
        this.scheme = scheme.toLowerCase();
        if (register) {
            KNOWN_SCHEMES.put(this.scheme, this);
        }
    }

    /**
     * Returns request scheme name with all letters de-capitalized.
     * @return request scheme name in lower case form
     */
    public String getName() {
        return scheme;
    }

    /**
     * {@inheritDoc}
     */
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
    public static Scheme of(final String name) {
        validateScheme(name);
        final Scheme retVal = KNOWN_SCHEMES.get(name.toLowerCase());
        return retVal != null ? retVal : new Scheme(name, false);
    }

}
