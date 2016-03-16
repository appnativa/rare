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

package com.appnativa.rare.ui.canvas;

import com.appnativa.rare.platform.apple.ui.util.AppleGraphics;
import com.appnativa.rare.platform.apple.ui.view.ParentView;
import com.appnativa.rare.platform.apple.ui.view.View;
import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.viewer.CanvasViewer;
import com.appnativa.rare.widget.iWidget;

public class CanvasComponent extends Component implements iCanvasComponent {
  iContext context;
  private boolean settingCanvasSize;

  public CanvasComponent(iWidget w) {
    super(new CanvasView());
  }

  @Override
  public void setContext(iContext context) {
    this.context = context;
    ((CanvasView) view).setContext(context);
  }

  @Override
  public iContext getContext() {
    return context;
  }

  @Override
  public void setBounds(float x, float y, float width, float height) {
    if (!settingCanvasSize) {
      settingCanvasSize=true;
      super.setBounds(x, y, width, height);
      ((CanvasViewer) widget).setSize((int) width, (int) height, false);
      settingCanvasSize=false;
    }
  }

  @Override
  protected void getPreferredSizeEx(UIDimension size, float maxWidth) {
    size.width  = 50;
    size.height = 50;
  }

  @Override
  public iPlatformComponent getPlatformComponent() {
    return this;
  }

  static class CanvasView extends ParentView {
    private iContext context;

    public CanvasView() {
      super(ParentView.createAPView());
      setPaintHandlerEnabled(true);
    }

    @Override
    public void paintBackground(AppleGraphics g, View v, UIRectangle rect) {
      super.paintBackground(g, v, rect);

      if (context instanceof CanvasRenderingContext2D) {
        ((CanvasRenderingContext2D) context).render(g);
      }
    }

    public void setContext(iContext context) {
      this.context = context;
    }
  }
}
