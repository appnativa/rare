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

package com.appnativa.rare.platform.apple.ui.view;

import com.appnativa.rare.platform.apple.ui.util.AppleGraphics;
import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.PainterUtils;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformPath;
/*-[
#import "RAREUIControl.h"
]-*/

public class MenuButtonView extends CustomButtonView {
  static iPlatformPath arrow;

  public MenuButtonView() {
    this(createProxy());
    setPaintHandlerEnabled(true);
  }

  public MenuButtonView(Object uiview) {
    super(uiview);
    setTextAlignment(RenderableDataItem.HorizontalAlign.LEADING, RenderableDataItem.VerticalAlign.CENTER);
    setMargin(2, 4, 2, 4);
    setCenteredIconOffset(-ScreenUtils.PLATFORM_PIXELS_8);
  }

  @Override
  public void getPreferredSize(UIDimension size, float maxWidth) {
    super.getPreferredSize(size, maxWidth);
    size.width += ScreenUtils.PLATFORM_PIXELS_8;
  }

  @Override
  public void paintOverlay(AppleGraphics g, View v, UIRectangle rect) {
    super.paintOverlay(g, v, rect);

    if (arrow == null) {
      // arrows are drawn have the size of the specified area an centered so
      // take that into account
      arrow = PainterUtils.drawArrow(arrow, ScreenUtils.PLATFORM_PIXELS_16, ScreenUtils.PLATFORM_PIXELS_16, true);
    }

    float              x  = rect.x + rect.width - ScreenUtils.PLATFORM_PIXELS_16;
    iPlatformComponent c  = g.getComponent();
    UIColor            fg = enabled
                            ? ColorUtils.getForeground()
                            : ColorUtils.getDisabledForeground();

    if ((c != null) && c.isForegroundSet()) {
      fg = c.getForeground();
    }

    g.setColor(fg);
    g.fillShape(arrow, x, rect.y + (rect.height / 2) - ScreenUtils.PLATFORM_PIXELS_8);
  }

  private native void setCenteredIconOffset(float offset)
  /*-[
   [((RAREUIControl*)proxy_) setCenteredIconOffset: offset];
   ]-*/
  ;
}
