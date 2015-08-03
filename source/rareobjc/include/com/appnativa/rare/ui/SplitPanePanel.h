//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple-splitpane/com/appnativa/rare/ui/SplitPanePanel.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RARESplitPanePanel_H_
#define _RARESplitPanePanel_H_

@class RAREAppleGraphics;
@class RAREMouseEvent;
@class RARESplitPanePanel_OverlayDividerPane;
@class RARESplitPanePanel_SplitPaneView;
@class RAREUIDimension;
@class RAREUIRectangle;
@class RAREaSplitPanePanel_Divider;
@protocol RAREiChangeListener;
@protocol RAREiPlatformComponent;
@protocol RAREiWidget;

#import "JreEmulation.h"
#include "com/appnativa/rare/platform/apple/ui/iAppleLayoutManager.h"
#include "com/appnativa/rare/platform/apple/ui/view/ParentView.h"
#include "com/appnativa/rare/platform/apple/ui/view/View.h"
#include "com/appnativa/rare/ui/aSplitPanePanel.h"
#include "com/appnativa/rare/ui/listener/iMouseListener.h"
#include "com/appnativa/rare/ui/listener/iMouseMotionListener.h"

@interface RARESplitPanePanel : RAREaSplitPanePanel {
 @public
  RARESplitPanePanel_SplitPaneView *splitPaneView_;
}

- (id)init;
- (id)initWithRAREiWidget:(id<RAREiWidget>)context;
- (void)addChangeListenerWithRAREiChangeListener:(id<RAREiChangeListener>)l;
- (RAREaSplitPanePanel_Divider *)createDivider;
- (void)dispose;
- (void)removeChangeListenerWithRAREiChangeListener:(id<RAREiChangeListener>)l;
- (void)copyAllFieldsTo:(RARESplitPanePanel *)other;
@end

J2OBJC_FIELD_SETTER(RARESplitPanePanel, splitPaneView_, RARESplitPanePanel_SplitPaneView *)

typedef RARESplitPanePanel ComAppnativaRareUiSplitPanePanel;

@interface RARESplitPanePanel_SplitPaneView : RAREParentView < RAREiAppleLayoutManager > {
 @public
  RARESplitPanePanel *this$0_;
  RAREUIDimension *size_;
  RARESplitPanePanel_OverlayDividerPane *overlayDragPane_;
}

- (id)initWithRARESplitPanePanel:(RARESplitPanePanel *)outer$;
- (void)layoutWithRAREParentView:(RAREParentView *)view
                       withFloat:(float)width
                       withFloat:(float)height;
- (void)disposeEx;
- (void)dividerDragWithRAREMouseEvent:(RAREMouseEvent *)me;
- (void)dividerDragFinishedWithRAREMouseEvent:(RAREMouseEvent *)me;
- (void)dividerDragStartedWithRAREaSplitPanePanel_Divider:(RAREaSplitPanePanel_Divider *)v
                                       withRAREMouseEvent:(RAREMouseEvent *)me;
- (BOOL)resizeViaDividerWithRAREaSplitPanePanel_Divider:(RAREaSplitPanePanel_Divider *)d
                                                withInt:(int)delta;
- (id<RAREiPlatformComponent>)getLayoutComponentAtWithInt:(int)index;
- (int)getLayoutComponentCount;
- (void)copyAllFieldsTo:(RARESplitPanePanel_SplitPaneView *)other;
@end

J2OBJC_FIELD_SETTER(RARESplitPanePanel_SplitPaneView, this$0_, RARESplitPanePanel *)
J2OBJC_FIELD_SETTER(RARESplitPanePanel_SplitPaneView, size_, RAREUIDimension *)
J2OBJC_FIELD_SETTER(RARESplitPanePanel_SplitPaneView, overlayDragPane_, RARESplitPanePanel_OverlayDividerPane *)

@interface RARESplitPanePanel_DividerPane : RAREView < RAREiMouseMotionListener, RAREiMouseListener > {
 @public
  RARESplitPanePanel *this$0_;
}

- (id)initWithRARESplitPanePanel:(RARESplitPanePanel *)outer$;
- (void)mouseDraggedWithRAREMouseEvent:(RAREMouseEvent *)e;
- (void)mouseEnteredWithRAREMouseEvent:(RAREMouseEvent *)e;
- (void)mouseExitedWithRAREMouseEvent:(RAREMouseEvent *)e;
- (void)mouseMovedWithRAREMouseEvent:(RAREMouseEvent *)e;
- (void)mousePressedWithRAREMouseEvent:(RAREMouseEvent *)e;
- (void)mouseReleasedWithRAREMouseEvent:(RAREMouseEvent *)e;
- (void)paintBackgroundWithRAREAppleGraphics:(RAREAppleGraphics *)g
                                withRAREView:(RAREView *)v
                         withRAREUIRectangle:(RAREUIRectangle *)rect;
- (void)pressCanceledWithRAREMouseEvent:(RAREMouseEvent *)e;
- (BOOL)wantsLongPress;
- (BOOL)wantsMouseMovedEvents;
- (void)setTypeWithBoolean:(BOOL)leftToRight;
- (void)getPreferredSizeWithRAREUIDimension:(RAREUIDimension *)size;
@end

J2OBJC_FIELD_SETTER(RARESplitPanePanel_DividerPane, this$0_, RARESplitPanePanel *)

@interface RARESplitPanePanel_OverlayDividerPane : RAREView {
 @public
  RARESplitPanePanel *this$0_;
}

- (id)initWithRARESplitPanePanel:(RARESplitPanePanel *)outer$;
- (void)paintBackgroundWithRAREAppleGraphics:(RAREAppleGraphics *)g
                                withRAREView:(RAREView *)v
                         withRAREUIRectangle:(RAREUIRectangle *)rect;
@end

J2OBJC_FIELD_SETTER(RARESplitPanePanel_OverlayDividerPane, this$0_, RARESplitPanePanel *)

#endif // _RARESplitPanePanel_H_
