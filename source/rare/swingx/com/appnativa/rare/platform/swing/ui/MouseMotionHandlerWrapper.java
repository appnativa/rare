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

package com.appnativa.rare.platform.swing.ui;

import com.appnativa.rare.platform.EventHelper;
import com.appnativa.rare.ui.listener.iMouseMotionListener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MouseMotionHandlerWrapper implements MouseMotionListener {
  iMouseMotionListener mouseHandler;

  public MouseMotionHandlerWrapper(iMouseMotionListener mouseHandler) {
    this.mouseHandler = mouseHandler;
  }

  @Override
  public void mouseDragged(MouseEvent e) {
    mouseHandler.mouseDragged(EventHelper.createMouseEvent(e.getSource(), e));
  }

  @Override
  public void mouseMoved(MouseEvent e) {
    mouseHandler.mouseMoved(EventHelper.createMouseEvent(e.getSource(), e));
  }
}
