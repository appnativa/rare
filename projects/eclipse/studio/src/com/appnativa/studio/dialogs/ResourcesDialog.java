/*
 * @(#)ResourcesDialog.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio.dialogs;

import com.appnativa.studio.Studio;
import com.appnativa.studio.Utilities;
import com.appnativa.studio.composite.BaseEditorComposite;

import com.appnativa.rare.Platform;
import com.appnativa.rare.platform.swing.AppContext;
import com.appnativa.rare.ui.UIImageIcon;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;

import java.awt.image.BufferedImage;

public class ResourcesDialog extends Dialog {
  private Button                 btnRuntime;
  private java.util.List<String> list;
  private List                   listWidget;
  private Preview                previewComposite;
  private String                 selectedResource;

  /**
   * Create the dialog.
   * @param parentShell
   * @param string
   */
  public ResourcesDialog(Shell parentShell) {
    super(parentShell);
    setShellStyle(SWT.DIALOG_TRIM | SWT.RESIZE | SWT.APPLICATION_MODAL);
  }

  public void setSelectedResource(String selection) {
    if (selection != null) {
      if (listWidget != null) {
        int n = listWidget.indexOf(selection);

        if (n != -1) {
          listWidget.setSelection(n);
          selectionChanged();
        } else {
          list = Utilities.getRareIcons((AppContext) Platform.getAppContext());
          n    = list.indexOf(selection);

          if (n != -1) {
            btnRuntime.setSelection(true);
            resetList(list);
            listWidget.select(n);
          }
        }
      }
    }
  }

  public String getSelectedResource() {
    return selectedResource;
  }

  @Override
  protected void configureShell(Shell newShell) {
    super.configureShell(newShell);
    newShell.setText(Studio.getResourceAsString("Studio.text.design.config_resource_icons"));
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

  /**
   * Create contents of the dialog.
   * @param parent
   */
  @Override
  protected Control createDialogArea(Composite parent) {
    Composite container = (Composite) super.createDialogArea(parent);

    container.setLayout(new GridLayout(1, false));

    Composite composite = new Composite(container, SWT.NONE);

    composite.setLayout(new FillLayout(SWT.HORIZONTAL));
    composite.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1));

    Button btnApplication = new Button(composite, SWT.RADIO);

    btnApplication.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        resetList(Utilities.getProjectIcons((AppContext) Platform.getAppContext()));
      }
    });
    btnApplication.setSelection(true);
    btnApplication.setText("Application");
    btnRuntime = new Button(composite, SWT.RADIO);
    btnRuntime.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        resetList(Utilities.getRareIcons((AppContext) Platform.getAppContext()));
      }
    });
    btnRuntime.setText("Runtime");
    listWidget = new List(container, SWT.BORDER | SWT.V_SCROLL);
    listWidget.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseDoubleClick(MouseEvent e) {
        if (listWidget.getSelectionIndex() != -1) {
          okPressed();
        }
      }
    });
    listWidget.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        selectionChanged();
      }
    });

    GridData gd_listWidget = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);

    gd_listWidget.minimumHeight = 100;
    listWidget.setLayoutData(gd_listWidget);

    Label lblPreview = new Label(container, SWT.SEPARATOR | SWT.HORIZONTAL);

    lblPreview.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
    previewComposite = new Preview(container, SWT.NONE);
    previewComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
    resetList(Utilities.getProjectIcons((AppContext) Platform.getAppContext()));

    return container;
  }

  @Override
  protected void okPressed() {
    if (listWidget.getSelectionIndex() != -1) {
      selectedResource = listWidget.getItem(listWidget.getSelectionIndex());
    }

    super.okPressed();
  }

  protected void resetList(java.util.List<String> items) {
    listWidget.deselectAll();
    listWidget.removeAll();

    for (String s : items) {
      listWidget.add(s);
    }
  }

  protected void selectionChanged() {
    int         n    = listWidget.getSelectionIndex();
    UIImageIcon icon = (UIImageIcon) ((n == -1)
                                      ? null
                                      : Platform.getResourceAsIcon(listWidget.getItem(n)));

    previewComposite.createPreview(icon);
    previewComposite.redraw();
  }

  /**
   * Return the initial size of the dialog.
   */
  @Override
  protected Point getInitialSize() {
    return new Point(450, 425);
  }

  static class Preview extends BaseEditorComposite implements PaintListener {
    UIImageIcon icon;

    public Preview(Composite parent, int style) {
      super(parent, style);
      addPaintListener(this);
    }

    public void createPreview(UIImageIcon icon) {
      if (this.icon != icon) {
        this.icon = icon;

        if (previewImage != null) {
          previewImage.dispose();
        }

        previewImage = null;
      }
    }

    @Override
    public void dispose() {
      super.dispose();
      icon = null;
    }

    @Override
    public void paintControl(PaintEvent e) {
      if (this.icon != null) {
        if (previewImage == null) {
          BufferedImage img = icon.getImage().getBufferedImage();

          if (img != null) {
            previewImage = Utilities.convertToSWTImage(img);
          }
        }

        paintPreview(e.gc, this);
      }
    }
  }
}
