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

package com.appnativa.rare.scripting;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

import javax.script.Bindings;
/*-[
 #import "RAREJSEngine.h"
 ]-*/
import javax.script.ScriptEngine;

public class JSCocoaBindings extends AbstractMap<String, Object> implements Bindings {
  BindingsIterator iterator;
  Object           proxy;
  GlobalVariables  engineVars = new GlobalVariables();

  public JSCocoaBindings(Object engineProxy) {
    proxy    = engineProxy;
    iterator = new BindingsIterator(proxy, null);
  }

  @Override
  public void clear() {
    super.clear();
    engineVars.clear();
  }

  @Override
  public boolean containsKey(Object key) {
    GlobalVariables gv = engineVars;

    if (key == DynamicBindings.WIDGET) {
      return gv.widget != null;
    }

    if (key == DynamicBindings.WINDOW) {
      return gv.window != null;
    }

    if (key == DynamicBindings.FORM) {
      return gv.form != null;
    }

    if (key == ScriptEngine.FILENAME) {
      return gv.fileName != null;
    }

    if (key.equals(DynamicBindings.WIDGET)) {
      return gv.widget != null;
    }

    if (key.equals(DynamicBindings.WINDOW)) {
      return gv.window != null;
    }

    if (key.equals(DynamicBindings.FORM)) {
      return gv.form != null;
    }

    if (key.equals(ScriptEngine.FILENAME)) {
      return gv.fileName != null;
    }

    return iterator.containsKey((String) key);
  }

  @Override
  public Set<java.util.Map.Entry<String, Object>> entrySet() {
    return new BindingsEntrySet(proxy);
  }

  @Override
  public Object put(String key, Object value) {
    GlobalVariables gv = engineVars;
    Object          ov;

    if (key == DynamicBindings.WIDGET) {
      ov        = gv.widget;
      gv.widget = value;

      if (ov != value) {
        ov = iterator.setValue(key, value);
      }
    } else if (key == DynamicBindings.WINDOW) {
      ov        = gv.window;
      gv.window = value;

      if (ov != value) {
        ov = iterator.setValue(key, value);
      }
    } else if (key == DynamicBindings.FORM) {
      ov      = gv.form;
      gv.form = value;

      if (ov != value) {
        ov = iterator.setValue(key, value);
      }
    } else if (key == ScriptEngine.FILENAME) {
      ov          = gv.fileName;
      gv.fileName = value;
    } else if (key.equals(DynamicBindings.WIDGET)) {
      ov        = gv.widget;
      gv.widget = value;

      if (ov != value) {
        ov = iterator.setValue(key, value);
      }
    } else if (key.equals(DynamicBindings.WINDOW)) {
      ov        = gv.window;
      gv.window = value;

      if (ov != value) {
        ov = iterator.setValue(key, value);
      }
    } else if (key.equals(DynamicBindings.FORM)) {
      ov      = gv.form;
      gv.form = value;

      if (ov != value) {
        ov = iterator.setValue(key, value);
      }
    } else if (key.equals(ScriptEngine.FILENAME)) {
      ov          = gv.fileName;
      gv.fileName = value;
    } else {
      ov = iterator.setValue(key, value);
    }

    return ov;
  }

  @Override
  public Object remove(Object key) {
    GlobalVariables gv = engineVars;
    Object          ov;

    if (key == DynamicBindings.WIDGET) {
      ov        = gv.widget;
      gv.widget = null;

      if (ov != null) {
        iterator.remove((String) key);
      }
    } else if (key == DynamicBindings.WINDOW) {
      ov        = gv.window;
      gv.window = null;

      if (ov != null) {
        iterator.remove((String) key);
      }
    } else if (key == DynamicBindings.FORM) {
      ov      = gv.form;
      gv.form = null;

      if (ov != null) {
        iterator.remove((String) key);
      }
    } else if (key == ScriptEngine.FILENAME) {
      ov          = gv.fileName;
      gv.fileName = null;
    } else if (key.equals(DynamicBindings.WIDGET)) {
      ov        = gv.widget;
      gv.widget = null;

      if (ov != null) {
        iterator.remove((String) key);
      }
    } else if (key.equals(DynamicBindings.WINDOW)) {
      ov        = gv.window;
      gv.window = null;

      if (ov != null) {
        iterator.remove((String) key);
      }
    } else if (key.equals(DynamicBindings.FORM)) {
      ov      = gv.form;
      gv.form = null;

      if (ov != null) {
        iterator.remove((String) key);
      }
    } else if (key.equals(ScriptEngine.FILENAME)) {
      ov          = gv.fileName;
      gv.fileName = null;
    } else {
      ov = iterator.remove((String) key);
    }

    return ov;
  }

