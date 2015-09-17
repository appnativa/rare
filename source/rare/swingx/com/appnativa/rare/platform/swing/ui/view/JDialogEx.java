/*
 * Copyright appNativa Inc. All Rights Reserved.
 *
 * This file is part of the Real-time Application Rendering Engine (RARE).
 *
 * RARE is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */

package com.appnativa.rare.platform.swing.ui.view;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.Window;

import javax.swing.JDialog;
import javax.swing.JRootPane;

public class JDialogEx extends JDialog {
  public JDialogEx() {}

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
