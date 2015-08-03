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

import android.app.Activity;

import android.content.res.Configuration;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;

import com.appnativa.rare.Platform;
import com.appnativa.rare.platform.android.AppContext;
import com.appnativa.rare.platform.android.ui.util.AndroidGraphics;
import com.appnativa.rare.platform.android.ui.util.ImageHelper;
import com.appnativa.rare.platform.android.ui.util.ImageUtils;
import com.appnativa.rare.ui.painter.NinePatch;
import com.appnativa.rare.ui.painter.iImagePainter.ScalingType;

/**
 *
 * @author Don DeCoteau
 */
public class UIImage extends aUIImage implements Cloneable {
  protected Bitmap        bitmap;
  protected Drawable      drawable;
  protected Configuration lastConfiguration;
  protected int           resID;
  protected int           cloneCount;

  public UIImage(Bitmap bitmap) {
    this.bitmap = bitmap;
  }

  public UIImage(Bitmap bitmap, String location) {
    this.bitmap   = bitmap;
    this.location = location;
  }

  public UIImage(Drawable drawable, int resID) {
    setDrawable(drawable, resID);
  }

  public UIImage(int width, int height) {
    this(ImageUtils.createCompatibleBitmap(width, height));
  }

  public UIImage(Drawable bitmap, int resID, String location) {
    this.location = location;
    setDrawable(bitmap, resID);
  }

  protected UIImage() {}

  public void addReflectionImage(int y, int height, float opacity, int gap) {
    if (bitmap.getHeight() < y + height + gap + height) {
      throw new IllegalArgumentException(
          "Image not tall enough to support adding a reflection with the specified dimensions.\nMight want to use createReflection");
    }

    ImageUtils.addReflection(bitmap, y, height, opacity, gap);
  }

  public void blurImage() {
    ImageUtils.blurBitmap(bitmap);
  }

  public Object clone() {
    UIImage img = (UIImage) super.clone();

    img.bitmap = Bitmap.createBitmap(bitmap);

    return img;
  }

  @Override
  public void contstrain(int width, int height, int constraints, UIColor bg, ScalingType st) {
    UIImage img = ImageHelper.constrain(this, width, height, constraints, bg, st);

    if (img != null) {
      bitmap   = img.getBitmap();
      drawable = null;
    }
  }

  public UIImage createDisabledImage() {
    if (bitmap == null) {
      return null;
    }

    return new UIImage(ImageUtils.createDisabledImage(bitmap));
  }

  public iPlatformGraphics createGraphics() {
    return new AndroidGraphics(getBitmap());
  }

  public UIImage createReflectionImage(int y, int height, float opacity, int gap) {
    Bitmap bmp = ImageUtils.createReflection(bitmap, y, height, opacity, gap);

    return new UIImage(bmp);
  }

  public void dispose() {
    if (bitmap != null) {
      try {
        bitmap.recycle();
      } catch(Exception e) {}

      bitmap = null;
    }

    drawable = null;
  }

  public static Drawable reloadDrawable(int resID, Drawable currentDrawable) {
    Activity a = AppContext.getContext().getActivity();
    Drawable d = null;

    try {
      d = a.getResources().getDrawable(resID);
    } catch(Throwable ignore) {}

    if ((d == null) && (AppContext.getRootActivity() != a)) {
      d = AppContext.getRootActivity().getResources().getDrawable(resID);
    }

    return (d == null)
           ? currentDrawable
           : d;
  }

  public void scale(int width, int height) {
    if ((bitmap != null) &&!isninePatch) {
      UIImage img = ImageHelper.scaleImage(this, width, height);

      if (img != this) {
        this.bitmap = img.bitmap;
      }
    }
  }

  /**
   * @param drawable the drawable to set
   */
  public void setDrawable(Drawable drawable, int resID) {
    this.drawable = drawable;

    if (drawable instanceof BitmapDrawable) {
      this.bitmap = ((BitmapDrawable) drawable).getBitmap();
    } else {
      this.bitmap = null;
    }

    this.resID = resID;

    if ((resID > 0) && (drawable != null) && (drawable.getChangingConfigurations() != 0)) {
      lastConfiguration = AppContext.getContext().getConfiguration();
    }
  }

  public void setBitmap(Bitmap bitmap) {
    this.bitmap = bitmap;
    drawable    = null;
  }

  public void setPixel(int x, int y, int value) {
    checkMutability();
    bitmap.setPixel(x, y, value);
  }

  public void setPixels(int[] pixels, int x, int y, int width, int height) {
    checkMutability();
    bitmap.setPixels(pixels, 0, width, x, y, width, height);
  }

  @Override
  public void setSlice(int x, int y, int width, int height) {
    checkForBitmap();
    bitmap = Bitmap.createBitmap(getBitmap(), x, y, width, height);
  }

