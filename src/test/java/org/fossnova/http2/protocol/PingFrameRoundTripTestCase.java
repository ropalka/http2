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

import org.junit.Test;

import static org.fossnova.http2.protocol.PingFrame.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author <a href="mailto:opalka.richard@gmail.com">Richard Opalka</a>
 */
public class PingFrameRoundTripTestCase extends AbstractHttp2TestCase {
    @Test
    public void pingFrameWithAck() {
        writePingFrameWithAck();
        readPingFrameWithAck();
    }

    @Test
    public void pingFrameWithoutAck() {
        writePingFrameWithoutAck();
        readPingFrameWithoutAck();
    }

    private void writePingFrameWithAck() {
        PingFrame.Builder builder = newPingFrameBuilder();
        builder.setPayloadSize(8);
        builder.setFlags(FLAG_ACK);
        builder.setOpaqueData(0xFE_DC_BA_98_76_54_32_10L);
        pushFrame(builder.build());
    }

    private void readPingFrameWithAck() {
        PingFrame frame = (PingFrame) pullFrame();
        assertNotNull(frame);
        assertEquals(frame.getPayloadSize(), 8);
        assertEquals(frame.getFlags(), FLAG_ACK);
        assertEquals(frame.getOpaqueData(), 0xFE_DC_BA_98_76_54_32_10L);
    }
    private void writePingFrameWithoutAck() {
        PingFrame.Builder builder = newPingFrameBuilder();
        builder.setPayloadSize(8);
        builder.setOpaqueData(0xFE_DC_BA_98_76_54_32_10L);
        pushFrame(builder.build());
    }

    private void readPingFrameWithoutAck() {
        PingFrame frame = (PingFrame) pullFrame();
        assertNotNull(frame);
        assertEquals(frame.getPayloadSize(), 8);
        assertEquals(frame.getFlags(), NO_FLAGS);
        assertEquals(frame.getOpaqueData(), 0xFE_DC_BA_98_76_54_32_10L);
    }
}
