package com.appnativa.rare.ui.border;

import com.appnativa.rare.ui.UIColor;
public class UIDropShadowBorder extends aUIDropShadowBorder  {

	public UIDropShadowBorder() {
		super();
	}

	public UIDropShadowBorder(boolean showLeftShadow) {
		super(showLeftShadow);
	}

	public UIDropShadowBorder(UIColor shadowColor, float shadowSize, float shadowOpacity, float cornerSize, boolean showTopShadow,
			boolean showLeftShadow, boolean showBottomShadow, boolean showRightShadow) {
		super(shadowColor, shadowSize, shadowOpacity, cornerSize, showTopShadow, showLeftShadow, showBottomShadow, showRightShadow);
	}

	public UIDropShadowBorder(UIColor shadowColor, float shadowSize) {
		super(shadowColor, shadowSize);
	}
	
}
