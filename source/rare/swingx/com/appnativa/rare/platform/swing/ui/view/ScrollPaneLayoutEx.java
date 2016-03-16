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

import com.appnativa.rare.platform.swing.ui.util.SwingHelper;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.border.UIEmptyBorder;
import com.appnativa.rare.ui.iPlatformComponent;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.ScrollPaneLayout;

/**
 * The layout manager used by <code>ScrollPaneEx</code>.
 * <code>ScrollPaneLayout</code> is responsible for the white space before and
 * after scrollbars. This the behavior of the corners correspond the the
 * behavior of their counterparts in ScrollPaneLayout
 */
public class ScrollPaneLayoutEx extends ScrollPaneLayout {

  /**
   * Identifies the area at the bottom where the viewport is between the lower
   * left corner and the lower right corner.
   */
  public static final String RARE_COLUMN_FOOTER = "RARE_COLUMN_FOOTER";

  /**
   * Identifies the area along the right side of the viewport between the upper
   * right corner and the lower right corner.
   */
  public static final String RARE_ROW_FOOTER = "RARE_ROW_FOOTER";
  protected JViewport        columnFooter;
  protected JViewport        rowFooter;
  protected boolean          extendVerticalScrollBar = true;
  protected boolean          extendHorizontalScrollBar;
  private UIEmptyBorder      viewportBorder;

  @Override
  public void addLayoutComponent(String s, Component c) {
    if (s.equals(RARE_ROW_FOOTER)) {
      rowFooter = (JViewport) addSingletonComponent(rowFooter, c);

      if (viewportBorder == null) {
        viewportBorder = new UIEmptyBorder(0);
      }

      ((JScrollPane) c.getParent()).setViewportBorder(viewportBorder);
    } else if (s.equals(RARE_COLUMN_FOOTER)) {
      columnFooter = (JViewport) addSingletonComponent(columnFooter, c);

      if (viewportBorder == null) {
        viewportBorder = new UIEmptyBorder(0);
      }

      ((JScrollPane) c.getParent()).setViewportBorder(viewportBorder);
    } else {
      super.addLayoutComponent(s, c);
    }
  }

  @Override
  public void layoutContainer(Container parent) {
    JViewportEx  vp         = (JViewportEx) getViewport();
    Dimension    sizea[]    = null;
    ScrollPaneEx scrollPane = (ScrollPaneEx) parent;

    if (isColumnFooterVisble() || isRowFooterVisble()) {
      sizea = updateViewPortBorder(scrollPane);
    }

    super.layoutContainer(parent);

    if (scrollPane.hasScrollPaneRowHeader()) {
      ((JViewportEx) rowHead).setScrollPaneRowHeaderBounds();
    }

    if (sizea != null) {
      viewportBorder.setInsets(0, 0, 0, 0);

      Dimension  size;
      Rectangle  r = vp.getBounds();
      JComponent c;
      Dimension  vsize = vp.getViewSize();
      int        hh    = 0;

      if (colHead != null) {
        hh = colHead.getHeight();
      }

      if (isRowFooterVisble()) {
        size = sizea[0];
        c    = (JComponent) rowFooter.getView();

        if (c != null) {
          c.putClientProperty(iPlatformComponent.RARE_SWING_WIDTH_FIXED_VALUE, size.width);
          c.putClientProperty(iPlatformComponent.RARE_SWING_HEIGHT_FIXED_VALUE, (vsize.height == 0)
                  ? null
                  : vsize.height);
        }

        if (colHead != null) {
          colHead.setBounds(colHead.getX(), colHead.getY(), colHead.getWidth() - size.width, hh);

          if (rowFooter.getView() instanceof ScrollPaneEx) {
            r.height += hh;
            r.y      -= hh;
          }
        }

        rowFooter.setBounds(r.x + r.width, r.y, size.width, r.height);
      }

      if (hsb.isVisible()) {
        if (scrollPane.isExtendHorizontalScrollbar()) {
          hsb.setBounds(0, hsb.getY(), hsb.getWidth() + hsb.getX(), hsb.getHeight());
        } else if (rowFooter != null) {
          hsb.setBounds(hsb.getX(), hsb.getY(), hsb.getWidth() - rowFooter.getWidth(), hsb.getHeight());
        }
      }

      if ((colHead != null) && vsb.isVisible() && scrollPane.isExtendVerticalScrollbar()) {
        vsb.setBounds(vsb.getX(), 0, vsb.getWidth(), vsb.getHeight() + vsb.getY());
      }

      if (isColumnFooterVisble()) {
        size = sizea[1];
        c    = (JComponent) columnFooter.getView();
        c.putClientProperty(iPlatformComponent.RARE_SWING_WIDTH_FIXED_VALUE, (vsize.width == 0)
                ? null
                : vsize.width);
        c.putClientProperty(iPlatformComponent.RARE_SWING_HEIGHT_FIXED_VALUE, size.height);

        if (vsize.width > size.width) {
          c.setSize(vsize.width, size.height);
        }

        columnFooter.setBounds(r.x, r.y + r.height, vsize.width, size.height);
      }
    }

    layoutCorners(parent, scrollPane.isExtendVerticalScrollbar(), scrollPane.isExtendHorizontalScrollbar());
  }

