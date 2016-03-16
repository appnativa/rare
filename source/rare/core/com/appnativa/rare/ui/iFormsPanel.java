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

import com.appnativa.rare.ui.painter.iPlatformPainter;

import com.appnativa.jgoodies.forms.layout.CellConstraints;
import com.appnativa.jgoodies.forms.layout.FormLayout;

public interface iFormsPanel {
  public void add(iPlatformComponent c, Object constraints);

  public int addColumn(int index, String colspec);

  public void addColumnSpacing(int space);

  public void addComponent(iPlatformComponent comp, int row, int col);

  public void addComponent(iPlatformComponent comp, int row, int col, int rowspan, int colspan);

  public int addRow(int index, String rowspec);

  public void addRowSpacing(int space);

  public void addSpacerColumn(int space);

  public void addSpacerRow(int space);

  public void addSpacing(int rowSpace, int columnSpace);

  public CellConstraints createConstraints(int row, int col, int rowspan, int colspan);

  public void ensureGrid(int rows, int cols, int rspacing, int cspacing);

  public void fillEmptySpace();

  public void growColumn(int col);

  public void growRow(int row);

  public void removeAll();

  public void removeColumn(int col);

  public void removeRow(int row);

  public void setCellPainters(iPlatformPainter[] painters);

  public void setColumnGroups(int[][] cg);

  public void setColumnSpec(int col, String spec);

  public void setLayout(String cstr, String rstr);

  public void setRowGroups(int[][] rg);

  public void setRowSpec(int row, String spec);

  public void setSpacerRow(int row, int space);

  public void setTableLayout(boolean tableLayout);

  public CellConstraints getCellConstraints(iPlatformComponent component);

  public CellConstraints getCellConstraints(UIPoint p);

  public int getColumn(iPlatformComponent component);

  public int getColumn(UIPoint p);

  public int getColumnCount();

  public String getColumns();

  public String getColumns(int skip);

  public iPlatformComponent getComponent();

  public int getFormHeight();

  public FormLayout getFormLayout();

  public int getFormWidth();

  public iPlatformComponent getGridComponentAt(int row, int col);

  public iParentComponent getParent();

  public int getRow(iPlatformComponent component);

  public int getRow(UIPoint p);

  public int getRowCount();

  public String getRows();

  public String getRows(int skip);

  public boolean[] isColumnRowComponentsHidden(int row, int col);

  public boolean isTableLayout();
}
