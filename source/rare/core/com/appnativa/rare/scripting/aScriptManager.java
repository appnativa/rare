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
import com.appnativa.rare.iConstants;
import com.appnativa.rare.iDebugHandler;
import com.appnativa.rare.iPlatformAppContext;
import com.appnativa.rare.spot.Application;
import com.appnativa.rare.ui.UIBorderHelper;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIColorHelper;
import com.appnativa.rare.ui.UIImageHelper;
import com.appnativa.rare.ui.UIScreen;
import com.appnativa.rare.ui.iEventHandler;
import com.appnativa.rare.viewer.WindowViewer;
import com.appnativa.rare.viewer.aWindowViewer;
import com.appnativa.rare.viewer.iFormViewer;
import com.appnativa.rare.viewer.iViewer;
import com.appnativa.rare.widget.aWidget;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.util.CharScanner;
import com.appnativa.util.iCancelable;

import com.google.j2objc.annotations.Weak;

import java.util.ArrayList;
import java.util.EventObject;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

import javax.script.Bindings;
import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.Invocable;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleBindings;
import javax.script.SimpleScriptContext;

/**
 *
 * @author Don DeCoteau
 */
public abstract class aScriptManager implements iScriptHandler {
  protected static final HashMap<String, ScriptEngine> loadedEngines        = new HashMap<String, ScriptEngine>();
  protected static boolean                             _constants_populated = false;
  protected volatile static SimpleBindings             nullBindings         = new SimpleBindings();
  protected static final HashSet<String>               protocols            = new HashSet<String>();
  protected static iDebugHandler                       _debugger;
  protected static boolean                             _rhinoInitialized;
  protected volatile static boolean                    debuggerLoaded;
  protected static ScriptEngineManager                 engineManager;
  protected volatile static Bindings                   globalBindings;
  protected static ConcurrentHashMap<String, String>   nameMappings;
  protected static volatile Object                     postExecuteCode;
  protected static volatile Object                     preExecuteCode;
  protected static int                                 shellPort;

  static {
    protocols.add("http");
    protocols.add("lib");
    protocols.add("file");
    protocols.add("jar");
    protocols.add("https");
  }

  protected iPlatformAppContext appContext;
  protected String              executingScriptName;
  protected volatile Bindings   mainWindowBindings;
  protected Compilable          scriptCompiler;
  protected ScriptContext       scriptContext;
  protected ScriptEngine        scriptEngine;
  protected String              scriptName;
  protected Object              selfObject;
  protected WindowViewer        theWindow;
  protected boolean             tracingEnabled;

  public aScriptManager() {}

  /**
   * Creates a new instance of aScriptManager
   */
  public aScriptManager(iPlatformAppContext ctx, Application app) {
    this(ctx, app, new SimpleBindings());
  }

  /**
   * Creates a new instance of aScriptManager
   */
  public aScriptManager(iPlatformAppContext ctx, Application app, Bindings globals) {
    appContext     = ctx;
    globalBindings = globals;

    if (app.populateGlobalConstants.booleanValue()) {
      _constants_populated = true;
      EnvConstants.populate(globalBindings);
    }

    String s = app.spot_getAttribute("onPreExecute");

    s = (s == null)
        ? null
        : s.trim();

    if ((s != null) && (s.length() > 0)) {
      preExecuteCode = s;
    }

    s = app.spot_getAttribute("onPostExecute");
    s = (s == null)
        ? null
        : s.trim();

    if ((s != null) && (s.length() > 0)) {
      postExecuteCode = s;
    }

    globalBindings.put("rare", Platform.getGlobalFunctions());
    globalBindings.put("uiFunctions", Platform.getGlobalFunctions());
    globalBindings.put("uiPlatform", new Platform());
    globalBindings.put("uiColor", UIColor.TRANSPARENT);
    globalBindings.put("uiScreen", new UIScreen());
    globalBindings.put("uiImageHelper", new UIImageHelper());
    globalBindings.put("uiColorHelper", new UIColorHelper());
    globalBindings.put("uiBorderHelper", new UIBorderHelper());

    if (nameMappings == null) {
      initializeMappings();
    }
  }

  public aScriptManager(iPlatformAppContext app, ScriptEngine engine, Bindings bindings, Object frame,
                        String scriptName, String source) {
    appContext   = app;
    scriptEngine = engine;
    initializeCompiler();

    try {
      theWindow     = createWindowViewer(app, "_top", frame, null, this);
      scriptContext = createScriptContext(engine);
      scriptContext.setBindings(globalBindings, ScriptContext.GLOBAL_SCOPE);

      if (bindings != null) {
        scriptContext.setBindings(bindings, ScriptContext.ENGINE_SCOPE);
      } else {
        if (app.isDynamicNameLookupEnabled()) {
          setupDynamicBindings(engine, scriptContext);
        }
      }

      if (mainWindowBindings == null) {
        mainWindowBindings = scriptContext.getBindings(ScriptContext.ENGINE_SCOPE);
      }

      WidgetContext oc = newWidgetContext();

      oc.scriptContext = scriptContext;
      oc.scriptObject  = theWindow;
      oc.scriptName    = scriptName;
      oc.scriptEngine  = engine;
      theWindow.setScriptingContext(oc);
      setWindow(scriptContext, theWindow);
      scriptContext.setAttribute("_top", theWindow, ScriptContext.GLOBAL_SCOPE);
      this.scriptName = scriptName;
      selfObject      = theWindow;
      initializeEngine(engine, scriptContext);
      initializeEngineEx(engine, scriptContext);

      if (scriptName != null) {
        scriptEngine.put(ScriptEngine.FILENAME, scriptName);
      }

      if (preExecuteCode instanceof String) {
        preExecuteCode = compile(oc, "preExecute", (String) preExecuteCode);
      }

      if (postExecuteCode instanceof String) {
        postExecuteCode = compile(oc, "postExecuteCode", (String) postExecuteCode);
      }

      setupScriptingShell(engine);

      if (_debugger != null) {
        try {
          _debugger.scriptContextInit(this, engine, scriptContext);
        } catch(Throwable t) {
          Platform.ignoreException("debugger.scriptContextInit()", t);
        }
      }

      if (source != null) {
        if (isInlineScript(scriptName)) {    // &&
          saveSourceForDebugging(scriptName, source);
        }

        addLoadedScript(scriptName);
        this.evaluate(oc, source, null);
      }
    } catch(Throwable e) {
      if (e instanceof RuntimeException) {
        throw(RuntimeException) e;
      } else {
        throw new ApplicationException(e);
      }
    }
  }

