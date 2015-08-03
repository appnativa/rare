/*
 * @(#)iPlatformComponent.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.ui;

import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.TransferHandler;

import com.appnativa.rare.ui.painter.PaintBucket;

public interface iPlatformComponent extends iComponent {
  static final String RARE_SWING_WIDTH_FIXED_VALUE            = "__RARE_SWING_WIDTH_FIXED_VALUE__";
  static final String RARE_SWING_HEIGHT_FIXED_VALUE           = "__RARE_SWING_HEIGHT_FIXED_VALUE__";

  UIImage capture();

  iPlatformComponent copy();

  void graphicsUnwrap(iPlatformGraphics g);

  iPlatformGraphics graphicsWrap(Graphics g);

  @Override
  void repaint();

  void setLocked(boolean lock);

  @Override
  void setScaleIcon(boolean scale, float scaleFactor);

  UIColor getDisabledColor();

  UIInsets getFocusInsets(UIInsets insets);

  PaintBucket getFocusPaint(iPlatformGraphics g, PaintBucket defaultFocusPaint);

 
  TransferHandler getTransferHandler();

  JComponent getView();

  boolean hasBeenFocused();

  boolean hasHadInteraction();

  boolean isLocked();

  boolean isMouseOver();

  boolean isPressed();

  @Override
  boolean isScaleIcon();
}
