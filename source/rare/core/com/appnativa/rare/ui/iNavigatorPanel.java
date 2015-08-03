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

import com.appnativa.rare.ui.RenderableDataItem.IconPosition;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;
import com.appnativa.rare.ui.painter.iPlatformPainter;

public interface iNavigatorPanel {

  /** the type of panel */
  public static enum PanelType { HIERARCHICAL, TOGGLE, OPTION }

  /**
   * Gets the selected action
   *
   * @return the selected action
   */
  public UIAction getSelectedAction();

  /**
   * Activates the action for the button at the specified index
   *
   * @param index
   *          the index of the button
   */
  void activateButton(int index);

  /**
   * Adds a button to the panel
   *
   * @param button
   *          the button instance
   */
  void addButton(iActionComponent button);

  /**
   * Adds a button to the panel
   *
   * @param button
   *          the button's text
   *
   * @return the button instance
   */
  iActionComponent addButton(String button);

  /**
   * Adds a button to the panel
   *
   * @param a
   *          the button's action
   *
   * @return the button instance
   */
  iActionComponent addButton(UIAction a);

  /**
   * Adds a button to the panel
   *
   * @param button
   *          the button's text
   * @param icon
   *          the button's icon
   *
   * @return the button instance
   */
  iActionComponent addButton(String button, iPlatformIcon icon);

  /**
   * Perform the backup action for a hierarchical panel
   */
  void backup();

  /**
   * Click the button at the given index
   *
   * @param index
   *          the button's index
   */
  void click(int index);

  /**
   * Perform the home action for a hierarchical panel
   */
  void home();

  /**
   * Gets the index of the specified button instance
   *
   * @param button
   *          the button
   * @return the index of the button or -1
   */
  int indexOf(iActionComponent button);

  /**
   * Gets the index of the specified button
   *
   * @param name
   *          of the action to get the index of
   * @return the index of the button or -1
   */
  int indexOf(String name);

  /**
   * Removes a button from the panel
   *
   * @param index
   *          the index of the button to remove
   */
  void removeButton(int index);

  /**
   * Removes a button from the panel
   *
   * @param name
   *          the name of the action
   */
  void removeButton(String name);

  /**
   * Sets whether the action at the specified index is enabled
   *
   * @param index
   *          the index
   * @param enabled
   *          true to enable; false to disable
   */
  void setActionEnabled(int index, boolean enabled);

  /**
   * @param alwaysFireAction
   *          the alwaysFireAction to set
   */
  void setAlwaysFireAction(boolean alwaysFireAction);

  /**
   * Sets the border of this component.
   *
   * @param border
   *          the border to be rendered for this component
   */
  void setBorder(iPlatformBorder border);

  void setButtonsSameSize(boolean same);

  void setComponentPainter(iPlatformComponentPainter cp);

  /**
   * Sets whether or not this component is enabled.
   *
   * @param enabled
   *          true if this component should be enabled, false otherwise
   */
  void setEnabled(boolean enabled);

  void setFont(UIFont font);

  void setForeground(UIColor fg);

  /**
   * Sets the icon position
   *
   * @param position
   *          the icon position
   */
  void setIconPosition(IconPosition position);

  /**
   * Sets the insets for the buttons
   *
   * @param in
   *          the insets for the buttons
   */
  void setInsets(UIInsets in);

  /**
   * Sets the panel type
   *
   * @param panelType
   *          the panel type
   */
  void setPanelType(PanelType panelType);

  /**
   * Set the painter to use to paint a button's pressed state
   *
   * @param pressedPainter
   *          the painter
   */
  void setPressedPainter(iPlatformPainter pressedPainter);

  /**
   * Sets the selected button
   *
   * @param b
   *          the button to select
   */
  void setSelectedButton(iActionComponent b);

  /**
   * Sets the selected button
   *
   * @param name
   *          the name of the action
   */
  void setSelectedButton(String name);

  /**
   * Set the font that is used for the selected button
   *
   * @param f
   *          the font that is used for the selected button
   */
  void setSelectedFont(UIFont f);

  /**
   * Set the foreground color that is used for the selected button
   *
   * @param fg
   *          he foreground color that is used for the selected button
   */
  void setSelectedForeground(UIColor fg);

  /**
   * Sets the selected button
   *
   * @param index
   *          the index of the button to select
   */
  void setSelectedIndex(int index);

  /**
   * Set the painter to use to paint a selected button's pressed state
   *
   * @param selectedPainter
   *          the painter
   */
  void setSelectedPainter(iPlatformPainter selectedPainter);

  /**
   * @param color
   *          the separatorLineColor to set
   */
  void setSeparatorLineColor(UIColor color);

  /**
   * Sets whether to show only icons or to show both icons and text
   *
   * @param icon
   *          true if only icons will be shown; false otherwise
   */
  void setShowIconsOnly(boolean icon);

  /**
   * Get the action at the specified index
   *
   * @param index
   *          the index
   * @return the action at the specified index
   */
  UIAction getAction(int index);

  /**
   * Get the back button
   *
   * @return the back button or null if the panel is not hierarchical
   */
  iActionComponent getBackButton();

  /**
   * Get the button at the specified index
   *
   * @param index
   *          the index
   * @return the button at the specified index
   */
  iActionComponent getButton(int index);

  /**
   * Gets the icon position for button icons
   *
   * @return the icon position
   */
  IconPosition getIconPosition();

  /**
   * Gets the panel type
   *
   * @return the panel type
   */
  PanelType getPanelType();

  /**
   * Gets the currently selected button. For hierarchical panels it is always
   * the last button on the panel.
   *
   * @return the currently selected button.
   */
  iActionComponent getSelectedButton();

  /**
   * Gets the index of the currently selected button.
   *
   * @return the index of the currently selected button.
   */
  int getSelectedIndex();

  /**
   * Gets whether the action at the specified index is enabled
   *
   * @param index
   *          the index
   * @return true if it is enabled; false if it is disabled
   */
  boolean isActionEnabled(int index);

  /**
   * @return the alwaysFireAction
   */
  boolean isAlwaysFireAction();

  /**
   * Tests whether the panel is a hierarchical type panel
   *
   * @return true if it is; false otherwise
   */
  boolean isHiearchical();

  /**
   * Gets whether to show only icons or to show both icons and text
   *
   * @return true if only icons will be shown; false otherwise
   */
  boolean isShowIconsOnly();

  /**
   * Tests whether the panel is a toggle type panel
   *
   * @return true if it is; false otherwise
   */
  boolean isToggle();
}
