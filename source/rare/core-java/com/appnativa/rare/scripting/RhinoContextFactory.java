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

import com.appnativa.rare.ErrorInformation;
import com.appnativa.rare.Platform;
import com.appnativa.rare.exception.ApplicationException;
import com.appnativa.rare.viewer.WindowViewer;
import com.appnativa.rare.viewer.iContainer;
import com.appnativa.rare.viewer.iViewer;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.util.Helper;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.FunctionNode;
import org.mozilla.javascript.NativeFunction;
import org.mozilla.javascript.NativeJavaObject;
import org.mozilla.javascript.RhinoException;
import org.mozilla.javascript.ScriptRuntime;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.WrapFactory;
import org.mozilla.javascript.WrappedException;
import org.mozilla.javascript.Wrapper;

import java.lang.reflect.Method;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Context class factory for Rhino. It sets up the class loader and disables
 * byte code generation when being run via WebStart
 *
 * @author Don DeCoteau
 */
public class RhinoContextFactory extends ContextFactory {
  private static Object[]            no_args = new Object[0];
  private static String              customPropertyPrefix;
  private static boolean             initialized;
  private static RhinoContextFactory instance;
  boolean                            strict              = true;
  private boolean                    wrapCollections     = true;
  private boolean                    optimizationEnabled = false;
  private boolean                    locationInErrors    = true;

  /**
   * Constructs a new instance
   *
   * @param cl
   *          the class loader to use to load application classes
   */
  public RhinoContextFactory(ClassLoader cl) {
    super();

    String s = Platform.getProperty("rare.scriptingStrict", Platform.getProperty("jnlp.rare.scriptingStrict", null));

    if ("false".equalsIgnoreCase(s)) {
      strict = false;
    }

    s = Platform.getProperty("rare.wrapCollections", Platform.getProperty("jnlp.rare.wrapCollections", null));

    if ("false".equalsIgnoreCase(s)) {
      wrapCollections = false;
    }

    s = Platform.getProperty("rare.locationInErrors", Platform.getProperty("jnlp.rare.locationInErrors", null));

    if ("false".equalsIgnoreCase(s)) {
      locationInErrors = false;
    }

    s = Platform.getProperty("rare.optimizationEnabled", Platform.getProperty("jnlp.rare.optimizationEnabled", null));

    if ("false".equalsIgnoreCase(s)) {
      optimizationEnabled = false;
    }

    customPropertyPrefix = Platform.getAppContext().getCustomPropertyPrefix();
  }

  public static Object callFunction(Object o, aScriptManager sm, Scriptable scope, Object _this) {
    NativeFunction f = (NativeFunction) o;

    try {
      Context cx      = Context.enter();
      Object  r       = null;
      Object  thisObj = Context.javaToJS(_this, scope);

      if (f.getArity() == 0) {
        r = f.call(cx, scope, (Scriptable) thisObj, no_args);
      } else {
        Object e = Context.javaToJS(((WindowViewer) sm.getWindowViewer()).getEvent(), scope);

        r = f.call(cx, scope, (Scriptable) thisObj, new Object[] { e });
      }

      return (r == null)
             ? null
             : Context.jsToJava(r, ScriptRuntime.ObjectClass);
    } catch(Exception e) {
      throw Context.throwAsScriptRuntimeEx(e);
    } finally {
      Context.exit();
    }
  }

  public static void initialize(ClassLoader cl) {
    if (!initialized) {
      initialized = true;

      RhinoContextFactory r = new RhinoContextFactory(cl);

      ContextFactory.initGlobal(r);
      instance = r;
    }
  }

  public static void loadMozillaDebugger() {
    try {
      Class  cls = Class.forName("org.mozilla.javascript.tools.debugger.Main");
      Method m   = cls.getMethod("mainEmbedded", String.class);

      m.invoke(null, "Mozilla Debugger");
    } catch(Exception e) {
      e.printStackTrace();
    }
  }

