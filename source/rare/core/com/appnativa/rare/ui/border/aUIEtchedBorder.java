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

package com.appnativa.rare.ui.border;

import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.UIStroke;
import com.appnativa.rare.ui.iPlatformGraphics;

/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

/**
 * @author Alexander T. Simbirtsev
 * @version $Revision$
 */
public abstract class aUIEtchedBorder extends aUIPlatformBorder {
  public static final int    LOWERED     = 1;
  public static final int    RAISED      = 0;
  private static final float INSETS_SIZE = ScreenUtils.PLATFORM_PIXELS_2;
  protected int              etchType    = LOWERED;;
  protected UIColor          highlight;
  protected UIColor          shadow;

  public aUIEtchedBorder() {}

  public aUIEtchedBorder(final int etchType) {
    if ((etchType != RAISED) && (etchType != LOWERED)) {
      throw new IllegalArgumentException();
    }

    this.etchType = etchType;
  }

  public aUIEtchedBorder(final UIColor highlightColor, final UIColor shadowColor) {
    highlight = highlightColor;
    shadow    = shadowColor;
  }

  public aUIEtchedBorder(final int etchType, final UIColor highlightColor, final UIColor shadowColor) {
    if ((etchType != RAISED) && (etchType != LOWERED)) {
      throw new IllegalArgumentException();
    }

    this.etchType = etchType;
    highlight     = highlightColor;
    shadow        = shadowColor;
  }

  @Override
  public void paint(iPlatformGraphics g, float x, float y, float width, float height, boolean last) {
    if (last == isPaintLast()) {
      UIStroke stroke = g.getStroke();
      UIColor  c      = g.getColor();

      g.setStroke(UIStroke.SOLID_STROKE);
      g.translate(x, y);

      float   p1    = ScreenUtils.PLATFORM_PIXELS_1;
      UIColor color = (etchType == UIEtchedBorder.LOWERED)
                      ? getShadowColor()
                      : getHighlightColor();

      g.setColor(color);
      g.drawRect(0, 0, width - 1, height - 1);
      color = (etchType == UIEtchedBorder.LOWERED)
              ? getHighlightColor()
              : getShadowColor();
      g.setColor(color);
      g.drawLine(p1, height - 2, p1, p1);
      g.drawLine(p1, p1, width - p1, p1);
      g.drawLine(0, height, width, height);
      g.drawLine(width, height, width, 0);
      g.translate(-x, -y);
      g.setStroke(stroke);
      g.setColor(c);
    }
  }

  @Override
  public UIInsets getBorderInsets(final UIInsets insets) {
    if (insets == null) {
      return new UIInsets(INSETS_SIZE, INSETS_SIZE, INSETS_SIZE, INSETS_SIZE);
    }

    insets.left   = INSETS_SIZE;
    insets.top    = INSETS_SIZE;
    insets.bottom = INSETS_SIZE;
    insets.right  = INSETS_SIZE;

    return insets;
  }

  public int getEtchType() {
    return etchType;
  }

  public UIColor getHighlightColor() {
    return highlight;
  }

  public UIColor getShadowColor() {
    return shadow;
  }
}
