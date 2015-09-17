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

package com.appnativa.rare.platform.swing.ui.util;

import com.appnativa.rare.exception.ApplicationException;
import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.iPath;
import com.appnativa.rare.ui.iPlatformPath;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;

public class SwingPath implements iPlatformPath {
  float       x;
  float       y;
  GeneralPath path;
  Rectangle   rect;

  public SwingPath() {
    this.path = new GeneralPath();
  }

  public SwingPath(GeneralPath path) {
    this.path = path;
  }

  public SwingPath(SwingPath path) {
    this(path.path);
    x = path.x;
    y = path.y;
  }

  @Override
  public iPath arc(float x, float y, float radius, float startAngle, float endAngle, boolean counterClockwise) {
    final float pi2 = (float) (2 * Math.PI);

    startAngle = startAngle % pi2;

    if (startAngle < 0) {
      startAngle += pi2;
    }

    endAngle = endAngle % pi2;

    if (endAngle < 0) {
      endAngle += pi2;
    }

    if (counterClockwise) {
      if (startAngle < endAngle) {
        endAngle -= pi2;
      }
    } else {
      if (startAngle > endAngle) {
        endAngle += pi2;
      }
    }

    float angle = startAngle - endAngle;

    if (angle == 0) {
      angle = pi2;
    }

    startAngle = -startAngle;

    Arc2D.Double arc = new Arc2D.Double(x - radius, y - radius, 2 * radius, 2 * radius, Math.toDegrees(startAngle),
                         Math.toDegrees(angle), Arc2D.OPEN);

    path.append(arc, true);

    return this;
  }

  @Override
  public iPath arcTo(float x1, float y1, float x2, float y2, float radius) {
    Point2D lastPt = path.getCurrentPoint();

    if (lastPt == null) {
      throw new ApplicationException("no starting point");
    }

    float lastX = (float) lastPt.getX();
    float lastY = (float) lastPt.getY();

    if ((lastX == x1) && (lastY == y1)) {
      x1 = lastX;
      y1 = lastY;
    }

    Arc2D.Double arc = new Arc2D.Double();

    arc.setArcByTangent(lastPt, new Point2D.Double(x1, y1), new Point2D.Double(x2, y2), radius);
    path.append(arc, true);

    return this;
  }

  @Override
  public void close() {
    if (path.getCurrentPoint() != null) {
      path.closePath();
    }
  }

  @Override
  public iPlatformPath copy() {
    return new SwingPath((GeneralPath) path.clone());
  }

  @Override
  public iPath cubicTo(float x1, float y1, float x2, float y2, float x3, float y3) {
    path.curveTo(x1, y1, x2, y2, x3, y3);

    return this;
  }

  @Override
  public void drawLine(float x1, float y1, float x2, float y2) {
    if ((x1 != this.x) || (y1 != this.y) || (path.getCurrentPoint() == null)) {
      path.moveTo(x1, y1);
    }

    path.lineTo(x2, y2);
    this.x = x2;
    this.y = y2;
  }

  @Override
  public void drawRect(float x, float y, float width, float height) {
    if ((x != this.x) || (y != this.y)) {
      path.moveTo(x, y);
    }

    if (rect == null) {
      rect = new Rectangle();
    }

    rect.setRect(x, y, width, height);
    path.append(rect, true);
    this.x = x;
    this.y = y;
  }

  @Override
  public void drawRoundedRect(float x, float y, float width, float height, float arcWidth, float arcHeight) {
    if ((x != this.x) || (y != this.y)) {
      path.moveTo(x, y);
    }

    path.append(new RoundRectangle2D.Double(x, y, width, height, arcWidth, arcHeight), true);
    this.x = x;
    this.y = y;
  }

  @Override
  public iPath lineTo(float x, float y) {
    path.lineTo(x, y);

    return this;
  }

  @Override
  public iPath moveTo(float x, float y) {
    path.moveTo(x, y);

    return this;
  }

  @Override
  public iPath quadTo(float x1, float y1, float x2, float y2) {
    path.quadTo(x1, y1, x2, y2);

    return this;
  }

  @Override
  public void reset() {
    x = 0;
    y = 0;
    path.reset();
  }

  @Override
  public void rewind() {
    x = 0;
    y = 0;
    path.reset();
  }

  @Override
  public iPath startLineDrawing(float x, float y, boolean move) {
    this.x = x;
    this.y = y;

    if (move) {
      path.moveTo(x, y);
    }

    return this;
  }

  @Override
  public void translate(float x, float y) {
    path.transform(AffineTransform.getTranslateInstance(x, y));
  }

  @Override
  public iPlatformPath setPath(GeneralPath path) {
    this.path = path;

    return this;
  }

  @Override
  public UIRectangle getBounds() {
    return new UIRectangle(path.getBounds());
  }

  @Override
  public GeneralPath getPath() {
    return path;
  }

  @Override
  public Rectangle getRectangle() {
    return path.getBounds();
  }

  @Override
  public Shape getShape() {
    return path;
  }

  @Override
  public boolean isEmpty() {
    return path.getCurrentPoint() == null;
  }

  @Override
  public boolean isPointInPath(float x, float y) {
    return path.contains(x, y);
  }
}
