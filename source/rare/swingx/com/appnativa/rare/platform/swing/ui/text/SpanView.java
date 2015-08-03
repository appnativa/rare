/*
 * @(#)SpanView.java   2009-05-07
 *
 * Copyright (c) appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.platform.swing.ui.text;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Shape;

import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.IconView;
import javax.swing.text.Position;

import com.appnativa.rare.Platform;
import com.appnativa.rare.viewer.iContainer;

/**
 *
 * @author Don DeCoteau
 */
public class SpanView extends IconView implements HTMLEditorKitEx.iGroupView {
  iContainer theViewer;
  boolean fromVisible=true;
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
    this.fromVisible=visible;
    if(theViewer!=null) {
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
