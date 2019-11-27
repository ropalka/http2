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

import org.fossnova.http2.protocol.GoAwayFrame;
import org.junit.Test;

import static org.fossnova.http2.protocol.GoAwayFrame.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * @author <a href="mailto:opalka.richard@gmail.com">Richard Opalka</a>
 */
public class GoAwayFrameRoundTripTestCase extends AbstractHttp2TestCase {
    @Test
    public void rstStreamFrame() {
        writeGoAwayFrame();
        readGoAwayFrame();
    }

    private void writeGoAwayFrame() {
        GoAwayFrame.Builder builder = framesHandler.newGoAwayFrameBuilder();
        builder.setPayloadSize(8); // if not invoked defaults to 0
        builder.setErrorCode(500);
        builder.setLastStreamId(1);
        framesHandler.push(builder.build());
    }

    private void readGoAwayFrame() {
        GoAwayFrame frame = (GoAwayFrame) framesHandler.pull();
        assertNotNull(frame);
        assertEquals(frame.getSize(), 8);
        assertEquals(frame.getErrorCode(), 500);
        assertEquals(frame.getLastStreamId(), 1);
        assertNull(frame.getAddidionalDebugData());
    }
}
