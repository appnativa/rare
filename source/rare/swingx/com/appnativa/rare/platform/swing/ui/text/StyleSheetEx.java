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

package com.appnativa.rare.platform.swing.ui.text;

import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.UIColorShade;

import java.awt.Color;
import java.awt.Font;

import javax.swing.UIManager;
import javax.swing.text.AttributeSet;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.CSS;
import javax.swing.text.html.CSS.Attribute;
import javax.swing.text.html.HTML;
import javax.swing.text.html.StyleSheet;

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
      return super.addAttribute(old, CSS.Attribute.BACKGROUND_COLOR, ColorUtils.getColor((String) value));
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
      if (fg instanceof UIColorShade) {
        rule.append(" color: ");
        rule.append(fg.toString());
      } else {
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
