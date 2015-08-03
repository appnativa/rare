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

package com.appnativa.rare.viewer;

import com.appnativa.rare.Platform;
import com.appnativa.rare.platform.apple.ui.util.AppleHelper;
import com.appnativa.rare.spot.Table;
import com.appnativa.rare.spot.TreeTable;
import com.appnativa.rare.spot.Viewer;
import com.appnativa.rare.spot.Widget;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.iPlatformListHandler;
import com.appnativa.rare.ui.renderer.ListItemRenderer;
import com.appnativa.rare.ui.table.MultiDataItemTableModel;
import com.appnativa.rare.ui.table.MultiTableContainer;
import com.appnativa.rare.ui.table.MultiTableLayout;
import com.appnativa.rare.ui.table.MultiTableTableComponent;
import com.appnativa.rare.ui.table.MultipleListHandler;
import com.appnativa.rare.ui.table.TableComponent;
import com.appnativa.rare.ui.table.TableListHandler;
import com.appnativa.rare.ui.table.TableView;
import com.appnativa.rare.ui.table.TreeTableComponent;
import com.appnativa.rare.ui.table.TreeTableItemRenderer;
import com.appnativa.rare.ui.table.TreeTableListHandler;
import com.appnativa.rare.ui.table.TreeTableView;
import com.appnativa.rare.ui.table.iTableComponent.TableType;

import java.util.Map;

public class TableViewer extends aTableViewer {

  /**
   * Constructs a new instance
   */
  public TableViewer() {
    this(null);
  }

  /**
   * Constructs a new instance
   *
   * @param parent the widget's parent
   */
  public TableViewer(iContainer parent) {
    super(parent);
  }

  @Override
  public void dispose() {
    if (!isDisposable()) {
      return;
    }

    if ((selectionModelGroup != null) && (selectionModel != null)) {
      selectionModelGroup.remove(selectionModel);
    }

    super.dispose();
    selectionModelGroup = null;
  }

  @Override
  public void editCell(int row, int column) {
    getTableView().editCell(row, column);
  }

  @Override
  public void sizeColumnToFit(int col) {}

  @Override
  public void sizeColumnsToFit() {}

  @Override
  public void setAutoSizeRowsToFit(boolean autoSize) {
    getTableView().setAutoSizeRows(autoSize);
  }

  @Override
  public void setSelectionMode(SelectionMode selectionMode) {
    super.setSelectionMode(selectionMode);
    getTableView().setSelectionMode(selectionMode);

    if ((selectionMode == SelectionMode.NONE) || (selectionMode == SelectionMode.INVISIBLE)) {
      getTableView().getItemRenderer().setSelectionPainted(false);
    }
  }

  @Override
  public void setShowLastDivider(boolean show) {
    ((TableView) getDataView()).setShowLastDivider(show);
  }

  @Override
  public int getEditingColumn() {
    return getTableView().getEditingColumn();
  }

  @Override
  public int getEditingRow() {
    return getTableView().getEditingRow();
  }

  @Override
  public boolean isEditing() {
    return getTableView().isEditing();
  }

