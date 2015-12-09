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

/**
 * This class represent a performance sensitive version of a <code>java.util.ArrayList</code>
 * instance specifically designed to handle integer primitives. Both the internal array
 * (<b>A</b>) and the list's length (<b>_length</b>) are exposed as
 * public variables. This allows direct manipulation of the lists contents. As
 * such, care should be taken when directly modifying either of these fields.
 *
 * @author Don DeCoteau
 * @see       java.util.ArrayList
 * @version 2.3
 */
public final class IntList {

  /** the array that holds the contents of the list */
  public int[] A;

  /** the length of the list */
  public int _length;

  /**
   * Constructs an empty list with an initial capacity of ten.
   */
  public IntList() {
    A = new int[10];
  }

  /**
   * Constructs an empty list with the specified initial capacity.
   *
   * @param len  the initial capacity of the list.
   */
  public IntList(int len) {
    A = new int[len];
  }

  /**
   * Constructs a new list and uses the specified array as is backing store
   *
   * @param values  the element values to copy
   */
  public IntList(int[] values) {
    A       = values;
    _length = values.length;
  }

  /**
   * Appends the specified element to the end of this list.
   *
   * @param e  element to be inserted.
   */
  public void add(int e) {
    if (_length == A.length) {
      ensureCapacity(_length + 1);
    }

    A[_length++] = e;
  }

  /**
   * Appends the specified array of elements to the end of this list.
   *
   * @param e  element to be inserted.
   */
  public void add(int[] e) {
    add(e, 0, e.length);
  }

  /**
   * Inserts the specified element at the specified position in this list. Shifts the
   * element currently at that position (if any) and any subsequent elements to the
   * right (adds one to their indices).
   *
   * @param pos  index at which the specified element is to be inserted.
   * @param e    element to be inserted.
   *
   */
  public void add(int pos, int e) {
    if (pos > _length) {
      throw new IndexOutOfBoundsException(String.valueOf(pos) + ">" + String.valueOf(_length));
    }

    if (pos < 0) {
      throw new IndexOutOfBoundsException(String.valueOf(pos) + "<0");
    }

    if (_length == A.length) {
      ensureCapacity(_length + 1);
    }

    if (pos == _length) {
      A[_length++] = e;

      return;
    }

    System.arraycopy(A, pos, A, pos + 1, _length - pos);
    A[pos] = e;
    _length++;
  }

  /**
   * Appends the specified array of elements to the end of this list.
   *
   * @param e    elements to be append.
   * @param pos  the position within the array
   * @param len  the number of elements
   */
  public void add(int[] e, int pos, int len) {
    if (len == -1) {
      len = e.length - pos;
    }

    int nlen = len + _length;

    if (nlen > A.length) {
      ensureCapacity(nlen);
    }

    if (len == 1) {
      add(e[pos]);
    } else {
      if (len > 20) {
        System.arraycopy(e, pos, A, _length, len);
        _length = nlen;
      } else {
        len += pos;

        for (int i = pos; i < len; i++) {
          A[_length++] = e[i];
        }
      }
    }
  }

  /**
   * Adds the specified array of elements at the specified position
   *
   * @param index  the position to insert the elements
   * @param e      elements to be append.
   * @param pos    the position within the array
   * @param len    the number of elements
   *
   */
  public void add(int index, int[] e, int pos, int len) {
    if (index > _length) {
      throw new IndexOutOfBoundsException(String.valueOf(pos) + ">" + String.valueOf(_length));
    }

    if (index < 0) {
      throw new IndexOutOfBoundsException(String.valueOf(pos) + "<0");
    }

    if (len == -1) {
      len = e.length - pos;
    }

    int nlen = len + _length;

    ensureCapacity(nlen);
    System.arraycopy(A, index, A, index + len, _length - index);
    System.arraycopy(e, pos, A, index, len);
    _length = nlen;
  }

  /**
   * Appends the specified list of elements to the end of this list.
   *
   * @param e  elements to append.
   */
  public void addAll(IntList e) {
    add(e.A, 0, e._length);
  }

  /**
   * Removes all of the elements from this list. The list will be empty after this
   * call returns.
   */
  public void clear() {
    _length = 0;
  }

  /**
   * Returns a shallow copy of this list. (The elements themselves are not copied.)
   *
   * @return   a clone of this list.
   */
  public Object clone() {
    IntList v = new IntList(_length);

    System.arraycopy(A, 0, v.A, 0, _length);

    return v;
  }

  /**
   * Returns <tt>true</tt> if this list contains the specified element.
   *
   * @param e  element whose presence in this List is to be tested.
   *
   * @return   <code>true</code> if the specified element is present; <code>false</code>
   *           otherwise.
   */
  public boolean contains(int e) {
    return indexOf(e) != -1;
  }

