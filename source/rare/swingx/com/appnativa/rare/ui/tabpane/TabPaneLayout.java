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

import com.appnativa.rare.platform.swing.ui.view.FrameView;
import com.appnativa.rare.ui.Location;
import com.appnativa.rare.ui.RenderType;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIRectangle;

public class TabPaneLayout extends aTabPaneLayout {
  Boolean oldWider;

  public TabPaneLayout(iContentManager cm) {
    super(cm);
  }

  @Override
  protected void adjustMoreButtonPopupLocation(UIRectangle bounds) {
    if (location == Location.LEFT) {
      UIDimension d = moreButton.getPreferredSize();

      if (bounds.x == 0) {
        bounds.x = d.height;
      }

      if (bounds.y == 0) {
        bounds.y = -(d.width / 2 + d.height);
      }
    }
  }

  @Override
  protected void setContentRenderType(RenderType type) {
    FrameView fv = (FrameView) contentArea.getView();

    fv.setViewRenderType(type);
  }

  @Override
  protected void configureRotation(int degrees) {}
}
