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

import com.appnativa.rare.iConstants;
import com.appnativa.rare.platform.apple.ui.util.ActionMap;
import com.appnativa.rare.platform.apple.ui.util.ImageUtils;
import com.appnativa.rare.platform.apple.ui.view.View;
import com.appnativa.rare.ui.RenderableDataItem.Orientation;
import com.appnativa.rare.ui.iPaintedButton.ButtonState;
import com.appnativa.rare.ui.effects.aAnimator;
import com.appnativa.rare.ui.event.KeyEvent;
import com.appnativa.rare.ui.event.MouseEvent;
import com.appnativa.rare.ui.listener.iKeyListener;
import com.appnativa.rare.ui.listener.iMouseListener;
import com.appnativa.rare.ui.listener.iMouseMotionListener;
import com.appnativa.rare.ui.listener.iViewListener;
import com.appnativa.rare.ui.painter.PainterHolder;
import com.appnativa.rare.ui.painter.UISimpleBackgroundPainter;
import com.appnativa.rare.ui.painter.iBackgroundPainter;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;
import com.appnativa.rare.ui.painter.iPlatformPainter;

/**
 * Main UI Component
 * @author Don DeCoteau
 */
public class Component extends aComponent implements iGestureListener {
  protected View           view;
  private ActionMap        actionMap;
  private iParentComponent parent;
  boolean                  scaleGestureListenerAdded;
  boolean                  rotateGestureListenerAdded;
  boolean                  flingGestureListenerAdded;
  boolean                  longPressGestureListenerAdded;
  protected UIColor        backgroundColor;
  protected boolean        foregroundSet;
  protected UIColor        fgColor;

  public Component(View view) {
    this.view = view;
    view.setComponent(this);
    useBorderInSizeCalculation = true;
  }

  protected Component() {
    useBorderInSizeCalculation = true;
  }

  public void addFlingListener(iGestureListener l) {
    removeGestureListener(l);
    getEventListenerList().add(iGestureListener.class, l);

    if (!flingGestureListenerAdded) {
      flingGestureListenerAdded = true;
      view.setFlingGestureListener(this);
    }
  }

  public void addInteactionListener() {
    if (!interactionListenerAdded) {
      view.setMouseListener(this);
      interactionListenerAdded = true;
    }
  }

  @Override
  public void addKeyListener(iKeyListener l) {
    super.addKeyListener(l);
    view.setKeyboardListener(l);
  }

  public void addLongPressListener(iGestureListener l) {
    removeGestureListener(l);
    getEventListenerList().add(iGestureListener.class, l);

    if (!longPressGestureListenerAdded) {
      longPressGestureListenerAdded = true;
      view.setLongPressGestureListener(this);
    }
  }

  @Override
  public void addMouseListener(iMouseListener l) {
    super.addMouseListener(l);
    view.setMouseListener(this);
  }

  @Override
  public void addMouseMotionListener(iMouseMotionListener l) {
    super.addMouseMotionListener(l);
    view.setMouseMotionListener(this);
  }

  public void addRotateListener(iGestureListener l) {
    removeGestureListener(l);
    getEventListenerList().add(iGestureListener.class, l);

    if (!rotateGestureListenerAdded) {
      rotateGestureListenerAdded = true;
      view.setRotateGestureListener(this);
    }
  }

  public void addScaleListener(iGestureListener l) {
    removeGestureListener(l);
    getEventListenerList().add(iGestureListener.class, l);

    if (!scaleGestureListenerAdded) {
      scaleGestureListenerAdded = true;
      view.setScaleGestureListener(this);
    }
  }

  @Override
  public void addViewListener(iViewListener l) {
    super.addViewListener(l);
    view.setViewListener(this);
  }

  @Override
  public void bringToFront() {
    view.bringToFront();
  }

  @Override
  public UIImage capture() {
    return ImageUtils.createImage(this);
  }

  public Component cloneWith(View v) {
    try {
      Component c = (Component) super.clone();

      c.listenerList = null;
      c.view         = v;

      return c;
    } catch(CloneNotSupportedException ex) {
      throw new InternalError("CloneNotSupportedException");
    }
  }

