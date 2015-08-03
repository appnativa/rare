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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;

public class JavaScriptEngineFactory implements ScriptEngineFactory {
  private static List<String> extensions;
  private static List<String> mimeTypes;
  private static List<String> names;

  static {
    names = new ArrayList<String>(7);
    names.add("js");
    names.add("JavaScript");
    names.add("javascript");
    names.add("ECMAScript");
    names.add("ecmascript");
    names     = Collections.unmodifiableList(names);
    mimeTypes = new ArrayList<String>(4);
    mimeTypes.add("application/javascript");
    mimeTypes.add("application/ecmascript");
    mimeTypes.add("text/javascript");
    mimeTypes.add("text/ecmascript");
    mimeTypes  = Collections.unmodifiableList(mimeTypes);
    extensions = new ArrayList<String>(1);
    extensions.add("js");
    extensions = Collections.unmodifiableList(extensions);
  }

  private boolean initialized;

  public void destroy() {
    if (initialized) {
      initialized = false;
    }
  }

  public void initialize() {
    if (!initialized) {
      initialized = true;
    }
  }

  public void registerWithEngineManager(ScriptEngineManager manager) {
    manager.registerEngineExtension("js", this);

    Iterator<String> it = mimeTypes.iterator();

    while(it.hasNext()) {
      manager.registerEngineMimeType(it.next(), this);
    }

    it = names.iterator();

    while(it.hasNext()) {
      manager.registerEngineName(it.next(), this);
    }
  }

  @Override
  public String getEngineName() {
    return "JAvaScriptCore-embedded";
  }

  @Override
  public String getEngineVersion() {
    return "1.0";
  }

  @Override
  public List<String> getExtensions() {
    return extensions;
  }

  @Override
  public String getLanguageName() {
    return "ECMAScript";
  }

  @Override
  public String getLanguageVersion() {
    return "1.8";
  }

  @Override
  public String getMethodCallSyntax(String obj, String method, String[] args) {
    String ret = obj + "." + method + "(";
    int    len = args.length;

    if (len == 0) {
      ret += ")";

      return ret;
    }

    for (int i = 0; i < len; i++) {
      ret += args[i];

      if (i != len - 1) {
        ret += ",";
      } else {
        ret += ")";
      }
    }

    return ret;
  }

  @Override
  public List<String> getMimeTypes() {
    return mimeTypes;
  }

  @Override
  public List<String> getNames() {
    return names;
  }

  @Override
  public String getOutputStatement(String toDisplay) {
    return "print(" + toDisplay + ")";
  }

  @Override
  public Object getParameter(String key) {
    if (key.equals(ScriptEngine.NAME)) {
      return "javascript";
    } else if (key.equals(ScriptEngine.ENGINE)) {
      return "JavaScriptCore for Rare";
    } else if (key.equals(ScriptEngine.ENGINE_VERSION)) {
      return "1.8";
    } else if (key.equals(ScriptEngine.LANGUAGE)) {
      return "ECMAScript";
    } else if (key.equals(ScriptEngine.LANGUAGE_VERSION)) {
      return "1.8";
    } else if (key.equals("THREADING")) {
      return "MULTITHREADED";
    } else {
      throw new IllegalArgumentException("Invalid key");
    }
  }

  @Override
  public String getProgram(String[] statements) {
    int    len = statements.length;
    String ret = "";

    for (int i = 0; i < len; i++) {
      ret += statements[i] + ";";
    }

    return ret;
  }

  @Override
  public ScriptEngine getScriptEngine() {
    JavaScriptEngine ret = new JavaScriptEngine(this);

    return ret;
  }
}
