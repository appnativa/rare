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

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * An abstract class implementing a filterable list
 * This implementation uses an unfiltered list to store all items and a filtered
 * list to hold the items that pass the currently installed filter. As a
 * convention, for an item to be in the filtered list, it must be in the main
 * list. Therefore while all other methods will operate only on the active list
 * (the filtered list, if there is one or the main list) the add method will
 * always add the item to the main list and then to the filtered list if it
 * passes the filter. The <b>getUnfilteredList()</b> and <b>getFilteredList()</b> methods
 * lets you directly manipulate the individual list.
 *
 * @author Don DeCoteau
 */
public abstract class aFilterableList<E> implements iFilterableList<E> {

  /** a character index to use when filtering */
  protected CharacterIndex characterIndex;

  /**  */
  protected ContainsFilter containsFilter;

  /** the filtered list */
  protected List<E> filteredList;

  /** the last filter that was applied */
  protected iFilter lastFilter;

  /** the current string converter */
  protected iStringConverter stringConverter;

  /** the main list */
  protected List<E> unfilteredList;

  public boolean add(E e) {
    handleCharacterIndex(e);

    if ((lastFilter != null) && (filteredList != null)) {
      if (passes(lastFilter, e)) {
        filteredList.add(e);
      }
    }

    return unfilteredList.add(e);
  }

  /**
   * Adds an item to the list. If a filter is in place it is ignored
   * and the item is added to the filtered list
   *
   * @param element the item to add
   */
  public void add(int index, E element) {
    handleCharacterIndex(element);

    if ((lastFilter != null) && (filteredList != null)) {
      if (passes(lastFilter, element)) {
        filteredList.add(index, element);
      }

      index = unfilteredList.size();    // add to the end of the main list
    }

    unfilteredList.add(index, element);
  }

  public boolean addAll(Collection<? extends E> c) {
    if (isFiltered() || (characterIndex != null)) {
      int                   len = unfilteredList.size();
      Iterator<? extends E> e   = c.iterator();

      while(e.hasNext()) {
        add(e.next());
      }

      return unfilteredList.size() != len;
    } else {
      return unfilteredList.addAll(c);
    }
  }

  /**
   * Appends all of the specified elements to the end of
   * this list
   *
   * @param a  the array of elements
   *
   * @return true if the elements were added; false otherwise
   */
  public boolean addAll(E... a) {
    int count = (a == null)
                ? 0
                : a.length;

    for (int i = 0; i < count; i++) {
      add(a[i]);
    }

    return count > 0;
  }

  /**
   * Appends all of the specified elements to the end of
   * this list
   *
   * @param a  the array of elements
   * @param count  the number of elements to add
   *
   * @return true if the elements were added; false otherwise
   */
  public boolean addAll(E[] a, int count) {
    for (int i = 0; i < count; i++) {
      add(a[i]);
    }

    return count > 0;
  }

  public boolean addAll(int index, Collection<? extends E> c) {
    if (isFiltered()) {
      boolean               modified = false;
      Iterator<? extends E> e        = c.iterator();

      while(e.hasNext()) {
        add(index++, e.next());
        modified = true;
      }

      return modified;
    } else {
      return unfilteredList.addAll(index, c);
    }
  }

  public void addIndexToFilteredList(int index) {
    if (filteredList == null) {
      filteredList = this.createFilteringList(unfilteredList.size() / 2);
    }

    if (index == -1) {
      filteredList.clear();
    } else {
      filteredList.add(unfilteredList.get(index));
    }
  }

  public void addToFilteredList(E element) {
    handleCharacterIndex(element);

    if (filteredList != null) {
      filteredList.add(element);
    }

    unfilteredList.add(element);
  }

  public void addToFilteredList(int index, E element) {
    handleCharacterIndex(element);

    if (filteredList != null) {
      filteredList.add(index, element);
      index = unfilteredList.size();    // add to the end of the main list
    }

    unfilteredList.add(index, element);
  }

  public int chop(int len) {
    if (len < 1) {
      return 0;
    }

    return chop(getList(), len);
  }