  /**
   * @return the bitmap
   */
  public Bitmap getBitmap() {
    return bitmap;
  }

  public Drawable getDrawable() {
    if (drawable == null) {
      if (bitmap != null) {
        drawable = new BitmapDrawable(Platform.getAppContext().getActivity().getResources(), bitmap);
      }
    } else if ((lastConfiguration != null) && (AppContext.getContext().getConfiguration() != lastConfiguration)) {
      drawable = reloadDrawable(resID, drawable);

      if (drawable instanceof BitmapDrawable) {
        bitmap = ((BitmapDrawable) drawable).getBitmap();
      }
    }

    return drawable;
  }

  public int getHeight() {
    return (bitmap == null)
           ? (drawable == null)
             ? -1
             : drawable.getIntrinsicHeight()
           : bitmap.getHeight();
  }

  public String getLocation() {
    return location;
  }

  public NinePatch getNinePatch(boolean force) {
    if ((ninePatch == null) && (force || isNinePatch())) {
      ninePatch = new AndroidNinePatch(this);
    }

    return ninePatch;
  }

  public int getPixel(int x, int y) {
    checkForBitmap();

    return bitmap.getPixel(x, y);
  }

  public int[] getPixels(int[] pixels, int x, int y, int width, int height) {
    checkForBitmap();

    if (pixels == null) {
      pixels = new int[width * height];
    } else if (pixels.length < width * height) {
      throw new IllegalArgumentException("Pixels array must have a length >= w * h");
    }

    if (bitmap != null) {
      if (!bitmap.isMutable()) {
        Bitmap bmp = bitmap.copy(bitmap.getConfig(), true);

        bitmap.recycle();
        bitmap = bmp;
      }

      bitmap.getPixels(pixels, 0, width, x, y, width, height);

      return pixels;
    }

    if (drawable != null) {
      throw new IllegalArgumentException("Cannot get prixels from a " + drawable.getClass().getName());
    }

    throw new UnsupportedOperationException("No pixel data available");
  }

  public UIImage getSlice(int x, int y, int w, int h) {
    checkForBitmap();

    return new UIImage(Bitmap.createBitmap(getBitmap(), x, y, w, h));
  }

  public UIImage getSubimage(int x, int y, int w, int h) {
    checkForBitmap();

    return new UIImage(Bitmap.createBitmap(getBitmap(), x, y, w, h));
  }

  public int getWidth() {
    return (bitmap == null)
           ? (drawable == null)
             ? -1
             : drawable.getIntrinsicWidth()
           : bitmap.getWidth();
  }

  @Override
  protected boolean isLoadedEx(boolean hasObserver) {
    return (bitmap != null) || (drawable != null);
  }

  @Override
  protected boolean isNinePatchEx() {
    if ((bitmap != null) && super.isNinePatchEx()) {
      drawable = ImageHelper.createNinePatchDrawable(AppContext.getAndroidContext(), bitmap, new Rect(0, 0, 0, 0));

      return true;
    }

    return drawable instanceof NinePatchDrawable;
  }

  private void checkForBitmap() {
    if (bitmap == null) {
      throw new UnsupportedOperationException("Image not backed by a bitmap");
    }
  }

  private void checkMutability() {
    if (bitmap != null) {
      if (!bitmap.isMutable()) {
        Bitmap bmp = bitmap.copy(bitmap.getConfig(), true);

        bitmap.recycle();
        bitmap = bmp;
      }
    } else {
      if (drawable != null) {
        throw new IllegalArgumentException("Cannot modify an imaged backe by a " + drawable.getClass().getName());
      }

      throw new UnsupportedOperationException("Image not backed by a bitmap");
    }
  }

  static class AndroidNinePatch extends NinePatch {
    public AndroidNinePatch(aUIImage image) {
      mImage = image;
    }

    public AndroidNinePatch(aUIImage image, UIColor newColor, UIColor oldColor) {
      super(image, newColor, oldColor);
    }

    public void changeNinePatchColor(UIColor newColor, UIColor oldColor) {
      if (((UIImage) mImage).getDrawable() instanceof NinePatchDrawable) {
        return;
      }

      super.changeNinePatchColor(newColor, oldColor);
    }

    public void draw(iPlatformGraphics g, float x, float y, float scaledWidth, float scaledHeight) {
      if (((UIImage) mImage).getDrawable() instanceof NinePatchDrawable) {
        Drawable d = ((UIImage) mImage).getDrawable();

        d.setBounds((int) x, (int) y, (int) (x + scaledWidth - 1), (int) (y + scaledHeight - 1));
        d.draw(g.getCanvas());

        return;
      }

      super.draw(g, x, y, scaledWidth, scaledHeight);
    }
  }
}
