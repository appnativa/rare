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

package com.appnativa.rare.platform.android.ui.util;

import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;

import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.iPath;
import com.appnativa.rare.ui.iPlatformPath;

public class AndroidPath implements iPlatformPath {
  boolean rectangle = true;
  Path    path;
  RectF   rect;
  RectF   rect2;
  float   x;
  float   y;

  public AndroidPath() {
    this.path = new Path();
  }

  public AndroidPath(AndroidPath path) {
    this.path = path.path;
    rectangle = path.rectangle;
    this.x    = path.x;
    this.y    = path.y;

    if (rectangle) {
      rect2 = new RectF(path.rect2);
    }
  }

  public AndroidPath(Path path) {
    this.path = path;
    rectangle = false;
  }

  public iPath arc(float x, float y, float radius, float startAngle, float endAngle, boolean counterClockwise) {
    if (rect == null) {
      rect = new RectF();
    }

    rect.set(x - radius, y - radius, x + radius, y + radius);
    startAngle = (float) Math.toDegrees(startAngle);
    endAngle   = (float) Math.toDegrees(endAngle);

    float sweepAngle = ((endAngle + 360) - startAngle) % 360;

    if (counterClockwise) {
      sweepAngle = sweepAngle - 360;
    } else if (sweepAngle == 0) {
      sweepAngle = 360;
    }

    path.addArc(rect, startAngle, sweepAngle);
    rectangle = false;

    return this;
  }

  public iPath arcTo(float x1, float y1, float x2, float y2, float radius) {
    PathMeasure  pm   = new PathMeasure(path, false);
    final double hpi  = Math.PI / 2;
    final double pi2  = Math.PI * 2;
    float        len  = pm.getLength();
    float        pt[] = { x, y };

    if (len != 0) {
      pm.getPosTan(len - 1, pt, null);
    }

    if (((pt[0] - x1 == 0) && (x1 - x2 == 0)) || ((pt[1] - y1 == 0) && (y1 - y2 == 0)) || (radius == 0)) {
      lineTo(x1, y1);
    } else if (((pt[0] == x1) && (pt[1] == y1)) || ((x1 == x2) && (y1 == y2))) {
      lineTo(x1, y1);
    } else {
      float  startAngle = (float) Math.atan2(pt[1] - y1, pt[0] - x1);
      float  endAngle   = (float) Math.atan2(y2 - y1, x2 - x1);
      double diff       = endAngle - startAngle;

      if (diff > Math.PI) {
        endAngle -= pi2;
      } else if (diff < -Math.PI) {
        endAngle += pi2;
      }

      double bisect = (startAngle + endAngle) / 2.0;
      double theta  = Math.abs(endAngle - bisect);
      double d      = radius / Math.sin(theta);
      double x      = x1 + d * Math.cos(bisect);
      double y      = y1 + d * Math.sin(bisect);

      if (startAngle < endAngle) {
        startAngle -= hpi;
        endAngle   += hpi;
      } else if (startAngle > endAngle) {
        startAngle += hpi;
        endAngle   -= hpi;
      }

      if (endAngle == 0) {
        endAngle = (float) (2 * Math.PI);
      }

      arc((float) x, (float) y, radius, (float) startAngle, (float) endAngle, endAngle > startAngle);
    }

    return this;
  }

  public void close() {
    if (!path.isEmpty()) {
      path.close();
    }
  }

  @Override
  public iPlatformPath copy() {
    return new AndroidPath(this);
  }

  public iPath cubicTo(float x1, float y1, float x2, float y2, float x3, float y3) {
    path.cubicTo(x1, y1, x2, y2, x3, y3);
    x         = x3;
    y         = y3;
    rectangle = false;

    return this;
  }

  public void drawLine(float x1, float y1, float x2, float y2) {
    if ((x1 != this.x) || (y1 != this.y)) {
      path.moveTo(x1, y1);
    }

    path.lineTo(x2, y2);
    this.x    = x2;
    this.y    = y2;
    rectangle = false;
  }

  public void drawRect(float x, float y, float width, float height) {
    if (!isEmpty()) {
      rectangle = false;
    } else if (rectangle) {
      if (rect2 == null) {
        rect2 = new RectF(x, y, x + width, y + height);
      } else {
        rect2.set(x, y, x + width, y + height);
      }
    }

    if ((x != this.x) || (y != this.y)) {
      path.moveTo(x, y);
    }

    path.addRect(x, y, (x + width), (y + height), Path.Direction.CCW);
    this.x = x;
    this.y = y;
  }

  public void drawRoundedRect(float x, float y, float width, float height, float arcWidth, float arcHeight) {
    if ((x != this.x) || (y != this.y)) {
      path.moveTo(x, y);
    }

    if (rect == null) {
      rect = new RectF();
    }

    rect.set(x, y, (x + width), (y + height));
    path.addRoundRect(rect, arcWidth, arcHeight, Path.Direction.CCW);
    this.x    = x;
    this.y    = y;
    rectangle = false;
  }

  public iPath lineTo(float x, float y) {
    path.lineTo(x, y);
    rectangle = false;

    return this;
  }

  public iPath moveTo(float x, float y) {
    path.moveTo(x, y);
    this.x = x;
    this.y = y;

    return this;
  }

  public iPath quadTo(float x1, float y1, float x2, float y2) {
    path.quadTo(x1, y1, x2, y2);
    rectangle = false;

    return this;
  }

  public void reset() {
    x = 0;
    y = 0;
    path.reset();
    rectangle = true;

    if (rect2 != null) {
      rect2.set(0, 0, 0, 0);
    }
  }

  public void rewind() {
    x = 0;
    y = 0;
    path.rewind();
    rectangle = true;

    if (rect2 != null) {
      rect2.set(0, 0, 0, 0);
    }
  }

  public iPath startLineDrawing(float x, float y, boolean move) {
    this.x = x;
    this.y = y;

    if (move) {
      path.moveTo(x, y);
    }

    return this;
  }

  public void translate(float x, float y) {
    path.offset(x, y);
  }

  public iPlatformPath setPath(Path path) {
    this.path = path;
    rectangle = false;

    return this;
  }

  public UIRectangle getBounds() {
    if (rect == null) {
      rect = new RectF();
    }

    path.computeBounds(rect, true);

    return new UIRectangle(rect.left, rect.top, rect.width(), rect.height());
  }

  public Path getPath() {
    return path;
  }

  public RectF getRectangle() {
    if (rectangle && (rect2 != null)) {
      RectF r = new RectF(rect2);

      return r;
    }

    return null;
  }

  public boolean isEmpty() {
    return path.isEmpty();
  }

  public boolean isPointInPath(float x, float y) {
    if (rect == null) {
      rect = new RectF();
    }

    path.computeBounds(rect, true);

    return rect.contains(x, y);
  }
}
