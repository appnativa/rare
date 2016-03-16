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

package com.appnativa.rare.ui.painter;

import com.appnativa.rare.ui.ContainerPanel;
import com.appnativa.rare.ui.iComposite;
import com.appnativa.rare.ui.iParentComponent;
import com.appnativa.rare.ui.iPlatformBorder;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformGraphics;

/**
 *
 * @author Don DeCoteau
 */
public class UIComponentPainter extends aUIComponentPainter {
  iComposite paintComposite;

  public UIComponentPainter() {}

  public static boolean isOkToPaint(iPlatformComponent c, iPlatformBorder border) {
    iParentComponent p = (c == null)
                         ? null
                         : c.getParent();

    if ((p instanceof ContainerPanel) && ((ContainerPanel) p).isBorderPanel()) {
      return false;
    }

    return true;
  }

  public void setComposite(iComposite composite) {
    paintComposite = composite;
  }
  
  public iComposite getComposite() {
    return paintComposite;
  }
  
  @Override
  public void paint(iPlatformGraphics g, float x, float y, float width, float height, int orientation, boolean end) {
    if (paintComposite != null) {
      g.setComposite(paintComposite);
    }

    super.paint(g, x, y, width, height, orientation, end);
  }
}
