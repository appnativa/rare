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

import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.UIStroke;
import com.appnativa.rare.ui.aListHandler;
import com.appnativa.rare.ui.event.iActionListener;
import com.appnativa.rare.ui.event.iItemChangeListener;
import com.appnativa.rare.ui.iListView;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformItemRenderer;
import com.appnativa.rare.ui.iPlatformListHandler;
import com.appnativa.rare.ui.iScrollerSupport;
import com.appnativa.rare.ui.iTableModel;
import com.appnativa.util.iStringConverter;

public abstract class aMultipleListHandler extends aListHandler implements iScrollerSupport {
  protected iPlatformListHandler     mainHandler;
  protected iPlatformListHandler[]   listHandlers;
  protected MultiTableTableComponent multiTableComponent;
  iTableModel                        tableModel;

  public aMultipleListHandler(MultiTableTableComponent mtc, iListView lv, iPlatformListHandler main,
                              iPlatformListHandler handler1, iPlatformListHandler handler2) {
    super(lv, mtc.getModel());
    multiTableComponent = mtc;
    tableModel          = mtc.getModel();

    if (handler2 != null) {
      listHandlers    = new iPlatformListHandler[3];
      listHandlers[2] = handler2;
    } else {
      listHandlers = new iPlatformListHandler[2];
    }

    mainHandler     = main;
    listHandlers[0] = main;
    listHandlers[1] = handler1;
  }

  @Override
  public void addActionListener(iActionListener l) {
    for (int i = 0; i < listHandlers.length; i++) {
      listHandlers[i].addActionListener(l);
    }
  }

  @Override
  public int[] getCheckedIndexes() {
    return null;
  }

  @Override
  public void addSelectionChangeListener(iItemChangeListener l) {
    for (int i = 0; i < listHandlers.length; i++) {
      listHandlers[i].addSelectionChangeListener(l);
    }
  }

  @Override
  public void removeActionListener(iActionListener l) {
    for (int i = 0; i < listHandlers.length; i++) {
      listHandlers[i].addActionListener(l);
    }
  }

  @Override
  public void removeSelectionChangeListener(iItemChangeListener l) {
    for (int i = 0; i < listHandlers.length; i++) {
      listHandlers[i].addSelectionChangeListener(l);
    }
  }

  @Override
  public void addSelectionIndex(int index) {
    mainHandler.addSelectionIndex(index);
  }

  @Override
  public void clearContextMenuIndex() {
    mainHandler.clearContextMenuIndex();
  }

  @Override
  public void clearSelection() {
    mainHandler.clearSelection();
  }

  @Override
  public boolean isAtLeftEdge() {
    return mainHandler.isAtLeftEdge();
  }

  @Override
  public void scrollToLeftEdge() {
    mainHandler.scrollToLeftEdge();
  }

  @Override
  public void scrollToRightEdge() {
    mainHandler.scrollToRightEdge();
  }

  @Override
  public boolean isAtRightEdge() {
    return mainHandler.isAtRightEdge();
  }

  @Override
  public void moveUpDown(boolean up, boolean block) {
    mainHandler.moveUpDown(up, block);
  }

  @Override
  public void moveLeftRight(boolean left, boolean block) {
    mainHandler.moveLeftRight(left, block);
  }

  @Override
  public void dispose() {
    if (tableModel != null) {
      super.dispose();
      tableModel.dispose();

      for (int i = 0; i < listHandlers.length; i++) {
        listHandlers[i].dispose();
        listHandlers[i] = null;
      }
    }

    listHandlers = null;
    mainHandler  = null;
    tableModel   = null;
  }

  @Override
  public void fireActionForSelected() {
    mainHandler.fireActionForSelected();
  }

  @Override
  public UIColor getAlternatingRowColor() {
    return mainHandler.getAlternatingRowColor();
  }

  @Override
  public int getAnchorSelectionIndex() {
    return mainHandler.getAnchorSelectionIndex();
  }

