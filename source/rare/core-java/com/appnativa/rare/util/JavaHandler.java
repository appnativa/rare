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

import com.appnativa.rare.Platform;
import com.appnativa.rare.exception.ApplicationException;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.util.IntList;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Don DeCoteau
 */
public class JavaHandler {
  private final static Map<Class, Object>     methods             = new HashMap<Class, Object>();
  private final static ThreadLocal<ArrayList> perThreadMethodList = new ThreadLocal<ArrayList>() {
    @Override
    protected synchronized ArrayList initialValue() {
      return new ArrayList();
    }
  };
  private final static ThreadLocal<IntList> perThreadIntList = new ThreadLocal<IntList>() {
    @Override
    protected synchronized IntList initialValue() {
      return new IntList();
    }
  };

  public static Object allocate(Class cls, Object[] params) throws Exception {
    if ((params == null) || (params.length == 0)) {
      return cls.newInstance();
    }

    final int len     = (params == null)
                        ? 0
                        : params.length;
    Class     types[] = new Class[len];
    Object    o;

    for (int i = 0; i < len; i++) {
      o        = params[i];
      types[i] = (o == null)
                 ? null
                 : o.getClass();
    }

    Constructor m = findConstructor(cls, types);

    if (m != null) {
      try {
        types = m.getParameterTypes();

        return m.newInstance(coerceParams(types, params));
      } catch(Throwable e) {
        throw new ApplicationException(e);
      }
    }

    throw new NoSuchMethodException("Constructor for " + cls.getName());
  }

  public static Object setFunctionValue(Object obj, String name, Object... params) {
    final int len     = (params == null)
                        ? 0
                        : params.length;
    Class     types[] = new Class[len];
    Object    o;

    for (int i = 0; i < len; i++) {
      o        = params[i];
      types[i] = (o == null)
                 ? null
                 : o.getClass();
    }

    Method m = findMethod((obj instanceof Class)
                          ? (Class) obj
                          : obj.getClass(), name, types);

    if (m != null) {
      try {
        return m.invoke((obj instanceof Class)
                        ? null
                        : obj, coerceParams(m.getParameterTypes(), params));
      } catch(Throwable e) {
        throw new ApplicationException(name, e);
      }
    }

    throw new NoSuchMethodError(name);
  }

  public static boolean setFunctionValueEx(Object obj, String name, Object... params) {
    final int len     = (params == null)
                        ? 0
                        : params.length;
    Class     types[] = new Class[len];
    Object    o;

    for (int i = 0; i < len; i++) {
      o        = params[i];
      types[i] = (o == null)
                 ? null
                 : o.getClass();
    }

    Method m = findMethod((obj instanceof Class)
                          ? (Class) obj
                          : obj.getClass(), name, types);

    if (m != null) {
      try {
        m.invoke((obj instanceof Class)
                 ? null
                 : obj, coerceParams(m.getParameterTypes(), params));

        return true;
      } catch(Throwable e) {}
    }

    return false;
  }

  public static Object getFunctionValue(Object obj, String name, Object... params) {
    final int len     = (params == null)
                        ? 0
                        : params.length;
    Class     types[] = new Class[len];
    Object    o;

    for (int i = 0; i < len; i++) {
      o        = params[i];
      types[i] = (o == null)
                 ? null
                 : o.getClass();
    }

    Method m = findMethod((obj instanceof Class)
                          ? (Class) obj
                          : obj.getClass(), name, types);

    if (m != null) {
      try {
        return m.invoke((obj instanceof Class)
                        ? null
                        : obj, coerceParams(m.getParameterTypes(), params));
      } catch(Throwable e) {
        throw new ApplicationException(name, e);
      }
    }

    throw new NoSuchMethodError(name);
  }

  public static Object getObject(iWidget context, String name) {
    if (name.equals("widget")) {
      return context;
    }

    if (name.equals("form")) {
      return context.getFormViewer();
    }

    if (name.equals("window")) {
      return context.getAppContext().getWindowViewer();
    }

    if (name.equals("rare")) {
      return Platform.getFunctionHandler();
    }

    if (name.equals("_top")) {
      return context.getAppContext().getWindowManager().getRootViewer();
    }

    Object o = context.getFormViewer().getWidget(name);

    if (o != null) {
      return o;
    }

    o = context.getAppContext().getWindowViewer().getViewer(name);

    if (o != null) {
      return o;
    }

    return context.getScriptHandler().getScriptingVariable(null, name);
  }

  public static Object getStaticValue(Class cls, String name) {
    try {
      Field f = cls.getField(name);

      return f.get(cls);
    } catch(Exception ex) {
      throw new RuntimeException(ex);
    }
  }

  public static Object getStaticValue(String cls, String name) {
    try {
      return getStaticValue(Class.forName(cls), name);
    } catch(ClassNotFoundException ex) {
      throw new RuntimeException(ex);
    }
  }

