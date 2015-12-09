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

import java.util.HashMap;
import java.util.List;

import com.appnativa.rare.iFunctionCallback;
import com.appnativa.rare.widget.iWidget;

/**
 * An interface for form viewers.
 *
 * @author Don DeCoteau
 */
public interface iFormViewer extends iContainer {

  /**
   * Clears the form
   */
  public void clearForm();

  /**
   * Registers a widget with the form
   *
   * @param name
   *          the name of the widget
   * @param w
   *          the widget to register
   * @return the widget previously registered with the same name
   */
  public iWidget registerFormWidget(String name, iWidget w);

  /**
   * Registers a widget with the form
   *
   * @param w
   *          the widget to register
   * @return the widget previously registered with the same name
   */
  public iWidget registerFormWidget(iWidget w);

  /**
   * Sets the label component for a specified widget
   *
   * @param label
   *          the label
   * @param widgetName
   *          the name of the widget the label is for
   */
  public void registerLabelForWidget(Object label, String widgetName);

  /**
   * Reloads the form
   */
  public void reloadForm();

  /**
   * Resets the from to it initial state
   */
  public void resetForm();

  /**
   * Submits the form via a background task. If successful, the returned object
   * is an instance of and <code>ObjectHolder</code> where the <code>key</code>
   * is the mime type and the <code>value</code> is the content.
   *
   * <p>
   * Note: The callback mechanism is only used if there are no action handlers
   * attached to the form.
   * </p>
   *
   * @param cb
   *          the callback to call when the data has been retrieved (can be
   *          null)
   */
  public void submitForm(iFunctionCallback cb);

  /**
   * Unregisters a widget from the form
   *
   * @param name
   *          the name of the widget
   *
   * @return the widget that was registered with the specified name
   */
  public iWidget unregisterFormWidget(String name);

  /**
   * Unregisters a widget from the form
   *
   * @param widget
   *          the the widget
   *
   */
  public void unregisterFormWidget(iWidget widget);

  /**
   * Sets a submit attribute for the form
   *
   * @param name
   *          the name of the attribute
   * @param value
   *          the attribute's value
   */
  public void setSubmittAttribute(String name, Object value);

  /**
   * Gets the list of widgets registered with the form. That is the widgets that
   * has the viewer registered as their form viewer
   *
   * @return the list of widgets registered with the form.
   */
  public List<iWidget> getFormWidgets();
  
  /**
   * Gets a hash map of the values that would be submitted as part of an HTTP
   * POST form submission
   *
   * @return the hash widget name and values
   */
  public HashMap getHTTPValuesHash();
  
  /**
   * gets a submit attribute for the form
   *
   * @param name
   *          the name of the attribute
   * @return the attribute's value
   */
  public Object getSubmittAttribute(String name);

  /**
   * Returns whether the form was configured to retain the initial values of its
   * widgets
   *
   * @return true if the form was configured to retain the initial values of its
   *         widgets; false otherwise
   */
  public boolean isRetainInitialWidgetValues();
}
