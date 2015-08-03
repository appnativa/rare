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

package com.appnativa.rare.ui.table;

import com.appnativa.rare.ui.iTableModel;

public class MultiDataItemTableModelEx extends MultiDataItemTableModel {
  public MultiDataItemTableModelEx(iTableModel main) {
    super(main);
  }

  @Override
  public void setSelectable(boolean selectable) {
    super.setSelectable(selectable);

    if (headerModel != null) {
      headerModel.setSelectable(selectable);
    }

    mainModel.setSelectable(selectable);

    if (footerModel != null) {
      footerModel.setSelectable(selectable);
    }
  }

  @Override
  public void setShowHorizontalLines(boolean show) {
    super.setShowHorizontalLines(show);

    if (headerModel != null) {
      ((DataItemTableModel) headerModel).setShowHorizontalLines(show);
    }

    ((DataItemTableModel) mainModel).setShowHorizontalLines(show);

    if (footerModel != null) {
      ((DataItemTableModel) footerModel).setShowHorizontalLines(show);
    }
  }

  public void setShowLastDivider(boolean show) {
    super.setShowLastDivider(show);

    if (headerModel != null) {
      headerModel.setShowLastDivider(show);
    }

    mainModel.setShowLastDivider(show);

    if (footerModel != null) {
      footerModel.setShowLastDivider(show);
    }
  }

  @Override
  public void setShowVerticalLines(boolean show) {
    super.setShowVerticalLines(show);

    if (headerModel != null) {
      ((DataItemTableModel) headerModel).setShowVerticalLines(show);
    }

    ((DataItemTableModel) mainModel).setShowVerticalLines(show);

    if (footerModel != null) {
      ((DataItemTableModel) footerModel).setShowVerticalLines(show);
    }
  }
}
