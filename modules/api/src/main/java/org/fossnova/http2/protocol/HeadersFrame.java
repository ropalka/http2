/*
 * Copyright (c) 2012-2019, FOSS Nova Software foundation (FNSF),
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
package org.fossnova.http2.protocol;

/**
 * // TODO: javadoc
 * @author <a href="mailto:opalka.richard@gmail.com">Richard Opalka</a>
 */
public interface HeadersFrame extends Frame {
    int FLAG_END_STREAM = 0x1;
    int FLAG_END_HEADERS = 0x4;
    int FLAG_PADDED = 0x8;
    int FLAG_PRIORITY = 0x20;

    int getPadLength();
    int getStreamDependency();
    int getWeight();
    byte[] getHeaderBlockFragment();

    static Builder newBuilder() {
        // TODO: implement
        throw new UnsupportedOperationException();
    }

    interface Builder extends Frame.Builder {
        void setPadLength(int padLength);
        void setStreamDependency(int streamId);
        void setWeight(int weight);
        void setHeaderBlockFragment(byte[] data);
        HeadersFrame build();
    }
}
