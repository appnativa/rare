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

import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;

import android.view.View;

import com.appnativa.rare.platform.android.ui.util.AndroidGraphics;
import com.appnativa.rare.platform.android.ui.util.AndroidPaint;
import com.appnativa.rare.platform.android.ui.view.ImageDrawable;
import com.appnativa.rare.ui.Displayed;
import com.appnativa.rare.ui.RenderType;
import com.appnativa.rare.ui.UIImage;

/**
 * A class that handles painting images
 *
 * @author Don DeCoteau
 */
public class UIImagePainter extends aUIImagePainter {
  BitmapShader          bitmapShader;
  AndroidGraphics       graphics;
  AndroidPaint          paint;
  private ImageDrawable drawable;

  public UIImagePainter() {
    super();
  }

  /**
   *  Creates a new instance of ImagePainter
   *
   *  @param image the image
   */
  public UIImagePainter(UIImage image) {
    super(image);
  }

  /**
   * Creates a new instance of ImagePainter
   *
   * @param image the image
   * @param opacity the opacity
   * @param type the render type
   */
  public UIImagePainter(UIImage image, int opacity, RenderType type) {
    super(image, opacity, type);
  }

  /**
   * Creates a new instance of ImagePainter
   *
   * @param image the image
   * @param transparency the transparency
   * @param type the render type
   * @param displayed the display criteria
   */
  public UIImagePainter(UIImage image, int opacity, RenderType type, Displayed displayed) {
    super(image, opacity, type, displayed);
  }

  /**
   * Clears the contents of the painter
   */
  public void clear() {
    super.clear();

    if (drawable != null) {
      drawable.setAlpha(255);
      drawable.setImage(null);
      drawable.setRenderType(renderType);
    }
  }

  public void paint(View c, Canvas g, float x, float y, float width, float height, int orientation) {
    graphics = AndroidGraphics.fromGraphics(g, c, graphics);
    paint(graphics, x, y, width, height, orientation);
    graphics.clear();
  }

  public void setImage(UIImage img) {
    super.setImage(img);

    if (drawable != null) {
      drawable.setImage(img);
    }
  }

  public void setImageOpacity(int alpha) {
    super.setImageAlpha(alpha);

    if (drawable != null) {
      drawable.getPaint().setAlpha(alpha);
    }
  }

  public void setRenderType(RenderType type) {
    if (type != renderType) {
      super.setRenderType(type);

      if (drawable != null) {
        drawable.setRenderType(renderType);
      }

      bitmapShader = null;
    }
  }

  public Drawable getDrawable(View view) {
    if (drawable == null) {
      drawable = new ImageDrawable();
      drawable.setImage(theImage);
      drawable.setAlpha((int) (composite.getAlpha() * 255));
      drawable.setRenderType(renderType);
    }

    return drawable;
  }

  /**
   * Gets the height of the icon
   *
   * @return the height of the icon
   */
  public int getIconHeight() {
    return (theImage == null)
           ? 0
           : theImage.getHeight();
  }

  /**
   * Gets the width of the icon
   *
   * @return  the width of the icon
   */
  public int getIconWidth() {
    return (theImage == null)
           ? 0
           : theImage.getWidth();
  }

  public Shader getShader(float width, float height) {
    return getShader();
  }

  @Override
  public Shader getShader() {
    if ((renderType != RenderType.TILED) || (theImage == null) ||!theImage.isLoaded()
        || (composite.getAlpha() != 255)) {
      return null;
    }

    if (bitmapShader == null) {
      bitmapShader = new BitmapShader(theImage.getBitmap(), TileMode.REPEAT, TileMode.REPEAT);
    }

    return bitmapShader;
  }

  @Override
  public int getColor() {
    return 0;
  }

  @Override
  public boolean isColor() {
    return false;
  }
}
