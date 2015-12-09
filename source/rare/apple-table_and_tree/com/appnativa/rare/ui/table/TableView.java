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

import java.util.List;

import com.appnativa.rare.platform.apple.ui.view.ListView;
import com.appnativa.rare.ui.Column;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.UIStroke;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformRenderingComponent;
import com.appnativa.rare.ui.table.iTableComponent.GridViewType;
import com.appnativa.rare.ui.table.iTableComponent.TableType;
import com.appnativa.rare.ui.text.HTMLCharSequence;
import com.appnativa.rare.ui.tree.iTreeItem;
import com.appnativa.util.IntList;
import com.google.j2objc.annotations.Weak;

/*-[
 #import "RAREAPTableView.h"
 #import "APView+Component.h"
 ]-*/
public class TableView extends ListView {
  private static RenderableDataItem NULL_REPLACEMENT = new RenderableDataItem("");
  protected TableHeader             header;
  private boolean                   autoSizeColumns;
  private IntList                   columnWidths;
  private IntList                   columnWidthsIndex;
  private UIDimension               computeSize;
  private boolean                   heightsDirty;
  private boolean                   horizontalScollEnabled;
  private boolean                   needSizeToFitCall;
  @Weak
  private TableComponent            tableComponent;
  private int                       oldWidth;
  private int                       oldHeight;
  private TableType                 tableType;

  public TableView() {
    this(createTableProxy());
  }

  public TableView(Object proxy) {
    super(proxy);
    createHeader();
  }

  @Override
  public void columnSelected(int row, int column) {
    header.setSelectedIndex(column);
    super.itemSelected(row);
  }

  private native void columnSizesUpdated()
  /*-[
   [self removeRowHeights];
    return [((RAREAPTableView*)proxy_) columnSizesUpdated];
  ]-*/
  ;

  protected void createHeader() {
    header = new TableHeader(this, getHeaderProxy());
  }

  protected Object createNativeColumnView(int row, int modelCol, Column col, RenderableDataItem item) {
    iPlatformComponent c = (item == null)
                           ? null
                           : item.getRenderingComponent();

    if (c != null) {
      return c.getProxy();
    }

    iPlatformRenderingComponent rc = col.getCellRenderer();

    if (rc != null) {
      return rc.createNewNativeView();
    }

    return new TableRowView();
  }

  @Override
  protected void disposeEx() {
    if (header != null) {
      header.dispose();
    }

    header = null;
    super.disposeEx();
  }

  public CharSequence getColumnTitle(Column c) {
    return HTMLCharSequence.checkSequence(c.getColumnTitle(), header.getFont());
  }

  protected int getGridRowHeight() {
    if (getRowCount() > 0) {
      return getRowHeight(0);
    }

    return getRowHeight();
  }

  public TableHeader getHeader() {
    return header;
  }

  protected native Object getHeaderProxy()
  /*-[
    return ((RAREAPTableView*)proxy_)->headerProxy;
  ]-*/
  ;

  public int getRowHeight(int row) {
    int rh = getRowHeight();

    if (fixedRowSize) {
      return rh;
    }

    return header.getRowHeight(row, itemRenderer, rh) + 8;
  }

  @Override
  public float getPreferredHeight(int row) {
    if (header != null) {
      return getRowHeight(row);
    }

    return super.getPreferredHeight(row);
  }

  @Override
  public int getSelectedColumn() {
    return (header == null)
           ? -1
           : header.getSelectedColumn();
  }

  @Override
  protected float getSelectionPaintEndX(float currentEndX) {
    if (!isTable()) {
      return currentEndX;
    }

    return ((TableHeader) tableComponent.getTableHeader()).getSelectionPaintEndX(currentEndX);
  }

  @Override
  protected float getSelectionPaintStartX(float currentStartX) {
    if (!isTable()) {
      return currentStartX;
    }

    return ((TableHeader) tableComponent.getTableHeader()).getSelectionPaintStartX(currentStartX);
  }

  public TableComponent getTableComponent() {
    return tableComponent;
  }

  @Override
  public boolean isScrolling() {
    return (tableComponent == null)
           ? super.isScrolling()
           : tableComponent.isScrolling();
  }

  protected boolean isScrollingEx() {
    return super.isScrolling();
  }

  @Override
  public boolean isColumnSelected(int col) {
    return (header == null)
           ? false
           : header.isColumnSelected(col);
  }

  @Override
  public boolean isColumnSelectionAllowed() {
    return (header == null)
           ? false
           : header.isColumnSelectionAllowed();
  }

  @Override
  protected boolean isTable() {
    return true;
  }

  public void layout(Object parentUIView, RenderableDataItem rowItem, int parentWidth, int parentHeight) {
    int                x = 0;
    Column             c;
    int                span;
    RenderableDataItem item;
    int                width   = 0;
    Column[]           columns = header.getColumns();
    int                n       = 0;
    final int          len     = columns.length;
    int                d       = 0;
    int[] vp=header.viewPositions;
    if (showVertivalGridLines) {
      d = ScreenUtils.PLATFORM_PIXELS_1;
    }

    int lvc = header.getLastVisibleColumn();

    for (int i = 0; i < len; i++) {
      c = columns[vp[i]];

      if (!c.isVisible()) {
        continue;
      }

      item = rowItem.getItemEx(i);

      if (item == null) {
        item = NULL_ITEM;
      }

      span = item.getColumnSpan();

      if (span == 0) {
        span = 1;
      }

      if (span != 1) {
        if (span < 0) {
          span = len;
        }

        width = TableHelper.getSpanWidth(i, span, columns, vp);
      } else {
        width = c.getWidth();
      }

      i += span - 1;

      if (lvc == i) {
        layoutItemView(parentUIView, n, x, parentWidth - x, parentHeight);
      } else {
        layoutItemView(parentUIView, n, x, width, parentHeight);
      }

      x += width + d;
      n++;
    }
  }

