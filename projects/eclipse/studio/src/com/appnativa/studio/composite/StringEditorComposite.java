/*
 * @(#)StringEditorComposite.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio.composite;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.wb.swt.SWTResourceManager;

import com.appnativa.studio.Utilities;
import com.appnativa.studio.properties.SPOTInspector;
import com.appnativa.rare.Platform;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.platform.swing.AppContext;
import com.appnativa.spot.iSPOTElement;

public class StringEditorComposite extends BaseEditorComposite {
  private Button       btnResourcePicker;
  private Button       btnTextEditor;
  private Button       btnUpdate;
  private Group        grpEditValue;
  private Group        grpResourceStrings;
  private boolean      multiline;
  private Composite    stackComposite;
  private Text resTextWidget;
  private Composite headerComposite;
  private Button btnApplication;
  private Button btnRuntime;

  /**
   * Create the composite.
   * @param parent
   * @param style
   */
  public StringEditorComposite(Composite parent, final boolean multiline) {
    super(parent, SWT.NONE);
    this.multiline = multiline;

    GridLayout gridLayout = new GridLayout(1, false);

    gridLayout.horizontalSpacing = 0;
    gridLayout.verticalSpacing   = 0;
    gridLayout.marginWidth       = 0;
    gridLayout.marginHeight      = 0;
    setLayout(gridLayout);

    headerComposite = new Composite(this, SWT.NONE);

    headerComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
    headerComposite.setLayout(new GridLayout(3, false));

    Composite rbComposite = new Composite(headerComposite, SWT.NONE);

    rbComposite.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1));
    rbComposite.setLayout(new GridLayout(2, false));
    btnTextEditor = new Button(rbComposite, SWT.RADIO);
    btnTextEditor.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        
        ((StackLayout) stackComposite.getLayout()).topControl = grpEditValue;
        stackComposite.layout(true);
      }
    });
    btnTextEditor.setSelection(true);
    btnTextEditor.setText("Editor");
    btnResourcePicker = new Button(rbComposite, SWT.RADIO);
    btnResourcePicker.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        ((StackLayout) stackComposite.getLayout()).topControl = grpResourceStrings;
        stackComposite.layout(true);
      }
    });
    btnResourcePicker.setText("Resource Picker");
    btnResourcePicker.setEnabled(!multiline);

    Label    labelSep1    = new Label(headerComposite, SWT.SEPARATOR | SWT.VERTICAL);
    GridData gd_labelSep1 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);

    gd_labelSep1.heightHint = 10;
    labelSep1.setLayoutData(gd_labelSep1);
    btnUpdate = new Button(headerComposite, SWT.NONE);
    btnUpdate.setEnabled(multiline);
    btnUpdate.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        if (listWidget.isVisible()) {
          valueChanged(listWidget);
        } else {
          valueChanged(textWidget);
        }
      }
    });
    btnUpdate.setText("Update");
    stackComposite = new Composite(this, SWT.NONE);
    GridData gd_stackComposite = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
    gd_stackComposite.minimumWidth = 100;
    if(!multiline) {
      gd_stackComposite.heightHint=30;
    }
    gd_stackComposite.minimumHeight=30;
    stackComposite.setLayoutData(gd_stackComposite);
    stackComposite.setLayout(new StackLayout());
    grpEditValue = new Group(stackComposite, SWT.NONE);
    setupTextEditor(grpEditValue);
    grpResourceStrings = new Group(stackComposite, SWT.NONE);
    setupResourcePicker(grpResourceStrings);
    ((StackLayout) stackComposite.getLayout()).topControl = grpEditValue;
  }

  public void hideHeader() {
    setVisible(headerComposite, false);
    setVisible(grpResourceStrings, false);
  }

  public void showHeader() {
    setVisible(headerComposite, true);
    setVisible(grpResourceStrings, true);
  }
  
  @Override
  public void setSelectedItem(iSPOTElement element) {
    super.setSelectedItem(element);

    String s = SPOTInspector.getComponentCategory(element);

    if (!"Appearance".equals(s)) {
      setVisible(grpResourceStrings, false);

      if (btnResourcePicker.getSelection()) {
        btnTextEditor.setSelection(true);
        ((StackLayout) stackComposite.getLayout()).topControl = grpEditValue;
        stackComposite.layout(true);
      }
    } else {
      setVisible(grpResourceStrings, true);
      btnResourcePicker.setEnabled(!multiline);
    }
  }
  @Override
  protected void resetValue(Widget widget) {
    if(grpResourceStrings.isVisible()) {
      String s=element==null ? null : element.toString();
      
      int n=s==null ? -1 : s.indexOf(iConstants.RESOURCE_PREFIX);
      s=n==-1 ? null :s.substring(n+iConstants.RESOURCE_PREFIX_LENGTH);
      java.util.List<String> list;
      if(s==null || !s.startsWith("Rare.")) {
        list = (Utilities.getProjectStrings((AppContext) Platform.getAppContext()));
        btnApplication.setSelection(true);
      }
      else {
        list = (Utilities.getRareStrings((AppContext) Platform.getAppContext()));
        btnRuntime.setSelection(true);
      }
      resetList(list);
      if(s!=null && s.endsWith("}")) {
        n=listWidget.indexOf(s.substring(0,s.length()-1));
        if(n!=-1) {
          listWidget.setSelection(n);
        }
      }
    }
    super.resetValue(widget);
    if(grpResourceStrings.isVisible()) {
      updateResText();
    }
  }
  void updateResText() {
    int n=listWidget.getSelectionIndex();
    listWidget.showSelection();
    String text=n==-1 ? null : Platform.getAppContext().getResourceAsString(listWidget.getItem(n));
    resTextWidget.setText(text==null ? "" :text);
    
  }
  @Override
  protected String getElementText(Widget widget) {
    String s=super.getElementText(widget);
    updateResText();
    return s;
  }
  public void setSelectedText(String text) {
    element  = null;
    oldValue = text;
    resetValue(null);
  }

  public boolean isMultiline() {
    return multiline;
  }

  void setupTextEditor(Group grpEditValue) {
    grpEditValue.setText("Edit Value");

    GridLayout gridLayout = new GridLayout(1, false);

    gridLayout.horizontalSpacing = 0;
    gridLayout.verticalSpacing   = 0;
    gridLayout.marginWidth       = 0;
    gridLayout.marginHeight      = 0;
    grpEditValue.setLayout(gridLayout);

    if (multiline) {
      textWidget = new Text(grpEditValue, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
    } else {
      textWidget = new Text(grpEditValue, SWT.BORDER | SWT.SINGLE | SWT.WRAP);
    }

    GridData gd_text = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);

    textWidget.setLayoutData(gd_text);
    textWidget.addTraverseListener(this);
    textWidget.addFocusListener(this);
    createAttributesEditor(grpEditValue, 1);
  }

  protected void resetList(java.util.List<String> items) {
    listWidget.deselectAll();
    listWidget.removeAll();

    for (String s : items) {
      listWidget.add(s);
    }
  }

  protected void setupResourcePicker(Group picker) {
    picker.setText("Resource Strings");

    GridLayout gl_grpResourceStrings = new GridLayout(1, false);

    gl_grpResourceStrings.verticalSpacing   = 2;
    gl_grpResourceStrings.horizontalSpacing = 0;
    gl_grpResourceStrings.marginHeight      = 0;
    gl_grpResourceStrings.marginWidth       = 0;
    picker.setLayout(gl_grpResourceStrings);

    Composite composite = new Composite(picker, SWT.NONE);

    composite.setLayout(new FillLayout(SWT.HORIZONTAL));
    composite.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1));

    btnApplication = new Button(composite, SWT.RADIO);

    btnApplication.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        resetList(Utilities.getProjectStrings((AppContext) Platform.getAppContext()));
      }
    });
    btnApplication.setSelection(true);
    btnApplication.setText("Application");

    btnRuntime = new Button(composite, SWT.RADIO);

    btnRuntime.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        resetList(Utilities.getRareStrings((AppContext) Platform.getAppContext()));
      }
    });
    btnRuntime.setText("Runtime");
    listWidget = new List(picker, SWT.BORDER | SWT.V_SCROLL);
    GridData gd_listWidget = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
    gd_listWidget.heightHint = 130;
    listWidget.setLayoutData(gd_listWidget);
    listWidget.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseDoubleClick(MouseEvent e) {
        valueChanged(e.widget);
      }
    });
    listWidget.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        int n = listWidget.getSelectionIndex();

        if (n == -1) {
          resTextWidget.setText("");
          btnUpdate.setEnabled(false);
        } else {
          resTextWidget.setText(Platform.getResourceAsString(listWidget.getItem(n)));
          btnUpdate.setEnabled(true);
        }
      }
    });

    resTextWidget = new Text(picker, SWT.BORDER | SWT.READ_ONLY | SWT.V_SCROLL | SWT.MULTI);
    resTextWidget.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
    resTextWidget.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
  }
  

  @Override
  protected boolean isListWidgetResourceList() {
    return true;
  }
 }
