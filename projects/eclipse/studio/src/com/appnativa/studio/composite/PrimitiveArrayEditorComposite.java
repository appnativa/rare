/*
 * @(#)PrimitiveArrayEditorComposite.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio.composite;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;

import com.appnativa.spot.SPOTSet;
import com.appnativa.spot.iSPOTElement;
import com.appnativa.studio.DesignPane;
import com.appnativa.studio.FormChanger;
import com.appnativa.studio.Studio;
import com.appnativa.studio.Utilities;

public class PrimitiveArrayEditorComposite extends BaseEditorComposite implements PropertyChangeListener {
  protected BaseEditorComposite editorComposite;
  protected iSPOTElement        newItem;
  private Button                btnDelete;
  private Button                btnEditAttributes;
  private Button                btnMoveDown;
  private Button                btnMoveUp;
  private SPOTSet               spotSet;

  /**
   * Create the composite.
   * @param parent
   * @param style
   */
  public PrimitiveArrayEditorComposite(Composite parent, int style) {
    super(parent, style);
    setLayout(new GridLayout(1, false));

    Composite listComposite = new Composite(this, SWT.NONE);

    listComposite.setLayout(new GridLayout(2, false));
    listComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
    listWidget = new List(listComposite, SWT.BORDER | SWT.V_SCROLL);
    listWidget.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
    listWidget.addSelectionListener(this);

    Composite buttons = new Composite(listComposite, SWT.NONE);

    buttons.setLayout(new FillLayout(SWT.HORIZONTAL));
    buttons.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
    btnDelete = new Button(buttons, SWT.PUSH);
    btnDelete.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        removeSelectedItem();
      }
    });
    btnDelete.setText("Delete");
    btnDelete.setEnabled(false);
    btnMoveUp = new Button(buttons, SWT.NONE);
    btnMoveUp.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        moveUp();
      }
    });
    btnMoveUp.setText("Move Up");
    btnMoveUp.setEnabled(false);
    btnMoveDown = new Button(buttons, SWT.NONE);
    btnMoveDown.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        moveDown();
      }
    });
    btnMoveDown.setText("Move Dn.");
    btnMoveDown.setEnabled(false);
    btnEditAttributes = new Button(listComposite, SWT.NONE);
    btnEditAttributes.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    btnEditAttributes.setText("Edit Set Attributes");
    btnEditAttributes.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        editAttributes();
      }
    });
    createEditor(this);
  }

  @Override
  public void focusEditWidget() {
    editorComposite.focusEditWidget();
  }

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    iSPOTElement e = (iSPOTElement) evt.getNewValue();
    String       s = (e == null)
                     ? null
                     : e.spot_stringValue();

    if ((s != null) && (s.length() > 0)) {
      if (e == newItem) {
        iSPOTElement ni=(iSPOTElement) newItem.clone();
        spotSet.add(ni);
        DesignPane pane=Studio.getActiveDesignPane();
        if(pane!=null) {
          FormChanger.addEditForElementInsert(pane, spotSet,spotSet.size()-1,ni);
        }
        listWidget.add(Utilities.toString(newItem));
        newItem.spot_clear();
        newItem.spot_cleanAttributes();
        editorComposite.setSelectedItem(newItem);
      }

      notifyPropertyChangeListener(element);
    }
  }

  @Override
  public void widgetSelected(SelectionEvent e) {
    if (e.widget == listWidget) {
      handleListSelectionChange();
    }
  }

  @Override
  public void setSelectedItem(iSPOTElement element) {
    super.setSelectedItem(element);
    spotSet = (SPOTSet) this.element;
    newItem = spotSet.spot_getArrayClassInstance();
    editorComposite.setSelectedItem(newItem);
    listWidget.removeAll();
    int len = spotSet.size();

    for (int i = 0; i < len; i++) {
      listWidget.add(Utilities.toString(spotSet.getEx(i)));
    }

    setVisible(btnEditAttributes, element.spot_hasDefinedAttributes());
  }


  protected void createEditor(Composite parent) {
    StringEditorComposite editor = new StringEditorComposite(parent, false);

    editor.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
    editor.setPropertyChangeListener(this, this);
    editor.hideHeader();
    editor.setCloneItem(false);
    editorComposite = editor;
  }

  protected void handleListSelectionChange() {
    int n = listWidget.getSelectionIndex();

    if (n == -1) {
      newItem.spot_clear();
      newItem.spot_cleanAttributes();
      editorComposite.setSelectedItem(newItem);
    } else {
      editorComposite.setSelectedItem(spotSet.getEx(n));
    }

    btnDelete.setEnabled(n != -1);
    btnMoveUp.setEnabled(n != -1);
    btnMoveDown.setEnabled(n != -1);
  }

  protected void moveDown() {
    int       n   = listWidget.getSelectionIndex();
    final int len = listWidget.getItemCount();

    if (n + 1 >= len) {
      return;
    }

    String s = listWidget.getItem(n);

    DesignPane pane=Studio.getActiveDesignPane();
    if(pane!=null) {
      FormChanger.addEditForElementSwap(pane, spotSet, n, n+1);
    }
    listWidget.remove(n);
    listWidget.add(s, n + 1);
    listWidget.select(n + 1);
    Object e = spotSet.get(n);
    spotSet.set(n, spotSet.get(n+1));
    spotSet.set(n+1, e);
    btnMoveUp.setEnabled(true);
    btnMoveDown.setEnabled(n + 2 < len);
    notifyPropertyChangeListener(element);
  }

  protected void moveUp() {
    int n = listWidget.getSelectionIndex();

    if (n < 1) {
      return;
    }

    String s = listWidget.getItem(n);

    DesignPane pane=Studio.getActiveDesignPane();
    if(pane!=null) {
      FormChanger.addEditForElementSwap(pane, spotSet, n, n-1);
    }
    listWidget.remove(n);
    listWidget.add(s, n - 1);
    listWidget.select(n - 1);
    Object e = spotSet.get(n);
    spotSet.set(n, spotSet.get(n-1));
    spotSet.set(n-1, e);
    btnMoveUp.setEnabled(n - 1 > 0);
    btnMoveDown.setEnabled(true);
    notifyPropertyChangeListener(element);
  }

  protected void removeSelectedItem() {
    int n = listWidget.getSelectionIndex();

    if (n != -1) {
      DesignPane pane=Studio.getActiveDesignPane();
      if(pane!=null) {
        FormChanger.addEditForElementDelete(pane, spotSet,n);
      }
      spotSet.remove(n);
      listWidget.remove(n);

      if (n < listWidget.getItemCount()) {
        listWidget.select(n);
      } else if (n > 0) {
        listWidget.select(n - 1);
      }

      handleListSelectionChange();
      notifyPropertyChangeListener(element);
    }
  }
}
