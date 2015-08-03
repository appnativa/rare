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

import com.appnativa.rare.iPlatformAppContext;
import com.appnativa.rare.spot.CollapsibleInfo;
import com.appnativa.rare.widget.iWidget;

public interface iComponentFactory {

  /**
   * Set the application context that the factory was created for
   *
   * @param app the application instance
   */
  void setAppContext(iPlatformAppContext app);

  /**
   * Gets a collapsible component
   *
   * @param context the widget context
   * @param cfg the configuration
   *
   * @return the view
   */
  iCollapsible getCollapsible(iWidget context, CollapsibleInfo cfg);
}
