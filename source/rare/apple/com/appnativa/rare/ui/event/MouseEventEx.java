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

public class MouseEventEx extends MouseEvent {
  public MouseEventEx(Object source, Object me, int modifiers) {
    super(source, EventHelper.getTouch(source, me), modifiers);
  }

  public MouseEventEx(Object source, Object me1, int modifiers, Object me2, float x, float y) {
    super(source, EventHelper.getTouch(source, me1), modifiers, EventHelper.getTouch(source, me2), x, y);
  }

  public MouseEventEx(Object source, Object me) {
    super(source, EventHelper.getTouch(source, me));
  }
}
