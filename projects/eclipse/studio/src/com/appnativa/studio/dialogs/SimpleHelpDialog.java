/*
 * @(#)SimpleHelpDialog.java
 * 
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio.dialogs;

import com.appnativa.studio.Activator;
import com.appnativa.util.Streams;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import java.io.InputStream;
import java.net.URL;

public class SimpleHelpDialog extends Dialog {
  private Text text;

  /**
   * Create the dialog.
   * @param parentShell
   */
  public SimpleHelpDialog(Shell parentShell) {
    super(parentShell);
  }

  public static void showHelp(Shell parentShell, String helpFile) {
    try {
      URL              u    = Activator.class.getResource(helpFile);
      InputStream      in   = u.openStream();
      String           text = Streams.streamToString(in);
      SimpleHelpDialog d    = new SimpleHelpDialog(parentShell);

      d.text.setText(text);
      d.open();
    } catch(Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Create contents of the button bar.
   * @param parent
   */
  @Override
  protected void createButtonsForButtonBar(Composite parent) {
    createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
  }

  /**
   * Create contents of the dialog.
   * @param parent
   */
  @Override
  protected Control createDialogArea(Composite parent) {
    Composite container = (Composite) super.createDialogArea(parent);

    container.setLayout(new FillLayout(SWT.HORIZONTAL));
    text = new Text(container, SWT.READ_ONLY | SWT.WRAP | SWT.V_SCROLL);

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
