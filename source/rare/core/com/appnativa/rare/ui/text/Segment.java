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

package com.appnativa.rare.ui.text;

import java.text.CharacterIterator;
/*
 * The following is:
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

public class Segment implements Cloneable, CharacterIterator, CharSequence {
  public char[]   array;
  public int      count;
  public int      offset;
  private boolean isPartial;
  private int     pos;

  public Segment() {
    this(null, 0, 0);
  }

  public Segment(final char[] array, final int offset, final int count) {
    this.array     = array;
    this.offset    = offset;
    this.count     = count;
    this.pos       = 0;
    this.isPartial = false;
  }

  @Override
  public Object clone() {
    Object clone;

    try {
      clone = super.clone();
    } catch(final CloneNotSupportedException e) {
      clone = null;
    }

    return clone;
  }

  @Override
  public char current() {
    if ((pos < 0) || (pos >= count + offset)) {
      return DONE;
    }

    return array[pos];
  }

  @Override
  public char first() {
    pos = offset;

    if (isEmpty()) {
      return DONE;
    }

    return array[pos];
  }

  @Override
  public int getBeginIndex() {
    return offset;
  }

  @Override
  public int getEndIndex() {
    return offset + count;
  }

  @Override
  public int getIndex() {
    return pos;
  }

  public boolean isPartialReturn() {
    return isPartial;
  }

  @Override
  public char last() {
    if (isEmpty()) {
      pos = offset + count;

      return DONE;
    }

    pos = offset + count - 1;

    return array[pos];
  }

  @Override
  public char next() {
    pos++;

    if (pos >= offset + count) {
      pos = offset + count;

      return DONE;
    }

    return array[pos];
  }

  @Override
  public char previous() {
    if (pos == offset) {
      return DONE;
    }

    return array[--pos];
  }

  @Override
  public char setIndex(final int position) {
    if ((position < 0) || (position > offset + count)) {
      throw new IllegalArgumentException("position:" + position);    //$NON-NLS-1$
    }

    pos = position;

    if (position == offset + count) {
      return DONE;
    }

    return array[pos];
  }

  public void setPartialReturn(final boolean p) {
    isPartial = p;

    return;
  }

  @Override
  public String toString() {
    return (array != null)
           ? new String(array, offset, count)
           : "";
  }

  private boolean isEmpty() {
    if ((count == 0) || (array == null) || (array.length == 0)) {
      return true;
    }

    return false;
  }

  @Override
  public char charAt(int index) {
    if ((index < offset) || (index >= (offset + count))) {
      throw new StringIndexOutOfBoundsException(index);
    }

    return array[offset + index];
  }

  @Override
  public int length() {
    return count;
  }

  @Override
  public CharSequence subSequence(int start, int end) {
    if ((start < offset) || (start >= (offset + count))) {
      throw new StringIndexOutOfBoundsException(start);
    }

    if ((end < offset) || (end >= (offset + count))) {
      throw new StringIndexOutOfBoundsException(end);
    }

    if (start > end) {
      throw new StringIndexOutOfBoundsException(start);
    }

    return new Segment(array, offset + start, end - start);
  }
}
