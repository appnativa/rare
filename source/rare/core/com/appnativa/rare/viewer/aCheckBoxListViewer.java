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

import java.util.List;

import com.appnativa.rare.Platform;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.scripting.iScriptHandler;
import com.appnativa.rare.spot.CheckBoxList;
import com.appnativa.rare.spot.CheckBoxTree;
import com.appnativa.rare.spot.ListBox;
import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.aWidgetListener;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.event.ItemChangeEvent;
import com.appnativa.rare.ui.event.iItemChangeListener;
import com.appnativa.rare.util.DataItemCollection;

public abstract class aCheckBoxListViewer extends ListBoxViewer {
  protected CheckBoxChangeHandler checkBoxChangeHandler;
  protected boolean               linkedSelection;
  protected boolean               settingFromHTTPFormValue;

  public aCheckBoxListViewer() {
    this(null);
  }

  /**
   * Constructs a new instance
   *
   * @param parent the widget's parent
   */
  public aCheckBoxListViewer(iContainer parent) {
    super(parent);
    widgetType = WidgetType.CheckBoxList;
  }

  /**
   * Clears all check marks
   */
  public abstract void clearCheckMarks();

  @Override
  public void dispose() {
    super.dispose();
    checkBoxChangeHandler = null;
  }

  /**
   * Removes the the handler that receives  changes to checkbox selection
   * states
   */
  public void removeCheckBoxChangedHandler() {
    if (checkBoxChangeHandler != null) {
      checkBoxChangeHandler = null;
    }
  }

  @Override
  public void swap(int index1, int index2) {
    super.swap(index1, index2);
    setRowChecked(index2, isRowChecked(index1));
    setRowChecked(index1, isRowChecked(index2));
  }

  /**
   * Sets the handler that will receive changes to checkbox selection
   * states.
   *
   * @param handler the code for the handler
   */
  public void setCheckBoxChangedHandler(String handler) {
    if (handler == null) {
      removeCheckBoxChangedHandler();
    } else {
      if (checkBoxChangeHandler == null) {
        checkBoxChangeHandler = new CheckBoxChangeHandler();
      }

      checkBoxChangeHandler.setHandler(handler);
    }
  }

  /**
   * Sets whether the checkbox is leading or trailing the list
   *
   * @param value
   *          true for the right; false for the left
   */
  public void setCheckboxTrailing(boolean value) {
    setListSelectionType(value
                         ? SelectionType.CHECKED_RIGHT
                         : SelectionType.CHECKED_LEFT);
  }

  /**
   * Set the checked state of an item in the list
   *
   * @param item the item
   * @param checked true to check the row; false to uncheck
   */
  public void setCheckedItem(RenderableDataItem item, boolean checked) {
    setRowChecked(indexOf(item), checked);
  }

  /**
   * Selects a set of rows.
   *
   * @param indices the indices of the rows to select
   */
  public abstract void setCheckedRows(int[] indices);

  @Override
  public void setFromHTTPFormValue(Object value) {
    try {
      settingFromHTTPFormValue = true;
      super.setFromHTTPFormValue(value);
    } finally {
      settingFromHTTPFormValue = false;
    }
  }

  /**
   * Sets whether  whether checkboxes and the list selection are automatically linked
   *
   * @param linked true if checkboxes and the list selection are automatically linked; false otherwise
   */
  public void setLinkSelection(boolean linked) {
    linkedSelection = linked;
  }

  /**
   * Set the checked state of a row
   *
   * @param row the row
   * @param checked true to check the row; false to un-check
   */
  public abstract void setRowChecked(int row, boolean checked);

  @Override
  public void setSelectedIndex(int index) {
    if (settingFromHTTPFormValue && (index > -1)) {
      switch(submitValueType) {
        case CheckBoxTree.CSubmitValue.checked_index :
        case CheckBoxTree.CSubmitValue.checked_linked_data :
          setRowChecked(index, true);

          return;
      }
    }

    super.setSelectedIndex(index);
  }

  @Override
  public void setSelectedIndexes(int[] indices) {
    if (settingFromHTTPFormValue && (indices != null)) {
      clearSelection();

      switch(submitValueType) {
        case CheckBoxTree.CSubmitValue.checked_index :
        case CheckBoxTree.CSubmitValue.checked_linked_data :
          setCheckedRows(indices);

          return;
      }
    }

    super.setSelectedIndexes(indices);
  }

  /**
   * Returns an array of the linked data of all of the checked rows, in
   * increasing order.
   * <p>
   * NOTE: The checked items are not necessarily the same as the selected items
   *
   * @return an array of the linked data for all of the check rows, or
   *         null if there are no checked rows
   */
  public Object[] getCheckedData() {
    return DataItemCollection.getValues(listComponent, getCheckedRows(), -1, true);
  }

  /**
   * Returns a string array of the linked data of all of the checked rows, in increasing order.
   * <p>
   * NOTE: The checked items are not necessarily the same as the selected items
   *
   * @return a string array of the linked data for all of the check rows, or null if there are no checked rows
   */
  public String[] getCheckedDataAsStrings() {
    return getSelectionsDataAsStrings();
  }

  /**
   * Gets the first item with a checked item
   *
   * @return the first item with a checked item
   */
  public Object getCheckedItem() {
    return getCheckedRowItem();
  }

  /**
   * Gets the string value of the first row with a checked item
   *
   * @return the string value of the first row with a checked item
   */
  public String getCheckedItemAsString() {
    RenderableDataItem di = getCheckedRowItem();

    return (di == null)
           ? null
           : di.toString();
  }

