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
package test.fossnova.http2.hpack;

import static org.junit.Assert.*;

import org.fossnova.http2.Header;
import org.fossnova.http2.HeaderField;
import org.fossnova.http2.Method;
import org.fossnova.http2.Scheme;
import org.fossnova.http2.hpack.HpackDecoder;
import org.fossnova.http2.hpack.HpackEncoder;
import org.fossnova.http2.hpack.HpackFactory;
import org.junit.Test;

/**
 * @author <a href="mailto:opalka.richard@gmail.com">Richard Opalka</a>
 */
public class HeadersSerializationTestCase  {
    private static final int MAX_DYNAMIC_TABLE_SIZE = 4096;
    private static final HeaderField METHOD_HF = HeaderField.of(Header.METHOD, Method.GET.getName());
    private static final HeaderField SCHEME_HF = HeaderField.of(Header.SCHEME, Scheme.HTTP.getName());
    private static final HeaderField PATH_HF = HeaderField.of(Header.PATH, "/");
    private static final HeaderField HOST_HF = HeaderField.of(Header.HOST, "localhost");
    private static final HpackFactory CLIENT_FACTORY = HpackFactory.newInstance(MAX_DYNAMIC_TABLE_SIZE, false);
    private static final HpackFactory SERVER_FACTORY = HpackFactory.newInstance(MAX_DYNAMIC_TABLE_SIZE, true);

    @Test
    public void headersRoadTrip() {
        final byte[] encodedHeaders = writeHeaders();
        readHeaders(encodedHeaders);
    }

    private byte[] writeHeaders() {
        final HpackEncoder encoder = newEncoder();
        encoder.add(METHOD_HF);
        encoder.add(SCHEME_HF);
        encoder.add(PATH_HF);
        encoder.add(HOST_HF);
        return encoder.finish();
    }

    private void readHeaders(final byte[] encodedHeaders) {
        final HpackDecoder decoder = newDecoder(encodedHeaders);
        assertTrue(decoder.hasNext());
        assertTrue(METHOD_HF.equals(decoder.next()));
        assertTrue(decoder.hasNext());
        assertTrue(SCHEME_HF.equals(decoder.next()));
        assertTrue(decoder.hasNext());
        assertTrue(PATH_HF.equals(decoder.next()));
        assertTrue(decoder.hasNext());
        assertTrue(HOST_HF.equals(decoder.next()));
        assertFalse(decoder.hasNext());
        decoder.finish();
    }

    private HpackEncoder newEncoder() {
        return CLIENT_FACTORY.newEncoder();
    }

    private HpackDecoder newDecoder(final byte[] encodedHeaders) {
        return SERVER_FACTORY.newDecoder(encodedHeaders);
    }

}
