/*
 * @(#)IconChooserDialog.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio.dialogs;

import com.appnativa.studio.Utilities;
import com.appnativa.studio.composite.IconEditorComposite;
import com.appnativa.studio.composite.ImageEditorComposite;
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

public class IconOrImageChooserDialog extends Dialog implements PropertyChangeListener {
  boolean             imageChooser;
  SPOTPrintableString selectedIcon;

  /**
   * Create the dialog.
   * @param parentShell
   */
  public IconOrImageChooserDialog(Shell parentShell, SPOTPrintableString icon, boolean imageChooser) {
    super(parentShell);
    setShellStyle(SWT.RESIZE | SWT.APPLICATION_MODAL);
    this.imageChooser = imageChooser;

    if (icon == null) {
      icon = new SPOTPrintableString();
    }

    this.selectedIcon = icon;
  }

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    selectedIcon = (SPOTPrintableString) evt.getNewValue();
  }

  public String getSelectedIcon() {
    if (selectedIcon != null) {
      return Utilities.toString(selectedIcon);
    }

    return null;
  }

  public SPOTPrintableString getIconElement() {
    return selectedIcon;
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

    if (imageChooser) {
      ImageEditorComposite editor = new ImageEditorComposite(container, SWT.NONE);

      editor.setSelectedItem(selectedIcon);
      editor.setPropertyChangeListener(this, this);
    } else {
      IconEditorComposite editor = new IconEditorComposite(container, SWT.NONE);

      editor.setSelectedItem(selectedIcon);
      editor.setPropertyChangeListener(this, this);
    }

    return container;
  }

  /**
   * Return the initial size of the dialog.
   */
  @Override
  protected Point getInitialSize() {
    return new Point(450, 401);
  }
}