  /**
   * Copies the contents of this list into the specified array. The array must be big
   * enough to hold all the ints in this list, otherwise an <code>IndexOutOfBoundsException</code>
   * is thrown.
   *
   * @param a  the array into which the elements get copied.
   */
  public void copyInto(int[] a) {
    System.arraycopy(A, 0, a, 0, _length);
  }

  /**
   * Copies the specified number elements of this list into the specified array.
   *
   * @param a    the array into which the elements get copied.
   * @param pos  the starting position within the array
   * @param len  the number of elements to copy
   *
   * @return   the actual number of elements copied. This will differ from <code>len,</code>
   *           if <code>len</code> is greater than the number of elements in the list.
   */
  public int copyInto(int[] a, int pos, int len) {
    if (len > _length) {
      len = _length;
    }

    System.arraycopy(A, 0, a, pos, len);

    return len;
  }

  /**
   * Removes the ints from the array
   *
   * @param start  The beginning index, inclusive.
   * @param end    The ending index, exclusive.
   *
   * @return   This character array.
   */
  public IntList delete(int start, int end) {
    checkRange(start);

    if ((end == -1) || (end > _length)) {
      end = _length;
    }

    int len = end - start;

    if (len > 0) {
      System.arraycopy(A, start + len, A, start, _length - end);
      _length -= len;
    }

    return this;
  }

  /**
   * Increases the capacity of this <tt>IntList</tt> instance, if necessary, to
   * ensure that it can hold at least the number of elements specified by the minimum
   * capacity argument.
   *
   * @param len  the desired minimum capacity.
   */
  public void ensureCapacity(int len) {
    int olen = A.length;

    if (len > olen) {
      int a[]         = A;
      int newCapacity = (olen * 3) / 2 + 1;

      if (newCapacity < len) {
        newCapacity = len;
      }

      A = new int[newCapacity];
      System.arraycopy(a, 0, A, 0, _length);
    }
  }

  /**
   * Returns the element at the specified position in this list.
   *
   * @param pos  index of element to return.
   *
   * @return   the element at the specified position in this list.
   *
   * @throws   IndexOutOfBoundsException if index is out of range
   */
  public int get(int pos) throws IndexOutOfBoundsException {
    checkRange(pos);

    return A[pos];
  }

  /**
   * Searches for the first occurrence of a value that is greater than or equal to the given argument
   *
   * @param e     the value to search for.
   *
   * @return   the index of the first occurrence that matches; returns <tt>-1</tt>
   *           if no match was found.
   *
   */
  public int gteIndexOf(int e) {
    for (int i = 0; i < _length; i++) {
      if (A[i] >= e) {
        return i;
      }
    }

    return -1;
  }

  /**
   * Searches for the first occurrence of a value that is  equal to the given argument
   *
   * @param e     the value to search for.
   *
   * @return   the index of the first occurrence of the argument in this list; returns <tt>-1</tt>
   *           if the integer is not found.
   *
   */
  public int indexOf(int e) {
    final int len   = _length;
    final int num[] = A;

    for (int i = 0; i < len; i++) {
      if (num[i] == e) {
        return i;
      }
    }

    return -1;
  }

  /**
   * Searches for the first occurrence of the given argument, testing for equality
   * using the <tt>equals</tt> method.
   *
   * @param list  the list to search
   * @param e     the value to search for.
   *
   * @return   the index of the first occurrence of the argument in this list; returns <tt>-1</tt>
   *           if the integer is not found.
   *
   */
  public static int indexOf(int[] list, int e) {
    for (int i = 0; i < list.length; i++) {
      if (list[i] == e) {
        return i;
      }
    }

    return -1;
  }

  /**
   * Moves a value from one location to another
   *
   * @param from     the from position.
   * @param to the to position
   */
  public  void move(int from, int to) {
    if(!move(A,_length,from,to)) {
      throw new IllegalArgumentException();
    }
  }
  

  /**
   * Moves a value in the specified array from one location to another
   *
   * @param list  the list to delete from
   * @param length the length of the array to move within (use -1 for the while array)
   * @param from     the from position.
   * @param to the to position
   * 
   * @return false if the specified parameters were invalid; true otherwise
   */
  public static boolean move(int[] list,int length, int from, int to) {
    int len=length==-1 ? list.length : length;
    if(to<0 || to>=len) {
      return false;
    }
    if(from<0 || from>=len) {
      return false;
    }
    if(from==to) {
      return true;
    }
    int val=list[from];
    if(from!=len-1) {
      System.arraycopy(list, from+1, list, from, len-from-1);
    }
    if(to!=len-1) {
      System.arraycopy(list, to, list, to+1, len-to-1);
    }
    list[to]=val;
    return true;
   }
  