  @Override
  public iPlatformComponent getContainerComponent() {
    return mainHandler.getContainerComponent();
  }

  @Override
  public int getFirstVisibleIndex() {
    return mainHandler.getFirstVisibleIndex();
  }

  @Override
  public int getLastVisibleIndex() {
    return mainHandler.getLastVisibleIndex();
  }

  @Override
  public iPlatformComponent getListComponent() {
    return mainHandler.getListComponent();
  }

  @Override
  public SelectionType getListSelectionType() {
    return mainHandler.getListSelectionType();
  }

  @Override
  public int getMinSelectionIndex() {
    return mainHandler.getMinSelectionIndex();
  }

  @Override
  public int getContextMenuIndex() {
    return mainHandler.getContextMenuIndex();
  }

  @Override
  public int getRowHeight() {
    return mainHandler.getRowHeight();
  }

  @Override
  public RenderableDataItem getContextMenuItem() {
    return mainHandler.getContextMenuItem();
  }

  @Override
  public UIRectangle getRowBounds(int row0, int row1) {
    UIRectangle r = mainHandler.getRowBounds(row0, row1);

    if (r != null) {
      for (iPlatformListHandler lc : listHandlers) {
        if (lc != mainHandler) {
          UIRectangle r2 = mainHandler.getRowBounds(row0, row1);

          if (r2 != null) {
            if (r2.x < r.x) {
              r.x     = r2.x;
              r.width += r2.width;
            } else if (r2.x >= (r.x + r.width)) {
              r.width += r2.width;
            }
          }
        }
      }
    }

    return r;
  }

  @Override
  public int getRowIndexAt(float x, float y) {
    for (iPlatformListHandler lc : listHandlers) {
      int w = lc.getContainerComponent().getWidth();

      if (x < w) {
        return lc.getRowIndexAt(x, y);
      }

      x -= w;
    }

    return mainHandler.getRowIndexAt(x, y);
  }

  @Override
  public int getSelectedIndex() {
    return mainHandler.getSelectedIndex();
  }

  @Override
  public int getSelectedIndexCount() {
    return mainHandler.getSelectedIndexCount();
  }

  @Override
  public int[] getSelectedIndexes() {
    return mainHandler.getSelectedIndexes();
  }

  @Override
  public boolean hasSelection() {
    return mainHandler.hasSelection();
  }

  @Override
  public boolean isRowSelected(int row) {
    return mainHandler.isRowSelected(row);
  }

  @Override
  public boolean isRowSelected(RenderableDataItem item) {
    return mainHandler.isRowSelected(item);
  }

  @Override
  public boolean isTabular() {
    return true;
  }

  @Override
  public void makeSelectionVisible() {
    mainHandler.makeSelectionVisible();
  }

  @Override
  public void refreshItems() {
    super.refreshItems();

    for (iPlatformListHandler lc : listHandlers) {
      lc.refreshItems();
    }
  }

  @Override
  public void removeSelection(int index) {
    mainHandler.removeSelection(index);
  }

  @Override
  public void scrollRowToTop(int row) {
    mainHandler.scrollRowToTop(row);
  }

  @Override
  public void scrollRowToBottom(int row) {
    mainHandler.scrollRowToBottom(row);
  }

  @Override
  public void scrollRowToVisible(int row) {
    mainHandler.scrollRowToVisible(row);
  }

  @Override
  public void selectAll() {
    mainHandler.selectAll();
  }

  @Override
  public void setAlternatingRowColor(UIColor color) {
    for (iPlatformListHandler lc : listHandlers) {
      lc.setAlternatingRowColor(color);
    }
  }

  @Override
  public void setAutoHilight(boolean autoHilight) {
    for (iPlatformListHandler lc : listHandlers) {
      lc.setAutoHilight(autoHilight);
    }
  }

  @Override
  public void setChangeEventsEnabled(boolean enabled) {
    for (iPlatformListHandler lc : listHandlers) {
      lc.setChangeEventsEnabled(enabled);
    }
  }

