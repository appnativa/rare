/*
 * @(#)InsetsDialog.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio.dialogs;

import com.appnativa.studio.composite.InsetsEditorComposite;
import com.appnativa.spot.SPOTPrintableString;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class InsetsDialog extends Dialog implements PropertyChangeListener {
  InsetsEditorComposite insetsComposite;
  SPOTPrintableString   selectedInsets;

  /**
   * Create the dialog.
   * @param parentShell
   */
  public InsetsDialog(Shell parentShell, String insets) {
    super(parentShell);
    setShellStyle(SWT.RESIZE | SWT.APPLICATION_MODAL);
    selectedInsets = new SPOTPrintableString(insets);
  }

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    selectedInsets = (SPOTPrintableString) evt.getNewValue();
  }

  public String getSelectedInsets() {
    return selectedInsets.toString();
  }

  /**
   * Create contents of the button bar.
   * @param parent
   */
  @Override
  protected void createButtonsForButtonBar(Composite parent) {
    createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, false);
    createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
  }

  /**
   * Create contents of the dialog.
   * @param parent
   */
  @Override
  protected Control createDialogArea(Composite parent) {
    Composite container = (Composite) super.createDialogArea(parent);

    insetsComposite = new InsetsEditorComposite(container, SWT.NONE);
    insetsComposite.setPropertyChangeListener(this, this);
    insetsComposite.setSelectedItem(selectedInsets);

    return container;
  }

  /**
   * Return the initial size of the dialog.
   */
  @Override
  protected Point getInitialSize() {
    return new Point(450, 300);
  }
}
