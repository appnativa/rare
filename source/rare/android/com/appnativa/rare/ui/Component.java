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
import android.content.res.ColorStateList;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.ViewParent;

import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Checkable;
import android.widget.TextView;

import com.appnativa.rare.Platform;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.platform.android.ui.NullDrawable;
import com.appnativa.rare.platform.android.ui.PainterDrawable;
import com.appnativa.rare.platform.android.ui.iComponentView;
import com.appnativa.rare.platform.android.ui.iFlingSupport;
import com.appnativa.rare.platform.android.ui.util.AndroidGraphics;
import com.appnativa.rare.platform.android.ui.util.ImageUtils;
import com.appnativa.rare.platform.android.ui.view.BackgroundDrawable;
import com.appnativa.rare.platform.android.ui.view.EditTextEx;
import com.appnativa.rare.platform.android.ui.view.ListViewEx;
import com.appnativa.rare.ui.event.ChangeEvent;
import com.appnativa.rare.ui.event.MouseEvent;
import com.appnativa.rare.ui.event.iAndroidViewListener;
import com.appnativa.rare.ui.iPaintedButton.ButtonState;
import com.appnativa.rare.ui.listener.iFocusListener;
import com.appnativa.rare.ui.listener.iKeyListener;
import com.appnativa.rare.ui.listener.iMouseListener;
import com.appnativa.rare.ui.listener.iMouseMotionListener;
import com.appnativa.rare.ui.listener.iViewListener;
import com.appnativa.rare.ui.painter.PainterHolder;
import com.appnativa.rare.ui.painter.iComponentPainter;
import com.appnativa.rare.ui.painter.iPainterSupport;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;
import com.appnativa.rare.ui.painter.iPlatformPainter;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.lang.reflect.Constructor;

import java.util.HashMap;

/**
 *
 * @author Don DeCoteau
 */
