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

public interface iComposite {

  /**
   * Composite types for image rendering
   */
  public static enum CompositeType {
    SRC_OVER, SRC_ATOP, SRC_IN, SRC_OUT, DST_OVER, DST_ATOP, DST_IN, DST_OUT, CLEAR, XOR, LIGHTEN, DARKEN, COPY;
  }

  public abstract iComposite derive(float alpha);

  public abstract float getAlpha();

  public abstract iComposite.CompositeType getCompositeType();

  public abstract String getName();
}
