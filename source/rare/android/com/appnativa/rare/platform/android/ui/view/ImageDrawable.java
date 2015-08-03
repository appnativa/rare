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

package com.appnativa.rare.platform.android.ui.view;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;

import com.appnativa.rare.platform.android.ui.util.ImageHelper;
import com.appnativa.rare.platform.android.ui.util.ImageHelper.DelayedImage;
import com.appnativa.rare.ui.RenderType;
import com.appnativa.rare.ui.UIImage;

/**
 *
 * @author Don DeCoteau
 */
public class ImageDrawable extends ShapeDrawable {
  private RenderType renderType = RenderType.TILED;
  private int        drawX;
  private int        drawY;
  private UIImage    theImage;

  public ImageDrawable() {}

  public ImageDrawable(Bitmap bm) {
    setImage(new UIImage(bm));
  }

  public ImageDrawable(UIImage image) {
    setImage(image);
  }

  public void draw(Canvas g) {
    if (theImage == null) {
      return;
    }

    paint(g, getBounds(), -1);
  }

  public static void drawTiledImage(Canvas g, UIImage image, int width, int height) {
    drawTiledImage(g, image, 0, 0, 0, 0, width, height);
  }

  public static void drawTiledImage(Canvas g, UIImage image, int baseX, int baseY, Rect drawRect) {
    drawTiledImage(g, image, baseX, baseY, drawRect.left, drawRect.top, drawRect.width(), drawRect.height());
  }

  public static void drawTiledImage(Canvas g, UIImage image, int baseX, int baseY, int x, int y, int width,
                                    int height) {
    Bitmap img     = image.getBitmap();
    int    iwidth  = img.getWidth();
    int    iheight = img.getHeight();

    if ((iwidth == -1) || (iheight == -1)) {
      image   = ImageHelper.ensureImageLoaded(image);
      img     = image.getBitmap();
      iwidth  = img.getWidth();
      iheight = img.getHeight();
    }

    if ((iwidth == -1) || (iheight == -1)) {
      return;
    }

    int ix   = 0;
    int iy   = baseY;
    int maxx = x + width;
    int maxy = y + height;

    for (;;) {
      ix = baseX;

      if ((iy + iheight) >= y) {
        for (;;) {
          if ((ix + iwidth) >= x) {
            g.drawBitmap(img, ix, iy, null);
          }

          ix += iwidth;

          if (ix >= maxx) {
            break;
          }
        }
      }

      iy += iheight;

      if (iy >= maxy) {
        break;
      }
    }
  }

  public void imageLoaded(UIImage image) {
    if (image instanceof DelayedImage) {
      theImage = ((DelayedImage) image).getRealImage();
      invalidateSelf();
    }
  }

  public void paint(Canvas g, Rect dst, int orientation) {
    if (theImage == null) {
      return;
    }

    if (theImage.isNinePatch()) {
      final Drawable d = theImage.getDrawable();

      if (d != null) {
        d.setBounds(dst);
        d.draw(g);
      }

      return;
    }

    int x = dst.left;
    int y = dst.top;
    int w = dst.width();
    int h = dst.height();

    g.save();

    try {
      UIImage image = theImage;
      int     px    = x;
      int     py    = y;
      int     iw    = theImage.getWidth();
      int     ih    = theImage.getHeight();

      if ((iw == -1) || (ih == -1)) {
        theImage = ImageHelper.ensureImageLoaded(theImage);
        iw       = theImage.getWidth();
        ih       = theImage.getHeight();
        setIntrinsicHeight((ih == -1)
                           ? 0
                           : ih);
        setIntrinsicWidth((iw == -1)
                          ? 0
                          : iw);
        image = theImage;
      }

      if (getRenderType() == RenderType.TILED) {
        drawTiledImage(g, theImage, x + drawX, y + drawY, px, py, w, h);
      } else {
        boolean drawn = false;

        y = 0;
        x = 0;

        switch(getRenderType()) {
          case HORIZONTAL_TILE :
            drawn = true;
            drawTiledImage(g, theImage, x + drawX, y + drawY, px, py, w, ih);

            break;

          case VERTICAL_TILE :
            drawn = true;
            drawTiledImage(g, theImage, x + drawX, y + drawY, px, py, iw, h);

            break;

          case STRETCHED :
            break;

          case STRETCH_WIDTH_MIDDLE :
          case STRETCH_WIDTH :
            y = (h - ih) / 2;
            h = ih;

            break;

          case STRETCH_HEIGHT_MIDDLE :
          case STRETCH_HEIGHT :
            x = (w - iw) / 2;
            w = iw;

            break;

          case UPPER_LEFT :
            w = iw;
            h = ih;

            break;

          case UPPER_RIGHT :
            x = w - iw;
            w = iw;
            h = ih;

            break;

          case LOWER_LEFT :
            y = h - ih;
            w = iw;
            h = ih;

            break;

          case LOWER_RIGHT :
            x = w - iw;
            y = h - ih;
            w = iw;
            h = ih;

            break;

          case LOWER_MIDDLE :
            x = (w - iw) / 2;
            y = h - ih;
            w = iw;
            h = ih;

            break;

          case UPPER_MIDDLE :
            x = (w - iw) / 2;
            w = iw;
            h = ih;

            break;

          case LEFT_MIDDLE :
            y = (h - ih) / 2;
            w = iw;
            h = ih;

            break;

          case RIGHT_MIDDLE :
            x = w - iw;
            y = (h - ih) / 2;
            w = iw;
            h = ih;

            break;

          case CENTERED :
            x = (w - iw) / 2;
            y = (h - ih) / 2;
            w = iw;
            h = ih;

            break;

          default :
        }

        if (!drawn) {
          x          = Math.max(x, 0);
          y          = Math.max(y, 0);
          dst.left   += x + drawX;
          dst.top    += y + drawY;
          dst.bottom = dst.top + h;
          dst.right  = dst.left + w;

          if (dst.top > dst.bottom) {
            dst.top = dst.bottom;
          }

          if (dst.left > dst.right) {
            dst.left = dst.right;
          }

          g.drawBitmap(image.getBitmap(), null, dst, null);
        }
      }
    } finally {
      g.restore();
    }
  }

  public void setDrawingOffset(int x, int y) {
    drawX = x;
    drawY = y;
  }

  public void setImage(UIImage image) {
    theImage = image;

    int h = (image == null)
            ? 0
            : image.getHeight();
    int w = (image == null)
            ? 0
            : image.getWidth();

    setIntrinsicHeight((h == -1)
                       ? 0
                       : h);
    setIntrinsicWidth((w == -1)
                      ? 0
                      : w);
  }

  /**
   * @param renderType the renderType to set
   */
  public void setRenderType(RenderType renderType) {
    this.renderType = renderType;
  }

  public UIImage getImage() {
    return theImage;
  }

  /**
   * @return the renderType
   */
  public RenderType getRenderType() {
    return renderType;
  }
}
