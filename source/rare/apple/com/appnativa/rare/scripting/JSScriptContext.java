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

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.SimpleScriptContext;

public class JSScriptContext extends SimpleScriptContext {
  public JSScriptContext() {}

  public JSScriptContext(Bindings engineScope) {
    super(engineScope);
  }

  @Override
  public void setAttribute(String name, Object value, int scope) {
    if (name.indexOf('.') != -1) {    //theses attributes are only called b internal code
      scope = ScriptContext.GLOBAL_SCOPE;
    }

    super.setAttribute(name, value, scope);
  }

  @Override
  public Object getAttribute(String name, int scope) {
    if (name.indexOf('.') != -1) {    //theses attributes are only called b internal code
      scope = ScriptContext.GLOBAL_SCOPE;
    }

    return super.getAttribute(name, scope);
  }
}