  static int canInvoke(Class[] types, Class... params) {
    final int len = (params == null)
                    ? 0
                    : params.length;

    if ((types.length == 1) && ((types[0] == Object[].class) || (types[0] == String[].class))) {
      return len;
    }

    if (types.length != len) {
      return -1;
    }

    if (len == 0) {
      return 0;
    }

    int   n = 0;
    Class o, cls;

    for (int i = 0; i < len; i++) {
      o   = params[i];
      cls = types[i];

      if ((cls == Object[].class) || ((cls == String[].class) && (i + 1 == len))) {
        n++;

        continue;
      }

      if ((o == null) || cls.isAssignableFrom(o)) {
        n++;

        continue;
      }

      if (Number.class.isAssignableFrom(o)) {
        if ((cls == int.class) || (cls == long.class) || (cls == double.class) || (cls == float.class)) {
          n++;

          continue;
        }
      }

      if (Boolean.class.isAssignableFrom(o) && (cls == boolean.class)) {
        n++;

        continue;
      }

      if (cls != String.class) {
        return -1;
      }
    }

    return n;
  }

  static int canInvoke(Constructor m, Class... params) {
    return canInvoke(m.getParameterTypes(), params);
  }

  static int canInvoke(Method m, Class... params) {
    return canInvoke(m.getParameterTypes(), params);
  }

  static Object[] coerceParams(Class[] types, Object... params) {
    final int len = (types == null)
                    ? 0
                    : types.length;
    Class     cls;
    Object    o;

    for (int i = 0; i < len; i++) {
      cls = types[i];

      if (i + 1 == len) {
        if (cls == Object[].class) {
          Object[] a = new Object[params.length - i];

          System.arraycopy(params, i, a, 0, params.length - i);

          Object b[] = new Object[len];

          System.arraycopy(params, 0, b, 0, len - 1);
          b[i] = a;

          return b;
        }

        if (cls == String[].class) {
          String[] a   = new String[params.length - i];
          Object   b[] = new Object[len];

          System.arraycopy(params, 0, b, 0, len - 1);
          b[i] = a;

          int n = 0;

          for (; i < params.length; i++) {
            o = params[i];

            if (o == null) {
              continue;
            }

            a[n++] = o.toString();
          }

          return b;
        }
      }

      o = params[i];

      if (o == null) {
        continue;
      }

      if ((cls == String.class) && (o.getClass() != String.class)) {
        params[i] = o.toString();
      } else if ((cls == Integer.class) || (cls == int.class)) {
        if (o instanceof Number) {
          params[i] = ((Number) o).intValue();
        }
      } else if (((cls == Float.class) || (cls == float.class))) {
        if (o instanceof Number) {
          params[i] = ((Number) o).floatValue();
        }
      } else if (((cls == Double.class) || (cls == double.class))) {
        if (o instanceof Number) {
          params[i] = ((Number) o).doubleValue();
        }
      } else if ((cls == Long.class) || (cls == long.class)) {
        if (o instanceof Number) {
          params[i] = ((Number) o).longValue();
        }
      }
    }

    return params;
  }

  static Constructor findConstructor(Class cls, Class... params) {
    Constructor[] a        = cls.getConstructors();
    int           ci       = 0;
    ArrayList     matches  = perThreadMethodList.get();
    IntList       pmatches = perThreadIntList.get();

    matches.clear();
    pmatches._length = 0;

    int plen = (params == null)
               ? 0
               : params.length;

    for (Constructor m : a) {
      if ((ci = canInvoke(m, params)) > -1) {
        if (ci == plen) {
          return m;
        }

        matches.add(m);
        pmatches.add(ci);
      }
    }

    if (pmatches._length == 0) {
      return null;
    }

    final int len = pmatches._length;

    ci = 0;

    int n = 0;

    for (int i = 0; i < len; i++) {
      if (pmatches.A[i] > ci) {
        n  = 0;
        ci = pmatches.A[i];
      }
    }

    return (Constructor) matches.get(n);
  }

  static Method findMethod(Class cls, String name, Class... params) {
    Method[]  a        = getMethods(cls);
    int       ci       = 0;
    ArrayList matches  = perThreadMethodList.get();
    IntList   pmatches = perThreadIntList.get();

    matches.clear();
    pmatches._length = 0;

    int plen = (params == null)
               ? 0
               : params.length;

    for (Method m : a) {
      if (m.getName().equals(name) && (ci = canInvoke(m, params)) > -1) {
        if (ci == plen) {
          return m;
        }

        matches.add(m);
        pmatches.add(ci);
      }
    }

    if (pmatches._length == 0) {
      return null;
    }

    final int len = pmatches._length;

    ci = 0;

    int n = 0;

    for (int i = 0; i < len; i++) {
      if (pmatches.A[i] > ci) {
        n  = 0;
        ci = pmatches.A[i];
      }
    }

    return (Method) matches.get(n);
  }

  static Method[] getMethods(Class cls) {
    Method[] m = (Method[]) methods.get(cls);

    if (m == null) {
      m = cls.getMethods();

      if (RenderableDataItem.class.isAssignableFrom(cls)) {
        methods.put(cls, m);
      }
    }

    return m;
  }
}
