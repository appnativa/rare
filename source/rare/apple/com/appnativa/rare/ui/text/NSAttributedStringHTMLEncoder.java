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

import com.appnativa.rare.ui.UIFont;

public class NSAttributedStringHTMLEncoder implements iHTMLEncoder {
  public NSAttributedStringHTMLEncoder() {}

  @Override
  public CharSequence applyFont(String text, UIFont font) {
    Object as = createAttributedString(text);

    font.addToAttributedString(as, 0, -1);

    return new HTMLCharSequence(text, as);
  }

  @Override
  public CharSequence parseHTML(String html, UIFont base) {
    Object as = parseHTMLEx(html);

    if (base != null) {
      base.addToAttributedString(as, 0, -1);
    }

    return new HTMLCharSequence(html, as);
  }

  @Override
  public native String getPlainText(String html)
  /*-[
    NSAttributedString* s=(NSAttributedString*)[self parseHTMLExWithNSString: html];
    return [s string];
  ]-*/
  ;

  protected native Object parseHTMLEx(String html)
  /*-[
    NSData* data=[html dataUsingEncoding:NSUTF8StringEncoding];
    return [[NSMutableAttributedString alloc]   initWithData:data
                                              options:@{NSDocumentTypeDocumentAttribute:NSHTMLTextDocumentType}
                                   documentAttributes:nil
                                                error:nil];
   ]-*/
  ;

  private static native Object createAttributedString(String text)
  /*-[
    NSMutableAttributedString* s=[NSMutableAttributedString new];
    [s.mutableString appendString: text];
    return s;
  ]-*/
  ;
}
