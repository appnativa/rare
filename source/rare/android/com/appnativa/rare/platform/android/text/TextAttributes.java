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

package com.appnativa.rare.platform.android.text;

import android.content.res.ColorStateList;

import android.graphics.Typeface;

import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.CharacterStyle;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.TextAppearanceSpan;
import android.text.style.UnderlineSpan;

import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.FontUtils;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIColorShade;
import com.appnativa.rare.ui.text.iTextAttributes;

public class TextAttributes implements iTextAttributes {
  final CharacterStyle[] style;
  UIColor                fg;
  UIColor                bg;
  String                 fam;
  float                  fs = 0;

  public TextAttributes(CharacterStyle[] style) {
    this.style = style;
  }

  public Object getPlatformAttributes() {
    return style;
  }

  public boolean isBold() {
    return hasStyle(Typeface.BOLD);
  }

  public boolean isItalic() {
    return hasStyle(Typeface.ITALIC);
  }

  public boolean isUnderline() {
    return hasStyle(UnderlineSpan.class);
  }

  public boolean isSuperscript() {
    return hasStyle(SuperscriptSpan.class);
  }

  public boolean isSubscript() {
    return hasStyle(SubscriptSpan.class);
  }

  public boolean isStrikeThrough() {
    return hasStyle(StrikethroughSpan.class);
  }

  public String getFontFamily() {
    if (fam == null) {
      TextAppearanceSpan s = (TextAppearanceSpan) getStyle(TextAppearanceSpan.class);

      if (s != null) {
        fam = s.getFamily();
      }

      if (fam == null) {
        fam = FontUtils.getSystemFont().getFamily();
      }
    }

    return fam;
  }

  public float getFontSize() {
    if (fs != 0) {
      return fs;
    }

    if (fs == 0) {
      AbsoluteSizeSpan s = (AbsoluteSizeSpan) getStyle(AbsoluteSizeSpan.class);

      if (s != null) {
        fs = s.getSize();
      }
    }

    if (fs == 0) {
      RelativeSizeSpan s = (RelativeSizeSpan) getStyle(RelativeSizeSpan.class);

      if (s != null) {
        fs = s.getSizeChange();
      }
    }

    if (fs == 0) {
      TextAppearanceSpan s = (TextAppearanceSpan) getStyle(TextAppearanceSpan.class);

      if (s != null) {
        fs = s.getTextSize();
      }
    }

    if (fs == 0) {
      fs = FontUtils.getDefaultFontSize();
    }

    return fs;
  }

  public UIColor getForegroundColor() {
    if (fg == null) {
      TextAppearanceSpan s = (TextAppearanceSpan) getStyle(TextAppearanceSpan.class);

      if (s != null) {
        ColorStateList cs = s.getTextColor();

        if (cs == null) {
          fg = ColorUtils.NULL_COLOR;
        } else {
          fg = new UIColorShade(cs);
        }
      }
    }

    return (fg == ColorUtils.NULL_COLOR)
           ? null
           : fg;
  }

  public UIColor getBackgroundColor() {
    if (bg == null) {
      BackgroundColorSpan s = (BackgroundColorSpan) getStyle(TextAppearanceSpan.class);

      if (s != null) {
        bg = new UIColor(s.getBackgroundColor());
      } else {
        bg = ColorUtils.NULL_COLOR;
      }
    }

    return (bg == ColorUtils.NULL_COLOR)
           ? null
           : bg;
  }

  public boolean isEqual(iTextAttributes attr) {
    return isEqual(((TextAttributes) attr).style);
  }

  public boolean isEqual(final CharacterStyle[] b) {
    final CharacterStyle[] a = style;
    final int              n = a.length;

    if (n != b.length) {
      return false;
    }

    for (int i = 0; i < n; i++) {
      if (a[i].getClass() != b[i].getClass()) {
        return false;
      }
    }

    return true;
  }

  boolean hasStyle(int nstyle) {
    final CharacterStyle[] a = style;

    for (CharacterStyle s : a) {
      if (s instanceof StyleSpan) {
        if ((((StyleSpan) s).getStyle() & nstyle) > 0) {
          return true;
        }
      } else if (s instanceof TextAppearanceSpan) {
        if ((((TextAppearanceSpan) s).getTextStyle() & nstyle) > 0) {
          return true;
        }
      }
    }

    return false;
  }

  boolean hasStyle(Class cls) {
    return getStyle(cls) != null;
  }

  CharacterStyle getStyle(Class cls) {
    final CharacterStyle[] a = style;

    for (CharacterStyle s : a) {
      if (cls.isInstance(s)) {
        return s;
      }
    }

    return null;
  }
}
