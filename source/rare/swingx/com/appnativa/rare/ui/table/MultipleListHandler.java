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

import com.appnativa.rare.ui.iListView;
import com.appnativa.rare.ui.iPlatformListHandler;

import javax.swing.ListSelectionModel;

public class MultipleListHandler extends aMultipleListHandler {
  public MultipleListHandler(MultiTableTableComponent mtc, iListView lv, iPlatformListHandler main,
                             iPlatformListHandler handler1, iPlatformListHandler handler2) {
    super(mtc, lv, main, handler1, handler2);
  }

  @Override
  public void setSelectionMode(SelectionMode selectionMode) {
    super.setSelectionMode(selectionMode);

    TableView          tv = ((TableComponent) multiTableComponent.getMainTable()).getTableView();
    ListSelectionModel sm = tv.getSelectionModel();
    TableComponent     tc = (TableComponent) multiTableComponent.getRowHeaderTable();

    if (tc != null) {
      tc.getTableView().setSelectionModel(sm);
    }

    tc = (TableComponent) multiTableComponent.getRowFooterTable();

    if (tc != null) {
      tc.getTableView().setSelectionModel(sm);
    }
  }
}
