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

import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.viewer.WindowViewer;

import java.awt.Component;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DragHandler extends MouseAdapter {
  int                        startX;
  int                        startY;
  int                        winX;
  int                        winY;
  private java.awt.Component windowComponent;

  public DragHandler(WindowViewer windowViewer) {
    windowComponent = (Component) windowViewer.getUIWindow();
  }

  public void addAsWindowDragger(iPlatformComponent c) {
    if (c != null) {
      c.getView().removeMouseMotionListener(this);
      c.getView().addMouseMotionListener(this);
      c.getView().removeMouseListener(this);
      c.getView().addMouseListener(this);
    }
  }

  public void dispose() {
    windowComponent = null;
  }

  @Override
  public void mouseDragged(MouseEvent e) {
    if (windowComponent instanceof Frame) {
      int state = ((Frame) windowComponent).getExtendedState();

      if ((state & Frame.MAXIMIZED_BOTH) != 0) {
        return;
      }
    }

    Component source = e.getComponent();
    Point     p      = e.getLocationOnScreen();

    if (p == null) {
      p = new Point(e.getX() + source.getLocationOnScreen().x, e.getY() + source.getLocationOnScreen().y);
    }

    int xCoord = p.x;
    int yCoord = p.y;

    windowComponent.setLocation(winX + (xCoord - startX), winY + (yCoord - startY));
    Toolkit.getDefaultToolkit().sync();
  }

  @Override
  public void mouseMoved(MouseEvent e) {}

  @Override
  public void mousePressed(MouseEvent e) {
    Point p = e.getLocationOnScreen();

    startX = p.x;
    startY = p.y;
    p      = windowComponent.getLocationOnScreen();
    winX   = p.x;
    winY   = p.y;
  }

  public void removeAsWindowDragger(iPlatformComponent c) {
    if (c != null) {
      c.getView().removeMouseListener(this);
      c.getView().removeMouseMotionListener(this);
    }
  }
}
