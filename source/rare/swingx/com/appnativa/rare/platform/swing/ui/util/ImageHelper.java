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

import com.appnativa.rare.Platform;
import com.appnativa.rare.iCancelableFuture;
import com.appnativa.rare.iPlatformAppContext;
import com.appnativa.rare.net.ActionLink;
import com.appnativa.rare.net.iURLConnection;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.UIBorderIcon;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIImage;
import com.appnativa.rare.ui.UIImageIcon;
import com.appnativa.rare.ui.effects.iImageFilter;
import com.appnativa.rare.ui.painter.iImagePainter.ScalingType;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.util.ObjectCache;
import com.appnativa.util.iCancelable;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorConvertOp;
import java.awt.image.ConvolveOp;
import java.awt.image.ImageObserver;
import java.awt.image.Kernel;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.UIManager;

/**
 * A class containing a set of methods for dealing with images
 *
 * @author Don DeCoteau
 */
public class ImageHelper {
  private final static Map<String, Integer> systemCursorMap = new HashMap<String, Integer>();
  private static final RenderingHints       biliniar_hint   = new RenderingHints(RenderingHints.KEY_INTERPOLATION,
                                                                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
  private static ImageIcon       IMAGE_LOADER;
  private static BufferedImageOp _disabledImageBufferedImageOp;
  private static ColorConvertOp  _disabledImageColorConvertOp;
  private static iImageFilter    disabledImageFilter;
  private static ObjectCache     imageCache;
  public static String           defaultIconDensity = "ldpi";

  static {
    systemCursorMap.put("default", Cursor.DEFAULT_CURSOR);
    systemCursorMap.put("crosshair", Cursor.CROSSHAIR_CURSOR);
    systemCursorMap.put("hand", Cursor.HAND_CURSOR);
    systemCursorMap.put("n_resize", Cursor.N_RESIZE_CURSOR);
    systemCursorMap.put("s_resize", Cursor.S_RESIZE_CURSOR);
    systemCursorMap.put("e_resize", Cursor.E_RESIZE_CURSOR);
    systemCursorMap.put("w_resize", Cursor.W_RESIZE_CURSOR);
    systemCursorMap.put("ne_resize", Cursor.NE_RESIZE_CURSOR);
    systemCursorMap.put("nw_resize", Cursor.NW_RESIZE_CURSOR);
    systemCursorMap.put("se_resize", Cursor.SE_RESIZE_CURSOR);
    systemCursorMap.put("sw_resize", Cursor.SW_RESIZE_CURSOR);
    systemCursorMap.put("wait", Cursor.WAIT_CURSOR);
    systemCursorMap.put("text", Cursor.TEXT_CURSOR);
    systemCursorMap.put("move", Cursor.MOVE_CURSOR);

    ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);

    _disabledImageColorConvertOp = new ColorConvertOp(cs, null);

    float[] brightKernel = { 0.75f };

    _disabledImageBufferedImageOp = new ConvolveOp(new Kernel(1, 1, brightKernel));
  }

  /**
   * Creates a new instance of ImageHelper
   */
  private ImageHelper() {}

  public static void cachedImage(ObjectCache cache, URL url, UIImage image) {
    String s = null;

    if (url.getProtocol().equals("file")) {
      s = url.getFile();
    }

    CachedImage ci = new CachedImage(s, image);

    cache.put(url, ci);
  }

