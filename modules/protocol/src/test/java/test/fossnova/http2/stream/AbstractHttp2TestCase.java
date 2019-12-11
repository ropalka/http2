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
    private FramesHandler framesHandler;

    @Before
    public final void setUp() {
        dataBytes = new byte[1024];
        dataBuffer = ByteBuffer.wrap(dataBytes);
        framesHandler = FramesHandler.newInstance(true, true);
    }

    @After
    public final void tearDown() {
        dataBytes = null;
        dataBuffer = null;
        framesHandler = null;
    }

    final void pushFrame(final Frame f) {
        framesHandler.push(f, dataBuffer);
        dataBuffer.flip();
    }

    final Frame pullFrame() {
        try {
            return framesHandler.pull(dataBuffer);
        } finally {
            dataBuffer.flip();
        }
    }

    final ContinuationFrame.Builder newContinuationFrameBuilder() {
        return framesHandler.newContinuationFrameBuilder();
    }

    final DataFrame.Builder newDataFrameBuilder() {
        return framesHandler.newDataFrameBuilder();
    }

    final GoAwayFrame.Builder newGoAwayFrameBuilder() {
        return framesHandler.newGoAwayFrameBuilder();
    }

    final HeadersFrame.Builder newHeadersFrameBuilder() {
        return framesHandler.newHeadersFrameBuilder();
    }

    final PingFrame.Builder newPingFrameBuilder() {
        return framesHandler.newPingFrameBuilder();
    }

    final PriorityFrame.Builder newPriorityFrameBuilder() {
        return framesHandler.newPriorityFrameBuilder();
    }

    final PushPromiseFrame.Builder newPushPromiseFrameBuilder() {
        return framesHandler.newPushPromiseFrameBuilder();
    }

    final RstStreamFrame.Builder newRstStreamFrameBuilder() {
        return framesHandler.newRstStreamFrameBuilder();
    }

    final SettingsFrame.Builder newSettingsFrameBuilder() {
        return framesHandler.newSettingsFrameBuilder();
    }

    final WindowUpdateFrame.Builder newWindowUpdateFrameBuilder() {
        return framesHandler.newWindowUpdateFrameBuilder();
    }

}
