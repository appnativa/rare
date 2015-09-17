/*
 * Copyright appNativa Inc. All Rights Reserved.
 *
 * This file is part of the Real-time Application Rendering Engine (RARE).
 *
 * RARE is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
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
