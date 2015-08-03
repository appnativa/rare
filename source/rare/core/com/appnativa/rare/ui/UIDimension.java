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

/**
 *
 * @author Don DeCoteau
 */
public class UIDimension {
  public float height;
  public float width;

  public UIDimension() {}

  public UIDimension(UIDimension d) {
    this.width  = d.width;
    this.height = d.height;
  }

  public UIDimension(double width, double height) {
    this.width  = (float) width;
    this.height = (float) height;
  }

  public UIDimension(float width, float height) {
    this.width  = width;
    this.height = height;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }

    if (o instanceof UIDimension) {
      UIDimension d = (UIDimension) o;

      return (d.width == width) && (d.height == height);
    }

    return false;
  }

  @Override
  public int hashCode() {
    return (int) (width * height);
  }

  @Override
  public String toString() {
    return "[width=" + width + ", height=" + height + "]";
  }

  public void setHeight(int height) {
    this.height = height;
  }

  public void setSize(UIDimension size) {
    this.width  = size.width;
    this.height = size.height;
  }

  public void setSize(double width, double height) {
    this.width  = (float) width;
    this.height = (float) height;
  }

  public void setSize(int width, int height) {
    this.width  = width;
    this.height = height;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public float getHeight() {
    return height;
  }

  public UIDimension getSize() {
    return new UIDimension(this);
  }

  public float getWidth() {
    return width;
  }

  public int intWidth() {
    return (int) Math.ceil(width);
  }

  public int intHeight() {
    return (int) Math.ceil(height);
  }
}
