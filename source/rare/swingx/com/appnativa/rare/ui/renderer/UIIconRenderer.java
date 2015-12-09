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

import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformRenderingComponent;

import javax.swing.JLabel;

/**
 * A label renderer that only displays an icon
 *
 * @author Don DeCoteau
 */
public class UIIconRenderer extends UILabelRenderer {
  public UIIconRenderer() {
    super();
  }

  public UIIconRenderer(JLabel label) {
    super(label);
  }

  @Override
  public iPlatformRenderingComponent newCopy() {
    return setupNewCopy(new UIIconRenderer());
  }

  /**
   * Overridden to do nothing
   *
   * @param text the text to set
   */
  @Override
  public void setText(CharSequence text) {}

  public iPlatformComponent getComponent(Object value, RenderableDataItem item) {
    setText("");

    return this;
  }
}
