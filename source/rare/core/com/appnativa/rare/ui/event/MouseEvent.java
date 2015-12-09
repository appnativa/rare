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
  public static final int FLING          = 0x2;
  public static final int LONG_PRESS     = 0x1;
  public static final int SCROLL         = 0x3;
  public static final int PRESS_CANCELED = 0x4;
  protected int           modifiers      = 0;
  protected Object        endEvent;
  protected float         gestureX;
  protected float         gestureY;
  protected Object        startEvent;
  private int             clickCount = 0;

  public static enum Type {
    MOUSE_UNKNOWN, LONG_PRESS, SCROLL, FLING, MOUSE_DOWN, MOUSE_UP, MOUSE_CLICK, MOUSE_DBLCLICK, MOUSE_DRAG,
    MOUSE_MOVE, MOUSE_ENTER, MOUSE_EXIT, MOUSE_SCALE, PRESS_CANCELED
  }

  public MouseEvent(Object source, Object me) {
    this(source, me, 0);
  }

  public MouseEvent(Object source, Object me, int modifiers) {
    super(source, me);
    startEvent     = endEvent = me;
    this.modifiers = modifiers;
  }

  public MouseEvent(Object source, Object me, int modifiers, int clickCount) {
    this(source, me, modifiers);
    this.clickCount = clickCount;
  }

  public Type getEventType() {
    if ((modifiers & LONG_PRESS) != 0) {
      return Type.LONG_PRESS;
    }

    if ((modifiers & FLING) != 0) {
      return Type.FLING;
    }

    if ((modifiers & SCROLL) != 0) {
      return Type.SCROLL;
    }

    if ((modifiers & PRESS_CANCELED) != 0) {
      return Type.PRESS_CANCELED;
    }

    if (clickCount > 1) {
      return Type.MOUSE_DBLCLICK;
    }

    if (clickCount > 1) {
      return Type.MOUSE_CLICK;
    }

    return EventHelper.getMouseEventType(getSource(), endEvent);
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

  public int getClickCount() {
    return (clickCount == 0)
           ? EventHelper.getClickCount(startEvent, endEvent)
           : clickCount;
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
    return (modifiers & FLING) != 0;
  }

  public boolean isLongPress() {
    return (modifiers & LONG_PRESS) != 0;
  }

  public boolean isPressCanceled() {
    return (modifiers & PRESS_CANCELED) != 0;
  }

  public boolean isPopupTrigger() {
    return isLongPress();
  }

  public boolean isScroll() {
    return (modifiers & SCROLL) != 0;
  }

  /**
   * Returns whether of the this event represents and event of the specified type
   *
   * @param type the type
   * @return true if it does; false otherwise
   */
  public boolean isType(Type type) {
    return getEventType() == type;
  }

  @Override
  public String toString() {
    return "modifiers=" + modifiers + ", motionEvent=" + endEvent.toString();
  }
}
