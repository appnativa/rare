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

package com.appnativa.rare.ui;

import android.annotation.SuppressLint;

import android.content.Context;

import android.graphics.Canvas;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

import android.widget.ProgressBar;

import com.appnativa.rare.Platform;
import com.appnativa.rare.platform.android.ui.view.ViewEx;
import com.appnativa.rare.platform.android.ui.view.ViewGroupEx;
import com.appnativa.rare.ui.effects.ValueRangeAnimator;
import com.appnativa.rare.ui.effects.aAnimator.BoundsChanger;
import com.appnativa.rare.ui.effects.iAnimatorListener;
import com.appnativa.rare.ui.effects.iAnimatorValueListener;
import com.appnativa.rare.ui.effects.iPlatformAnimator;
import com.appnativa.rare.widget.iWidget;

@SuppressLint("ClickableViewAccessibility")
public class ImagePanel extends aImagePanel {
  ScaleGestureDetector scaleHandler;
  GestureDetector      touchHandler;
  UIPoint              pt = new UIPoint();

  public ImagePanel(iWidget context) {
    this((context == null)
         ? Platform.getAppContext().getActivity()
         : context.getAppContext().getActivity());
  }

  public ImagePanel(boolean useTransforms) {
    this(Platform.getAppContext().getActivity());
    this.usingTransforms = useTransforms;
  }

  public ImagePanel(Context context) {
    super(new ImageViewEx(context));
    ((ImageViewEx) view).panel = this;
  }

  public void setMovingAllowed(boolean allowed) {
    super.setMovingAllowed(allowed);

    if (allowed) {
      setupTouchHandler();
    }
  }

  public void setZoomingAllowed(boolean allowed) {
    super.setZoomingAllowed(allowed);

    if (allowed) {
      setupTouchHandler();
    }
  }

  protected void addSpinnerComponent(iPlatformComponent c) {
    ((ViewGroupEx) view).removeView(c.getView());
    ((ViewGroupEx) view).addView(c.getView());
  }

  protected iPlatformComponent createSpinnerComponent() {
    ProgressBar pb = new ProgressBar(view.getContext());

    pb.setIndeterminate(true);

    return new Component(pb);
  }

  protected void removeSpinnerComponent(iPlatformComponent c) {
    ((ViewGroupEx) view).removeView(c.getView());
  }

  @Override
  protected void handleSizeChangeAnimation(final BoundsChanger bc) {
    ValueRangeAnimator a = new ValueRangeAnimator(0f, 1f);

    a.addValueListener(new iAnimatorValueListener() {
      @Override
      public void valueChanged(iPlatformAnimator animator, float value) {
        destBounds.x      = bc.getX(value);
        destBounds.y      = bc.getY(value);
        destBounds.width  = bc.getWidth(value);
        destBounds.height = bc.getHeight(value);
        repaint();
      }
    });
    a.addListener(new iAnimatorListener() {
      @Override
      public void animationStarted(iPlatformAnimator animator) {
        if (animateSizeChangeListener != null) {
          animateSizeChangeListener.animationStarted(animator);
        }
      }
      @Override
      public void animationEnded(iPlatformAnimator animator) {
        destBounds.setBounds(bc.to);
        animatingSizeChange = false;

        if (animateSizeChangeListener != null) {
          animateSizeChangeListener.animationEnded(animator);
        }
      }
    });
    destBounds.setBounds(bc.from);
    animatingSizeChange = true;
    a.start();
  }

  protected void setupTouchHandler() {
    if (touchHandler == null) {
      AGestureListener gl = new AGestureListener();

      touchHandler = new GestureDetector(getView().getContext(), gl);
      touchHandler.setIsLongpressEnabled(false);
      touchHandler.setOnDoubleTapListener(gl);
      scaleHandler = new ScaleGestureDetector(getView().getContext(), gl);
    }
  }

  static class ImageViewEx extends ViewEx {
    ImagePanel      panel;
    private boolean hasListener;

    public ImageViewEx(Context context) {
      super(context);
    }

    public void callRequestLayout() {
      super.requestLayout();
    }

    public boolean dispatchTouchEvent(MotionEvent event) {
      if ((panel.theImage == null) ||!(panel.theImage.isLoaded())) {
        return super.dispatchTouchEvent(event);
      }

      if (panel.touchHandler == null) {
        return super.dispatchTouchEvent(event);
      }

      panel.touchHandler.onTouchEvent(event);
      panel.scaleHandler.onTouchEvent(event);

      if (hasListener) {
        super.dispatchTouchEvent(event);
      }

      return true;
    }

    @Override
    public void dispose() {
      super.dispose();
      panel = null;
    }

    @Override
    public void requestLayout() {}

    public void setOnTouchListener(OnTouchListener l) {
      hasListener = l != null;
      super.setOnTouchListener(l);
    }

    protected void onDraw(Canvas canvas) {
      panel.paint(graphics, 0, 0, getWidth(), getHeight());
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {}
  }


  private class AGestureListener extends GestureDetector.SimpleOnGestureListener
          implements ScaleGestureDetector.OnScaleGestureListener {
    float   baseSpan;
    boolean scaling;

    public boolean onDoubleTap(MotionEvent e) {
      if (!zoomingAllowed) {
        return true;
      }

      pt.set(e.getX(), e.getY());

      if (rotation != 0) {
        Utils.transformPoint(pt, rotation, getWidth(), getHeight());
      }

      zoomOnPoint(pt.x, pt.y);

      return true;
    }

    public boolean onFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {
      return false;
    }

    public boolean onScale(ScaleGestureDetector so) {
      pt.set(so.getFocusX(), so.getFocusY());

      if (rotation != 0) {
        Utils.transformPoint(pt, rotation, getWidth(), getHeight());
      }

      pinchZoomHandlerScale(pt.x, pt.y, so.getCurrentSpan() / baseSpan);

      return true;
    }

    public boolean onScaleBegin(ScaleGestureDetector so) {
      baseSpan = so.getCurrentSpan();
      pt.set(so.getFocusX(), so.getFocusY());

      if (rotation != 0) {
        Utils.transformPoint(pt, rotation, getWidth(), getHeight());
      }

      pinchZoomHandlerStart(pt.x, pt.y);
      scaling = true;

      return true;
    }

    public void onScaleEnd(ScaleGestureDetector so) {
      pt.set(so.getFocusX(), so.getFocusY());

      if (rotation != 0) {
        Utils.transformPoint(pt, rotation, getWidth(), getHeight());
      }

      pinchZoomHandlerEnd(pt.x, pt.y);
      scaling = false;
    }

    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
      if ((!movingAllowed &&!movingAllowedWhenToLarge) || scaling || (e1.getPointerCount() > 1)
          || (e2.getPointerCount() > 1)) {
        return false;
      }

      if (!movingAllowed && movingAllowedWhenToLarge && isImageFitted()) {
        return false;
      }

      float n;

      switch(rotation) {
        case 90 :
          n         = distanceX;
          distanceX = distanceY;
          distanceY = -n;

          break;

        case -90 :
        case 270 :
          n         = distanceX;
          distanceX = -distanceY;
          distanceY = n;

          break;

        case 180 :
          distanceX = -distanceX;
          distanceY = -distanceY;
        default :
          break;
      }

      moveImage(-distanceX, -distanceY);

      return true;
    }
  }
}
