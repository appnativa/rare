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

import com.appnativa.util.iCancelable;

/**
 * Class for specifying background worker tasks
 *
 * @author Don DeCoteau
 */
public interface iWorkerTask extends iCancelable {

  /**
   * Performs the computation and returns the result.
   * Called on the background thread.
   *
   * @return the result of the computation
   */
  public Object compute();

  /**
   * Called on the UI thread once the computation has finished.
   *
   * @param result the compute result
   */
  public void finish(Object result);
}
