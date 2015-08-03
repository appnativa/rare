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

import com.appnativa.rare.platform.apple.ui.view.View;
import com.appnativa.rare.ui.UIPoint;
/*-[
#import "com/appnativa/rare/platform/apple/ui/view/View.h"
#import "APView+Component.h"
]-*/
import com.appnativa.rare.ui.event.MouseEvent;
import com.appnativa.rare.ui.event.ScaleGestureObject;
import com.appnativa.rare.ui.event.UITouch;

/**
 * Event helper for the platform.
 *
 * @author Don DeCoteau
 *
 */
public class EventHelper {
  public EventHelper() {}

  public native static UITouch getTouch(Object source, Object motionEvent)
  /*-[
    if([motionEvent isKindOfClass:[RAREUITouch class]]) {
      return (RAREUITouch*)motionEvent;
    }

    RAREUITouch* t=[RAREUITouch new];
    NSSet* set=[((UIEvent*)motionEvent) allTouches];
    if(!set) {
      return t;
    }

    UITouch* touch=[set anyObject];
    UIWindow *window = touch.window;
    UIView* view=(UIView*)((RAREView*)source)->proxy_;
    CGPoint p = [touch locationInView:view];
    t->x_=p.x;
    t->y_=p.y;

    p = [touch locationInView:nil];
    CGPoint wp= [UIScreen convertPoint:p withScreen:window.screen];
    t->wx_=wp.x;
    t->wy_=wp.y;

    p.x += window.frame.origin.x;
    p.y += window.frame.origin.y;
    p = [UIScreen convertPoint: p withScreen:window.screen];
    t->sx_=p.x;
    t->sy_=p.y;

    t->clickCount_=touch.tapCount;
    t->time_=((UIEvent*)motionEvent).timestamp*1000;
    return t;
  ]-*/
  ;

  public static MouseEvent retarget(MouseEvent e, View view) {
    return new MouseEvent(view, e.getNativeStartEvent(), e.getModifiers(), e.getNativeEndEvent(), e.getGestureX(),
                          e.getGestureY());
  }

  public static int getClickCount(Object source, Object motionEvent) {
    return ((UITouch) motionEvent).clickCount;
  }

  public static long getEventTime(Object source, Object motionEvent) {
    return ((UITouch) motionEvent).time;
  }

  public static int getKeyChar(Object keyEvent) {
    return 0;
  }

  public static int getKeyCode(Object keyEvent) {
    return 0;
  }

  public static UIPoint getMouseLocationInWindow(Object source, Object motionEvent) {
    UITouch t = (UITouch) motionEvent;

    return new UIPoint(t.wx, t.wy);
  }

  public static UIPoint getMouseLocationOnScreen(Object source, Object motionEvent) {
    UITouch t = (UITouch) motionEvent;

    return new UIPoint(t.sx, t.sy);
  }

  public static UIPoint getMousePoint(Object source, Object motionEvent) {
    UITouch t = (UITouch) motionEvent;

    return new UIPoint(t.x, t.y);
  }

  public static float getMouseX(Object source, Object motionEvent) {
    return ((UITouch) motionEvent).x;
  }

  public static float getMouseXInWindow(Object source, Object motionEvent) {
    return ((UITouch) motionEvent).wx;
  }

  public static float getMouseXOnScreen(Object source, Object motionEvent) {
    return ((UITouch) motionEvent).sx;
  }

  public static float getMouseY(Object source, Object motionEvent) {
    return ((UITouch) motionEvent).y;
  }

  public static float getMouseYInWindow(Object source, Object motionEvent) {
    return ((UITouch) motionEvent).wy;
  }

  public static float getMouseYOnScreen(Object source, Object motionEvent) {
    return ((UITouch) motionEvent).sy;
  }

  public static float getScaleCurrentSpan(Object scaleGestureDetector) {
    ScaleGestureObject gp = (ScaleGestureObject) scaleGestureDetector;

    return gp.getCurrentSpan();
  }

  public static float getScaleFocusX(Object scaleGestureDetector) {
    ScaleGestureObject gp = (ScaleGestureObject) scaleGestureDetector;

    return gp.getFocusX();
  }

  public static float getScaleFocusY(Object scaleGestureDetector) {
    ScaleGestureObject gp = (ScaleGestureObject) scaleGestureDetector;

    return gp.getFocusY();
  }

  public static float getScalePreviousSpan(Object scaleGestureDetector) {
    ScaleGestureObject gp = (ScaleGestureObject) scaleGestureDetector;

    return gp.getPreviousSpan();
  }

  public static int getWhich(Object keyEvent) {
    return 0;
  }

  public static boolean isAltKeyPressed(Object keyEvent) {
    return false;
  }

  public static boolean isBackspaceKeyPressed(Object keyEvent) {
    return false;
  }

  public static boolean isDeleteKeyPressed(Object keyEvent) {
    return false;
  }

  public static boolean isDownArrowKeyPressed(Object keyEvent) {
    return false;
  }

  public static boolean isEndKeyPressed(Object keyEvent) {
    return false;
  }

  public static boolean isEnterKeyPressed(Object keyEvent) {
    return false;
  }

  public static boolean isEscapeKeyPressed(Object keyEvent) {
    return false;
  }

  public static boolean isHomeKeyPressed(Object keyEvent) {
    return false;
  }

  public static boolean isLeftArrowKeyPressed(Object keyEvent) {
    return false;
  }

  public static boolean isLeftnArrowKeyPressed(Object keyEvent) {
    return false;
  }

  public static boolean isMetaKeyPressed(Object keyEvent) {
    return false;
  }

  public static boolean isPageDownKeyPressed(Object keyEvent) {
    return false;
  }

  public static boolean isPageUpKeyPressed(Object keyEvent) {
    return false;
  }

  public static boolean isRightArrowKeyPressed(Object keyEvent) {
    return false;
  }

  public static boolean isShiftKeyPressed(Object keyEvent) {
    return false;
  }

  public static boolean isTabKeyPressed(Object keyEvent) {
    return false;
  }

  public static boolean isUpArrowKeyPressed(Object keyEvent) {
    return false;
  }
}
