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

import com.appnativa.rare.Platform;
import com.appnativa.rare.ui.BorderUtils;
import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.UIScreen;
import com.appnativa.rare.ui.UIStroke;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformGraphics;
import com.appnativa.rare.ui.iPlatformPath;

public abstract class aUIBalloonBorder extends UILineBorder {
  protected float        peakSize     = ScreenUtils.PLATFORM_PIXELS_5;
  protected float        origPeakSize = ScreenUtils.PLATFORM_PIXELS_5;
  protected PeakLocation peakLocation = PeakLocation.UL_TOP;
  protected float        peakOffset   = ScreenUtils.PLATFORM_PIXELS_5;
  protected boolean      autoLocatePeak;

  public enum PeakLocation {
    LL_BOTTOM, LL_LEFT, LR_RIGHT, LR_BOTTOM, UL_TOP, UL_LEFT, UR_TOP, UR_RIGHT
  }

  public aUIBalloonBorder(UIColor color) {
    super(color, ScreenUtils.PLATFORM_PIXELS_1, ScreenUtils.PLATFORM_PIXELS_6);
  }

  public aUIBalloonBorder(UIColor color, float thickness) {
    super(color, thickness, ScreenUtils.PLATFORM_PIXELS_6);
  }

  public aUIBalloonBorder(UIColor color, float thickness, float arc) {
    super(color, thickness, arc);
  }

  public aUIBalloonBorder(UIColor color, float thickness, float arc, PeakLocation pl) {
    super(color, thickness, arc);
    peakLocation = pl;
  }

  public aUIBalloonBorder(UIColor color, float thickness, float arc, PeakLocation pl, float peakSize) {
    super(color, thickness, arc);
    peakLocation      = pl;
    this.peakSize     = peakSize;
    this.origPeakSize = peakSize;
    this.peakOffset   = peakSize;
  }

  @Override
  public Object clone() {
    aUIBalloonBorder b = (aUIBalloonBorder) super.clone();

    return b;
  }

  @Override
  public void paint(iPlatformGraphics g, float x, float y, float width, float height, boolean last) {
    if (!last) {
      return;
    }

    UIColor color = getColor();

    if (color == null) {
      return;
    }

    iPlatformPath path   = getPath(paintShape, x, y, width, height, false);
    UIStroke      stroke = g.getStroke();
    UIColor       c      = g.getColor();

    g.setColor(color);

    if (lineStroke != null) {
      g.setStroke(lineStroke);
    } else {
      g.setStrokeWidth(thickness);
    }

    g.drawShape(path, 0, 0);
    g.setStroke(stroke);
    g.setColor(c);
  }

  /**
   * @param peakLocation
   *          the peakLocation to set
   */
  public void setPeakLocation(PeakLocation peakLocation) {
    this.peakLocation = peakLocation;
  }

  /**
   * @param peakOffset
   *          the peakOffset to set
   */
  public void setPeakOffset(float peakOffset) {
    this.peakOffset = peakOffset;
  }

  public void setPeakSize(float peakSize) {
    this.peakSize     = peakSize;
    this.origPeakSize = peakSize;
  }

  @Override
  public UIInsets getBorderInsets(UIInsets insets) {
    if (this.insets != null) {
      insets.left   = this.insets.left;
      insets.right  = this.insets.right;
      insets.top    = this.insets.top;
      insets.bottom = this.insets.bottom;

      return insets;
    }

    int tw = (int) thickness;
    int tt = tw;
    int tb = tw;

    if (padForArc) {
      tw += (arcWidth / 5);
      tt += (arcHeight / 5);
      tb += (arcWidth / 5);
    }

    if (insets == null) {
      insets = new UIInsets(tt, tw, tb, tw);
    } else {
      insets.set(tt, tw, tb, tw);
    }

    return adjustForPeak(insets);
  }

  /**
   * @return the peakLocation
   */
  public PeakLocation getPeakLocation() {
    return peakLocation;
  }

  /**
   * @return the peakOffset
   */
  public float getPeakOffset() {
    return peakOffset;
  }

  public float getPeakSize() {
    return origPeakSize;
  }

  @Override
  public boolean isPaintLast() {
    return true;
  }

  @Override
  public boolean isRectangular() {
    return false;
  }

