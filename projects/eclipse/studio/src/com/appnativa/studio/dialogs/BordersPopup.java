/*
 * @(#)BordersPopup.java
 * 
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio.dialogs;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;

import com.appnativa.studio.Studio;

public class BordersPopup extends Dialog {
  private String border;
  private List   list;

  /**
   * Create the dialog.
   * @param parentShell
   */
  public BordersPopup(Shell parentShell) {
    super(parentShell);
  }

  public String getBorder() {
    return border;
  }

  /**
   * Create contents of the button bar.
   * @param parent
   */
  @Override
  protected void createButtonsForButtonBar(Composite parent) {
    createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
    createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
    getButton(IDialogConstants.OK_ID).setEnabled(false);
  }
  
  @Override
  protected void configureShell(Shell newShell) {
    super.configureShell(newShell);
    newShell.setText(Studio.getResourceAsString("Studio.text.design.config_select_border"));
  }

  /**
   * Create contents of the dialog.
   * @param parent
   */
  @Override
  protected Control createDialogArea(Composite parent) {
    Composite container = (Composite) super.createDialogArea(parent);

    container.setLayout(new FillLayout(SWT.HORIZONTAL));
    list = new List(container, SWT.BORDER);
    list.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        getButton(IDialogConstants.OK_ID).setEnabled(true);
      }
    });
    list.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseDoubleClick(MouseEvent e) {
        okPressed();
      }
    });
    list.add("line");
    list.add("matte");
    list.add("empty");
    list.add("none");
    list.add("back");
    list.add("balloon");
    list.add("bevel_raised");
    list.add("bevel_lowered");
    list.add("drop_shadow");
    list.add("etched_raised");
    list.add("etched_lowered");
    list.add("frame_raised");
    list.add("frame_lowered");
    list.add("group_box");
    list.add("icon");
    list.add("lowered");
    list.add("raised");
    list.add("shadow");
    list.add("standard");
    

    return container;
  }

  @Override
  protected void okPressed() {
    if (list.getSelectionCount() > 0) {
      border = list.getSelection()[0];
    }

    super.okPressed();
  }

  /**
   * Return the initial size of the dialog.
   */
  @Override
  protected Point getInitialSize() {
    return new Point(286, 400);
  }
}
