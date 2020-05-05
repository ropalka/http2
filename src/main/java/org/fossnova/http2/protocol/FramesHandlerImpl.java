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

import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author <a href="mailto:opalka.richard@gmail.com">Richard Opalka</a>
 */
public final class FramesHandlerImpl extends FramesHandler {

    private final String host;
    private final int port;
    private final boolean server;
    private final boolean validate;
    private final AtomicBoolean started = new AtomicBoolean();
    private final AtomicBoolean stopped = new AtomicBoolean();
    private final ByteBuffer buffer = ByteBuffer.allocate(SettingsFrame.DEFAULT_MAX_FRAME_SIZE);

    public FramesHandlerImpl(final String host, final int port, final boolean server, final boolean validate) {
        this.host = host;
        this.port = port;
        this.server = server;
        this.validate = validate;
    }

    @Override
    public void start() {
        if (started.compareAndSet(false, true)) {
            if (server) {
                // start non blocking HTTP server emulator
            } else {
                // start non blocking HTTP client emulator
            }
        } else {
            // TODO: what???
        }
    }

    @Override
    public void stop() {
        if (stopped.compareAndSet(false, true)) {
            if (server) {
                // stop non blocking HTTP server emulator
            } else {
                // stop non blocking HTTP client emulator
            }
        } else {
            // TODO: what???
        }
    }

    @Override
    public void push(final Frame frame) {
        ((AbstractFrameImpl) frame).writeTo(buffer);
    }

    @Override
    public Frame pull() {
        return AbstractFrameImpl.readFrom(buffer, server, validate);
    }

    @Override
    public ContinuationFrame.Builder newContinuationFrameBuilder() {
        return new ContinuationFrameImpl.Builder(server, !server, validate);
    }

    @Override
    public DataFrame.Builder newDataFrameBuilder() {
        return new DataFrameImpl.Builder(server, !server, validate);
    }

    @Override
    public GoAwayFrame.Builder newGoAwayFrameBuilder() {
        return new GoAwayFrameImpl.Builder(server, !server, validate);
    }

    @Override
    public HeadersFrame.Builder newHeadersFrameBuilder() {
        return new HeadersFrameImpl.Builder(server, !server, validate);
    }

    @Override
    public PingFrame.Builder newPingFrameBuilder() {
        return new PingFrameImpl.Builder(server, !server, validate);
    }

    @Override
    public PriorityFrame.Builder newPriorityFrameBuilder() {
        return new PriorityFrameImpl.Builder(server, !server, validate);
    }

    @Override
    public PushPromiseFrame.Builder newPushPromiseFrameBuilder() {
        return new PushPromiseFrameImpl.Builder(server, !server, validate);
    }

    @Override
    public RstStreamFrame.Builder newRstStreamFrameBuilder() {
        return new RstStreamFrameImpl.Builder(server, !server, validate);
    }

    @Override
    public SettingsFrame.Builder newSettingsFrameBuilder() {
        return new SettingsFrameImpl.Builder(server, !server, validate);
    }

    @Override
    public WindowUpdateFrame.Builder newWindowUpdateFrameBuilder() {
        return new WindowUpdateFrameImpl.Builder(server, !server, validate);
    }
}
