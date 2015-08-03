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

import com.appnativa.rare.ui.RenderableDataItem.HorizontalAlign;
import com.appnativa.rare.ui.event.iExpandedListener;
import com.appnativa.rare.ui.event.iExpansionListener;

import java.util.Map;

/**
 * An interface representing collapsible panes.
 *
 * @author Don DeCoteau
 */
public interface iCollapsible {

  /**
   * Adds a expanded listener
   *
   * @param l the listener to add
   */
  public void addExpandedListener(iExpandedListener l);

  /**
   * Adds a expansion listener
   *
   * @param l the listener to add
   */
  public void addExpansionListener(iExpansionListener l);

  /**
   * Collapses the pane
   */
  public void collapsePane();

  /**
   * DIspose of the pane
   */
  public void disposePane();

  /**
   * Expands the pane
   */
  public void expandPane();

  /**
   * Removes a expanded listener
   *
   * @param l the listener to remove
   */
  public void removeExpandedListener(iExpandedListener l);

  /**
   * Removes a expansion listener
   *
   * @param l the listener to remove
   */
  public void removeExpansionListener(iExpansionListener l);

  /**
   * Toggles the panes expanded/collapsed state
   */
  public void togglePane();

  /**
   * Sets the pane's expanded/collapsed state
   *
   * @param collapsed true if the pane is collapsed; false otherwise
   */
  public void setCollapsed(boolean collapsed);

  /**
   * Sets the content for the pane
   *
   * @param c the content for the pane
   */
  public void setContent(iPlatformComponent c);

  /**
   * Sets whether events should be fired when the pane changes expansion state
   *
   * @param enabled true for enabled; false otherwise
   */
  public void setEventsEnabled(boolean enabled);

  /**
   * Sets whether the pane should automatically expand what a drop-able item hovers
   * over it
   *
   * @param expand true if the pane should automatically expand; false otherwise
   */
  public void setExpandOnDragOver(boolean expand);

  /**
   * Set the background color of the pane's title
   *
   * @param bg the background color
   */
  public void setTitleBackground(UIColor bg);

  /**
   * Sets the pane's title border
   *
   * @param b the border for the title
   */
  public void setTitleBorder(iPlatformBorder b);

  /**
   * Set the font of the pane's title
   *
   * @param font the font
   */
  public void setTitleFont(UIFont font);

  /**
   * Set the foreground color of the pane's title
   *
   * @param fg the foreground color
   */
  public void setTitleForeground(UIColor fg);

  /**
   * Sets the pane's title icon
   *
   * @param icon the title icon
   */
  public void setTitleIcon(iPlatformIcon icon);

  /**
   * Sets the pane's title
   *
   * @param title the title
   */
  public void setTitleText(CharSequence title);

  /**
   * Sets the horizontal alignment of the pane's title text
   *
   * @param align the horizontal alignment
   */
  public void setTitleTextHAlignment(HorizontalAlign align);

  /**
   * Sets whether the the UI provides a mechanism for a user to
   * expand/collapse the pane
   *
   * @param uc true to provide a user controllable UI; false otherwise
   */
  public void setUserControllable(boolean uc);

  /**
   * Gets the component that represents the pane's context
   *
   * @return the component that represents the pane's context
   */
  public iPlatformComponent getContent();

  /**
   * Gets the component the represents the pane
   *
   * @return the component the represents the pane
   */
  public iParentComponent getPane();

  /**
   * Gets the pane's title
   *
   * @return the pane's title
   */
  public CharSequence getTitle();

  /**
   * Gets the pane's title component
   *
   * @return the pane's title component
   */
  public iPlatformComponent getTitleComponent();

  /**
   *   Gets the pane's title icon
   *
   *   @return the pane's title icon
   */
  public iPlatformIcon getTitleIcon();

  /**
   * Gets whether events should be fired when the pane changes expansion state
   *
   * @return true for enabled; false otherwise
   */
  public boolean isEventsEnabled();

  /**
   * Returns whether the pane is expanded
   *
   * @return true if the pane is expanded; false otherwise
   */
  public boolean isExpanded();

  /**
   * Returns whether the the UI provides a mechanism for a user to
   * expand/collapse the pane
   *
   * @return true if it provides a user controllable UI; false otherwise
   */
  public boolean isUserControllable();

  /**
   * Sets the icon to display to collapse the pane
   * The user will click on this icon to collapse the pane.
   *
   * @param icon the icon to display to collapse the pane
   */
  void setCollapseIcon(iPlatformIcon icon);

  /**
   * Sets the tool tip to display to collapse the pane
   * This is uses when the pane is expanded
   *
   * @param s the tool tip to display to collapse the pane
   */
  void setCollapseTip(String s);

  /**
   * Sets a string to use for the title when the pane is collapsed
   *
   * @param s the string to use for the title when the pane is collapsed
   */
  void setCollapsedTitle(String s);

  /**
   * Sets the icon to display to expand the pane.
   * The user will click on this icon to expand the pane.
   *
   * @param icon the icon to display to expand the pane
   */
  void setExpandIcon(iPlatformIcon icon);

  /**
   * Sets the tool tip to display to expand the pane
   * This is used when the pane is collapsed
   *
   * @param s the tool tip to display to expand the pane
   */
  void setExpandTip(String s);

  /**
   * Sets whether or not the title is shown
   * @param show true to show; false otherwise
   */
  void setShowTitle(boolean show);

  /**
   * Sets whether the title icon is to be placed on the left
   *
   * @param b true to place the icon on the left; false to place the icon on the far right
   */
  void setTitleIconOnLeft(boolean b);

  /**
   * Sets whether the title bar should be opaque
   *
   * @param b true if the title bar is to be opaque (and rendered); false otherwise
   */
  void setTitleOpaque(boolean b);

  /**
   * Set that object that will provide expand/collapse titles for the pane
   * @param tp the title provider
   */
  void setTitleProvider(iTitleProvider tp);

  /**
   * Sets whether the pane should toggle when the title is clicked once
   * instead of on a double click
   *
   * @param toggle true to toggle on a single click; false to toggle only on a double click
   */
  void setToggleOnTitleSingleClick(boolean toggle);

  /**
   * Sets whether the pane should use animation when it expands or collapses
   *
   * @param animate true to animate; false otherwise
   */
  void setAnimateTransitions(boolean animate);

  /**
   * Sets the options for the transition animator
   * @param options the options for the transition animator
   */
  void setAnimatorOptions(Map options);

  /**
   * An interface for providing title for collapsible panes
   */
  public static interface iTitleProvider {

    /**
     * Get the title for the specified component when the pane is collapsed
     *
     * @param pane the hosting pane
     * @param c the hosted component
     * @return the title
     */
    String getCollapsedTitle(iCollapsible pane, iPlatformComponent c);

    /**
     * Get the title for the specified component when the pane is expanded
     *
     * @param pane the hosting pane
     * @param c the hosted component
     * @return the title
     */
    String getExpandedTitle(iCollapsible pane, iPlatformComponent c);
  }


  public void setUseChevron(boolean b);
}