@SuppressLint("ClickableViewAccessibility")
public class Component extends aComponent
        implements iAndroidViewListener, View.OnFocusChangeListener, View.OnTouchListener, View.OnKeyListener,
                   GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener,
                   ScaleGestureDetector.OnScaleGestureListener, OnLongClickListener, iPainterSupport,
                   PropertyChangeListener {
  public static boolean               SIZE_CACHE_ENABLED = false;
  GestureDetector                     gestureDetector;
  boolean                             mouseDown;
  protected iPlatformBorder           border;
  protected iPlatformComponentPainter componentPainter;
  protected UIColor                   fgColor;
  protected UIFont                    font;
  protected InteractionListener       interactionListener;
  protected View                      view;
  private boolean                     visible   = false;
  protected ComponentSizeCache        sizeCache = new ComponentSizeCache();
  private boolean                     crossPlatformMouseEventsEnabled;
  private AndroidGraphics             graphics;
  private int                         graphicsReference;
  private boolean                     hadFocus;
  private boolean                     hadInteraction;
  private boolean                     mouseOutside;

  public Component(View view) {
    this.view = view;
    view.setTag(this);
    useBorderInSizeCalculation = false;
  }

  protected Component() {
    useBorderInSizeCalculation = false;
  }

  public void addFocusListener(iFocusListener l) {
    super.addFocusListener(l);
    view.setOnFocusChangeListener(this);
  }

  public void addFocusListenerEx(View.OnFocusChangeListener l) {
    view.setOnFocusChangeListener(this);
    getEventListenerList().add(View.OnFocusChangeListener.class, l);
  }

  public void addKeyListener(iKeyListener l) {
    super.addKeyListener(l);
    view.setOnKeyListener(this);
  }

  public void addKeyListenerEx(View.OnKeyListener l) {
    view.setOnKeyListener(this);
    getEventListenerList().add(View.OnKeyListener.class, l);
  }

  public void addMouseListener(iMouseListener l) {
    super.addMouseListener(l);
    view.setOnTouchListener(this);
    crossPlatformMouseEventsEnabled = true;
  }

  public void addMouseListenerEx(View.OnTouchListener l) {
    view.setOnTouchListener(this);
    getEventListenerList().add(View.OnTouchListener.class, l);
  }

  public void addMouseMotionListener(iMouseMotionListener l) {
    super.addMouseMotionListener(l);
    view.setOnTouchListener(this);
    crossPlatformMouseEventsEnabled = true;
  }

  public void addMouseMotionListenerEx(View.OnTouchListener l) {
    view.setOnTouchListener(this);
    getEventListenerList().add(View.OnTouchListener.class, l);

    if ((view instanceof iFlingSupport) && (l instanceof GestureDetector.OnGestureListener)) {
      ((iFlingSupport) view).setFlingGestureListener((GestureDetector.OnGestureListener) l);
    }
  }

  public void addViewListener(iViewListener l) {
    super.addViewListener(l);
  }

  public void addViewListenerEx(iAndroidViewListener l) {
    getEventListenerList().add(iAndroidViewListener.class, l);
  }

  @Override
  public void bringToFront() {
    view.bringToFront();
  }

  @Override
  public UIImage capture() {
    return ImageUtils.createImage(view);
  }

  public Component copy() {
    try {
      Class       cls = view.getClass();
      Constructor ctr = cls.getConstructor(new Class[] { Context.class });
      View        v   = (View) ctr.newInstance(view.getContext());
      Component   c   = (Component) super.clone();

      c.listenerList = null;
      c.view         = v;

      if (properties != null) {
        c.properties = new HashMap<String, Object>(properties);
      }

      return c;
    } catch(CloneNotSupportedException ex) {
      throw new InternalError("CloneNotSupportedException");
    } catch(Exception ex) {
      throw new UnsupportedOperationException(ex);
    }
  }

  public void dispatchEvent(com.appnativa.rare.ui.event.KeyEvent keyEvent) {
    view.dispatchKeyEvent((KeyEvent) keyEvent.getNativeEvent());
  }

  public void dispatchEvent(com.appnativa.rare.ui.event.MouseEvent mouseEvent) {
    view.onTouchEvent(((MotionEvent) mouseEvent.getNativeEvent()));
  }

  public void dispose() {
    try {
      if (view != null) {
        view.setTag(null);
        view.setOnKeyListener(null);
        view.setOnFocusChangeListener(null);
        view.setOnTouchListener(null);
        unbindDrawables(view);

        if (view instanceof iComponentView) {
          ((iComponentView) view).dispose();
        }

        if ((view instanceof ViewGroup) &&!(view instanceof AdapterView)) {
          try {
            ((ViewGroup) view).removeAllViews();
          } catch(Exception ignore) {}
        }

        if (view.getParent() instanceof ViewGroup) {
          ((ViewGroup) view.getParent()).removeView(view);
        }
      }
    } catch(Exception e) {
      Platform.ignoreException("Disposing component", e);
    }

    super.dispose();

    if (componentPainter != null) {
      componentPainter.dispose();
    }

    view             = null;
    componentPainter = null;
    border           = null;
    font             = null;
    fgColor          = null;
  }

  @Override
  public Object getClientProperty(String key) {
    if (key == iConstants.RARE_CONSTRAINTS_PROPERTY) {
      return (constraints != null)
             ? constraints
             : view.getLayoutParams();
    }

    return super.getClientProperty(key);
  }

  public static iPlatformComponent findFromView(View view) {
    Object o = (view == null)
               ? null
               : view.getTag();

    while(!(o instanceof Component) && (view != null) && (view.getParent() instanceof View)) {
      view = (View) view.getParent();
      o    = view.getTag();
    }

    return (o instanceof Component)
           ? (Component) o
           : null;
  }

  public static Component fromView(View view) {
    Object o = (view == null)
               ? null
               : view.getTag();

    return (o instanceof Component)
           ? (Component) o
           : null;
  }

  @Override
  public void graphicsUnwrap(iPlatformGraphics g) {
    if (g == graphics) {
      graphicsReference--;

      if (graphicsReference <= 0) {
        graphics.clear();
        graphicsReference = 0;
      }
    } else {
      ((AndroidGraphics) g).clear();
    }
  }

  @Override
  public iPlatformGraphics graphicsWrap(Canvas g) {
    Canvas og = (graphics == null)
                ? null
                : graphics.getCanvas();

    if ((og != null) && (og != g)) {
      return new AndroidGraphics(g, this);
    }

    if ((graphics == null) || (og == null)) {
      graphics = AndroidGraphics.fromGraphics(g, view, graphics);
    }

    graphicsReference++;

    return graphics;
  }

  public void invalidate() {
    revalidate();
  }

  public void onAttachedToWindow(View view) {
    if (listenerList == null) {
      return;
    }

    if (!visible && (view.getVisibility() == View.VISIBLE)) {
      onVisibilityChanged(view, view, View.VISIBLE);
    }

    Object[] listeners = listenerList.getListenerList();

    for (int i = listeners.length - 2; i >= 0; i -= 2) {
      if (listeners[i] == iAndroidViewListener.class) {
        ((iAndroidViewListener) listeners[i + 1]).onAttachedToWindow(view);
      }
    }
  }

  public void onDetachedFromWindow(View view) {
    if (listenerList == null) {
      return;
    }

    if (visible && (view.getVisibility() == View.VISIBLE)) {
      onVisibilityChanged(view, view, View.GONE);
    }

    Object[] listeners = listenerList.getListenerList();

    for (int i = listeners.length - 2; i >= 0; i -= 2) {
      if (listeners[i] == iAndroidViewListener.class) {
        ((iAndroidViewListener) listeners[i + 1]).onDetachedFromWindow(view);
      }
    }
  }

  public boolean onDoubleTap(MotionEvent me) {
    MouseEvent e         = new MouseEvent(this, me, 0);
    Object[]   listeners = listenerList.getListenerList();

    for (int i = listeners.length - 2; i >= 0; i -= 2) {
      if (listeners[i] == iMouseListener.class) {
        ((iMouseListener) listeners[i + 1]).mousePressed(e);
      }
    }

    return false;
  }

  public boolean onDoubleTapEvent(MotionEvent me) {
    return false;
  }

  public boolean onDown(MotionEvent me) {
    MouseEvent e         = new MouseEvent(this, me, 0);
    Object[]   listeners = listenerList.getListenerList();

    for (int i = listeners.length - 2; i >= 0; i -= 2) {
      if (listeners[i] == iMouseListener.class) {
        ((iMouseListener) listeners[i + 1]).mousePressed(e);
      }
    }

    return e.isConsumed();
  }

  public boolean onFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {
    return true;
  }

  public void onFocusChange(View view, boolean hasFocus) {
    if (listenerList == null) {
      return;
    }

    Object[] listeners = listenerList.getListenerList();

    for (int i = listeners.length - 2; i >= 0; i -= 2) {
      if (listeners[i] == View.OnFocusChangeListener.class) {
        ((View.OnFocusChangeListener) listeners[i + 1]).onFocusChange(view, hasFocus);
      }
    }
  }

  public boolean onKey(View v, int keyCode, KeyEvent event) {
    if (listenerList == null) {
      return false;
    }

    Object[] listeners = listenerList.getListenerList();

    for (int i = listeners.length - 2; i >= 0; i -= 2) {
      if (listeners[i] == View.OnTouchListener.class) {
        if (((View.OnKeyListener) listeners[i + 1]).onKey(v, keyCode, event)) {
          return true;
        }
      }
    }

    com.appnativa.rare.ui.event.KeyEvent e = null;

    for (int i = listeners.length - 2; i >= 0; i -= 2) {
      if (listeners[i] == iKeyListener.class) {
        if (e == null) {
          e = new com.appnativa.rare.ui.event.KeyEvent(this, event);

          if (event.getAction() == android.view.KeyEvent.ACTION_UP) {
            ((iKeyListener) listeners[i + 1]).keyReleased(e);
            ((iKeyListener) listeners[i + 1]).keyTyped(e);
          } else {
            ((iKeyListener) listeners[i + 1]).keyPressed(e);
          }
        }
      }
    }

    return false;
  }

  public boolean onLongClick(View v) {
    return false;
  }

  public void onLongPress(MotionEvent me) {
    MouseEvent e         = null;
    Object[]   listeners = listenerList.getListenerList();

    for (int i = listeners.length - 2; i >= 0; i -= 2) {
      if (listeners[i] == iMouseListener.class) {
        if (e == null) {
          e = new MouseEvent(this, me, MouseEvent.LONG_PRESS);
        }

        ((iMouseListener) listeners[i + 1]).mouseReleased(e);
      }
    }
  }

  public boolean onScale(ScaleGestureDetector detector) {
    return false;
  }

  public boolean onScaleBegin(ScaleGestureDetector detector) {
    return false;
  }

  public void onScaleEnd(ScaleGestureDetector detector) {}

  public boolean onScroll(MotionEvent me1, MotionEvent me2, float distanceX, float distanceY) {
    MouseEvent e         = null;
    Object[]   listeners = listenerList.getListenerList();

    for (int i = listeners.length - 2; i >= 0; i -= 2) {
      if (listeners[i] == iMouseMotionListener.class) {
        if (e == null) {
          e = new MouseEvent(this, me1, MouseEvent.SCROLL, me2, distanceY, distanceY);
        }

        ((iMouseMotionListener) listeners[i + 1]).mouseDragged(e);
      }
    }

    return false;
  }

  public void onShowPress(MotionEvent e) {}

  public boolean onSingleTapConfirmed(MotionEvent e) {
    return false;
  }

  public boolean onSingleTapUp(MotionEvent me) {
    return false;
  }

  protected boolean mouseUp(MotionEvent me) {
    MouseEvent e         = new MouseEvent(this, me, 0);
    Object[]   listeners = listenerList.getListenerList();

    for (int i = listeners.length - 2; i >= 0; i -= 2) {
      if (listeners[i] == iMouseListener.class) {
        ((iMouseListener) listeners[i + 1]).mouseReleased(e);
      }
    }

    return e.isConsumed();
  }

  public void onSizeChanged(View view, int w, int h, int oldw, int oldh) {
    if ((listenerList == null) || ((oldw == w) && (oldh == h))) {
      return;
    }

    Object[] listeners = listenerList.getListenerList();

    for (int i = listeners.length - 2; i >= 0; i -= 2) {
      if (listeners[i] == iAndroidViewListener.class) {
        ((iAndroidViewListener) listeners[i + 1]).onSizeChanged(view, w, h, oldw, oldh);
      } else if (listeners[i] == iViewListener.class) {
        if (changeEvent == null) {
          changeEvent = new ChangeEvent(this);
        }

        ((iViewListener) listeners[i + 1]).viewResized(changeEvent);
      }
    }
  }

  public boolean onTouch(View view, MotionEvent me) {
    if (listenerList == null) {
      return false;
    }

    Object[] listeners = listenerList.getListenerList();

    for (int i = listeners.length - 2; i >= 0; i -= 2) {
      if (listeners[i] == View.OnTouchListener.class) {
        if (((View.OnTouchListener) listeners[i + 1]).onTouch(view, me)) {
          return true;
        }
      }
    }

    if (crossPlatformMouseEventsEnabled) {
      try {
        if (view.getWindowToken() == null) {
          return true;
        }

        if (me.getAction() == MotionEvent.ACTION_MOVE) {
          float x = me.getX();
          float y = me.getY();

          if ((x < 0) || (y < 0) || (y > view.getHeight()) || (x > view.getWidth())) {
            if (!mouseOutside) {
              mouseExited(me);
              mouseOutside = true;
            }
          } else if (mouseOutside) {
            mouseEntered(me);
            mouseOutside = false;
          }
        } else if (me.getAction() == MotionEvent.ACTION_UP) {
          mouseUp(me);
        }

        if (gestureDetector == null) {
          gestureDetector = new GestureDetector(view.getContext(), this);
          gestureDetector.setOnDoubleTapListener(this);
        }

        if ((gestureDetector != null)) {
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

    return false;
  }

  public void onVisibilityChanged(View view, View changedView, int visibility) {
    if (listenerList == null) {
      return;
    }

    boolean v = visibility == View.VISIBLE;

    if (v == visible) {
      // return;
    }

    visible = v;

    Object[] listeners = listenerList.getListenerList();

    for (int i = listeners.length - 2; i >= 0; i -= 2) {
      if (listeners[i] == iAndroidViewListener.class) {
        ((iAndroidViewListener) listeners[i + 1]).onVisibilityChanged(view, changedView, visibility);
      } else if (listeners[i] == iViewListener.class) {
        if (changeEvent == null) {
          changeEvent = new ChangeEvent(this);
        }

        if (visible) {
          ((iViewListener) listeners[i + 1]).viewShown(changeEvent);
        } else {
          ((iViewListener) listeners[i + 1]).viewHidden(changeEvent);
        }
      }
    }
  }

  public void propertyChange(PropertyChangeEvent event) {
    String name = event.getPropertyName();

    if (name == iComponentPainter.PROPERTY_BORDER) {
      border = componentPainter.getBorder();

      Drawable d = view.getBackground();

      if (!(d instanceof NullDrawable)) {
        view.setBackgroundDrawable(NullDrawable.getInstance());
      }

      setPaddingFromBorder(view, border);
    } else if (name == iComponentPainter.PROPERTY_PAINTER_HOLDER) {
      Drawable d = view.getBackground();

      if (!(d instanceof NullDrawable) || ((d != null) &&!d.isStateful())) {
        view.setBackgroundDrawable(NullDrawable.getStatefulInstance());
      }

      PainterHolder   ph = componentPainter.getPainterHolder();
      iPlatformBorder b  = ph.getBorder(ButtonState.DEFAULT);

      if (b == null) {
        b = border;
      }

      setPaddingFromBorder(view, b);
    } else {
      Object o = event.getNewValue();

      if (o instanceof iPlatformPainter) {
        Displayed d = ((iPlatformPainter) o).getDisplayed();

        switch(d) {
          case BEFORE_FIRST_FOCUS :
            addFocusListenerEx(getInteractionListener());

            break;

          case BEFORE_INTERACTION :
            addMouseListenerEx(getInteractionListener());

            break;

          default :
            break;
        }
      }
    }
  }

  public void putClientProperty(String key, Object value) {
    super.putClientProperty(key, value);

    if (key == iConstants.RARE_MIN_HEIGHT_PROPERTY) {
      if (sageMinHeight != null) {
        view.setMinimumHeight(ScreenUtils.toPlatformPixelHeight(sageMinHeight, this, -1));
      }
    } else if (key == iConstants.RARE_MIN_WIDTH_PROPERTY) {
      if (sageMinWidth != null) {
        view.setMinimumWidth(ScreenUtils.toPlatformPixelWidth(sageMinWidth, this, -1));
      }
    }
  }

  public void removeFocusListenerEx(View.OnFocusChangeListener l) {
    remove(View.OnFocusChangeListener.class, l);
  }

  public void removeKeyListenerEx(View.OnKeyListener l) {
    remove(View.OnKeyListener.class, l);
  }

  public void removeMouseListenerEx(View.OnTouchListener l) {
    remove(View.OnTouchListener.class, l);
  }

  public void removeViewListenerEx(iAndroidViewListener l) {
    remove(iAndroidViewListener.class, l);
  }

  public void repaint() {
    if (Platform.isUIThread()) {
      view.invalidate();
    } else {
      view.postInvalidate();
    }
  }

  public void requestFocus() {
    view.requestFocus();
  }

  public void revalidate() {
    sizeCache.preferredDirty = true;
    sizeCache.minDirty       = true;

    if (Platform.isUIThread()) {
      view.requestLayout();
    } else {
      Platform.invokeLater(new Runnable() {
        public void run() {
          if (view != null) {
            view.requestLayout();
          }
        }
      });
    }
  }

  @Override
  public void removeFromParent() {
    ViewParent vp = (view == null)
                    ? null
                    : view.getParent();

    if (vp instanceof ViewGroup) {
      ((ViewGroup) vp).removeView(view);
    }
  }

  @Override
  public void sendToBack() {
    if (view.getParent() instanceof ViewGroup) {
      ViewGroup p   = (ViewGroup) view.getParent();
      int       len = p.getChildCount();
      int       n   = p.indexOfChild(view);

      if ((len > 1) && (n != len - 1)) {
        View[] a = new View[len - 1];
        int    j = len - 2;

        for (int i = 0; i < len; i++) {
          if (i != n) {
            a[j--] = p.getChildAt(i);
          }
        }

        for (View v : a) {
          p.bringChildToFront(v);
        }
      }
    }
  }

  public void setBackground(UIColor bg) {
    if (view instanceof iPainterSupport) {
      if ((bg == null) || (bg.getAlpha() == 0)) {
        iPlatformComponentPainter cp = getComponentPainter(false);

        if (cp != null) {
          cp.setBackgroundColor(bg, true);
        }
      } else {
        getComponentPainter(true).setBackgroundColor(bg, true);
      }
    } else {
      if ((bg != null) && (bg.getAlpha() > 0)) {
        view.setBackgroundDrawable(bg.getDrawable());
      } else {
        view.setBackgroundDrawable(null);
      }
    }
  }

  public void setBorder(iPlatformBorder border) {
    this.border = border;

    if (view instanceof iPainterSupport) {
      if (componentPainter == null) {
        getComponentPainter(true);
      }

      componentPainter.setBorder(border);
    } else {
      view.setBackgroundDrawable((border == null)
                                 ? NullDrawable.getInstance()
                                 : border.getDrawable(view));
    }

    revalidate();
  }

  public void setBounds(float x, float y, float w, float h) {
    setBounds(UIScreen.snapToPosition(x), UIScreen.snapToPosition(y), UIScreen.snapToSize(w), UIScreen.snapToSize(h));
  }

  public void setBounds(int x, int y, int w, int h) {
    sizeCache.minDirty             = true;
    sizeCache.minDirty             = true;
    sizeCache.resuestLayoutChecked = false;

    if (!isAnimating()) {
      if (view.isLayoutRequested() || (view.getMeasuredWidth() != w) || (view.getMeasuredHeight() != h)
          || (view instanceof ViewGroup)) {
        view.measure(MeasureSpec.makeMeasureSpec(w, MeasureSpec.EXACTLY),
                     MeasureSpec.makeMeasureSpec(h, MeasureSpec.EXACTLY));
        view.forceLayout();
      }
    }

    view.layout(x, y, x + w, y + h);
  }

  public void setComponentPainter(iPlatformComponentPainter cp) {
    if (cp != componentPainter) {
      if (componentPainter != null) {
        componentPainter.removePropertyChangeListener(this);
      }

      componentPainter = cp;

      if (componentPainter != null) {
        componentPainter.removePropertyChangeListener(this);
        componentPainter.addPropertyChangeListener(this);
      }

      if (view instanceof iPainterSupport) {
        ((iPainterSupport) view).setComponentPainter(cp);

        iPlatformBorder b = (cp == null)
                            ? null
                            : cp.getBorderAlways();

        if ((cp != null) && (cp.getPainterHolder() != null)) {
          view.setBackgroundDrawable(NullDrawable.getStatefulInstance());
        }

        setPaddingFromBorder(view, b);

        return;
      }

      if (cp != null) {
        view.setBackgroundDrawable(new PainterDrawable(view, cp));
      } else {
        if (componentPainter != null) {
          view.setBackgroundDrawable(null);
        }
      }
    }
  }

  public void setCursor(UICursor cursor) {}

  public void setEnabled(boolean enabled) {
    view.setEnabled(enabled);
  }

  public void setFocusable(boolean focusable) {
    view.setFocusable(focusable);
  }

  public void setFont(UIFont f) {
    if (font != f) {
      font = f;

      if (view instanceof TextView) {
        if (f == null) {
          f = FontUtils.getDefaultFont();
        }

        TextView tv = (TextView) view;

        f.setupTextView(tv);
      }

      revalidate();
    }
  }

  public void setForeground(UIColor fg) {
    fgColor = fg;

    if (fg != null) {
      if (view instanceof TextView) {
        fg.setTextColor((TextView) view);
      }

      if (componentPainter != null) {
        componentPainter.setForegroundColor(fg);
      }
    }
  }

  @Override
  public void setLocation(float x, float y) {
    setBounds(UIScreen.snapToPosition(x), UIScreen.snapToPosition(y), getWidth(), getHeight());
  }

  public void setOpaque(boolean opaque) {
    super.setOpaque(opaque);

    if (!opaque) {
      view.setBackgroundDrawable(NullDrawable.getInstance());
    }
  }

  public void setSelected(boolean selected) {
    view.setSelected(selected);
  }

  @Override
  public void setSize(float width, float height) {
    setBounds(view.getLeft(), view.getTop(), width, height);
  }

  public void setVisible(boolean visible) {
    view.setVisibility(visible
                       ? View.VISIBLE
                       : View.GONE);

    final ViewParent vp = view.getParent();

    if (vp instanceof ViewGroup) {
      ((ViewGroup) vp).forceLayout();
    }
  }

  public UIColor getBackgroundEx() {
    if (componentPainter != null) {
      return componentPainter.getBackgroundColor();
    }

    Drawable d = view.getBackground();

    if (d instanceof BackgroundDrawable) {
      return ((BackgroundDrawable) d).getColor();
    }

    return null;
  }

  public iPlatformBorder getBorder() {
    if (componentPainter != null) {
      return componentPainter.getBorder();
    }

    return border;
  }

  public UIRectangle getBounds() {
    return new UIRectangle(view.getLeft(), view.getTop(), view.getWidth(), view.getHeight());
  }

  public iPlatformComponent getComponentForView(View view) {
    if (view == null) {
      return null;
    }

    Object o = view.getTag();

    if (o instanceof Component) {
      return (iPlatformComponent) o;
    }

    return null;
  }

  public UIFont getFontEx() {
    return font;
  }

  public UIColor getForeground() {
    if (fgColor == null) {
      ColorStateList csl = null;

      if (view instanceof TextView) {
        csl = ((TextView) view).getTextColors();
      }

      if (csl == null) {
        iParentComponent p = getParent();

        if (p != null) {
          return p.getForeground();
        }

        return UIColorHelper.getForeground();
      }

      fgColor = new UIColor(csl.getDefaultColor());
    }

    return fgColor;
  }

  public UIColor getForegroundEx() {
    return fgColor;
  }

  public int getHeight() {
    return view.getHeight();
  }

  public UIPoint getLocationOnScreen(UIPoint pt) {
    if (pt == null) {
      pt = new UIPoint();
    }

    if (!view.isShown()) {
      return null;
    }

    if (this instanceof WindowPane) {
      int[] loc = new int[2];

      view.getLocationOnScreen(loc);
      pt.x = loc[0];
      pt.y = loc[1];

      return pt;
    }

    pt.x = 0;
    pt.y = 0;

    View       v = view;
    ViewParent vp;
    int        x = v.getLeft();
    int        y = v.getTop();
    int        n;

    while(v != null) {
      vp = v.getParent();

      if (vp instanceof View) {
        v = (View) vp;
      } else {
        break;
      }

      Component c = fromView(v);

      if (c instanceof WindowPane) {
        pt.x += x;
        pt.y += y;

        int[] loc = new int[2];

        c.getView().getLocationOnScreen(loc);
        x = loc[0];
        y = loc[1];

        break;
      } else if (c != null) {
        switch(c.getOrientation()) {
          case VERTICAL_DOWN :
            n = x;
            x = y;
            y = n;

            break;

          case VERTICAL_UP :
            n = x;
            x = y;
            y = v.getHeight() - n;

            break;

          default :
            break;
        }
      }

      pt.x += x;
      pt.y += y;
      x    = v.getLeft();
      y    = v.getTop();
    }

    pt.x += x;
    pt.y += y;

    return pt;
  }

  public Object getNativeView() {
    return view;
  }

  public iParentComponent getParent() {
    if (view == null) {
      return null;
    }

    ViewParent vp = view.getParent();
    View       v  = null;

    if (vp instanceof View) {
      v = (View) vp;
    }

    Object o = ((v == null) || (v == view))
               ? null
               : v.getTag();

    if ((v != null) && ((o == null) ||!(o instanceof iParentComponent))) {
      o = findFromView(v);
    }

    return (iParentComponent) o;
  }

  public UIDimension getSize(UIDimension size) {
    if (size == null) {
      size = new UIDimension();
    }

    size.setSize(view.getWidth(), view.getHeight());

    return size;
  }

  public void getSizeConstraints(UIDimension size, int maxWidth, int maxHeight, boolean enforceMax) {
    if (sageHeight != null) {
      size.height = ScreenUtils.toPlatformPixelHeight(sageHeight, this, maxHeight);

      if ((size.height > maxHeight) && (maxHeight > 0) && enforceMax) {
        size.height = maxHeight;
      }
    } else {
      size.height = -1;
    }

    if (sageWidth != null) {
      size.width = ScreenUtils.toPlatformPixelWidth(sageWidth, this, maxWidth);

      if ((size.width > maxWidth) && (maxWidth > 0) && enforceMax) {
        size.width = maxWidth;
      }
    } else {
      size.width = -1;
    }
  }

  public View getView() {
    return view;
  }

  public iAndroidViewListener getViewListener() {
    return (listenerList == null)
           ? null
           : this;
  }

  public int getWidth() {
    return view.getWidth();
  }

  public int getX() {
    return view.getLeft();
  }

  public int getY() {
    return view.getTop();
  }

  public boolean hasBeenFocused() {
    return hadFocus;
  }

  public boolean hasChildren() {
    return (view instanceof ViewGroup) && ((ViewGroup) view).getChildCount() > 0;
  }

  public boolean hasHadInteraction() {
    return hadInteraction;
  }

  public boolean hasMouseListeners() {
    if (super.hasMouseListeners()) {
      return true;
    }

    return (listenerList != null) && (listenerList.getListenerCount(View.OnTouchListener.class) > 0);
  }

  public boolean isDisplayable() {
    return (view != null) && (view.getWindowToken() != null);
  }

  public boolean isEnabled() {
    return view.isEnabled();
  }

  public boolean isFocusOwner() {
    return view.isFocused();
  }

  public boolean isFocusable() {
    return view.isFocusable();
  }

  public boolean isPressed() {
    return view.isPressed();
  }

  public boolean isSelectable() {
    if (view instanceof Checkable) {
      return true;
    }

    return false;
  }

  public boolean isSelected() {
    return view.isSelected();
  }

  public boolean isShowing() {
    return (view != null) && view.isShown();
  }

  public boolean isVisible() {
    return (view != null) && (view.getVisibility() == View.VISIBLE);
  }

  protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
    if (changeSupport != null) {
      changeSupport.firePropertyChange(propertyName, oldValue, newValue);
    }
  }

  protected void interacted() {}

  protected void mouseEntered(MotionEvent me) {
    MouseEvent e         = null;
    Object[]   listeners = listenerList.getListenerList();

    for (int i = listeners.length - 2; i >= 0; i -= 2) {
      if (e == null) {
        e = new MouseEvent(this, me, 0);
      }

      if (listeners[i] == iMouseListener.class) {
        ((iMouseListener) listeners[i + 1]).mouseExited(e);
      }
    }
  }

  protected void mouseExited(MotionEvent me) {
    MouseEvent e         = null;
    Object[]   listeners = listenerList.getListenerList();

    for (int i = listeners.length - 2; i >= 0; i -= 2) {
      if (e == null) {
        e = new MouseEvent(this, me, 0);
      }

      if (listeners[i] == iMouseListener.class) {
        ((iMouseListener) listeners[i + 1]).mouseExited(e);
      }
    }
  }

  protected void setPaddingFromBorder(View v, iPlatformBorder b) {
    if (b == null) {
      if (v instanceof EditTextEx) {
        v.setPadding(ScreenUtils.PLATFORM_PIXELS_2, ScreenUtils.PLATFORM_PIXELS_2, ScreenUtils.PLATFORM_PIXELS_2,
                     ScreenUtils.PLATFORM_PIXELS_2);
      } else {
        v.setPadding(0, 0, 0, 0);
      }
    } else {
      UIInsets in = b.getBorderInsets(computeInsets);

      if (v instanceof EditTextEx) {
        in.addInsets(ScreenUtils.PLATFORM_PIXELS_2, ScreenUtils.PLATFORM_PIXELS_2, ScreenUtils.PLATFORM_PIXELS_2,
                     ScreenUtils.PLATFORM_PIXELS_2);
      }

      v.setPadding(in.intLeft(), in.intTop(), in.intRight(), in.intBottom());
    }

    revalidate();
  }

  protected void setView(View view) {
    this.view = view;
    view.setTag(this);
  }

  protected iPlatformComponentPainter getComponentPainterEx() {
    return componentPainter;
  }

  protected InteractionListener getInteractionListener() {
    if (interactionListener == null) {
      interactionListener = new InteractionListener();
    }

    return interactionListener;
  }

  protected void getMinimumSizeEx(UIDimension size) {
    if (!SIZE_CACHE_ENABLED) {
      sizeCache.minDirty = true;
    }

    if (!sizeCache.resuestLayoutChecked) {
      if (view.isLayoutRequested()) {
        sizeCache.minDirty             = true;
        sizeCache.preferredDirty       = true;
        sizeCache.resuestLayoutChecked = true;
      }
    }

    if (!sizeCache.minDirty) {
      size.width  = sizeCache.minWidth;
      size.height = sizeCache.minHeight;
    } else {
      if (view instanceof iComponentView) {
        size.height = ((iComponentView) view).getSuggestedMinimumHeight();
        size.width  = ((iComponentView) view).getSuggestedMinimumWidth();
      } else {
        size.setSize(view.getPaddingLeft() + view.getPaddingRight(), view.getPaddingTop() + view.getPaddingBottom());
      }

      sizeCache.minDirty  = false;
      sizeCache.minHeight = size.height;
      sizeCache.minWidth  = size.width;
    }
  }

  protected void getPreferredSizeEx(UIDimension size, float maxWidth) {
    if (!SIZE_CACHE_ENABLED) {
      sizeCache.preferredDirty = true;
    }

    if (!sizeCache.resuestLayoutChecked) {
      if (view.isLayoutRequested()) {
        sizeCache.minDirty             = true;
        sizeCache.preferredDirty       = true;
        sizeCache.resuestLayoutChecked = true;
      }
    }

    if (!sizeCache.preferredDirty && ((maxWidth == 0) || (sizeCache.preferredHeightforWidthWidth == maxWidth))) {
      if (maxWidth > 0) {
        size.width  = maxWidth;
        size.height = sizeCache.preferredHeightforWidth;
      } else {
        size.width  = sizeCache.preferredWidth;
        size.height = sizeCache.preferredHeight;
      }
    } else {
      int wms;

      if (maxWidth > 0) {
        wms = MeasureSpec.makeMeasureSpec((int) Math.ceil(maxWidth), MeasureSpec.AT_MOST);
      } else {
        wms = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
      }

      view.measure(wms, MeasureSpec.UNSPECIFIED);

      if (view instanceof ListViewEx) {
        size.setSize(view.getMeasuredWidth() + ScreenUtils.PLATFORM_PIXELS_16,
                     ((ListViewEx) view).getPreferredHeight());
      } else {
        size.setSize(view.getMeasuredWidth(), view.getMeasuredHeight());
      }

      sizeCache.preferredDirty = false;

      if (maxWidth > 0) {
        sizeCache.preferredHeightforWidth      = size.height;
        sizeCache.preferredHeightforWidthWidth = maxWidth;
      } else {
        sizeCache.preferredHeight = size.height;
        sizeCache.preferredWidth  = size.width;
      }
    }
  }

  private void remove(Class cls, Object l) {
    if (listenerList != null) {
      listenerList.remove(cls, l);
    }
  }

  private void unbindDrawables(View view) {
    try {
      if (view != null) {
        if (view.getBackground() != null) {
          view.getBackground().setCallback(null);
        }

        if (view instanceof ViewGroup) {
          for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
            unbindDrawables(((ViewGroup) view).getChildAt(i));
          }
        }
      }
    } catch(Throwable e) {
      Platform.ignoreException("Unbindind drawables", e);
    }
  }

  protected class InteractionListener implements View.OnTouchListener, View.OnFocusChangeListener, Runnable {
    public void interacted() {
      hadInteraction = true;
      repaint();
      Platform.invokeLater(this);
    }

    public void onFocusChange(View v, boolean hasFocus) {
      hadFocus = true;
      repaint();
      Platform.invokeLater(this);
    }

    public boolean onTouch(View v, MotionEvent event) {
      interacted();

      return false;
    }

    public void run() {
      if (hadFocus && (view != null)) {
        removeFocusListenerEx(this);

        if (listenerList != null) {
          if ((listenerList.getListenerCount(View.OnFocusChangeListener.class) == 0)
              && (listenerList.getListenerCount(View.OnFocusChangeListener.class) == 0)) {
            view.setOnFocusChangeListener(null);
          }
        }
      }

      if (hadInteraction && (view != null)) {
        removeMouseListenerEx(this);

        if (!hasMouseListeners() &&!hasMouseMotionListeners()) {
          view.setOnTouchListener(null);
        }
      }
    }
  }
}
