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
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.ui.XPActionComponent;
import com.appnativa.rare.ui.event.ActionEvent;
import com.appnativa.rare.ui.event.iActionListener;

public class DateButton extends XPActionComponent implements iActionListener {
  protected aDateViewManager dateViewManager;

  public DateButton(aDateViewManager dvm) {
    super(PlatformHelper.createDateButtonView());
    dateViewManager = dvm;
    setIcon(Platform.getResourceAsIcon("Rare.icon.calendar"));
    super.addActionListener(this);
  }

  @Override
  public void dispose() {
    super.dispose();
    dateViewManager = null;
  }

  @Override
  public void addActionListener(iActionListener l) {
    dateViewManager.addActionListener(l);
  }

  @Override
  public void doClick() {
    dateViewManager.showDialog(this);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    doClick();
  }

  public iDateViewManager getDateViewManager() {
    return dateViewManager;
  }
}
