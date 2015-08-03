/*
 * @(#)SequenceArrayEditorComposite.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio.composite;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import com.appnativa.studio.dialogs.SequenceArrayEditor;
import com.appnativa.spot.iSPOTElement;

public class SequenceArrayEditorComposite extends BaseEditorComposite {

  /**
   * Create the composite.
   * @param parent
   * @param style
   */
  public SequenceArrayEditorComposite(Composite parent, int style) {
    super(parent, style);
    setLayout(new GridLayout(1, false));

    Composite composite = new Composite(this, SWT.NONE);

    composite.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
    composite.setLayout(new FillLayout(SWT.HORIZONTAL));

    Button btnEditItems = new Button(composite, SWT.NONE);

    btnEditItems.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        showsequenceEditor(false);
      }
    });
    btnEditItems.setText("Edit Items");

    Button btnAddItem = new Button(composite, SWT.NONE);

    btnAddItem.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        showsequenceEditor(true);
      }
    });
    btnAddItem.setText("Add Item");

    Label label = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);

    label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
    textWidget = new Text(this, SWT.BORDER);
    textWidget.setEditable(false);
    textWidget.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
    textWidget.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
    createAttributesEditor(this, 1);
  }

  public void setSelectedItem(iSPOTElement element) {
    this.element = (iSPOTElement) element.clone();
    textWidget.setText(element.toString());
    calculatePreferredTextHeight();
    resetAttributes();
  }

  protected int calculatePreferredTextHeight() {
    Point p = textWidget.computeSize(SWT.DEFAULT, SWT.DEFAULT, true);

    p.y                                                = Math.min(p.y, 300);
    p.y                                                = Math.max(p.y, 100);
    ((GridData) textWidget.getLayoutData()).heightHint = p.y;

    return p.y;
  }

  protected void showsequenceEditor(boolean addNew) {
    final SequenceArrayEditor se = new SequenceArrayEditor(getShell(), element,null,false);

    if (addNew) {
      Display.getDefault().asyncExec(new Runnable() {
        @Override
        public void run() {
          se.addElement();
        }
      });
    }

    int ret = se.open();

    if ((ret == IDialogConstants.OK_ID) || (ret == IDialogConstants.CLIENT_ID)) {
      this.element = se.getSPOTElement();
      textWidget.setText(element.toString());

      int y = textWidget.getSize().y;

      if (y != calculatePreferredTextHeight()) {
        this.getParent().getParent().layout();
      }

      if (propertyChangeListener != null) {
        notifyPropertyChangeListener(element);
      }
    }
  }
}
