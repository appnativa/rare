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
import com.appnativa.rare.ui.listener.iMouseListener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseHandlerWrapper implements MouseListener {
  iMouseListener mouseHandler;

  public MouseHandlerWrapper(iMouseListener mouseHandler) {
    this.mouseHandler = mouseHandler;
  }

  @Override
  public void mouseClicked(MouseEvent e) {}

  @Override
  public void mouseEntered(MouseEvent e) {
    mouseHandler.mouseEntered(EventHelper.createMouseEvent(e.getSource(), e));
  }

  @Override
  public void mouseExited(MouseEvent e) {
    mouseHandler.mouseExited(EventHelper.createMouseEvent(e.getSource(), e));
  }

  @Override
  public void mousePressed(MouseEvent e) {
    mouseHandler.mousePressed(EventHelper.createMouseEvent(e.getSource(), e));
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    mouseHandler.mouseReleased(EventHelper.createMouseEvent(e.getSource(), e));
  }
}
