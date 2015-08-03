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

import android.widget.LinearLayout;

import com.appnativa.rare.platform.android.ui.NullDrawable;
import com.appnativa.rare.platform.android.ui.iComponentView;
import com.appnativa.rare.platform.android.ui.util.AndroidGraphics;
import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.painter.iPainterSupport;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;
import com.appnativa.rare.ui.painter.iPlatformPainter;

/**
 *
 * @author Don DeCoteau
 */
public class LinearLayoutEx extends LinearLayout implements iPainterSupport, iComponentView {
  protected iPlatformComponentPainter componentPainter;
  protected AndroidGraphics           graphics;
  private UIDimension                 size = new UIDimension();

  public LinearLayoutEx(Context context) {
    this(context, null);
  }

  public LinearLayoutEx(Context context, AttributeSet attrs) {
    super(context, attrs);

    if (attrs == null) {
      this.setBackgroundDrawable(NullDrawable.getInstance());
    }
  }

  public void dispose() {
    if (graphics != null) {
      graphics.dispose();
      graphics = null;
    }
  }

  public void draw(Canvas canvas) {
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

  public void setComponentPainter(iPlatformComponentPainter cp) {
    componentPainter = cp;
  }

  public iPlatformComponentPainter getComponentPainter() {
    return componentPainter;
  }

  public int getSuggestedMinimumHeight() {
    return Math.max(super.getSuggestedMinimumHeight(), getSuggestedMinimum(true));
  }

  public int getSuggestedMinimumWidth() {
    return Math.max(super.getSuggestedMinimumWidth(), getSuggestedMinimum(false));
  }

  public final View getView() {
    return this;
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

  protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    ViewHelper.onAttachedToWindow(this);
  }

  protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    ViewHelper.onDetachedFromWindow(this);
  }

  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    ViewHelper.onSizeChanged(this, w, h, oldw, oldh);
  }

  protected void onVisibilityChanged(View changedView, int visibility) {
    super.onVisibilityChanged(changedView, visibility);
    ViewHelper.onVisibilityChanged(this, changedView, visibility);
  }

  protected int getSuggestedMinimum(boolean forHeight) {
    int count     = getChildCount();
    int maxHeight = 0;
    int maxWidth  = 0;
    int w;
    int h;
    int viewGap = 0;

    for (int i = 0; i < count; i++) {
      View child = getChildAt(i);

      if ((child instanceof iComponentView) && (child.getVisibility() != GONE)) {
        h = ((iComponentView) child).getSuggestedMinimumHeight();
        w = ((iComponentView) child).getSuggestedMinimumWidth();

        switch(getOrientation()) {
          case HORIZONTAL :
            maxWidth  += w + viewGap;
            maxHeight = Math.max(maxHeight, h);

            break;

          case VERTICAL :
            maxWidth  = Math.max(maxWidth, w);
            maxHeight += h + viewGap;

            break;

          default :
            maxWidth  = Math.max(maxWidth, w);
            maxHeight = Math.max(maxHeight, h);

            break;
        }
      }
    }

    switch(getOrientation()) {
      case HORIZONTAL :
        if (maxWidth > viewGap) {
          maxWidth -= viewGap;
        }

        break;

      case VERTICAL :
        if (maxHeight > viewGap) {
          maxHeight -= viewGap;
        }

        break;

      default :
        break;
    }

    maxWidth  += getPaddingLeft() + getPaddingRight();
    maxHeight += getPaddingTop() + getPaddingBottom();

    return forHeight
           ? maxHeight
           : maxWidth;
  }
}
