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
import android.graphics.drawable.Drawable;

import android.util.AttributeSet;

import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import android.widget.SeekBar;

import com.appnativa.rare.platform.android.ui.iComponentView;
import com.appnativa.rare.platform.android.ui.util.AndroidGraphics;
import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.event.EventBase;
import com.appnativa.rare.ui.event.iChangeListener;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iSlider;
/* Portions from https://github.com/h6ah4i/android-verticalseekbar
 *    Copyright (C) 2015 Haruki Hasegawa
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
import com.appnativa.rare.ui.painter.iPainterSupport;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;

/* This file contains AOSP code copied from /frameworks/base/core/java/android/widget/AbsSeekBar.java */
/*============================================================================*/
/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*============================================================================*/
import com.appnativa.rare.ui.painter.iPlatformPainter;

/**
 *
 * @author Don DeCoteau
 */
public class SliderView extends SeekBar
        implements SeekBar.OnSeekBarChangeListener, iSlider, iPainterSupport, iComponentView {
  public static final int             ROTATION_ANGLE_CW_90  = 90;
  public static final int             ROTATION_ANGLE_CW_270 = 270;
  protected iChangeListener           changeListener;
  protected boolean                   horizontal;
  protected int                       lastValue;
  protected int                       maxValue=100;
  protected int                       minValue;
  protected boolean                   mIsDragging;
  protected int                       mRotationAngle;
  protected boolean                   showTicks;
  protected iPlatformComponentPainter componentPainter;
  protected AndroidGraphics           graphics;

  public SliderView(Context context) {
    super(context);
    setMinimumWidth(ScreenUtils.platformPixels(50));
  }

  public SliderView(Context context, AttributeSet attrs) {
    super(context, attrs);
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

  public iPlatformComponent getComponent() {
    Component c = Component.fromView(this);

    if (c == null) {
      c = new Component(this);
    }

    return c;
  }

  @Override
  public iPlatformComponentPainter getComponentPainter() {
    return componentPainter;
  }

  @Override
  public int getMaximum() {
    return maxValue;
  }

  @Override
  public int getMinimum() {
    return minValue;
  }

  public int getRotationAngle() {
    return mRotationAngle;
  }

  @Override
  public int getSuggestedMinimumHeight() {
    return super.getSuggestedMinimumHeight();
  }

  @Override
  public int getSuggestedMinimumWidth() {
    return super.getSuggestedMinimumWidth();
  }

  @Override
  public int getValue() {
    return getProgress() + minValue;
  }

  /**
   * @return the horizontal
   */
  @Override
  public boolean isHorizontal() {
    return horizontal;
  }

  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event) {
    if (horizontal) {
      return super.onKeyDown(keyCode, event);
    }

    if (isEnabled()) {
      final boolean handled;
      int           direction = 0;

      switch(keyCode) {
        case KeyEvent.KEYCODE_DPAD_DOWN :
          direction = (mRotationAngle == ROTATION_ANGLE_CW_90)
                      ? 1
                      : -1;
          handled   = true;

          break;

        case KeyEvent.KEYCODE_DPAD_UP :
          direction = (mRotationAngle == ROTATION_ANGLE_CW_270)
                      ? 1
                      : -1;
          handled   = true;

          break;

        case KeyEvent.KEYCODE_DPAD_LEFT :
        case KeyEvent.KEYCODE_DPAD_RIGHT :
          handled = true;

          break;

        default :
          handled = false;

          break;
      }

      if (handled) {
        final int keyProgressIncrement = getKeyProgressIncrement();
        int       progress             = getProgress();

        progress += (direction * keyProgressIncrement);

        if ((progress >= 0) && (progress <= getMax())) {
          setProgress(progress - keyProgressIncrement);
        }

        return true;
      }
    }

    return super.onKeyDown(keyCode, event);
  }

  @Override
  public void onProgressChanged(SeekBar sb, int progress, boolean fromUser) {
    if ((progress != lastValue) && (changeListener != null)) {
      lastValue = progress;
      changeListener.stateChanged(new EventBase(sb));
    }
  }

  @Override
  public void onStartTrackingTouch(SeekBar seekBar) {}

  @Override
  public void onStopTrackingTouch(SeekBar seekBar) {}

  @SuppressLint("ClickableViewAccessibility")
  @Override
  public boolean onTouchEvent(MotionEvent event) {
    if (horizontal) {
      return super.onTouchEvent(event);
    } else {
      return onTouchEventTraditionalRotation(event);
    }
  }

  public void setChangeListener(iChangeListener l) {
    changeListener = l;
    setOnSeekBarChangeListener((l == null)
                               ? null
                               : this);
  }

  @Override
  public void setComponentPainter(iPlatformComponentPainter cp) {
    componentPainter = cp;
  }

  /**
   * @param horizontal the horizontal to set
   */
  @Override
  public void setHorizontal(boolean horizontal) {
    this.horizontal = horizontal;
    mRotationAngle  = horizontal
                      ? 0
                      : ROTATION_ANGLE_CW_270;
  }

  @Override
  public void setMajorTickSpacing(int value) {
    setKeyProgressIncrement(value);
  }

  @Override
  public void setMaximum(int maximum) {
    maxValue = maximum;

    float max = Math.abs(maxValue - minValue);

    setMax((int) max);
  }

  @Override
  public void setMinimum(int minimum) {
    minValue = minimum;
    float max = Math.abs(maxValue - minValue);

    setMax((int) max);
  }

  @Override
  public void setMinorTickSpacing(int value) {}

  @Override
  public synchronized void setProgress(int progress) {
    super.setProgress(progress);
    if(mRotationAngle!=0) {
      refreshThumb();
    }
  }

  public void setRotationAngle(int angle) {
    if (!isValidRotationAngle(angle)) {
      throw new IllegalArgumentException("Invalid angle specified :" + angle);
    }

    if (mRotationAngle == angle) {
      return;
    }

    mRotationAngle = angle;
    requestLayout();
  }

  @Override
  public void setShowTicks(boolean show) {
    showTicks = show;
  }

  @Override
  public void setValue(int value) {
    int val = Math.max(0, value - minValue);

    super.setProgress(val);
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
  protected synchronized void onDraw(Canvas canvas) {
//    if(trackPainter!=null) {
//      int l = getPaddingLeft();
//      int r = getPaddingRight();
//      int b = getPaddingBottom();
//      int t = getPaddingTop();
//      int w=getWidth();
//      int h=getHeight();
//      int to=getThumbOffset();
//      w-=(l+r);
//      h-=(t+b);
//      if(horizontal) {
//      }
//      else {
//        h-=(to+to);
//        t+=to;
////        h-=(l+r);
////        w-=(t+b);
//      }
////      if(trackPainterWidth>0) {
////        if(horizontal) {
////          h=Math.min(trackPainterWidth, h);
////        }
////        else {
////          w=Math.min(trackPainterWidth, w);
////        }
////      }
//      trackPainter.paint(this, canvas, l, t, w, h, iPainter.HORIZONTAL);
//    }
    if (!horizontal) {
      switch(mRotationAngle) {
        case ROTATION_ANGLE_CW_90 :
          canvas.rotate(90);
          canvas.translate(0, -super.getWidth());

          break;

        case ROTATION_ANGLE_CW_270 :
          canvas.rotate(-90);
          canvas.translate(-super.getHeight(), 0);

          break;
      }
    }

    super.onDraw(canvas);
  }

  @Override
  protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(heightMeasureSpec, widthMeasureSpec);

    if (horizontal) {
      final ViewGroup.LayoutParams lp = getLayoutParams();

      if (isInEditMode() && (lp != null) && (lp.height >= 0)) {
        setMeasuredDimension(super.getMeasuredHeight(), getLayoutParams().height);
      } else {
        setMeasuredDimension(super.getMeasuredHeight(), super.getMeasuredWidth());
      }
    }
  }

  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    if (horizontal) {
      super.onSizeChanged(w, h, oldw, oldh);
    } else {
      super.onSizeChanged(h, w, oldh, oldw);
    }

    ViewHelper.onSizeChanged(this, w, h, oldw, oldh);
  }

  @Override
  protected void onVisibilityChanged(View changedView, int visibility) {
    super.onVisibilityChanged(changedView, visibility);
    ViewHelper.onVisibilityChanged(this, changedView, visibility);
  }

  /**
   * Tries to claim the user's drag motion, and requests disallowing any
   * ancestors from stealing events in the drag.
   */
  private void attemptClaimDrag(boolean active) {
    final ViewParent parent = getParent();

    if (parent != null) {
      parent.requestDisallowInterceptTouchEvent(active);
    }
  }

  /**
   * This is called when the user has started touching this widget.
   */
  private void onStartTrackingTouch() {
    mIsDragging = true;
  }

  /**
   * This is called when the user either releases his touch or the touch is
   * canceled.
   */
  private void onStopTrackingTouch() {
    mIsDragging = false;
  }

  private boolean onTouchEventTraditionalRotation(MotionEvent event) {
    if (!isEnabled()) {
      return false;
    }

    final Drawable mThumb = getThumb();

    switch(event.getAction()) {
      case MotionEvent.ACTION_DOWN :
        setPressed(true);

        if (mThumb != null) {
          // This may be within the padding region
          invalidate(mThumb.getBounds());
        }

        onStartTrackingTouch();
        trackTouchEvent(event);
        attemptClaimDrag(true);

        break;

      case MotionEvent.ACTION_MOVE :
        if (mIsDragging) {
          trackTouchEvent(event);
        }

        break;

      case MotionEvent.ACTION_UP :
        if (mIsDragging) {
          trackTouchEvent(event);
          onStopTrackingTouch();
          setPressed(false);
        } else {
          // Touch up when we never crossed the touch slop threshold
          // should
          // be interpreted as a tap-seek to that location.
          onStartTrackingTouch();
          trackTouchEvent(event);
          onStopTrackingTouch();
          attemptClaimDrag(false);
        }

        // ProgressBar doesn't know to repaint the thumb drawable
        // in its inactive state when the touch stops (because the
        // value has not apparently changed)
        invalidate();

        break;

      case MotionEvent.ACTION_CANCEL :
        if (mIsDragging) {
          onStopTrackingTouch();
          setPressed(false);
        }

        invalidate();    // see above explanation

        break;
    }

    return true;
  }

  // refresh thumb position
  private void refreshThumb() {
    onSizeChanged(super.getWidth(), super.getHeight(), 0, 0);
  }

  private void trackTouchEvent(MotionEvent event) {
    final int   paddingTop    = super.getPaddingTop();
    final int   paddingBottom = super.getPaddingBottom();
    final int   height        = getHeight();
    final int   available     = height - paddingTop - paddingBottom;
    int         y             = (int) event.getY();
    final float scale;
    float       value = 0;

    switch(mRotationAngle) {
      case ROTATION_ANGLE_CW_90 :
        value = y - paddingTop;

        break;

      case ROTATION_ANGLE_CW_270 :
        value = (height - paddingTop) - y;

        break;
    }

    if ((value < 0) || (available == 0)) {
      return;
    } else if (value > available) {
      scale = 1.0f;
    } else {
      scale = value / available;
    }

    final int   max      = getMax();
    final float progress = scale * max;

    setProgress((int) progress);
  }

  private static boolean isValidRotationAngle(int angle) {
    return ((angle == ROTATION_ANGLE_CW_90) || (angle == ROTATION_ANGLE_CW_270));
  }
}
