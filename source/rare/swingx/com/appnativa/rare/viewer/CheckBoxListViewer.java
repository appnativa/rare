/*
 * @(#)CheckBoxListViewer.java
 * 
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.viewer;

import com.appnativa.rare.platform.swing.ui.view.ListView;
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
  	ListView list = (ListView) getDataView();
    list.setIcons(checked, unchecked,indeterminate);
  }
}
