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

import org.fossnova.http2.ConnectionPreface;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

/**
 * @author <a href="mailto:ropalka@redhat.com">Richard Opalka</a>
 */
public class Client implements Runnable {
    private final String host;
    private final int port;
    private final Selector selector;
    private final SocketChannel clientChannel;
    private final CountDownLatch startLatch, stopLatch;
    private final ByteBuffer frameHeaderReadBuffer = ByteBuffer.allocate(9);
    private final ByteBuffer frameHeaderWriteBuffer = ByteBuffer.allocate(9);
    private final ByteBuffer framePayloadReadBuffer = ByteBuffer.allocate(SettingsFrame.DEFAULT_MAX_FRAME_SIZE);
    private final ByteBuffer framePayloadWriteBuffer = ByteBuffer.allocate(SettingsFrame.DEFAULT_MAX_FRAME_SIZE);
    private volatile Throwable failure;

    Client(final String host, final int port, final CountDownLatch startLatch, final CountDownLatch stopLatch) throws IOException {
        this.host = host;
        this.port = port;
        this.startLatch = startLatch;
        this.stopLatch = stopLatch;
        selector = Selector.open();
        clientChannel = SocketChannel.open();
        clientChannel.configureBlocking(false);
        clientChannel.connect(new InetSocketAddress(host, port));
        SelectionKey sk = clientChannel.register(selector, SelectionKey.OP_WRITE);
        sk.attach(new Writer());
        while (!clientChannel.finishConnect()) {
            System.out.println("still connecting");
        }
    }

    @Override
    public void run() {
        try {
            startLatch.countDown();
            // prepare preface data
            final ByteBuffer connPreface = ConnectionPreface.getClientInitialBytes();
            // send preface data
            int writtenBytes;
            do {
                writtenBytes = clientChannel.write(connPreface);
            } while (writtenBytes != -1);
            if (connPreface.hasRemaining()) throw new IllegalStateException();
            // enter main thread loop
            while (!Thread.interrupted()) {
                selector.select();
                Set<SelectionKey> selected = selector.selectedKeys();
                Iterator<SelectionKey> it = selected.iterator();
                while (it.hasNext())
                    dispatch(it.next());
                selected.clear();
            }
        } catch (final Throwable t) {
            failure = t;
        } finally {
            stopLatch.countDown();
        }
    }

    void dispatch(final SelectionKey sk) {
        final Runnable r = (Runnable)(sk.attachment());
        if (r != null) r.run();
    }

    private static class Writer implements Runnable {
        @Override
        public void run() {

        }
    }

}
