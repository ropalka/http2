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
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

/**
 * @author <a href="mailto:ropalka@redhat.com">Richard Opalka</a>
 */
public class Client implements RawFrameHandler {
    private final String host;
    private final int port;
    private final Selector selector;
    private final SocketChannel clientChannel;
    private final CountDownLatch startLatch, stopLatch;
    private final ClientChannelProcessor acceptor;
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
        final SelectionKey sk = clientChannel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
        while (!clientChannel.finishConnect()) {
            System.out.println("still connecting");
        }
        acceptor = new ClientChannelProcessor(true, host);
    }

    @Override
    public void run() {
        try {
            startLatch.countDown();
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

    @Override
    public void push(final RawFrame rawFrame) {
        acceptor.push(rawFrame);
    }

    @Override
    public RawFrame pull() {
        return acceptor.pull();
    }

    void dispatch(final SelectionKey sk) {
        final Runnable r = (Runnable)(sk.attachment());
        if (r != null) r.run();
    }

    private static class ClientChannelProcessor {

        private static final int IDLE = 0;
        private static final int UPGRADE_TO_HTTP2_REQUEST_SENT = 1;
        private static final int SWITCHING_PROTOCOLS_RECEIVED = 2;
        private static final int CLIENT_PREFACE_SENT = 3;
        private static final int SERVER_PREFACE_RECEIVED = 4;

        private final Queue<ReadChannelTask> readTasks = new LinkedList<>();
        private final Queue<WriteChannelTask> writeTasks = new LinkedList<>();
        private final String host;
        ReadChannelTask currentReadTask;
        WriteChannelTask currentWriteTask;
        private int connectionState;

        private ClientChannelProcessor(final boolean http2, final String host) {
            this.host = host;
            connectionState = http2 ? SWITCHING_PROTOCOLS_RECEIVED : IDLE;
        }

        public void handleEvent(final SelectionKey sk) {
            final SocketChannel channel = (SocketChannel) sk.channel();
            if (sk.isWritable()) {
                // process write tasks
                while (true) {
                    if (currentWriteTask == null) {
                        if (connectionState == IDLE) {
                            currentWriteTask = new UpgradeToHttp2WriteChannelTask(host);
                        }
                        if (connectionState == UPGRADE_TO_HTTP2_REQUEST_SENT) {
                            break; // awaiting SWITCHING_PROTOCOLS_RECEIVED event
                        }
                        if (connectionState == SWITCHING_PROTOCOLS_RECEIVED) {
                            currentWriteTask = new ClientConnectionPrefaceWriteChannelTask();
                        }
                        if (connectionState == CLIENT_PREFACE_SENT) {
                            break; // awaiting SERVER_PREFACE_RECEIVED event
                        }
                        if (connectionState == SERVER_PREFACE_RECEIVED) {
                            // sending user defined frames after successful initial handshake
                            synchronized (writeTasks) {
                                currentWriteTask = writeTasks.poll();
                            }
                            if (currentWriteTask == null) {
                                break; // no user defined frames to be written are available
                            }
                        }
                    } else {
                        currentWriteTask.execute(channel);
                        if (currentWriteTask.isDone()) {
                            if (currentWriteTask instanceof UpgradeToHttp2WriteChannelTask) {
                                currentWriteTask = new ClientConnectionPrefaceWriteChannelTask();
                                continue;
                            }
                            if (currentWriteTask instanceof ClientConnectionPrefaceWriteChannelTask) {
                                connectionState = CLIENT_PREFACE_SENT;
                            }
                            currentWriteTask = null;
                        }
                    }
                }
            }
            if (sk.isReadable()) {
                // process read tasks
                while (true) {
                    if (currentReadTask == null) {
                        if (connectionState == IDLE) throw new RuntimeException();
                        if (connectionState == CLIENT_PREFACE_SENT) {
                            currentReadTask = null; // TODO: new read task to do connection negotiation
                        }
                        if (connectionState == SERVER_PREFACE_RECEIVED) {
                            currentReadTask = null; // TODO: new read task to process next frame
                        }
                        if (currentReadTask == null) throw new RuntimeException();
                        currentReadTask.execute(channel);
                        if (currentReadTask.isDone()) {
                            synchronized (readTasks) {
                                readTasks.offer(currentReadTask);
                            }
                            currentReadTask = null;
                        } else break;
                    }
                }
            }
        }

        public void push(final RawFrame rawFrame) {
            synchronized (writeTasks) {
                writeTasks.offer(new WriteChannelTask(ByteBuffer.wrap(rawFrame.header), ByteBuffer.wrap(rawFrame.payload)));
            }
        }

        public RawFrame pull() {
            synchronized (readTasks) {
                final ReadChannelTask readTask = readTasks.poll();
                if (readTask == null) return null; // TODO: implement thread wait if value is not yet available ?
                final ByteBuffer[] buffers = readTask.getBuffers();
                return new RawFrame(buffers[0].array(), buffers[1].array());
            }
        }

    }

}
