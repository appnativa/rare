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

package com.appnativa.rare.platform.android.ui.view;

import android.annotation.SuppressLint;

import android.content.Context;

import android.graphics.Canvas;

import android.util.AttributeSet;

import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;

import android.widget.ScrollView;

import com.appnativa.rare.platform.android.ui.util.AndroidGraphics;
import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIPoint;
import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.iScrollerSupport;
import com.appnativa.rare.ui.painter.UIScrollingEdgePainter;
import com.appnativa.rare.ui.painter.iPainterSupport;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;
import com.appnativa.rare.ui.painter.iPlatformPainter;
import com.appnativa.util.IdentityArrayList;

import java.util.List;

/**
 *
 * @author Don DeCoteau
 */
public class ScrollViewEx extends ScrollView implements iPainterSupport, iScrollerSupport {
  iPlatformComponentPainter      componentPainter;
  int                            nLoc[];
  UIRectangle                    rRect;
  IdentityArrayList<View>        scrollerViews;
  protected AndroidGraphics      graphics;
  private UIDimension            size = new UIDimension();
  private boolean                scrolling;
  private UIScrollingEdgePainter scrollingEdgePainter;

  public ScrollViewEx(Context context) {
    super(context);

    if (UIScrollingEdgePainter.isPaintVerticalScrollEdge()) {
      scrollingEdgePainter = UIScrollingEdgePainter.getInstance();
    }
  }

  public ScrollViewEx(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public void addScrollerViewEx(View view) {
    if (view != this) {
      if (scrollerViews == null) {
        scrollerViews = new IdentityArrayList<View>();
        nLoc          = new int[2];
        rRect         = new UIRectangle();
      }

      scrollerViews.addIfNotPresent(view);
    }
  }

  public void dispose() {
    if (scrollerViews != null) {
      scrollerViews.clear();
    }

    scrollerViews = null;
  }

  public void draw(Canvas canvas) {
    graphics = AndroidGraphics.fromGraphics(canvas, this, graphics);

    final iPlatformComponentPainter cp = componentPainter;
    int                             x  = getScrollX();
    int                             y  = getScrollY();
    int                             w  = getWidth();
    int                             h  = getHeight();

    if (cp == null) {
      super.draw(canvas);
    } else {
      cp.paint(graphics, x, y, w, h, iPlatformPainter.UNKNOWN, false);
      super.draw(canvas);
      cp.paint(graphics, x, y, w, h, iPlatformPainter.UNKNOWN, true);
    }

    if (scrollingEdgePainter != null) {
      h -= (getPaddingTop() + getPaddingBottom());
      w -= (getPaddingLeft() + getPaddingRight());
      x += getPaddingLeft();
      y += getPaddingTop();
      scrollingEdgePainter.paint(graphics, x, y, w, h, true);
    }

    graphics.clear();
  }

  public iPlatformComponentPainter getComponentPainter() {
    return componentPainter;
  }

  public UIPoint getContentOffset() {
    UIPoint p = new UIPoint();

    if (getChildCount() > 0) {
      View v = getChildAt(0);

      p.x = v.getScrollX();
      p.y = v.getScrollY();
    }

    return p;
  }

  public boolean isAtBottomEdge() {
    if (getChildCount() > 0) {
      View v = getChildAt(0);

      return v.getHeight() - v.getScrollY() <= getHeight();
    }

    return true;
  }

  public boolean isAtLeftEdge() {
    return true;
  }

  public boolean isAtRightEdge() {
    return true;
  }

  public boolean isAtTopEdge() {
    if (getChildCount() > 0) {
      return getScrollY() == 0;
    }

    return true;
  }

  @Override
  public void setScrollingEdgePainter(UIScrollingEdgePainter painter) {
    scrollingEdgePainter = painter;
  }

  @Override
  public UIScrollingEdgePainter getScrollingEdgePainter() {
    return scrollingEdgePainter;
  }

  protected boolean isScrollerTouched(int x, int y) {
    if (scrollerViews == null) {
      return false;
    }

    int               loc[] = nLoc;
    final List<View>  views = scrollerViews;
    final int         len   = views.size();
    final UIRectangle r     = rRect;

    for (int i = 0; i < len; i++) {
      View v = views.get(i);

      v.getLocationOnScreen(loc);
      r.x      = loc[0];
      r.y      = loc[1];
      r.width  = v.getWidth();
      r.height = v.getHeight();

      if (r.contains(x, y)) {
        return true;
      }
    }

    return false;
  }

  public boolean isScrolling() {
    return scrolling;
  }

  protected void measureChild(View child, int parentWidthMeasureSpec, int parentHeightMeasureSpec) {
    Component c = Component.fromView(child);

    if (c == null) {
      super.measureChild(child, parentWidthMeasureSpec, parentHeightMeasureSpec);

      return;
    }

    final UIDimension s         = size;
    int               maxWidth  = -1;
    int               maxHeight = -1;

    if (MeasureSpec.getMode(parentWidthMeasureSpec) != MeasureSpec.UNSPECIFIED) {
      maxWidth = MeasureSpec.getSize(parentWidthMeasureSpec) - getPaddingLeft() - getPaddingRight();
    }

    if (MeasureSpec.getMode(parentHeightMeasureSpec) != MeasureSpec.UNSPECIFIED) {
      maxHeight = MeasureSpec.getSize(parentHeightMeasureSpec) - getPaddingTop() - getPaddingBottom();
    }

    c.getSizeConstraints(s, maxWidth, maxHeight, true);

    if (s.width != -1) {
      parentWidthMeasureSpec = MeasureSpec.makeMeasureSpec(s.intWidth(), MeasureSpec.EXACTLY);
    }

    if (s.height != -1) {
      parentHeightMeasureSpec = MeasureSpec.makeMeasureSpec(s.intHeight(), MeasureSpec.EXACTLY);
    }

    super.measureChild(child, parentWidthMeasureSpec, parentHeightMeasureSpec);
  }

  protected void measureChildWithMargins(View child, int parentWidthMeasureSpec, int widthUsed,
          int parentHeightMeasureSpec, int heightUsed) {
    Component c = Component.fromView(child);

    if (c == null) {
      super.measureChild(child, parentWidthMeasureSpec, parentHeightMeasureSpec);

      return;
    }

    final UIDimension s         = size;
    int               maxWidth  = -1;
    int               maxHeight = -1;

    if (MeasureSpec.getMode(parentWidthMeasureSpec) != MeasureSpec.UNSPECIFIED) {
      maxWidth = MeasureSpec.getSize(parentWidthMeasureSpec) - getPaddingLeft() - getPaddingRight();
    }

    if (MeasureSpec.getMode(parentHeightMeasureSpec) != MeasureSpec.UNSPECIFIED) {
      maxHeight = MeasureSpec.getSize(parentHeightMeasureSpec) - getPaddingTop() - getPaddingBottom();
    }

    c.getSizeConstraints(s, maxWidth, maxHeight, true);

    if (s.width != -1) {
      parentWidthMeasureSpec = MeasureSpec.makeMeasureSpec(s.intWidth(), MeasureSpec.EXACTLY);
    }

    if (s.height != -1) {
      parentHeightMeasureSpec = MeasureSpec.makeMeasureSpec(s.intHeight(), MeasureSpec.EXACTLY);
    }

    super.measureChildWithMargins(child, parentWidthMeasureSpec, widthUsed, parentHeightMeasureSpec, heightUsed);
  }

  protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    ViewHelper.onAttachedToWindow(this);
  }

  protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    ViewHelper.onDetachedFromWindow(this);
  }

  @Override
  public boolean onInterceptTouchEvent(MotionEvent ev) {
    if (isScrollerTouched((int) ev.getRawX(), (int) ev.getRawY())) {
      return false;
    }

    return super.onInterceptTouchEvent(ev);
  }

  @Override
  protected void onScrollChanged(int l, int t, int oldl, int oldt) {
    super.onScrollChanged(l, t, oldl, oldt);
    scrolling = true;
  }

  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    ViewHelper.onSizeChanged(this, w, h, oldw, oldh);
  }

  @SuppressLint("ClickableViewAccessibility")
  @Override
  public boolean onTouchEvent(MotionEvent ev) {
    final int action = ev.getAction();

    if ((action & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP) {
      scrolling = false;
    }

    return super.onTouchEvent(ev);
  }

  protected void onVisibilityChanged(View changedView, int visibility) {
    super.onVisibilityChanged(changedView, visibility);
    ViewHelper.onVisibilityChanged(this, changedView, visibility);
  }

  public void removeScrollerViewEx(View view) {
    if (scrollerViews != null) {
      scrollerViews.remove(view);
    }
  }

  public void setComponentPainter(iPlatformComponentPainter cp) {
    componentPainter = cp;
  }

  public static void addScrollerView(View v) {
    ScrollViewEx sv = findScrollView(v);

    if ((sv != null) && (sv != v)) {
      sv.addScrollerViewEx(v);
    }
  }

  public static ScrollViewEx findScrollView(View v) {
    while(v != null) {
      ViewParent vp = v.getParent();

      if (vp instanceof View) {
        v = (View) vp;
      } else {
        v = null;
      }

      if (v instanceof ScrollViewEx) {
        return (ScrollViewEx) v;
      }
    }

    return null;
  }

  public void scrollToLeftEdge() {}

  public void scrollToRightEdge() {}

  public void scrollToTopEdge() {
    if (getChildCount() > 0) {
      if (getScrollY() != 0) {
        setScrollY(0);
      }
    }
  }

  @Override
  public void moveUpDown(boolean up, boolean block) {
    if (block) {
      pageScroll(up
                 ? View.FOCUS_DOWN
                 : View.FOCUS_UP);
    } else {
      arrowScroll(up
                  ? View.FOCUS_DOWN
                  : View.FOCUS_UP);
    }
  }

  @Override
  public void moveLeftRight(boolean left, boolean block) {}

  public void scrollToBottomEdge() {
    if (getChildCount() > 0) {
      int  h = getHeight() - getPaddingTop() - getPaddingBottom();
      View v = getChildAt(0);
      int  y = Math.max(v.getHeight() - h, 0);

      if (y != getScrollY()) {
        setScrollY(y);
      }
    }
  }

  public static void removeScrollerView(View v) {
    ScrollViewEx sv = findScrollView(v);

    if (sv != null) {
      sv.removeScrollerViewEx(v);
    }
  }
}
