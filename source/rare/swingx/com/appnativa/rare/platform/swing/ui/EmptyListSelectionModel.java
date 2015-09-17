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

import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Don DeCoteau
 */
public class EmptyListSelectionModel implements ListSelectionModel {
  private static EmptyListSelectionModel _instance;

  /**
   * Gets a sharable instance of the list model
   * @return a sharable instance of the list model
   */
  public static EmptyListSelectionModel getSharableInstance() {
    if (_instance == null) {
      _instance = new EmptyListSelectionModel();
    }

    return _instance;
  }

  /** Creates a new instance of NullListSelectionModel */
  public EmptyListSelectionModel() {}

  @Override
  public void addListSelectionListener(ListSelectionListener x) {}

  @Override
  public void addSelectionInterval(int index0, int index1) {}

  @Override
  public void clearSelection() {}

  @Override
  public int getAnchorSelectionIndex() {
    return -1;
  }

  @Override
  public int getLeadSelectionIndex() {
    return -1;
  }

  @Override
  public int getMaxSelectionIndex() {
    return -1;
  }

  @Override
  public int getMinSelectionIndex() {
    return -1;
  }

  @Override
  public int getSelectionMode() {
    return ListSelectionModel.SINGLE_SELECTION;
  }

  @Override
  public boolean getValueIsAdjusting() {
    return false;
  }

  @Override
  public void insertIndexInterval(int index, int length, boolean before) {}

  @Override
  public boolean isSelectedIndex(int index) {
    return false;
  }

  @Override
  public boolean isSelectionEmpty() {
    return true;
  }

  @Override
  public void removeIndexInterval(int index0, int index1) {}

  @Override
  public void removeListSelectionListener(ListSelectionListener x) {}

  @Override
  public void removeSelectionInterval(int index0, int index1) {}

  @Override
  public void setAnchorSelectionIndex(int index) {}

  @Override
  public void setLeadSelectionIndex(int index) {}

  @Override
  public void setSelectionInterval(int index0, int index1) {}

  @Override
  public void setSelectionMode(int selectionMode) {}

  @Override
  public void setValueIsAdjusting(boolean valueIsAdjusting) {}
}
