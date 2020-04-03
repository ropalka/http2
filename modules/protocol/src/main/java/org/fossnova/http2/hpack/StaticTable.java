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

import org.fossnova.http2.Header;
import org.fossnova.http2.HeaderField;

import static org.fossnova.http2.Header.*;
import static org.fossnova.http2.Method.*;
import static org.fossnova.http2.Scheme.*;
import static org.fossnova.http2.StatusCode.*;

/**
 * @author <a href="mailto:opalka.richard@gmail.com">Richard Opalka</a>
 */
final class StaticTable {

    private static final HeaderField[] HEADER_FIELDS = new HeaderField[62];
    private static final int ITEM_NOT_FOUND = -1;

    static {
        int i = 1;
        HEADER_FIELDS[i++] = HeaderField.of(AUTHORITY);
        HEADER_FIELDS[i++] = HeaderField.of(METHOD, GET.getName());
        HEADER_FIELDS[i++] = HeaderField.of(METHOD, POST.getName());
        HEADER_FIELDS[i++] = HeaderField.of(PATH, "/");
        HEADER_FIELDS[i++] = HeaderField.of(PATH, "/index.html");
        HEADER_FIELDS[i++] = HeaderField.of(SCHEME, HTTP.getName());
        HEADER_FIELDS[i++] = HeaderField.of(SCHEME, HTTPS.getName());
        HEADER_FIELDS[i++] = HeaderField.of(STATUS, OK.getCodeAsString());
        HEADER_FIELDS[i++] = HeaderField.of(STATUS, NO_CONTENT.getCodeAsString());
        HEADER_FIELDS[i++] = HeaderField.of(STATUS, PARTIAL_CONTENT.getCodeAsString());
        HEADER_FIELDS[i++] = HeaderField.of(STATUS, NOT_MODIFIED.getCodeAsString());
        HEADER_FIELDS[i++] = HeaderField.of(STATUS, BAD_REQUEST.getCodeAsString());
        HEADER_FIELDS[i++] = HeaderField.of(STATUS, NOT_FOUND.getCodeAsString());
        HEADER_FIELDS[i++] = HeaderField.of(STATUS, INTERNAL_SERVER_ERROR.getCodeAsString());
        HEADER_FIELDS[i++] = HeaderField.of(ACCEPT_CHARSET);
        HEADER_FIELDS[i++] = HeaderField.of(ACCEPT_ENCODING, "gzip, deflate");
        HEADER_FIELDS[i++] = HeaderField.of(ACCEPT_LANGUAGE);
        HEADER_FIELDS[i++] = HeaderField.of(ACCEPT_RANGES);
        HEADER_FIELDS[i++] = HeaderField.of(ACCEPT);
        HEADER_FIELDS[i++] = HeaderField.of(ACCESS_CONTROL_ALLOW_ORIGIN);
        HEADER_FIELDS[i++] = HeaderField.of(AGE);
        HEADER_FIELDS[i++] = HeaderField.of(ALLOW);
        HEADER_FIELDS[i++] = HeaderField.of(AUTHORIZATION);
        HEADER_FIELDS[i++] = HeaderField.of(CACHE_CONTROL);
        HEADER_FIELDS[i++] = HeaderField.of(CONTENT_DISPOSITION);
        HEADER_FIELDS[i++] = HeaderField.of(CONTENT_ENCODING);
        HEADER_FIELDS[i++] = HeaderField.of(CONTENT_LANGUAGE);
        HEADER_FIELDS[i++] = HeaderField.of(CONTENT_LENGTH);
        HEADER_FIELDS[i++] = HeaderField.of(CONTENT_LOCATION);
        HEADER_FIELDS[i++] = HeaderField.of(CONTENT_RANGE);
        HEADER_FIELDS[i++] = HeaderField.of(CONTENT_TYPE);
        HEADER_FIELDS[i++] = HeaderField.of(COOKIE);
        HEADER_FIELDS[i++] = HeaderField.of(DATE);
        HEADER_FIELDS[i++] = HeaderField.of(ETAG);
        HEADER_FIELDS[i++] = HeaderField.of(EXPECT);
        HEADER_FIELDS[i++] = HeaderField.of(EXPIRES);
        HEADER_FIELDS[i++] = HeaderField.of(FROM);
        HEADER_FIELDS[i++] = HeaderField.of(HOST);
        HEADER_FIELDS[i++] = HeaderField.of(IF_MATCH);
        HEADER_FIELDS[i++] = HeaderField.of(IF_MODIFIED_SINCE);
        HEADER_FIELDS[i++] = HeaderField.of(IF_NONE_MATCH);
        HEADER_FIELDS[i++] = HeaderField.of(IF_RANGE);
        HEADER_FIELDS[i++] = HeaderField.of(IF_UNMODIFIED_SINCE);
        HEADER_FIELDS[i++] = HeaderField.of(LAST_MODIFIED);
        HEADER_FIELDS[i++] = HeaderField.of(LINK);
        HEADER_FIELDS[i++] = HeaderField.of(LOCATION);
        HEADER_FIELDS[i++] = HeaderField.of(MAX_FORWARDS);
        HEADER_FIELDS[i++] = HeaderField.of(PROXY_AUTHENTICATE);
        HEADER_FIELDS[i++] = HeaderField.of(PROXY_AUTHORIZATION);
        HEADER_FIELDS[i++] = HeaderField.of(RANGE);
        HEADER_FIELDS[i++] = HeaderField.of(REFERER);
        HEADER_FIELDS[i++] = HeaderField.of(REFRESH);
        HEADER_FIELDS[i++] = HeaderField.of(RETRY_AFTER);
        HEADER_FIELDS[i++] = HeaderField.of(SERVER);
        HEADER_FIELDS[i++] = HeaderField.of(SET_COOKIE);
        HEADER_FIELDS[i++] = HeaderField.of(STRICT_TRANSPORT_SECURITY);
        HEADER_FIELDS[i++] = HeaderField.of(TRANSFER_ENCODING);
        HEADER_FIELDS[i++] = HeaderField.of(USER_AGENT);
        HEADER_FIELDS[i++] = HeaderField.of(VARY);
        HEADER_FIELDS[i++] = HeaderField.of(VIA);
        HEADER_FIELDS[i++] = HeaderField.of(WWW_AUTHENTICATE);
    }

