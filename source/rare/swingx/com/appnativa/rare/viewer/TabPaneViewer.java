/*
 * @(#)TabPaneViewer.java   2012-03-17
 *
 * Copyright (c) 2007-2009 appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.viewer;

import com.appnativa.rare.Platform;
import com.appnativa.rare.spot.TabPane;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.iTabDocument;
import com.appnativa.rare.ui.iTabPaneComponent;
import com.appnativa.rare.ui.tabpane.TabDocument;
import com.appnativa.rare.ui.tabpane.TabPaneComponent;

public class TabPaneViewer extends aTabPaneViewer {
  public TabPaneViewer() {
    super();
  }

  public TabPaneViewer(iContainer parent) {
    super(parent);
  }

  @Override
  protected iTabDocument createNewDocument(String name, String title, iPlatformIcon icon) {
    TabDocument doc = new TabDocument(getAppContext(), name, (iTabDocument.iDocumentListener) tabPane);

    doc.setTitle(title);
    doc.setIcon(icon);

    return doc;
  }

  @Override
  protected iTabPaneComponent createTabPaneComponent(TabPane cfg) {
    TabPaneComponent p = new TabPaneComponent(this);

    if (isDesignMode()) {
      p.setDefaultMinimumSize(200, 100, true);
    }

    return p;
  }

  @Override
  protected int getDefaultTabStyle() {
    if (Platform.isIOS()) {
      return TabPane.CTabStyle.box;
    }

    return super.getDefaultTabStyle();
  }
}
