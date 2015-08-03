/*
 * @(#)ICellEditorActivationListener.java
 * 
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio.dialogs;

import org.eclipse.jface.viewers.CellEditor;

public interface ICellEditorActivationListener {
  void cellEditorActivated(CellEditor activatedCellEditor);

  void cellEditorDeactivated(CellEditor activatedCellEditor);
}
