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

package com.appnativa.rare.ui;

import com.appnativa.rare.iWeakReference;
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.ui.event.iDataModelListener;

import java.util.IdentityHashMap;
import java.util.List;

public class ItemModelMap extends IdentityHashMap<RenderableDataItem, Object> implements iDataModelListener {
  protected iWeakReference modelReference;
  protected boolean        isTable;

  public ItemModelMap(iPlatformListDataModel dm) {
    modelReference = PlatformHelper.createWeakReference(dm);
    dm.addDataModelListener(this);
    isTable = dm instanceof iTableModel;
  }

  @Override
  public void contentsChanged(Object source) {
    clear();
  }

  @Override
  public void contentsChanged(Object source, int index0, int index1) {
    iPlatformListDataModel dm = (iPlatformListDataModel) modelReference.get();

    if (dm == null) {
      clear();

      return;
    }

    for (int i = index0; i <= index1; i++) {
      RenderableDataItem item = dm.get(i);

      if (item != null) {
        if (isTable &&!item.isEmpty()) {
          for (RenderableDataItem col : item) {
            if (col != null) {
              remove(col);
            }
          }
        } else {
          remove(item);
        }
      }
    }
  }

  public void dispose() {
    iPlatformListDataModel dm = (iPlatformListDataModel) modelReference.get();

    if (dm != null) {
      dm.removeDataModelListener(this);
    }

    modelReference.clear();
    clear();
  }

  @Override
  public void intervalAdded(Object source, int index0, int index1) {}

  @Override
  public void intervalRemoved(Object source, int index0, int index1, List<RenderableDataItem> removed) {
    if (removed != null) {
      for (RenderableDataItem item : removed) {
        if (isTable &&!item.isEmpty()) {
          for (RenderableDataItem col : item) {
            if (col != null) {
              remove(col);
            }
          }
        } else {
          remove(item);
        }
      }
    }
  }

  public void remodel(iPlatformListDataModel model) {
    iPlatformListDataModel dm = (iPlatformListDataModel) modelReference.get();

    if (dm != null) {
      dm.removeDataModelListener(this);
    }

    modelReference.clear();
    modelReference = PlatformHelper.createWeakReference(model);
    model.addDataModelListener(this);
  }

  @Override
  public void structureChanged(Object source) {
    clear();
  }

  public boolean isForModel(iPlatformListDataModel dm) {
    return dm == modelReference.get();
  }
}
