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

public interface iTransform {
  public Object getPlatformTransform();

  public void concatenate(iTransform tx);

  public void rotate(float angle);

  public void scale(float sx, float sy);

  public void shear(float shx, float shy);

  public void translate(float x, float y);

  public void setTransform(float m00, float m10, float m01, float m11, float m02, float m12);

  public void concatenate(float m00, float m10, float m01, float m11, float m02, float m12);

  public iTransform cloneCopy();
}