  public void setEngine(JavaScriptEngine e) {
    this.proxy = e.proxy;
  }

  @Override
  public Object get(Object key) {
    GlobalVariables gv = engineVars;

    if (key == DynamicBindings.WIDGET) {
      return gv.widget;
    }

    if (key == DynamicBindings.WINDOW) {
      return gv.window;
    }

    if (key == DynamicBindings.FORM) {
      return gv.form;
    }

    if (key == ScriptEngine.FILENAME) {
      return gv.fileName;
    }

    if (key.equals(DynamicBindings.WIDGET)) {
      return gv.widget;
    }

    if (key.equals(DynamicBindings.WINDOW)) {
      return gv.window;
    }

    if (key.equals(DynamicBindings.FORM)) {
      return gv.form;
    }

    if (key.equals(ScriptEngine.FILENAME)) {
      return gv.fileName;
    }

    return iterator.getValue((String) key);
  }

  static class BindingsEntrySet extends AbstractSet<java.util.Map.Entry<String, Object>> {
    Object           engine;
    BindingsIterator iterator;

    public BindingsEntrySet(Object engine) {
      this.engine = engine;
    }

    @Override
    public Iterator<java.util.Map.Entry<String, Object>> iterator() {
      return new BindingsIterator(engine, (iterator == null)
              ? null
              : iterator.namesArray);
    }

    @Override
    public int size() {
      if (iterator == null) {
        iterator = new BindingsIterator(engine, null);
      }

      return iterator.size();
    }
  }


  static class BindingsIterator implements Iterator<Entry<String, Object>> {
    int     count;
    boolean currentDeleted;
    String  currentName;
    Object  engine;
    Object  namesArray;
    int     pos;

    public BindingsIterator(Object engine, Object namesArray) {
      this.engine     = engine;
      this.namesArray = namesArray;
    }

    @Override
    public Entry<String, Object> next() {
      if (pos >= count) {
        throw new NoSuchElementException();
      }

      currentName    = getName(pos++);
      currentDeleted = false;

      return new JSEntry(currentName, this);
    }

    @Override
    public void remove() {
      if ((pos >= count) || (currentName == null)) {
        throw new NoSuchElementException();
      }

      remove(currentName);
      currentDeleted = true;
    }

    public void reset() {
      namesArray     = null;
      pos            = 0;
      count          = 0;
      currentName    = null;
      currentDeleted = false;
    }

    public int size() {
      if (namesArray == null) {
        loadNames();
      }

      return count;
    }

    @Override
    public boolean hasNext() {
      if (namesArray == null) {
        loadNames();
      }

      return pos < count;
    }

    native boolean containsKey(String name)
    /*-[
        return [((RAREJSEngine*)engine_) hasProperty: name];
    ]-*/
    ;

    protected native void loadNames()
    /*-[
        namesArray_=[((RAREJSEngine*)engine_) getPropertyNames];
        count_=(int)[((NSArray*)namesArray_) count];
    ]-*/
    ;

    protected native Object remove(String name)
    /*-[
        [((RAREJSEngine*)engine_) deleteValue: name];
        return nil;
    ]-*/
    ;

    protected native Object setValue(String name, Object value)
    /*-[
        [((RAREJSEngine*)engine_) setValue: value withName: name];
        return nil;
    ]-*/
    ;

    protected native String getName(int pos)
    /*-[
        return (NSString*)[((NSArray*)namesArray_) objectAtIndex: pos];
    ]-*/
    ;

    native Object getValue(String name)
    /*-[
        return (NSObject*)[((RAREJSEngine*)engine_) getValue: name];
    ]-*/
    ;
  }


  static class JSEntry implements Entry<String, Object> {
    boolean          gotValue;
    BindingsIterator iterator;
    String           key;
    Object           value;

    public JSEntry(String key, BindingsIterator iterator) {
      this.key      = key;
      this.iterator = iterator;
    }

    @Override
    public Object setValue(Object value) {
      Object v = getValue();

      iterator.setValue(key, value);

      return v;
    }

    @Override
    public String getKey() {
      return key;
    }

    @Override
    public Object getValue() {
      if (!gotValue) {
        gotValue = true;
        value    = iterator.getValue(key);
      }

      return value;
    }
  }


  static class GlobalVariables {
    Object widget;
    Object form;
    Object window;
    Object fileName;

    void clear() {
      widget   = null;
      form     = null;
      window   = null;
      fileName = null;
    }
  }
}
