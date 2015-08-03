/*
 * @(#)RectangleShape.java
 * 
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.platform.swing.ui.util;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;

import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIPoint;
import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.iPlatformShape;

public class RectangleShape extends UIRectangle implements iPlatformShape {
  Rectangle2D shape;

  public RectangleShape() {}

  public RectangleShape(Rectangle r) {
    super(r);
  }

  public RectangleShape(UIDimension d) {
    super(d);
  }

  public RectangleShape(UIPoint p) {
    super(p);
  }

  public RectangleShape(UIRectangle r) {
    super(r);
  }

  public RectangleShape(float width, float height) {
    super(width, height);
  }

  public RectangleShape(UIPoint p, UIDimension d) {
    super(p, d);
  }

  public RectangleShape(float x, float y, float width, float height) {
    super(x, y, width, height);
  }

  @Override
  public UIRectangle getBounds() {
    return this;
  }

  @Override
  public Rectangle2D getRectangle() {
    if (shape == null) {
      shape = new Rectangle2D.Float(x, y, width, height);
    }

    return shape;
  }

  @Override
  public Shape getShape() {
    return getRectangle();
  }
}
