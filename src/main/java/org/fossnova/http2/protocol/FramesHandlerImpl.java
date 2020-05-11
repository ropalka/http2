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

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.CountDownLatch;
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
    private final CountDownLatch startLatch = new CountDownLatch(1);
    private final CountDownLatch stopLatch = new CountDownLatch(1);
    private final ByteBuffer buffer = ByteBuffer.allocate(SettingsFrame.DEFAULT_MAX_FRAME_SIZE);
    private volatile Thread connThread;
    private volatile RawFrameHandler rawFrameHandler;

    public FramesHandlerImpl(final String host, final int port, final boolean server, final boolean validate) {
        this.host = host;
        this.port = port;
        this.server = server;
        this.validate = validate;
    }

    @Override
    public void start() throws IOException, InterruptedException {
        if (started.compareAndSet(false, true)) {
            rawFrameHandler = server ? new Server(host, port, startLatch, stopLatch) : new Client(host, port, startLatch, stopLatch);
            connThread = new Thread(rawFrameHandler);
            connThread.start();
            startLatch.await();
        }
    }

    @Override
    public void stop() throws InterruptedException {
        if (stopped.compareAndSet(false, true)) {
            connThread.interrupt(); // TODO: Raw frame handler should handle that?
            stopLatch.await();
            rawFrameHandler = null; // TODO: revisit
        }
    }

    @Override
    public void push(final Frame frame) {
        if (!(frame instanceof AbstractFrameImpl)) throw new IllegalArgumentException();
        final AbstractFrameImpl frameImpl = (AbstractFrameImpl)frame;
        final byte[] headerBytes = frameImpl.writeHeader();
        final byte[] payloadBytes = frameImpl.writePayload();
        final RawFrame rawFrame = new RawFrame(headerBytes, payloadBytes);
        rawFrameHandler.push(rawFrame);
    }

    @Override
    public Frame pull() {
        RawFrame rawFrame = null;
        do {
            rawFrame = rawFrameHandler.pull();
        } while (rawFrame != null);
        final byte[] headerBuffer = rawFrame.getHeaderBytes();
        final byte[] payloadBuffer = rawFrame.getPayloadBytes();
        return AbstractFrameImpl.readFrom(headerBuffer, payloadBuffer, server, validate);
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
