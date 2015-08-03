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

import com.appnativa.rare.scripting.Functions;
import com.appnativa.rare.widget.iWidget;

import java.text.ParseException;

/**
 * An interface for objects that handle the execution of functions that are
 * embedded in strings
 *
 * @author Don DeCoteau
 */
public interface iFunctionHandler {

  /**
   * Executes a function
   *
   * @param w the widget context
   * @param function the unparsed function (including all parameters)
   *
   * @return the results of the function
   *
   * @throws ParseException if the specified string is not properly formatted
   */
  String execute(iWidget w, String function) throws ParseException;

  /**
   * Executes a function
   *
   * @param w the widget context
   * @param name the name of the function
   * @param parameters the function parameters
   *
   * @return the results of the function
   */
  String executeFunction(iWidget w, String name, String[] parameters);

  /**
   * Gets the functions object
   * @return  the functions object
   */
  Functions getFunctions();
}
