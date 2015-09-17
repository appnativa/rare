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

package com.appnativa.rare.platform.swing.ui.util;

import com.appnativa.rare.ui.UIImage;
import com.appnativa.rare.ui.UIImageIcon;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.painter.iImagePainter.ScalingType;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Composite;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorConvertOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

public class ImageUtils {
  private static final GraphicsConfiguration configuration =
    GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();

  /**
   * Creates the reflection for the image. The reflection will be copied to y+height
   * of the specified image and as such the specified image should be at least y+(height*2)
   * in height.
   *
   * @param image the image to create the reflection for.
   * @param y the point at which to get the data for the reflection
   * @param height the height of the reflection
   *
   * @param opacity  the reflection opacity
   * @return the passed in image with the reflection added to the bottom
   */
  public static BufferedImage addReflection(BufferedImage image, int y, int height, float opacity, int gap) {
    BufferedImage bi = createCompatibleImage(image, image.getWidth(), image.getHeight() + height + gap);
    Graphics2D    g2 = bi.createGraphics();

    g2.drawImage(image, 0, 0, null);
    g2.setColor(Color.black);
    g2.fillRect(0, image.getHeight(), image.getWidth(), gap);
    g2.dispose();

    return createReflection(image, y, height, opacity, gap);
  }

  public static BufferedImage blurImage(BufferedImage img, BufferedImage dest) {
    Kernel     kernel = new Kernel(3, 3, new float[] {
      1f / 9f, 1f / 9f, 1f / 9f, 1f / 9f, 1f / 9f, 1f / 9f, 1f / 9f, 1f / 9f, 1f / 9f
    });
    ConvolveOp cop    = new ConvolveOp(kernel);

    return cop.filter(img, dest);
  }

  public static BufferedImage copyImage(Image img) {
    return copyImage(img, null);
  }

  public static BufferedImage copyImage(Image img, Composite composite) {
    BufferedImage bImg = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TRANSLUCENT);
    Graphics2D    g    = bImg.createGraphics();

    if (composite != null) {
      g.setComposite(composite);
    }

    g.drawImage(img, 0, 0, null);
    g.dispose();

