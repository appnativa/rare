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

import com.appnativa.rare.Platform;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.ui.RenderableDataItem.Orientation;
import com.appnativa.rare.ui.effects.aAnimator;
import com.appnativa.rare.ui.event.ChangeEvent;
import com.appnativa.rare.ui.event.EventListenerList;
import com.appnativa.rare.ui.event.KeyEvent;
import com.appnativa.rare.ui.event.MouseEvent;
import com.appnativa.rare.ui.listener.iFocusListener;
import com.appnativa.rare.ui.listener.iKeyListener;
import com.appnativa.rare.ui.listener.iMouseListener;
import com.appnativa.rare.ui.listener.iMouseMotionListener;
import com.appnativa.rare.ui.listener.iTextChangeListener;
import com.appnativa.rare.ui.listener.iViewListener;
import com.appnativa.rare.ui.painter.PaintBucket;
import com.appnativa.rare.ui.painter.UIComponentPainter;
import com.appnativa.rare.ui.painter.iPainterSupport;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;
import com.appnativa.rare.util.PropertyChangeSupportEx;
import com.appnativa.rare.widget.iWidget;

import com.jgoodies.forms.layout.CellConstraints;

import java.beans.PropertyChangeListener;

import java.util.HashMap;

public abstract class aComponent
        implements iPainterSupport, iPlatformComponent, iMouseListener, iMouseMotionListener, iKeyListener,
                   iViewListener, iTextChangeListener, iFocusListener, iImageObserver {
  protected UIInsets                computeInsets                    = new UIInsets(0, 0, 0, 0);
  protected boolean                 useBorderInSizeCalculation       = true;
  protected Orientation             orientation                      = Orientation.HORIZONTAL;
  protected boolean                 checkMinWhenCalculatingPreferred = true;
  protected PropertyChangeSupportEx changeSupport;
  protected Object                  constraints;
  protected UIColor                 disabledColor;
  protected boolean                 disposed;
  protected PaintBucket             focusPaint;
  protected boolean                 focusPainted;
  protected boolean                 interactionListenerAdded;
  protected EventListenerList       listenerList;
  protected boolean                 locked;
  protected HashMap<String, Object> properties;
  protected String                  sageHeight;
  protected String                  sageMinHeight;
  protected String                  sageMinWidth;
  protected String                  sageWidth;
  protected boolean                 sizeLocked;
  protected boolean                 wantsMoveEvents;
  protected boolean                 wantsViewResizeEvent;
  protected iWidget                 widget;
  protected boolean                 opaque = true;
  protected ChangeEvent             changeEvent;

  protected aComponent() {}

  @Override
  public void addFocusListener(iFocusListener l) {
    getEventListenerList().remove(iFocusListener.class, l);
    getEventListenerList().add(iFocusListener.class, l);
  }

  @Override
  public boolean isScaleIcon() {
    return false;
  }

  @Override
  public float getIconScaleFactor() {
    return 1;
  }

  @Override
  public void setScaleIcon(boolean scale, float scaleFactor) {}

  @Override
  public void addKeyListener(iKeyListener l) {
    if (l == this) {
      throw new IllegalArgumentException("Cannot this component as a key listener to itself");
    }

    if (l != null) {
      getEventListenerList().remove(iKeyListener.class, l);
      getEventListenerList().add(iKeyListener.class, l);
    }
  }

  @Override
  public void addMouseListener(iMouseListener l) {
    if (l == this) {
      throw new IllegalArgumentException("Cannot this component as a mouse listener to itself");
    }

    if (l != null) {
      getEventListenerList().remove(iMouseListener.class, l);
      getEventListenerList().add(iMouseListener.class, l);
    }
  }

  @Override
  public void addMouseMotionListener(iMouseMotionListener l) {
    if (l == this) {
      throw new IllegalArgumentException("Cannot this component as a mouse motion listener to itself");
    }

    if (l != null) {
      wantsMoveEvents = l.wantsMouseMovedEvents();
      getEventListenerList().remove(iMouseMotionListener.class, l);
      getEventListenerList().add(iMouseMotionListener.class, l);
    }
  }

  @Override
  public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
    if (listener != null) {
      if (changeSupport == null) {
        changeSupport = new PropertyChangeSupportEx(this);
      }

      changeSupport.addPropertyChangeListener(propertyName, listener);
    }
  }

  @Override
  public void addTextChangeListener(iTextChangeListener l) {
    if (l == this) {
      throw new IllegalArgumentException("Cannot this component as a text change listener to itself");
    }

    if (l != null) {
      getEventListenerList().remove(iTextChangeListener.class, l);
      getEventListenerList().add(iTextChangeListener.class, l);
    }
  }

  @Override
  public void addViewListener(iViewListener l) {
    getEventListenerList().remove(iViewListener.class, l);
    getEventListenerList().add(iViewListener.class, l);

    if (l.wantsResizeEvent()) {
      wantsViewResizeEvent = true;
    }
  }

  public boolean setAlpha(float alpha) {
    return PlatformHelper.setComponentAlpha(this, alpha);
  }

  @Override
  public boolean adjustMinimumHeightForWidth() {
    return false;
  }

  @Override
  public void dispose() {
    if (listenerList != null) {
      listenerList.clear();
      listenerList = null;
    }

    widget = null;

    if (changeSupport != null) {
      changeSupport.dispose();
    }

    if (properties != null) {
      properties.clear();
    }

    changeSupport = null;
    constraints   = null;
    properties    = null;
    disposed      = true;
    computeInsets = null;
  }

  @Override
  public void focusChanged(Object view, boolean hasFocus, Object oppositeView) {
    if ((view == null) || disposed) {    // got an event after disposal
      return;
    }

    if (focusPainted &&!isDisposed() && isShowing()) {
      iParentComponent p = getParent();

      if ((p != null) && (p.getOrientation() != Orientation.HORIZONTAL)) {
        p.repaint();
      }

      repaint();
    }

    if (listenerList != null) {
      Object[] listeners = listenerList.getListenerList();

      for (int i = listeners.length - 2; i >= 0; i -= 2) {
        if ((listeners[i] == iFocusListener.class) && (listeners[i + 1] != this)) {
          ((iFocusListener) listeners[i + 1]).focusChanged(view, hasFocus, oppositeView);
        }
      }
    }
  }

  @Override
  public boolean heightChangesBasedOnWidth() {
    return false;
  }

  @Override
  public void imageLoaded(UIImage image) {
    revalidate();
    repaint();
  }

  @Override
  public void keyPressed(KeyEvent e) {
    if (disposed) {    // got an event after disposal
      return;
    }

    if (interactionListenerAdded) {
      interacted();
    }

    if (listenerList != null) {
      Object[] listeners = listenerList.getListenerList();

      for (int i = listeners.length - 2; i >= 0; i -= 2) {
        if ((listeners[i] == iKeyListener.class) && (listeners[i + 1] != this)) {
          ((iKeyListener) listeners[i + 1]).keyPressed(e);
        }
      }
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    if (disposed) {    // got an event after disposal
      return;
    }

    if (listenerList != null) {
      Object[] listeners = listenerList.getListenerList();

      for (int i = listeners.length - 2; i >= 0; i -= 2) {
        if ((listeners[i] == iKeyListener.class) && (listeners[i + 1] != this)) {
          ((iKeyListener) listeners[i + 1]).keyReleased(e);
        }
      }
    }
  }

  @Override
  public void keyTyped(KeyEvent e) {
    if (disposed) {    // got an event after disposal
      return;
    }

    if (listenerList != null) {
      Object[] listeners = listenerList.getListenerList();

      for (int i = listeners.length - 2; i >= 0; i -= 2) {
        if ((listeners[i] == iKeyListener.class) && (listeners[i + 1] != this)) {
          ((iKeyListener) listeners[i + 1]).keyTyped(e);
        }
      }
    }
  }

  @Override
  public void mouseDragged(MouseEvent e) {
    if (disposed) {    // got an event after disposal
      return;
    }

    if (listenerList != null) {
      Object[] listeners = listenerList.getListenerList();

      for (int i = listeners.length - 2; i >= 0; i -= 2) {
        if ((listeners[i] == iMouseMotionListener.class) && (listeners[i + 1] != this)) {
          ((iMouseMotionListener) listeners[i + 1]).mouseDragged(e);
        }
      }
    }
  }

  @Override
  public void mouseEntered(MouseEvent e) {
    if (disposed) {    // got an event after disposal
      return;
    }

    if (listenerList != null) {
      Object[] listeners = listenerList.getListenerList();

      for (int i = listeners.length - 2; i >= 0; i -= 2) {
        if ((listeners[i] == iMouseListener.class) && (listeners[i + 1] != this)) {
          ((iMouseListener) listeners[i + 1]).mouseEntered(e);
        }
      }
    }
  }

  @Override
  public void mouseExited(MouseEvent e) {
    if (disposed) {    // got an event after disposal
      return;
    }

    if (listenerList != null) {
      Object[] listeners = listenerList.getListenerList();

      for (int i = listeners.length - 2; i >= 0; i -= 2) {
        if ((listeners[i] == iMouseListener.class) && (listeners[i + 1] != this)) {
          ((iMouseListener) listeners[i + 1]).mouseExited(e);
        }
      }
    }
  }

  @Override
  public void mouseMoved(MouseEvent e) {
    if (disposed) {    // got an event after disposal
      return;
    }

    if (listenerList != null) {
      Object[] listeners = listenerList.getListenerList();

      for (int i = listeners.length - 2; i >= 0; i -= 2) {
        if ((listeners[i] == iMouseMotionListener.class) && (listeners[i + 1] != this)) {
          ((iMouseMotionListener) listeners[i + 1]).mouseMoved(e);
        }
      }
    }
  }

  @Override
  public void mousePressed(MouseEvent e) {
    if (disposed) {    // got an event after disposal
      return;
    }

    if (interactionListenerAdded) {
      interacted();
    }

    if (listenerList != null) {
      Object[] listeners = listenerList.getListenerList();

      for (int i = listeners.length - 2; i >= 0; i -= 2) {
        if ((listeners[i] == iMouseListener.class) && (listeners[i + 1] != this)) {
          ((iMouseListener) listeners[i + 1]).mousePressed(e);
        }
      }
    }
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    if (disposed) {    // got an event after disposal
      return;
    }

    if (listenerList != null) {
      Object[] listeners = listenerList.getListenerList();

      for (int i = listeners.length - 2; i >= 0; i -= 2) {
        if ((listeners[i] == iMouseListener.class) && (listeners[i + 1] != this)) {
          ((iMouseListener) listeners[i + 1]).mouseReleased(e);
        }
      }
    }
  }

  @Override
  public void pressCanceled(MouseEvent e) {}

  @Override
  public void putClientProperty(String key, Object value) {
    if (key == iConstants.RARE_WIDTH_PROPERTY) {
      sageWidth = (String) value;
    } else if (key == iConstants.RARE_HEIGHT_PROPERTY) {
      sageHeight = (String) value;
    } else if (key == iConstants.RARE_MIN_WIDTH_PROPERTY) {
      sageMinWidth = (String) value;
    } else if (key == iConstants.RARE_MIN_HEIGHT_PROPERTY) {
      sageMinHeight = (String) value;
    } else if (key == iConstants.RARE_WIDGET_COMPONENT_PROPERTY) {
      widget = (iWidget) value;
    } else if (key == iConstants.RARE_CONSTRAINTS_PROPERTY) {
      constraints = value;
    } else {
      putClientPropertyEx(key, value);
    }
  }

  @Override
  public void removeFocusListener(iFocusListener l) {
    if (listenerList != null) {
      listenerList.remove(iFocusListener.class, l);
    }
  }

  @Override
  public void removeFromParent() {
    iParentComponent p = isDisposed()
                         ? null
                         : getParent();

    if ((p != null) &&!p.isDisposed()) {
      p.remove(this);
    }
  }

  @Override
  public void removeKeyListener(iKeyListener l) {
    if (listenerList != null) {
      listenerList.remove(iKeyListener.class, l);
    }
  }

  @Override
  public void removeMouseListener(iMouseListener l) {
    if (listenerList != null) {
      listenerList.remove(iMouseListener.class, l);
    }
  }

  @Override
  public void removeMouseMotionListener(iMouseMotionListener l) {
    if (listenerList != null) {
      listenerList.remove(iMouseMotionListener.class, l);
    }
  }

  @Override
  public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
    if ((listener != null) && (changeSupport != null)) {
      changeSupport.removePropertyChangeListener(propertyName, listener);
    }
  }

  @Override
  public void removeTextChangeListener(iTextChangeListener l) {
    if ((l != this) && (l != null) && (listenerList != null)) {
      listenerList.remove(iTextChangeListener.class, l);
    }
  }

  @Override
  public void removeViewListener(iViewListener l) {
    if (listenerList != null) {
      getEventListenerList().remove(iViewListener.class, l);
    }
  }

  public void requestFocus(boolean temporary) {
    requestFocus();
  }

  @Override
  public boolean shouldStopEditing(Object source) {
    if (listenerList != null) {
      Object[] listeners = listenerList.getListenerList();

      for (int i = listeners.length - 2; i >= 0; i -= 2) {
        if (listeners[i] == iTextChangeListener.class) {
          if (!((iTextChangeListener) listeners[i + 1]).shouldStopEditing(this)) {
            return false;
          }
        }
      }
    }

    return true;
  }

  @Override
  public void textChanged(Object source) {
    if (listenerList != null) {
      Object[] listeners = listenerList.getListenerList();

      for (int i = listeners.length - 2; i >= 0; i -= 2) {
        if (listeners[i] == iTextChangeListener.class) {
          ((iTextChangeListener) listeners[i + 1]).textChanged(this);
        }
      }
    }
  }

  @Override
  public boolean textChanging(Object source, int startIndex, int endIndex, CharSequence replacementString) {
    if (listenerList != null) {
      Object[] listeners = listenerList.getListenerList();

      for (int i = listeners.length - 2; i >= 0; i -= 2) {
        if (listeners[i] == iTextChangeListener.class) {
          if (!((iTextChangeListener) listeners[i + 1]).textChanging(this, startIndex, endIndex, replacementString)) {
            return false;
          }
        }
      }
    }

    return true;
  }

  @Override
  public void updateUI() {
    revalidate();
    repaint();
  }

  @Override
  public void viewHidden(ChangeEvent e) {
    if (disposed ||!hasListeners(iViewListener.class) || aAnimator.isViewEventsDisabled(this)) {    // got
      // an
      // event
      // after
      // disposal
      // or
      // animating
      return;
    }

    if (listenerList != null) {
      Object[] listeners = listenerList.getListenerList();

      for (int i = listeners.length - 2; i >= 0; i -= 2) {
        if (listeners[i] == iViewListener.class) {
          ((iViewListener) listeners[i + 1]).viewHidden(e);
        }
      }
    }
  }

  @Override
  public void viewResized(ChangeEvent e) {
    if (disposed ||!hasListeners(iViewListener.class) || aAnimator.isViewEventsDisabled(this)) {    // got
      // an
      // event
      // after
      // disposal
      // or
      // animating
      return;
    }

    if (listenerList != null) {
      Object[] listeners = listenerList.getListenerList();

      for (int i = listeners.length - 2; i >= 0; i -= 2) {
        if (listeners[i] == iViewListener.class) {
          ((iViewListener) listeners[i + 1]).viewResized(e);
        }
      }
    }
  }

  @Override
  public void viewShown(ChangeEvent e) {
    if (disposed ||!hasListeners(iViewListener.class) || aAnimator.isViewEventsDisabled(this)) {    // got
      // an
      // event
      // after
      // disposal
      // or
      // animating
      return;
    }

    if (listenerList != null) {
      Object[] listeners = listenerList.getListenerList();

      for (int i = listeners.length - 2; i >= 0; i -= 2) {
        if (listeners[i] == iViewListener.class) {
          ((iViewListener) listeners[i + 1]).viewShown(e);
        }
      }
    }
  }

  @Override
  public boolean wantsLongPress() {
    if (listenerList != null) {
      Object[] listeners = listenerList.getListenerList();

      for (int i = listeners.length - 2; i >= 0; i -= 2) {
        if (listeners[i] == iMouseListener.class) {
          if (((iMouseListener) listeners[i + 1]).wantsLongPress()) {
            return true;
          }
        }
      }
    }

    return false;
  }

  @Override
  public boolean wantsMouseMovedEvents() {
    return wantsMoveEvents;
  }

  @Override
  public boolean wantsResizeEvent() {
    return wantsViewResizeEvent;
  }

  @Override
  public void setBounds(UIRectangle bounds) {
    setBounds(bounds.x, bounds.y, bounds.width, bounds.height);
  }

  public void setBounds(int x, int y, int width, int height) {
    setBounds((float) x, (float) y, (float) width, (float) height);
  }

  @Override
  public void setDisabledColor(UIColor color) {
    disabledColor = color;
  }

  public void setDisabledForeground(UIColor fg) {
    iPlatformComponentPainter cp = getComponentPainter(true);

    cp.setDisabledForegroundColor(fg);
  }

  public void setFocusPaint(PaintBucket focusPaint) {
    this.focusPaint = focusPaint;
  }

  @Override
  public void setFocusPainted(boolean focusPainted) {
    this.focusPainted = focusPainted;

    if (focusPainted) {
      removeFocusListener(this);
      addFocusListener(this);
    }
  }

  @Override
  public void setLocked(boolean lock) {
    locked = lock;
    setEnabled(!lock);
  }

  public void setOrientation(Orientation orientation) {
    this.orientation = orientation;
  }

  @Override
  public void setSelected(boolean selected) {}

  /**
   * Locks the component's size such that calls to getMinimumSize and
   * getPreferredSize returns the current size of the component.
   *
   * @param sizeLocked
   *          true to lock the components size; false to unlock
   */
  @Override
  public void setSizeLocked(boolean sizeLocked) {
    this.sizeLocked = sizeLocked;
  }

  /**
   * Sets the preferred size of the widget
   *
   * @param width
   *          the width
   * @param height
   *          the height
   */
  @Override
  public void setPreferredSize(float width, float height) {
    String w = null;
    String h = null;

    if (height != -1) {
      h = height + "px";
    }

    if (width != -1) {
      w = width + "px";
    }

    putClientProperty(iConstants.RARE_WIDTH_PROPERTY, w);
    putClientProperty(iConstants.RARE_HEIGHT_PROPERTY, h);
  }

  @Override
  public void setWidget(iWidget widget) {
    this.widget = widget;
  }

  @Override
  public UIColor getBackground() {
    UIColor bg = getBackgroundEx();

    if (bg != null) {
      return bg;
    }

    if (getParent() != null) {
      return getParent().getBackground();
    }

    return ColorUtils.getBackground();
  }

  @Override
  public abstract UIColor getBackgroundEx();

  public int getBottom() {
    return getY() + getHeight();
  }

  @Override
  public Object getClientProperty(String key) {
    if (disposed) {
      return null;
    }

    if (key == iConstants.RARE_WIDTH_PROPERTY) {
      return sageWidth;
    } else if (key == iConstants.RARE_HEIGHT_PROPERTY) {
      return sageHeight;
    } else if (key == iConstants.RARE_MIN_WIDTH_PROPERTY) {
      return sageMinWidth;
    } else if (key == iConstants.RARE_MIN_HEIGHT_PROPERTY) {
      return sageMinHeight;
    } else if (key == iConstants.RARE_WIDGET_COMPONENT_PROPERTY) {
      return widget;
    } else if (key == iConstants.RARE_CONSTRAINTS_PROPERTY) {
      return constraints;
    }

    return getClientPropertyEx(key);
  }

  @Override
  public iPlatformComponentPainter getComponentPainter() {
    return getComponentPainter(false);
  }

  @Override
  public UIColor getDisabledColor() {
    return disabledColor;
  }

  @Override
  public UIInsets getFocusInsets(UIInsets insets) {
    return null;
  }

  @Override
  public PaintBucket getFocusPaint(iPlatformGraphics g, PaintBucket def) {
    if (!focusPainted ||!isFocusOwner()) {
      return null;
    }

    return (focusPaint == null)
           ? def
           : focusPaint;
  }

  @Override
  public UIFont getFont() {
    UIFont f = getFontEx();

    if (f != null) {
      return f;
    }

    if (getParent() != null) {
      return getParent().getFont();
    }

    return UIFontHelper.getDefaultFont();
  }

  @Override
  public abstract UIFont getFontEx();

  @Override
  public UIColor getForeground() {
    UIColor fg = getForegroundEx();

    if (fg != null) {
      return fg;
    }

    if (getParent() != null) {
      return getParent().getForeground();
    }

    return UIColorHelper.getForeground();
  }

  @Override
  public abstract UIColor getForegroundEx();

  public iWidget getHostWidget() {
    return (widget != null)
           ? widget
           : Platform.findWidgetForComponent(this);
  }

  @Override
  public UIRectangle getInnerBounds(UIRectangle rect) {
    if (rect == null) {
      rect = new UIRectangle();
    }

    rect.x      = 0;
    rect.y      = 0;
    rect.width  = getWidth();
    rect.height = getHeight();

    iPlatformBorder b = getBorder();

    if (b != null) {
      UIInsets in = b.getBorderInsets(new UIInsets());

      rect.x      = in.left;
      rect.y      = in.top;
      rect.width  -= (in.left + in.right);
      rect.height -= (in.top + in.bottom);
    }

    return rect;
  }

  @Override
  public UIDimension getInnerSize(UIDimension size) {
    size = getSize(size);

    iPlatformBorder b = getBorder();

    if (b != null) {
      UIInsets in = b.getBorderInsets(new UIInsets());

      size.width  -= (in.left + in.right);
      size.height -= (in.top + in.bottom);
    }

    return size;
  }

  public int getLeft() {
    return getX();
  }

  @Override
  public UIPoint getLocation() {
    return getLocation(new UIPoint());
  }

  @Override
  public UIPoint getLocation(UIPoint loc) {
    if (loc == null) {
      loc = new UIPoint(getX(), getY());
    } else {
      loc.x = getX();
      loc.y = getY();
    }

    return loc;
  }

  @Override
  public UIPoint getLocationOnScreen() {
    return getLocationOnScreen(null);
  }

  @Override
  public UIDimension getMinimumSize() {
    return getMinimumSize(new UIDimension());
  }

  @Override
  public UIDimension getMinimumSize(UIDimension size) {
    if (sizeLocked) {
      return getSize(size);
    }

    float h = -1;
    float w = -1;

    if (size == null) {
      size = new UIDimension();
    }

    boolean pget = false;

    if (sageMinHeight != null) {
      if (sageMinHeight.equals("!")) {
        getPreferredSize(size);
        h = size.height;
      } else {
        h = ScreenUtils.toPlatformPixelHeight(sageMinHeight, this, -1);
      }
    }

    if (sageMinWidth != null) {
      if (sageMinWidth.equals("!")) {
        if (!pget) {
          getPreferredSize(size);
        }

        w = size.width;
      } else {
        w = ScreenUtils.toPlatformPixelWidth(sageMinWidth, this, -1);
      }
    }

    if ((h > -1) && (w > -1)) {
      size.setSize(w, h);

      return new UIDimension(w, h);
    }

    getMinimumSizeEx(size);

    if (useBorderInSizeCalculation) {
      iPlatformBorder b  = getBorder();
      UIInsets        in = (b == null)
                           ? null
                           : b.getBorderInsets(computeInsets);

      if (in != null) {
        size.width  += in.left + in.right;
        size.height += in.top + in.bottom;
      }
    }

    if (w > -1) {
      size.width = Math.max(w, size.width);
    }

    if (h > -1) {
      size.height = Math.max(h, size.height);
    }

    return size;
  }

  @Override
  public Orientation getOrientation() {
    return orientation;
  }

  public UIPoint getOrientedLocation(UIPoint loc) {
    getLocation(loc);

    iPlatformComponent c = getParent();
    float              n;

    if (c != null) {
      switch(c.getOrientation()) {
        case VERTICAL_DOWN :
          n     = loc.x;
          loc.x = loc.y;
          loc.y = n;

          break;

        case VERTICAL_UP :
          UIDimension size = c.getOrientedSize(null);

          n     = loc.x;
          loc.x = loc.y;
          loc.y = size.height - n;
          size  = getOrientedSize(size);
          loc.y -= size.height;

          break;

        default :
          break;
      }
    }

    return loc;
  }

  @Override
  public UIDimension getOrientedSize(UIDimension size) {
    if (size == null) {
      size = new UIDimension();
    }

    getSize(size);

    iPlatformComponent c = getParent();

    if (c != null) {
      switch(c.getOrientation()) {
        case VERTICAL_UP :
        case VERTICAL_DOWN :
          size.setSize(size.height, size.width);

          break;

        default :
          break;
      }
    }

    return size;
  }

  @Override
  public UIDimension getPreferredSize() {
    return getPreferredSize(new UIDimension(), 0);
  }

  @Override
  public UIDimension getPreferredSize(UIDimension size) {
    return getPreferredSize(size, 0);
  }

  @Override
  public UIDimension getPreferredSize(UIDimension size, float maxWidth) {
    if (sizeLocked) {
      return getSize(size);
    }

    float h = -1;
    float w = -1;

    if (sageHeight != null) {
      h = ScreenUtils.toPlatformPixelHeight(sageHeight, this, -1);
    }

    if (sageWidth != null) {
      w = ScreenUtils.toPlatformPixelWidth(sageWidth, this, -1);
    }

    if (size == null) {
      size = new UIDimension();
    }

    if ((h > -1) && (w > -1)) {
      size.setSize(w, h);

      return size;
    }

    if ((w > -1) && ((maxWidth == 0) || (w < maxWidth))) {
      maxWidth = w;
    }

    getPreferredSizeEx(size, maxWidth);

    if (useBorderInSizeCalculation) {
      iPlatformBorder b  = getBorder();
      UIInsets        in = (b == null)
                           ? null
                           : b.getBorderInsets(computeInsets);

      if (in != null) {
        size.width  += in.left + in.right;
        size.height += in.top + in.bottom;
      }
    }

    if (w > -1) {
      size.width = w;
    }

    if (h > -1) {
      size.height = h;
    }

    if (checkMinWhenCalculatingPreferred) {
      if (sageMinHeight != null) {
        h = ScreenUtils.toPlatformPixelHeight(sageMinHeight, this, -1);

        if (h > size.height) {
          size.height = h;
        }
      }

      if (sageMinWidth != null) {
        w = ScreenUtils.toPlatformPixelWidth(sageMinWidth, this, -1);

        if (w > size.width) {
          size.width = w;
        }
      }
    }

    return size;
  }

  public int getRight() {
    return getX() + getWidth();
  }

  @Override
  public UIDimension getSize() {
    return getSize(new UIDimension());
  }

  public int getTop() {
    return getY();
  }

  @Override
  public iWidget getWidget() {
    return widget;
  }

  @Override
  public boolean hasChildren() {
    return false;
  }

  public boolean hasMouseListeners() {
    if (listenerList == null) {
      return false;
    }

    return listenerList.getListenerCount(iMouseListener.class) > 0;
  }

  public boolean hasMouseMotionListeners() {
    if (listenerList == null) {
      return false;
    }

    return listenerList.getListenerCount(iMouseMotionListener.class) > 0;
  }

  public boolean isAnimating() {
    return aAnimator.isAnimating(this);
  }

  @Override
  public boolean isBackgroundSet() {
    return getBackgroundEx() != null;
  }

  @Override
  public boolean isDisposed() {
    return disposed;
  }

  @Override
  public boolean isFocusPainted() {
    return focusPainted;
  }

  @Override
  public boolean isFontSet() {
    return getFontEx() != null;
  }

  @Override
  public boolean isForegroundSet() {
    return getForegroundEx() != null;
  }

  @Override
  public boolean isLeftToRight() {
    return true;
  }

  @Override
  public boolean isLocked() {
    return locked;
  }

  @Override
  public boolean isMouseOver() {
    return false;
  }

  @Override
  public boolean isOpaque() {
    return opaque;
  }

  public void setOpaque(boolean opaque) {
    this.opaque = opaque;

    iPlatformComponentPainter cp = getComponentPainterEx();

    if (cp != null) {
      cp.setBackgroundColor(null, true);
    }
  }

  public boolean isPartOfAnimation() {
    if (isAnimating()) {
      return true;
    }

    iParentComponent p = getParent();

    if (p instanceof aComponent) {
      return ((aComponent) p).isPartOfAnimation();
    }

    return false;
  }

  @Override
  public boolean isPressed() {
    return false;
  }

  @Override
  public boolean isSelectable() {
    return false;
  }

  @Override
  public boolean isSelected() {
    return false;
  }

  /**
   * Gets whether the the component's size is locked. If locked calls to
   * getMinimumSize and getPreferredSize returns the current size of the
   * component.
   *
   * @return true if locked; false if unlocked
   */
  @Override
  public boolean isSizeLocked() {
    return sizeLocked;
  }

  protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
    if (changeSupport != null) {
      changeSupport.firePropertyChange(propertyName, oldValue, newValue);
    }
  }

  protected abstract void interacted();

  protected boolean needsHiearachyInvalidated() {
    return true;
  }

  protected static void populateMeasuredSizeCache(iParentComponent parent, boolean populateMin) {
    iPlatformComponent c;
    int                len  = parent.getComponentCount();
    UIDimension        size = new UIDimension();

    for (int i = 0; i < len; i++) {
      c = parent.getComponentAt(i);

      if (!c.isVisible()) {
        continue;
      }

      Object o = c.getClientProperty(iConstants.RARE_CONSTRAINTS_PROPERTY);

      if (!(o instanceof CellConstraints)) {
        o = new CellConstraints();
        c.putClientProperty(iConstants.RARE_CONSTRAINTS_PROPERTY, o);
      }

      CellConstraints cc = (CellConstraints) o;

      c.getPreferredSize(size);
      cc.gridWidth  = (int) size.width;
      cc.gridHeight = (int) size.height;

      if (populateMin) {
        c.getMinimumSize(size);
        cc.gridX = (int) size.width;
        cc.gridY = (int) size.height;
      }
    }
  }

  protected void putClientPropertyEx(String key, Object value) {
    if (properties == null) {
      properties = new HashMap<String, Object>(5);
    }

    properties.put(key, value);
  }

  protected Object getClientPropertyEx(String key) {
    return (properties == null)
           ? null
           : properties.get(key);
  }

  protected iPlatformComponentPainter getComponentPainter(boolean create) {
    iPlatformComponentPainter cp = getComponentPainterEx();

    if ((cp != null) ||!create) {
      return cp;
    }

    cp = new UIComponentPainter();
    setComponentPainter(cp);

    return cp;
  }

  protected abstract iPlatformComponentPainter getComponentPainterEx();

  protected EventListenerList getEventListenerList() {
    if (listenerList == null) {
      listenerList = new EventListenerList();
    }

    return listenerList;
  }

  protected static CellConstraints getMeasuredCellConstraints(iPlatformComponent c, boolean populateMin) {
    Object o = c.getClientProperty(iConstants.RARE_CONSTRAINTS_PROPERTY);

    if (!(o instanceof CellConstraints)) {
      CellConstraints cc = new CellConstraints();

      c.putClientProperty(iConstants.RARE_CONSTRAINTS_PROPERTY, cc);

      UIDimension d = c.getPreferredSize();

      cc.gridWidth  = (int) d.width;
      cc.gridHeight = (int) d.height;

      if (populateMin) {
        c.getMinimumSize(d);
        cc.gridX = (int) d.width;
        cc.gridY = (int) d.height;
      }

      o = cc;
    }

    return (CellConstraints) o;
  }

  protected abstract void getMinimumSizeEx(UIDimension size);

  protected abstract void getPreferredSizeEx(UIDimension size, float maxWidth);

  protected boolean hasListeners(Class cls) {
    return (listenerList == null)
           ? false
           : listenerList.hasListeners(cls);
  }

  protected void componentEvent(boolean shown, boolean resized, boolean moved) {
    if (listenerList != null) {
      Object[] listeners = listenerList.getListenerList();

      for (int i = listeners.length - 2; i >= 0; i -= 2) {
        if (listeners[i] == iViewListener.class) {
          if (changeEvent == null) {
            changeEvent = new ChangeEvent(this);
          }

          if (resized) {
            ((iViewListener) listeners[i + 1]).viewResized(changeEvent);
          } else if (shown) {
            ((iViewListener) listeners[i + 1]).viewShown(changeEvent);
          } else if (shown) {
            ((iViewListener) listeners[i + 1]).viewShown(changeEvent);
          } else {
            ((iViewListener) listeners[i + 1]).viewHidden(changeEvent);
          }
        }
      }
    }
  }
}
