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

package com.appnativa.rare.platform.android.ui.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.graphics.drawable.StateListDrawable;

import com.appnativa.rare.Platform;
import com.appnativa.rare.iCancelableFuture;
import com.appnativa.rare.iPlatformAppContext;
import com.appnativa.rare.net.JavaURLConnection;
import com.appnativa.rare.net.iURLConnection;
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.platform.android.ui.IconDrawable;
import com.appnativa.rare.platform.android.ui.NullDrawable;
import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIImage;
import com.appnativa.rare.ui.UIImageIcon;
import com.appnativa.rare.ui.aUIImageIcon;
import com.appnativa.rare.ui.iImageObserver;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.painter.iImagePainter.ScalingType;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.util.CharScanner;
import com.appnativa.util.iCancelable;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;

/**
 *
 * @author Don DeCoteau
 */
public class ImageHelper {
  private static ThreadLocal<CharScanner> perThreadScanner = new ThreadLocal<CharScanner>() {
    protected synchronized CharScanner initialValue() {
      return new CharScanner();
    }
  };

  public static void changeNinePatchColor(Bitmap bmp, UIColor newColor, UIColor oldColor) {
    if ((newColor == null) || (oldColor == null)) {
      return;
    }

    int       w  = bmp.getWidth();
    int       h  = bmp.getHeight();
    int       nc = newColor.getRGB();
    final int nr = (nc >> 16) & 0xff;
    final int ng = (nc >> 8) & 0xff;
    final int nb = nc & 0xff;
    int       oc = oldColor.getRGB();
    final int or = (oc >> 16) & 0xff;
    final int og = (oc >> 8) & 0xff;
    final int ob = oc & 0xff;

    for (int y = 1; y < h; ++y) {
      for (int x = 1; x < w; ++x) {
        final int c = bmp.getPixel(x, y);
        final int a = (c >> 24) & 0xff;
        final int r = (c >> 16) & 0xff;
        final int g = (c >> 8) & 0xff;
        final int b = c & 0xff;

        if ((a == 0) || (Math.abs(r - or) > 5) || (Math.abs(g - og) > 5) || (Math.abs(b - ob) > 5)) {
          continue;
        }

        bmp.setPixel(x, y, (a << 24) | (nr << 16) | (ng << 8) | nb);
      }
    }
  }

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
          height = (int) Math.max(1, height * ((float) width) / ((float) img.getWidth()));
          img    = ImageUtils.getScaledInstance(img, width, height, scalingType.getValue(),
                  scalingType.isProgressive());
        }

        break;

      case 2 :
        if (img.getHeight() > height) {
          width = (int) Math.max(1, width * ((float) (height) / (float) img.getHeight()));
          img   = ImageUtils.getScaledInstance(img, width, height, scalingType.getValue(), scalingType.isProgressive());
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
              img = ImageUtils.getScaledInstance(img, iw, ih, scalingType.getValue(), scalingType.isProgressive());

              if (bg != null) {
                UIImage bimg = ImageUtils.createCompatibleImage(width, height);

                bimg.getBitmap().eraseColor(bg.getColor());

                Canvas g = new Canvas(bimg.getBitmap());

                g.drawBitmap((img).getBitmap(), (width - iw) / 2, (height - ih) / 2, null);
                img = bimg;
              }
            }
          }
        }

        break;

      case 4 :
        iw = img.getWidth();
        ih = img.getHeight();

        if ((iw > 0) && (ih > 0) && ((iw != width) || (ih != height))) {
          img = ImageUtils.getScaledInstance(img, width, height, scalingType.getValue(), scalingType.isProgressive());
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

          img = ImageUtils.getScaledInstance(img, iw, ih, scalingType.getValue(), scalingType.isProgressive());

          if (bg != null) {
            UIImage bimg = ImageUtils.createCompatibleImage(width, height);

            bimg.getBitmap().eraseColor(bg.getColor());

            Canvas g = new Canvas(bimg.getBitmap());

            g.drawBitmap((img).getBitmap(), (width - iw) / 2, (height - ih) / 2, null);
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

    UIImage image;

    if (icon instanceof aUIImageIcon) {
      image = ((aUIImageIcon) icon).getImage();
    } else {
      image = ImageUtils.createImage(icon);
    }

    return new UIImageIcon(ImageHelper.createDisabledImage(image));
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
    Bitmap bm = image.getBitmap();

    if (bm != null) {
      bm    = ImageUtils.createDisabledImage(bm);
      image = new UIImage(bm);
    }

    return image;
  }

  /**
   * Creates an icon
   *
   * @param url
   *          the url to use to create the icon
   * @param defer
   *          true the defer the creation of the list image data; false to
   *          create immediately
   * @param density
   *          the density of the icon (only valid is densityScale is true)
   *
   * @return the newly created icon
   */
  public static UIImageIcon createIcon(URL url, boolean defer, int density) {
    return (url == null)
           ? null
           : createIcon(url, url.toExternalForm(), defer, density);
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
  public static UIImageIcon createIcon(URL url, String description, boolean defer, float density) {
    if (url == null) {
      return null;
    }

    try {
      if (description == null) {
        description = url.toExternalForm();
      }

      return new UIImageIcon(createImage(url, defer, density), description);
    } catch(IOException ex) {
      Platform.ignoreException(url.toExternalForm(), ex);

      return null;
    }
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
   *          the density of the icon (only valid is densityScale is true)
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
    String protocol=url.getProtocol();
    if (protocol.equals("file") || protocol.equals("data")) {
      defer = false;
    }

    iPlatformAppContext app = Platform.getAppContext();

    if (defer) {
      return new DelayedImage(app, url, size, constraints, st, bg, density);
    }

    iURLConnection conn = app.openConnection(url);

    try {
      String      mime   = conn.getContentType();
      InputStream stream = conn.getInputStream();
      Integer     to     = (Integer) app.getUIDefaults().get("Rare.net.imageLoadTimeout");

      if (to != null) {
        conn.setReadTimeout(to);
      }

      UIImage img = createImage(JavaURLConnection.toExternalForm(url), mime, stream, density);

      if (img!=null && (constraints > 0) && (size > 0)) {
        img = constrain(img, size, size, constraints, bg, st);
      }

      return img==null ? UIImageIcon.getBrokenImage() : img;
    }
    catch(IOException e) {
      Platform.ignoreException(e);
      return UIImageIcon.getBrokenImage();
    } finally {
      conn.dispose();
    }
  }

  public static NinePatchDrawable createNinePatchDrawable(Context ctx, Bitmap bmp, Rect padding) {
    return new NinePatchDrawable(ctx.getResources(), bmp, bmp.getNinePatchChunk(), padding, "");
  }

  public static NinePatchDrawable createNinePatchDrawable(Context ctx, Bitmap source, Rect padding, UIColor newColor,
          UIColor oldCOlor) {
    Bitmap bmp   = source.copy(source.getConfig(), true);
    byte[] chunk = source.getNinePatchChunk();

    changeNinePatchColor(bmp, newColor, oldCOlor);

    return new NinePatchDrawable(ctx.getResources(), bmp, chunk, padding, "");
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
    return scaleImage(image, width, height, ScalingType.BILINEAR, true);
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
   * @param hint
   *          the rendering hint
   * @param progressive
   *          true for progressive; false otherwise
   * @return the scaled image
   */
  public static UIImage scaleImage(UIImage image, int width, int height, Object hint, boolean progressive) {
    if (image instanceof DelayedImage) {
      image = ((DelayedImage) image).getRealImage();
    }

    if ((image == null) || ((image.getWidth() == width) && (image.getHeight() == height))) {
      return image;
    }

    return ImageUtils.getScaledInstance(image, width, height, hint, true);
  }

  /**
   * Returns a icon for a string representing a drawable state list
   *
   * @param icon
   *          the string representing the icon
   * @param context
   *          the context
   *
   * @return an icon object represented by the specified icon string
   */
  public static iPlatformIcon getDrawableStateList(String icon, iWidget context) {
    Map<String, String> map = CharScanner.parseOptionStringEx(icon, ';');
    CharScanner         sc  = perThreadScanner.get();

    if (map == null) {
      return null;
    }

    Entry<String, String>           e;
    Iterator<Entry<String, String>> it = map.entrySet().iterator();
    iPlatformIcon                   ic;
    int                             a[];
    Integer                         state;
    List<String>                    list = new ArrayList<String>(3);
    StateListDrawable               sl   = new StateListDrawable();

    while(it.hasNext()) {
      e    = it.next();
      icon = e.getValue();
      ic   = context.getIcon(icon, null);

      if (ic == null) {
        ic = NullDrawable.getInstance();
      }

      icon = e.getKey();
      sc.reset(icon);

      if ("normal".equals(icon)) {
        sl.addState(new int[0], ic.getDrawable(null));

        continue;
      }

      list = sc.getTokens(',', true, list);
      a    = new int[list.size()];

      for (int n = 0; n < a.length; n++) {
        state = PlatformHelper.colorStates.get(list.get(n));

        if (state == null) {
          a[n] = android.R.attr.state_window_focused;
          Platform.debugLog("Unknow state (" + list.get(n) + ") in drawable state list");
        } else {
          a[n] = state;
        }
      }

      sl.addState(a, ic.getDrawable(null));
    }

    return new IconDrawable(sl);
  }

  /**
   * Retrieves the icon identified by the specified resource path. If the icon
   * is not currently loaded, it will be loaded and cached
   *
   * @param app
   *          the application context
   * @param id
   *          the resource id
   *
   * @return the newly created icon
   */
  public static UIImageIcon getResourceIcon(iPlatformAppContext app, int id) {
    Drawable d = app.getActivity().getResources().getDrawable(id);

    if (d != null) {
      return new UIImageIcon(new UIImage(((BitmapDrawable) d).getBitmap()));
    }

    return null;
  }

  /**
   * Creates an image
   *
   * @param source
   *          a string identifying the source of the image's data
   * @param mime
   *          the mime type for the image
   * @param stream
   *          the stream to use to create the image
   * @param defer
   *          true the defer the creation of the image data; false to create
   *          immediately
   * @param densityScale
   *          true to scale the image for the screen density; false to leave the
   *          image as is
   * @param density
   *          the density of the icon (only valid is densityScale is true)
   *
   * @return the newly created image
   * @throws IOException
   *           throws an I/O exception
   */
  private static UIImage createImage(String source, String mime, InputStream stream, float density) throws IOException {
    if (stream == null) {
      return null;
    }

    try {
      if (density <= 0) {
        density = ScreenUtils.getDefaultIconDensity();
      }

      BitmapFactory.Options opts = new BitmapFactory.Options();

      if (density > 0) {
        opts.inDensity       = (int) (density * 160);
        opts.inTargetDensity = PlatformHelper.getDisplayMetrics().densityDpi;
      }

      opts.inPreferredConfig = Bitmap.Config.ARGB_8888;

      Bitmap bmp=null;
      try {
        bmp=BitmapFactory.decodeStream(new FlushedInputStream(stream), null, opts);
      } catch(OutOfMemoryError err) {
        Platform.ignoreException("createImage", err);
        System.gc();

        bmp=BitmapFactory.decodeStream(new FlushedInputStream(stream), null, opts);
      }
      return bmp==null ? null : new UIImage(bmp);
    } finally {
      try {
        stream.close();
      } catch(Exception ignored) {}
    }
  }

  /**
   * An images that defers the creation on another image until the underlying
   * image is accessed
   */
  public static class DelayedImage extends UIImage implements Callable<UIImage>, iCancelable {
    private int               height = -1;
    private int               width  = -1;
    private int               constraints;
    private float             density;
    private iCancelableFuture future;
    private volatile UIImage  image;
    private Object            notifyObject;
    private int               size;
    private volatile URL      url;

    public DelayedImage(iPlatformAppContext app, URL url, float density) {
      this(app, url, 0, 0, null, null, density);
    }

    private DelayedImage(iPlatformAppContext app, URL url, int size, int constraints, ScalingType scalingType,
                         UIColor background, float density) {
      if (app == null) {
        app = Platform.getAppContext();
      }

      if (!app.isShuttingDown()) {
        this.url         = url;
        this.density     = density;
        future           = app.executeBackgroundTask(this);
        this.background  = background;
        this.size        = size;
        this.scalingType = scalingType;
        this.constraints = constraints;
      }
    }

    
    public UIImage call() throws Exception {
      UIImage img = null;

      if (!Platform.isShuttingDown()) {
        try {
          img = ImageHelper.createImage(url, false, density);

          if ((img != null) && (constraints > 0) && (size > 0)) {
            img = constrain(img, size, size, constraints, background, scalingType);
          }

          image = img;
        } catch(Exception ex) {
          image = UIImageIcon.getBrokenImage();
          Platform.ignoreException(url.toExternalForm(), ex);
        }
      }

      if (image != null) {
        bitmap = image.getBitmap();
      }

      future = null;
      url    = null;
      Platform.invokeLater(new Runnable() {
        public void run() {
          notifyObservers(DelayedImage.this);
        }
      });

      if (notifyObject != null) {
        synchronized(notifyObject) {
          notifyObject.notifyAll();
          notifyObject = null;
        }
      }

      return img;
    }
    @Override
    public void load() throws Exception {
      call();
    }

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
     * @param notifyObject
     *          the notifyObject to set
     */
    public void setNotifyObject(Object notifyObject) {
      this.notifyObject = notifyObject;
    }

    /**
     * @param width
     *          the width to set
     */
    public void setWidth(int width) {
      this.width = width;
    }

    public Bitmap getBitmap() {
      return (getRealImage()).getBitmap();
    }

    public int getHeight() {
      return (image == null)
             ? height
             : image.getHeight();
    }

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

    public UIImage getSubimage(int x, int y, int w, int h) {
      return getRealImage().getSubimage(x, y, w, h);
    }

    public int getWidth(iImageObserver observer) {
      return (image == null)
             ? width
             : image.getWidth();
    }

    public boolean isCanceled() {
      return canceled;
    }

    public boolean isDone() {
      return (future == null) || future.isDone();
    }

    protected boolean isLoadedEx(boolean hasObserver) {
      return image != null;
    }
  }


  static class FlushedInputStream extends FilterInputStream {
    public FlushedInputStream(InputStream inputStream) {
      super(inputStream);
    }

    @Override
    public long skip(long n) throws IOException {
      long totalBytesSkipped = 0L;

      while(totalBytesSkipped < n) {
        long bytesSkipped = in.skip(n - totalBytesSkipped);

        if (bytesSkipped == 0L) {
          int bs = read();

          if (bs < 0) {
            break;               // we reached EOF
          } else {
            bytesSkipped = 1;    // we read one byte
          }
        }

        totalBytesSkipped += bytesSkipped;
      }

      return totalBytesSkipped;
    }
  }
}
