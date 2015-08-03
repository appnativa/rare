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

import android.widget.ListAdapter;

import com.appnativa.rare.ui.iPlatformListDataModel;
import com.appnativa.rare.ui.iTableModel;
import com.appnativa.rare.widget.iWidget;

/**
 *
 *
 * @version    0.3, 2007-05-14
 * @author     Don DeCoteau
 */
public class DataItemTableModel extends aTableAdapter implements ListAdapter, iPlatformListDataModel {
  public DataItemTableModel() {
    super();
  }

  public DataItemTableModel(iWidget widget) {
    super(widget);
  }

  public iTableModel createEmptyCopy() {
    return new DataItemTableModel(getWidget());
  }
}
