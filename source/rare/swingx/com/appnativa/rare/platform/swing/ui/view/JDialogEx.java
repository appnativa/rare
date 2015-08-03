package com.appnativa.rare.platform.swing.ui.view;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.Window;

import javax.swing.JDialog;
import javax.swing.JRootPane;

public class JDialogEx extends JDialog {

	public JDialogEx() {
	}

	public JDialogEx(Frame owner) {
		super(owner);
	}

	public JDialogEx(Dialog owner) {
		super(owner);
	}

	public JDialogEx(Window owner) {
		super(owner);
	}

	public JDialogEx(Frame owner, boolean modal) {
		super(owner, modal);
	}

	public JDialogEx(Frame owner, String title) {
		super(owner, title);
	}

	public JDialogEx(Dialog owner, boolean modal) {
		super(owner, modal);
	}

	public JDialogEx(Dialog owner, String title) {
		super(owner, title);
	}

	public JDialogEx(Window owner, ModalityType modalityType) {
		super(owner, modalityType);
	}

	public JDialogEx(Window owner, String title) {
		super(owner, title);
	}

	public JDialogEx(Frame owner, String title, boolean modal) {
		super(owner, title, modal);
	}

	public JDialogEx(Dialog owner, String title, boolean modal) {
		super(owner, title, modal);
	}

	public JDialogEx(Window owner, String title, ModalityType modalityType) {
		super(owner, title, modalityType);
	}

	public JDialogEx(Frame owner, String title, boolean modal, GraphicsConfiguration gc) {
		super(owner, title, modal, gc);
	}

	public JDialogEx(Dialog owner, String title, boolean modal, GraphicsConfiguration gc) {
		super(owner, title, modal, gc);
	}

	public JDialogEx(Window owner, String title, ModalityType modalityType, GraphicsConfiguration gc) {
		super(owner, title, modalityType, gc);
	}

	@Override
  protected JRootPane createRootPane() {
		return new JRootPaneEx();
	}

}
