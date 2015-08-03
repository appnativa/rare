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
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.util.CharScanner;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class JavaPackage implements iScriptingContextSupport {
  private WidgetContext           scriptingContext;
  static Map<String, JavaPackage> packages = new ConcurrentHashMap<String, JavaPackage>();

  static {
    packages.put("com", new JavaPackage("com"));
    packages.put("org", new JavaPackage("org"));
    packages.put("java", new JavaPackage("java"));
    packages.put("javax", new JavaPackage("javax"));
  }

  Map<String, JavaPackage> children = new ConcurrentHashMap<String, JavaPackage>();
  String                   name;
  JavaPackage              parent;

  public JavaPackage(String name) {
    this.name = name;
  }

  public JavaPackage(String name, JavaPackage parent) {
    this.name   = name;
    this.parent = parent;
  }

  public static JavaPackage importPackage(String name) {
    if (name.indexOf(',') == -1) {
      return addPackage(name);
    }

    List<String> list = CharScanner.getTokens(name, ',', true);
    int          len  = list.size();

    name = list.get(0);

    if (name.length() == 0) {
      return null;
    }

    JavaPackage p = addPackage(name);

    for (int i = 1; i < len; i++) {
      name = list.get(i);

      if (name.length() == 0) {
        return p;
      }

      p = p.addChildPackage(name);
    }

    return p;
  }

  @Override
  public String toString() {
    if (parent == null) {
      return name;
    }

    return parent.toString() + "." + name;
  }

  public static JavaPackage getPackage(String name) {
    return packages.get(name);
  }

  public Object getProperty(String name) {
    String lc = name.toLowerCase(Locale.getDefault());

    if (!lc.equals(name)) {
      try {
        return PlatformHelper.loadClass(this.toString() + "." + name);
      } catch(ClassNotFoundException e) {
        return null;
      }
    }

    JavaPackage p = children.get(name);

    if (p == null) {
      p = new JavaPackage(name, this);
      children.put(name, p);
    }

    return p;
  }

  private JavaPackage addChildPackage(String name) {
    JavaPackage p = children.get(name);

    if (p == null) {
      p = new JavaPackage(name);
      children.put(name, p);
    }

    return p;
  }

  private static JavaPackage addPackage(String name) {
    JavaPackage p = packages.get(name);

    if (p == null) {
      p = new JavaPackage(name);
      packages.put(name, p);
    }

    return p;
  }

  @Override
  public WidgetContext getScriptingContext() {
    if (scriptingContext == null) {
      scriptingContext = Platform.getAppContext().getScriptingManager().createScriptingContext(this);
    }

    return scriptingContext;
  }
}
