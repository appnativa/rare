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

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

public class NSDictionaryMap extends AbstractMap {
  protected Object proxy;

  static enum IteratorType { KEY, VALUE, ENTRY }

  ;
  public NSDictionaryMap() {
    proxy = createDictionary(10);
  }

  public NSDictionaryMap(int initialCapacity) {
    proxy = createDictionary(initialCapacity);
  }

  public NSDictionaryMap(NSDictionaryMap map) {
    proxy = createDictionary(map.proxy);
  }

  public NSDictionaryMap(Map map) {
    proxy = createDictionary(map.size());
    putAll(map);
  }

  @Override
  public native Object get(Object key)
  /*-[
    NSMutableDictionary* d=(NSMutableDictionary*)proxy_;
    return [d objectForKey: key];
  ]-*/
  ;

  protected NSDictionaryMap(Object nsdictionary) {
    this.proxy = nsdictionary;
  }

  public static NSDictionaryMap mapUsingBackingDictionary(Object nsdictionary) {
    return new NSDictionaryMap(nsdictionary);
  }

  public Object getProxy() {
    return proxy;
  }

  @Override
  public Set entrySet() {
    return new DictionarySet(this, IteratorType.ENTRY);
  }

  @Override
  public Set keySet() {
    return new DictionarySet(this, IteratorType.KEY);
  }

  @Override
  public Collection values() {
    return new DictionarySet(this, IteratorType.VALUE);
  }

  @Override
  public native Object put(Object key, Object value)
  /*-[
    NSMutableDictionary* d=(NSMutableDictionary*)proxy_;
    NSObject* o=[d objectForKey: key];
    [d setObject:value forKey: key];
    return o;
  ]-*/
  ;

  @Override
  public native Object remove(Object key)
  /*-[
    NSMutableDictionary* d=(NSMutableDictionary*)proxy_;
    NSObject* o=[d objectForKey: key];
    [d removeObjectForKey: key];
    return o;
  ]-*/
  ;

  private static native Object createEnumerator(Object proxy)
  /*-[
    NSDictionary* d=(NSDictionary*)proxy;
    return [d keyEnumerator];
  ]-*/
  ;

  private static native Object createDictionary(Object proxy)
  /*-[
    NSDictionary* d=(NSDictionary*)proxy;
    int size=(int)d.count;
    if(size<1) {
        size=10;
    }
    NSMutableDictionary* nd=[NSMutableDictionary dictionaryWithCapacity: size];
    [nd addEntriesFromDictionary: d];
    return nd;
  ]-*/
  ;

  private static native Object createDictionary(int size)
  /*-[
    if(size<1) {
        size=10;
    }
    return [NSMutableDictionary dictionaryWithCapacity: size];
  ]-*/
  ;

  public static class DictionaryEntry implements Entry {
    Object          key;
    NSDictionaryMap map;

    public DictionaryEntry(NSDictionaryMap map, Object key) {
      this.map = map;
      this.key = key;
    }

    @Override
    public Object setValue(Object value) {
      return map.put(key, value);
    }

    @Override
    public Object getKey() {
      return key;
    }

    @Override
    public Object getValue() {
      return map.get(key);
    }
  }


  public static class DictionarySet extends AbstractSet {
    NSDictionaryMap map;
    IteratorType    type;

    public DictionarySet(NSDictionaryMap map, IteratorType type) {
      this.map  = map;
      this.type = type;
    }

    @Override
    public Iterator iterator() {
      return new DictionaryIterator(map, type);
    }

    @Override
    public int size() {
      return map.size();
    }
  }


  static class DictionaryIterator implements Iterator {
    Object          current;
    Object          enumerator;
    NSDictionaryMap map;
    Object          next;
    IteratorType    type;

    public DictionaryIterator(NSDictionaryMap map, IteratorType type) {
      this.map        = map;
      this.enumerator = NSDictionaryMap.createEnumerator(map.proxy);
      this.type       = type;
      next            = getNext();
    }

    @Override
    public Object next() {
      if (next == null) {
        throw new NoSuchElementException();
      }

      current = next;
      next    = getNext();

      switch(type) {
        case VALUE :
          return map.get(current);

        case ENTRY :
          return new DictionaryEntry(map, current);

        default :
          return current;
      }
    }

    @Override
    public void remove() {
      if (current == null) {
        throw new NoSuchElementException();
      }

      map.remove(current);
    }

    @Override
    public boolean hasNext() {
      return next != null;
    }

    private native Object getNext()
    /*-[
      return [((NSEnumerator*)enumerator_) nextObject];
    ]-*/
    ;
  }
}
