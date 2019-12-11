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

import org.fossnova.http2.protocol.PriorityFrame;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author <a href="mailto:opalka.richard@gmail.com">Richard Opalka</a>
 */
public class PriorityFrameRoundTripTestCase extends AbstractHttp2TestCase {
    @Test
    public void priorityFrame() {
        writePriorityFrame();
        readPriorityFrame();
    }

    private void writePriorityFrame() {
        PriorityFrame.Builder builder = newPriorityFrameBuilder();
        builder.setPayloadSize(6);
        builder.setStreamDependency(2);
        builder.setWeight(1);
        pushFrame(builder.build());
    }

    private void readPriorityFrame() {
        PriorityFrame frame = (PriorityFrame) pullFrame();
        assertNotNull(frame);
        assertEquals(frame.getPayloadSize(), 6);
        assertEquals(frame.getStreamDependency(), 2);
        assertEquals(frame.getWeight(), 1);
    }
}
