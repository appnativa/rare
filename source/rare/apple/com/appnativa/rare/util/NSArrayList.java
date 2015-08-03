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

package com.appnativa.rare.util;

import java.util.AbstractList;

public class NSArrayList extends AbstractList {
  private static Object nillObject = new Object();
  protected boolean     mutable;
  protected Object      proxy;

  public NSArrayList() {
    this(10);
  }

  public NSArrayList(int initialCapacity) {}

  protected NSArrayList(Object nsarray, boolean mutable) {
    proxy        = nsarray;
    this.mutable = mutable;
  }

  @Override
  public void add(int index, Object element) {
    if (!mutable) {
      super.add(index, element);
    }
  }

  @Override
  public void clear() {
    if (!mutable) {
      super.clear();
    } else {
      clearEx();
    }
  }

  @Override
  public native int indexOf(Object element)
  /*-[
          NSUInteger index=[((NSArray*)proxy_) indexOfObject: element];
          return index==NSNotFound ? -1 : (int)index;
        ]-*/
  ;

  public static NSArrayList listWithBackingArray(Object nsarray, boolean mutable) {
    return new NSArrayList(nsarray, mutable);
  }

  @Override
  public Object remove(int index) {
    return mutable
           ? removeEx(index)
           : super.remove(index);
  }

  @Override
  public boolean remove(Object o) {
    if (!mutable) {
      return super.remove(o);
    }

    int n = indexOf(o);

    if (n == -1) {
      return false;
    }

    remove(n);

    return true;
  }

  @Override
  public native int size()
  /*-[
    return (int)((NSArray*)proxy_).count;
  ]-*/
  ;

  @Override
  public Object set(int index, Object element) {
    return mutable
           ? setEx(index, element)
           : super.set(index, element);
  }

  @Override
  public native Object get(int index)
  /*-[
   id o=[((NSArray*)proxy_) objectAtIndex: index];
   return o==[RARENSArrayList nillObject] ? nil : o;
  ]-*/
  ;

  public Object getProxy() {
    return proxy;
  }

  private native void clearEx()
  /*-[
    if(((NSMutableArray*)proxy_).count>0) {
      [((NSMutableArray*)proxy_) removeAllObjects];
      modCount_++;
    }
  ]-*/
  ;

  private native Object removeEx(int index)
  /*-[
    id o=[((NSArray*)proxy_) objectAtIndex: index];
    [((NSMutableArray*)proxy_) removeObjectAtIndex: index];
    modCount_++;
    return o==[RARENSArrayList nillObject] ? nil : o;
  ]-*/
  ;

  private native Object setEx(int index, Object element)
  /*-[
    id o=[((NSArray*)proxy_) objectAtIndex: index];
    if(!element) {
        element=[RARENSArrayList nillObject];
    }
    [((NSMutableArray*)proxy_) replaceObjectAtIndex: index withObject:element];
    modCount_++;
    return o==[RARENSArrayList nillObject] ? nil : o;
  ]-*/
  ;

  private native void addEx(int index, Object element)
  /*-[
    if(!element) {
        element=[RARENSArrayList nillObject];
    }
    [((NSMutableArray*)proxy_) insertObject:element atIndex:index];
    modCount_++;
  ]-*/
  ;
}