  @Override
  public void setChangeSelColorOnLostFocus(boolean change) {
    for (iPlatformListHandler lc : listHandlers) {
      lc.setChangeSelColorOnLostFocus(change);
    }
  }

  @Override
  public void setConverter(iStringConverter<RenderableDataItem> converter) {
    for (iPlatformListHandler lc : listHandlers) {
      lc.setConverter(converter);
    }
  }

  @Override
  public void setDataEventsEnabled(boolean enabled) {
    for (iPlatformListHandler lc : listHandlers) {
      lc.setDataEventsEnabled(enabled);
    }
  }

  @Override
  public void setDeselectEventsDisabled(boolean disabled) {
    for (iPlatformListHandler lc : listHandlers) {
      lc.setDeselectEventsDisabled(disabled);
    }
  }

  @Override
  public void setDividerLine(UIColor c, UIStroke stroke) {
    for (iPlatformListHandler lc : listHandlers) {
      lc.setDividerLine(c, stroke);
    }
  }

  @Override
  public void setHandleFirstFocusSelection(boolean handle) {
    for (iPlatformListHandler lc : listHandlers) {
      lc.setHandleFirstFocusSelection(handle);
    }
  }

  @Override
  public void setKeepSelectionVisible(boolean keepSelectionVisible) {
    for (iPlatformListHandler lc : listHandlers) {
      lc.setKeepSelectionVisible(keepSelectionVisible);
    }
  }

  @Override
  public void setListSelectable(boolean selectable) {
    for (iPlatformListHandler lc : listHandlers) {
      lc.setListSelectable(selectable);
    }
  }

  @Override
  public void setListSelectionType(SelectionType type) {
    for (iPlatformListHandler lc : listHandlers) {
      lc.setListSelectionType(type);
    }
  }

  @Override
  public void setSelectionMode(SelectionMode selectionMode) {
    for (iPlatformListHandler lc : listHandlers) {
      lc.setSelectionMode(selectionMode);
    }
  }

  @Override
  public void setMinimumVisibleRowCount(int rows) {
    for (iPlatformListHandler lc : listHandlers) {
      lc.setMinimumVisibleRowCount(rows);
    }
  }

  @Override
  public void setMinRowHeight(int min) {
    for (iPlatformListHandler lc : listHandlers) {
      lc.setMinRowHeight(min);
    }
  }

  @Override
  public void setRowHeight(int height) {
    for (iPlatformListHandler lc : listHandlers) {
      lc.setRowHeight(height);
    }
  }

  @Override
  public void setSelectedIndex(int index) {
    mainHandler.setSelectedIndex(index);
  }

  @Override
  public void setSelectedIndexes(int[] indices) {
    mainHandler.setSelectedIndexes(indices);
  }

  @Override
  public void setSelectedItem(RenderableDataItem value) {
    mainHandler.setSelectedItem(value);
  }

  @Override
  public void setShowDivider(boolean show) {
    for (iPlatformListHandler lc : listHandlers) {
      lc.setShowDivider(show);
    }
  }

  @Override
  public void setSingleClickAction(boolean singleClick) {
    for (iPlatformListHandler lc : listHandlers) {
      lc.setSingleClickAction(singleClick);
    }
  }

  @Override
  public void setVisibleRowCount(int rows) {
    for (iPlatformListHandler lc : listHandlers) {
      lc.setVisibleRowCount(rows);
    }
  }

  @Override
  public void sizeRowsToFit() {
    mainHandler.sizeRowsToFit();
  }

  @Override
  public iPlatformItemRenderer getItemRenderer() {
    return mainHandler.getItemRenderer();
  }

  @Override
  protected void setRowHeightEx(int height) {}

  @Override
  protected void setSelectedIndexEx(int index) {}

  @Override
  protected void setSelectedIndexesEx(int[] indices) {}
}
