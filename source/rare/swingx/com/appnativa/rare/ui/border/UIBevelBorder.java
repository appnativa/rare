/*
 * @(#)UIBevelBorder.java   2012-02-13
 *
 * Copyright (c) 2007-2009 appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.ui.border;

import com.appnativa.rare.ui.UIColor;


/**
 *
 * @author Don DeCoteau
 */
public class UIBevelBorder extends aUIBevelBorder {

	public UIBevelBorder(int bevelType, boolean fourcolor) {
		super(bevelType, fourcolor);
	}

	public UIBevelBorder(int bevelType, UIColor highlight, UIColor shadow, boolean fourColor) {
		super(bevelType, highlight, shadow, fourColor);
	}

	public UIBevelBorder(int bevelType, UIColor highlightOuterColor, UIColor highlightInnerColor, UIColor shadowOuterColor,
			UIColor shadowInnerColor, boolean fourColor) {
		super(bevelType, highlightOuterColor, highlightInnerColor, shadowOuterColor, shadowInnerColor, fourColor);
	}

	public UIBevelBorder(int bevelType, UIColor highlightOuterColor, UIColor highlightInnerColor, UIColor shadowOuterColor,
			UIColor shadowInnerColor) {
		super(bevelType, highlightOuterColor, highlightInnerColor, shadowOuterColor, shadowInnerColor);
	}

	public UIBevelBorder(int bevelType) {
		super(bevelType);
	}

}