  public static int chop(List list, int len) {
    if (list == null) {
      return 0;
    }

    int end = list.size();

    if (len >= end) {
      list.clear();

      return end;
    }

    for (int i = 0; i < len; i++) {
      list.remove(--end);
    }

    return len;
  }

  public void clear() {
    if (this.filteredList != null) {
      filteredList.clear();
    } else {
      unfilteredList.clear();

      if (characterIndex != null) {
        characterIndex.clear();
      }
    }
  }

  public List<E> concat(List<E>... e) {
    aFilterableList<E> list = (aFilterableList<E>) slice(0, size());

    if (e != null) {
      for (List<E> l : e) {
        list.addAll(l);
      }
    }

    return list;
  }

  public boolean contains(Object o) {
    return getList().contains(o);
  }

  public boolean containsAll(Collection<?> c) {
    return getList().containsAll(c);
  }

  public abstract void ensureCapacity(int capacity);

  public boolean filter(iFilter filter) {
    if (filter == null) {
      return unfilter();
    }

    List<E> list = unfilteredList;
    int     len  = list.size();
    int     olen = size();    // current size

    if (filteredList == null) {
      filteredList = this.createFilteringList(len / 2);
    } else {
      filteredList.clear();
    }

    List<E> flist = filteredList;
    E       item;

    for (int i = 0; i < len; i++) {
      item = list.get(i);

      if (passes(filter, item)) {
        flist.add(item);
      }
    }

    lastFilter = filter;

    return (flist.size() != olen);
  }

  public boolean filter(String filter, boolean contains) {
    return filter(filter, contains, false, false);
  }

  public boolean filter(String filter, boolean contains, boolean nullPasses, boolean emptyPasses) {
    if ((filter == null) || (filter.length() == 0)) {
      if (filteredList != null) {
        if (filteredList.size() != unfilteredList.size()) {
          filteredList = null;

          return true;
        }
      }

      filteredList = null;

      return false;
    }

    boolean filtered = false;

    filter = filter.toLowerCase();

    if (!contains && isSubFilter(filter)) {
      containsFilter.setValue(filter);
      containsFilter.setStartsWith(true);
      containsFilter.setNullPasses(nullPasses);
      containsFilter.setEmptyStringPasses(emptyPasses);
      filtered = subFilter(containsFilter);
    } else {
      iFilter f = null;

      if (contains) {
        if (containsFilter == null) {
          containsFilter = new ContainsFilter(filter, true, false);
        } else {
          containsFilter.setValue(filter);
          containsFilter.setStartsWith(false);
        }

        containsFilter.setNullPasses(nullPasses);
        containsFilter.setEmptyStringPasses(emptyPasses);
        f = containsFilter;
      } else {
        if ((characterIndex != null)) {
          char c = filter.charAt(0);

          filtered       = filterFromIndex(c);
          containsFilter = new ContainsFilter(filter, true, true, nullPasses, emptyPasses);

          if (filter.length() > 1) {
            if (subFilter(containsFilter)) {
              filtered = true;
            }
          }
        } else {
          if (containsFilter == null) {
            containsFilter = new ContainsFilter(filter, true, true, nullPasses, emptyPasses);
          } else {
            containsFilter.setValue(filter);
            containsFilter.setStartsWith(true);
            containsFilter.setNullPasses(nullPasses);
            containsFilter.setEmptyStringPasses(emptyPasses);
          }

          f = containsFilter;
        }
      }

      if (f != null) {    // will be null if w used the index
        filtered = filter(f);
      }
    }

    return filtered;
  }

  public void setFilteredList(List<E> list, iFilter lastFilter) {
    filteredList    = list;
    this.lastFilter = lastFilter;
  }

  public int find(iFilter filter, int start) {
    List<E> list = getList();
    int     len  = list.size();
    int     n    = -1;

    if (start < 0) {
      start = 0;
    }

    E item;

    for (int i = start; i < len; i++) {
      item = list.get(i);

      if (passes(filter, item)) {
        n = i;

        break;
      }
    }

    return n;
  }

