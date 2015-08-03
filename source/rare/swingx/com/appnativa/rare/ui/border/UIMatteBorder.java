package com.appnativa.rare.ui.border;

import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIInsets;


/**
 * 
 *  @author Don DeCoteau
 */
public class UIMatteBorder extends aUIMatteBorder{

	public UIMatteBorder(int top, int right, int bottom, int left, UIColor matteColor) {
		super(top, right, bottom, left, matteColor);
	}

	public UIMatteBorder(UIInsets borderInsets, UIColor matteColor) {
		super(borderInsets, matteColor);
	}
 }
