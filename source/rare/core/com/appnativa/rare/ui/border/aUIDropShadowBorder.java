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
import com.appnativa.rare.exception.ApplicationException;
import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIImage;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.UIPoint;
import com.appnativa.rare.ui.Utils;
import com.appnativa.rare.ui.iImageObserver;
import com.appnativa.rare.ui.iPlatformGraphics;
import com.appnativa.rare.ui.painter.NinePatch;

import java.io.IOException;

import java.util.Map;

public abstract class aUIDropShadowBorder extends aUIPlatformBorder implements iImageObserver {
  protected static NinePatch[] dropShadowNinePatch = new NinePatch[3];
  protected static NinePatch[] shadowNinePatch     = new NinePatch[3];
  protected final float        cornerSize;
  protected boolean            loaded;
  protected NinePatch          ninePatch;
  protected final UIColor      shadowColor;
  protected final float        shadowOpacity;
  protected final float        shadowSize;
  protected final boolean      showBottomShadow;
  protected final boolean      showLeftShadow;
  protected final boolean      showRightShadow;
  protected final boolean      showTopShadow;
  protected String             imageName;
  private UIPoint              paintOffset;

  public aUIDropShadowBorder() {
    this(getDefaultShadowColor(), ScreenUtils.PLATFORM_PIXELS_5);
  }

  public aUIDropShadowBorder(boolean showLeftShadow) {
    this(getDefaultShadowColor(), ScreenUtils.PLATFORM_PIXELS_5, .5f,
         ScreenUtils.PLATFORM_PIXELS_6 + ScreenUtils.PLATFORM_PIXELS_6, showLeftShadow, showLeftShadow, true, true);
  }

  public aUIDropShadowBorder(UIColor shadowColor, float shadowSize) {
    this(shadowColor, shadowSize, .5f, ScreenUtils.PLATFORM_PIXELS_6 + ScreenUtils.PLATFORM_PIXELS_6, false, false,
         true, true);
  }

  public aUIDropShadowBorder(UIColor shadowColor, float shadowSize, float shadowOpacity, float cornerSize,
                             boolean showTopShadow, boolean showLeftShadow, boolean showBottomShadow,
                             boolean showRightShadow) {
    this.shadowColor      = shadowColor;
    this.shadowSize       = shadowSize;
    this.shadowOpacity    = shadowOpacity;
    this.cornerSize       = cornerSize;
    this.showTopShadow    = showTopShadow;
    this.showLeftShadow   = showLeftShadow;
    this.showBottomShadow = showBottomShadow;
    this.showRightShadow  = showRightShadow;

    try {
      ninePatch = getNinePatch();
      loaded    = ninePatch.isLoaded(this);
    } catch(IOException e) {
      throw new ApplicationException(e);
    }
  }

  @Override
  public void imageLoaded(UIImage image) {
    loaded = true;
  }

  @Override
  public void paint(iPlatformGraphics g, float x, float y, float width, float height, boolean end) {
    if (end) {
      return;
    }

    if (loaded) {
      if (paintOffset != null) {
        x      += paintOffset.x;
        y      += paintOffset.y;
        width  -= (paintOffset.x * 2);
        height -= (paintOffset.y * 2);
      }

      ninePatch.draw(g, x, y, width, height);
    }
  }

  @Override
  public void setPadForArc(boolean pad) {}

  @Override
  public float getArcHeight() {
    return 0;
  }

  @Override
  public float getArcWidth() {
    return 0;
  }

  @Override
  public UIInsets getBorderInsets(UIInsets insets) {
    float top    = showTopShadow
                   ? shadowSize
                   : 0;
    float left   = showLeftShadow
                   ? shadowSize
                   : 0;
    float bottom = showBottomShadow
                   ? shadowSize
                   : 0;
    float right  = showRightShadow
                   ? shadowSize
                   : 0;

    if (insets == null) {
      insets = new UIInsets();
    }

    insets.set(top, right, bottom, left);

    return insets;
  }

  public float getCornerSize() {
    return cornerSize;
  }

  public static UIColor getDefaultShadowColor() {
    UIColor c = Platform.getUIDefaults().getColor("Rare.ShadowBorder.color");

    if (c == null) {
      c = UIColor.BLACK;
    }

    return c;
  }

  public UIColor getShadowColor() {
    return shadowColor;
  }

  public float getShadowOpacity() {
    return shadowOpacity;
  }

  public float getShadowSize() {
    return shadowSize;
  }

  @Override
  public boolean isPadForArc() {
    return false;
  }

  @Override
  public boolean isPaintLast() {
    return false;
  }

  public boolean isShowBottomShadow() {
    return showBottomShadow;
  }

  public boolean isShowLeftShadow() {
    return showLeftShadow;
  }

  public boolean isShowRightShadow() {
    return showRightShadow;
  }

  public boolean isShowTopShadow() {
    return showTopShadow;
  }

  @Override
  public void handleCustomProperties(Map map) {
    super.handleCustomProperties(map);
    paintOffset = Utils.getPoint((String) map.get("offset"));
    imageName   = (String) map.get("image");
  }

  protected UIImage getImage(String name) {
    return Platform.getAppContext().getResourceAsImage(name);
  }

  protected NinePatch getNinePatch() throws IOException {
    if (imageName != null) {
      UIImage   img = getImage(imageName);
      NinePatch np  = img.getNinePatch(true);
      UIColor   d   = getDefaultShadowColor();

      if (shadowColor.getRGB() != d.getRGB()) {
        np.changeNinePatchColor(shadowColor, d);
      }

      return np;
    }

    String s;
    int    pos = 0;

    if (showLeftShadow || showTopShadow) {
      if (shadowSize > ScreenUtils.PLATFORM_PIXELS_7) {
        s   = "Rare.icon.shadow14";
        pos = 2;
      } else if (shadowSize > ScreenUtils.PLATFORM_PIXELS_5) {
        s   = "Rare.icon.shadow7";
        pos = 1;
      } else {
        s   = "Rare.icon.shadow";
        pos = 0;
      }

      UIColor d = getDefaultShadowColor();

      if (shadowColor.getRGB() != d.getRGB()) {
        UIImage   img = getImage(s);
        NinePatch np  = img.getNinePatch(true);

        np.changeNinePatchColor(shadowColor, d);

        return np;
      } else {
        if (shadowNinePatch[pos] == null) {
          UIImage img = getImage(s);

          shadowNinePatch[pos] = img.getNinePatch(true);
        }

        return shadowNinePatch[pos];
      }
    } else {
      if (shadowSize > ScreenUtils.PLATFORM_PIXELS_7) {
        s   = "Rare.icon.drop_shadow14";
        pos = 2;
      } else if (shadowSize > ScreenUtils.PLATFORM_PIXELS_5) {
        s   = "Rare.icon.drop_shadow7";
        pos = 1;
      } else {
        s   = "Rare.icon.drop_shadow";
        pos = 0;
      }

      UIColor d = getDefaultShadowColor();

      if (shadowColor.getRGB() != d.getRGB()) {
        UIImage   img = getImage(s);
        NinePatch np  = img.getNinePatch(true);

        np.changeNinePatchColor(shadowColor, d);

        return np;
      } else {
        if (dropShadowNinePatch[pos] == null) {
          UIImage img = getImage(s);

          dropShadowNinePatch[pos] = img.getNinePatch(true);
        }

        return dropShadowNinePatch[pos];
      }
    }
  }

  public UIPoint getPaintOffset() {
    return paintOffset;
  }

  public void setPaintOffset(UIPoint paintOffset) {
    this.paintOffset = paintOffset;
  }
}
