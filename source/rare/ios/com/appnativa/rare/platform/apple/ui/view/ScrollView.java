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
import com.appnativa.rare.ui.UIScreen;
import com.appnativa.rare.ui.iPlatformBorder;
import com.appnativa.rare.ui.iScrollerSupport;
import com.appnativa.rare.ui.painter.UIScrollingEdgePainter;
import com.appnativa.util.IdentityArrayList;
import com.google.j2objc.annotations.Weak;

/*-[
 #import "RAREAPScrollView.h"
 ]-*/
public class ScrollView extends ParentView implements iApplePainterSupport, iAppleLayoutManager, iScrollerSupport {
  protected ScrollBarView                       hsb;
  protected ScrollBarView                       vsb;
  protected boolean                             hasVerticalScrollbar   = true;
  protected boolean                             hasHorizontalScrollbar = true;
  protected UIDimension                         measureSize            = new UIDimension();
  protected UIScrollingEdgePainter              scrollingEdgePainter;
  protected int                                 offsetX;
  protected int                                 offsetY;
  protected IdentityArrayList<iScrollerSupport> hScrollSynchronizer;
  protected IdentityArrayList<iScrollerSupport> vScrollSynchronizer;
  protected boolean                               inOnScrollChanged;
  protected boolean tieVisibilityToContent;
  @Weak
  protected View contentView;
  
  public ScrollView() {
    super(createProxy());
  }

  public ScrollView(boolean tieVisibilityToContent) {
    super(createProxy());
    this.tieVisibilityToContent=tieVisibilityToContent;
  }
  protected ScrollView(Object uiview) {
    super(uiview);
  }

  @Override
  public void add(int position, View view) {
    setContentView(view);
  }

  public void setHeaderView(iScrollerSupport ss) {
    turnOffScrollBarVisibility(ss, true);
    syncScrolling(ss, false);
  }

  public void setFooterView(iScrollerSupport ss) {
    setShowsHorizontalScrollIndicator(false);
    syncScrolling(ss, false);
  }

  public void setRowHeaderView(iScrollerSupport ss) {
    turnOffScrollBarVisibility(ss, false);
    syncScrolling(ss, true);
  }

  public void setRowFooterView(iScrollerSupport rowFooterView) {
    setShowsVerticalScrollIndicator(false);
    syncScrolling(rowFooterView, true);
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

  public void syncScrolling(iScrollerSupport ss, boolean vertical) {
    if (vertical) {
      if (vScrollSynchronizer == null) {
        vScrollSynchronizer = new IdentityArrayList<iScrollerSupport>(2);
      }

      vScrollSynchronizer.addIfNotPresent(ss);
    } else {
      if (hScrollSynchronizer == null) {
        hScrollSynchronizer = new IdentityArrayList<iScrollerSupport>(2);
      }

      hScrollSynchronizer.addIfNotPresent(ss);
    }
  }
  public boolean isVisible() {
    if(tieVisibilityToContent) {
      return contentView==null ? false : contentView.isVisible();
    }
    else {
      return super.isVisible();
    }
  }

  @Override
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

  @Override
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

  @Override
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

  @Override
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

  @Override
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

  @Override
  public native boolean isAtLeftEdge()
  /*-[
    if(!hasHorizontalScrollbar_) {
      return YES;
    }
    return ((UIScrollView*)proxy_).contentOffset.x==0;
  ]-*/
  ;

  @Override
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

  @Override
  public native boolean isAtTopEdge()
  /*-[
    if(!hasVerticalScrollbar_) {
      return YES;
    }
    return ((UIScrollView*)proxy_).contentOffset.y==0;
   ]-*/
  ;

  @Override
  public void removeChildren() {}

  public void setScrollBarListeners(aPlatformTableBasedView view) {
    ScrollBarView h = null;
    ScrollBarView v = null;

    if ((hsb != null) && hsb.hasListener()) {
      h   = hsb;
      hsb = null;
    }

    if ((vsb != null) && vsb.hasListener()) {
      v   = vsb;
      vsb = null;
    }

    view.setScrollBarViews(h, v);
  }

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
    contentView_=view;
    [((UIScrollView*)proxy_) addSubview: (UIView*)view->proxy_];
  ]-*/
  ;

  public native void setShowsHorizontalScrollIndicator(boolean show)
  /*-[
   hasVerticalScrollbar_=show;
    UIScrollView* sv=(UIScrollView*)proxy_;
    [sv setShowsHorizontalScrollIndicator: show];
  ]-*/
  ;

  public native void setShowsVerticalScrollIndicator(boolean show)
  /*-[
   hasVerticalScrollbar_=show;
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
  public void getMinimumSize(UIDimension size, float maxWidth) {
    size.width = size.height = UIScreen.PLATFORM_PIXELS_16 * 2;

    iPlatformBorder border = component.getBorder();
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

  @Override
  public boolean isScrollView() {
    return true;
  }

  @Override
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

    if (hScrollSynchronizer != null) {
      hScrollSynchronizer.clear();
    }

    if (vScrollSynchronizer != null) {
      vScrollSynchronizer.clear();
    }

    if (hsb != null) {
      hsb.dispose();
    }

    if (vsb != null) {
      vsb.dispose();
    }

    vScrollSynchronizer  = null;
    hScrollSynchronizer  = null;
    hsb                  = null;
    vsb                  = null;
    scrollingEdgePainter = null;
  }

  protected native void removeOverlayView()
  /*-[
    [((RAREAPScrollView*)proxy_) removeOverlayView];
  ]-*/
  ;

  @Override
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
   if(!inOnScrollChanged_) {
     ((RAREAPScrollView*)proxy_).contentOffset=CGPointMake(x,y);
   }
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

  protected void viewDidScroll(float x, float y) {
    inOnScrollChanged = true;

    if (vScrollSynchronizer != null) {
      for (iScrollerSupport ss : vScrollSynchronizer) {
        ss.setContentOffset(0, y);
      }
    }

    if (hScrollSynchronizer != null) {
      for (iScrollerSupport ss : hScrollSynchronizer) {
        ss.setContentOffset(x, 0);
      }
    }

    int xx = (int) x;
    int yy = (int) y;

    if (hsb != null) {
      if (xx != offsetX) {
        hsb.notifyChangeListeners();
      }
    }

    if (vsb != null) {
      if (yy != offsetY) {
        vsb.notifyChangeListeners();
      }
    }

    offsetX           = xx;
    offsetY           = yy;
    inOnScrollChanged = false;
  }

  private void turnOffScrollBarVisibility(iScrollerSupport ss, boolean horizontal) {
    if (ss instanceof ScrollView) {
      if (horizontal) {
        ((ScrollView) ss).setShowsHorizontalScrollIndicator(false);
      } else {
        ((ScrollView) ss).setShowsVerticalScrollIndicator(false);
      }
    } else if (ss instanceof aPlatformTableBasedView) {
      if (horizontal) {
        ((aPlatformTableBasedView) ss).setShowsHorizontalScrollIndicator(false);
      } else {
        ((aPlatformTableBasedView) ss).setShowsVerticalScrollIndicator(false);
      }
    }
  }

  public boolean isTieVisibilityToContent() {
    return tieVisibilityToContent;
  }

  public void setTieVisibilityToContent(boolean tieVisibilityToContent) {
    this.tieVisibilityToContent = tieVisibilityToContent;
  }
}
