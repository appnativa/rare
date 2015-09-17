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

package com.appnativa.rare.ui.table;

import com.appnativa.rare.platform.swing.ui.util.SwingGraphics;
import com.appnativa.rare.platform.swing.ui.util.SwingHelper;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.iTableModel;
import com.appnativa.rare.ui.painter.PaintBucket;
import com.appnativa.rare.ui.painter.iPainter;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Don DeCoteau
 */
public class TablePainter {
  SwingGraphics            graphics;
  protected boolean        extendBackground = false;
  protected Stroke         lineStroke;
  protected TableComponent tableComponent;
  protected TableStyle     tableStyle;
  private boolean          changeSelColorOnLostFocus = true;
  private TableInformation tableInformation;

  /**
   * Creates a new instance of TableViewPort
   *
   * @param th
   *          the table handler
   */
  public TablePainter(TableComponent tableComponent) {
    lineStroke          = SwingHelper.HALF_SOLID_STROKE;
    tableInformation    = new TableInformation();
    tableStyle          = tableComponent.tableStyle;
    this.tableComponent = tableComponent;
    extendBackground    = tableStyle.extendBackgroundRendering;
  }

  public void paintAfterComponent(TableView table, Graphics2D g) {
    paintGrid(table, g);
  }

  public void paintBeforeComponent(TableView table, Graphics2D g) {
    if (!extendBackground && (table.getRowCount() == 0)) {
      return;
    }

    setTableInformation(tableInformation, g, table);

    PaintBucket pb =
      table.getItemRenderer().getSelectionPaintForExternalPainter(tableComponent.getWidget().isFocusOwner());
    iPlatformComponentPainter cp         = (pb == null)
            ? null
            : pb.getCachedComponentPainter();
    TableInformation          ti         = tableInformation;
    int                       viewWidth  = table.getWidth();
    int                       viewHeight = table.getHeight();
    int                       rMax       = ti.rowMax;
    int                       rMin       = ti.rowMin;
    int                       y          = ti.damagedArea.y;
    int                       h;
    int                       pw = tableStyle.showVerticalLines
                                   ? 1
                                   : 0;
    int                       ph = tableStyle.showHorizontalLines
                                   ? 1
                                   : 0;
    //int lead = (cp == null) ? -1 : table.getSelectionModel().getLeadSelectionIndex();
    iTableModel      tm    = tableComponent.getTableModel();
    int              x     = 0;
    TableColumnModel cm    = table.getColumnModel();
    int              start = tableComponent.getTableHeader().getSelectionPaintStartColumn();
    int              end   = tableComponent.getTableHeader().getSelectionPaintEndColumn();
    int              sx    = x;
    int              ex    = viewWidth - x;

    if ((start != 0) && (cm.getColumn(start).getModelIndex() == start)) {
      sx = table.getCellRect(rMin, start, false).x - cm.getColumnMargin();
    }

    if ((end != cm.getColumnCount() - 1) && (cm.getColumn(end).getModelIndex() == end)) {
      Rectangle r = table.getCellRect(rMin, end, true);

      ex = r.x + r.width;
    }

    UIColor hiliteColor = null;

    if (tableStyle.backgroundHilite == TableStyle.BackgroundHighlight.ROW) {
      hiliteColor = tableStyle.backgroundHiliteColor;
    } else if (tableStyle.backgroundHilite == TableStyle.BackgroundHighlight.COLUMN) {
      paintAlternatingColumnColor(ti, g, viewWidth, viewHeight, tableStyle.backgroundHiliteColor);
    }

    if (tableStyle.hiliteSortColumn && (tableStyle.sortColumnHiliteColor != null)) {
      int sort = tableComponent.getSelectedColumn();

      if (sort > -1) {
        sort = table.convertColumnIndexToView(sort);
      }

      if (sort > -1) {
        paintColumnHilite(g, table, sort, tableStyle.sortColumnHiliteColor);
      }
    }

    for (int row = rMin; row <= rMax; row++) {
      h = table.getRowHeight(row);

      if ((cp != null) && table.isRowSelected(row)) {
        cp.paint(table, g, sx, y, ex - sx, h - ph, true, iPainter.UNKNOWN);
      } else {
        if ((hiliteColor != null) && (row % 2 == 1)) {
          g.setColor(hiliteColor);
          g.fillRect(x, y, viewWidth, h);
        }

        RenderableDataItem item = tm.getRow(row);
        Border             b    = item.getBorder();

        if (b != null) {
          b.paintBorder(table, g, 0, y, viewWidth - pw, h - ph);
        }
      }

      y += h;
    }

    if (extendBackground && (y + 1 < viewHeight)) {
      paintEmptyCells(table, ti, g, viewWidth, viewHeight, hiliteColor);
    }
  }

