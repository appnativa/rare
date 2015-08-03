/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.appnativa.rare.platform.swing.ui.view;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Composite;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.LayoutManager2;
import java.awt.geom.AffineTransform;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JToolTip;
import javax.swing.border.Border;

import com.appnativa.rare.platform.swing.ui.util.SwingGraphics;
import com.appnativa.rare.platform.swing.ui.util.SwingHelper;
import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.iPlatformBorder;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.effects.aAnimator;
import com.appnativa.rare.ui.effects.iPlatformAnimator;
import com.appnativa.rare.ui.effects.iTransitionAnimator;
import com.appnativa.rare.ui.painter.iPainter;
import com.appnativa.rare.ui.painter.iPainterSupport;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;

/**
 *
 * @author Don DeCoteau
 */
public class JPanelEx extends JPanel implements iPainterSupport, iView, LayoutManager2 {

  /** a system painter */
  public static iPlatformComponentPainter SYSTEM_PAINTER;
  boolean                                 paintComponentCalled;
  protected iPlatformComponentPainter     componentPainter;
  protected Composite                     composite;
  protected SwingGraphics                 graphics;
  protected boolean                       shapedBorder;
  protected AffineTransform               transform;
  boolean                                 inPreferedLayout;

  public JPanelEx() {
    super(null);
    setLayout(this);
    setBackground(SwingHelper.getBackgroundColorResource());
    setOpaque(false);
  }

  public JPanelEx(JComponent comp) {
    this(new BorderLayout());
    add(comp);
  }

  public JPanelEx(LayoutManager layout) {
    super(layout);
  }

  public JPanelEx(LayoutManager layout, boolean isDoubleBuffered) {
    super(layout, isDoubleBuffered);
  }

  @Override
  public void addLayoutComponent(java.awt.Component comp, Object constraints) {
  }

  @Override
  public void addLayoutComponent(String name, java.awt.Component comp) {
  }

  @Override
  public JToolTip createToolTip() {
    return JToolTipEx.createNewToolTip(this);
  }

  @Override
  public Dimension getPreferredSize() {
    Dimension d = inPreferedLayout ? new Dimension(0, 0) : super.getPreferredSize();
    Integer w = (Integer) getClientProperty(iPlatformComponent.RARE_SWING_WIDTH_FIXED_VALUE);
    Integer h = (Integer) getClientProperty(iPlatformComponent.RARE_SWING_HEIGHT_FIXED_VALUE);

    if ((w != null) && (w.intValue() > 0)) {
      d.width = w;
    }
    if ((h != null) && (h.intValue() > 0)) {
      d.height = h;
    }
    return d;
  }

  @Override
  public void invalidateLayout(Container target) {
  }

  
  @Override
  public void layoutContainer(Container parent) {
    Object a = getClientProperty(aAnimator.ANIMATOR_KEY);

    if (a != null) {
      if ((a instanceof iPlatformAnimator) && ((iPlatformAnimator) a).isRunning()) {
        return;
      }

      if ((a instanceof iTransitionAnimator) && ((iTransitionAnimator) a).isRunning()) {
        return;
      }

      putClientProperty(aAnimator.ANIMATOR_KEY, null);
    }

    int width = getWidth();
    int height = getHeight();

    layoutContainerEx(width, height);
  }

  @Override
  public Dimension maximumLayoutSize(Container target) {
    return target.getMaximumSize();
  }

  @Override
  public Dimension minimumLayoutSize(java.awt.Container parent) {
    return SwingHelper.setDimension(null, Component.fromView((JComponent) parent).getMinimumSize());
  }

