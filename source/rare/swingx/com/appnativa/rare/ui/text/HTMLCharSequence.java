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

public class HTMLCharSequence implements CharSequence {
  protected String text;
  protected Object attributedText;

  public HTMLCharSequence(String text, Object attributedText) {
    super();
    this.text           = text;
    this.attributedText = attributedText;
  }

  @Override
  public char charAt(int index) {
    // TODO Auto-generated method stub
    return text.charAt(index);
  }

  @Override
  public int length() {
    return text.length();
  }

  public Object getAttributedText() {
    return attributedText;
  }

  @Override
  public CharSequence subSequence(int beginIndex, int endIndex) {
    return text.substring(beginIndex, endIndex);
  }

  @Override
  public String toString() {
    return text;
  }
}
