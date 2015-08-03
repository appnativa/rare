/*
 * @(#)DataItemTableModel.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.ui.table;

import java.util.List;

import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import com.appnativa.rare.ui.Column;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.iTableModel;
import com.appnativa.rare.ui.event.iDataModelListener;
import com.appnativa.util.iFilter;

public class DataItemTableModel extends aDataItemTableModel implements TableModel {
  private boolean uniformHeight = true;

  public DataItemTableModel() {
    super();
  }

  @Override
  public void addListDataListener(ListDataListener l) {
    addDataModelListener(new ListDataListenerWrapper(l));
  }

  @Override
  public void addTableModelListener(TableModelListener l) {
    addDataModelListener(new TableModelListenerWrapper(l));
  }

  @Override
  public iTableModel createEmptyCopy() {
    return new DataItemTableModel();
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
  public void refreshItems() {
    fireTableDataChanged();
  }

  @Override
  public void removeListDataListener(ListDataListener l) {
    removeListener(l);
  }

  @Override
  public void removeTableModelListener(TableModelListener l) {
    removeListener(l);
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

  public void setUniformHeight(boolean uniformHeight) {
    this.uniformHeight = uniformHeight;
  }

  @Override
  public String getColumnName(int columnIndex) {
    return (String) getColumn(columnIndex).getColumnTitle();
  }

  @Override
  public Object getElementAt(int index) {
    return getRow(index);
  }

  @Override
  public iFilter getLastFilter() {
    return null;
  }

  @Override
  public int getSize() {
    return rowCount;
  }

  @Override
  public Object getValueAt(int row, int col) {
    return super.getItemAt(row, col);
  }

  public boolean isUniformHeight() {
    return uniformHeight;
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

  protected void removeListener(Object o) {
    Object[]           listeners = listenerList.getListenerList();
    iDataModelListener found     = null;

    for (int i = listeners.length - 2; i >= 0; i -= 2) {
      if (listeners[i] == iDataModelListener.class) {
        iDataModelListener l = (iDataModelListener) listeners[i + 1];

        if ((l instanceof TableModelListenerWrapper) && ((TableModelListenerWrapper) l).listener == o) {
          found = l;

          break;
        }

        if ((l instanceof ListDataListenerWrapper) && ((ListDataListenerWrapper) l).listener == o) {
          found = l;

          break;
        }
      }
    }

    if (found != null) {
      removeDataModelListener(found);
    }
  }

  static class ListDataListenerWrapper implements iDataModelListener {
    ListDataListener listener;

    public ListDataListenerWrapper(ListDataListener listener) {
      this.listener = listener;
    }

    @Override
    public void contentsChanged(Object source) {
      listener.contentsChanged(new ListDataEvent(source, ListDataEvent.CONTENTS_CHANGED, 0, 0));
    }

    @Override
    public void contentsChanged(Object source, int index0, int index1) {
      listener.contentsChanged(new ListDataEvent(source, ListDataEvent.CONTENTS_CHANGED, index0, index1));
    }

    @Override
    public void intervalAdded(Object source, int index0, int index1) {
      listener.contentsChanged(new ListDataEvent(source, ListDataEvent.INTERVAL_ADDED, index0, index1));
    }

    @Override
    public void intervalRemoved(Object source, int index0, int index1, List<RenderableDataItem> removed) {
      listener.contentsChanged(new ListDataEvent(source, ListDataEvent.INTERVAL_REMOVED, index0, index1));
    }

    @Override
    public void structureChanged(Object source) {
      contentsChanged(source);
    }
  }


  static class TableModelListenerWrapper implements iDataModelListener {
    TableModelListener listener;

    public TableModelListenerWrapper(TableModelListener listener) {
      this.listener = listener;
    }

    @Override
    public void contentsChanged(Object source) {
      TableModelEvent e = new TableModelEvent((TableModel) source);

      listener.tableChanged(e);
    }

    @Override
    public void contentsChanged(Object source, int index0, int index1) {
      TableModelEvent e = new TableModelEvent((TableModel) source, index0, index1);

      listener.tableChanged(e);
    }

    @Override
    public void intervalAdded(Object source, int index0, int index1) {
      TableModelEvent e = new TableModelEvent((TableModel) source, index0, index1, TableModelEvent.ALL_COLUMNS,TableModelEvent.INSERT);

      listener.tableChanged(e);
    }

    @Override
    public void intervalRemoved(Object source, int index0, int index1, List<RenderableDataItem> removed) {
      TableModelEvent e = new TableModelEvent((TableModel) source, index0, index1, TableModelEvent.ALL_COLUMNS,TableModelEvent.DELETE);

      listener.tableChanged(e);
    }

    @Override
    public void structureChanged(Object source) {
      contentsChanged(source);
    }
  }


  @Override
  public void setEditing(boolean editing) {
  }
}
