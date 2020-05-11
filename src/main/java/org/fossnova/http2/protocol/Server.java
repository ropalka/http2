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
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:ropalka@redhat.com">Richard Opalka</a>
 */
final class Server implements RawFrameHandler {
    private final String host;
    private final int port;
    private final Selector selector;
    private final ServerSocketChannel serverChannel;
    private final CountDownLatch startLatch, stopLatch;
    private volatile Throwable failure;

    Server(final String host, final int port, final CountDownLatch startLatch, final CountDownLatch stopLatch) throws IOException {
        this.host = host;
        this.port = port;
        this.startLatch = startLatch;
        this.stopLatch = stopLatch;
        selector = Selector.open();
        serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false);
        serverChannel.bind(new InetSocketAddress(host, port));
        SelectionKey sk = serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        sk.attach(new Acceptor());
    }

    @Override
    public void run() {
        try {
            startLatch.countDown();
            while (!Thread.interrupted()) {
                selector.select();
                Set selected = selector.selectedKeys();
                Iterator it = selected.iterator();
                while (it.hasNext())
                    dispatch((SelectionKey)(it.next()));
                selected.clear();
            }
        } catch (final Throwable t) {
            failure = t;
        } finally {
            stopLatch.countDown();
        }
    }

    @Override
    public void push(final RawFrame rawFrame) {
        throw new UnsupportedOperationException(); // TODO: implement
    }

    @Override
    public RawFrame pull() {
        throw new UnsupportedOperationException(); // TODO: implement
    }

    void dispatch(final SelectionKey sk) {
        final Runnable r = (Runnable)(sk.attachment());
        if (r != null) r.run();
    }

    Throwable getError() {
        return failure;
    }

    void close() {
        try {
            selector.close();
        } catch (final Throwable t) {
            failure = t;
        }
    }

    private class Acceptor implements Runnable {
        // inner
        public void run() {
            try {
                SocketChannel c = serverChannel.accept();
                if (c != null) new Handler(selector, c);
            } catch (IOException ex) {
                /* ... */
            }
        }
    }

    static final int MAXIN = 10;
    static final int MAXOUT = 10;

    static final class Handler implements Runnable {
        static final int CORE_THREADS_COUNT = 2;
        static final int MAX_THREADS_COUNT = 4;
        final SocketChannel socket;
        final SelectionKey sk;
        ByteBuffer input = ByteBuffer.allocate(MAXIN);
        ByteBuffer output = ByteBuffer.allocate(MAXOUT);
        static ThreadPoolExecutor pool = new ThreadPoolExecutor(CORE_THREADS_COUNT, MAX_THREADS_COUNT, 30L, TimeUnit.SECONDS, new LinkedBlockingDeque<>());
        static final int READING = 1, SENDING = 2, PROCESSING = 3;
        int state = READING;

        Handler(Selector sel, SocketChannel c) throws IOException {
            socket = c;
            c.configureBlocking(false);// Optionally try first read now
            sk = socket.register(sel, 0);
            sk.attach(this);
            sk.interestOps(SelectionKey.OP_READ);
            sel.wakeup();
        }

        boolean inputIsComplete()  { /* ... */ return false; }
        boolean outputIsComplete() { /* ... */ return false; }
        void process()             { /* ... */ }
        public void run() {
            try {
                if      (state == READING) read();
                else if (state == SENDING) send();
            } catch (IOException ex) { /* ... */ }
        }

        void read() throws IOException {
            socket.read(input);
            if (inputIsComplete()) {
                state = PROCESSING;
                pool.execute(new Processer());
            }
        }

        void send() throws IOException {
            socket.write(output);
            if (outputIsComplete()) sk.cancel();
        }

        synchronized void processAndHandOff() {    process();
            sk.attach(new Sender());
            sk.interestOps(SelectionKey.OP_WRITE);
            sk.selector().wakeup();
            state = SENDING;        // Normally also do first write now
        }

        class Processer implements Runnable {    public void run() { processAndHandOff(); }  }

        class Sender implements Runnable {
            public void run(){ // ...
                try {
                    socket.write(output);
                    if (outputIsComplete()) sk.cancel();
                } catch (Throwable t) {
                    // TODO: handle
                }
            }
        }
    }
}

