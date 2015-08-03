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

import com.appnativa.rare.platform.android.ui.util.AndroidGraphics;
import com.appnativa.rare.platform.android.ui.view.ViewGroupEx;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;
import com.appnativa.rare.ui.painter.iPlatformPainter;
import com.appnativa.rare.widget.iWidget;

public class AbsolutePanel extends aAbsolutePanel {
  public AbsolutePanel(iWidget context) {
    super(new AbsoluteView(context.getAppContext().getActivity()));
  }

  static class AbsoluteView extends ViewGroupEx {
    public AbsoluteView(Context context) {
      super(context);
      measureType = MeasureType.HORIZONTAL;
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

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
      AbsolutePanel p = (AbsolutePanel) Component.fromView(this);

      p.layout(right - left, bottom - top);
    }
  }
}
