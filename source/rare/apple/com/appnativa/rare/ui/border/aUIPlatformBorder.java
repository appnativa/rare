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

package com.appnativa.rare.ui.border;

import com.appnativa.rare.ui.iPlatformBorder;

/**
 *
 * @author Don DeCoteau
 */
public abstract class aUIPlatformBorder extends aUIBorder implements iPlatformBorder {
  public aUIPlatformBorder() {}

  @Override
  public boolean canUseMainLayer() {
    return false;
  }

  @Override
  public boolean requiresPanel() {
    return false;
  }

  @Override
  public boolean usesPath() {
    return false;
  }

  public float getPathWidth() {
    return 0;
  }

  public float getPathOffset() {
    return getPathWidth() / 2;
  }
}