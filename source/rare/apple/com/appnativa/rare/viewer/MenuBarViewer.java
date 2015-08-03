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

package com.appnativa.rare.viewer;

import com.appnativa.rare.Platform;
import com.appnativa.rare.platform.apple.ui.view.NonViewView;
import com.appnativa.rare.spot.MenuBar;
import com.appnativa.rare.ui.ActionBar;
import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.Menu;
import com.appnativa.rare.ui.iMenuBarComponent;

/**
 * A viewer for menu items on a menu bar.
 *
 * @author Don DeCoteau
 */
public class MenuBarViewer extends aMenuBarViewer {

  /**
   * Constructs a new instance
   */
  public MenuBarViewer() {
    super(null);
  }

  /**
   * Constructs a new instance
   *
   * @param parent the widget's parent
   */
  public MenuBarViewer(iContainer parent) {
    super(parent);
  }

  /**
   * Creates a menu bar from the specified configuration
   *
   * @param context the viewer context for the menu bar
   * @param cfg the menu bar configuration
   *
   * @return a handle to a new menu bar
   */
  public static MenuBarViewer createMenuBar(iContainer context, MenuBar cfg) {
    MenuBarViewer v = new MenuBarViewer();

    v.setParent(context);
    v.create(cfg);

    return v;
  }

  public Menu getMenuBar() {
    return (Menu) menuBar;
  }

  /**
   * Creates the actual menu bar component
   *
   * @param cfg the configuration
   * @return the menu bar component
   */
  @Override
  protected iMenuBarComponent createMenuBarAndComponents(MenuBar cfg) {
    if (Platform.isIOS() || "true".equals(cfg.spot_getAttribute("installAsActionBar"))) {
      ActionBar ab = new ActionBar(this, cfg);

      ab.setNeedsHiearachyInvalidated(true);
      formComponent = dataComponent = ab;

      return ab;
    } else {
      Menu b = new Menu();

      dataComponent = formComponent = new Component(new NonViewView());

      return b;
    }
  }
}
