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

package com.appnativa.rare.platform.swing.ui.view;

import com.appnativa.rare.ui.UIPoint;
import com.appnativa.rare.ui.iScrollerSupport;
import com.appnativa.rare.ui.painter.UIScrollingEdgePainter;

public class aScrollerSupport implements iScrollerSupport {
  iScrollerSupport scrollerSupport;

  @Override
  public boolean isScrolling() {
    return scrollerSupport.isScrolling();
  }

  @Override
  public boolean isAtLeftEdge() {
    return scrollerSupport.isAtLeftEdge();
  }

  @Override
  public boolean isAtRightEdge() {
    return scrollerSupport.isAtRightEdge();
  }

  @Override
  public boolean isAtTopEdge() {
    return scrollerSupport.isAtTopEdge();
  }

  @Override
  public boolean isAtBottomEdge() {
    return scrollerSupport.isAtBottomEdge();
  }

  @Override
  public UIPoint getContentOffset() {
    return scrollerSupport.getContentOffset();
  }

  @Override
  public void setScrollingEdgePainter(UIScrollingEdgePainter painter) {
    scrollerSupport.setScrollingEdgePainter(painter);
  }

  @Override
  public UIScrollingEdgePainter getScrollingEdgePainter() {
    return scrollerSupport.getScrollingEdgePainter();
  }

  @Override
  public void scrollToBottomEdge() {
    scrollerSupport.scrollToBottomEdge();
  }

  @Override
  public void scrollToLeftEdge() {
    scrollerSupport.scrollToLeftEdge();
  }

  @Override
  public void scrollToRightEdge() {
    scrollerSupport.scrollToRightEdge();
  }

  @Override
  public void scrollToTopEdge() {
    scrollerSupport.scrollToTopEdge();
  }

  @Override
  public void moveUpDown(boolean up, boolean block) {
    scrollerSupport.moveUpDown(up, block);
  }

  @Override
  public void moveLeftRight(boolean left, boolean block) {
    scrollerSupport.moveLeftRight(left, block);
  }

  public aScrollerSupport(iScrollerSupport scrollerSupport) {
    this.scrollerSupport = scrollerSupport;
  }
}
