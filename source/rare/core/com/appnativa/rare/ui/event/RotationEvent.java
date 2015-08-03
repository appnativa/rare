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

/**
 *
 * @author Don DeCoteau
 */
public class RotationEvent extends EventBase {
  public static final int ROTATION       = 2;
  public static final int ROTATION_BEGIN = 1;
  public static final int ROTATION_END   = 3;
  private final int       eventType;
  private final float     velocity;
  private final float     focusX;
  private final float     focusY;
  private float           rotation;

  public RotationEvent(Object source, int type, float rotation, float velocity, float focusX, float focusY) {
    super(source);
    eventType     = type;
    this.focusX   = focusX;
    this.focusY   = focusY;
    this.rotation = rotation;
    this.velocity = velocity;
  }

  /**
   * @return the eventType
   */
  public int getEventType() {
    return eventType;
  }

  public float getFocusX() {
    return focusX;
  }

  public float getFocusY() {
    return focusY;
  }

  public float getVelocity() {
    return velocity;
  }

  public float getRotation() {
    return rotation;
  }
}
