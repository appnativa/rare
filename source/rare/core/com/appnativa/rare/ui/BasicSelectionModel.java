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

import android.util.SparseArray;

import com.appnativa.rare.ui.event.ChangeEvent;
import com.appnativa.rare.ui.event.EventListenerList;
import com.appnativa.rare.ui.event.iChangeListener;

import java.util.ArrayList;
import java.util.BitSet;

public class BasicSelectionModel {
  protected int                 integralLast = -1;
  protected EventListenerList   listenerList = new EventListenerList();
  protected int                 selColMax    = -1;
  protected int                 selColMin    = -1;
  protected int                 selMax       = -1;
  protected int                 selMin       = -1;
  protected int                 anchorColumnIndex;
  protected int                 anchorIndex;
  protected int                 bitCount;
  protected boolean             blockChangeEvent;
  protected ChangeEvent         changeEvent;
  protected boolean             fireNeedsCalling;
  protected UIRectangle         intervalRect;
  protected boolean             intervalSelection;
  protected UIRectangle         intervalTesRect;
  protected int                 leadColumnIndex;
  protected int                 leadIndex;
  protected SparseArray<BitSet> rows;
  protected BitSet              selections;
  protected ArrayList<BitSet>   setList;

  public BasicSelectionModel(int size) {
    selections  = new BitSet(size);
    changeEvent = new ChangeEvent(this);
    bitCount    = size;
  }

  public void addChangeListener(iChangeListener l) {
    listenerList.remove(iChangeListener.class, l);
    listenerList.add(iChangeListener.class, l);
  }

  public void addSelectionInterval(int index0, int index1) {
    addSelectionInterval(index0, 0, index1, 0);
  }

  public void addSelectionInterval(int row0, int col0, int row1, int col1) {
    if ((row0 == -1) || (row1 == -1)) {
      return;
    }

    fireNeedsCalling = false;
    blockChangeEvent = true;
    handleInterval(true, row0, col0, row1, col1);
    anchorIndex       = row0;
    leadIndex         = row1;
    leadColumnIndex   = col1;
    anchorColumnIndex = col0;
    blockChangeEvent  = false;

    if (fireNeedsCalling) {
      fireChanged();
    }
  }

  public void clearAndSelect(int index) {
    clearAndSelect(index, 0);
  }

  public void clearAndSelect(int[] indices) {
    clearAndSelect(indices, 0);
  }

  public int getFirstSelectedColumn() {
    BitSet bs = selections;

    if (bs.get(0)) {
      return 0;
    }

    return bs.nextSetBit(0);
  }

  public int getLastSelectedColumn() {
    BitSet bs  = selections;
    int    col = bs.get(0)
                 ? 0
                 : -1;
    int    n   = 0;

    while((n = bs.nextSetBit(n)) != -1) {
      col = n;
    }

    return col;
  }

  public void clearAndSelect(int row, int col) {
    boolean fire = false;

    if (rows != null) {
      BitSet b = rows.get(row);

      if ((b != null) && b.get(col)) {
        b.clear();
      }

      fire = !isEmpty();

      if (fire) {
        clearSelection();
      }

      if (b == null) {
        b = selections;
      }

      selections = b;
      setList.add(b);
      rows.put(row, b);
      b.set(col);
    } else {
      if (!selections.get(row)) {
        fire = true;
      }

      selections.clear();
      selections.set(row);
    }

    leadIndex         = row;
    anchorIndex       = row;
    leadColumnIndex   = col;
    anchorColumnIndex = col;

    if (fire) {
      fireChanged();
    }
  }

  public void clearAndSelect(int[] indices, int col) {
    boolean fire = true;
    int     len  = indices.length;

    if (rows != null) {
      clearSelectionEx();

      for (int i = 0; i < len; i++) {
        BitSet bs = resolveSelection(indices[i]);

        bs.set(col);
      }
    } else {
      BitSet bs = selections;

      bs.clear();

      for (int i = 0; i < len; i++) {
        bs.set(indices[i]);
      }
    }

    leadIndex         = indices[0];
    anchorIndex       = indices[1];
    leadColumnIndex   = col;
    anchorColumnIndex = col;

    if (fire) {
      fireChanged();
    }
  }

  public void clearSelection() {
    if (!isEmpty()) {
      clearSelectionEx();
      fireChanged();
    }
  }

  public void clearSelection(int index) {
    clearSelection(index, 0);
  }

  public void clearSelection(int row, int col) {
    if (intervalSelection) {
      handleInterval(false, row, col, row, col);
    } else {
      if (rows != null) {
        BitSet b = rows.get(row);

        if ((b != null) && b.get(col)) {
          b.clear(col);
          fireChanged();
        }
      } else {
        if (selections.get(row)) {
          selections.clear(row);
          fireChanged();
        }
      }
    }
  }

