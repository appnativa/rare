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

import android.content.Context;

import com.appnativa.jgoodies.forms.layout.CellConstraints;
import com.appnativa.jgoodies.forms.layout.ColumnSpec;
import com.appnativa.jgoodies.forms.layout.FormLayout;
import com.appnativa.jgoodies.forms.layout.RowSpec;
import com.appnativa.rare.platform.android.ui.view.FormsView;
import com.appnativa.rare.ui.painter.iPlatformPainter;
import com.appnativa.rare.widget.iWidget;

/**
 *
 * @author Don DeCoteau
 */
public class FormsPanel extends aFormsPanel {
  boolean cacheInvalidatedForLayout;

  public FormsPanel(Context context) {
    super(new FormsView(context, new FormLayout()));
  }

  public FormsPanel(FormsView layout) {
    super(layout);
  }

  public FormsPanel(iWidget context) {
    this(context.getAppContext().getActivity());
  }

  public FormsPanel(iWidget context, FormLayout formLayout) {
    super(new FormsView(context.getAppContext().getActivity(), formLayout));
  }

  public FormsPanel(iWidget context, int rows, int cols) {
    this(context, rows, cols, "CENTER:DEFAULT:NONE", "FILL:DEFAULT:NONE");
  }

  public FormsPanel(iWidget context, int rows, int cols, String rspec, String cspec) {
    super();

    RowSpec r = RowSpec.decode(rspec);
    ColumnSpec c = ColumnSpec.decode(cspec);
    RowSpec[] ra = new RowSpec[rows];
    ColumnSpec[] ca = new ColumnSpec[cols];

    for (int i = 0; i < cols; i++) {
      ca[i] = c;
    }

    for (int i = 0; i < rows; i++) {
      ra[i] = r;
    }

    setView(new FormsView(context.getAppContext().getActivity(), new FormLayout(ca, ra)));
  }

  protected FormsPanel(Object view) {
    super(view);
  }

  public void add(iPlatformComponent c, Object constraints, int position) {
    if (constraints instanceof CellConstraints) {
      constraints = ((FormsView) view).createLayoutParams((CellConstraints) constraints);
    }

    super.add(c, constraints, position);
  }

  public void setCellPainters(iPlatformPainter[] painters) {
    ((FormsView) view).setCellPainters(painters);
  }

  public CellConstraints getCellConstraints(iPlatformComponent component) {
    return ((FormsView) view).getCellConstraints(component.getView());
  }

  public FormLayout getFormLayout() {
    return (view == null) ? null : ((FormsView) view).getLayout();
  }
//  protected void getMinimumSizeEx(UIDimension size) {
//    if (view.isLayoutRequested()) {
//      getFormLayout().invalidateMinimumCache();
//    }
//
//    super.getMinimumSizeEx(size);
//  }

  protected void getPreferredSizeEx(UIDimension size, float maxWidth) {
//    if (view.isLayoutRequested() && (maxWidth < 1)) {
//      getFormLayout().invalidatePreferredCache();
//    }
    if(getWidget()!=null && getWidget().getName().equals("testBox")) {
      getWidget();
    }
    getFormLayout().setSizeMaxWidth(maxWidth);
    super.getPreferredSizeEx(size, maxWidth);
  }
}
