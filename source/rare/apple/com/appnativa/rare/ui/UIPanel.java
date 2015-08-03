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

import com.appnativa.rare.platform.apple.ui.iAppleLayoutManager;
import com.appnativa.rare.platform.apple.ui.util.AppleGraphics;
import com.appnativa.rare.platform.apple.ui.view.ParentView;
import com.appnativa.rare.platform.apple.ui.view.View;

public class UIPanel extends aUIPanel {
  public UIPanel() {
    super(new PanelView());

    PanelView v = (PanelView) view;

    v.panel = this;
  }

  @Override
  public iPlatformComponent getPlatformComponent() {
    return this;
  }

  static class PanelView extends ParentView implements iAppleLayoutManager {
    UIPanel panel;

    public PanelView() {
      super(View.createAPView());
      super.setPaintHandlerEnabled(true);
      setLayoutManager(this);
    }

    @Override
    public void setPaintHandlerEnabled(boolean enabled) {}

    @Override
    public void paintBackground(AppleGraphics g, View v, UIRectangle rect) {
      super.paintBackground(g, v, rect);

      UIInsets in     = panel.getInsetsEx();
      float    x      = 0;
      float    y      = 0;
      float    width  = getWidth();
      float    height = getHeight();

      if (in != null) {
        x      = in.left;
        y      = in.top;
        width  -= (in.left + in.right);
        height -= (in.top + in.bottom);
      }

      panel.paint(g, x, y, width, height);
    }

    @Override
    public void layout(ParentView view, float width, float height) {
      panel.layout(width, height);
    }
  }
}