  /**
   * Returns the value for all the rows that have check marks.
   * <p>
   * NOTE: The checked items are not necessarily the same as the selected items
   *
   * @return an array with the value for all the rows that have check marks or null if there are none
   */
  public Object[] getCheckedItems() {
    return getSelections();
  }

  /**
   * Returns a string array of the value for all the checked rows, in increasing order.
   * <p>
   * NOTE: The checked items are not necessarily the same as the selected items
   *
   * @return a string array of the value for all the check rows, or null if there are no checked rows
   */
  public String[] getCheckedItemsAsStrings() {
    return getSelectionsAsStrings();
  }

  /**
   * Gets the first row with a check item
   *
   * @return the first row with a check item
   */
  public RenderableDataItem getCheckedRowItem() {
    return getSelectedItem();
  }

  /**
   * Returns the indices for all the rows that have check marks.
   * <p>
   * NOTE: The checked items are not necessarily the same as the selected items
   *
   * @return the indices for all the rows that have check marks or null if there are none
   */
  public int[] getCheckedRows() {
    return getSelectedIndexes();
  }

  @Override
  public Object getHTTPFormValue() {
    if (!hasSelection() &&!hasCheckedRows()) {
      return null;
    }

    switch(submitValueType) {
      case ListBox.CSubmitValue.checked_index :
        return getCheckedRows();

      case ListBox.CSubmitValue.checked_linked_data :
        return getCheckedData();

      default :
        return super.getHTTPFormValue();
    }
  }

  /**
   * Gets whether  whether checkboxes and the list selection are automatically linked
   *
   * @return true if checkboxes and the list selection are automatically linked; false otherwise
   */
  public boolean getLinkSelection() {
    return linkedSelection;
  }

  /**
   * Returns whether there are any checked rows
   * @return true if there are checked rows; false otherwise
   */
  public abstract boolean hasCheckedRows();

  /**
   * Gets whether the checkbox is leading or trailing the list
   *
   * @return true for the right; false for the left
   */
  public boolean isCheckboxTrailing() {
    return SelectionType.CHECKED_RIGHT == getListSelectionType();
  }

  /**
   * Returns whether the specified row is checked
   *
   * @param row the row to test
   *
   * @return true if the specified row is checked; false otherwise
   */
  public abstract boolean isRowChecked(int row);

  @Override
  public boolean isValidForSubmission(boolean showerror) {
    switch(submitValueType) {
      case ListBox.CSubmitValue.checked_value_text :
      case ListBox.CSubmitValue.checked_linked_data :
      case ListBox.CSubmitValue.checked_index :
        return hasCheckedRows();

      default :
        return listComponent.hasSelection();
    }
  }

  @Override
  protected void configureEx(ListBox cfg) {
    super.configureEx(cfg);

    CheckBoxList cbl = (CheckBoxList) cfg;

    if (cbl.checkboxTrailing.spot_valueWasSet() && Platform.isTouchDevice()) {
      setCheckboxTrailing(cbl.checkboxTrailing.booleanValue());
    } else {
      setCheckboxTrailing(Platform.isTouchDevice()
                          ? true
                          : false);
    }

    setLinkSelection(cbl.linkSelection.booleanValue());

    UIColor fg = getForeground();

    if (fg == null) {
      fg = ColorUtils.getForeground();
    }

    iPlatformIcon checked;
    iPlatformIcon unchecked;
    iPlatformIcon indeterminate;

    if (fg.isDarkColor()) {
      checked       = Platform.getResourceAsIcon("Rare.icon.checkboxchecked");
      unchecked     = Platform.getResourceAsIcon("Rare.icon.checkbox");
      indeterminate = Platform.getResourceAsIcon("Rare.icon.checkboxindeterminate");
    } else {
      checked       = Platform.getResourceAsIcon("Rare.icon.checkboxchecked.light");
      unchecked     = Platform.getResourceAsIcon("Rare.icon.checkbox.light");
      indeterminate = Platform.getResourceAsIcon("Rare.icon.checkboxindeterminate.light");
    }

    setIcons(checked, unchecked, indeterminate);
  }

  @Override
  protected void finishedLoadingEx() {
    if (isDisposed()) {
      return;
    }

    super.finishedLoadingEx();

    if (itemAttributes != null) {
      if (itemAttributes.check != null) {
        setCheckedItem(itemAttributes.check, true);
      }

      List<RenderableDataItem> list = itemAttributes.checked;
      int                      len  = (list == null)
                                      ? 0
                                      : list.size();

      for (int i = 0; i < len; i++) {
        int n = indexOf(list.get(i));

        if (n != -1) {
          setRowChecked(n, true);
        }
      }

      if ((getFormViewer() == null) ||!getFormViewer().isRetainInitialWidgetValues()) {
        itemAttributes = null;
      }
    }
  }

  protected abstract void setIcons(iPlatformIcon checked, iPlatformIcon unchecked, iPlatformIcon indeterminate);

  private class CheckBoxChangeHandler implements iItemChangeListener {
    String handler;
    Object scriptCode;

    @Override
    public void itemChanged(ItemChangeEvent e) {
      iScriptHandler sh = getScriptHandler();

      if (scriptCode == null) {
        scriptCode = sh.compile(getScriptingContext(), getName() + ".CheckBoxChangeHandler", handler);
        handler    = null;
      }

      aWidgetListener.execute(aCheckBoxListViewer.this, sh, scriptCode, iConstants.EVENT_CHANGE, e);
    }

    public void setHandler(String handler) {
      this.handler = handler;
      scriptCode   = null;
    }
  }
}
