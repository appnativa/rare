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

package com.appnativa.rare.ui.spinner;

import java.util.List;

/**
 *
 * @author Don DeCoteau
 */
public class SpinnerListModel extends aSpinnerModel {
  boolean     isCircular = false;
  List        list;
  private int index;

  public SpinnerListModel(boolean circular) {
    isCircular = circular;
  }

  public SpinnerListModel(List values, boolean circular) {
    this.list  = values;
    isCircular = circular;
  }

  @Override
  public Object fromString(String value) {
    return (list.indexOf(value) == -1)
           ? null
           : value;
  }

  public void setList(List list) {
    if ((list == null) || (list.isEmpty())) {
      throw new IllegalArgumentException("invalid list");
    }

    this.list = list;
    index     = 0;
    fireStateChanged();
  }

  @Override
  public void setValue(Object value) {
    int n = list.indexOf(value);

    if (n == -1) {
      throw new IllegalArgumentException("invalid value");
    } else if (n != this.index) {
      this.index = n;
      fireStateChanged();
    }
  }

  @Override
  public void dispose() {
    super.dispose();

    if (list != null) {
      list.clear();
      list = null;
    }
  }

  public List getList() {
    return list;
  }

  @Override
  public Object getNextValue() {
    Object o = (index + 1 < list.size())
               ? list.get(index + 1)
               : null;

    if ((o == null) && isCircular && (getList().size() > 0)) {
      o = getList().get(0);
    }

    return o;
  }

  @Override
  public Object getPreviousValue() {
    Object o = (index > 0)
               ? list.get(index - 1)
               : null;

    if ((o == null) && isCircular && (getList().size() > 0)) {
      o = getList().get(getList().size() - 1);
    }

    return o;
  }

  @Override
  public Object getValue() {
    return (list == null)
           ? null
           : list.get(index);
  }

  @Override
  public boolean isCircular() {
    return isCircular;
  }
}