  public static Object unwrap(Object o) {
    if (o instanceof Wrapper) {
      return ((Wrapper) o).unwrap();
    }

    return o;
  }

  public static Object wrapCompiledFunction(final org.mozilla.javascript.Script script, aScriptManager sm,
          final Scriptable scope) {
    try {
      Context cx = Context.enter();

      ScriptRuntime.initFunction(cx, scope, (NativeFunction) script, FunctionNode.FUNCTION_STATEMENT, false);

      return script;
    } catch(Exception e) {
      throw Context.throwAsScriptRuntimeEx(e);
    } finally {
      Context.exit();
    }
  }

  /**
   * Sets whether optimizations are enabled
   *
   * @param enabled
   *          true to enable; false otherwise
   */
  public static void setOptimizationEnabled(boolean enabled) {
    if (instance != null) {
      instance.optimizationEnabled = enabled;
    }
  }

  public static void setStrictScriptingMode(boolean strict) {
    if (instance != null) {
      instance.strict = strict;
    }
  }

  public static ErrorInformation getErrorInformation(Object error) {
    if (error instanceof ApplicationException) {
      ApplicationException ae = (ApplicationException) error;

      if (ae.getCauseEx() != null) {
        error = ae.getCauseEx();
      }
    }

    if (error instanceof org.mozilla.javascript.EvaluatorException) {
      Throwable e = ((org.mozilla.javascript.EvaluatorException) error).getCause();

      if (e != null) {
        error = e;
      }
    }

    if (error instanceof RhinoException) {
      return handleRhinoException(error, (RhinoException) error);
    }

    Throwable e = null;

    if (error instanceof Scriptable) {
      e = getJavaException((Scriptable) error, false);

      if (e instanceof RhinoException) {
        return handleRhinoException(error, e);
      }

      if (e != null) {
        return new ErrorInformation(error, "Scripting Error", ApplicationException.getMessageEx(e),
                                    ApplicationException.getStackTrace(e));
      }

      e = getJavaException((Scriptable) error, true);
    } else if (error instanceof Throwable) {
      e = (Throwable) error;
    }

    if (e != null) {
      return new ErrorInformation(error, "Application Exception", ApplicationException.getMessageEx(e),
                                  ApplicationException.getStackTrace(e));
    }

    return new ErrorInformation(error, "Error", (error == null)
            ? ""
            : error.toString());
  }

  public static RhinoContextFactory getInstance() {
    return instance;
  }

  public static boolean getStrictScriptingMode() {
    return (instance == null)
           ? false
           : instance.strict;
  }

  public static boolean isFunctionObject(Object o) {
    return o instanceof NativeFunction;
  }

  /**
   * Gets whether optimizations are enabled
   *
   * @return true if enabled; false otherwise
   */
  public static boolean isOptimizationEnabled() {
    return (instance == null)
           ? true
           : instance.optimizationEnabled;
  }

  @Override
  protected Context makeContext() {
    Context cx = super.makeContext();

    if (!optimizationEnabled) {    // disable byte code generation, which fails due
      // to lack of permisions
      cx.setOptimizationLevel(-1);
      Logger.getAnonymousLogger().log(Level.FINE, "Turning off JavaScript optimization");
    } else {
      cx.setOptimizationLevel(9);
    }

    if (wrapCollections) {
      cx.setWrapFactory(new EnhancedWrapFactory());
    }

    return cx;
  }

  @Override
  protected boolean hasFeature(Context cx, int featureIndex) {
    switch(featureIndex) {
      case Context.FEATURE_STRICT_MODE :
        return strict;

      case Context.FEATURE_WARNING_AS_ERROR :
        return strict;

      case Context.FEATURE_LOCATION_INFORMATION_IN_ERROR :
        return locationInErrors;
    }

    return super.hasFeature(cx, featureIndex);
  }

