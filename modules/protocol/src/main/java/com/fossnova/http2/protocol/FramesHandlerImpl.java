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
package com.fossnova.http2.protocol;

import org.fossnova.http2.protocol.*;

import java.nio.ByteBuffer;

/**
 * @author <a href="mailto:opalka.richard@gmail.com">Richard Opalka</a>
 */
public final class FramesHandlerImpl extends FramesHandler {

    private final boolean server;
    private final boolean validate;

    public FramesHandlerImpl(final boolean server, final boolean validate) {
        this.server = server;
        this.validate = validate;
    }

    @Override
    public void push(final Frame frame, final ByteBuffer buffer) {
        ((AbstractFrameImpl) frame).writeTo(buffer);
    }

    @Override
    public Frame pull(final ByteBuffer buffer) {
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
        // TODO: implement
        throw new UnsupportedOperationException();
    }

    @Override
    public PushPromiseFrame.Builder newPushPromiseFrameBuilder() {
        // TODO: implement
        throw new UnsupportedOperationException();
    }

    @Override
    public RstStreamFrame.Builder newRstStreamFrameBuilder() {
        return new RstStreamFrameImpl.Builder(server, !server, validate);
    }

    @Override
    public SettingsFrame.Builder newSettingsFrameBuilder() {
        // TODO: implement
        throw new UnsupportedOperationException();
    }

    @Override
    public WindowUpdateFrame.Builder newWindowUpdateFrameBuilder() {
        return new WindowUpdateFrameImpl.Builder(server, !server, validate);
    }
}
