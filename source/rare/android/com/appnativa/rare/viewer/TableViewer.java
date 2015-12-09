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

import android.view.View;

import com.appnativa.rare.platform.android.AppContext;
import com.appnativa.rare.platform.android.ui.util.AndroidHelper;
import com.appnativa.rare.platform.android.ui.view.ListViewEx;
import com.appnativa.rare.spot.Table;
import com.appnativa.rare.spot.TreeTable;
import com.appnativa.rare.spot.Viewer;
import com.appnativa.rare.ui.iPlatformListHandler;
import com.appnativa.rare.ui.renderer.ListItemRenderer;
import com.appnativa.rare.ui.table.MultiDataItemTableModel;
import com.appnativa.rare.ui.table.MultiDataItemTableModelEx;
import com.appnativa.rare.ui.table.MultiTableContainer;
import com.appnativa.rare.ui.table.MultiTableLayout;
import com.appnativa.rare.ui.table.MultiTableTableComponent;
import com.appnativa.rare.ui.table.MultipleListHandler;
import com.appnativa.rare.ui.table.TableComponent;
import com.appnativa.rare.ui.table.TableListHandler;
import com.appnativa.rare.ui.table.TreeTableComponent;
import com.appnativa.rare.ui.table.TreeTableListHandler;
import com.appnativa.rare.ui.table.aTableAdapter;
import com.appnativa.rare.ui.table.iTableComponent.TableType;

/**
 * A widget that allows a user to select one or more choices from a scrollable
 * list of items.
 *
 * @author Don DeCoteau
 */
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
   * @param fv
   *          the widget's parent
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
  public void sizeColumnToFit(int col) {
    ((TableComponent) tableHandler).sizeColumnToFit(col);
  }

  @Override
  public void sizeColumnsToFit() {
    ((TableComponent) tableHandler).sizeColumnsToFit();
  }

  @Override
  public void setAutoSizeRowsToFit(boolean autoSize) {
    ((TableComponent) tableHandler).setAutoSizeRows(autoSize);
  }

  @Override
  public void setSelectionMode(SelectionMode selectionMode) {
    super.setSelectionMode(selectionMode);

    boolean selectable = true;
    boolean invisible  = false;

    switch(selectionMode) {
      case SINGLE :
        break;

      case BLOCK :
      case MULTIPLE :
        break;

      case INVISIBLE :
        invisible = true;

        break;

      default :
        selectable = false;
        invisible  = true;

        break;
    }

    if (invisible) {
      getItemRenderer().setSelectionPainted(false);
    }

    ((aTableAdapter) tableModel).setSelectable(selectable);
  }

  @Override
  public void setShowLastDivider(boolean show) {}

  @Override
  public View getDataView() {
    TableComponent table = (TableComponent) tableHandler.getMainTable();

    return table.getListView();
  }

  /**
   * Configures the listbox
   *
   * @param cfg
   *          the listbox's configuration
   */
  @Override
  protected void configureEx(Table cfg) {
    super.configureEx(cfg);
    ((ListViewEx) getDataView()).viewConfigured();
  }

  @Override
  protected void createModelAndComponents(Viewer vcfg) {
    Table            cfg  = (Table) vcfg;
    ListViewEx       list = getAppContext().getComponentCreator().getTable(this, cfg);
    ListItemRenderer lr;

    if (vcfg instanceof TreeTable) {
      TreeTable tcfg = (TreeTable) vcfg;

      tableHandler = new TreeTableComponent(list, tcfg);
      tableModel   = ((TreeTableComponent) tableHandler).getTableModel();
      treeHandler  = ((TreeTableComponent) tableHandler).getTreeHandler();
      dataComponent = tableHandler.getPlatformComponent();
      listComponent = new TreeTableListHandler((TreeTableComponent) dataComponent, tableModel,
              ((TreeTableComponent) tableHandler).getTreeModel());
      lr = new ListItemRenderer();
      tableModel.setItemRenderer(lr);
      list.setItemsCanFocus(false);
      lr.setPaintRowBackground(false);
    } else {
      AppContext app = (AppContext) getAppContext();

      tableHandler  = new TableComponent(list, cfg, true);
      tableModel    = ((TableComponent) tableHandler).getTableModel();
      dataComponent = tableHandler.getPlatformComponent();
      listComponent = new TableListHandler((TableComponent) dataComponent, tableModel);
      lr            = new ListItemRenderer();
      tableModel.setItemRenderer(lr);
      list.setItemsCanFocus(false);
      lr.setPaintRowBackground(false);

      int mtype = getMiltiTableConfigurationType(cfg);

      if (mtype != 0) {
        if (cfg.rowHeaderFooterSelectionPainted.booleanValue()) {
          adjustMultiTableRenderer(lr, TableType.MAIN);
        }

        ListViewEx lv;

        tableModel = new MultiDataItemTableModelEx(tableModel);
        tableModel.setWidget(this);

        MultiTableTableComponent mt = new MultiTableTableComponent(dataComponent, (MultiDataItemTableModel) tableModel);

        mt.setMainTable(tableHandler);;

        MultiTableContainer sp = new MultiTableContainer(new MultiTableLayout(dataComponent.getView().getContext()));

        tableModel.addDataModelListener(sp);
        tableHandler = mt;

        iPlatformListHandler h1 = null;
        iPlatformListHandler h2 = null;

        if ((mtype & 1) != 0) {
          TableComponent tc = new TableComponent(lv = app.getComponentCreator().getTable(this, cfg), cfg, false);

          registerWithWidget(tc);
          mt.setRowHeaderTable(tc);
          sp.add(tc);
          h1 = new TableListHandler(tc, tc.getModel());
          tc.getTableModel().setItemRenderer(lr = new ListItemRenderer());
          ((MultiDataItemTableModel) tableModel).setHeaderModel(tc.getModel());
          lv.setItemsCanFocus(false);
          lr.setPaintRowBackground(false);

          if (cfg.rowHeaderFooterSelectionPainted.booleanValue()) {
            adjustMultiTableRenderer(lr, TableType.HEADER);
          } else {
            lr.setSelectionPainted(false);
          }
        }

        registerWithWidget(dataComponent);
        sp.add(dataComponent);
        dataComponent = sp;

        if ((mtype & 2) != 0) {
          TableComponent tc = new TableComponent(lv = app.getComponentCreator().getTable(this, cfg), cfg, false);

          registerWithWidget(tc);
          mt.setRowFooterTable(tc);
          sp.add(tc);
          h2 = new TableListHandler(tc, tc.getModel());

          if (h1 == null) {
            h1 = h2;
            h2 = null;
          }

          tc.getTableModel().setItemRenderer(lr = new ListItemRenderer());
          ((MultiDataItemTableModel) tableModel).setFooterModel(tc.getModel());
          lv.setItemsCanFocus(false);
          lr.setPaintRowBackground(false);

          if (cfg.rowHeaderFooterSelectionPainted.booleanValue()) {
            adjustMultiTableRenderer(lr, TableType.FOOTER);
          } else {
            lr.setSelectionPainted(false);
          }
        }

        listComponent = new MultipleListHandler(mt, listComponent, h1, h2);
      }
    }

    formComponent = AndroidHelper.configureScrollPane(this, dataComponent, list, cfg.getScrollPane());
  }
}
