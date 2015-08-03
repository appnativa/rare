/*
 * @(#)ListView.java   2010-06-18
 *
 * Copyright (c) 2007-2009 appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

/**
 * Copyright 2006 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.appnativa.rare.platform.swing.ui.text;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Shape;

import javax.swing.text.Element;
import javax.swing.text.View;
import javax.swing.text.html.BlockView;

/**
 *
 * @author Don DeCoteau
 */
public class ListView extends BlockView {
  private ListPainter listPainter;

  /**
   * Creates a new view that represents a list element.
   *
   * @param elem the element to create a view for
   */
  public ListView(Element elem) {
    super(elem, View.Y_AXIS);
  }

  /**
   * Renders using the given rendering surface and area on that
   * surface.
   *
   * @param g the rendering surface to use
   * @param allocation the allocated region to render into
   * @see View#paint
   */
  @Override
  public void paint(Graphics g, Shape allocation) {
    super.paint(g, allocation);

    Rectangle alloc = allocation.getBounds();
    Rectangle clip  = g.getClipBounds();

    // Since listPainter paints in the insets we have to check for the
    // case where the child is not painted because the paint region is
    // to the left of the child. This assumes the ListPainter paints in
    // the left margin.
    if ((clip.x + clip.width) < (alloc.x + getLeftInset())) {
      Rectangle childRect = alloc;

      alloc = getInsideAllocation(allocation);

      int n    = getViewCount();
      int endY = clip.y + clip.height;

      for (int i = 0; i < n; i++) {
        childRect.setBounds(alloc);
        childAllocation(i, childRect);

        if (childRect.y < endY) {
          if ((childRect.y + childRect.height) >= clip.y) {
            listPainter.paint(g, childRect.x, childRect.y, childRect.width, childRect.height, this, i);
          }
        } else {
          break;
        }
      }
    }
  }

  /**
   * Calculates the desired shape of the list.
   *
   * @return the desired span
   * @see View#getPreferredSpan
   */
  @Override
  public float getAlignment(int axis) {
    switch(axis) {
      case View.X_AXIS :
        return 0.5f;

      case View.Y_AXIS :
        return 0.5f;

      default :
        throw new IllegalArgumentException("Invalid axis: " + axis);
    }
  }

  /**
   * Paints one of the children; called by paint().  By default
   * that is all it does, but a subclass can use this to paint
   * things relative to the child.
   *
   * @param g the graphics context
   * @param alloc the allocated region to render the child into
   * @param index the index of the child
   */
  @Override
  protected void paintChild(Graphics g, Rectangle alloc, int index) {
    listPainter.paint(g, alloc.x, alloc.y, alloc.width, alloc.height, this, index);
    super.paintChild(g, alloc, index);
  }

  @Override
  protected void setPropertiesFromAttributes() {
    super.setPropertiesFromAttributes();
    listPainter = new ListPainter(getAttributes(), getStyleSheet());
  }
}
