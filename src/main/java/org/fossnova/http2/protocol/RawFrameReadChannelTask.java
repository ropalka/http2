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
import java.nio.channels.SocketChannel;

/**
 * @author <a href="mailto:ropalka@redhat.com">Richard Opalka</a>
 */
final class RawFrameReadChannelTask extends ReadChannelTask {
    private final ByteBuffer headerBuffer;
    private ByteBuffer payloadBuffer;
    private int written;

    RawFrameReadChannelTask() {
        this.headerBuffer = ByteBuffer.allocate(9);
        this.payloadBuffer = null;
    }

    @Override
    public void execute(final SocketChannel channel) {
        long count;
        ByteBuffer buffer = payloadBuffer == null ? headerBuffer : payloadBuffer;
        try {
            do {
                // TODO: rewrite this to use selector.select() if not possible to write to channel???
                count = channel.read(headerBuffer);
                if (count > 0) {
                    written += count;
                }
            } while (written < total);
        } catch (final Throwable t) {
            reason = t;
            t.printStackTrace(System.err); // TODO: handle failure somehow
        }
    }
    @Override
    ByteBuffer[] getBuffers() {
        return new ByteBuffer[] {headerBuffer, payloadBuffer};
    }


}
