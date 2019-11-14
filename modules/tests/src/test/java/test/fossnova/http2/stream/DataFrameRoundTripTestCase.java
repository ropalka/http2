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

import org.fossnova.http2.protocol.DataFrame;
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
        DataFrame.Builder builder = newBuilder();
        builder.setPayloadSize(15); // if not invoked defaults to 0
        builder.setFlags(FLAG_END_STREAM | FLAG_PADDED);
        builder.setPadLength(3);
        builder.setData(MSG);
        framesHandler.push(builder.build());
    }

    private void readDataFrameWithPadding() {
        DataFrame frame = (DataFrame) framesHandler.pull();
        assertNotNull(frame);
        assertEquals(frame.getSize(), 15);
        assertEquals(frame.getPadLength(), 3);
        assertEquals(frame.getFlags(), FLAG_END_STREAM | FLAG_PADDED);
        assertEquals(frame.getData(), MSG);
    }

    private void writeDataFrameWithoutPadding() {
        DataFrame.Builder builder = newBuilder();
        builder.setPayloadSize(12); // if not invoked defaults to 0
        builder.setFlags(FLAG_END_STREAM);
        builder.setData(MSG);
        framesHandler.push(builder.build());
    }

    private void readDataFrameWithoutPadding() {
        DataFrame frame = (DataFrame) framesHandler.pull();
        assertNotNull(frame);
        assertEquals(frame.getSize(), 12);
        assertEquals(frame.getPadLength(), 0);
        assertEquals(frame.getFlags(), FLAG_END_STREAM);
        assertEquals(frame.getData(), MSG);
    }

}
