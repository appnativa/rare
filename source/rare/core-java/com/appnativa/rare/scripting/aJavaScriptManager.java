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
import com.appnativa.rare.iPlatformAppContext;
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.spot.Application;
import com.appnativa.util.Streams;

import com.sun.phobos.script.javascript.RhinoScriptEngine;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import java.lang.ref.WeakReference;

import java.net.URL;

import java.util.Properties;
import java.util.WeakHashMap;
import java.util.concurrent.FutureTask;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.SimpleScriptContext;

public abstract class aJavaScriptManager extends aScriptManager {
  protected static WeakHashMap<String, WeakReference<String>> codeMap;
  private static volatile Properties                          resources;

  public aJavaScriptManager() {
    super();
  }

  public aJavaScriptManager(iPlatformAppContext ctx, Application app) {
    super(ctx, app);
  }

  public aJavaScriptManager(iPlatformAppContext app, ScriptEngine engine, Bindings bindings, Object frame,
                            String scriptName, String source) {
    super(app, engine, bindings, frame, scriptName, source);
  }

  public static void createEngineManager(ClassLoader loader, int shellPort) {
    if (engineManager == null) {
      aScriptManager.shellPort = shellPort;
    }

    engineManager = new ScriptEngineManager(loader);
  }

  @Override
  protected ScriptContext createScriptContext(ScriptEngine e) {
    return new SimpleScriptContext();
  }

  @Override
  protected void initializeEngine(ScriptEngine engine, ScriptContext context) {
    checkForRhino(engine);

    if (context != null) {
      try {
        engine.eval("importClass(com.appnativa.rare.ui.canvas.Image)", context);
      } catch(Exception e) {
        Platform.debugLog(e.toString());
      }
    }
  }

  public static Reader getResourceAsReader(String file) throws IOException {
    String s = aScriptManager.class.getName();
    int    n = s.lastIndexOf('.');

    if (n != -1) {
      s = s.substring(0, n);
    }

    String name = "/" + s + "/";

    name = name.replace('.', '/') + file;

    URL url = aScriptManager.class.getResource(name);

    if (url == null) {
      return null;
    }

    InputStream in = url.openStream();

    return new InputStreamReader(in);
  }

  public static InputStream getResourceAsStream(String file) throws IOException {
    String s = aScriptManager.class.getName();
    int    n = s.lastIndexOf('.');

    if (n != -1) {
      s = s.substring(0, n);
    }

    String name = "/" + s + "/";

    name = name.replace('.', '/') + file;

    URL url = aScriptManager.class.getResource(name);

    if (url == null) {
      return null;
    }

    return url.openStream();
  }

  public static String getResourceAsString(String name) {
    if (resources == null) {
      resources = new Properties();

      try {
        InputStream in = getResourceAsStream("scripting.properties");

        if (in != null) {
          resources.load(in);
        }
      } catch(Exception ex) {
        return "missing scripting.properties file";
      }
      // ResourceBundle.getBundle(Constants.class.getPackage().getName() + ".scripting"); //jdk1.6 bug
    }

    return resources.getProperty(name);
  }

  public static String getResourceStreamAsString(String file) throws IOException {
    return Streams.readerToString(getResourceAsReader(file));
  }

  @Override
  public Object createVariableValue(WidgetContext context, Object javaobj) {
    if (javaobj instanceof org.mozilla.javascript.Script) {
      RhinoScriptEngine se = (RhinoScriptEngine) context.scriptEngine;
      ScriptContext     sc = (ScriptContext) context.scriptContext;

      if (sc == null) {
        sc = scriptContext;
      }

      return RhinoContextFactory.wrapCompiledFunction((org.mozilla.javascript.Script) javaobj, this,
              se.getRuntimeScope(sc));
    }

    return javaobj;
  }

  @Override
  public Object unwrap(Object o) {
    return (o == null)
           ? null
           : RhinoContextFactory.unwrap(o);
  }

  public static String getInlineScriptSource(String name) {
    if ((name == null) || (codeMap == null)) {
      return null;
    }

    synchronized(codeMap) {
      WeakReference<String> r = codeMap.get(name);

      return (r == null)
             ? null
             : r.get();
    }
  }

  @Override
  protected Object InvokeNativeScriptFunctionObject(Object function, ScriptEngine engine, ScriptContext context,
          Object scriptObject) {
    return RhinoContextFactory.callFunction(function, this, ((RhinoScriptEngine) engine).getRuntimeScope(context),
            scriptObject);
  }

  protected void checkForRhino(ScriptEngine engine) {
    if (!_rhinoInitialized
        && engine.getFactory().getClass().getName().equals(
          "com.sun.phobos.script.javascript.RhinoScriptEngineFactory")) {
      initializeRhino();
    }
  }

  protected void initializeRhino() {
    RhinoContextFactory.initialize(PlatformHelper.getApplicationClassLoader());
    _rhinoInitialized = true;
  }

  @Override
  protected void invokeAndWait(iScriptRunnable r) {
    try {
      FutureTask f = new FutureTask(r, null);

      Platform.invokeLater(f);
      f.get();
    } catch(Exception e) {
      throw ApplicationException.runtimeException(e);
    }
  }

  @Override
  protected void saveSourceForDebugging(String scriptName, String source) {
    if (codeMap != null) {
      synchronized(codeMap) {
        codeMap.put(scriptName, new WeakReference<String>(source));
      }
    }
  }

  @Override
  protected ErrorInformation getNativeScriptErrorInformation(Object error) {
    return RhinoContextFactory.getErrorInformation(error);
  }

  @Override
  protected boolean isNativeScriptFunctionObject(Object function) {
    return RhinoContextFactory.isFunctionObject(function);
  }

  @Override
  public boolean releaseEngineEx(ScriptEngine e) {
    e.getBindings(ScriptContext.ENGINE_SCOPE).clear();

    return true;
  }
}
