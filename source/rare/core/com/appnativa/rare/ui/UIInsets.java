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
 * A calls for holding insets.
 *
 * Although the insets components are floats they are generally assumed to hold
 * integers. The is the case when calling methods on this class the perform
 * comparisons of the value of the sides
 *
 * @author Don DeCoteau
 */
public class UIInsets implements Cloneable {
  public float bottom;
  public float left;
  public float right;
  public float top;

  public UIInsets() {}

  public UIInsets(float size) {
    this.top    = size;
    this.left   = size;
    this.bottom = size;
    this.right  = size;
  }

  public UIInsets(UIInsets insets) {
    set(insets);
  }

  public UIInsets(double top, double right, double bottom, double left) {
    set(top, right, bottom, left);
  }

  public UIInsets(int top, int right, int bottom, int left) {
    set(top, right, bottom, left);
  }

  public UIInsets addInsets(UIInsets in) {
    addInsets(in.top, in.right, in.bottom, in.left);

    return this;
  }

  public UIInsets addInsets(float top, float right, float bottom, float left) {
    this.top    += top;
    this.left   += left;
    this.bottom += bottom;
    this.right  += right;

    return this;
  }

  @Override
  public Object clone() {
    try {
      return super.clone();
    } catch(CloneNotSupportedException e) {
      throw new InternalError();
    }
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof UIInsets) {
      UIInsets insets = (UIInsets) obj;

      return ((top == insets.top) && (left == insets.left) && (bottom == insets.bottom) && (right == insets.right));
    }

    return false;
  }

  @Override
  public int hashCode() {
    float sum1 = left + bottom;
    float sum2 = right + top;
    float val1 = sum1 * (sum1 + 1) / 2 + left;
    float val2 = sum2 * (sum2 + 1) / 2 + top;
    float sum3 = val1 + val2;

    return (int) (sum3 * (sum3 + 1) / 2 + val2);
  }

  public String toCSS(boolean fx) {
    final String name = fx
                        ? "-fx-padding: "
                        : "padding: ";

    return name + top + "px " + right + "px " + bottom + "px " + left + "px;";
  }

  public StringBuilder toCSS(String name, StringBuilder sb) {
    sb.append(name).append(": ").append(top).append("px ").append(right);
    sb.append("px ").append(bottom).append("px ").append(left).append("px;");

    return sb;
  }

  @Override
  public String toString() {
    return "[top=" + top + ",right=" + right + ",bottom=" + bottom + ",left=" + left + "]";
  }

  public void set(int all) {
    this.top    = all;
    this.left   = all;
    this.bottom = all;
    this.right  = all;
  }

  public UIInsets set(UIInsets in) {
    set(in.top, in.right, in.bottom, in.left);

    return this;
  }

  public UIInsets set(double top, double right, double bottom, double left) {
    this.top    = (int) Math.round(top);
    this.left   = (int) Math.round(left);
    this.bottom = (int) Math.round(bottom);
    this.right  = (int) Math.round(right);

    return this;
  }

  public UIInsets set(int top, int right, int bottom, int left) {
    this.top    = top;
    this.left   = left;
    this.bottom = bottom;
    this.right  = right;

    return this;
  }

  public UIInsets setBottom(int bottom) {
    this.bottom = bottom;

    return this;
  }

  public UIInsets setLeft(int left) {
    this.left = left;

    return this;
  }

  public UIInsets setRight(int right) {
    this.right = right;

    return this;
  }

  public UIInsets setTop(int top) {
    this.top = top;

    return this;
  }

  public float getBottom() {
    return bottom;
  }

  public float getHeight() {
    return top + bottom;
  }

  public float getLeft() {
    return left;
  }

  public float getRight() {
    return right;
  }

  public float getTop() {
    return top;
  }

  public float getWidth() {
    return left + right;
  }

  public final boolean isAllSidesEqual() {
    if (Math.abs(left - top) > 0.00001) {
      return false;
    }

    if (Math.abs(left - right) > 0.00001) {
      return false;
    }

    if (Math.abs(left - bottom) > 0.00001) {
      return false;
    }

    return true;
  }

  public final boolean isAllOne() {
    return ((int) left == 1) && ((int) top == 1) && ((int) right == 1) && ((int) bottom == 1);
  }

  public final boolean isAllTwo() {
    return ((int) left == 2) && ((int) top == 2) && ((int) right == 2) && ((int) bottom == 2);
  }

  public boolean isEmpty() {
    return ((int) left == 0) && ((int) top == 0) && ((int) right == 0) && ((int) bottom == 0);
  }

  public final boolean isOnlyLeftOne() {
    return ((int) left == 1) && ((int) top == 0) && ((int) right == 0) && ((int) bottom == 0);
  }

  public final boolean isOnlyLeftThree() {
    return ((int) left == 3) && ((int) top == 0) && ((int) right == 0) && ((int) bottom == 0);
  }

  /**
   * Tests to see of the only the left value is set and is set to 2
   *
   * @return return true if it is ; false otherwise
   */
  public final boolean isOnlyLeftTwo() {
    return ((int) left == 2) && ((int) top == 0) && ((int) right == 0) && ((int) bottom == 0);
  }

  public int intTop() {
    return (int) Math.ceil(top);
  }

  public int intLeft() {
    return (int) Math.ceil(left);
  }

  public int intBottom() {
    return (int) Math.ceil(bottom);
  }

  public int intRight() {
    return (int) Math.ceil(right);
  }
}
