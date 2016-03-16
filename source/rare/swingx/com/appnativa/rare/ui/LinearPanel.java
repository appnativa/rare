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

import com.appnativa.rare.platform.swing.ui.view.FormsView;
import com.appnativa.rare.ui.painter.iPlatformPainter;
import com.appnativa.rare.widget.iWidget;

import com.appnativa.jgoodies.forms.layout.CellConstraints;
import com.appnativa.jgoodies.forms.layout.FormLayout;

public class LinearPanel extends aLinearPanel {
  public LinearPanel(boolean horizontal) {
    this(null, horizontal, null, null);
  }

  public LinearPanel(iWidget context, boolean horizontal) {
    this(context, horizontal, null, null);
  }

  public LinearPanel(iPlatformComponent context, boolean horizontal) {
    this(null, horizontal, null, null);
  }

  public LinearPanel(iWidget context, boolean horizontal, String rspec, String cspec) {
    super(horizontal);
    setSpecs(horizontal, rspec, cspec);
    setView(new FormsView(new FormLayout(colSpec, rowSpec)));
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
    return ((FormsView) view).getFormLayout();
  }

  @Override
  protected void updateFormLayout() {
    ((FormsView) view).setFormLayout(new FormLayout(colSpec, rowSpec));
  }
}
