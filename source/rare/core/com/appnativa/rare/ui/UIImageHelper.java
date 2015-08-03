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

package com.appnativa.rare.ui;

import com.appnativa.rare.Platform;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.iPlatformAppContext;
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.ui.painter.UIImagePainter;
import com.appnativa.rare.ui.painter.iImagePainter;
import com.appnativa.rare.ui.painter.iImagePainter.ScalingType;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.spot.SPOTPrintableString;

import java.io.IOException;

import java.net.URL;

import java.util.Map;

public class UIImageHelper {
  public static UIImageIcon _animatedSpinner;

  /** small animated spinner icon */
  public static UIImageIcon _animatedSpinnerSmall;

  /**
   * Configures a component's icon scaling
   *
   * @param context
   *          the context
   * @param painter
   *          the image painter to configure (can be null)
   * @param imgURL
   *          the configuration object
   * @param emptyOk
   *          true to all the creation of a painter without and image; false
   *          otherwise
   * @return returns the configured painter (if one was not passed in, then a
   *         new one was created)
   */
  public static iImagePainter configureImage(iWidget context, iImagePainter painter, SPOTPrintableString imgURL,
          boolean emptyOk) {
    return configureImage(context, painter, imgURL.getValue(), imgURL.spot_getAttributesEx(), emptyOk);
  }

  /**
   * Configures a component's icon scaling
   *
   * @param context
   *          the context
   * @param painter
   *          the image painter to configure (can be null)
   * @param url
   *          the url to the image
   * @param attrs
   *          the image attributes
   * @param emptyOk
   *          true to all the creation of a painter without and image; false
   *          otherwise
   * @return returns the configured painter (if one was not passed in, then a
   *         new one was created)
   */
  public static iImagePainter configureImage(iWidget context, iImagePainter painter, String url,
          Map<String, String> attrs, boolean emptyOk) {
    return Utils.configureImage(context, painter, url, attrs, emptyOk);
  }

  public static iPlatformIcon createDisabledIcon(iPlatformIcon icon) {
    return PlatformHelper.createDisabledIcon(icon);
  }

  public static UIImage createImage(iPlatformIcon icon) {
    UIImage           img = new UIImage(icon.getIconWidth(), icon.getIconHeight());
    iPlatformGraphics g   = img.createGraphics();

    if (Platform.isIOS() || (Platform.isMac() &&!Platform.isJava())) {
      Transform t = new Transform();

      t.scale(1, -1);
      t.translate(0, -img.getHeight());
      g.setTransform(t);
    }

    g.clearRect(null, 0, 0, icon.getIconWidth(), icon.getIconHeight());
    icon.paint(g, 0, 0, icon.getIconWidth(), icon.getIconHeight());
    g.dispose();

    return img;
  }

  public static UIImageIcon createIcon(URL url, boolean defer) {
    return createIcon(url, null, defer, 0);
  }

  public static UIImageIcon createIcon(URL url, boolean defer, float density) {
    return createIcon(url, null, defer, density);
  }

  public static UIImageIcon createIcon(URL url, String description, boolean defer, float density) {
    return PlatformHelper.createIcon(url, description, defer, density);
  }

  public static UIImage createImage(URL url, boolean defer) throws IOException {
    return PlatformHelper.createImage(url, defer, 0);
  }

  public static UIImage createImage(URL url, boolean defer, float density) throws IOException {
    return PlatformHelper.createImage(url, defer, density);
  }

  /**
   * Creates a new image painter
   *
   * @param image
   *          the image for the painter (can be null)
   * @return the new image painter
   */
  public static iImagePainter createImagePainter(UIImage image) {
    return new UIImagePainter(image);
  }

  /**
   * Creates a state list icon from the specified string
   *
   * @param icon
   *          the icon string
   * @param context
   *          the context
   * @return the icon
   */
  public static iPlatformIcon createStateListIcon(String icon, iWidget context) {
    return PlatformHelper.createStateListIcon(icon, context);
  }

  public static UIImage scaleImage(UIImage img, int width, int height) {
    return PlatformHelper.scaleImage(img, width, height);
  }

  /**
   * sets the default animated spinner
   *
   * @param icon
   *          the default animated spinner
   */
  public static void setAnimatedSpinner(UIImageIcon icon) {
    _animatedSpinner = icon;
  }

  /**
   * sets the default small animated spinner
   *
   * @param icon
   *          the default small animated spinner
   */
  public static void setAnimatedSpinnerSmall(UIImageIcon icon) {
    _animatedSpinnerSmall = icon;
  }

  /**
   * Gets the default animated spinner
   *
   * @return the default animated spinner
   */
  public static UIImageIcon getAnimatedSpinner() {
    if (_animatedSpinner == null) {
      return Platform.getAppContext().getResourceAsIcon("animated_spinner");
    }

    return _animatedSpinner;
  }