  protected native void rectOfRow(int row, UIRectangle rect)
  /*-[
    CGRect r=[((RAREAPTableView*)proxy_) rectOfRow: row];
    rect->x_=r.origin.x;
    rect->y_=r.origin.y;
    rect->width_=r.size.width;
    rect->height_=r.size.height;
  ]-*/
  ;

  @Override
  public void refreshItems() {
    super.refreshItems();

    if ((header != null) && header.isGridView()) {
      header.handleGridView((int) getWidth(), (int) getHeight(), getGridRowHeight(), true);
    }

    sizeToFit();
  }

  private void removeRowHeights() {
    if (!heightsDirty ||!tableComponent.autoSizeRowsToFit) {
      return;
    }

    List<RenderableDataItem> list = tableComponent.getTableModel().getRowsEx();
    int                      len  = list.size();

    for (int i = 0; i < len; i++) {
      list.get(0).setHeight(0);
    }
  }

  @Override
  public void renderItem(int row, RenderableDataItem item, RowView view, boolean isSelected, boolean isPressed,
                         iTreeItem ti) {
    if (renderingCallback != null) {
      view.row = row;

      if (renderingCallback.renderItem(row, item, view, isSelected, isPressed, ti)) {
        return;
      }
    }

    ((TableRowView) view).render(this, getComponent(), itemRenderer, row, item, header.columns, isSelected,
                                 isPressed, ti,header.viewPositions);
  }

  public void setAutoSizeColumns(boolean autoSizeColumns) {
    this.autoSizeColumns = autoSizeColumns;

    if (tableComponent != null) {
      sizeToFit();
    }
  }

  public void setHorizontalScollEnabled(boolean enabled) {
    horizontalScollEnabled = enabled;
  }

  public void setShowGridLines(boolean horizontal, boolean vertical, UIColor color, UIStroke stroke) {
    dividerLineColor        = color;
    dividerStroke           = stroke;
    showDivider             = horizontal;
    showHorizontalGridLines = horizontal;
    setShowVertivalGridLines(vertical);
  }

  public void setTableComponent(TableComponent tableComponent) {
    this.tableComponent = tableComponent;
  }

  public void sizeColumnsToFit() {
    if (header != null) {
      header.sizeColumnsToFitTableData();
      columnSizesUpdated();
    }
  }

  public void sizeRowsToFit() {
    columnSizesUpdated();
  }

  public void sizeToFit() {
    if (tableComponent != null) {
      needSizeToFitCall = false;

      if (!columnSizesInitialized) {
        needSizeToFitCall = true;

        return;
      }

      if (this.autoSizeColumns) {
        this.sizeColumnsToFit();
        tableComponent.revalidate();
      }
    }
  }

  @Override
  public void makeSelectionVisible() {
    tableComponent.makeSelectionVisible();
  }

  public boolean updateColumnSizes(int width, int height) {
    if ((Math.abs(oldWidth - width) > 5)
        || ((header.getGridViewType() == GridViewType.HORIZONTAL_WRAP) && (Math.abs(oldHeight - height) > 5))) {
      oldWidth               = width;
      oldHeight              = height;
      columnSizesInitialized = true;

      boolean updated = false;

      if (header.isGridView()) {
        width   = tableComponent.getWidth();
        height  = tableComponent.getHeight() - header.getHeight();
        updated = header.handleGridView(width, height, getGridRowHeight(), false);
      } else {
        if (header.isAutoSizedColumns()) {
          return false;
        }

        int leftOver = TableHelper.calculateColumnSizes(component, header.getColumns(), width, true);

        if ((leftOver < 0) &&!horizontalScollEnabled) {
          header.reduceColumnSizes(itemRenderer, -leftOver);
        }

        updated = true;
      }

      if (updated) {
        columnSizesUpdated();
      }

      if (needSizeToFitCall) {
        sizeToFit();
      }

      return updated;
    } else {
      return false;
    }
  }

  @Override
  protected void updateRenderInsetsForCheckBox(float left, float right) {}

  protected native static Object createTableProxy()
  /*-[
    RAREAPTableView* v=[[[RAREAPTableView alloc]init] configureForTable];
    return v;
  ]-*/
  ;

  public TableType getTableType() {
    return tableType;
  }

  public void setTableType(TableType tableType) {
    this.tableType = tableType;
  }

  protected native void moveColumnEx(int from, int to)
  /*-[
    [(RAREAPTableView*)proxy_ moveColumn: from toColumn: to];
  ]-*/;
  
  public void moveColumn(int from, int to) {
    moveColumnEx(from, to);
    for(iPlatformRenderingComponent row:rows) {
      if (row instanceof UITableCellViewRenderingComponent) {
        ((UITableCellViewRenderingComponent) row).columnMoved(from, to);
      }
    }
  }
}
