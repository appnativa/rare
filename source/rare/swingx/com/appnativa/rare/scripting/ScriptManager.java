/*
 * @(#)ScriptManager.java   2011-11-19
 * 
 * Copyright (c) 2007-2009 appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.scripting;

import java.io.InputStream;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;

import com.appnativa.rare.Platform;
import com.appnativa.rare.iPlatformAppContext;
import com.appnativa.rare.spot.Application;
import com.appnativa.rare.ui.Frame;
import com.appnativa.rare.ui.iWindow;
import com.appnativa.rare.viewer.WindowViewer;
import com.appnativa.util.Streams;

/**
 *
 * @author Don DeCoteau
 */
public class ScriptManager extends aJavaScriptManager {
  static ScriptingShell      scriptingShell;
  static boolean             shellEnabled;
  static ShellEngineProvider shellEngineProvider;
  public static String       jsInitCode;

  /**
   * Constructs a new instance
   * 
   * @param ctx
   *          the application context
   * @param app
   *          the application configuration
   */
  public ScriptManager(iPlatformAppContext ctx, Application app) {
    super(ctx, app);
  }

  public ScriptManager(iPlatformAppContext app, ScriptEngine engine, Bindings bindings, Object frame, String scriptName,
      String source) {
    super(app, engine, bindings, frame, scriptName, source);
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

    return new ScriptManager(app, engine, share ? mainWindowBindings : null, frame, name, source);
  }

 @Override
protected void addLoadedScript(String name) {
  }

  @Override
  protected void configureDebuggingInfo(ScriptEngine engine, ScriptContext ctx, String name, String source) {
  }

  @Override
  protected WindowViewer createWindowViewer(iPlatformAppContext ctx, String name, Object win, WindowViewer parent, iScriptHandler sh) {
    WindowViewer v = new WindowViewer(ctx, name, (iWindow) win, parent, sh);
    if (win instanceof Frame) {
      ((Frame) win).setViewer(v);
    }
    return v;
  }

  @Override
  protected void loadDebugger() {
  }

  @Override
  protected void setupDebugger() {
  }

  @Override
  protected void setupScriptingShell(ScriptEngine engine) {
    if (shellPort > 999) {
      if (scriptingShell == null) {
        shellEngineProvider = new ShellEngineProvider(engineManager, this);
        scriptingShell = new ScriptingShell(shellEngineProvider);
        scriptingShell.launch(shellPort);
      } else if ((shellEngineProvider.scriptManager == null) || (shellEngineProvider.scriptManager.theWindow == null)) {
        shellEngineProvider.setManager(this, engine);
      }
    }

  }

  @Override
  protected String spiClassNameForJavascripEngineFactory() {
    return null;
  }

  @Override
  protected String getInitScript(String name) {
    if (jsInitCode != null && "ecmascript.init".equals(name)) {
      return jsInitCode;
    }
    String s = (Platform.class.getPackage().getName() + ".resources.raw.rare_raw_").replace('.', '/') + name;
    try {
      InputStream in = Package.class.getResourceAsStream("/" + s);
      if (in != null) {
        return Streams.streamToString(in);
      }
    } catch (Exception e) {
      Platform.ignoreException(s, e);
    }
    return null;
  }

}