  protected void paintBorder(Graphics g, Border b, int row) {
    TableInformation ti        = tableInformation;
    JTable           table     = ti.theTable;
    int              viewWidth = table.getWidth();
    int              rMax      = ti.rowMax;
    int              rMin      = ti.rowMin;
    int              pw        = tableStyle.showVerticalLines
                                 ? 1
                                 : 0;
    int              ph        = tableStyle.showHorizontalLines
                                 ? 1
                                 : 0;

    if ((row >= rMin) && (row <= rMax)) {
      Rectangle rect = table.getCellRect(row, 0, true);

      b.paintBorder(table, g, rect.x, rect.y, viewWidth - pw, rect.height - ph);
    }
  }

  protected void paintColumnHilite(Graphics g, JTable table, int col, Color color) {
    int row = table.getRowCount();

    if (row > 0) {
      Graphics2D g2d    = (Graphics2D) g;
      Rectangle  clip   = g.getClipBounds();
      Color      ocolor = g2d.getColor();

      // the color should have the tranparency set
      g2d.setColor(color);

      try {
        int       h    = clip.height;
        Rectangle rect = table.getTableHeader().getHeaderRect(col);

        if (!extendBackground) {
          Rectangle r = table.getCellRect(row - 1, col, true);

          h = (r.height + r.y) - clip.y;
        }

        g2d.fillRect(rect.x, clip.y, rect.width, h);
      } finally {
        g2d.setColor(ocolor);
      }
    }
  }

  protected void paintGrid(TableView table, Graphics2D g) {
    Dimension size = table.getParent().getSize();

    if (table.getRowCount() == 0) {
      if (extendBackground) {
        paintEmptyTableGrid(table, g, tableStyle.showHorizontalLines, tableStyle.showVerticalLines, size,
                            tableInformation);
      }
    } else {
      paintGrid(tableInformation, g, size);
    }
  }

