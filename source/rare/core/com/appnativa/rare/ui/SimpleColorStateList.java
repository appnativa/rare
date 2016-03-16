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

import com.appnativa.rare.ui.iPaintedButton.ButtonState;
import com.appnativa.util.CharScanner;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class SimpleColorStateList {
  private UIColor defaultColor;
  private UIColor disabledColor;
  private UIColor focusedColor;
  private UIColor pressedColor;
  private UIColor rolloverColor;
  private UIColor selectedColor;
  private UIColor selectedDisabledColor;
  private UIColor selectedPressedColor;

  public SimpleColorStateList() {
    this.defaultColor = ColorUtils.getForeground();
  }

  public SimpleColorStateList(UIColor defaultColor, UIColor disabledColor) {
    this.defaultColor  = defaultColor;
    this.disabledColor = disabledColor;
  }

  public SimpleColorStateList(UIColor defaultColor, UIColor disabledColor, UIColor selectedColor,
                              UIColor selectedDisabledColor) {
    this.defaultColor          = defaultColor;
    this.disabledColor         = disabledColor;
    this.selectedColor         = selectedColor;
    this.selectedDisabledColor = selectedDisabledColor;
  }

  public static SimpleColorStateList create(CharScanner sc, Map<String, String> map) {
    String                          color;
    Entry<String, String>           e;
    Iterator<Entry<String, String>> it = map.entrySet().iterator();
    UIColor                         c;
    List<String>                    list = new ArrayList<String>(3);
    SimpleColorStateList            cs   = new SimpleColorStateList();

    while(it.hasNext()) {
      e     = it.next();
      color = e.getValue();
      c     = ColorUtils.getColor(color);

      if (c == null) {
        c = ColorUtils.TRANSPARENT_COLOR;
      }

      color = e.getKey();
      sc.reset(color);

      if ("normal".equals(color)) {
        cs.defaultColor = c;

        continue;
      }
      list.clear();
      list = sc.getTokens(',', true, list);

      for (String s : list) {
        if (s.equals("pressed")) {
          cs.pressedColor = c;
        } else if (s.equals("hover") || s.equalsIgnoreCase("rollover")) {
          cs.rolloverColor = c;
        } else if (s.equals("focused")) {
          cs.focusedColor = c;
        } else if (s.equals("disabled") || s.equalsIgnoreCase("not_enabled")) {
          cs.disabledColor = c;
        } else if (s.equals("selected_disabled") || s.equals("disabled_selected")) {
          cs.selectedDisabledColor = c;
        } else if (s.equals("selected_pressed") || s.equals("pressed_selected")) {
          cs.selectedPressedColor = c;
        } else if (s.equals("selected")) {
          cs.selectedColor = c;
        }
      }
    }

    return cs;
  }

  public void setDefaultColor(UIColor defaultColor) {
    this.defaultColor = defaultColor;
  }

  public void setDisabledColor(UIColor disabledColor) {
    this.disabledColor = disabledColor;
  }

  public void setFocusedColor(UIColor focusedColor) {
    this.focusedColor = focusedColor;
  }

  public void setRolloverColor(UIColor rolloverColor) {
    this.rolloverColor = rolloverColor;
  }

  public void setSelectedColor(UIColor selectedColor) {
    this.selectedColor = selectedColor;
  }

  public void setSelectedDisabledColor(UIColor selectedDisabledColor) {
    this.selectedDisabledColor = selectedDisabledColor;
  }

  public UIColor getColor(ButtonState state) {
    switch(state) {
      case DISABLED_SELECTED :
        return getSelectedDisabledColor();

      case DISABLED :
        return getDisabledColor();

      case PRESSED :
        return getSelectedPressedColor();

      case PRESSED_SELECTED :
        return getPressedColor();

      case SELECTED :
        return getSelectedColor();

      case ROLLOVER :
        return getRolloverColor();

      default :
        return getDefaultColor();
    }
  }

  public UIColor getDefaultColor() {
    return defaultColor;
  }

  public UIColor getDisabledColor() {
    return (disabledColor == null)
           ? defaultColor.getDisabledColor()
           : disabledColor;
  }

  public UIColor getFocusedColor() {
    return (focusedColor == null)
           ? defaultColor
           : focusedColor;
  }

  public UIColor getPressedColor() {
    return (pressedColor == null)
           ? getSelectedColor()
           : pressedColor;
  }

  public UIColor getRolloverColor() {
    return (rolloverColor == null)
           ? defaultColor
           : rolloverColor;
  }

  public UIColor getSelectedColor() {
    return (selectedColor == null)
           ? defaultColor
           : selectedColor;
  }

  public UIColor getSelectedDisabledColor() {
    return (selectedDisabledColor == null)
           ? getDisabledColor()
           : selectedDisabledColor;
  }

  public UIColor getSelectedPressedColor() {
    return (selectedPressedColor == null)
           ? getPressedColor()
           : selectedPressedColor;
  }

  public void setSelectedPressedColor(UIColor selectedPressedColor) {
    this.selectedPressedColor = selectedPressedColor;
  }
  
  public void setPressedColor(UIColor pressedColor) {
    this.pressedColor = pressedColor;
  }
}
