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
import java.util.Map;

/**
 * Interface for scripting engines
 * @author Don DeCoteau
 */
public interface iScriptingEngine {

  /**
   * Enables/Disables script tracing
   *
   * @param arg1 on/off or null to toggle
   */
  void trace(String arg1);

  /**
   * Dumps the contents of the specified script to the engines output device
   *
   * @param path
   */
  void dumpScript(String path);

  /**
   * Gets a map of all the existing environment variables
   *
   * @return a map of all the existing environment variables
   */
  Map envVariables();

  /**
   * Evaluates a string of commands
   *
   * @param cmds the commands to evaluate
   *
   * @throws Exception
   */
  void eval(String cmds) throws Exception;

  /**
   * Gets the value of a variable within the environment
   *
   * @param name the name of the variable
   *
   * @return the value of the variable or null if the variable does no exist
   */
  Object get(String name);

  /**
   * Gets a map of all the existing global variables
   *
   * @return a map of all the existing global variables
   */
  Map getGlobalVariables();

  /**
   * Get the name of the language that this engine is for
   *
   * @return the name of the language that this engine is for
   */
  String getLanguageName();

  /**
   * Get the full name of the language that this engine is for
   *
   * @return full the name of the language that this engine is for
   */
  String getLanguageFullName();

  /**
   * Gets a list of all loaded scripts
   *
   * @return a list of all loaded scripts
   */
  List<String> getLoadedScripts();

  /**
   * Loads a script into the environment
   *
   *
   * @param path
   */
  void loadScript(String path);
}
