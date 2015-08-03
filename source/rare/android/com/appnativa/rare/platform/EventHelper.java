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

package com.appnativa.rare.platform;

import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

import com.appnativa.rare.ui.UIPoint;

public final class EventHelper {
  public EventHelper() {
    // TODO Auto-generated constructor stub
  }

  public static int getClickCount(Object source, Object motionEvent) {
    return 1;
  }

  public static long getEventTime(Object source, Object inputEvent) {
    if (inputEvent instanceof KeyEvent) {
      return ((KeyEvent) inputEvent).getEventTime();
    }

    if (inputEvent instanceof MotionEvent) {
      return ((MotionEvent) inputEvent).getEventTime();
    }

    return 0;
  }

  public static int getKeyChar(Object keyEvent) {
    return ((KeyEvent) keyEvent).getUnicodeChar();
  }

  public static int getKeyCode(Object keyEvent) {
    return ((KeyEvent) keyEvent).getKeyCode();
  }

  public static UIPoint getMouseLocationInWindow(Object source, Object motionEvent) {
    MotionEvent e = (MotionEvent) motionEvent;

    return new UIPoint(e.getRawX(), e.getRawY());
  }

  public static UIPoint getMouseLocationOnScreen(Object source, Object motionEvent) {
    MotionEvent e = (MotionEvent) motionEvent;

    return new UIPoint(e.getRawX(), e.getRawY());
  }

  public static UIPoint getMousePoint(Object source, Object motionEvent) {
    return new UIPoint(((MotionEvent) motionEvent).getX(), ((MotionEvent) motionEvent).getY());
  }

  public static float getMouseX(Object source, Object motionEvent) {
    return ((MotionEvent) motionEvent).getX();
  }

  public static float getMouseXInWindow(Object source, Object motionEvent) {
    return ((MotionEvent) motionEvent).getRawX();
  }

  public static float getMouseXOnScreen(Object source, Object motionEvent) {
    return ((MotionEvent) motionEvent).getRawX();
  }

  public static float getMouseY(Object source, Object motionEvent) {
    return ((MotionEvent) motionEvent).getY();
  }

  public static float getMouseYInWindow(Object source, Object motionEvent) {
    return ((MotionEvent) motionEvent).getRawY();
  }

  public static float getMouseYOnScreen(Object source, Object motionEvent) {
    return ((MotionEvent) motionEvent).getRawY();
  }

  public static float getScaleCurrentSpan(Object scaleGestureDetector) {
    return ((ScaleGestureDetector) scaleGestureDetector).getCurrentSpan();
  }

  public static float getScaleFactor(Object scaleGestureDetector) {
    return ((ScaleGestureDetector) scaleGestureDetector).getScaleFactor();
  }

  public static float getScaleFocusX(Object scaleGestureDetector) {
    return ((ScaleGestureDetector) scaleGestureDetector).getFocusX();
  }

  public static float getScaleFocusY(Object scaleGestureDetector) {
    return ((ScaleGestureDetector) scaleGestureDetector).getFocusY();
  }

  public static float getScalePreviousSpan(Object scaleGestureDetector) {
    return ((ScaleGestureDetector) scaleGestureDetector).getPreviousSpan();
  }

  public static int getWhich(Object keyEvent) {
    int n = 0;

    if (((KeyEvent) keyEvent).getAction() == android.view.KeyEvent.ACTION_UP) {
      n = getKeyChar(keyEvent);
    } else {
      n = getKeyCode(keyEvent);
    }

    return (n == 10)
           ? 13
           : n;
  }

  public static boolean isAltKeyPressed(Object keyEvent) {
    return ((KeyEvent) keyEvent).isAltPressed();
  }

  public static boolean isBackspaceKeyPressed(Object keyEvent) {
    return false;
  }

  public static boolean isDeleteKeyPressed(Object keyEvent) {
    return ((KeyEvent) keyEvent).getKeyCode() == KeyEvent.KEYCODE_DEL;
  }

  public static boolean isDownArrowKeyPressed(Object keyEvent) {
    return ((KeyEvent) keyEvent).getKeyCode() == KeyEvent.KEYCODE_DPAD_DOWN;
  }

  public static boolean isEndKeyPressed(Object keyEvent) {
    return false;
  }

  public static boolean isEnterKeyPressed(Object keyEvent) {
    int c = ((KeyEvent) keyEvent).getKeyCode();

    return (c == KeyEvent.KEYCODE_ENTER) || (c == KeyEvent.KEYCODE_DPAD_CENTER);
  }

  public static boolean isEscapeKeyPressed(Object keyEvent) {
    return ((KeyEvent) keyEvent).getKeyCode() == android.view.KeyEvent.KEYCODE_BACK;
  }

  public static boolean isHomeKeyPressed(Object keyEvent) {
    return ((KeyEvent) keyEvent).getKeyCode() == KeyEvent.KEYCODE_HOME;
  }

  public static boolean isLeftArrowKeyPressed(Object keyEvent) {
    return ((KeyEvent) keyEvent).getKeyCode() == KeyEvent.KEYCODE_DPAD_LEFT;
  }

  public static boolean isMetaKeyPressed(Object keyEvent) {
    return ((KeyEvent) keyEvent).isSymPressed();
  }

  public static boolean isPageDownKeyPressed(Object keyEvent) {
    return false;
  }

  public static boolean isPageUpKeyPressed(Object keyEvent) {
    return false;
  }

  public static boolean isRightArrowKeyPressed(Object keyEvent) {
    return ((KeyEvent) keyEvent).getKeyCode() == KeyEvent.KEYCODE_DPAD_RIGHT;
  }

  public static boolean isShiftKeyPressed(Object keyEvent) {
    return ((KeyEvent) keyEvent).isShiftPressed();
  }

  public static boolean isTabKeyPressed(Object keyEvent) {
    return ((KeyEvent) keyEvent).getKeyCode() == KeyEvent.KEYCODE_TAB;
  }

  public static boolean isUpArrowKeyPressed(Object keyEvent) {
    return ((KeyEvent) keyEvent).getKeyCode() == KeyEvent.KEYCODE_DPAD_UP;
  }
}
