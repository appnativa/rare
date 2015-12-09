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

import java.util.Map;

import android.annotation.SuppressLint;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.widget.AbsListView;

import com.appnativa.rare.Platform;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.platform.android.MainActivity;
import com.appnativa.rare.scripting.iScriptHandler;
import com.appnativa.rare.ui.event.ChangeEvent;
import com.appnativa.rare.ui.event.EventBase;
import com.appnativa.rare.ui.event.FlingEvent;
import com.appnativa.rare.ui.event.FocusEvent;
import com.appnativa.rare.ui.event.KeyEvent;
import com.appnativa.rare.ui.event.MouseEvent;
import com.appnativa.rare.ui.event.ScaleEvent;
import com.appnativa.rare.ui.event.iAndroidViewListener;
import com.appnativa.rare.widget.iWidget;

/**
 * This class is responsible for invoking scripts for triggered events. It
 * implements all the standard listeners and will invoke the appropriate
 * scripting function when when it receives and event notification.
 *
 * It also provides methods for executing arbitrary events and for checking
 * whether a particular event is enabled for the widget.
 *
 * @author Don DeCoteau
 */
@SuppressWarnings("unchecked")
public class WidgetListener extends aWidgetListener
        implements iAndroidViewListener, View.OnFocusChangeListener, OnKeyListener, OnTouchListener,
                   GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener,
                   ScaleGestureDetector.OnScaleGestureListener, OnLongClickListener {
  GestureDetector      gestureDetector;
  boolean              mouseDown;
  boolean              mouseOutside;
  ScaleGestureDetector scaleHandler;
  private long         scaleEndTime;
  private int          scaleFlingTimeSlop;
  private float        baseSpan;

  /**
   * Creates a new instance of WidgetListenerHelper
   *
   * @param widget
   *          the widget the listener is for
   * @param map
   *          the widget's event map
   * @param sh
   *          the widget's script handler
   */
  public WidgetListener(iWidget widget, Map map, iScriptHandler sh) {
    super(widget, map, sh);

    Integer i = Platform.getUIDefaults().getInteger("Rare.Pointer.scaleFlingTimeSlop");

    scaleFlingTimeSlop = (i == null)
                         ? 300
                         : i;
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    //override because double tap fires a mouse release event
    if (e.getClickCount() > 1) {
      if (isEnabled(iConstants.EVENT_DOUBLECLICK)) {
        execute(iConstants.EVENT_DOUBLECLICK, e);
      }
    } else {
      super.mouseReleased(e);
    }
  }

  public void onAttachedToWindow(View view) {
    if ((view.getVisibility() == View.VISIBLE)) {
      if (isEnabled(iConstants.EVENT_SHOWN)) {
        viewShown(new ChangeEvent(getSource(view)));
      }
    }
  }

  public void onDetachedFromWindow(View view) {
    if (isEnabled(iConstants.EVENT_HIDDEN)) {
      viewHidden(new ChangeEvent(getSource(view)));
    }
  }

  public boolean onDoubleTap(MotionEvent me) {
    if (isEnabled(iConstants.EVENT_DOUBLECLICK)) {
      MouseEvent e = new MouseEvent(getSource(null), me, 0);

      evaluate(iConstants.EVENT_DOUBLECLICK, e, false);

      return e.isConsumed();
    }

    return false;
  }

  public boolean onDoubleTapEvent(MotionEvent me) {
    return false;
  }

  public boolean onDown(MotionEvent me) {
    if (isEnabled(iConstants.EVENT_MOUSE_DOWN)) {
      MouseEvent e = new MouseEvent(getSource(null), me, 0);

      evaluate(iConstants.EVENT_MOUSE_DOWN, e, false);

      if (e.isConsumed()) {
        return true;
      }
    }

    return false;
  }

  public boolean onFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {
    if ((me1.getEventTime() - scaleEndTime) > scaleFlingTimeSlop) {
      if ((theWidget != null) && theWidget.isShowing()) {
        iPlatformComponent source = getSource(null);

        if (isEnabled(iConstants.EVENT_FLING)) {
          FlingEvent e = new FlingEvent(source, me1, MouseEvent.FLING, me2, velocityX, velocityY);

          evaluate(iConstants.EVENT_FLING, e, false);

          return e.isConsumed();
        }

        if (isEnabled(iConstants.EVENT_SCROLL)) {
          FlingEvent e = new FlingEvent(source, me1, MouseEvent.FLING, me2, velocityX, velocityY);

          evaluate(iConstants.EVENT_SCROLL, e, false);

          return e.isConsumed();
        }
      }

      return false;
    }

    return true;
  }

  public void onFocusChange(View view, boolean hasFocus) {
    if ((theWidget != null) &&!theWidget.getAppContext().isShuttingDown()) {
      FocusEvent e = new FocusEvent(getSource(view), hasFocus);

      execute(hasFocus
              ? iConstants.EVENT_FOCUS
              : iConstants.EVENT_BLUR, e);
    }
  }

  public boolean onKey(View view, int keyCode, android.view.KeyEvent ke) {
    KeyEvent e = new KeyEvent(view, ke);

    evaluate((ke.getAction() == android.view.KeyEvent.ACTION_UP)
             ? iConstants.EVENT_KEY_UP
             : iConstants.EVENT_KEY_DOWN, e, false);

    return e.isConsumed();
  }

  public boolean onLongClick(View view) {
    if (isEnabled(iConstants.EVENT_CONTEXTMENU)) {
      MainActivity ma = (MainActivity) Platform.getAppContext().getActivity();
      MouseEvent   e  = new MouseEvent(getSource(null), ma.getLastMotionEvent(), MouseEvent.LONG_PRESS);

      evaluate(iConstants.EVENT_CONTEXTMENU, e, false);

      return true;
    }

    return false;
  }

  public void onLongPress(MotionEvent me) {
    if (isEnabled(iConstants.EVENT_CONTEXTMENU)) {
      MouseEvent e = new MouseEvent(getSource(null), me, MouseEvent.LONG_PRESS);

      evaluate(iConstants.EVENT_CONTEXTMENU, e, false);
    } else if (isEnabled(iConstants.EVENT_CLICK)) {
      MouseEvent e = new MouseEvent(getSource(null), me, MouseEvent.LONG_PRESS);

      evaluate(iConstants.EVENT_CLICK, e, false);
    }
  }

  protected PinchZoomHandler pinchZoom;

  protected void pinchZoomHandlerStart(ScaleGestureDetector sgd) {
    if (pinchZoom == null) {
      if (pinchZoom == null) {
        pinchZoom = new PinchZoomHandler(0, 100);
      }
    }

    baseSpan = sgd.getCurrentSpan();

    UIRectangle r = theWidget.getContainerComponent().getBounds();

    pinchZoom.resetBounds(r, r.width, r.height, 1);
    pinchZoom.scaleStart(sgd.getFocusX(), sgd.getFocusY());
  }

  protected boolean pinchZoomHandlerScale(ScaleGestureDetector sgd) {
    if ((pinchZoom != null) && isEnabled(iConstants.EVENT_SCALE)) {
      if (pinchZoom.scale(sgd.getFocusX(), sgd.getFocusY(), sgd.getCurrentSpan() / baseSpan)) {
        ScaleEvent e = new ScaleEvent(getSource(null), sgd, ScaleEvent.Type.SCALE, pinchZoom.getScale());
        Object     o = evaluate(iConstants.EVENT_SCALE, e, false);

        if (o instanceof Boolean) {
          return Boolean.TRUE.equals(o);
        }

        return true;
      }
    }

    return false;
  }

  public boolean onScale(ScaleGestureDetector sgd) {
    if (isEnabled(iConstants.EVENT_SCALE) && pinchZoomHandlerScale(sgd)) {
      ScaleEvent e = new ScaleEvent(getSource(null), sgd, ScaleEvent.Type.SCALE, pinchZoom.getScale());
      Object     o = evaluate(iConstants.EVENT_SCALE, e, false);

      if (o instanceof Boolean) {
        return Boolean.TRUE.equals(o);
      }

      return true;
    }

    return false;
  }

  public boolean onScaleBegin(ScaleGestureDetector sgd) {
    if (isEnabled(iConstants.EVENT_SCALE)) {
      pinchZoomHandlerStart(sgd);

      ScaleEvent e = new ScaleEvent(getSource(null), sgd, ScaleEvent.Type.SCALE_BEGIN, 1);
      Object     o = evaluate(iConstants.EVENT_SCALE, e, false);

      if (o instanceof Boolean) {
        return Boolean.TRUE.equals(o);
      }

      return true;
    }

    return false;
  }

  public void onScaleEnd(ScaleGestureDetector sgd) {
    if (isEnabled(iConstants.EVENT_SCALE) && (pinchZoom != null)) {
      pinchZoom.scaleEnd(sgd.getFocusX(), sgd.getFocusY());
      scaleEndTime = sgd.getEventTime();

      ScaleEvent e = new ScaleEvent(getSource(null), sgd, ScaleEvent.Type.SCALE_END, pinchZoom.getScale());

      evaluate(iConstants.EVENT_SCALE, e, false);
    }
  }

  public boolean onScroll(MotionEvent me, MotionEvent me1, float distanceX, float distanceY) {
    if (isMouseMotionEventsEnabled()) {
      MouseEvent e = new MouseEvent(getSource(null), me, MouseEvent.SCROLL, me1, distanceY, distanceY);

      evaluate(iConstants.EVENT_MOUSE_DRAGGED, e, false);

      if (e.isConsumed()) {
        return true;
      }
    }

    return false;
  }

  public void onShowPress(MotionEvent me) {}

  public boolean onSingleTapConfirmed(MotionEvent me) {
    if (isEnabled(iConstants.EVENT_CLICK)) {
      MouseEvent e = new MouseEvent(getSource(null), me, 0);

      evaluate(iConstants.EVENT_CLICK, e, false);

      return e.isConsumed();
    }

    return false;
  }

  public boolean onSingleTapUp(MotionEvent me) {
    if (isEnabled(iConstants.EVENT_MOUSE_UP)) {
      MouseEvent e = new MouseEvent(getSource(null), me, 0);

      evaluate(iConstants.EVENT_MOUSE_UP, e, false);

      return e.isConsumed();
    }

    return false;
  }

  public void onSizeChanged(View view, int w, int h, int oldw, int oldh) {
    if (isEnabled(iConstants.EVENT_RESIZE)) {
      Component c = Component.fromView(view);

      if (c == null) {
        c = new Component(view);
      }

      evaluate(iConstants.EVENT_RESIZE, new EventBase(c), false);
    }
  }

  @SuppressLint("ClickableViewAccessibility")
  public boolean onTouch(View view, MotionEvent me) {
    try {
      if (view.getWindowToken() == null) {
        return true;
      }

      if ((scaleHandler == null) && scaleEventsEnabled) {
        scaleHandler = new ScaleGestureDetector(view.getContext(), this);
      }

      if (scaleHandler != null) {
        scaleHandler.onTouchEvent(me);
      }

      if ((gestureDetector == null) && (mouseEventsEnabled || mouseMotionEventsEnabled)) {
        gestureDetector = new GestureDetector(view.getContext(), this);
        gestureDetector.setOnDoubleTapListener(this);
      }

      if ((gestureDetector != null) && ((scaleHandler == null) ||!scaleHandler.isInProgress())) {
        if (view instanceof AbsListView) {
          return gestureDetector.onTouchEvent(me);
        } else {
          gestureDetector.onTouchEvent(me);
        }
      }
    } catch(Exception ignore) {
      Platform.ignoreException("Possible view hierarchy change while in event", ignore);
    }

    return true;
  }

  public void onVisibilityChanged(View view, View changedView, int visibility) {
    if (visibility != View.VISIBLE) {
      if (isEnabled(iConstants.EVENT_HIDDEN)) {
        viewHidden(new ChangeEvent(getSource(view)));
      }

      return;
    }

    if (isEnabled(iConstants.EVENT_SHOWN) && (view.getWindowToken() != null)) {
      viewShown(new ChangeEvent(getSource(view)));
    }
  }

  protected iPlatformComponent getSource(Object v) {
    Component c = (v == null)
                  ? null
                  : Component.fromView((View) v);

    if (c == null) {
      c = (Component) theWidget.getDataComponent();
    }

    return c;
  }
}