  public void removeChangeListener(iChangeListener l) {
    listenerList.remove(iChangeListener.class, l);
  }

  public void removeSelectionInterval(int index0, int index1) {
    removeSelectionInterval(index0, 0, index1, 0);
  }

  public void removeSelectionInterval(int row0, int col0, int row1, int col1) {
    if ((row0 == -1) || (row1 == -1)) {
      return;
    }

    fireNeedsCalling = false;
    blockChangeEvent = true;
    handleInterval(false, row0, col0, row1, col1);
    anchorIndex       = row0;
    leadIndex         = row1;
    leadColumnIndex   = col1;
    anchorColumnIndex = col0;
    blockChangeEvent  = false;

    if (fireNeedsCalling) {
      fireChanged();
    }
  }

  public void reset(int size) {
    if (size != selections.size()) {
      bitCount = size;

      if (rows != null) {
        rows.clear();
        setList.clear();
      }

      selections = new BitSet(size);
    } else {
      clearSelectionEx();
    }
  }

  public void select(int index) {
    select(index, 0);
  }

  public void select(int row, int col) {
    if (intervalSelection) {
      handleInterval(true, row, col, row, col);
    } else {
      if (rows != null) {
        resolveSelection(row);
      } else {
        col = row;
      }

      if (!selections.get(col)) {
        selections.set(col);
        fireChanged();
      }
    }
  }

  public void toggleSelectionInterval(int index0, int index1) {
    if ((index0 == -1) || (index1 == -1)) {
      return;
    }

    toggleInterval(index0, index1);
    fireChanged();
  }

  public void toggleSelectionInterval(int row0, int col0, int row1, int col1) {
    if ((row0 == -1) || (row1 == -1)) {
      return;
    }

    toggleInterval(row0, col0, row1, col1);
    fireChanged();
  }

  public void setAnchorColumnIndex(int anchorColumnIndex) {
    this.anchorColumnIndex = anchorColumnIndex;
  }

  public void setAnchorIndex(int anchorIndex) {
    this.anchorIndex = anchorIndex;
  }

  public void setColumnSelectionAllowed(boolean allowed) {
    if (allowed) {
      if (rows == null) {
        rows    = new SparseArray<BitSet>();
        setList = new ArrayList<BitSet>();
      }
    } else {
      if (rows != null) {
        rows.clear();
        setList.clear();
        setList = null;
        rows    = null;
      }
    }
  }

  public void setIntervalSelection(boolean intervalSelection) {
    if (this.intervalSelection != intervalSelection) {
      this.intervalSelection = intervalSelection;

      if (intervalSelection) {
        intervalTesRect = new UIRectangle();
        intervalRect    = new UIRectangle();
      }
    }
  }

  public void setLeadColumnIndex(int leadColumnIndex) {
    this.leadColumnIndex = leadColumnIndex;
  }

  public void setLeadIndex(int leadIndex) {
    this.leadIndex = leadIndex;
  }

  public void setSelectionInterval(int index0, int index1) {
    setSelectionInterval(index0, 0, index1, 0);
  }

  public void setSelectionInterval(int row0, int col0, int row1, int col1) {
    if ((row0 == -1) || (row1 == -1)) {
      return;
    }

    fireNeedsCalling = false;
    blockChangeEvent = true;
    clearSelectionEx();
    handleInterval(true, row0, col0, row1, col1);
    anchorIndex       = row0;
    leadIndex         = row1;
    leadColumnIndex   = col1;
    anchorColumnIndex = col0;
  }

  public int getAnchorColumnIndex() {
    return anchorColumnIndex;
  }

  public int getAnchorIndex() {
    return anchorIndex;
  }

  public int getLeadColumnIndex() {
    return leadColumnIndex;
  }

  public int getLeadIndex() {
    return leadIndex;
  }

  public int[] getSelectedIndices() {
    if (rows == null) {
      if (leadIndex == -1) {
        return null;
      }

      BitSet bs     = selections;
      int    len    = bs.cardinality();
      int    ints[] = new int[len];
      int    n      = 0;
      int    b      = 0;

      if (bs.get(0)) {
        ints[n++] = 0;
      }

      while((b = bs.nextSetBit(b)) != -1) {
        ints[n++] = b;
      }

      return ints;
    }

    return null;
  }

  public int getSelectionCount() {
    if (rows != null) {
      int count = 0;
      int len   = setList.size();

      for (int i = 0; i < len; i++) {
        count += setList.get(i).cardinality();
      }

      return count;
    } else {
      return selections.cardinality();
    }
  }

  public boolean isEmpty() {
    if (setList != null) {
      int len = setList.size();

      for (int i = 0; i < len; i++) {
        if (!setList.get(i).isEmpty()) {
          return false;
        }
      }

      return true;
    }

    return selections.isEmpty();
  }

  public boolean isIntervalSelection() {
    return intervalSelection;
  }

