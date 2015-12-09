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

import com.appnativa.rare.platform.apple.ui.util.AppleGraphics;
import com.appnativa.rare.platform.apple.ui.view.LabelView;
import com.appnativa.rare.platform.apple.ui.view.View;
import com.appnativa.rare.ui.BasicSelectionModel;
import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.Column;
import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.ui.UIImageIcon;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.iPlatformBorder;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.iRenderingComponent;
import com.appnativa.rare.ui.painter.PaintBucket;
import com.appnativa.rare.ui.painter.aUIComponentPainter;
import com.appnativa.rare.ui.renderer.UILabelRenderer;
import com.appnativa.rare.ui.renderer.WordWrapLabelRenderer;
import com.appnativa.rare.ui.table.iTableComponent.GridViewType;
import com.appnativa.util.iFilterableList;

/*-[
 #import "RAREAPTableView.h"
 #import "RAREAPTableColumn.h"
 #import "RAREAPTableHeaderView.h"
 #import "com/appnativa/rare/ui/RenderableDataItem.h"
 ]-*/
import com.google.j2objc.annotations.Weak;

public class TableHeader extends aTableHeader {
  private TableHeaderView headerView;
  private TableView       tableView;

  public TableHeader(TableView table, Object proxy) {
    this(table, proxy, proxy);
  }

  public TableHeader(TableView table, Object proxy, Object headerProxy) {
    super();
    headerView        = new TableHeaderView(table, proxy, headerProxy);
    headerView.header = this;
    setView(headerView);
    tableView = table;

    MouseListener l = new MouseListener();

    addMouseListener(l);
    addMouseMotionListener(l);
    setForeground(ColorUtils.getForeground());
  }
  
  @Override
  public void moveColumn(int column, int targetColumn) {
    int len=this.columns.length;
    if(column<0 || column>=len || targetColumn<0 || targetColumn>=len) {
      throw new IllegalArgumentException();
    }
   tableView.moveColumn(column, targetColumn);
   columnMoved(column, targetColumn);
  }
  
  public boolean isColumnSelected(int col) {
    return (selectionModel == null)
           ? false
           : selectionModel.isSelected(col);
  }

  @Override
  public void setGridViewType(GridViewType gridViewMode) {
    super.setGridViewType(gridViewMode);
    setVerticalScrollingEnabled(gridViewMode == GridViewType.VERTICAL_WRAP);
  }

  @Override
  public void setColumnIcon(int col, iPlatformIcon icon) {
    headerView.setColumnIcon(col, icon);
    revalidate();
  }

  @Override
  public void setColumnTitle(int col, CharSequence title) {
    headerView.setColumnTitle(col, title);
    revalidate();
  }

  @Override
  protected void setColumnVisibleEx(int col, boolean visible) {
    headerView.setColumnVisible(col, visible);
    resetSelectionPaintColumns();
    revalidate();
  }

  @Override
  public int getColumnIndexAt(float x, float y) {
    return headerView.getColumnIndexAt(x, y);
  }

  @Override
  public Column getColumnForViewAt(int viewColumn) {
    return headerView.getColumnForView(viewColumn);
  }

  @Override
  public iTableComponent getTableComponent() {
    return tableView.getTableComponent();
  }

  protected void addViewColumn(int index, Column col, UIInsets insets, UIFont font, UIColor color) {
    headerView.addViewColumn(index, col, insets, font, color);
  }

  @Override
  protected void repaintColumn(int col) {
    headerView.repaintColumn(col);
  }

  @Override
  protected void tableHadInteraction() {
    tableView.setHasHadInteraction();
    getTableComponentEx().repaint();
  }

  @Override
  protected void setColumnPressed(int index, boolean pressed) {
    headerView.setColumnPressed(index, pressed);
  }

  @Override
  protected void setColumnsEx() {
    int len = columns.length;

    headerView.removeAllViewColumns();

    UIColor fg   = getForeground();
    UIFont  font = getFont();

    for (int i = 0; i < len; i++) {
      Column c = columns[i];

      if (c.wordWrap) {
        iRenderingComponent rc = c.getCellRenderer();

        if (rc == null) {
          c.setCellRenderer(new WordWrapLabelRenderer());
        } else if (rc instanceof UILabelRenderer) {
          ((UILabelRenderer) rc).setWordWrap(true);
        }
      }

      iPlatformIcon icon = c.getHeaderIcon();

      if (icon instanceof UIImageIcon) {
        ((UIImageIcon) icon).isImageLoaded(this);
      }

      addViewColumn(i, c, cellInsets, font, fg);
    }

    if (columnSelectionAllowed) {
      selectionModel = new BasicSelectionModel(len);
    }

    headerView.columnsSet();
  }

