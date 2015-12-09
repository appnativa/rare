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

package com.appnativa.rare.platform.swing.ui.view;

import com.appnativa.rare.platform.swing.ui.util.SwingHelper;
import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.Container;
import com.appnativa.rare.ui.RenderType;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.iPlatformBorder;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.painter.RenderSpace;
import com.appnativa.rare.ui.painter.aUIPainter;

import java.awt.Dimension;
import java.awt.Rectangle;

public class FrameView extends UtilityPanel {
  private RenderType  renderType  = RenderType.STRETCHED;
  private RenderSpace renderSpace = RenderSpace.WITHIN_MARGIN;
  private UIInsets    padding     = new UIInsets();
  private UIInsets    insets      = new UIInsets();

  public FrameView() {
    super();
  }

  @Override
  public Dimension minimumLayoutSize(java.awt.Container parent) {
    UIDimension size = new UIDimension();

    getMinimumSize(size, 0);

    return SwingHelper.setDimension(null, size);
  }

  @Override
  public Dimension preferredLayoutSize(java.awt.Container parent) {
    UIDimension size = new UIDimension();

    getPreferredSize(size, 0);

    return SwingHelper.setDimension(null, size);
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
    revalidate();
    repaint();
  }

  @Override
  public Dimension getMinimumSize() {
    UIDimension size = new UIDimension();

    getMinimumSize(size, 0);

    return new Dimension(size.intWidth(), size.intHeight());
  }

  @Override
  public void getMinimumSize(UIDimension size, int maxWidth) {
    Container container = (Container) Component.fromView(this);
    Component child     = null;

    if (size == null) {
      size = new UIDimension(0, 0);
    }

    if (container.getComponentCount() > 0) {
      child = (Component) container.getComponentAt(0);
      child.getMinimumSize(size);
    }

    iPlatformBorder border = container.getBorder();
    UIInsets        in     = (border != null)
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
  public Dimension getPreferredSize() {
    UIDimension size = new UIDimension();

    getPreferredSize(size, 0);

    return new Dimension(size.intWidth(), size.intHeight());
  }

  @Override
  public void getPreferredSize(UIDimension size, int maxWidth) {
    Container container = (Container) Component.fromView(this);
    Component child     = null;

    if (size == null) {
      size = new UIDimension(0, 0);
    }

    int len = container.getComponentCount();

    if (len > 0) {
      child = (Component) container.getComponentAt(len - 1);

      if (child.isVisible()) {
        child.getPreferredSize(size, maxWidth);
      }
    }

    iPlatformBorder border = container.getBorder();
    UIInsets        in     = (border != null)
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

  public RenderSpace getRenderSpace() {
    return renderSpace;
  }

  @Override
  public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
    return 40;
  }

  public RenderType getViewRenderType() {
    return renderType;
  }

  @Override
  protected void layoutContainerEx(int width, int height) {
    if (renderType == RenderType.XY) {
      return;
    }

    Container container = (Container) Component.fromView(this);
    int       len       = container.getComponentCount();

    if (len > 0) {
      iPlatformComponent c  = container.getComponentAt(len - 1);
      float              iw = 0;
      float              ih = 0;

      if (renderType != RenderType.STRETCHED) {
        UIDimension d = c.getPreferredSize();

        iw = d.width;
        ih = d.height;
      }

      width  = (int) Math.ceil(width - padding.left - padding.right);
      height = (int) Math.ceil(height - padding.top - padding.bottom);

      UIRectangle rect = aUIPainter.getRenderLocation(container, renderSpace, renderType, padding.left, padding.top,
                           width, height, iw, ih, null);

      for (int i = 0; i < len; i++) {
        c = container.getComponentAt(i);

        if (c.isVisible()) {
          c.setBounds(rect.x, rect.y, rect.width, rect.height);
        }
      }
    }
  }
}
