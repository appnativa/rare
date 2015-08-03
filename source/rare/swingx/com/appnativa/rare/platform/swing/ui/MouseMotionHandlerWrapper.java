/*
 * @(#)MouseMotionHandlerWrapper.java
 * 
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.platform.swing.ui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import com.appnativa.rare.platform.EventHelper;
import com.appnativa.rare.ui.listener.iMouseMotionListener;

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
