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

  public UIPoint getContentOffset() {
    UIPoint p = table.getListView().getContentOffset();

    p.x = getScrollX();

    return p;
  }

  public UIScrollingEdgePainter getScrollingEdgePainter() {
    return table.getListView().getScrollingEdgePainter();
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

  public void setScrollingEdgePainter(UIScrollingEdgePainter painter) {
    table.getListView().setScrollingEdgePainter(painter);
  }

  protected boolean addViewInLayout(View child, int index, LayoutParams params, boolean preventRequestLayout) {
    child.setOnTouchListener(touchListener);

    return super.addViewInLayout(child, index, params, preventRequestLayout);
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

  @Override
  protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
    int  x  = getPaddingLeft();
    int  y  = getPaddingTop();
    int  w  = right - left - x - getPaddingRight();
    View hv = table.getHeader().getView();
    View lv = table.getListView();
    int  yy = y;
    int  hh = 0;
    int  h  = 0;

    if (hv.getVisibility() != View.GONE) {
      h = (headerHeight > 0)
          ? headerHeight
          : hv.getMeasuredHeight();
      hv.layout(x, yy, x + w, yy + h);
      yy += h;
      hh += h;
    } else {
      ((TableHeaderLayout) hv).onLayout(false, x, y, x + w, y + hh);
    }

    h = bottom - top - getPaddingBottom() - y;

    measureExactly(lv, w, h - hh);
    lv.layout(x, yy, x + w, yy + h - hh);

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
    View vh = table.getHeader().getView();
    View lv = table.getListView();

    if (MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.EXACTLY) {
      measureSize.setSize(width, height);

      if ((table.getGridViewType() == null) && (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY)) {
        table.updateColumnSizes(w, h);
      }


      if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY) {
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
            measureExactly(vh, w, hh);
          }
        } else {
          measureExactly(vh, w, hh);
        }

        measureExactly(lv, w, h - hh);
      } else {

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
          measureExactly(vh, w, hh);
        }

        lv.measure(MeasureSpec.makeMeasureSpec(w, MeasureSpec.EXACTLY),
                  MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        measureSize.height = lv.getMeasuredHeight() + hh;
      }
    } else if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY) {
      measureSize.setSize(width, height);

      vh.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));    // measure
      // even
      // if
      // invisible
      // to
      // align
      // columns

      if (vh.getVisibility() != View.GONE) {
        hh = vh.getMeasuredHeight();
      }

      int ww = vh.getMeasuredWidth();

      if (MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.EXACTLY) {
        measureSize.width = width;
        measureExactly(lv, w, h - hh);
      } else {
        lv.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                  MeasureSpec.makeMeasureSpec(h - hh, MeasureSpec.EXACTLY));
        measureSize.width = Math.max(ww, lv.getMeasuredWidth());
        measureExactly(lv, measureSize.intWidth(), h - hh);
      }

      if (hh != 0) {
        measureExactly(vh, measureSize.intWidth(), hh);
      }
    } else {
      table.measurePreferredSizeEx(measureSize, width);
    }

    setMeasuredDimension(resolveSize(measureSize.intWidth(), widthMeasureSpec),
                         resolveSize(measureSize.intHeight(), heightMeasureSpec));
  }

}
