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
package test.fossnova.http2.stream;

import org.fossnova.http2.protocol.*;
import org.junit.After;
import org.junit.Before;

import java.nio.ByteBuffer;

/**
 * @author <a href="mailto:opalka.richard@gmail.com">Richard Opalka</a>
 */
public class AbstractHttp2TestCase {
    private byte[] dataBytes;
    private ByteBuffer dataBuffer;
    private FramesHandler clientFramesHandler, serverFramesHandler;

    @Before
    public final void setUp() {
        dataBytes = new byte[1024];
        dataBuffer = ByteBuffer.wrap(dataBytes);
        clientFramesHandler = FramesHandler.newInstance(false, true);
        serverFramesHandler = FramesHandler.newInstance(true, true);
    }

    @After
    public final void tearDown() {
        dataBytes = null;
        dataBuffer = null;
        clientFramesHandler = null;
        serverFramesHandler = null;
    }

    final void pushFrame(final Frame f) {
        clientFramesHandler.push(f, dataBuffer);
        dataBuffer.flip();
    }

    final Frame pullFrame() {
        try {
            return serverFramesHandler.pull(dataBuffer);
        } finally {
            dataBuffer.flip();
        }
    }

    final ContinuationFrame.Builder newContinuationFrameBuilder() {
        return clientFramesHandler.newContinuationFrameBuilder();
    }

    final DataFrame.Builder newDataFrameBuilder() {
        return clientFramesHandler.newDataFrameBuilder();
    }

    final GoAwayFrame.Builder newGoAwayFrameBuilder() {
        return clientFramesHandler.newGoAwayFrameBuilder();
    }

    final HeadersFrame.Builder newHeadersFrameBuilder() {
        return clientFramesHandler.newHeadersFrameBuilder();
    }

    final PingFrame.Builder newPingFrameBuilder() {
        return clientFramesHandler.newPingFrameBuilder();
    }

    final PriorityFrame.Builder newPriorityFrameBuilder() {
        return clientFramesHandler.newPriorityFrameBuilder();
    }

    final PushPromiseFrame.Builder newPushPromiseFrameBuilder() {
        return clientFramesHandler.newPushPromiseFrameBuilder();
    }

    final RstStreamFrame.Builder newRstStreamFrameBuilder() {
        return clientFramesHandler.newRstStreamFrameBuilder();
    }

    final SettingsFrame.Builder newSettingsFrameBuilder() {
        return clientFramesHandler.newSettingsFrameBuilder();
    }

    final WindowUpdateFrame.Builder newWindowUpdateFrameBuilder() {
        return clientFramesHandler.newWindowUpdateFrameBuilder();
    }

}
