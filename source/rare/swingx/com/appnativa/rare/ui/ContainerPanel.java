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

import com.appnativa.rare.platform.swing.ui.view.FrameView;
import com.appnativa.rare.ui.painter.PaintBucket;
import com.appnativa.rare.ui.painter.RenderSpace;
import com.appnativa.rare.ui.painter.UIComponentPainter;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;

public class ContainerPanel extends Container implements iTargetContainer {
  private UIComponentPainter borderComponentPainter;
  private boolean            borderPanel;

  public ContainerPanel() {
    super(new FrameView());
  }

  public ContainerPanel(iPlatformComponent c) {
    super(new FrameView());
    add(c);
  }

  @Override
  public void dispose() {
    if (borderComponentPainter != null) {
      borderComponentPainter.dispose();
      borderComponentPainter = null;
    }

    super.dispose();
  }

  @Override
  public boolean isFocusPainted() {
    return false;
  }

  @Override
  public void setBorder(iPlatformBorder border) {
    if (borderComponentPainter != null) {
      borderComponentPainter.setBorder(border);
      ((FrameView) view).setBorder(border);
    } else {
      super.setBorder(border);
    }
  }

  @Override
  public PaintBucket getFocusPaint(iPlatformGraphics g, PaintBucket def) {
    if (!focusPainted || (getComponentCount() == 0)) {
      return null;
    }

    return getComponentAt(0).isFocusOwner()
           ? def
           : null;
  }

  @Override
  public void setBackground(UIColor bg) {
    if (borderPanel && (getComponentCount() > 0)) {
      getComponentAt(0).setBackground(bg);
    } else {
      super.setBackground(bg);
    }
  }

  @Override
  public void setFocusPainted(boolean focusPainted) {
    super.setFocusPainted(focusPainted);

    if (getComponentCount() > 0) {
      iPlatformComponent c = getComponentAt(0);

      c.setFocusPainted(focusPainted);
    }
  }

  public void setBorderPanel(boolean borderPanel) {
    if (borderComponentPainter == null) {
      borderComponentPainter = new UIComponentPainter();
    }

    ((FrameView) view).setComponentPainter(borderComponentPainter);
    this.borderPanel = borderPanel;
    ((FrameView) view).setRenderSpace(borderPanel
                                      ? RenderSpace.WITHIN_BORDER
                                      : RenderSpace.WITHIN_MARGIN);
  }

  @Override
  public void setComponentPainter(iPlatformComponentPainter cp) {
    if (borderPanel && (getComponentCount() > 0)) {
      iPlatformComponent c = getComponentAt(0);

      c.setComponentPainter(cp);
      revalidate();
    } else {
      super.setComponentPainter(cp);
    }
  }

  @Override
  public void setFont(UIFont f) {
    super.setFont(f);

    if (getComponentCount() > 0) {
      getComponentAt(0).setFont(f);
    }
  }

  @Override
  public void setForeground(UIColor fg) {
    super.setForeground(fg);

    if (getComponentCount() > 0) {
      getComponentAt(0).setForeground(fg);
    }
  }

  public void setPadding(UIInsets insets) {
    ((FrameView) view).setPadding(insets);
  }

  @Override
  public boolean setRenderType(RenderType renderType) {
    ((FrameView) view).setViewRenderType(renderType);

    return true;
  }

  @Override
  public RenderType getRenderType() {
    return ((FrameView) view).getViewRenderType();
  }

  public boolean isBorderPanel() {
    return borderPanel;
  }

  @Override
  protected iPlatformComponentPainter getComponentPainterEx() {
    if (borderPanel && (getComponentCount() > 0)) {
      return getComponentAt(0).getComponentPainter();
    } else {
      return super.getComponentPainterEx();
    }
  }

  @Override
  protected void getMinimumSizeEx(UIDimension size) {
    size.setSize(0, 0);

    if (getComponentCount() > 0) {
      iPlatformComponent c = getComponentAt(0);

      if (c.isVisible()) {
        c.getMinimumSize(size);
      }
    }

    UIInsets in = getInsets(null);

    size.width  += in.left + in.right;
    size.height += in.top + in.bottom;
  }

  protected void getPreferredSizeEx(UIDimension size) {
    size.setSize(0, 0);

    if (getComponentCount() > 0) {
      iPlatformComponent c = getComponentAt(0);

      if (c.isVisible()) {
        c.getPreferredSize(size);
      }
    }

    UIInsets in = getInsets(null);

    size.width  += in.left + in.right;
    size.height += in.top + in.bottom;
  }
}
