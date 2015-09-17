/*
 * Copyright appNativa Inc. All Rights Reserved.
 *
 * This file is part of the Real-time Application Rendering Engine (RARE).
 *
 * RARE is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */

package com.appnativa.rare.ui;

import com.appnativa.rare.ui.painter.PaintBucket;

import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.TransferHandler;

public interface iPlatformComponent extends iComponent {
  static final String RARE_SWING_WIDTH_FIXED_VALUE  = "__RARE_SWING_WIDTH_FIXED_VALUE__";
  static final String RARE_SWING_HEIGHT_FIXED_VALUE = "__RARE_SWING_HEIGHT_FIXED_VALUE__";

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