  /**
   * Constrains an image
   *
   * @param img the image
   * @param width the width constraint
   * @param height the width constraint
   * @param constraints the constraints
   *        0=do nothing
   *        1=constrain width to the specified size
   *        2=constrain height  to the specified size
   *        3=constrain width and height to fit within the specified size (will maintain proportions)
   *        4=constrain width and height to the specified size
   *        5=constrain width and height to fill the specified size (will maintain proportions)
   * @param bg the background color to use to fill empty space (can be null)
   * @param scalingType the type of scaling to do (null for bilinear)
   * @return the constrained image
   */
  public static Image constrain(Image img, int width, int height, int constraints, Color bg, ScalingType scalingType) {
    if (scalingType == null) {
      scalingType = ScalingType.BILINEAR;
    }

    int iw;
    int ih;

    switch(constraints) {
      case 1 :
        if (img.getWidth(null) > width) {
          height = (int) Math.max(1, height * ((float) width) / (img.getWidth(null)));
          img    = Java2DUtils.getScaledInstance((BufferedImage) img, width, height, scalingType.getValue(),
                  scalingType.isProgressive());
        }

        break;

      case 2 :
        if (img.getHeight(null) > height) {
          width = (int) Math.max(1, width * ((float) (height) / (float) img.getHeight(null)));
          img   = Java2DUtils.getScaledInstance((BufferedImage) img, width, height, scalingType.getValue(),
                  scalingType.isProgressive());
        }

        break;

      case 3 :
        iw = img.getWidth(null);
        ih = img.getHeight(null);

        if ((iw > 0) && (ih > 0)) {
          if ((iw > width) || (ih > height)) {
            if (iw > width) {
              ih = (int) (((float) width / (float) iw) * ih);
              iw = width;
            }

            if (ih > height) {
              iw = (int) (((float) height / (float) ih) * iw);
              ih = height;
            }

            if ((iw > 0) && (ih > 0)) {
              img = Java2DUtils.getScaledInstance((BufferedImage) img, iw, ih, scalingType.getValue(),
                      scalingType.isProgressive());

              if (bg != null) {
                BufferedImage bimg = Java2DUtils.createCompatibleImage(width, height);
                Graphics2D    g    = bimg.createGraphics();

                g.setColor(bg);
                g.fillRect(0, 0, width, height);
                g.drawImage(img, (width - iw) / 2, (height - ih) / 2, null);
                g.dispose();
                img = bimg;
              }
            }
          }
        }

        break;

      case 4 :
        iw = img.getWidth(null);
        ih = img.getHeight(null);

        if ((iw > 0) && (ih > 0) && ((iw != width) || (ih != height))) {
          img = Java2DUtils.getScaledInstance((BufferedImage) img, width, height, scalingType.getValue(),
                  scalingType.isProgressive());
        }

        break;

      case 5 :
        iw = img.getWidth(null);
        ih = img.getHeight(null);

        if ((iw > 0) && (ih > 0) && ((iw != width) || (ih != height))) {
          if (iw > ih) {
            ih = (int) (((float) width / (float) iw) * ih);
            iw = width;
          } else if (ih > iw) {
            iw = (int) (((float) height / (float) ih) * iw);
            ih = height;
          } else {
            iw = width;
            ih = height;
          }

          img = Java2DUtils.getScaledInstance((BufferedImage) img, iw, ih, scalingType.getValue(),
                  scalingType.isProgressive());

          if (bg != null) {
            BufferedImage bimg = Java2DUtils.createCompatibleImage(width, height);
            Graphics2D    g    = bimg.createGraphics();

            g.setColor(bg);
            g.fillRect(0, 0, width, height);
            g.drawImage(img, (width - iw) / 2, (height - ih) / 2, null);
            g.dispose();
            img = bimg;
          }
        }

        break;

      default :
        break;
    }

    return img;
  }

  /**
   * Creates a cursor from an ImageIcon
   *
   * @param icon the icon for the cursor
   * @param hotspot the cursor's hotspot
   *
   * @return the newly created cursor
   */
  public static Cursor createCursor(ImageIcon icon, Point hotspot) {
    if (icon == null) {
      return null;
    }

    return createCursor(icon.getImage(), hotspot, icon.getDescription());
  }

  /**
   * Creates a cursor from an image
   *
   * @param image the image for the cursor
   * @param hotspot the cursor's hotspot
   * @param name the name of the cursor
   *
   * @return the newly created cursor
   */
  public static Cursor createCursor(Image image, Point hotspot, String name) {
    int       w    = image.getWidth(null);
    int       h    = image.getHeight(null);
    Dimension size = Toolkit.getDefaultToolkit().getBestCursorSize(w, h);

    if ((size.width != w) || (size.height != h)) {
      BufferedImage b = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
      Graphics2D    g = b.createGraphics();

      g.drawImage(image, 0, 0, null);
      image = b;
      g.dispose();
    }

    if (hotspot == null) {
      hotspot = new Point(0, 0);
    }

    return Toolkit.getDefaultToolkit().createCustomCursor(image, hotspot, name);
  }

  /**
   * Creates a disabled icon
   *
   * @param comp the component the icon is for
   * @param icon the icon to create the disabled version of
   *
   * @return the disabled icon
   */
  public static UIImageIcon createDisabledIcon(Component comp, Icon icon) {
    if (icon == null) {
      return null;
    }

    Image image;

    if (icon instanceof UIImageIcon) {
      image = ((UIImageIcon) icon).getImage().getImage();
    } else {
      image = Java2DUtils.createImage(comp, icon);
    }

    return new UIImageIcon(createDisabledImage(image));
  }