  @Override
  public Object callFunction(WidgetContext context, String name, Object[] args) {
    try {
      Object        o;
      ScriptEngine  e  = (ScriptEngine) context.scriptEngine;
      ScriptContext sc = (ScriptContext) context.scriptContext;

      if (sc == null) {
        sc = scriptContext;
      }

      if (e instanceof Invocable) {
        Invocable inv = (Invocable) e;

        o = inv.invokeFunction(name, args);
      } else {
        String s;
        String a[] = null;
        int    len = (args == null)
                     ? 0
                     : args.length;

        if (len == 0) {
          s = getFunctionCall(name, (String[]) null);
        } else {
          a = new String[len];

          for (int i = 0; i < len; i++) {
            a[i] = "_rareP" + i;
            sc.setAttribute(a[i], args[i], ScriptContext.ENGINE_SCOPE);
          }

          s = getFunctionCall(name, a);
        }

        try {
          o = e.eval(s, sc);
        } finally {
          if ((len > 0) && (a != null)) {
            for (int i = 0; i < len; i++) {
              a[i] = "_rareP" + i;
              sc.removeAttribute(a[i], ScriptContext.ENGINE_SCOPE);
            }
          }
        }
      }

      return o;
    } catch(Exception e) {
      if (e instanceof RuntimeException) {
        throw(RuntimeException) e;
      } else {
        throw new ApplicationException(e);
      }
    }
  }

  /**
   * Removes the rare constants from the global binding
   */
  public static void clearGlobalConstants() {
    if (_constants_populated) {
      Iterator<String> it = EnvConstants.getConstants().keySet().iterator();
      Bindings         gb = globalBindings;

      while(it.hasNext()) {
        gb.remove(it.next());
      }
    }
  }

  @Override
  public Object compile(WidgetContext context, String name, String code) {
    return compile((ScriptEngine) context.scriptEngine, context, name, code);
  }

  @Override
  public iScriptHandler.iScriptRunnable createRunner(WidgetContext context, Object compiled, ScriptingEvent e) {
    return createRunner(context, compiled, false, e);
  }

  @Override
  public iScriptRunnable createRunner(WidgetContext context, String source, ScriptingEvent e) {
    return new EvalRunner(this, context, source, e);
  }

  @Override
  public iScriptRunnable createRunner(WidgetContext context, String code, String language, ScriptingEvent e) {
    return createRunner(context, code, language, false, e);
  }

  @Override
  public WidgetContext createScriptingContext(Object javaobj) {
    WidgetContext oc     = newWidgetContext();
    Object        engine = scriptEngine;

    if (javaobj instanceof iWidget) {
      iWidget       w  = (iWidget) javaobj;
      iFormViewer   fv = w.getFormViewer();
      WidgetContext fc = null;

      if ((fv != null) && (fv != w) && (fv.getViewer() != w)) {
        fc = fv.getScriptingContext();
      }

      if ((fc != null) && (fc.scriptEngine != null)) {
        engine           = fc.scriptEngine;
        oc.scriptContext = fc.scriptContext;
      }
    }

    oc.scriptEngine = engine;
    oc.scriptObject = javaobj;

    return oc;
  }

  @Override
  public void dispose() {
    if (_debugger != null) {
      try {
        _debugger.dispose();
      } catch(Throwable ignore) {}
    }

    _debugger = null;

    if (Platform.isShuttingDown()) {
      if (scriptContext != null) {
        Bindings b;

        b = scriptContext.getBindings(ScriptContext.GLOBAL_SCOPE);

        if (b != null) {
          b.clear();
        }

        b = scriptContext.getBindings(ScriptContext.ENGINE_SCOPE);

        if (b != null) {
          b.clear();
        }

        scriptEngine.setContext(new SimpleScriptContext());
      }
    } else if (selfObject == theWindow) {
      if (scriptContext != null) {
        setWindow(scriptContext, null);
        scriptContext.setBindings(nullBindings, ScriptContext.ENGINE_SCOPE);
        scriptContext.setBindings(nullBindings, ScriptContext.GLOBAL_SCOPE);
      }
    }

    scriptContext = null;
    selfObject    = null;
    theWindow     = null;
    scriptEngine  = null;
  }

  @Override
  public Object evaluate(WidgetContext context, Object compiled, ScriptingEvent e) {
    iScriptRunnable r = createRunner(context, compiled, false, e);

    if (Platform.isUIThread()) {
      r.run();
    } else {
      invokeAndWait(r);
    }

    return r.getResult();
  }

