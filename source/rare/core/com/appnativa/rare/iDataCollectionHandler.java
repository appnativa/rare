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

import com.appnativa.rare.net.ActionLink;
import com.appnativa.rare.spot.DataCollection;

import java.util.Map;

/**
 * A interface representing collection handlers.
 *
 * @author Don DeCoteau
 */
public interface iDataCollectionHandler {

  /**
   * Create a collection from the configuration
   *
   * @param context the context
   * @param collection the data collection configuration
   * @param cb the callback to invoke when the collection has been loaded (passing null will load the collection inline)
   *
   * @return a collection
   */
  iDataCollection createCollection(iPlatformAppContext context, DataCollection collection, iFunctionCallback cb);

  /**
   * Create a collection from the specified URL
   *
   * @param context the context
   * @param name the name of the collection
   * @param link the link to use to create the collection
   * @param attributes  attributes for the collection
   * @param cb the callback to invoke when the collection has been loaded (passing null will load the collection inline)
   *
   * @return a collection
   */
  iDataCollection createCollection(iPlatformAppContext context, String name, ActionLink link, Map attributes,
                                   iFunctionCallback cb);

  /**
   * Disposes of the handler
   *
   * @param context the context
   */
  void dispose(iPlatformAppContext context);
}
