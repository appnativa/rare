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

import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.iPlatformPath;
import com.appnativa.util.SNumber;

/**
 * A line border that supports non rectangular lines
 *
 * @author Don DeCoteau
 */
public class UILineBorder extends aUILineBorder {
  public UILineBorder(UIColor color) {
    super(color);
  }

  public UILineBorder(UIColor color, float thickness) {
    super(color, thickness);
  }

  public UILineBorder(UIColor color, float thickness, boolean roundedCorners) {
    super(color, thickness, roundedCorners);
  }

  public UILineBorder(UIColor color, float thickness, float arc) {
    super(color, thickness, arc);
  }

  public UILineBorder(UIColor color, float thickness, float arcWidth, float arcHeight) {
    super(color, thickness, arcWidth, arcHeight);
  }

  @Override
  protected float getClipingOffset() {
    return 0;
  }

  @Override
  public boolean usesPath() {
    return !has2MissingSides;
  }

  @Override
  public iPlatformPath getPath(iPlatformPath p, float x, float y, float width, float height, boolean forClip) {
    if (forClip &&!has2MissingSides) {
      boolean nb = noBottom;
      boolean nt = noTop;
      boolean nr = noRight;
      boolean nl = noLeft;

      noBottom = false;
      noTop    = false;
      noLeft   = false;
      noRight  = false;

      iPlatformPath path = super.getPath(p, x, y, width, height, false);

      noBottom = nb;
      noTop    = nt;
      noLeft   = nl;
      noRight  = nr;

      return path;
    }

    return super.getPath(p, x, y, width, height, forClip);
  }

  @Override
  public boolean canUseMainLayer() {
    if (roundedCorners && (flatBottom || flatTop || flatRight || flatLeft)) {
      return false;
    }

    if (!SNumber.isEqual(arcHeight, arcWidth)) {
      return false;
    }

    if (!"solid".equalsIgnoreCase(lineStyle)) {
      return false;
    }

    return !noBottom &&!noTop &&!noLeft &&!noRight;
  }

  public float getPathWidth() {
    return thickness;
  }
}