  protected void paintGrid(TableInformation ti, Graphics2D g, Dimension size) {
    Color     ocolor     = g.getColor();
    Stroke    ostroke    = g.getStroke();
    int       viewWidth  = size.width;
    int       viewHeight = size.height;
    TableView table      = (TableView) ti.theTable;
    int       endy       = ti.damagedArea.y + ti.damagedArea.height;
    int       endx       = ti.damagedArea.x + ti.damagedArea.width;

    if (extendBackground) {
      if (ti.damagedArea.y + viewHeight > endy) {
        endy = ti.damagedArea.y + viewHeight;
      }

      if (ti.damagedArea.x + viewWidth > endx) {
        endx = ti.damagedArea.y + viewWidth;
      }
    }

    g.setColor(tableStyle.gridColor);

    if (lineStroke != null) {
      g.setStroke(lineStroke);
    }

    try {
      int              rowHeight  = table.getRowHeight();
      int              tableWidth = table.getWidth();
      int              x          = ti.damagedArea.x;
      int              rMax       = ti.rowMax;
      int              rMin       = ti.rowMin;
      int              cMin       = ti.columnMin;
      int              cMax       = ti.columnMax;
      int              y          = ti.damagedArea.y;
      boolean          showv      = tableStyle.showVerticalLines;
      boolean          showh      = tableStyle.showHorizontalLines;
      Rectangle        r;
      JTableHeader     th       = table.getTableHeader();
      TableColumnModel cm       = th.getColumnModel();
      iTableModel      tm       = (iTableModel) table.getModel();
      int              colCount = cm.getColumnCount();

      cMax++;

      if (cMax >= colCount) {
        cMax--;
      }

      for (int row = rMin; row <= rMax; row++) {
        int rh = table.getRowHeight(row);

        if (showv &&!table.isRowSelected(row)) {
          RenderableDataItem rowItem = tm.get(row);
          int                vx      = 0;
          int                i       = 0;

          if (rowItem.getColumnSpan() != -1) {
            if (table.header.paintRightMargin) {
              g.drawLine(tableWidth - 1, y, tableWidth - 1, y + rh - 1);
            }

            if (table.header.paintLeftMargin) {
              g.drawLine(0, y, 0, y + rh - 1);
            }
          }

          while(i < cMax) {
            RenderableDataItem item = rowItem.getItemEx(i);
            int                span = (item == null)
                                      ? 1
                                      : item.getColumnSpan();

            if (span < 0) {
              break;
            }

            if (span == 0) {
              span = 1;
            }

            if (span == 1) {
              vx += cm.getColumn(i).getWidth();
            } else {
              for (int n = 0; n < span; n++) {
                vx += cm.getColumn(i + n).getWidth();
              }
            }

            i += span;

            if ((i < cMin) || (vx - 1 < x)) {
              continue;
            }

            g.drawLine(vx - 1, y, vx - 1, y + rh - 1);
          }
        }

        if (showh) {
          y += rh;
          g.drawLine(x, y - 1, endx - 1, y - 1);
        }
      }

      y = ti.displayHeight;

      if (extendBackground && (y < viewHeight)) {
        if (showv) {
          if (table.header.paintRightMargin) {
            g.drawLine(tableWidth - 1, y, tableWidth - 1, viewHeight);
          }

          if (table.header.paintLeftMargin) {
            g.drawLine(0, y, 0, viewHeight);
          }

          for (int col = cMin; col <= cMax; col++) {
            r = th.getHeaderRect(col);

            if ((r.x != 0) && (r.width != 0)) {
              g.drawLine(r.x - 1, y, r.x - 1, viewHeight);
            }
          }
        }

        if (showh) {
          while(y < endy) {
            y += rowHeight;
            g.drawLine(x, y - 1, endx - 1, y - 1);
          }
        }
      }
    } finally {
      g.setColor(ocolor);
      g.setStroke(ostroke);
    }
  }

  public void paintResizeLine(Component source, Graphics g, Dimension size, int x, int y, boolean vertical) {
    Graphics2D g2d        = (Graphics2D) g;
    Color      ocolor     = g2d.getColor();
    int        viewWidth  = size.width;
    int        viewHeight = size.height;

    g2d.setXORMode(source.getBackground());
    g2d.setColor(source.getForeground());

    try {
      if (vertical) {
        g.drawLine(x, y, x, viewHeight);
      } else {
        g.drawLine(x, y, viewWidth, y);
      }
    } finally {
      g2d.setColor(ocolor);
      g2d.setPaintMode();
    }
  }

  public void setExtendBackground(boolean extendBackground) {
    this.extendBackground = extendBackground;
  }

  public void setLineStroke(Stroke lineStroke) {
    if (lineStroke == null) {
      lineStroke = SwingHelper.HALF_SOLID_STROKE;
    }

    this.lineStroke = lineStroke;
  }

