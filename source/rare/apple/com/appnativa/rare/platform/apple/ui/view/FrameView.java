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

import com.appnativa.rare.platform.apple.ui.iAppleLayoutManager;
import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.Container;
import com.appnativa.rare.ui.RenderType;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.painter.RenderSpace;
import com.appnativa.rare.ui.painter.aUIPainter;

/*-[
 #import "RAREAPView.h"
 ]-*/
public class FrameView extends ParentView implements iAppleLayoutManager {
  private RenderType  renderType  = RenderType.STRETCHED;
  private RenderSpace renderSpace = RenderSpace.WITHIN_MARGIN;
  private UIInsets    padding     = new UIInsets();
  private UIInsets    insets      = new UIInsets();

  public FrameView() {
    super(createProxy());
    setLayoutManager(this);
  }

  public FrameView(Object proxy) {
    super(proxy);
    setLayoutManager(this);
  }

  @Override
  public void layout(ParentView view, float width, float height) {
    if (renderType == RenderType.XY) {
      return;
    }

    Container container = (Container) component;

    if (container.isPartOfAnimation()) {
      return;
    }

    int len = container.getComponentCount();

    if (len > 0) {
      iPlatformComponent c  = container.getComponentAt(0);
      float              iw = 0;
      float              ih = 0;

      if (renderType != RenderType.STRETCHED) {
        UIDimension d = c.getPreferredSize();

        iw = d.width;
        ih = d.height;
      }

      width  = width - padding.left - padding.right;
      height = height - padding.top - padding.bottom;

      UIRectangle rect = aUIPainter.getRenderLocation(container, renderSpace, renderType, padding.left, padding.top,
                           width, height, iw, ih, null);

      if (c.isVisible()) {
        c.setBounds(rect.x, rect.y, rect.width, rect.height);
      }

      for (int i = 1; i < len; i++) {
        c = container.getComponentAt(i);

        if (c.isVisible()) {
          c.setBounds(rect.x, rect.y, rect.width, rect.height);
        }
      }
    }
  }

  @Override
  public void setComponent(Component component) {
    if (!(component instanceof Container)) {
      throw new IllegalArgumentException("must be a Container");
    }

    super.setComponent(component);
  }

  public void setPadding(UIInsets insets) {
    if (insets == null) {
      padding.set(0, 0, 0, 0);
    } else {
      padding.set(insets);
    }
  }

  public void setRenderSpace(RenderSpace renderSpace) {
    this.renderSpace = renderSpace;
  }

  public void setViewRenderType(RenderType renderType) {
    this.renderType = renderType;
    invalidateLayout();
    revalidate();
  }

  @Override
  public void getMinimumSize(UIDimension size,float maxWidth) {
    Container container = (Container) component;
    Component child     = null;

    if (size == null) {
      size = new UIDimension(0, 0);
    }

    if (container.getComponentCount() > 0) {
      child = (Component) container.getComponentAt(0);
      child.getMinimumSize(size,maxWidth);
    }

    UIInsets in = (border != null)
                  ? container.getInsets(insets)
                  : null;

    if (in != null) {
      size.width  += in.left + in.right;
      size.height += in.top + in.bottom;
    }

    in          = padding;
    size.width  += in.left + in.right;
    size.height += in.top + in.bottom;
  }

  @Override
  public void getPreferredSize(UIDimension size, float maxWidth) {
    Container container = (Container) component;
    Component child     = null;

    if (size == null) {
      size = new UIDimension(0, 0);
    }

    if (container.getComponentCount() > 0) {
      child = (Component) container.getComponentAt(0);
      child.getPreferredSize(size, maxWidth);
    }

    UIInsets in = padding;

    size.width  += in.left + in.right;
    size.height += in.top + in.bottom;
  }

  public RenderSpace getRenderSpace() {
    return renderSpace;
  }

  public RenderType getViewRenderType() {
    return renderType;
  }

  static native Object createProxy()
  /*-[
    return [[RAREAPView alloc]init];
  ]-*/
  ;
}
