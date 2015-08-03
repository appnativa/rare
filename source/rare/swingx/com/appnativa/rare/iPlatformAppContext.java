/*
 * @(#)iPlatformAppContext.java   2011-11-19
 *
 * Copyright (c) 2007-2009 appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare;

import com.appnativa.rare.ui.iPlatformComponentFactory;
import com.appnativa.rare.ui.iSpeechEnabler;
import com.appnativa.rare.ui.painter.iPlatformPainter;

public interface iPlatformAppContext extends iAppContext {
	/**
	 * Returns whether this is an embedded instance
	 * @return true if embedded; false otherwise
	 */
	public boolean isEmbeddedInstance();


	@Override
  public iSpeechEnabler getSpeechEnabler();

	@Override
  public void setSpeechEnabler(iSpeechEnabler speechEnabler);
 
  /**
   * Gets a handle to the object that is responsible for creating UI components
   *
   * @return a handle to the object that is responsible for creating UI
   *         components
   */
  public iPlatformComponentFactory getComponentCreator();


  public boolean alwaysUseHeavyTargets();
  
  public iPlatformPainter getRequiredFieldOverlayPainter();
}
