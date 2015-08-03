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

package com.appnativa.rare.ui.tabpane;

import com.appnativa.rare.platform.apple.ui.view.LabelView;
import com.appnativa.rare.ui.ActionComponent;
import com.appnativa.rare.ui.UIAction;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIScreen;

public class TabLabel extends ActionComponent implements iTabLabel {
  public TabLabel() {
    super(new LabelView());
  }

  public TabLabel(UIAction a) {
    this();
    setAction(a);
  }

  @Override
  public void setIsSelectedTab(boolean selected) {
    setSelected(selected);
    revalidate();
  }

  @Override
  public void setMinHeight(int minTabHeight) {}

  @Override
  public UIDimension getPreferredSize(UIDimension size) {
    size       = super.getPreferredSize(size);
    size.width += UIScreen.platformPixels(4);

    return size;
  }
}
