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

import java.util.Iterator;

/**
 * A iterator combines a set of iterators in one
 * iterator
 *
 * @author Don DeCoteau
 */
public class ComboIterator<T> implements Iterator<T> {
  private int           itPos = 0;
  private Iterator<T>   currentIterator;
  private boolean       finishedCurrent;
  private int           itCount;
  private Iterator<T>[] theIterators;

  /**
   * Creates a new instance of ComboIterator
   *
   * @param iterators the iterators to combine
   */
  public ComboIterator(Iterator<T>[] iterators) {
    theIterators = iterators;
    itCount      = (iterators == null)
                   ? 0
                   : iterators.length;

    if (itCount > 0) {
      currentIterator = theIterators[itPos++];
      finishedCurrent = false;
    } else {
      finishedCurrent = false;
    }
  }

  /**
   * {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  public boolean hasNext() {
    if (finishedCurrent) {
      if (itPos == itCount) {
        return false;
      }

      currentIterator = theIterators[itPos++];
    }

    boolean has = currentIterator.hasNext();

    finishedCurrent = !has;

    return has;
  }

  /**
   * {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  public T next() {
    return currentIterator.next();
  }

  public void remove() {
    currentIterator.remove();
  }
}
