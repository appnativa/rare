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

package com.appnativa.rare.ui;

import com.appnativa.rare.ui.event.iDataModelListener;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.util.CharacterIndex;
import com.appnativa.util.iFilterableList;

import java.util.Collection;
import java.util.List;

/**
 *
 * @author Don DeCoteau
 */
public interface iListDataModel extends iFilterableList<RenderableDataItem> {
  void addDataModelListener(iDataModelListener l);

  void dispose();

  void editModeChangeAllMarks(boolean mark);

  void editModeChangeMark(int index, boolean mark);

  void editModeClearMarks();

  int editModeGetMarkCount();

  int[] editModeGetMarkedIndices();

  RenderableDataItem[] editModeGetMarkedItems();

  boolean editModeIsItemMarked(int index);

  void editModeToggleMark(int index);

  void refreshItems();

  void removeDataModelListener(iDataModelListener l);

  @Override
  void removeRows(int[] indexes);

  void removeRows(int firstRow, int lastRow);

  void rowChanged(int index);

  void rowChanged(RenderableDataItem item);

  void rowsChanged(int... index);

  void rowsChanged(int firstRow, int lastRow);

  /**
   * Notifies the model that the specified range of rows have been deleted
   *
   * @param firstRow the first row in the range
   * @param lastRow the last row in the range
   * @param removed the items that were removed
   */
  void rowsDeleted(int firstRow, int lastRow, List<RenderableDataItem> removed);

  /**
   * Notifies the model that the specified range of rows have been inserted
   *
   * @param firstRow the first row in the range
   * @param lastRow the last row in the range
   */
  void rowsInserted(int firstRow, int lastRow);

  @Override
  boolean setAll(Collection<? extends RenderableDataItem> collection);

  void setColumnDescription(Column itemDescription);

  void setEventsEnabled(boolean enabled);

  void setItems(Collection<? extends RenderableDataItem> collection);

  /**
   * Tells the model to create an index to use for filtering.
   * This will improve filtering performance for large indexes
   * @param useIndex true to use an index false otherwise
   */
  void setUseIndexForFiltering(boolean useIndex);

  /**
   * Sets the widget the model is associated with
   * @param widget the widget the model is associated with
   */
  void setWidget(iWidget widget);

  /**
   * Gets a rendering description for items in the model
   * @return a rendering description for items in the model
   */
  Column getColumnDescription();

  /**
   * Returns the filtering filtering index for the model
   * @return the filtering index for the list or null is the model is not using an index
   */
  CharacterIndex getFilteringIndex();

  boolean isEventsEnabled();
}
