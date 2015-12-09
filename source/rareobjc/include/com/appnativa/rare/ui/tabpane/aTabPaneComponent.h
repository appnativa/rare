//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core-tabpane/com/appnativa/rare/ui/tabpane/aTabPaneComponent.java
//
//  Created by decoteaud on 12/8/15.
//

#ifndef _RAREaTabPaneComponent_H_
#define _RAREaTabPaneComponent_H_

@class JavaLangException;
@class RAREActionEvent;
@class RARELocationEnum;
@class RAREPaintBucket;
@class RARERenderTypeEnum;
@class RARERenderableDataItem_HorizontalAlignEnum;
@class RAREUIAction;
@class RAREUIFont;
@class RAREUIInsets;
@class RAREUIRectangle;
@class RAREaTabPaneLayout;
@class RAREiTabPaneViewer_ShapeEnum;
@protocol RAREiPlatformComponent;
@protocol RAREiPlatformIcon;
@protocol RAREiTabDocument;
@protocol RAREiTabPaneListener;
@protocol RAREiTransitionAnimator;
@protocol RAREiViewer;
@protocol RAREiWidget;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/BorderPanel.h"
#include "com/appnativa/rare/ui/event/iActionListener.h"
#include "com/appnativa/rare/ui/iTabDocument.h"
#include "com/appnativa/rare/ui/iTabPaneComponent.h"
#include "com/appnativa/rare/ui/tabpane/aTabPaneLayout.h"

@interface RAREaTabPaneComponent : RAREBorderPanel < RAREaTabPaneLayout_iContentManager, RAREiTabDocument_iDocumentListener, RAREiTabDocument_iCanChangeCallback, RAREiTabPaneComponent > {
 @public
  int changeIndex_;
  int newTabPosition_;
  int selectedIndex_;
  BOOL closingAll_;
  id<RAREiPlatformIcon> moreIcon_;
  RAREPaintBucket *normalTabPainter_;
  RAREPaintBucket *selectedTabPainter_;
  RARERenderTypeEnum *sizingAnchor_;
  id<RAREiActionListener> tabAction_;
  RAREaTabPaneLayout *tabsLayout_;
  id<RAREiTransitionAnimator> transitionAnimator_;
  RARERenderableDataItem_HorizontalAlignEnum *horizontalAlignment_;
  BOOL tabStripsFloats_;
  long long int reloadTimeout_;
}

- (id)init;
- (id)initWithRAREiWidget:(id<RAREiWidget>)context;
- (id)initWithId:(id)view;
- (long long int)getReloadTimeout;
- (void)setReloadTimeoutWithLong:(long long int)reloadTimeout;
- (int)addTabWithRAREiTabDocument:(id<RAREiTabDocument>)doc;
- (void)addTabPaneListenerWithRAREiTabPaneListener:(id<RAREiTabPaneListener>)l;
- (void)beginTabsUpdate;
- (void)canChangeWithRAREiWidget:(id<RAREiWidget>)context
            withRAREiTabDocument:(id<RAREiTabDocument>)doc;
- (void)checkOrientationWithId:(id)newConfig;
- (void)closeAll;
- (void)closeAllButWithInt:(int)pos;
- (void)closeTabWithInt:(int)pos;
- (void)dispose;
- (void)documentChangedWithRAREiTabDocument:(id<RAREiTabDocument>)doc;
- (void)endTabsUpdate;
- (void)errorHappenedWithRAREiWidget:(id<RAREiWidget>)context
                withRAREiTabDocument:(id<RAREiTabDocument>)doc
               withJavaLangException:(JavaLangException *)e;
- (int)indexOfDocumentWithRAREiTabDocument:(id<RAREiTabDocument>)doc;
- (int)indexOfTabWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon;
- (int)indexOfTabWithNSString:(NSString *)title;
- (void)removeTabPaneListenerWithRAREiTabPaneListener:(id<RAREiTabPaneListener>)l;
- (void)viewerRemovedWithRAREiViewer:(id<RAREiViewer>)v;
- (void)viewerSetWithRAREiViewer:(id<RAREiViewer>)v;
- (void)setBoldSelectedTabWithBoolean:(BOOL)bold;
- (void)setBottomButtonCountWithInt:(int)tabCount;
- (void)setEnabledAtWithInt:(int)pos
                withBoolean:(BOOL)enable;
- (void)setFontWithRAREUIFont:(RAREUIFont *)f;
- (void)setIconAtWithInt:(int)pos
   withRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon;
