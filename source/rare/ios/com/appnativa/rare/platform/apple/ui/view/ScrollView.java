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

package com.appnativa.rare.platform.apple.ui.view;

import com.appnativa.rare.platform.apple.ui.iAppleLayoutManager;
import com.appnativa.rare.platform.apple.ui.iApplePainterSupport;
import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.Container;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.UIPoint;
import com.appnativa.rare.ui.iPlatformBorder;
import com.appnativa.rare.ui.iScrollerSupport;
import com.appnativa.rare.ui.painter.UIScrollingEdgePainter;

/*-[
 #import "RAREAPScrollView.h"
 ]-*/
public class ScrollView extends ParentView implements iApplePainterSupport, iAppleLayoutManager, iScrollerSupport {
  ScrollBarView                  hsb;
  ScrollBarView                  vsb;
  private boolean                hasVerticalScrollbar   = true;
  private boolean                hasHorizontalScrollbar = true;
  UIDimension                    measureSize            = new UIDimension();
  private UIScrollingEdgePainter scrollingEdgePainter;

  public ScrollView() {
    super(createProxy());
  }

  protected ScrollView(Object uiview) {
    super(uiview);
  }

  public void add(int position, View view) {
    setContentView(view);
  }

  @Override
  public void layout(ParentView view, float width, float height) {
    if (!(component instanceof Container)) {
      return;
    }

    Container parent = (Container) component;

    if (parent.getComponentCount() == 0) {
      return;
    }

    Component c = (Component) parent.getComponentAt(0);

    c.getPreferredSize(measureSize, (int) width);

    float w = measureSize.width;
    float h = measureSize.height;

    if (w < width) {
      w = width;
    }

    if (h < height) {
      h = height;
    }

    if (!hasHorizontalScrollbar) {
      w = width;
    }

    if (!hasVerticalScrollbar) {
      h = height;
    }

    c.setSize(w, h);
    setContentSize(w, h);
  }

  public native boolean isAtBottomEdge()
  /*-[
    if(!hasVerticalScrollbar_) {
      return YES;
    }
    UIScrollView* sv=(UIScrollView*)proxy_;
    CGPoint p=sv.contentOffset;
    CGSize size=sv.contentSize;
    return size.height-p.y<=sv.frame.size.height;
  ]-*/
  ;

  public native void scrollToLeftEdge()
  /*-[
  UIScrollView* sv=(UIScrollView*)proxy_;
  CGPoint p=sv.contentOffset;
  if(p.x!=0) {
    p.x=0;
    sv.contentOffset=p;
  }
  ]-*/
  ;

  public native void scrollToTopEdge()
  /*-[
  UIScrollView* sv=(UIScrollView*)proxy_;
  CGPoint p=sv.contentOffset;
  if(p.y!=0) {
    p.y=0;
    sv.contentOffset=p;
  }
  ]-*/
  ;

  public native void scrollToRightEdge()
  /*-[
    UIScrollView* sv=(UIScrollView*)proxy_;
    CGPoint p=sv.contentOffset;
    CGSize size=sv.contentSize;
    CGFloat x=MAX(size.width-sv.frame.size.width,0);
    if(p.x!=x) {
      p.x=x;
      sv.contentOffset=p;
    }
  ]-*/
  ;

  public native void scrollToBottomEdge()
  /*-[
    UIScrollView* sv=(UIScrollView*)proxy_;
    CGPoint p=sv.contentOffset;
    CGSize size=sv.contentSize;
    CGFloat y=MAX(size.height-sv.frame.size.height,0);
    if(p.y!=y) {
      p.y=y;
      sv.contentOffset=p;
    }
  ]-*/
  ;

  public native boolean isAtLeftEdge()
  /*-[
    if(!hasHorizontalScrollbar_) {
      return YES;
    }
    return ((UIScrollView*)proxy_).contentOffset.x==0;
  ]-*/
  ;

  public native boolean isAtRightEdge()
  /*-[
    if(!hasHorizontalScrollbar_) {
      return YES;
    }
    UIScrollView* sv=(UIScrollView*)proxy_;
    CGPoint p=sv.contentOffset;
    CGSize size=sv.contentSize;
    return size.width-p.x<=sv.frame.size.width;
  ]-*/
  ;

  public native boolean isAtTopEdge()
  /*-[
    if(!hasVerticalScrollbar_) {
      return YES;
    }
    return ((UIScrollView*)proxy_).contentOffset.y==0;
   ]-*/
  ;

  public void removeChildren() {}

  public void unwrap() {
    if (hsb != null) {
      hsb.dispose();
    }

    if (vsb != null) {
      vsb.dispose();
    }

    proxy = null;
    hsb   = null;
    vsb   = null;
  }

  public static ScrollView wrap(Object uiview) {
    ScrollView sv = new ScrollView();

    sv.proxy = uiview;

    return sv;
  }

  public native void setAutoHideScrollbars(boolean autoHide)
  /*-[
  ]-*/
  ;

  @Override
  public void setComponent(Component component) {
    if (!(component instanceof Container)) {
      throw new IllegalArgumentException("must be a Container");
    }

    super.setComponent(component);
  }

  public native void setContentView(View view)
  /*-[
    [((UIScrollView*)proxy_) addSubview: (UIView*)view->proxy_];
  ]-*/
  ;

  public void setHasHorizontalScrollBar(boolean hasHorizontalScrollbar) {
    this.hasHorizontalScrollbar = hasHorizontalScrollbar;
  }

