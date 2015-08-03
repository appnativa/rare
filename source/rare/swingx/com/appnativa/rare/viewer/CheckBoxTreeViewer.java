/*
 * @(#)CheckBoxTreeViewer.java   2012-01-11
 *
 * Copyright (c) 2007-2009 appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.viewer;

import com.appnativa.rare.platform.swing.ui.view.TreeView;
import com.appnativa.rare.spot.CheckBoxList;
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
   * Creates a new checkbox list widget
   *
   * @param parent the parent
   * @param cfg the configuration
   *
   * @return the checkbox tree widget
   */
  public static CheckBoxTreeViewer create(iContainer fv, CheckBoxList cfg) {
    CheckBoxTreeViewer widget = new CheckBoxTreeViewer(fv);

    widget.configure(cfg);

    return widget;
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
  public void setManageChildNodeSelections(boolean manage) {
    getListView().setManageChildNodeSelections(manage);
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

  @Override
  protected void setIcons(iPlatformIcon checked, iPlatformIcon unchecked, iPlatformIcon indeterminate) {
    getListView().setIcons(checked, unchecked, indeterminate);
  }

  protected TreeView getListView() {
    return (TreeView) getDataView();
  }
}