  protected Dimension[] updateViewPortBorder(JScrollPane scrollPane) {
    Dimension     csize = null;
    Dimension     rsize = null;
    UIEmptyBorder b     = viewportBorder;

    if (b == null) {
      b = viewportBorder = new UIEmptyBorder(0);
    }

    if (scrollPane.getViewportBorder() != b) {
      scrollPane.setViewportBorder(b);
    }

    int bottom = 0;
    int right  = 0;

    if (isRowFooterVisble()) {
      rsize = rowFooter.getPreferredSize();
      right = Math.min(scrollPane.getWidth(), rsize.width);
    }

    if (isColumnFooterVisble()) {
      csize  = columnFooter.getPreferredSize();
      bottom = Math.min(scrollPane.getWidth(), csize.height);
    }

    b.setInsets(0, right, bottom, 0);

    return new Dimension[] { rsize, csize };
  }

  protected void layoutCorners(Container parent, boolean extendvsb, boolean extendhsb) {
    if (!isColumnFooterVisble() &&!isRowFooterVisble()) {
      return;
    }

    if ((lowerLeft == null) && (lowerRight == null) && (upperRight == null)) {
      return;
    }

    Rectangle bounds = parent.getBounds();

    bounds.x = bounds.y = 0;

    Insets insets = parent.getInsets();

    bounds.x      = insets.left;
    bounds.y      = insets.top;
    bounds.width  -= insets.left + insets.right;
    bounds.height -= insets.top + insets.bottom;

    int sx;
    int ex;
    int sy;
    int h;

    if (isRowFooterVisble()) {
      ex = bounds.width;

      boolean spfooter = rowFooter.getView() instanceof ScrollPaneEx;

      if ((upperRight != null) && (colHead != null)) {
        sx = rowFooter.getX();
        sy = colHead.getY();
        h  = colHead.getHeight();

        if (spfooter) {
          h = 0;
        }

        if ((h > 0) && (ex - sx > 0)) {
          upperRight.setVisible(true);
          upperRight.setBounds(sx, sy, ex - sx, h);
        }
      }

      if (lowerRight != null) {
        sx = rowFooter.getX();
        sy = rowFooter.getY() + rowFooter.getHeight();
        h  = bounds.height - sy;

        if (spfooter) {}

        if ((h > 0) && (ex - sx > 0)) {
          lowerRight.setVisible(true);
          lowerRight.setBounds(sx, sy, ex - sx, bounds.height - sy);

          if ((vsb != null) && vsb.isVisible()) {
            sx = vsb.getX();
            sy = vsb.getY();
            vsb.setBounds(sx, sy, vsb.getWidth(), vsb.getHeight() - h);
          }
        }
      }
    }

    if (isColumnFooterVisble() && (rowHead != null) && (lowerLeft != null)) {
      sx = bounds.x;
      ex = columnFooter.getX();
      sy = columnFooter.getY();
      h  = bounds.height - sy;
      lowerLeft.setVisible(true);
      lowerLeft.setBounds(sx, sy, ex - sx, h);
    }
  }

  @Override
  public Dimension preferredLayoutSize(Container parent) {
    Dimension d = super.preferredLayoutSize(parent);

    if (isColumnFooterVisble() || isRowFooterVisble()) {
      UIDimension size = new UIDimension();

      if (isColumnFooterVisble()) {
        size     = getSize(columnFooter, size, true);
        d.height += size.height;
      }

      if (isRowFooterVisble()) {
        size    = getSize(rowFooter, size, true);
        d.width += size.width;
      }
    }

    return d;
  }

  @Override
  public Dimension minimumLayoutSize(Container parent) {
    Dimension d = super.minimumLayoutSize(parent);

    if (isRowFooterVisble() || isColumnFooterVisble()) {
      UIDimension size = new UIDimension();

      if (isColumnFooterVisble()) {
        size     = getSize(columnFooter, size, true);
        d.height += size.height;
      }

      if (isRowFooterVisble()) {
        size    = getSize(rowFooter, size, true);
        d.width += size.width;
      }
    }

    return d;
  }

  protected boolean isColumnFooterVisble() {
    Component c = (columnFooter == null)
                  ? null
                  : columnFooter.getView();

    return (c == null)
           ? false
           : c.isVisible();
  }

  protected boolean isRowFooterVisble() {
    Component c = (rowFooter == null)
                  ? null
                  : rowFooter.getView();

    return (c == null)
           ? false
           : c.isVisible();
  }

  private UIDimension getSize(Component c, UIDimension size, boolean preferred) {
    iPlatformComponent pc = com.appnativa.rare.ui.Component.fromView((JComponent) c);

    if (pc == null) {
      return SwingHelper.setUIDimension(size, preferred
              ? c.getPreferredSize()
              : c.getMinimumSize());
    }

    return preferred
           ? pc.getPreferredSize(size)
           : pc.getMinimumSize(size);
  }
}
