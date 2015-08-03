package com.appnativa.rare.ui.painter;

import java.awt.Paint;

import com.appnativa.rare.ui.iPlatformGraphics;

/**
 * A compound painter
 * 
 * @author Don DeCoteau
 */
public class UICompoundPainter extends aUICompoundPainter {

	public UICompoundPainter(iPlatformPainter firstPainter,
			iPlatformPainter secondPainter) {
		super(firstPainter,secondPainter);
	}

	@Override
  public void paint(iPlatformGraphics g, float x, float y, float width, float height,
			int orientation) {
		if ((firstPainter != null) && firstPainter.isEnabled()) {
			firstPainter.paint(g, x, y, width, height, orientation);
		}

		if ((secondPainter != null) && secondPainter.isEnabled()) {
			secondPainter.paint(g, x, y, width, height, orientation);
		}
	}
	
	public Paint getPaint() {
		return null;
	}
}
