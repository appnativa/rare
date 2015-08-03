package com.appnativa.rare.platform.swing.ui.view;

import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JRootPane;

public class JFrameEx extends JFrame{

	public JFrameEx() {
	}

	public JFrameEx(GraphicsConfiguration gc) {
		super(gc);
	}

	public JFrameEx(String title, GraphicsConfiguration gc) {
		super(title, gc);
	}

	public JFrameEx(String title) throws HeadlessException {
		super(title);
	}
	@Override
  protected JRootPane createRootPane() {
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		return new JRootPaneEx();
	}
	
	@Override
	public void dispose() {
	  ((JRootPaneEx)getRootPane()).disposeOfPane();
	  super.dispose();
	}
}
