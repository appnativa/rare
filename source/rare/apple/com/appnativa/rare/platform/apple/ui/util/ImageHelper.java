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

package com.appnativa.rare.platform.apple.ui.util;

import com.appnativa.rare.Platform;
import com.appnativa.rare.iFunctionCallback;
import com.appnativa.rare.iPlatformAppContext;
import com.appnativa.rare.net.ActionLink;
import com.appnativa.rare.net.JavaURLConnection;
import com.appnativa.rare.net.NSURLConnectionHelper;
import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIImage;
import com.appnativa.rare.ui.UIImageIcon;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.painter.iImagePainter.ScalingType;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.util.iCancelable;

import java.io.IOException;

import java.net.URL;

import java.util.concurrent.Callable;

public class ImageHelper {

  /**
   * Constrains an image
   *
   * @param img
   *          the image
   * @param width
   *          the width constraint
   * @param height
   *          the width constraint
   * @param constraints
   *          the constraints 0=do nothing 1=constrain width to the specified
   *          size 2=constrain height to the specified size 3=constrain width
   *          and height to fit within the specified size (will maintain
   *          proportions) 4=constrain width and height to the specified size
   *          5=constrain width and height to fill the specified size (will
   *          maintain proportions)
   * @param bg
   *          the background color to use to fill empty space (can be null)
   * @param scalingType
   *          the type of scaling to do (null for bilinear)
   * @return the constrained image
   */
  public static UIImage constrain(UIImage img, int width, int height, int constraints, UIColor bg,
                                  ScalingType scalingType) {
    if (scalingType == null) {
      scalingType = ScalingType.BILINEAR;
    }

    int iw;
    int ih;

    switch(constraints) {
      case 1 :
        if (img.getWidth() > width) {
          height = (int) Math.max(1, height * ((float) width) / (img.getWidth()));
          img    = ImageUtils.getScaledImage(img, width, height, scalingType);
        }

        break;

      case 2 :
        if (img.getHeight() > height) {
          width = (int) Math.max(1, width * ((float) (height) / (float) img.getHeight()));
          img   = ImageUtils.getScaledImage(img, width, height, scalingType);
        }

        break;

      case 3 :
        iw = img.getWidth();
        ih = img.getHeight();

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
              img = ImageUtils.getScaledImage(img, iw, ih, scalingType);

              if (bg != null) {
//                                                              UIImage bimg =  ImageUtils.createCompatibleImage(width, height);
//
//                                                              bimg.getBitmap().eraseColor(bg.getColor());
//
//                                                              Canvas g = new Canvas(bimg.getBitmap());
//
//                                                              g.drawBitmap((img).getBitmap(), (width - iw) / 2, (height - ih) / 2, null);
//                                                              img = bimg;
              }
            }
          }
        }

        break;

      case 4 :
        iw = img.getWidth();
        ih = img.getHeight();

        if ((iw > 0) && (ih > 0) && ((iw != width) || (ih != height))) {
          img = ImageUtils.getScaledImage(img, width, height, scalingType);
        }

        break;

      case 5 :
        iw = img.getWidth();
        ih = img.getHeight();

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

          img = ImageUtils.getScaledImage(img, iw, ih, scalingType);

          if (bg != null) {
//                                              UIImage bimg =  ImageUtils.createCompatibleImage(width, height);
//
//                                              bimg.getBitmap().eraseColor(bg.getColor());
//
//                                              Canvas g = new Canvas(bimg.getBitmap());
//
//                                              g.drawBitmap((img).getBitmap(), (width - iw) / 2, (height - ih) / 2, null);
//                                              img = bimg;
          }
        }

        break;

      default :
        break;
    }

    return img;
  }

  /**
   * Creates a disabled icon
   *
   * @param icon
   *          the icon to create the disabled version of
   *
   * @return the disabled icon
   */
  public static UIImageIcon createDisabledIcon(iPlatformIcon icon) {
    if (icon == null) {
      return null;
    }

    if (icon instanceof UIImageIcon) {
      return new UIImageIcon(createDisabledImage(((UIImageIcon) icon).getImage()));
    }

    UIImage image;

    image = ImageUtils.createImage(icon);

    return new UIImageIcon(createDisabledImage(image));
  }

  /**
   * Creates a disabled image
   *
   * @param image
   *          the source image
   *
   * @return the disabled image
   */
  public static UIImage createDisabledImage(UIImage image) {
    return new UIImage(ImageUtils.createDisabledImage(image.getProxy()));
  }

  /**
   * Creates an icon
   *
   * @param url
   *          the url to use to create the icon
   * @param defer
   *          true the defer the creation of the list image data; false to
   *          create immediately
   * @param densityScale
   *          true to scale the image for the screen density; false to leave the
   *          image as is
   * @param density
   *          the density of the icon (only valid is densityScale is true)
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
   * @param app
   *          the application context
   * @param url
   *          the url to use to create the icon
   * @param description
   *          the icon's description
   * @param name
   *          the name of the icon
   * @param defer
   *          true the defer the creation of the list image data; false to
   *          create immediately
   * @param densityScale
   *          true to scale the image for the screen density; false to leave the
   *          image as is
   *
   * @param density
   *          the density of the icon (only valid is densityScale is true)
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
      url = null;

      return new UIImageIcon(UIImageIcon.getBrokenImage());
    }
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
   * @param density
   *          the density of the icon
   *
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
   * @param url
   *          the url to use to create the image
   * @param defer
   *          true the defer the creation of the image data; false to create
   *          immediately
   * @param density
   *          the density of the icon
   *
   * @return the newly created image
   * @throws IOException
   *           throws an I/O exception
   */
  public static UIImage createImage(URL url, boolean defer, float density) throws IOException {
    return createImage(url, defer, 0, 0, null, null, density);
  }

  /**
   * Creates an image
   *
   * @param url
   *          the url to use to create the image
   * @param defer
   *          true the defer the creation of the image data; false to create
   *          immediately
   * @param densityScale
   *          true to scale the image for the screen density; false to leave the
   *          image as is
   * @param size
   *          a size constraint
   * @param constraints
   *          the type of constraint
   * @param st
   *          the type of scaling to do (null for bilinear)
   * @param bg
   *          the background color to use to fill empty space (can be null)
   * @param density
   *          the density of the icon (only valid is densityScale is true)
   *
   * @return the newly created image
   * @throws IOException
   *           throws an I/O exception
   */
  public static UIImage createImage(URL url, boolean defer, int size, int constraints, ScalingType st, UIColor bg,
                                    float density)
          throws IOException {
    if (url == null) {
      return null;
    }

    String protocol = url.getProtocol();

    if (protocol.equals("file") || protocol.equals("data")) {
      defer = false;
    }

    if (density == 0) {
      density = ScreenUtils.getDefaultIconDensity();
    }

    iPlatformAppContext app = Platform.getAppContext();

    if (defer && Platform.isUIThread()) {
      return new DelayedImage(app, url, size, constraints, st, bg, density);
    }

    Integer to    = (Integer) app.getUIDefaults().get("Rare.net.imageLoadTimeout");
    int     tm    = (to == null)
                    ? 100 * 60 * 10
                    : to.intValue();
    Object  proxy = ImageUtils.createImageProxy(url, tm, density);
    UIImage img   = (proxy == null)
                    ? null
                    : new UIImage(proxy, JavaURLConnection.toExternalForm(url));

    if ((img != null) && (constraints > 0) && (size > 0)) {
      img = constrain(img, size, size, constraints, bg, st);
    }

    return img==null ? UIImageIcon.getBrokenImage() : img;
  }

  public static UIImage createImage(URL url, Object proxy, boolean densityScale, int size, int constraints,
                                    ScalingType st, UIColor bg, float density)
          throws IOException {
    if (url == null) {
      return null;
    }

    UIImage img = new UIImage(proxy, JavaURLConnection.toExternalForm(url));

    if ((constraints > 0) && (size > 0)) {
      img = constrain(img, size, size, constraints, bg, st);
    }

    return img;
  }

  /**
   * Ensures that the image is properly loaded before returning
   *
   * @param img
   *          the image to wait for
   * @return the loaded image (may be different than the one passed in)
   */
  public static UIImage ensureImageLoaded(UIImage img) {
    if (img instanceof DelayedImage) {
      return ((DelayedImage) img).getRealImage();
    }

    return img;
  }

  /**
   * Scales the image to the specified size
   *
   * @param image
   *          the image to scale
   * @param width
   *          the width of the image
   * @param height
   *          the height of the image
   * @return the scaled image
   */
  public static UIImage scaleImage(UIImage image, int width, int height) {
    if (image instanceof DelayedImage) {
      image = ((DelayedImage) image).getRealImage();
    }

    if ((image == null) || ((image.getWidth() == width) && (image.getHeight() == height))) {
      return image;
    }

    return ImageUtils.getScaledImage(image, width, height, ScalingType.BILINEAR);
  }

  /**
   * An images that defers the creation on another image until the underlying
   * image is accessed
   */
  public static class DelayedImage extends UIImage implements Callable<UIImage>, iCancelable, iFunctionCallback {
    private int                   height = -1;
    private int                   width  = -1;
    private int                   constraints;
    private float                 density;
    private NSURLConnectionHelper future;
    private volatile UIImage      image;
    private int                   size;
    private volatile URL          url;
    boolean                       hasWaiters;

    public DelayedImage(iPlatformAppContext app, URL url) {
      this(app, url, 0, 0, null, null, ScreenUtils.getDefaultIconDensity());
    }

    private DelayedImage(iPlatformAppContext app, URL url, int size, int constraints, ScalingType scalingType,
                         UIColor background, float density) {
      if (app == null) {
        app = Platform.getAppContext();
      }

      if (!app.isShuttingDown()) {
        this.url         = url;
        this.background  = background;
        this.size        = size;
        this.scalingType = scalingType;
        this.constraints = constraints;
        this.density     = density;
        future           = new NSURLConnectionHelper();
        future.sendURL(url, this);
      }
    }

    @Override
    public UIImage call() throws Exception {
      UIImage img = null;

      if (!Platform.isShuttingDown()) {
        try {
          if (url != null) {
            img = ImageHelper.createImage(url, false, density);
          }

          if ((img != null) && (constraints > 0) && (size > 0)) {
            img = constrain(img, size, size, constraints, background, scalingType);
          }

          image = img;
        } catch(Exception ex) {
          img = image = UIImageIcon.getBrokenImage();
          Platform.ignoreException((url == null)
                                   ? null
                                   : url.toExternalForm(), ex);
        }
      }

      future = null;
      url    = null;

      if (img != null) {
        proxy = img.getProxy();
      }

      Platform.invokeLater(new Runnable() {
        @Override
        public void run() {
          notifyObservers(DelayedImage.this);
        }
      });

      return img;
    }

    @Override
    public void cancel(boolean canInterrupt) {
      NSURLConnectionHelper h = future;

      future = null;

      if (h != null) {
        canceled = true;
        this.url = null;

        try {
          h.cancel();
        } catch(Exception ignore) {}
      }
    }

    @Override
    public void finished(boolean canceled, Object returnValue) {
      NSURLConnectionHelper h = (NSURLConnectionHelper) returnValue;
      String                s = (url == null)
                                ? ""
                                : url.toExternalForm();

      if (canceled) {
        String err = h.getError();

        if (!this.canceled && (err != null)) {
          s += "\n" + err;
          Platform.debugLog(s);
        }

        image = UIImageIcon.getBrokenImage();
        proxy = image.getProxy();
      } else {
        proxy      = ImageUtils.createImageProxy(h.getData(), density);
        this.image = new UIImage(proxy, s);
      }

      h.dispose();
      future = null;
      Platform.invokeLater(new Runnable() {
        @Override
        public void run() {
          notifyObservers(DelayedImage.this);
        }
      });
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

    public void load() throws Exception {
      call();
    }

    @Override
    public int getHeight() {
      return (image == null)
             ? height
             : image.getHeight();
    }

    public UIImage getRealImage() {
      Object  f   = future;
      UIImage img = this.image;

      if ((img == null) && (f != null)) {
        synchronized(this) {
          hasWaiters = true;

          try {
            wait();    //socket will timeout
          } catch(InterruptedException ignore) {
            if (this.image == null) {
              this.image = UIImageIcon.getBrokenImage();
            }
          }
        }
      }

      return this.image;
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
      return (future == null);
    }

    @Override
    protected boolean isLoadedEx(boolean hasObserver) {
      return image != null;
    }
  }
}
