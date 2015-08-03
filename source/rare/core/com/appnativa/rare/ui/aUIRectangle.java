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
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

/**
 * @author Denis M. Kishenko
 */
public abstract class aUIRectangle {
  public float height;
  public float width;
  public float x;
  public float y;

  public aUIRectangle() {
    setBounds(0, 0, 0, 0);
  }

  public aUIRectangle(UIDimension d) {
    setBounds(0, 0, d.width, d.height);
  }

  public aUIRectangle(UIPoint p) {
    setBounds(p.x, p.y, 0, 0);
  }

  public aUIRectangle(aUIRectangle r) {
    setBounds(r.x, r.y, r.width, r.height);
  }

  public aUIRectangle(float width, float height) {
    setBounds(0, 0, width, height);
  }

  public aUIRectangle(UIPoint p, UIDimension d) {
    setBounds(p.x, p.y, d.width, d.height);
  }

  public aUIRectangle(float x, float y, float width, float height) {
    setBounds(x, y, width, height);
  }

  public void add(UIPoint p) {
    add(p.x, p.y);
  }

  public void add(aUIRectangle r) {
    float x1 = Math.min(x, r.x);
    float x2 = Math.max(x + width, r.x + r.width);
    float y1 = Math.min(y, r.y);
    float y2 = Math.max(y + height, r.y + r.height);

    setBounds(x1, y1, x2 - x1, y2 - y1);
  }

  public void add(float px, float py) {
    float x1 = Math.min(x, px);
    float x2 = Math.max(x + width, px);
    float y1 = Math.min(y, py);
    float y2 = Math.max(y + height, py);

    setBounds(x1, y1, x2 - x1, y2 - y1);
  }

  public float bottom() {
    return y + height;
  }

  public boolean contains(UIPoint p) {
    return contains(p.x, p.y);
  }

  public boolean contains(aUIRectangle r) {
    return contains(r.x, r.y, r.width, r.height);
  }

  public boolean contains(float px, float py) {
    if ((width <= 0) || (height <= 0)) {
      return false;
    }

    return (px >= x) && (px < x + width) && (py >= y) && (py < y + height);
  }

  public boolean contains(float rx, float ry, float rwidth, float rheight) {
    if ((width <= 0) || (height <= 0) || (rwidth <= 0) || (rheight <= 0)) {
      return false;
    }

    if (!((rx >= x) && (rx < x + width) && (ry >= y) && (ry < y + height))) {
      return false;
    }
    ;

    rx += rwidth - 1;
    ry += rheight - 1;

    return ((rx >= x) && (rx < x + width) && (ry >= y) && (ry < y + height));
  }

  public void grow(float dx, float dy) {
    x      -= dx;
    y      -= dy;
    width  += dx + dx;
    height += dy + dy;
  }

  public UIRectangle intersection(aUIRectangle r) {
    float x1 = Math.max(x, r.x);
    float y1 = Math.max(y, r.y);
    float x2 = Math.min(x + width, r.x + r.width);
    float y2 = Math.min(y + height, r.y + r.height);

    return new UIRectangle(x1, y1, x2 - x1, y2 - y1);
  }

  public boolean intersects(aUIRectangle r) {
    return !intersection(r).isEmpty();
  }

  public float right() {
    return x + width;
  }

  @Override
  public String toString() {
    return "[x=" + x + ",y=" + y +                      // $NON-NLS-1$ //$NON-NLS-2$
      ",width=" + width + ",height=" + height + "]";    // $NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
  }

  public void translate(float mx, float my) {
    x += mx;
    y += my;
  }

  public UIRectangle union(aUIRectangle r) {
    UIRectangle dst = new UIRectangle(x, y, width, height);

    dst.add(r);

    return dst;
  }

  public void set(aUIRectangle r) {
    setBounds(r.x, r.y, r.width, r.height);
  }

  public void set(float x, float y, float width, float height) {
    setBounds(x, y, width, height);
  }

  public void setBounds(aUIRectangle r) {
    setBounds(r.x, r.y, r.width, r.height);
  }

  public void setBounds(float x, float y, float width, float height) {
    this.x      = x;
    this.y      = y;
    this.height = height;
    this.width  = width;
  }

  public void setBounds(double x, double y, double width, double height) {
    this.x      = (float) x;
    this.y      = (float) y;
    this.height = (float) height;
    this.width  = (float) width;
  }

  public void setLocation(UIPoint p) {
    setLocation(p.x, p.y);
  }

  public void setLocation(float x, float y) {
    this.x = x;
    this.y = y;
  }

  public void setRect(float x, float y, float width, float height) {
    float x1 = (float) Math.floor(x);
    float y1 = (float) Math.floor(y);
    float x2 = (float) Math.ceil(x + width);
    float y2 = (float) Math.ceil(y + height);

    setBounds(x1, y1, x2 - x1, y2 - y1);
  }

  public void setSize(UIDimension d) {
    setSize(d.width, d.height);
  }

  public int intWidth() {
    return (int) Math.ceil(width);
  }

  public int intHeight() {
    return (int) Math.ceil(height);
  }

  public int intX() {
    return Math.round(width);
  }

  public int intY() {
    return Math.round(height);
  }

  public void setSize(float width, float height) {
    this.width  = width;
    this.height = height;
  }

  public UIRectangle getBounds() {
    return new UIRectangle(x, y, width, height);
  }

  public float getHeight() {
    return height;
  }

  public UIPoint getLocation() {
    return new UIPoint(x, y);
  }

  public UIDimension getSize() {
    return new UIDimension(width, height);
  }

  public float getWidth() {
    return width;
  }

  public float getX() {
    return x;
  }

  public float getY() {
    return y;
  }

  public boolean isEmpty() {
    return (width <= 0) || (height <= 0);
  }

  @Override
  public int hashCode() {
    return ((int) (x * y) ^ (int) ((long) (width * height) >> 32));
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }

    if (obj instanceof aUIRectangle) {
      aUIRectangle r = (aUIRectangle) obj;

      if (Math.abs(x - r.x) > .0000001) {
        return false;
      }

      if (Math.abs(y - r.y) > .0000001) {
        return false;
      }

      if (Math.abs(width - r.width) > .0000001) {
        return false;
      }

      if (Math.abs(height - r.height) > .0000001) {
        return false;
      }

      return true;
    }

    return false;
  }
}
