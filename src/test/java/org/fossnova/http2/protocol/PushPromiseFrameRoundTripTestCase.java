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

import static org.fossnova.http2.protocol.PushPromiseFrame.*;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author <a href="mailto:opalka.richard@gmail.com">Richard Opalka</a>
 */
public class PushPromiseFrameRoundTripTestCase extends AbstractHttp2TestCase {
    private static final byte[] MSG = "Hello World!".getBytes();

    @Test
    public void pushPromiseFrameWithPadding() {
        writePushPromiseFrameWithPadding();
        readPushPromiseFrameWithPadding();
    }

    @Test
    public void pushPromiseFrameWithoutPadding() {
        writePushPromiseFrameWithoutPadding();
        readPushPromiseFrameWithoutPadding();
    }

    private void writePushPromiseFrameWithPadding() {
        PushPromiseFrame.Builder builder = newPushPromiseFrameBuilder();
        builder.setPayloadSize(15);
        builder.setFlags(FLAG_PADDED | FLAG_END_HEADERS);
        builder.setPromisedStreamId(3);
        builder.setHeaderBlockFragment(MSG);
        pushFrame(builder.build());
    }

    private void readPushPromiseFrameWithPadding() {
        PushPromiseFrame frame = (PushPromiseFrame) pullFrame();
        assertNotNull(frame);
        assertEquals(frame.getPayloadSize(), 15);
        assertEquals(frame.getFlags(), FLAG_PADDED | FLAG_END_HEADERS);
        assertEquals(frame.getPromisedStreamId(), 3);
        assertArrayEquals(frame.getHeaderBlockFragment(), MSG);
    }

    private void writePushPromiseFrameWithoutPadding() {
        PushPromiseFrame.Builder builder = newPushPromiseFrameBuilder();
        builder.setPayloadSize(12);
        builder.setFlags(FLAG_END_HEADERS);
        builder.setPromisedStreamId(1);
        builder.setHeaderBlockFragment(MSG);
        pushFrame(builder.build());
    }

    private void readPushPromiseFrameWithoutPadding() {
        PushPromiseFrame frame = (PushPromiseFrame) pullFrame();
        assertNotNull(frame);
        assertEquals(frame.getPayloadSize(), 12);
        assertEquals(frame.getFlags(), FLAG_END_HEADERS);
        assertEquals(frame.getPromisedStreamId(), 1);
        assertArrayEquals(frame.getHeaderBlockFragment(), MSG);
    }

}
