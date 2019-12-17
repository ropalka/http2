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
package test.fossnova.http2.stream;

import org.junit.Test;

import org.fossnova.http2.protocol.SettingsFrame;

import static org.junit.Assert.*;
import static org.fossnova.http2.protocol.SettingsFrame.*;

/**
 * @author <a href="mailto:opalka.richard@gmail.com">Richard Opalka</a>
 */
public class SettingsFrameRoundTripTestCase extends AbstractHttp2TestCase {
    @Test
    public void initialSettingsFrame() {
        writeInitialSettingsFrame();
        readInitialSettingsFrame();
    }

    @Test
    public void confirmationSettingsFrame() {
        writeConfirmationSettingsFrame();
        readConfirmationSettingsFrame();
    }

    private void writeInitialSettingsFrame() {
        SettingsFrame.Builder builder = newSettingsFrameBuilder();
        builder.setPayloadSize(36);
        builder.setParameter(HEADER_TABLE_SIZE, DEFAULT_HEADER_TABLE_SIZE);
        builder.setParameter(ENABLE_PUSH, DEFAULT_ENABLE_PUSH);
        builder.setParameter(MAX_CONCURRENT_STREAMS, DEFAULT_MAX_CONCURRENT_STREAMS);
        builder.setParameter(INITIAL_WINDOW_SIZE, DEFAULT_INITIAL_WINDOW_SIZE);
        builder.setParameter(MAX_FRAME_SIZE, DEFAULT_MAX_FRAME_SIZE);
        builder.setParameter(MAX_HEADER_LIST_SIZE, DEFAULT_MAX_HEADER_LIST_SIZE);
        pushFrame(builder.build());
    }

    private void readInitialSettingsFrame() {
        SettingsFrame frame = (SettingsFrame) pullFrame();
        assertNotNull(frame);
        assertEquals(frame.getPayloadSize(), 36);
        assertEquals(frame.getFlags(), NO_FLAGS);
        assertEquals(frame.getParameter(HEADER_TABLE_SIZE), DEFAULT_HEADER_TABLE_SIZE);
        assertEquals(frame.getParameter(ENABLE_PUSH), DEFAULT_ENABLE_PUSH);
        assertEquals(frame.getParameter(MAX_CONCURRENT_STREAMS), DEFAULT_MAX_CONCURRENT_STREAMS);
        assertEquals(frame.getParameter(INITIAL_WINDOW_SIZE), DEFAULT_INITIAL_WINDOW_SIZE);
        assertEquals(frame.getParameter(MAX_FRAME_SIZE), DEFAULT_MAX_FRAME_SIZE);
        assertEquals(frame.getParameter(MAX_HEADER_LIST_SIZE), DEFAULT_MAX_HEADER_LIST_SIZE);
    }

    private void writeConfirmationSettingsFrame() {
        SettingsFrame.Builder builder = newSettingsFrameBuilder();
        builder.setFlags(SettingsFrame.FLAG_ACK);
        pushFrame(builder.build());
    }

    private void readConfirmationSettingsFrame() {
        SettingsFrame frame = (SettingsFrame) pullFrame();
        assertNotNull(frame);
        assertEquals(frame.getPayloadSize(), 0);
        assertEquals(frame.getFlags(), SettingsFrame.FLAG_ACK);
    }
}
