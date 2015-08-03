/*
 * @(#)DocumentPaneViewer.java   2012-02-13
 *
 * Copyright (c) 2007-2009 appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.viewer;

import com.appnativa.rare.spot.DocumentPane;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.text.iPlatformTextEditor;
import com.appnativa.rare.ui.text.iTextAttributes;

public class DocumentPaneViewer extends aDocumentPaneViewer {
  public DocumentPaneViewer() {
    super();
  }

  public DocumentPaneViewer(iContainer parent) {
    super(parent);
  }

  @Override
  public iTextAttributes getAttributeSet(int pos, boolean paragraph) {
    return null;
  }

  @Override
  public iTextAttributes getAttributeSetAtCursor(boolean paragraph) {
    return null;
  }

  @Override
  public iTextAttributes getAttributeSetForSelection(boolean paragraph) {
    return null;
  }

  @Override
  public void newHTMLDocument() {
    htmlDocument = true;
    textEditor.setText("", true);
  }

  @Override
  public void newPlainTextDocument() {
    htmlDocument = false;
    textEditor.setText("", false);
  }

  @Override
  protected void configureEx(DocumentPane cfg) {
    super.configureEx(cfg);
    if (cfg.bgColor.getValue() == null && cfg.bgImageURL.getValue() == null) {
      setBackground(UIColor.WHITE);
    }
  }

  @Override
  protected iPlatformTextEditor createEditor(DocumentPane cfg) {
    return getAppContext().getComponentCreator().getDocumentPane(this, cfg);
  }
}
