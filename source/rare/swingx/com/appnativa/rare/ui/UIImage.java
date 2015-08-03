/*
 * @(#)UIImage.java   2011-11-12
 *
 * Copyright (c) 2007-2009 appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.ui;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import com.appnativa.rare.platform.swing.ui.util.ImageHelper;
import com.appnativa.rare.platform.swing.ui.util.ImageUtils;
import com.appnativa.rare.platform.swing.ui.util.SwingGraphics;
import com.appnativa.rare.ui.painter.iImagePainter.ScalingType;

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

  protected UIImage() {}

  @Override
  public void addReflectionImage(int y, int height, float opacity, int gap) {
    awtImage = ImageUtils.addReflection(getBufferedImage(), y, height, opacity, gap);
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
  public UIImage createReflectionImage(int y, int height, float opacity, int gap) {
    return new UIImage(ImageUtils.createReflection(getBufferedImage(), y, height, opacity, gap));
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