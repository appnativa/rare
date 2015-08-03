/*
 * @(#)JViewportEx.java   2009-01-17
 *
 * Copyright (c) appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.platform.swing.ui.view;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.Point;

import javax.swing.JComponent;
import javax.swing.JViewport;
import javax.swing.Scrollable;
import javax.swing.ViewportLayout;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.appnativa.rare.platform.swing.ui.util.SwingGraphics;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.painter.iPainter;
import com.appnativa.rare.ui.painter.iPainterSupport;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;

/**
 *
 * @author Don DeCoteau
 */
public class JViewportEx extends JViewport implements iPainterSupport,ChangeListener {
  int                               viewportHeight;
  int                               viewportWidth;

  /**
   * whether the viewport should force the view to match it's size if the view
   * is to small
   */
  protected boolean                 forceSizeMatch;
  protected SwingGraphics           graphics;
  private iPlatformComponentPainter componentPainter;
  protected int                     preferredHeight;
  protected int                     preferredWidth;
  private boolean                   fixedViewPosition;

  /** Creates a new instance of JViewportEx */
  public JViewportEx() {
    super.setOpaque(false);
  }

  @Override
  public void paint(Graphics g) {
    graphics = SwingGraphics.fromGraphics(g, this, graphics);
    super.paint(g);
    graphics.clear();
  }

  @Override
  public void setComponentPainter(iPlatformComponentPainter cp) {
    this.componentPainter = cp;
  }

  public void setForceSizeMatch(boolean forceSizeMatch) {
    this.forceSizeMatch = forceSizeMatch;
  }

  public void setViewHeight(int height) {
    viewportHeight = height;
  }

  @Override
  public void setViewPosition(Point p) {
    if (!fixedViewPosition) {
      super.setViewPosition(p);
    }
  }

  public void setViewPositionEx(Point p) {
    Component v = getView();
    if (v instanceof ScrollPaneEx) {
      ((ScrollPaneEx) v).getViewport().setViewPosition(p);
    } else {
      setViewPosition(p);
    }
  }

  public Point getViewPositionEx() {
    Component v = getView();
    if (v instanceof ScrollPaneEx) {
      return ((ScrollPaneEx) v).getViewport().getViewPosition();
    } else {
      return super.getViewPosition();
    }
  }

  @Override
  public void setViewSize(Dimension d) {
    if (forceSizeMatch) {
      int w = getWidth();
      int h = getHeight();

      if (w > d.width) {
        d.width = w;
      }

      if (h > d.height) {
        d.height = h;
      }
    }

    Component view = getView();

    if (view != null) {
      iPlatformComponent pc = com.appnativa.rare.ui.Component.fromView((JComponent) view);
      Dimension oldSize = view.getSize();

      if (!d.equals(oldSize)) {
        // scrollUnderway will be true if this is invoked as the
        // result of a validate and setViewPosition was previously
        // invoked.
        scrollUnderway = false;

        if (pc == null) {
          view.setSize(d);
        } else {
          pc.setBounds(view.getX(), view.getY(), d.width, d.height);
        }

        isViewSizeSet = true;
        fireStateChanged();
      }
    }

    super.setViewSize(d);
  }

  @Override
  public void setBounds(int x, int y, int width, int height) {
    Component p = getParent();
    if (p instanceof ScrollPaneEx) {
      ScrollPaneEx pane = (ScrollPaneEx) p;
      if (pane.hasScrollPaneRowHeader() && pane.getRowHeader() == this) {
        viewportWidth = width;
        return;
      }
    }
    super.setBounds(x, y, width, height);
  }

  @Override
  public Dimension getPreferredSize() {
    Dimension d = super.getPreferredSize();
    if (preferredHeight > 0) {
      d.height = preferredHeight;
    }
    if (preferredWidth > 0) {
      d.width = preferredWidth;
    }
    return d;
  }

  protected void setScrollPaneRowHeaderBounds() {
    ScrollPaneEx pane = (ScrollPaneEx) getParent();
    JViewport vp = pane.getViewport();
    JViewport ch = pane.getColumnHeader();
    pane = (ScrollPaneEx) getView();
    JViewport ch2 = pane.getColumnHeader();
    int y;
    int h = vp.getHeight();
    if (ch != null && ch.isVisible() && ch2 != null && ch2.isVisible() && viewportWidth > 0) {
      y = ch.getY();
      h += ch.getHeight();
    } else {
      y = vp.getY();
    }
    super.setBounds(vp.getX() - viewportWidth, y, viewportWidth, h);
  }

  public void setViewWidth(int width) {
    viewportWidth = width;
  }

  @Override
  public iPlatformComponentPainter getComponentPainter() {
    return componentPainter;
  }

  @Override
  public Dimension getViewSize() {
    Component view = getView();

    if (view == null) {
      return new Dimension(0, 0);
    }

    Dimension d;

    if (isViewSizeSet) {
      d = view.getSize();
    } else {
      iPlatformComponent pc = com.appnativa.rare.ui.Component.fromView((JComponent) view);
      UIDimension size = pc.getPreferredSize(null, getWidth());

      d = new Dimension(size.intWidth(), size.intHeight());
    }

    return d;
  }

