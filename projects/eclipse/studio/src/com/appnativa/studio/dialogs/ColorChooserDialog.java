/*
 * @(#)ColorChooserDialog.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio.dialogs;

import java.awt.Dimension;
import java.util.EventObject;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

import com.appnativa.studio.Studio;
import com.appnativa.studio.composite.ColorPaletteComposite;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.event.iActionListener;
import com.appnativa.rare.ui.event.iChangeListener;

public class ColorChooserDialog extends Dialog implements iActionListener, iChangeListener {
  ColorPaletteComposite colorPanel;
  UIColor    selectedColor;
  Dimension  preferredSize;
  Exception exception;
  private Label lblColorSample;
  /**
   * Create the dialog.
   * @param parentShell
   */
  public ColorChooserDialog(Shell parentShell) {
    super(parentShell);
  }

  @Override
  public void actionPerformed(com.appnativa.rare.ui.event.ActionEvent e) {
    selectedColor = colorPanel.getSelectedColor();
  }

  public void setColor(UIColor color) {
    selectedColor = color;
  }

  public UIColor getSelectedColor() {
    return selectedColor;
  }
  public void setSelectedColor(UIColor selectedColor) {
    this.selectedColor = selectedColor;
    if(colorPanel!=null) {
      colorPanel.setSelectedColor(selectedColor);
    }
  }
  
  @Override
  protected void configureShell(Shell newShell) {
    super.configureShell(newShell);
    newShell.setText(Studio.getResourceAsString("Studio.text.design.config_color"));
  }

  /**
   * Create contents of the button bar.
   * @param parent
   */
  @Override
  protected void createButtonsForButtonBar(Composite parent) {
    createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
    createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);

    Button b = createButton(parent, IDialogConstants.CLIENT_ID, "None", false);

    b.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        setReturnCode(IDialogConstants.CLIENT_ID);
        selectedColor=null;
        ColorChooserDialog.this.close();
      }
    });
  }
  @Override
  protected void okPressed() {
    selectedColor=colorPanel.getSelectedColor();
    super.okPressed();
  }
  /**
   * Create contents of the dialog.
   * @param parent
   */
  @Override
  protected Control createDialogArea(Composite parent) {
    Composite container    = (Composite) super.createDialogArea(parent);
    
    colorPanel = new ColorPaletteComposite(container, SWT.NONE);
    GridData gd_colorPanel = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
    gd_colorPanel.minimumHeight = 150;
    colorPanel.setLayoutData(gd_colorPanel);
    colorPanel.addChangeListener(this);
    
    Label label = new Label(container, SWT.SEPARATOR | SWT.HORIZONTAL);
    label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
    
    lblColorSample = new Label(container, SWT.BORDER);
    lblColorSample.setAlignment(SWT.CENTER);
    GridData gd_lblColorSample = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);
    gd_lblColorSample.heightHint = 24;
    lblColorSample.setLayoutData(gd_lblColorSample);
    lblColorSample.setText("Color Sample");
    
    Label label_1 = new Label(container, SWT.SEPARATOR | SWT.HORIZONTAL);
    label_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
    
    return container;
  }

  @Override
  public void stateChanged(EventObject e) {
    Color c=colorPanel.getSWTColor();
    if(c==null) {
      c=SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND);
    }
    lblColorSample.setBackground(c);
  }
}
