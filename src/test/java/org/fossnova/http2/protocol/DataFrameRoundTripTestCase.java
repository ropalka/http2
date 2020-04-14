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

import static org.fossnova.http2.protocol.DataFrame.*;
import static org.junit.Assert.*;

/**
 * @author <a href="mailto:opalka.richard@gmail.com">Richard Opalka</a>
 */
public class DataFrameRoundTripTestCase extends AbstractHttp2TestCase {
    private static final byte[] MSG = "Hello World!".getBytes();

    @Test
    public void dataFrameWithPadding() {
        writeDataFrameWithPadding();
        readDataFrameWithPadding();
    }

    @Test
    public void dataFrameWithoutPadding() {
        writeDataFrameWithoutPadding();
        readDataFrameWithoutPadding();
    }

    private void writeDataFrameWithPadding() {
        DataFrame.Builder builder = newDataFrameBuilder();
        builder.setPayloadSize(250);
        builder.setFlags(FLAG_END_STREAM | FLAG_PADDED);
        builder.setStreamId(1);
        builder.setData(MSG);
        pushFrame(builder.build());
    }

    private void readDataFrameWithPadding() {
        DataFrame frame = (DataFrame) pullFrame();
        assertNotNull(frame);
        assertEquals(frame.getPayloadSize(), 250);
        assertEquals(frame.getFlags(), FLAG_END_STREAM | FLAG_PADDED);
        assertEquals(frame.getStreamId(), 1);
        assertArrayEquals(frame.getData(), MSG);
    }

    private void writeDataFrameWithoutPadding() {
        DataFrame.Builder builder = newDataFrameBuilder();
        builder.setPayloadSize(12);
        builder.setFlags(FLAG_END_STREAM);
        builder.setStreamId(1);
        builder.setData(MSG);
        pushFrame(builder.build());
    }

    private void readDataFrameWithoutPadding() {
        DataFrame frame = (DataFrame) pullFrame();
        assertNotNull(frame);
        assertEquals(frame.getPayloadSize(), 12);
        assertEquals(frame.getFlags(), FLAG_END_STREAM);
        assertEquals(frame.getStreamId(), 1);
        assertArrayEquals(frame.getData(), MSG);
    }

}