  /**
   * Searches for the first occurrence of a value that is greater than the given argument
   *
   * @param e     the value to search for.
   *
   * @return   the index of the first occurrence that matches; returns <tt>-1</tt>
   *           if no match was found.
   *
   */
  public int indexOfGT(int e) {
    for (int i = 0; i < _length; i++) {
      if (A[i] > e) {
        return i;
      }
    }

    return -1;
  }

  /**
   * Searches for the first occurrence of a value that is greater than or equal to the given argument
   *
   * @param e     the value to search for.
   *
   * @return   the index of the first occurrence that matches; returns <tt>-1</tt>
   *           if no match was found.
   *
   */
  public int indexOfGTE(int e) {
    for (int i = 0; i < _length; i++) {
      if (A[i] >= e) {
        return i;
      }
    }

    return -1;
  }

  /**
   * Searches for the first occurrence of a value that is less than the given argument
   *
   * @param e     the value to search for.
   *
   * @return   the index of the first occurrence that matches; returns <tt>-1</tt>
   *           if no match was found.
   *
   */
  public int indexOfLT(int e) {
    for (int i = 0; i < _length; i++) {
      if (A[i] < e) {
        return i;
      }
    }

    return -1;
  }

  /**
   * Searches for the first occurrence of a value that is less than or equal to the given argument
   *
   * @param e     the value to search for.
   *
   * @return   the index of the first occurrence that matches; returns <tt>-1</tt>
   *           if no match was found.
   *
   */
  public int indexOfLTE(int e) {
    for (int i = 0; i < _length; i++) {
      if (A[i] <= e) {
        return i;
      }
    }

    return -1;
  }

  /**
   * Tests if this buffer is empty.
   *
   * @return   <tt>true</tt> if this list has no elements; <tt>false</tt> otherwise.
   */
  public boolean isEmpty() {
    return _length == 0;
  }

  /**
   * Returns the index of the last occurrence of the specified integer in this list.
   *
   * @param e  the desired integer.
   *
   * @return   the index of the last occurrence of the specified element in this list;
   *           returns -1 if the integer is not found.
   */
  public int lastIndexOf(int e) {
    for (int i = _length - 1; i >= 0; i--) {
      if (A[i] == e) {
        return i;
      }
    }

    return -1;
  }

  /**
   * Returns the index of the last occurrence of an integer in this list that is greater than the specified integer.
   *
   * @param e  the desired integer.
   *
   * @return   the index of the last occurrence of the specified integer in this list;
   *           returns -1 if the integer is not found.
   */
  public int lastIndexOfGT(int e) {
    for (int i = _length - 1; i >= 0; i--) {
      if (A[i] > e) {
        return i;
      }
    }

    return -1;
  }

  /**
   * Returns the index of the last occurrence of an integer in this list that is greater than or equal
   * to the specified integer.
   *
   * @param e  the desired integer.
   *
   * @return   the index of the last occurrence of the specified integer in this list;
   *           returns -1 if the integer is not found.
   */
  public int lastIndexOfGTE(int e) {
    for (int i = _length - 1; i >= 0; i--) {
      if (A[i] >= e) {
        return i;
      }
    }

    return -1;
  }

  /**
   * Returns the index of the last occurrence of an integer in this list that is less than the specified integer.
   *
   * @param e  the desired integer.
   *
   * @return   the index of the last occurrence of the specified integer in this list;
   *           returns -1 if the integer is not found.
   */
  public int lastIndexOfLT(int e) {
    for (int i = _length - 1; i >= 0; i--) {
      if (A[i] < e) {
        return i;
      }
    }

    return -1;
  }

  /**
   * Returns the index of the last occurrence of an integer in this list that is less than or equal
   * to the specified integer.
   *
   * @param e  the desired integer.
   *
   * @return   the index of the last occurrence of the specified integer in this list;
   *           returns -1 if the integer is not found.
   */
  public int lastIndexOfLTE(int e) {
    for (int i = _length - 1; i >= 0; i--) {
      if (A[i] <= e) {
        return i;
      }
    }

    return -1;
  }

  /**
   * Looks at the last item in the list without removing it.
   *
   * @return   the last item
   *
   */
  public int peek() {
    if (_length == 0) {
      throw new java.util.EmptyStackException();
    }

    return A[_length - 1];
  }

  /**
   * Removes the last item in the list and returns that item as the value of this
   * function.
   *
   * @return   the last item
   *
   */
  public int pop() {
    if (_length == 0) {
      throw new java.util.EmptyStackException();
    }

    _length--;

    int s = A[_length];

    return s;
  }

