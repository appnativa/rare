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

import com.appnativa.rare.platform.EventHelper;
import com.appnativa.rare.ui.UIPoint;

/**
 *
 * @author Don DeCoteau
 */
public class MouseEvent extends InputEvent {
  public static final int FLING      = 2;
  public static final int LONG_PRESS = 1;
  public static int       SCROLL     = 4;
  protected int           modifiers  = 0;
  protected Object        endEvent;
  protected float         gestureX;
  protected float         gestureY;
  protected Object        startEvent;

  public MouseEvent(Object source, Object me, int modifiers) {
    super(source, me);
    startEvent     = endEvent = me;
    this.modifiers = modifiers;
  }

  public MouseEvent(Object source, Object me) {
    this(source, me, 0);
  }

  public MouseEvent(Object source, Object me, int modifiers, Object me1, float x, float y) {
    super(source, me1);
    startEvent     = me;
    endEvent       = me1;
    this.modifiers = modifiers;
    this.gestureX  = x;
    this.gestureY  = y;
  }

  @Override
  public void consume() {
    consumed = true;
  }

  @Override
  public String toString() {
    return "modifiers=" + modifiers + ", motionEvent=" + endEvent.toString();
  }

  public int getClickCount() {
    return EventHelper.getClickCount(getSource(), endEvent);
  }

  public float getEndX() {
    return getX();
  }

  public float getEndY() {
    return getY();
  }

  /**
   * @return the gestureX
   */
  public float getGestureX() {
    return gestureX;
  }

  /**
   * @return the gestureY
   */
  public float getGestureY() {
    return gestureY;
  }

  public UIPoint getLocationInWindow() {
    return EventHelper.getMouseLocationInWindow(getSource(), endEvent);
  }

  public UIPoint getLocationOnScreen() {
    return EventHelper.getMouseLocationOnScreen(getSource(), endEvent);
  }

  /**
   * @return the modifiers
   */
  public int getModifiers() {
    return modifiers;
  }

  public Object getNativeEndEvent() {
    return endEvent;
  }

  @Override
  public Object getNativeEvent() {
    return endEvent;
  }

  public Object getNativeStartEvent() {
    return startEvent;
  }

  public UIPoint getPoint() {
    return EventHelper.getMousePoint(getSource(), endEvent);
  }

  public float getScreenX() {
    return EventHelper.getMouseXOnScreen(getSource(), endEvent);
  }

  public float getScreenY() {
    return EventHelper.getMouseYOnScreen(getSource(), endEvent);
  }

  public float getStartX() {
    return getX();
  }

  public float getStartY() {
    return getY();
  }

  public long getWhen() {
    return EventHelper.getEventTime(getSource(), endEvent);
  }

  public float getWindowX() {
    return EventHelper.getMouseXInWindow(getSource(), endEvent);
  }

  public float getWindowY() {
    return EventHelper.getMouseYInWindow(getSource(), endEvent);
  }

  public float getX() {
    return EventHelper.getMouseX(getSource(), endEvent);
  }

  public float getY() {
    return EventHelper.getMouseY(getSource(), endEvent);
  }

  public boolean isFling() {
    return (modifiers & FLING) > 0;
  }

  public boolean isLongPress() {
    return (modifiers & LONG_PRESS) > 0;
  }

  public boolean isPopupTrigger() {
    return isLongPress();
  }

  public boolean isScroll() {
    return (modifiers & SCROLL) > 0;
  }
}