  protected Object getRealHeaderViewProxy() {
    return headerView.headerView;
  }

  @Override
  protected void resetTableModel(iFilterableList<RenderableDataItem> rows) {
    super.resetTableModel(rows);
    getTableComponentEx().revalidate();
  }

  protected float getSelectionPaintEndX(float currentEndX) {
    final int col = selectionPaintEndCol;

    if (col == columns.length - 1) {
      return currentEndX;
    }

    return headerView.getColumnEndX(col);
  }

  protected float getSelectionPaintStartX(float currentStartX) {
    final int col = selectionPaintStartCol;

    if (col == 0) {
      return currentStartX;
    }

    return headerView.getColumnStartX(col) - (tableView.isShowVertivalGridLines()
            ? 1
            : 0);
  }

  native void setVerticalScrollingEnabled(boolean enabled)
  /*-[
    RAREAPTableView* table=(RAREAPTableView*)tableView_->proxy_;
    table.scrollEnabled=enabled;
  ]-*/
  ;

  static class TableHeaderView extends View {
    @Weak
    private TableHeader header;
    @Weak
    private Object      headerView;
    @Weak
    private TableView   tableView;

    public TableHeaderView(TableView table, Object proxy) {
      this(table, proxy, proxy);
    }

    public TableHeaderView(TableView table, Object proxy, Object headerView) {
      super(proxy);
      this.headerView = headerView;
      this.tableView  = table;
    }

    @Override
    public void paintBackground(AppleGraphics g, View v, UIRectangle rect) {
      super.paintBackground(g, v, rect);

      iPlatformBorder b = (header.headerCellPainter == null)
                          ? null
                          : header.headerCellPainter.getBorder();

      if ((header.showHeaderMargin) || (b != null)) {
        drawSeparatorEx(g, b, (b == null)
                              ? header.marginColor
                              : null);

        if (header.bottomMarginColor != null) {
          g.setStrokeWidth(1.5f);
          g.setColor(header.bottomMarginColor);
          g.drawLine(rect.x, rect.y + rect.height, rect.x + rect.width, rect.y + rect.height);
        }
      }
    }

    public native void setColumnIcon(int col, iPlatformIcon icon)
    /*-[
      RAREAPTableView* table=(RAREAPTableView*)tableView_->proxy_;
      RAREAPTableColumn* c=[table.tableColumns objectAtIndex: col];
      [c->columnDescription setHeaderIconWithRAREiPlatformIcon: icon];
      [table updateColumnAtIndex:col visualsOnly:YES];
    ]-*/
    ;

    public native void setColumnTitle(int col, CharSequence title)
    /*-[
      RAREAPTableView* table=(RAREAPTableView*)tableView_->proxy_;
      RAREAPTableColumn* c=[table.tableColumns objectAtIndex: col];
      [c->columnDescription setColumnTitleWithJavaLangCharSequence:title];
      [table updateColumnAtIndex:col visualsOnly:YES];
    ]-*/
    ;

    public native void setColumnVisible(int col, boolean visible)
    /*-[
      RAREAPTableView* table=(RAREAPTableView*)tableView_->proxy_;
      RAREAPTableColumn* c=[table.tableColumns objectAtIndex: col];
      [c setHidden: !visible];
      [c->columnDescription setVisibleWithBoolean: visible];
    ]-*/
    ;

    public iPlatformComponent getColumnComponent(int col) {
      Object proxy = getColumnViewProxy(col);
      View   v     = View.viewFromProxy(proxy);

      if (v == null) {
        v = new LabelView(proxy);
      }

      Component c = Component.fromView(v);

      return (c == null)
             ? new Component(v)
             : c;
    }

    public native float getColumnEndX(int index)
    /*-[
      RAREAPTableView* table=(RAREAPTableView*)tableView_->proxy_;
      RAREAPTableColumn* c=[table.tableColumns objectAtIndex: index];
      return c.frame.origin.x+c.frame.size.width;
    ]-*/
    ;

    public native int getColumnIndexAt(float x, float y)
    /*-[
      return [((RAREAPTableHeaderView*)headerView_) getColumnIndexAtX: x andY: y];
    ]-*/
    ;

