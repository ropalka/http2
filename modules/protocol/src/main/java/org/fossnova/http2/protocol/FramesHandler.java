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

import com.fossnova.http2.protocol.FramesHandlerImpl;

import java.nio.ByteBuffer;

/**
 * // TODO: javadoc
 * @author <a href="mailto:opalka.richard@gmail.com">Richard Opalka</a>
 */
public abstract class FramesHandler {

    /**
     * All implementations must provide public default constructor overriding this one.
     */
    protected FramesHandler() {
    }

    /**
     * TODO: javadoc
     */
    public static FramesHandler newInstance(final boolean server, final boolean validate) {
        return new FramesHandlerImpl(server, validate);
    }
    public abstract ContinuationFrame.Builder newContinuationFrameBuilder();
    public abstract DataFrame.Builder newDataFrameBuilder();
    public abstract GoAwayFrame.Builder newGoAwayFrameBuilder();
    public abstract HeadersFrame.Builder newHeadersFrameBuilder();
    public abstract PingFrame.Builder newPingFrameBuilder();
    public abstract PriorityFrame.Builder newPriorityFrameBuilder();
    public abstract PushPromiseFrame.Builder newPushPromiseFrameBuilder();
    public abstract RstStreamFrame.Builder newRstStreamFrameBuilder();
    public abstract SettingsFrame.Builder newSettingsFrameBuilder();
    public abstract WindowUpdateFrame.Builder newWindowUpdateFrameBuilder();
    public abstract void push(final Frame frame, final ByteBuffer buffer);
    public abstract Frame pull(final ByteBuffer buffer);
}
