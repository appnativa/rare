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
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.util.CharScanner;

import com.google.j2objc.annotations.Weak;

import java.util.EventObject;
import java.util.Map;

/**
 *
 * @author Don DeCoteau
 */
public class EventBase extends EventObject {
  protected boolean             consumed;
  protected boolean             failureEvent;
  protected Object              nativeEvent;
  private static Object         fakeSource = new Object();
  protected Map<String, String> queryMap;
  protected String              queryString;
  @Weak
  private Object                source_;

  public EventBase(Object source) {
    super(fakeSource);
    source_ = source;
  }

  public EventBase(Object source, Object nativeEvent) {
    super(fakeSource);
    source_          = source;
    this.nativeEvent = nativeEvent;
  }

  public EventBase(Object source, int id, Object nativeEvent) {
    this(source, id);
    this.nativeEvent = nativeEvent;
  }

  public void consume() {
    consumed = true;
  }

  public void markAsFailureEvent() {
    failureEvent = true;
  }

  /**
   * @param consumed the consumed to set
   */
  public void setConsumed(boolean consumed) {
    this.consumed = consumed;
  }

  public iPlatformComponent getComponent() {
    return Platform.findPlatformComponent(source_);
  }

  public iWidget getWidget() {
    if (source_ instanceof iWidget) {
      return (iWidget) source_;
    }

    iPlatformComponent c = getComponent();

    return (c == null)
           ? null
           : Platform.findWidgetForComponent(c);
  }

  public Object getNativeEvent() {
    return (nativeEvent == null)
           ? this
           : nativeEvent;
  }

  /**
   * @return the consumed
   */
  public boolean isConsumed() {
    return consumed;
  }

  public boolean isFailureEvent() {
    return failureEvent;
  }

  @Override
  public Object getSource() {
    return source_;
  }

  public String getQueryString() {
    return queryString;
  }

  public void setQueryString(String queryString) {
    this.queryString = queryString;
  }

  public Map<String, String> getQueryMap() {
    if (queryMap == null) {
      if (queryString != null) {
        queryMap = CharScanner.parseOptionStringEx(queryString, '&');
      }
    }

    return queryMap;
  }
}
