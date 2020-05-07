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
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.concurrent.CountDownLatch;

/**
 * @author <a href="mailto:ropalka@redhat.com">Richard Opalka</a>
 */
public class Client implements Runnable {
    private final String host;
    private final int port;
    private final SocketChannel clientChannel;
    private final CountDownLatch startLatch, stopLatch;
    private volatile Throwable failure;

    Client(final String host, final int port, final CountDownLatch startLatch, final CountDownLatch stopLatch) throws IOException {
        this.host = host;
        this.port = port;
        this.startLatch = startLatch;
        this.stopLatch = stopLatch;
        clientChannel = SocketChannel.open();
        clientChannel.configureBlocking(false);
        clientChannel.connect(new InetSocketAddress(host, port));
        while (!clientChannel.finishConnect()) {
            System.out.println("still connecting");
        }
    }

    @Override
    public void run() {
        try {
            startLatch.countDown();
            while (!Thread.interrupted()) {
                // TODO:implement
            }
        } catch (final Throwable t) {
            failure = t;
        } finally {
            stopLatch.countDown();
        }
    }
}
