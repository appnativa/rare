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

import com.appnativa.rare.Platform;
import com.appnativa.rare.platform.ActionHelper;
import com.appnativa.rare.platform.swing.ui.util.SwingGraphics;
import com.appnativa.rare.platform.swing.ui.util.SwingHelper;
import com.appnativa.rare.spot.ItemDescription;
import com.appnativa.rare.spot.Table;
import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.Column;
import com.appnativa.rare.ui.Container;
import com.appnativa.rare.ui.FontUtils;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.ui.UIPoint;
import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.iListHandler;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformGraphics;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.iPlatformItemRenderer;
import com.appnativa.rare.ui.iScrollerSupport;
import com.appnativa.rare.ui.iTableModel;
import com.appnativa.rare.ui.painter.PaintBucket;
import com.appnativa.rare.ui.painter.UIScrollingEdgePainter;
import com.appnativa.rare.ui.renderer.ListItemRenderer;
import com.appnativa.rare.ui.table.TableStyle.BackgroundHighlight;
import com.appnativa.rare.ui.table.aTableHeader.SizeType;
import com.appnativa.rare.viewer.TableViewer;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.spot.SPOTSet;
import com.appnativa.util.FilterableList;
import com.appnativa.util.iFilterableList;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import java.util.Comparator;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Locale;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;

public class TableComponent extends Container implements iTableComponent, iScrollerSupport {
  protected final static int[]                          EMPTY_INTS = new int[0];
  protected int                                         sortColumn = -1;
  protected boolean                                     descending = true;
  protected boolean                                     horizontalScroll;
  protected IdentityHashMap<RenderableDataItem, Object> itemMap;
  protected TableHeader                                 tableHeader;
  protected iTableModel                                 tableModel;
  protected TablePainter                                tablePainter;
  protected TableStyle                                  tableStyle;
  protected TableView                                   tableView;
  private TableType                                     tableType;
  protected static AbstractAction                       selectPreviousRow;
  protected static AbstractAction                       selectNextRow;
  protected static AbstractAction                       previousEditableCellAction;
  protected static AbstractAction                       nextEditableCellAction;
  protected static AbstractAction                       enterKeyActionNextRow;
  protected static AbstractAction                       enterKeyAction;

