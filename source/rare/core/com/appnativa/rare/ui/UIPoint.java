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
public class UIPoint {
  public float x;
  public float y;

  public UIPoint() {}

  public UIPoint(double x, double y) {
    this.x = (int) Math.round(x);
    this.y = (int) Math.round(y);
  }

  public UIPoint(float x, float y) {
    this.x = x;
    this.y = y;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }

    if (o instanceof UIPoint) {
      UIPoint p = (UIPoint) o;

      return (Math.abs(p.x - x) < .0000001) && (Math.abs(p.y - y) < .0000001);
    }

    return false;
  }

  @Override
  public int hashCode() {
    return (int) (x * y);
  }

  @Override
  public String toString() {
    return "[x=" + x + ", y=" + y + "]";
  }

  public void set(UIPoint p) {
    this.x = p.x;
    this.y = p.y;
  }

  public void set(double x, double y) {
    this.x = (float) x;
    this.y = (float) y;
  }

  public void set(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public void setLocation(double x, double y) {
    this.x = (float) x;
    this.y = (float) y;
  }

  public void setLocation(float x, float y) {
    this.x = x;
    this.y = y;
  }

  public void setX(float x) {
    this.x = x;
  }

  public void setY(float y) {
    this.y = y;
  }

  public UIPoint getLocation() {
    return new UIPoint(x, y);
  }

  public float getX() {
    return x;
  }

  public float getY() {
    return y;
  }

  public int intY() {
    return Math.round(y);
  }

  public int intX() {
    return Math.round(x);
  }
}
