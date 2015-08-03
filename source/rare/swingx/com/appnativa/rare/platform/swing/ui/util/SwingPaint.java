/*
 * @(#)SwingPaint.java
 * 
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.platform.swing.ui.util;

import java.awt.Paint;

import com.appnativa.rare.ui.iPlatformPaint;

public class SwingPaint implements iPlatformPaint{
  private Paint paint;

  public SwingPaint(Paint paint) {
    this.paint = paint;
  }

  public void setPaint(Paint paint) {
    this.paint = paint;
  }

  @Override
  public Paint getPaint() {
    return paint;
  }
}
