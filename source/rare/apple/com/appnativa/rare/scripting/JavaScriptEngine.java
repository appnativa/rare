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
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.viewer.aContainer;
import com.appnativa.rare.viewer.aViewer;
import com.appnativa.rare.widget.aWidget;
import com.appnativa.spot.SPOTSequence;
import com.appnativa.util.Helper;
import com.appnativa.util.SNumber;
import com.appnativa.util.Streams;
/*-[
#import "RAREJSEngine.h"
#import "AppleHelper.h"
]-*/

import java.io.IOException;
import java.io.Reader;

import java.util.List;
import java.util.Map;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptException;
import javax.script.SimpleScriptContext;

public class JavaScriptEngine implements ScriptEngine {
  public static final Object Undefined = new Object();
  Object                     packages  = new Object();
  ScriptContext              context;
  ScriptContext              executionContext;
  JavaScriptEngineFactory    factory;
  Object                     proxy;
  ScriptContext              tmpContext;
  private String             customPropertyPrefix;
  private boolean            populatingConstants;

  public JavaScriptEngine(JavaScriptEngineFactory factory) {
    this.proxy       = createProxy(this);
    this.factory     = factory;
    this.context     = new SimpleScriptContext();
    executionContext = context;
    context.setBindings(createBindings(), ScriptContext.ENGINE_SCOPE);
    customPropertyPrefix = Platform.getAppContext().getCustomPropertyPrefix();
  }

  public native Object InvokeNativeScriptFunctionObject(Object sparJSFunction, Object... args)      /*-[
       executionContext_=context_;
       RAREJSEngine* e=(RAREJSEngine*)proxy_;
       NSArray* a=nil;
       if(args) {
          a=[AppleHelper toNSArray: args];
       }
        return [e callJSFunction:(RAREJSFunction *) sparJSFunction arguments: a thisObject: nil controller: nil];
     ]-*/
  ;

  public native Object InvokeNativeScriptFunctionObject(String functionName, Object... args)    /*-[
     executionContext_=context_;
     RAREJSEngine* e=(RAREJSEngine*)proxy_;
     NSArray* a=nil;
     if(args) {
           a=[AppleHelper toNSArray: args];
     }
       return [e callJSFunctionNamed:functionName arguments: a thisObject: nil controller: nil];
     ]-*/
  ;

  public native Object InvokeNativeScriptFunctionObject(Object sparJSFunction, ScriptEngine engine,
          ScriptContext context, Object scriptObject)
  /*-[
      if(!context) {
        context=context_;
      }
      executionContext_=context;
      RAREJSEngine* e=(RAREJSEngine*)proxy_;
      return [e callJSFunction:(RAREJSFunction *) sparJSFunction arguments: nil thisObject: scriptObject controller: nil];
  ]-*/
  ;

  public native Object InvokeNativeScriptFunctionObjectEx(Object sparJSFunction, Object nsArray)    /*-[
    executionContext_=context_;
    RAREJSEngine* e=(RAREJSEngine*)proxy_;
    NSArray* a=(NSArray*)nsArray;
    return [e callJSFunction:(RAREJSFunction *) sparJSFunction arguments: a thisObject: nil controller: nil];
  ]-*/
  ;

  @Override
  public Bindings createBindings() {
    return new JSCocoaBindings(proxy);
  }

  public native void disposeJSObjectForScriptingContext(WidgetContext wc)
  /*-[
    RAREJSEngine* e=(RAREJSEngine*)proxy_;
    [e disposeJSObjectForScriptingContext: wc];
  ]-*/
  ;

  @Override
  public Object eval(Reader reader) throws ScriptException {
    try {
      return eval(Streams.readerToString(reader));
    } catch(IOException e) {
      throw new ScriptException(e);
    }
  }

  @Override
  public Object eval(String script) throws ScriptException {
    return eval(script, context);
  }

  @Override
  public Object eval(Reader reader, Bindings bindings) throws ScriptException {
    try {
      return eval(Streams.readerToString(reader), bindings);
    } catch(IOException e) {
      throw new ScriptException(e);
    }
  }

  @Override
  public Object eval(Reader reader, ScriptContext context) throws ScriptException {
    try {
      return eval(Streams.readerToString(reader), context);
    } catch(IOException e) {
      throw new ScriptException(e);
    }
  }

  @Override
  public Object eval(String script, Bindings bindings) throws ScriptException {
    if (tmpContext == null) {
      tmpContext = new SimpleScriptContext();
      tmpContext.setBindings(getContext().getBindings(ScriptContext.GLOBAL_SCOPE), ScriptContext.GLOBAL_SCOPE);
    }

    tmpContext.setBindings(bindings, ScriptContext.ENGINE_SCOPE);

    try {
      return eval(script, tmpContext);
    } finally {
      setContext(context);
    }
  }

  @Override
  public Object eval(String script, ScriptContext context) throws ScriptException {
    if (context == null) {
      context = getContext();
    }

    try {
      return evalEx(script, context, (String) context.getAttribute(FILENAME, ScriptContext.GLOBAL_SCOPE));
    } catch(Exception e) {
      throw new ScriptException(e);
    }
  }

  @Override
  public void put(String key, Object value) {
    getBindings(ScriptContext.ENGINE_SCOPE).put(key, value);
  }

  @Override
  public void setBindings(Bindings bindings, int scope) {
    context.setBindings(bindings, scope);
  }

