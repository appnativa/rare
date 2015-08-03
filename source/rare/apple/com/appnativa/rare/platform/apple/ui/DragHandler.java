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

package com.appnativa.rare.platform.apple.ui;

import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.event.MouseEvent;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.listener.iMouseListener;
import com.appnativa.rare.ui.listener.iMouseMotionListener;
import com.appnativa.rare.viewer.WindowViewer;

import com.google.j2objc.annotations.Weak;

public class DragHandler implements iMouseMotionListener, iMouseListener {
  float        startX;
  float        startY;
  float        winX;
  float        winY;
  @Weak
  WindowViewer window;

  public DragHandler(WindowViewer windowViewer) {
    window = windowViewer;
  }

  public void addAsWindowDragger(iPlatformComponent c) {
    ((Component) c).removeMouseMotionListener(this);
    ((Component) c).removeMouseListener(this);
    ((Component) c).addMouseMotionListener(this);
    ((Component) c).addMouseListener(this);
  }

  public void dispose() {
    window = null;
  }

  @Override
  public void mouseDragged(MouseEvent e) {
    float xCoord = e.getScreenX();
    float yCoord = e.getScreenY();

    window.moveTo(Math.round(winX + (xCoord - startX)), Math.round(winY + (yCoord - startY)));
  }

  @Override
  public void mouseEntered(MouseEvent e) {}

  @Override
  public void mouseExited(MouseEvent e) {}

  @Override
  public void mouseMoved(MouseEvent e) {}

  @Override
  public void mousePressed(MouseEvent e) {
    startX = e.getScreenX();
    startY = e.getScreenY();
    winX   = window.getScreenX();
    winY   = window.getScreenY();
  }

  @Override
  public void mouseReleased(MouseEvent e) {}

  @Override
  public void pressCanceled(MouseEvent e) {}

  public void removeAsWindowDragger(iPlatformComponent c) {
    ((Component) c).removeMouseMotionListener(this);
  }

  @Override
  public boolean wantsLongPress() {
    return false;
  }

  @Override
  public boolean wantsMouseMovedEvents() {
    return false;
  }
}
