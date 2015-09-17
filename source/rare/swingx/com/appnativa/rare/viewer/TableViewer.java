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

import com.appnativa.rare.platform.swing.ui.ListBoxListHandler;
import com.appnativa.rare.platform.swing.ui.util.SwingHelper;
import com.appnativa.rare.spot.ItemDescription;
import com.appnativa.rare.spot.Table;
import com.appnativa.rare.spot.TreeTable;
import com.appnativa.rare.spot.Viewer;
import com.appnativa.rare.ui.Column;
import com.appnativa.rare.ui.PainterUtils;
import com.appnativa.rare.ui.iPlatformListHandler;
import com.appnativa.rare.ui.renderer.ListItemRenderer;
import com.appnativa.rare.ui.renderer.UITextAreaRenderer;
import com.appnativa.rare.ui.table.MultiDataItemTableModel;
import com.appnativa.rare.ui.table.MultiTableTableComponent;
import com.appnativa.rare.ui.table.MultipleListHandler;
import com.appnativa.rare.ui.table.TableComponent;
import com.appnativa.rare.ui.table.TableHeader;
import com.appnativa.rare.ui.table.TableScrollPane;
import com.appnativa.rare.ui.table.TableView;
import com.appnativa.rare.ui.table.TreeTableComponent;
import com.appnativa.rare.ui.table.TreeTableItemRenderer;
import com.appnativa.rare.ui.table.TreeTableListHandler;
import com.appnativa.rare.ui.table.iTableComponent.TableType;

