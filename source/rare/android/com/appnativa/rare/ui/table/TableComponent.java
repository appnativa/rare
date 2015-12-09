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

import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.GestureDetector.OnGestureListener;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;

import com.appnativa.rare.platform.android.iContextMenuHandler;
import com.appnativa.rare.platform.android.ui.iFlingSupport;
import com.appnativa.rare.platform.android.ui.view.HorizontalScrollViewEx;
import com.appnativa.rare.platform.android.ui.view.ListViewEx;
import com.appnativa.rare.spot.Table;
import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.Column;
import com.appnativa.rare.ui.Container;
import com.appnativa.rare.ui.ListComponent;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIPoint;
import com.appnativa.rare.ui.UIPopupMenu;
import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.UIStroke;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.iPlatformItemRenderer;
import com.appnativa.rare.ui.iScrollerSupport;
import com.appnativa.rare.ui.iTableModel;
import com.appnativa.rare.ui.event.KeyEvent;
import com.appnativa.rare.ui.event.MouseEvent;
import com.appnativa.rare.ui.event.iAndroidViewListener;
import com.appnativa.rare.ui.event.iDataModelListener;
import com.appnativa.rare.ui.painter.PaintBucket;
import com.appnativa.rare.ui.painter.UIScrollingEdgePainter;
import com.appnativa.rare.ui.renderer.ListItemRenderer;
import com.appnativa.rare.ui.table.TableHeader.ColumnView;
import com.appnativa.rare.ui.table.aTableAdapter.TableRow;
import com.appnativa.rare.ui.table.aTableHeader.SizeType;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.util.FilterableList;
import com.appnativa.util.iFilterableList;

/**
 *
 * @author Don DeCoteau
 */
