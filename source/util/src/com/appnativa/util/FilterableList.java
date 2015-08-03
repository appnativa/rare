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
import java.util.List;

/**
 * A filterablie list implementation
 *
 * @author Don DeCoteau
 */
public class FilterableList<E> extends aFilterableList<E> implements Cloneable {
  public FilterableList(List<E> list) {
    unfilteredList = (list == null)
                     ? new ArrayList<E>()
                     : list;
  }

  /**
   * Creates a new instance of FilterableList backed by a java.util.ArrayList
   */
  public FilterableList() {
    unfilteredList = new ArrayList<E>();
  }

  /**
   * Creates a new instance of FilterableList
   *
   * @param c a collection to use to populate the list
   */
  public FilterableList(Collection<? extends E> c) {
    unfilteredList = new ArrayList<E>(c);
  }

  /**
   * Creates a new instance of FilterableList
   *
   * @param initialCapacity the initial capacity of the list
   */
  public FilterableList(int initialCapacity) {
    unfilteredList = new ArrayList<E>(initialCapacity);
  }

  public void ensureCapacity(int capacity) {
    ((ArrayList) unfilteredList).ensureCapacity(capacity);
  }

  public List<E> subList(int fromIndex, int toIndex) {
    return new FilterableList<E>(getList().subList(fromIndex, toIndex));
  }

  public void trimToSize() {
    if (unfilteredList instanceof ArrayList) {
      ((ArrayList) unfilteredList).trimToSize();
    }
  }

  protected List<E> createFilteringList(int capacity) {
    return new ArrayList<E>(capacity);
  }

  protected List<E> createNewList(int len) {
    return new FilterableList<E>(len);
  }

  public Object clone() {
    try {
      FilterableList<E> fl = (FilterableList<E>) super.clone();

      fl.unfilteredList = fl.createFilteringList(unfilteredList.size());
      fl.unfilteredList.addAll(unfilteredList);

      if (filteredList != null) {
        fl.filteredList = fl.createFilteringList(filteredList.size());
        fl.filteredList.addAll(filteredList);
      }

      return fl;
    } catch(CloneNotSupportedException ex) {
      throw new InternalError();
    }
  }
}
