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

package com.appnativa.rare.scripting;

import java.util.List;

/**
 * Interface for scripting engine providers for scripting shells.
 *
 * @author Don DeCoteau
 */
public interface iShellEngineProvider {

  /**
   * Gets an engine for the default scripting language
   *
   * @param sio a handle to an interface for scripting IO
   *
   * @return an interface to a scripting engine for the default scripting language
   */
  iScriptingEngine getDefaultEngine(iScriptingIO sio);

  /**
   * Gets an engine for the specified language
   *
   * @param language the language
   * @param sio a handle to an interface for scripting IO
   *
   * @return an interface to a scripting engine for the specified language
   */
  iScriptingEngine getEngine(String language, iScriptingIO sio);

  /**
   * Gets a list of all supported languages
   *
   * @return a list of all supported languages
   */
  List<String> getLanguages();

  /**
   * Gets a list of all the registered targets
   *
   * @return a list of all the registered targets
   */
  List<String> getTargets();

  /**
   * Gets a list of all the registered viewers
   *
   * @return a list of all the registered viewers
   */
  List<String> getViewers();

  /**
   * Gets a list of the names of all the widgets for the specified viewer
   *
   * @param viewer the name of the viewer
   *
   * @return a list of the names of all the widgets for the specified viewer
   */
  List<String> getWidgetNames(String viewer);
}