  public void setTableInformation(TableInformation ti, Graphics g, JTable table) {
    Rectangle clip     = g.getClipBounds();
    Rectangle bounds   = table.getBounds();
    int       rowCount = table.getRowCount();

    if (rowCount == 0) {
      ti.displayHeight = 0;
    } else {
      Rectangle rect = table.getCellRect(rowCount - 1, 0, true);

      ti.displayHeight = rect.y + rect.height;
    }

    // account for the fact that the graphics has already been translated
    // into the table's bounds
    bounds.x = bounds.y = 0;

    if ((clip == null) ||!bounds.intersects(clip)
        || (((rowCount <= 0) || (table.getColumnCount() <= 0)) &&!extendBackground))
    // this check prevents us from painting the entire table
    // when the clip doesn't intersect our bounds at all
    {
      bounds.y       = bounds.height;
      ti.theTable    = table;
      ti.rowMin      = table.getRowCount();
      ti.rowMax      = ti.rowMin - 1;
      ti.columnMin   = table.getColumnCount();
      ti.columnMax   = ti.columnMin - 1;
      ti.damagedArea = bounds;

      return;
    }

    Point upperLeft  = clip.getLocation();
    Point lowerRight = new Point(clip.x + clip.width - 1, clip.y + clip.height - 1);
    int   rMin       = table.rowAtPoint(upperLeft);
    int   rMax       = table.rowAtPoint(lowerRight);

    // This should never happen (as long as our bounds intersect the clip,
    // which is why we bail above if that is the case).
    if (rMin == -1) {
      rMin = 0;
    }

    // If the table does not have enough rows to fill the view we'll get -1.
    // (We could also get -1 if our bounds don't intersect the clip,
    // which is why we bail above if that is the case).
    // Replace this with the index of the last row.
    if (rMax == -1) {
      rMax = table.getRowCount() - 1;
    }

    boolean ltr  = table.getComponentOrientation().isLeftToRight();
    int     cMin = table.columnAtPoint(ltr
                                       ? upperLeft
                                       : lowerRight);
    int     cMax = table.columnAtPoint(ltr
                                       ? lowerRight
                                       : upperLeft);

    // This should never happen.
    if (cMin == -1) {
      cMin = 0;
    }

    // If the table does not have enough columns to fill the view we'll get -1.
    // Replace this with the index of the last column.
    if (cMax == -1) {
      cMax = table.getColumnCount() - 1;
    }

    ti.theTable  = table;
    ti.rowMin    = rMin;
    ti.rowMax    = rMax;
    ti.columnMax = cMax;
    ti.columnMin = cMin;

    Rectangle minCell = table.getCellRect(rMin, cMin, true);
    Rectangle maxCell = table.getCellRect(rMax, cMax, true);

    ti.damagedArea = minCell.union(maxCell);
  }

  public Stroke getLineStroke() {
    return lineStroke;
  }

  public boolean isExtendBackground() {
    return extendBackground;
  }

  protected void paintAlternatingColumnColor(TableInformation ti, Graphics2D g, int viewWidth, int viewHeight,
          Color color) {
    Color  ocolor  = g.getColor();
    JTable table   = ti.theTable;
    Color  tableBg = null;    // table.getBackground(); dont paint table always
    // transparent
    Color bg = null;

    try {
      int              y    = ti.damagedArea.y;
      TableColumnModel cm   = table.getColumnModel();
      int              cMin = ti.columnMin;
      int              cMax = ti.columnMax;
      int              x;
      int              w;

      viewHeight -= y;

      if (table.getComponentOrientation().isLeftToRight()) {
        x = ti.damagedArea.x;

        for (int column = cMin; column <= cMax; column++) {
          w  = cm.getColumn(column).getWidth();
          bg = (column % 2 == 1)
               ? color
               : tableBg;

          if (bg != null) {
            g.setColor(bg);
            g.fillRect(x, y, w, viewHeight);
          }

          x += w;
        }

        if ((x < viewWidth) && (bg != null)) {
          g.fillRect(x, y, viewHeight, viewHeight);
        }
      } else {
        x  = ti.damagedArea.x;
        bg = null;

        for (int column = cMin; column <= cMax; column++) {
          bg = (column % 2 == 1)
               ? color
               : tableBg;
          w  = cm.getColumn(column).getWidth();

          if (bg != null) {
            g.setColor(bg);
            g.fillRect(x, y, w, viewHeight);
          }

          x -= w;
        }

        if ((x > 0) && (bg != null)) {
          g.fillRect(0, y, viewHeight, x);
        }
      }
    } finally {
      g.setColor(ocolor);
    }
  }

