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

import com.appnativa.rare.platform.apple.ui.view.FrameView;
import com.appnativa.rare.ui.RenderType;

public class TabPaneLayout extends aTabPaneLayout {
  public TabPaneLayout(iContentManager cm) {
    super(cm);
  }

  @Override
  protected void configureRotation(int degrees) {
    tabStrip.getView().setRotation(degrees);
  }

  @Override
  protected void setContentRenderType(RenderType type) {
    FrameView fv = (FrameView) contentArea.getView();

    fv.setViewRenderType(type);
  }
}
