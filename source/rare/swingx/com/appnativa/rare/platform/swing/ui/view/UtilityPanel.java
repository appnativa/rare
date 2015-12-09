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

package com.appnativa.rare.platform.swing.ui.view;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.KeyboardFocusManager;
import java.awt.LayoutManager;
import java.awt.Paint;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.Scrollable;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import javax.swing.event.MouseInputListener;

import com.appnativa.rare.iConstants;
import com.appnativa.rare.platform.swing.ui.util.SwingHelper;
import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.iLayoutManager;
import com.appnativa.rare.ui.iPlatformComponent;

/**
 * A general purpose panel
 *
 * @version 0.3, 2007-05-14
 * @author Don DeCoteau
 */
public class UtilityPanel extends JPanelEx implements iLayoutManager, Scrollable {
  public static boolean PAINT_GLASS = false;
  protected boolean     locked;

  /** minimum height */
  protected int minimumHeight;

  /** minimum width */
  protected int minimumWidth;

  /**
   * whether the minimumWidth/minimumHeight values should only be used when the
   * component is empty
   */
  protected boolean          useMinimumVarsOnlyWhenEmpty;
  private boolean            blockLayout;
  private iLayoutTracker     layoutTracker;
  private boolean            lockViewHeight;
  private boolean            lockViewWidth;
  private JComponent         glassPane;
  private MouseInputListener glassPaneMouseListener;
  private UIColor            lockedColor;
  private Cursor             lockedCursor;
  private java.awt.Component recentFocusOwner;

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
      size.width  = Math.max(minimumWidth, size.width);
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
  public Object getConstraints(iPlatformComponent c) {
    return null;
  }

  public JComponent getGlassPane() {
    return glassPane;
  }

  public iLayoutTracker getLayoutTracker() {
    return layoutTracker;
  }

  /**
   * Returns the mouse cursor to be used for the locked layer, it is a
   * <code>Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)</code> by default
   *
   * @return the cursor to be used for the locked layer
   *
   * @see #setLocked(boolean)
   */
  public Cursor getLockedCursor() {
    return lockedCursor;
  }

  /**
   * Gets the paint used when the view is locked
   *
   * @return the paint
   */
  public Paint getLockedPainter() {
    return lockedColor;
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
  public void getMinimumSize(UIDimension size, int maxWidth) {
    Dimension d = getMinimumSize();

    size.width  = d.width;
    size.height = d.height;
  }

  @Override
  public Dimension getPreferredScrollableViewportSize() {
    return getPreferredSize();
  }

  @Override
  public void getPreferredSize(UIDimension size, int maxWidth) {
    Dimension d = getPreferredSize();

    size.width  = d.width;
    size.height = d.height;
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

  @Override
  public boolean imageUpdate(Image img, int infoflags, int x, int y, int w, int h) {
    if (!SwingHelper.isContinueImageUpdate(this, img)) {
      return false;
    }

    return super.imageUpdate(img, infoflags, x, y, w, h);
  }

  /**
   * @return the blockLayout
   */
  public boolean isBlockLayout() {
    return blockLayout;
  }

  /**
   * Returns <code>true</code> is this layer is locked, <code>false</code>
   * otherwise
   *
   * @return <code>true</code> is this layer is locked, <code>false</code>
   *         otherwise
   *
   * @see #setLocked(boolean)
   * @see #setLockedCursor(java.awt.Cursor)
   */
  public boolean isLocked() {
    return locked;
  }

  /**
   *  Gets whether the height of the view is locked (not scrollable)
   * 
   *  @return true if it is locked; false otherwise
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

  /**
   * Sets the glassPane of this layer
   *
   * @param glassPane
   *          the glassPane of this layer
   */
  public void setGlassPane(JComponent glassPane) {
    if (glassPane == null) {
      throw new IllegalArgumentException("GlassPane can't be set to null");
    }

    JComponent oldGlassPane = getGlassPane();

    if (oldGlassPane != null) {
      super.remove(oldGlassPane);
    }

    super.addImpl(glassPane, null, 0);
    this.glassPane = glassPane;

    if (PAINT_GLASS) {
      if (glassPane instanceof GlassPanel) {
        UIColor c = (UIColor) getClientProperty(iConstants.RARE_DISABLEDCOLOR_PROPERTY);

        if (c != null) {
          ((GlassPanel) glassPane).setLockedColor(c);
        }
      }
    }
  }

  /**
   * Sets the listener to use for the glass pane
   *
   * @param l
   *          the listener
   */
  public void setGlassPaneMouseListener(MouseInputListener l) {
    this.glassPaneMouseListener = l;
  }

  @Override
  public void setLayoutTracker(iLayoutTracker layoutTracker) {
    this.layoutTracker = layoutTracker;
  }

  /**
   * Sets if this layer is locked or not.
   *
   * @param locked
   *          <code>true</code> if this layer should be locked
   *          <code>false</code> otherwise
   *
   * @see #setLockedCursor(java.awt.Cursor)
   */
  public void setLocked(boolean locked) {
    if (locked != isLocked()) {
      if (glassPane == null) {
        setGlassPane(new GlassPanel(false));
      }

      java.awt.Component focusOwner         =
        KeyboardFocusManager.getCurrentKeyboardFocusManager().getPermanentFocusOwner();
      boolean            isFocusInsideLayer = (focusOwner != null) && SwingUtilities.isDescendingFrom(focusOwner, this);

      if (locked) {
        getGlassPane().addMouseListener(glassPaneMouseListener);
        getGlassPane().addMouseMotionListener(glassPaneMouseListener);
        setFocusTraversalPolicyProvider(true);

        if (isFocusInsideLayer) {
          recentFocusOwner = focusOwner;
          requestFocusInWindow();
        }

        getGlassPane().setVisible(true);
        getGlassPane().setCursor(getLockedCursor());
      } else {
        getGlassPane().removeMouseListener(glassPaneMouseListener);
        getGlassPane().removeMouseMotionListener(glassPaneMouseListener);
        setFocusTraversalPolicyProvider(false);

        if (isFocusInsideLayer && (recentFocusOwner != null)) {
          recentFocusOwner.requestFocusInWindow();
        }

        recentFocusOwner = null;
        getGlassPane().setVisible(false);
        getGlassPane().setCursor(null);
      }

      this.locked = locked;
      repaint();
    }
  }

  /**
   * Sets the mouse cursor to be used for the locked layer
   *
   * @param lockedCursor
   *          the mouse cursor to be used for the locked layer
   *
   * @see #setLocked(boolean)
   */
  public void setLockedCursor(Cursor lockedCursor) {
    this.lockedCursor = lockedCursor;

    if (isLocked()) {
      getGlassPane().setCursor(lockedCursor);
    }
  }

  /**
   * Sets the paint to use when the view is locked
   *
   * @param paint
   *          the paint
   */
  public void setLockedPaint(UIColor paint) {
    this.lockedColor = paint;

    if (getGlassPane() instanceof GlassPanel) {
      ((GlassPanel) getGlassPane()).setLockedColor(lockedColor);
    }
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
    this.lockViewWidth  = lock;
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

  @Override
  protected void addImpl(java.awt.Component comp, Object constraints, int index) {
    super.addImpl(comp, constraints, index);

    if ((comp != glassPane) && (glassPane != null)) {
      setComponentZOrder(glassPane, 0);
    }
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
  protected void layoutContainerEx(int width, int height) {
    if (glassPane != null) {
      glassPane.setBounds(0, 0, width, height);
    }
  }
}
