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

package com.appnativa.rare.ui.chart;

import com.appnativa.rare.Platform;
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.ui.UIPanel;
import com.appnativa.rare.ui.iActionComponent;
import com.appnativa.rare.ui.iPlatformGraphics;

public class CartesianPanel extends UIPanel {
  iActionComponent label;

  public CartesianPanel() {
    label = PlatformHelper.createLabel(this);
    label.setText("<html><center>" + Platform.getResourceAsString("Rare.runtime.text.noCharts") + "</center></html>");
    label.setForeground(ColorUtils.getForeground());
    add(label);
  }

  @Override
  public void setBounds(float x, float y, float w, float h) {
    super.setBounds(x, y, w, h);
    label.setBounds(0, 0, w, h);
  }

  @Override
  public void setFont(UIFont f) {
    super.setFont(f);

    if (f != null) {
      label.setFont(f);;
    }
  }

  @Override
  public void setForeground(UIColor fg) {
    super.setForeground(fg);

    if (fg != null) {
      label.setForeground(fg);
    }
  }

  void drawXTick(iPlatformGraphics g, float inc, float x, float y, float endx, float size, boolean even) {
    float pos  = inc + x;
    float yoff = size / 2;
    int   n    = 0;

    while(pos < endx) {
      if (!even || (n % 2 == 0)) {
        g.drawLine(pos, y - yoff, pos, y + yoff);
      }

      pos += inc;
      n++;
    }
  }

  void drawYTick(iPlatformGraphics g, float inc, float x, float y, float starty, float size, boolean even) {
    float pos  = y - inc;
    float xoff = size / 2;
    float n    = 0;

    while(pos > starty) {
      if (!even || (n % 2 == 0)) {
        g.drawLine(x - xoff, pos, x + xoff, pos);
      }

      pos -= inc;
      n++;
    }
  }

  @Override
  protected void paint(iPlatformGraphics g, float x, float y, float width, float height) {
    float   offset = 20;
    float   endx   = x + width - offset;
    float   endy   = y + height - offset;
    UIColor fg     = getForeground();

    g.setColor(fg);
    // x-axis
    g.drawLine(x + offset - 10, endy, endx + 5, endy);
    // y-axis
    g.drawLine(x + offset - 5, y + offset - 5, x + offset - 5, endy + 5);
    drawXTick(g, 20, offset, endy, endx, 12, false);
    drawYTick(g, 20, offset + 2, endy, offset, 12, false);

    if (fg.isDarkColor()) {
      fg = fg.light(20);
    } else {
      fg = fg.darker();
    }

    g.setColor(fg);
    drawXTick(g, 10, offset, endy, endx, 6, true);
    drawYTick(g, 10, offset + 2, endy, offset, 6, true);
  }
}
