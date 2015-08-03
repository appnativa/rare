/*
 * @(#)ListEditorComposite.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio.composite;

import com.appnativa.spot.SPOTBoolean;
import com.appnativa.spot.SPOTEnumerated;
import com.appnativa.spot.iSPOTElement;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.List;

public class ListEditorComposite extends BaseEditorComposite {
  int oldSelection = -1;

  /**
   * Create the composite.
   * @param parent
   * @param style
   */
  public ListEditorComposite(Composite parent, int style) {
    super(parent, style);

    GridLayout gridLayout = new GridLayout(1, false);

    gridLayout.marginHeight      = 0;
    gridLayout.marginWidth       = 0;
    gridLayout.verticalSpacing   = 2;
    gridLayout.horizontalSpacing = 2;
    setLayout(gridLayout);

    Group    grpChooseValue    = new Group(this, SWT.NONE);
    GridData gd_grpChooseValue = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
    gd_grpChooseValue.heightHint = 100;
    grpChooseValue.setLayoutData(gd_grpChooseValue);
    grpChooseValue.setText("Choose Value");
    grpChooseValue.setLayout(new FillLayout(SWT.HORIZONTAL));
    listWidget = new List(grpChooseValue, SWT.V_SCROLL);
    listWidget.addSelectionListener(this);
    createAttributesEditor(this, 1);
  }

  @Override
  public void focusEditWidget() {
    listWidget.forceFocus();
  }

  public void setItems(java.util.List items, Object selectedItem) {
    element = null;
    setVisible(grpAttributes, false);
    listWidget.removeAll();

    for (Object o : items) {
      listWidget.add(o.toString());
    }

    setSelection(selectedItem);
  }

  @Override
  public void setSelectedItem(iSPOTElement element) {
    super.setSelectedItem(element);
    listWidget.removeAll();

    if (element instanceof SPOTBoolean) {
      listWidget.add("true");
      listWidget.add("false");
    }

    if (element instanceof SPOTEnumerated) {
      String[] a = ((SPOTEnumerated) element).spot_getCopyOfStrChoices();

      for (String s : a) {
        listWidget.add(s);
      }
    }

    setSelection(element.spot_stringValue());
  }

  private void setSelection(Object selectedItem) {
    oldSelection = -1;

    if (selectedItem != null) {
      int n = listWidget.indexOf(selectedItem.toString());

      if (n != -1) {
        oldSelection = n;
        listWidget.select(n);
      }
    }
  }
}
