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

package com.appnativa.rare.ui;

import com.appnativa.rare.ui.painter.UIScrollingEdgePainter;

public class ScrollPanePanel extends XPContainer implements iScrollerSupport {
  private iScrollerSupport scrollSupport;

  public ScrollPanePanel(Object view) {
    super(view);

    if (view instanceof iScrollerSupport) {
      scrollSupport = (iScrollerSupport) view;
    }
  }

  @Override
  public void moveUpDown(boolean up, boolean block) {
    scrollSupport.moveUpDown(up, block);
  }

  @Override
  public void moveLeftRight(boolean left, boolean block) {
    scrollSupport.moveLeftRight(left, block);
  }

  @Override
  public void scrollToBottomEdge() {
    scrollSupport.scrollToBottomEdge();
  }

  @Override
  public void scrollToLeftEdge() {
    scrollSupport.scrollToLeftEdge();
  }

  @Override
  public void scrollToRightEdge() {
    scrollSupport.scrollToRightEdge();
  }

  @Override
  public void scrollToTopEdge() {
    scrollSupport.scrollToTopEdge();
  }

  public iScrollerSupport getScrollSupport() {
    return scrollSupport;
  }

  public void setScrollSupport(iScrollerSupport scrollSupport) {
    this.scrollSupport = scrollSupport;
  }

  @Override
  public boolean isScrolling() {
    return scrollSupport.isScrolling();
  }

  @Override
  public boolean isAtLeftEdge() {
    return scrollSupport.isAtLeftEdge();
  }

  @Override
  public boolean isAtRightEdge() {
    return scrollSupport.isAtRightEdge();
  }

  @Override
  public void setScrollingEdgePainter(UIScrollingEdgePainter painter) {
    scrollSupport.setScrollingEdgePainter(painter);
  }

  @Override
  public UIScrollingEdgePainter getScrollingEdgePainter() {
    return scrollSupport.getScrollingEdgePainter();
  }

  @Override
  public boolean isAtTopEdge() {
    return scrollSupport.isAtTopEdge();
  }

  @Override
  public boolean isAtBottomEdge() {
    return scrollSupport.isAtBottomEdge();
  }

  @Override
  public UIPoint getContentOffset() {
    return scrollSupport.getContentOffset();
  }
}
