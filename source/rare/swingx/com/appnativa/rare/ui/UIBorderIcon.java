/*
 * @(#)UIBorderIcon.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.ui;

import java.awt.Component;
import java.awt.Graphics;

/**
 *
 *  @author Don DeCoteau
 */
public class UIBorderIcon extends aUIBorderIcon {
  @Override
  public void paintIcon(Component c, Graphics g, int x, int y) {
    int width  = getIconWidth();
    int height = getIconHeight();

    getBorder().paintBorder(c, g, x, y, width, height);
    getIcon().paintIcon(c, g, x + (int)insets.left, y + (int)insets.top);
  }

	public UIBorderIcon(iPlatformBorder border, iPlatformIcon icon) {
		super(border, icon);
	}
}
