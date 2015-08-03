/*
 * Copyright SparseWare Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.appnativa.util;

import java.util.List;
import java.util.NoSuchElementException;

/**
 *
 * @author Don DeCoteau
 */
public class BidirectionalListIterator<T> implements iBidirectionalIterator<T> {
  private List<T> list;
  private int     index = -1;
  private int     mark  = -1;

  public BidirectionalListIterator(List<T> list) {
    this.list = list;
  }

  public T previous() {
    if (index == -2) {
      index = list.size();
    }

    int n = index - 1;

    if ((n > -1) && (n < list.size())) {
      return list.get(--index);
    }

    throw new NoSuchElementException();
  }

  public void home() {
    index = -1;
  }

  public int getSize() {
    return list.size();
  }

  public void end() {
    index = -2;
  }

  public void mark() {
    mark = index;
  }

  public void reset() {
    index = mark;
  }

  public int getPosition() {
    return index;
  }

  public boolean hasPrevious() {
    if (index == -2) {
      return list.size() > 0;
    }

    int n = index - 1;

    if ((n > -1) && (n < list.size())) {
      return true;
    }

    return false;
  }

  public int getRelativePosition(T item) {
    int n = list.indexOf(item);

    if (n == -1) {
      return -1;
    }

    float f = ((float) n) / ((float) list.size());

    return (int) (f * 100);
  }

  public int getPosition(T item) {
    return list.indexOf(item);
  }

  public int setPosition(int position) {
    int ret = 0;
    int n   = position;

    if ((n < list.size()) && (n >= -1)) {
      if (index < n) {
        ret = 1;
      } else if (index > n) {
        ret = -1;
      }

      index = n;
    }

    return ret;
  }

  public int setRelativePosition(int percent) {
    int ret = 0;

    percent = Math.abs(percent);

    float f = ((float) percent) / 100f % 1f;
    int   n = (int) (list.size() * f);

    if ((n < list.size()) && (n >= 0)) {
      if (index < n) {
        ret = 1;
      } else if (index > n) {
        ret = -1;
      }

      index = n;
    }

    return ret;
  }

  public T get() {
    if ((index > -1) && (index < list.size())) {
      return list.get(index);
    }

    throw new NoSuchElementException();
  }

  public boolean hasNext() {
    int len = list.size();
    int n   = index + 1;

    return (n > -1) && (n < len);
  }

  public T next() {
    int len = list.size();
    int n   = index + 1;

    if ((n > -1) && (n < len)) {
      return list.get(++index);
    }

    throw new NoSuchElementException();
  }

  public void remove() {
    int len = list.size();

    if ((index > 0) && (index < len)) {
      list.remove(index);
    }

    if (index >= list.size()) {
      index = -2;
    }
  }

  public boolean isFixedSize() {
    return true;
  }
}
