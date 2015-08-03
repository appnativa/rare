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

import android.widget.ListAdapter;
import android.widget.SpinnerAdapter;

import com.appnativa.rare.ui.Column;
import com.appnativa.rare.widget.iWidget;

/**
 *
 * @author Don DeCoteau
 */
public class DataItemListModel extends aDataItemListModelEx implements ListAdapter, SpinnerAdapter {

  /** Creates a new instance of DataItemListModel */
  public DataItemListModel() {
    super();
  }

  /**
   * Creates a new instance of DataItemComboBoxModel
   * that is a copy of this one
   * @param m the model
   */
  public DataItemListModel(DataItemListModel m) {
    super(m);
  }

  /**
   * Creates a new instance of DataItemListModel
   *
   * @param widget {@inheritDoc}
   * @param column {@inheritDoc}
   */
  public DataItemListModel(iWidget widget, Column column) {
    super(widget, column);
  }

  public DataItemListModel copy() {
    return new DataItemListModel(this);
  }
}
