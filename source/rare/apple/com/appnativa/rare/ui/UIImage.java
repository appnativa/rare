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

import com.appnativa.rare.platform.apple.ui.util.AppleGraphics;
import com.appnativa.rare.platform.apple.ui.util.ImageUtils;
import com.appnativa.rare.ui.painter.iImagePainter.ScalingType;

/**
 *
 * @author Don DeCoteau
 */
public class UIImage extends aUIImage implements Cloneable {
  boolean          smoothScale = true;
  protected Object proxy;

  public UIImage(Object image) {
    this.proxy = image;
  }

  public UIImage(int width, int height) {
    this.proxy = ImageUtils.createImageProxy(width, height);
  }

  public UIImage(Object image, String location) {
    this.proxy    = image;
    this.location = location;
  }

  protected UIImage() {}

  @Override
  public void addReflectionImage(int y, int height, float opacity, int gap) {
    if (getHeight() < y + gap + height) {
      throw new IllegalArgumentException(
          "Image not tall enough to support adding a reflection with the specified dimensions.\nMight want to use createReflection");
    }

    proxy = ImageUtils.addReflection(proxy, y, height, opacity, gap);
  }

  @Override
  public void blurImage() {
    proxy = ImageUtils.blurImage(proxy);
  }

  public void cancel() {
    if (proxy != null) {
      canceled = true;
    }
  }

  @Override
  public Object clone() {
    UIImage img = (UIImage) super.clone();

    img.proxy = ImageUtils.copyImage(proxy);

    return img;
  }

  @Override
  public void contstrain(int width, int height, int constraints, UIColor bg, ScalingType st) {
    constraintWidth  = width;
    constraintHeight = height;
    constraintType   = constraints;

    if (st != null) {
      switch(st) {
        case NEAREST_NEIGHBOR :
        case NEAREST_NEIGHBOR_CACHED :
          smoothScale = false;

          break;

        default :
          break;
      }
    }
  }

  @Override
  public UIImage createDisabledImage() {
    return new UIImage(ImageUtils.createDisabledImage(proxy));
  }

  public iPlatformGraphics createGraphics() {
    return new AppleGraphics(ImageUtils.createContext(proxy), null);
  }

  @Override
  public UIImage createCopyWithReflection(int height, float opacity, int gap) {
    return new UIImage(ImageUtils.createCopyWithReflection(proxy, height, opacity, gap));
  }

  @Override
  public UIImage createReflection(int height, float opacity, int gap) {
    return new UIImage(ImageUtils.createReflection(proxy, height, opacity, gap));
  }

  @Override
  public void dispose() {
    super.dispose();
    proxy = null;
  }

  public static UIImage fromNativeImage(Object nativeImage) {
    return new UIImage(ImageUtils.createImageProxy(nativeImage));
  }

  @Override
  public void scale(int width, int height) {
    constraintWidth  = width;
    constraintHeight = height;
    smoothScale      = true;
  }

  @Override
  public void setPixel(int x, int y, int value) {
    ImageUtils.setPixel(proxy, x, y, value);
  }

  @Override
  public void setPixels(int[] pixels, int x, int y, int width, int height) {
    ImageUtils.setPixels(proxy, pixels, x, y, width, height);
  }

  @Override
  public void setSlice(int x, int y, int width, int height) {
    proxy = ImageUtils.getSubImage(proxy, x, y, width, height);
  }

  @Override
  public int getHeight() {
    return (proxy == null)
           ? -1
           : (int) ImageUtils.getHeight(proxy);
  }

  @Override
  public int getPixel(int x, int y) {
    return ImageUtils.getPixel(proxy, x, y);
  }

  @Override
  public int[] getPixels(int[] pixels, int x, int y, int width, int height) {
    if (pixels == null) {
      pixels = new int[width * height];
    } else if (pixels.length < width * height) {
      throw new IllegalArgumentException("Pixels array must have a length >= w * h");
    }

    ImageUtils.getPixels(proxy, x, y, width, height, pixels);

    return pixels;
  }

  /**
   * Get the proxy that the image represents
   * @return the proxy that the image represents
   */
  public Object getProxy() {
    return proxy;
  }

  /**
   * Gets the native image that this UIImage represents
   * @return the native image that this UIImage represents
   */
  public Object getNativeImage() {
    return (proxy == null)
           ? null
           : ImageUtils.getNativeImage(proxy);
  }

  /**
   * Changes the image proxy this UIImage object points to
   *
   * @param proxy the new proxy
   */
  public void setProxy(Object proxy) {
    this.proxy = proxy;
  }

  /**
   * Creates an image proxy from a native image object.
   * The proxy can be used to set into and existing UIImage object.
   *
   * @param nativeImage the nartive image
   * @return the new image proxy.
   */
  public static Object createImageProxyFromNativeImage(Object nativeImage) {
    return ImageUtils.createImageProxy(nativeImage);
  }

  @Override
  public UIImage getSlice(int x, int y, int width, int height) {
    UIImage img = (UIImage) clone();

    img.setSlice(x, y, width, height);

    return img;
  }

  @Override
  public UIImage getSubimage(int x, int y, int width, int height) {
    return new UIImage(ImageUtils.getSubImage(proxy, x, y, width, height));
  }

  @Override
  public int getWidth() {
    return (proxy == null)
           ? -1
           : (int) ImageUtils.getWidth(proxy);
  }

  @Override
  public boolean isNinePatchEx() {
    return (proxy != null) && super.isNinePatchEx();
  }

  @Override
  public void load() throws Exception {
  }
  @Override
  protected boolean isLoadedEx(boolean hasObserver) {
    return proxy != null;
  }
}