  @Override
  public Object evaluate(WidgetContext context, String code, ScriptingEvent e) {
    iScriptRunnable r = createRunner(context, code, false, e);

    runTask(r);

    return r.getResult();
  }

  @Override
  public Object evaluate(WidgetContext context, String code, String language, ScriptingEvent e) {
    iScriptRunnable r = createRunner(context, code, language, false, e);

    runTask(r);

    return r.getResult();
  }

  @Override
  public void execute(WidgetContext context, Object compiled, ScriptingEvent e) {
    submitTask(createRunner(context, compiled, false, e));
  }

  @Override
  public void execute(WidgetContext context, String code, ScriptingEvent e) {
    submitTask(createRunner(context, code, false, e));
  }

  @Override
  public void execute(WidgetContext context, String code, String language, ScriptingEvent e) {
    submitTask(createRunner(context, code, language, false, e));
  }

  @Override
  public void loadScript(String name, String code, String language) {
    ScriptEngine     e      = (language == null)
                              ? scriptEngine
                              : getEngine(language, true, scriptContext);
    CompiledScriptEx script = new CompiledScriptEx(e, scriptContext, name, code);

    try {
      script.eval();
    } catch(ScriptException ex) {
      throw new ApplicationException(ex);
    }
  }

  public static void registerEngineFactories(String stringList) {
    if ((stringList != null) && (stringList.length() > 0)) {
      List<String> l = CharScanner.getTokens(stringList, ',', true);

      for (String e : l) {
        registerEngineFactory(e);
      }
    }
  }

  public static void registerEngineFactory(String className) {
    try {
      ScriptEngineFactory f = (ScriptEngineFactory) Platform.loadClass(className).newInstance();

      engineManager.registerEngineName(f.getEngineName(), f);

      List<String> l = f.getNames();

      for (String s : l) {
        engineManager.registerEngineName(s, f);
      }

      l = f.getExtensions();

      for (String s : l) {
        engineManager.registerEngineExtension(s, f);
      }

      l = f.getMimeTypes();

      for (String s : l) {
        engineManager.registerEngineMimeType(s, f);
      }
    } catch(Exception ex) {
      if (Platform.isDebugEnabled()) {
        ex.printStackTrace();
      }
    }
  }

  public void restoreSaveWindow(ScriptContext sc, WidgetContext wc) {
    if (wc.savedWindow != null) {
      sc.setAttribute("window", wc.savedWindow, ScriptContext.ENGINE_SCOPE);
      sc.setAttribute("rwindow", wc.savedWindow, ScriptContext.ENGINE_SCOPE);
    }
  }

  @Override
  public void runTask(iScriptRunnable r) {
    if (Platform.isUIThread()) {
      r.run();
    } else {
      invokeAndWait(r);
    }
  }

  @Override
  public void submitTask(iScriptRunnable r) {
    Platform.invokeLater(r);
  }

  @Override
  public void setGlobalVariable(String name, Object value) {
    if (!"self".equals(name) &&!"window".equals(name)) {
      globalBindings.put(name, value);
    }
  }

  @Override
  public WidgetContext setScriptingContext(iViewer viewer, String type, String name, String source, boolean shared) {
    if (name == null) {
      if (source == null) {
        name = scriptName;
      } else {
        name = viewer.getName();

        if (name == null) {
          name = "eval";
        }
      }
    }

    ScriptContext ctx;
    ScriptEngine  engine = getEngine(type, true, null);

    if (!shared) {
      ctx = createScriptContext(engine);
    } else {
      ctx = scriptContext;
    }

    WidgetContext oc = newWidgetContext();

    viewer.setScriptingContext(oc);

    if (!shared) {
      if (viewer.getAppContext().isDynamicNameLookupEnabled()) {
        setupDynamicBindings(engine, ctx);
      } else {
        ctx.setBindings(engine.createBindings(), ScriptContext.ENGINE_SCOPE);
      }

      ctx.setBindings(scriptContext.getBindings(ScriptContext.GLOBAL_SCOPE), ScriptContext.GLOBAL_SCOPE);
      ctx.getBindings(ScriptContext.ENGINE_SCOPE).putAll(scriptContext.getBindings(ScriptContext.ENGINE_SCOPE));
    }

    oc.scriptEngine  = engine;
    oc.scriptObject  = viewer;
    oc.scriptName    = name;
    oc.scriptContext = ctx;

    if ((_debugger != null) && (source != null)) {
      configureDebuggingInfo(engine, ctx, name, source);
    }

    if (source != null) {
      try {
        EventObject    e   = new EventObject(viewer);
        ScriptingEvent evt = new ScriptingEvent(this, iConstants.ATTRIBUTE_FUNCTION_EVAL, e, viewer, null);

        this.evaluate(oc, source, evt);
      } catch(Throwable e) {
        if (e instanceof RuntimeException) {
          throw(RuntimeException) e;
        } else {
          throw new ApplicationException(e);
        }
      }
    }

    return oc;
  }

  @Override
  public void setScriptingVariable(WidgetContext context, String name, Object scriptobj) {
    if (!"self".equals(name) &&!"window".equals(name)) {
      getScriptContext(context).setAttribute(name, scriptobj, ScriptContext.ENGINE_SCOPE);
    }
  }

  public iPlatformAppContext getAppContext() {
    return appContext;
  }

  @Override
  public WindowViewer getCurrentWindowViewer() {
    WindowViewer w = null;

    if (scriptContext != null) {
      w = (WindowViewer) scriptContext.getAttribute("window", ScriptContext.ENGINE_SCOPE);

      if (w.isDisposed()) {
        w = null;
      }
    }

    return (w == null)
           ? theWindow
           : w;
  }