  protected UIInsets adjustForPeak(UIInsets insets) {
    switch(peakLocation) {
      case UR_RIGHT :
      case LR_RIGHT :
        insets.right += peakSize;

        break;

      case LL_LEFT :
      case UL_LEFT :
        insets.left += peakSize;

        break;

      case UL_TOP :
      case UR_TOP :
        insets.top += peakSize;

        break;

      default :
        insets.bottom += peakSize;

        break;
    }

    return insets;
  }

  @Override
  protected iPlatformPath createBorderPath(iPlatformPath p, float x, float y, float width, float height, float aw,
          float ah, boolean clip) {
    if (p != null) {
      p.reset();
    }

    switch(peakLocation) {
      case LL_BOTTOM :
        p = BorderUtils.createBalloonLLBPath(p, width, height, aw, peakSize, peakOffset);

        break;

      case LL_LEFT :
        p = BorderUtils.createBalloonLLLPath(p, width, height, aw, peakSize, peakOffset);

        break;

      case LR_RIGHT :
        p = BorderUtils.createBalloonLRRPath(p, width, height, aw, peakSize, peakOffset);

        break;

      case UL_LEFT :
        p = BorderUtils.createBalloonULLPath(p, width, height, aw, peakSize, peakOffset);

        break;

      case UL_TOP :
        p = BorderUtils.createBalloonULTPath(p, width, height, aw, peakSize, peakOffset);

        break;

      case UR_RIGHT :
        p = BorderUtils.createBalloonURRPath(p, width, height, aw, peakSize, peakOffset);

        break;

      case UR_TOP :
        p = BorderUtils.createBalloonURTPath(p, width, height, aw, peakSize, peakOffset);

        break;

      default :
        p = BorderUtils.createBalloonLRBPath(p, width, height, aw, peakSize, peakOffset);

        break;
    }

    p.translate(x, y);

    return p;
  }

  @Override
  protected UIColor getColor() {
    if (lineColor == null) {
      UIColor c = Platform.getUIDefaults().getColor("Rare.BalloonBorder.color");

      if (c != null) {
        return c;
      }

      return getDefaultLineColor();
    }

    return lineColor;
  }

  public boolean isAutoLocatePeak() {
    return autoLocatePeak;
  }

  public void autoLocatePeakForProposedBounds(iPlatformComponent owner, UIRectangle r) {
    UIRectangle  ob = owner.getBounds();
    PeakLocation pl = peakLocation;
    float        po = peakOffset;

    peakSize = origPeakSize;

    float midx = ob.x + (ob.width / 2);
    float midy = ob.y + (ob.height / 2);

    if (r.y >= 0) {
      if (r.x >= 0) {
        peakLocation = PeakLocation.UL_TOP;
        peakOffset   = Math.max(UIScreen.snapToPosition(midx - (r.x + ob.x)), ScreenUtils.PLATFORM_PIXELS_2)
                       - (peakSize * 2);
      } else {
        peakLocation = PeakLocation.UR_TOP;
        peakOffset   = Math.max(UIScreen.snapToPosition(r.x + r.width + ob.x - midx) - (peakSize * 2),
                                ScreenUtils.PLATFORM_PIXELS_2);
      }
    } else if (r.y + r.height + ob.height <= 0) {
      if (r.x >= 0) {
        peakLocation = PeakLocation.LL_BOTTOM;
        peakOffset   = Math.max(UIScreen.snapToPosition(midx - (r.x + ob.x)), ScreenUtils.PLATFORM_PIXELS_2)
                       - (peakSize * 2);
      } else {
        peakLocation = PeakLocation.LR_BOTTOM;
        peakOffset   = Math.max(UIScreen.snapToPosition(r.x + r.width + ob.x - midx) - (peakSize * 2),
                                ScreenUtils.PLATFORM_PIXELS_2);
      }
    } else {
      if (r.x >= 0) {
        peakLocation = PeakLocation.LL_LEFT;
      } else {
        peakLocation = PeakLocation.LR_RIGHT;
      }

      peakOffset = Math.max(UIScreen.snapToPosition(r.y + r.height + ob.y + ob.height - midy) - (peakSize * 2),
                            ScreenUtils.PLATFORM_PIXELS_2);
    }

    if ((pl != peakLocation) || (po != peakOffset)) {
      modCount++;
    }
  }

  public void setAutoLocatePeak(boolean autoLocatePeak) {
    this.autoLocatePeak = autoLocatePeak;
  }
}
