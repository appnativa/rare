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
import com.appnativa.rare.iPlatformAppContext;
import com.appnativa.rare.viewer.iContainer;
import com.appnativa.rare.viewer.iTarget;
import com.appnativa.rare.viewer.iViewer;
import com.appnativa.util.FilterableList;
import com.appnativa.util.Helper;
import com.appnativa.util.Streams;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import java.net.URL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;

/**
 *
 * @author Don DeCoteau
 */
public class ShellEngineProvider implements iShellEngineProvider, iScriptingEngine {

  /**  */
  ArrayList<String> loadedScripts = new ArrayList<String>();

  /**  */
  ScriptEngine currentEngine;

  /**  */
  private iScriptingIO scriptingIO;

  /**  */
  protected ScriptEngineManager engineManager;

  /**  */
  protected List<String>   languageNames;
  protected aScriptManager scriptManager;
  protected ScriptContext  currentContext;
  protected boolean        tracing;

  /**
   * Creates a new instance of ShellEngineProvider
   *
   *
   * Creates a new instance of ShellEngineProvider
   *
   * @param sm engine manager
   */
  public ShellEngineProvider(ScriptEngineManager sem, aScriptManager sm) {
    engineManager = sem;
    scriptManager = sm;

    List<String>              names = new ArrayList<String>();
    List<ScriptEngineFactory> list  = engineManager.getEngineFactories();
    ScriptEngineFactory       f;
    int                       len = list.size();

    for (int i = 0; i < len; i++) {
      f = list.get(i);
      names.add(f.getLanguageName() + "/" + f.getEngineName() + " " + f.getEngineVersion() + "/"
                + f.getLanguageVersion());
    }

    languageNames = Collections.unmodifiableList(names);
  }

  public void addLoadedScript(String script) {
    loadedScripts.add(script);
  }

  @Override
  public void dumpScript(String script) {
    try {
      iPlatformAppContext app = scriptManager.getAppContext();
      URL                 url = app.getURL(script);
      Reader              r   = app.getReader(url);

      getScriptingIO().getWriter().println(Streams.readerToString(r));
    } catch(Throwable e) {
      e.printStackTrace(getScriptingIO().getErrorWriter());
    }
  }

  public void setManager(aScriptManager sm, ScriptEngine e) {
    scriptManager  = sm;
    currentEngine  = e;
    currentContext = (ScriptContext) scriptManager.getScriptingContext();
    loadedScripts.clear();

    if (getScriptingIO() != null) {
      currentContext.setWriter(getScriptingIO().getWriter());
      currentContext.setErrorWriter(getScriptingIO().getErrorWriter());
      currentContext.setReader(getScriptingIO().getReader());
    }
  }

  @Override
  public iScriptingEngine getDefaultEngine(iScriptingIO sio) {
    if (engineManager != null) {
      setScriptingIO(sio);
      currentEngine  = scriptManager.getScriptingEngine();
      currentContext = (ScriptContext) scriptManager.getScriptingContext();
      currentContext.setWriter(sio.getWriter());
      currentContext.setErrorWriter(sio.getErrorWriter());
      currentContext.setReader(sio.getReader());

      return this;
    }

    return null;
  }

  @Override
  public iScriptingEngine getEngine(String language, iScriptingIO sio) {
    ScriptEngine e = scriptManager.getEngine(language, true, (ScriptContext) scriptManager.getScriptingContext());

    if (e == null) {
      return null;
    }

    setScriptingIO(sio);
    currentEngine  = e;
    currentContext = (ScriptContext) scriptManager.getScriptingContext();
    currentContext.setWriter(sio.getWriter());
    currentContext.setErrorWriter(sio.getErrorWriter());
    currentContext.setReader(sio.getReader());

    return this;
  }

  @Override
  public List<String> getLoadedScripts() {
    return loadedScripts;
  }