  public ScriptEngine getEngine(String type, boolean reuse, ScriptContext context) {
    synchronized(loadedEngines) {
      String       language  = type;
      ScriptEngine engine    = null;
      String       className = null;

      if (language == null) {
        language = appContext.getDefaultScrptingLanguage();
        type     = language;
      } else {
        int n = language.indexOf(';');

        if (n != -1) {
          language = language.substring(0, n);
        }

        language = language.trim();
      }

      int n = language.lastIndexOf('.');

      if (n != -1) {
        className = language;
      }

      String lang = nameMappings.get(language);

      if (lang != null) {
        language = lang;
      }

      if (reuse) {
        if (lang != null) {
          engine = loadedEngines.get(lang);

          if (engine != null) {
            return engine;
          }

          language = lang;
        }
      }

      if ("js".equals(language) || "javascript".equalsIgnoreCase(language) || "ecmascript".equalsIgnoreCase(language)) {
        className = spiClassNameForJavascripEngineFactory();
      }

      if (className != null) {
        List<ScriptEngineFactory> fs = engineManager.getEngineFactories();

        for (ScriptEngineFactory f : fs) {
          if (f.getClass().getName().equals(className)) {
            engine = f.getScriptEngine();

            break;
          }
        }
      }

      if (engine == null) {
        engine = engineManager.getEngineByMimeType(type);

        if (engine == null) {
          engine = engineManager.getEngineByName(language);
        }

        if (engine == null) {
          engine = engineManager.getEngineByExtension(language);
        }
      }

      if (engine == null) {
        throw new ApplicationException("Unsupported scripting language:" + type);
      }

      language = engine.getFactory().getLanguageName();
      loadedEngines.put(language, engine);

      if (className != null) {
        loadedEngines.put(className, engine);
      }

      initializeEngine(engine, context);

      if (!debuggerLoaded) {
        loadDebugger();
      }

      if (_debugger != null) {
        _debugger.scriptEngineInit(this, engine);
      }

      return engine;
    }
  }

  @Override
  public ErrorInformation getErrorInformation(iPlatformAppContext app, Object error) {
    ErrorInformation ei = getNativeScriptErrorInformation(error);

    if (ei == null) {
      if (error instanceof Throwable) {
        ei = new ErrorInformation((Throwable) error);
      }

      if (ei == null) {
        ei = new ErrorInformation(error, null, (error == null)
                ? ""
                : error.toString());
      }
    }

    return ei;
  }

  @Override
  public iFormViewer getFormViewer() {
    if (scriptContext != null) {
      return (iFormViewer) scriptContext.getAttribute("form", ScriptContext.ENGINE_SCOPE);
    }

    return null;
  }

  @Override
  public String getFunctionCall(String function, String[] args) {
    if ((args == null) || (args.length == 0)) {
      return function + "()";
    }

    String ret = function;

    ret += "(";

    for (int i = 0; i < args.length; i++) {
      ret += args[i];

      if (i == args.length - 1) {
        ret += ")";
      } else {
        ret += ",";
      }
    }

    return ret;
  }

  public Bindings getGlobalBindings() {
    return globalBindings;
  }

  public static ScriptEngine getJavaScriptEngine() {
    return engineManager.getEngineByExtension("js");
  }

  @Override
  public String getMethodCall(String obj, String method, String[] args) {
    return scriptEngine.getFactory().getMethodCallSyntax(obj, method, args);
  }

  /**
   * Returns the engine manager ins use by the script manager
   *
   * @return the engine manager ins use by the script manager
   */
  public static ScriptEngineManager getScriptEngineManager() {
    return engineManager;
  }

  public Object getScriptingContext() {
    return scriptContext;
  }

  public ScriptEngine getScriptingEngine() {
    return scriptEngine;
  }

  @Override
  public String getScriptingName() {
    return executingScriptName;
  }

  @Override
  public Object getScriptingVariable(WidgetContext context, String name) {
    return getScriptContext(context).getAttribute(name);
  }

  @Override
  public iWidget getWidget() {
    if (scriptContext != null) {
      return (iWidget) scriptContext.getAttribute("widget", ScriptContext.ENGINE_SCOPE);
    }

    return null;
  }

  @Override
  public aWindowViewer getWindowViewer() {
    return theWindow;
  }

  public static boolean isInlineScript(String name) {
    if (name == null) {
      return false;
    }

    int n = name.indexOf(':');

    if (n == -1) {
      return true;
    }

    return !protocols.contains(name.substring(0, n));
  }

  public static boolean isRhinoInitialized() {
    return _rhinoInitialized;
  }

  ScriptContext getScriptContext(WidgetContext context) {
    if ((context != null) && (context.scriptContext != null)) {
      return (ScriptContext) context.scriptContext;
    }

    return scriptContext;
  }

  protected abstract Object InvokeNativeScriptFunctionObject(Object function, ScriptEngine engine,
          ScriptContext context, Object scriptObject);

  protected abstract void addLoadedScript(String name);