- (void)setMoreTabsIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)moreIcon;
- (void)setSelectedIndexWithInt:(int)index;
- (void)setSelectedPainterWithRAREPaintBucket:(RAREPaintBucket *)pb;
- (void)setShowCloseButtonWithBoolean:(BOOL)b;
- (void)setShowCloseButtonOnTabWithBoolean:(BOOL)b;
- (void)setShowIconsOnTabWithBoolean:(BOOL)show;
- (void)setTabAreaMarginWithRAREUIInsets:(RAREUIInsets *)margin;
- (void)setTabAreaPainterWithRAREPaintBucket:(RAREPaintBucket *)pb;
- (void)setTabLeadingComponentWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c;
- (void)setTabMinHeightWithInt:(int)height;
- (void)setTabMinWidthWithInt:(int)width;
- (void)setTabPainterWithRAREPaintBucket:(RAREPaintBucket *)pb;
- (void)setTabPlacementWithRARELocationEnum:(RARELocationEnum *)location;
- (void)setTabShapeWithRAREiTabPaneViewer_ShapeEnum:(RAREiTabPaneViewer_ShapeEnum *)shape;
- (void)setTabTrailingComponentWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c;
- (void)setTextHorizontalAlignmentWithRARERenderableDataItem_HorizontalAlignEnum:(RARERenderableDataItem_HorizontalAlignEnum *)horizontalAlignment;
- (void)setTitleAtWithInt:(int)pos
             withNSString:(NSString *)title;
- (void)setTransitionAnimatorWithRAREiTransitionAnimator:(id<RAREiTransitionAnimator>)animator;
- (void)setVisibleCountWithInt:(int)intValue;
- (int)getBottomButtonCount;
- (int)getChangeIndex;
- (id<RAREiPlatformComponent>)getComponent;
- (id<RAREiViewer>)getContentWithRAREUIAction:(RAREUIAction *)a;
- (id<RAREiTabDocument>)getDocumentAtWithInt:(int)index;
- (id<RAREiTabDocument_iDocumentListener>)getDocumentListener;
- (id<RAREiPlatformIcon>)getIconAtWithInt:(int)pos;
- (id<RAREiTabDocument>)getSelectedDocument;
- (int)getSelectedIndex;
- (int)getTabCount;
- (int)getTabMinHeight;
- (RARELocationEnum *)getTabPlacement;
- (RAREUIRectangle *)getTabStripBounds;
- (id<RAREiPlatformComponent>)getTabStrip;
- (NSString *)getTitleAtWithInt:(int)pos;
- (id<RAREiTransitionAnimator>)getTransitionAnimator;
- (BOOL)isHorizontal;
- (void)setBoundsWithFloat:(float)x
                 withFloat:(float)y
                 withFloat:(float)width
                 withFloat:(float)height;
- (void)navigateToTabWithId:(id)source
                withBoolean:(BOOL)next;
- (void)tabWillChangeWithInt:(int)index;
- (void)setLayoutWithRAREaTabPaneLayout:(RAREaTabPaneLayout *)layout;
- (void)setSelectedIndexExWithInt:(int)index;
- (BOOL)getTabStripsFloats;
- (void)setTabStripsFloatsWithBoolean:(BOOL)floats;
- (void)copyAllFieldsTo:(RAREaTabPaneComponent *)other;
@end

J2OBJC_FIELD_SETTER(RAREaTabPaneComponent, moreIcon_, id<RAREiPlatformIcon>)
J2OBJC_FIELD_SETTER(RAREaTabPaneComponent, normalTabPainter_, RAREPaintBucket *)
J2OBJC_FIELD_SETTER(RAREaTabPaneComponent, selectedTabPainter_, RAREPaintBucket *)
J2OBJC_FIELD_SETTER(RAREaTabPaneComponent, sizingAnchor_, RARERenderTypeEnum *)
J2OBJC_FIELD_SETTER(RAREaTabPaneComponent, tabAction_, id<RAREiActionListener>)
J2OBJC_FIELD_SETTER(RAREaTabPaneComponent, tabsLayout_, RAREaTabPaneLayout *)
J2OBJC_FIELD_SETTER(RAREaTabPaneComponent, transitionAnimator_, id<RAREiTransitionAnimator>)
J2OBJC_FIELD_SETTER(RAREaTabPaneComponent, horizontalAlignment_, RARERenderableDataItem_HorizontalAlignEnum *)

typedef RAREaTabPaneComponent ComAppnativaRareUiTabpaneATabPaneComponent;

@interface RAREaTabPaneComponent_$1 : NSObject < RAREiActionListener > {
 @public
  RAREaTabPaneComponent *this$0_;
}

- (void)actionPerformedWithRAREActionEvent:(RAREActionEvent *)e;
- (id)initWithRAREaTabPaneComponent:(RAREaTabPaneComponent *)outer$;
@end

J2OBJC_FIELD_SETTER(RAREaTabPaneComponent_$1, this$0_, RAREaTabPaneComponent *)

#endif // _RAREaTabPaneComponent_H_