  @Override
  public Component copy() {
    try {
      Class     cls = view.getClass();
      View      v   = (View) cls.newInstance();
      Component c   = (Component) super.clone();

      c.listenerList = null;
      c.view         = v;

      return c;
    } catch(CloneNotSupportedException ex) {
      throw new InternalError("CloneNotSupportedException");
    } catch(Exception ex) {
      throw new UnsupportedOperationException(ex);
    }
  }

  @Override
  public void dispatchEvent(KeyEvent ke) {
    view.dispatchEvent(ke);
  }

  @Override
  public void dispatchEvent(MouseEvent me) {
    view.dispatchEvent(me);
  }

  @Override
  public void dispose() {
    super.dispose();

    if (view != null) {
      view.dispose();
    }

    view = null;
  }

  public static Component findFromView(View view) {
    iPlatformComponent o = (view == null)
                           ? null
                           : view.getComponent();

    while((o != null) && (view != null)) {
      view = view.getParent();

      if (view != null) {
        o = view.getComponent();
      }
    }

    return (Component) o;
  }

  public static Component fromView(View view) {
    return (view == null)
           ? null
           : (Component) view.getComponent();
  }

  @Override
  public void onFling(Object view, MouseEvent e1, MouseEvent e2, float velocityX, float velocityY) {
    if (listenerList != null) {
      Object[] listeners = listenerList.getListenerList();

      for (int i = listeners.length - 2; i >= 0; i -= 2) {
        if (listeners[i] == iGestureListener.class) {
          ((iGestureListener) listeners[i + 1]).onFling(view, e1, e2, velocityX, velocityY);
        }
      }
    }
  }

  @Override
  public void onLongPress(Object view, MouseEvent e) {
    if (listenerList != null) {
      Object[] listeners = listenerList.getListenerList();

      for (int i = listeners.length - 2; i >= 0; i -= 2) {
        if (listeners[i] == iGestureListener.class) {
          ((iGestureListener) listeners[i + 1]).onLongPress(view, e);
        }
      }
    }
  }

  @Override
  public void onRotate(Object view, int type, float rotation, float velocity, float focusX, float focusY) {
    if (listenerList != null) {
      Object[] listeners = listenerList.getListenerList();

      for (int i = listeners.length - 2; i >= 0; i -= 2) {
        if (listeners[i] == iGestureListener.class) {
          ((iGestureListener) listeners[i + 1]).onRotate(view, type, rotation, velocity, focusX, focusY);
        }
      }
    }
  }

  @Override
  public void onScaleEvent(Object view, int type, Object sgd, float factor) {
    if (listenerList != null) {
      Object[] listeners = listenerList.getListenerList();

      for (int i = listeners.length - 2; i >= 0; i -= 2) {
        if (listeners[i] == iGestureListener.class) {
          ((iGestureListener) listeners[i + 1]).onScaleEvent(view, type, sgd, factor);
        }
      }
    }
  }

  public void removeGestureListener(iGestureListener l) {
    getEventListenerList().remove(iGestureListener.class, l);
  }

  @Override
  public void repaint() {
    view.repaint();
  }

  @Override
  public void requestFocus() {
    view.requestFocus();
  }

  @Override
  public void revalidate() {
    view.revalidate();
    view.repaint();

    if ((parent != null) && needsHiearachyInvalidated()) {
      parent.revalidate();
    }
  }

  @Override
  public void sendToBack() {
    view.sendToBack();
  }

  @Override
  public boolean setAlpha(float alpha) {
    view.setAlpha(alpha);

    return true;
  }

  @Override
  public void setBackground(UIColor bg) {
    backgroundColor = bg;

    iPlatformPainter p = (bg == null)
                         ? null
                         : ColorUtils.getPainter(bg);

    if ((p == null) && (bg != null)) {
      p = new UISimpleBackgroundPainter(bg);
    }

    if (p instanceof iBackgroundPainter) {
      getComponentPainter(true).setBackgroundPainter((iBackgroundPainter) p, true);
    }
  }

  @Override
  public void setBorder(iPlatformBorder border) {
    if (view.getBorder() != border) {
      getComponentPainter(true).setBorder(border);
    } else if (border != null) {    // component painter keeps track of border mod count
      getComponentPainter(true).setBorder(border);
    }
  }