  private static ErrorInformation handleRhinoException(Object error, Throwable e) {
    String ss = null;

    if (e instanceof RhinoException) {
      ss = ((RhinoException) e).getScriptStackTrace();
    }

    if (e instanceof WrappedException) {
      e = ((WrappedException) e).getWrappedException();
    }

    ErrorInformation ei = new ErrorInformation(error, "Scripting Error", ApplicationException.getMessageEx(e),
                            ApplicationException.getStackTrace(e), ss);

    if (e instanceof Exception) {
      ei.setJavaException((Exception) e);
    }

    return ei;
  }

  private static Throwable getJavaException(Scriptable error, boolean java) {
    try {
      String key   = java
                     ? "javaException"
                     : "rhinoException";
      Object value = ScriptableObject.getProperty(error, key);

      if (value == Scriptable.NOT_FOUND) {
        return null;
      }

      if (value instanceof NativeJavaObject) {
        value = ((NativeJavaObject) value).unwrap();
      }

      return (Throwable) value;
    } catch(Exception e) {
      return null;
    }
  }

  static class EnhancedWrapFactory extends WrapFactory {
    public EnhancedWrapFactory() {
      // setJavaPrimitiveWrap(false);
    }

    @Override
    public Scriptable wrapAsJavaObject(Context cx, Scriptable scope, Object javaObject, Class staticType) {
      if (javaObject instanceof Map) {
        return new NativeMapAdapter(scope, javaObject, staticType);
      } else if (javaObject instanceof List) {
        Object     o;
        Scriptable s;
        iWidget    w;

        if (javaObject instanceof iViewer) {
          w = (iWidget) javaObject;

          WidgetContext wc = w.getScriptingContext();

          o = wc.languageObject;

          if (o instanceof NativeViewerAdapter) {
            s = (Scriptable) o;
          } else {
            s                 = new NativeViewerAdapter(scope, javaObject, staticType);
            wc.languageObject = s;
          }

          return s;
        }

        if (javaObject instanceof iWidget) {
          w = (iWidget) javaObject;

          WidgetContext wc = w.getScriptingContext();

          o = wc.languageObject;

          if (o instanceof NativeWidgetAdapter) {
            s = (Scriptable) o;
          } else {
            s                 = new NativeWidgetAdapter(scope, javaObject, staticType);
            wc.languageObject = s;
          }

          return s;
        }

        return new NativeListAdapter(scope, javaObject, staticType);
      }

      return super.wrapAsJavaObject(cx, scope, javaObject, staticType);
    }
  }


  static class NativeListAdapter extends NativeJavaObject {
    public NativeListAdapter(Scriptable scope, Object javaObject, Class staticType) {
      super(scope, javaObject, staticType);
    }

    @Override
    public void delete(int index) {
      try {
        getList().remove(index);
      } catch(RuntimeException e) {
        throw Context.throwAsScriptRuntimeEx(e);
      }
    }

    @Override
    public void put(int index, Scriptable start, Object value) {
      try {
        List list = (List) javaObject;
        int  len  = list.size();

        while(index >= len) {
          list.add(null);
          len++;
        }

        list.set(index, Context.jsToJava(value, ScriptRuntime.ObjectClass));
      } catch(RuntimeException e) {
        Context.throwAsScriptRuntimeEx(e);
      }
    }

    @Override
    public void put(String name, Scriptable start, Object value) {
      if ("length".equals(name)) {
        try {
          int  l    = ((Number) value).intValue();
          List list = (List) javaObject;
          int  len  = list.size();

          while(l > len) {
            list.add(null);
            len++;
          }
        } catch(RuntimeException e) {
          Context.throwAsScriptRuntimeEx(e);
        }
      }

      super.put(name, start, value);
    }

    @Override
    public String toString() {
      return javaObject.toString();
    }

    @Override
    public Object get(int index, Scriptable start) {
      Context cx = Context.getCurrentContext();

      try {
        return cx.getWrapFactory().wrap(cx, this, getList().get(index), null);
      } catch(RuntimeException e) {
        throw Context.throwAsScriptRuntimeEx(e);
      }
    }

