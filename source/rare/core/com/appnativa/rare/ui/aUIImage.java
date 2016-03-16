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

import com.appnativa.rare.ui.painter.NinePatch;
import com.appnativa.rare.ui.painter.iImagePainter.ScalingType;
import com.appnativa.util.IdentityArrayList;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 *
 * @author Don DeCoteau
 */
public abstract class aUIImage implements iPlatformImage {
  protected ScalingType                       scalingType = ScalingType.BILINEAR;
  protected UIColor                           background;
  protected boolean                           canceled;
  protected int                               constraintHeight;
  protected int                               constraintType;
  protected int                               constraintWidth;
  protected iImageObserver                    imageObserver;
  protected boolean                           isninePatch;
  protected String                            location;
  protected NinePatch                         ninePatch;
  protected IdentityArrayList<iImageObserver> observersList;
  private boolean                             ninePatchChecked;
  private String                              resourceName;

  protected aUIImage() {}

  /**
   * Add a reflection to this image. The reflection will be copied to y+height+gap
   * of the specified image and as such the specified image should be at least y+(height*2)+gap
   * in height.
   * 
   * @param y the point at which to get the data for the reflection
   * @param height the height of the reflection
   * @param opacity  the reflection opacity
   * @param gap the gap between the image an its reflection
   */
  public abstract void addReflectionImage(int y, int height, float opacity, int gap);

  public abstract void blurImage();

  @Override
  public Object clone() {
    try {
      aUIImage img = (aUIImage) super.clone();

      img.observersList = null;
      img.imageObserver = null;
      img.ninePatch     = null;

      return img;
    } catch(CloneNotSupportedException e) {
      throw new InternalError();
    }
  }

