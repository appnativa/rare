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

import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.platform.apple.ui.iAppleLayoutManager;
import com.appnativa.rare.ui.Container;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.UIScreen;
import com.appnativa.rare.ui.iPlatformComponent;

public class ButtonPanelView extends ParentView implements iAppleLayoutManager {
  float            maxButtonHeight = -1;
  float            maxButtonWidth  = -1;
  private UIInsets buttonPadding   = new UIInsets(UIScreen.platformPixels(4));

  public ButtonPanelView() {
    super(createAPView());
  }

  public ButtonPanelView(Object nsview) {
    super(nsview);
  }

  @Override
  public void layout(ParentView view, float width, float height) {
    Container container = (Container) component;
    int       len       = container.getComponentCount();

    if (len == 0) {
      return;
    }

    UIInsets in = container.getInsets(null);
    float    x  = 0;
    float    y  = 0;

    if (in != null) {
      width  -= (in.left + in.right);
      height -= (in.top + in.bottom);
      x      = in.left;
      y      = in.top;
    }

    in     = buttonPadding;
    height -= (in.top + in.bottom);

    iPlatformComponent c;
    boolean            fixedSize = ((maxButtonWidth > 0) && (len * maxButtonWidth <= width));
    UIDimension        size      = fixedSize
                                   ? null
                                   : new UIDimension();

    if ((maxButtonHeight > height) || (maxButtonHeight < 1)) {
      maxButtonHeight = height;
    }

    float padw = in.left + in.right;

    y = y + ((height - maxButtonHeight) / 2);

    float w;

    x = x + width;

    while(len-- > -1) {
      c = container.getComponentAt(len);

      if (fixedSize) {
        w = maxButtonWidth;
      } else {
        c.getPreferredSize(size);
        w = size.width + padw;
      }

      PlatformHelper.layout(c, x - w + in.left, y, w - padw, height);
      x -= w;
    }
  }

  @Override
  public void revalidate() {
    super.revalidate();
    maxButtonWidth = -1;
  }

  public void setButtonPadding(UIInsets buttonPadding) {
    this.buttonPadding.set(buttonPadding);
  }

  public void getButtonPadding(UIInsets in) {
    in.set(buttonPadding);
  }

  @Override
  public void getPreferredSize(UIDimension size, float maxWidth) {
    Container container = (Container) component;
    int       len       = container.getComponentCount();
    float     width     = 0;
    float     height    = 0;
    float     padw      = buttonPadding.left + buttonPadding.right;

    maxButtonWidth = 0;

    for (int i = 0; i < len; i++) {
      container.getComponentAt(i).getPreferredSize(size, maxWidth);
      width          += size.width;
      height         = Math.max(size.height, height);
      maxButtonWidth = Math.max(size.width + padw, maxButtonWidth);
    }

    size.height     = height + buttonPadding.top + buttonPadding.bottom;
    size.width      = width + (padw * len);
    ;
    maxButtonHeight = size.height;
  }
}