    return bImg;
  }

  public static BufferedImage createCompatibleImage(int width, int height) {
    return configuration.createCompatibleImage(width, height, Transparency.TRANSLUCENT);
  }

  public static BufferedImage createCompatibleImage(BufferedImage image, int width, int height) {
    return configuration.createCompatibleImage(width, height, image.getTransparency());
  }

  public static BufferedImage createDisabledImage(BufferedImage img) {
    ColorSpace     cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
    ColorConvertOp op = new ColorConvertOp(cs, null);

    img = op.filter(img, null);

    return setBrightnessFactor(img, .75f, null);
  }

  public static BufferedImage createDisabledImageEx(Image img) {
    return (img instanceof BufferedImage)
           ? createDisabledImage((BufferedImage) img)
           : createDisabledImage(copyImage(img));
  }

  public static UIImage createImage(Component c) {
    int w = (c == null)
            ? 0
            : c.getWidth();
    int h = (c == null)
            ? 0
            : c.getHeight();

    if ((w < 1) || (h < 1)) {
      return null;
    }

    BufferedImage image = new BufferedImage(w, h, BufferedImage.TRANSLUCENT);
    Graphics2D    g2    = image.createGraphics();

    try {
      c.paint(g2);
    } finally {
      g2.dispose();
    }

    return new UIImage(image);
  }

  public static UIImage createImage(iPlatformIcon icon) {
    if (icon instanceof UIImageIcon) {
      return ((UIImageIcon) icon).getImage();
    }

    return (icon == null)
           ? null
           : createImageEx(icon);
  }

  public static UIImage createImageEx(iPlatformIcon icon) {
    return new UIImage(createCompatibleImage(icon.getIconWidth(), icon.getIconHeight()));
  }

  /**
   * Creates the reflection for the image. The reflection will be copied to y+height
   * of the specified image and as such the specified image should be at least y+(height*2)
   * in height.
   *
   * @param image the image to create the reflection for.
   * @param y the point at which to get the data for the reflection
   * @param height the height of the reflection
   *
   * @param opacity  the reflection opacity
   * @return the passed in image with the reflection added to the bottom
   */
  public static BufferedImage createReflection(BufferedImage image, int y, int height, float opacity, int gap) {
    int           width  = image.getWidth();
    BufferedImage buffer = image.getSubimage(0, y + height + gap, width, height);
    Graphics2D    g2     = buffer.createGraphics();
    BufferedImage b      = image.getSubimage(0, y, width, height);

    g2.translate(0, height);
    g2.scale(1.0, -1.0);
    g2.setComposite(AlphaComposite.SrcOver);
    g2.drawImage(b, 0, 0, null);
    g2.scale(1.0, -1.0);
    g2.translate(0, -height);
    g2.setComposite(AlphaComposite.DstIn);

    GradientPaint p;

    p = new GradientPaint(0.0f, 0.0f, new Color(0.0f, 0.0f, 0.0f, opacity), 0.0f, buffer.getHeight(),
                          new Color(0.0f, 0.0f, 0.0f, 0.0f), true);
    g2.setPaint(p);
    g2.fillRect(0, 0, buffer.getWidth(), buffer.getHeight());
    g2.dispose();

    return image;
  }

  public static BufferedImage createThumbnail(BufferedImage image, int requestedThumbSize) {
    float         ratio = (float) image.getWidth() / (float) image.getHeight();
    int           width = image.getWidth();
    BufferedImage thumb = image;

    do {
      width /= 2;

      if (width < requestedThumbSize) {
        width = requestedThumbSize;
      }

      BufferedImage temp = new BufferedImage(width, (int) (width / ratio), BufferedImage.TRANSLUCENT);
      Graphics2D    g2   = temp.createGraphics();

      g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
      g2.drawImage(thumb, 0, 0, temp.getWidth(), temp.getHeight(), null);
      g2.dispose();
      thumb = temp;
    } while(width != requestedThumbSize);

    return thumb;
  }

  public static BufferedImage setBrightnessFactor(BufferedImage img, float multiple, BufferedImage dest) {
    float[]         brightKernel = { multiple };
    BufferedImageOp bright       = new ConvolveOp(new Kernel(1, 1, brightKernel));

    return bright.filter(img, dest);
  }

  public static void setPixel(BufferedImage img, int x, int y, int c) {
    img.setRGB(x, y, c);
  }

  public static void setPixels(BufferedImage img, int x, int y, int width, int height, int[] pixels) {
    int imageType = img.getType();

    if ((imageType == BufferedImage.TYPE_INT_ARGB) || (imageType == BufferedImage.TYPE_INT_RGB)) {
      WritableRaster raster = img.getRaster();

      raster.setPixels(x, y, width, height, pixels);
    } else {
      img.setRGB(x, y, width, height, pixels, 0, width);
    }
  }

  public static int getPixel(BufferedImage img, int x, int y) {
    return img.getRGB(x, y);
  }

  public static int[] getPixels(BufferedImage img, int x, int y, int w, int h, int[] pixels) {
    if ((w == 0) || (h == 0)) {
      return new int[0];
    }

    if (pixels == null) {
      pixels = new int[w * h];
    } else if (pixels.length < w * h) {
      throw new IllegalArgumentException("Pixels array must have a length >= w * h");
    }

    int imageType = img.getType();

    if ((imageType == BufferedImage.TYPE_INT_ARGB) || (imageType == BufferedImage.TYPE_INT_RGB)) {
      Raster raster = img.getRaster();

      return (int[]) raster.getDataElements(x, y, w, h, pixels);
    }

    // Unmanages the image
    return img.getRGB(x, y, w, h, pixels, 0, w);
  }

  public static Object getRenderingHint(ScalingType type) {
    switch(type) {
      case NEAREST_NEIGHBOR :
      case NEAREST_NEIGHBOR_CACHED :
      case PROGRESSIVE_NEAREST_NEIGHBOR :
      case PROGRESSIVE_NEAREST_NEIGHBOR_CACHED :
        return RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR;

      case BICUBIC :
      case BICUBIC_CACHED :
      case PROGRESSIVE_BICUBIC :
      case PROGRESSIVE_BICUBIC_CACHED :
        return RenderingHints.VALUE_INTERPOLATION_BICUBIC;

      default :
        return RenderingHints.VALUE_INTERPOLATION_BILINEAR;
    }
  }

  /**
   * Convenience method that returns a scaled instance of the
   * provided BufferedImage.
   *
   *
   * @param img the original image to be scaled
   * @param targetWidth the desired width of the scaled instance,
   *    in pixels
   * @param targetHeight the desired height of the scaled instance,
   *    in pixels
   * @param hint one of the rendering hints that corresponds to
   *    RenderingHints.KEY_INTERPOLATION (e.g.
   *    RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR,
   *    RenderingHints.VALUE_INTERPOLATION_BILINEAR,
   *    RenderingHints.VALUE_INTERPOLATION_BICUBIC)
   * @param progressiveBilinear if true, this method will use a multi-step
   *    scaling technique that provides higher quality than the usual
   *    one-step technique (only useful in down-scaling cases, where
   *    targetWidth or targetHeight is
   *    smaller than the original dimensions)
   * @return a scaled version of the original BufferedImage
   */
  public static BufferedImage getScaledInstance(BufferedImage img, int targetWidth, int targetHeight, ScalingType st) {
    if (st == null) {
      st = ScalingType.BILINEAR;
    }

    int           type         = (img.getTransparency() == Transparency.OPAQUE)
                                 ? BufferedImage.TYPE_INT_RGB
                                 : BufferedImage.TYPE_INT_ARGB;
    BufferedImage ret          = img;
    BufferedImage scratchImage = null;
    Graphics2D    g2           = null;
    int           w, h;
    int           prevW               = ret.getWidth();
    int           prevH               = ret.getHeight();
    boolean       isTranslucent       = img.getTransparency() != Transparency.OPAQUE;
    Object        hint                = getRenderingHint(st);
    boolean       progressiveBilinear = st.isProgressive();

    if (progressiveBilinear) {
      // Use multi-step technique: start with original size, then
      // scale down in multiple passes with drawImage()
      // until the target size is reached
      w = img.getWidth();
      h = img.getHeight();
    } else {
      // Use one-step technique: scale directly from original
      // size to target size with a single drawImage() call
      w = targetWidth;
      h = targetHeight;
    }

    do {
      if (progressiveBilinear && (w > targetWidth)) {
        w /= 2;
      }

      if (w < targetWidth) {
        w = targetWidth;
      }

      if (progressiveBilinear && (h > targetHeight)) {
        h /= 2;
      }

      if (h < targetHeight) {
        h = targetHeight;
      }

      if ((scratchImage == null) || isTranslucent) {
        // Use a single scratch buffer for all iterations
        // and then copy to the final, correctly-sized image
        // before returning
        scratchImage = new BufferedImage(w, h, type);
        g2           = scratchImage.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, hint);
        g2.drawImage(ret, 0, 0, w, h, 0, 0, prevW, prevH, null);
        prevW = w;
        prevH = h;
        ret   = scratchImage;
        g2.dispose();
        g2 = null;
      }
    } while((w != targetWidth) || (h != targetHeight));

    // If we used a scratch buffer that is larger than our target size,
    // create an image of the right size and copy the results into it
    if ((targetWidth != ret.getWidth()) || (targetHeight != ret.getHeight())) {
      scratchImage = new BufferedImage(targetWidth, targetHeight, type);
      g2           = scratchImage.createGraphics();
      g2.drawImage(ret, 0, 0, null);
      g2.dispose();
      ret = scratchImage;
    }

    return ret;
  }
}
