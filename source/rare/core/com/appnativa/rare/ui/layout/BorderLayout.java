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

package com.appnativa.rare.ui.layout;

import com.appnativa.jgoodies.forms.layout.ColumnSpec;
import com.appnativa.jgoodies.forms.layout.FormLayout;
import com.appnativa.jgoodies.forms.layout.LayoutMap;
import com.appnativa.jgoodies.forms.layout.RowSpec;

public class BorderLayout extends FormLayout {
  public BorderLayout() {}

  public BorderLayout(ColumnSpec[] colSpecs) {
    super(colSpecs);
  }

  public BorderLayout(String encodedColumnSpecs) {
    super(encodedColumnSpecs);
  }

  public BorderLayout(ColumnSpec[] colSpecs, RowSpec[] rowSpecs) {
    super(colSpecs, rowSpecs);
  }

  public BorderLayout(String encodedColumnSpecs, LayoutMap layoutMap) {
    super(encodedColumnSpecs, layoutMap);
  }

  public BorderLayout(String encodedColumnSpecs, String encodedRowSpecs) {
    super(encodedColumnSpecs, encodedRowSpecs);
  }

  public BorderLayout(String encodedColumnSpecs, String encodedRowSpecs, LayoutMap layoutMap) {
    super(encodedColumnSpecs, encodedRowSpecs, layoutMap);
  }
}
