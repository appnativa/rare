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

package com.appnativa.rare.ui.dnd;

/**
 *
 * @author Don DeCoteau
 */
public class DnDConstants {
  public static final int ACTION_COPY         = 1;
  public static final int ACTION_COPY_OR_MOVE = 3;
  public static final int ACTION_LINK         = 1073741824;
  public static final int ACTION_MOVE         = 2;
  public static final int ACTION_NONE         = 0;
  public static final int ACTION_REFERENCE    = 1073741824;

  private DnDConstants() {}

  static boolean isValidAction(int action) {
    return (action & ~(ACTION_COPY_OR_MOVE | ACTION_LINK)) == 0;
  }
}
