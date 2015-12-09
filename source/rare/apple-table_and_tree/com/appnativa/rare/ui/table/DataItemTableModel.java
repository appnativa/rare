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

import com.appnativa.rare.ui.Column;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.iTableModel;
import com.appnativa.util.iFilter;

public class DataItemTableModel extends aDataItemTableModel {
  private boolean uniformHeight = true;

  public DataItemTableModel() {
    super();
  }

  @Override
  public iTableModel createEmptyCopy() {
    return new DataItemTableModel();
  }

  @Override
  public void refreshItems() {
    fireTableDataChanged();
  }

  @Override
  public void rowChanged(RenderableDataItem item) {
    int n = indexForRow(item);

    if (n != -1) {
      item.setHeight(-1);
      item.setSpanningData(null);
      super.fireTableRowsUpdated(n, n);
    }
  }

  @Override
  public void rowsChanged(int... index) {
    int min = 0;
    int max = 0;

    for (int i : index) {
      if (i < min) {
        min = i;
      }

      if (i > max) {
        max = i;
      }

      if (!uniformHeight) {
        get(i).setHeight(-1);
      }
    }

    super.fireTableRowsUpdated(min, max);
  }

  @Override
  public void setColumnDescription(Column itemDescription) {}

  @Override
  public iFilter getLastFilter() {
    return null;
  }

  @Override
  public void fireTableRowsUpdated(int firstRow, int lastRow) {
    if (!uniformHeight) {
      if (firstRow < lastRow) {
        for (int i = firstRow; i <= lastRow; i++) {
          get(i).setHeight(-1);
        }
      } else {
        for (int i = lastRow; i <= firstRow; i++) {
          get(i).setHeight(-1);
        }
      }
    }

    super.fireTableRowsUpdated(firstRow, lastRow);
  }

  @Override
  protected void fireTableDataChanged() {
    if (!uniformHeight) {
      int len = size();

      for (int i = 0; i < len; i++) {
        get(i).setHeight(-1);
      }
    }

    super.fireTableDataChanged();
  }

  @Override
  protected void fireTableRowsInserted(int firstRow, int lastRow) {
    if (!uniformHeight) {
      if (firstRow < lastRow) {
        for (int i = firstRow; i <= lastRow; i++) {
          get(i).setHeight(-1);
        }
      } else {
        for (int i = lastRow; i <= firstRow; i++) {
          get(i).setHeight(-1);
        }
      }
    }

    super.fireTableRowsInserted(firstRow, lastRow);
  }

  public boolean isUniformHeight() {
    return uniformHeight;
  }

  public void setUniformHeight(boolean uniformHeight) {
    this.uniformHeight = uniformHeight;
  }

  @Override
  public void setEditing(boolean b) {
    // TODO Auto-generated method stub
  }
}
