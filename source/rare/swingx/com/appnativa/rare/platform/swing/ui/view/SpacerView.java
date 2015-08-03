/*
 * @(#)SpacerView.java
 * 
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.platform.swing.ui.view;

import java.awt.Dimension;
import java.awt.geom.AffineTransform;

import javax.swing.JComponent;

import com.appnativa.rare.ui.UIDimension;

public class SpacerView extends JComponent implements iView {
  protected int height;
  protected int width;

  public SpacerView() {
    super();
  }

  public SpacerView(int width, int height) {
    super();
    this.width  = width;
    this.height = height;
  }

  @Override
  public void setTransformEx(AffineTransform tx) {}

  @Override
  public Dimension getMinimumSize() {
    return new Dimension(0, 0);
  }

  @Override
  public void getMinimumSize(UIDimension size) {
    size.width  = (width > -1)
                  ? width
                  : 0;
    size.height = (height > -1)
                  ? height
                  : 0;
  }

  @Override
  public Dimension getPreferredSize() {
    int w = (width > -1)
            ? width
            : 0;
    int h = (height > -1)
            ? height
            : 0;

    return new Dimension(w, h);
  }

  @Override
  public void getPreferredSize(UIDimension size, int maxWidth) {
    size.width  = (width > -1)
                  ? width
                  : Short.MAX_VALUE;
    size.height = (height > -1)
                  ? height
                  : Short.MIN_VALUE;
  }

  @Override
  public AffineTransform getTransformEx() {
    return null;
  }
}
