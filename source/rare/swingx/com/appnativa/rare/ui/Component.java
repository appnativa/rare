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
import com.appnativa.rare.exception.ApplicationException;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.platform.EventHelper;
import com.appnativa.rare.platform.swing.ui.ScaleGestureDetector;
import com.appnativa.rare.platform.swing.ui.UIProxyBorder;
import com.appnativa.rare.platform.swing.ui.util.ImageUtils;
import com.appnativa.rare.platform.swing.ui.util.SwingGraphics;
import com.appnativa.rare.platform.swing.ui.util.SwingHelper;
import com.appnativa.rare.platform.swing.ui.view.JPanelEx;
import com.appnativa.rare.platform.swing.ui.view.UtilityPanel;
import com.appnativa.rare.platform.swing.ui.view.iView;
import com.appnativa.rare.ui.RenderableDataItem.Orientation;
import com.appnativa.rare.ui.effects.aAnimator;
import com.appnativa.rare.ui.event.KeyEvent;
import com.appnativa.rare.ui.event.MouseEvent;
import com.appnativa.rare.ui.iPaintedButton.ButtonState;
import com.appnativa.rare.ui.listener.iFocusListener;
import com.appnativa.rare.ui.listener.iKeyListener;
import com.appnativa.rare.ui.listener.iMouseListener;
import com.appnativa.rare.ui.listener.iMouseMotionListener;
import com.appnativa.rare.ui.listener.iViewListener;
import com.appnativa.rare.ui.painter.PainterHolder;
import com.appnativa.rare.ui.painter.UIComponentPainter;
import com.appnativa.rare.ui.painter.iComponentPainter;
import com.appnativa.rare.ui.painter.iPainterSupport;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JComponent;
import javax.swing.TransferHandler;
import javax.swing.plaf.UIResource;

/**
 *
 * @author Don DeCoteau
 */
