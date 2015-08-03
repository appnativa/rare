/*
 * @(#)ScreenSupport.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio.wizards;

import com.appnativa.studio.Studio;
import com.appnativa.studio.composite.BaseEditorComposite;
import com.appnativa.studio.wizards.NewLayoutPage.ContentProvider;
import com.appnativa.studio.wizards.NewLayoutPage.Node;
import com.appnativa.studio.wizards.NewLayoutPage.ViewLabelProvider;

import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class ScreenSupport extends Composite {
  private Button      btnActionBar;
  private Button      btnMenuBar;
  private Button      btnStatusBar;
  private Button      btnToolBar;
  private Combo       iconDensity;
  private Label       lblBars;
  private Label       lblDesktopIconDensity;
  private Label       lblScreenSize;
  private Text        screenPointSize;
  private ComboViewer workspaceViewer;
  private Composite composite_1;
  private Button btnShowTitle;

  /**
   * Create the composite.
   * @param parent
   * @param style
   */
  public ScreenSupport(Composite parent, int style) {
    super(parent, style);

    GridLayout gridLayout = new GridLayout(2, false);

    gridLayout.verticalSpacing   = 2;
    gridLayout.marginWidth       = 2;
    gridLayout.marginHeight      = 2;
    gridLayout.horizontalSpacing = 2;
    setLayout(gridLayout);
    lblScreenSize = new Label(this, SWT.NONE);
    lblScreenSize.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
    lblScreenSize.setText("Screen Size");

    Label label = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);

    label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));

    Label lblWHMax = new Label(this, SWT.NONE);

    lblWHMax.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    lblWHMax.setText("Max Landscape Pt Size:");
    screenPointSize = new Text(this, SWT.BORDER);

    GridData gd_screenPointSize = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);

    gd_screenPointSize.widthHint = 50;
    screenPointSize.setLayoutData(gd_screenPointSize);

    Label lblWorkspaceViewer = new Label(this, SWT.NONE);

    lblWorkspaceViewer.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    lblWorkspaceViewer.setText("Main Viewer:");
    workspaceViewer = new ComboViewer(this, SWT.READ_ONLY);

    Combo combo = workspaceViewer.getCombo();

    combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
    workspaceViewer.setContentProvider(new ContentProvider());
    workspaceViewer.setLabelProvider(new ViewLabelProvider());
    workspaceViewer.setInput("");    //$NON-NLS-1$
    selectTabPaneViewer();
    lblBars = new Label(this, SWT.NONE);
    lblBars.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    lblBars.setText("Bars:");

    Composite composite = new Composite(this, SWT.NONE);

    composite.setLayout(new FillLayout(SWT.HORIZONTAL));
    btnActionBar = new Button(composite, SWT.CHECK);
    btnActionBar.setSelection(true);
    btnActionBar.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        boolean b=btnActionBar.getSelection();
        if(b) {
          btnMenuBar.setSelection(false);
        }
        btnShowTitle.setEnabled(b);
      }
    });
    btnActionBar.setText("Action bar");
    btnToolBar = new Button(composite, SWT.CHECK);
    btnToolBar.setSize(67, 18);
    btnToolBar.setText("Tool Bar");
    btnStatusBar = new Button(composite, SWT.CHECK);
    btnStatusBar.setSize(76, 18);
    btnStatusBar.setText("Status Bar");
    new Label(this, SWT.NONE);
    
    composite_1 = new Composite(this, SWT.NONE);
    composite_1.setLayout(new FillLayout(SWT.HORIZONTAL));
    
    btnShowTitle = new Button(composite_1, SWT.CHECK);
    btnShowTitle.setSelection(true);
    btnShowTitle.setText("Show Application Title");
    lblDesktopIconDensity = new Label(this, SWT.NONE);
    lblDesktopIconDensity.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    lblDesktopIconDensity.setText("Desktop Icon Density:");
    iconDensity = new Combo(this, SWT.READ_ONLY);
    iconDensity.setItems(new String[] { "ldpi", "mdpi" });

    GridData gd_iconDensity = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);

    gd_iconDensity.widthHint = 50;
    iconDensity.setLayoutData(gd_iconDensity);
    iconDensity.select(0);
    new Label(this, SWT.NONE);
    btnMenuBar = new Button(this, SWT.CHECK);
    btnMenuBar.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        if(btnMenuBar.getSelection()) {
          btnActionBar.setSelection(false);
        }
      }
    });
    btnMenuBar.setText("Desktop Menu Bar");
  }

  public boolean wantsActionBar() {
    return btnActionBar.getSelection();
  }

  public boolean wantsMenuBar() {
    return btnMenuBar.getSelection();
  }

  public boolean wantsStatusBar() {
    return btnStatusBar.getSelection();
  }

  public boolean wantsToolBar() {
    return btnToolBar.getSelection();
  }
  public boolean showTitle() {
    return btnShowTitle.getSelection();
  }
  public void setInfo(String name, String maxDpi, boolean touch) {
    btnActionBar.setSelection(touch);
    btnStatusBar.setSelection(false);
    btnToolBar.setSelection(false);
    btnMenuBar.setSelection(false);
    btnStatusBar.setEnabled(!touch);
    btnMenuBar.setEnabled(!touch);
    btnToolBar.setEnabled(!touch);
    screenPointSize.setText(maxDpi);
    lblScreenSize.setText(name);
    screenPointSize.setEnabled(true);
    BaseEditorComposite.setVisible(iconDensity, !touch);
    BaseEditorComposite.setVisible(lblDesktopIconDensity, !touch);
    BaseEditorComposite.setVisible(btnMenuBar, !touch);
  }

  public void setLargeScreenMode(boolean supportDesktop) {
    setSingleScreenMode(supportDesktop);
    lblScreenSize.setText(Studio.getResourceAsString("Studio.text.largeScreen"));
  }

  public void setSingleScreenMode(boolean supportDesktop) {
    lblScreenSize.setText(Studio.getResourceAsString("Studio.text.anyScreen"));
    screenPointSize.setText("<Any>");
    screenPointSize.setEnabled(false);
    BaseEditorComposite.setVisible(iconDensity, supportDesktop);
    BaseEditorComposite.setVisible(lblDesktopIconDensity, supportDesktop);
    BaseEditorComposite.setVisible(btnMenuBar, supportDesktop);
  }

  public String getDesktopIconDensity() {
    return iconDensity.getText();
  }

  public String getScreenPointSize() {
    return screenPointSize.getText();
  }

  public String getWorkspaceViewer() {
    IStructuredSelection sel = ((IStructuredSelection) workspaceViewer.getSelection());

    if ((sel != null) &&!sel.isEmpty()) {
      return ((Node) sel.getFirstElement()).id;
    }

    return null;
  }

  @Override
  protected void checkSubclass() {}

  private void selectTabPaneViewer() {
    int len = workspaceViewer.getCombo().getItemCount();

    for (int i = 0; i < len; i++) {
      Node node = (Node) workspaceViewer.getElementAt(i);

      if (node.id.equals("Studio.icon.toolbox.tabpane")) {
        workspaceViewer.setSelection(new StructuredSelection(node));
      }
    }
  }
}