  static {
    selectPreviousRow = new AbstractAction("selectPreviousRow") {
      @Override
      public void actionPerformed(ActionEvent e) {
        Object o = (e == null)
                   ? null
                   : e.getSource();

        if (o instanceof JTable) {
          JTable table = (JTable) o;

          if (table.isEditing()) {
            table.getCellEditor().stopCellEditing();

            return;
          }

          int         row = table.getSelectedRow();
          TableViewer tv  = (TableViewer) Platform.getWidgetForComponent(table);

          if (tv.getSelectionModelGroup() != null) {
            iListHandler comp = tv.getSelectionModelGroup().selectPreviousRow(row, table.getSelectionModel());

            if ((comp != null) && (comp.getListComponent().getView() != table)) {
              int col = table.getSelectedColumn();

              if (col != -1) {
                ((JTable) comp.getListComponent()).setColumnSelectionInterval(col, col);
              }
            }
          } else {
            Action a = table.getActionMap().get("Rare.origSelectPreviousRow");

            a.actionPerformed(e);
          }
        }
      }
    };
    selectNextRow = new AbstractAction("selectNextRow") {
      @Override
      public void actionPerformed(ActionEvent e) {
        Object o = (e == null)
                   ? null
                   : e.getSource();

        if (o instanceof JTable) {
          JTable table = (JTable) o;

          if (table.isEditing()) {
            table.getCellEditor().stopCellEditing();

            return;
          }

          int         row = table.getSelectedRow();
          TableViewer tv  = (TableViewer) Platform.getWidgetForComponent(table);

          if (tv.getSelectionModelGroup() != null) {
            iListHandler comp = tv.getSelectionModelGroup().selectNextRow(row, table.getSelectionModel());

            if ((comp != null) && (comp.getListComponent().getView() != table)) {
              int col = table.getSelectedColumn();

              if (col != -1) {
                ((JTable) comp.getListComponent()).setColumnSelectionInterval(col, col);
              }
            }
          } else {
            Action a = table.getActionMap().get("Rare.origSelectNextRow");

            a.actionPerformed(e);
          }
        }
      }
    };
    previousEditableCellAction = new AbstractAction("Rare.previousEditableCellAction") {
      @Override
      public void actionPerformed(ActionEvent e) {
        Object o = (e == null)
                   ? null
                   : e.getSource();

        if (o instanceof JTable) {
          movetoPreviousEditableCell((JTable) o);
        }
      }
    };
    nextEditableCellAction = new AbstractAction("Rare.nextEditableCellAction") {
      @Override
      public void actionPerformed(ActionEvent e) {
        Object o = (e == null)
                   ? null
                   : e.getSource();

        if (o instanceof JTable) {
          movetoNextEditableCell((JTable) o);
        }
      }
    };
    enterKeyActionNextRow = new AbstractAction("Rare.enterKeyAction") {
      @Override
      public void actionPerformed(ActionEvent e) {
        enterKeyAction.actionPerformed(e);

        Object o = (e == null)
                   ? null
                   : e.getSource();

        if (o instanceof JTable) {
          JTable table = (JTable) o;

          if (table.isEditing()) {
            table.getCellEditor().stopCellEditing();

            return;
          }

          int orow = table.getSelectedRow();
          int row  = orow + 1;

          if (row >= table.getRowCount()) {
            row = 0;
          }

          if (orow != row) {
            Action a = table.getActionMap().get("selectNextRow");

            if (a != null) {
              a.actionPerformed(e);
            }
          }
        }
      }
    };
    enterKeyAction = new AbstractAction("Rare.enterKeyAction") {
      @Override
      public void actionPerformed(ActionEvent e) {
        Object o = (e == null)
                   ? null
                   : e.getSource();

        if (o instanceof TableView) {
          TableView table = (TableView) o;

          if (table.isEditing()) {
            table.getCellEditor().stopCellEditing();

            return;
          }

          int row = table.getSelectedRow();

          if (row == -1) {
            return;
          }

          RenderableDataItem item = table.getItemAt(row);

          if (!item.isEnabled() ||!item.isSelectable()) {
            return;
          }

          if (table.actionListener != null) {
            table.actionListener.actionPerformed(new com.appnativa.rare.ui.event.ActionEvent(table, "enter"));
          }
        }
      }
    };
  }

  public TableComponent(Table cfg) {
    this(new TableScrollPane(), cfg);
  }

  public TableComponent(TableView view, Table cfg) {
    super(view);
    initialize(view, cfg);
  }

  public TableComponent(TableScrollPane scrollpane, Table cfg) {
    super(scrollpane);
    initialize(scrollpane.table, cfg);
  }

  @Override
  public int convertModelIndexToView(int index) {
    return tableView.getJTable().convertColumnIndexToView(index);
  }

  @Override
  public int convertViewIndexToModel(int index) {
    return tableView.getJTable().convertColumnIndexToModel(index);
  }

  @Override
  public Column createColumn(String title, Object value, int type, Object data, iPlatformIcon icon) {
    return new Column(title, value, type, data, icon);
  }

  protected void createTableModel() {
    tableModel = new DataItemTableModel();
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

    tableStyle  = null;
    tableModel  = null;
    tableHeader = null;
    tableView   = null;
    tableStyle  = null;
  }

  @Override
  public UIRectangle getCellRect(int row, int column, boolean includeMargin) {
    Rectangle r = tableView.getCellRect(row, column, includeMargin);

    return new UIRectangle(r.x, r.y, r.width, r.height);
  }

  public Column getColumnAt(int index) {
    return tableHeader.getColumnAt(index);
  }

  @Override
  public int getColumnCount() {
    return tableModel.getColumnCount();
  }

  @Override
  public int getColumnIndexAt(float x, float y) {
    return tableHeader.getColumnIndexAt(x, y);
  }

  @Override
  public int getFirstVisibleColumnIndex() {
    return tableHeader.getFirstVisibleColumnInView();
  }

