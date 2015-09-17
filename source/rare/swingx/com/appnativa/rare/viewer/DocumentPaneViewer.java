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

    if ((cfg.bgColor.getValue() == null) && (cfg.bgImageURL.getValue() == null)) {
      setBackground(UIColor.WHITE);
    }
  }

  @Override
  protected iPlatformTextEditor createEditor(DocumentPane cfg) {
    return getAppContext().getComponentCreator().getDocumentPane(this, cfg);
  }
}
