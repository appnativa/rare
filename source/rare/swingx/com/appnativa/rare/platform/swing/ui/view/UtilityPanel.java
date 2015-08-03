/*
 * @(#)UtilityPanel.java   2009-03-16
 *
 * Copyright (c) appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.platform.swing.ui.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.Scrollable;
import javax.swing.border.TitledBorder;

import com.appnativa.rare.Platform;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.platform.swing.ui.util.SwingHelper;
import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.iLayoutManager;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.painter.UIComponentPainter;

/**
 * A general purpose panel
 *
 * @version 0.3, 2007-05-14
 * @author Don DeCoteau
 */
public class UtilityPanel extends JPanelEx implements iLayoutManager, Scrollable {
  protected boolean      locked;

  /** minimum height */
  protected int          minimumHeight;

  /** minimum width */
  protected int          minimumWidth;

  /**
   * whether the minimumWidth/minimumHeight values should only be used when the
   * component is empty
   */
  protected boolean      useMinimumVarsOnlyWhenEmpty;
  private boolean        blockLayout;
  private iLayoutTracker layoutTracker;
  private boolean        lockViewHeight;
  private boolean        lockViewWidth;

  /**
   * Constructs a new instance
   */
  public UtilityPanel() {
    super();
    setBackground(ColorUtils.getBackground());
    setOpaque(false);
  }

  /**
   * Constructs a new instance
   *
   * @param comp
   *          the center component
   */
  public UtilityPanel(JComponent comp) {
    this(null, new BorderLayout());
    add(comp);
    setOpaque(false);
  }

  /**
   * Constructs a new instance
   *
   * @param layout
   *          {@inheritDoc}
   */
  public UtilityPanel(LayoutManager layout) {
    this(null, layout);
  }

  /**
   * Constructs a new instance
   *
   * @param title
   *          the panel's title
   */
  public UtilityPanel(String title) {
    this(title, new BorderLayout());
  }

  /**
   * Constructs a new instance
   *
   * @param title
   *          the panel's title
   * @param layout
   *          the layout
   */
  public UtilityPanel(String title, LayoutManager layout) {
    super(layout);
    setBackground(ColorUtils.getBackground());
    setOpaque(false);
    putClientProperty("Synthetica.opaque", Boolean.FALSE);

    if (title != null) {
      setBorder(new TitledBorder(title));
    }
  }

  @Override
  public void add(iPlatformComponent c, Object constraints, int position) {
    add(c.getView(), constraints, position);
  }

  public void adjustMinimumSize(UIDimension size) {
    if (!useMinimumVarsOnlyWhenEmpty || (getComponentCount() > 0)) {
      size.width = Math.max(minimumWidth, size.width);
      size.height = Math.max(minimumHeight, size.height);
    }
  }

  @Override
  public void doLayout() {
    if (!blockLayout) {
      if (layoutTracker != null) {
        layoutTracker.willPerformLayout(this);
      }

      super.doLayout();

      if (layoutTracker != null) {
        layoutTracker.layoutPerformed(this);
      }
    }
  }

  @Override
  public boolean imageUpdate(Image img, int infoflags, int x, int y, int w, int h) {
    if (!SwingHelper.isContinueImageUpdate(this, img)) {
      return false;
    }

    return super.imageUpdate(img, infoflags, x, y, w, h);
  }

  @Override
  public void remove(iPlatformComponent c) {
    if (c != null) {
      remove(c.getView());
    }
  }

  /**
   * @param blockLayout
   *          the blockLayout to set
   */
  public void setBlockLayout(boolean blockLayout) {
    this.blockLayout = blockLayout;
  }

  @Override
  public void setLayoutTracker(iLayoutTracker layoutTracker) {
    this.layoutTracker = layoutTracker;
  }

  /**
   * Sets whether the height of the view should be locked (not scrollable)
   *
   * @param lock
   *          true to lock; false otherwise
   */
  public void setLockViewHeight(boolean lock) {
    this.lockViewHeight = lock;
    this.revalidate();
    this.repaint();
  }

  /**
   * Sets whether the size of the view should be locked (not scrollable)
   *
   * @param lock
   *          true to lock; false otherwise
   */
  public void setLockViewSize(boolean lock) {
    this.lockViewWidth = lock;
    this.lockViewHeight = lock;
    this.revalidate();
    this.repaint();
  }

  /**
   * Sets whether the width of the view should be locked (not scrollable)
   *
   * @param lock
   *          true to lock; false otherwise
   */
  public void setLockViewWidth(boolean lock) {
    this.lockViewWidth = lock;
    this.revalidate();
    this.repaint();
  }

  public void setLocked(boolean lock) {
    locked = lock;
    if (lock) {
      iPlatformComponent pc = Component.fromView(this);
      if (componentPainter == null) {
        pc.setComponentPainter(new UIComponentPainter());
      }
      UIColor c = (UIColor) getClientProperty(iConstants.RARE_DISABLEDCOLOR_PROPERTY);
      if (c == null) {
        c = Platform.getUIDefaults().getColor("Rare.disabledBackgroundColor");
      }
      if (c == null) {
        c = ColorUtils.DISABLED_TRANSPARENT_COLOR;
      }
      pc.setDisabledColor(c);
    }

    setEnabled(!lock);
  }

  @Override
  public Object getConstraints(iPlatformComponent c) {
    return null;
  }

  public iLayoutTracker getLayoutTracker() {
    return layoutTracker;
  }

  @Override
  public Dimension getMinimumSize() {
    Dimension d = super.getMinimumSize();

    if (!useMinimumVarsOnlyWhenEmpty || (this.getComponentCount() == 0)) {
      if (d.width < minimumWidth) {
        d.width = minimumWidth;
      }

      if (d.height < minimumHeight) {
        d.height = minimumHeight;
      }
    }

    return d;
  }

  @Override
  public Dimension getPreferredScrollableViewportSize() {
    return getPreferredSize();
  }

  public UIDimension getPreferrredSize(UIDimension size, int maxWidth) {
    Component c = (Component) Component.fromView(this);

    return c.getPreferredSize(size, maxWidth);
  }

  @Override
  public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
    return 40;
  }

  @Override
  public boolean getScrollableTracksViewportHeight() {
    return lockViewHeight;
  }

  @Override
  public boolean getScrollableTracksViewportWidth() {
    return lockViewWidth;
  }

  @Override
  public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
    return 10;
  }

  /**
   * @return the blockLayout
   */
  public boolean isBlockLayout() {
    return blockLayout;
  }

  /**
   * Gets whether the height of the view is locked (not scrollable)
   *
   * @return true if it is locked; false otherwise
   */
  public boolean isLockViewHeight() {
    return lockViewHeight;
  }

  /**
   * Gets whether the width of the view is locked (not scrollable)
   *
   * @return true if it is locked; false otherwise
   */
  public boolean isLockViewWidth() {
    return lockViewWidth;
  }

  public boolean isLocked() {
    return locked;
  }

  /**
   * Gets the default minimum size for the panel when the panel is empty
   *
   * @return the default minimum size for the panel when it is empty
   */
  protected Dimension getDefaultEmptyMinSize() {
    return new Dimension(minimumWidth, minimumHeight);
  }

  @Override
  public void getMinimumSize(UIDimension size) {
    Dimension d = getMinimumSize();
    size.width = d.width;
    size.height = d.height;
  }

  @Override
  public void getPreferredSize(UIDimension size, int maxWidth) {
    Dimension d = getPreferredSize();
    size.width = d.width;
    size.height = d.height;
  }

}
