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
import com.appnativa.rare.iPlatformAppContext;
import com.appnativa.rare.net.ActionLink;
import com.appnativa.rare.platform.apple.AppContext;
import com.appnativa.rare.spot.Application;
import com.appnativa.rare.ui.Frame;
import com.appnativa.rare.ui.iWindow;
import com.appnativa.rare.viewer.WindowViewer;
import com.appnativa.util.Streams;

import java.io.FileInputStream;

/*-[
#import "RAREJSFunction.h"
#import "AppleHelper.h"
#import <javax/script/ScriptException.h>
]-*/
import java.net.URL;

import java.util.Iterator;
import java.util.Map.Entry;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.SimpleBindings;

public class ScriptManager extends aScriptManager {
  public ScriptManager() {
    super();
  }

  public ScriptManager(iPlatformAppContext ctx, Application app) {
    super(ctx, app, new SimpleBindings());

    ScriptEngineFactory f = (ScriptEngineFactory) engineManager.getEngineFactories().get(0);

    globalBindings.put(ScriptEngine.LANGUAGE, f.getLanguageName());
    globalBindings.put(ScriptEngine.ENGINE_VERSION, f.getEngineVersion());
    globalBindings.put(ScriptEngine.LANGUAGE_VERSION, f.getLanguageVersion());
    globalBindings.put(ScriptEngine.NAME, f.getLanguageName());
  }

  public ScriptManager(iPlatformAppContext app, ScriptEngine engine, Bindings bindings, Frame frame, String scriptName,
                       String source) {
    super(app, engine, bindings, frame, scriptName, source);
  }

  public Object InvokeNativeScriptFunctionObject(Object sparJSFunction, Object... args) {
    return ((JavaScriptEngine) scriptEngine).InvokeNativeScriptFunctionObject(sparJSFunction, args);
  }

  public Object InvokeNativeScriptFunctionObjectEx(Object sparJSFunction, Object nsArray) {
    return ((JavaScriptEngine) scriptEngine).InvokeNativeScriptFunctionObjectEx(sparJSFunction, nsArray);
  }

  public static void createEngineManager() {
    engineManager = new ScriptEngineManager();

    JavaScriptEngineFactory f = new JavaScriptEngineFactory();

    f.registerWithEngineManager(engineManager);
  }

  @Override
  public Object createVariableValue(WidgetContext context, Object javaobj) {
    return javaobj;
  }

  @Override
  public Object unwrap(Object o) {
    return o;
  }

  @Override
  public void setGlobalVariable(String name, Object value) {
    super.setGlobalVariable(name, value);

    JavaScriptEngine je = (JavaScriptEngine) scriptEngine;

    je.setPopulatingConstants(true);
    je.setConstantValue(name, value);
    je.setPopulatingConstants(false);
  }

  @Override
  public iScriptHandler getRootHandler(iPlatformAppContext app, iWindow frame, String type, String name, String source,
          boolean share) {
    ScriptEngine engine = getEngine(type, false, null);

    if (name == null) {
      if (source == null) {
        name = scriptName;
      } else {
        name = "window";
      }
    }

    return new ScriptManager(app, engine, share
            ? mainWindowBindings
            : null, (Frame) frame, name, source);
  }

  @Override
  protected Object InvokeNativeScriptFunctionObject(Object function, ScriptEngine engine, ScriptContext context,
          Object scriptObject) {
    return ((JavaScriptEngine) engine).InvokeNativeScriptFunctionObject(function, engine, context, scriptObject);
  }

  @Override
  protected void addLoadedScript(String name) {}

  @Override
  protected void configureDebuggingInfo(ScriptEngine engine, ScriptContext ctx, String name, String source) {}

  @Override
  protected ScriptContext createScriptContext(ScriptEngine e) {
    return new JSScriptContext(((JavaScriptEngine) e).createBindings());
  }

  @Override
  protected WindowViewer createWindowViewer(iPlatformAppContext ctx, String name, Object win, WindowViewer parent,
          iScriptHandler sh) {
    return new WindowViewer(ctx, name, (Frame) win, parent, sh);
  }

