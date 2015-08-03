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

/**
 *
 *  @author Don DeCoteau
 */
public class UIClipboard {
  public UIClipboard() {}

  public static void clear() {}

  public static boolean setContent(java.util.Map map) {
    return false;
  }

  public static boolean hasUrl() {
    return false;
  }

  public static boolean hasString() {
    return false;
  }

  public static boolean hasRtf() {
    return false;
  }

  public static boolean hasImage() {
    return false;
  }

  public static boolean hasHtml() {
    return false;
  }

  public static boolean hasFiles() {
    return false;
  }

  public static String getUrl() {
    return null;
  }

  public static String getString() {
    return null;
  }

  public static String getRtf() {
    return null;
  }

  public static com.appnativa.rare.ui.UIImage getImage() {
    return null;
  }

  public static String getHtml() {
    return null;
  }

  public static java.util.List getFiles() {
    return null;
  }

  public static java.util.List getAvailableFlavors() {
    return null;
  }
}
