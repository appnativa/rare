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

import com.appnativa.rare.Platform;
import com.appnativa.rare.platform.swing.PlatformImpl;
import com.appnativa.rare.platform.swing.ui.ScaleGestureDetector;
import com.appnativa.rare.ui.UIPoint;
import com.appnativa.rare.ui.iPlatformComponent;

import java.awt.Point;
import java.awt.Window;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

public final class EventHelper {
  public static int getClickCount(Object source, Object motionEvent) {
    return ((MouseEvent) motionEvent).getClickCount();
  }

  public static long getEventTime(Object source, Object nativeEvent) {
    if (nativeEvent instanceof InputEvent) {
      return ((InputEvent) nativeEvent).getWhen();
    }

    return 0;
  }

  public static int getKeyChar(Object keyEvent) {
    return ((KeyEvent) keyEvent).getKeyChar();
  }

  public static int getKeyCode(Object keyEvent) {
    return ((KeyEvent) keyEvent).getKeyCode();
  }

  public static UIPoint getMouseLocationInWindow(Object source, Object motionEvent) {
    iPlatformComponent c = ((PlatformImpl) Platform.getPlatform()).getPlatformComponent(source);
    Point              p = ((MouseEvent) motionEvent).getPoint();
    Window             w = (c == null)
                           ? null
                           : SwingUtilities.getWindowAncestor(c.getView());

    if (w != null) {
      p = SwingUtilities.convertPoint(c.getView(), p, w);
    }

    return new UIPoint(p.x, p.y);
  }

  public static UIPoint getMouseLocationOnScreen(Object source, Object motionEvent) {
    Point p = ((MouseEvent) motionEvent).getLocationOnScreen();

    return new UIPoint(p.x, p.y);
  }

  public static UIPoint getMousePoint(Object source, Object motionEvent) {
    return new UIPoint(((MouseEvent) motionEvent).getX(), ((MouseEvent) motionEvent).getY());
  }

  public static float getMouseX(Object source, Object motionEvent) {
    return ((MouseEvent) motionEvent).getX();
  }

  public static float getMouseXInWindow(Object source, Object motionEvent) {
    iPlatformComponent c = ((PlatformImpl) Platform.getPlatform()).getPlatformComponent(source);
    Point              p = ((MouseEvent) motionEvent).getPoint();
    Window             w = (c == null)
                           ? null
                           : SwingUtilities.getWindowAncestor(c.getView());

    if (w != null) {
      p = SwingUtilities.convertPoint(c.getView(), p, w);
    }

    return p.x;
  }

  public static float getMouseXOnScreen(Object source, Object motionEvent) {
    return ((MouseEvent) motionEvent).getXOnScreen();
  }

  public static float getMouseY(Object source, Object motionEvent) {
    return ((MouseEvent) motionEvent).getY();
  }

  public static float getMouseYInWindow(Object source, Object motionEvent) {
    iPlatformComponent c = ((PlatformImpl) Platform.getPlatform()).getPlatformComponent(source);
    Point              p = ((MouseEvent) motionEvent).getPoint();
    Window             w = (c == null)
                           ? null
                           : SwingUtilities.getWindowAncestor(c.getView());

    if (w != null) {
      p = SwingUtilities.convertPoint(c.getView(), p, w);
    }

    return p.y;
  }

  public static float getMouseYOnScreen(Object source, Object motionEvent) {
    return ((MouseEvent) motionEvent).getYOnScreen();
  }

  public static float getScaleCurrentSpan(Object sgd) {
    return ((ScaleGestureDetector) sgd).getCurrentSpan();
  }

  public static float getScaleFocusX(Object sgd) {
    return ((ScaleGestureDetector) sgd).getFocusX();
  }

  public static float getScaleFocusY(Object sgd) {
    return ((ScaleGestureDetector) sgd).getFocusY();
  }

  public static float getScalePreviousSpan(Object sgd) {
    return ((ScaleGestureDetector) sgd).getPreviousSpan();
  }

  public static int getWhich(Object keyEvent) {
    int n  = 0;
    int et = ((KeyEvent) keyEvent).getID();

    if ((et == KeyEvent.KEY_PRESSED) || (et == KeyEvent.KEY_TYPED)) {
      n = getKeyChar(keyEvent);
    } else {
      n = getKeyCode(keyEvent);
    }

    return (n == 10)
           ? 13
           : n;
  }

  public static boolean isAltKeyPressed(Object keyEvent) {
    return ((KeyEvent) keyEvent).isAltDown();
  }

  public static boolean isBackspaceKeyPressed(Object keyEvent) {
    return ((KeyEvent) keyEvent).getKeyCode() == KeyEvent.VK_BACK_SPACE;
  }

  public static boolean isDeleteKeyPressed(Object keyEvent) {
    return ((KeyEvent) keyEvent).getKeyCode() == KeyEvent.VK_DELETE;
  }

  public static boolean isDownArrowKeyPressed(Object keyEvent) {
    return ((KeyEvent) keyEvent).getKeyCode() == KeyEvent.VK_DOWN;
  }

  public static boolean isEndKeyPressed(Object keyEvent) {
    return ((KeyEvent) keyEvent).getKeyCode() == KeyEvent.VK_END;
  }

  public static boolean isEnterKeyPressed(Object keyEvent) {
    return ((KeyEvent) keyEvent).getKeyCode() == KeyEvent.VK_ENTER;
  }

  public static boolean isEscapeKeyPressed(Object keyEvent) {
    return ((KeyEvent) keyEvent).getKeyCode() == KeyEvent.VK_ESCAPE;
  }

  public static boolean isHomeKeyPressed(Object keyEvent) {
    return ((KeyEvent) keyEvent).getKeyCode() == KeyEvent.VK_HOME;
  }

  public static boolean isLeftArrowKeyPressed(Object keyEvent) {
    return ((KeyEvent) keyEvent).getKeyCode() == KeyEvent.VK_LEFT;
  }

  public static boolean isMetaKeyPressed(Object keyEvent) {
    return ((KeyEvent) keyEvent).isMetaDown();
  }

  public static boolean isPageDownKeyPressed(Object keyEvent) {
    return ((KeyEvent) keyEvent).getKeyCode() == KeyEvent.VK_PAGE_DOWN;
  }

  public static boolean isPageUpKeyPressed(Object keyEvent) {
    return ((KeyEvent) keyEvent).getKeyCode() == KeyEvent.VK_PAGE_UP;
  }

  public static boolean isRightArrowKeyPressed(Object keyEvent) {
    return ((KeyEvent) keyEvent).getKeyCode() == KeyEvent.VK_RIGHT;
  }

  public static boolean isShiftKeyPressed(Object keyEvent) {
    return ((KeyEvent) keyEvent).isShiftDown();
  }

  public static boolean isTabKeyPressed(Object keyEvent) {
    return ((KeyEvent) keyEvent).getKeyCode() == KeyEvent.VK_TAB;
  }

  public static boolean isUpArrowKeyPressed(Object keyEvent) {
    return ((KeyEvent) keyEvent).getKeyCode() == KeyEvent.VK_UP;
  }

  public static com.appnativa.rare.ui.event.MouseEvent createMouseEvent(Object source, java.awt.event.MouseEvent e) {
    int m = 0;

    if (e.isPopupTrigger()) {
      m = com.appnativa.rare.ui.event.MouseEvent.LONG_PRESS;
    }

    return new com.appnativa.rare.ui.event.MouseEvent(source, e, m);
  }
}
