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

import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import android.widget.HorizontalScrollView;

import com.appnativa.rare.platform.android.ui.util.AndroidGraphics;
import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIPoint;
import com.appnativa.rare.ui.aAdjustable;
import com.appnativa.rare.ui.iScrollerSupport;
import com.appnativa.rare.ui.painter.UIScrollingEdgePainter;
import com.appnativa.rare.ui.painter.iPainterSupport;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;
import com.appnativa.rare.ui.painter.iPlatformPainter;
import com.appnativa.util.IdentityArrayList;

/**
 *
 * @author Don DeCoteau
 */
public class HorizontalScrollViewEx extends HorizontalScrollView implements iPainterSupport, iScrollerSupport {
  protected iPlatformComponentPainter           componentPainter;
  protected AndroidGraphics                     graphics;
  protected boolean                             intercetorEnabled = true;;
  protected UIDimension                         measureSize       = new UIDimension();
  protected boolean                             scrolling;
  protected UIScrollingEdgePainter              scrollingEdgePainter;
  protected aAdjustable                         adjustable;
  protected boolean                             inOnScrollChanged;
  protected IdentityArrayList<iScrollerSupport> scrollSynchronizer;
  protected float                               enabledAlpha = 1;

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
  public void setAlpha(float alpha) {
    enabledAlpha = alpha;
    super.setAlpha(alpha);
  }

  @Override
  public void setEnabled(boolean enabled) {
    super.setEnabled(enabled);

    if (enabled) {
      super.setAlpha(enabledAlpha * ColorUtils.getDisabledAplhaPercent());
    } else {
      super.setAlpha(0.5f);
    }
  }

  @Override
  public boolean dispatchTouchEvent(MotionEvent ev) {
    if (!isEnabled()) {
      return true;
    }

    return super.dispatchTouchEvent(ev);
  }

  @Override
  public boolean dispatchKeyEvent(KeyEvent event) {
    if (!isEnabled()) {
      return true;
    }

    return super.dispatchKeyEvent(event);
  }

  @Override
  public boolean dispatchGenericMotionEvent(MotionEvent event) {
    if (!isEnabled()) {
      return true;
    }

    return super.dispatchGenericMotionEvent(event);
  }

  @Override
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
  public iPlatformComponentPainter getComponentPainter() {
    return componentPainter;
  }

  @Override
  public UIPoint getContentOffset() {
    return new UIPoint(getScrollX(), 0);
  }

  @Override
  public UIScrollingEdgePainter getScrollingEdgePainter() {
    return scrollingEdgePainter;
  }

  public final View getView() {
    return this;
  }

  @Override
  public boolean isAtBottomEdge() {
    View v = getChild();

    if (v instanceof ScrollViewEx) {
      return ((ScrollViewEx) v).isAtBottomEdge();
    } else if (v instanceof ListViewEx) {
      return ((ListViewEx) v).isAtBottomEdge();
    }

    return true;
  }

  @Override
  public boolean isAtLeftEdge() {
    return getScrollX() == 0;
  }

  @Override
  public boolean isAtRightEdge() {
    if (getChildCount() > 0) {
      View v = getChildAt(0);

      return v.getWidth() - getScrollX() <= getWidth();
    }

    return true;
  }

  @Override
  public boolean isAtTopEdge() {
    View v = getChild();

    if (v instanceof ScrollViewEx) {
      return ((ScrollViewEx) v).isAtTopEdge();
    } else if (v instanceof ListViewEx) {
      return ((ListViewEx) v).isAtTopEdge();
    }

    return true;
  }

  /**
   * @return the intercetorEnabled
   */
  public boolean isIntercetorEnabled() {
    return intercetorEnabled;
  }

  @Override
  public boolean isScrolling() {
    return scrolling;
  }

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

  @Override
  public void moveUpDown(boolean up, boolean block) {
    View v = getChild();

    if (v instanceof ScrollViewEx) {
      ((ScrollViewEx) v).moveUpDown(up, block);
    } else if (v instanceof ListViewEx) {
      ((ListViewEx) v).moveUpDown(up, block);
    }
  }

