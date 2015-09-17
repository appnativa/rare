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

package com.appnativa.rare.platform.swing.plaf;

import com.appnativa.rare.Platform;
import com.appnativa.rare.platform.swing.ui.text.BasicHTMLEx;
import com.appnativa.rare.platform.swing.ui.util.SwingHelper;
import com.appnativa.rare.platform.swing.ui.view.JToolTipEx;
import com.appnativa.rare.ui.UIColorShade;
import com.appnativa.rare.ui.painter.PaintBucket;
import com.appnativa.rare.ui.painter.iBackgroundPainter;
import com.appnativa.rare.ui.painter.iPainter;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;
import javax.swing.JToolTip;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicToolTipUI;

/**
 *
 * @author Don DeCoteau
 */
public class CustomTooltipUI extends BasicToolTipUI {
  static CustomTooltipUI sharedInstance = new CustomTooltipUI();

  public static ComponentUI createUI(JComponent c) {
    return sharedInstance;
  }

  @Override
  public void installUI(JComponent c) {
    if (Platform.getUIDefaults().getBoolean("Rare.Label.useCustomEditorKit", true)) {
      installDefaults(c);
      BasicHTMLEx.updateRenderer(c, ((JToolTip) c).getTipText());
    } else {
      super.installUI(c);
    }
  }

  @Override
  public void paint(Graphics g, JComponent c) {
    if (!(c instanceof JToolTipEx)) {
      PaintBucket pb = (PaintBucket) Platform.getUIDefaults().get("Rare.Tooltip.painter");

      if (pb != null) {
        iPlatformComponentPainter cp = pb.getComponentPainter();

        cp.paint(c, (Graphics2D) g, 0, 0, c.getWidth(), c.getHeight(), true, iPainter.HORIZONTAL);
      } else {
        Color color = c.getBackground();

        if (color instanceof UIColorShade) {
          iBackgroundPainter bp = ((UIColorShade) color).getBackgroundPainter();

          if (bp != null) {
            bp.paint(c, (Graphics2D) g, 0, 0, c.getWidth(), c.getHeight(), true, iPainter.HORIZONTAL);
          }
        }
      }
    }

    super.paint(g, c);
  }

  @Override
  protected void installDefaults(JComponent c) {
    super.installDefaults(c);

    PaintBucket pb = (PaintBucket) Platform.getUIDefaults().get("Rare.Tooltip.painter");

    if ((pb != null) &&!(c instanceof JToolTipEx)) {
      SwingHelper.installPaint(pb, c);
    }

    c.setOpaque(!SwingHelper.isNonRectangularBorder(c.getBorder()));
  }
}
