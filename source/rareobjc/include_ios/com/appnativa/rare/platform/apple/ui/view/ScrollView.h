//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/ios/com/appnativa/rare/platform/apple/ui/view/ScrollView.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREScrollView_H_
#define _RAREScrollView_H_

@class RAREComponent;
@class RAREScrollBarView;
@class RAREUIDimension;
@class RAREUIPoint;
@class RAREUIScrollingEdgePainter;
@class RAREUTIdentityArrayList;
@class RAREView;
@class RAREaPlatformTableBasedView;

#import "JreEmulation.h"
#include "com/appnativa/rare/platform/apple/ui/iAppleLayoutManager.h"
#include "com/appnativa/rare/platform/apple/ui/iApplePainterSupport.h"
#include "com/appnativa/rare/platform/apple/ui/view/ParentView.h"
#include "com/appnativa/rare/ui/iScrollerSupport.h"

@interface RAREScrollView : RAREParentView < RAREiApplePainterSupport, RAREiAppleLayoutManager, RAREiScrollerSupport > {
 @public
  RAREScrollBarView *hsb_;
  RAREScrollBarView *vsb_;
  BOOL hasVerticalScrollbar_;
  BOOL hasHorizontalScrollbar_;
  RAREUIDimension *measureSize_;
  RAREUIScrollingEdgePainter *scrollingEdgePainter_;
  int offsetX_;
  int offsetY_;
  RAREUTIdentityArrayList *hScrollSynchronizer_;
  RAREUTIdentityArrayList *vScrollSynchronizer_;
  BOOL inOnScrollChanged_;
  BOOL tieVisibilityToContent_;
  __weak RAREView *contentView_;
}

- (id)init;
- (id)initWithBoolean:(BOOL)tieVisibilityToContent;
- (id)initWithId:(id)uiview;
- (void)addWithInt:(int)position
      withRAREView:(RAREView *)view;
- (void)setHeaderViewWithRAREiScrollerSupport:(id<RAREiScrollerSupport>)ss;
- (void)setFooterViewWithRAREiScrollerSupport:(id<RAREiScrollerSupport>)ss;
- (void)setRowHeaderViewWithRAREiScrollerSupport:(id<RAREiScrollerSupport>)ss;
- (void)setRowFooterViewWithRAREiScrollerSupport:(id<RAREiScrollerSupport>)rowFooterView;
- (void)layoutWithRAREParentView:(RAREParentView *)view
                       withFloat:(float)width
                       withFloat:(float)height;
- (void)syncScrollingWithRAREiScrollerSupport:(id<RAREiScrollerSupport>)ss
                                  withBoolean:(BOOL)vertical;
- (BOOL)isVisible;
- (BOOL)isAtBottomEdge;
- (void)scrollToLeftEdge;
- (void)scrollToTopEdge;
- (void)scrollToRightEdge;
- (void)scrollToBottomEdge;
- (BOOL)isAtLeftEdge;
- (BOOL)isAtRightEdge;
- (BOOL)isAtTopEdge;
- (void)removeChildren;
- (void)setScrollBarListenersWithRAREaPlatformTableBasedView:(RAREaPlatformTableBasedView *)view;
- (void)unwrap;
+ (RAREScrollView *)wrapWithId:(id)uiview;
- (void)setAutoHideScrollbarsWithBoolean:(BOOL)autoHide;
- (void)setComponentWithRAREComponent:(RAREComponent *)component;
- (void)setContentViewWithRAREView:(RAREView *)view;
- (void)setShowsHorizontalScrollIndicatorWithBoolean:(BOOL)show;
- (void)setShowsVerticalScrollIndicatorWithBoolean:(BOOL)show;
- (void)setScrollEnabledWithBoolean:(BOOL)enabled;
- (RAREScrollBarView *)getHorizontalScrollBar;
- (void)getMinimumSizeWithRAREUIDimension:(RAREUIDimension *)size
                                withFloat:(float)maxWidth;
- (RAREUIDimension *)getPreferredSizeWithRAREParentView:(RAREParentView *)view;
- (void)getPreferredSizeWithRAREUIDimension:(RAREUIDimension *)size
                                  withFloat:(float)maxWidth;
- (RAREScrollBarView *)getVerticalScrollBar;
- (BOOL)hasHorizontalScrollBar;
- (BOOL)hasVerticalScrollBar;
- (BOOL)isScrollView;
- (BOOL)isScrolling;
+ (id)createProxy;
- (void)createOverlayViewWithBoolean:(BOOL)wi;
- (void)disposeEx;
- (void)removeOverlayView;
- (RAREUIPoint *)getContentOffset;
- (void)setContentSizeWithFloat:(float)w
                      withFloat:(float)h;
- (void)setContentOffsetWithFloat:(float)x
                        withFloat:(float)y;
- (void)setScrollingEdgePainterWithRAREUIScrollingEdgePainter:(RAREUIScrollingEdgePainter *)painter;
- (RAREUIScrollingEdgePainter *)getScrollingEdgePainter;
- (void)moveUpDownWithBoolean:(BOOL)up
                  withBoolean:(BOOL)block;
- (void)moveLeftRightWithBoolean:(BOOL)left
                     withBoolean:(BOOL)block;
- (void)viewDidScrollWithFloat:(float)x
                     withFloat:(float)y;
- (void)turnOffScrollBarVisibilityWithRAREiScrollerSupport:(id<RAREiScrollerSupport>)ss
                                               withBoolean:(BOOL)horizontal;
- (BOOL)isTieVisibilityToContent;
- (void)setTieVisibilityToContentWithBoolean:(BOOL)tieVisibilityToContent;
- (void)copyAllFieldsTo:(RAREScrollView *)other;
@end

J2OBJC_FIELD_SETTER(RAREScrollView, hsb_, RAREScrollBarView *)
J2OBJC_FIELD_SETTER(RAREScrollView, vsb_, RAREScrollBarView *)
J2OBJC_FIELD_SETTER(RAREScrollView, measureSize_, RAREUIDimension *)
J2OBJC_FIELD_SETTER(RAREScrollView, scrollingEdgePainter_, RAREUIScrollingEdgePainter *)
J2OBJC_FIELD_SETTER(RAREScrollView, hScrollSynchronizer_, RAREUTIdentityArrayList *)
J2OBJC_FIELD_SETTER(RAREScrollView, vScrollSynchronizer_, RAREUTIdentityArrayList *)

typedef RAREScrollView ComAppnativaRarePlatformAppleUiViewScrollView;

#endif // _RAREScrollView_H_
