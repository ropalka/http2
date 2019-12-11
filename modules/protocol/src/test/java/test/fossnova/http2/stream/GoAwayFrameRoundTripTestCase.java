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

import org.fossnova.http2.protocol.ErrorCode;
import org.fossnova.http2.protocol.GoAwayFrame;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * @author <a href="mailto:opalka.richard@gmail.com">Richard Opalka</a>
 */
public class GoAwayFrameRoundTripTestCase extends AbstractHttp2TestCase {
    @Test
    public void goAwayFrame() {
        writeGoAwayFrame();
        readGoAwayFrame();
    }

    private void writeGoAwayFrame() {
        GoAwayFrame.Builder builder = newGoAwayFrameBuilder();
        builder.setPayloadSize(8);
        builder.setErrorCode(ErrorCode.HTTP_1_1_REQUIRED);
        builder.setLastStreamId(1);
        pushFrame(builder.build());
    }

    private void readGoAwayFrame() {
        GoAwayFrame frame = (GoAwayFrame) pullFrame();
        assertNotNull(frame);
        assertEquals(frame.getPayloadSize(), 8);
        assertEquals(frame.getErrorCode(), ErrorCode.HTTP_1_1_REQUIRED);
        assertEquals(frame.getLastStreamId(), 1);
        assertNull(frame.getAdditionalDebugData());
    }
}
