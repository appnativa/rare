/*
 * @(#)Transform.java
 * 
 * Copyright (c) SparseWare. All rights reserved.
 *
 * Use is subject to license terms.
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
