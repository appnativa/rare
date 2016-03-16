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

package com.appnativa.rare.platform.swing.ui;

import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.iPlatformListDataModel;

import javax.swing.DefaultListSelectionModel;

public class SelectiveListSelectionModel extends DefaultListSelectionModel {
  boolean                        checked;
  private iPlatformListDataModel listModel;
  private iSelectionChecker      selectionChecker;
  private boolean toggleSelections;

  public SelectiveListSelectionModel(iPlatformListDataModel listModel) {
    this.listModel = listModel;
  }

  @Override
  public void addSelectionInterval(int index0, int index1) {
    if (checked) {
      super.addSelectionInterval(index0, index1);
    } else {
      setSelections(index0, index1, true);
    }
  }

  public void setListModel(iPlatformListDataModel listModel) {
    this.listModel = listModel;
  }

  public void setSelectionChecker(iSelectionChecker selectionChecker) {
    this.selectionChecker = selectionChecker;
  }

  @Override
  public void setSelectionInterval(int index0, int index1) {
    if(toggleSelections) {
      if(index0==index1 && index0!=-1) {
        if(isSelectedIndex(index0)) {
          removeIndexInterval(index0, index1);
          return;
        }
        else if(getSelectionMode()==MULTIPLE_INTERVAL_SELECTION){
          addSelectionInterval(index0, index1);
          return;
        }
      }
    }
    if (checked) {
      super.setSelectionInterval(index0, index1);
    } else {
      setSelections(index0, index1, false);
    }
  }

  public iPlatformListDataModel getListModel() {
    return listModel;
  }

  public iSelectionChecker getSelectionChecker() {
    return selectionChecker;
  }

  protected void setSelections(int index0, int index1, boolean add) {
    if (listModel == null) {
      if ((index0 == -1) || (index1 == -1)) {
        super.clearSelection();
      } else if (add) {
        super.addSelectionInterval(index0, index1);
      } else {
        super.setSelectionInterval(index0, index1);
      }

      return;
    }

    if ((index0 == -1) || (index1 == -1)) {
      super.clearSelection();

      return;
    }

    checked = true;

    try {
      int                    start = index0;
      int                    end   = index1;
      iPlatformListDataModel lm    = listModel;
      int                    len   = lm.size();

      if (end >= len) {
        end = len - 1;
      }

      boolean allSelectable = true;

      if (selectionChecker != null) {
        for (int i = start; i <= end; i++) {
          RenderableDataItem item = lm.get(i);

          if ((!selectionChecker.isSelectable(i, item)) ||!item.isSelectable()) {
            allSelectable = false;

            if (i != start) {
              if (add) {
                super.addSelectionInterval(start, i - 1);
              } else {
                super.setSelectionInterval(start, i - 1);
              }
            }

            start = i + 1;
          }
        }
      } else {
        allSelectable = false;
      }

      if (allSelectable) {
        if (add) {
          super.addSelectionInterval(start, end);
        } else {
          super.setSelectionInterval(start, end);
        }
      } else {
        if (start <= end) {
          for (int i = start; i <= end; i++) {
            RenderableDataItem item = lm.get(i);

            if (item.isSelectable()) {
              if (add) {
                super.addSelectionInterval(i, i);
              } else {
                super.setSelectionInterval(i, i);
              }
            }
          }
        }
      }
    } finally {
      checked = false;
    }
  }

  public boolean isToggleSelections() {
    return toggleSelections;
  }

  public void setToggleSelections(boolean toggleSelections) {
    this.toggleSelections = toggleSelections;
  }

  public interface iSelectionChecker {
    boolean isSelectable(int row, RenderableDataItem item);
  }
}
