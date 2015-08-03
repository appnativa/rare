/*
 * @(#)URLSetEditor.java
 * 
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio.composite;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

public class URLSetEditorComposite extends PrimitiveArrayEditorComposite {

  /**
   * Create the composite.
   * @param parent
   * @param style
   */
  public URLSetEditorComposite(Composite parent, int style) {
    super(parent, style);
  }

  @Override
  protected void createEditor(Composite parent) {
    URLEditorComposite editor = new URLEditorComposite(parent, SWT.NONE);

    editor.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
    editor.setPropertyChangeListener(this, this);
    editor.setCloneItem(false);
    editorComposite = editor;
  }
}
