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

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 *
 * @author Don DeCoteau
 */
public class UnsupportedFilterableList<E> implements iFilterableList<E> {
  private static final Object[] EMPTY_ARRAY = new Object[0];

  /** Creates a new instance of UnsupportedFilterableList */
  public UnsupportedFilterableList() {}

  public boolean add(E e) {
    throw new UnsupportedOperationException();
  }

  public void add(int index, E element) {
    throw new UnsupportedOperationException();
  }

  public boolean addAll(Collection<? extends E> c) {
    throw new UnsupportedOperationException();
  }

  public boolean addAll(int index, Collection<? extends E> c) {
    throw new UnsupportedOperationException();
  }

  public void addIndexToFilteredList(int index) {}

  public void addToFilteredList(E element) {
    throw new UnsupportedOperationException();
  }

  public void addToFilteredList(int index, E element) {
    throw new UnsupportedOperationException();
  }

  public void clear() {}

  public List<E> concat(List<E>... e) {
    throw new UnsupportedOperationException();
  }

  public boolean contains(Object o) {
    return false;
  }

  public boolean containsAll(Collection<?> c) {
    return false;
  }

  public void ensureCapacity(int capacity) {}

  public boolean filter(iFilter filter) {
    return false;
  }

  public boolean filter(String filter, boolean contains) {
    return false;
  }

  public boolean filter(String filter, boolean contains, boolean nullPasses, boolean emptyPasses) {
    return false;
  }

  public int find(iFilter filter, int start) {
    return -1;
  }

  public int find(String filter, int start, boolean contains) {
    return -1;
  }

  public int indexOf(Object o) {
    return -1;
  }

  public Iterator<E> iterator() {
    return new Iterator<E>() {
      public boolean hasNext() {
        return false;
      }
      public E next() {
        return null;
      }
      public void remove() {}
    };
  }

  public String join(String sep) {
    throw new UnsupportedOperationException();
  }

  public int lastIndexOf(Object o) {
    return -1;
  }

  public ListIterator<E> listIterator() {
    return new ListIterator<E>() {
      public void add(E arg0) {
        throw new UnsupportedOperationException();
      }
      public boolean hasNext() {
        return false;
      }
      public boolean hasPrevious() {
        return false;
      }
      public E next() {
        return null;
      }
      public int nextIndex() {
        // TODO Auto-generated method stub
        return 0;
      }
      public E previous() {
        return null;
      }
      public int previousIndex() {
        return -1;
      }
      public void remove() {
        throw new UnsupportedOperationException();
      }
      public void set(E arg0) {
        throw new UnsupportedOperationException();
      }
    };
  }

  public ListIterator<E> listIterator(int index) {
    return listIterator();
  }

  public void move(int from, int to) {
    throw new UnsupportedOperationException();
  }

  public E pop() {
    return null;
  }

  public void push(E... e) {
    throw new UnsupportedOperationException();
  }

  public boolean refilter() {
    return false;
  }

  public E remove(int index) {
    throw new UnsupportedOperationException();
  }

  public boolean remove(Object o) {
    return false;
  }

  public boolean removeAll(Collection<?> c) {
    return false;
  }

  public void removeRows(int[] indexes) {
    throw new UnsupportedOperationException();
  }

  public boolean retainAll(Collection<?> c) {
    return false;
  }

  public List<E> reverse() {
    return this;
  }

  public E shift() {
    return null;
  }

  public int size() {
    return 0;
  }

  public List<E> slice(int start) {
    throw new UnsupportedOperationException();
  }

  public List<E> slice(int start, int end) {
    throw new UnsupportedOperationException();
  }

  public void sort(Comparator comparator) {}

  public List<E> splice(int index, int howMany) {
    throw new UnsupportedOperationException();
  }

  public List<E> splice(int index, int howMany, E... e) {
    throw new UnsupportedOperationException();
  }

  public List<E> spliceList(int index, int howMany, List<E> e) {
    throw new UnsupportedOperationException();
  }

  public List<E> subList(int fromIndex, int toIndex) {
    throw new UnsupportedOperationException();
  }

  public void swap(int index1, int index2) {
    throw new UnsupportedOperationException();
  }

  public Object[] toArray() {
    return EMPTY_ARRAY;
  }

  public <T> T[] toArray(T[] a) {
    return a;
  }

  public boolean unfilter() {
    return false;
  }

  public void unshift(E value) {
    throw new UnsupportedOperationException();
  }

  public E set(int index, E element) {
    throw new UnsupportedOperationException();
  }

  public boolean setAll(Collection<? extends E> collection) {
    throw new UnsupportedOperationException();
  }

  public void setConverter(iStringConverter<E> converter) {}

  public E get(int index) {
    throw new UnsupportedOperationException();
  }

  public iStringConverter<E> getConverter() {
    return null;
  }

  public List<E> getFilteredList() {
    return this;
  }

  public iFilter getLastFilter() {
    return null;
  }

  public List<E> getUnfilteredList() {
    return this;
  }

  public boolean isEmpty() {
    return true;
  }

  public boolean isFiltered() {
    return false;
  }

  @Override
  public void setFilteredList(List<E> list, iFilter lastFilter) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void trimToSize() {}
}