  @Override
  public boolean onInterceptTouchEvent(MotionEvent ev) {
    if (!isIntercetorEnabled()) {
      return false;
    }

    return super.onInterceptTouchEvent(ev);
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

  @Override
  public void scrollToBottomEdge() {
    View v = getChild();

    if (v instanceof ScrollViewEx) {
      ((ScrollViewEx) v).scrollToBottomEdge();
    } else if (v instanceof ListViewEx) {
      ((ListViewEx) v).scrollToBottomEdge();
    }
  }

  @Override
  public void scrollToLeftEdge() {
    if (getChildCount() > 0) {
      if (getScrollX() != 0) {
        setScrollX(0);
      }
    }
  }

  @Override
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

  @Override
  public void scrollToTopEdge() {
    View v = getChild();

    if (v instanceof ScrollViewEx) {
      ((ScrollViewEx) v).scrollToTopEdge();
    } else if (v instanceof ListViewEx) {
      ((ListViewEx) v).scrollToTopEdge();
    }
  }

  public void setFooterView(iScrollerSupport ss) {
    syncScrolling(ss);

    if (ss instanceof HorizontalScrollViewEx) {
      HorizontalScrollViewEx hv = (HorizontalScrollViewEx) ss;

      hv.syncScrolling(this);
      setHorizontalScrollBarEnabled(false);
    }
  }

  public void syncScrolling(iScrollerSupport ss) {
    if (scrollSynchronizer == null) {
      scrollSynchronizer = new IdentityArrayList<iScrollerSupport>(2);
    }

    scrollSynchronizer.addIfNotPresent(ss);
  }

  public void setHeaderView(iScrollerSupport ss) {
    syncScrolling(ss);

    if (ss instanceof HorizontalScrollViewEx) {
      HorizontalScrollViewEx hv = (HorizontalScrollViewEx) ss;

      hv.syncScrolling(this);
      hv.setHorizontalScrollBarEnabled(false);
    }
  }

  public void setRowFooter(iScrollerSupport footerView) {
    View v = getChild();

    if (v instanceof ScrollViewEx) {
      ((ScrollViewEx) v).setRowFooter(footerView);
    } else if (v instanceof ListViewEx) {
      ((ListViewEx) v).setRowFooter(footerView);
    }
  }

  public void setRowHeader(iScrollerSupport headerView) {
    View v = getChild();

    if (v instanceof ScrollViewEx) {
      ((ScrollViewEx) v).setRowHeader(headerView);
    } else if (v instanceof ListViewEx) {
      ((ListViewEx) v).setRowHeader(headerView);
    }
  }

  @Override
  public void setComponentPainter(iPlatformComponentPainter cp) {
    componentPainter = cp;
  }

  @Override
  public void setContentOffset(float x, float y) {
    if (!inOnScrollChanged) {
      setScrollX((int) x);
    }
  }

  /**
   * @param intercetorEnabled
   *          the intercetorEnabled to set
   */
  public void setIntercetorEnabled(boolean intercetorEnabled) {
    this.intercetorEnabled = intercetorEnabled;
  }

  @Override
  public void setScrollingEdgePainter(UIScrollingEdgePainter painter) {
    scrollingEdgePainter = painter;
  }

  public void setAdjustable(aAdjustable ad) {
    adjustable = ad;
  }

  @Override
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

  @Override
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
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);

    if (adjustable != null) {
      adjustable.setMaximum(w);
    }

    ViewHelper.onSizeChanged(this, w, h, oldw, oldh);
  }

  @Override
  protected void onVisibilityChanged(View changedView, int visibility) {
    super.onVisibilityChanged(changedView, visibility);
    ViewHelper.onVisibilityChanged(this, changedView, visibility);
  }

  @Override
  protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    ViewHelper.onAttachedToWindow(this);
  }

  @Override
  protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    ViewHelper.onDetachedFromWindow(this);
  }

  @Override
  protected void onScrollChanged(int l, int t, int oldl, int oldt) {
    super.onScrollChanged(l, t, oldl, oldt);
    scrolling         = true;
    inOnScrollChanged = true;

    if (scrollSynchronizer != null) {
      for (iScrollerSupport ss : scrollSynchronizer) {
        ss.setContentOffset(getScrollX(), 0);
      }
    }

    if (adjustable != null) {
      adjustable.setValue(l);
    }

    inOnScrollChanged = false;
  }

  private View getChild() {
    if (getChildCount() > 0) {
      return getChildAt(0);
    }

    return null;
  }
}
