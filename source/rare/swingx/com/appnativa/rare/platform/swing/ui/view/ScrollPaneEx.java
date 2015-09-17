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
import com.appnativa.rare.platform.swing.ui.util.SwingGraphics;
import com.appnativa.rare.platform.swing.ui.util.SwingHelper;
import com.appnativa.rare.ui.BorderUtils;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIPoint;
import com.appnativa.rare.ui.iScrollerSupport;
import com.appnativa.rare.ui.painter.UIScrollingEdgePainter;
import com.appnativa.rare.ui.painter.iPainter;
import com.appnativa.rare.ui.painter.iPainterSupport;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.AffineTransform;

import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

/**
 *
 * @author Don DeCoteau
 */
public class ScrollPaneEx extends JScrollPane implements iPainterSupport, iView, iScrollerSupport {
  protected SwingGraphics             graphics;
  protected int                       preferredSizemaxWidth;
  protected boolean                   adjustPrefSizeForHiddenHoriz = true;
  protected boolean                   adjustPrefSizeForHiddenVert  = true;
  protected JViewportEx               columnFooter;
  protected iPlatformComponentPainter componentPainter;
  protected Color                     disabledColor;
  protected boolean                   extendHorizontalScrollbar;
  protected boolean                   extendVerticalScrollbar;
  protected JViewportEx               rowFooter;
  protected boolean                   shapedBorder;
  private UIScrollingEdgePainter      scrollingEdgePainter = null;    //UIScrollingEdgePainter.getInstance();

  /**
   * Creates a new instance of ScrollPaneEx
   */
  public ScrollPaneEx() {
    this(null, VERTICAL_SCROLLBAR_AS_NEEDED, HORIZONTAL_SCROLLBAR_AS_NEEDED);
  }

  public ScrollPaneEx(int vsbPolicy, int hsbPolicy) {
    this(null, vsbPolicy, hsbPolicy);
  }

  public ScrollPaneEx(java.awt.Component view) {
    this(view, VERTICAL_SCROLLBAR_AS_NEEDED, HORIZONTAL_SCROLLBAR_AS_NEEDED);
  }

  public ScrollPaneEx(java.awt.Component view, int vsbPolicy, int hsbPolicy) {
    super(view, vsbPolicy, hsbPolicy);
    setLayout(new ScrollPaneLayoutEx());
    setViewportBorder(BorderUtils.EMPTY_BORDER);
    SwingHelper.configureScrollPaneCornerFromUIProperty(this, Platform.getUIDefaults().get("Rare.ScrollPane.urCorner"),
            ScrollPaneLayoutEx.RARE_SCROLLBAR_UR_CORNER);
    SwingHelper.configureScrollPaneCornerFromUIProperty(this, Platform.getUIDefaults().get("Rare.ScrollPane.lrCorner"),
            ScrollPaneLayoutEx.RARE_SCROLLBAR_LR_CORNER);
    setBorder(BorderUtils.EMPTY_BORDER);
  }

  public void addSlaveViewport(JViewportEx vp, boolean horizontal) {
    vp.linkToViewPort(getViewport(), horizontal);
  }

  @Override
  protected JViewport createViewport() {
    return new JViewportEx();
  }

