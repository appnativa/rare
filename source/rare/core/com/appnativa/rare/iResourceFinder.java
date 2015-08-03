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

package com.appnativa.rare;

import com.appnativa.rare.ui.UIAction;
import com.appnativa.rare.ui.UIImage;
import com.appnativa.rare.ui.UIImageIcon;
import com.appnativa.rare.ui.UIProperties;
import com.appnativa.rare.widget.iWidget;

import java.net.URL;

/**
 * An interface for locating application level resources
 *
 * @author Don DeCoteau
 */
public interface iResourceFinder {

  /**
   * Gets a previously registered action
   *
   * @param name the name of the action
   *
   * @return the named action or null if the action is not registered
   *
   */
  public UIAction getAction(String name);

  /**
   * Get the URL for the given resource path
   *
   * @param path the resource path
   *
   * @return the URL for the given resource path
   */
  public URL getResource(String path);

  /**
   * Gets the resource icon with the specified name
   *
   * @param name the name of the icon
   *
   * @return the resource icon with the specified name
   */
  public UIImageIcon getResourceAsIcon(String name);

  /**
   * Gets the resource image with the specified name
   *
   * @param name the name of the image
   *
   * @return the resource image with the specified name
   */
  public UIImage getResourceAsImage(String name);

  /**
   * Gets the resource string with the specified name
   *
   * @param name the name of the string
   *
   * @return the resource string
   */
  public String getResourceAsString(String name);

  /**
   * Gets the template handler
   *
   * @param context the context
   * @return the template handler for the specified context
   */
  public TemplateHandler getTemplateHandler(iWidget context);

  /**
   * Sets the template handler
   *
   * @param context the context
   * @param th the template handler
   */
  public void setTemplateHandler(iWidget context, TemplateHandler th);

  /**
   * Gets the UI defaults
   * @return  the UI defaults
   */
  public UIProperties getUIDefaults();

  public String getApplicationRoot();
}
