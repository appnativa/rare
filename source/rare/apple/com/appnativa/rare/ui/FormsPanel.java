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

import com.appnativa.rare.platform.apple.ui.view.FormsView;
import com.appnativa.rare.ui.painter.iPlatformPainter;
import com.appnativa.rare.widget.iWidget;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class FormsPanel extends aFormsPanel {
  public FormsPanel() {
    super(new FormsView(new FormLayout()));
  }

  public FormsPanel(FormsView view) {
    super(view);
  }

  public FormsPanel(iWidget context) {
    this();
  }

  public FormsPanel(iWidget context, FormLayout layout) {
    super(new FormsView(layout));
  }

  public FormsPanel(iWidget context, int rows, int cols) {
    this(context, rows, cols, "CENTER:DEFAULT:NONE", "FILL:DEFAULT:NONE");
  }

  public FormsPanel(iWidget context, int rows, int cols, String rspec, String cspec) {
    super();

    RowSpec      r  = RowSpec.decode(rspec);
    ColumnSpec   c  = ColumnSpec.decode(cspec);
    RowSpec[]    ra = new RowSpec[rows];
    ColumnSpec[] ca = new ColumnSpec[cols];

    for (int i = 0; i < cols; i++) {
      ca[i] = c;
    }

    for (int i = 0; i < rows; i++) {
      ra[i] = r;
    }

    setView(new FormsView(new FormLayout(ca, ra)));
  }

  protected FormsPanel(Object view) {
    super(view);
  }

  @Override
  public void setCellPainters(iPlatformPainter[] painters) {
    ((FormsView) view).setCellPainters(painters);
  }

  @Override
  public CellConstraints getCellConstraints(iPlatformComponent component) {
    return ((FormsView) view).getCellConstraints(component);
  }

  @Override
  public FormLayout getFormLayout() {
    return ((FormsView) view).getLayout();
  }

  @Override
  protected void getPreferredSizeEx(UIDimension size, float maxWidth) {
    getFormLayout().invalidatePreferredCache();
    super.getPreferredSizeEx(size, maxWidth);
  }
}