    @Override
    public Object get(String name, Scriptable start) {
      if ("length".equals(name)) {
        Context cx = Context.getCurrentContext();

        return cx.getWrapFactory().wrap(cx, this, getList().size(), int.class);
      }

      return super.get(name, start);
    }

    @Override
    public String getClassName() {
      return "NativeListAdapter";
    }

    @Override
    public Object[] getIds() {
      int       size = getList().size();
      Integer[] ids  = new Integer[size];

      for (int i = 0; i < size; ++i) {
        ids[i] = new Integer(i);
      }

      return ids;
    }

    @Override
    public boolean has(int index, Scriptable start) {
      return (index >= 0) && (index < getList().size());
    }

    @Override
    public boolean has(String name, Scriptable start) {
      if ("length".equals(name)) {
        return true;
      }

      return super.has(name, start);
    }

    private List getList() {
      return (List) javaObject;
    }
  }


  static class NativeMapAdapter extends NativeJavaObject {
    public NativeMapAdapter(Scriptable scope, Object javaObject, Class staticType) {
      super(scope, javaObject, staticType);
    }

    @Override
    public void delete(String name) {
      try {
        getMap().remove(name);
      } catch(RuntimeException e) {
        Context.throwAsScriptRuntimeEx(e);
      }
    }

    @Override
    public void put(int index, Scriptable start, Object value) {
      try {
        getMap().put(index, Context.jsToJava(value, ScriptRuntime.ObjectClass));
      } catch(RuntimeException e) {
        Context.throwAsScriptRuntimeEx(e);
      }
    }

    @Override
    public void put(String name, Scriptable start, Object value) {
      try {
        getMap().put(name, Context.jsToJava(value, ScriptRuntime.ObjectClass));
      } catch(RuntimeException e) {
        Context.throwAsScriptRuntimeEx(e);
      }
    }

    @Override
    public String toString() {
      return javaObject.toString();
    }

    @Override
    public Object get(int index, Scriptable start) {
      Context cx = Context.getCurrentContext();

      try {
        return cx.getWrapFactory().wrap(cx, this, getMap().get(index), null);
      } catch(RuntimeException e) {
        throw Context.throwAsScriptRuntimeEx(e);
      }
    }

    @Override
    public Object get(String name, Scriptable start) {
      Object value = super.get(name, start);

      if (value != Scriptable.NOT_FOUND) {
        return value;
      }

      value = getMap().get(name);

      Context cx = Context.getCurrentContext();

      return cx.getWrapFactory().wrap(cx, this, value, null);
    }

    @Override
    public String getClassName() {
      return "NativeMapAdapter";
    }

    @Override
    public Object[] getIds() {
      return getMap().keySet().toArray();
    }

    @Override
    public boolean has(int index, Scriptable start) {
      return getMap().containsKey(index);
    }

    @Override
    public boolean has(String name, Scriptable start) {
      return getMap().containsKey(name) || super.has(name, start);
    }

    private Map getMap() {
      return (Map) javaObject;
    }
  }


  static class NativeViewerAdapter extends NativeWidgetAdapter {
    public NativeViewerAdapter(Scriptable scope, Object javaObject, Class staticType) {
      super(scope, javaObject, staticType);
    }

    @Override
    public Object get(int index, Scriptable start) {
      Context cx = Context.getCurrentContext();

      try {
        if (getViewer() instanceof List) {
          return cx.getWrapFactory().wrap(cx, this, ((List) getViewer()).get(index), null);
        }

        return super.get(index, start);
      } catch(RuntimeException e) {
        throw Context.throwAsScriptRuntimeEx(e);
      }
    }

    @Override
    public Object get(String name, Scriptable start) {
      Object value = super.get(name, start);

      if (value != Scriptable.NOT_FOUND) {
        return value;
      }

      value = getViewer().getNamedItem(name);

      if ((value == null) && "length".equals(name) && (javaObject instanceof List)) {
        value = ((List) javaObject).size();

        Context cx = Context.getCurrentContext();

        return cx.getWrapFactory().wrap(cx, this, value, int.class);
      }

      if (value == null) {
        return Scriptable.NOT_FOUND;
      }

      Context cx = Context.getCurrentContext();

      return cx.getWrapFactory().wrap(cx, this, value, null);
    }