  public int find(String filter, int start, boolean contains) {
    iFilter f;

    if ((filter == null) || (filter.length() == 0)) {
      return -1;
    }

    filter = filter.toLowerCase();

    if (contains) {
      if (containsFilter == null) {
        containsFilter = new ContainsFilter(filter, true);
      } else {
        containsFilter.setValue(filter);
        containsFilter.setStartsWith(false);
      }

      f = containsFilter;
    } else {
      if (containsFilter == null) {
        containsFilter = new ContainsFilter(filter, true, true);
      } else {
        containsFilter.setValue(filter);
        containsFilter.setStartsWith(true);
      }

      f = containsFilter;

      if (characterIndex != null) {
        int n = characterIndex.getPosition(filter.charAt(0));

        if (n > start) {
          start = n;
        }
      }
    }

    return find(f, start);
  }

  /**
   * Returns the index of the first occurrence of the specified element
   * in this list, or -1 if this list does not contain the element.
   *
   * @param o element to search for
   * @return the index of the first occurrence of the specified element in
   *         this list, or -1 if this list does not contain the element
   */
  public int identityIndexOf(E o) {
    List<E> list = (filteredList == null)
                   ? unfilteredList
                   : filteredList;
    int     len  = list.size();

    for (int i = 0; i < len; i++) {
      if (list.get(i) == o) {
        return i;
      }
    }

    return -1;
  }

  public int indexOf(Object o) {
    return getList().indexOf(o);
  }

  public Iterator<E> iterator() {
    return getList().iterator();
  }

  public String join() {
    return join(null);
  }

  public String join(String sep) {
    if (sep == null) {
      sep = ",";
    }

    return Helper.toString(getList(), sep);
  }

  public int lastIndexOf(Object o) {
    return getList().lastIndexOf(o);
  }

  public ListIterator<E> listIterator() {
    return getList().listIterator();
  }

  public ListIterator<E> listIterator(int index) {
    return getList().listIterator(index);
  }

  public void move(int from, int to) {
    List<E> list = getList();
    int     len  = list.size();

    if ((from < 0) || (from >= len)) {
      throw new ArrayIndexOutOfBoundsException();
    }

    E e = remove(from);

    len--;

    if ((to < 0) || (to >= len)) {
      add(e);
    } else {
      add(to, e);
    }
  }

  public E pop() {
    List<E> list = getList();

    if (list.size() > 0) {
      return list.remove(list.size() - 1);
    }

    return null;
  }

  public void push(E... e) {
    if ((e != null) && (e.length > 0)) {
      if (e.length == 1) {
        add(e[0]);
      } else {
        addAll(e, e.length);
      }
    }
  }

  public void push(List<E> list) {
    addAll(list);
  }

  public boolean refilter() {
    return (lastFilter == null)
           ? false
           : filter(lastFilter);
  }

  public E remove(int index) {
    if (filteredList != null) {
      E item = filteredList.remove(index);

      if (item != null) {
        unfilteredList.remove(item);
      }

      return item;
    }

    E item = unfilteredList.remove(index);

    if ((characterIndex != null) && (item != null)) {
      rebuilItemIndex();
    }

    return item;
  }

  protected E removeEx(int index) {
    if (filteredList != null) {
      E item = filteredList.remove(index);

      if (item != null) {
        unfilteredList.remove(item);
      }

      return item;
    }

    return unfilteredList.remove(index);
  }

  public boolean remove(Object o) {
    int index = getList().indexOf(o);

    if (index == -1) {
      return false;
    }

    remove(index);

    return true;
  }

  public boolean removeAll(Collection<?> c) {
    if (filteredList != null) {
      boolean  removed = false;
      Iterator it      = c.iterator();

      while(it.hasNext()) {
        Object o     = it.next();
        int    index = filteredList.indexOf(o);

        if (index != -1) {
          filteredList.remove(index);
          unfilteredList.remove(o);
          removed = true;
        }
      }

      return removed;
    }

    if (characterIndex == null) {
      boolean modified = unfilteredList.removeAll(c);

      return modified;
    }

    int len = unfilteredList.size();

    if (len > 5) {
      unfilteredList.removeAll(c);
      this.rebuilItemIndex();
    } else {
      Iterator<?> it = c.iterator();

      while(it.hasNext()) {
        remove(it.next());
      }
    }

    return unfilteredList.size() != len;
  }

