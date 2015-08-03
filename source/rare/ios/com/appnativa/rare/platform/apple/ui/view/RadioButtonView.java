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

package com.appnativa.rare.platform.apple.ui.view;

import com.appnativa.rare.Platform;
import com.appnativa.rare.ui.ColorUtils;

/*-[
 #import "RAREUIControl.h"
 ]-*/
public class RadioButtonView extends CustomButtonView {
  public RadioButtonView() {
    super();
    setButtonStyle();
    setDefaultIcons();
  }

  protected void setDefaultIcons() {
    if (ColorUtils.getForeground().isDarkColor()) {
      setIcon(Platform.getResourceAsIcon("Rare.icon.radiobutton.off.light"));
      setPressedIcon(Platform.getResourceAsIcon("Rare.icon.radiobutton.off.pressed.light"));
      setDisabledIcon(Platform.getResourceAsIcon("Rare.icon.radiobutton.off.disabled.light"));
      setSelectedIcon(Platform.getResourceAsIcon("Rare.icon.radiobutton.on.light"));
      setPressedSelectedIcon(Platform.getResourceAsIcon("Rare.icon.radiobutton.on.pressed.light"));
      setDisabledSelectedIcon(Platform.getResourceAsIcon("Rare.icon.radiobutton.on.disabled.light"));
    } else {
      setIcon(Platform.getResourceAsIcon("Rare.icon.radiobutton.off.dark"));
      setPressedIcon(Platform.getResourceAsIcon("Rare.icon.radiobutton.off.pressed.dark"));
      setDisabledIcon(Platform.getResourceAsIcon("Rare.icon.radiobutton.off.disabled.dark"));
      setSelectedIcon(Platform.getResourceAsIcon("Rare.icon.radiobutton.on.dark"));
      setPressedSelectedIcon(Platform.getResourceAsIcon("Rare.icon.radiobutton.on.pressed.dark"));
      setDisabledSelectedIcon(Platform.getResourceAsIcon("Rare.icon.radiobutton.on.disabled.dark"));
    }
  }

  private native void setButtonStyle()
  /*-[
   ((RAREUIControl*)proxy_).radioButtonStyle= YES;
   ((RAREUIControl*)proxy_).isToggle= YES;
   ]-*/
  ;
}