    public native float getColumnStartX(int index)
    /*-[
      RAREAPTableView* table=(RAREAPTableView*)tableView_->proxy_;
      RAREAPTableColumn* c=[table.tableColumns objectAtIndex: index];
      return c.frame.origin.x;
    ]-*/
    ;

    protected native void addViewColumn(int index, Column col, UIInsets insets, UIFont font, UIColor color)
    /*-[
      RAREAPTableView* table=(RAREAPTableView*)tableView_->proxy_;
      [table addColumnAtIndex: index column: col insets: insets font: font fgColor: color];
    ]-*/
    ;

    @Override
    protected void disposeEx() {
      super.disposeEx();
      headerView = null;
      tableView  = null;
      header     = null;
    }

    protected void paintColumnCustomBackground(AppleGraphics g, int index, Column col, UIRectangle rect,
            boolean selected) {
      PaintBucket pb = null;

      if (selected) {
        pb = col.getHeaderSelectionPainter();

        if (pb == null) {
          pb = header.getPressedPainter();
        }
      } else {
        pb = col.getHeaderPainter();
      }

      if (pb != null) {
        float h = rect.height;
        float w = rect.width;

        if (header.showHeaderMargin) {
          h--;
        }

        if (index != header.getLastVisibleColumn()) {
          w--;
        }

        aUIComponentPainter.paint(g, rect.x, rect.y, w, h, pb);
      }

      int scol = header.sortColumn;

      if ((scol != -1) && (header.getColumnAt(scol) == col)) {
        iPlatformIcon icon = header.getSortIcon(header.descending);

        if (icon != null) {
          float iw = icon.getIconWidth();
          float ih = icon.getIconHeight();
          float x  = (rect.width - iw) / 2;
          float y  = rect.y + ScreenUtils.PLATFORM_PIXELS_1;

          icon.paint(g, x, y, iw, ih);
        }
      }
    }

    protected native void removeAllViewColumns()
    /*-[
      RAREAPTableView* table=(RAREAPTableView*)tableView_->proxy_;
      [table removeAllColumns];
    ]-*/
    ;

    protected native void columnsSet()
    /*-[
      RAREAPTableView* table=(RAREAPTableView*)tableView_->proxy_;
      [table columnsSet];
    ]-*/
    ;

    protected native void removeViewColumn(int index)
    /*-[
      RAREAPTableView* table=(RAREAPTableView*)tableView_->proxy_;
      [table removeColumn: index];
    ]-*/
    ;

    protected native void repaintColumn(int index)
    /*-[
      RAREAPTableView* table=(RAREAPTableView*)tableView_->proxy_;
      [table repaintColumn: index];
    ]-*/
    ;

    protected native void updateViewColumn(int index, boolean visualsOnly)
    /*-[
      RAREAPTableView* table=(RAREAPTableView*)tableView_->proxy_;
      [table updateColumnAtIndex: index visualsOnly: visualsOnly];
    ]-*/
    ;

    protected native void setColumnPressed(int index, boolean pressed)
    /*-[
      RAREAPTableView* table=(RAREAPTableView*)tableView_->proxy_;
      [table setColumnPressedAtIndex: index pressed: pressed];
    ]-*/
    ;

    protected Object getColumnAPForeground(Column col, boolean pressed) {
      PaintBucket pb = pressed
                       ? header.getPressedPainter()
                       : null;
      UIColor     fg = (pb == null)
                       ? null
                       : pb.getForegroundColor();

      if (fg == null) {
        fg = header.headerCellPainter.getForegroundColor();
      }

      return fg.getAPColor();
    }

    private native void drawSeparatorEx(AppleGraphics g, iPlatformBorder b, UIColor color)
    /*-[
      [((RAREAPTableHeaderView*)headerView_) drawSeparatorEx:g border: b lineColor: color];
    ]-*/
    ;

    private native Object getColumnViewProxy(int col)
    /*-[
      RAREAPTableView* table=(RAREAPTableView*)tableView_->proxy_;
      return [table.tableColumns objectAtIndex: col];
    ]-*/
    ;

    private native Column getColumnForView(int viewColumn)
    /*-[
      RAREAPTableView* table=(RAREAPTableView*)tableView_->proxy_;
      RAREAPTableColumn* tc=(RAREAPTableColumn*) [table.tableColumns objectAtIndex: viewColumn];
      return tc->columnDescription;
    ]-*/
    ;
  }
}
