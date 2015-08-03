/*
 * Copyright appNativa Inc. All Rights Reserved.
 *
 * This file is part of the Real-time Application Rendering Engine (RARE).
 *
 * RARE is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */

package com.appnativa.rare.ui.canvas;

import java.nio.ByteBuffer;

public class Uint8ClampedArray {
  public static long BYTES_PER_ELEMENT = 1;
  ByteBuffer         buffer;

  public Uint8ClampedArray(ByteBuffer bb) {
    buffer = bb;
  }

  public Uint8ClampedArray subarray(int begin, int end) {
    buffer.position(begin);
    buffer.limit(end);

    return new Uint8ClampedArray(buffer.slice());
  }

  public void set(byte[] array, int offset) {
    buffer.position(offset);
    buffer.limit(buffer.capacity());
    buffer.put(array);
  }

  public void set(int index, byte value) {
    buffer.put(index, value);
  }

  public byte get(int index) {
    return buffer.get(index);
  }

  public int getLength() {
    return buffer.capacity();
  }
}
