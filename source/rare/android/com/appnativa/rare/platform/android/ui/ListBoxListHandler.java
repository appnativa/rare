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

import android.view.View;

import android.widget.ListView;

import com.appnativa.rare.platform.android.ui.view.ListViewEx;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.UIPoint;
import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.iPlatformListDataModel;
import com.appnativa.rare.ui.iScrollerSupport;
import com.appnativa.rare.ui.painter.UIScrollingEdgePainter;

/**
 * A base class for list style components
 *
 * @author Don DeCoteau
 */
public class ListBoxListHandler extends aAdapterListHandler implements iScrollerSupport {
  private int choiceMode = ListView.CHOICE_MODE_SINGLE;

  public ListBoxListHandler(ListView view, iPlatformListDataModel model) {
    super(view, model);
  }

  public void addSelectionIndex(int index) {
    if ((index > -1) && (index < getItemCount())) {
      getListView().setItemChecked(index, true);
    }
  }

  public void clear() {
    final boolean empty = isEmpty();

    super.clear();

    if (!empty) {
      getListView().setAdapter(getListView().getAdapter());
    }
  }

  @Override
  public void clearPopupMenuIndex() {
    ListView v = getListView();

    if (v instanceof ListViewEx) {
      ((ListViewEx) getListView()).clearPopupMenuIndex();
    }
  }

  public void removeSelection(int index) {
    if ((index > -1) && (index < getItemCount())) {
      ListView lv = getListView();

      lv.setItemChecked(index, false);
    }
  }

  @Override
  public void scrollRowToTop(int row) {
    getListView().scrollRowToTop(row);
  }

  @Override
  public void scrollRowToBottom(int row) {
    getListView().scrollRowToBottom(row);
  }

  public void scrollRowToVisible(final int row) {
    getListView().scrollRowToVisible(row);
  }

  public void selectAll() {
    int      len = listModel.size();
    ListView lv  = getListView();

    for (int i = 0; i < len; i++) {
      lv.setItemChecked(i, true);
    }
  }

  public void sizeRowsToFit() {
    getAdapterView().requestLayout();
  }

  public boolean isAtBottomEdge() {
    return getListView().isAtBottomEdge();
  }

  public boolean isAtLeftEdge() {
    return getListView().isAtLeftEdge();
  }

  public boolean isAtRightEdge() {
    return getListView().isAtRightEdge();
  }

  public boolean isAtTopEdge() {
    return getListView().isAtTopEdge();
  }

  public UIPoint getContentOffset() {
    return getListView().getContentOffset();
  }

  public boolean isScrolling() {
    return getListView().isScrolling();
  }

  public void setListSelectable(boolean selectable) {
    if (!selectable) {
      choiceMode = getListView().getChoiceMode();
      getListView().setChoiceMode(ListView.CHOICE_MODE_NONE);
    } else {
      getListView().setChoiceMode(choiceMode);
    }
  }

  public void setSelectedIndex(int index) {
    ListView lv = getListView();

    if ((index < 0) || (index >= listModel.size())) {
      if (lv.getChoiceMode() == ListView.CHOICE_MODE_SINGLE) {
        index = lv.getCheckedItemPosition();

        if (index != -1) {
          lv.setItemChecked(index, false);
          lv.invalidate();
        }
      }

      lv.clearChoices();

      return;
    }

    lv.setItemChecked(index, true);
  }

  public void setSelectedIndexes(int[] indexes) {
    ListView lv = getListView();

    lv.clearChoices();

    int size = listModel.size();

    for (int i = 0; i < indexes.length; i++) {
      if ((indexes[i] > -1) && (indexes[i] < size)) {
        lv.setSelection(indexes[i]);
      }
    }
  }

  public int getMaxSelectionIndex() {
    return getListView().getMaxSelectionIndex();
  }

  public int getMinSelectionIndex() {
    return getListView().getMinSelectionIndex();
  }

  @Override
  public int getPopupMenuIndex() {
    return getListView().getPopupMenuIndex();
  }

  public int getPreferredHeight(int row) {
    if ((row < 0) || (row > getItemCount())) {
      return ScreenUtils.lineHeight(getListComponent());
    }

    View v = getListView().getAdapter().getView(row, null, getListView());

    return v.getMeasuredHeight();
  }

  public UIRectangle getRowBounds(int row0, int row1) {
    ListViewEx lv  = (ListViewEx) getListView();
    int        len = lv.getCount();

    if ((row0 < 0) || (row0 > row1) || (row0 > len) || (row1 > len)) {
      View        view = lv.getViewForRow(row0);
      UIRectangle r    = null;

      if (view != null) {
        r = new UIRectangle(view.getLeft(), view.getTop(), view.getWidth(), view.getHeight());
      }

      view = lv.getViewForRow(row1);

      if (view != null) {
        UIRectangle r2 = new UIRectangle(view.getLeft(), view.getTop(), view.getWidth(), view.getHeight());

        if (r != null) {
          r = r.union(r2);
        } else {
          r = r2;
        }
      }

      if (r != null) {
        return r;
      }
    }

    return new UIRectangle(0, 0, 0, 0);
  }

  public int getRowIndexAt(float x, float y) {
    return ((ListView) getAdapterView()).pointToPosition((int) x, (int) y);
  }

  public int getSelectedIndex() {
    return getListView().getSelectedIndex();
  }

  public int getSelectedIndexCount() {
    return getListView().getSelectedIndexCount();
  }

  public int[] getSelectedIndexes() {
    return getListView().getSelectedIndexes();
  }

  public RenderableDataItem getSelectedItem() {
    int n = getSelectedIndex();

    return (n < 0)
           ? null
           : listModel.get(n);
  }

  public boolean isListSelectable() {
    return getListView().getChoiceMode() != ListView.CHOICE_MODE_NONE;
  }

  public boolean isRowSelected(int row) {
    return getListView().isItemChecked(row);
  }

  /**
   * Registers this object as a selection listener
   */
  protected void registerSelectionListener() {
    getListView().setOnItemSelectedListener(this);
  }

  protected ListViewEx getListView() {
    return (ListViewEx) getAdapterView();
  }

  @Override
  public void setSelectionMode(SelectionMode selectionMode) {
    getListView().setSelectionMode(selectionMode);
  }

  @Override
  public void setScrollingEdgePainter(UIScrollingEdgePainter painter) {
    getListView().setScrollingEdgePainter(painter);
  }

  @Override
  public UIScrollingEdgePainter getScrollingEdgePainter() {
    return getListView().getScrollingEdgePainter();
  }

  @Override
  public void scrollToBottomEdge() {
    getListView().scrollToBottomEdge();
  }

  @Override
  public void scrollToLeftEdge() {
    getListView().scrollToLeftEdge();
  }

  @Override
  public void scrollToRightEdge() {
    getListView().scrollToRightEdge();
  }

  @Override
  public void scrollToTopEdge() {
    getListView().scrollToTopEdge();
  }

  @Override
  public void moveUpDown(boolean up, boolean block) {
    getListView().moveUpDown(up, block);
  }

  @Override
  public void moveLeftRight(boolean left, boolean block) {
    getListView().moveLeftRight(left, block);
  }

  public void setShowLastDivider(boolean show) {
    getListView().setShowLastDivider(show);
  }
}