    /**
     * Returns index of header field with potentially empty header value inside static table.
     * @param hf header field
     * @return index of header field inside static table or <code>-1</code> if no such header field is present
     * @throws NullPointerException if method parameter is null
     */
    static int indexOf(final HeaderField hf) {
        if (hf == null) throw new NullPointerException();
        for (int i = 1; i < HEADER_FIELDS.length; i++) {
            if (hf.equals(HEADER_FIELDS[i])) return i;
        }
        return ITEM_NOT_FOUND;
    }

    /**
     * Returns index of header name inside static table.
     * @param h message header
     * @return index of header name inside static table or <code>-1</code> if none such header name is present
     * @throws NullPointerException if method parameter is null
     */
    static int indexOf(final Header h) {
        if (h == null) throw new NullPointerException();
        for (int i = 1; i < HEADER_FIELDS.length; i++) {
            if (HEADER_FIELDS[i].getHeaderName().equals(h)) return i;
        }
        return ITEM_NOT_FOUND;
    }

    /**
     * Returns header field for given index.
     * @param index inside static table
     * @return header field for given index
     * @throws IllegalArgumentException if index contains invalid value
     */
    static HeaderField getHeaderField(final int index) {
        if (index <= 1 || index >= HEADER_FIELDS.length) throw new IllegalArgumentException();
        return HEADER_FIELDS[index];
    }

    /**
     * Returns header name for given index.
     * @param index inside static table
     * @return header name for given index
     * @throws IllegalArgumentException if index contains invalid value
     */
    static Header getHeader(final int index) {
        return getHeaderField(index).getHeaderName();
    }

}
