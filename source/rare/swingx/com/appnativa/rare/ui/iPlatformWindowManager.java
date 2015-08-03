/*
 * @(#)iPlatformWindowManager.java   2008-01-14
 * 
 * Copyright (c) appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.ui;


/**
 * This interface defines the operations necessary for a window manager.
 * The window manager is responsible for creating and managing all the core visual
 * elements of the application
 * 
 * @author Don DeCoteau
 * 
 */
public interface iPlatformWindowManager extends iWindowManager{

	 
  /**
   * Gets the handle to the object responsible for creating components
   * 
   * @return the handle to the object responsible for creating components
   */
	iPlatformComponentFactory getComponentCreator();

}