  @Override
  public UIFont getFontEx() {
    if ((tableView != null) && tableView.isFontSet()) {
      return UIFont.fromFont(tableView.getFont());
    }

    return null;
  }

  @Override
  public GridViewType getGridViewType() {
    return tableHeader.getGridViewType();
  }

  @Override
  public iPlatformItemRenderer getItemRenderer() {
    return tableView.getItemRenderer();
  }

  @Override
  public int getLastVisibleColumnIndex() {
    return tableHeader.getLastVisibleColumnInView();
  }

  @Override
  protected void getMinimumSizeEx(UIDimension size) {
    tableHeader.getMinimumSize(size);

    int rc = tableView.getMinimumVisibleRowCount();

    if (rc == 0) {
      rc = 1;
    }

    int rh = tableView.getRowHeight();

    if (rh == 0) {
      rh = FontUtils.getDefaultLineHeight();
    }

    if (tableHeader.isVisible()) {
      size.height += rc * rh;
    } else {
      size.height = rc * rh;
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
  protected void getPreferredSizeEx(UIDimension size, float maxWidth) {
    Dimension d = view.getPreferredSize();

    size.width  = d.width;
    size.width  += 16;
    size.height = d.height;
  }

  public int getRowHeight() {
    return tableView.getRowHeight();
  }

  public TableScrollPane getScrollPane() {
    return (TableScrollPane) view;
  }

  @Override
  public int getSelectedColumn() {
    return tableView.getJTable().getSelectedColumn();
  }

  @Override
  public int getSelectedColumnCount() {
    return tableView.getJTable().getSelectedColumnCount();
  }

  @Override
  public int[] getSelectedColumns() {
    return tableView.getJTable().getSelectedColumns();
  }

  public int[] getSelectedRows() {
    return tableView.getJTable().getSelectedRows();
  }

  @Override
  public int getSortColumn() {
    return sortColumn;
  }

  public iPlatformComponent getTable() {
    return tableView.getListComponent();
  }

  @Override
  public boolean isMultiTableComponent() {
    return false;
  }

  @Override
  public aTableHeader getTableHeader() {
    return tableHeader;
  }

  public iTableModel getTableModel() {
    return tableModel;
  }

  TablePainter getTablePainter() {
    if (tablePainter == null) {
      if (tableStyle != null) {
        tablePainter = new TablePainter(this);
        tablePainter.setLineStroke(SwingHelper.getStroke(tableStyle.gridLineStroke));
      }
    }

    return tablePainter;
  }

  public TableView getTableView() {
    return tableView;
  }

  @Override
  public UIRectangle getViewRect() {
    Rectangle r = tableView.getViewport().getViewRect();

    return new UIRectangle(r.x, r.y, r.width, r.height);
  }

  @Override
  public int getVisibleColumnCount() {
    return tableHeader.getVisibleColumnCount();
  }

  @Override
  public boolean hasHadInteraction() {
    return super.hasHadInteraction() || tableHeader.hasHadInteraction();
  }

  @Override
  public PaintBucket getFocusPaint(iPlatformGraphics g, PaintBucket def) {
    if (!focusPainted ||!tableView.isFocusOwner() || ((SwingGraphics) g).getSwingComponent() != view) {
      return null;
    }

    return (focusPaint == null)
           ? def
           : focusPaint;
  }

  protected void initialize(TableView table, Table cfg) {
    tableView   = table;
    tableHeader = table.getHeader();
    tableHeader.setTableComponent(this);
    tableView.setAutoSizeRows(cfg.autoSizeRowsToFit.booleanValue());
    horizontalScroll = isHorizontalScollEnabled(cfg);

    boolean grid = cfg.displayAsGridView.booleanValue();

    if (grid) {
      setGridViewType(GridViewType.valueOf(cfg.gridViewType.stringValue().toUpperCase(Locale.US)));
      table.getJTable().setColumnSelectionAllowed(true);
    }

    if (!cfg.showStandardColumnHeader.booleanValue() || grid) {
      tableView.setHeaderVisible(false);
    }

    if (!grid) {
      table.setAutoSizeColumns(cfg.autoSizeColumnsToFit.booleanValue());

      if (cfg.headerHeight.spot_valueWasSet()) {
        tableHeader.setHeaderHeight(cfg.headerHeight.getValue());
      }
    }

    int autoResizeMode;

    switch(cfg.autoResizeMode.intValue()) {
      case Table.CAutoResizeMode.none :
        autoResizeMode = JTable.AUTO_RESIZE_OFF;

        break;

      case Table.CAutoResizeMode.resize_last_column :
        autoResizeMode = JTable.AUTO_RESIZE_LAST_COLUMN;

        break;

      case Table.CAutoResizeMode.resize_next_column :
        autoResizeMode = JTable.AUTO_RESIZE_NEXT_COLUMN;

        break;

      case Table.CAutoResizeMode.resize_subsequent_columns :
        autoResizeMode = JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS;

        break;

      default :
        autoResizeMode = JTable.AUTO_RESIZE_ALL_COLUMNS;

        break;
    }

    if (grid) {
      autoResizeMode = JTable.AUTO_RESIZE_OFF;
    }

    if (cfg.fgColor.getValue() == null) {
      setForeground(ColorUtils.getListForeground());
    }

    if (cfg.bgColor.getValue() == null) {
      setBackground(ColorUtils.getListBackground());
    }

    table.setAutoResizeMode(autoResizeMode);
    createTableModel();

    ActionMap am = table.getActionMap();
    InputMap  im = table.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

    am.put("Rare.origSelectNextRow", am.get("selectNextRow"));
    am.put("Rare.origSelectPreviousRow", am.get("selectPreviousRow"));
    am.put("selectNextRow", selectNextRow);
    am.put("selectPreviousRow", selectPreviousRow);

    boolean changeTabKey = cfg.tabKeyAction.intValue() != Table.CTabKeyAction.next_cell;

    switch(cfg.enterKeyAction.intValue()) {
      case Table.CEnterKeyAction.both :
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), enterKeyActionNextRow.getValue(Action.NAME));
        am.put(enterKeyActionNextRow.getValue(Action.NAME), enterKeyActionNextRow);

        break;

      case Table.CEnterKeyAction.action_event :
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), enterKeyAction.getValue(Action.NAME));
        am.put(enterKeyAction.getValue(Action.NAME), enterKeyAction);

