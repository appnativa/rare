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

package com.appnativa.rare.ui.renderer;

import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.iPlatformRenderingComponent;

import javax.swing.JLabel;

/**
 * A label renderer that only displays text
 *
 * @author Don DeCoteau
 */
public class UIStringRenderer extends UILabelRenderer {
  public UIStringRenderer() {
    super();
  }

  public UIStringRenderer(JLabel tv) {
    super(tv);
  }

  @Override
  public iPlatformRenderingComponent newCopy() {
    return setupNewCopy(new UIStringRenderer());
  }

  /**
   * Overridden to do nothing
   *
   * @param icon the icon to set
   */
  @Override
  public void setDisabledIcon(iPlatformIcon icon) {}

  /**
   * Overridden to do nothing
   *
   * @param icon the icon to set
   */
  @Override
  public void setIcon(iPlatformIcon icon) {}
}
