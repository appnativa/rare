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

import com.appnativa.rare.platform.apple.ui.view.PopupWindow;
import com.appnativa.rare.ui.iPopup;
import com.appnativa.rare.ui.print.iPageSetup;

/**
 * Base class for viewers
 *
 * @version 0.3, 2007-05-14
 * @author Don DeCoteau
 */
public abstract class aPlatformViewer extends aViewer {

  /**
   * Constructs a new instance
   *
   * @param parent
   *          the parent
   */
  public aPlatformViewer(iContainer parent) {
    super(parent);
  }

  @Override
  public iPageSetup createPageSetup() {
    return null;
  }

  @Override
  protected iPopup createPopup(int width, int height) {
    iPopup popup = new PopupWindow();

    popup.setSize(width, height);

    return popup;
  }
   
}
