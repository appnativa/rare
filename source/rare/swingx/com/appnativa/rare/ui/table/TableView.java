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

/**
 * Setup table view similar to list view so that we can copy/paste tree view to table tree view
 */
package com.appnativa.rare.ui.table;

import com.appnativa.rare.Platform;
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.platform.swing.plaf.BasicTableExUI;
import com.appnativa.rare.platform.swing.ui.EmptyListSelectionModel;
import com.appnativa.rare.platform.swing.ui.SelectiveListSelectionModel;
import com.appnativa.rare.platform.swing.ui.util.SwingHelper;
import com.appnativa.rare.platform.swing.ui.view.LabelRenderer;
import com.appnativa.rare.ui.CheckListManager;
import com.appnativa.rare.ui.Column;
import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.FontUtils;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.UIStroke;
import com.appnativa.rare.ui.aListHandler;
import com.appnativa.rare.ui.event.ActionEvent;
import com.appnativa.rare.ui.event.ChangeEvent;
import com.appnativa.rare.ui.event.ItemChangeEvent;
import com.appnativa.rare.ui.event.iActionListener;
import com.appnativa.rare.ui.event.iChangeListener;
import com.appnativa.rare.ui.event.iDataModelListener;
import com.appnativa.rare.ui.event.iItemChangeListener;
import com.appnativa.rare.ui.iListHandler.SelectionMode;
import com.appnativa.rare.ui.iListHandler.SelectionType;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.iPlatformListView;
import com.appnativa.rare.ui.iPlatformRenderingComponent;
import com.appnativa.rare.ui.iRenderingComponent;
import com.appnativa.rare.ui.iScrollerSupport;
import com.appnativa.rare.ui.iTableModel;
import com.appnativa.rare.ui.renderer.ListItemRenderer;
import com.appnativa.rare.ui.renderer.UILabelRenderer;
import com.appnativa.rare.ui.renderer.aListItemRenderer;
import com.appnativa.rare.ui.table.iTableComponent.GridViewType;
import com.appnativa.rare.ui.table.iTableComponent.TableType;
import com.appnativa.rare.viewer.TableViewer;
import com.appnativa.util.IntList;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.dnd.Autoscroll;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.ListSelectionModel;
import javax.swing.event.EventListenerList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

