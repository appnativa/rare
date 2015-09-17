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

package com.appnativa.rare.platform.swing.ui.text;

import com.appnativa.rare.Platform;
import com.appnativa.rare.viewer.iContainer;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Shape;

import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.IconView;
import javax.swing.text.Position;

/**
 *
 * @author Don DeCoteau
 */
public class SpanView extends IconView implements HTMLEditorKitEx.iGroupView {
  iContainer theViewer;
  boolean    fromVisible = true;

  public SpanView(iContainer parent, Element elem) {
    super(elem);
    theViewer = FormsView.createViewer(this, parent, elem);
    theViewer.setParent(parent);

    if (!fromVisible) {
      Platform.invokeLater(new Runnable() {
        @Override
        public void run() {
          setContainerVisible(false);
        }
      });
    }
  }

  @Override
  public Shape modelToView(int pos, Shape a, Position.Bias b) throws BadLocationException {
    Rectangle r = a.getBounds();

    r.width = 0;

    return r;
  }

  @Override
  public void paint(Graphics g, Shape a) {}

  /**
   * @param visible the visible to set
   */
  @Override
  public void setContainerVisible(boolean visible) {
    this.fromVisible = visible;

    if (theViewer != null) {
      int        len = theViewer.getWidgetCount();
      iContainer gb  = theViewer;

      for (int i = 0; i < len; i++) {
        gb.getWidget(i).setVisible(visible);
      }

      theViewer.getParent().update();
    }
  }

  @Override
  public float getPreferredSpan(int axis) {
    return 0;
  }

  @Override
  public iContainer getViewer() {
    return theViewer;
  }

  @Override
  public boolean isVisible() {
    return false;
  }
}
