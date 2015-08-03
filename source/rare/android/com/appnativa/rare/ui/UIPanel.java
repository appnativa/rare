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

import android.graphics.Canvas;

import android.view.ViewGroup;

import com.appnativa.rare.Platform;
import com.appnativa.rare.platform.android.ui.iAndroidLayoutManager;
import com.appnativa.rare.platform.android.ui.util.AndroidGraphics;
import com.appnativa.rare.platform.android.ui.view.ViewGroupEx;
import com.appnativa.rare.ui.listener.iViewListener;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;
import com.appnativa.rare.ui.painter.iPlatformPainter;

public class UIPanel extends aUIPanel implements iAndroidLayoutManager {
  public UIPanel() {
    super(new PanelView(Platform.getAppContext().getActivity()));

    PanelView v = (PanelView) view;

    v.panel = this;
    v.setLayoutManager(this);
  }

  public void addViewListener(iViewListener l) {}

  public void layout(ViewGroup parent, float width, float height) {
    layout(width, height);
  }

  public void removeViewListener(iViewListener l) {}

  public iPlatformComponent getPlatformComponent() {
    return this;
  }

  static class PanelView extends ViewGroupEx {
    UIPanel panel;

    public PanelView(Context context) {
      super(context);
    }

    public void draw(Canvas canvas) {
      if (matrix != null) {
        canvas.concat(matrix);
      }

      graphics = AndroidGraphics.fromGraphics(canvas, this, graphics);

      final iPlatformComponentPainter cp = componentPainter;

      if (cp == null) {
        super.draw(canvas);
      } else {
        cp.paint(graphics, getScrollX(), getScrollY(), getWidth(), getHeight(), iPlatformPainter.UNKNOWN, false);
        super.draw(canvas);
        cp.paint(graphics, getScrollX(), getScrollY(), getWidth(), getHeight(), iPlatformPainter.UNKNOWN, true);
      }

      graphics.clear();
    }

    protected void onDraw(Canvas canvas) {
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

      panel.paint(new AndroidGraphics(canvas, this), x, y, width, height);
    }
  }
}
