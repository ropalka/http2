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

/**
 * HTTP request methods.
 *
 * @author <a href="mailto:opalka.richard@gmail.com">Richard Opalka</a>
 */
public final class Method {
    private static final Map<String, Method> KNOWN_METHODS = new HashMap<>();
    private final String method;

    public static final Method GET = new Method("GET", true);
    public static final Method HEAD = new Method("HEAD", true);
    public static final Method POST = new Method("POST", true);
    public static final Method PUT = new Method("PUT", true);
    public static final Method DELETE = new Method("DELETE", true);
    public static final Method CONNECT = new Method("CONNECT", true);
    public static final Method OPTIONS = new Method("OPTIONS", true);
    public static final Method TRACE = new Method("TRACE", true);

    public static final Method of(final String method) {
        if (method == null || method.length() == 0) throw new IllegalArgumentException();
        Method retVal = KNOWN_METHODS.get(method.toUpperCase());
        return retVal != null ? retVal : new Method(method, false);
    }

    private Method(final String method, final boolean register) {
        this.method = method;
        if (register) {
            KNOWN_METHODS.put(method, this);
        }
    }

    @Override
    public String toString() {
        return method;
    }
}
