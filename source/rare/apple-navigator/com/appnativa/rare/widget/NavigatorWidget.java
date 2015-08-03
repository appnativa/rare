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

package com.appnativa.rare.widget;

import com.appnativa.rare.Platform;
import com.appnativa.rare.platform.apple.ui.util.AppleGraphics;
import com.appnativa.rare.platform.apple.ui.view.BorderLayoutView;
import com.appnativa.rare.platform.apple.ui.view.View;
import com.appnativa.rare.spot.Navigator;
import com.appnativa.rare.ui.NavigatorPanel;
import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.aNavigatorPanel;
import com.appnativa.rare.viewer.iContainer;

/**
 *  A widget that provides the navigation of a hierarchy
 *  as a horizontal series of  click-able buttons that identify
 *  the path taken.
 *  <p>
 *  A home and back button can be provided to allow the user to back
 *  up or go directly back to the beginning.
 *  </p>
 *
 *  @author Don DeCoteau
 */
public class NavigatorWidget extends aNavigatorWidget {

  /**
   *  Constructs a new instance
   *
   *  @param parent the widget's parent
   */
  public NavigatorWidget(iContainer parent) {
    super(parent);
  }

  @Override
  protected Object createBorderLayoutView() {
    return new BorderLayoutView() {
      @Override
      public void paintBackground(AppleGraphics g, View v, UIRectangle rect) {}
    };
  }

  @Override
  protected aNavigatorPanel createNavigatorPanel() {
    return new NavigatorPanel(this, getAppContext().getResourceAsIcon("Rare.icon.navigatorBack"));
  }

  protected static void registerForUse() {
    Platform.getAppContext().registerWidgetClass(Platform.getSPOTName(Navigator.class), NavigatorWidget.class);
  }
}