  protected Object compile(ScriptEngine e, WidgetContext context, String name, String code) {
    ScriptContext sc = (ScriptContext) context.scriptContext;

    if (sc == null) {
      sc = scriptContext;
    }

    if (code.startsWith("class:")) {
      if ((context != null) && (context.scriptObject instanceof aWidget)) {
        code = ((aWidget) context.scriptObject).expandString(code);
      }

      return new EventHandlerInterfaceScript(e, sc, new EventHandlerInterface(code));
    }

    if ((e instanceof Compilable) &&!Platform.isDebugEnabled()) {
      Object savedName = sc.getAttribute(ScriptEngine.FILENAME, ScriptContext.ENGINE_SCOPE);

      if (name != null) {
        sc.setAttribute(ScriptEngine.FILENAME, name, ScriptContext.ENGINE_SCOPE);
      } else if (savedName instanceof String) {
        name = (String) savedName;
      }

      try {
        return new CompiledScriptEx(e, sc, name, ((Compilable) e).compile(code));
      } catch(ScriptException ex) {
        throw new ApplicationException(ex);
      } finally {
        sc.setAttribute(ScriptEngine.FILENAME, savedName, ScriptContext.ENGINE_SCOPE);
      }
    }

    if (Platform.isDebuggingEnabled()) {
      if (name == null) {
        name = context.getWidget().getName();
      }

      int n = name.indexOf(':');

      if ((n == -1) ||!protocols.contains(name.substring(0, n))) {
        name = name + "__" + System.identityHashCode(code);
        saveSourceForDebugging(name, code);
      }
    }

    return new CompiledScriptEx(e, sc, name, code);
  }

  protected abstract void configureDebuggingInfo(ScriptEngine engine, ScriptContext ctx, String name, String source);

  protected iScriptRunnable createRunner(WidgetContext context, Object code, boolean string, ScriptingEvent e) {
    ScriptEngine  se = (ScriptEngine) context.scriptEngine;
    ScriptContext sc = (ScriptContext) context.scriptContext;

    if (sc == null) {
      sc = scriptContext;
    }

    if (code instanceof Runnable) {
      code = new RunnableScript(se, sc, (Runnable) code);
    } else if (code instanceof EventHandlerInterface) {
      code = new EventHandlerInterfaceScript(se, sc, (EventHandlerInterface) code);
    } else if (isNativeScriptFunctionObject(code)) {
      code = new JSFunctionScript(se, sc, code);
    } else if (code instanceof MultiScript) {
      code = new CompiledMultiScript(se, sc, (MultiScript) code);
    } else if (code instanceof String) {
      code = compile(context, null, (String) code);
    }

    return new EvalRunner(this, context, code, false, e);
  }

  protected iScriptRunnable createRunner(WidgetContext context, String source, String language, boolean string,
          ScriptingEvent e) {
    ScriptEngine  se = getEngine(language, true, scriptContext);
    ScriptContext sc = (ScriptContext) context.scriptContext;

    if (language.equalsIgnoreCase("coffee") || language.equalsIgnoreCase("coffeescript")) {
      String var = "_coffeeSrc_" + System.currentTimeMillis();

      se.put(var, source);
      source = "CoffeeScript.compile(" + var + ");" + var + "=null";
    }

    if (sc == null) {
      context.scriptContext = scriptContext;
    }

    String type     = (e == null)
                      ? "unknownEvent"
                      : e.getType();
    Object compiled = compile(se, context, context.getWidget().getName() + "_" + type, source);

    return new EvalRunner(this, context, compiled, false, e);
  }

  protected abstract ScriptContext createScriptContext(ScriptEngine e);

  protected abstract WindowViewer createWindowViewer(iPlatformAppContext ctx, String name, Object win,
          WindowViewer parent, iScriptHandler sh);

  protected void handleScriptTracing(String source) {}

  protected void initializeCompiler() {
    if ((scriptEngine instanceof Compilable) &&!Platform.isDebugEnabled()) {
      scriptCompiler = (Compilable) scriptEngine;
    }
  }

  protected void initializeEngineEx(ScriptEngine engine, ScriptContext context) {
    String fileName = (String) context.getAttribute(ScriptEngine.FILENAME, ScriptContext.ENGINE_SCOPE);

    try {
      String source = engine.getFactory().getLanguageName().toLowerCase() + ".init";
      String code   = getInitScript(source);

      if (code != null) {
        context.setAttribute(ScriptEngine.FILENAME, source, ScriptContext.ENGINE_SCOPE);
        engine.eval(code, context);
      }
    } catch(Throwable e) {
      context.setAttribute(ScriptEngine.FILENAME, fileName, ScriptContext.ENGINE_SCOPE);
      Platform.debugLog(e.toString());
    }
  }

  protected abstract String getInitScript(String name);

  protected abstract void initializeEngine(ScriptEngine engine, ScriptContext context);

  protected void initializeMappings() {
    nameMappings = new ConcurrentHashMap<String, String>();

    List<String>              names;
    List<ScriptEngineFactory> list = engineManager.getEngineFactories();
    ScriptEngineFactory       f;
    int                       len = list.size();
    int                       len2;
    int                       n;
    String                    language;

    for (int i = 0; i < len; i++) {
      f        = list.get(i);
      language = f.getLanguageName();
      names    = f.getExtensions();
      len2     = names.size();

      for (n = 0; n < len2; n++) {
        nameMappings.put(names.get(n), language);
      }

      names = f.getMimeTypes();
      len2  = names.size();

      for (n = 0; n < len2; n++) {
        nameMappings.put(names.get(n), language);
      }
    }

    nameMappings.put("coffee", "javascript");
    nameMappings.put("coffeescript", "javascript");
  }

  protected abstract void invokeAndWait(iScriptRunnable r);

  protected abstract void loadDebugger();

  protected WidgetContext newWidgetContext() {
    return new WidgetContext();
  }

  protected void saveCurrentWindow(ScriptContext sc, WidgetContext wc) {
    wc.savedWindow = sc.getAttribute("window", ScriptContext.ENGINE_SCOPE);
  }

