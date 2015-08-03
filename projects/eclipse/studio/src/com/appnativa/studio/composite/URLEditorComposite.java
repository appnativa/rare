/*
 * @(#)URLEditorComposite.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio.composite;

import com.appnativa.studio.Studio;
import com.appnativa.studio.RMLDocument;
import com.appnativa.studio.dialogs.SWTResourceSelectionDialogEx;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.widgets.Composite;

public class URLEditorComposite extends aDialogEditorFieldComposite {
  public URLEditorComposite(Composite parent, int style) {
    super(parent, style, "URL:");
    createAttributesEditor(this, 3);

    RMLDocument doc = Studio.getSelectedDocument();

    if ((doc == null) || (doc.getIProject() == null)) {
      btnDialog.setEnabled(false);
    } else {
      btnDialog.setEnabled(true);
    }
  }

  @Override
  public void focusLost(FocusEvent e) {
    if (checkForTextWidgetChange(e.widget, textWidget)) {
      super.focusLost(e);
    }
  }

  @Override
  public void keyTraversed(TraverseEvent e) {
    if ((e.detail != SWT.TRAVERSE_RETURN) || checkForTextWidgetChange(e.widget, textWidget)) {
      super.keyTraversed(e);
    }
  }

  @Override
  protected String openDialog(String currentValue) {
    RMLDocument                  doc    = Studio.getSelectedDocument();
    SWTResourceSelectionDialogEx dialog = new SWTResourceSelectionDialogEx(getShell(), doc.getIProject(),
                                            "Select Resource");

    dialog.setProject(doc.getProject());
    dialog.setDocumentPath(doc.getAssetsRelativePrefix());
    dialog.setDirectory(doc.getProject().getURLFolder(currentValue));
    dialog.open();

    String s = dialog.getSelectedResource();

    if (s != null) {
      return s;
    }

    return currentValue;
  }
}
