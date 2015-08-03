/*
 * @(#)TextFieldEditor.java   2009-12-31
 *
 * Copyright (c) 2007-2009 appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.ui.celleditor;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.tree.TreeCellEditor;

import com.appnativa.rare.ui.iPlatformCellEditingComponent;

/**
 * A textfield-based editor
 *
 * @author Don DeCoteau
 */
public class ComboBoxEditor extends DefaultCellEditor implements iPlatformCellEditingComponent, Cloneable, TreeCellEditor {

  /**
   * Creates a new instance of TextFieldEditor
   */
  public ComboBoxEditor() {
    super(new JComboBox());
  }

  @Override
  public Object clone() {
    return new ComboBoxEditor();
  }
  public JComboBox getComboBox() {
    return (JComboBox)getComponent();
  }
}
