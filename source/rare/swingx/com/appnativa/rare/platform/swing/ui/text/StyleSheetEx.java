/*
 * @(#)StyleSheetEx.java   2010-04-07
 *
 * Copyright (c) 2007-2009 appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.platform.swing.ui.text;

import java.awt.Color;
import java.awt.Font;

import javax.swing.UIManager;
import javax.swing.text.AttributeSet;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.CSS;
import javax.swing.text.html.CSS.Attribute;
import javax.swing.text.html.HTML;
import javax.swing.text.html.StyleSheet;

import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.UIColorShade;

/**
 *
 * @author Don DeCoteau
 */
public class StyleSheetEx extends StyleSheet {
  protected CSS css;

  public StyleSheetEx() {
    super();
  }

  @Override
  public AttributeSet addAttribute(AttributeSet old, Object key, Object value) {
    if ((key == HTML.Attribute.COLOR) && (value instanceof String)) {
      return super.addAttribute(old, CSS.Attribute.COLOR, ColorUtils.getColor((String) value));
    }

    if (key == HTML.Attribute.BGCOLOR) {
      return super.addAttribute(old, CSS.Attribute.BACKGROUND_COLOR,
      		ColorUtils.getColor((String) value));
    }

    return super.addAttribute(old, key, value);
  }

  @Override
  public void addCSSAttribute(MutableAttributeSet attr, Attribute key, String value) {
    if ((key == CSS.Attribute.COLOR) || (key == CSS.Attribute.BACKGROUND_COLOR)) {
      attr.addAttribute(key, ColorUtils.getColor(value));
    } else {
      super.addCSSAttribute(attr, key, value);
    }
  }

  public static String displayPropertiesToCSS(Font font, Color fg) {
    StringBuffer rule = new StringBuffer("body {");

    if (font != null) {
      rule.append(" font-family: ");
      rule.append(font.getFamily());
      rule.append(" ; ");
      rule.append(" font-size: ");
      rule.append(font.getSize());
      rule.append("pt ;");

      if (font.isBold()) {
        rule.append(" font-weight: 700 ; ");
      }

      if (font.isItalic()) {
        rule.append(" font-style: italic ; ");
      }
    }

    if (fg != null) {
      if(fg instanceof UIColorShade) {
        rule.append(" color: ");
        rule.append(fg.toString());
      }
      else {
        rule.append(" color: #");

        if (fg.getRed() < 16) {
          rule.append('0');
        }

        rule.append(Integer.toHexString(fg.getRed()));

        if (fg.getGreen() < 16) {
          rule.append('0');
        }

        rule.append(Integer.toHexString(fg.getGreen()));

        if (fg.getBlue() < 16) {
          rule.append('0');
        }

        rule.append(Integer.toHexString(fg.getBlue()));
      }
      rule.append(" ; ");
    }

    rule.append(" }");

    return rule.toString();
  }

  @Override
  public Color stringToColor(String string) {
    return ColorUtils.getColor(string);
  }

  @Override
  public Color getBackground(AttributeSet a) {
    Object o = a.getAttribute(CSS.Attribute.BACKGROUND_COLOR);

    if (o instanceof Color) {
      return (Color) o;
    }

    return super.getBackground(a);
  }

  @Override
  public Color getForeground(AttributeSet a) {
    Object o = a.getAttribute(CSS.Attribute.COLOR);

    if (o instanceof Color) {
      return (Color) o;
    }

    if (a.getAttribute(CSS.Attribute.COLOR) != null) {
      return super.getForeground(a);
    }

    return UIManager.getColor("Label.foreground");
  }

  protected CSS getCSS() {
    if (css == null) {
      css = new CSS();
    }

    return css;
  }
}
