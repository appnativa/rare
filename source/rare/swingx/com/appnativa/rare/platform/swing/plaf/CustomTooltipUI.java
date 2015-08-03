/*
 * @(#)CustomTooltipUI.java   2010-03-08
 *
 * Copyright (c) appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.platform.swing.plaf;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;
import javax.swing.JToolTip;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicToolTipUI;

import com.appnativa.rare.Platform;
import com.appnativa.rare.platform.swing.ui.text.BasicHTMLEx;
import com.appnativa.rare.platform.swing.ui.util.SwingHelper;
import com.appnativa.rare.platform.swing.ui.view.JToolTipEx;
import com.appnativa.rare.ui.UIColorShade;
import com.appnativa.rare.ui.painter.PaintBucket;
import com.appnativa.rare.ui.painter.iBackgroundPainter;
import com.appnativa.rare.ui.painter.iPainter;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;

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
    if (Platform.getUIDefaults().getBoolean("Rare.Label.useCustomEditorKit",true)) {
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
        cp.paint(c, (Graphics2D) g, 0,0,c.getWidth(),c.getHeight(),true, iPainter.HORIZONTAL);
      } else {
        Color color = c.getBackground();

        if (color instanceof UIColorShade) {
        	iBackgroundPainter bp = ((UIColorShade) color).getBackgroundPainter();
        	if(bp!=null) {
        		bp.paint(c, (Graphics2D) g, 0,0,c.getWidth(),c.getHeight(),true, iPainter.HORIZONTAL);
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
