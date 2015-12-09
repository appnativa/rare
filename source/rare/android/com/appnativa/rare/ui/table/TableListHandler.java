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

import com.appnativa.rare.platform.android.ui.ListBoxListHandler;
import com.appnativa.rare.ui.iPlatformListDataModel;
import com.appnativa.rare.ui.table.iTableComponent.TableType;

public class TableListHandler extends ListBoxListHandler {
  protected TableComponent tableComponent;

  public TableListHandler(TableComponent table, iPlatformListDataModel model) {
    super(table.getListView(), model);
    tableComponent = table;
  }

  @Override
  public void scrollToLeftEdge() {
    tableComponent.scrollToLeftEdge();
  }

  @Override
  public void scrollToRightEdge() {
    tableComponent.scrollToRightEdge();
  }

  @Override
  public void moveLeftRight(boolean left, boolean block) {
    tableComponent.moveLeftRight(left, block);
  }

  @Override
  public void makeSelectionVisible() {
    tableComponent.makeSelectionVisible();
  }

  public TableType getTableType() {
    return tableComponent.getTableType();
  }

  @Override
  public boolean isTabular() {
    return true;
  }

  @Override
  public void setKeepSelectionVisible(boolean keepSelectionVisible) {
    super.setKeepSelectionVisible(keepSelectionVisible);
    tableComponent.setKeepSelectionVisible(keepSelectionVisible);
  }
}
