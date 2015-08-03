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

import android.content.Context;

import com.appnativa.rare.Platform;
import com.appnativa.rare.platform.android.ui.view.FrameView;
import com.appnativa.rare.ui.painter.RenderSpace;

public class ContainerPanel extends Container implements iTargetContainer {
  public ContainerPanel() {
    super(new FrameView(Platform.getAppContext().getActivity()));
    useBorderInSizeCalculation = true;
  }

  public ContainerPanel(Context context) {
    super(new FrameView(context));
    useBorderInSizeCalculation = true;
  }

  public ContainerPanel(iPlatformComponent c) {
    super(new FrameView(Platform.getAppContext().getActivity()));
    useBorderInSizeCalculation = true;
    add(c);
  }

  public ContainerPanel(Object view) {
    super(view);
    useBorderInSizeCalculation = true;
  }

  public void setPadding(UIInsets insets) {
    if (insets == null) {
      ((FrameView) view).setPadding(0, 0, 0, 0);
    } else {
      ((FrameView) view).setPadding(insets.intLeft(), insets.intTop(), insets.intRight(), insets.intBottom());
    }
  }

  public boolean setRenderType(RenderType renderType) {
    ((FrameView) view).setViewRenderType(renderType);

    return true;
  }

  public RenderType getRenderType() {
    return ((FrameView) view).getViewRenderType();
  }

  public void setRenderSpace(RenderSpace renderSpace) {
    this.renderSpace = renderSpace;
  }

  protected void getMinimumSizeEx(UIDimension size) {
    size.setSize(0, 0);

    if (getComponentCount() > 0) {
      iPlatformComponent c = getComponentAt(0);

      if (c.isVisible()) {
        c.getMinimumSize(size);
      }
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

  protected void getPreferredSizeEx(UIDimension size, float maxWidth) {
    size.setSize(0, 0);

    if (getComponentCount() > 0) {
      iPlatformComponent c = getComponentAt(0);

      if (c.isVisible()) {
        c.getPreferredSize(size, maxWidth);
      }
    }
  }

  public void setBorderPanel(boolean borderPanel) {
    renderSpace = RenderSpace.WITHIN_BORDER;
    ((FrameView) view).setRenderSpace(RenderSpace.WITHIN_BORDER);
  }
}
