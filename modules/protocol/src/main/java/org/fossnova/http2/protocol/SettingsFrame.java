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
package org.fossnova.http2.protocol;

/**
 * // TODO: javadoc
 * @author <a href="mailto:opalka.richard@gmail.com">Richard Opalka</a>
 */
public interface SettingsFrame extends Frame {
    byte FLAG_ACK = 0x1;

    // parameter identifiers
    int HEADER_TABLE_SIZE = 0x1;
    int ENABLE_PUSH = 0x2;
    int MAX_CONCURRENT_STREAMS = 0x3;
    int INITIAL_WINDOW_SIZE = 0x4;
    int MAX_FRAME_SIZE = 0x5;
    int MAX_HEADER_LIST_SIZE = 0x6;

    // parameter default values
    int DEFAULT_HEADER_TABLE_SIZE = 1 << 12;
    int DEFAULT_ENABLE_PUSH = 1;
    int DEFAULT_MAX_CONCURRENT_STREAMS = 100;
    int DEFAULT_INITIAL_WINDOW_SIZE = 1 << 16 - 1;
    int DEFAULT_MAX_FRAME_SIZE = 1 << 14;
    int DEFAULT_MAX_HEADER_LIST_SIZE = 1 << 12;

    // other useful parameter values
    int PUSH_ENABLED = 1;
    int PUSH_DISABLED = 0;

    int getParameter(int paramIdentifier);

    interface Builder extends Frame.Builder {
        void setParameter(int identifier, int value);
        SettingsFrame build();
    }
}