  @Override
  protected void createModelAndComponents(Viewer vcfg) {
    Table            cfg = (Table) vcfg;
    ListItemRenderer lr;
    TableView        list;

    if (cfg instanceof TreeTable) {
      list = new TreeTableView();
    } else {
      list = new TableView();
    }

    if (vcfg instanceof TreeTable) {
      TreeTable tcfg = (TreeTable) vcfg;

      tableHandler = new TreeTableComponent(list, tcfg);
      tableModel   = ((TreeTableComponent) tableHandler).getTableModel();
      treeHandler  = ((TreeTableComponent) tableHandler).getTreeHandler();
      treeHandler.setIndentBy(tcfg.indentBy.intValue());
      ((TreeTableComponent) tableHandler).setExpandAll(tcfg.expandAll.booleanValue());
      ((TreeTableComponent) tableHandler).setExpandableColumn(tcfg.expandableColumn.intValue());
      listComponent = new TreeTableListHandler((TreeTableComponent) tableHandler, tableModel,
              ((TreeTableComponent) tableHandler).getTreeModel());
      lr = new TreeTableItemRenderer(cfg.columnSelectionAllowed.booleanValue());
      list.setItemRenderer(lr);
      dataComponent = listComponent.getListComponent();
    } else {
      tableHandler  = new TableComponent(list, cfg, true);
      tableModel    = ((TableComponent) tableHandler).getTableModel();
      listComponent = new TableListHandler((TableComponent) tableHandler, tableModel);
      lr            = new ListItemRenderer(cfg.columnSelectionAllowed.booleanValue()
              || cfg.displayAsGridView.booleanValue());
      list.setItemRenderer(lr);

      int mtype = getMiltiTableConfigurationType(cfg);

      dataComponent = listComponent.getListComponent();

      if (mtype != 0) {
        if (cfg.rowHeaderFooterSelectionPainted.booleanValue()) {
          adjustMultiTableRenderer(lr, TableType.MAIN);
        }

        TableView tv;

        tableModel = new MultiDataItemTableModel(tableModel);
        tableModel.setWidget(this);

        MultiTableContainer      sp = new MultiTableContainer(new MultiTableLayout());
        MultiTableTableComponent mt = new MultiTableTableComponent(sp, (MultiDataItemTableModel) tableModel);

        tableModel.addDataModelListener(sp);
        ((TableComponent) tableHandler).setBackground(UIColor.TRANSPARENT);

        iPlatformListHandler h1 = null;
        iPlatformListHandler h2 = null;

        if ((mtype & 1) != 0) {
          TableComponent tc = new TableComponent(tv = new TableView(), cfg, false);

          tc.setBackground(UIColor.TRANSPARENT);
          tc.setTableType(TableType.HEADER);
          registerWithWidget(tc);
          mt.setRowHeaderTable(tc);
          sp.add(tc);
          h1 = new TableListHandler(tc, tc.getModel());
          tv.setItemRenderer(lr = new ListItemRenderer(false));
          ((MultiDataItemTableModel) tableModel).setHeaderModel(tc.getModel());
          lr.setPaintRowBackground(false);

          if (cfg.rowHeaderFooterSelectionPainted.booleanValue()) {
            adjustMultiTableRenderer(lr, TableType.HEADER);
          } else {
            lr.setSelectionPainted(false);
          }

          tableHandler.getTableHeader().setPaintLeftMargin(true);
        }

        tableHandler.setTableType(TableType.MAIN);
        registerWithWidget(tableHandler.getPlatformComponent());
        sp.add(tableHandler.getPlatformComponent());
        mt.setMainTable(tableHandler);
        dataComponent = sp;
        tableHandler  = mt;

        if ((mtype & 2) != 0) {
          TableComponent tc = new TableComponent(tv = new TableView(), cfg, false);

          tc.setTableType(TableType.FOOTER);
          tc.setBackground(UIColor.TRANSPARENT);
          registerWithWidget(tc);
          mt.setRowFooterTable(tc);
          sp.add(tc);
          h2 = new TableListHandler(tc, tc.getModel());

          if (h1 == null) {
            h1 = h2;
            h2 = null;
          }

          tv.setItemRenderer(lr = new ListItemRenderer(false));
          ((MultiDataItemTableModel) tableModel).setFooterModel(tc.getModel());

          if (cfg.rowHeaderFooterSelectionPainted.booleanValue()) {
            adjustMultiTableRenderer(lr, TableType.FOOTER);
          } else {
            lr.setSelectionPainted(false);
          }

          mt.getMainTable().getTableHeader().setPaintRightMargin(true);
        }

        listComponent = new MultipleListHandler(mt, listComponent, h1, h2);
        sp.setBackground(UIColor.WHITE);
      }
    }

    formComponent = tableHandler.getPlatformComponent();
    formComponent = AppleHelper.configureScrollPane(this, formComponent, list, cfg.getScrollPane());
  }

  @Override
  protected void handleCustomProperties(Widget cfg, Map<String, Object> properties) {
    super.handleCustomProperties(cfg, properties);
    ListBoxViewer.handleCustomProperties(getTableView(), cfg, properties);
  }

  protected static void registerForUse() {
    Platform.getAppContext().registerWidgetClass(Platform.getSPOTName(Table.class), TableViewer.class);
  }

  @Override
  protected void setFlingThreshold(int threshold) {
    getTableView().setFlingThreshold(threshold);
  }

  @Override
  protected void setSelectFlinged(boolean select) {
    getTableView().setSelectFlinged(select);
  }

  @Override
  protected void setWholeViewFling(boolean wholeViewFling) {
    getTableView().setWholeViewFling(wholeViewFling);
  }

  protected TableView getTableView() {
    return ((TableComponent) tableHandler.getMainTable()).getTableView();
  }
}
