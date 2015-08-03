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

import android.graphics.Matrix;

public class Transform extends Matrix implements iTransform {
  private static final float radMultiplier = 180f / (float) Math.PI;

  public Transform() {
    super();
  }

  public Transform(Transform tx) {
    super(tx);
  }

  public Transform(float m00, float m10, float m01, float m11, float m02, float m12) {
    super();
    setTransform(m00, m10, m01, m11, m02, m12);
  }

  @Override
  public iTransform cloneCopy() {
    return new Transform(this);
  }

  @Override
  public void concatenate(iTransform tx) {
    preConcat((Transform) tx);
  }

  @Override
  public void concatenate(float m00, float m10, float m01, float m11, float m02, float m12) {
    preConcat(new Transform(m00, m10, m01, m11, m02, m12));
  }

  @Override
  public void rotate(float angle) {
    preRotate(angle * radMultiplier);
  }

  @Override
  public void scale(float sx, float sy) {
    preScale(sx, sy);
  }

  public void shear(float shx, float shy) {
    setSkew(shx, shy);
  }

  @Override
  public void translate(float x, float y) {
    postTranslate(x, y);
  }

  @Override
  public void setTransform(float m00, float m10, float m01, float m11, float m02, float m12) {
    float[] f = new float[9];

    f[0] = (float) m00;
    f[1] = (float) m01;
    f[2] = (float) m02;
    f[3] = (float) m10;
    f[4] = (float) m11;
    f[5] = (float) m12;
    f[6] = 0;
    f[7] = 0;
    f[8] = 1;
    setValues(f);
  }

  @Override
  public Object getPlatformTransform() {
    return this;
  }
}
