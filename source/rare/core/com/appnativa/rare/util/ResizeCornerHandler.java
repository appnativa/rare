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

package com.appnativa.rare.util;

import com.appnativa.rare.Platform;
import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIPoint;
import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.UIScreen;
import com.appnativa.rare.ui.event.MouseEvent;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.listener.iMouseListener;
import com.appnativa.rare.ui.listener.iMouseMotionListener;
import com.appnativa.rare.viewer.WindowViewer;

public class ResizeCornerHandler implements iMouseListener, iMouseMotionListener {
  UIRectangle                screenSize;
  float                      startX;
  float                      startY;
  float                      winX;
  float                      winY;
  private iPlatformComponent windowComponent;

  public void addAsWindowResizer(iPlatformComponent c) {
    if (c != null) {
      c.removeMouseMotionListener(this);
      c.addMouseMotionListener(this);
      c.removeMouseListener(this);
      c.addMouseListener(this);
    }
  }

  public void dispose() {
    windowComponent = null;
  }

  @Override
  public void mouseDragged(MouseEvent e) {
    if (windowComponent == null) {
      return;
    }

    UIPoint p = e.getLocationOnScreen();

    if (p == null) {
      return;
    }

    float              xCoord = p.x;
    float              yCoord = p.y;
    float              dx     = (xCoord - startX);
    float              dy     = (yCoord - startY);
    UIDimension        d      = new UIDimension(windowComponent.getSize());
    iPlatformComponent source = e.getComponent();

    d.width  += dx;
    d.height += dy;

    if (d.height + winY > screenSize.height) {
      d.height = screenSize.height - winY;
    }

    if (d.width + winX > screenSize.width) {
      d.width = screenSize.width - winX;
    }

    d.height = Math.max(d.height, 50 + source.getWidth());
    d.width  = Math.max(d.width, 50 + source.getHeight());

    if ((d.width != windowComponent.getWidth()) || (d.height != windowComponent.getHeight())) {
      windowComponent.setSize(UIScreen.snapToSize(d.width), UIScreen.snapToSize(d.height));
    }
  }

  @Override
  public void mouseEntered(MouseEvent e) {}

  @Override
  public void mouseExited(MouseEvent e) {}

  @Override
  public void mouseMoved(MouseEvent e) {}

  @Override
  public void mousePressed(MouseEvent e) {
    windowComponent = null;

    UIPoint      p = e.getLocationOnScreen();
    WindowViewer w = Platform.getWindowViewer(e.getComponent());

    if (w != null) {
      windowComponent = w.getComponent();
      startX          = p.x;
      startY          = p.y;
      p               = windowComponent.getLocationOnScreen();
      winX            = p.x;
      winY            = p.y;
      screenSize      = ScreenUtils.getUsableScreenBounds(windowComponent);
    }
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    windowComponent = null;
  }

  @Override
  public void pressCanceled(MouseEvent e) {}

  public void removeAsWindowResizer(iPlatformComponent c) {
    if (c != null) {
      c.removeMouseListener(this);
      c.removeMouseMotionListener(this);
    }
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