public class Component extends aComponent
        implements MouseListener, MouseMotionListener, KeyListener, FocusListener, PropertyChangeListener,
                   ComponentListener, HierarchyListener {
  public static String                RARE_COMPONENT_PROXY_PROPERTY = "__RARE_COMPONENT_PROXY_PROPERTY__";
  SwingGraphics                       graphics;
  int                                 graphicsReference;
  protected UIRectangle               bounds;
  protected iPlatformComponentPainter componentPainter;
  protected boolean                   hadInteraction;
  protected iParentComponent          parent;
  protected JComponent                view;
  protected boolean                   foregroundSet;
  private ScaleGestureDetector        scaleGestureDetector;

  public Component(JComponent component) {
    this.view                  = component;
    useBorderInSizeCalculation = false;

    if (component != null) {
      component.putClientProperty(RARE_COMPONENT_PROXY_PROPERTY, this);
    }
  }

  protected Component() {}

  @Override
  public void addFocusListener(iFocusListener l) {
    super.addFocusListener(l);
    view.removeFocusListener(this);
    view.addFocusListener(this);
  }

  @Override
  public void addKeyListener(iKeyListener l) {
    super.addKeyListener(l);
    view.removeKeyListener(this);
    view.addKeyListener(this);
  }

  @Override
  public void addMouseListener(iMouseListener l) {
    super.addMouseListener(l);
    view.removeMouseListener(this);
    view.addMouseListener(this);
  }

  @Override
  public void addMouseMotionListener(iMouseMotionListener l) {
    super.addMouseMotionListener(l);
    view.removeMouseMotionListener(this);
    view.addMouseMotionListener(this);
  }

  @Override
  public void addViewListener(iViewListener l) {
    super.addViewListener(l);
    view.removeComponentListener(this);
    view.addComponentListener(this);
    view.removeHierarchyListener(this);
    view.addHierarchyListener(this);
  }

  @Override
  public void bringToFront() {
    if (view.getParent() != null) {
      view.getParent().setComponentZOrder(view, 0);
    }
  }

  @Override
  public UIImage capture() {
    return ImageUtils.createImage(view);
  }

  @Override
  public void componentHidden(ComponentEvent e) {}

  @Override
  public void componentMoved(ComponentEvent e) {
    componentEvent(false, false, true);
  }

  @Override
  public void componentResized(ComponentEvent e) {}

  @Override
  public void componentShown(ComponentEvent e) {}

  public boolean setAlpha(float alpha) {
    if (isDisposed()) {
      return false;
    }

    if (view instanceof JPanelEx) {
      ((JPanelEx) view).setComposite(AlphaComposite.SrcOver.derive(alpha));

      return true;
    } else {
      UIComponentPainter cp = (UIComponentPainter) getComponentPainter(true);

      cp.setComposite(GraphicsComposite.DEFAULT_COMPOSITE.derive(alpha));
    }

    return true;
  }

  @Override
  public iPlatformComponent copy() {
    try {
      return new Component(view.getClass().newInstance());
    } catch(Exception e) {
      throw new ApplicationException(e);
    }
  }

  @Override
  public void dispatchEvent(KeyEvent e) {
    if (e.getNativeEvent() != null) {
      SwingHelper.retargetKeyEvent(e.getID(), (java.awt.event.KeyEvent) e.getNativeEvent(), view);
    }
  }

  @Override
  public void dispatchEvent(MouseEvent e) {
    if (e.getNativeEvent() != null) {
      SwingHelper.retargetMouseEvent(e.getID(), (java.awt.event.MouseEvent) e.getNativeEvent(), view);
    }
  }

  @Override
  public void dispose() {
    if (view != null) {
      if (componentPainter != null) {
        componentPainter.removePropertyChangeListener(this);
      }

      super.dispose();
      disposeEx();
      view = null;
    }
  }

  @Override
  public void focusGained(FocusEvent e) {
    focusChanged(view, true, e.getOppositeComponent());
  }

  @Override
  public void focusLost(FocusEvent e) {
    focusChanged(view, false, e.getOppositeComponent());
  }

  public static iPlatformComponent fromView(JComponent c) {
    if (c == null) {
      return null;
    }

    if (c instanceof iPlatformComponent) {
      return (iPlatformComponent) c;
    }

    iPlatformComponent pc = (iPlatformComponent) c.getClientProperty(RARE_COMPONENT_PROXY_PROPERTY);

    if (pc == null) {
      pc = new Container(c);
      c.putClientProperty(RARE_COMPONENT_PROXY_PROPERTY, pc);
    }

    return pc;
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
      ((SwingGraphics) g).clear();
    }
  }

  @Override
  public iPlatformGraphics graphicsWrap(Graphics g) {
    Graphics og = (graphics == null)
                  ? null
                  : graphics.getGraphics();

    if ((og != null) && (og != g)) {
      return new SwingGraphics(g, this);
    }

    if ((graphics == null) || (og == null)) {
      graphics = SwingGraphics.fromGraphics(g, view, graphics);
    }

    graphicsReference++;

    return graphics;
  }

  @Override
  public void hierarchyChanged(HierarchyEvent e) {
    final long type = e.getChangeFlags();

    if ((type & HierarchyEvent.SHOWING_CHANGED) > 0) {
      if (e.getChanged().isShowing()) {
        // invoking later so that it works on linux (ensuring the component is
        // actually visible)
        Platform.invokeLater(new Runnable() {
          @Override
          public void run() {
            componentEvent(true, false, false);
          }
        });
      } else {
        componentEvent(false, false, false);
      }
    }
  }

  @Override
  public void keyPressed(java.awt.event.KeyEvent e) {
    if ((listenerList == null) || (listenerList.getListenerCount(iKeyListener.class) == 0)) {
      return;
    }

    KeyEvent ke        = new KeyEvent(this, e);
    Object[] listeners = listenerList.getListenerList();

    for (int i = listeners.length - 2; i >= 0; i -= 2) {
      if (listeners[i] == iKeyListener.class) {
        ((iKeyListener) listeners[i + 1]).keyPressed(ke);

        if (ke.isConsumed()) {
          break;
        }
      }
    }
  }

  @Override
  public void keyReleased(java.awt.event.KeyEvent e) {
    if ((listenerList == null) || (listenerList.getListenerCount(iKeyListener.class) == 0)) {
      return;
    }

    KeyEvent ke        = new KeyEvent(this, e);
    Object[] listeners = listenerList.getListenerList();

    for (int i = listeners.length - 2; i >= 0; i -= 2) {
      if (listeners[i] == iKeyListener.class) {
        ((iKeyListener) listeners[i + 1]).keyReleased(ke);

        if (ke.isConsumed()) {
          break;
        }
      }
    }
  }

  @Override
  public void keyTyped(java.awt.event.KeyEvent e) {
    if ((listenerList == null) || (listenerList.getListenerCount(iKeyListener.class) == 0)) {
      return;
    }

    KeyEvent ke        = new KeyEvent(this, e);
    Object[] listeners = listenerList.getListenerList();

    for (int i = listeners.length - 2; i >= 0; i -= 2) {
      if (listeners[i] == iKeyListener.class) {
        ((iKeyListener) listeners[i + 1]).keyTyped(ke);

        if (ke.isConsumed()) {
          break;
        }
      }
    }
  }

  @Override
  public void mouseClicked(java.awt.event.MouseEvent e) {}

  @Override
  public void mouseDragged(java.awt.event.MouseEvent e) {
    if ((listenerList == null) || (listenerList.getListenerCount(iMouseMotionListener.class) == 0)) {
      return;
    }

    MouseEvent me        = EventHelper.createMouseEvent(this, e);
    Object[]   listeners = listenerList.getListenerList();

    for (int i = listeners.length - 2; i >= 0; i -= 2) {
      if (listeners[i] == iMouseMotionListener.class) {
        ((iMouseMotionListener) listeners[i + 1]).mouseDragged(me);

        if (me.isConsumed()) {
          break;
        }
      }
    }
  }

  @Override
  public void mouseEntered(java.awt.event.MouseEvent e) {
    if ((listenerList == null) || (listenerList.getListenerCount(iMouseListener.class) == 0)) {
      return;
    }

    MouseEvent me        = EventHelper.createMouseEvent(this, e);
    Object[]   listeners = listenerList.getListenerList();

    for (int i = listeners.length - 2; i >= 0; i -= 2) {
      if (listeners[i] == iMouseListener.class) {
        ((iMouseListener) listeners[i + 1]).mouseEntered(me);

        if (me.isConsumed()) {
          break;
        }
      }
    }
  }

  @Override
  public void mouseExited(java.awt.event.MouseEvent e) {
    if ((listenerList == null) || (listenerList.getListenerCount(iMouseListener.class) == 0)) {
      return;
    }

    MouseEvent me        = EventHelper.createMouseEvent(this, e);
    Object[]   listeners = listenerList.getListenerList();

    for (int i = listeners.length - 2; i >= 0; i -= 2) {
      if (listeners[i] == iMouseListener.class) {
        ((iMouseListener) listeners[i + 1]).mouseExited(me);

        if (me.isConsumed()) {
          break;
        }
      }
    }
  }

  @Override
  public void mouseMoved(java.awt.event.MouseEvent e) {
    if ((listenerList == null) || (listenerList.getListenerCount(iMouseMotionListener.class) == 0)) {
      return;
    }

    MouseEvent me        = EventHelper.createMouseEvent(this, e);
    Object[]   listeners = listenerList.getListenerList();

    for (int i = listeners.length - 2; i >= 0; i -= 2) {
      if (listeners[i] == iMouseMotionListener.class) {
        ((iMouseMotionListener) listeners[i + 1]).mouseMoved(me);

        if (me.isConsumed()) {
          break;
        }
      }
    }
  }

  @Override
  public void mousePressed(java.awt.event.MouseEvent e) {
    if ((listenerList == null) || (listenerList.getListenerCount(iMouseListener.class) == 0)) {
      return;
    }

    MouseEvent me        = EventHelper.createMouseEvent(this, e);
    Object[]   listeners = listenerList.getListenerList();

    for (int i = listeners.length - 2; i >= 0; i -= 2) {
      if (listeners[i] == iMouseListener.class) {
        ((iMouseListener) listeners[i + 1]).mousePressed(me);

        if (me.isConsumed()) {
          break;
        }
      }
    }
  }

  @Override
  public void mouseReleased(java.awt.event.MouseEvent e) {
    if ((listenerList == null) || (listenerList.getListenerCount(iMouseListener.class) == 0)) {
      return;
    }

    MouseEvent me        = EventHelper.createMouseEvent(this, e);
    Object[]   listeners = listenerList.getListenerList();

    for (int i = listeners.length - 2; i >= 0; i -= 2) {
      if (listeners[i] == iMouseListener.class) {
        ((iMouseListener) listeners[i + 1]).mouseReleased(me);

        if (me.isConsumed()) {
          break;
        }
      }
    }
  }

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    String property = evt.getPropertyName();

    if (property == iComponentPainter.PROPERTY_BORDER) {
      iPlatformBorder b = (iPlatformBorder) evt.getNewValue();

      if (view.getBorder() != b) {
        view.setBorder(b);
      }
    } else if (property == iComponentPainter.PROPERTY_BACKGROUND_COLOR) {
      UIColor c = (UIColor) evt.getNewValue();

      if (c != null) {
        boolean op = view.isOpaque();

        view.setBackground(c);

        if (!op && view.isOpaque()) {
          view.setOpaque(op);
        }
      }
    } else if (property == iComponentPainter.PROPERTY_PAINTER_HOLDER) {
      PainterHolder   ph = (PainterHolder) evt.getNewValue();
      iPlatformBorder b  = (ph == null)
                           ? null
                           : ph.getBorder(ButtonState.DEFAULT);

      if (b != null) {
        view.setBorder(b);
      }
    }
  }

  @Override
  public void repaint() {
    view.repaint();
  }

  @Override
  public void requestFocus() {
    view.requestFocusInWindow();
  }

  @Override
  public void requestFocus(boolean temporary) {
    view.requestFocus(temporary);
  }

  @Override
  public void revalidate() {
    view.revalidate();
    repaint();

    if (needsHiearachyInvalidated() && (parent != null)) {
      parent.revalidate();
    }
  }

  @Override
  public void sendToBack() {
    java.awt.Container p = view.getParent();

    if (p != null) {
      p.setComponentZOrder(view, p.getComponentCount() - 1);
    }
  }

  @Override
  public void setBackground(UIColor bg) {
    view.setBackground(bg);

    if ((bg != null) && (bg.getAlpha() == 0)) {
      view.setOpaque(false);

      if (componentPainter != null) {
        componentPainter.setBackgroundColor(bg, true);
      }
    } else if (view instanceof iPainterSupport) {
      getComponentPainter(true).setBackgroundColor(bg, true);
      view.setOpaque(false);
    } else {
      view.setBackground(bg);
      view.setOpaque(true);
    }
  }

  @Override
  public void setBorder(iPlatformBorder border) {
    if (view instanceof iPainterSupport) {
      view.setBorder(border);    // call first because then next line will cause a
      // property change event to be fired
      getComponentPainter(true).setBorder(border);
    } else {
      view.setBorder(border);
    }
  }

  @Override
  public void setBounds(float x, float y, float width, float height) {
    setBounds(UIScreen.snapToPosition(x), UIScreen.snapToPosition(y), UIScreen.snapToSize(width),
              UIScreen.snapToSize(height));
  }

  @Override
  public void setBounds(int x, int y, int width, int height) {
    boolean changed = (width != getWidth()) || (height != getHeight());

    view.setBounds(x, y, width, height);

    if ((parent != null) && (parent.getOrientation() != Orientation.HORIZONTAL)) {
      if (bounds == null) {
        bounds = new UIRectangle();
      }

      bounds.set(x, y, width, height);
    } else {
      bounds = null;
    }

    if (wantsViewResizeEvent && changed) {
      componentEvent(false, true, false);
    }
  }

  @Override
  public void setComponentPainter(iPlatformComponentPainter cp) {
    if (componentPainter != cp) {
      iPlatformBorder b = null;

      if (this.componentPainter != null) {
        componentPainter.removePropertyChangeListener(this);
        b = componentPainter.getBorder();
      }

      componentPainter = cp;

      if (cp != null) {
        iPlatformBorder nb = cp.getBorder();
        UIColor         bg = cp.getBackgroundColor();

        if (bg != null) {
          view.setBackground(bg);
        }

        cp.addPropertyChangeListener(this);

        if (nb != null) {
          view.setBorder(nb);
          b = null;
        }
      }

      if ((b != null) && (view.getBorder() == b)) {
        view.setBorder(null);
      }

      if (view instanceof iPainterSupport) {
        ((iPainterSupport) view).setComponentPainter(cp);
      }
    }
  }

  public void setComponentPainterEx(iPlatformComponentPainter cp) {
    if (componentPainter != cp) {
      iPlatformBorder b = null;

      if (this.componentPainter != null) {
        b = componentPainter.getBorder();
      }

      componentPainter = cp;

      if (cp != null) {
        iPlatformBorder nb = cp.getBorder();
        UIColor         bg = cp.getBackgroundColor();

        if (bg != null) {
          view.setBackground(bg);
        }

        if (nb != null) {
          view.setBorder(nb);
          b = null;
        }
      }

      if ((b != null) && (view.getBorder() == b)) {
        view.setBorder(null);
      }

      if (view instanceof iPainterSupport) {
        ((iPainterSupport) view).setComponentPainter(cp);
      }
    }
  }

  @Override
  public void setCursor(UICursor cursor) {
    view.setCursor((cursor == null)
                   ? null
                   : cursor.getCursor());
  }

  @Override
  public void setDisabledColor(UIColor color) {
    super.setDisabledColor(color);
    view.putClientProperty(iConstants.RARE_DISABLEDCOLOR_PROPERTY, color);
  }

  @Override
  public void setEnabled(boolean enabled) {
    if (enabled != view.isEnabled()) {
      view.setEnabled(enabled);

      if (!foregroundSet) {
        view.setForeground(enabled
                           ? ColorUtils.getForeground()
                           : ColorUtils.getDisabledForeground());
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
    view.setForeground(fg);

    if ((fg != null) && (componentPainter != null)) {
      componentPainter.setForegroundColor(fg);
    }
  }

  @Override
  public void setLocation(float x, float y) {
    view.setLocation(UIScreen.snapToPosition(x), UIScreen.snapToPosition(y));
  }

  @Override
  public void setLocked(boolean lock) {
    if (view instanceof UtilityPanel) {
      this.locked = lock;
      ((UtilityPanel) view).setLocked(lock);
    } else {
      super.setLocked(lock);
    }
  }

  @Override
  public void setOpaque(boolean opaque) {
    super.setOpaque(opaque);
    view.setOpaque(opaque);
  }

  public void setParent(iParentComponent parent) {
    this.parent = parent;
  }

  @Override
  public void setSize(float width, float height) {
    boolean changed = (width != getWidth()) || (height != getHeight());

    view.setSize((int) Math.ceil(width), (int) Math.ceil(height));

    if (wantsViewResizeEvent && changed) {
      componentEvent(false, true, false);
    }
  }

  public void setText(CharSequence text) {}

  public void setView(JComponent view) {
    if (view != null) {
      view.putClientProperty(RARE_COMPONENT_PROXY_PROPERTY, this);
    }

    if (this.view != null) {
      this.view.putClientProperty(RARE_COMPONENT_PROXY_PROPERTY, null);
    }

    this.view = view;
  }

  @Override
  public void setVisible(boolean visible) {
    view.setVisible(visible);
  }

  @Override
  public UIColor getBackgroundEx() {
    if (componentPainter != null) {
      UIColor c = componentPainter.getBackgroundColor();

      if (c != null) {
        return c;
      }
    }

    if (view.isBackgroundSet()) {
      Color c = view.getBackground();

      return UIColor.fromColor(c);
    }

    return null;
  }

  @Override
  public iPlatformBorder getBorder() {
    if (!(view instanceof JComponent)) {
      return null;
    }

    return UIProxyBorder.fromBorder(view.getBorder());
  }

  @Override
  public UIRectangle getBounds() {
    return SwingHelper.setUIRectangle(null, view.getBounds());
  }

  @Override
  public UIFont getFontEx() {
    if (!view.isFontSet()) {
      return null;
    }

    Font f = view.getFont();

    if ((f != null) &&!(f instanceof UIFont)) {
      return null;
    }

    return UIFont.fromFont(f);
  }

  @Override
  public UIColor getForegroundEx() {
    if (!foregroundSet ||!view.isForegroundSet()) {
      return null;
    }

    Color c = view.getForeground();

    if ((c != null) &&!(c instanceof UIColor)) {
      return null;
    }

    return UIColor.fromColor(c);
  }

  @Override
  public int getHeight() {
    return view.getHeight();
  }

  public JComponent getJComponent() {
    if (!(view instanceof JComponent)) {
      return null;
    }

    return view;
  }

  @Override
  public UIPoint getLocation(UIPoint loc) {
    if (loc == null) {
      loc = new UIPoint();
    }

    if (bounds != null) {
      loc.set(bounds.x, bounds.y);

      return loc;
    }

    return SwingHelper.setUIPoint(loc, view.getLocation());
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
      Point p = view.getLocationOnScreen();

      loc.x = p.x;
      loc.y = p.y;

      return loc;
    }

    Point p = pc.getView().getLocationOnScreen();

    getOrientedLocation(loc);
    loc.x += p.x;
    loc.y += p.y;

    return loc;
  }

  @Override
  public Object getNativeView() {
    return view;
  }

  @Override
  public iParentComponent getParent() {
    if (parent == null) {
      java.awt.Component c = view.getParent();

      while(c != null) {
        iPlatformComponent pc = Platform.findPlatformComponent(c);

        if (pc instanceof iParentComponent) {
          parent = (iParentComponent) pc;

          break;
        }

        if (pc != null) {
          c = pc.getView().getParent();
        } else {
          c = null;
        }
      }
    }

    return parent;
  }

  public Object getPlatformObject() {
    return view;
  }

  @Override
  public UIDimension getSize(UIDimension size) {
    return SwingHelper.setUIDimension(size, view.getSize());
  }

  @Override
  public TransferHandler getTransferHandler() {
    return view.getTransferHandler();
  }

  @Override
  public JComponent getView() {
    return view;
  }

  @Override
  public int getWidth() {
    return view.getWidth();
  }

  @Override
  public int getX() {
    return view.getX();
  }

  @Override
  public int getY() {
    return view.getY();
  }

  @Override
  public boolean hasBeenFocused() {
    return false;
  }

  @Override
  public boolean hasChildren() {
    return view.getComponentCount() > 0;
  }

  @Override
  public boolean hasHadInteraction() {
    return hadInteraction;
  }

  @Override
  public boolean isBackgroundSet() {
    if (!view.isBackgroundSet()) {
      return false;
    }

    Color c = view.getBackground();

    return (c != null) &&!(c instanceof UIResource);
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
    return view.isFocusOwner();
  }

  @Override
  public boolean isFocusable() {
    return view.isFocusable();
  }

  @Override
  public boolean isLeftToRight() {
    return view.getComponentOrientation().isLeftToRight();
  }

  @Override
  public boolean isSelected() {
    return false;
  }

  @Override
  public boolean isShowing() {
    return (view != null) && view.isShowing();
  }

  @Override
  public boolean isVisible() {
    return (view != null) && view.isVisible();
  }

  protected void disposeEx() {
    if (scaleGestureDetector != null) {
      view.removeMouseListener(scaleGestureDetector);
      scaleGestureDetector = null;
    }

    parent = null;
  }

  @Override
  protected void interacted() {}

  @Override
  protected boolean needsHiearachyInvalidated() {
    return false;
  }

  @Override
  protected void putClientPropertyEx(String key, Object value) {
    if (key == aAnimator.ANIMATOR_KEY) {
      view.putClientProperty(key, value);
    }

    super.putClientPropertyEx(key, value);
  }

  // protected void putClientPropertyEx(String key, Object value) {
  // view.putClientProperty(key, value);
  // }
  //
  // protected Object getClientPropertyEx(Object key) {
  // if (!(view instanceof JComponent)) {
  // return null;
  // }
  //
  // return ((JComponent) view).getClientProperty(key);
  // }
  //
  // protected Object getClientPropertyEx(String key) {
  // return view.getClientProperty(key);
  // }
  @Override
  protected iPlatformComponentPainter getComponentPainterEx() {
    if (!(view instanceof iPainterSupport)) {
      return null;
    }

    return ((iPainterSupport) view).getComponentPainter();
  }

  @Override
  protected void getMinimumSizeEx(UIDimension size) {
    if (view instanceof iView) {
      ((iView) view).getMinimumSize(size);
    } else {
      SwingHelper.setUIDimension(size, view.getMinimumSize());
    }
  }

  protected int getPrefHeightEx(int width) {
    return view.getPreferredSize().height;
  }

  @Override
  protected void getPreferredSizeEx(UIDimension size, float maxWidth) {
    view.putClientProperty(iConstants.RARE_WIDTH_FIXED_VALUE, maxWidth);

    if (view instanceof iView) {
      ((iView) view).getPreferredSize(size, (int) Math.ceil(maxWidth));
    } else {
      SwingHelper.setUIDimension(size, view.getPreferredSize());
    }
  }
}
