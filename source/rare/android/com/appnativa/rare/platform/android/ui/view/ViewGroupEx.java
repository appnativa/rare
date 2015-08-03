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
import android.graphics.Matrix;
import android.graphics.Rect;

import android.util.AttributeSet;

import android.view.View;
import android.view.ViewGroup;

import com.appnativa.rare.platform.android.ui.NullDrawable;
import com.appnativa.rare.platform.android.ui.iAndroidLayoutManager;
import com.appnativa.rare.platform.android.ui.iComponentView;
import com.appnativa.rare.platform.android.ui.util.AndroidGraphics;
import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.Utils;
import com.appnativa.rare.ui.effects.aAnimator;
import com.appnativa.rare.ui.event.ChangeEvent;
import com.appnativa.rare.ui.event.EventListenerList;
import com.appnativa.rare.ui.event.iChangeListener;
import com.appnativa.rare.ui.iDisposable;
import com.appnativa.rare.ui.painter.iPainterSupport;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;

/**
 *
 * @author Don DeCoteau
 */
public class ViewGroupEx extends ViewGroup implements iPainterSupport, iDisposable, iComponentView {
  protected MeasureType               measureType = MeasureType.UNKNOWN;
  protected UIDimension               measureSize = new UIDimension();
  protected boolean                   blockRequestLayout;
  protected ChangeEvent               changeEvent;
  protected iPlatformComponentPainter componentPainter;
  protected AndroidGraphics           graphics;
  protected int                       heightPad;
  protected iAndroidLayoutManager     layoutManager;
  protected EventListenerList         listenerList;
  protected Matrix                    matrix;
  protected boolean                   nullLayout;
  protected int                       viewGap;
  protected int                       widthPad;

  public enum MeasureType { HORIZONTAL, VERTICAL, UNKNOWN }

  public ViewGroupEx(Context context) {
    this(context, null);
  }

  public ViewGroupEx(Context context, AttributeSet attrs) {
    super(context, attrs);

    if (attrs == null) {
      this.setBackgroundDrawable(NullDrawable.getInstance());
    }
  }

