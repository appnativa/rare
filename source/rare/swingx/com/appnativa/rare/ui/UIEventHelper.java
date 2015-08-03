/*
 * @(#)UIEventHelper.java   2011-11-19
 * 
 * Copyright (c) 2007-2009 appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.ui;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;


public final class UIEventHelper {
  public static int getKeyChar(Object keyEvent) {
    return  ((KeyEvent) keyEvent).getKeyChar();
  }

  public static int getKeyCode(Object keyEvent) {
    return  ((KeyEvent) keyEvent).getKeyCode();
  }

  public static UIPoint getMousePoint(Object motionEvent) {
    return new UIPoint(((MouseEvent) motionEvent).getX(), ((MouseEvent) motionEvent).getY());
  }

  public static int getMouseX(Object motionEvent) {
    return ((MouseEvent) motionEvent).getX();
  }

  public static int getMouseXOnScreen(Object motionEvent) {
    return ((MouseEvent) motionEvent).getXOnScreen();
  }

  public static int getMouseY(Object motionEvent) {
    return ((MouseEvent) motionEvent).getY();
  }

  public static int getMouseYOnScreen(Object motionEvent) {
    return ((MouseEvent) motionEvent).getYOnScreen();
  }
  public static int getWhich(Object keyEvent) {
    int n = 0;
    int et=((KeyEvent) keyEvent).getID();
    if (et == KeyEvent.KEY_PRESSED || et == KeyEvent.KEY_TYPED) {
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

  public static boolean isEscapeKeyPressed(Object keyEvent) {
    return ((KeyEvent) keyEvent).getKeyCode() == KeyEvent.VK_ESCAPE;
  }

  public static boolean isMetaKeyPressed(Object keyEvent) {
    return ((KeyEvent) keyEvent).isMetaDown();
  }

  public static boolean isShiftKeyPressed(Object keyEvent) {
    return ((KeyEvent) keyEvent).isShiftDown();
  }
	public static boolean isEnterKeyPressed(Object keyEvent) {
    return ((KeyEvent) keyEvent).getKeyCode()==KeyEvent.VK_ENTER;
	}

	public static boolean isTabKeyPressed(Object keyEvent) {
    return ((KeyEvent) keyEvent).getKeyCode()==KeyEvent.VK_TAB;
	}

	public static boolean isBackspaceKeyPressed(Object keyEvent) {
    return ((KeyEvent) keyEvent).getKeyCode()==KeyEvent.VK_BACK_SPACE;
	}

	public static boolean isDeleteKeyPressed(Object keyEvent) {
    return ((KeyEvent) keyEvent).getKeyCode()==KeyEvent.VK_DELETE;
	}
	public static boolean isHomeKeyPressed(Object keyEvent) {
    return ((KeyEvent) keyEvent).getKeyCode()==KeyEvent.VK_HOME;
	}

	public static boolean isEndKeyPressed(Object keyEvent) {
    return ((KeyEvent) keyEvent).getKeyCode()==KeyEvent.VK_END;
	}

	public static boolean isPageDownKeyPressed(Object keyEvent) {
    return ((KeyEvent) keyEvent).getKeyCode()==KeyEvent.VK_PAGE_DOWN;
	}

	public static boolean isPageUpKeyPressed(Object keyEvent) {
    return ((KeyEvent) keyEvent).getKeyCode()==KeyEvent.VK_PAGE_UP;
	}

	public static boolean isDownArrowKeyPressed(Object keyEvent) {
    return ((KeyEvent) keyEvent).getKeyCode()==KeyEvent.VK_DOWN;
	}

	public static boolean isUpArrowKeyPressed(Object keyEvent) {
    return ((KeyEvent) keyEvent).getKeyCode()==KeyEvent.VK_UP;
	}
	public static boolean isLeftnArrowKeyPressed(Object keyEvent) {
    return ((KeyEvent) keyEvent).getKeyCode()==KeyEvent.VK_LEFT;
	}

	public static boolean isRightArrowKeyPressed(Object keyEvent) {
    return ((KeyEvent) keyEvent).getKeyCode()==KeyEvent.VK_RIGHT;
	}

  public static boolean isLeftArrowKeyPressed(Object keyEvent) {
    throw new UnsupportedOperationException("Not yet implemented");
  }

  public static float getScalePreviousSpan(Object scaleGestureDetector) {
    throw new UnsupportedOperationException("Not yet implemented");
  }

  public static float getScaleCurrentSpan(Object scaleGestureDetector) {
    throw new UnsupportedOperationException("Not yet implemented");
  }

  public static int getScaleFocusX(Object scaleGestureDetector) {
    throw new UnsupportedOperationException("Not yet implemented");
  }

  public static int getScaleFocusY(Object scaleGestureDetector) {
    throw new UnsupportedOperationException("Not yet implemented");
  }
}
