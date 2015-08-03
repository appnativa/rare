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

package com.appnativa.rare.ui.listener;

import com.appnativa.rare.iConstants;
import com.appnativa.rare.scripting.iScriptHandler;
import com.appnativa.rare.ui.aWidgetListener;
import com.appnativa.rare.ui.event.iChangeListener;
import com.appnativa.rare.widget.iWidget;

import java.util.EventObject;

public class WidgetScriptChangeListener implements iChangeListener {
  int             direction = 1;
  private Object  code;
  private iWidget widget;
  private String  eventName = "_OnScrollBarChange";

  public WidgetScriptChangeListener(String code) {
    this.code = code;
  }

  public WidgetScriptChangeListener(iWidget widget, Object code) {
    this(widget, code, null);
  }

  public WidgetScriptChangeListener(iWidget widget, Object code, String eventName) {
    this.widget = widget;
    this.code   = code;
    setEventName(eventName);
  }

  public void setEventName(String name) {
    if (name != null) {
      eventName = name;
    }
  }

  /**
   * @param code the code to set
   */
  public void setCode(Object code) {
    this.code = code;
  }

  public void setWidget(iWidget widget) {
    this.widget = widget;
  }

  /**
   * @return the code
   */
  public Object getCode() {
    return code;
  }

  public iWidget getWidget() {
    return widget;
  }

  @Override
  public void stateChanged(EventObject e) {
    iWidget        w  = getWidget();
    iScriptHandler sh = w.getScriptHandler();

    if (code instanceof String) {
      code = sh.compile(w.getScriptingContext(), w.getName() + eventName, (String) code);
    }

    aWidgetListener.evaluate(w, sh, code, iConstants.EVENT_CHANGE, e);
  }
}