  public boolean isSelected(int index) {
    return isSelected(index, 0);
  }

  public boolean isSelected(int row, int col) {
    if (rows != null) {
      BitSet b = rows.get(row);

      return (b != null) && b.get(col);
    } else {
      return selections.get(row);
    }
  }

  protected void clearSelectionEx() {
    if (rows != null) {
      rows.clear();
      setList.clear();
    }

    selMin            = -1;
    selMax            = -1;
    selColMax         = -1;
    selColMin         = -1;
    anchorColumnIndex = -1;
    leadColumnIndex   = -1;
    leadIndex         = -1;
    anchorIndex       = -1;
    selections.clear();
  }

  protected void fireChanged() {
    fireNeedsCalling = true;

    if (!blockChangeEvent) {
      Utils.fireChangeEvent(listenerList, changeEvent);
      fireNeedsCalling = false;
    }
  }

  protected void handleInterval(boolean select, int row0, int col0, int row1, int col1) {
    if (row1 < row0) {
      int n = row0;

      row0 = row1;
      row1 = n;
    }

    if (intervalSelection) {
      UIRectangle ir = intervalRect;
      UIRectangle tr = intervalTesRect;

      tr.set(col0, row0, col1 - col0 + 1, row1 - row0 + 1);

      if (select) {
        if (!tr.intersects(ir)) {
          clearSelection();
          ir.set(col0, row0, col1 - col0 + 1, row1 - row0 + 1);
        } else {
          ir.add(tr);
        }
      } else {
        int max = (int) ir.y + (int) ir.height - 1;

        if ((row0 > ir.y) && (row1 < max)) {
          if (tr.y > max - row1) {
            row0 = row1;
            row1 = max;
            ir.set(col0, row0, col1 - col0 + 1, ir.height - row1 - row0);
          } else {
            row1 = row0;
            row0 = (int) ir.y;
            ir.set(col0, row1 + 1, col1 - col0 + 1, ir.height - row1 - row0);
          }
        }
      }
    }

    handleIntervalEx(select, row0, col0, row1, col1);
  }

  protected void handleRowInterval(boolean select, int row, int col0, int col1) {
    boolean fire = false;

    if (col1 < col0) {
      int n = col0;

      col0 = col1;
      col1 = n;
    }

    BitSet b = resolveSelection(row);

    for (int col = col0; col <= col1; col++) {
      if (select) {
        if (!fire) {
          fire = !b.get(col);
        }

        b.set(col);
      } else {
        if (!fire) {
          fire = b.get(col);
        }

        b.clear(col);
      }
    }

    if (fire) {
      fireNeedsCalling = true;
    }
  }

  protected BitSet resolveSelection(int row) {
    BitSet b = rows.get(row);

    if (b == null) {
      if (setList.isEmpty()) {
        b = selections;
      } else {
        b = new BitSet(bitCount);
      }

      rows.put(row, b);
      setList.add(b);
    }

    selections = b;

    return b;
  }

  protected void toggleInterval(int index0, int index1) {
    if (index1 < index0) {
      int n = index0;

      index0 = index1;
      index1 = n;
    }

    BitSet b = selections;

    for (int row = index0; row <= index1; row++) {
      b.flip(row);
    }
  }

  protected void toggleInterval(int row0, int col0, int row1, int col1) {
    if (rows == null) {
      toggleInterval(row0, row1);

      return;
    }

    if (row1 < row0) {
      int n = row0;

      row0 = row1;
      row1 = n;
    }

    for (int row = row0; row <= row1; row++) {
      toggleRowInterval(row, col0, col1);
    }
  }

  protected void toggleRowInterval(int row, int col0, int col1) {
    if (col1 < col0) {
      int n = col0;

      col0 = col1;
      col1 = n;
    }

    BitSet b = resolveSelection(row);

    for (int col = col0; col <= col1; col++) {
      b.flip(col);
    }
  }

  private void handleInterval(boolean select, int index0, int index1) {
    boolean fire = false;
    BitSet  b    = selections;

    for (int row = index0; row <= index1; row++) {
      if (select) {
        if (!b.get(row)) {
          b.set(row);
          fire = true;
        }
      } else {
        if (!b.get(row)) {
          b.set(row);
          fire = true;
        }
      }
    }

    if (select) {
      selMin = Math.min(selMin, index0);
      selMax = Math.max(selMax, index1);
    } else {
      selMin = Math.max(selMin, index0);
      selMax = Math.min(selMax, index1);
    }

    fireNeedsCalling = fire;
  }

  private void handleIntervalEx(boolean select, int row0, int col0, int row1, int col1) {
    if (rows == null) {
      handleInterval(select, row0, row1);

      return;
    }

    for (int row = row0; row <= row1; row++) {
      handleRowInterval(select, row, col0, col1);
    }
  }
}
