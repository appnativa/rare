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
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.widget.aCheckBoxWidget.State;

/*-[
 #import "RAREUIControl.h"
 ]-*/
public class CheckBoxView extends CustomButtonView {
  boolean isSwitch;
  boolean tristate;

  public CheckBoxView(boolean onOffSwitch) {
    super();

    if (onOffSwitch) {
      isSwitch = true;
      makeSwitch();
    } else {
      setButtonStyle();
      setDefaultIcons();
    }
  }

  public void makeTriState() {
    if (!tristate) {
      iPlatformIcon icon;
      iPlatformIcon picon;

      if (ColorUtils.getForeground().isDarkColor()) {
        icon  = Platform.getResourceAsIcon("Rare.icon.checkbox.indeterminate.light");
        picon = Platform.getResourceAsIcon("Rare.icon.checkbox.indeterminate.pressed.light");
      } else {
        icon  = Platform.getResourceAsIcon("Rare.icon.checkbox.indeterminate.dark");
        picon = Platform.getResourceAsIcon("Rare.icon.checkbox.indeterminate.pressed.dark");
      }

      makeTriStateCheckbox(icon, picon);
      this.tristate = true;
    }
  }

  public void setChecked(boolean checked) {
    setSelected(checked);
  }

  public native void setIndeterminate()
  /*-[
    if(tristate_) {
      [((RAREUIControl*)proxy_) setIndeterminate];
    }
  ]-*/
  ;

  public void setState(State state) {
    switch(state) {
      case DESELECTED :
        setSelected(false);

        break;

      case SELECTED :
        setSelected(true);

        break;

      default :
        if (isTriState()) {
          setIndeterminate();
        } else {
          setSelected(true);
        }

        break;
    }
  }

  public State getState() {
    if (isIndeterminate()) {
      return State.INDETERMINATE;
    }

    return isSelected()
           ? State.SELECTED
           : State.DESELECTED;
  }

  public boolean isChecked() {
    return isSelected();
  }

  public native boolean isIndeterminate()
  /*-[
    return [((RAREUIControl*)proxy_) isIndeterminate];
   ]-*/
  ;

  public boolean isTriState() {
    return tristate;
  }

  protected native void makeSwitch()
  /*-[
    return [((RAREUIControl*)proxy_) makeSwitch];
  ]-*/
  ;

  protected native void makeTriStateCheckbox(iPlatformIcon icon, iPlatformIcon pressedIcon)
  /*-[
    [((RAREUIControl*)proxy_) makeTriState: icon pressedIcon: pressedIcon];
  ]-*/
  ;

  protected void setDefaultIcons() {
    if (ColorUtils.getForeground().isDarkColor()) {
      setIcon(Platform.getResourceAsIcon("Rare.icon.checkbox.off.light"));
      setPressedIcon(Platform.getResourceAsIcon("Rare.icon.checkbox.off.pressed.light"));
      setDisabledIcon(Platform.getResourceAsIcon("Rare.icon.checkbox.off.disabled.light"));
      setSelectedIcon(Platform.getResourceAsIcon("Rare.icon.checkbox.on.light"));
      setPressedSelectedIcon(Platform.getResourceAsIcon("Rare.icon.checkbox.on.pressed.light"));
      setDisabledSelectedIcon(Platform.getResourceAsIcon("Rare.icon.checkbox.on.disabled.light"));
    } else {
      setIcon(Platform.getResourceAsIcon("Rare.icon.checkbox.off.dark"));
      setPressedIcon(Platform.getResourceAsIcon("Rare.icon.checkbox.off.pressed.dark"));
      setDisabledIcon(Platform.getResourceAsIcon("Rare.icon.checkbox.off.disabled.dark"));
      setSelectedIcon(Platform.getResourceAsIcon("Rare.icon.checkbox.on.dark"));
      setPressedSelectedIcon(Platform.getResourceAsIcon("Rare.icon.checkbox.on.pressed.dark"));
      setDisabledSelectedIcon(Platform.getResourceAsIcon("Rare.icon.checkbox.on.disabled.dark"));
    }
  }

  private native void setButtonStyle()
  /*-[
    ((RAREUIControl*)proxy_).isToggle= YES;
  ]-*/
  ;
}
