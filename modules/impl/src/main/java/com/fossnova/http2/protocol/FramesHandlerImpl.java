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

/**
 * @author <a href="mailto:opalka.richard@gmail.com">Richard Opalka</a>
 */
public final class FramesHandlerImpl extends FramesHandler {

    public FramesHandlerImpl() {
    }

    @Override
    public void push(Frame frame) {
        // TODO: implement
        throw new UnsupportedOperationException();
    }

    @Override
    public Frame pull() {
        // TODO: implement
        throw new UnsupportedOperationException();
    }

    @Override
    public ContinuationFrame.Builder newContinuationFrameBuilder() {
        // TODO: implement
        throw new UnsupportedOperationException();
    }

    @Override
    public DataFrame.Builder newDataFrameBuilder() {
        // TODO: implement
        throw new UnsupportedOperationException();
    }

    @Override
    public GoAwayFrame.Builder newGoAwayFrameBuilder() {
        // TODO: implement
        throw new UnsupportedOperationException();
    }

    @Override
    public HeadersFrame.Builder newHeadersFrameBuilder() {
        // TODO: implement
        throw new UnsupportedOperationException();
    }

    @Override
    public PingFrame.Builder newPingFrameBuilder() {
        // TODO: implement
        throw new UnsupportedOperationException();
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
        // TODO: implement
        throw new UnsupportedOperationException();
    }

    @Override
    public SettingsFrame.Builder newSettingsFrameBuilder() {
        // TODO: implement
        throw new UnsupportedOperationException();
    }

    @Override
    public WindowUpdateFrame.Builder newWindowUpdateFrameBuilder() {
        // TODO: implement
        throw new UnsupportedOperationException();
    }
}