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

import com.appnativa.rare.platform.swing.ui.util.ImageHelper;
import com.appnativa.rare.platform.swing.ui.util.ImageUtils;
import com.appnativa.rare.platform.swing.ui.util.SwingGraphics;
import com.appnativa.rare.ui.painter.iImagePainter.ScalingType;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public class UIImage extends aUIImage implements Cloneable {
  protected Image awtImage;

  public UIImage(Image image) {
    this.awtImage = image;
  }

  public UIImage(Image image, String location) {
    this.awtImage = image;
    this.location = location;
  }

  public UIImage(int width, int height) {
    this(ImageUtils.createCompatibleImage(width, height));
  }

  @Override
  public Object clone() {
    UIImage       img = (UIImage) super.clone();
    BufferedImage bi  = getBufferedImage();

    img.awtImage = ImageUtils.copyImage(bi);

    return img;
  }

  protected UIImage() {}

  @Override
  public void addReflectionImage(int y, int height, float opacity, int gap) {
    if (getHeight() < y + gap + height) {
      throw new IllegalArgumentException(
          "Image not tall enough to support adding a reflection with the specified dimensions.\nMight want to use createReflection");
    }
    ImageUtils.addReflection(getBufferedImage(), y, height, opacity, gap);
  }

  @Override
  public void blurImage() {
    awtImage = ImageUtils.blurImage(getBufferedImage(), null);
  }

  @Override
  public void contstrain(int width, int height, int constraints, UIColor bg, ScalingType st) {}

  @Override
  public UIImage createDisabledImage() {
    return new UIImage(ImageUtils.createDisabledImage(getBufferedImage()));
  }

  public iPlatformGraphics createGraphics() {
    return new SwingGraphics(getBufferedImage().createGraphics());
  }

  @Override
  public aUIImage createCopyWithReflection(int height, float opacity, int gap) {
    return new UIImage(ImageUtils.createCopyWithReflection(getBufferedImage(), height, opacity, gap));
  }

  @Override
  public UIImage createReflection(int height, float opacity, int gap) {
    if (height == -1) {
      return new UIImage(ImageUtils.createReflection(getBufferedImage(), opacity, gap));
    } else {
      BufferedImage bi = getBufferedImage();

      return new UIImage(ImageUtils.createSubReflection(bi, bi.getHeight() - height, height, opacity, gap,true));
    }
  }

  @Override
  public void dispose() {
    awtImage = null;
  }

  public void ensureImageLoaded() {
    awtImage = ImageHelper.ensureImageLoaded(awtImage);
  }

  @Override
  public void scale(int width, int height) {
    ScalingType st = scalingType;

    if (st == null) {
      st = ScalingType.BILINEAR;
    }

    awtImage = ImageUtils.getScaledInstance(getBufferedImage(), width, height, st);
  }

  public void setImage(Image image) {
    this.awtImage = image;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  @Override
  public void setPixel(int x, int y, int value) {
    ImageUtils.setPixel(getBufferedImage(), x, y, value);
  }

  @Override
  public void setPixels(int[] pixels, int x, int y, int width, int height) {
    ImageUtils.setPixels(getBufferedImage(), x, y, width, height, pixels);
  }

  @Override
  public void setScalingType(ScalingType scalingType) {
    this.scalingType = scalingType;
  }

  @Override
  public void setSlice(int x, int y, int width, int height) {
    awtImage = getBufferedImage().getSubimage(x, y, width, height);
  }

  @Override
  public BufferedImage getBufferedImage() {
    getRealImage();

    return (BufferedImage) awtImage;
  }

  @Override
  public int getHeight() {
    return (awtImage == null)
           ? 0
           : awtImage.getHeight(null);
  }

  @Override
  public Image getImage() {
    return awtImage;
  }

  public static Image getImage(UIImage image) {
    return (image == null)
           ? null
           : image.getImage();
  }

  @Override
  public void load() throws Exception {
  }

  @Override
  public String getLocation() {
    return location;
  }

  @Override
  public int getPixel(int x, int y) {
    return ImageUtils.getPixel(getBufferedImage(), x, y);
  }

  @Override
  public int[] getPixels(int[] pixels, int x, int y, int width, int height) {
    return ImageUtils.getPixels(getBufferedImage(), x, y, width, height, pixels);
  }

  public Object getPlatformObject() {
    return awtImage;
  }

  public Object getProperty(String name, ImageObserver observer) {
    return (awtImage == null)
           ? null
           : awtImage.getProperty(name, observer);
  }

  public UIImage getRealImage() {
    return this;
  }

  @Override
  public UIImage getSlice(int x, int y, int width, int height) {
    return new UIImage(getBufferedImage().getSubimage(x, y, width, height));
  }

  @Override
  public UIImage getSubimage(int x, int y, int w, int h) {
    if (awtImage == null) {
      return null;
    }

    int iw = getWidth();
    int ih = getHeight();

    if ((x + w > iw) || (y + h > ih)) {
      return null;
    }

    BufferedImage img = (awtImage instanceof BufferedImage)
                        ? (BufferedImage) awtImage
                        : ImageUtils.copyImage(awtImage);

    return new UIImage(img.getSubimage(x, y, w, h));
  }

  @Override
  public int getWidth() {
    return (awtImage == null)
           ? 0
           : awtImage.getWidth(null);
  }

  public boolean isBufferedImage() {
    return awtImage instanceof BufferedImage;
  }

  @Override
  protected boolean isLoadedEx(boolean hasObserver) {
    return awtImage != null;
  }
}
