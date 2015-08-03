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

import com.appnativa.rare.Platform;
import com.appnativa.rare.platform.android.ui.view.FormsView;
import com.appnativa.rare.ui.painter.iPlatformPainter;
import com.appnativa.rare.widget.iWidget;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 *
 * @author Don DeCoteau
 */
public class LinearPanel extends aLinearPanel {
  public LinearPanel(boolean horizontal) {
    this(Platform.getAppContext().getActivity(), horizontal, null, null);
  }

  public LinearPanel(Context context, boolean horizontal) {
    this(context, horizontal, null, null);
  }

  public LinearPanel(iWidget context, boolean horizontal) {
    this(context, horizontal, null, null);
  }

  public LinearPanel(Context context, boolean horizontal, String rspec, String cspec) {
    super(horizontal);
    setSpecs(horizontal, rspec, cspec);
    setView(new FormsView(context, new FormLayout(colSpec, rowSpec)));
  }

  public LinearPanel(iWidget context, boolean horizontal, String rspec, String cspec) {
    this(context.getAppContext().getActivity(), horizontal, rspec, cspec);
    this.widget = context;
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
    return (view == null)
           ? null
           : ((FormsView) view).getLayout();
  }

  protected void updateFormLayout() {
    ((FormsView) view).setFormLayout(new FormLayout(colSpec, rowSpec));
  }

  protected void getMinimumSizeEx(UIDimension size) {
    if (view.isLayoutRequested()) {
      getFormLayout().invalidateMinimumCache();
    }

    super.getMinimumSizeEx(size);
  }

  protected void getPreferredSizeEx(UIDimension size, float maxWidth) {
    if (view.isLayoutRequested() && (maxWidth < 1)) {
      getFormLayout().invalidatePreferredCache();
    }

    super.getPreferredSizeEx(size, maxWidth);
  }
}
