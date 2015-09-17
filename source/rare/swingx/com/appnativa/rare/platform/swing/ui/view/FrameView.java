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

import com.appnativa.rare.Platform;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.platform.swing.ui.util.SwingHelper;
import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.Container;
import com.appnativa.rare.ui.RenderType;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.iPlatformBorder;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.painter.RenderSpace;
import com.appnativa.rare.ui.painter.aUIPainter;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.KeyboardFocusManager;
import java.awt.Paint;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputListener;

public class FrameView extends UtilityPanel {
  private RenderType         renderType  = RenderType.STRETCHED;
  private RenderSpace        renderSpace = RenderSpace.WITHIN_MARGIN;
  private UIInsets           padding     = new UIInsets();
  private UIInsets           insets      = new UIInsets();
  private JComponent         glassPane;
  private MouseInputListener glassPaneMouseListener;
  private UIColor            lockedColor;
  private Cursor             lockedCursor;
  private java.awt.Component recentFocusOwner;

  public FrameView() {
    super();
  }

  @Override
  public Dimension minimumLayoutSize(java.awt.Container parent) {
    UIDimension size = new UIDimension();

    getMinimumSize(size);

    return SwingHelper.setDimension(null, size);
  }

  @Override
  public Dimension preferredLayoutSize(java.awt.Container parent) {
    UIDimension size = new UIDimension();

    getPreferredSize(size, 0);

    return SwingHelper.setDimension(null, size);
  }

  public void setDisabledColor(UIColor disabledColor) {
    setLockedPaint(disabledColor);
  }

  /**
   * Sets whether or not this component is enabled.
   *
   * @param enabled
   *          true if this component should be enabled, false otherwise
   */
  @Override
  public void setEnabled(boolean enabled) {
    setLocked(!enabled);
    super.setEnabled(enabled);
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

    if (glassPane instanceof GlassPanel) {
      UIColor c = (UIColor) getClientProperty(iConstants.RARE_DISABLEDCOLOR_PROPERTY);

      if (c == null) {
        c = Platform.getUIDefaults().getColor("Rare.disabledBackgroundColor");
      }

      if (c == null) {
        c = ColorUtils.DISABLED_TRANSPARENT_COLOR;
      }

      ((GlassPanel) glassPane).setLockedColor(c);
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

  /**
   * Sets if this layer is locked or not.
   *
   * @param locked
   *          <code>true</code> if this layer should be locked
   *          <code>false</code> otherwise
   *
   * @see #setLockedCursor(java.awt.Cursor)
   */
  @Override
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

  public void setPadding(UIInsets insets) {
    if (insets == null) {
      padding.set(0, 0, 0, 0);
    } else {
      padding.set(insets);
    }
  }

  public void setRenderSpace(RenderSpace renderSpace) {
    this.renderSpace = renderSpace;
  }

  public void setViewRenderType(RenderType renderType) {
    this.renderType = renderType;
    revalidate();
    repaint();
  }

  public JComponent getGlassPane() {
    return glassPane;
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
    UIDimension size = new UIDimension();

    getMinimumSize(size);

    return new Dimension(size.intWidth(), size.intHeight());
  }

  @Override
  public void getMinimumSize(UIDimension size) {
    Container container = (Container) Component.fromView(this);
    Component child     = null;

    if (size == null) {
      size = new UIDimension(0, 0);
    }

    if (container.getComponentCount() > 0) {
      child = (Component) container.getComponentAt(0);
      child.getMinimumSize(size);
    }

    iPlatformBorder border = container.getBorder();
    UIInsets        in     = (border != null)
                             ? container.getInsets(insets)
                             : null;

    if (in != null) {
      size.width  += in.left + in.right;
      size.height += in.top + in.bottom;
    }

    in          = padding;
    size.width  += in.left + in.right;
    size.height += in.top + in.bottom;
  }

  @Override
  public Dimension getPreferredSize() {
    UIDimension size = new UIDimension();

    getPreferredSize(size, 0);

    return new Dimension(size.intWidth(), size.intHeight());
  }

  @Override
  public void getPreferredSize(UIDimension size, int maxWidth) {
    Container container = (Container) Component.fromView(this);
    Component child     = null;

    if (size == null) {
      size = new UIDimension(0, 0);
    }

    int len = container.getComponentCount();

    if (len > 0) {
      child = (Component) container.getComponentAt(len - 1);

      if (child.isVisible()) {
        child.getPreferredSize(size, maxWidth);
      }
    }

    iPlatformBorder border = container.getBorder();
    UIInsets        in     = (border != null)
                             ? container.getInsets(insets)
                             : null;

    if (in != null) {
      size.width  += in.left + in.right;
      size.height += in.top + in.bottom;
    }

    in          = padding;
    size.width  += in.left + in.right;
    size.height += in.top + in.bottom;
  }

  public RenderSpace getRenderSpace() {
    return renderSpace;
  }

  @Override
  public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
    return 40;
  }

  public RenderType getViewRenderType() {
    return renderType;
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
  @Override
  public boolean isLocked() {
    return locked;
  }

  @Override
  protected void addImpl(java.awt.Component comp, Object constraints, int index) {
    super.addImpl(comp, constraints, index);

    if ((comp != glassPane) && (glassPane != null)) {
      setComponentZOrder(glassPane, 0);
    }
  }

  @Override
  protected void layoutContainerEx(int width, int height) {
    if (renderType == RenderType.XY) {
      return;
    }

    Container container = (Container) Component.fromView(this);
    int       len       = container.getComponentCount();

    if (len > 0) {
      iPlatformComponent c  = container.getComponentAt(len - 1);
      float              iw = 0;
      float              ih = 0;

      if (renderType != RenderType.STRETCHED) {
        UIDimension d = c.getPreferredSize();

        iw = d.width;
        ih = d.height;
      }

      width  = (int) Math.ceil(width - padding.left - padding.right);
      height = (int) Math.ceil(height - padding.top - padding.bottom);

      UIRectangle rect = aUIPainter.getRenderLocation(container, renderSpace, renderType, padding.left, padding.top,
                           width, height, iw, ih, null);

      for (int i = 0; i < len; i++) {
        c = container.getComponentAt(i);

        if (c.isVisible()) {
          c.setBounds(rect.x, rect.y, rect.width, rect.height);
        }
      }
    }

    if (glassPane != null) {
      glassPane.setBounds(0, 0, width, height);
    }
  }
}
