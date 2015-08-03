/*
 * @(#)UIStringRenderer.java   2012-01-10
 * 
 * Copyright (c) 2007-2009 appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.ui.renderer;

import javax.swing.JLabel;

import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.iPlatformRenderingComponent;

/**
 * A label renderer that only displays text
 *
 * @author Don DeCoteau
 */
public class UIStringRenderer extends UILabelRenderer {

  public UIStringRenderer() {
		super();
	}

	public UIStringRenderer(JLabel tv) {
		super(tv);
	}

	@Override
  public iPlatformRenderingComponent newCopy() {
    return Renderers.setupNewCopy(this, new UIStringRenderer());
  }

  /**
   * Overridden to do nothing
   *
   * @param icon the icon to set
   */
  @Override
  public void setDisabledIcon(iPlatformIcon icon) {}

  /**
   * Overridden to do nothing
   *
   * @param icon the icon to set
   */
  @Override
  public void setIcon(iPlatformIcon icon) {}
}
