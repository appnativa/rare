/*
 * @(#)BordersDialog.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio.dialogs;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import com.appnativa.studio.Studio;
import com.appnativa.studio.RMLDocument;
import com.appnativa.studio.composite.BordersEditorComposite;
import com.appnativa.rare.Platform;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.spot.SPOTSet;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public class BordersDialog extends Dialog implements PropertyChangeListener{
  BordersEditorComposite bordersComposite;
  SPOTSet                bordersSet;

  /**
   * Create the dialog.
   *
   * @param parentShell
   */
  public BordersDialog(Shell parentShell, SPOTSet borders) {
    super(parentShell);
    setShellStyle(SWT.DIALOG_TRIM);
    this.bordersSet = (SPOTSet) borders.clone();
  }

  public SPOTSet getSelectedBorders() {
    return bordersSet;
  }

  @Override
  protected void configureShell(Shell newShell) {
    super.configureShell(newShell);
    newShell.setText(Studio.getResourceAsString("Studio.text.design.config_borders"));
  }

  /**
   * Create contents of the button bar.
   *
   * @param parent
   */
  @Override
  protected void createButtonsForButtonBar(Composite parent) {
    createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, false);
    createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
  }

  /**
   * Create contents of the dialog.
   *
   * @param parent
   */
  @Override
  protected Control createDialogArea(Composite parent) {
    Composite container = (Composite) super.createDialogArea(parent);

    bordersComposite = new BordersEditorComposite(container, SWT.NONE);
    bordersComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

    RMLDocument doc           = Studio.getSelectedDocument();
    iWidget     contextWidget = doc.getDesignPane().getRootWidget();

    if (contextWidget == null) {
      contextWidget = Platform.getWindowViewer();
    }

    bordersComposite.setBordersSet(contextWidget, bordersSet);
    bordersComposite.setPropertyChangeListener(this, this);
    bordersSet = null;

    return container;
  }

  @Override
  protected void initializeBounds() {
    super.initializeBounds();
    getShell().pack(true);
  }

  @Override
  protected void okPressed() {
    bordersSet = bordersComposite.getBorders();
    super.okPressed();
  }

  /**
   * Return the initial size of the dialog.
   */
  @Override
  protected Point getInitialSize() {
    return new Point(679, 569);
  }

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
  }
}
