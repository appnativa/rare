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

import com.appnativa.rare.scripting.iScriptHandler;

/**
 * Interface for debugging handlers
 *
 * @author Don DeCoteau
 */
public interface iDebugHandler {

  /** dispose of the debugger */
  void dispose();

  /**
   * Invoked when the scripting context has been initialized
   *
   * @param sm the script manager
   * @param se the scripting engine
   * @param sc the script context
   */
  void scriptContextInit(iScriptHandler sm, Object se, Object sc);

  /**
   * Invoked when the scripting engine has been initialized
   *
   * @param sm the script manager
   * @param se the scripting engine
   */
  void scriptEngineInit(iScriptHandler sm, Object se);

  /**
   * Invoked when the scripting context has been initialized
   *
   * @param sm the script manager
   * @param params a parameter string for the handler
   */
  void scriptManagerInit(iScriptHandler sm, String params);
}
