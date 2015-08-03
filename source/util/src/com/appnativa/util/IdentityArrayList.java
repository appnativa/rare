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

import java.util.ArrayList;
import java.util.Collection;

/**
 * This class is an array list that use an identity equals test (instead of an equals() call)
 * to find items within the list. The add method dose not allow duplicates to be added.
 *
 * @author Don DeCoteau
 */
@SuppressWarnings("serial")
public class IdentityArrayList<E> extends ArrayList<E> {
  public IdentityArrayList() {}

  public IdentityArrayList(Collection<? extends E> c) {
    super(c);
  }

  public IdentityArrayList(int initialCapacity) {
    super(initialCapacity);
  }

  public boolean addIfNotPresent(E e) {
    if (indexOf(e) == -1) {
      return super.add(e);
    }

    return false;
  }

  @Override
  public boolean contains(Object o) {
    return indexOf(o) != -1;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }

    if (!(o instanceof IdentityArrayList)) {
      return false;
    }

    IdentityArrayList list = (IdentityArrayList) o;
    int               len  = size();

    if (len != list.size()) {
      return false;
    }

    for (int i = 0; i < len; i++) {
      if (list.get(i) != get(i)) {
        return false;
      }
    }

    return true;
  }

  public int indexOf(Object o) {
    int len = size();

    for (int i = 0; i < len; i++) {
      if (o == get(i)) {
        return i;
      }
    }

    return -1;
  }

  public int lastIndexOf(Object o) {
    int len = size();

    for (int i = len - 1; i >= 0; i--) {
      if (o == get(i)) {
        return i;
      }
    }

    return -1;
  }

  public boolean remove(Object o) {
    int n = indexOf(o);

    if (n != -1) {
      remove(n);

      return true;
    }

    return false;
  }
}