  public void disableScrolling() {
    setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_NEVER);
    setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
  }

  @Override
  public void doLayout() {
    JViewportEx hvp = getRowHeaderColumnHeader();
    JViewportEx fvp = getRowFooterColumnHeader();
    ;

    if (((hvp != null) || (fvp != null)) && (columnHeader != null)) {
      int h = 0;

      if (hvp != null) {
        h = Math.max(hvp.getPreferredSize().height, h);
      }

      if (fvp != null) {
        h = Math.max(fvp.getPreferredSize().height, h);
      }

      h = Math.max(columnHeader.getPreferredSize().height, h);
      ((JViewportEx) columnHeader).setPreferredHeight(h);

      if (hvp != null) {
        hvp.setPreferredHeight(h);
      }

      if (fvp != null) {
        fvp.setPreferredHeight(h);
      }
    }

    super.doLayout();
  }

  @Override
  public Color getBackground() {
    if ((getDisabledColor() == null) || isEnabled()) {
      return super.getBackground();
    }

    return getDisabledColor();
  }

  public JViewport getColumnFooter() {
    return columnFooter;
  }

  @Override
  public iPlatformComponentPainter getComponentPainter() {
    return componentPainter;
  }

  @Override
  public UIPoint getContentOffset() {
    Point p = getViewport().getViewPosition();

    return new UIPoint(p.x, p.y);
  }

  @Override
  public java.awt.Component getCorner(String key) {
    if (key.startsWith("HORIZONTAL") || key.startsWith("VERTICAL")) {
      return getCorner(key);
    } else if (!key.startsWith("RARE_") ||!(getLayout() instanceof ScrollPaneLayoutEx)) {
      return super.getCorner(key);
    }

    ScrollPaneLayoutEx l = (ScrollPaneLayoutEx) getLayout();

    if (key.equals(ScrollPaneLayoutEx.RARE_SCROLLBAR_UR_CORNER)) {
      return l._urCorner;
    } else if (key.equals(ScrollPaneLayoutEx.RARE_SCROLLBAR_LR_CORNER)) {
      return l._lrCorner;
    } else if (key.equals(ScrollPaneLayoutEx.RARE_SCROLLBAR_LL_CORNER)) {
      return l._llCorner;
    }

    return null;
  }

  /**
   * @return the disabledColor
   */
  public Color getDisabledColor() {
    return disabledColor;
  }

  @Override
  public void getMinimumSize(UIDimension size) {
    Dimension d = getMinimumSize();

    size.width  = d.width;
    size.height = d.height;
  }

  @Override
  public Dimension getPreferredSize() {
    Dimension          d  = super.getPreferredSize();
    java.awt.Component v  = getViewport().getView();
    Dimension          vd = null;

    if (v != null) {
      if (isAdjustPrefSizeForHiddenHoriz() && (getHorizontalScrollBarPolicy() == HORIZONTAL_SCROLLBAR_NEVER)) {
        vd = v.getPreferredSize();

        if (d.width < vd.width) {
          d.width = vd.width;
        }
      }

      if (isAdjustPrefSizeForHiddenVert() && (getVerticalScrollBarPolicy() == VERTICAL_SCROLLBAR_NEVER)) {
        if (vd == null) {
          vd = v.getPreferredSize();
        }

        if (d.height < vd.height) {
          d.height = vd.height;
        }
      }
    }

    return d;
  }

  @Override
  public void getPreferredSize(UIDimension size, int maxWidth) {
    preferredSizemaxWidth = maxWidth;

    Dimension d = getPreferredSize();

    size.width            = d.width;
    size.height           = d.height;
    preferredSizemaxWidth = 0;
  }

  public JViewport getRowFooter() {
    return rowFooter;
  }

  protected JViewportEx getRowFooterColumnHeader() {
    Component c = (rowFooter == null)
                  ? null
                  : rowFooter.getView();

    if (c instanceof ScrollPaneEx) {
      return (JViewportEx) ((ScrollPaneEx) c).getColumnHeader();
    }

    return null;
  }

  protected JViewportEx getRowHeaderColumnHeader() {
    Component c = (rowHeader == null)
                  ? null
                  : rowHeader.getView();

    if (c instanceof ScrollPaneEx) {
      return (JViewportEx) ((ScrollPaneEx) c).getColumnHeader();
    }

    return null;
  }

  @Override
  public AffineTransform getTransformEx() {
    return null;
  }

  public boolean hasScrollPaneRowFooter() {
    return ((rowFooter != null) && (rowFooter.getView() instanceof ScrollPaneEx));
  }

  public boolean hasScrollPaneRowHeader() {
    return ((rowHeader != null) && (rowHeader.getView() instanceof ScrollPaneEx));
  }

  public boolean hasValue() {
    return getViewport().getView() != null;
  }

  @Override
  public boolean imageUpdate(Image img, int infoflags, int x, int y, int w, int h) {
    if (!SwingHelper.isContinueImageUpdate(this, img)) {
      return false;
    }

    return super.imageUpdate(img, infoflags, x, y, w, h);
  }

  public boolean isAdjustPrefSizeForHiddenHoriz() {
    return adjustPrefSizeForHiddenHoriz;
  }

  public boolean isAdjustPrefSizeForHiddenVert() {
    return adjustPrefSizeForHiddenVert;
  }

  @Override
  public boolean isAtBottomEdge() {
    int y = getViewport().getViewPosition().y;

    return getViewport().getViewSize().height - y <= getViewport().getExtentSize().height;
  }

  @Override
  public boolean isAtLeftEdge() {
    return getViewport().getViewPosition().x == 0;
  }

  @Override
  public boolean isAtRightEdge() {
    int x = getViewport().getViewPosition().x;

    return getViewport().getViewSize().width - x <= getViewport().getExtentSize().width;
  }

  @Override
  public boolean isAtTopEdge() {
    return getViewport().getViewPosition().y == 0;
  }

  public boolean isExtendHorizontalScrollbar() {
    return extendHorizontalScrollbar;
  }

  public boolean isExtendVerticalScrollbar() {
    return extendVerticalScrollbar;
  }

  @Override
  public void scrollToLeftEdge() {
    JViewport vp = getViewport();
    Point     p  = vp.getViewPosition();

    if (p.x != 0) {
      p.x = 0;
      vp.setViewPosition(p);
    }
  }

  @Override
  public void scrollToRightEdge() {
    JViewport vp = getViewport();
    Point     p  = vp.getViewPosition();
    int       x  = Math.max(vp.getViewSize().width - vp.getExtentSize().width, 0);

    if (p.x != x) {
      p.x = x;
      vp.setViewPosition(p);
    }
  }

  @Override
  public void scrollToTopEdge() {
    JViewport vp = getViewport();
    Point     p  = vp.getViewPosition();

    if (p.y != 0) {
      p.y = 0;
      vp.setViewPosition(p);
    }
  }

  @Override
  public void scrollToBottomEdge() {
    JViewport vp = getViewport();
    Point     p  = vp.getViewPosition();
    int       y  = Math.max(vp.getViewSize().height - vp.getExtentSize().height, 0);

    if (p.y != y) {
      p.y = y;
      vp.setViewPosition(p);
    }
  }

  @Override
  public boolean isOpaque() {
    if ((componentPainter != null) && componentPainter.isBackgroundPaintEnabled()) {
      return false;
    }

    return !shapedBorder && super.isOpaque();
  }

  @Override
  public boolean isScrolling() {
    return getVerticalScrollBar().getValueIsAdjusting() || getHorizontalScrollBar().getValueIsAdjusting();
  }

  @Override
  public void paint(Graphics g) {
    graphics = SwingGraphics.fromGraphics(g, this, graphics);
    super.paint(g);
    graphics.clear();
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
      float width  = getWidth();

      cp.paint(graphics, 0, 0, width, height, iPainter.HORIZONTAL, true);
    }

    if (scrollingEdgePainter != null) {
      JViewport vp = getViewport();

      scrollingEdgePainter.paint(graphics, vp.getX(), vp.getY(), vp.getWidth(), vp.getHeight(), true);
      scrollingEdgePainter.paint(graphics, vp.getX(), 0, vp.getWidth(), vp.getY() + vp.getHeight(), false);
    }
  }

  @Override
  protected void paintComponent(Graphics g) {
    iPlatformComponentPainter cp = getComponentPainter();

    if (cp != null) {
      float height = getHeight();
      float width  = getWidth();

      cp.paint(graphics, 0, 0, width, height, iPainter.HORIZONTAL, false);
    }

    super.paintComponent(g);
  }

  @Override
  public boolean requestFocusInWindow() {
    JViewport          v = this.getViewport();
    java.awt.Component c = (v == null)
                           ? null
                           : v.getView();

    if (c != null) {
      return c.requestFocusInWindow();
    } else {
      return super.requestFocusInWindow();
    }
  }

  public void setAdjustPrefSizeForHiddenHoriz(boolean adjust) {
    this.adjustPrefSizeForHiddenHoriz = adjust;
  }

  public void setAdjustPrefSizeForHiddenVert(boolean adjust) {
    this.adjustPrefSizeForHiddenVert = adjust;
  }

  public void setColumnFooterView(Component view) {
    if (columnFooter == null) {
      columnFooter = new JViewportEx();
      add(columnFooter, ScrollPaneLayoutEx.RARE_COLUMN_FOOTER);
    }

    columnFooter.setView(view);
  }

  @Override
  public void setComponentPainter(iPlatformComponentPainter cp) {
    this.componentPainter = cp;
  }

  @Override
  public void setCorner(String key, java.awt.Component corner) {
    if (key.startsWith("HORIZONTAL") || key.startsWith("VERTICAL")) {
      super.setCorner(key, corner);

      return;
    }

    if (!(getLayout() instanceof ScrollPaneLayoutEx)) {
      super.setCorner(key, corner);

      return;
    }

    ScrollPaneLayoutEx l     = (ScrollPaneLayoutEx) getLayout();
    java.awt.Component old   = null;
    boolean            found = false;

    if (key.equals(ScrollPaneLayoutEx.RARE_SCROLLBAR_UR_CORNER)) {
      old   = l._urCorner;
      found = true;
    } else if (key.equals(ScrollPaneLayoutEx.RARE_SCROLLBAR_LR_CORNER)) {
      old   = l._lrCorner;
      found = true;
    } else if (key.equals(ScrollPaneLayoutEx.RARE_SCROLLBAR_LL_CORNER)) {
      old   = l._llCorner;
      found = true;
    }

    if (!found) {
      super.setCorner(key, corner);

      return;
    }

    if (old != null) {
      remove(old);
    }

    if (corner != null) {
      add(corner, key);
    }

    firePropertyChange(key, old, corner);
    revalidate();
    repaint();
  }

  /**
   * @param disabledColor
   *          the disabledColor to set
   */
  public void setDisabledColor(Color disabledColor) {
    this.disabledColor = disabledColor;
  }

  public void setExtendHorizontalScrollbar(boolean extendHorizontalScrollbar) {
    this.extendHorizontalScrollbar = extendHorizontalScrollbar;
  }

  public void setExtendVerticalScrollbar(boolean extendVerticalScrollbar) {
    this.extendVerticalScrollbar = extendVerticalScrollbar;
  }

  @Override
  public void setFont(Font f) {
    super.setFont(f);

    JComponent c = getViewport();

    if (c != null) {
      c.setFont(f);
    }
  }

  @Override
  public void setForeground(Color fg) {
    super.setForeground(fg);
    getViewport().setForeground(fg);

    JComponent c = getViewport();

    if (c != null) {
      c.setForeground(fg);
    }
  }

  public void setRowFooterView(Component view) {
    if (rowFooter == null) {
      rowFooter = new JViewportEx();
      add(rowFooter, ScrollPaneLayoutEx.RARE_ROW_FOOTER);
    }

    rowFooter.setView(view);
  }

  @Override
  public void setTransformEx(AffineTransform tx) {}

  @Override
  public void setScrollingEdgePainter(UIScrollingEdgePainter painter) {
    scrollingEdgePainter = painter;
  }

  @Override
  public UIScrollingEdgePainter getScrollingEdgePainter() {
    return scrollingEdgePainter;
  }

  @Override
  public void moveUpDown(boolean up, boolean block) {
    move(getVerticalScrollBar(), up, block);
  }

  protected void move(JScrollBar sb, boolean up, boolean block) {
    int d;

    if (block) {
      d = sb.getBlockIncrement(up
                               ? 1
                               : -1);
    } else {
      d = sb.getUnitIncrement(up
                              ? 1
                              : -1);
    }

    if (!up) {
      d = -d;
    }

    int n = sb.getValue() + d;

    sb.setValue(n);
  }

  @Override
  public void moveLeftRight(boolean left, boolean block) {
    move(getHorizontalScrollBar(), left, block);
  }
}
