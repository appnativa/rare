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

package com.appnativa.rare.ui.calendar;

import com.appnativa.rare.Platform;
import com.appnativa.rare.ui.BorderPanel;
import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.widget.iWidget;

public class DatePanel extends BorderPanel {
  protected aDateViewManager dateViewManager;
  protected boolean          viewsSet;

  public DatePanel(iWidget context, aDateViewManager dvm) {
    super(context);

    UIColor c = Platform.getUIDefaults().getColor("Rare.DateChooser.background");

    if (c == null) {
      c = ColorUtils.getBackground();
    }

    setBackground(c);
    dateViewManager = dvm;
    this.widget     = context;
  }

  @Override
  public void dispose() {
    super.dispose();
    dateViewManager = null;
  }

  public void setContent() {
    setCenterView(dateViewManager.getDatePickerComponent());
    setBottomView(dateViewManager.getButtonPanel(getWidget()));
  }

  public iDateViewManager getDateViewManager() {
    return dateViewManager;
  }
}