  public boolean changeColor(UIColor newColor, UIColor oldColor) {
    if ((newColor == null) || (oldColor == null)) {
      return false;
    }

    if (!isLoaded()) {
      return false;
    }

    int       w  = getWidth();
    int       h  = getHeight();
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
        final int c = getPixel(x, y);
        final int a = (c >> 24) & 0xff;
        final int r = (c >> 16) & 0xff;
        final int g = (c >> 8) & 0xff;
        final int b = c & 0xff;

        if ((a == 0) || (Math.abs(r - or) > 5) || (Math.abs(g - og) > 5) || (Math.abs(b - ob) > 5)) {
          continue;
        }

        setPixel(x, y, (a << 24) | (nr << 16) | (ng << 8) | nb);
      }
    }

    return true;
  }

  public abstract void contstrain(int width, int height, int constraints, UIColor bg, ScalingType st);

  public abstract aUIImage createDisabledImage();

  /**
   * Creates a new image that is a reflection of this image
   * 
   * @param height the height of the reflection (use -1 to use the full height of the image)
   * @param gap the gap to gap above the reflection
   * @return the new image
   */
  public abstract aUIImage createReflection(int height, float opacity, int gap);

  /**
   * Creates a new image that is this image with a reflection added
   * 
   * @param height the height of the reflection (use -1 to use the full height of the image)
   * @param opacity  the reflection opacity
   * @param gap the gap between the image an its reflection
   * @return the new image
   */
  public abstract aUIImage createCopyWithReflection(int height,float opacity, int gap);
  public void dispose() {
    if (observersList != null) {
      observersList.clear();
    }

    imageObserver = null;
    observersList = null;
    ninePatch     = null;
    background    = null;
  }

  /**
   * Gets the image pixels, at the specified location
   *
   * @param x the x location
   * @param y the y location
   * @param width the width
   * @param height the height
   *
   * @return the byte buffer containing the pixels
   */
  public ByteBuffer getImageBytes(int x, int y, int width, int height) {
    //specific platforms should optimize this method
    aUIImage   img = this;
    ByteBuffer b   = ByteBuffer.allocate(width * height * 4);

    b = b.order(ByteOrder.BIG_ENDIAN);

    int xx = x;
    int yy = y;

    for (y = 0; y < height; y++) {
      for (x = 0; x < width; x++) {
        int p = img.getPixel(xx + x, yy + y);

        b.put((byte) (p >>> 24));
        b.put((byte) (p >>> 16));
        b.put((byte) (p >>> 8));
        b.put((byte) p);
      }
    }

    return b;
  }

  /**
   * Sets the image pixels, at the specified location, from a byte buffer
   *
   * @param x the x location
   * @param y the y location
   * @param width the width
   * @param height the height
   * @param bb the byte buffer contain the bytes
   */
  public void setImageBytes(int x, int y, int width, int height, ByteBuffer bb) {
    //specific platforms should optimize this method
    int      xx  = x;
    int      yy  = y;
    aUIImage img = this;

    for (y = 0; y < height; y++) {
      for (x = 0; x < width; x++) {
        img.setPixel(xx + x, yy + y, bb.getInt());
      }
    }
  }

  @Override
  public String toString() {
    if (resourceName != null) {
      return resourceName;
    }

    if (location != null) {
      return location;
    }

    return super.toString();
  }

  /**
   * SCales the image proportionally to fit in a box of the specified size
   * @param size the new image size
   */
  public void scale(int size) {
    int iw     = getWidth();
    int ih     = getHeight();
    int width  = size;
    int height = size;

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
          scale(iw, ih);
        }
      }
    }
  }

  public abstract void scale(int width, int height);

  public void setNinePatch(NinePatch ninePatch) {
    this.ninePatch = ninePatch;
  }

  public abstract void setPixel(int x, int y, int value);

  public abstract void setPixels(int[] pixels, int x, int y, int width, int height);

  public void setResourceName(String resourceName) {
    this.resourceName = resourceName;
  }

  public void setScalingType(ScalingType scalingType) {
    this.scalingType = scalingType;
  }

  public void setSlice(UIRectangle rect) {
    setSlice((int) rect.x, (int) rect.y, (int) rect.width, (int) rect.height);
  }

  public void setSlice(int pos, int size) {
    int width  = getWidth();
    int height = getHeight();
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

    setSlice(x, y, w, h);
  }

  public abstract void setSlice(int x, int y, int width, int height);

  @Override
  public abstract int getHeight();

  public String getLocation() {
    return location;
  }

  public NinePatch getNinePatch(boolean force) {
    if ((ninePatch == null) && (force || isNinePatch())) {
      ninePatch = new NinePatch(this);
    }

    return ninePatch;
  }

  public abstract int getPixel(int x, int y);

  public abstract int[] getPixels(int[] pixels, int x, int y, int width, int height);

  public String getResourceName() {
    return resourceName;
  }

  public ScalingType getScalingType() {
    return scalingType;
  }

  public aUIImage getSlice(UIRectangle rect) {
    return getSlice((int) rect.x, (int) rect.y, (int) rect.width, (int) rect.height);
  }

  public aUIImage getSlice(int pos, int size) {
    int width  = getWidth();
    int height = getHeight();
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

    return getSlice(x, y, w, h);
  }

  public abstract aUIImage getSlice(int x, int y, int width, int height);

  public abstract aUIImage getSubimage(int x, int y, int width, int height);

  @Override
  public abstract int getWidth();

  public boolean isCanceled() {
    return canceled;
  }

  @Override
  public boolean isLoaded() {
    return isLoadedEx(false);
  }

  @Override
  public boolean isImageLoaded(iImageObserver is) {
    if (isLoadedEx(is != null)) {
      return true;
    }

    if ((is != null) && (is != this.imageObserver)) {
      if (this.imageObserver == null) {
        this.imageObserver = is;
      } else {
        if (observersList == null) {
          observersList = new IdentityArrayList<iImageObserver>(3);
        }

        observersList.addIfNotPresent(is);
      }
    }

    return false;
  }

  @Override
  public boolean isNinePatch() {
    if (!ninePatchChecked) {
      ninePatchChecked = true;
      isninePatch      = isNinePatchEx();
    }

    return isninePatch;
  }

  protected void notifyObserver(UIImage img, iImageObserver is) {
    if (is != null) {
      try {
        is.imageLoaded(img);
      } catch(Exception ignore) {}
    }
  }

  protected synchronized void notifyObservers(UIImage img) {
    if (imageObserver != null) {
      notifyObserver(img, imageObserver);
    }

    if (observersList != null) {
      for (iImageObserver is : observersList) {
        notifyObserver(img, is);
      }
    }

    imageObserver = null;
    observersList = null;
  }

  protected abstract boolean isLoadedEx(boolean hasObserver);

  protected boolean isNinePatchEx() {
    if (location != null) {
      if (location.endsWith(".9.png")) {
        return true;
      }

      if (location.contains(".9.png?")) {
        return true;
      }

      if (location.contains(".9.png#")) {
        return true;
      }
    }

    return false;
  }
}
