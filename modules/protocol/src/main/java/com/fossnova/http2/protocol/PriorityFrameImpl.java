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
package com.fossnova.http2.protocol;

import org.fossnova.http2.protocol.PriorityFrame;

import java.nio.ByteBuffer;

/**
 * @author <a href="mailto:opalka.richard@gmail.com">Richard Opalka</a>
 */
final class PriorityFrameImpl extends AbstractFrameImpl implements PriorityFrame {
    private final boolean exclusive;
    private final int dependencyStreamId;
    private final int weight;

    PriorityFrameImpl(final byte flags, final int streamId, final boolean exclusive, final int dependencyStreamId, final int weight) {
        super(5, FrameType.PRIORITY, flags, streamId);
        this.exclusive = exclusive;
        this.dependencyStreamId = dependencyStreamId;
        this.weight = weight;
    }

    @Override
    public boolean isDependencyExclusive() {
        return exclusive;
    }

    @Override
    public int getDependencyStream() {
        return dependencyStreamId;
    }

    @Override
    public int getWeight() {
        return weight;
    }

    void writeTo(final ByteBuffer buffer) {
        super.writeTo(buffer);
        if (exclusive) {
            buffer.put((byte)(0b00000000_00000000_00000000_10000000 | dependencyStreamId >>> 24));
        } else {
            buffer.put((byte)(dependencyStreamId >>> 24));
        }
        buffer.put((byte)(dependencyStreamId >>> 16));
        buffer.put((byte)(dependencyStreamId >>> 8));
        buffer.put((byte)(dependencyStreamId));
        buffer.put((byte)weight);
    }

    static PriorityFrameImpl readFrom(final ByteBuffer buffer, final Builder builder) {
        // implementation
        int dependencyStreamId = 0xFF_00_00_00 & buffer.get() << 24;
        boolean exclusive = (0b10000000_00000000_00000000_00000000 & dependencyStreamId) != 0;
        if (exclusive) {
            dependencyStreamId &= 0b01111111_11111111_11111111_11111111;
        }
        dependencyStreamId |= 0x00_FF_00_00 & buffer.get() << 16;
        dependencyStreamId |= 0x00_00_FF_00 & buffer.get() << 8;
        dependencyStreamId |= 0x00_00_00_FF & buffer.get();
        int weight = 0x00_00_00_FF & buffer.get();
        builder.setDependencyExclusive(exclusive);
        builder.setDependencyStream(dependencyStreamId);
        builder.setWeight(weight);
        return builder.build();
    }

    final static class Builder extends AbstractFrameImpl.Builder implements PriorityFrame.Builder {
        boolean exclusive;
        int dependencyStreamId;
        int streamWeight;
        boolean built;

        Builder(final boolean server, final boolean request, final boolean validate) {
            super(server, request, validate);
        }

        Builder(final boolean server, final boolean request, final boolean validate, final int payloadSize, final FrameType frameType, final byte flags, final int streamId) {
            super(server, request, validate);
            super.setPayloadSize(payloadSize);
            super.setFrameType(frameType);
            super.setFlags(flags);
            super.setStreamId(streamId);
        }

        @Override
        public void setDependencyExclusive(final boolean exclusive) {
            // preconditions
            ensureThreadSafety();
            ensureNotBuilt();
            // implementation
            this.exclusive = exclusive;
        }

        @Override
        public void setDependencyStream(final int dependencyStreamId) {
            // preconditions
            ensureThreadSafety();
            ensureNotBuilt();
            ensure31BitsOnlySet(dependencyStreamId);
            // implementation
            this.dependencyStreamId = dependencyStreamId;
        }

        @Override
        public void setWeight(final int streamWeight) {
            // preconditions
            ensureThreadSafety();
            ensureNotBuilt();
            ensure8BitsOnlySet(streamWeight);
            // implementation
            this.streamWeight = streamWeight;
        }

        @Override
        public PriorityFrameImpl build() {
            // preconditions
            ensureThreadSafety();
            ensureNotBuilt();
            // validation
            validateStreamId(streamId);
            // implementation
            built = true;
            return new PriorityFrameImpl((byte)flags, streamId, exclusive, dependencyStreamId, streamWeight);
        }

        @Override
        void validateFlags(final int flags) {
            if (flags != 0) {
                throw new IllegalArgumentException();
            }
        }

        @Override
        void validateStreamId(final int streamId) {
            if (streamId == 0) {
                throw new IllegalArgumentException();
            }
        }

        @Override
        void validatePayloadSize(final int payloadSize) {
            if (payloadSize != 5) {
                throw new IllegalArgumentException();
            }
        }

        private void ensureNotBuilt() {
            if (built) {
                throw new IllegalStateException();
            }
        }

    }
}
