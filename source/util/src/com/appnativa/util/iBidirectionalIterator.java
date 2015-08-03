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
 *
 * @author Don DeCoteau
 */
public interface iBidirectionalIterator<T> extends Iterator<T> {
  void end();

  void home();

  void mark();

  T previous();

  void reset();

  /**
   * Sets the relative position
   * @param percent the relative percentage
   * @return -1 if the new position is before the current position;
   *          0 if there is no change in position
   *          1 if the new position is after the current position
   */
  int setRelativePosition(int percent);

  /**
   * Gets the relative position of the specified item such that
   * a call to <code>setRelativePosition</code> will cause
   * the iterator to be positioned on the specified item
   *
   * @param item the item to get the relative position for
   * @return the position of the specified item or -1 if the item does not exist
   * @see #setRelativePosition(int)
   */
  int getRelativePosition(T item);

  int getSize();

  boolean hasPrevious();

  boolean isFixedSize();
}