  public boolean isForceSizeMatch() {
    return forceSizeMatch;
  }

  @Override
  protected LayoutManager createLayoutManager() {
    return ViewportLayoutEx.SHARED_INSTANCE2;
  }

  @Override
  protected void paintBorder(Graphics g) {
    if (componentPainter == null) {
      super.paintBorder(g);
    }
  }

  @Override
  protected void paintChildren(Graphics g) {
    super.paintChildren(graphics.getGraphics());

    iPlatformComponentPainter cp = getComponentPainter();

    if (cp != null) {
      float height = getHeight();
      float width = getWidth();

      cp.paint(graphics, 0, 0, width, height, iPainter.HORIZONTAL, true);
    }
  }

  @Override
  protected void paintComponent(Graphics g) {
    iPlatformComponentPainter cp = getComponentPainter();

    if (cp != null) {
      float height = getHeight();
      float width = getWidth();

      cp.paint(graphics, 0, 0, width, height, iPainter.HORIZONTAL, false);
    }

    super.paintComponent(g);
  }

  public int getPreferredWidth() {
    return preferredWidth;
  }

  public void setPreferredWidth(int preferredWidth) {
    this.preferredWidth = preferredWidth;
  }

  public int getPreferredHeight() {
    return preferredHeight;
  }

  public void setPreferredHeight(int preferredHeight) {
    this.preferredHeight = preferredHeight;
  }

  public boolean isFixedViewPosition() {
    return fixedViewPosition;
  }

  public void setFixedViewPosition(boolean fixedViewPosition) {

    this.fixedViewPosition = fixedViewPosition;
  }

  public void linkToViewPort(JViewport vp, boolean horizontal) {
    ViewportLayoutEx l = new ViewportLayoutEx();
    l.horizontalLink = horizontal;
    l.linkedViewPort = vp;
    setLayout(l);
    vp.addChangeListener(this);
  }

  // Copied for ViewPortLayout
  static class ViewportLayoutEx extends ViewportLayout {
    // Single instance used by JViewport.
    static ViewportLayoutEx SHARED_INSTANCE2 = new ViewportLayoutEx();
    JViewport               linkedViewPort;
    boolean                 horizontalLink;

    @Override
    public void removeLayoutComponent(Component c) {
      ((JComponent) c).putClientProperty(iPlatformComponent.RARE_SWING_WIDTH_FIXED_VALUE, null);
      ((JComponent) c).putClientProperty(iPlatformComponent.RARE_SWING_HEIGHT_FIXED_VALUE, null);
      super.removeLayoutComponent(c);
    }

    @Override
    public void addLayoutComponent(String name, Component c) {
      ((JComponent) c).putClientProperty(iPlatformComponent.RARE_SWING_WIDTH_FIXED_VALUE, null);
      ((JComponent) c).putClientProperty(iPlatformComponent.RARE_SWING_HEIGHT_FIXED_VALUE, null);
      super.addLayoutComponent(name, c);
    }

    @Override
    public void layoutContainer(Container parent) {
      if (linkedViewPort == null) {
        super.layoutContainer(parent);
      } else {
        JViewport vp = (JViewport) parent;
        Component view = vp.getView();
        if (view != null) {
          Point p = linkedViewPort.getViewPosition();
          Dimension size = linkedViewPort.getViewSize();
          if (horizontalLink) {
            p.y = 0;
            size.height = vp.getExtentSize().height;
          } else {
            p.x = 0;
            size.width = vp.getExtentSize().width;
          }
          vp.setViewPosition(p);
          vp.setViewSize(size);
          size = linkedViewPort.getSize();
          if (horizontalLink) {
            size.height=vp.getHeight();
          }
          else {
            size.width=vp.getWidth();
          }
          vp.setSize(size);
        }
      }
    }

    /**
     * Returns the preferred dimensions for this layout given the components in
     * the specified target container.
     * 
     * @param parent
     *          the component which needs to be laid out
     * @return a <code>Dimension</code> object containing the preferred
     *         dimensions
     * @see #minimumLayoutSize
     */
    @Override
    public Dimension preferredLayoutSize(Container parent) {
      Component view = ((JViewport) parent).getView();
      int max = 0;
      if (parent.getParent() instanceof ScrollPaneEx) {
        max = ((ScrollPaneEx) parent.getParent()).preferredSizemaxWidth;
      }
      if (view == null) {
        return new Dimension(0, 0);
      } else if (view instanceof iView) {
        UIDimension size = new UIDimension();
        ((iView) view).getPreferredSize(size, max);
        return new Dimension(size.intWidth(), size.intHeight());
      } else if (view instanceof Scrollable) {
        return ((Scrollable) view).getPreferredScrollableViewportSize();
      } else {
        return view.getPreferredSize();
      }
    }

  }

  @Override
  public void stateChanged(ChangeEvent e) {
    revalidate();
    repaint();
    
  }
}
