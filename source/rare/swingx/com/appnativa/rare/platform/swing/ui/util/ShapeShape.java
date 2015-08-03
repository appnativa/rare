/*
 * @(#)ShapShape.java
 * 
 * Copyright (c) SparseWare. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.platform.swing.ui.util;

import java.awt.Shape;
import java.awt.geom.Rectangle2D;

import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.iPlatformShape;

public class ShapeShape implements iPlatformShape {
  Shape shape;

  public ShapeShape(Shape shape) {
    super();
    this.shape = shape;
  }

  @Override
  public UIRectangle getBounds() {
    Rectangle2D r = shape.getBounds2D();

    return new UIRectangle((float) r.getX(), (float) r.getY(), (float) r.getWidth(), (float) r.getHeight());
  }

  @Override
  public Rectangle2D getRectangle() {
    return shape.getBounds2D();
  }

  @Override
  public Shape getShape() {
    return shape;
  }
}
