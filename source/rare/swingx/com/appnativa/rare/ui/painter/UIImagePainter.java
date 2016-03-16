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

import java.awt.Paint;
import java.awt.TexturePaint;
import java.awt.geom.Rectangle2D;

/**
 * A class that handles painting images
 *
 * @author Don DeCoteau
 */
public class UIImagePainter extends aUIImagePainter {
  Paint paint;
  int   paintModCount;

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
  public void setRenderType(RenderType type) {
    super.setRenderType(type);
    paint = null;
  }

  public Paint getPaintEx(float width, float height) {
    if (canBecomePaint() && (theImage != null) && theImage.isLoaded()) {
      if ((paint == null) || (paintModCount != modCount)) {
        paintModCount = modCount;

        Rectangle2D anchor = new Rectangle2D.Double(0, 0, theImage.getWidth(), theImage.getHeight());

        paint = new TexturePaint(getImage().getBufferedImage(), anchor);
      }
    }

    return paint;
  }

  protected boolean canBecomePaint() {
    return (renderType == RenderType.TILED) && (composite.getAlpha() == 255) &&!isNinePatch
           && (displayed == Displayed.ALWAYS);
  }

  @Override
  public Paint getPaint() {
    return getPaintEx(50, 50);
  }
}
