/*
 * @(#)InsetsEditorComposite.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio.composite;

import com.appnativa.rare.spot.Margin;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.Utils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.swt.events.SelectionAdapter;

public class InsetsEditorComposite extends BaseEditorComposite {
  private Spinner bottom;
  private Label   lblBottom;
  private Spinner left;
  private Spinner right;
  private Spinner top;

  /**
   * Create the composite.
   * 
   * @param parent
   * @param style
   */
  public InsetsEditorComposite(Composite parent, int style) {
    super(parent, style);

    Group group = new Group(this, SWT.NONE);

    group.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
    group.setText("Insets");

    GridLayout gl_group = new GridLayout(4, false);

    gl_group.verticalSpacing = 2;
    gl_group.marginWidth = 0;
    gl_group.marginHeight = 0;
    gl_group.horizontalSpacing = 2;
    group.setLayout(gl_group);

    Label lblTop = new Label(group, SWT.NONE);

    lblTop.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    lblTop.setText("Top:");
    top = new Spinner(group, SWT.BORDER);
    top.addSelectionListener(this);
    top.setMaximum(1000);
    lblBottom = new Label(group, SWT.NONE);
    lblBottom.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    lblBottom.setText("Bottom:");
    bottom = new Spinner(group, SWT.BORDER);
    bottom.setMaximum(1000);
    bottom.addSelectionListener(this);

    Label lblLeft = new Label(group, SWT.NONE);

    lblLeft.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    lblLeft.setText("Left:");
    left = new Spinner(group, SWT.BORDER);
    left.setMaximum(1000);
    left.addSelectionListener(this);

    Label lblRight = new Label(group, SWT.NONE);

    lblRight.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    lblRight.setText("Right:");
    right = new Spinner(group, SWT.BORDER);
    right.setMaximum(1000);
    right.addSelectionListener(this);

    Button button = new Button(group, SWT.NONE);
    button.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        top.setSelection(0);
        right.setSelection(0);
        bottom.setSelection(0);
        left.setSelection(0);
        valueChanged(null);
      }
    });

    button.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 4, 1));
    button.setText("Clear");
  }

  @Override
  public void widgetSelected(SelectionEvent e) {
    valueChanged(null);
  }

  @Override
  protected void resetValue(Widget widget) {
    UIInsets ins = null;

    if (element instanceof Margin) {
      ins = ((Margin) element).getInsets();
    } else {

      Object o = (element == null) ? oldValue : element.spot_stringValue();

      if (o != null) {
        ins = Utils.getInsets(o.toString());
      }
    }

    left.setSelection((ins == null) ? 0 : (int) ins.left);
    top.setSelection((ins == null) ? 0 : (int) ins.top);
    bottom.setSelection((ins == null) ? 0 : (int) ins.bottom);
    right.setSelection((ins == null) ? 0 : (int) ins.right);
    resetAttributes();
  }

  @Override
  protected void valueChanged(Widget widget) {
    StringBuilder sb = new StringBuilder();

    sb.append(top.getSelection()).append(',');
    sb.append(right.getSelection()).append(',');
    sb.append(bottom.getSelection()).append(',');
    sb.append(left.getSelection());
    String text = sb.toString();
    if (element instanceof Margin) {
      Margin m = (Margin) element;
      sb.setLength(0);
      sb.append(m.top.getValue()).append(',');
      sb.append(m.right.getValue()).append(',');
      sb.append(m.bottom.getValue()).append(',');
      sb.append(m.left.getValue());
      if (!text.equals(sb.toString())) {
        m.top.setValue(top.getSelection());
        m.right.setValue(right.getSelection());
        m.bottom.setValue(bottom.getSelection());
        m.left.setValue(left.getSelection());
        notifyPropertyChangeListener(element);
      }
    } else {

      if (element == null) {
        if (!isStringEqual(text, oldValue)) {
          notifyPropertyChangeListener(text);
        }
      } else {
        if (!isStringEqual(text, element.spot_stringValue())) {
          element.spot_setValue(text);
          notifyPropertyChangeListener(element);
        }
      }
    }
  }
}
