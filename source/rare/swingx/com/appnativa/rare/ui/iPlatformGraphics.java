/*
 * @(#)iPlatformGraphics.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.ui;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;

public interface iPlatformGraphics extends iGraphics {
  void setComponent(iPlatformComponent c);

  void set(Graphics g,iPlatformComponent c);

  @Override
  iPlatformComponent getComponent();

  Graphics2D getGraphics();

  Component getView();


}
