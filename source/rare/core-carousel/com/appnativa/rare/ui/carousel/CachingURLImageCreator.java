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

package com.appnativa.rare.ui.carousel;

import com.appnativa.rare.Platform;
import com.appnativa.rare.iExecutionHandler;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.UIImage;
import com.appnativa.rare.ui.UIImageIcon;
import com.appnativa.rare.ui.iImageObserver;
import com.appnativa.util.ObjectCache;
import com.appnativa.util.iURLResolver;

import java.net.MalformedURLException;

/**
 *
 * @author Don DeCoteau
 */
public class CachingURLImageCreator implements iImageCreator {
  private static UIImageIcon NULL_ICON;
  iExecutionHandler          executorService;
  private volatile boolean   canceled;
  private UIImageIcon        delayedImage;
  private ObjectCache        imageCache;
  private iURLResolver       resolver;

  public CachingURLImageCreator(iURLResolver resolver, iExecutionHandler es, UIImage delayedImage) {
    this.resolver        = resolver;
    this.executorService = es;

    if (delayedImage != null) {
      this.delayedImage = new UIImageIcon(delayedImage);
    } else {
      this.delayedImage = getNullIcon();
    }
  }

  public CachingURLImageCreator(iURLResolver resolver, int cacheSize, iExecutionHandler es, UIImage delayedImage) {
    this.resolver        = resolver;
    this.executorService = es;

    if (delayedImage != null) {
      this.delayedImage = new UIImageIcon(delayedImage);
    } else {
      this.delayedImage = getNullIcon();
    }

    imageCache = new ObjectCache();
    imageCache.setBufferSize(cacheSize);
  }

  @Override
  public void cancelLoading(aCarouselPanel panel) {
    canceled = true;
  }

  @Override
  public UIImage createImage(aCarouselPanel panel, Object value) {
    UIImage            img  = null;
    String             s    = null;
    RenderableDataItem item = (RenderableDataItem) value;

    if (panel.isUseLinkedData()) {
      if (item.getLinkedData() != null) {
        s = item.getLinkedData().toString();
      }
    } else {
      s = item.toString();
    }

    if ((s != null) && (s.length() > 0)) {
      UIImageIcon icon = loadImage(panel, s);

      img = icon.getUIImage();

      if (icon.getResourceName() == null) {
        icon.setResourceName("converted");
      }
    } else {
      img = (delayedImage == null)
            ? null
            : delayedImage.getUIImage();
    }

    return img;
  }

  @Override
  public void dispose(aCarouselPanel panel) {
    if (imageCache != null) {
      imageCache.clear();
    }

    resolver     = null;
    delayedImage = null;
  }

  @Override
  public void reset() {
    if (imageCache != null) {
      imageCache.clear();
    }

    canceled = false;
  }

  public void setDelayedImage(UIImage delayedImage) {
    if (delayedImage == null) {
      this.delayedImage = getNullIcon();
    } else {
      this.delayedImage = new UIImageIcon(delayedImage);
    }
  }

  public void setImageCache(ObjectCache imageCache) {
    this.imageCache = imageCache;
  }

  public void setResolver(iURLResolver resolver) {
    this.resolver = resolver;
  }

  public UIImage getDelayedImage() {
    return (delayedImage == null)
           ? null
           : delayedImage.getUIImage();
  }

  public ObjectCache getImageCache() {
    return imageCache;
  }

  /**
   * Returns an empty 1x1 pixel icon
   * @return a blank 1x1 pixel icon
   */
  public static UIImageIcon getNullIcon() {
    if (NULL_ICON == null) {
      NULL_ICON = new UIImageIcon(new UIImage(1, 1));
    }

    return NULL_ICON;
  }

  public iURLResolver getResolver() {
    return resolver;
  }

  protected UIImageIcon loadImage(iImageObserver is, String key) {
    UIImageIcon icon = (UIImageIcon) ((imageCache == null)
                                      ? null
                                      : imageCache.get(key));

    if (!canceled && (icon == null) && (resolver != null)) {
      try {
        icon = new UIImageIcon(resolver.getURL(key), key, delayedImage, 0);
        executorService.executeBackgroundTask(icon);

        if (imageCache != null) {
          imageCache.put(key, icon);
        }
      } catch(MalformedURLException ex) {
        Platform.ignoreException(null, ex);
      }
    }

    if ((icon != null) &&!icon.isImageLoaded(is)) {
      if (canceled) {
        icon.cancel(true);
      }

      icon = null;
    }

    if (icon == null) {
      icon = delayedImage;
    }

    return icon;
  }
}
