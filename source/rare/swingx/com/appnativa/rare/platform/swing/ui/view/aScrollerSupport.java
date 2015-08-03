package com.appnativa.rare.platform.swing.ui.view;

import com.appnativa.rare.ui.UIPoint;
import com.appnativa.rare.ui.iScrollerSupport;
import com.appnativa.rare.ui.painter.UIScrollingEdgePainter;

public class aScrollerSupport implements iScrollerSupport{
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
    this.scrollerSupport=scrollerSupport;
  }

}
