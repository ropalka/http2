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
package org.fossnova.http2.hpack;

import org.fossnova.http2.HeaderField;

/**
 * @author <a href="mailto:opalka.richard@gmail.com">Richard Opalka</a>
 */
final class EncoderImpl implements HpackEncoder {

    private static final Policy DEFAULT_POLICY = new DefaultFunction();

    private static final class DefaultFunction implements Policy {
        @Override
        public boolean indexize(final HeaderField hf) {
            return false;
        }

        @Override
        public boolean huffmanize(final HeaderField hf) {
            return false;
        }
    }

    private enum Instruction {
        INDEXED((byte) 0x80),
        SIZE_UPDATE((byte) 0x20),
        WITH_INDEXING((byte) 0x40),
        WITHOUT_INDEXING((byte) 0x0),
        NEVER_INDEXED((byte) 0x10);

        private final byte flag;

        Instruction(final byte flag) {
            this.flag = flag;
        }

        public static Instruction of(final byte b) {
            if ((b & INDEXED.flag) != 0) return INDEXED;
            if ((b & SIZE_UPDATE.flag) != 0) return SIZE_UPDATE;
            if ((b & WITH_INDEXING.flag) != 0) return WITH_INDEXING;
            if ((b & NEVER_INDEXED.flag) != 0) return NEVER_INDEXED;
            return WITHOUT_INDEXING;
        }

    }

    private static final int INITIAL_DYNAMIC_TABLE_SIZE = 4096;
    private final int maxDynamicTableSize;
    private final Policy policy;
    private final boolean server;

    private EncoderImpl(final Policy policy, final int maxDynamicTableSize, final boolean server) {
        this.policy = policy;
        this.maxDynamicTableSize = Math.min(maxDynamicTableSize, INITIAL_DYNAMIC_TABLE_SIZE);
        this.server = server;
    }

    @Override
    public void add(final HeaderField hf) {
        // TODO: implement
        throw new UnsupportedOperationException();
    }

    @Override
    public byte[] finish() {
        // TODO: implement
        throw new UnsupportedOperationException();
    }

    public static EncoderImpl newInstance(final int maxDynamicTableSize, final boolean server) {
        return newInstance(DEFAULT_POLICY, maxDynamicTableSize, server);
    }

    public static EncoderImpl newInstance(final Policy policy, final int maxDynamicTableSize, final boolean server) {
        return new EncoderImpl(policy == null ? DEFAULT_POLICY : policy, maxDynamicTableSize, server);
    }

}
