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

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.Size;
import com.jgoodies.forms.layout.Sizes;

public abstract class aFormsPanel extends XPContainer implements iFormsPanel {
  public static RowSpec    emptyRowSpec      = RowSpec.decode("14dlu");
  public static ColumnSpec emptyColumnSpec   = ColumnSpec.decode("14dlu");
  public static RowSpec    defaultRowSpec    = RowSpec.decode("d");
  public static ColumnSpec defaultColumnSpec = ColumnSpec.decode("d");
  protected boolean        tableLayout;

  public aFormsPanel() {
    super();
  }

  public aFormsPanel(Object view) {
    super(view);
  }

  @Override
  public int addColumn(int index, String colspec) {
    FormLayout l = getFormLayout();
    ColumnSpec c = ColumnSpec.decode((colspec == null)
                                     ? "FILL:DEFAULT:NONE"
                                     : colspec);

    if ((index < 0) || (index >= l.getColumnCount())) {
      l.appendColumn(c);

      return l.getColumnCount() - 1;
    } else {
      l.insertColumn(this, index + 1, c);

      return index;
    }
  }

  @Override
  public void addColumnSpacing(int space) {
    Size       s   = (space == 0)
                     ? Sizes.ZERO
                     : Sizes.pixel(space);
    ColumnSpec c   = new ColumnSpec(ColumnSpec.DEFAULT, s, 0);
    FormLayout l   = getFormLayout();
    int        len = l.getColumnCount();

    for (int i = len; i > 1; i--) {
      l.insertColumn(this, i, c);
    }
  }

  @Override
  public void addComponent(iPlatformComponent comp, int col, int row) {
    addComponent(comp, col, row, 1, 1);
  }

  @Override
  public void addComponent(iPlatformComponent comp, int col, int row, int rowspan, int colspan) {
    ensureGrid(col + 1, row + 1, false, false, 0, 0);
    add(comp, createConstraints(col, row, rowspan, colspan));
  }

  @Override
  public int addRow(int index, String rowspec) {
    FormLayout l = getFormLayout();
    RowSpec    r = RowSpec.decode((rowspec == null)
                                  ? "CENTER:DEFAULT:NONE"
                                  : rowspec);

    if ((index < 0) || (index >= l.getRowCount())) {
      l.appendRow(r);

      return l.getRowCount() - 1;
    } else {
      l.insertRow(this, index + 1, r);

      return index;
    }
  }

  @Override
  public void addRowSpacing(int space) {
    Size       s   = (space == 0)
                     ? Sizes.ZERO
                     : Sizes.pixel(space);
    RowSpec    r   = new RowSpec(RowSpec.DEFAULT, s, 0);
    FormLayout l   = getFormLayout();
    int        len = l.getRowCount();

    for (int i = len; i > 1; i--) {
      l.insertRow(this, i, r);
    }
  }

  @Override
  public void addSpacerColumn(int space) {
    Size       s = (space == 0)
                   ? Sizes.ZERO
                   : Sizes.pixel(space);
    ColumnSpec c = new ColumnSpec(ColumnSpec.DEFAULT, s, 0);
    FormLayout l = getFormLayout();

    l.appendColumn(c);
  }

  @Override
  public void addSpacerRow(int space) {
    FormLayout l = getFormLayout();
    Size       s = (space == 0)
                   ? Sizes.ZERO
                   : Sizes.pixel(space);
    RowSpec    r = new RowSpec(RowSpec.DEFAULT, s, 0);

    l.appendRow(r);
  }

  @Override
  public void addSpacing(int rowSpace, int columnSpace) {
    addColumnSpacing(columnSpace);
    addRowSpacing(rowSpace);
  }

  @Override
  public CellConstraints createConstraints(int col, int row, int rowspan, int colspan) {
    row++;
    col++;

    return new CellConstraints(col, row, colspan, rowspan);
  }

  @Override
  public void dispose() {
    if (!isDisposed()) {
      FormLayout l = getFormLayout();

      if (l != null) {
        l.dispose();
      }

      super.dispose();
    }
  }

  @Override
  public void ensureGrid(int cols, int rows, int rspacing, int cspacing) {
    ensureGrid(cols, rows, false, false, rspacing, cspacing);
  }

