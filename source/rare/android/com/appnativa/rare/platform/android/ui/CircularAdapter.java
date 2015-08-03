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

import android.database.DataSetObserver;

import android.view.View;
import android.view.ViewGroup;

import android.widget.ListAdapter;
import android.widget.SpinnerAdapter;

/**
 *
 * @author Don DeCoteau
 */
public class CircularAdapter implements ListAdapter, SpinnerAdapter {
  private final int         HALF_MAX_VALUE = Integer.MAX_VALUE / 2;
  private int               MIDDLE;
  private int               SIZE;
  private DataItemListModel listModel;

  public CircularAdapter(DataItemListModel m) {
    listModel = m;
    m.registerDataSetObserver(new DataSetObserver() {
      @Override
      public void onInvalidated() {
        super.onInvalidated();
        SIZE   = getListModel().size();
        MIDDLE = HALF_MAX_VALUE - HALF_MAX_VALUE % SIZE;
      }
    });
    SIZE   = m.size();
    MIDDLE = HALF_MAX_VALUE - HALF_MAX_VALUE % SIZE;
  }

  public boolean areAllItemsEnabled() {
    return getListModel().areAllItemsEnabled();
  }

  public void registerDataSetObserver(DataSetObserver dso) {
    getListModel().registerDataSetObserver(dso);
  }

  public void unregisterDataSetObserver(DataSetObserver dso) {
    getListModel().unregisterDataSetObserver(dso);
  }

  public int getCount() {
    return Integer.MAX_VALUE;
  }

  public View getDropDownView(int position, View convertView, ViewGroup parent) {
    return getListModel().getDropDownView(position % SIZE, convertView, parent);
  }

  public Object getItem(int position) {
    return getListModel().getItem(position % SIZE);
  }

  public long getItemId(int position) {
    return getListModel().getItemId(position % SIZE);
  }

  public int getItemViewType(int position) {
    return getListModel().getItemViewType(position % SIZE);
  }

  /**
   * @return the listModel
   */
  public DataItemListModel getListModel() {
    return listModel;
  }

  public final int getMiddle() {
    return MIDDLE;
  }

  public View getView(int position, View convertView, ViewGroup parent) {
    return getListModel().getView(position % SIZE, convertView, parent);
  }

  public int getViewTypeCount() {
    return getListModel().getViewTypeCount();
  }

  public boolean hasStableIds() {
    return getListModel().hasStableIds();
  }

  public boolean isEmpty() {
    return SIZE == 0;
  }

  public boolean isEnabled(int position) {
    return getListModel().isEnabled(position % SIZE);
  }
}