  /**
   * Creates a disabled image
   *
   * @param image the source image
   *
   * @return the disabled image
   */
  public static UIImage createDisabledImage(Image image) {
    BufferedImage bi;

    if (image instanceof BufferedImage) {
      bi = (BufferedImage) image;
    } else {
      bi = Java2DUtils.toCompatibleImage(image);
    }

    if (disabledImageFilter != null) {
      return disabledImageFilter.filter(new UIImage(bi));
    }

    // return Java2DUtils.createDisabledImage(bi);
    BufferedImage img = _disabledImageColorConvertOp.filter(bi, null);

    return new UIImage(_disabledImageBufferedImageOp.filter(img, null));
  }

  /**
   * Creates an icon
   *
   * @param url the url to use to create the icon
   * @param defer true the defer the creation of the list image data; false to create immediately
   *
   * @return the newly created icon
   */
  public static UIImageIcon createIcon(URL url, boolean defer, float density) {
    return (url == null)
           ? null
           : createIcon(url, url.toExternalForm(), null, defer, density);
  }

  /**
   * Creates an icon
   *
   * @param url the url to use to create the icon
   * @param description the icon's description
   * @param name the name of the icon
   * @param defer true the defer the creation of the list image data; false to create immediately
   *
   * @return the newly created icon
   */
  public static UIImageIcon createIcon(URL url, String description, String name, boolean defer, float density) {
    if (url == null) {
      return null;
    }

    try {
      if (description == null) {
        description = url.toExternalForm();
      }

      return new UIImageIcon(createImage(url, defer, density), description, name);
    } catch(IOException ex) {
      Platform.ignoreException(url.toExternalForm(), ex);

      return null;
    }
  }

  /**
   * Creates an image
   *
   * @param app the application context
   * @param url the url to use to create the image
   * @param defer true the defer the creation of the image data; false to create immediately
   *
   * @return the newly created image
   * @throws IOException throws an I/O exception
   */
  public static UIImage createImage(URL url, boolean defer, float density) throws IOException {
    if (Platform.isInSandbox() || url.getFile().endsWith(".gif")) {
      return new UIImage(ensureImageLoaded(Toolkit.getDefaultToolkit().createImage(url)));
    }

    return createImage(url, defer, 0, 0, null, null, density);
  }

  /**
   * Creates an image
   *
   * @param context
   *          the widget context
   * @param long the action link
   * @param defer
   *          true the defer the creation of the image data; false to create
   *          immediately
   * @param densityScale
   *          true to scale the image for the screen density; false to leave the
   *          image as is
   *
   * @return the newly created image
   * @throws IOException
   *           throws an I/O exception
   */
  public static UIImage createImage(iWidget context, ActionLink link, boolean defer, float density) throws IOException {
    return createImage(link.getURL(context), defer, density);
  }