  @Override
  public List<String> getTargets() {
    iTarget a[] = scriptManager.getAppContext().getWindowManager().getTargets();

    if (a == null) {
      return Collections.emptyList();
    }

    int                    len  = a.length;
    FilterableList<String> list = new FilterableList<String>();

    for (int i = 0; i < len; i++) {
      list.add(a[i].getName());
    }

    return list;
  }

  @Override
  public List<String> getViewers() {
    iViewer a[] = scriptManager.getAppContext().getWindowManager().getViewers();

    if ((a == null) || (a.length == 0)) {
      return Collections.emptyList();
    }

    FilterableList<String> list = new FilterableList<String>();
    String                 name;

    for (iViewer v : a) {
      name = v.getName();

      if (name != null) {
        list.add(name);
      } else {
        Platform.debugLog("null viewer name:" + v.getClass().getName());
      }
    }

    return list;
  }

  @Override
  public List<String> getWidgetNames(String name) {
    iViewer v = scriptManager.getAppContext().getWindowManager().getViewer(name);
    String  s = String.format(Constants.TEXT_RESOURCE_NOT_FOUND, "viewer", "name");

    if (v == null) {
      throw new RuntimeException(s);
    }

    if (v instanceof iContainer) {
      return ((iContainer) v).getWidgetNames();
    }

    return Collections.EMPTY_LIST;
  }

  /**
   * {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  public int availableEngines() {
    return languageNames.size();
  }

  @Override
  public Map envVariables() {
    return currentContext.getBindings(ScriptContext.ENGINE_SCOPE);
  }

  @Override
  public void eval(String line) throws Exception {
    Object result = currentEngine.eval(line, currentContext);

    if (result != null) {
      getScriptingIO().getWriter().println(result.toString());
    }
  }

  @Override
  public Object get(String name) {
    return currentContext.getAttribute(name);
  }

  public String getFirstLanguage() {
    return languageNames.get(0);
  }

  @Override
  public Map getGlobalVariables() {
    return currentContext.getBindings(ScriptContext.GLOBAL_SCOPE);
  }

  @Override
  public String getLanguageName() {
    return currentEngine.getFactory().getLanguageName();
  }

  @Override
  public String getLanguageFullName() {
    ScriptEngineFactory f = currentEngine.getFactory();

    return f.getLanguageName() + "/" + f.getEngineName() + " " + f.getEngineVersion();
  }

  @Override
  public List<String> getLanguages() {
    return languageNames;
  }

  @Override
  public void loadScript(String script) {
    try {
      URL         url = Platform.getAppContext().createURL(Platform.getContextRootViewer(), script);
      InputStream in  = url.openStream();

      script = url.toString();
      currentEngine.put(ScriptEngine.FILENAME, script);

      if (!loadedScripts.contains(script)) {
        loadedScripts.add(script);
      }

      currentEngine.eval(new InputStreamReader(in), currentContext);
    } catch(Throwable e) {
      Helper.pealException(e).printStackTrace(getScriptingIO().getErrorWriter());
    }
  }

  @Override
  public void trace(String arg1) {
    if (arg1 == null) {
      tracing = !tracing;
    } else {
      tracing = !arg1.equalsIgnoreCase("false");
    }
  }

  iScriptingEngine switchEngine(String language) throws IOException {
    if (getEngine(language, getScriptingIO()) == null) {
      getScriptingIO().getErrorWriter().println(String.format(Constants.TEXT_ENGINE_NOT_FOUND, language));

      return null;
    } else {
      getScriptingIO().getWriter().println(String.format(Constants.TEXT_ENGINE_LOADED, language));

      return this;
    }
  }

  /**
   * @return the scriptingIO
   */
  public

  /**  */
  iScriptingIO getScriptingIO() {
    return scriptingIO;
  }

  /**
   * @param scriptingIO the scriptingIO to set
   */
  public void setScriptingIO(iScriptingIO scriptingIO) {
    this.scriptingIO = scriptingIO;
  }

  public aScriptManager getScriptManager() {
    return scriptManager;
  }
}
