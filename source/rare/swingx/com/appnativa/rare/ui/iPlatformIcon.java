/*
 * @(#)iPlatformIcon.java   2011-11-11
 * 
 * Copyright (c) 2007-2009 appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.ui;

import javax.swing.Icon;

/**
 *
 * @author Don DeCoteau
 */
public interface iPlatformIcon extends iIcon, Icon {
  @Override
  int getIconHeight();

  @Override
  int getIconWidth();

	iPlatformIcon getDisabledVersion();
}