  public void removeRows(int[] indexes) {
    int len = (indexes == null)
              ? 0
              : indexes.length;

    if (len > 0) {
      Arrays.sort(indexes);

      while(len > 0) {
        removeEx(indexes[--len]);
      }

      if (characterIndex != null) {
        rebuilItemIndex();
      }
    }
  }

  public boolean retainAll(Collection<?> c) {
    if (filteredList != null) {
      return filteredList.retainAll(c);
    }

    boolean modified = unfilteredList.retainAll(c);

    rebuilItemIndex();

    return modified;
  }

  public List<E> reverse() {
    Collections.reverse(getList());

    return this;
  }

  public E shift() {
    List<E> list = getList();

    if (list.size() > 0) {
      return list.remove(0);
    }

    return null;
  }

  public int size() {
    List<E> list = (filteredList == null)
                   ? unfilteredList
                   : filteredList;

    return list.size();
  }

  public List<E> slice(int start) {
    return slice(start, size());
  }

  public List<E> slice(int start, int end) {
    List<E> list = getList();
    int     len  = list.size();

    if (end < 0) {
      end = len - end;
    }

    if (start >= end) {
      return createNewList(5);
    }

    if (start < 0) {
      throw new IllegalArgumentException("start<0");
    }

    List<E> fl = createNewList(end - start);

    while(start < end) {
      fl.add(list.get(start++));
    }

    return fl;
  }

  public void sort() {
    if ((size() > 0) && (get(0) instanceof String)) {
      sort(Porting.getDefaultComparator());
    } else {
      Collections.sort(getList(), null);
    }
  }

  public void sort(Comparator comparator) {
    Collections.sort(getList(), comparator);
  }

  public List<E> splice(int index, int howMany) {
    return splice(index, howMany, (E[]) null);
  }

  public List<E> splice(int index, int howMany, E... e) {
    return spliceList(index, howMany, (e == null)
                                      ? null
                                      : Arrays.asList(e));
  }

  public List<E> spliceList(int index, int howMany, List<E> e) {
    List<E> list = getList();
    int     len  = list.size();

    if (index < 0) {
      index = len + index;
    }

    if (index < 0) {
      throw new IllegalArgumentException("index=" + index);
    }

    int i = index;

    if (len > i + howMany) {
      len = i + howMany;
    }

    List<E> rlist = createNewList((i < len)
                                  ? len - i
                                  : 0);

    while(i < len) {
      rlist.add(list.get(index));
      list.remove(index);
      i++;
    }

    if (e != null) {
      if (index >= list.size()) {
        addAll(e);
      } else {
        addAll(index, e);
      }
    }

    return rlist;
  }

  public List<E> subList(int fromIndex, int toIndex) {
    return getList().subList(fromIndex, toIndex);
  }

  public void swap(int index1, int index2) {
    E a = get(index1);
    E b = get(index2);

    set(index1, b);
    set(index2, a);
  }

  public Object[] toArray() {
    return getList().toArray();
  }

  public <T> T[] toArray(T[] a) {
    return getList().toArray(a);
  }

  public String toString() {
    return getList().toString();
  }

  /**
   * Trims the list to the specified sizem truncating elements as necessary
   *
   * @param size the size to trim the lis to
   */
  public void trimTo(int size) {
    chop(size() - size);
  }

  /**
   * Trims the list internal data store to fit its current contents
   */
  public abstract void trimToSize();

  public boolean unfilter() {
    boolean filtered = filteredList != null;

    filteredList = null;

    return filtered;
  }

  public void unshift(E value) {
    add(0, value);
  }

  public E set(int index, E element) {
    if (filteredList != null) {
      return filteredList.set(index, element);
    }

    rebuilItemIndex();

    return unfilteredList.set(index, element);
  }

  /**
   * Sets the tree to be the contents of the specified collection
   *
   * @param collection the collection for the tree
   * @return true if the elements were added; false otherwise
   */
  public boolean setAll(Collection<? extends E> collection) {
    clear();

    return addAll(collection);
  }

  public void setConverter(iStringConverter converter) {
    stringConverter = converter;
  }

