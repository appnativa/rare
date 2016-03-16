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

import com.appnativa.rare.widget.iWidget;

import com.google.j2objc.annotations.Weak;

/**
 *
 * @author Don DeCoteau
 */
public class WidgetContext {
  @Weak
  public Object  languageObject;
  @Weak
  public Object  scriptContext;
  @Weak
  public Object  scriptEngine;
  public String  scriptName;
  @Weak
  public Object  scriptObject;
  public boolean needsJSRetention;
  boolean        releaseEngine;

  public WidgetContext() {}

  public void dispose() {
    if (releaseEngine && (scriptEngine != null)) {
      aScriptManager.releaseEngine(scriptEngine);
    }

    scriptObject   = null;
    scriptContext  = null;
    scriptName     = null;
    scriptEngine   = null;
    languageObject = null;
  }

  public boolean hasLanguageObject() {
    return languageObject != null;
  }

  public iWidget getWidget() {
    if (scriptObject instanceof iWidget) {
      return (iWidget) scriptObject;
    }

    return null;
  }

  public Object getLanguageObject() {
    return languageObject;
  }

  public void setLanguageObject(Object languageObject) {
    this.languageObject = languageObject;
  }
}