  @Override
  public void setBounds(float x, float y, float width, float height) {
    boolean changed = (width != getWidth()) || (height != getHeight());

    view.setBounds(x, y, width, height);

    if (wantsViewResizeEvent && changed) {
      componentEvent(false, true, false);
    }
  }

  @Override
  public void setComponentPainter(iPlatformComponentPainter cp) {
    iPlatformComponentPainter ocp = view.getComponentPainter();

    view.setComponentPainter(cp);
    firePropertyChange(iConstants.RARE_COMPONENT_PAINTER_PROPERTY, ocp, cp);
  }

  @Override
  public void setCursor(UICursor cursor) {
    view.setCursor(cursor);
  }

  @Override
  public void setEnabled(boolean enabled) {
    if (enabled != view.isEnabled()) {
      view.setEnabled(enabled);
      firePropertyChange(iConstants.PROPERTY_ENABLED, !enabled, enabled);

      iPlatformComponentPainter cp = view.getComponentPainter();
      iPlatformBorder           b  = view.getBorder();

      if ((b != null) && b.isEnabledStateAware()) {
        view.updateForStateChange(b);
        view.setBorder(b);
      }

      boolean setDisabled = true;

      if (!view.usesForegroundColor()) {
        setDisabled = false;
      }

      if (setDisabled) {
        ButtonState state=Utils.getState(enabled, isPressed(), isSelected(), false);
        UIColor fg = getForegroundEx();

        if (fg == null) {
          PainterHolder ph = (cp == null)
                             ? null
                             : cp.getPainterHolder();

          if (ph != null) {
            fg = ph.getForeground(state);
          }

          if (fg == null) {
            if (fgColor == null) {
              fgColor = getForeground();
            }
            fg = fgColor.getColor(state);
          }
          else {
            fg=fg.getColor(state);
          }
        }

        view.setForegroundColor(fg);
      }
    }
  }

  @Override
  public void setFocusable(boolean focusable) {
    view.setFocusable(focusable);
  }

  @Override
  public void setFont(UIFont f) {
    view.setFont(f);
  }

  @Override
  public void setForeground(UIColor fg) {
    foregroundSet = fg != null;
    fgColor       = fg;
    view.setForegroundColor(fg);
  }

  @Override
  public void setLocation(float x, float y) {
    view.setLocation(x, y);
  }

  @Override
  public void setOpaque(boolean opaque) {
    super.setOpaque(opaque);

    if (!opaque) {
      view.makeTransparent();
    }
  }

  public void setParent(iParentComponent parent) {
    this.parent = parent;
  }

  @Override
  public void setSize(float width, float height) {
    boolean changed = (width != getWidth()) || (height != getHeight());

    view.setSize(width, height);

    if (wantsViewResizeEvent && changed) {
      componentEvent(false, true, false);
    }
  }

  public void setSize(int width, int height) {
    view.setSize(width, height);
  }

  @Override
  public void setVisible(boolean visible) {
    view.setVisible(visible);
  }

  public ActionMap getActionMap() {
    if (actionMap == null) {
      actionMap = new ActionMap();
      addKeyListener(actionMap);
    }

    return actionMap;
  }

  @Override
  public UIColor getBackgroundEx() {
    UIColor bg = view.getBackgroundColor();

    return (bg == null)
           ? backgroundColor
           : bg;
  }

  @Override
  public iPlatformBorder getBorder() {
    return view.getBorder();
  }

  @Override
  public UIRectangle getBounds() {
    return view.getBounds();
  }

  public UIRectangle getBounds(UIRectangle rect) {
    return view.getBounds(rect);
  }

  @Override
  public UIFont getFontEx() {
    return view.getFont();
  }

  @Override
  public UIColor getForegroundEx() {
    return foregroundSet
           ? fgColor
           : null;
  }

  @Override
  public int getHeight() {
    return UIScreen.snapToSize(view.getHeight());
  }

  @Override
  public UIPoint getLocation(UIPoint loc) {
    return view.getLocation(loc);
  }