public class TableView extends JTable
        implements iPlatformListView, MouseListener, MouseMotionListener, iDataModelListener, FocusListener,
                   Autoscroll {
  protected static int                INDICATOR_SLOP = ScreenUtils.PLATFORM_PIXELS_4;
  protected static final int          PAD_SIZE       = 0;
  protected static final int          ICON_GAP       = ScreenUtils.PLATFORM_PIXELS_4;
  boolean                             editing;
  int                                 leftOffset;
  int                                 oldWidth;
  int                                 pressedRow;
  protected int                       currentSelection = -1;
  protected int                       hilightIndex     = -1;
  protected int                       indicatorHeight  = 0;
  protected int                       indicatorWidth   = 0;
  protected int                       popupIndex       = -1;
  protected boolean                   selectable       = true;
  protected iActionListener           actionListener;
  protected boolean                   alternatingColumns;
  protected UIColor                   alternatingRowColor;
  protected boolean                   autoHilight;
  protected boolean                   autoSizeColumns;
  protected boolean                   autoSizeRows;
  protected iItemChangeListener       changeListener;
  protected SelectionChangeMaintainer changeManager;
  protected CheckListManager          checkListManager;
  protected int                       checkboxHeight;
  protected int                       checkboxWidth;
  protected boolean                   columnSizesInitialized;
  protected boolean                   disableChangeEvent;
  protected TableHeader               header;
  protected boolean                   invisibleSelection;
  protected ListItemRenderer          itemRenderer;
  protected boolean                   linkedSelection;
  protected boolean                   needSizeToFitCall;
  protected int                       rightOffset;
  protected SelectionType             selectionType;
  protected boolean                   singleClickAction;
  protected Component                 table;
  protected boolean                   draggingAllowed;
  protected Point                     mousePressedPoint;
  protected long                      mousePressedTime;
  protected boolean                   rowHeightSet;
  protected int                       oldHeight;
  protected boolean                   keepSelectionVisible;
  protected int                       minimumVisibleRowCount;
  protected MouseListener             mouseOverideListener;
  protected boolean                   preferedSizeCalled;
  protected boolean                   resizeRepaintBlocked;
  protected int                       visibleRowCount;
  protected boolean                   mainTable = true;
  protected TableType                 tableType;

  public TableView() {
    super();
    setShowGrid(false);
    setOpaque(false);
    setBorder(null);
    setPreferredScrollableViewportSize(new Dimension(50, 50));
    setFillsViewportHeight(true);
    setSelectionModel(new SelectiveListSelectionModel(null));
    getSelectionModel().addListSelectionListener(this);
    addMouseListener(this);
    setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    getColumnModel().getSelectionModel().addListSelectionListener(this);
    setRowHeight(FontUtils.getDefaultLineHeight());
    addFocusListener(this);
    table = new Component(this);
  }

  public TableView(TableModel lm) {
    this();
    setModel(lm);
  }

  @Override
  public void addSelectionIndex(int index) {
    disableChangeEvent = true;
    getSelectionModel().addSelectionInterval(index, index);
    disableChangeEvent = false;
  }

  public boolean canChangeSelection(int rowIndex, int columnIndex, boolean toggle, boolean extend) {
    RenderableDataItem item = ((iTableModel) getModel()).get(rowIndex);

    if (!item.isSelectable()) {
      return false;
    }

    if (header.isColumnSelectionAllowed()) {
      item = item.getItemEx(columnIndex);

      return (item != null) && item.isSelectable();
    }

    return true;
  }

  @Override
  public void clearContextMenuIndex() {
    popupIndex = -1;
  }
  @Override
  public void columnMoved(TableColumnModelEvent e) {
    super.columnMoved(e);
    header.columnMoved(e.getFromIndex(), e.getToIndex());
  }
  
  public void columnResized() {
    TableColumn tc = getTableHeader().getResizingColumn();
    if (autoSizeRows) {
       tc = getTableHeader().getResizingColumn();

      if (tc != null) {
        header.columns[tc.getModelIndex()].setWidth(tc.getWidth());
      }

      needSizeToFitCall = true;
    }
    if(tc!=null) {
      header.columnSizeChanged(header.getViewIndexForColumnAt(tc.getModelIndex()));
    }
  }

  @Override
  public void contentsChanged(Object source) {
    if (header.isGridView()) {
      header.handleGridView(getParent().getWidth(), getParent().getHeight(), getGridRowHeight(), true);
    }

    if (autoSizeRows) {
      needSizeToFitCall = true;
    }
  }

  @Override
  public void contentsChanged(Object source, int index0, int index1) {
    sizeRowsToFit(index0, index1);
  }

  public void dispose() {
    header = null;
  }

  public static void fireActionEvent(EventListenerList listenerList, ActionEvent ae) {
    Object[] listeners = listenerList.getListenerList();

    // Process the listeners last to first, notifying
    // those that are interested in this event
    for (int i = listeners.length - 2; i >= 0; i -= 2) {
      if (listeners[i] == iActionListener.class) {
        ((iActionListener) listeners[i + 1]).actionPerformed(ae);
      }
    }
  }

  public static void fireChangeEvent(EventListenerList listenerList, ChangeEvent e) {
    Object[] listeners = listenerList.getListenerList();

    // Process the listeners last to first, notifying
    // those that are interested in this event
    for (int i = listeners.length - 2; i >= 0; i -= 2) {
      if (listeners[i] == iChangeListener.class) {
        ((iChangeListener) listeners[i + 1]).stateChanged(e);
      }
    }
  }

  @Override
  public void intervalAdded(Object source, int index0, int index1) {
  }

  @Override
  public void intervalRemoved(Object source, int index0, int index1, List<RenderableDataItem> removed) {
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    clickCheck(e, false);
  }

  @Override
  public void mouseDragged(MouseEvent e) {}

  @Override
  public void mouseEntered(MouseEvent e) {}

  @Override
  public void mouseExited(MouseEvent e) {}

  @Override
  public void mouseMoved(MouseEvent e) {
    if(isEnabled()) {
      int n = rowAtPoint(e.getPoint());
  
      if (n != hilightIndex) {
        hilightIndex = n;
  
        Rectangle r1 = getCellRect(n, 0, true);
        Rectangle r2 = getCellRect(n, getColumnCount() - 1, true);
  
        repaint(r1.union(r2));
      }
    }
  }

  @Override
  public void mousePressed(MouseEvent e) {
    if (isEnabled() && !e.isConsumed()) {
      header.tableHadInteraction();

      if (e.isPopupTrigger()) {
        popupIndex = rowAtPoint(e.getPoint());
      } else {
        mousePressedPoint = e.getLocationOnScreen();
        mousePressedTime  = e.getWhen();
        popupIndex=-1;
      }
    }
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    clickCheck(e, true);
  }

  @Override
  public iPlatformRenderingComponent prepareRendererForReuse(iPlatformRenderingComponent rc, int row, int col) {
    if (checkListManager != null) {
      ListItemLabelRenderer view = (ListItemLabelRenderer) ((UIListLabelRenderer) rc).getView();

      view.checkIcon = checkListManager.getRowIcon(row, ((iTableModel) getModel()).get(row));
    }

    rc.prepareForReuse(row, col);

    return rc;
  }

  public void repaintRow(int row) {
    Rectangle r1 = getCellRect(row, 0, true);
    Rectangle r2 = getCellRect(row, getColumnCount() - 1, true);

    repaint(r1.union(r2));
  }

  public void repaintRow(RenderableDataItem item) {
    int index = ((iTableModel) getModel()).indexForRow(item);

    if (index != -1) {
      repaintRow(index);
    }
  }

  public void repaintRows(int firstRow, int lastRow) {
    Rectangle r1 = getCellRect(firstRow, 0, true);
    Rectangle r2 = getCellRect(lastRow, getColumnCount() - 1, true);

    repaint(r1.union(r2));
  }

  public void reupdateColumnSizes() {
    oldWidth = 0;
    updateColumnSizes(getParent().getWidth(), getParent().getHeight());
  }

  public void rowChanged(int row) {
    repaintRow(row);
  }

  @Override
  public void scrollRowToTop(int row) {
    if (autoSizeRows) {
      setRowSizes();
    }

    Rectangle r = getCellRect(row, 0, true);

    if (r != null) {
      JViewport vp = getViewport();
      Point     p  = vp.getViewPosition();

      if (vp.getExtentSize().height - r.y > 0) {
        p.y = r.y;
        vp.setViewPosition(p);
      }
    }
  }

  @Override
  public void scrollRowToBottom(int row) {
    Rectangle r = getCellRect(row, 0, true);

    if (r != null) {
      JViewport vp = getViewport();
      Point     p  = vp.getViewPosition();
      int       y  = r.y + vp.getExtentSize().height - r.height;

      if (y > vp.getViewSize().height) {
        y = vp.getViewSize().height - vp.getExtentSize().height;
      }

      if (p.y != y) {
        p.y = y;
        vp.setViewPosition(p);
      }
    }
  }

  @Override
  public void scrollRowToVisible(int index) {
    if (autoSizeRows) {
      setRowSizes();
    }

    JViewport viewport = getViewport();
    Point     pt       = viewport.getViewPosition();
    int       col      = getTableHeader().getColumnModel().getColumnIndexAtX(pt.x);

    if (col == -1) {
      col = 0;
    }

    Rectangle r = getCellRect(index, col, true);

    scrollRectToVisible(r);
  }

  @Override
  public void selectAll() {
    int len = getRowCount();

    if (len > 0) {
      disableChangeEvent = true;

      ListSelectionModel sm = getSelectionModel();

      switch(sm.getSelectionMode()) {
        case ListSelectionModel.MULTIPLE_INTERVAL_SELECTION :
        case ListSelectionModel.SINGLE_INTERVAL_SELECTION :
          sm.clearSelection();
          sm.addSelectionInterval(0, len - 1);

          break;

        default :
          break;
      }

      disableChangeEvent = false;
    }
  }

  public void sizeToFit() {
    needSizeToFitCall = false;

    if (!columnSizesInitialized) {
      needSizeToFitCall = true;

      return;
    }

    if (header.isGridView()) {
      header.handleGridView(getViewport().getWidth(), getViewport().getHeight(), getGridRowHeight(), true);
    } else if (this.autoSizeColumns) {
      header.sizeColumnsToFitTableData();
    }

    if (autoSizeRows) {
      sizeRowsToFit(0, getRowCount() - 1);
    }

    revalidate();
    repaint();
  }

  @Override
  public void structureChanged(Object source) {
    tableChanged(new TableModelEvent(getModel(), TableModelEvent.HEADER_ROW));
  }

  public boolean updateColumnSizes(int width, int height) {
    if ((Math.abs(oldWidth - width) > 5)
        || ((header.getGridViewType() == GridViewType.HORIZONTAL_WRAP) && (Math.abs(oldHeight - height) > 5))) {
      oldWidth               = width;
      oldHeight              = height;
      columnSizesInitialized = true;

      if (header.isAutoSizedColumns()) {
        return false;
      }

      boolean updated = false;

      if (header.isGridView()) {
        updated = header.handleGridView(width, height, getGridRowHeight(), false);
      } else {
        int leftOver = TableHelper.calculateColumnSizes(table, header.getColumns(), width, true);

        if ((leftOver < 0) && (getAutoResizeMode() != JTable.AUTO_RESIZE_OFF)) {
          header.reduceColumnSizes(itemRenderer, -leftOver);
        }

        header.updateColumnModel();
        updated = true;
      }

      if (needSizeToFitCall) {
        sizeToFit();
        updated = true;
      } else if (autoSizeRows && (getAutoResizeMode() != JTable.AUTO_RESIZE_OFF)) {
        sizeRowsToFit(0, getRowCount() - 1);
        updated = true;
      }
      if(updated) {
        header.columnSizeChanged(-1);
      }
      return updated;
    } else {
      return false;
    }
  }

  @Override
  public void valueChanged(ListSelectionEvent e) {
    super.valueChanged(e);

    if (!e.getValueIsAdjusting() && (changeListener != null) &&!disableChangeEvent) {
      Object  oldValue = null;
      Object  newValue = null;
      boolean multiple = isMultipleSelectionAllowed();

      if (multiple) {
        if (changeManager == null) {
          changeManager = new SelectionChangeMaintainer();
        }

        changeManager.selectionChanged(e.getFirstIndex(), e.getLastIndex());
        newValue = changeManager.newSelections;
        oldValue = changeManager.oldSelections;
      } else {
        oldValue = currentSelection;
        newValue = e.getFirstIndex();
      }

      ItemChangeEvent ie = new ItemChangeEvent(this, oldValue, newValue);

      changeListener.itemChanged(ie);

      if (multiple) {
        changeManager.makeNewOld();
      } else {
        currentSelection = e.getFirstIndex();
      }
    }
  }

  public void willPaintRows(int rMin, int rMax) {}

  @Override
  public void setActionListener(iActionListener l) {
    actionListener = l;
  }

  public void setAlternatingColumnColor(UIColor color) {
    alternatingRowColor = color;
    alternatingColumns  = true;
  }

  @Override
  public void setAlternatingRowColor(UIColor color) {
    alternatingRowColor = color;
  }

  @Override
  public void setAutoHilight(boolean autoHilight) {
    if (this.autoHilight != autoHilight) {
      this.autoHilight = autoHilight;
      hilightIndex     = -1;

      if (autoHilight) {
        this.addMouseMotionListener(this);
      } else {
        this.removeMouseMotionListener(this);
      }
    }
  }

  public void setAutoSizeColumns(boolean autoSizeColumns) {
    this.autoSizeColumns = autoSizeColumns;

    if (autoSizeColumns) {
      sizeToFit();
    }
  }

  public void setAutoSizeRows(boolean autoSizeRows) {
    this.autoSizeRows = autoSizeRows;

    if (autoSizeRows) {
      sizeToFit();
    }
  }

  @Override
  public boolean isKeepSelectionVisible() {
    return keepSelectionVisible;
  }

  @Override
  public void setKeepSelectionVisible(boolean keepSelectionVisible) {
    this.keepSelectionVisible = keepSelectionVisible;
  }

  public void setCheckMarkBoxIcon(iPlatformIcon icon) {}

  public void setCheckMarkIcon(iPlatformIcon icon) {}

  public void setCheckedRows(int[] indices) {}

  @Override
  public void setDividerLine(UIColor color, UIStroke stroke) {
    if (stroke != null) {
      ((TableComponent) header.getTableComponent()).getTablePainter().setLineStroke(SwingHelper.getStroke(stroke));
    }
  }

  @Override
  public void setEditingMode(EditingMode mode) {
    // if (mode == null) {
    // mode = EditingMode.NONE;
    // }
    //
    // editingMode = mode;
    //
    // aListViewer lv = (aListViewer) Component.fromView(this).getWidget();
    //
    // draggingAllowed = (mode == EditingMode.REORDERING) || (mode ==
    // EditingMode.REORDERING_AND_SELECTION);
    // setMouseOverideListener(new
    // EditableGestureListener(lv.canDelete()));
  }

  public void setHeaderVisible(boolean visible) {
    getTableHeader().setVisible(visible);
  }

  public void setItemRenderer(ListItemRenderer lr) {
    itemRenderer = lr;
    lr.setRenderingComponent(new UIListLabelRenderer());
    setDefaultRenderer(RenderableDataItem.class, lr);

    if (header.isGridView()) {
      lr.setHandlesSelection(true);
    }
  }

  public void setLinkSelection(boolean linked) {}

  public void setListModel(iTableModel listModel) {
    if (getModel() == listModel) {
      listModel.refreshItems();
    } else {
      TableModel tm = getModel();

      if (tm instanceof iTableModel) {
        ((iTableModel) tm).removeDataModelListener(this);
      }

      setModel((TableModel) listModel);
      listModel.addDataModelListener(this);
    }
  }

  public void setRowChecked(int row, boolean checked) {}

  @Override
  public void setRowHeight(int height) {
    super.setRowHeight(height);
    rowHeight    = height;
    rowHeightSet = true;
  }

  @Override
  public void setSelectable(boolean selectable) {
    this.selectable = selectable;

    if (selectable) {
      if (selectionModel != null) {
        setSelectionModel(selectionModel);
      }
    } else {
      selectionModel = getSelectionModel();
      setSelectionModel(EmptyListSelectionModel.getSharableInstance());
    }
  }

  public void setSelectedIndex(int index) {
    disableChangeEvent = true;
    getSelectionModel().setSelectionInterval(index, index);
    disableChangeEvent = false;
  }

  public void setSelectedIndices(int[] indices) {
    disableChangeEvent = true;

    ListSelectionModel sm = getSelectionModel();

    sm.clearSelection();

    if (indices != null) {
      for (int i : indices) {
        sm.addSelectionInterval(i, i);
      }
    }

    disableChangeEvent = false;
  }

  public void setSelectedColumnIndex(int index) {
    disableChangeEvent = true;
    getColumnModel().getSelectionModel().setSelectionInterval(index, index);
    disableChangeEvent = false;
  }

  public int getSelectedColumnIndex() {
    return getSelectedColumn();
  }

  public int[] getSelectedColumnIndices() {
    return getSelectedColumns();
  }

  public void setSelectedColumnIndices(int[] indices) {
    disableChangeEvent = true;

    ListSelectionModel sm = getColumnModel().getSelectionModel();

    sm.clearSelection();

    if (indices != null) {
      for (int i : indices) {
        sm.addSelectionInterval(i, i);
      }
    }

    disableChangeEvent = false;
  }

  @Override
  public void setSelectionChangeListener(iItemChangeListener l) {
    getSelectionModel().removeListSelectionListener(this);
    getSelectionModel().addListSelectionListener(this);
    changeListener = l;
  }

  @Override
  public void setSelectionMode(SelectionMode selectionMode) {
    if (itemRenderer != null) {
      itemRenderer.setSelectionPainted(true);
    }

    if (getSelectionModel() instanceof EmptyListSelectionModel) {
      setSelectionModel(new DefaultListSelectionModel());
    }

    switch(selectionMode) {
      case BLOCK :
        setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        break;

      case MULTIPLE :
        setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        break;

      case INVISIBLE :
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        if (itemRenderer != null) {
          itemRenderer.setSelectionPainted(false);
        }

        break;

      case NONE :
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setSelectionModel(new EmptyListSelectionModel());

        break;

      default :
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
  }

  public void setSelectionType(SelectionType type) {
    selectionType = type;
  }

  @Override
  public void setShowDivider(boolean show) {
    setRowMargin(show
                 ? 1
                 : 0);
  }

  @Override
  public void setSingleClickAction(boolean singleClickAction) {
    this.singleClickAction = singleClickAction;
  }

  public void setTableType(TableType type) {
    tableType = type;
  }

  @Override
  public UIColor getAlternatingRowColor() {
    return alternatingRowColor;
  }

  public Rectangle getCellBounds(int index) {
    Rectangle r = getCellRect(index, getComponentCount(), true);

    if (r != null) {
      r.x = 0;
    }

    return r;
  }

  @Override
  public int getFirstVisibleIndex() {
    Point p        = getViewport().getViewPosition();
    int   rowIndex = rowAtPoint(p);

    if ((rowIndex == -1) && (getRowCount() > 0)) {
      rowIndex = getRowCount() - 1;
    }

    return rowIndex;
  }

  public TableHeader getHeader() {
    if (header == null) {
      header = new TableHeader(this);
    }

    return header;
  }

  @Override
  public int getHilightedIndex() {
    return hilightIndex;
  }

  public RenderableDataItem getItemAt(int index) {
    return ((iTableModel) getModel()).get(index);
  }

  @Override
  public aListItemRenderer getItemRenderer() {
    return itemRenderer;
  }

  public JTable getJTable() {
    return this;
  }

  @Override
  public int getLastVisibleIndex() {
    Rectangle r = getViewport().getViewRect();

    r.y += (r.height - 2);

    int rowIndex = rowAtPoint(r.getLocation());

    if ((rowIndex == -1) && (getRowCount() > 0)) {
      rowIndex = getRowCount() - 1;
    }

    return rowIndex;
  }

  @Override
  public iPlatformComponent getListComponent() {
    return table;
  }

  @Override
  public int getContextMenuIndex() {
    return popupIndex;
  }

  public int getRowAtPoint(Point p) {
    return rowAtPoint(p);
  }

  public int getRowAtPoint(int x, int y) {
    Point p = new Point(x, y);

    return rowAtPoint(p);
  }

  public Rectangle getRowBounds(int index) {
    return getRowBounds(index, index);
  }

  public Rectangle getRowBounds(int firstRow, int lastRow) {
    Rectangle r1 = getCellRect(firstRow, 0, true);
    Rectangle r2 = getCellRect(lastRow, getColumnCount(), true);

    if ((r1 == null) || (r2 == null)) {
      return null;
    }

    r1.width = r2.x + r2.width - r1.x;

    return r1;
  }

  protected JViewport getViewport() {
    return (JViewport) getParent();
  }

  protected int getGridRowHeight() {
    if (getRowCount() > 0) {
      return getRowHeight(0);
    }

    return getRowHeight();
  }

  @Override
  public int getRowHeight(int row) {
    int rh = getRowHeight();

    if (!autoSizeRows) {
      return rh;
    }

    if (!rowHeightSet) {
      rh = 0;
    }

    iTableModel tm = (iTableModel) getModel();
    int         h;

    if (tableType != null) {
      h = super.getRowHeight(row);
    } else {
      if (needSizeToFitCall) {
        needSizeToFitCall = false;

        int len = tm.getRowCount();

        for (int i = 0; i < len; i++) {
          tm.getRow(i).setHeight(0);
        }
      }

      RenderableDataItem rowItem = tm.getRow(row);

      h = rowItem.getHeight();

      if (h < 1) {
        h = TableHelper.calculateRowHeight(table, itemRenderer, tm, row, header.columns, false, rh,header.viewPositions);
        h += 4;
        rowItem.setHeight(h);
      }
    }

    if (super.getRowHeight(row) != h) {
      setRowHeight(row, h);
    }

    return h;
  }

  @Override
  public int getSelectedIndex() {
    return getSelectedRow();
  }

  public SelectionType getSelectionType() {
    return null;
  }

  public TablePainter getTablePainter() {
    return ((TableComponent) header.getTableComponent()).getTablePainter();
  }

  public boolean hasCheckedRows() {
    return false;
  }

  @Override
  public void focusGained(FocusEvent e) {
    TableViewer        tv = (TableViewer) Platform.getWidgetForComponent(this);
    iPlatformComponent pc = tv.getContainerComponent();

    if (pc.isFocusPainted()) {
      pc.repaint();
    }

    if ((getSelectedIndex() == -1) && (getRowCount() > 0)) {
      if (tv.isHandleFirstFocusSelection()) {
        int n = aListHandler.getFirstSelectableIndex(((iTableModel) getModel()));

        if (n != -1) {
          setSelectedIndex(n);
        }
      }
    }
  }

  @Override
  public void focusLost(FocusEvent e) {
    TableViewer tv = (TableViewer) Platform.getWidgetForComponent(this);

    if ((tv == null) || tv.isDisposed() ||!tv.isShowing()) {
      return;
    }

    iPlatformComponent pc = tv.getContainerComponent();

    if (pc.isFocusPainted()) {
      pc.repaint();
    }

    int n = getSelectedIndex();

    if (n != -1) {
      if (tv.isChangeSelColorOnLostFocus()) {
        if (tv.getSelectedIndexCount() > 1) {
          int sels[] = tv.getSelectedIndexes();

          for (int i : sels) {
            tv.repaintRow(i);
          }
        } else {
          tv.repaintRow(n);
        }
      }
    }
  }

  @Override
  public boolean isAutoHilight() {
    return autoHilight;
  }

  public boolean isShowVerticalLinesEx() {
    return ((TableComponent) header.getTableComponent()).tableStyle.showVerticalLines;
  }

  public boolean isAutoSizeRows() {
    return autoSizeRows;
  }

  @Override
  public boolean isEditing() {
    return false;
  }

  public boolean isMultipleSelectionAllowed() {
    ListSelectionModel sm = getSelectionModel();

    switch(sm.getSelectionMode()) {
      case ListSelectionModel.MULTIPLE_INTERVAL_SELECTION :
      case ListSelectionModel.SINGLE_INTERVAL_SELECTION :
        return true;

      default :
        return false;
    }
  }

  public boolean isRowChecked(int row) {
    return false;
  }

  @Override
  public boolean isRowSizeFixed() {
    return !header.getTableComponent().isAutoSizeRows();
  }

  @Override
  public boolean isSelectable() {
    return selectable;
  }

  @Override
  public boolean isSingleClickAction() {
    return singleClickAction;
  }

  protected void calculateOffset() {}

  protected boolean checkForCellHotspot(int row, float x, float y, float width, float height) {
    return false;
  }

  protected void clickCheck(MouseEvent e, boolean release) {
    if (!selectable || e.isPopupTrigger() ||!isEnabled()) {
      return;
    }

    if (singleClickAction) {
      if (!release || (e.getClickCount() > 1)) {
        return;
      }
    } else if (e.getClickCount() < 2) {
      return;
    }

    if (release &&!PlatformHelper.isMouseClick(mousePressedPoint, mousePressedTime, e)) {
      return;
    }

    int row = rowAtPoint(e.getPoint());

    if ((row == -1) || (getSelectedIndex() != row)) {
      return;
    }

    RenderableDataItem item = getItemAt(row);

    if (!item.isEnabled() ||!item.isSelectable()) {
      return;
    }

    if (actionListener != null) {
      ActionEvent ae = new ActionEvent(this, (e.getClickCount() > 1)
              ? "dblClick"
              : "click");

      actionListener.actionPerformed(ae);
    }
  }

  protected void sizeRowsToFit(int rMin, int rMax) {
    if (tableType == null) {
      iTableModel tm  = (iTableModel) getModel();
      int         len = tm.getRowCount();

      if (rMin > len - 1) {
        rMin = len - 1;

        if (rMin < 0) {
          rMin = 0;
        }
      }

      if (rMax > len - 1) {
        rMax = len - 1;
      }

      needSizeToFitCall = false;

      for (int i = rMin; i <= rMax; i++) {
        tm.getRow(i).setHeight(0);
      }
    } else {
      setRowSizes();
    }
  }

  protected void setRowSizes() {
    iTableModel tm  = (iTableModel) getModel();
    int         len = tm.getRowCount();

    if (len == 0) {
      return;
    }

    needSizeToFitCall = false;
    setResizeRepaintBlocked(true);

    if (tableType != null) {
      MultiTableTableComponent mt = (MultiTableTableComponent) ((TableViewer) header.getWidget()).getTableComponent();

      ((TableComponent) mt.getMainTable()).setMultiTableRowSizes();
    } else {
      int rh = getRowHeight();
      int h;

      if (!rowHeightSet) {
        rh = 0;
      }
      int []vp=header.viewPositions;
      for (int i = 0; i < len; i++) {
        RenderableDataItem rowItem = tm.getRow(i);

        h = rowItem.getHeight();

        if (h < 1) {
          h = TableHelper.calculateRowHeight(table, itemRenderer, tm, i, header.columns, false, rh,vp);
          h += 4;
          rowItem.setHeight(h);
        }

        if (super.getRowHeight(i) != h) {
          setRowHeight(i, h);
        }
      }
    }

    setResizeRepaintBlocked(false);
  }

  protected void setTreeInfoForRow(iRenderingComponent rc, iPlatformIcon indicator, int indent) {
    if (rc instanceof UIListLabelRenderer) {
      ListItemLabelRenderer view = (ListItemLabelRenderer) ((UIListLabelRenderer) rc).getView();

      if (indicator != null) {
        indent += indicatorWidth;
      }

      view.indicatorIcon = indicator;
      view.indent        = indent;
    }
  }

  protected boolean isOnCheckBox(float x, float y, float width, float height, int indent) {
    return false;
  }

  public static class SelectionChangeMaintainer {
    IntList newSelections;
    IntList oldSelections;

    public void makeNewOld() {
      if (oldSelections == null) {
        oldSelections = new IntList();
      } else {
        oldSelections.clear();
      }

      oldSelections.addAll(newSelections);
    }

    public void selectionChanged(int first, int last) {
      if (newSelections == null) {
        newSelections = new IntList();
      } else {
        newSelections.clear();
      }

      for (int i = first; i <= last; i++) {
        newSelections.add(i);
      }
    }
  }


  class EditableGestureListener extends MouseInputAdapter {
    int             reordingRow = -1;
    boolean         canDelete;
    int             dragThreshold;
    int             reordingMouseY;
    int             reordingRowY;
    BasicTableExUI  ui;
    private boolean pressConsumed;
    private Point   pressPoint;

    public EditableGestureListener(boolean canDelete) {
      this.canDelete = canDelete;

      if (getUI() instanceof BasicTableExUI) {
        ui            = (BasicTableExUI) getUI();
        dragThreshold = java.awt.dnd.DragSource.getDragThreshold();
      }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
      super.mouseDragged(e);

      if (draggingAllowed && editing && ((pressedRow != -1) || (reordingRow != -1)) && (ui != null)) {
        e.consume();

        if ((reordingRow != -1) || (Math.abs(e.getY() - pressPoint.y) > dragThreshold)) {
          Rectangle r;

          if (reordingRow == -1) {
            reordingRow    = pressedRow;
            r              = getCellRect(pressedRow, pressedRow, false);
            pressedRow     = -1;
            reordingMouseY = pressPoint.y;

            int y      = pressPoint.y;
            int height = getRowHeight();

            if (r != null) {
              height       = r.height;
              y            = r.y + (e.getY() - y);
              reordingRowY = r.y;
            } else {
              reordingRowY = y;
            }

            ui.setReordingInfo(reordingRow, y, height);
          } else {
            int y = reordingRowY + (e.getY() - pressPoint.y);

            ui.setReordingY(y);

            int row = rowAtPoint(e.getPoint());

            if (row != -1) {
              r = getCellRect(row, row, false);

              if (r != null) {
                scrollRectToVisible(r);
              }
            }
          }

          repaint();
        }
      }
    }

    @Override
    public void mousePressed(MouseEvent e) {
      pressPoint  = e.getPoint();
      reordingRow = -1;
      pressedRow  = rowAtPoint(e.getPoint());
      super.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
      pressedRow = -1;

      if (ui != null) {
        reordingRow = -1;
        ui.setReordingInfo(-1, 0, 0);
      }

      if (pressConsumed) {
        e.consume();
      } else {
        super.mouseReleased(e);
      }
    }
  }


  class ListItemLabelRenderer extends LabelRenderer {
    Insets                  insets = new Insets(0, 0, 0, 0);
    protected iPlatformIcon checkIcon;
    protected int           indent;
    protected iPlatformIcon indicatorIcon;

    @Override
    protected void paintComponent(Graphics g) {
      super.paintComponent(g);

      if ((checkboxWidth > 0) || (indicatorWidth > 0)) {
        int x = PAD_SIZE + leftOffset + indent - indicatorWidth;

        if (indicatorIcon != null) {
          int y = (getHeight() - indicatorHeight) / 2;

          indicatorIcon.paintIcon(this, g, x, y);
        }

        if (indicatorWidth > 0) {
          x += indicatorWidth + ICON_GAP;
        }

        if (checkboxWidth > 0) {
          if (selectionType == SelectionType.CHECKED_LEFT) {
            int y = (getHeight() - checkboxHeight) / 2;

            checkIcon.paintIcon(this, g, x, y);
          } else {
            int y = (getHeight() - checkboxHeight) / 2;

            x = getWidth() - rightOffset - PAD_SIZE - checkboxWidth;
            checkIcon.paintIcon(this, g, x, y);
          }
        }
      }
    }
  }


  class UIListLabelRenderer extends UILabelRenderer {
    public UIListLabelRenderer() {
      super(new ListItemLabelRenderer());
    }

    public void setIndent(int indent) {
      ((ListItemLabelRenderer) view).indent = indent;
    }

    @Override
    public void setMargin(UIInsets insets) {
      float left = insets.left;

      insets.left += ((ListItemLabelRenderer) view).indent;
      super.setMargin(insets);
      insets.left = left;
    }
  }


  @Override
  public void makeSelectionVisible() {
    int index = getSelectedIndex();

    if (index > -1) {
      int col = getHeader().getSelectedColumn();

      if (col == -1) {
        col = 0;
      }

      Rectangle r = getCellRect(index, col, true);

      scrollRectToVisible(r);
    }
  }

  @Override
  public void autoscroll(Point cursorLocn) {
    SwingHelper.defaultAutoScroll(this, cursorLocn);
  }

  @Override
  public void changeSelection(int rowIndex, int columnIndex, boolean toggle, boolean extend) {
    if (canChangeSelection(rowIndex, columnIndex, toggle, extend)) {
      super.changeSelection(rowIndex, columnIndex, toggle, extend);
    }
  }

  @Override
  public void columnMarginChanged(javax.swing.event.ChangeEvent e) {
    super.columnMarginChanged(e);

    TableColumn tc = getTableHeader().getResizingColumn();

    if (tc != null) {
      int    n = tc.getModelIndex();
      Column c = getHeader().getColumnAt(n);

      c.preferedWidth = tc.getWidth();
      c.widthUnit     = ScreenUtils.Unit.PIXELS;
      columnResized();
    }
  }

  @Override
  public void doLayout() {
    if ((autoResizeMode != AUTO_RESIZE_OFF) || (columnModel == null) ||!isResizingColumn()) {
      super.doLayout();

      return;
    }

    TableColumnModel cm    = columnModel;
    int              width = getWidth();
    int              cw    = cm.getTotalColumnWidth();
    int              delta = width - cw;

    if ((delta > 0) && (cm.getColumnCount() > 0)) {
      TableColumn c = cm.getColumn(getLastResizableColumn());

      c.setWidth(c.getWidth() + delta);
    }
  }

  @Override
  public void invalidate() {
    preferedSizeCalled = false;
    super.invalidate();
  }

  @Override
  public void tableChanged(TableModelEvent e) {
    super.tableChanged(e);
    sizeToFit();
  }

  @Override
  public void setMinimumVisibleRowCount(int minimumVisibleRowCount) {
    this.minimumVisibleRowCount = minimumVisibleRowCount;
  }

  public void setMouseOverideListener(MouseListener mouseOverideListener) {
    this.mouseOverideListener = mouseOverideListener;
  }

  public void setResizeRepaintBlocked(boolean blocked) {
    resizeRepaintBlocked = blocked;
  }

  @Override
  public void setVisibleRowCount(int visibleRowCount) {
    this.visibleRowCount = visibleRowCount;
  }

  @Override
  public Insets getAutoscrollInsets() {
    return SwingHelper.defaultGetAutoscrollInsets(this);
  }

  public Dimension getHeaderMinimumSize() {
    UIDimension d = getHeader().getMinimumSize();

    return new Dimension(d.intWidth(), d.intHeight());
  }

  public Dimension getHeaderPreferredSize() {
    UIDimension d = getHeader().getPreferredSize();

    return new Dimension(d.intWidth(), d.intHeight());
  }

  public int getLastResizableColumn() {
    TableColumnModel cm  = columnModel;
    int              len = cm.getColumnCount();

    for (int i = len - 1; i > -1; i--) {
      if (cm.getColumn(i).getResizable()) {
        return i;
      }
    }

    return len - 1;
  }

  @Override
  public Dimension getMinimumSize() {
    Dimension d = super.getMinimumSize();

    if (minimumVisibleRowCount > 0) {
      int fixedCellHeight = rowHeight;

      if (fixedCellHeight == 0) {
        fixedCellHeight = FontUtils.getDefaultLineHeight();
      }

      int h = fixedCellHeight * minimumVisibleRowCount;

      if (d.height < h) {
        d.height = h;
      }
    }

    return d;
  }

  public int getMinimumVisibleRowCount() {
    return minimumVisibleRowCount;
  }

  public MouseListener getMouseOverideListener() {
    return mouseOverideListener;
  }

  @Override
  public Dimension getPreferredScrollableViewportSize() {
    if (!preferedSizeCalled) {
      Dimension d = getPreferredSize();

      if (d.height > 0) {
        preferedSizeCalled = true;

        if (SwingHelper.isHorizontalScrollBarHiddenAlways(getScrollPane())) {
          preferredViewportSize.width = d.width;
        }

        if (SwingHelper.isVerticalScrollBarHiddenAlways(getScrollPane())) {
          preferredViewportSize.height = d.height;
        }
      }

      if (d.width > preferredViewportSize.width) {
        preferredViewportSize.width = d.width;
      }

      if (visibleRowCount > 0) {
        preferredViewportSize.height = (this.getRowHeight() + this.getRowMargin()) * visibleRowCount;
      } else {
        if (d.height > preferredViewportSize.height) {
          preferredViewportSize.height = d.height;
        }
      }
    }

    return preferredViewportSize;
  }

  @Override
  public Dimension getPreferredSize() {
    preferedSizeCalled = true;

    int         rowCount = getRowCount();
    UIDimension size     = header.getPreferredSize();
    int         width    = (int) size.width;
    int         height   = (int) size.height;

    if ((rowCount > 0) && (getColumnCount() > 0)) {
      Rectangle r = getCellRect(rowCount - 1, 0, true);

      height = r.y + r.height;
    }

    long tmp = Math.abs(width);

    if (tmp > Integer.MAX_VALUE) {
      tmp = Integer.MAX_VALUE;
    }

    Dimension d    = new Dimension((int) tmp, height);
    int       rows = visibleRowCount;

    if (rows == 0) {
      rows = minimumVisibleRowCount;
    }

    if (rows > 0) {
      int fixedCellHeight = rowHeight;

      if (fixedCellHeight == 0) {
        fixedCellHeight = FontUtils.getDefaultLineHeight();
      }

      int h = fixedCellHeight * rows;

      if (d.height < h) {
        d.height = h;
      }
    }

    Integer w = (Integer) getClientProperty(iPlatformComponent.RARE_SWING_WIDTH_FIXED_VALUE);
    Integer h = (Integer) getClientProperty(iPlatformComponent.RARE_SWING_HEIGHT_FIXED_VALUE);

    if ((w != null) && (w.intValue() > 0)) {
      d.width = w;
    }

    if ((h != null) && (h.intValue() > 0)) {
      d.height = h;
    }

    preferredViewportSize.setSize(d);

    return d;
  }

  public JScrollPane getScrollPane() {
    Container parent = getParent();

    while(parent != null) {
      if (parent instanceof JScrollPane) {
        return (JScrollPane) parent;
      }

      parent = parent.getParent();
    }

    return null;
  }

  @Override
  public boolean getScrollableTracksViewportWidth() {
    if (autoResizeMode != AUTO_RESIZE_OFF) {
      return true;
    }

    java.awt.Component parent = getParent();

    if (parent instanceof JViewport) {
      return parent.getWidth() >= getPreferredSize().width;
    }

    return super.getScrollableTracksViewportWidth();
  }

  public int getVisibleRowCount() {
    return visibleRowCount;
  }

  public boolean isResizingColumn() {
    JTableHeader h = getTableHeader();

    return (h == null)
           ? false
           : h.getResizingColumn() != null;
  }

  @Override
  protected void configureEnclosingScrollPane() {
    if (mainTable) {
      super.configureEnclosingScrollPane();

      if (!getTableHeader().isVisible()) {
        JScrollPane sp = getScrollPane();

        if (sp.getColumnHeader() != null) {
          sp.getColumnHeader().setVisible(false);
        }
      }
    }
  }

  @Override
  protected TableColumnModel createDefaultColumnModel() {
    return new RareTableColumnModel();
  }

  @Override
  protected JTableHeader createDefaultTableHeader() {
    return new TableHeaderView(columnModel);
  }

  @Override
  protected void paintComponent(Graphics g) {
    TablePainter tp = getTablePainter();

    if (tp != null) {
      tp.paintBeforeComponent(this, (Graphics2D) g);
      super.paintComponent(g);
      tp.paintAfterComponent(this, (Graphics2D) g);
    } else {
      super.paintComponent(g);
    }
  }

  @Override
  protected void processMouseEvent(MouseEvent e) {
    if (mouseOverideListener != null) {
      int id = e.getID();

      switch(id) {
        case MouseEvent.MOUSE_PRESSED :
          mouseOverideListener.mousePressed(e);

          break;

        case MouseEvent.MOUSE_RELEASED :
          mouseOverideListener.mouseReleased(e);

          break;

        case MouseEvent.MOUSE_CLICKED :
          mouseOverideListener.mouseClicked(e);

          break;

        case MouseEvent.MOUSE_EXITED :
          mouseOverideListener.mouseExited(e);

          break;

        case MouseEvent.MOUSE_ENTERED :
          mouseOverideListener.mouseEntered(e);

          break;
      }
    }

    super.processMouseEvent(e);
  }

  @Override
  public void resizeAndRepaint() {
    if (!resizeRepaintBlocked) {
      super.resizeAndRepaint();
    }
  }

  public boolean isMainTable() {
    return mainTable;
  }

  public void setMainTable(boolean mainTable) {
    this.mainTable = mainTable;
  }

  static class RareTableColumnModel extends DefaultTableColumnModel {
    private boolean eventsEnabled = true;

    public void setEventsEnabled(boolean eventsEnabled) {
      this.eventsEnabled = eventsEnabled;
    }

    public boolean isEventsEnabled() {
      return eventsEnabled;
    }

    @Override
    protected void fireColumnMarginChanged() {
      if (eventsEnabled) {
        super.fireColumnMarginChanged();
      }
    }
  }


  @Override
  public iScrollerSupport getScrollerSupport() {
    return (iScrollerSupport) getScrollPane();
  }

  @Override
  public void setShowLastDivider(boolean show) {
    // TODO Auto-generated method stub
  }
}