        break;

      case Table.CEnterKeyAction.next_row :
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "selectNextRow");

        break;

      default :
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "none");

        break;
    }

    boolean tableeditable = false;
    SPOTSet columns       = cfg.columns;
    int     len           = columns.size();

    for (int i = 0; i < len; i++) {
      if (((ItemDescription) columns.getEx(i)).editable.booleanValue()) {
        tableeditable = true;

        break;
      }
    }

    if (!tableeditable) {
      im.put(KeyStroke.getKeyStroke("F2"), "none");
      im.put(KeyStroke.getKeyStroke("ESCAPE"), "none");
    } else {
      changeTabKey = cfg.tabKeyAction.intValue() == Table.CTabKeyAction.next_component;

      if (!changeTabKey) {
        im.put(KeyStroke.getKeyStroke("TAB"), nextEditableCellAction.getValue(Action.NAME));
        im.put(KeyStroke.getKeyStroke("shift TAB"), previousEditableCellAction.getValue(Action.NAME));
        am.put(nextEditableCellAction.getValue(Action.NAME), nextEditableCellAction);
        am.put(previousEditableCellAction.getValue(Action.NAME), previousEditableCellAction);
      }
    }

    if (changeTabKey) {
      Action a = ActionHelper.getNextComponentAction();

      am.put(a.getValue(Action.NAME), a);
      im.put(KeyStroke.getKeyStroke("TAB"), a.getValue(Action.NAME));
      a = ActionHelper.getPreviousComponentAction();
      am.put(a.getValue(Action.NAME), a);
      im.put(KeyStroke.getKeyStroke("shift TAB"), a.getValue(Action.NAME));
    }
  }

  @Override
  public boolean isAutoSizeRows() {
    return tableView.isAutoSizeRows();
  }

  @Override
  public boolean isEditing() {
    return false;
  }

  @Override
  public boolean isSortDescending() {
    return descending;
  }

  @Override
  public void moveColumn(int column, int targetColumn) {
    tableHeader.moveColumn(column, targetColumn);
  }

  @Override
  public void repaint() {
    super.repaint();
    tableHeader.repaint();
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
  public void revalidate() {
    tableHeader.revalidate();
    super.revalidate();
  }

  public void setAutoSizeRowsToFit(boolean autoSize) {
    tableView.setAutoSizeRows(autoSize);
  }

  @Override
  public void setColumnIcon(int col, iPlatformIcon icon) {
    if ((col < 0) || (col > getColumnCount())) {
      return;
    }

    tableHeader.setColumnIcon(col, icon);
  }

  @Override
  public void setColumnTitle(int col, CharSequence title) {
    if ((col < 0) || (col > getColumnCount())) {
      return;
    }

    tableHeader.setColumnTitle(col, title);
  }

  @Override
  public boolean isScrolling() {
    return getScrollPane().isScrolling();
  }

  @Override
  public boolean isAtLeftEdge() {
    return getScrollPane().isAtLeftEdge();
  }

  @Override
  public boolean isAtRightEdge() {
    return getScrollPane().isAtRightEdge();
  }

  @Override
  public boolean isAtTopEdge() {
    return getScrollPane().isAtTopEdge();
  }

  @Override
  public boolean isAtBottomEdge() {
    return getScrollPane().isAtBottomEdge();
  }

  @Override
  public void scrollToBottomEdge() {
    getScrollPane().scrollToBottomEdge();
  }

  @Override
  public void scrollToLeftEdge() {
    getScrollPane().scrollToLeftEdge();
  }

  @Override
  public void scrollToRightEdge() {
    getScrollPane().scrollToRightEdge();
  }

  @Override
  public void scrollToTopEdge() {
    getScrollPane().scrollToTopEdge();
  }

  @Override
  public void moveLeftRight(boolean left, boolean block) {
    getScrollPane().moveLeftRight(left, block);
  }

  @Override
  public void moveUpDown(boolean up, boolean block) {
    getScrollPane().moveUpDown(up, block);
  }

  @Override
  public UIPoint getContentOffset() {
    return getScrollPane().getContentOffset();
  }

  @Override
  public void setScrollingEdgePainter(UIScrollingEdgePainter painter) {
    getScrollPane().setScrollingEdgePainter(painter);
  }

  @Override
  public UIScrollingEdgePainter getScrollingEdgePainter() {
    return getScrollPane().getScrollingEdgePainter();
  }

  @Override
  public void setColumnVisible(int col, boolean visible) {
    if ((col < 0) || (col > getColumnCount())) {
      return;
    }

    Column c = getColumnAt(col);

    if (c.isVisible() != visible) {
      c.setVisible(visible);

      if (c.getWidth() == 0) {
        tableView.reupdateColumnSizes();
      } else {
        tableHeader.updateColumnModel(col);
      }
    }
  }

  @Override
  public void setFont(UIFont f) {
    getTable().setFont(f);
  }

  @Override
  public void setForeground(UIColor fg) {
    getTable().setForeground(fg);
  }

  @Override
  public void setGridViewType(GridViewType type) {
    tableHeader.setGridViewType(type);

    if (type == GridViewType.HORIZONTAL_WRAP) {
      tableView.getScrollPane().setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
      tableView.getScrollPane().setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    } else {
      tableView.getScrollPane().setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
      tableView.getScrollPane().setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    }
  }

  @Override
  public void setHeaderBackground(PaintBucket pb) {
    tableHeader.setComponentPainter(null);
    pb.install(tableHeader, true);
  }

  public void setRowHeight(int height) {
    tableView.setRowHeight(height);
  }

  @Override
  public void setRowHeight(int row, int height) {
    tableModel.get(row).setHeight(height);
    tableView.rowChanged(row);
  }

  @Override
  public void setSelectedColumnIndex(int index) {
    tableView.setSelectedColumnIndex(index);
  }

  @Override
  public void setSelectedColumnIndices(int[] indices) {
    tableView.setSelectedColumnIndices(indices);
  }

  @Override
  public void setShowHorizontalLines(boolean show) {
    tableStyle.showHorizontalLines = show;
    tableView.setShowDivider(tableStyle.showHorizontalLines);
  }

  public void setShowLastItemBorder(boolean show) {}

  @Override
  public void setShowVerticalLines(boolean show) {
    tableStyle.showVerticalLines = show;
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

    if (style.backgroundHilite != null) {
      if (style.backgroundHiliteColor == null) {
        style.backgroundHiliteColor = ColorUtils.getListDividerColor();
      }

      if (style.backgroundHilite == BackgroundHighlight.ROW) {
        tableView.setAlternatingRowColor(style.backgroundHiliteColor);
      } else {
        tableView.setAlternatingColumnColor(style.backgroundHiliteColor);
      }
    }

    if (style.headerMarginColor != null) {
      tableHeader.setMarginColor(style.headerMarginColor);
    }

    if (style.headerBottomMarginColor != null) {
      tableHeader.setBottomMarginColor(style.headerBottomMarginColor);
    }

    if (tablePainter != null) {
      tablePainter.setLineStroke(SwingHelper.getStroke(tableStyle.gridLineStroke));
    }

    JTable table = getTableView().getJTable();

    table.getTableHeader().setReorderingAllowed(style.columnReorderingAllowed);
    table.getTableHeader().setResizingAllowed(style.columnResizingAllowed);

    if (!tableHeader.isGridView()) {
      if (style.columnSelectionAllowed) {
        table.setColumnSelectionAllowed(true);
        ((ListItemRenderer) getItemRenderer()).setHandlesSelection(true);
      }
    }

    if ((tableType != null) && (tableType != TableType.MAIN)) {
      getItemRenderer().setSelectionPainted(tableStyle.rowHeaderFooterSelectionPainted);
    }
  }

  @Override
  public void setTable() {
    tableHeader.setColumns(tableModel.getColumns());
    tableView.setListModel(tableModel);
    tableView.setShowDivider(tableStyle.showHorizontalLines);
  }

  @Override
  public void setWidget(iWidget widget) {
    super.setWidget(widget);
    tableHeader.setWidget(widget);
    tableView.getListComponent().setWidget(widget);
    tableModel.setWidget(widget);
  }

  public void sizeColumnsToFit() {
    tableHeader.sizeColumnsToFitTableData();
  }

  public void sizeColumnToFit(int col) {
    Column c = getColumnAt(col);
    int    w = tableHeader.calculateColumnWidth(tableModel, col, c, Short.MAX_VALUE, new UIDimension(), SizeType.FIT);

    c.setWidth(w);
    tableHeader.updateColumnModel(col);
  }

  @Override
  public void sizeRowsToFit() {
    tableView.sizeRowsToFit(0, getColumnCount() - 1);
    revalidate();
    repaint();
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

  public void sortEx(Comparator c) {
    tableModel.sort(c);
  }

  public void sortEx(int col, boolean descending, boolean useLinkedData) {
    sortColumn      = col;
    this.descending = descending;
    tableModel.sort(col, descending, useLinkedData);
    tableHeader.setSortColumn(col, descending);
  }

  @Override
  public void stopEditing() {}

  protected static boolean isHorizontalScollEnabled(Table cfg) {
    if (cfg.autoResizeMode.intValue() == Table.CAutoResizeMode.none) {
      return true;
    }

    if ((cfg.getScrollPane() != null) && cfg.getScrollPane().isHorizontalScrollEnabled()) {
      return true;
    }

    return false;
  }

  public static void movetoNextEditableCell(JTable table) {
    int row;
    int col;

    if (table.isEditing()) {
      row = table.getEditingRow();
      col = table.getEditingColumn();
      table.getCellEditor().stopCellEditing();
    } else {
      row = table.getSelectedRow();
      col = table.getSelectedColumn();
    }

    int orow   = row;
    int ocol   = col;
    int rcount = table.getRowCount();
    int ccount = table.getColumnCount();

    if ((row != -1) && (col != -1)) {
      do {
        col++;

        if (col >= ccount) {
          row++;
          col = 0;
        }

        if (row >= rcount) {
          row = 0;
        }

        if ((col == ocol) && (row == orow)) {
          break;
        }

        if (table.isCellEditable(row, col)) {
          table.setRowSelectionInterval(row, row);
          table.setColumnSelectionInterval(col, col);

          TableViewer tv = (TableViewer) Platform.getWidgetForComponent(table);
          Rectangle   r  = table.getCellRect(row, col, true);

          if (tv == null) {
            table.scrollRectToVisible(r);
          } else {
            ((JTable) tv.getDataComponent()).scrollRectToVisible(r);
          }

          break;
        }
      } while(true);
    }
  }

  public static void movetoPreviousEditableCell(JTable table) {
    int row;
    int col;

    if (table.isEditing()) {
      row = table.getEditingRow();
      col = table.getEditingColumn();
      table.getCellEditor().stopCellEditing();
    } else {
      row = table.getSelectedRow();
      col = table.getSelectedColumn();
    }

    int orow   = row;
    int ocol   = col;
    int rcount = table.getRowCount();
    int ccount = table.getColumnCount();

    if ((row != -1) && (col != -1)) {
      do {
        col--;

        if (col < 0) {
          row--;
          col = ccount - 1;
        }

        if (row < 0) {
          row = rcount - 1;
        }

        if ((col == ocol) && (row == orow)) {
          break;
        }

        if (table.isCellEditable(row, col)) {
          table.setRowSelectionInterval(row, row);
          table.setColumnSelectionInterval(col, col);

          break;
        }
      } while(true);
    }
  }

  @Override
  public iTableComponent getMainTable() {
    return this;
  }

  @Override
  public iTableComponent getRowHeaderTable() {
    return null;
  }

  @Override
  public iTableComponent getRowFooterTable() {
    return null;
  }

  @Override
  public iTableComponent getColumnHeaderTable() {
    return null;
  }

  @Override
  public iTableComponent getColumnFooterTable() {
    return null;
  }

  @Override
  public boolean isMainTable() {
    return (tableType == null) || (tableType == TableType.MAIN);
  }

  @Override
  public TableType getTableType() {
    return tableType;
  }

  @Override
  public void setTableType(TableType type) {
    tableType = type;
    tableView.setTableType(type);
  }

  public boolean isSelectionPainted() {
    if (tableStyle == null) {
      return false;
    }

    if (!tableView.getItemRenderer().isSelectionPainted()) {
      return false;
    }

    if ((tableType == null) || (tableType == TableType.MAIN)) {
      return true;
    }

    return tableStyle.rowHeaderFooterSelectionPainted;
  }

  protected void multiTableSizeRowsTtFit(int rMin, int rMax) {
    boolean                  needSizeToFitCall = tableView.needSizeToFitCall;
    MultiTableTableComponent mt                =
      (MultiTableTableComponent) ((TableViewer) getWidget()).getTableComponent();
    MultiDataItemTableModel  tm                = (MultiDataItemTableModel) mt.getModel();
    TableComponent           tc                = (TableComponent) mt.getRowHeaderTable();
    TableView                htable            = (tc == null)
            ? null
            : tc.getTableView();

    tc = (TableComponent) mt.getRowFooterTable();

    TableView ftable = (tc == null)
                       ? null
                       : tc.getTableView();

    if (htable != null) {
      if (!needSizeToFitCall) {
        needSizeToFitCall = htable.needSizeToFitCall;
      }

      htable.needSizeToFitCall = false;
    }

    if (ftable != null) {
      if (!needSizeToFitCall) {
        needSizeToFitCall = ftable.needSizeToFitCall;
      }

      ftable.needSizeToFitCall = false;
    }

    tableView.needSizeToFitCall = false;

    if (needSizeToFitCall) {
      int len = tm.getRowCount();

      rMax = Math.min(len, rMax + 1);

      for (int i = rMin; i < rMax; i++) {
        RenderableDataItem rowItem = tm.getRow(i);
        int                h       = TableHelper.calculateRowHeight(this, getItemRenderer(), tm, i, tm.getColumnsEx(),
                                       false, getRowHeight());

        h += 4;
        rowItem.setHeight(h);
      }
    }
  }

  /**
   * Get s the row height for a row that spans multiple tables. Should only be
   * called on the main table
   */
  protected int getMultiTableRowHeight(int row) {
    boolean                  needSizeToFitCall = tableView.needSizeToFitCall;
    MultiTableTableComponent mt                =
      (MultiTableTableComponent) ((TableViewer) getWidget()).getTableComponent();
    MultiDataItemTableModel  tm                = (MultiDataItemTableModel) mt.getModel();
    TableComponent           tc                = (TableComponent) mt.getRowHeaderTable();
    TableView                htable            = (tc == null)
            ? null
            : tc.getTableView();

    tc = (TableComponent) mt.getRowFooterTable();

    TableView ftable = (tc == null)
                       ? null
                       : tc.getTableView();

    if (htable != null) {
      if (!needSizeToFitCall) {
        needSizeToFitCall = htable.needSizeToFitCall;
      }

      htable.needSizeToFitCall = false;
    }

    if (ftable != null) {
      if (!needSizeToFitCall) {
        needSizeToFitCall = ftable.needSizeToFitCall;
      }

      ftable.needSizeToFitCall = false;
    }

    tableView.needSizeToFitCall = false;

    if (needSizeToFitCall) {
      int len = tm.getRowCount();

      for (int i = 0; i < len; i++) {
        RenderableDataItem rowItem = tm.getRow(i);

        rowItem.setHeight(0);
      }

      if (htable != null) {
        htable.resizeAndRepaint();
      }

      if (ftable != null) {
        htable.resizeAndRepaint();
      }

      tableView.resizeAndRepaint();
    }

    RenderableDataItem rowItem = tm.getRow(row);
    int                h       = rowItem.getHeight();

    if (h < 1) {
      h = TableHelper.calculateRowHeight(this, getItemRenderer(), tm, row, tm.getColumnsEx(), false, getRowHeight());
      h += 4;
      rowItem.setHeight(h);
    }

    return h;
  }

  protected void setMultiTableRowSizes() {
    MultiTableTableComponent mt     = (MultiTableTableComponent) ((TableViewer) getWidget()).getTableComponent();
    MultiDataItemTableModel  tm     = (MultiDataItemTableModel) mt.getModel();
    TableComponent           tc     = (TableComponent) mt.getRowHeaderTable();
    TableView                htable = (tc == null)
                                      ? null
                                      : tc.getTableView();
    TableView                table  = getTableView();

    tc = (TableComponent) mt.getRowFooterTable();

    TableView ftable = (tc == null)
                       ? null
                       : tc.getTableView();

    if (htable != null) {
      htable.needSizeToFitCall = false;
      htable.setResizeRepaintBlocked(true);
    }

    if (ftable != null) {
      ftable.needSizeToFitCall = false;
      ftable.setResizeRepaintBlocked(true);
    }

    tableView.needSizeToFitCall = false;

    int len = tm.getRowCount();

    for (int i = 0; i < len; i++) {
      RenderableDataItem rowItem = tm.getRow(i);
      int                h       = rowItem.getHeight();

      if (h < 1) {
        h = TableHelper.calculateRowHeight(this, getItemRenderer(), tm, i, tm.getColumnsEx(), false, getRowHeight());
        h += 4;
        rowItem.setHeight(h);
      }

      table.setRowHeight(i, h);

      if (ftable != null) {
        ftable.setRowHeight(i, h);
      }

      if (htable != null) {
        htable.setRowHeight(i, h);
      }
    }

    if (ftable != null) {
      ftable.setResizeRepaintBlocked(false);
    }

    if (htable != null) {
      htable.setResizeRepaintBlocked(false);
    }
  }
}
