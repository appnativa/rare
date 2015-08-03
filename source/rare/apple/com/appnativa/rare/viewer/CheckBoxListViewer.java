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

import com.appnativa.rare.platform.apple.ui.view.ListView;
import com.appnativa.rare.ui.iPlatformIcon;

public class CheckBoxListViewer extends aCheckBoxListViewer {
  public CheckBoxListViewer() {
    super();
  }

  public CheckBoxListViewer(iContainer parent) {
    super(parent);
  }

  @Override
  public void clearCheckMarks() {
    ((ListView) getDataView()).clearCheckedItems();
  }

  @Override
  public void setCheckedRows(int[] indices) {
    ((ListView) getDataView()).setCheckedRows(indices);
  }

  @Override
  public void setLinkSelection(boolean linked) {
    super.setLinkSelection(linked);
    ((ListView) getDataView()).setLinkSelection(linked);
  }

  @Override
  public void setListSelectionType(SelectionType type) {
    super.setListSelectionType(type);
    ((ListView) getDataView()).setSelectionType(type);
  }

  @Override
  public void setRowChecked(int row, boolean checked) {
    ((ListView) getDataView()).setRowChecked(row, checked);
  }

  @Override
  public boolean hasCheckedRows() {
    return ((ListView) getDataView()).hasCheckedRows();
  }

  @Override
  public boolean isRowChecked(int row) {
    return ((ListView) getDataView()).isRowChecked(row);
  }

  @Override
  protected void setIcons(iPlatformIcon checked, iPlatformIcon unchecked, iPlatformIcon indeterminate) {
    ((ListView) getDataView()).setIcons(checked, unchecked, indeterminate);
  }
}
