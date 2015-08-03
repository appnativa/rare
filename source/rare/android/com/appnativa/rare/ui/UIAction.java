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

import com.appnativa.rare.spot.ActionItem;
import com.appnativa.rare.widget.iWidget;

/**
 * A class representing an executable action
 *
 * @author Don DeCoteau
 */
public class UIAction extends aUIAction {
  public UIAction(aUIAction a) {
    super(a);
    // TODO Auto-generated constructor stub
  }

  public UIAction(iWidget context, ActionItem item) {
    super(context, item);
  }

  public UIAction(String name, CharSequence text, iPlatformIcon icon, String desc) {
    super(name, text, icon, desc);
  }

  public UIAction(String name, CharSequence text, iPlatformIcon icon) {
    super(name, text, icon);
  }

  public UIAction(String name) {
    super(name);
  }
}
