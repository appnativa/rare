/*
 * @(#)FunctionHelper.java
 * 
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.scripting;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import com.appnativa.rare.platform.swing.ui.util.ImageUtils;
import com.appnativa.rare.ui.UIBorderIcon;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.ui.UIImage;
import com.appnativa.rare.ui.UITextIcon;
import com.appnativa.rare.ui.iPlatformBorder;
import com.appnativa.rare.ui.iPlatformGraphics;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.border.UILineBorder;
import com.appnativa.rare.ui.effects.iAnimator;
import com.appnativa.rare.ui.effects.iAnimatorValueListener;

public class FunctionHelper extends aFunctionHelper {
  public static iPlatformIcon createColorIcon(final UIColor color, final int width, final int height,
          final iPlatformBorder border) {
    iPlatformIcon icon = new iPlatformIcon() {
      @Override
      public int getIconWidth() {
        return width;
      }
      @Override
      public int getIconHeight() {
        return height;
      }
      @Override
      public iPlatformIcon getDisabledVersion() {
        return this;
      }
      @Override
      public void paintIcon(Component c, Graphics g, int x, int y) {
        Color cc = g.getColor();

        g.setColor(color);
        g.fillRect(x, y, width, height);
        g.setColor(cc);
      }
      @Override
      public void paint(iPlatformGraphics g, float x, float y, float width, float height) {
        UIColor cc = g.getColor();

        g.setColor(color);
        g.fillRect(x, y, width, height);
        g.setColor(cc);
      }
    };

    if (border == null) {
      return icon;
    }

    return new UIBorderIcon(border, icon);
  }

  public static iPlatformIcon createEmptyIcon(final int width, final int height, final UIColor borderColor) {
    iPlatformIcon ic = new iPlatformIcon() {
      @Override
      public int getIconWidth() {
        return width;
      }
      @Override
      public int getIconHeight() {
        return height;
      }
      @Override
      public iPlatformIcon getDisabledVersion() {
        return this;
      }
      @Override
      public void paintIcon(Component c, Graphics g, int x, int y) {}
      @Override
      public void paint(iPlatformGraphics g, float x, float y, float width, float height) {}
    };

    if (borderColor == null) {
      return ic;
    }

    return new UIBorderIcon(new UILineBorder(borderColor), ic);
  }

  public static UIImage createTextImage(String text, UIFont font, UIColor fg, UIColor bg, iPlatformBorder b,
          boolean square) {
    UITextIcon icon = new UITextIcon(text, fg, font, b);

    if (bg != null) {
      icon.setBackgroundColor(bg);
    }

    return ImageUtils.createImage(icon);
  }

  public static iAnimator createValueAnimator(double start, double end, double inc, boolean accelerate, boolean decelerate,
          iAnimatorValueListener l) {
    return null;
  }

  public static String getLocation() {
    return null;
  }
}
