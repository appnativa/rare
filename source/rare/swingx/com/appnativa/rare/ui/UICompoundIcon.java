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

import java.awt.Component;
import java.awt.Graphics;

/**
 * A simple icon implementation that can combine
 * two icons
 *
 * @author Don DeCoteau
 */
public class UICompoundIcon extends aUICompoundIcon {
  public UICompoundIcon() {
    super();
  }

  public UICompoundIcon(iPlatformIcon[] icons) {
    super(icons);
  }

  public UICompoundIcon(iPlatformIcon firstIcon, iPlatformIcon secondIcon) {
    super(firstIcon, secondIcon);
  }

  @Override
  public void paintIcon(Component c, Graphics g, int x, int y) {
    int       xx;
    int       yy;
    final int len = icons.length;

    for (int i = 0; i < len; i++) {
      iPlatformIcon icon = icons[i];
      UIPoint       p    = (iconLocations != null)
                           ? iconLocations[i]
                           : null;

      if (icon != null) {
        xx = x;
        yy = y;

        if (p != null) {
          xx += p.x;
          yy += p.y;
        }

        icon.paintIcon(c, g, xx, yy);
      }
    }
  }
}
