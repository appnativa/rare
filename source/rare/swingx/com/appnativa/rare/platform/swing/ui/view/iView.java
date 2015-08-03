package com.appnativa.rare.platform.swing.ui.view;

import java.awt.geom.AffineTransform;

import com.appnativa.rare.ui.UIDimension;

public interface iView {
	public void getMinimumSize(UIDimension size);
	public void getPreferredSize(UIDimension size,int maxWidth);
	public AffineTransform getTransformEx();
	public void setTransformEx(AffineTransform tx);
	
}
