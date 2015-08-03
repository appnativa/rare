/*
 * @(#)iPlatformMenuBar.java   2008-01-03
 * 
 * Copyright (c) appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.ui;

import javax.swing.JMenuBar;


/**
 * An interface for menu bars
 *
 * @author Don DeCoteau
 */
public interface iPlatformMenuBar extends iMenuBar{

  /**
   * Gets the menu bar component
   *
   * @return the menu bar component
   */
  JMenuBar getMenuBar();

	@Override
  iPlatformComponent getContainerComponent();
}