  @Override
  public void fillEmptySpace() {
    FormLayout            l    = getFormLayout();
    FormLayout.LayoutInfo info = getLayoutInfo();
    int                   len  = l.getRowCount();

    for (int i = 0; i < len; i++) {
      if (tableLayout && (i % 2 == 0)) {
        continue;
      }

      if (isRowEmpty(i, info)) {
        l.setRowSpec(i + 1, emptyRowSpec);
      }
    }

    len = l.getColumnCount();

    for (int i = 0; i < len; i++) {
      if (tableLayout && (i % 2 == 0)) {
        continue;
      }

      if (isColumnEmpty(i, info)) {
        l.setColumnSpec(i + 1, emptyColumnSpec);
      }
    }
  }

  @Override
  public void growColumn(int col) {
    getFormLayout().setColumnSpec(col + 1, ColumnSpec.decode("FILL:DEFAULT:GROW(1.0)"));
  }

  @Override
  public void growRow(int row) {
    getFormLayout().setRowSpec(row + 1, RowSpec.decode("CENTER:DEFAULT:GROW(1.0)"));
  }

  @Override
  public void remove(iPlatformComponent c) {
    super.remove(c);
    if(c!=null) {
      FormLayout l = getFormLayout();
      if(l!=null) {
        l.removeLayoutComponent(c);
      }
    }
  }

  @Override
  public void removeColumn(int col) {
    FormLayout l = getFormLayout();

    l.removeColumn(this, col + 1);
  }

  @Override
  public void removeRow(int row) {
    FormLayout l = getFormLayout();

    l.removeRow(this, row + 1);
  }

  @Override
  public void setColumnGroups(int[][] cg) {
    getFormLayout().setColumnGroups(cg);
  }

  @Override
  public void setColumnSpec(int col, String spec) {
    getFormLayout().setColumnSpec(col + 1, ColumnSpec.decode(spec));
  }

  @Override
  public void setLayout(String cstr, String rstr) {
    getFormLayout().setLayout(cstr, rstr);
  }

  @Override
  public void setRowGroups(int[][] rg) {
    getFormLayout().setRowGroups(rg);
  }

  @Override
  public void setRowSpec(int row, String spec) {
    getFormLayout().setRowSpec(row + 1, RowSpec.decode(spec));
  }

  @Override
  public void setSpacerRow(int row, int space) {
    FormLayout l = getFormLayout();
    RowSpec    r = RowSpec.decode("CENTER:" + space + "PX:NONE");

    l.setRowSpec(row + 1, r);
  }

  @Override
  public void setTableLayout(boolean tableLayout) {
    this.tableLayout = tableLayout;
  }

  @Override
  public CellConstraints getCellConstraints(UIPoint p) {
    if (p == null) {
      return null;
    }

    FormLayout.LayoutInfo info = getLayoutInfo();
    int[]                 cols = info.columnOrigins;
    int[]                 rows = info.rowOrigins;
    int                   w    = info.getWidth();
    int                   h    = info.getHeight();
    int                   cw;
    int                   rh;
    UIRectangle           r = new UIRectangle();

    for (int y = 0; y < rows.length; y++) {
      for (int x = 0; x < cols.length; x++) {
        if (x + 1 == cols.length) {
          cw = w - cols[x] + 1;
        } else {
          cw = cols[x + 1] - cols[x] + 1;
        }

        if (y + 1 == rows.length) {
          rh = h - rows[y] + 1;
        } else {
          rh = rows[y + 1] - rows[y] + 1;
        }

        r.setBounds(cols[x], rows[y], cw, rh);

        if (r.contains(p)) {
          return new CellConstraints(x + 1, y + 1);
        }
      }
    }

    return null;
  }

  @Override
  public int getColumn(iPlatformComponent component) {
    CellConstraints cc = getCellConstraints(component);

    return (cc == null)
           ? -1
           : cc.gridX - 1;
  }

  @Override
  public int getColumn(UIPoint p) {
    CellConstraints cc = getCellConstraints(p);

    return (cc == null)
           ? -1
           : cc.gridX - 1;
  }

  @Override
  public int getColumnCount() {
    return getFormLayout().getColumnCount();
  }

  @Override
  public String getColumns() {
    return getColumns(-1);
  }

