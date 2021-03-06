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

import java.nio.ByteBuffer;

public final class Huffman {

    private static final int[][] a = new int[256][2];

    static {
        a[   0 ] = new int[] { 0b11111111_11000,                    13 };
        a[   1 ] = new int[] { 0b11111111_11111111_1011000,         23 };
        a[   2 ] = new int[] { 0b11111111_11111111_11111110_0010,   28 };
        a[   3 ] = new int[] { 0b11111111_11111111_11111110_0011,   28 };
        a[   4 ] = new int[] { 0b11111111_11111111_11111110_0100,   28 };
        a[   5 ] = new int[] { 0b11111111_11111111_11111110_0101,   28 };
        a[   6 ] = new int[] { 0b11111111_11111111_11111110_0110,   28 };
        a[   7 ] = new int[] { 0b11111111_11111111_11111110_0111,   28 };
        a[   8 ] = new int[] { 0b11111111_11111111_11111110_1000,   28 };
        a[   9 ] = new int[] { 0b11111111_11111111_11101010,        24 };
        a[  10 ] = new int[] { 0b11111111_11111111_11111111_111100, 30 };
        a[  11 ] = new int[] { 0b11111111_11111111_11111110_1001,   28 };
        a[  12 ] = new int[] { 0b11111111_11111111_11111110_1010,   28 };
        a[  13 ] = new int[] { 0b11111111_11111111_11111111_111101, 30 };
        a[  14 ] = new int[] { 0b11111111_11111111_11111110_1011,   28 };
        a[  15 ] = new int[] { 0b11111111_11111111_11111110_1100,   28 };
        a[  16 ] = new int[] { 0b11111111_11111111_11111110_1101,   28 };
        a[  17 ] = new int[] { 0b11111111_11111111_11111110_1110,   28 };
        a[  18 ] = new int[] { 0b11111111_11111111_11111110_1111,   28 };
        a[  19 ] = new int[] { 0b11111111_11111111_11111111_0000,   28 };
        a[  20 ] = new int[] { 0b11111111_11111111_11111111_0001,   28 };
        a[  21 ] = new int[] { 0b11111111_11111111_11111111_0010,   28 };
        a[  22 ] = new int[] { 0b11111111_11111111_11111111_111110, 30 };
        a[  23 ] = new int[] { 0b11111111_11111111_11111111_0011,   28 };
        a[  24 ] = new int[] { 0b11111111_11111111_11111111_0100,   28 };
        a[  25 ] = new int[] { 0b11111111_11111111_11111111_0101,   28 };
        a[  26 ] = new int[] { 0b11111111_11111111_11111111_0110,   28 };
        a[  27 ] = new int[] { 0b11111111_11111111_11111111_0111,   28 };
        a[  28 ] = new int[] { 0b11111111_11111111_11111111_1000,   28 };
        a[  29 ] = new int[] { 0b11111111_11111111_11111111_1001,   28 };
        a[  30 ] = new int[] { 0b11111111_11111111_11111111_1010,   28 };
        a[  31 ] = new int[] { 0b11111111_11111111_11111111_1011,   28 };
        a[  32 ] = new int[] { 0b010100,                             6 };
        a[  33 ] = new int[] { 0b11111110_00,                       10 };
        a[  34 ] = new int[] { 0b11111110_01,                       10 };
        a[  35 ] = new int[] { 0b11111111_1010,                     12 };
        a[  36 ] = new int[] { 0b11111111_11001,                    13 };
        a[  37 ] = new int[] { 0b010101,                             6 };
        a[  38 ] = new int[] { 0b11111000,                           8 };
        a[  39 ] = new int[] { 0b11111111_010,                      11 };
        a[  40 ] = new int[] { 0b11111110_10,                       10 };
        a[  41 ] = new int[] { 0b11111110_11,                       10 };
        a[  42 ] = new int[] { 0b11111001,                           8 };
        a[  43 ] = new int[] { 0b11111111_011,                      11 };
        a[  44 ] = new int[] { 0b11111010,                           8 };
        a[  45 ] = new int[] { 0b010110,                             6 };
        a[  46 ] = new int[] { 0b010111,                             6 };
        a[  47 ] = new int[] { 0b011000,                             6 };
        a[  48 ] = new int[] { 0b00000,                              5 };
        a[  49 ] = new int[] { 0b00001,                              5 };
        a[  50 ] = new int[] { 0b00010,                              5 };
        a[  51 ] = new int[] { 0b011001,                             6 };
        a[  52 ] = new int[] { 0b011010,                             6 };
        a[  53 ] = new int[] { 0b011011,                             6 };
        a[  54 ] = new int[] { 0b011100,                             6 };
        a[  55 ] = new int[] { 0b011101,                             6 };
        a[  56 ] = new int[] { 0b011110,                             6 };
        a[  57 ] = new int[] { 0b011111,                             6 };
        a[  58 ] = new int[] { 0b1011100,                            7 };
        a[  59 ] = new int[] { 0b11111011,                           8 };
        a[  60 ] = new int[] { 0b11111111_1111100,                  15 };
        a[  61 ] = new int[] { 0b100000,                             6 };
        a[  62 ] = new int[] { 0b11111111_1011,                     12 };
        a[  63 ] = new int[] { 0b11111111_00,                       10 };
        a[  64 ] = new int[] { 0b11111111_11010,                    13 };
        a[  65 ] = new int[] { 0b100001,                             6 };
        a[  66 ] = new int[] { 0b1011101,                            7 };
        a[  67 ] = new int[] { 0b1011110,                            7 };
        a[  68 ] = new int[] { 0b1011111,                            7 };
        a[  69 ] = new int[] { 0b1100000,                            7 };
        a[  70 ] = new int[] { 0b1100001,                            7 };
        a[  71 ] = new int[] { 0b1100010,                            7 };
        a[  72 ] = new int[] { 0b1100011,                            7 };
        a[  73 ] = new int[] { 0b1100100,                            7 };
        a[  74 ] = new int[] { 0b1100101,                            7 };
        a[  75 ] = new int[] { 0b1100110,                            7 };
        a[  76 ] = new int[] { 0b1100111,                            7 };
        a[  77 ] = new int[] { 0b1101000,                            7 };
        a[  78 ] = new int[] { 0b1101001,                            7 };
        a[  79 ] = new int[] { 0b1101010,                            7 };
        a[  80 ] = new int[] { 0b1101011,                            7 };
        a[  81 ] = new int[] { 0b1101100,                            7 };
        a[  82 ] = new int[] { 0b1101101,                            7 };
        a[  83 ] = new int[] { 0b1101110,                            7 };
        a[  84 ] = new int[] { 0b1101111,                            7 };
        a[  85 ] = new int[] { 0b1110000,                            7 };
        a[  86 ] = new int[] { 0b1110001,                            7 };
        a[  87 ] = new int[] { 0b1110010,                            7 };
        a[  88 ] = new int[] { 0b11111100,                           8 };
        a[  89 ] = new int[] { 0b1110011,                            7 };
        a[  90 ] = new int[] { 0b11111101,                           8 };
        a[  91 ] = new int[] { 0b11111111_11011,                    13 };
        a[  92 ] = new int[] { 0b11111111_11111110_000,             19 };
        a[  93 ] = new int[] { 0b11111111_11100,                    13 };
        a[  94 ] = new int[] { 0b11111111_111100,                   14 };
        a[  95 ] = new int[] { 0b100010,                             6 };
        a[  96 ] = new int[] { 0b11111111_1111101,                  15 };
        a[  97 ] = new int[] { 0b00011,                              5 };
        a[  98 ] = new int[] { 0b100011,                             6 };
        a[  99 ] = new int[] { 0b00100,                              5 };
        a[ 100 ] = new int[] { 0b100100,                             6 };
        a[ 101 ] = new int[] { 0b00101,                              5 };
        a[ 102 ] = new int[] { 0b100101,                             6 };
        a[ 103 ] = new int[] { 0b100110,                             6 };
        a[ 104 ] = new int[] { 0b100111,                             6 };
        a[ 105 ] = new int[] { 0b00110,                              5 };
        a[ 106 ] = new int[] { 0b1110100,                            7 };
        a[ 107 ] = new int[] { 0b1110101,                            7 };
        a[ 108 ] = new int[] { 0b101000,                             6 };
        a[ 109 ] = new int[] { 0b101001,                             6 };
        a[ 110 ] = new int[] { 0b101010,                             6 };
        a[ 111 ] = new int[] { 0b00111,                              5 };
        a[ 112 ] = new int[] { 0b101011,                             6 };
        a[ 113 ] = new int[] { 0b1110110,                            7 };
        a[ 114 ] = new int[] { 0b101100,                             6 };
        a[ 115 ] = new int[] { 0b01000,                              5 };
        a[ 116 ] = new int[] { 0b01001,                              5 };
        a[ 117 ] = new int[] { 0b101101,                             6 };
        a[ 118 ] = new int[] { 0b1110111,                            7 };
        a[ 119 ] = new int[] { 0b1111000,                            7 };
        a[ 120 ] = new int[] { 0b1111001,                            7 };
        a[ 121 ] = new int[] { 0b1111010,                            7 };
        a[ 122 ] = new int[] { 0b1111011,                            7 };
        a[ 123 ] = new int[] { 0b11111111_1111110,                  15 };
        a[ 124 ] = new int[] { 0b11111111_100,                      11 };
        a[ 125 ] = new int[] { 0b11111111_111101,                   14 };
        a[ 126 ] = new int[] { 0b11111111_11101,                    13 };
        a[ 127 ] = new int[] { 0b11111111_11111111_11111111_1100,   28 };
        a[ 128 ] = new int[] { 0b11111111_11111110_0110,            20 };
        a[ 129 ] = new int[] { 0b11111111_11111111_010010,          22 };
        a[ 130 ] = new int[] { 0b11111111_11111110_0111,            20 };
        a[ 131 ] = new int[] { 0b11111111_11111110_1000,            20 };
        a[ 132 ] = new int[] { 0b11111111_11111111_010011,          22 };
        a[ 133 ] = new int[] { 0b11111111_11111111_010100,          22 };
        a[ 134 ] = new int[] { 0b11111111_11111111_010101,          22 };
        a[ 135 ] = new int[] { 0b11111111_11111111_1011001,         23 };
        a[ 136 ] = new int[] { 0b11111111_11111111_010110,          22 };
        a[ 137 ] = new int[] { 0b11111111_11111111_1011010,         23 };
        a[ 138 ] = new int[] { 0b11111111_11111111_1011011,         23 };
        a[ 139 ] = new int[] { 0b11111111_11111111_1011100,         23 };
        a[ 140 ] = new int[] { 0b11111111_11111111_1011101,         23 };
        a[ 141 ] = new int[] { 0b11111111_11111111_1011110,         23 };
        a[ 142 ] = new int[] { 0b11111111_11111111_11101011,        24 };
        a[ 143 ] = new int[] { 0b11111111_11111111_1011111,         23 };
        a[ 144 ] = new int[] { 0b11111111_11111111_11101100,        24 };
        a[ 145 ] = new int[] { 0b11111111_11111111_11101101,        24 };
        a[ 146 ] = new int[] { 0b11111111_11111111_010111,          22 };
        a[ 147 ] = new int[] { 0b11111111_11111111_1100000,         23 };
        a[ 148 ] = new int[] { 0b11111111_11111111_11101110,        24 };
        a[ 149 ] = new int[] { 0b11111111_11111111_1100001,         23 };
        a[ 150 ] = new int[] { 0b11111111_11111111_1100010,         23 };
        a[ 151 ] = new int[] { 0b11111111_11111111_1100011,         23 };
        a[ 152 ] = new int[] { 0b11111111_11111111_1100100,         23 };
        a[ 153 ] = new int[] { 0b11111111_11111110_11100,           21 };
        a[ 154 ] = new int[] { 0b11111111_11111111_011000,          22 };
        a[ 155 ] = new int[] { 0b11111111_11111111_1100101,         23 };
        a[ 156 ] = new int[] { 0b11111111_11111111_011001,          22 };
        a[ 157 ] = new int[] { 0b11111111_11111111_1100110,         23 };
        a[ 158 ] = new int[] { 0b11111111_11111111_1100111,         23 };
        a[ 159 ] = new int[] { 0b11111111_11111111_11101111,        24 };
        a[ 160 ] = new int[] { 0b11111111_11111111_011010,          22 };
        a[ 161 ] = new int[] { 0b11111111_11111110_11101,           21 };
        a[ 162 ] = new int[] { 0b11111111_11111110_1001,            20 };
        a[ 163 ] = new int[] { 0b11111111_11111111_011011,          22 };
        a[ 164 ] = new int[] { 0b11111111_11111111_011100,          22 };
        a[ 165 ] = new int[] { 0b11111111_11111111_1101000,         23 };
        a[ 166 ] = new int[] { 0b11111111_11111111_1101001,         23 };
        a[ 167 ] = new int[] { 0b11111111_11111110_11110,           21 };
        a[ 168 ] = new int[] { 0b11111111_11111111_1101010,         23 };
        a[ 169 ] = new int[] { 0b11111111_11111111_011101,          22 };
        a[ 170 ] = new int[] { 0b11111111_11111111_011110,          22 };
        a[ 171 ] = new int[] { 0b11111111_11111111_11110000,        24 };
        a[ 172 ] = new int[] { 0b11111111_11111110_11111,           21 };
        a[ 173 ] = new int[] { 0b11111111_11111111_011111,          22 };
        a[ 174 ] = new int[] { 0b11111111_11111111_1101011,         23 };
        a[ 175 ] = new int[] { 0b11111111_11111111_1101100,         23 };
        a[ 176 ] = new int[] { 0b11111111_11111111_00000,           21 };
        a[ 177 ] = new int[] { 0b11111111_11111111_00001,           21 };
        a[ 178 ] = new int[] { 0b11111111_11111111_100000,          22 };
        a[ 179 ] = new int[] { 0b11111111_11111111_00010,           21 };
        a[ 180 ] = new int[] { 0b11111111_11111111_1101101,         23 };
        a[ 181 ] = new int[] { 0b11111111_11111111_100001,          22 };
        a[ 182 ] = new int[] { 0b11111111_11111111_1101110,         23 };
        a[ 183 ] = new int[] { 0b11111111_11111111_1101111,         23 };
        a[ 184 ] = new int[] { 0b11111111_11111110_1010,            20 };
        a[ 185 ] = new int[] { 0b11111111_11111111_100010,          22 };
        a[ 186 ] = new int[] { 0b11111111_11111111_100011,          22 };
        a[ 187 ] = new int[] { 0b11111111_11111111_100100,          22 };
        a[ 188 ] = new int[] { 0b11111111_11111111_1110000,         23 };
        a[ 189 ] = new int[] { 0b11111111_11111111_100101,          22 };
        a[ 190 ] = new int[] { 0b11111111_11111111_100110,          22 };
        a[ 191 ] = new int[] { 0b11111111_11111111_1110001,         23 };
        a[ 192 ] = new int[] { 0b11111111_11111111_11111000_00,     26 };
        a[ 193 ] = new int[] { 0b11111111_11111111_11111000_01,     26 };
        a[ 194 ] = new int[] { 0b11111111_11111110_1011,            20 };
        a[ 195 ] = new int[] { 0b11111111_11111110_001,             19 };
        a[ 196 ] = new int[] { 0b11111111_11111111_100111,          22 };
        a[ 197 ] = new int[] { 0b11111111_11111111_1110010,         23 };
        a[ 198 ] = new int[] { 0b11111111_11111111_101000,          22 };
        a[ 199 ] = new int[] { 0b11111111_11111111_11110110_0,      25 };
        a[ 200 ] = new int[] { 0b11111111_11111111_11111000_10,     26 };
        a[ 201 ] = new int[] { 0b11111111_11111111_11111000_11,     26 };
        a[ 202 ] = new int[] { 0b11111111_11111111_11111001_00,     26 };
        a[ 203 ] = new int[] { 0b11111111_11111111_11111011_110,    27 };
        a[ 204 ] = new int[] { 0b11111111_11111111_11111011_111,    27 };
        a[ 205 ] = new int[] { 0b11111111_11111111_11111001_01,     26 };
        a[ 206 ] = new int[] { 0b11111111_11111111_11110001,        24 };
        a[ 207 ] = new int[] { 0b11111111_11111111_11110110_1,      25 };
        a[ 208 ] = new int[] { 0b11111111_11111110_010,             19 };
        a[ 209 ] = new int[] { 0b11111111_11111111_00011,           21 };
        a[ 210 ] = new int[] { 0b11111111_11111111_11111001_10,     26 };
        a[ 211 ] = new int[] { 0b11111111_11111111_11111100_000,    27 };
        a[ 212 ] = new int[] { 0b11111111_11111111_11111100_001,    27 };
        a[ 213 ] = new int[] { 0b11111111_11111111_11111001_11,     26 };
        a[ 214 ] = new int[] { 0b11111111_11111111_11111100_010,    27 };
        a[ 215 ] = new int[] { 0b11111111_11111111_11110010,        24 };
        a[ 216 ] = new int[] { 0b11111111_11111111_00100,           21 };
        a[ 217 ] = new int[] { 0b11111111_11111111_00101,           21 };
        a[ 218 ] = new int[] { 0b11111111_11111111_11111010_00,     26 };
        a[ 219 ] = new int[] { 0b11111111_11111111_11111010_01,     26 };
        a[ 220 ] = new int[] { 0b11111111_11111111_11111111_1101,   28 };
        a[ 221 ] = new int[] { 0b11111111_11111111_11111100_011,    27 };
        a[ 222 ] = new int[] { 0b11111111_11111111_11111100_100,    27 };
        a[ 223 ] = new int[] { 0b11111111_11111111_11111100_101,    27 };
        a[ 224 ] = new int[] { 0b11111111_11111110_1100,            20 };
        a[ 225 ] = new int[] { 0b11111111_11111111_11110011,        24 };
        a[ 226 ] = new int[] { 0b11111111_11111110_1101,            20 };
        a[ 227 ] = new int[] { 0b11111111_11111111_00110,           21 };
        a[ 228 ] = new int[] { 0b11111111_11111111_101001,          22 };
        a[ 229 ] = new int[] { 0b11111111_11111111_00111,           21 };
        a[ 230 ] = new int[] { 0b11111111_11111111_01000,           21 };
        a[ 231 ] = new int[] { 0b11111111_11111111_1110011,         23 };
        a[ 232 ] = new int[] { 0b11111111_11111111_101010,          22 };
        a[ 233 ] = new int[] { 0b11111111_11111111_101011,          22 };
        a[ 234 ] = new int[] { 0b11111111_11111111_11110111_0,      25 };
        a[ 235 ] = new int[] { 0b11111111_11111111_11110111_1,      25 };
        a[ 236 ] = new int[] { 0b11111111_11111111_11110100,        24 };
        a[ 237 ] = new int[] { 0b11111111_11111111_11110101,        24 };
        a[ 238 ] = new int[] { 0b11111111_11111111_11111010_10,     26 };
        a[ 239 ] = new int[] { 0b11111111_11111111_1110100,         23 };
        a[ 240 ] = new int[] { 0b11111111_11111111_11111010_11,     26 };
        a[ 241 ] = new int[] { 0b11111111_11111111_11111100_110,    27 };
        a[ 242 ] = new int[] { 0b11111111_11111111_11111011_00,     26 };
        a[ 243 ] = new int[] { 0b11111111_11111111_11111011_01,     26 };
        a[ 244 ] = new int[] { 0b11111111_11111111_11111100_111,    27 };
        a[ 245 ] = new int[] { 0b11111111_11111111_11111101_000,    27 };
        a[ 246 ] = new int[] { 0b11111111_11111111_11111101_001,    27 };
        a[ 247 ] = new int[] { 0b11111111_11111111_11111101_010,    27 };
        a[ 248 ] = new int[] { 0b11111111_11111111_11111101_011,    27 };
        a[ 249 ] = new int[] { 0b11111111_11111111_11111111_1110,   28 };
        a[ 250 ] = new int[] { 0b11111111_11111111_11111101_100,    27 };
        a[ 251 ] = new int[] { 0b11111111_11111111_11111101_101,    27 };
        a[ 252 ] = new int[] { 0b11111111_11111111_11111101_110,    27 };
        a[ 253 ] = new int[] { 0b11111111_11111111_11111101_111,    27 };
        a[ 254 ] = new int[] { 0b11111111_11111111_11111110_000,    27 };
        a[ 255 ] = new int[] { 0b11111111_11111111_11111011_10,     26 };
        a[ 256 ] = new int[] { 0b11111111_11111111_11111111_111111, 30 };
    }

    private Huffman() {
        // forbidden instantiation
    }

    public void encode(final byte[] source, final ByteBuffer target) {
        encode(source, 0, source.length, target);
    }

    public void encode(final byte[] source, final int offset, final int length, final ByteBuffer target) {
        if (source == null) {
            throw new NullPointerException("source");
        }
        if (target == null) {
            throw new NullPointerException("target");
        }
        // TODO: encode
    }

}
