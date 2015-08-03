/*
 * @(#)BackgroundColorDialog.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio.dialogs;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import com.appnativa.studio.composite.BackgroundColorEditorComposite;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.painter.iGradientPainter;

public class BackgroundColorDialog extends Dialog implements PropertyChangeListener {
  UIColor                  backgroundColor;
  BackgroundColorEditorComposite colorPanel;

  /**
   * Create the dialog.
   *
   * @param parentShell
   */
  public BackgroundColorDialog(Shell parentShell, UIColor color) {
    super(parentShell);
    setShellStyle(SWT.CLOSE | SWT.RESIZE | SWT.APPLICATION_MODAL);
    backgroundColor = color;
  }

  public iGradientPainter getGradientPainter() {
    return colorPanel.getGradientPainter();
  }

  public UIColor getSelectedColor() {
    return backgroundColor;
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
    final Composite container = (Composite) super.createDialogArea(parent);

    colorPanel = new BackgroundColorEditorComposite(container, SWT.NONE);
    colorPanel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1));
    colorPanel.setSelectedColor(backgroundColor);
    colorPanel.setPropertyChangeListener(this, this);

    return container;
  }

  @Override
  protected void okPressed() {
    backgroundColor = colorPanel.getSelectedColor();
    super.okPressed();
  }
  /**
   * Return the initial size of the dialog.
   */
  @Override
  protected Point getInitialSize() {
    return colorPanel.getPreferredSize();
  }

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
  }
}