  @Override
  protected void initializeEngine(ScriptEngine engine, ScriptContext context) {
    if (context == null) {
      return;
    }

    JavaScriptEngine je = (JavaScriptEngine) engine;

    context.setBindings(je.createBindings(), ScriptContext.ENGINE_SCOPE);
    context.setAttribute(DynamicBindings.FORM, theWindow, ScriptContext.ENGINE_SCOPE);
    context.setAttribute(DynamicBindings.WIDGET, theWindow, ScriptContext.ENGINE_SCOPE);
    je.setPopulatingConstants(true);

    Iterator<Entry<String, Object>> it = globalBindings.entrySet().iterator();

    while(it.hasNext()) {
      Entry<String, Object> e = it.next();

      je.setConstantValue(e.getKey(), e.getValue());
    }

    je.setPopulatingConstants(false);

    if (!"false".equalsIgnoreCase(Platform.getProperty("rare.scriptingRunInit",
            Platform.getProperty("jnlp.rare.scriptingRunInit", null)))) {
      String fileName = (String) context.getAttribute(ScriptEngine.FILENAME, ScriptContext.ENGINE_SCOPE);

      try {
        String source = engine.getFactory().getLanguageName().toLowerCase() + ".init";
        String code   = getResourceStreamAsString(source);

        if (code != null) {
          context.setAttribute(ScriptEngine.FILENAME, source, ScriptContext.ENGINE_SCOPE);
          engine.eval(code, context);
        }
      } catch(Throwable e) {
        context.setAttribute(ScriptEngine.FILENAME, fileName, ScriptContext.ENGINE_SCOPE);
        Platform.debugLog(e.toString());
      }
    }
  }

  @Override
  protected native void invokeAndWait(iScriptRunnable r)    /*-[
    [AppleHelper invokeAndWait: r];
  ]-*/
  ;

  @Override
  protected void loadDebugger() {}

  @Override
  protected WidgetContext newWidgetContext() {
    return new WidgetContextEx();
  }

  @Override
  protected void saveSourceForDebugging(String scriptName, String source) {}

  @Override
  protected void setupDebugger() {}

  @Override
  protected void setupDynamicBindings(ScriptEngine e, ScriptContext sc) {
    sc.setBindings(new DynamicBindings(sc.getBindings(ScriptContext.GLOBAL_SCOPE),
                                       sc.getBindings(ScriptContext.ENGINE_SCOPE)), ScriptContext.GLOBAL_SCOPE);
  }

  @Override
  protected void setupScriptingShell(ScriptEngine engine) {}

  @Override
  protected String spiClassNameForJavascripEngineFactory() {
    return null;
  }

  @Override
  protected ErrorInformation getNativeScriptErrorInformation(Object error) {
    if (error instanceof Throwable) {
      return new ErrorInformation((Throwable) error);
    }

    return getNativeScriptErrorInformationEx(error);
  }

  protected native ErrorInformation getNativeScriptErrorInformationEx(Object error)
  /*-[
    NSString* msg=@"Unknown Error!!!";
    if (error != nil) {
      if([error isKindOfClass: [NSDictionary class]]) {
        NSDictionary* d=(NSDictionary*)error;
        NSString* source=d[@"sourceURL"];
        NSNumber* line=d[@"line"];
        NSString* stack=d[@"stack"];
        msg=@"Script Error";
        if(source && line) {
          msg=[NSString stringWithFormat:@"Script Error in '%@' on line:%@", source,line];
        }
        else if(source){
          msg=[NSString stringWithFormat:@"Script Error in '%@'", source];
        }
        RAREErrorInformation *ei= [[RAREErrorInformation alloc] initWithNSString:msg withNSString:msg];
        [ei setScriptStackTraceWithNSString:stack];
        return ei;
      }
      msg = [error description];
    }
    return [[RAREErrorInformation alloc] initWithId:error withNSString:nil withNSString:nil];
   ]-*/
  ;

  @Override
  protected native boolean isNativeScriptFunctionObject(Object function)
  /*-[
    return [function isKindOfClass: [RAREJSFunction class]];
   ]-*/
  ;

  @Override
  protected String getInitScript(String name) {
    String s = "raw/rare_raw_" + name;

    try {
      s = ((AppContext) appContext).makeResourcePath(s, null);

      if (s != null) {
        FileInputStream fin = new FileInputStream(s);

        return Streams.streamToString(fin);
      }
    } catch(Exception e) {
      Platform.ignoreException(s, e);
    }

    return null;
  }

  private String getResourceStreamAsString(String source) {
    try {
      URL url = Platform.getAppContext().getResourceURL(source);

      if (url != null) {
        ActionLink l = new ActionLink(null, url);

        return l.getContentAsString();
      }
    } catch(Exception e) {
      Platform.ignoreException(null, e);
    }

    return null;
  }
}