  @Override
  public String getColumns(int skip) {
    return getColumns(getFormLayout(), skip);
  }

  public static String getColumns(FormLayout l, int skip) {
    StringBuilder sb  = new StringBuilder(80);
    int           len = l.getColumnCount();

    for (int i = 0; i < len; i++) {
      if (i != skip) {
        sb.append(l.getColumnSpec(i + 1).encodeEx()).append(',');
      }
    }

    sb.setLength(sb.length() - 1);

    return sb.toString();
  }

  @Override
  public iPlatformComponent getComponent() {
    return this;
  }

  @Override
  public Object getComponentConstraints(iPlatformComponent component) {
    return getCellConstraints(component);
  }

  @Override
  public int getFormHeight() {
    return getLayoutInfo().getHeight();
  }

  @Override
  public abstract FormLayout getFormLayout();

  @Override
  public int getFormWidth() {
    return getLayoutInfo().getWidth();
  }

  @Override
  public iPlatformComponent getGridComponentAt(int col, int row) {
    return getFormLayout().getComponentAt(this, col, row);
  }

  @Override
  public int getRow(iPlatformComponent component) {
    CellConstraints cc = getCellConstraints(component);

    return (cc == null)
           ? -1
           : cc.gridY - 1;
  }

  @Override
  public int getRow(UIPoint p) {
    CellConstraints cc = getCellConstraints(p);

    return (cc == null)
           ? -1
           : cc.gridY - 1;
  }

  @Override
  public int getRowCount() {
    return getFormLayout().getRowCount();
  }

  @Override
  public String getRows() {
    return getRows(-1);
  }

  @Override
  public String getRows(int skip) {
    return getRows(getFormLayout(), skip);
  }

  public static String getRows(FormLayout l, int skip) {
    StringBuilder sb  = new StringBuilder(80);
    int           len = l.getRowCount();

    for (int i = 0; i < len; i++) {
      if (i != skip) {
        sb.append(l.getRowSpec(i + 1).encodeEx()).append(',');
      }
    }

    sb.setLength(sb.length() - 1);

    return sb.toString();
  }

  public static boolean isColumnEmpty(int column, FormLayout.LayoutInfo info) {
    return (info.columnOrigins[column + 1] - info.columnOrigins[column]) < 2;
  }

  @Override
  public boolean[] isColumnRowComponentsHidden(int col, int row) {
    return getFormLayout().isColumnRowComponentsHidden(this, col, row);
  }

  public static boolean isRowEmpty(int row, FormLayout.LayoutInfo info) {
    return (info.rowOrigins[row + 1] - info.rowOrigins[row]) < 2;
  }

  @Override
  public boolean isTableLayout() {
    return tableLayout;
  }

  protected void ensureGrid(int cols, int rows, boolean growRow, boolean growColumn, int rspacing, int cspacing) {
    FormLayout l = getFormLayout();

    rows -= l.getRowCount();
    cols -= l.getColumnCount();

    if (rows > 0) {
      RowSpec r;

      if (growRow) {
        r = RowSpec.decode("CENTER:DEFAULT:GROW(1.0)");
      } else {
        r = RowSpec.decode("CENTER:DEFAULT:NONE");
      }

      for (int i = 0; i < rows; i++) {
        if (rspacing > 0) {
          addSpacerRow(rspacing);
        }

        l.appendRow(r);
      }
    }

    if (cols > 0) {
      ColumnSpec c;

      if (growColumn) {
        c = ColumnSpec.decode("FILL:DEFAULT:GROW(1.0)");
      } else {
        c = ColumnSpec.decode("FILL:DEFAULT:NONE");
      }

      for (int i = 0; i < cols; i++) {
        if (cspacing > 0) {
          addSpacerColumn(cspacing);
        }

        l.appendColumn(c);
      }
    }
  }

  protected FormLayout.LayoutInfo getLayoutInfo() {
    return getFormLayout().getLayoutInfo(this);
  }

  @Override
  protected void getMinimumSizeEx(UIDimension size, float maxWidth) {
    getFormLayout().getMinimumSize(this, size,maxWidth);
  }

  @Override
  protected void getPreferredSizeEx(UIDimension size, float maxWidth) {
    getFormLayout().getPreferredSize(this, size, maxWidth);
  }
}
