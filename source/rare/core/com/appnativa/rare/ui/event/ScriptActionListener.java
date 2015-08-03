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

package com.appnativa.rare.ui.event;

import com.appnativa.rare.Platform;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.scripting.iScriptHandler;
import com.appnativa.rare.ui.aWidgetListener;
import com.appnativa.rare.widget.iWidget;

import com.google.j2objc.annotations.Weak;

/**
 *
 * @author Don DeCoteau
 */
public class ScriptActionListener implements iActionListener {
  Object  code;
  @Weak
  iWidget context;
  @Weak
  Object  source;

  public ScriptActionListener(iWidget context, String code) {
    this.context = context;
    this.code    = code;
  }

  public ScriptActionListener(iWidget context, Object source, String code) {
    this.context = context;
    this.code    = code;
    this.source  = source;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    iWidget w = this.context;

    if (w == null) {
      w = Platform.getContextRootViewer();
    }

    iScriptHandler sh = w.getScriptHandler();

    if (code instanceof String) {
      code = sh.compile(w.getScriptingContext(), "scriptActionListener", (String) code);
    }

    Object s = (this.source == null)
               ? w
               : this.source;

    aWidgetListener.execute(context, sh, code, iConstants.EVENT_ACTION, new ActionEvent(s, "onAction"));
  }
}
