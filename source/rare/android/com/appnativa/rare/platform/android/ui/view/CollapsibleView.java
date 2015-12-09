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

import android.content.Context;

import android.graphics.Canvas;

import android.util.AttributeSet;

import android.view.View;

import com.appnativa.rare.platform.android.ui.util.AndroidGraphics;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;
import com.appnativa.rare.ui.painter.iPlatformPainter;

/**
 *
 * @author Don DeCoteau
 */
public class CollapsibleView extends ViewGroupEx {
  public CollapsibleView(Context context) {
    super(context);
    setMeasureType(MeasureType.VERTICAL);
  }

  public CollapsibleView(Context context, AttributeSet attrs) {
    super(context, attrs);
    setMeasureType(MeasureType.VERTICAL);
  }

  @Override
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

    graphics.clear();
  }

  @Override
  protected LayoutParams generateDefaultLayoutParams() {
    return new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
  }

  @Override
  protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
    int  pleft      = getPaddingLeft();
    int  pright     = getPaddingRight();
    int  ptop       = getPaddingTop();
    int  pbottom    = getPaddingBottom();
    int  count      = getChildCount();
    View titleLabel = getChildAt(0);
    int  theight    = titleLabel.getMeasuredHeight();
    View mainView   = (count > 1)
                      ? getChildAt(1)
                      : null;

    titleLabel.layout(pleft, ptop, right - pright, ptop + theight);

    if ((mainView != null) && (mainView.getVisibility() != GONE)) {
      mainView.layout(pleft, ptop + theight, right - pright, bottom - pbottom);
    }
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    int  count    = getChildCount();
    View mainView = (count > 1)
                    ? getChildAt(1)
                    : null;

    super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    if ((MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.UNSPECIFIED)
        || (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.UNSPECIFIED)) {
      if ((mainView != null) && (mainView.getVisibility() == GONE)) {
        mainView.measure(widthMeasureSpec, heightMeasureSpec);

        int w = mainView.getMeasuredWidth();

        if (w > getMeasuredWidth()) {
          setMeasuredDimension(w + getPaddingLeft() + getPaddingRight(), getMeasuredHeight());
        }
      }

      return;
    }

    int  width      = getMeasuredWidth() - getPaddingRight() - getPaddingLeft();
    int  height     = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();
    View titleLabel = (count > 0)
                      ? getChildAt(0)
                      : null;

    if (titleLabel != null) {
      measureExactly(titleLabel, width, Math.min(titleLabel.getMeasuredHeight(), height));
      height -= titleLabel.getMeasuredHeight();
    }

    if (mainView != null) {
      measureExactly(mainView, width, height);
    }
  }
}
