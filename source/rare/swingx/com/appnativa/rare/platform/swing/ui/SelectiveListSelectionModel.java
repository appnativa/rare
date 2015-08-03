/*
 * @(#)SelectiveListSelectionModel.java
 *
 * Copyright (c) SparseWare. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.platform.swing.ui;

import javax.swing.DefaultListSelectionModel;

import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.iPlatformListDataModel;

public class SelectiveListSelectionModel extends DefaultListSelectionModel {
  boolean                        checked;
  private iPlatformListDataModel listModel;
  private iSelectionChecker      selectionChecker;

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
      if(index0==-1 || index1==-1) {
        super.clearSelection();
      }
      else if (add) {
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
      int start = index0;
      int end = index1;
      iPlatformListDataModel lm = listModel;
      int len = lm.size();

      if (end >= len) {
        end = len - 1;
      }

      boolean allSelectable = true;
      if (selectionChecker != null) {
        for (int i = start; i <= end; i++) {
          RenderableDataItem item = lm.get(i);

          if ((!selectionChecker.isSelectable(i, item)) || !item.isSelectable()) {
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
      }
      else {
        allSelectable=false;
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

  public interface iSelectionChecker {
    boolean isSelectable(int row, RenderableDataItem item);
  }
}
