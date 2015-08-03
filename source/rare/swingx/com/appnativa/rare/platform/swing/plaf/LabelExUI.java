/*
 * @(#)LabelExUI.java   2010-04-08
 *
 * Copyright (c) 2007-2009 appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.platform.swing.plaf;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.beans.PropertyChangeEvent;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.LabelUI;
import javax.swing.plaf.basic.BasicLabelUI;
import javax.swing.text.View;

import com.appnativa.rare.platform.swing.ui.text.BasicHTMLEx;
import com.appnativa.rare.platform.swing.ui.view.LabelView;

/**
 *
 * @author Don DeCoteau
 */
public class LabelExUI extends BasicLabelUI {
  private static final LabelUI INSTANCE = new LabelExUI();
  private boolean              painting;
  private boolean              wordWrap;

  public static ComponentUI createUI(JComponent c) {
    return INSTANCE;
  }

  @Override
  public void paint(Graphics g, JComponent c) {
    painting = true;

    try {
      super.paint(g, c);
    } finally {
      painting = false;
    }
  }

  @Override
  public void propertyChange(PropertyChangeEvent e) {
    String name = e.getPropertyName();

    if ((name == "text") || ("font" == name) || ("foreground" == name)) {
      // remove the old html view client property if one
      // existed, and install a new one if the text installed
      // into the JLabel is html source.
      JLabel lbl = ((JLabel) e.getSource());
      String text = lbl.getText();
      BasicHTMLEx.updateRenderer(lbl, text, wordWrap);
    } else if ((name == "labelFor") || (name == "displayedMnemonic")) {
      installKeyboardActions((JLabel) e.getSource());
    }
  }

  public void setWordWrap(boolean wrap) {
    wordWrap = wrap;
  }

  @Override
  protected void paintDisabledText(JLabel l, Graphics g, String s, int textX, int textY) {
    super.paintEnabledText(l, g, s, textX, textY);
  }

  public static LabelExUI getInstance() {
    return (LabelExUI) INSTANCE;
  }

  @Override
  public Dimension getPreferredSize(JComponent c) {
    JLabel label = (JLabel) c;
    String text = label.getText();
    Icon icon = (label.isEnabled()) ? label.getIcon() : label.getDisabledIcon();
    Insets insets = label.getInsets(null);
    Font font = label.getFont();

    int dx = insets.left + insets.right;
    int dy = insets.top + insets.bottom;
    final int tlen = text == null ? 0 : text.length();
    if ((icon == null) && ((tlen == 0) || ((text != null) && (font == null)))) {
      return new Dimension(dx, dy);
    } else if ((tlen == 0) || ((icon != null) && (font == null))) {
      return new Dimension(icon.getIconWidth() + dx, icon.getIconHeight() + dy);
    } else {
      FontMetrics fm = label.getFontMetrics(font);
      Rectangle iconR = new Rectangle();
      Rectangle textR = new Rectangle();
      Rectangle viewR = new Rectangle();

      iconR.x = iconR.y = iconR.width = iconR.height = 0;
      textR.x = textR.y = textR.width = textR.height = 0;
      viewR.x = dx;
      viewR.y = dy;
      viewR.width = viewR.height = Short.MAX_VALUE;

      layoutCL(label, fm, text, icon, viewR, iconR, textR);
      int x1 = Math.min(iconR.x, textR.x);
      int x2 = Math.max(iconR.x + iconR.width, textR.x + textR.width);
      int y1 = Math.min(iconR.y, textR.y);
      int y2 = Math.max(iconR.y + iconR.height, textR.y + textR.height);
      Dimension rv = new Dimension(x2 - x1, y2 - y1);

      rv.width += dx;
      rv.height += dy;
      return rv;
    }
  }

  @Override
  protected void installComponents(JLabel c) {
    BasicHTMLEx.updateRenderer(c, c.getText(), wordWrap);
    c.setInheritsPopupMenu(true);
  }

  @Override
  protected String layoutCL(JLabel label, FontMetrics fontMetrics, String text, Icon icon, Rectangle viewR, Rectangle iconR,
      Rectangle textR) {
    if (painting) {
      View view = (View) label.getClientProperty(javax.swing.plaf.basic.BasicHTML.propertyKey);

      if (view != null) {
        if (icon != null) {
          if (label.getVerticalTextPosition() != SwingConstants.CENTER) {
            int h = viewR.height - label.getIconTextGap() - icon.getIconHeight();

            view.setSize(viewR.width, h);
          } else {
            int w = viewR.width - label.getIconTextGap() - icon.getIconWidth();

            view.setSize(w, viewR.height);
          }
        } else {
          view.setSize(viewR.width, viewR.height);
        }
      }
    }

    final String s = super.layoutCL(label, fontMetrics, text, icon, viewR, iconR, textR);
    if (painting && icon != null) {
      if (s == null || s.length() == 0) {
        iconR.x = (viewR.width - iconR.width) / 2;

      } else if ((label instanceof LabelView) && ((LabelView) label).isIconRightJustified()) {
        iconR.x = viewR.width - iconR.width;
      }
    }

    return s;
  }
}
