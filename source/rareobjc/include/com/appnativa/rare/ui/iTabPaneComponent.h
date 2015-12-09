//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/iTabPaneComponent.java
//
//  Created by decoteaud on 12/8/15.
//

#ifndef _RAREiTabPaneComponent_H_
#define _RAREiTabPaneComponent_H_

@class RARELocationEnum;
@class RAREPaintBucket;
@class RAREUIFont;
@class RAREUIInsets;
@class RAREUIRectangle;
@class RAREiTabPaneViewer_ShapeEnum;
@protocol RAREiPlatformComponent;
@protocol RAREiPlatformIcon;
@protocol RAREiTabDocument;
@protocol RAREiTabDocument_iDocumentListener;
@protocol RAREiTabPaneListener;
@protocol RAREiTransitionAnimator;

#import "JreEmulation.h"

@protocol RAREiTabPaneComponent < NSObject, JavaObject >
- (int)addTabWithRAREiTabDocument:(id<RAREiTabDocument>)doc;
- (void)addTabPaneListenerWithRAREiTabPaneListener:(id<RAREiTabPaneListener>)l;
- (void)beginTabsUpdate;
- (void)checkOrientationWithId:(id)newConfig;
- (void)closeAll;
- (void)closeAllButWithInt:(int)pos;
- (void)closeTabWithInt:(int)pos;
- (void)endTabsUpdate;
- (int)indexOfDocumentWithRAREiTabDocument:(id<RAREiTabDocument>)doc;
- (int)indexOfTabWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon;
- (int)indexOfTabWithNSString:(NSString *)title;
- (void)removeTabPaneListenerWithRAREiTabPaneListener:(id<RAREiTabPaneListener>)l;
- (void)setBoldSelectedTabWithBoolean:(BOOL)bold;
- (void)setEnabledAtWithInt:(int)pos
                withBoolean:(BOOL)enable;
- (void)setFontWithRAREUIFont:(RAREUIFont *)f;
- (void)setIconAtWithInt:(int)pos
   withRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon;
- (void)setSelectedIndexWithInt:(int)index;
- (void)setSelectedPainterWithRAREPaintBucket:(RAREPaintBucket *)pb;
- (void)setShowCloseButtonWithBoolean:(BOOL)b;
- (void)setShowCloseButtonOnTabWithBoolean:(BOOL)b;
- (void)setShowIconsOnTabWithBoolean:(BOOL)show;
- (void)setTabAreaPainterWithRAREPaintBucket:(RAREPaintBucket *)pb;
- (void)setTabAreaMarginWithRAREUIInsets:(RAREUIInsets *)margin;
- (void)setTabLeadingComponentWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c;
- (void)setTabMinHeightWithInt:(int)height;
- (void)setTabPainterWithRAREPaintBucket:(RAREPaintBucket *)pb;
- (void)setTabPlacementWithRARELocationEnum:(RARELocationEnum *)location;
- (void)setTabShapeWithRAREiTabPaneViewer_ShapeEnum:(RAREiTabPaneViewer_ShapeEnum *)shape;
- (void)setTabTrailingComponentWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c;
- (void)setTitleAtWithInt:(int)pos
             withNSString:(NSString *)title;
- (void)setTransitionAnimatorWithRAREiTransitionAnimator:(id<RAREiTransitionAnimator>)animator;
- (id<RAREiTransitionAnimator>)getTransitionAnimator;
- (int)getChangeIndex;
- (id<RAREiPlatformComponent>)getComponent;
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
- (void)setMoreTabsIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon;
- (BOOL)getTabStripsFloats;
- (void)setTabStripsFloatsWithBoolean:(BOOL)floats;
- (void)setReloadTimeoutWithLong:(long long int)reloadTimeout;
- (long long int)getReloadTimeout;
@end

#define ComAppnativaRareUiITabPaneComponent RAREiTabPaneComponent

#endif // _RAREiTabPaneComponent_H_
