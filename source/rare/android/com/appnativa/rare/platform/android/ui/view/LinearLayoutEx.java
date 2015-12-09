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

import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import android.widget.LinearLayout;

import com.appnativa.rare.platform.android.ui.NullDrawable;
import com.appnativa.rare.platform.android.ui.iComponentView;
import com.appnativa.rare.platform.android.ui.util.AndroidGraphics;
import com.appnativa.rare.ui.ColorUtils;
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
  private UIDimension                 size         = new UIDimension();
  protected float                     enabledAlpha = 1;

  public LinearLayoutEx(Context context) {
    this(context, null);
  }

  public LinearLayoutEx(Context context, AttributeSet attrs) {
    super(context, attrs);

    if (attrs == null) {
      this.setBackground(NullDrawable.getInstance());
    }
  }

  @Override
  public void dispose() {
    if (graphics != null) {
      graphics.dispose();
      graphics = null;
    }
  }

  @Override
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

  @Override
  public void setComponentPainter(iPlatformComponentPainter cp) {
    componentPainter = cp;
  }

  @Override
  public iPlatformComponentPainter getComponentPainter() {
    return componentPainter;
  }

  @Override
  public int getSuggestedMinimumHeight() {
    return Math.max(super.getSuggestedMinimumHeight(), getSuggestedMinimum(true));
  }

  @Override
  public int getSuggestedMinimumWidth() {
    return Math.max(super.getSuggestedMinimumWidth(), getSuggestedMinimum(false));
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
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    ViewHelper.onSizeChanged(this, w, h, oldw, oldh);
  }

  @Override
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
