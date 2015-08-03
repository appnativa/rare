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

package com.appnativa.rare.ui.table;

import android.annotation.SuppressLint;

import android.content.Context;

import android.graphics.Canvas;
import android.graphics.Paint;

import android.view.GestureDetector.OnGestureListener;
import android.view.View;
import android.view.ViewGroup;

import com.appnativa.rare.Platform;
import com.appnativa.rare.platform.android.ui.iFlingSupport;
import com.appnativa.rare.platform.android.ui.util.AndroidGraphics;
import com.appnativa.rare.platform.android.ui.view.ViewGroupEx;
import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.UIPoint;
import com.appnativa.rare.ui.painter.UIScrollingEdgePainter;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;
import com.appnativa.rare.ui.painter.iPlatformPainter;
import com.appnativa.rare.ui.table.TableHeader.TableHeaderLayout;

public class TableViewLayout extends ViewGroupEx implements iFlingSupport {
  TableComponent  table;
  OnTouchListener touchListener;
  int             headerHeight;

  public TableViewLayout(Context context) {
    super(context);
    setMeasureType(MeasureType.VERTICAL);
  }

  @Override
  public void requestLayout() {
    // TODO Auto-generated method stub
    super.requestLayout();
  }

  protected boolean addViewInLayout(View child, int index, LayoutParams params, boolean preventRequestLayout) {
    child.setOnTouchListener(touchListener);

    return super.addViewInLayout(child, index, params, preventRequestLayout);
  }

  public void draw(Canvas canvas) {
    if (matrix != null) {
      canvas.concat(matrix);
    }

    graphics = AndroidGraphics.fromGraphics(canvas, this, graphics);

    final iPlatformComponentPainter cp = componentPainter;

    if (cp == null) {
      super.draw(canvas);
    } else {
      cp.paint(graphics, getScrollX(), getScrollY(), getWidth(), getHeight(), iPlatformPainter.UNKNOWN, false);
      super.draw(canvas);
      cp.paint(graphics, getScrollX(), getScrollY(), getWidth(), getHeight(), iPlatformPainter.UNKNOWN, true);
    }

    if (((aTableAdapter) table.getListView().getAdapter()).showVerticalLines) {
      drawVerticalLines(canvas);
    }

    graphics.clear();
  }

  public void setScrollingEdgePainter(UIScrollingEdgePainter painter) {
    table.getListView().setScrollingEdgePainter(painter);
  }

  public UIScrollingEdgePainter getScrollingEdgePainter() {
    return table.getListView().getScrollingEdgePainter();
  }

  protected void drawVerticalLines(Canvas canvas) {
    boolean extend = table.getListView().isExtendBackgroundRendering();

    if (!extend) {
      return;
    }

    ViewGroup vg     = (ViewGroup) table.tableHeader.getView();
    int       len    = vg.getChildCount();
    int       sx     = getScrollX();
    int       bottom = getBottom();
    Paint     p      = table.getListView().getDividerPaint();
    int       top    = vg.getBottom();
    int       n      = table.getListView().getLastVisiblePosition();

    if ((n == -1) && (n != table.getRowCount())) {
      return;
    }

    View v = table.getListView().getViewForRow(n);

    if (v != null) {
      top += v.getBottom();
    }

    int d = ScreenUtils.PLATFORM_PIXELS_1;

    for (int i = 1; i < len; i++) {
      v = vg.getChildAt(i);

      if (v.getVisibility() == View.GONE) {
        continue;
      }

      int x = v.getLeft();

      if (x >= sx) {
        canvas.drawLine(x - d, top, x - d, bottom, p);
      }
    }
  }

  public UIPoint getContentOffset() {
    UIPoint p = table.getListView().getContentOffset();

    p.x = getScrollX();

    return p;
  }

  public boolean isAtBottomEdge() {
    return table.getListView().isAtBottomEdge();
  }

  public boolean isAtLeftEdge() {
    return getScrollX() == 0;
  }

  public boolean isAtRightEdge() {
    return getScrollX() + getWidth() < ((View) getParent()).getWidth();
  }

  public boolean isAtTopEdge() {
    return table.getListView().isAtTopEdge();
  }

  public boolean isScrolling() {
    return table.getListView().isScrolling();
  }

  @Override
  protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
    int  x  = getPaddingLeft();
    int  y  = getPaddingTop();
    int  w  = right - left - x - getPaddingRight();
    int  h  = bottom - top - getPaddingBottom() - y;
    int  hh = 0;
    View v  = getChildAt(0);