  protected abstract void saveSourceForDebugging(String scriptName, String source);

  protected abstract void setupDebugger();

  protected void setupDynamicBindings(ScriptEngine e, ScriptContext sc) {
    sc.setBindings(new DynamicBindings(e.getBindings(ScriptContext.ENGINE_SCOPE)), ScriptContext.ENGINE_SCOPE);
  }

  protected abstract void setupScriptingShell(ScriptEngine engine);

  protected abstract String spiClassNameForJavascripEngineFactory();

  protected void setWindow(ScriptContext sc, Object window) {
    scriptContext.setAttribute("window", window, ScriptContext.ENGINE_SCOPE);
    scriptContext.setAttribute("rwindow", window, ScriptContext.ENGINE_SCOPE);
  }

  protected abstract ErrorInformation getNativeScriptErrorInformation(Object error);

  protected abstract boolean isNativeScriptFunctionObject(Object function);

  protected static class CompiledMultiScript extends aCompiledScript {
    MultiScript theScript;

    public CompiledMultiScript(ScriptEngine engine, ScriptContext context, MultiScript ms) {
      super(engine, context);
      this.theScript = ms;
    }

    @Override
    public Object eval(ScriptContext context) throws ScriptException {
      if (context == null) {
        context = engine.getContext();
      }

      ArrayList list = theScript.getCompiledCode();
      final int len  = list.size();

      for (int i = 0; i < len; i++) {
        Object o = list.get(i);

        if (o instanceof CompiledScript) {
          ((CompiledScript) o).eval(context);
        } else if (scriptManager.isNativeScriptFunctionObject(o)) {
          scriptManager.InvokeNativeScriptFunctionObject(o, engine, context, widgetContext.scriptObject);
        } else if (o instanceof String) {
          o = compile((String) o, context);
          ((CompiledScript) o).eval(context);
        } else if (o instanceof Runnable) {
          ((Runnable) o).run();
        }
      }

      return null;
    }

    @Override
    public String toString() {
      return theScript.toString();
    }

    protected Object compile(String code, ScriptContext sc) {
      String       name = theScript.getEvent();
      ScriptEngine e    = engine;

      if ((e instanceof Compilable) &&!Platform.isDebugEnabled()) {
        Object savedName = sc.getAttribute(ScriptEngine.FILENAME, ScriptContext.ENGINE_SCOPE);

        if (name != null) {
          sc.setAttribute(ScriptEngine.FILENAME, name, ScriptContext.ENGINE_SCOPE);
        }

        try {
          return new CompiledScriptEx(e, sc, name, ((Compilable) e).compile(code));
        } catch(ScriptException ex) {
          sc.setAttribute(ScriptEngine.FILENAME, savedName, ScriptContext.ENGINE_SCOPE);

          throw new ApplicationException(ex);
        }
      }

      return new CompiledScriptEx(e, sc, name, code);
    }
  }


  /**
   *
   *
   * @version 0.3, 2007-05-14
   * @author Don DeCoteau
   */
  protected static class CompiledScriptEx extends aCompiledScript {
    String         theCode;
    CompiledScript theScript;

    public CompiledScriptEx(ScriptEngine engine, ScriptContext context, String name, CompiledScript script) {
      super(engine, context);
      scriptName = name;
      theScript  = script;
    }

    public CompiledScriptEx(ScriptEngine engine, ScriptContext context, String name, String code) {
      super(engine, context);
      scriptName = name;
      theCode    = code;
    }

    @Override
    public Object eval(ScriptContext context) throws ScriptException {
      if (context == null) {
        context = engine.getContext();
      }

      return (theScript == null)
             ? getEngine().eval(theCode, context)
             : theScript.eval(context);
    }

    @Override
    public String toString() {
      return (theCode == null)
             ? theScript.toString()
             : theCode;
    }
  }


  protected static class EvalRunner implements iScriptRunnable, Callable {
    private static final String DISPOSE_EVENT_TYPE = iConstants.ATTRIBUTE_ON_DISPOSE.substring(2);
    boolean                     disposed;
    long                        runTime;
    protected boolean           handleException = true;
    protected Object            cancelRunner;
    protected ScriptingEvent    event;
    protected RuntimeException  executionException;
    protected WidgetContext     runContext;
    @Weak
    protected iScriptHandler    scriptHandler;
    // protected boolean string;
    protected Object         theResult;
    protected Object         theScript;
    protected String         theSource;
    private volatile boolean canceled;
    private boolean          cancelerOnEventThread;
    private volatile boolean done;
    private boolean          notifierOnEventThread;
    private Object           notifierRunner;

    /**
     * Constructs a new instance
     */
    public EvalRunner(iScriptHandler sh, WidgetContext context, String source, ScriptingEvent e) {
      scriptHandler = sh;
      runContext    = context;
      theSource     = source;
      event         = e;
    }

    /**
     * Constructs a new instance
     */
    public EvalRunner(iScriptHandler sh, WidgetContext context, Object script, boolean string, ScriptingEvent e) {
      scriptHandler = sh;
      runContext    = context;
      theScript     = script;
      event         = e;
    }

    @Override
    public Object call() throws Exception {
      run();

      return getResult();
    }

    @Override
    public void cancel(boolean canInterrupt) {
      Runnable r = getRunnable(cancelRunner);

      canceled = true;

      try {
        if (theScript instanceof aCompiledScript) {
          ((aCompiledScript) theScript).cancel(canInterrupt);
        }

        if (r != null) {
          if (cancelerOnEventThread) {
            Platform.invokeLater(r);
          } else {
            r.run();
          }
        }
      } finally {
        dispose();
      }
    }