  /**
   * Creates an image
   *
   * @param app the application context
   * @param url the url to use to create the image
   * @param defer true the defer the creation of the image data; false to create immediately
   * @param preserve true to preserve the original bits that created the image; false otherwise
   * @param size a size constraint
   * @param constraints the type of constraint
   * @param st the type of scaling to do (null for bilinear)
   * @param bg the background color to use to fill empty space (can be null)
   *
   * @return the newly created image
   * @throws IOException throws an I/O exception
   */
  public static UIImage createImage(URL url, boolean defer, int size, int constraints, ScalingType st, UIColor bg,
                                    float density)
          throws IOException {
    if (url == null) {
      return null;
    }
    String protocol=url.getProtocol();
    if (protocol.equals("file") || protocol.equals("data")) {
      defer = false;
    }

    if (imageCache != null) {
      UIImage image = getCachedImage(url, imageCache);

      if (image != null) {
        return image;
      }
    }

    if (Platform.isInSandbox()) {
      return new UIImage(ensureImageLoaded(Toolkit.getDefaultToolkit().createImage(url)));
    }

    if (Platform.isEmbedded() && Platform.isSwing()) {
      defer = false;
    }

    if (defer) {
      return new DelayedImage(url, size, constraints, st, bg, density);
    }

    iURLConnection conn = Platform.getAppContext().openConnection(url);

    try {
      InputStream stream = conn.getInputStream();
      Integer     to     = (Integer) UIManager.get("Rare.net.imageLoadTimeout");

      if (to != null) {
        conn.setReadTimeout(to);
      }

      BufferedImage img = ImageIO.read(stream);
      String        s   = url.getFile();
      if(img==null) {
        return UIImageIcon.getBrokenImage();
      }
      if (!s.endsWith(".9.png")) {
        if (s.contains("drawable-mdpi") && "ldpi".equals(defaultIconDensity)) {
          int nw = (int) Math.ceil(img.getWidth() * 2f / 3f);
          int nh = (int) Math.ceil(img.getHeight() * 2f / 3f);

          img = Java2DUtils.getScaledInstance(img, nw, nh, RenderingHints.VALUE_INTERPOLATION_BILINEAR, false);
        } else if (s.contains("drawable-xhdpi") && "ldpi".equals(defaultIconDensity)) {
          int nw = (int) Math.ceil(img.getWidth() * 2f / 6f);
          int nh = (int) Math.ceil(img.getHeight() * 2f / 6f);

          img = Java2DUtils.getScaledInstance(img, nw, nh, RenderingHints.VALUE_INTERPOLATION_BILINEAR, false);
        } else if (s.contains("/drawable/") && "mdpi".equals(defaultIconDensity)) {
          int nw = (int) Math.ceil(img.getWidth() * 3f / 2f);
          int nh = (int) Math.ceil(img.getHeight() * 3f / 2f);

          img = Java2DUtils.getScaledInstance(img, nw, nh, RenderingHints.VALUE_INTERPOLATION_BILINEAR, false);
        }
      }

      if ((constraints > 0) && (size > 0)) {
        img = (BufferedImage) constrain(img, size, size, constraints, bg, st);
      }

      UIImage image = new UIImage(img);

      if (imageCache != null) {
        cachedImage(imageCache, url, image);
      }

      return image;
    } finally {
      conn.dispose();
    }
  }

  /**
   * Creates a pressed icon (a sharper crisper version)
   *
   * @param comp the component the icon is for
   * @param icon the icon to create the pressed version of
   *
   * @return the pressed icon
   */
  public static UIImageIcon createPressedIcon(Component comp, Icon icon) {
    if (icon == null) {
      return null;
    }

    Image image;

    if (icon instanceof ImageIcon) {
      image = Java2DUtils.copyImage(((ImageIcon) icon).getImage());
    } else {
      image = Java2DUtils.createImage(comp, icon);
    }

    return new UIImageIcon(new UIImage(createPressedImage(image)));
  }

  /**
   * Creates a pressed image (a sharper crisper version)
   *
   * @param image the image to create the pressed version of
   *
   * @return the pressed image
   */
  public static Image createPressedImage(Image image) {
    if (image == null) {
      return null;
    }

    BufferedImage bi;

    if (image instanceof BufferedImage) {
      bi = (BufferedImage) image;
    } else {
      bi = Java2DUtils.toCompatibleImage(image);
    }

    return Java2DUtils.sharpen(bi, null);
  }

  /**
   * Ensures that the image is properly loaded before returning
   *
   * @param img the image to wait for
   * @return the loaded image (may be different than the one passed in)
   */
  public static Image ensureImageLoaded(Image img) {
    if (img instanceof BufferedImage) {
      return img;
    }

    if (img.getWidth(null) > -1) {
      return img;
    }

    if (IMAGE_LOADER == null) {
      IMAGE_LOADER = new ImageIcon();
    }

    IMAGE_LOADER.setImage(img);    // forces a load of the image;

    return img;
  }

  /**
   * Loads an image from a URL
   *
   * @param url the url for which to
   * @return the newly created image
   *
   * @throws java.io.IOException
   */
  public static UIImage loadImage(URL url) throws IOException {
    if (Platform.isInSandbox()) {
      return new UIImage(Toolkit.getDefaultToolkit().createImage(url));
    }

    return new UIImage(ImageIO.read(url));
  }

  /**
   * Rotates an icon 90 degrees
   *
   * @param comp the component for the icon
   * @param icon the icon
   * @param clockwise true to rotate clockwise; false otherwise
   *
   * @return the rotated icon
   */
  public static UIImageIcon rotateIcon(Component comp, Icon icon, boolean clockwise) {
    if (icon != null) {
      BufferedImage img;

      if (clockwise) {
        img = Java2DUtils.rotateRight(Java2DUtils.getBufferedImage(comp, icon), biliniar_hint);
      } else {
        img = Java2DUtils.rotateLeft(Java2DUtils.getBufferedImage(comp, icon), biliniar_hint);
      }

      return new UIImageIcon(new UIImage(img));
    }

    return null;
  }

