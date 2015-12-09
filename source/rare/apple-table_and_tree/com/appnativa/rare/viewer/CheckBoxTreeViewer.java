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
import com.appnativa.rare.platform.apple.ui.view.TreeView;
import com.appnativa.rare.spot.CheckBoxTree;
import com.appnativa.rare.ui.iPlatformIcon;

/**
 * A viewer that allows a user to select one or more choices from a
 * scrollable list of items.
 *
 * In addition, each item in the tree has an associated check box
 * that can be independently selected/deselected.
 *
 * @author Don DeCoteau
 */
public class CheckBoxTreeViewer extends aCheckBoxTreeViewer {

  /**
   * Constructs a new instance
   */
  public CheckBoxTreeViewer() {
    this(null);
  }

  /**
   * Constructs a new instance
   *
   * @param parent the widget's parent
   */
  public CheckBoxTreeViewer(iContainer parent) {
    super(parent);
  }

  /**
   * Clears all check marks
   */
  @Override
  public void clearCheckMarks() {
    getListView().clearCheckedItems();
  }

  /**
   * Selects a set of rows.
   *
   * @param indices the indices of the rows to select
   */
  @Override
  public void setCheckedRows(int[] indices) {
    getListView().setCheckedRows(indices);
  }

  @Override
  public void setLinkSelection(boolean linked) {
    super.setLinkSelection(linked);
    getListView().setLinkSelection(linkedSelection);
  }

  @Override
  public void setListSelectionType(SelectionType type) {
    super.setListSelectionType(type);
    getListView().setSelectionType(type);
  }

  @Override
  public void setManageChildNodeSelections(boolean manage) {
    getListView().setManageCheckboxSelection(manage);
  }

  /**
   * Set the checked state of a row
   *
   * @param row the row
   * @param checked true to check the row; false to un-check
   */
  @Override
  public void setRowChecked(int row, boolean checked) {
    getListView().setRowChecked(row, checked);
  }

  /**
   * Returns whether there are any checked rows
   * @return true if there are checked rows; false otherwise
   */
  @Override
  public boolean hasCheckedRows() {
    return getListView().hasCheckedRows();
  }

  /**
   * Returns whether the specified row is checked
   *
   * @param row the row to test
   *
   * @return true if the specified row is checked; false otherwise
   */
  @Override
  public boolean isRowChecked(int row) {
    return getListView().isRowChecked(row);
  }

  TreeView getListView() {
    return (TreeView) getDataView();
  }

  protected static void registerForUse() {
    Platform.getAppContext().registerWidgetClass(Platform.getSPOTName(CheckBoxTree.class), CheckBoxTreeViewer.class);
  }

  @Override
  protected void setIcons(iPlatformIcon checked, iPlatformIcon unchecked, iPlatformIcon indeterminate) {
    ((TreeView) getDataView()).setIcons(checked, unchecked, indeterminate);
  }
}
