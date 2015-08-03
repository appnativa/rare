/*
 * @(#)UIShapeBorder.java   2010-02-06
 *
 * Copyright (c) appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.ui.border;

import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.iShapeCreator;

/**
 *
 * @author Don DeCoteau
 */
public class UIShapeBorder extends aUIShapeBorder {

	public UIShapeBorder(iShapeCreator shapeCreator) {
		super(shapeCreator);
	}

	public UIShapeBorder(UIColor color, iShapeCreator shapeCreator, float thickness) {
		super(color, shapeCreator, thickness);
	}

	public UIShapeBorder(UIColor color, iShapeCreator shapeCreator, UIInsets insets, float thickness) {
		super(color, shapeCreator, insets, thickness);
	}
 
}