  /**
   * Scales the image to the specified size
   *
   * @param image the image to scale
   * @param width the width of the image
   * @param height the height of the image
   * @return the scaled image
   */
  public static UIImage scaleImage(UIImage image, int width, int height) {
    return scaleImage(image, width, height, RenderingHints.VALUE_INTERPOLATION_BILINEAR, true);
  }

  /**
   * Scales the image to the specified size
   *
   * @param img the image to scale
   * @param width the width of the image
   * @param height the height of the image
   * @param hint the rendering hint
   * @param progressive true for progressive; false otherwise
   * @return the scaled image
   */
  public static UIImage scaleImage(UIImage image, int width, int height, Object hint, boolean progressive) {
    if (image instanceof DelayedImage) {
      image = ((DelayedImage) image).getRealImage();
    }

    Image img = (image == null)
                ? null
                : image.getImage();

    if ((img == null) || ((img.getWidth(null) == width) && (img.getHeight(null) == height))) {
      return image;
    }

    if (img instanceof BufferedImage) {
      img = Java2DUtils.getScaledInstance((BufferedImage) img, width, height, hint, true);
    } else {
      img = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }

    return new UIImage(img);
  }

  public static void setCacheImages(boolean cache, int cacheSize) {
    if (cache) {
      imageCache = new ObjectCache();
      imageCache.setBufferSize(cacheSize);
      imageCache.setStrongReferences(true);
    } else {
      if (imageCache != null) {
        imageCache.clear();
        imageCache = null;
      }
    }
  }

  /**
   * Sets the filter to use to create disabled images
   *
   * @param filter the filter
   *
   * @return the existing filter or null if there is none
   */
  public static iImageFilter setDisabledImageFilter(iImageFilter filter) {
    iImageFilter of = disabledImageFilter;

    disabledImageFilter = filter;

    return of;
  }

  public static UIImage getCachedImage(URL url, ObjectCache cache) {
    CachedImage ci = (CachedImage) cache.get(url);

    if (ci != null) {
      if (ci.isValid()) {
        return ci.image;
      }

      cache.remove(url);
    }

    return null;
  }

  /**
   * Get the outmost image icon for the specified icon (if there is one)
   *
   * @param ic the icon
   *
   * @return the outmost image icon for the specified icon (if there is one)
   */
  public static UIImageIcon getImageIcon(Icon ic) {
    if (ic instanceof RenderableDataItem) {
      ic = ((RenderableDataItem) ic).getIcon();
    }

    if (ic instanceof UIBorderIcon) {
      ic = ((UIBorderIcon) ic).getIcon();
    }

    if (ic instanceof UIImageIcon) {
      return (UIImageIcon) ic;
    }

    return null;
  }

  /**
   * Gets a slice of the specified image
   *
   * @param image the image
   * @param rect the rectangular slice
   *
   * @return the slice
   */
  public static UIImage getSlice(UIImage image, Rectangle rect) {
    if ((rect.width == 0) && (rect.height == 0)) {
      return image;
    } else {
      return getSlice(image, rect.x, rect.y, rect.width, rect.height);
    }
  }

  /**
   * Gets a slice of the specified image.
   * The image is assumed to be made up of uniform slices
   *
   * @param image the image
   * @param pos the position of the slice
   * @param size the uniform size of slices
   *
   * @return the slice
   */
  public static UIImage getSlice(UIImage image, int pos, int size) {
    int width  = image.getWidth();
    int height = image.getHeight();
    int h      = size;
    int w      = size;
    int x      = 0;
    int y      = 0;

    if (height > width) {
      w = width;
      y = pos * size;
    } else {
      h = height;
      x = pos * size;
    }

    return getSlice(image, x, y, w, h);
  }

