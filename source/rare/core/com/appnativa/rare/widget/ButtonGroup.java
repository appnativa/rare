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

package com.appnativa.rare.widget;

import com.appnativa.rare.widget.aGroupableButton;
import com.appnativa.util.IdentityArrayList;

/**
 * A class that manages the selection state of a group of buttons
 *
 * allowing only one button in the group to be selected at a time
 *
 * @author Don DeCoteau
 *
 */
public class ButtonGroup {
  protected IdentityArrayList<aGroupableButton> buttons = new IdentityArrayList<aGroupableButton>();
  protected aGroupableButton                    selectedButton;
  protected String                              groupName;

  /**
   * Creates a new group
   *
   * @param groupName
   *          the name of the group
   */
  public ButtonGroup(String groupName) {
    super();
    this.groupName = groupName;
  }

  public void add(aGroupableButton b) {
    buttons.add(b);
  }

  /**
   * Clears the current selection
   */
  public void clearSelection() {
    if (selectedButton != null) {
      selectedButton.setSelected(false);
      selectedButton = null;
    }
  }

  /**
   * Gets the name of the group
   *
   * @return the name of the group
   */
  public String getName() {
    return groupName;
  }

  /**
   * Gets the currently selected button for the group
   *
   * @return the currently selected button for the group
   */
  public aGroupableButton getSelectedButton() {
    return selectedButton;
  }

  /**
   * Removes the specified button from the group. The button will be deselected
   * (if it is currently selected)
   *
   * @param button
   *          the button
   */
  public void remove(aGroupableButton button) {
    if (button != null) {
      if (button == selectedButton) {
        selectedButton = null;
        button.setSelected(false);
      }

      buttons.remove(button);
    }
  }

  /**
   * Removes the specified button from the group. No action will be taken on the
   * button
   *
   * @param button
   *          the button
   */
  public void removeEx(aGroupableButton button) {
    if (button != null) {
      if (button == selectedButton) {
        selectedButton = null;
      }

      buttons.remove(button);
    }
  }

  /**
   * Called when the selection changes for a button in the group
   *
   * @param button
   *          the button whose state changed
   * @param selected
   *          true if the button was selected; false otherwise
   */
  public void selectionChanged(aGroupableButton button, boolean selected) {
    aGroupableButton clear = selected
                             ? selectedButton
                             : null;

    if (button != selectedButton) {
      if (selected) {
        selectedButton = button;
      } else {
        return;
      }
    } else {
      if (selected ||!button.allowDeselection) {
        return;
      }
    }

    if (clear != null) {
      clear.setSelected(false);
    }
  }
}
