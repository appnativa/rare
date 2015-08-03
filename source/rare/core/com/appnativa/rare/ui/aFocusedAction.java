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

import com.appnativa.rare.Platform;
import com.appnativa.rare.spot.ActionItem;
import com.appnativa.rare.widget.iWidget;

/**
 * Base class for actions that enable/disable based on the currently focused widget
 *
 * @version    0.3, 2007-05-14
 * @author     Don DeCoteau
 */
public abstract class aFocusedAction extends UIAction {

  /**
   * Constructs a new instance
   *
   * @param name the name of the action
   */
  public aFocusedAction(String name) {
    super(name);
  }

  /**
   * Constructs a new instance
   *
   * @param a the action to copy
   */
  public aFocusedAction(UIAction a) {
    super(a);
  }

  /**
   * Constructs a new instance
   *
   *
   * @param context the action's context
   * @param item the item to copy
   */
  public aFocusedAction(iWidget context, ActionItem item) {
    super(context, item);
  }

  /**
   * Constructs a new instance
   *
   * @param name the name of the action
   * @param text the text for the action
   * @param icon the icon for the action
   */
  public aFocusedAction(String name, String text, iPlatformIcon icon) {
    super(name, text, icon);
  }

  public void update() {
    update(Platform.getAppContext().getPermanentFocusOwner());
  }

  /**
   * Updates the action for the specified permanent focus owner
   *
   * @param permanentFocusOwner the permanent focus owner
   */
  public abstract void update(iPlatformComponent permanentFocusOwner);
}