  /**
   * Gets a slice of the specified image
   *
   * @param img the image
   * @param x the x-position
   * @param y the y-position
   * @param w the width
   * @param h the height
   *
   * @return the slice
   */
  public static UIImage getSlice(UIImage image, int x, int y, int w, int h) {
    if (image instanceof DelayedImage) {
      image = ((DelayedImage) image).getRealImage();
    }

    Image img = image.getImage();

    if (img instanceof BufferedImage) {
      return new UIImage(((BufferedImage) img).getSubimage(x, y, w, h));
    }

    int[]        pixels = new int[w * h];
    PixelGrabber pg     = new PixelGrabber(img, x, y, w, h, pixels, 0, w);

    try {
      pg.grabPixels();
    } catch(InterruptedException e) {
      Platform.ignoreException("interrupted waiting for pixels!", null);

      return null;
    }

    if ((pg.status() & ImageObserver.ABORT) != 0) {
      Platform.ignoreException("image fetch aborted or errored", null);

      return null;
    }

    return new UIImage(Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(w, h, pixels, 0, w)));
  }

  /**
   * Gets a predefined system cursor
   *
   * @param name the name of the cursor
   *
   * @return the cursor or null if no such cursor exists
   */
  public static Cursor getSystemCursor(String name) {
    if (name == null) {
      return null;
    }

    Integer i = systemCursorMap.get(name.toLowerCase());

    try {
      return (i == null)
             ? Cursor.getSystemCustomCursor(name)
             : Cursor.getPredefinedCursor(i);
    } catch(Exception ignore) {
      return null;
    }
  }

  /**
   * An images that defers the creation on another image until the underlying
   * image is accessed
   */
  public static class DelayedImage extends UIImage implements Callable<UIImage>, iCancelable {
    float                     density;
    private int               height = -1;
    private int               width  = -1;
    private int               constraints;
    private iCancelableFuture future;
    private volatile UIImage  image;
    private int               size;
    private volatile URL      url;

    public DelayedImage(URL url) {
      this(url, 0, 0, null, null, 0);
    }

    private DelayedImage(URL url, int size, int constraints, ScalingType scalingType, UIColor background,
                         float density) {
      iPlatformAppContext app = Platform.getAppContext();

      if (!app.isShuttingDown()) {
        this.url         = url;
        future           = app.executeBackgroundTask(this);
        this.background  = background;
        this.size        = size;
        this.scalingType = scalingType;
        this.constraints = constraints;
        this.density     = density;
      }
    }

    @Override
    public void load() throws Exception {
      call();
    }
    
    @Override
    public UIImage call() throws Exception {
      UIImage img = null;

      if (!Platform.isShuttingDown()) {
        try {
          img   = ImageHelper.createImage(url, false, size, constraints, scalingType, background, density);
          image = img;
        } catch(Exception ex) {
          img = image = UIImageIcon.getBrokenImage();
          Platform.ignoreException(url.toExternalForm(), ex);
        }
      }

      future = null;
      url    = null;
      Platform.invokeLater(new Runnable() {
        @Override
        public void run() {
          notifyObservers(DelayedImage.this);
        }
      });
      awtImage = (img == null)
                 ? null
                 : img.getImage();

      return img;
    }

    @Override
    public void cancel(boolean canInterrupt) {
      if (future != null) {
        canceled = true;
        this.url = null;

        try {
          future.cancel(true);
        } catch(Exception ignore) {}
      } else {}
    }

    public void flush() {}

    /**
     * @param height
     *          the height to set
     */
    public void setHeight(int height) {
      this.height = height;
    }

    /**
     * @param width
     *          the width to set
     */
    public void setWidth(int width) {
      this.width = width;
    }

    @Override
    public int getHeight() {
      return (image == null)
             ? height
             : image.getHeight();
    }

    @Override
    public UIImage getRealImage() {
      iCancelableFuture f   = future;
      UIImage           img = this.image;

      if ((img == null) && (f != null)) {
        try {
          img = (UIImage) f.get();
        } catch(Exception ex) {
          img = null;
        }

        if (img == null) {
          canceled = true;
          img      = UIImageIcon.getBrokenImage();
        }

        future = null;
        url    = null;
      }

      return img;
    }

    @Override
    public UIImage getSubimage(int x, int y, int w, int h) {
      return getRealImage().getSubimage(x, y, w, h);
    }

    @Override
    public int getWidth() {
      return (image == null)
             ? width
             : image.getWidth();
    }

    @Override
    public boolean isCanceled() {
      return canceled;
    }

    @Override
    public boolean isDone() {
      return (future == null) || future.isDone();
    }

    @Override
    protected boolean isLoadedEx(boolean hasObserver) {
      return image != null;
    }
  }


  static class CachedImage {
    String  fileName;
    UIImage image;
    long    timestamp;

    public CachedImage(String filename, UIImage image) {
      this.image = image;
      fileName   = filename;
      timestamp  = (filename == null)
                   ? 0
                   : new File(filename).lastModified();
    }

    public boolean isValid() {
      if ((fileName == null) || (timestamp == 0)) {
        return true;
      }

      File f = new File(fileName);

      if (f.lastModified() != timestamp) {
        return false;
      }

      return true;
    }
  }
}