  public void sort() {
    if (_length > 0) {
      Arrays.sort(A, 0, _length);
    }
  }

  /**
   * Adds the specified element to the end of the list
   *
   * @param e  the element
   */
  public void push(int e) {
    if (_length == A.length) {
      ensureCapacity(_length + 1);
    }

    A[_length++] = e;
  }

  /**
   * Removes the element at the specified position in this list. Shifts any
   * subsequent elements to the left (subtracts one from their indices).
   *
   * @param pos  the index of the integer to be removed.
   *
   * @return   the integer that was removed from the list.
   */
  public int removeAt(int pos) {
    checkRange(pos);

    int ret = A[pos];

    if (pos != (_length - 1)) {
      System.arraycopy(A, pos + 1, A, pos, _length - (pos + 1));
    }

    _length--;

    return ret;
  }

  /**
   * Removes the specified element from this list. Shifts any subsequent elements to
   * the left (subtracts one from their indices).
   *
   * @param num  the number to remove
   *
   */
  public void removeValue(int num) {
    int i = indexOf(num);

    if (i != -1) {
      removeAt(i);
    }
  }

  /**
   * Removes the specified element from this list
   *
   * @param num  the numbers to remove
   *
   */
  public void removeValues(int... num) {
    if (num == null) {
      return;
    }

    final int len = num.length;

    for (int i = 0; i < len; i++) {
      int n = indexOf(num[i]);

      if (n != -1) {
        removeAt(n);
      }
    }
  }

  /**
   * Removes from this list all of its elements that are contained in the
   * specified list
   *
   * @param list the list of elements to remove
   */
  public void removeValues(IntList list) {
    final int len = (list == null)
                    ? 0
                    : list._length;

    if (len == 0) {
      return;
    }

    final int[] num = list.A;

    for (int i = 0; i < len; i++) {
      int n = indexOf(num[i]);

      if (n != -1) {
        removeAt(n);
      }
    }
  }

  /**
   * Replaces the element at the specified position in this list with the specified
   * element.
   *
   * @param pos  index of element to replace.
   * @param e    element to be stored at the specified position.
   *
   * @return   the element previously at the specified position.
   */
  public int set(int pos, int e) {
    checkRange(pos);

    int o = A[pos];

    A[pos] = e;

    return o;
  }

  /**
   * Sets the length of this list.
   * <p>The <code>newLength</code> argument must be greater than or equal to <code>0</code>.
   *
   * @param newLength  the new length of the buffer.
   */
  public void setLength(int newLength) {
    if (newLength > A.length) {
      ensureCapacity(newLength);
    }

    _length = newLength;
  }

  /**
   * Returns the number of elements in this list.
   *
   * @return   the number of elements in this list.
   */
  public int size() {
    return _length;
  }

  /**
   * Returns an array containing all of the elements in this list in the correct
   * order.
   *
   * @return   an array containing all of the elements in this list in the correct order.
   */
  public int[] toArray() {
    if (A.length == _length) {
      return A;
    }

    int[] b = new int[_length];

    if (_length > 20) {
      System.arraycopy(A, 0, b, 0, _length);
    } else {
      for (int i = 0; i < _length; i++) {
        b[i] = A[i];
      }
    }

    return b;
  }

  /**
   * {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  public String toString() {
    return toStringEx(0);
  }

  /**
   * Returns a string representing the list
   *
   * @param depth the depth for the string. Each depth unit corresponds
   *              to 2 padding spaces prepended to the string
   * @return the string
   */
  public String toStringEx(int depth) {
    StringBuilder sb = new StringBuilder();

    for (int i = 0; i < _length; i++) {
      aStreamer.writePadding(sb, depth);
      sb.append(A[i]);
      sb.append("\n");
    }

    return sb.toString();
  }

  /**
   * Trims the capacity of this <tt>IntList</tt> instance to be the list's current
   * size. An application can use this operation to minimize the storage of an <tt>IntList</tt>
   * instance.
   */
  public void trimToSize() {
    A = toArray();
  }

  /**
   * {@inheritDoc}
   *
   * @param pos {@inheritDoc}
   *
   * @throws IndexOutOfBoundsException {@inheritDoc}
   */
  void checkRange(int pos) throws IndexOutOfBoundsException {
    if (pos >= _length) {
      throw new IndexOutOfBoundsException(String.valueOf(pos) + ">=" + String.valueOf(_length));
    }

    if (pos < 0) {
      throw new IndexOutOfBoundsException(String.valueOf(pos) + "<0");
    }
  }
}
