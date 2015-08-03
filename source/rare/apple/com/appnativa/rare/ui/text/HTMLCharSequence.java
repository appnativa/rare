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

package com.appnativa.rare.ui.text;

import com.appnativa.rare.Platform;
import com.appnativa.rare.ui.UIFont;

public class HTMLCharSequence implements CharSequence {
  public static iHTMLEncoder htmlEncoder;
  protected Object           attributedText;
  protected String           text;

  public HTMLCharSequence(String text, Object attributedText) {
    super();
    this.text           = text;
    this.attributedText = attributedText;
  }

  @Override
  public char charAt(int index) {
    return text.charAt(index);
  }

  public static CharSequence checkSequence(CharSequence s, UIFont font) {
    if (s instanceof HTMLCharSequence) {
      return s;
    }

    if (!(s instanceof String)) {
      return s;
    }

    String str = (String) s;

    if (str.startsWith("<html>")) {
      if ((font != null) && (font.isStrikeThrough() || font.isUnderline())) {
        return getEncoder().parseHTML(str, font);
      } else {
        return getEncoder().parseHTML(str, null);
      }
    }

    if (font.isStrikeThrough() || font.isUnderline()) {
      return getEncoder().applyFont(str, font);
    }

    return s;
  }

  @Override
  public int length() {
    return text.length();
  }

  public static CharSequence parseHTMLSequence(CharSequence s, UIFont font) {
    String str = (String) s;

    if (str.startsWith("<html>")) {
      return getEncoder().parseHTML(str, font);
    }

    if (font.isStrikeThrough() || font.isUnderline()) {
      return getEncoder().applyFont(str, font);
    }

    return s;
  }

  @Override
  public CharSequence subSequence(int beginIndex, int endIndex) {
    return text.substring(beginIndex, endIndex);
  }

  @Override
  public String toString() {
    return text;
  }

  public Object getAttributedText() {
    return attributedText;
  }

  public native String getPlainText()    /*-[
       if(attributedText_) {
         return [(NSAttributedString*)attributedText_ string];
       }
       return text_;
     ]-*/
  ;

  public static String getPlainText(String html) {
    return getEncoder().getPlainText(html);
  }

  private static iHTMLEncoder getEncoder() {
    if (htmlEncoder == null) {
      htmlEncoder = (iHTMLEncoder) Platform.createObject("com.appnativa.rare.ui.text.HTMLEncoder");

      if (htmlEncoder == null) {
        if (Platform.isIOS() && (Platform.getOsVersion() >= 7)) {
          htmlEncoder = new NSAttributedStringHTMLEncoder();
        } else {
          htmlEncoder = new PlainTextHTMLEncoder();
        }
      }
    }

    return htmlEncoder;
  }
}
