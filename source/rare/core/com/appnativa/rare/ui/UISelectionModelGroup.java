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

import com.appnativa.rare.ui.event.ItemChangeEvent;
import com.appnativa.rare.ui.event.iItemChangeListener;
import com.appnativa.util.IdentityArrayList;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Don DeCoteau
 */
public class UISelectionModelGroup {
  protected Map<Object, iListHandler>       _componentMap;
  protected IdentityArrayList<iListHandler> _models;
  protected iItemChangeListener             _selectionListener;

  /**
   * Creates a new <code>SelectionModelGroupEx</code>.
   */
  public UISelectionModelGroup() {
    _models            = new IdentityArrayList<iListHandler>();
    _selectionListener = createSelectionListener();
  }

  public void add(iListHandler comp, Object model, int position) {
    if (!_models.contains(comp)) {
      if ((position < 0) || (position > _models.size())) {
        _models.add(comp);
      } else {
        _models.add(position, comp);
      }

      addSelectionListener(comp, _selectionListener);

      if (_componentMap == null) {
        _componentMap = new HashMap<Object, iListHandler>(2);
      }

      _componentMap.put(model, comp);
    }
  }

  public static int findNextSelectable(iListHandler comp, int start, int end) {
    if ((comp != null) &&!comp.getContainerComponent().isShowing()) {
      return -1;
    }

    int len = (comp == null)
              ? 0
              : comp.size();

    if (end != -1) {
      len = end;
    }

    RenderableDataItem di;

    for (int i = start; i < len; i++) {
      di = comp.get(i);

      if ((di != null) && di.isSelectable()) {
        return i;
      }
    }

    return -1;
  }

  public static int findPreviousSelectable(iListHandler comp, int start, int end) {
    if ((comp != null) &&!comp.getContainerComponent().isShowing()) {
      return -1;
    }

    int len = (comp == null)
              ? 0
              : comp.size();

    if (end != -1) {
      len = end;
    }

    RenderableDataItem di;

    for (int i = len - 1; i >= start; i--) {
      di = comp.get(i);

      if ((di != null) && di.isSelectable()) {
        return i;
      }
    }

    return -1;
  }

  public void remove(Object model) {
    iListHandler comp = _componentMap.remove(model);

    if (comp != null) {
      _models.remove(comp);
      removeSelectionListener(comp, _selectionListener);
    }
  }

  public static void selectNextRow(iListHandler comp, int row) {
    int pos = findNextSelectable(comp, row + 1, -1);

    if (pos != -1) {
      comp.setSelectedIndex(pos);
      comp.scrollRowToVisible(pos);
    }
  }

  public iListHandler selectNextRow(int row, Object lm) {
    if (_componentMap == null) {
      return null;
    }

    iListHandler comp = _componentMap.get(lm);

    if (comp == null) {
      return null;
    }

    int n = _models.indexOf(lm);

    if (n == -1) {
      return null;
    }

    int pos = findNextSelectable(comp, row + 1, -1);

    if (pos != -1) {
      selectRow(comp, pos);

      return comp;
    }

    iListHandler ocomp = comp;
    int          len   = _models.size();

    for (int i = n + 1; i < len; i++) {
      comp = _componentMap.get(_models.get(i));
      pos  = findNextSelectable(comp, 0, -1);

      if (pos != -1) {
        selectRow(comp, pos);

        return comp;
      }
    }

    for (int i = 0; i < n; i++) {
      comp = _componentMap.get(_models.get(i));
      pos  = findNextSelectable(comp, 0, -1);

      if (pos != -1) {
        selectRow(comp, pos);

        return comp;
      }
    }

    pos = findNextSelectable(ocomp, 0, row);

    if (pos != -1) {
      selectRow(ocomp, pos);

      return ocomp;
    }

    return null;
  }

  public static void selectPreviousRow(iListHandler comp, int row) {
    int pos = findPreviousSelectable(comp, 0, row);

    if (pos != -1) {
      comp.setSelectedIndex(pos);
      comp.scrollRowToVisible(pos);
    }
  }

  public iListHandler selectPreviousRow(int row, Object lm) {
    if (_componentMap == null) {
      return null;
    }

    iListHandler comp = _componentMap.get(lm);

    if (comp == null) {
      return null;
    }

    int n = _models.indexOf(lm);

    if (n == -1) {
      return null;
    }

    if (row < 0) {
      row = 0;
    }

    int pos = findPreviousSelectable(comp, 0, row);

    if (pos != -1) {
      selectRow(comp, pos);

      return comp;
    }

    iListHandler ocomp = comp;
    int          len   = _models.size();

    for (int i = n - 1; i >= 0; i--) {
      comp = _componentMap.get(_models.get(i));
      pos  = findPreviousSelectable(comp, 0, -1);

      if (pos != -1) {
        selectRow(comp, pos);

        return comp;
      }
    }

    for (int i = len - 1; i > n; i--) {
      comp = _componentMap.get(_models.get(i));
      pos  = findPreviousSelectable(comp, 0, -1);

      if (pos != -1) {
        selectRow(comp, pos);

        return comp;
      }
    }

    pos = findPreviousSelectable(ocomp, row + 1, -1);

    if (pos != -1) {
      selectRow(ocomp, pos);

      return ocomp;
    }

    return null;
  }

  public boolean hasMap() {
    return _componentMap != null;
  }

  protected void addSelectionListener(iListHandler comp, iItemChangeListener listener) {
    comp.removeSelectionChangeListener(listener);
    comp.addSelectionChangeListener(listener);
  }

  protected iItemChangeListener createSelectionListener() {
    return new iItemChangeListener() {
      @Override
      public void itemChanged(ItemChangeEvent e) {
        if (e.getNewValue() != null) {
          iListHandler m;
          Object       o   = e.getComponent();
          int          len = _models.size();

          for (int i = 0; i < len; i++) {
            m = _models.get(i);

            if (m != o) {
              m.clearSelection();
            }
          }
        }
      }
    };
  }

  protected void removeSelectionListener(iListHandler comp, iItemChangeListener listener) {
    comp.removeSelectionChangeListener(listener);
  }

  private void selectRow(iListHandler comp, int pos) {
    comp.setSelectedIndex(pos);
    comp.scrollRowToVisible(pos);

    if (!comp.getListComponent().isFocusOwner()) {
      comp.getListComponent().requestFocus();
    }
  }
}
