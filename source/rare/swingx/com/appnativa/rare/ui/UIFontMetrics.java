/*
 * @(#)UIFontMetrics.java   2012-01-24
 *
 * Copyright (c) 2007-2009 appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.ui;

import java.awt.FontMetrics;

import com.appnativa.rare.Platform;

public class UIFontMetrics {
  FontMetrics    metrics;
  private UIFont font;

  public UIFontMetrics(UIFont font) {
    this.font = font;
    metrics   = Platform.getAppContext().getRootViewer().getContainerComponent().getView().getFontMetrics(font);
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof UIFontMetrics) {
      if (o == this) {
        return true;
      }

      return ((UIFontMetrics) o).font.equals(font);
    }

    return false;
  }

  public int stringWidth(String str) {
    return metrics.stringWidth(str);
  }

  public void setFont(UIFont font) {
    this.font = font;
    metrics   = Platform.getAppContext().getRootViewer().getContainerComponent().getView().getFontMetrics(font);
  }

  public float getAscent() {
    return metrics.getAscent();
  }

  public float getDescent() {
    return metrics.getDescent();
  }

  public UIFont getFont() {
    return font;
  }

  public float getHeight() {
    return metrics.getHeight();
  }

  public float getLeading() {
    return metrics.getLeading();
  }

  public static UIFontMetrics getMetrics(UIFont font) {
    return new UIFontMetrics(font);
  }
}
