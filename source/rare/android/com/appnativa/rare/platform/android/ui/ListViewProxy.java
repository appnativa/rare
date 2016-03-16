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

package com.appnativa.rare.platform.android.ui;

import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIStroke;
import com.appnativa.rare.ui.event.iActionListener;
import com.appnativa.rare.ui.event.iItemChangeListener;
import com.appnativa.rare.ui.iListHandler.SelectionMode;
import com.appnativa.rare.ui.iListView;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformListHandler;
import com.appnativa.rare.ui.iScrollerSupport;
import com.appnativa.rare.ui.renderer.aListItemRenderer;

public class ListViewProxy implements iListView {
  iPlatformListHandler handler;

  public ListViewProxy(iPlatformListHandler handler) {
    this.handler = handler;
  }

  @Override
  public void addSelectionIndex(int index) {
    handler.addSelectionIndex(index);
  }

  @Override
  public void clearContextMenuIndex() {
    handler.clearContextMenuIndex();
  }

  @Override
  public UIColor getAlternatingRowColor() {
    return handler.getAlternatingRowColor();
  }

  @Override
  public int getFirstVisibleIndex() {
    return handler.getFirstVisibleIndex();
  }

  @Override
  public int getHilightedIndex() {
    return 0;
  }
  
  @Override
  public void repaintRow(int row) {
    handler.repaintRow(row);
  }

  @Override
  public aListItemRenderer getItemRenderer() {
    return (aListItemRenderer) handler.getItemRenderer();
  }

  @Override
  public int getLastVisibleIndex() {
    return handler.getLastVisibleIndex();
  }

  @Override
  public iPlatformComponent getListComponent() {
    return handler.getListComponent();
  }

  @Override
  public int getContextMenuIndex() {
    return handler.getContextMenuIndex();
  }

  @Override
  public int getSelectedIndex() {
    return handler.getSelectedIndex();
  }

  @Override
  public boolean isAutoHilight() {
    return false;
  }

  @Override
  public boolean isKeepSelectionVisible() {
    return handler.isKeepSelectionVisible();
  }

  @Override
  public boolean isRowSizeFixed() {
    return true;
  }

  @Override
  public boolean isSelectable() {
    return false;
  }

  @Override
  public boolean isSingleClickAction() {
    return handler.isSingleClickAction();
  }

  @Override
  public void makeSelectionVisible() {
    handler.makeSelectionVisible();
  }

  @Override
  public void scrollRowToBottom(int row) {
    handler.scrollRowToBottom(row);
  }

  @Override
  public void scrollRowToTop(int row) {
    handler.scrollRowToTop(row);
  }

  @Override
  public void scrollRowToVisible(int row) {
    handler.scrollRowToVisible(row);
  }

  @Override
  public void setActionListener(iActionListener l) {}

  @Override
  public void setAlternatingRowColor(UIColor color) {
    handler.setAlternatingRowColor(color);
  }

  @Override
  public void setAutoHilight(boolean autoHilight) {
    handler.setAutoHilight(autoHilight);
  }

  @Override
  public void setDividerLine(UIColor c, UIStroke stroke) {
    handler.setDividerLine(c, stroke);
  }
  
  @Override
  public void setKeepSelectionVisible(boolean keepSelectionVisible) {
    handler.setKeepSelectionVisible(keepSelectionVisible);
  }

  @Override
  public void setMinimumVisibleRowCount(int rows) {
    handler.setMinimumVisibleRowCount(rows);
  }

  @Override
  public void setRowHeight(int height) {
    handler.setRowHeight(height);
  }

  @Override
  public void setSelectable(boolean selectable) {}

  @Override
  public void setSelectionChangeListener(iItemChangeListener l) {}

  @Override
  public void setSelectionMode(SelectionMode selectionMode) {
    handler.setSelectionMode(selectionMode);
  }

  @Override
  public void setShowDivider(boolean show) {
    handler.setShowDivider(show);
  }

  @Override
  public void setSingleClickAction(boolean singleClickAction) {
    handler.setSingleClickAction(singleClickAction);
  }

  @Override
  public void setVisibleRowCount(int rows) {
    handler.setVisibleRowCount(rows);
  }

  @Override
  public iScrollerSupport getScrollerSupport() {
    return handler;
  }

  @Override
  public void setShowLastDivider(boolean show) {
    if (handler instanceof ListBoxListHandler) {
      ((ListBoxListHandler) handler).setShowLastDivider(show);
    }
  }

  @Override
  public int getSelectedIndexCount() {
    return handler.getSelectedIndexCount();
  }
}
