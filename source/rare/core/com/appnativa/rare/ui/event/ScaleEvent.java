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

package com.appnativa.rare.ui.event;

import com.appnativa.rare.platform.EventHelper;

/**
 *
 * @author Don DeCoteau
 */
public class ScaleEvent extends EventBase {
  public static enum Type { SCALE, SCALE_BEGIN, SCALE_END; }

  private final Type   type;
  private final float  scaleFactor;
  private final Object scaleGestureDetector;

  public ScaleEvent(Object source, Object sgd, Type type, float scaleFactor) {
    super(source);
    this.type            = type;
    scaleGestureDetector = sgd;
    this.scaleFactor     = scaleFactor;
  }

  public float getCurrentSpan() {
    return EventHelper.getScaleCurrentSpan(scaleGestureDetector);
  }

  /**
   * @return the eventType
   */
  public Type getEventType() {
    return type;
  }

  public float getFocusX() {
    return EventHelper.getScaleFocusX(scaleGestureDetector);
  }

  public float getFocusY() {
    return EventHelper.getScaleFocusY(scaleGestureDetector);
  }

  public float getPreviousSpan() {
    return EventHelper.getScalePreviousSpan(scaleGestureDetector);
  }

  public float getScaleFactor() {
    return scaleFactor;
  }

  /**
   * @return the scaleGestureDetector
   */
  public Object getScaleGestureDetector() {
    return scaleGestureDetector;
  }

  /**
   * Returns whether of the this event represents and event of the specified type
   *
   * @param type the type
   * @return true if it does; false otherwise
   */
  public boolean isType(Type type) {
    return this.type == type;
  }
}
