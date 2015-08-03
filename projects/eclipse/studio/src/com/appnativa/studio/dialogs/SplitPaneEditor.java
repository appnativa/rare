/*
 * @(#)SplitPaneEditor.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio.dialogs;

import com.appnativa.studio.Studio;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class SplitPaneEditor extends Dialog implements ControlListener {
  float            splits[];
  private Label    lblSplits;
  private SashForm sashForm;

  /**
   * Create the dialog.
   * @param parentShell
   */
  public SplitPaneEditor(Shell parentShell, float[] splits) {
    super(parentShell);
    this.splits = splits;
  }

  @Override
  public void controlMoved(ControlEvent e) {}

  @Override
  public void controlResized(ControlEvent e) {
    calculateSplits();
  }

  public float[] getSplits() {
    return splits;
  }

  protected void calculateSplits() {
    int[]   w = sashForm.getWeights();
    float[] s = new float[w.length - 1];

    for (int i = 0; i < w.length - 1; i++) {
      s[i] = (float) w[i] / 1000;
      s[i] = Math.round(s[i] * 100f) / 100f;
    }

    StringBuilder sb = new StringBuilder();

    sb.append(s[0]);

    for (int i = 1; i < s.length; i++) {
      sb.append(",");
      sb.append(s[i]);
    }

    splits = s;
    lblSplits.setText(sb.toString());
  }

  /**
   * Create contents of the button bar.
   * @param parent
   */
  @Override
  protected void createButtonsForButtonBar(Composite parent) {
    createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
    createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
  }

  protected void createChildren(float[] splits) {
    this.splits = splits;

    int  weights[] = new int[splits.length + 1];
    int  total     = 1000;
    Text l;

    for (int i = 0; i < splits.length; i++) {
      float n = splits[i];
      int   w = (int) Math.floor(n * 1000);

      weights[i] = w;
      total      -= w;
      l          = new Text(sashForm, SWT.BORDER | SWT.READ_ONLY);
      l.addControlListener(this);;
    }

    l = new Text(sashForm, SWT.BORDER | SWT.READ_ONLY);
    l.addControlListener(this);;
    weights[splits.length] = total;
    sashForm.setWeights(weights);
  }

  /**
   * Create contents of the dialog.
   * @param parent
   */
  @Override
  protected Control createDialogArea(Composite parent) {
    Composite container         = (Composite) super.createDialogArea(parent);
    Label     lblAdjustTheSplit = new Label(container, SWT.NONE);

    lblAdjustTheSplit.setText(Studio.getResourceAsString("Studio.text.design.adjust_split"));
    sashForm = new SashForm(container, SWT.NONE);
    sashForm.setSashWidth(5);
    sashForm.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
    createChildren(splits);
    
    Composite composite = new Composite(container, SWT.NONE);
    composite.setLayout(new GridLayout(2, false));
    composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
    lblSplits = new Label(composite, SWT.NONE);
    lblSplits.setAlignment(SWT.CENTER);
    lblSplits.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
    
    Button btnNewButton = new Button(composite, SWT.NONE);
    btnNewButton.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        int[]   w = sashForm.getWeights();
        int s=(int)Math.floor(1000/w.length);
        for(int i=0;i<w.length;i++) {
          w[i]=s;
        }
        sashForm.setWeights(w);
        calculateSplits();
      }
    });
    btnNewButton.setText("Split Evenly");

    Label label = new Label(container, SWT.SEPARATOR | SWT.HORIZONTAL);

    label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

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
