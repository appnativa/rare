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

import android.graphics.Paint;
import android.graphics.Typeface;

import android.widget.TextView;

/**
 *
 * @author Don DeCoteau
 */
public class UIFont {
  public final static int PLAIN  = Typeface.NORMAL;
  public final static int ITALIC = Typeface.ITALIC;
  public final static int BOLD   = Typeface.BOLD;
  private float           size;
  private boolean         strikeThrough;
  private Typeface        typeface;
  private boolean         underline;

  public UIFont(UIFont f) {
    this(f.typeface, f.size);
  }

  public UIFont(Typeface typeface, float size) {
    set(typeface, size);
  }

  public UIFont(Typeface typeface, int size) {
    set(typeface, size);
  }

  public UIFont(String family, int style, int size) {
    this("serif".equalsIgnoreCase(family)
         ? Typeface.create(Typeface.SERIF, style)
         : Typeface.create(family, style), size);
  }

  public UIFont(Typeface typeface, float size, boolean strikeThrough, boolean underline) {
    set(typeface, size);
    this.strikeThrough = strikeThrough;
    this.underline     = underline;
  }

  public UIFont(String family, int style, int size, boolean strikeThrough, boolean underline) {
    this("serif".equalsIgnoreCase(family)
         ? Typeface.create(Typeface.SERIF, style)
         : Typeface.create(family, style), size);
    this.strikeThrough = strikeThrough;
    this.underline     = underline;
  }

  public UIFont deriveBold() {
    return deriveFont(BOLD, size);
  }

  public UIFont deriveBoldItalic() {
    return deriveFont(BOLD | ITALIC, size);
  }

  public UIFont deriveFontSize(float size) {
    return new UIFont(typeface, size);
  }

  public UIFont deriveFont(int style, float fsize) {
    return new UIFont(Typeface.create(typeface, style), fsize);
  }

  public UIFont deriveItalic() {
    return deriveFont(ITALIC, size);
  }

  public UIFont deriveStrikethrough() {
    return new UIFont(typeface, size, true, false);
  }

  public UIFont derive(boolean strikethrough, boolean underlined) {
    return new UIFont(typeface, size, strikethrough, underlined);
  }

  public UIFont deriveUnderline() {
    return new UIFont(typeface, size, false, true);
  }

  public void setupPaint(Paint p) {
    if (typeface != null) {
      p.setTypeface(typeface);
    }

    if (size > 0) {
      p.setTextSize(ScreenUtils.platformPixelsf(size));
    }

    if (underline || strikeThrough) {
      int flags = p.getFlags();

      if (underline) {
        flags |= Paint.UNDERLINE_TEXT_FLAG;
      }

      if (strikeThrough) {
        flags |= Paint.STRIKE_THRU_TEXT_FLAG;
      }

      p.setFlags(flags);
    }
  }

  public void setupTextView(TextView tv) {
    if (typeface != null) {
      tv.setTypeface(typeface, typeface.getStyle());
    }

    int flags = tv.getPaintFlags();

    if ((flags & Paint.UNDERLINE_TEXT_FLAG) != 0) {
      flags -= Paint.UNDERLINE_TEXT_FLAG;
    }

    if ((flags & Paint.STRIKE_THRU_TEXT_FLAG) != 0) {
      flags -= Paint.STRIKE_THRU_TEXT_FLAG;
    }

    if (underline) {
      flags |= Paint.UNDERLINE_TEXT_FLAG;
    }

    if (strikeThrough) {
      flags |= Paint.STRIKE_THRU_TEXT_FLAG;
    }

    tv.setPaintFlags(flags);

    if (size > 0) {
      tv.setTextSize(size);
    }
  }

  public final void set(Typeface typeface, float size) {
    this.typeface = typeface;
    this.size     = size;

    if (size == 1) {
      this.size = size;
    }
  }

  public void setStrikeThrough(boolean strikeThrough) {
    this.strikeThrough = strikeThrough;
  }

  public void setUnderline(boolean underline) {
    this.underline = underline;
  }

  public String getFamily() {
    return (typeface == null)
           ? "serif"
           : typeface.toString();
  }

  public final int getSize() {
    return (int) size;
  }

  public final float getSize2D() {
    return size;
  }

  public final int getStyle() {
    return typeface.getStyle();
  }

  public final Typeface getTypeface() {
    return typeface;
  }

  public final boolean isBold() {
    return typeface.isBold();
  }

  public final boolean isItalic() {
    return typeface.isItalic();
  }

  public boolean isStrikeThrough() {
    return strikeThrough;
  }

  public boolean isUnderline() {
    return underline;
  }
}
