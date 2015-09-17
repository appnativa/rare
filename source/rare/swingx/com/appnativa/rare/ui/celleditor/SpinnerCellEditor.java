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

package com.appnativa.rare.ui.celleditor;

import com.appnativa.rare.ui.iPlatformCellEditingComponent;

import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.table.TableCellEditor;
import javax.swing.tree.TreeCellEditor;

public class SpinnerCellEditor extends AbstractCellEditor
        implements iPlatformCellEditingComponent, Cloneable, TreeCellEditor, TableCellEditor {
  JSpinner spinner;

  public SpinnerCellEditor() {
    spinner = new JSpinner();
  }

  public JSpinner getSpinner() {
    return spinner;
  }

  @Override
  public Object getCellEditorValue() {
    return spinner;
  }

  @Override
  public Component getTreeCellEditorComponent(JTree tree, Object value, boolean isSelected, boolean expanded,
          boolean leaf, int row) {
    return spinner;
  }

  @Override
  public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
    // TODO Auto-generated method stub
    return null;
  }
}