  @Override
  public void paint(Graphics g) {
    paintComponentCalled = false;

    Graphics2D g2 = (Graphics2D) g;
    AffineTransform tx = g2.getTransform();
    Composite cx = (composite == null) ? null : g2.getComposite();

    if (transform != null) {
      g2.transform(transform);
    }

    if (composite != null) {
      g2.setComposite(composite);
    }

    graphics = SwingGraphics.fromGraphics(g2, this, graphics);
    super.paint(g2);

    if (tx != null) {
      g2.setTransform(tx);
    }

    if (composite != null) {
      g2.setComposite((cx == null) ? AlphaComposite.SrcOver : cx);
    }

    graphics.clear();
  }

  @Override
  public Dimension preferredLayoutSize(java.awt.Container parent) {
    try {
      inPreferedLayout = true;
      return SwingHelper.setDimension(null, Component.fromView((JComponent) parent).getPreferredSize());
    } finally {
      inPreferedLayout = false;
    }
  }

  @Override
  public void removeLayoutComponent(java.awt.Component comp) {
  }

  @Override
  public void setBorder(Border border) {
    super.setBorder(border);
    shapedBorder = false;

    if (border instanceof iPlatformBorder) {
      shapedBorder = !((iPlatformBorder) border).isRectangular();
    }
  }

  @Override
  public void setComponentPainter(iPlatformComponentPainter cp) {
    this.componentPainter = cp;
  }

  public void setComposite(Composite composite) {
    this.composite = composite;
  }

  @Override
  public void setTransformEx(AffineTransform tx) {
    transform = tx;
  }

  @Override
  public iPlatformComponentPainter getComponentPainter() {
    return componentPainter;
  }

  public Composite getComposite() {
    return composite;
  }

  @Override
  public float getLayoutAlignmentX(Container parent) {
    return 0.5f;
  }

  @Override
  public float getLayoutAlignmentY(Container parent) {
    return 0.5f;
  }

  @Override
  public Dimension getMinimumSize() {
    return new Dimension(0, 0);
  }

  @Override
  public void getMinimumSize(UIDimension size) {
    size.width = 0;
    size.height = 0;
  }

  @Override
  public void getPreferredSize(UIDimension size, int maxWidth) {
    size.width = 0;
    size.height = 0;
  }

  @Override
  public AffineTransform getTransformEx() {
    return transform;
  }

  public boolean hasValue() {
    return getComponentCount() > 0;
  }

  @Override
  public boolean isOpaque() {
    if ((componentPainter != null) && componentPainter.isBackgroundPaintEnabled()) {
      return false;
    }

    return !shapedBorder && super.isOpaque();
  }

  protected void layoutContainerEx(int width, int height) {
  }

  @Override
  protected void paintBorder(Graphics g) {
    if (componentPainter == null) {
      super.paintBorder(g);
    }
  }

  @Override
  protected void paintChildren(Graphics g) {
    iPlatformComponentPainter cp = getComponentPainter();
    float height = getHeight();
    float width = getWidth();

    if (!paintComponentCalled && (cp != null)) {
      cp.paint(graphics, 0, 0, width, height, iPainter.HORIZONTAL, false);
    }

    super.paintChildren(graphics.getGraphics());

    if (cp != null) {
      cp.paint(graphics, 0, 0, width, height, iPainter.HORIZONTAL, true);
    }

    if (SYSTEM_PAINTER != null) {
      SYSTEM_PAINTER.paint(graphics, 0, 0, width, height, iPainter.HORIZONTAL, true);
    }
  }

  @Override
  protected void paintComponent(Graphics g) {
    paintComponentCalled = true;

    iPlatformComponentPainter cp = getComponentPainter();
    float height = getHeight();
    float width = getWidth();

    if (cp != null) {
      cp.paint(graphics, 0, 0, width, height, iPainter.HORIZONTAL, false);
    }

    if (SYSTEM_PAINTER != null) {
      SYSTEM_PAINTER.paint(graphics, 0, 0, width, height, iPainter.HORIZONTAL, false);
    }

    super.paintComponent(g);
  }

  protected iPlatformComponent getComponentEx() {
    return Component.fromView(this);
  }
}
