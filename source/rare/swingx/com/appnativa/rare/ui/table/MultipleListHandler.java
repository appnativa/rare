package com.appnativa.rare.ui.table;

import javax.swing.ListSelectionModel;

import com.appnativa.rare.ui.iListView;
import com.appnativa.rare.ui.iPlatformListHandler;

public class MultipleListHandler extends aMultipleListHandler {

  public MultipleListHandler(MultiTableTableComponent mtc, iListView lv, iPlatformListHandler main, iPlatformListHandler handler1,
      iPlatformListHandler handler2) {
    super(mtc, lv, main, handler1, handler2);
  }

  @Override
  public void setSelectionMode(SelectionMode selectionMode) {
    super.setSelectionMode(selectionMode);
    TableView tv = ((TableComponent) multiTableComponent.getMainTable()).getTableView();
    ListSelectionModel sm = tv.getSelectionModel();
    TableComponent tc = (TableComponent) multiTableComponent.getRowHeaderTable();
    if (tc != null) {
      tc.getTableView().setSelectionModel(sm);
    }
    tc = (TableComponent) multiTableComponent.getRowFooterTable();
    if (tc != null) {
      tc.getTableView().setSelectionModel(sm);
    }
  }

}
