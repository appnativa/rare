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

import com.appnativa.rare.Platform;
import com.appnativa.rare.platform.PlatformHelper;

/**
 *
 * @author Don DeCoteau
 */
public class UISoundHelper {
  static UISound success;
  static UISound error;

  public UISoundHelper() {}

  public static void beep() {
    PlatformHelper.beep();
  }

  public static void errorSound() {
    if (error == null) {
      String s = Platform.getResourceAsString("Rare.sound.error");

      if (s == null) {
        s = "rare_raw_error_beep";
      }

      try {
        error = Platform.getAppContext().getSound(s);
      } catch(Exception e) {
        Platform.ignoreException(null, e);
      }
    }

    if (error != null) {
      error.play();
    }
  }

  public static void successSound() {
    if (success == null) {
      String s = Platform.getResourceAsString("Rare.sound.success");

      if (s != null) {
        try {
          success = Platform.getAppContext().getSound(s);
        } catch(Exception e) {
          Platform.ignoreException(null, e);
        }
      }
    }

    if (success != null) {
      error.play();
    }
  }
}
