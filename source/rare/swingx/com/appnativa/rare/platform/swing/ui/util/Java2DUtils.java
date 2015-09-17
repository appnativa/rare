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

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Composite;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.TexturePaint;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.color.ColorSpace;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorConvertOp;
import java.awt.image.ColorModel;
import java.awt.image.ConvolveOp;
import java.awt.image.FilteredImageSource;
import java.awt.image.IndexColorModel;
import java.awt.image.Kernel;
import java.awt.image.RGBImageFilter;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import javax.imageio.ImageIO;

import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * A class containing some Java2D utility functions
 *
 *
 * @version    0.3, 2007-05-04
 * @author     Don DeCoteau
 * @author Charles A. Loomis, Jr., and University of California, Santa Cruz,
 *
 */
public class Java2DUtils {
  private static final AffineTransform       nullTransform = new AffineTransform();
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

  public static void clearArea(Graphics2D g, Shape shape) {
    Composite       oc = g.getComposite();
    AffineTransform tx = g.getTransform();

    g.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR));
    g.setTransform(nullTransform);
    g.fill(shape);
    g.setComposite(oc);
    g.setTransform(tx);
  }

  public static void clearArea(Graphics2D g, int width, int height) {
    clearArea(g, 0, 0, width, height);
  }

  public static void clearArea(Graphics2D g, int x, int y, int width, int height) {
    Composite       oc = g.getComposite();
    AffineTransform tx = g.getTransform();

    g.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR));
    g.setTransform(nullTransform);
    g.fillRect(x, y, width, height);
    g.setComposite(oc);
    g.setTransform(tx);
  }

  public static BufferedImage convertToGrayscale(BufferedImage img) {
    ColorSpace     cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
    ColorConvertOp op = new ColorConvertOp(cs, null);

    img = op.filter(img, null);

    return toCompatibleImage(img);
  }

  /**
   * Copies the properties of one graphics object to another
   * The following properties are copied:  color, stroke,composite,background,clip,font,paint
   *
   * @param from the graphics to copy from
   * @param to  the graphics to copy from
   */
  public static void copyGraphicsAttributes(Graphics2D from, Graphics2D to) {
    to.setColor(from.getColor());
    to.setComposite(from.getComposite());
    to.setFont(from.getFont());
    to.setBackground(from.getBackground());
    to.setStroke(from.getStroke());
    to.setPaint(from.getPaint());
    to.setClip(from.getClip());
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
    return getGraphicsConfiguration().createCompatibleImage(width, height, image.getTransparency());
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

  public static BufferedImage createGrayscaleCompatibleImage(int width, int height) {
    return configuration.createCompatibleImage(width, height, Transparency.TRANSLUCENT);
  }

  public static BufferedImage createImage(Component comp) {
    Dimension size = comp.getSize();

    if ((size.width < 1) || (size.height < 1)) {
      size.width  = 1;
      size.height = 1;
    }

    BufferedImage image = new BufferedImage(size.width, size.height, BufferedImage.TRANSLUCENT);
    Graphics2D    g2    = image.createGraphics();

    try {
      comp.paint(g2);
    } finally {
      g2.dispose();
    }

    return image;
  }

  public static BufferedImage createImage(Component comp, Icon icon) {
    return createImage(comp, icon, null);
  }

  public static BufferedImage createImage(Component comp, Icon icon, Composite composite) {
    if (icon == null) {
      return null;
    }

    BufferedImage image = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TRANSLUCENT);
    Graphics2D    g2    = image.createGraphics();

    if (composite != null) {
      g2.setComposite(composite);
    }

    try {
      icon.paintIcon(comp, g2, 0, 0);
    } finally {
      g2.dispose();
    }

    return image;
  }

  /**
   * Creates an image, if necessary, otherwise returns the passed in image
   * if it is not null and has the specified dimensions
   *
   * @param img the default image
   * @param width the required width
   * @param height the required height
   *
   * @return a buffered image with the specified dimensions
   */
  public static BufferedImage createImageIfNecessary(BufferedImage img, int width, int height) {
    if ((img == null) || (img.getWidth() != width) || (img.getHeight() != height)) {
      if (img != null) {
        img = new BufferedImage(width, height, img.getType());
      } else {
        img = configuration.createCompatibleImage(width, height, Transparency.TRANSLUCENT);
      }
    }

    return img;
  }

  /**
   * Creates an image, if necessary, otherwise returns the passed in image
   * if it is not null and has the specified dimensions
   *
   * @param img the default image
   * @param width the required width
   * @param height the required height
   * @param imageType the image type
   *
   * @return a buffered image with the specified dimensions
   */
  public static BufferedImage createImageIfNecessary(BufferedImage img, int width, int height, int imageType) {
    if ((img == null) || (img.getWidth() != width) || (img.getHeight() != height)) {
      ColorModel cm = configuration.getColorModel(Transparency.TRANSLUCENT);

      if (cm instanceof IndexColorModel) {
        img = new BufferedImage(width, height, imageType, (IndexColorModel) cm);
      } else {
        img = new BufferedImage(width, height, imageType);
      }
    }

    return img;
  }

  public static BufferedImage createOpaqueCompatibleImage(int width, int height) {
    return configuration.createCompatibleImage(width, height);
  }

  public static TexturePaint createPaint(Image image) {
    BufferedImage bi = (image instanceof BufferedImage)
                       ? (BufferedImage) image
                       : toCompatibleImage(image);
    Rectangle2D   tr = new Rectangle2D.Double(0, 0, bi.getWidth(), bi.getHeight());

    return new TexturePaint(bi, tr);
  }

  /**
   * Creates a punched out image from the given image
   * @param image
   * @param unblurredShadowSize_pixels
   * @return
   *
   * @author Ken Orr (http://explodingpixels.wordpress.com/)
   */
  public static BufferedImage createPunchedImage(Image image, int unblurredShadowSize_pixels) {
    // create an image in which to draw the given image with the inner
    // shadow.
    BufferedImage newImage = new BufferedImage(image.getWidth(null), image.getHeight(null),
                               BufferedImage.TYPE_4BYTE_ABGR);
    // 1) paint a gradient background in the resultant image.
    Graphics2D    graphics = newImage.createGraphics();
    GradientPaint paint    = new GradientPaint(0, 0, Color.BLACK, 0, image.getHeight(null), new Color(0x3e3e3e));

    graphics.setPaint(paint);
    graphics.fillRect(0, 0, newImage.getWidth(), newImage.getHeight());
    // 2) paint the given image into resultant image, only keeping pixels that
    // existed in the given image.
    graphics.setComposite(AlphaComposite.DstIn);
    graphics.drawImage(image, 0, 0, null);

    // 3) create an inner shadow for the given image.
    BufferedImage shadowImage = createInnerShadow(image, unblurredShadowSize_pixels);

    graphics.setComposite(AlphaComposite.SrcAtop);
    graphics.drawImage(shadowImage, 0, 0, null);
    graphics.dispose();

    return newImage;
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

  public static BufferedImage createThumbnail(BufferedImage image, int newWidth, int newHeight) {
    int           width  = image.getWidth();
    int           height = image.getHeight();
    BufferedImage thumb  = image;

    do {
      if (width > newWidth) {
        width /= 2;

        if (width < newWidth) {
          width = newWidth;
        }
      }

      if (height > newHeight) {
        height /= 2;

        if (height < newHeight) {
          height = newHeight;
        }
      }

      BufferedImage temp = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);
      Graphics2D    g2   = temp.createGraphics();

      g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
      g2.drawImage(thumb, 0, 0, temp.getWidth(), temp.getHeight(), null);
      g2.dispose();
      thumb = temp;
    } while((width != newWidth) || (height != newHeight));

    return thumb;
  }

  public static BufferedImage createTranslucentCompatibleImage(int width, int height) {
    return getGraphicsConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
  }

  // The following sections contains some code from:
  // Charles A. Loomis, Jr., and University of California, Santa Cruz,
  // Copyright (c) 2000

  /**
   * Flips the image horizontally
   *
   * @param image  the image
   * @param hints rendering hints
   * @return       the flipped image of the Returned Value
   */
  public static BufferedImage flipHorizontally(BufferedImage image, RenderingHints hints) {
    if (image == null) {
      return null;
    }

    AffineTransform   at = getYFlipTransform(image.getHeight(null));
    AffineTransformOp op = new AffineTransformOp(at, hints);

    return op.filter(image, null);
  }

  /**
   * Flips the image vertically
   *
   * @param image  the image
   * @param hints rendering hints
   * @return       the flipped image of the Returned Value
   */
  public static BufferedImage flipVertically(BufferedImage image, RenderingHints hints) {
    if (image == null) {
      return null;
    }

    AffineTransform   at = getXFlipTransform(image.getHeight(null));
    AffineTransformOp op = new AffineTransformOp(at, hints);

    return op.filter(image, null);
  }

  public static BufferedImage loadCompatibleImage(InputStream stream) throws IOException {
    BufferedImage image = ImageIO.read(stream);

    return toCompatibleImage(image);
  }

  public static BufferedImage loadCompatibleImage(URL resource) throws IOException {
    BufferedImage image = ImageIO.read(resource);

    return toCompatibleImage(image);
  }

  /**
   * Rotates the image 90 degrees to the left
   *
   * @param image  the image of Parameter
   * @param hints rendering hints
   * @return the rotated image
   */
  public static BufferedImage rotateLeft(BufferedImage image, RenderingHints hints) {
    AffineTransform rot270Transform = AffineTransform.getRotateInstance(3 * Math.PI / 2);

    rot270Transform.translate(-image.getWidth(), 0);

    AffineTransformOp op = new AffineTransformOp(rot270Transform, hints);

    return op.filter(image, null);
  }

  /**
   * Rotates the image 90 degrees to the right
   *
   * @param image  the image of Parameter
   * @param hints rendering hints
   * @return the rotated image
   */
  public static BufferedImage rotateRight(BufferedImage image, RenderingHints hints) {
    if (image == null) {
      return null;
    }

    AffineTransform rot90Transform = AffineTransform.getRotateInstance(Math.PI / 2);

    rot90Transform.translate(0, -image.getHeight());

    AffineTransformOp op = new AffineTransformOp(rot90Transform, hints);

    return op.filter(image, null);
  }

  public static BufferedImage sharpen(BufferedImage img, BufferedImage dest) {
    float[]         sharpKernel = {
      0.0f, -1.0f, 0.0f, -1.0f, 5.0f, -1.0f, 0.0f, -1.0f, 0.0f
    };
    BufferedImageOp sharpen     = new ConvolveOp(new Kernel(3, 3, sharpKernel), ConvolveOp.EDGE_NO_OP, null);

    return sharpen.filter(img, dest);
  }

  /**
   * {@inheritDoc}
   *
   * @param image {@inheritDoc}
   * @param imageColor {@inheritDoc}
   * @param newColor {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  public static Image swapColor(Image image, Color imageColor, Color newColor) {
    final int      irgb   = imageColor.getRGB();
    final int      nrgb   = newColor.getRGB();
    RGBImageFilter filter = new RGBImageFilter() {
      @Override
      public int filterRGB(int x, int y, int rgb) {
        if (rgb == irgb) {
          return nrgb;
        }

        return rgb;
      }
    };
    FilteredImageSource filteredSrc = new FilteredImageSource(image.getSource(), filter);

    return Toolkit.getDefaultToolkit().createImage(filteredSrc);
  }

  public static BufferedImage toCompatibleImage(BufferedImage image) {
    if (isHeadless()) {
      return image;
    }

    if (image.getColorModel().equals(getGraphicsConfiguration().getColorModel())) {
      return image;
    }

    BufferedImage compatibleImage = getGraphicsConfiguration().createCompatibleImage(image.getWidth(),
                                      image.getHeight(), image.getTransparency());
    Graphics g = compatibleImage.getGraphics();

    g.drawImage(image, 0, 0, null);
    g.dispose();

    return compatibleImage;
  }

  public static BufferedImage toCompatibleImage(Image image) {
    BufferedImage compatibleImage = configuration.createCompatibleImage(image.getWidth(null), image.getHeight(null),
                                      Transparency.TRANSLUCENT);
    Graphics g = compatibleImage.createGraphics();

    g.drawImage(image, 0, 0, null);
    g.dispose();

    return compatibleImage;
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

  public static BufferedImage getBufferedImage(Component comp, Icon icon) {
    if (icon instanceof ImageIcon) {
      Image img = ((ImageIcon) icon).getImage();

      if (img instanceof BufferedImage) {
        return (BufferedImage) img;
      }
    }

    return createImage(comp, icon);
  }

  /**
   * This returns an affine transform which will rotate the contents
   * of the window by 90 degrees.  (NOTE: that this transform should
   * be pre-concatenated with the existing one!) The returned
   * transform will rotate the contents of the window by 90 degrees
   * while keeping the centerpoint the same.  The x and y-scaling
   * will be adjusted to keep the same area visible.
   *
   * @param width the width
   * @param height the height
   *
   * @return the transform
   */
  public static AffineTransform getCCWRotateTransform(int width, int height) {
    return new AffineTransform(0., -((double) height) / width, ((double) width) / height, 0., 0., height);
  }

  /**
   * This returns an affine transform which will rotate the contents
   * of the window by -90 degrees.  (NOTE: that this transform should
   * be pre-concatenated with the existing one!) The returned
   * transform will rotate the contents of the window by -90 degrees
   * while keeping the centerpoint the same.  The x and y-scaling
   * will be adjusted to keep the same area visible.
   *
   * @param width the width
   * @param height the height
   *
   * @return the transform
   */
  public static AffineTransform getCWRotateTransform(int width, int height) {
    return new AffineTransform(0., ((double) height) / width, -((double) width) / height, 0., width, 0.);
  }

  /**
   * This returns an affine transform which will center the given
   * point in the window.  (NOTE: that this transform should be
   * pre-concatenated with the existing one!)  The returned
   * transform will move the given point to the center and maintain
   * the x and y scales.
   *
   * @param newX the new x position
   * @param newY the new y position
   * @param width the width
   * @param height the height
   *
   * @return the transform
   */
  public static AffineTransform getCenteringTransform(int newX, int newY, int width, int height) {
    return new AffineTransform(1., 0., 0., 1., width / 2. - newX, height / 2. - newY);
  }

  /**
   * This modifies the supplied affine transform so that the
   * rectangle given by realBounds will fit exactly inside the
   * rectangle given by windowBounds.  The origins of the realBounds
   * and the windowBounds coincide; the opposite corner corresponds
   * to (x0+dx,y0-dy) for the real coordinates.
   *
   * @param transform the transform which will be modified
   * @param realBounds the user space rectangle
   * @param windowBounds the window to map the user rectangle to
   */
  public static void getFillingTransform(AffineTransform transform, RectangularShape realBounds,
          RectangularShape windowBounds) {
    if ((realBounds == null) || (windowBounds == null)) {
      transform.setToIdentity();
    } else {
      // Get the dimensions of the windows.
      double realWidth    = realBounds.getWidth();
      double realHeight   = realBounds.getHeight();
      double windowWidth  = windowBounds.getWidth();
      double windowHeight = windowBounds.getHeight();

      if ((realWidth > 0) && (realHeight > 0)) {
        // Get the necessary scaling factor.
        double scaleWidth  = windowWidth / realWidth;
        double scaleHeight = windowHeight / realHeight;

        transform.setTransform(scaleWidth, 0., 0., -scaleHeight, -scaleWidth * realBounds.getX(),
                               scaleHeight * realBounds.getY());
      } else {
        transform.setToIdentity();
      }
    }
  }

  /**
   * This modifies the supplied affine transform so that the
   * rectangle given by realBounds will fit inside of the rectangle
   * given by windowBounds.  The center of the realBounds rectangle
   * will coincide with that of the windowBounds rectangle.
   *
   * NOTE: THIS ONLY CORRECTLY HANDLES THE CASE WHEN THE USER SPACE
   * RECTANGLE IS CENTERED ON THE ORIGIN.
   *
   * @param transform the transform which will be modified
   * @param realBounds the user space rectangle
   * @param windowBounds the window to map the user rectangle to
   */
  public static void getFittingTransform(AffineTransform transform, RectangularShape realBounds,
          RectangularShape windowBounds) {
    if ((realBounds == null) || (windowBounds == null)) {
      transform.setToIdentity();
    } else {
      // Get the dimensions of the windows.
      double realWidth    = realBounds.getWidth();
      double realHeight   = realBounds.getHeight();
      double windowWidth  = windowBounds.getWidth();
      double windowHeight = windowBounds.getHeight();

      if ((realWidth > 0) && (realHeight > 0)) {
        // Get the necessary scaling factor.
        double scaleWidth  = windowWidth / realWidth;
        double scaleHeight = windowHeight / realHeight;
        double scale       = Math.min(scaleWidth, scaleHeight);

        transform.setTransform(scale, 0., 0., -scale, windowWidth / 2., windowHeight / 2.);
      } else {
        transform.setToIdentity();
      }
    }
  }

  /**
   * This returns the "local" bounds of a component.  This does the
   * same calculation as the method of the same name in
   * SwingUtilities, but this doesn't create a new Rectangle, but
   * instead overwrites the one passed in.
   *
   * @param bounds rectangle to modify with the given component's
   * bounds (will create new Rectangle if this is null)
   * @param c component to get the bounds from
   *
   * @return convenience reference to the rectangle passed in (or
   * the created rectangle)
   */
  public static Rectangle getLocalBounds(Rectangle bounds, Container c) {
    // Create a new Rectangle only if necessary.
    if (bounds == null) {
      bounds = new Rectangle();
    }

    // Get the insets of the components.
    Insets insets = c.getInsets();

    // Set the origin to (0,0) and the width and height to those
    // of the given component.
    bounds.setBounds(0, 0, c.getWidth() - (insets.left + insets.right), c.getHeight() - (insets.top + insets.bottom));

    // Return the given rectangle (or the created one if this was
    // necessary).
    return bounds;
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

  /**
   * This returns an affine transform which is appropriate for
   * modifying an existing one for a change in the window size.
   * (NOTE: that this transform should be pre-concatenated with the
   * existing one!) The returned transform will satisfy the
   * following constraints: the centerpoint of the old window will
   * map to the center point of the new window, a uniform scaling
   * will be used in both the x and y-directions, the entire visible
   * region of in the old window will be visible in the new
   * window.
   *
   * @param oldWidth the old width
   * @param oldHeight the old height
   * @param newWidth the new width
   * @param newHeight the new height
   *
   * @return the transform
   */
  public static AffineTransform getResizeTransform(int oldWidth, int oldHeight, int newWidth, int newHeight) {
    // First calculate the scaling which is necessary.
    double scale = Math.min((double) newWidth / (double) oldWidth, (double) newHeight / (double) oldHeight);
    // Now calculate the translation which is necessary.
    double tx = 0.5 * (newWidth - scale * oldWidth);
    double ty = 0.5 * (newHeight - scale * oldHeight);

    // Now create the transform and return it.
    return new AffineTransform(scale, 0., 0., scale, tx, ty);
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
  public static BufferedImage getScaledInstance(BufferedImage img, int targetWidth, int targetHeight, Object hint,
          boolean progressiveBilinear) {
    int           type         = (img.getTransparency() == Transparency.OPAQUE)
                                 ? BufferedImage.TYPE_INT_RGB
                                 : BufferedImage.TYPE_INT_ARGB;
    BufferedImage ret          = img;
    BufferedImage scratchImage = null;
    Graphics2D    g2           = null;
    int           w, h;
    int           prevW         = ret.getWidth();
    int           prevH         = ret.getHeight();
    boolean       isTranslucent = img.getTransparency() != Transparency.OPAQUE;

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
      }

      g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, hint);
      g2.drawImage(ret, 0, 0, w, h, 0, 0, prevW, prevH, null);
      prevW = w;
      prevH = h;
      ret   = scratchImage;
    } while((w != targetWidth) || (h != targetHeight));

    if (g2 != null) {
      g2.dispose();
    }

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

  /**
   * This returns an affine transform which is appropriate for
   * modifying an existing one for a change in the window size.
   * (NOTE: that this transform should be pre-concatenated with the
   * existing one!)  The final transform will map the four corners
   * of the old window to the four corners of the new window.  In
   * general, the scaling in the x and y direction will be
   * different.
   *
   * @param oldWidth the old width
   * @param oldHeight the old height
   * @param newWidth the new width
   * @param newHeight the new height
   *
   * @return the transform
   */
  public static AffineTransform getStretchTransform(int oldWidth, int oldHeight, int newWidth, int newHeight) {
    // First calculate the scaling which is necessary.
    double scaleX = (double) newWidth / (double) oldWidth;
    double scaleY = (double) newHeight / (double) oldHeight;

    // Now create the transform and return it.
    return new AffineTransform(scaleX, 0., 0., scaleY, 0., 0.);
  }

  /**
   * This returns an affine transform which will flip the horizontal
   * axis around.  (NOTE: that this transform should be
   * pre-concatenated with the existing one!)  The returned
   * transform will maintain the centerpoint of the window and flip
   * the direction of the x-axis.
   *
   * @param width the width
   *
   * @return the transform
   */
  public static AffineTransform getXFlipTransform(int width) {
    return new AffineTransform(-1., 0., 0., 1., width, 0.);
  }

  /**
   * This returns an affine transform which will flip the vertical
   * axis around.  (NOTE: that this transform should be
   * pre-concatenated with the existing one!)  The returned
   * transform will maintain the centerpoint of the window and flip
   * the direction of the y-axis.
   *
   * @param height the height
   *
   * @return the transform
   */
  public static AffineTransform getYFlipTransform(int height) {
    return new AffineTransform(1., 0., 0., -1., 0., height);
  }

  /**
   * This returns an affine transform which will keep the center point in
   * the center and scale the x- and y-directions uniformly by the factor
   * given.  Note that a value of 1 corresponds to the identify transform,
   * values less than 1 will "zoom out," and values greater than 1 will
   * "zoom in."  (NOTE: that this transform should be pre-concatenated with
   * the existing one!)
   *
   * @param zoomFactor trhe zoom factor
   * @param width the width
   * @param height the height
   *
   * @return the transform
   */
  public static AffineTransform getZoomTransform(double zoomFactor, int width, int height) {
    double tx = width / 2. * (1. - zoomFactor);
    double ty = height / 2. * (1 - zoomFactor);

    return new AffineTransform(zoomFactor, 0., 0., zoomFactor, tx, ty);
  }

  private static BufferedImage createInnerShadow(Image image, int unblurredShadowSize_pixels) {
    // create an image padded by the shadow size. this allows the given
    // image to abut the edge and still receive an inner shadow. if we don't
    // do this, an image that abuts the edge will have a shadow that is
    // blurred into full alpha transparency, when it should in fact be opaque.
    int           twiceShadowSize = unblurredShadowSize_pixels * 2;
    BufferedImage punchedImage    = new BufferedImage(image.getWidth(null) + twiceShadowSize,
                                      image.getHeight(null) + twiceShadowSize, BufferedImage.TYPE_INT_ARGB);
    // 1) start by filling the entire rectangle with black.
    Graphics2D graphics = punchedImage.createGraphics();

    graphics.setColor(new Color(0, 0, 0, 140));
    graphics.fillRect(0, 0, punchedImage.getWidth(), punchedImage.getHeight());
    // 2) next erase the given image from the previously drawn rectangle. this
    // punches out the image from the rectangle, which will let the "light"
    // flow through when we create the drop shadow. note that we're moving
    // down and to left shadowSize pixels to compensate for the pad, then
    // another shadowSize pixels to offset the image.
    graphics.setComposite(AlphaComposite.DstOut);
    graphics.drawImage(image, twiceShadowSize, twiceShadowSize, null);
    graphics.dispose();

    // create a drop shadow for the punched out image.
    BufferedImage innerShaodowImage = createLinearBlurOp(unblurredShadowSize_pixels).filter(punchedImage, null);

    // return an image of the original size. we're subtracting off the pad
    // that we added in the beginning which was only used to allow images
    // that abut the edge.
    return innerShaodowImage.getSubimage(unblurredShadowSize_pixels, unblurredShadowSize_pixels,
            punchedImage.getWidth() - twiceShadowSize, punchedImage.getHeight() - twiceShadowSize);
  }

  private static ConvolveOp createLinearBlurOp(int size) {
    float[] data  = new float[size * size];
    float   value = 1.0f / (size * size);

    for (int i = 0; i < data.length; i++) {
      data[i] = value;
    }

    return new ConvolveOp(new Kernel(size, size, data));
  }

  private static GraphicsConfiguration getGraphicsConfiguration() {
    return configuration;
  }

  private static boolean isHeadless() {
    return GraphicsEnvironment.isHeadless();
  }
}