  /**
   * Gets the default small animated spinner
   *
   * @return the default small animated spinner
   */
  public static UIImageIcon getAnimatedSpinnerSmall() {
    if (_animatedSpinnerSmall == null) {
      return Platform.getAppContext().getResourceAsIcon("animated_spinner_small");
    }

    return _animatedSpinnerSmall;
  }

  /**
   * Resolves the specified string image to an image instance The string may
   * contain embedded variables or functions
   *
   * @param context the context
   *
   * @param image
   *          the string image for the image
   * @param deferred
   *          true to defer the loading of the image; false to load inline
   * @param size
   *          the size to scale the image to (can be null)
   * @param st
   *          the scaling type to use when scaling the image
   * @param density
   *          the density of the icon (use zero if unknown)
   *
   * @return the image instance or null if the image does not exist or cannot be
   *         loaded
   */
  public static UIImage getImage(iWidget context, String image, boolean deferred, UIDimension size, ScalingType st,
                                 float density) {
    if (image == null) {
      return null;
    }

    if (context == null) {
      context = Platform.getContextRootViewer();
    }

    iPlatformAppContext app  = context.getAppContext();
    UIRectangle         rect = null;

    if (image.endsWith("]")) {
      int n = image.lastIndexOf('[');

      if (n > 0) {
        String s = image.substring(n + 1, image.length() - 1);

        image = image.substring(0, n);
        rect  = Utils.getRectangle(s);
      }
    }

    UIImage img = null;

    if (image.startsWith(iConstants.RESOURCE_PREFIX)) {
      img = app.getResourceAsImage(image.substring(9));
    } else {
      try {
        URL url = context.getURL(image);

        if (url != null) {
          if (image.startsWith(iConstants.SCRIPT_PREFIX)) {
            img = (UIImage) app.openConnection(url).getContent();
          } else {
            img = createImage(url, deferred, density);
          }
        }
      } catch(Exception e) {
        Platform.ignoreException(null, e);
      }
    }

    if (img != null) {
      if (st != null) {
        img.setScalingType(st);
      }

      if (rect != null) {
        img = getSlice(img, rect);
      }

      if (size != null) {
        img = scaleImage(img, size.intWidth(), size.intHeight());
      }
    }

    return img;
  }

  /**
   * Get the image referenced by the specified URL
   *
   * @param url
   *          the URL
   * @param deferred
   *          true to defer the loading of the image; false to load inline
   * @param size
   *          the size to scale the image to (can be null)
   * @param st
   *          the scaling type to use when scaling the image
   * @param density
   *          the density of the icon (use zero if unknown)
   *
   * @return the image instance or null if the image does not exist or cannot be
   *         loaded
   */
  public static UIImage getImage(iWidget context, URL url, boolean deferred, UIDimension size, ScalingType st,
                                 float density) {
    if (url == null) {
      return null;
    }

    UIImage img = null;

    try {
      img = PlatformHelper.createImage(url, deferred, density);

      if (st != null) {
        img.setScalingType(st);
      }

      if (size != null) {
        img = PlatformHelper.scaleImage(img, size.intWidth(), size.intHeight());
      }
    } catch(Exception e) {
      Platform.ignoreException(null, e);
    }

    return img;
  }

  /**
   * Gets a slice of the specified image
   *
   * @param image
   *          the image
   * @param rect
   *          the rectangular slice
   *
   * @return the slice
   */
  public static UIImage getSlice(UIImage image, UIRectangle rect) {
    return Utils.getSlice(image, rect);
  }

  /**
   * Gets a slice of the specified image. The image is assumed to be made up of
   * uniform slices
   *
   * @param image
   *          the image
   * @param pos
   *          the position of the slice
   * @param size
   *          the uniform size of slices
   *
   * @return the slice
   */
  public static UIImage getSlice(UIImage image, int pos, int size) {
    return Utils.getSlice(image, pos, size);
  }

  /**
   * Rotates an image given a 90 degree angle increment( 90, 180,270)
   *
   * @param image the image
   * @param rotation the rotation angle
   * @return the rotated image
   */
  public static UIImage rotateImage(UIImage image, int rotation) {
    int       iw     = image.getWidth();
    int       ih     = image.getHeight();
    int       width  = iw;
    int       height = ih;
    int       n;
    Transform t = new Transform();

    switch(rotation) {
      case 90 :
        t.rotate((float) Math.toRadians(90));
        t.translate(0, -ih);
        n      = width;
        width  = height;
        height = n;

        break;

      case 180 :
      case -180 :
        if (!PlatformHelper.areImagesUpsideDown()) {
          t.scale(1, -1);
          t.translate(0, -ih);
        }

        break;

      case 270 :
      case -90 :
        t.rotate((float) Math.toRadians(-90));
        t.translate(-iw, 0);
        n      = width;
        width  = height;
        height = n;

        break;

      default :
        return image;
    }

    UIImage           img = new UIImage(width, height);
    iPlatformGraphics g   = img.createGraphics();

    g.setTransform(t);
    g.drawImage(image, 0, 0);
    g.dispose();

    return img;
  }
}
