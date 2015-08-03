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

import com.appnativa.rare.Platform;
import com.appnativa.rare.viewer.WindowViewer;
import com.appnativa.rare.viewer.iContainer;
import com.appnativa.rare.viewer.iFormViewer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.script.Bindings;

/**
 * A binding that provides dynamic name lookup of rare widget names
 *
 * @author Don DeCoteau
 */
public class DynamicBindings implements Bindings {
  public static final String FORM            = "form";
  public static final String TOP             = "_top";
  public static final String WIDGET          = "widget";
  public static final String WINDOW          = "window";
  final static HashMap       generatedValues = new HashMap(10);

  static {
    generatedValues.put("document", "document");
    generatedValues.put("navigator", "navigator");
  }

  final Bindings bindings;
  final Bindings ctxBindings;

  public DynamicBindings(Bindings bindings) {
    this.bindings    = bindings;
    this.ctxBindings = bindings;
  }

  public DynamicBindings(Bindings bindings, Bindings ctxBindings) {
    this.bindings    = bindings;
    this.ctxBindings = ctxBindings;
  }

  @Override
  public void clear() {
    bindings.clear();
  }

  @Override
  public boolean containsKey(Object key) {
    return bindings.containsKey(key)
           ? true
           : getOther(key) != null;
  }

  @Override
  public boolean containsValue(Object value) {
    return bindings.containsValue(value);
  }

  @Override
  public Set<Entry<String, Object>> entrySet() {
    return bindings.entrySet();
  }

  @Override
  public Set<String> keySet() {
    return bindings.keySet();
  }

  @Override
  public Object put(String name, Object value) {
    return bindings.put(name, value);
  }

  @Override
  public void putAll(Map<? extends String, ? extends Object> toMerge) {
    bindings.putAll(toMerge);
  }

  @Override
  public Object remove(Object key) {
    return bindings.remove(key);
  }

  @Override
  public int size() {
    return bindings.size();
  }

  @Override
  public Collection<Object> values() {
    return bindings.values();
  }

  @Override
  public Object get(Object key) {
    final Object o = bindings.get(key);

    return (o == null)
           ? getOther(key)
           : o;
  }

  @Override
  public boolean isEmpty() {
    return bindings.isEmpty();
  }

  protected final Object getOther(Object key) {
    if ((key == javax.script.ScriptEngine.FILENAME) || (key.equals("Function")) || (key.equals("Object"))
        || (key.equals(WIDGET)) || (key.equals(FORM)) || (key.equals(WINDOW))) {
      return null;
    }

    WindowViewer win = null;
    Object       o   = null;

    try {
      String s = (String) key;
      Object w = Platform.isUIThread()
                 ? ctxBindings.get(WIDGET)
                 : null;

      if (w instanceof iContainer) {
        o = ((iContainer) w).getWidget(s);
      }

      if (o == null) {
        if (w instanceof WindowViewer) {
          win = (WindowViewer) w;
        } else if (w != null) {
          Object f = Platform.isUIThread()
                     ? ctxBindings.get(FORM)
                     : null;

          if ((f != w) && (f instanceof iFormViewer)) {
            o = ((iFormViewer) f).getWidget(s);
          }
        }

        if (o == null) {
          if (win == null) {
            win = Platform.isUIThread()
                  ? (WindowViewer) ctxBindings.get(WINDOW)
                  : Platform.getWindowViewer();
          }

          if (win != null) {
            if (win != w) {
              o = win.getViewer(s);
            }

            if (o == null) {
              key = generatedValues.get(s);

              if (key != null) {
                if (key.equals("document")) {
                  o = win;
                } else if (key.equals("navigator")) {
                  o = win.getNavigator();
                }
              }
            }
          }
        }
      }
    } catch(Throwable ignore) {
      Platform.ignoreException(null, ignore);
    }

    return o;
  }
}
