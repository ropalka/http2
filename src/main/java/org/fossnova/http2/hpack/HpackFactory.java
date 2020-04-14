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

/**
 * // TODO: javadoc
 * @author <a href="mailto:opalka.richard@gmail.com">Richard Opalka</a>
 */
public final class HpackFactory {

    private final boolean server;
    private volatile int maxDynamicTableSize;

    private HpackFactory(final int maxDynamicTableSize, final boolean server) {
        this.maxDynamicTableSize = maxDynamicTableSize;
        this.server = server;
    }

    public static HpackFactory newInstance(final int maxDynamicTableSize, final boolean server) {
        if (maxDynamicTableSize <=0) throw new IllegalArgumentException();
        return new HpackFactory(maxDynamicTableSize, server);
    }

    public HpackEncoder newEncoder() {
        return EncoderImpl.newInstance(maxDynamicTableSize, server);
    }

    public HpackEncoder newEncoder(final HpackEncoder.Policy encodingPolicy) {
        // TODO: implement
        throw new UnsupportedOperationException();
    }

    public HpackDecoder newDecoder(final byte[] serializedHeaders) {
        // TODO: implement
        throw new UnsupportedOperationException();
    }

}
