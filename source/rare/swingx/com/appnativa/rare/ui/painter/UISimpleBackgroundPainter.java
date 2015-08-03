/*
 * @(#)SimpleBackgroundPainter.java   2011-03-02
 *
 * Copyright (c) 2007-2009 appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.ui.painter;

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Paint;

import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.iPlatformGraphics;

/**
 *
 * @author Don DeCoteau
 */
public class UISimpleBackgroundPainter extends aUIPlatformPainter implements iBackgroundPainter {
  protected UIColor                             backgroundColor;
  public static final UISimpleBackgroundPainter NULL_BGPAINTER = new UISimpleBackgroundPainter(UIColor.TRANSPARENT);

  public UISimpleBackgroundPainter() {
    setBackgroundColor(ColorUtils.NULL_COLOR);
  }

  public UISimpleBackgroundPainter(UIColor color) {
    setBackgroundColor(color);
  }

  @Override
  public UIColor getBackgroundColor() {
    return backgroundColor;
  }

  @Override
  public Paint getPaintEx(float width, float height) {
    return backgroundColor;
  }

  @Override
  public boolean isSingleColorPainter() {
    return true;
  }
  
  @Override
  public void paint(Component c, Graphics2D g, int x, int y, int width, int height, boolean hasValue, int orientation) {
    if(backgroundColor==ColorUtils.NULL_COLOR) {
      return;
    }
    g.setColor(backgroundColor);
    g.fillRect(x, y, width, height);
  }
  
  @Override
  public void paint(iPlatformGraphics g, float x, float y, float width, float height, int orientation) {
    if(backgroundColor==ColorUtils.NULL_COLOR) {
      return;
    }
    g.setColor(backgroundColor);
    g.fillRect(x, y, width, height);
  }

  public void setBackgroundColor(UIColor bg) {
    if(bg==null) {
      bg=ColorUtils.NULL_COLOR;
    }
    backgroundColor = bg;
  }
}
