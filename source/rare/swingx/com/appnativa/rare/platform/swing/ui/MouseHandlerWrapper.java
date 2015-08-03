/*
 * @(#)MouseHandlerWrapper.java
 * 
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.platform.swing.ui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import com.appnativa.rare.platform.EventHelper;
import com.appnativa.rare.ui.listener.iMouseListener;

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
