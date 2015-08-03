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

package com.appnativa.rare.platform.apple.ui;

import com.appnativa.rare.ui.iPaintedButton.ButtonState;
import com.appnativa.rare.ui.iPlatformGraphics;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.painter.PainterHolder;

public class StateListIcon implements iPlatformIcon {
  protected iPlatformIcon icon;
  protected PainterHolder painterHolder;

  public StateListIcon(PainterHolder ph) {
    painterHolder = ph;
    icon          = ph.normalIcon;
  }

  @Override
  public void paint(iPlatformGraphics g, float x, float y, float width, float height) {
    if (icon != null) {
      icon.paint(g, x, y, width, height);
    }
  }

  @Override
  public iPlatformIcon getDisabledVersion() {
    return painterHolder.disabledIcon;
  }

  public iPlatformIcon getIcon(ButtonState state) {
    return painterHolder.getIcon(state);
  }

  @Override
  public int getIconHeight() {
    return (icon == null)
           ? 0
           : icon.getIconHeight();
  }

  @Override
  public int getIconWidth() {
    return (icon == null)
           ? 0
           : icon.getIconWidth();
  }
}
