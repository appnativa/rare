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

import com.appnativa.rare.ui.Displayed;
import com.appnativa.rare.ui.RenderType;
import com.appnativa.rare.ui.UIImage;
import com.appnativa.rare.ui.iComposite;

/**
 * A class that handles painting images
 *
 * @author Don DeCoteau
 */
public class UIImagePainter extends aUIImagePainter {
  public UIImagePainter() {
    super();
  }

  public UIImagePainter(UIImage image) {
    super(image);
  }

  public UIImagePainter(UIImage image, int opacity, RenderType type) {
    super(image, opacity, type);
  }

  public UIImagePainter(UIImage image, int opacity, RenderType type, Displayed displayed) {
    super(image, opacity, type, displayed);
  }

  @Override
  public boolean canUseLayer() {
    switch(getRenderType()) {
      case HORIZONTAL_TILE :
      case VERTICAL_TILE :
      case STRETCH_HEIGHT :
      case STRETCH_WIDTH :
      case STRETCH_HEIGHT_MIDDLE :
      case STRETCH_WIDTH_MIDDLE :
        return false;

      default :
        return (displayed == Displayed.ALWAYS) && (composite.getCompositeType() == iComposite.CompositeType.SRC_OVER);
    }
  }
}
