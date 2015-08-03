/*
 * @(#)JideScrollPaneLayoutEx.java   2010-07-05
 *
 * Copyright (c) 2007-2009 appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.platform.swing.ui.view;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.ScrollPaneLayout;

import com.appnativa.rare.platform.swing.ui.util.SwingHelper;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.border.UIEmptyBorder;

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
  public static final String RARE_COLUMN_FOOTER       = "RARE_COLUMN_FOOTER";

  /**
   * Identifies the area along the right side of the viewport between the upper
   * right corner and the lower right corner.
   */
  public static final String RARE_ROW_FOOTER          = "RARE_ROW_FOOTER";
  public static final String RARE_SCROLLBAR_LL_CORNER = "RARE_SCROLLBAR_LL_CORNER";
  public static final String RARE_SCROLLBAR_LR_CORNER = "RARE_SCROLLBAR_LR_CORNER";
  public static final String RARE_SCROLLBAR_UR_CORNER = "RARE_SCROLLBAR_UR_CORNER";
  protected Component        _llCorner;
  protected Component        _lrCorner;
  protected Component        _urCorner;
  protected JViewport        columnFooter;
  protected JViewport        rowFooter;
  protected boolean          extendVerticalScrollBar=true;
  protected boolean extendHorizontalScrollBar;
  private UIEmptyBorder      viewportBorder;

  @Override
  public void addLayoutComponent(String s, Component c) {
    if (s.equals(RARE_SCROLLBAR_UR_CORNER)) {
      _urCorner = addSingletonComponent(_urCorner, c);
    } else if (s.equals(RARE_SCROLLBAR_LR_CORNER)) {
      _lrCorner = addSingletonComponent(_lrCorner, c);
    } else if (s.equals(RARE_SCROLLBAR_LL_CORNER)) {
      _llCorner = addSingletonComponent(_llCorner, c);
    } else if (s.equals(RARE_ROW_FOOTER)) {
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
    JViewportEx vp = (JViewportEx) getViewport();
    Dimension sizea[] = null;
    ScrollPaneEx scrollPane = (ScrollPaneEx) parent;
    if ((rowFooter != null) || (columnFooter != null)) {
      sizea = updateViewPortBorder(scrollPane);
    }

    super.layoutContainer(parent);
    if (scrollPane.hasScrollPaneRowHeader()) {
      ((JViewportEx) rowHead).setScrollPaneRowHeaderBounds();
    }
    if ((rowFooter != null) || (columnFooter != null)) {
      viewportBorder.setInsets(0, 0, 0, 0);

      Dimension size;
      Rectangle r = vp.getBounds();
      JComponent c;
      Dimension vsize = vp.getViewSize();

      if (rowFooter != null) {
        size = sizea[0];
        if (colHead != null) {
          colHead.setBounds(colHead.getX(), colHead.getY(), colHead.getWidth() - size.width, colHead.getHeight());
          r.y = colHead.getY();
          r.height += colHead.getHeight();
        }
        c = (JComponent) rowFooter.getView();
        if (c != null) {
          c.putClientProperty(iPlatformComponent.RARE_SWING_WIDTH_FIXED_VALUE, size.width);
          c.putClientProperty(iPlatformComponent.RARE_SWING_HEIGHT_FIXED_VALUE, vsize.height == 0 ? null : vsize.height);
        }
        rowFooter.setBounds(r.x + r.width, r.y, size.width, r.height);
      }
      if (hsb.isVisible()) {
        if(scrollPane.isExtendHorizontalScrollbar()) {
          hsb.setBounds(0, hsb.getY(), hsb.getWidth()+hsb.getX(), hsb.getHeight());
        }
        else if(rowFooter!=null) {
          hsb.setBounds(hsb.getX(), hsb.getY(), hsb.getWidth() - rowFooter.getWidth(), hsb.getHeight());
        }
      }
      if(colHead!=null && vsb.isVisible() && scrollPane.isExtendVerticalScrollbar()) {
        vsb.setBounds(vsb.getX(), 0, vsb.getWidth(), vsb.getHeight()+vsb.getY());
      }
      if (columnFooter != null) {
        size = sizea[1];
        c = (JComponent) columnFooter.getView();
        c.putClientProperty(iPlatformComponent.RARE_SWING_WIDTH_FIXED_VALUE, vsize.width == 0 ? null : vsize.width);
        c.putClientProperty(iPlatformComponent.RARE_SWING_HEIGHT_FIXED_VALUE, size.height);
        columnFooter.setBounds(r.x, r.y + r.height, r.width, size.height);
      }
    }
    layoutOtherCorners(parent,scrollPane.isExtendVerticalScrollbar(),scrollPane.isExtendHorizontalScrollbar());
  }

  protected Dimension[] updateViewPortBorder(JScrollPane scrollPane) {
    Dimension csize = null;
    Dimension rsize = null;
    UIEmptyBorder b = viewportBorder;
    if (b == null) {
      b = viewportBorder = new UIEmptyBorder(0);
    }
    if (scrollPane.getViewportBorder() != b) {
      scrollPane.setViewportBorder(b);
    }
    int bottom = 0;
    int right = 0;
    if (rowFooter != null) {
      rsize = rowFooter.getPreferredSize();
      right = Math.min(scrollPane.getWidth(), rsize.width);
    }
    if (columnFooter != null) {
      rsize = columnFooter.getPreferredSize();
      bottom = Math.min(scrollPane.getWidth(), rsize.height);
    }
    b.setInsets(0, right, bottom, 0);
    return new Dimension[] { rsize, csize };
  }

  protected void layoutOtherCorners(Container parent, boolean extendvsb,boolean extendhsb) {

    if ((_lrCorner == null) && (_urCorner == null) && (_llCorner == null)) {
      return;
    }

    if (!vsb.isVisible() && !hsb.isVisible()) {
      if (_llCorner != null) {
        _llCorner.setVisible(false);
      }

      if (_lrCorner != null) {
        _lrCorner.setVisible(false);
      }

      if (_urCorner != null) {
        _urCorner.setVisible(false);
      }

      return;
    }

    JScrollPane scrollPane = (JScrollPane) parent;
    Rectangle vsbR = vsb.getBounds();
    Rectangle hsbR = hsb.getBounds();
    int rx = (rowHead != null) ? rowHead.getX() : 0;
    int cy = (colHead == null) ? 0 : colHead.getY();
    int rw = (rowHead != null) ? rowHead.getWidth() : 0;
    int ch = (colHead == null) ? 0 : colHead.getHeight();
    boolean leftToRight = scrollPane.getComponentOrientation().isLeftToRight();
    if (!extendvsb && (_urCorner != null) && vsb.isVisible()) {
      _urCorner.setVisible(true);
      _urCorner.setBounds(leftToRight ? vsbR.x : rx, cy, leftToRight ? vsbR.width : rw, ch);
    } else {
      if (_urCorner != null) {
        _urCorner.setVisible(false);
      }
    }

    if (hsb.isVisible()) {
      if (_llCorner != null) {
        _llCorner.setVisible(rowHead != null);
        _llCorner.setBounds(leftToRight ? rx : vsbR.x, hsbR.y, leftToRight ? rw : vsbR.width, hsbR.height);
      }

      if (!extendhsb && _lrCorner != null) {
        _lrCorner.setVisible(rowFooter != null || vsb.isVisible());
        int sx = hsbR.x + hsbR.width;
        int ex = rowFooter != null ? (rowFooter.getX() + rowFooter.getWidth()) : (vsbR.x + vsbR.width);
        if (ex - sx > 1) {
          _lrCorner.setBounds(sx, hsbR.y, ex - sx, hsbR.height);
        }
      }
    } else {
      if (_llCorner != null) {
        _llCorner.setVisible(false);
      }

      if (_lrCorner != null) {
        _lrCorner.setVisible(false);
      }
    }
  }

  @Override
  public Dimension preferredLayoutSize(Container parent) {
    Dimension d = super.preferredLayoutSize(parent);

    if ((rowFooter != null) || (columnFooter != null)) {
      UIDimension size = new UIDimension();

      if (columnFooter != null) {
        size = getSize(columnFooter, size, true);
        d.height += size.height;
      }

      if (rowFooter != null) {
        size = getSize(rowFooter, size, true);
        d.width += size.width;
      }
    }

    return d;
  }

  @Override
  public Dimension minimumLayoutSize(Container parent) {
    Dimension d = super.minimumLayoutSize(parent);
    if ((rowFooter != null) || (columnFooter != null)) {
      UIDimension size = new UIDimension();

      if (columnFooter != null) {
        size = getSize(columnFooter, size, true);
        d.height += size.height;
      }

      if (rowFooter != null) {
        size = getSize(rowFooter, size, true);
        d.width += size.width;
      }
    }

    return d;
  }

  /**
   * @return the _llCorner
   */
  public Component getLLCorner() {
    return _llCorner;
  }

  /**
   * @return the _lrCorner
   */
  public Component getLRCorner() {
    return _lrCorner;
  }

  /**
   * @return the _urCorner
   */
  public Component getURCorner() {
    return _urCorner;
  }

  private UIDimension getSize(Component c, UIDimension size, boolean preferred) {
    iPlatformComponent pc = com.appnativa.rare.ui.Component.fromView((JComponent) c);

    if (pc == null) {
      return SwingHelper.setUIDimension(size, preferred ? c.getPreferredSize() : c.getMinimumSize());
    }

    return preferred ? pc.getPreferredSize(size) : pc.getMinimumSize(size);
  }
}
