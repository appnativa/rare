/*
 * @(#)DragHandler.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.platform.swing.ui;

import java.awt.Component;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.viewer.WindowViewer;

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
    windowComponent=null;
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