public class TableComponent extends Container
        implements iTableComponent, iContextMenuHandler, iDataModelListener, iScrollerSupport {
  boolean                      horizontalScroll;
  protected int                sortColumn            = -1;
  protected boolean            descending            = true;
  protected boolean            sortingAllowed        = true;
  boolean                      multipleSelectColumns = false;
  protected boolean            autoSizeColumns;
  protected boolean            autoSizeRows;
  protected ListComponent      list;
  protected TableHeader        tableHeader;
  protected DataItemTableModel tableModel;
  protected TableStyle         tableStyle;
  private int                  oldWidth;
  private int                  oldHeight;
  private boolean              keepSelectionVisible;
  boolean                      columnSizesInitialized;
  private TableType            tableType;

  public TableComponent(ListViewEx table, Table cfg, boolean main) {
    super();

    Context context = table.getContext();

    if (((cfg.autoResizeMode.intValue() == Table.CAutoResizeMode.none) && main)
        || cfg.displayAsGridView.booleanValue()) {
      setView(new HorizontalScrollingTableComponentLayout(context));
      ((HorizontalScrollingTableComponentLayout) view).table.table = this;
      horizontalScroll                                             = true;
    } else {
      setView(new TableViewLayout(context));
      ((TableViewLayout) view).table = this;
    }

    autoSizeColumns = cfg.autoSizeColumnsToFit.booleanValue();
    autoSizeRows    = cfg.autoSizeRowsToFit.booleanValue();
    tableHeader     = new TableHeader(context, this);
    table.setHeader(tableHeader);

    boolean grid = cfg.displayAsGridView.booleanValue();

    if (grid) {
      setGridViewType(GridViewType.valueOf(cfg.gridViewType.stringValue().toUpperCase(Locale.US)));
      tableHeader.setVisible(false);
    } else if (!cfg.showStandardColumnHeader.booleanValue()) {
      tableHeader.setVisible(false);
    }

    if (cfg.headerHeight.spot_valueWasSet()) {
      tableHeader.setHeaderHeight(cfg.headerHeight.getValue());
    }

    if (cfg.fgColor.getValue() == null) {
      setForeground(ColorUtils.getListForeground());
    }

    if (cfg.bgColor.getValue() == null) {
      setBackground(ColorUtils.getListBackground());
    }

    list = new ListComponent(table);
    add(tableHeader);
    add(list);
    list.setOpaque(false);
    createTableModel();
    tableModel.addDataModelListener(this);
    getListView().setExtendBackgroundRendering(cfg.extendBackgroundRendering.booleanValue());

    if (grid) {
      tableHeader.setColumnSelectionAllowed(true);
    } else {
      ((aTableAdapter) tableModel).setAutoSizeColumns(cfg.autoSizeColumnsToFit.booleanValue());
      ((aTableAdapter) tableModel).setAutoSizeRows(cfg.autoSizeRowsToFit.booleanValue());
    }
  }

  public void addFocusListener(View.OnFocusChangeListener l) {
    list.addFocusListenerEx(l);
  }

  public void addKeyListener(View.OnKeyListener l) {
    list.addKeyListenerEx(l);
  }

  public void addMouseListener(View.OnTouchListener l) {
    list.addMouseListenerEx(l);
  }

  public void addViewListener(iAndroidViewListener l) {
    list.addViewListenerEx(l);
  }

  public View getScrollingView() {
    View v = getView();

    if (v instanceof HorizontalScrollViewEx) {
      return v;
    }

    return getListView();
  }

  @Override
  public void contentsChanged(Object source) {
    if (tableHeader.isGridView()) {
      tableHeader.handleGridView(oldWidth, getHeight(), getGridRowHeight(), true);
    } else if (autoSizeColumns || autoSizeRows) {
      sizeToFit();
    }
  }

  @Override
  public void contentsChanged(Object source, int index0, int index1) {}

  @Override
  public int convertModelIndexToView(int index) {
    return tableHeader.convertModelIndexToView(index);
  }

  @Override
  public int convertViewIndexToModel(int index) {
    return tableHeader.convertViewIndexToModel(index);
  }

  @Override
  public Column createColumn(String title, Object value, int type, Object data, iPlatformIcon icon) {
    return new Column(title, value, type, data, icon);
  }

  protected void createTableModel() {
    tableModel = new DataItemTableModel();
    tableModel.setAutoSizeRows(autoSizeRows);
  }

  @Override
  public void dispatchEvent(KeyEvent keyEvent) {
    list.dispatchEvent(keyEvent);
  }

  @Override
  public void dispatchEvent(MouseEvent mouseEvent) {
    list.dispatchEvent(mouseEvent);
  }

  @Override
  public boolean isMultiTableComponent() {
    return false;
  }

  @Override
  public void dispose() {
    if (tableModel != null) {
      tableModel.dispose();
    }

    super.dispose();

    if (tableHeader != null) {
      tableHeader.dispose();
    }

    if (list != null) {
      list.dispose();
    }

    tableHeader = null;
    list        = null;
    tableStyle  = null;
    tableModel  = null;
  }

  @Override
  public UIRectangle getCellRect(int row, int column, boolean includeMargin) {
    UIRectangle r    = null;
    View        view = getListView().getViewForRow(row);

    if (view instanceof TableRow) {
      TableRow tr = (TableRow) view;
      View     v  = (tr.getChildCount() < column)
                    ? null
                    : tr.getChildAt(column);

      if (v != null) {
        r = new UIRectangle(v.getLeft(), v.getTop(), v.getWidth(), v.getHeight());
      }
    } else {
      View v = tableHeader.getColumnView(column);

      r   = new UIRectangle(v.getLeft(), v.getTop(), v.getWidth(), v.getHeight());
      r.y = row * getRowHeight();
    }

    return r;
  }

  public Column getColumnAt(int index) {
    return tableHeader.getColumnAt(index);
  }

  @Override
  public int getColumnCount() {
    return tableModel.getColumnCount();
  }

  @Override
  public iTableComponent getColumnFooterTable() {
    return null;
  }

  @Override
  public iTableComponent getColumnHeaderTable() {
    return null;
  }

  @Override
  public int getColumnIndexAt(float x, float y) {
    return tableHeader.getColumnIndexAt(x, y);
  }

  @Override
  public int getFirstVisibleColumnIndex() {
    return tableHeader.getFirstVisibleColumnInView();
  }

  protected int getGridRowHeight() {
    if (getRowCount() > 0) {
      return getRowHeight(0);
    }

    return getRowHeight();
  }

  @Override
  public GridViewType getGridViewType() {
    return tableHeader.getGridViewType();
  }

  public TableHeader getHeader() {
    return tableHeader;
  }

  @Override
  public iPlatformItemRenderer getItemRenderer() {
    return tableModel.getItemRenderer();
  }

  @Override
  public int getLastVisibleColumnIndex() {
    return tableHeader.getLastVisibleColumnInView();
  }

  public ListViewEx getListView() {
    return (ListViewEx) list.getView();
  }

  @Override
  public iTableComponent getMainTable() {
    return this;
  }

  @Override
  protected void getMinimumSizeEx(UIDimension size, float maxWidth) {
    tableHeader.getMinimumSize(size, maxWidth);

    float w = size.width;
    float h = size.height;

    list.getMinimumSize(size);
    size.width = Math.max(w, size.width);

    if (tableHeader.isVisible()) {
      size.height += h;
    }

    if (size.width > 140) {
      w = size.width;
    }

    if (horizontalScroll) {
      size.width = 100;
    }
  }

  @Override
  public iTableModel getModel() {
    return tableModel;
  }

  @Override
  public iPlatformComponent getPlatformComponent() {
    return this;
  }

  @Override
  public UIPopupMenu getPopupMenu(View v, ContextMenuInfo menuInfo) {
    if (v instanceof ColumnView) {
      return (((ColumnView) v).column).getPopupMenu(widget);
    }

    return null;
  }

  @Override
  protected void getPreferredSizeEx(UIDimension size, float maxWidth) {
    if (maxWidth > 0) {
      tableHeader.getSize(size, true, maxWidth);
    } else {
      tableHeader.getPreferredSize(size);
    }

    float w = size.width;
    float h = size.height;

    list.getPreferredSize(size);
    size.width = Math.max(w, size.width);

    if (tableHeader.isVisible()) {
      size.height += h;
    }
  }

  public UIPoint getPressedPoint() {
    return getListView().getPressedPoint();
  }

  public int getRowCount() {
    return tableModel.getRowCount();
  }

  @Override
  public iTableComponent getRowFooterTable() {
    return null;
  }

  @Override
  public iTableComponent getRowHeaderTable() {
    return null;
  }

  public int getRowHeight() {
    return getListView().getRowHeight();
  }

  public int getRowHeight(int row) {
    int rh = getRowHeight();

    if (!autoSizeRows) {
      return rh;
    }

    return tableHeader.getRowHeight(row, tableModel.getItemRenderer(), rh);
  }

  @Override
  public int getSelectedColumn() {
    return tableHeader.getSelectedColumn();
  }

  @Override
  public int getSelectedColumnCount() {
    if (multipleSelectColumns) {
      return tableHeader.getSelectedColumnCount();
    } else {
      return (tableHeader.getSelectedColumn() > -1)
             ? 1
             : 0;
    }
  }

  @Override
  public int[] getSelectedColumns() {
    return tableHeader.getSelectedColumnIndices();
  }

  @Override
  public int getSortColumn() {
    return sortColumn;
  }

  public iPlatformComponent getTable() {
    return list;
  }

  @Override
  public TableHeader getTableHeader() {
    return tableHeader;
  }

  public iTableModel getTableModel() {
    return tableModel;
  }

  @Override
  public TableType getTableType() {
    return tableType;
  }

  TableViewLayout getTableViewLayout() {
    if (view instanceof HorizontalScrollingTableComponentLayout) {
      return ((HorizontalScrollingTableComponentLayout) view).table;
    } else {
      return ((TableViewLayout) view);
    }
  }

  @Override
  public UIRectangle getViewRect() {
    ListViewEx lv     = getListView();
    int        width  = getWidth() - view.getPaddingLeft() - view.getPaddingRight();
    int        height = getHeight() - view.getPaddingTop() - view.getPaddingBottom();

    if (horizontalScroll) {
      return new UIRectangle(lv.getScrollX(), lv.getScrollY(), width, height);
    }

    int  first = lv.getFirstVisiblePosition();
    View v     = lv.getViewForRow(first);
    int  x     = 0;
    int  y     = 0;

    if (v != null) {
      Rect r = new Rect();

      if (lv.getChildVisibleRect(v, r, null)) {
        x = r.left;
        y = r.top;
      }
    }

    return new UIRectangle(x, y, width, height);
  }

  @Override
  public int getVisibleColumnCount() {
    return tableHeader.getVisibleColumnCount();
  }

  protected void hadInteraction() {
    if (interactionListener != null) {
      interactionListener.interacted();
    }
  }

  @Override
  public void intervalAdded(Object source, int index0, int index1) {}

  @Override
  public void intervalRemoved(Object source, int index0, int index1, List<RenderableDataItem> removed) {}

  @Override
  public boolean isAtBottomEdge() {
    return getTableViewLayout().isAtBottomEdge();
  }

  @Override
  public boolean isAtLeftEdge() {
    if (view instanceof HorizontalScrollingTableComponentLayout) {
      return ((HorizontalScrollingTableComponentLayout) view).isAtLeftEdge();
    }

    return true;
  }

  @Override
  public boolean isAtRightEdge() {
    if (view instanceof HorizontalScrollingTableComponentLayout) {
      return ((HorizontalScrollingTableComponentLayout) view).isAtRightEdge();
    }

    return true;
  }

  @Override
  public void scrollToBottomEdge() {
    list.scrollToBottomEdge();
  }

  @Override
  public void scrollToLeftEdge() {
    if (view instanceof HorizontalScrollingTableComponentLayout) {
      ((HorizontalScrollingTableComponentLayout) view).scrollToLeftEdge();
    }
  }

  @Override
  public void scrollToRightEdge() {
    if (view instanceof HorizontalScrollingTableComponentLayout) {
      ((HorizontalScrollingTableComponentLayout) view).scrollToRightEdge();
    }
  }

  @Override
  public void scrollToTopEdge() {
    list.scrollToTopEdge();
  }

  @Override
  public void moveUpDown(boolean up, boolean block) {
    list.moveUpDown(up, block);
  }

  @Override
  public void moveLeftRight(boolean left, boolean block) {
    if (view instanceof HorizontalScrollingTableComponentLayout) {
      ((HorizontalScrollingTableComponentLayout) view).moveLeftRight(left, block);
    }
  }

  @Override
  public boolean isAtTopEdge() {
    return getTableViewLayout().isAtTopEdge();
  }

  @Override
  public UIPoint getContentOffset() {
    UIPoint p = getTableViewLayout().getContentOffset();

    if (view instanceof HorizontalScrollingTableComponentLayout) {
      p.x = ((HorizontalScrollingTableComponentLayout) view).getContentOffset().x;
    }

    return p;
  }

  @Override
  public void setContentOffset(float x, float y) {
    if (view instanceof HorizontalScrollingTableComponentLayout) {
      ((HorizontalScrollingTableComponentLayout) view).setContentOffset(x, y);
    } else {
      getListView().setContentOffset(x, y);
    }
  }

  @Override
  public boolean isAutoSizeRows() {
    return ((aTableAdapter) tableModel).isAutoSizeRows();
  }

  public boolean isColumnSizesInitialized() {
    return columnSizesInitialized;
  }

  @Override
  public boolean isEditing() {
    return false;
  }

  public boolean isKeepSelectionVisible() {
    return keepSelectionVisible;
  }

  @Override
  public boolean isMainTable() {
    return (tableType == null) || (tableType == TableType.MAIN);
  }

  boolean isShowVerticalLines() {
    return ((aTableAdapter) tableModel).showVerticalLines;
  }

  @Override
  public boolean isSortDescending() {
    return descending;
  }

  public void makeSelectionVisible() {
    int index = getListView().getSelectedIndex();

    if (index != -1) {
      getListView().scrollRowToVisible(index);

      if (horizontalScroll && tableHeader.isColumnSelectionAllowed()) {
        int col = getSelectedColumn();

        if (col == -1) {
          col = 0;
        }

        UIRectangle r     = getViewRect();
        float       x     = tableHeader.getColumnX(col);
        float       width = tableHeader.getColumnAt(col).getWidth();
        float       vex   = r.x + r.width;
        float       cex   = x + width;

        x = cex - vex;

        if (x < 0) {
          x = -r.x;
        }

        if (x != 0) {
          ((HorizontalScrollingTableComponentLayout) view).scrollBy((int) x, 0);
        }
      }
    }
  }

  protected void measurePreferredSizeEx(UIDimension size, int width) {
    GridViewType gt = getGridViewType();

    if (gt != null) {
      int len = tableHeader.getOriginalsGridViewsRowCount();

      if (len == 0) {
        len = 1;
      }

      switch(gt) {
        case HORIZONTAL_WRAP :
          size.width  = len * tableHeader.getGridColumnWidth(size);
          size.height = getGridRowHeight();

          break;

        case VERTICAL_WRAP :
          size.height = len * getGridRowHeight();
          size.width  = tableHeader.getGridColumnWidth(size);

          break;
      }
    } else {
      tableHeader.getSize(size, false, width);

      float w = size.width;
      float h = size.height;

      list.getPreferredSize(size);
      size.width  = Math.max(w, size.width);
      size.height += h;
    }
  }

  /**
   * Moves the column column to the position currently occupied by the column
   * targetColumn in the view. The old column at targetColumn is shifted left or
   * right to make room.
   *
   * @param column
   *          the index of column to be moved
   * @param targetColumn
   *          the new index of the column
   *
   */
  @Override
  public void moveColumn(int column, int targetColumn) {
    tableHeader.columnMoved(column, targetColumn);
    revalidate();
  }

  public void onConfigurationChanged(boolean reset) {}

  public void removeFocusListener(OnFocusChangeListener l) {
    list.removeFocusListenerEx(l);
  }

  @Override
  public void removeKeyListenerEx(OnKeyListener l) {
    list.removeKeyListenerEx(l);
  }

  public void removeMouseListener(OnTouchListener l) {
    list.removeMouseListenerEx(l);
  }

  public void removeViewListener(iAndroidViewListener l) {
    list.removeViewListenerEx(l);
  }

  @Override
  public void resetTable(List<Column> columns, List<RenderableDataItem> rows) {
    FilterableList<RenderableDataItem> l;

    if ((rows == null) || (rows.size() < 10)) {
      l = new FilterableList<RenderableDataItem>();
    } else {
      l = new FilterableList<RenderableDataItem>(rows.size());
    }

    if (rows != null) {
      l.addAll(rows);
    }

    iFilterableList<Column> cols = new FilterableList<Column>(columns.size());

    cols.addAll(columns);
    resetTableEx(cols, l);
  }

  protected void resetTableEx(iFilterableList<Column> columns, iFilterableList<RenderableDataItem> rows) {
    tableHeader.setColumns(columns);
    tableModel.resetModel(columns, rows);
  }

  @Override
  public void setBounds(int x, int y, int w, int h) {
    final LayoutParams lp = view.getLayoutParams();

    if (lp != null) {
      lp.width  = w;
      lp.height = h;
    }

    view.measure(MeasureSpec.makeMeasureSpec(w, MeasureSpec.EXACTLY),
                 MeasureSpec.makeMeasureSpec(h, MeasureSpec.EXACTLY));
    view.layout(x, y, x + w, y + h);
  }

  @Override
  public void setColumnIcon(int col, iPlatformIcon icon) {
    col = convertViewIndexToModel(col);
    tableHeader.setColumnIcon(col, icon);
  }

  @Override
  public void setColumnTitle(int col, CharSequence title) {
    col = convertViewIndexToModel(col);
    tableHeader.setColumnTitle(col, title);
    revalidate();
    repaint();
  }

  @Override
  public void setColumnVisible(int col, boolean visible) {
    col = convertViewIndexToModel(col);
    tableHeader.setColumnVisible(col, visible);
    tableModel.refreshItems();
  }

  protected void setDividerLine(UIColor color, UIStroke stroke) {
    getListView().setDividerLine(color, stroke);
  }

  @Override
  public void setForeground(UIColor fg) {
    super.setForeground(fg);

    if (list != null) {
      list.setForeground(fg);
    }
  }

  @Override
  public void setGridViewType(GridViewType type) {
    tableHeader.setGridViewType(type);

    if (type == GridViewType.VERTICAL_WRAP) {
      horizontalScroll = false;
    } else if (view instanceof HorizontalScrollingTableComponentLayout) {
      horizontalScroll = true;
    }
  }

  @Override
  public void setHeaderBackground(PaintBucket pb) {
    tableHeader.setComponentPainter(null);
    pb.install(tableHeader, true);
  }

  public void setKeepSelectionVisible(boolean keepSelectionVisible) {
    this.keepSelectionVisible = keepSelectionVisible;
  }

  void setMeasuredHeaderHeight(int height) {
    getTableViewLayout().setHeaderHeight(height);
  }

  @Override
  public void setOpaque(boolean opaque) {
    super.setOpaque(opaque);
    list.setOpaque(opaque);
  }

  public void setRowHeight(int height) {
    getListView().setRowHeight(height);
    ((aTableAdapter) tableModel).setRowHeight(height);
  }

  @Override
  public void setRowHeight(int row, int h) {}

  @Override
  public void setSelectedColumnIndex(int index) {
    tableHeader.setSelectedIndex(index);
  }

  @Override
  public void setSelectedColumnIndices(int[] indices) {
    tableHeader.setSelectedIndices(indices);
  }

  public void setShowHeaderMargin(boolean show) {
    tableHeader.setShowHeaderMargin(show);
  }

  @Override
  public void setShowHorizontalLines(boolean show) {
    ((aTableAdapter) tableModel).setShowHorizontalLines(show);
  }

  @Override
  public void setShowVerticalLines(boolean show) {
    ((aTableAdapter) tableModel).setShowVerticalLines(show);
  }

  @Override
  public void setSortColumn(int sortColumn, boolean descending) {
    this.sortColumn = sortColumn;
    this.descending = descending;
    tableHeader.setSortColumn(sortColumn, descending);
  }

  @Override
  public void setStyle(TableStyle style) {
    tableStyle = style;

    ListViewEx lv = (ListViewEx) list.getView();

    lv.setDividerHeight(0);

    if (style.headerCellPainter != null) {
      tableHeader.setHeaderCellPainter(style.headerCellPainter);
    }

    if (style.headerFont != null) {
      tableHeader.setFont(style.headerFont);
    }

    if (style.headerForeground != null) {
      tableHeader.setForeground(style.headerForeground);
    }

    tableHeader.setSortingAllowed(style.columnSortingAllowed);
    setShowHorizontalLines(style.showHorizontalLines);
    setShowVerticalLines(style.showVerticalLines);

    if (style.showHorizontalLines || style.showVerticalLines) {
      if ((style.gridColor != null) || (style.gridLineStroke != null)) {
        setDividerLine(style.gridColor, style.gridLineStroke);
      }
    }

    if (style.backgroundHilite == TableStyle.BackgroundHighlight.ROW) {
      UIColor c = (style.backgroundHiliteColor == null)
                  ? new UIColor(247, 247, 247)
                  : style.backgroundHiliteColor;

      tableModel.setAlternatingColor(c);
      lv.setAlternateRowColor(c);
    } else if (style.backgroundHilite == TableStyle.BackgroundHighlight.COLUMN) {
      tableHeader.setAlternatingColor((style.backgroundHiliteColor == null)
                                      ? new UIColor(247, 247, 247)
                                      : style.backgroundHiliteColor);
      lv.setDrawCallback(tableHeader);
    }

    if (style.headerMarginColor != null) {
      tableHeader.setMarginColor(style.headerMarginColor);
    }

    if (style.headerBottomMarginColor != null) {
      tableHeader.setBottomMarginColor(style.headerBottomMarginColor);
    }

    if (!tableHeader.isGridView()) {
      if (style.columnSelectionAllowed) {
        tableHeader.setColumnSelectionAllowed(true);
      }
    }

    ListItemRenderer lr = (ListItemRenderer) getItemRenderer();

    if (tableHeader.isColumnSelectionAllowed() && lr.isSelectionPainted()) {
      lr.setHandlesSelection(true);
    }
  }

  @Override
  public void setTable() {
    tableHeader.setColumns(tableModel.getColumns());
    getListView().setAdapter(tableModel);
  }

  @Override
  public void setTableType(TableType type) {
    tableType = type;
  }

  @Override
  public void setWidget(iWidget widget) {
    super.setWidget(widget);
    list.setWidget(widget);
    tableHeader.setWidget(widget);
    tableModel.setWidget(widget);
    tableModel.setListComponent(this);
  }

  public boolean sizeColumnsToFit() {
    return tableHeader.sizeColumnsToFitTableData();
  }

  public void sizeColumnToFit(int col) {
    Column c = getColumnAt(col);
    int    w = tableHeader.calculateColumnWidth(tableModel, col, c, Short.MAX_VALUE, new UIDimension(), SizeType.FIT);

    c.setWidth(w);
  }

  @Override
  public void sizeRowsToFit() {
    DataItemTableModel tm  = tableModel;
    int                len = (tm == null)
                             ? 0
                             : tm.size();

    for (int i = 0; i < len; i++) {
      tm.getRow(i).setHeight(0);
    }
  }

  public void sizeToFit() {
    if (this.autoSizeColumns) {
      if (sizeColumnsToFit()) {
        revalidate();
        repaint();
      }
    }

    if (autoSizeRows) {
      sizeRowsToFit();
    }
  }

  @Override
  public void sort(Comparator c) {
    tableModel.sort(c);
  }

  @Override
  public void sort(int col) {
    if (sortColumn == col) {
      descending = !descending;
    } else {
      descending = false;
    }

    sort(col, descending, false);
  }

  @Override
  public void sort(int col, boolean descending, boolean useLinkedData) {
    sortColumn      = col;
    this.descending = descending;
    tableModel.sort(col, descending, useLinkedData);
    tableHeader.setSortColumn(col, descending);
  }

  @Override
  public void stopEditing() {}

  @Override
  public void structureChanged(Object source) {
    if (tableHeader.columns.length == 0) {
      return;
    }

    if (autoSizeColumns) {
      sizeToFit();
    }
  }

  public boolean updateColumnSizes(int width, int height) {
    if ((Math.abs(oldWidth - width) > 5)
        || ((tableHeader.getGridViewType() == GridViewType.HORIZONTAL_WRAP) && (Math.abs(oldHeight - height) > 5))) {
      oldWidth  = width;
      oldHeight = height;

      if (tableHeader.isAutoSizedColumns()) {
        return true;
      }

      boolean changed = true;

      if (tableHeader.isGridView()) {
        changed = tableHeader.handleGridView(width, height, getGridRowHeight(), false);
      } else {
        int leftOver = TableHelper.calculateColumnSizes(this, tableHeader.getColumns(), width, true);

        if ((leftOver < 0) &&!horizontalScroll) {
          tableHeader.reduceColumnSizes(getItemRenderer(), -leftOver);
        }
      }

      columnSizesInitialized = true;

      if (changed && autoSizeRows) {
        sizeRowsToFit();
      }

      return changed;
    }

    return false;
  }

  @SuppressLint("ClickableViewAccessibility")
  protected class HorizontalScrollingTableComponentLayout extends HorizontalScrollViewEx implements iFlingSupport {
    TableViewLayout table;

    public HorizontalScrollingTableComponentLayout(Context context) {
      super(context);
      table = new TableViewLayout(context);
      super.addView(table, -1, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
      setFillViewport(true);
    }

    @Override
    public void addView(View child) {
      table.addView(child);
    }

    @Override
    public void addView(View child, android.view.ViewGroup.LayoutParams params) {
      table.addView(child, params);
    }

    @Override
    public void addView(View child, int index, android.view.ViewGroup.LayoutParams params) {
      table.addView(child, index, params);
    }

    @Override
    public void addView(View child, int width, int height) {
      table.addView(child, width, height);
    }

    @Override
    public void setRowFooter(iScrollerSupport footerView) {
      getListView().setRowFooter(footerView);
    }

    @Override
    public void setRowHeader(iScrollerSupport headerView) {
      getListView().setRowHeader(headerView);
    }
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
      if (tableHeader.isGridView()) {
        if ((MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.EXACTLY)
            && (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY)) {
          int width  = MeasureSpec.getSize(widthMeasureSpec);
          int height = MeasureSpec.getSize(heightMeasureSpec);
          int w      = Math.max(width - getPaddingLeft() - getPaddingRight(), 0);
          int h      = Math.max(height - getPaddingTop() - getPaddingBottom(), 0);

          table.table.updateColumnSizes(w, h);
        }
      }

      super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
      super.onLayout(changed, l, t, r, b);

      if ((tableHeader.getGridViewType() == GridViewType.VERTICAL_WRAP) && (getChildCount() > 0)) {
        View v = getChildAt(0);

        v.layout(v.getLeft(), v.getTop(), r - getPaddingRight(), view.getBottom());
      }
    }

    @Override
    public void setFlingGestureListener(OnGestureListener flingGestureListener) {
      table.setFlingGestureListener(flingGestureListener);
    }

    @Override
    public void setOnTouchListener(OnTouchListener l) {
      table.setOnTouchListener(l);
    }
  }


  @Override
  public boolean isScrolling() {
    if (view instanceof HorizontalScrollingTableComponentLayout) {
      if (((HorizontalScrollingTableComponentLayout) view).isScrolling()) {
        return true;
      }
    }

    return getTableViewLayout().isScrolling();
  }

  @Override
  public void setScrollingEdgePainter(UIScrollingEdgePainter painter) {
    if (view instanceof HorizontalScrollingTableComponentLayout) {
      ((HorizontalScrollingTableComponentLayout) view).setScrollingEdgePainter(painter);
    }

    getTableViewLayout().setScrollingEdgePainter(painter);
  }

  @Override
  public UIScrollingEdgePainter getScrollingEdgePainter() {
    if (view instanceof HorizontalScrollingTableComponentLayout) {
      return ((HorizontalScrollingTableComponentLayout) view).getScrollingEdgePainter();
    }

    return getTableViewLayout().getScrollingEdgePainter();
  }

  public void setAutoSizeRows(boolean autoSize) {
    autoSizeRows = autoSize;
  }
}
