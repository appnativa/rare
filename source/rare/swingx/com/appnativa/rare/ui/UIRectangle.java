/*
 * @(#)UIRectangle.java   2011-02-26
 *
 * Copyright (c) 2007-2009 appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.ui;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;

public class UIRectangle extends aUIRectangle implements iPlatformShape {
  public UIRectangle() {
    super();
  }

  public UIRectangle(Rectangle r) {
    super(r.x, r.y, r.width, r.height);
  }

  public UIRectangle(UIDimension d) {
    super(d);
  }

  public UIRectangle(UIPoint p) {
    super(p);
  }

  public UIRectangle(UIRectangle r) {
    super(r);
  }

  public UIRectangle(float width, float height) {
    super(width, height);
  }

  public UIRectangle(UIPoint p, UIDimension d) {
    super(p, d);
  }

  public UIRectangle(float x, float y, float width, float height) {
    super(x, y, width, height);
  }

  @Override
  public Rectangle2D getRectangle() {
    return new Rectangle2D.Float(x, y, width, height);
  }

  @Override
  public Shape getShape() {
    return getRectangle();
  }
}