    if (v.getVisibility() != View.GONE) {
      // if (changed) {
      // v.measure(MeasureSpec.makeMeasureSpec(w, MeasureSpec.EXACTLY),
      // MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
      // }
      hh = (headerHeight > 0)
           ? headerHeight
           : v.getMeasuredHeight();
      v.layout(x, y, x + w, y + hh);
    } else {
      // if (changed) {
      // measureExactly(v, w, hh);
      // }
      ((TableHeaderLayout) v).onLayout(false, x, y, x + w, y + hh);
    }

    v = getChildAt(1);
    measureExactly(v, w, h - hh);
    v.layout(x, hh + y, x + w, y + h);

    if (changed) {
      if (table.isKeepSelectionVisible()) {
        Platform.invokeLater(new Runnable() {
          @Override
          public void run() {
            if ((table != null) &&!table.isDisposed()) {
              table.makeSelectionVisible();
            }
          }
        });
      }
    }
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    int       width  = MeasureSpec.getSize(widthMeasureSpec);
    int       height = MeasureSpec.getSize(heightMeasureSpec);
    final int w      = Math.max(width - getPaddingLeft() - getPaddingRight(), 0);
    final int h      = Math.max(height - getPaddingTop() - getPaddingBottom(), 0);
    int       hh     = headerHeight;

    if (MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.EXACTLY) {
      measureSize.setSize(width, height);

      if ((table.getGridViewType() == null) && (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY)) {
        table.updateColumnSizes(w, h);
      }

      View v = getChildAt(1);

      if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY) {
        View vh = getChildAt(0);

        if (hh == 0) {
          vh.measure(MeasureSpec.makeMeasureSpec(w, MeasureSpec.EXACTLY),
                     MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));    // measure
          // even
          // if
          // invisible
          // to
          // align
          // columns

          if (vh.getVisibility() != View.GONE) {
            hh = vh.getMeasuredHeight();
            measureExactly(vh, width, hh);
          }
        } else {
          measureExactly(vh, width, hh);
        }

        measureExactly(v, w, h - hh);
      } else {
        View vh = getChildAt(0);

        vh.measure(MeasureSpec.makeMeasureSpec(w, MeasureSpec.EXACTLY),
                   MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));    // measure
        // even
        // if
        // invisible
        // to
        // align
        // columns

        if (vh.getVisibility() != View.GONE) {
          hh = vh.getMeasuredHeight();
          measureExactly(vh, width, hh);
        }

        v.measure(MeasureSpec.makeMeasureSpec(w, MeasureSpec.EXACTLY),
                  MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        measureSize.height = v.getMeasuredHeight() + hh;
      }
    } else if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY) {
      measureSize.setSize(width, height);

      View v = getChildAt(0);

      v.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));    // measure
      // even
      // if
      // invisible
      // to
      // align
      // columns

      if (v.getVisibility() != View.GONE) {
        hh = v.getMeasuredHeight();
      }

      int ww = v.getMeasuredWidth();

      v = getChildAt(1);

      if (MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.EXACTLY) {
        measureSize.width = width;
        measureExactly(v, w, h - hh);
      } else {
        v.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                  MeasureSpec.makeMeasureSpec(h - hh, MeasureSpec.EXACTLY));
        measureSize.width = Math.max(ww, v.getMeasuredWidth());
        measureExactly(v, measureSize.intWidth(), h - hh);
      }

      if (hh != 0) {
        measureExactly(getChildAt(0), measureSize.intWidth(), hh);
      }
    } else {
      table.measurePreferredSizeEx(measureSize, width);
    }

    setMeasuredDimension(resolveSize(measureSize.intWidth(), widthMeasureSpec),
                         resolveSize(measureSize.intHeight(), heightMeasureSpec));
  }

  public void setFlingGestureListener(OnGestureListener flingGestureListener) {
    table.getListView().setFlingGestureListener(flingGestureListener);
  }

  public void setHeaderHeight(int height) {
    headerHeight = height;
  }

  @SuppressLint("ClickableViewAccessibility")
  public void setOnTouchListener(OnTouchListener l) {
    if (touchListener != l) {
      super.setOnTouchListener(l);
      touchListener = l;

      int len = getChildCount();

      for (int i = 0; i < len; i++) {
        getChildAt(i).setOnTouchListener(l);
      }
    }
  }
}
