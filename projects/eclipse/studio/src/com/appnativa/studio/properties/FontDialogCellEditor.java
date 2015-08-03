/*
 * @(#)FontDialogCellEditor.java
 * 
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio.properties;

import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FontDialog;

/**
 * @author Administrator
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class FontDialogCellEditor extends DialogCellEditor {

  /**
   * Creates a new Font dialog cell editor parented under the given control.
   * The cell editor value is <code>null</code> initially, and has no
   * validator.
   *
   * @param parent the parent control
   */
  protected FontDialogCellEditor(Composite parent) {
    super(parent);
  }

  /**
   * @see org.eclipse.jface.viewers.DialogCellEditor#openDialogBox(Control)
   */
  protected Object openDialogBox(Control cellEditorWindow) {
    FontDialog ftDialog = new FontDialog(cellEditorWindow.getShell());
    String     value    = (String) getValue();

    if ((value != null) && (value.length() > 0)) {
      ftDialog.setFontList(new FontData[] { new FontData(value) });
    }

    FontData fData = ftDialog.open();

    if (fData != null) {
      value = fData.toString();
    }

    return value;
  }
}
