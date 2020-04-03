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
 * TODO: javadoc
 * @author <a href="mailto:ropalka@redhat.com">Richard Opalka</a>
 */
public final class HeaderField {

    private final Header headerName;
    private final String headerValue;
    private final int hashCode;

    private HeaderField(final Header headerName, final String headerValue) {
        this.headerName = headerName;
        this.headerValue = headerValue;
        int result = 17;
        result = 37 * result + headerName.hashCode();
        result = 37 * result + (headerValue == null ? 0 : headerValue.hashCode());
        hashCode = result;
    }

    /**
     * Creates new HTTP header field instance.
     * @param headerName http header name
     * @param headerValue http header value
     * @return new header field instance
     */
    public static HeaderField of(final Header headerName, final String headerValue) {
        if (headerName == null) throw new IllegalArgumentException();
        return new HeaderField(headerName, headerValue);
    }

    /**
     * Creates new HTTP header field instance.
     * @param headerName http header name
     * @return new header field instance
     */
    public static HeaderField of(final Header headerName) {
        if (headerName == null) throw new IllegalArgumentException();
        return of(headerName, null);
    }

    /**
     * Gests header name.
     * @return header name
     */
    public Header getHeaderName() {
        return headerName;
    }

    /**
     * Gets header value.
     * @return header value
     */
    public String getHeaderValue() {
        return headerValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object other) {
        if (other == this) return true;
        if (!(other instanceof HeaderField)) return false;
        final HeaderField o = (HeaderField) other;
        return headerName.equals(o.headerName) && headerValue == null ? o.headerValue == null : headerValue.equals(o.headerValue);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return hashCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return headerName + " : " + ((headerValue == null) ? "<null>" : headerValue);
    }

}
