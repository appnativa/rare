/*
 * @(#)ColorChooserDialog.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio.dialogs;

import com.appnativa.studio.Studio;
import com.appnativa.studio.composite.FontChooserComposite;
import com.appnativa.rare.spot.Font;
import com.appnativa.rare.ui.UIFont;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public class FontChooserDialog extends Dialog {
  UIFont               baseFont;
  FontChooserComposite fontPanel;
  private Font         rareFont;

  /**
   * Create the dialog.
   * @param parentShell
   */
  public FontChooserDialog(Shell parentShell) {
    super(parentShell);
  }

  public void setFont(UIFont base, Font font) {
    if (fontPanel != null) {
      fontPanel.setSelectedFont(base, font);
    } else {
      baseFont = base;
      rareFont = font;
    }
  }

  public Font getSelectedFont() {
    return rareFont;
  }
  
  @Override
  protected void configureShell(Shell newShell) {
    super.configureShell(newShell);
    newShell.setText(Studio.getResourceAsString("Studio.text.design.config_font"));
  }

  /**
   * Create contents of the button bar.
   * @param parent
   */
  @Override
  protected void createButtonsForButtonBar(Composite parent) {
    createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);

    Button b = createButton(parent, IDialogConstants.CLIENT_ID, "None", false);

    b.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        setReturnCode(IDialogConstants.CLIENT_ID);
        rareFont = fontPanel.getSelectedFont();
        rareFont.spot_clear();
        FontChooserDialog.this.close();
      }
    });
    createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, false);
  }

  /**
   * Create contents of the dialog.
   * @param parent
   */
  @Override
  protected Control createDialogArea(Composite parent) {
    Composite container = (Composite) super.createDialogArea(parent);

    fontPanel = new FontChooserComposite(container, SWT.NONE);
    fontPanel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
    fontPanel.setSelectedFont(baseFont, rareFont);
    baseFont = null;
    rareFont = null;

    return container;
  }

  @Override
  protected void okPressed() {
    rareFont = fontPanel.getSelectedFont();
    super.okPressed();
  }
}
