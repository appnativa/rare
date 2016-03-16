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

import com.appnativa.rare.platform.swing.ui.view.JPanelEx;
import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.viewer.CanvasViewer;
import com.appnativa.rare.widget.iWidget;

import java.awt.Dimension;
import java.awt.Graphics;

public class CanvasComponent extends Component implements iCanvasComponent {
  iContext        context;
  private boolean settingCanvasSize;

  public CanvasComponent(iWidget w) {
    super(new CanvasView());
    setWidget(w);
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
  public iPlatformComponent getPlatformComponent() {
    return this;
  }

  protected void paintComponent(Graphics g) {}

  static class CanvasView extends JPanelEx {
    private iContext context;

    public CanvasView() {
      super();
    }

    @Override
    public void getPreferredSize(UIDimension size, int maxWidth) {
      if (!isPreferredSizeSet()) {
        size.width  = 50;
        size.height = 50;
      } else {
        Dimension d = super.getPreferredSize();

        size.width  = d.width;
        size.height = d.height;
      }
    }

    public void setContext(iContext context) {
      this.context = context;
    }

    @Override
    protected void paintComponent(Graphics g) {
      super.paintComponent(g);

      if (context instanceof CanvasRenderingContext2D) {
        ((CanvasRenderingContext2D) context).paint(this, g, true);
      }
    }
  }
}
