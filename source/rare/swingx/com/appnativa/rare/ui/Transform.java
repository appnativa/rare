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

import java.awt.geom.AffineTransform;

public class Transform implements iTransform {
  protected AffineTransform transform;

  public Transform() {
    super();
    this.transform = new AffineTransform();
  }

  public Transform(AffineTransform transform) {
    super();
    this.transform = transform;
  }

  @Override
  public iTransform cloneCopy() {
    return new Transform(new AffineTransform(transform));
  }

  @Override
  public void concatenate(iTransform tx) {
    transform.concatenate(((Transform) tx).transform);
  }

  @Override
  public void concatenate(float m00, float m10, float m01, float m11, float m02, float m12) {
    transform.concatenate(new AffineTransform(m00, m10, m01, m11, m02, m12));
  }

  @Override
  public void rotate(float angle) {
    transform.rotate(angle);
  }

  @Override
  public void scale(float sx, float sy) {
    transform.scale(sx, sy);
  }

  @Override
  public void translate(float x, float y) {
    transform.translate(x, y);
  }

  @Override
  public void setTransform(float m00, float m10, float m01, float m11, float m02, float m12) {
    transform.setTransform(m00, m10, m01, m11, m02, m12);
  }

  @Override
  public Object getPlatformTransform() {
    return transform;
  }

  @Override
  public void shear(float shx, float shy) {
    transform.shear(shx, shy);
  }
}
