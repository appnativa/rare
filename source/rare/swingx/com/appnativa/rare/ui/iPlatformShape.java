/*
 * @(#)iPlatformShape.java
 * 
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.ui;

import java.awt.Shape;
import java.awt.geom.Rectangle2D;

public interface iPlatformShape extends iShape{
  Rectangle2D getRectangle();
  Shape getShape();
}
