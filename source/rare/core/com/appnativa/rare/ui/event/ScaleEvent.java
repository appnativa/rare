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
  public static final int SCALE       = 2;
  public static final int SCALE_BEGIN = 1;
  public static final int SCALE_END   = 3;
  private final int       eventType;
  private final float     scaleFactor;
  private final Object    scaleGestureDetector;

  public ScaleEvent(Object source, Object sgd, int type, float scaleFactor) {
    super(source);
    eventType            = type;
    scaleGestureDetector = sgd;
    this.scaleFactor     = scaleFactor;
  }

  public float getCurrentSpan() {
    return EventHelper.getScaleCurrentSpan(scaleGestureDetector);
  }

  /**
   * @return the eventType
   */
  public int getEventType() {
    return eventType;
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
}