  /**
   * Sets whether an index is should be used for filtering
   *
   * @param indexForFiltering true if an index is should be used for filtering; false otherwise
   */
  public void setUseIndexForFiltering(boolean indexForFiltering) {
    if (indexForFiltering) {
      characterIndex = new CharacterIndex();
    } else {
      characterIndex = null;
    }
  }

  public E get(int index) {
    List<E> list = (filteredList == null)
                   ? unfilteredList
                   : filteredList;

    return list.get(index);
  }

  public iStringConverter getConverter() {
    return stringConverter;
  }

  public List<E> getFilteredList() {
    return filteredList;
  }

  public CharacterIndex getFilteringIndex() {
    return characterIndex;
  }

  public iFilter getLastFilter() {
    return lastFilter;
  }

  public List<E> getUnfilteredList() {
    return unfilteredList;
  }

  public boolean isEmpty() {
    return getList().isEmpty();
  }

  public boolean isFiltered() {
    return filteredList != null;
  }

  /**
   * Returns whether an index is being used for filtering
   *
   * @return true if an index is being used for filtering; false otherwise
   */
  public boolean isIndexForFiltering() {
    return characterIndex != null;
  }

  /**
   * Creates the list that will hold the filtered items
   *
   * @param capacity the initial capacity for the list
   *
   * @return the list that will hold the filtered items
   */
  protected abstract List<E> createFilteringList(int capacity);

  protected abstract List<E> createNewList(int len);

  /**
   * Filters the list using the filtering index
   *
   * @param c the character to use to do the index lookup
   *
   * @return whether the list was actually filtered
   */
  protected boolean filterFromIndex(char c) {
    if (filteredList == null) {
      filteredList = this.createFilteringList(10);
    } else {
      filteredList.clear();
    }

    int pos = (characterIndex == null)
              ? -1
              : characterIndex.getPosition(c);

    if (pos == -1) {
      return true;
    }

    List<E> flist = filteredList;
    int     pos2  = characterIndex.getPosition((char) (c + 1));

    if (pos2 == -1) {
      pos2 = unfilteredList.size();
    }

    for (int i = pos; i < pos2; i++) {
      flist.add(unfilteredList.get(i));
    }

    return (flist.size() != unfilteredList.size());
  }

  /**
   * Whether the specified item matches the criteria represented by this filter
   *
   * @param filter the filter to use
   * @param e the item to test
   *
   * @return whether the specified value matches the criteria represented by this filter
   */
  protected boolean passes(iFilter filter, E e) {
    if (e != null) {
      return filter.passes(e, stringConverter);
    }

    return false;
  }

  /**
   * Rebuild the item index
   */
  protected void rebuilItemIndex() {
    if (characterIndex == null) {
      return;
    }

    characterIndex.clear();

    Iterator<E> it = unfilteredList.listIterator();
    String      s;
    int         index = 0;

    while(it.hasNext()) {
      s = it.next().toString();

      if ((s != null) && (s.length() > 0)) {
        characterIndex.addCharacter(s.charAt(0), index);
      }

      index++;
    }
  }

  /**
   * Applies a filter to the currently filtered list
   *
   * @param filter the filter to applu
   *
   * @return true if elements were filtered out; false otherwise
   */
  protected boolean subFilter(iFilter filter) {
    List<E> list = filteredList;
    int     len  = list.size();
    E       item;

    for (int i = len - 1; i >= 0; i--) {
      item = list.get(i);

      if (!passes(filter, item)) {
        list.remove(i);
      }
    }

    return list.size() != len;
  }

  /**
   * Gets the list that should be returned as the sub-items list
   *
   * @return the list that should be returned as the sub-items list
   */
  protected List<E> getList() {
    return (filteredList == null)
           ? unfilteredList
           : filteredList;
  }

  protected void handleCharacterIndex(E element) {
    if (characterIndex != null) {
      String s = element.toString();

      if ((s != null) && (s.length() > 0)) {
        characterIndex.addCharacter(s.charAt(0), unfilteredList.size());
      }
    }
  }

  protected boolean isSubFilter(String filter) {
    if ((filteredList != null) && (lastFilter != null) && (containsFilter != null) && (containsFilter.isStartsWith())) {
      return filter.startsWith(containsFilter.getValue());
    }

    return false;
  }
}