    @Override
    public void dispose() {
      if (!disposed) {
        disposed           = true;
        scriptHandler      = null;
        theSource          = null;
        event              = null;
        theScript          = null;
        theResult          = null;
        executionException = null;
        notifierRunner     = null;
        cancelRunner       = null;
      }
    }

    @Override
    public void run() {
      canceled  = false;
      theResult = null;
      runTime   = System.currentTimeMillis();

      WindowViewer win = (WindowViewer) scriptHandler.getWindowViewer();

      if ((win == null) || (runContext.scriptObject == null)) {
        return;
      }

      iWidget w = runContext.getWidget();

      if (w != null) {
        iViewer v = w.getViewer();

        if (v == null) {
          return;    // orphaned widget; can happen when we are reloading
        }

        if (v instanceof WindowViewer) {
          win = (WindowViewer) v;
        } else {
          win = v.getWindow();
        }

        // if the context no longer exists and this is an invoke later event the
        // change the context to the current window
        if ((win == null) && (event != null) && (event.isInvokeLater() || DISPOSE_EVENT_TYPE.equals(event.getType()))) {
          win = (WindowViewer) scriptHandler.getWindowViewer();
          w   = win;
        }

        if (win == null) {
          return;    // orphaned widget; can happen when we are reloading
        }
      }

      ScriptingEvent se = win.getEvent();

      if ((event != null) && Platform.isUIThread()) {
        win.setEventEx(event);
      }

      Runnable nr = getRunnable(notifierRunner);

      done = false;

      Throwable exception = null;

      try {
        theResult = executeCode(win, w);
      } catch(final Throwable e) {
        exception = e;
      }

      done = true;

      if (Platform.isUIThread()) {
        win.setEventEx(se);
      }

      if (exception != null) {
        cancelRunner = null;

        if (handleException) {
          final iWidget   ww = (w == null)
                               ? win
                               : w;
          final Throwable ex = exception;
          Runnable        r  = new Runnable() {
            @Override
            public void run() {
              if (ww.isDisposed()) {
                throw ApplicationException.runtimeException(ex);
              } else {
                ww.handleException(ApplicationException.runtimeException(ex));
              }
            }
          };

          Platform.invokeLater(r);
        } else {
          executionException = ApplicationException.runtimeException(exception);
        }
      }

      if (!canceled && (nr != null)) {
        if (notifierOnEventThread) {
          Platform.invokeLater(nr);
        } else {
          nr.run();
        }
      }
    }

    @Override
    public void setCancelRunner(Object code, boolean runOnEventThread) {
      if (code != this) {
        cancelRunner          = code;
        cancelerOnEventThread = runOnEventThread;
      }
    }

    @Override
    public void setHandleException(boolean handle) {
      handleException = handle;
    }

    @Override
    public void setNotifierRunner(Object code, boolean runOnEventThread) {
      if (code != this) {
        notifierRunner        = code;
        notifierOnEventThread = runOnEventThread;
      }
    }

    public Object getCancelRunner() {
      return cancelRunner;
    }

    @Override
    public Throwable getExecutionException() {
      return executionException;
    }

    public Object getNotifierRunner() {
      return notifierRunner;
    }

    @Override
    public Object getResult() {
      if (executionException != null) {
        throw executionException;
      }

      Object o = theResult;

      theResult = null;

      return o;
    }

    public String getResultString() {
      if (executionException != null) {
        throw executionException;
      }

      return (String) theResult;
    }

    @Override
    public boolean isCanceled() {
      return canceled;
    }

    @Override
    public boolean isDisposed() {
      return disposed;
    }

    @Override
    public boolean isDone() {
      return done || canceled;
    }

    protected Object executeCode(WindowViewer win, iWidget w) throws Exception {
      ScriptEngine e = (ScriptEngine) runContext.scriptEngine;

      if (e == null) {
        e = (ScriptEngine) win.getScriptingContext().scriptEngine;
      }

      ScriptContext scriptContext = (ScriptContext) runContext.scriptContext;

      if (scriptContext == null) {
        scriptContext = (ScriptContext) win.getScriptingContext().scriptContext;
      }

      Object          savedWidget         = null;
      Object          savedForm           = null;
      Object          savedName           = null;
      Object          form                = (w == null)
              ? null
              : w.getFormViewer();
      final boolean   backgroundExecution = !Platform.isUIThread();
      aScriptManager  sm                  = (aScriptManager) scriptHandler;
      aCompiledScript cs                  = (aCompiledScript) theScript;
      boolean         save                = ((cs == null) || cs.usesBindings) &&!backgroundExecution;

      try {
        if (save) {
          savedWidget = scriptContext.getAttribute(DynamicBindings.WIDGET, ScriptContext.ENGINE_SCOPE);
          savedForm   = scriptContext.getAttribute(DynamicBindings.FORM, ScriptContext.ENGINE_SCOPE);
          scriptContext.setAttribute(DynamicBindings.WIDGET, w, ScriptContext.ENGINE_SCOPE);
          scriptContext.setAttribute(DynamicBindings.FORM, form, ScriptContext.ENGINE_SCOPE);
          sm.saveCurrentWindow(scriptContext, runContext);
          sm.setWindow(scriptContext, win);
        }

        if (save && (runContext.scriptName != null)) {
          savedName = scriptContext.getAttribute(ScriptEngine.FILENAME, ScriptContext.ENGINE_SCOPE);
          scriptContext.setAttribute(ScriptEngine.FILENAME, runContext.scriptName, ScriptContext.ENGINE_SCOPE);
          sm.executingScriptName = runContext.scriptName;
        }

        if (cs != null) {
          if (sm.tracingEnabled) {
            sm.handleScriptTracing(theScript.toString());
          }

          if (preExecuteCode != null) {
            ((CompiledScript) preExecuteCode).eval(scriptContext);
          }

          cs.setEnvironment((aScriptManager) scriptHandler, runContext, event);

          Object o = cs.eval(scriptContext);

          if (postExecuteCode != null) {
            ((CompiledScript) postExecuteCode).eval(scriptContext);
          }

          return o;
        } else {
          if (sm.tracingEnabled) {
            sm.handleScriptTracing(theSource);
          }

          return e.eval(theSource, scriptContext);
        }
      } finally {
        if (save) {
          sm.restoreSaveWindow(scriptContext, runContext);
          scriptContext.setAttribute("widget", savedWidget, ScriptContext.ENGINE_SCOPE);
          scriptContext.setAttribute("form", savedForm, ScriptContext.ENGINE_SCOPE);

          if (runContext.scriptName != null) {
            sm.executingScriptName = (String) savedName;
            scriptContext.setAttribute(ScriptEngine.FILENAME, savedName, ScriptContext.ENGINE_SCOPE);
          }
        }
      }
    }

