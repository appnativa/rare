package com.appnativa.rare.platform.swing.ui.view;

import javax.swing.JScrollBar;

public class ScrollBarView extends JScrollBar {

	public ScrollBarView(boolean horizontal) {
		super(horizontal ? HORIZONTAL : VERTICAL);
	}

	public ScrollBarView(int orientation) {
		super(orientation);
	}

	public ScrollBarView(int orientation, int value, int extent, int min, int max) {
		super(orientation, value, extent, min, max);
	}

}
