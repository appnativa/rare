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

import android.widget.HorizontalScrollView;

import com.appnativa.rare.platform.android.ui.util.AndroidGraphics;
import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIPoint;
import com.appnativa.rare.ui.iScrollerSupport;
import com.appnativa.rare.ui.painter.UIScrollingEdgePainter;
import com.appnativa.rare.ui.painter.iPainterSupport;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;
import com.appnativa.rare.ui.painter.iPlatformPainter;

/**
 *
 * @author Don DeCoteau
 */
public class HorizontalScrollViewEx extends HorizontalScrollView implements iPainterSupport, iScrollerSupport {
  iPlatformComponentPainter      componentPainter;
  protected AndroidGraphics      graphics;
  private boolean                intercetorEnabled = true;;
  protected UIDimension          measureSize       = new UIDimension();
  private boolean                scrolling;
  private UIScrollingEdgePainter scrollingEdgePainter;

  public HorizontalScrollViewEx(Context context) {
    super(context);
    setFillViewport(true);

    if (UIScrollingEdgePainter.isPaintHorizontalScrollEdge()) {
      scrollingEdgePainter = (UIScrollingEdgePainter) UIScrollingEdgePainter.getInstance().clone();
      scrollingEdgePainter.setScrollerSupport(this);
    }
  }

  public HorizontalScrollViewEx(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override
  public void setScrollingEdgePainter(UIScrollingEdgePainter painter) {
    scrollingEdgePainter = painter;
  }

  @Override
  public UIScrollingEdgePainter getScrollingEdgePainter() {
    return scrollingEdgePainter;
  }

  public void scrollToLeftEdge() {
    if (getChildCount() > 0) {
      if (getScrollX() != 0) {
        setScrollX(0);
      }
    }
  }

  public void scrollToRightEdge() {
    if (getChildCount() > 0) {
      View v = getChildAt(0);
      int  w = getWidth() - getPaddingLeft() - getPaddingRight();
      int  x = Math.max(v.getWidth() - w, 0);

      if (x != getScrollX()) {
        setScrollX(x);
      }
    }
  }

  public void scrollToTopEdge() {}

  public void scrollToBottomEdge() {}

  public void draw(Canvas canvas) {
    graphics = AndroidGraphics.fromGraphics(canvas, this, graphics);

    final iPlatformComponentPainter cp = componentPainter;
    int                             w  = getWidth();
    int                             h  = getHeight();
    int                             x  = getScrollX();

    if (cp == null) {
      super.draw(canvas);
    } else {
      cp.paint(graphics, x, 0, w, h, iPlatformPainter.UNKNOWN, false);
      super.draw(canvas);
      cp.paint(graphics, x, 0, w, h, iPlatformPainter.UNKNOWN, true);
    }

    if (scrollingEdgePainter != null) {
      h -= (getPaddingTop() + getPaddingBottom());
      w -= (getPaddingLeft() + getPaddingRight());
      x += getPaddingLeft();

      int y = getScrollY() + getPaddingTop();

      scrollingEdgePainter.paint(graphics, x, y, w, h, false);
    }

    graphics.clear();
  }

  @Override
  public boolean onInterceptTouchEvent(MotionEvent ev) {
    if (!isIntercetorEnabled()) {
      return false;
    }

    return super.onInterceptTouchEvent(ev);
  }

  public void setComponentPainter(iPlatformComponentPainter cp) {
    componentPainter = cp;
  }

  /**
   * @param intercetorEnabled
   *          the intercetorEnabled to set
   */
  public void setIntercetorEnabled(boolean intercetorEnabled) {
    this.intercetorEnabled = intercetorEnabled;
  }

  public iPlatformComponentPainter getComponentPainter() {
    return componentPainter;
  }

  public final View getView() {
    return this;
  }

  /**
   * @return the intercetorEnabled
   */
  public boolean isIntercetorEnabled() {
    return intercetorEnabled;
  }

  public boolean isScrolling() {
    return scrolling;
  }

  public UIPoint getContentOffset() {
    return new UIPoint(getScrollX(), 0);
  }

  public boolean isAtLeftEdge() {
    return getScrollX() == 0;
  }

  public boolean isAtRightEdge() {
    if (getChildCount() > 0) {
      View v = getChildAt(0);

      return v.getWidth() - getScrollX() <= getWidth();
    }

    return true;
  }

  public boolean isAtTopEdge() {
    return true;
  }

  public boolean isAtBottomEdge() {
    return true;
  }

  @Override
  protected void onScrollChanged(int l, int t, int oldl, int oldt) {
    super.onScrollChanged(l, t, oldl, oldt);
    scrolling = true;
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

  protected void measureChild(View child, int parentWidthMeasureSpec, int parentHeightMeasureSpec) {
    Component c = Component.fromView(child);

    if (c == null) {
      super.measureChild(child, parentWidthMeasureSpec, parentHeightMeasureSpec);

      return;
    }

    final UIDimension s = c.getPreferredSize(measureSize);

    parentWidthMeasureSpec  = MeasureSpec.makeMeasureSpec(s.intWidth(), MeasureSpec.EXACTLY);
    parentHeightMeasureSpec = MeasureSpec.makeMeasureSpec(s.intHeight(), MeasureSpec.EXACTLY);
    super.measureChild(child, parentWidthMeasureSpec, parentHeightMeasureSpec);
  }

  protected void measureChildWithMargins(View child, int parentWidthMeasureSpec, int widthUsed,
          int parentHeightMeasureSpec, int heightUsed) {
    Component c = Component.fromView(child);

    if (c == null) {
      super.measureChild(child, parentWidthMeasureSpec, parentHeightMeasureSpec);

      return;
    }

    final UIDimension s = c.getPreferredSize(measureSize);

    parentWidthMeasureSpec  = MeasureSpec.makeMeasureSpec(s.intWidth(), MeasureSpec.EXACTLY);
    parentHeightMeasureSpec = MeasureSpec.makeMeasureSpec(s.intHeight(), MeasureSpec.EXACTLY);
    super.measureChild(child, parentWidthMeasureSpec, parentHeightMeasureSpec);
  }

  @Override
  public void moveUpDown(boolean up, boolean block) {}

  @Override
  public void moveLeftRight(boolean left, boolean block) {
    if (block) {
      pageScroll(left
                 ? View.FOCUS_RIGHT
                 : View.FOCUS_LEFT);
    } else {
      arrowScroll(left
                  ? View.FOCUS_RIGHT
                  : View.FOCUS_LEFT);
    }
  }
}
