/*
 * @(#)aDialogEditorFieldComposite.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio.composite;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public abstract class aDialogEditorFieldComposite extends BaseEditorComposite {
  protected Button btnDialog;
  protected Label  lblPrompt;

  /**
   * Create the composite.
   * @param parent
   * @param style
   */
  public aDialogEditorFieldComposite(Composite parent, int style, String prompt) {
    super(parent, style);

    GridLayout gridLayout = new GridLayout(3, false);

    gridLayout.verticalSpacing   = 0;
    gridLayout.marginWidth       = 0;
    gridLayout.horizontalSpacing = 2;
    gridLayout.marginHeight      = 0;
    setLayout(gridLayout);
    lblPrompt = new Label(this, SWT.NONE);
    lblPrompt.setAlignment(SWT.RIGHT);
    lblPrompt.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    lblPrompt.setText(prompt);
    textWidget = new Text(this, SWT.BORDER);
    textWidget.addTraverseListener(this);
    textWidget.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
    btnDialog = new Button(this, SWT.NONE);
    btnDialog.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        String s = openDialog(textWidget.getText());

        if (s != null) {
          textWidget.setText(s);
          if(element!=null) {
            element.spot_setValue(s);
            notifyPropertyChangeListener(element);
          }
          else {
            notifyPropertyChangeListener(s);
          }
        }
      }
    });
    btnDialog.setText("...");
  }

  protected abstract String openDialog(String currentValue);
}
