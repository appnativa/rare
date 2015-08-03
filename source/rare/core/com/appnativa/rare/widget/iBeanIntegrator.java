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

import com.appnativa.rare.net.iURLConnection;
import com.appnativa.rare.spot.Widget;
import com.appnativa.rare.ui.aWidgetListener;
import com.appnativa.rare.ui.iPlatformComponent;

/**
 * An interface for runtime configuration of beans.
 *
 * @author Don DeCoteau
 */
public interface iBeanIntegrator {

  /**
   * The bean widget that is invoking the configurator
   *
   * @param w the bean widget
   * @param cfg the bean configuration object
   */
  void configure(iWidget w, Widget cfg);

  /**
   * Called when the widget is being disposed
   */
  void disposing();

  /**
   * Handles a url connection containing data for the bean
   *
   * @param conn the connection
   */
  void handleConnection(iURLConnection conn);

  /**
   * Allows the bean to configure listeners
   *
   * @param l the widget listener
   */
  void initializeListeners(aWidgetListener l);

  /**
   * Whether the bean wants the URL connection
   *
   * @return true if the bean wants the URL connection; false otherwise
   */
  boolean wantsURLConnection();

  /**
   * Sets the widgets value based on a value that would be returned from
   * {@link #getHTTPFormValue()}
   *
   * @param value the value to set
   */
  void setFromHTTPFormValue(Object value);

  /**
   * Sets the bean value
   *
   * @param value the value
   */
  void setValue(Object value);

  /**
   * Get the component that contains all visual elements for the bean
   *
   * @return the bean container
   */
  iPlatformComponent getContainer();

  /**
   * Gets the component that actually manages the widgets data
   *
   * @return the component that actually manages the widgets data
   */
  iPlatformComponent getDataComponent();

  /**
   * Gets the HTTP form value for the widget.
   *
   * @return the HTTP form value for the widget
   */
  Object getHTTPFormValue();

  /**
   * Returns the selected items or <code>null</code> if no
   * items are selected.
   *
   * @return the selected items or <code>null</code> if no
   * items are selected.
   */
  Object[] getSelectedObjects();

  /**
   * Gets the bean's selection
   *
   * @return the bean's selection
   */
  Object getSelection();

  /**
   * Gets the bean's value
   *
   * @return the bean's value
   */
  Object getValue();
}
