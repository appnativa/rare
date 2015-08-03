/*
 * @(#)UITextIcon.java   2008-10-26
 *
 * Copyright (c) appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.ui;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Paint;

import com.appnativa.rare.platform.swing.ui.view.LabelRenderer;
import com.appnativa.rare.ui.painter.aUIPainter;

/**
 * An icon that draws text
 *
 * @author Don DeCoteau
 */
public class UITextIcon extends aUITextIcon {
  public UITextIcon(CharSequence text) {
    super(text);
  }

  public UITextIcon(CharSequence text, UIColor fg) {
    super(text, fg);
  }

  public UITextIcon(CharSequence text, UIColor fg, UIFont font, iPlatformBorder border) {
    super(text, fg, font, border);
  }

  @Override
  public void paint(iPlatformGraphics g, float x, float y, float width, float height) {
    paintIcon(g.getView(), g.getGraphics(), UIScreen.snapToPosition(x), UIScreen.snapToPosition(y));
  }

  public void paint(Component c, Graphics g, int x, int y, int width, int height, boolean hasValue, int orientation) {
    UIRectangle rect = aUIPainter.getRenderLocation(c, renderSpace, renderType, x, y, width, height, size.width,
                           size.height, null);

    g.translate((int) rect.x, (int) rect.y);
    this.paintIcon(c, g, x, y);
    g.translate(-(int) rect.x, -(int) rect.y);
  }

  @Override
  public void paintIcon(Component c, Graphics g, int x, int y) {
    g.translate(x, y);
    label.getView().paint(g);
    g.translate(-x, -y);
  }

  public Paint getPaintEx(float x, float y, float width, float height) {
    return null;
  }

  @Override
  protected iActionComponent createComponent() {
    LabelRenderer r = new LabelRenderer();

    return new ActionComponent(r);
  }
}