  protected void paintEmptyCells(JComponent container, TableInformation ti, Graphics2D g, int viewWidth,
                                 int viewHeight, UIColor rowHilite) {
    int              cMin   = ti.columnMin;
    int              cMax   = ti.columnMax;
    TableView        table  = (TableView) ti.theTable;
    TableColumnModel model  = table.getColumnModel();
    int              rm     = table.getRowMargin();
    int              cm     = model.getColumnMargin();
    int              y      = ti.displayHeight;
    int              height = table.getRowHeight();
    int              cw     = 0;
    int              x      = 0;
    TableColumn      col;
    aTableHeader     h = tableComponent.getTableHeader();

    graphics = SwingGraphics.fromGraphics(g, table, graphics);

    SwingGraphics sg  = graphics;
    int           row = table.getRowCount();
    int           lvc = table.header.getLastVisibleColumn();

    while(y < viewHeight) {
      x = ti.damagedArea.x;

      if ((rowHilite != null) && (row % 2 == 1)) {
        g.setColor(rowHilite);
        g.fillRect(0, y, viewWidth, height);
      }

      for (int column = cMin; column <= cMax; column++) {
        col = model.getColumn(column);

        int w = col.getWidth();

        h.paintColumn(col.getModelIndex(), sg, x, y, w - ((lvc == column)
                ? 0
                : cm), height - rm);
        x += w;
      }

      if (cw == 0) {
        cw = x;
      }

      y += height;
      row++;
    }

    sg.clear();
  }

  protected void paintEmptyTableGrid(TableView table, Graphics g, boolean horizontal, boolean vertical, Dimension size,
                                     TableInformation ti) {
    int        viewWidth  = table.getWidth();
    int        viewHeight = size.height;
    int        y          = 0;
    int        x          = 0;
    Graphics2D g2d        = (Graphics2D) g;
    Color      ocolor     = g2d.getColor();
    Stroke     ostroke    = g2d.getStroke();

    g2d.setColor(tableStyle.gridColor);

    if ((lineStroke != null) && (lineStroke != SwingHelper.SOLID_STROKE)) {
      g2d.setStroke(lineStroke);
    }

    try {
      if (horizontal) {
        int height = table.getRowHeight();

        while(y < viewHeight) {
          y += height;
          g.drawLine(x, y - 1, viewWidth - 1, y - 1);
        }
      }

      if (vertical) {
        JTableHeader     th    = table.getTableHeader();
        TableColumnModel cm    = th.getColumnModel();
        int              len   = cm.getColumnCount();
        boolean          first = true;

        if (table.header.paintRightMargin) {
          g.drawLine(viewWidth - 1, 0, viewWidth - 1, viewHeight);
        }

        if (table.header.paintLeftMargin) {
          g.drawLine(0, 0, 0, viewHeight);
        }

        for (int i = 0; i < len; i++) {
          Rectangle r = th.getHeaderRect(i);

          if (r.width > 0) {
            if (first) {
              first = false;
            } else {
              g.drawLine(r.x - 1, 0, r.x - 1, viewHeight);
            }
          }
        }
      }
    } finally {
      g2d.setColor(ocolor);
      g2d.setStroke(ostroke);
    }
  }

  public boolean isChangeSelColorOnLostFocus() {
    return changeSelColorOnLostFocus;
  }

  public void setChangeSelColorOnLostFocus(boolean changeSelColorOnLostFocus) {
    this.changeSelColorOnLostFocus = changeSelColorOnLostFocus;
  }
}
