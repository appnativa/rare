/*
 * @(#)JToolTipEx.java   2010-07-07
 *
 * Copyright (c) 2007-2009 appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.platform.swing.ui.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Window;

import javax.swing.JComponent;
import javax.swing.JToolTip;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicHTML;
import javax.swing.text.View;

import com.appnativa.rare.Platform;
import com.appnativa.rare.platform.swing.ui.text.BasicHTMLEx;
import com.appnativa.rare.platform.swing.ui.util.SwingGraphics;
import com.appnativa.rare.platform.swing.ui.util.SwingHelper;
import com.appnativa.rare.ui.BorderUtils;
import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.ComponentFactory;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.UIProperties;
import com.appnativa.rare.ui.iPlatformBorder;
import com.appnativa.rare.ui.border.UICompoundBorder;
import com.appnativa.rare.ui.border.UIEmptyBorder;
import com.appnativa.rare.ui.border.UILineBorder;
import com.appnativa.rare.ui.painter.PaintBucket;
import com.appnativa.rare.ui.painter.iPainter;
import com.appnativa.rare.ui.painter.iPainterSupport;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;

public class JToolTipEx extends JToolTip implements iPainterSupport {
  boolean                   shapedBorder = false;
  boolean                   overlapping  = false;
  UIInsets                  cellInsets;
  iPlatformComponentPainter componentPainter;
  protected SwingGraphics   graphics;
  private boolean           borderSet;

  public JToolTipEx(JComponent c, boolean overlapping) {
    super();
    this.overlapping = overlapping;
    setComponent(c);

    UIProperties defs = Platform.getUIDefaults();

    if (!overlapping) {
      setForeground(defs.getColor("ToolTip.foreground"));
      setBackground(defs.getColor("ToolTip.background"));

      PaintBucket pb = (PaintBucket) defs.get("Rare.Tooltip.painter");

      if (pb != null) {
        componentPainter = pb.getComponentPainter();

        Color cc = pb.getBackgroundColor();

        if (cc != null) {
          setBackground(cc);
        }

        cc = pb.getForegroundColor();

        if (cc != null) {
          setForeground(cc);
        }

        Border b = pb.getBorder();

        if (b != null) {
          setBorder(b);
        }
      }
    } else {
      ComponentFactory.toolTipMouseListener.register(this);

      if (c.getBackground() != null) {
        Color bg = c.getBackground();

        if (bg.getAlpha() != 255) {
          bg = new Color(bg.getRed(), bg.getGreen(), bg.getBlue());
        }

        setBackground(bg);
      }

      if (c.getForeground() != null) {
        setForeground(c.getForeground());
      }

      if (c.getFont() != null) {
        setFont(c.getFont());
      }
    }
  }

  @Override
  public void addNotify() {
    super.addNotify();

    if (overlapping &&!borderSet) {
      iPlatformBorder b = Platform.getUIDefaults().getBorder("Rare.ToolTip.overlappingBorder");

      if (b == null) {
        b = UILineBorder.createBlackLineBorder();
      }

      iPlatformBorder eb = (cellInsets == null)
                           ? BorderUtils.TWO_POINT_EMPTY_BORDER
                           : new UIEmptyBorder(cellInsets);

      b = new UICompoundBorder(b, eb);
      setBorder(b);
      borderSet = true;
    }

    if (!overlapping && Platform.getUIDefaults().getBoolean("Rare.ToolTip.isTransparent", true)) {
      Window w  = SwingUtilities.getWindowAncestor(this);
      Window ww = SwingUtilities.getWindowAncestor(getComponent());

      if ((w != null) && (w != ww)) {
        w.setBackground(ColorUtils.TRANSPARENT_COLOR);
      }
    }
  }

  public static JToolTip createNewToolTip(JComponent c) {
    return createNewToolTip(c, false);
  }

  public static JToolTip createNewToolTip(JComponent c, boolean overlapping) {
    JToolTip tip = new JToolTipEx(c, overlapping);

    tip.setComponent(c);

    return tip;
  }

  @Override
  public void paint(Graphics g) {
    graphics = SwingGraphics.fromGraphics(g, this, graphics);
    super.paint(g);
    graphics.clear();
  }

  @Override
  public void removeNotify() {
    if (!overlapping && Platform.getUIDefaults().getBoolean("Rare.ToolTip.isTransparent", true)) {
      Window w  = SwingUtilities.getWindowAncestor(this);
      Window ww = SwingUtilities.getWindowAncestor(getComponent());

      if ((w != null) && (w != ww)) {
        w.setBackground(ColorUtils.TRANSPARENT_COLOR);
      }
    }

    super.removeNotify();
  }

  @Override
  public void update(Graphics g) {
    paint(g);
  }

  @Override
  public void setComponentPainter(iPlatformComponentPainter cp) {
    componentPainter = cp;
  }

  @Override
  public void setTipText(String tipText) {
    super.setTipText(tipText);

    if ((tipText != null) && (tipText.length() > 0)) {
      //if (Platform.getUIDefaults().getBoolean("Rare.Label.useCustomEditorKit", true)) {
        BasicHTMLEx.updateRenderer(this, tipText);
      //}
    }
  }

  @Override
  public iPlatformComponentPainter getComponentPainter() {
    return componentPainter;
  }

  public boolean hasValue() {
    return true;
  }

  @Override
  public boolean isOpaque() {
    return shapedBorder
           ? false
           : super.isOpaque();
  }

  @Override
  protected void paintBorder(Graphics g) {}

  @Override
  protected void paintChildren(Graphics g) {
    super.paintChildren(graphics.getGraphics());

    iPlatformComponentPainter cp = getComponentPainter();

    if (cp != null) {
      float height = getHeight();
      float width  = getWidth();

      cp.paint(graphics, 0, 0, width, height, iPainter.HORIZONTAL, true);
    }
  }

  @Override
  protected void paintComponent(Graphics g) {
    iPlatformComponentPainter cp = getComponentPainter();

    if (cp != null) {
      float height = getHeight();
      float width  = getWidth();

      cp.paint(graphics, 0, 0, width, height, iPainter.HORIZONTAL, false);
    }

    String tipText = getTipText();

    if (tipText != null) {
      java.awt.Font font    = getFont();
      FontMetrics   metrics = getFontMetrics(font);
      Dimension     size    = getSize();

      g.setColor(getForeground());

      Insets    insets     = getInsets();
      Rectangle paintTextR = new Rectangle(insets.left + 3, insets.top, size.width - (insets.left + insets.right) - 6,
                               size.height - (insets.top + insets.bottom));
      View v = (View) getClientProperty(BasicHTML.propertyKey);

      if (v != null) {
        v.paint(g, paintTextR);
      } else {
        g.setFont(font);
        SwingHelper.drawString(g, tipText, paintTextR.x, paintTextR.y + metrics.getAscent());
      }
    }
  }
}
