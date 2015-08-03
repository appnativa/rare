//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/ScrollPanePanel.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RAREScrollPanePanel_H_
#define _RAREScrollPanePanel_H_

@class RAREUIPoint;
@class RAREUIScrollingEdgePainter;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/XPContainer.h"
#include "com/appnativa/rare/ui/iScrollerSupport.h"

@interface RAREScrollPanePanel : RAREXPContainer < RAREiScrollerSupport > {
 @public
  id<RAREiScrollerSupport> scrollSupport_;
}

- (id)initWithId:(id)view;
- (void)moveUpDownWithBoolean:(BOOL)up
                  withBoolean:(BOOL)block;
- (void)moveLeftRightWithBoolean:(BOOL)left
                     withBoolean:(BOOL)block;
- (void)scrollToBottomEdge;
- (void)scrollToLeftEdge;
- (void)scrollToRightEdge;
- (void)scrollToTopEdge;
- (id<RAREiScrollerSupport>)getScrollSupport;
- (void)setScrollSupportWithRAREiScrollerSupport:(id<RAREiScrollerSupport>)scrollSupport;
- (BOOL)isScrolling;
- (BOOL)isAtLeftEdge;
- (BOOL)isAtRightEdge;
- (void)setScrollingEdgePainterWithRAREUIScrollingEdgePainter:(RAREUIScrollingEdgePainter *)painter;
- (RAREUIScrollingEdgePainter *)getScrollingEdgePainter;
- (BOOL)isAtTopEdge;
- (BOOL)isAtBottomEdge;
- (RAREUIPoint *)getContentOffset;
- (void)copyAllFieldsTo:(RAREScrollPanePanel *)other;
@end

J2OBJC_FIELD_SETTER(RAREScrollPanePanel, scrollSupport_, id<RAREiScrollerSupport>)

typedef RAREScrollPanePanel ComAppnativaRareUiScrollPanePanel;

#endif // _RAREScrollPanePanel_H_