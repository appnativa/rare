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

import com.appnativa.rare.iConstants;

public class aAbsolutePanel extends XPContainer {
  protected aAbsolutePanel(Object view) {
    super(view);
  }

  @Override
  public void add(iPlatformComponent c, Object constraints, int position) {
    super.add(c, constraints, position);
    c.putClientProperty(iConstants.RARE_CONSTRAINTS_PROPERTY, constraints);
  }

  protected void layout(float width, float height) {
    iPlatformComponent c;
    int                len = getComponentCount();

    for (int i = 0; i < len; i++) {
      c = getComponentAt(i);

      Object o = c.getClientProperty(iConstants.RARE_CONSTRAINTS_PROPERTY);

      if (!(o instanceof UIRectangle)) {
        continue;
      }

      UIRectangle r = (UIRectangle) o;

      c.setBounds(r.x, r.y, r.width, r.height);
    }
  }

  @Override
  protected void getMinimumSizeEx(UIDimension size, float maxWidth) {
    super.getPreferredSizeEx(size, 0);
  }

  @Override
  protected void getPreferredSizeEx(UIDimension size, float maxWidth) {
    iPlatformComponent c;
    int                len    = getComponentCount();
    float              width  = 0;
    float              height = 0;

    for (int i = 0; i < len; i++) {
      c = getComponentAt(i);

      if (!c.isVisible()) {
        continue;
      }

      Object o = c.getClientProperty(iConstants.RARE_CONSTRAINTS_PROPERTY);

      if (!(o instanceof UIRectangle)) {
        continue;
      }

      UIRectangle r = (UIRectangle) o;

      width  = Math.max(r.x + r.width, width);
      height = Math.max(r.y + r.height, height);
    }

    size.width  = width;
    size.height = height;
  }
}
