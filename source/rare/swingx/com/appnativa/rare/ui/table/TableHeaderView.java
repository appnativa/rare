/*
 * @(#)TableHeaderEx.java   2010-07-03
 *
 * Copyright (c) 2007-2009 appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.ui.table;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;

import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.appnativa.rare.platform.swing.ui.util.SwingGraphics;
import com.appnativa.rare.platform.swing.ui.util.SwingHelper;
import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.painter.iPainter;
import com.appnativa.rare.ui.painter.iPainterSupport;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;

/**
 *
 * @author Don DeCoteau
 */
public class TableHeaderView extends JTableHeader implements iPainterSupport {
  boolean                           inSizeCall;
  TableColumn                       wasResizingColumn;
  protected SwingGraphics           graphics;
  private UIColor                   bottomMarginColor;
  private boolean                   columnResized;
  private iPlatformComponentPainter componentPainter;
  private UIColor                   marginColor;

  /**
   * Creates a new instance of TableHeaderEx
   *
   *
   * @param model the column model
   * @param th the table header
   * @param gp the gradient painter
   */
  public TableHeaderView(TableColumnModel model) {
    super(model);
    setBackground(ColorUtils.getBackground());
  }

  @Override
  public void columnMarginChanged(ChangeEvent e) {
    super.columnMarginChanged(e);

    if (resizingColumn != null) {
      wasResizingColumn = resizingColumn;
    }
  }

  @Override
  public void paint(Graphics g) {
    graphics = SwingGraphics.fromGraphics(g, this, graphics);
    super.paint(g);
    int w = getWidth();
    int h = getHeight();

    if (bottomMarginColor != null) {
      Graphics2D g2 = (Graphics2D) g;
      Color      oc = g2.getColor();
      Stroke     s  = g2.getStroke();

      g2.setStroke(SwingHelper.SOLID_STROKE);


      g2.setColor(bottomMarginColor);
      g2.drawLine(0, h - 1, w - 1, h - 1);
      g2.setStroke(s);
      g2.setColor(oc);
    }

    if (marginColor != null) {
      TableColumnModel cm  = getColumnModel();
      int              len = cm.getColumnCount();
      boolean first=true;
      g.setColor(marginColor);

      for (int i = 0; i < len; i++) {
        Rectangle r = getHeaderRect(i);

        if (r.width > 0) {
          if(first) {
            first=false;
          }
          else {
            g.drawLine(r.x-1, 0, r.x-1, h-2);
          }
        }
      }
      TableView table=(TableView) getTable();
      if(table.header.paintRightMargin) {
        g.drawLine(w-1, 0, w - 1, h - 2);
      }
      if(table.header.paintLeftMargin) {
        g.drawLine(0, 0, 0, h - 2);
      }
    }

    graphics.clear();
  }

  public boolean wasColumnResized() {
    return columnResized;
  }

  public void setBottomMarginColor(UIColor bottomMarginColor) {
    this.bottomMarginColor = bottomMarginColor;
  }

  @Override
  public void setComponentPainter(iPlatformComponentPainter cp) {
    this.componentPainter = cp;
  }

  public void setMarginColor(UIColor marginColor) {
    this.marginColor = marginColor;
  }

  public UIColor getBottomMarginColor() {
    return bottomMarginColor;
  }

  @Override
  public iPlatformComponentPainter getComponentPainter() {
    return componentPainter;
  }

  public UIColor getMarginColor() {
    return marginColor;
  }

  @Override
  public Dimension getMinimumSize() {
    if (!inSizeCall) {
      inSizeCall = true;

      try {
        JTable t = getTable();

        if (t instanceof TableView) {
          return ((TableView) t).getHeaderMinimumSize();
        }
      } finally {
        inSizeCall = false;
      }
    }

    return super.getMinimumSize();
  }

  @Override
  public Dimension getPreferredSize() {
    if (!inSizeCall && isVisible()) {
      inSizeCall = true;

      try {
        JTable t = getTable();

        if (t instanceof TableView) {
          return ((TableView) t).getHeaderPreferredSize();
        }
      } finally {
        inSizeCall = false;
      }
    }

    return super.getPreferredSize();
  }

  @Override
  public boolean isOpaque() {
    return false;
  }

  @Override
  protected void paintBorder(Graphics g) {}

  @Override
  protected void paintChildren(Graphics g) {
    super.paintChildren(graphics.getGraphics());

    iPlatformComponentPainter cp = getComponentPainter();

    if (cp != null) {
      float height = getHeight();
      float width  = getWidth();

      cp.paint(graphics, 0, 0, width, height, iPainter.HORIZONTAL, true);
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
}