  public ViewGroupEx(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  public void addChangeListener(iChangeListener l) {
    getListenerList().add(iChangeListener.class, l);
  }

  public void dispose() {
    changeEvent      = null;
    componentPainter = null;

    if (listenerList != null) {
      listenerList.clear();
      listenerList = null;
    }

    if (graphics != null) {
      graphics.dispose();
      graphics = null;
    }
  }

  public static void measureExactly(View view, int width, int height) {
    view.measure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
                 MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
  }

  public void removeChangeListener(iChangeListener l) {
    if (listenerList != null) {
      listenerList.remove(iChangeListener.class, l);
    }
  }

  @Override
  public void requestLayout() {
    if (!blockRequestLayout) {
      super.requestLayout();
    }
  }

  public static View getViewtAt(ViewGroup vg, float x, float y, boolean deepest) {
    int  len = vg.getChildCount();
    Rect r   = new Rect();

    for (int i = 0; i < len; i++) {
      View v = vg.getChildAt(i);

      v.getHitRect(r);

      if (r.contains((int) x, (int) y)) {
        x -= r.left;
        y -= r.top;

        if (deepest && (x > -1) && (y > -1) && (v instanceof ViewGroup)) {
          View vv = getViewtAt((ViewGroup) v, x, y, true);

          if (vv != null) {
            return vv;
          }
        }

        return v;
      }
    }

    return null;
  }

  public void setBlockRequestLayout(boolean blockRequestLayout) {
    this.blockRequestLayout = blockRequestLayout;
  }

  public void setComponentPainter(iPlatformComponentPainter cp) {
    componentPainter = cp;
  }

  public void setLayoutManager(iAndroidLayoutManager layoutManager) {
    this.layoutManager = layoutManager;
  }

  public void setMatrixEx(Matrix matrix) {
    this.matrix = matrix;
  }

  public void setMeasureType(MeasureType measureType) {
    this.measureType = measureType;
  }

  public void setNullLayout(boolean nullLayout) {
    this.nullLayout = nullLayout;
  }

  public void setSize(int width, int height) {
    setMeasuredDimension(width, height);
  }

  public void setViewGap(int viewGap) {
    this.viewGap = viewGap;
  }

  public View getChildAt(int x, int y, boolean deepest) {
    int  len = getChildCount();
    Rect r   = new Rect();

    for (int i = 0; i < len; i++) {
      View v = getChildAt(i);

      v.getHitRect(r);

      if (r.contains((int) x, (int) y)) {
        if (v == this) {
          return null;
        }

        x -= r.left;
        y -= r.top;

        if (deepest && (x > -1) && (y > -1) && (v instanceof ViewGroupEx)) {
          View vv = ((ViewGroupEx) v).getChildAt(x, y, true);

          if (vv != null) {
            return vv;
          }
        }

        return v;
      }
    }

    return null;
  }

  public iPlatformComponentPainter getComponentPainter() {
    return componentPainter;
  }

  public iAndroidLayoutManager getLayoutManager() {
    return layoutManager;
  }

  /**
   * @return the listenerList
   */
  public EventListenerList getListenerList() {
    if (listenerList == null) {
      listenerList = new EventListenerList();
    }

    return listenerList;
  }

  public MeasureType getMeasureType() {
    return measureType;
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

  public int getViewGap() {
    return viewGap;
  }

  public boolean isAnimating() {
    return isAnimating(this);
  }

  public static boolean isAnimating(View view) {
    Component c = Component.fromView(view);

    return (c == null)
           ? false
           : aAnimator.isAnimating(c);
  }

  public boolean isBlockRequestLayout() {
    return blockRequestLayout;
  }

  public boolean isNullLayout() {
    return nullLayout;
  }

  protected void callSuperDraw(Canvas canvas) {
    super.draw(canvas);
  }

  protected void fireChangeEvent() {
    if (listenerList != null) {
      if (changeEvent == null) {
        changeEvent = new ChangeEvent(Component.fromView(this));
      }

      Utils.fireChangeEvent(listenerList, changeEvent);
    }
  }

  protected void measureChild(View child, int parentWidthMeasureSpec, int parentHeightMeasureSpec) {
    Component c = Component.fromView(child);

    if (c == null) {
      super.measureChild(child, parentWidthMeasureSpec, parentHeightMeasureSpec);

      return;
    }

    final UIDimension s         = measureSize;
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

  @Override
  protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
    if (isAnimating(this)) {
      return;
    }

    if (layoutManager != null) {
      layoutManager.layout(this, right - left, bottom - top);

      return;
    }

    if (!isNullLayout()) {
      throw new UnsupportedOperationException("Not supported yet.");
    }
  }

  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    if ((MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.EXACTLY)
        && (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY)) {
      setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));
    } else if (isAnimating(this)) {
      setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight());
    } else {
      onMeasureEx(widthMeasureSpec, heightMeasureSpec);
    }
  }

  protected void onMeasureEx(int widthMeasureSpec, int heightMeasureSpec) {
    int count     = getChildCount();
    int maxHeight = 0;
    int maxWidth  = 0;

    measureChildren(widthMeasureSpec, heightMeasureSpec);

    for (int i = 0; i < count; i++) {
      View child = getChildAt(i);

      if (child.getVisibility() != GONE) {
        switch(measureType) {
          case HORIZONTAL :
            maxWidth  += child.getMeasuredWidth() + viewGap;
            maxHeight = Math.max(maxHeight, child.getMeasuredHeight());

            break;

          case VERTICAL :
            maxWidth  = Math.max(maxWidth, child.getMeasuredWidth());
            maxHeight += child.getMeasuredHeight() + viewGap;

            break;

          default :
            maxWidth  = Math.max(maxWidth, child.getMeasuredWidth());
            maxHeight = Math.max(maxHeight, child.getMeasuredHeight());

            break;
        }
      }
    }

    switch(measureType) {
      case HORIZONTAL :
        if (maxWidth > viewGap) {
          maxWidth = Math.max(maxWidth - viewGap, 0);
        }

        break;

      case VERTICAL :
        if (maxHeight > viewGap) {
          maxHeight = Math.max(maxHeight - viewGap, 0);
        }

        break;

      default :
        break;
    }

    maxWidth  += getPaddingLeft() + getPaddingRight();
    maxHeight += getPaddingTop() + getPaddingBottom();
    maxHeight = Math.max(maxHeight, getSuggestedMinimumHeight());
    maxWidth  = Math.max(maxWidth, getSuggestedMinimumWidth());
    setMeasuredDimension(resolveSize(maxWidth, widthMeasureSpec), resolveSize(maxHeight, heightMeasureSpec));
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

    for (int i = 0; i < count; i++) {
      View child = getChildAt(i);

      if ((child instanceof iComponentView) && (child.getVisibility() != GONE)) {
        h = ((iComponentView) child).getSuggestedMinimumHeight();
        w = ((iComponentView) child).getSuggestedMinimumWidth();

        switch(measureType) {
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

    switch(measureType) {
      case HORIZONTAL :
        if (maxWidth > viewGap) {
          maxWidth = Math.max(maxWidth - viewGap, 0);
        }

        break;

      case VERTICAL :
        if (maxHeight > viewGap) {
          maxHeight = Math.max(maxHeight - viewGap, 0);
        }

        break;

      default :
        break;
    }

    maxWidth  += getPaddingLeft() + getPaddingRight() + widthPad;
    maxHeight += getPaddingTop() + getPaddingBottom() + heightPad;

    return forHeight
           ? maxHeight
           : maxWidth;
  }
}
