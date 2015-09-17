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

import com.appnativa.rare.ui.Column;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.aDataItemListModel;
import com.appnativa.rare.ui.event.EventListenerList;
import com.appnativa.rare.ui.event.iDataModelListener;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.util.IntList;

import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

public class DataItemListModel extends aDataItemListModel implements ListModel, iDataModelListener {
  public DataItemListModel() {
    super();
  }

  public DataItemListModel(aDataItemListModel m) {
    super(m);
  }

  public DataItemListModel(iWidget widget, Column column) {
    super(widget, column);
  }

  @Override
  public void addListDataListener(ListDataListener l) {
    if (listenerList == null) {
      listenerList = new EventListenerList();
      addDataModelListener(this);
    }

    listenerList.add(ListDataListener.class, l);
  }

  @Override
  public void contentsChanged(Object source) {
    editModeClearMarks();

    if (listenerList == null) {
      return;
    }

    Object[]      listeners = listenerList.getListenerList();
    ListDataEvent e         = null;

    // Process the listeners last to first, notifying
    // those that are interested in this event
    for (int i = listeners.length - 2; i >= 0; i -= 2) {
      if (listeners[i] == ListDataListener.class) {
        if (e == null) {
          e = new ListDataEvent(source, ListDataEvent.CONTENTS_CHANGED, 0, 0);
        }

        ((ListDataListener) listeners[i + 1]).contentsChanged(e);
      }
    }
  }

  @Override
  public void contentsChanged(Object source, int index0, int index1) {
    contentsChanged(source);
  }

  @Override
  public void intervalAdded(Object source, int index0, int index1) {
    editModeClearMarks();

    if (listenerList == null) {
      return;
    }

    Object[]      listeners = listenerList.getListenerList();
    ListDataEvent e         = null;

    // Process the listeners last to first, notifying
    // those that are interested in this event
    for (int i = listeners.length - 2; i >= 0; i -= 2) {
      if (listeners[i] == ListDataListener.class) {
        if (e == null) {
          e = new ListDataEvent(source, ListDataEvent.INTERVAL_ADDED, index0, index1);
        }

        ((ListDataListener) listeners[i + 1]).intervalAdded(e);
      }
    }
  }

  @Override
  public void intervalRemoved(Object source, int index0, int index1, List<RenderableDataItem> removed) {
    editModeClearMarks();

    if (listenerList == null) {
      return;
    }

    Object[]      listeners = listenerList.getListenerList();
    ListDataEvent e         = null;

    // Process the listeners last to first, notifying
    // those that are interested in this event
    for (int i = listeners.length - 2; i >= 0; i -= 2) {
      if (listeners[i] == ListDataListener.class) {
        if (e == null) {
          e = new ListDataEvent(source, ListDataEvent.INTERVAL_REMOVED, index0, index1);
        }

        ((ListDataListener) listeners[i + 1]).intervalRemoved(e);
      }
    }
  }

  @Override
  public void removeListDataListener(ListDataListener l) {
    if (listenerList != null) {
      listenerList.remove(ListDataListener.class, l);
    }
  }

  @Override
  public void structureChanged(Object source) {
    contentsChanged(source);
  }

  @Override
  public void setEditing(boolean editing) {
    if (editing) {
      if (editingMarks == null) {
        editingMarks = new IntList();
      }
    } else {
      editingMarks = null;
    }
  }

  @Override
  public int getSize() {
    return size();
  }
}