import javax.swing.JTable;

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
   * @param parent
   *          the widget's parent
   */
  public TableViewer(iContainer parent) {
    super(parent);
  }

  /**
   * Creates a new table widget
   *
   * @param parent
   *          the parent
   * @param cfg
   *          the configuration
   *
   * @return the table widget
   */
  public static TableViewer create(iContainer fv, Table cfg) {
    TableViewer widget = new TableViewer(fv);

    widget.configure(cfg);

    return widget;
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
    JTable  table   = ((TableComponent) tableHandler).getTableView().getJTable();
    boolean success = table.editCellAt(row, column);

    if (success) {
      boolean toggle = false;
      boolean extend = false;

      table.changeSelection(row, column, toggle, extend);
    }
  }

  @Override
  public void sizeColumnToFit(int col) {
    ((TableComponent) tableHandler.getMainTable()).sizeColumnToFit(col);
  }

  @Override
  public void sizeColumnsToFit() {
    ((TableComponent) tableHandler.getMainTable()).sizeColumnsToFit();
  }

  @Override
  public void setAutoSizeRowsToFit(boolean autoSize) {
    ((TableComponent) tableHandler.getMainTable()).setAutoSizeRowsToFit(autoSize);
  }

  @Override
  public void setSelectionMode(SelectionMode selectionMode) {
    super.setSelectionMode(selectionMode);
    ((TableComponent) tableHandler.getMainTable()).getTableView().setSelectionMode(selectionMode);
  }

  @Override
  public void setShowLastDivider(boolean show) {
    ((TableComponent) tableHandler.getMainTable()).setShowLastItemBorder(show);
  }

  @Override
  public int getEditingColumn() {
    return ((TableComponent) tableHandler.getMainTable()).getTableView().getJTable().getEditingColumn();
  }

  @Override
  public int getEditingRow() {
    return ((TableComponent) tableHandler.getMainTable()).getTableView().getJTable().getEditingRow();
  }

  @Override
  public Column createColumn(ItemDescription id) {
    Column c = super.createColumn(id);

    if (c.headerWordWrap) {
      String s = id.headerWordWrap.spot_getAttribute("supportHTML");

      if (((s != null) && s.equals("false")) && (c.getCellRenderer() == null)) {
        c.setCustomProperty(TableHeader.USE_TEXTAREA_PROPERTY, Boolean.TRUE);
      }
    }

    if (c.wordWrap) {
      String s = id.wordWrap.spot_getAttribute("supportHTML");

      if (((s != null) && s.equals("false")) && (c.getCellRenderer() == null)) {
        c.setCellRenderer(new UITextAreaRenderer());
      }
    }

    return c;
  }

  @Override
  protected void createModelAndComponents(Viewer vcfg) {
    Table            cfg = (Table) vcfg;
    ListItemRenderer lr;
    TableView        list;

    if (vcfg instanceof TreeTable) {
      TreeTable tcfg = (TreeTable) vcfg;

      tableHandler = new TreeTableComponent(tcfg);
      list         = ((TreeTableComponent) tableHandler).getTableView();
      tableModel   = ((TreeTableComponent) tableHandler).getTableModel();
      treeHandler  = ((TreeTableComponent) tableHandler).getTreeHandler();
      treeHandler.setIndentBy(tcfg.indentBy.intValue());
      ((TreeTableComponent) tableHandler).setExpandAll(tcfg.expandAll.booleanValue());
      ((TreeTableComponent) tableHandler).setExpandableColumn(tcfg.expandableColumn.intValue());
      ((TreeTableComponent) tableHandler).setTreeIcons(new PainterUtils.TwistyIcon(dataComponent,false),
          new PainterUtils.TwistyIcon(dataComponent,true));

      formComponent = tableHandler.getPlatformComponent();
      listComponent = new TreeTableListHandler(list, tableModel, ((TreeTableComponent) tableHandler).getTreeModel());
      lr            = new TreeTableItemRenderer(list);
    } else {
      tableHandler  = new TableComponent(cfg);
      list          = ((TableComponent) tableHandler).getTableView();
      tableModel    = ((TableComponent) tableHandler).getTableModel();
      formComponent = tableHandler.getPlatformComponent();
      listComponent = new ListBoxListHandler(list, tableModel);
      lr            = new ListItemRenderer(list);

      int mtype = getMiltiTableConfigurationType(cfg);

      if (mtype != 0) {
        if (cfg.rowHeaderFooterSelectionPainted.booleanValue()) {
          adjustMultiTableRenderer(lr, TableType.MAIN);
        }

        tableModel = new MultiDataItemTableModel(tableModel);
        tableModel.setWidget(this);

        MultiTableTableComponent mt = new MultiTableTableComponent(formComponent, (MultiDataItemTableModel) tableModel);

        mt.setMainTable(tableHandler);;
        tableHandler = mt;

        iPlatformListHandler h1 = null;
        iPlatformListHandler h2 = null;
        TableScrollPane      sp = (TableScrollPane) formComponent.getView();

        if ((mtype & 1) != 0) {
          TableComponent tc = new TableComponent(cfg);

          registerWithWidget(tc);
          tc.getScrollPane().disableScrolling();
          mt.setRowHeaderTable(tc);
          sp.setRowHeaderView(tc.getView());

          TableView tv = tc.getTableView();

          h1 = new ListBoxListHandler(tv, tc.getModel());
          tv.setItemRenderer(new ListItemRenderer(tv));
          ((MultiDataItemTableModel) tableModel).setHeaderModel(tc.getModel());
          mt.getMainTable().getTableHeader().setPaintLeftMargin(true);

          if (cfg.rowHeaderFooterSelectionPainted.booleanValue()) {
            adjustMultiTableRenderer(tv.getItemRenderer(), TableType.HEADER);
          } else {
            lr.setSelectionPainted(false);
          }
        }

        if ((mtype & 2) != 0) {
          TableComponent tc = new TableComponent(cfg);

          registerWithWidget(tc);
          tc.getScrollPane().disableScrolling();

          TableView tv = tc.getTableView();

          mt.setRowFooterTable(tc);
          sp.setRowFooterView(tc.getView());
          h2 = new ListBoxListHandler(tv, tc.getModel());

          if (h1 == null) {
            h1 = h2;
            h2 = null;
          }

          tv.setItemRenderer(new ListItemRenderer(tv));
          ((MultiDataItemTableModel) tableModel).setFooterModel(tc.getModel());
          mt.getMainTable().getTableHeader().setPaintRightMargin(true);

          if (cfg.rowHeaderFooterSelectionPainted.booleanValue()) {
            adjustMultiTableRenderer(tv.getItemRenderer(), TableType.FOOTER);
          } else {
            lr.setSelectionPainted(false);
          }
        }

        listComponent = new MultipleListHandler(mt, list, listComponent, h1, h2);
      }
    }

    dataComponent = ((TableComponent) tableHandler.getMainTable()).getTable();
    SwingHelper.configureScrollPane(this, ((TableComponent) tableHandler.getMainTable()).getScrollPane(),
                                    cfg.getScrollPane());
    list.setItemRenderer(lr);
  }
}
