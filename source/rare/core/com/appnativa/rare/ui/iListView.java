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

import com.appnativa.rare.ui.event.iActionListener;
import com.appnativa.rare.ui.event.iItemChangeListener;
import com.appnativa.rare.ui.iListHandler.SelectionMode;
import com.appnativa.rare.ui.renderer.aListItemRenderer;

public interface iListView {
  public enum EditingMode {
    NONE, REORDERING, SELECTION, DELETEING, REORDERING_AND_SELECTION, REORDERING_AND_DELETEING;
  }

  void addSelectionIndex(int index);
  void clearContextMenuIndex();

  void scrollRowToVisible(int row);

  void setActionListener(iActionListener l);

  void setAlternatingRowColor(UIColor color);

  void setAutoHilight(boolean autoHilight);

  void setDividerLine(UIColor color, UIStroke stroke);

  void setMinimumVisibleRowCount(int rows);

  void setRowHeight(int height);

  void setSelectable(boolean selectable);

  void setSelectionMode(SelectionMode selectionMode);

  void setSelectionChangeListener(iItemChangeListener l);

  void setShowDivider(boolean show);

  void setShowLastDivider(boolean show);

  void setSingleClickAction(boolean singleClickAction);

  void setVisibleRowCount(int rows);

  UIColor getAlternatingRowColor();

  int getFirstVisibleIndex();

  int getHilightedIndex();

  aListItemRenderer getItemRenderer();

  int getLastVisibleIndex();

  iPlatformComponent getListComponent();

  int getContextMenuIndex();

  int getSelectedIndex();

  int getSelectedIndexCount();
  
  boolean isAutoHilight();

  boolean isRowSizeFixed();

  boolean isSelectable();

  boolean isSingleClickAction();

  iScrollerSupport getScrollerSupport();

  void scrollRowToTop(int row);

  void scrollRowToBottom(int row);

  void makeSelectionVisible();

  public void repaintRow(int row);
  
  /**
   * Gets whether the table should keep it's selection visible
   * when the size of the table is changed.
   *
   * @return true to keep visible; false otherwise
   */
  boolean isKeepSelectionVisible();

  /**
   * Sets whether the table should keep it's selection visible
   * when the size of the table is changed.
   *
   * @param keepSelectionVisible true to keep visible; false otherwise
   */
  void setKeepSelectionVisible(boolean keepSelectionVisible);
}
