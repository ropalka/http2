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

import static org.fossnova.http2.Utils.validateMethod;

/**
 * HTTP request methods.
 *
 * @author <a href="mailto:opalka.richard@gmail.com">Richard Opalka</a>
 */
public final class Method {

    public static final Method GET = new Method("GET");
    public static final Method HEAD = new Method("HEAD");
    public static final Method POST = new Method("POST");
    public static final Method PUT = new Method("PUT");
    public static final Method DELETE = new Method("DELETE");
    public static final Method CONNECT = new Method("CONNECT");
    public static final Method OPTIONS = new Method("OPTIONS");
    public static final Method TRACE = new Method("TRACE");
    private static final Map<String, Method> KNOWN_METHODS = new HashMap<>();
    private final String name;

    private Method(final String name) {
        this(name, true);
    }

    private Method(final String name, final boolean register) {
        validateMethod(name);
        this.name = name;
        if (register) {
            KNOWN_METHODS.put(name, this);
        }
    }

    /**
     * Returns request method name.
     * @return request method name
     */
    public String getName() {
        return name;
    }

    /**
     * Creates specified request method.
     *
     * @param name method name
     * @return request method instance
     * @throws IllegalArgumentException if request method name doesn't match HTTP's spec. <code>token</code> definition
     */
    public static Method of(final String name) {
        validateMethod(name);
        final Method retVal = KNOWN_METHODS.get(name);
        return retVal != null ? retVal : new Method(name, false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object o) {
        return o == this || o instanceof Method && name.equals(((Method)o).name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return name.hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Request method: " + name;
    }

}