    @Override
    public String getClassName() {
      return "NativeViewerAdapter";
    }

    @Override
    public Object[] getIds() {
      if (javaObject instanceof iContainer) {
        return ((iContainer) javaObject).getWidgetNames().toArray();
      }

      return getViewer().getNames().toArray();
    }

    @Override
    public boolean has(int index, Scriptable start) {
      return (index >= 0) && (getViewer() instanceof List) && (index < ((List) getViewer()).size());
    }

    @Override
    public boolean has(String name, Scriptable start) {
      if ((getViewer().getNamedItem(name) != null) || super.has(name, start)) {
        return true;
      }

      if (javaObject instanceof iContainer) {
        return ((iContainer) javaObject).getWidget(name) != null;
      }

      return false;
    }

    private iViewer getViewer() {
      return (iViewer) javaObject;
    }
  }


  static class NativeWidgetAdapter extends NativeJavaObject {
    public NativeWidgetAdapter(Scriptable scope, Object javaObject, Class staticType) {
      super(scope, javaObject, staticType);
    }

    @Override
    public void delete(String name) {
      iWidget w = getMap();

      w.removeEventHandler(name, null);
    }

    @Override
    public void put(String name, Scriptable start, Object value) {
      iWidget w = getMap();

      if (name.startsWith("on")) {
        w.setEventHandler(name, value, false);

        return;
      }

      if (w.hasCustomProperty(name)) {
        w.setAttribute(name, value);

        return;
      }

      try {
        super.put(name, start, value);
      } catch(RuntimeException e) {
        if ((customPropertyPrefix != null) &&!name.startsWith(customPropertyPrefix)) {
          String s = Platform.getResourceAsString("Rare.runtime.text.customPropertyRestriction");

          throw new ApplicationException(Helper.expandString(s, name, customPropertyPrefix));
        }

        if (super.has(name, start)) {
          throw Context.throwAsScriptRuntimeEx(e);
        }

        w.setAttribute(name, value);
      }
    }

    @Override
    public String toString() {
      return javaObject.toString();
    }

    @Override
    public Object get(int index, Scriptable start) {
      Context cx = Context.getCurrentContext();

      try {
        if (getMap() instanceof List) {
          return cx.getWrapFactory().wrap(cx, this, ((List) getMap()).get(index), null);
        }

        return super.get(index, start);
      } catch(RuntimeException e) {
        throw Context.throwAsScriptRuntimeEx(e);
      }
    }

    @Override
    public Object get(String name, Scriptable start) {
      Object value = super.get(name, start);

      if (value != Scriptable.NOT_FOUND) {
        return value;
      }

      value = null;

      iWidget w = getMap();

      if (w instanceof iContainer) {
        value = ((iContainer) w).getWidget(name);
      }

      if (value == null) {
        value = w.getEventHandler(name);
      }

      if (value == null) {
        value = w.getAttribute(name);
      }

      if ((value == null) &&!w.hasCustomProperty(name)) {
        return Scriptable.NOT_FOUND;
      }

      Context cx = Context.getCurrentContext();

      return cx.getWrapFactory().wrap(cx, this, value, null);
    }

    @Override
    public String getClassName() {
      return "NativeWidgetAdapter";
    }

    @Override
    public boolean has(int index, Scriptable start) {
      return (index >= 0) && (getMap() instanceof List) && (index < ((List) getMap()).size());
    }

    @Override
    public boolean has(String name, Scriptable start) {
      if (super.has(name, start)) {
        return true;
      }

      iWidget w = getMap();

      if (w.isEventEnabled(name)) {
        return true;
      }

      return w.hasCustomProperty(name);
    }

    private iWidget getMap() {
      return (iWidget) javaObject;
    }
  }
}
