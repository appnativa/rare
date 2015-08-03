/*
 * @(#)ImagePainter.java   2010-09-19
 *
 * Copyright (c) 2007-2009 appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.ui.painter;

import java.awt.Paint;
import java.awt.TexturePaint;
import java.awt.geom.Rectangle2D;

import com.appnativa.rare.ui.Displayed;
import com.appnativa.rare.ui.RenderType;
import com.appnativa.rare.ui.UIImage;

/**
 * A class that handles painting images
 *
 * @author Don DeCoteau
 */
public class UIImagePainter extends aUIImagePainter {
  Paint paint;
  int   paintModCount;

  public UIImagePainter() {
    super();
  }

  public UIImagePainter(UIImage image) {
    super(image);
  }

  public UIImagePainter(UIImage image, int opacity, RenderType type) {
    super(image, opacity, type);
  }

  public UIImagePainter(UIImage image, int opacity, RenderType type, Displayed displayed) {
    super(image, opacity, type, displayed);
  }

  @Override
  public void setRenderType(RenderType type) {
    super.setRenderType(type);
    paint = null;
  }

  public Paint getPaintEx(float x, float y, float width, float height) {
    if (canBecomePaint() && (theImage != null) && theImage.isLoaded()) {
      if ((paint == null) || (paintModCount != modCount)) {
        paintModCount = modCount;

        Rectangle2D anchor = new Rectangle2D.Double(0, 0, theImage.getWidth(), theImage.getHeight());

        paint = new TexturePaint(getImage().getBufferedImage(), anchor);
      }
    }

    return paint;
  }

  protected boolean canBecomePaint() {
    return (renderType == RenderType.TILED) && (composite.getAlpha() == 255) &&!isNinePatch && (displayed == Displayed.ALWAYS);
  }

  @Override
  public Paint getPaint() {
    return null;
  }
}