  @Override
  public UIPoint getLocationOnScreen(UIPoint loc) {
    if (loc == null) {
      loc = new UIPoint();
    }

    iParentComponent pc = getParent();
    Orientation      o  = (pc == null)
                          ? Orientation.HORIZONTAL
                          : pc.getOrientation();

    if ((o != Orientation.VERTICAL_DOWN) && (o != Orientation.VERTICAL_UP)) {
      return view.getLocationOnScreen(loc);
    }

    UIPoint p = pc.getView().getLocationOnScreen(null);

    getOrientedLocation(loc);
    loc.x += p.x;
    loc.y += p.y;

    return loc;
  }

  @Override
  public Object getNativeView() {
    return (view == null)
           ? null
           : view.getProxy();
  }

  @Override
  public iParentComponent getParent() {
    return parent;
  }

  @Override
  public Object getProxy() {
    return (view == null)
           ? null
           : view.getProxy();
  }

  @Override
  public int getRight() {
    return getX() + getWidth();
  }

  @Override
  public UIDimension getSize() {
    return getSize(new UIDimension());
  }

  @Override
  public UIDimension getSize(UIDimension size) {
    if (size == null) {
      size = new UIDimension();
    }

    view.getSize(size);

    return size;
  }

  @Override
  public int getTop() {
    return getY();
  }

  @Override
  public View getView() {
    return view;
  }

  @Override
  public int getWidth() {
    return UIScreen.snapToSize(view.getWidth());
  }

  @Override
  public int getX() {
    return UIScreen.snapToPosition(view.getX());
  }

  @Override
  public int getY() {
    return UIScreen.snapToPosition(view.getY());
  }

  @Override
  public boolean hasBeenFocused() {
    return view.hasBeenFocused();
  }

  @Override
  public boolean hasHadInteraction() {
    return view.hasHadInteraction();
  }

  @Override
  public boolean hasMouseListeners() {
    return (view == null)
           ? false
           : view.getMouseListener() != null;
  }

  @Override
  public boolean hasMouseMotionListeners() {
    return (view == null)
           ? false
           : view.getMouseMotionListener() != null;
  }

  @Override
  public boolean isDisplayable() {
    return (view != null) && view.isDisplayable();
  }

  @Override
  public boolean isEnabled() {
    return view.isEnabled();
  }

  @Override
  public boolean isFocusOwner() {
    return view.isFocusedOrChildOfFocused();
  }

  @Override
  public boolean isFocusable() {
    return view.isFocusable();
  }

  @Override
  public boolean isPartOfAnimation() {
    return aAnimator.isAnimating(this);
  }

  @Override
  public boolean isPressed() {
    return view.isPressed();
  }

  @Override
  public boolean isSelected() {
    return view.isSelected();
  }

  @Override
  public boolean isShowing() {
    return (view == null)
           ? false
           : view.isShowing();
  }

  @Override
  public boolean isVisible() {
    return (view == null)
           ? false
           : view.isVisible();
  }

  void makeOrphan() {
    if (view != null) {
      view.makeOrphan();
    }

    parent = null;
  }

  @Override
  protected void interacted() {
    interactionListenerAdded = false;

    if (listenerList != null) {
      if (listenerList.getListenerCount(iMouseListener.class) == 0) {
        view.setMouseListener(null);
      }
    }

    putClientProperty(iConstants.RARE_HAD_INTERACTION_PROPERTY, Boolean.TRUE);
    firePropertyChange(iConstants.RARE_HAD_INTERACTION_PROPERTY, Boolean.FALSE, Boolean.TRUE);
  }

  @Override
  protected boolean needsHiearachyInvalidated() {
    return true;
  }

  protected void setView(View view) {
    if (this.view != null) {
      this.view.setComponent(null);
    }

    this.view = view;
    view.setComponent(this);
  }

  @Override
  protected iPlatformComponentPainter getComponentPainterEx() {
    return view.getComponentPainter();
  }

  @Override
  protected void getMinimumSizeEx(UIDimension size, float maxWidth) {
    view.getMinimumSize(size, maxWidth);
  }

  @Override
  protected void getPreferredSizeEx(UIDimension size, float maxWidth) {
    view.getPreferredSize(size, maxWidth);
  }
}