  public native void setConstantValue(String name, Object value) 
  /*-[
    RAREJSEngine* e=(RAREJSEngine*)proxy_;
    [e setConstantValue: value withName: name];
  ]-*/
  ;

  @Override
  public void setContext(ScriptContext context) {
    this.context     = context;
    executionContext = context;
  }

  public boolean setObjectProperty(Object o, String property, Object value) {
    if (SNumber.isNumeric(property, 0)) {
      SNumber num = new SNumber(property);

      if ((num.decimalPlaces() > 0) || num.isNegative()) {
        return false;
      }

      return setNumericProperty(property, num.intValue(), value);
    }

    if (o instanceof Map) {
      Map m = (Map) o;

      if (!m.containsKey(property)) {
        return false;
      }

      m.put(property, value);

      return true;
    }

    if (o instanceof aViewer) {
      Object ow = ((aViewer) o).getNamedItem(property);

      if (ow instanceof RenderableDataItem) {
        ((RenderableDataItem) ow).setValue(value);

        return true;
      }
    }

    if (o instanceof aWidget) {
      aWidget w = (aWidget) o;

      if (property.startsWith("on")) {
        w.setEventHandler(property, value, false);

        return true;
      }

      if (property.equals("value")) {
        w.setValue(value);

        return true;
      }
    }

    return false;
  }

  public void setPopulatingConstants(boolean populatingConstants) {
    this.populatingConstants = populatingConstants;
  }

  @Override
  public Object get(String key) {
    return executionContext.getAttribute(key);
  }

  @Override
  public Bindings getBindings(int scope) {
    return context.getBindings(scope);
  }

  @Override
  public ScriptContext getContext() {
    return context;
  }

  @Override
  public ScriptEngineFactory getFactory() {
    return factory;
  }

  public Object getGlobalJSValue(String name) {
    if (populatingConstants) {
      return Undefined;
    }

    Object o = executionContext.getAttribute(name, ScriptContext.GLOBAL_SCOPE);

    if (o == null) {
      o = JavaPackage.getPackage(name);
    }

    if ((o == null) && name.equals("Packages")) {
      o = packages;
    }

    if ((o == null) &&!executionContext.getBindings(ScriptContext.GLOBAL_SCOPE).containsKey(name)) {
      return Undefined;
    }

    return o;
  }

  public Object getObjectProperty(Object o, String property) {
    if (o == packages) {
      return JavaPackage.importPackage(property);
    }

    if (o instanceof JavaPackage) {
      return ((JavaPackage) o).getProperty(property);
    }

    if (o instanceof SPOTSequence) {
      o = ((SPOTSequence) o).spot_elementFor(property);

      return (o == null)
             ? Undefined
             : o;
    }

    if (SNumber.isNumeric(property, 0)) {
      SNumber num = new SNumber(property);

      if ((num.decimalPlaces() > 0) || num.isNegative()) {
        return null;
      }

      return getNumericProperty(o, num.intValue());
    }

    if (o instanceof Map) {
      Map m = (Map) o;

      return m.get(property);
    }

    Object value = null;

    if ((o instanceof List) && "length".equals(property)) {
      return ((List) o).size();
    }

    if (o instanceof aContainer) {
      value = ((aContainer) o).getWidget(property);

      if (value != null) {
        return value;
      }
    }

    if (o instanceof aWidget) {
      aWidget w = (aWidget) o;

      value = w.getEventHandler(property);

      if (value == null) {
        value = w.getAttribute(property);
      }

      if (value == null) {
        final Map map = w.getCustomProperties();

        if ((map != null) && map.containsKey(property)) {
          return null;
        }
      }

      if (value != null) {
        return value;
      }
    }

    return Undefined;
  }

  protected String setCustomProperty(RenderableDataItem item, String property, Object value) {
    if ((customPropertyPrefix != null) &&!property.startsWith(customPropertyPrefix)) {
      String s = Platform.getResourceAsString("Rare.runtime.text.customPropertyRestriction");

      return Helper.expandString(s, property, customPropertyPrefix);
    }

    if (item instanceof aWidget) {
      ((aWidget) item).setAttribute(property, value);
    } else {
      item.setCustomProperty(property, value);
    }

    return null;
  }

  private static native Object createProxy(JavaScriptEngine e)
  /*-[
    RAREJSEngine* se=[RAREJSEngine new];
    se.engine=e;
    return se;
  ]-*/
  ;

  private native Object evalEx(String script, ScriptContext context, String filename) throws Exception
  /*-[
    if(!context) {
      context=context_;
    }
    executionContext_=context;
    RAREJSEngine* e=(RAREJSEngine*)proxy_;
    return [e eval: script asName: filename];
  ]-*/
  ;

  private boolean setNumericProperty(Object o, int n, Object value) {
    if (o instanceof List) {
      List l = (List) o;

      if (l.size() > n) {
        l.set(n, value);
      }

      return true;
    }

    if (o instanceof Object[]) {
      Object[] a = (Object[]) o;

      if (a.length > n) {
        a[n] = value;
      }

      return true;
    }

    return false;
  }

  private Object getNumericProperty(Object o, int n) {
    if (o instanceof List) {
      List l = (List) o;

      return (l.size() > n)
             ? l.get(n)
             : null;
    }

    if (o instanceof Object[]) {
      Object[] a = (Object[]) o;

      return (a.length > n)
             ? a[n]
             : null;
    }

    if (o instanceof Object[]) {
      Object[] a = (Object[]) o;

      return (a.length > n)
             ? a[n]
             : null;
    }

    return null;
  }
}
