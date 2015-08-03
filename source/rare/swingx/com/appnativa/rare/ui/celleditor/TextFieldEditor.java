/*
 * @(#)TextFieldEditor.java   2009-12-31
 *
 * Copyright (c) 2007-2009 appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.ui.celleditor;

import javax.swing.DefaultCellEditor;
import javax.swing.JTextField;
import javax.swing.text.DefaultCaret;
import javax.swing.tree.TreeCellEditor;

import org.jdesktop.swingx.util.OS;

import com.appnativa.rare.ui.iPlatformCellEditingComponent;

/**
 * A textfield-based editor
 *
 * @author Don DeCoteau
 */
public class TextFieldEditor extends DefaultCellEditor implements iPlatformCellEditingComponent, Cloneable, TreeCellEditor {

  /**
   * Creates a new instance of TextFieldEditor
   */
  public TextFieldEditor() {
    super(new JTextField());
    if (OS.isMacOSX()) {
      ((JTextField)getComponent()).setCaret(new DefaultCaret());
    }
  }

  @Override
  public Object clone() {
    return new TextFieldEditor();
  }

  public JTextField getTextField() {
    return (JTextField)getComponent();
  }
}