  public void setHasVerticalScrollBar(boolean hasVerticalScrollbar) {
    this.hasVerticalScrollbar = hasVerticalScrollbar;
  }

  public native void setShowsHorizontalScrollIndicator(boolean show)
  /*-[
    UIScrollView* sv=(UIScrollView*)proxy_;
    [sv setShowsHorizontalScrollIndicator: show];
  ]-*/
  ;

  public native void setShowsVerticalScrollIndicator(boolean show)
  /*-[
    UIScrollView* sv=(UIScrollView*)proxy_;
    [sv setShowsVerticalScrollIndicator: show];
  ]-*/
  ;

  public native void setScrollEnabled(boolean enabled)
  /*-[
    UIScrollView* sv=(UIScrollView*)proxy_;
    sv.scrollEnabled=enabled;
  ]-*/
  ;

  public ScrollBarView getHorizontalScrollBar() {
    if (hsb == null) {
      hsb = new ScrollBarView(this, true);
    }

    return hsb;
  }

  @Override
  public void getMinimumSize(UIDimension size) {
    Container container = (Container) component;
    Component child     = null;

    if (container.getComponentCount() > 0) {
      child = (Component) container.getComponentAt(0);
      child.getMinimumSize(size);
    }

    iPlatformBorder border = container.getBorder();
    UIInsets        insets = (border == null)
                             ? null
                             : border.getBorderInsets(null);

    if (insets != null) {
      size.width  += insets.left + insets.right;
      size.height += insets.top + insets.bottom;
    }
  }

  public UIDimension getPreferredSize(ParentView view) {
    UIDimension size = new UIDimension(0, 0);

    getPreferredSize(size, 0);

    return size;
  }

  @Override
  public void getPreferredSize(UIDimension size, float maxWidth) {
    Container container = (Container) component;
    Component child     = null;

    if (container.getComponentCount() > 0) {
      child = (Component) container.getComponentAt(0);
      child.getPreferredSize(size, maxWidth);
    }

    iPlatformBorder border = container.getBorder();
    UIInsets        insets = (border == null)
                             ? null
                             : border.getBorderInsets(null);

    if (insets != null) {
      size.width  += insets.left + insets.right;
      size.height += insets.top + insets.bottom;
    }
  }

  public ScrollBarView getVerticalScrollBar() {
    if (vsb == null) {
      vsb = new ScrollBarView(this, false);
    }

    return vsb;
  }

  public boolean hasHorizontalScrollBar() {
    return hasHorizontalScrollbar;
  }

  public boolean hasVerticalScrollBar() {
    return hasVerticalScrollbar;
  }

  public boolean isScrollView() {
    return true;
  }

  public native boolean isScrolling()
  /*-[
    return ((UIScrollView*)proxy_).dragging;
  ]-*/
  ;

  static native Object createProxy()
  /*-[
    return [[RAREAPScrollView alloc]init];
  ]-*/
  ;

  protected native void createOverlayView(boolean wi)
  /*-[
    [((RAREAPScrollView*)proxy_) createOverlayView: wi];
  ]-*/
  ;

  @Override
  protected void disposeEx() {
    super.disposeEx();

    if (hsb != null) {
      hsb.dispose();
    }

    if (vsb != null) {
      vsb.dispose();
    }

    hsb                  = null;
    vsb                  = null;
    scrollingEdgePainter = null;
  }

  protected native void removeOverlayView()
  /*-[
    [((RAREAPScrollView*)proxy_) removeOverlayView];
  ]-*/
  ;

  public native UIPoint getContentOffset()
  /*-[
    CGPoint p=((RAREAPScrollView*)proxy_).contentOffset;
    return [[RAREUIPoint alloc] initWithFloat:p.x withFloat:p.y];
  ]-*/
  ;

  private native void setContentSize(float w, float h)
  /*-[
    ((UIScrollView*)proxy_).contentSize=CGSizeMake(w,h);
  ]-*/
  ;

  public native void setContentOffset(float x, float y)
  /*-[
    CGPoint p=CGPointMake(x,y);
    ((RAREAPScrollView*)proxy_).contentOffset=p;
  ]-*/
  ;

  @Override
  public void setScrollingEdgePainter(UIScrollingEdgePainter painter) {
    scrollingEdgePainter = painter;
    setPaintHandlerEnabled(true);
    setSystemOverlayPainterEx(painter);
  }

  @Override
  public UIScrollingEdgePainter getScrollingEdgePainter() {
    return scrollingEdgePainter;
  }

  @Override
  public native void moveUpDown(boolean up, boolean block)
  /*-[
    RAREAPScrollView* sv=(RAREAPScrollView*)proxy_;
    int increment=0;
    if(vsb_) {
      increment=block ? [vsb_ getBlockIncrement] : [vsb_ getUnitIncrement];
    }
    if(increment>0) {
      [sv moveUpDown:up increment:increment];
    }
    else {
      [sv moveUpDown:up block:block];
    }
  ]-*/
  ;

  @Override
  public native void moveLeftRight(boolean left, boolean block)
  /*-[
    RAREAPScrollView* sv=(RAREAPScrollView*)proxy_;
    int increment=0;
    if(hsb_) {
      increment=block ? [hsb_ getBlockIncrement] : [hsb_ getUnitIncrement];
    }
    if(increment>0) {
      [sv moveLeftRight:left increment:increment];
    }
    else {
      [sv moveLeftRight:left block:block];
    }
  ]-*/
  ;
}