    protected void notifyAll(Object o) {}

    private Runnable getRunnable(Object o) {
      if ((o == null) || (o instanceof Runnable)) {
        return (Runnable) o;
      }

      return scriptHandler.createRunner(runContext, o, event);
    }
  }


  protected static class JSFunctionScript extends aCompiledScript {
    Object _this;
    Object function;

    public JSFunctionScript(ScriptEngine engine, ScriptContext context, Object f) {
      super(engine, context);
      this.function = f;
    }

    @Override
    public Object eval(ScriptContext context) throws ScriptException {
      if (context == null) {
        context = engine.getContext();
      }

      return scriptManager.InvokeNativeScriptFunctionObject(function, engine, context, _this);
    }

    @Override
    public String toString() {
      return function.toString();
    }

    @Override
    public void setEnvironment(aScriptManager sm, WidgetContext wc, ScriptingEvent event) {
      super.setEnvironment(sm, wc, event);
      _this = wc.scriptObject;
    }
  }


  protected static class RunnableScript extends aCompiledScript {
    Runnable runnable;

    public RunnableScript(ScriptEngine engine, ScriptContext context, Runnable r) {
      super(engine, context);
      this.runnable = r;
    }

    @Override
    public void cancel(boolean interrupt) {
      if (runnable instanceof iCancelable) {
        try {
          ((iCancelable) runnable).cancel(interrupt);
        } catch(Throwable ignore) {}
      }
    }

    @Override
    public Object eval(ScriptContext context) throws ScriptException {
      runnable.run();

      return null;
    }

    @Override
    public String toString() {
      return runnable.toString();
    }
  }


  protected static class EventHandlerInterfaceScript extends aCompiledScript {
    EventHandlerInterface handler;
    iEventHandler         eventHandler;
    EventObject           nativeEvent;
    String                eventName;

    public EventHandlerInterfaceScript(ScriptEngine engine, ScriptContext context, EventHandlerInterface handler) {
      super(engine, context);
      this.handler = handler;
//      this.eventHandler=handler.getHandler(); //hold on to this so that it does not get garbage collected
      usesBindings = false;
    }

    @Override
    public void setEnvironment(aScriptManager sm, WidgetContext wc, ScriptingEvent event) {
      super.setEnvironment(sm, wc, event);
      this.nativeEvent = event.getUIEvent();
      eventName        = event.getType();
    }

    @Override
    public Object eval(ScriptContext context) throws ScriptException {
      try {
        handler.callHandler(eventName, widgetContext.getWidget(), nativeEvent);
      } finally {
        nativeEvent = null;
      }

      return null;
    }
  }


  /**
   *
   *
   * @version 0.3, 2007-05-14
   * @author Don DeCoteau
   */
  protected static abstract class aCompiledScript extends CompiledScript {
    @Weak
    ScriptContext  context;
    @Weak
    ScriptEngine   engine;
    @Weak
    aScriptManager scriptManager;
    String         scriptName;
    @Weak
    WidgetContext  widgetContext;
    boolean        usesBindings = true;

    public aCompiledScript(ScriptEngine engine, ScriptContext context) {
      this.engine  = engine;
      this.context = context;
    }

    public void cancel(boolean interrupt) {}

    @Override
    public Object eval() throws ScriptException {
      return eval(context);
    }

    @Override
    public Object eval(Bindings bindings) throws ScriptException {
      Bindings      ob = null;
      Object        value;
      ScriptContext ctx = (context == null)
                          ? engine.getContext()
                          : context;

      if (bindings != null) {
        ob = ctx.getBindings(ScriptContext.ENGINE_SCOPE);
        ctx.setBindings(bindings, ScriptContext.ENGINE_SCOPE);
      }

      try {
        value = eval(ctx);
      } finally {
        if (ob != null) {
          ctx.setBindings(ob, ScriptContext.ENGINE_SCOPE);
        }
      }

      return value;
    }

    public void setEnvironment(aScriptManager sm, WidgetContext wc, ScriptingEvent event) {
      scriptManager = sm;
      widgetContext = wc;
    }

    @Override
    public ScriptEngine getEngine() {
      return engine;
    }
  }
}
