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

package com.appnativa.rare.viewer;

import com.appnativa.rare.spot.Widget;
import com.appnativa.rare.ui.UISelectionModelGroup;
import com.appnativa.rare.ui.iListHandler;
import com.appnativa.rare.ui.iParentComponent;
import com.appnativa.rare.widget.iWidget;

import java.util.List;
import java.util.Map;

/**
 * An interface for widget containers
 *
 * @author Don DeCoteau
 */
public interface iContainer extends iViewer {

  /**
   * Enumeration of possible layouts
   */
  public static enum Layout {
    ABSOLUTE, TABLE, FORMS, FLOW, CUSTOM
  }

  /**
   * Adds the named widget to a selection group.
   *
   * @param name the name of the widget
   * @param comp the list component the model is for
   * @param model the selection model
   * @param position the position at which the model should be inserted
   *
   * @return the group the model was added to
   */
  public UISelectionModelGroup addToSelectionGroup(String name, iListHandler comp, Object model, int position);

  /**
   * Adds a widget to the form
   *
   * @param cfg the widget's configuration
   *
   * @return the widget that was added
   */
  public iWidget addWidget(Widget cfg);

  /**
   * Adds a widget to the form
   *
   * @param panel the panes that the widget should be added to
   * @param cfg the widget's configuration
   * @param fm the type of form layout
   *
   * @return the widget that was added
   */
  public iWidget addWidget(iParentComponent panel, Widget cfg, Layout fm);

  /**
   * Handles the focusing of a widget
   * @param from the widget the focus is being moved from
   * @param next true to focus the next widget; false to focus the previous widget
   * @return true if the focusing was handled; false otherwise
   */
  public boolean handleFocus(iWidget from, boolean next);

  /**
   * Creates a new widget from a widget configuration
   *
   * @param cfg the configuration
   *
   * @return the newly created widget
   */
  public iWidget createWidget(Widget cfg);

  @Override
  public boolean isTextDirectionSet();

  /**
   * Adds a widget to this widget's hierarchy
   *
   * @param widget the widget to add
   * @param constraints the constraints
   * @param position the position at which to add the widget
   *
   * @throws UnsupportedOperationException
   */
  void addWidget(iWidget widget, Object constraints, int position);

  /**
   * Removes the named widget from a selection group.
   *
   * @param name the name of the widget
   * @param model the selection model
   */
  void removeFromSelectionGroup(String name, Object model);

  /**
   * Removes a widget to this widget's hierarchy
   *
   * @param widget the widget to remove
   *
   * @throws UnsupportedOperationException
   */
  void removeWidget(iWidget widget);

  /**
   * Gets the disable behavior for the container
   *
   * @return the disable behavior for the container
   */
  DisableBehavior getDisableBehavior();

  /**
   * Retrieves the widget at the specified position
   *
   * @param index the position of the widget
   * @return the widget
   */
  iWidget getWidget(int index);

  /**
   * Retrieves the widget with the specified name.
   *
   * @param name the name of the widget
   * @return the widget or null if no such widget exists
   */
  iWidget getWidget(String name);

  /**
   * Retrieves the widget that is represented by the specified path name. A forward slash
   * is used as a separator in path names. If the path does no start with a forward slash then
   * the current viewer and its children are used to search for the widget.
   * If the path starts with a forward slash the search starts with the main window
   *
   * @param path the path name of the widget
   * @return the widget or null if no such widget exists
   */
  iWidget getWidgetFromPath(String path);

  /**
   * Gets the list of widgets that this viewer contains
   *
   * @return the list of the widgets that this viewer contains
   */
  List<iWidget> getWidgetList();

  /**
   * Gets a list of names of the widgets that this viewer contains
   *
   * @return a list of names of the widgets that this viewer contains
   */
  List<String> getWidgetNames();

  /**
   * Gets the map of widgets that this viewer contains
   *
   * @return the map of the widgets that this viewer contains
   */
  Map<String, iWidget> getWidgets();

  /**
   * Returns whether the container is a focus hog.
   * A focus hog prevents the user from leaving the container using keyboard
   * navigation keys.
   *
   * @return true if it hog's the focus; false otherwise
   */
  public boolean isHogFocus();

  /**
   * Sets whether the container is a focus hog.
   * A focus hog prevents the user from leaving the container using keyboard
   * navigation keys.
   *
   * @param hog true to hog the focus ; false otherwise
   */
  public void setHogFocus(boolean hog);
}
