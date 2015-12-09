//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core-tabpane/com/appnativa/rare/viewer/aTabPaneViewer.java
//
//  Created by decoteaud on 12/8/15.
//

#ifndef _RAREaTabPaneViewer_H_
#define _RAREaTabPaneViewer_H_

@class JavaNetURL;
@class RARELocationEnum;
@class RAREPaintBucket;
@class RARESPOTTab;
@class RARESPOTTabPane;
@class RARESPOTViewer;
@class RAREUIDimension;
@class RAREUIFont;
@class RAREUIInsets;
@class RAREUIRectangle;
@class RAREUTMutableInteger;
@class RAREaTabPaneViewer_TabOptions;
@class RAREiTabPaneViewer_ShapeEnum;
@class SPOTSet;
@protocol JavaUtilList;
@protocol RAREiContainer;
@protocol RAREiFunctionCallback;
@protocol RAREiPlatformComponent;
@protocol RAREiPlatformIcon;
@protocol RAREiTabDocument;
@protocol RAREiTabPaneComponent;
@protocol RAREiTabPaneListener;
@protocol RAREiTarget;
@protocol RAREiTransitionAnimator;
@protocol RAREiViewer;

#import "JreEmulation.h"
#include "com/appnativa/rare/viewer/aContainer.h"
#include "com/appnativa/rare/viewer/iTabPaneViewer.h"

@interface RAREaTabPaneViewer : RAREaContainer < RAREiTabPaneViewer > {
 @public
  int initiallySelectedIndex_;
  id<RAREiTabDocument> selectedDocument_;
  RAREaTabPaneViewer_TabOptions *tabOptions_;
  id<RAREiTabPaneComponent> tabPane_;
  RAREUTMutableInteger *unique_;
  int minimumHeight_;
  int minimumWidth_;
  BOOL tabChangeCancelable_;
  BOOL useMinimumVarsOnlyWhenEmpty_;
}

- (id)init;
- (id)initWithRAREiContainer:(id<RAREiContainer>)parent;
- (int)addTabWithNSString:(NSString *)name
             withNSString:(NSString *)title
    withRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon;
- (int)addTabWithNSString:(NSString *)name
             withNSString:(NSString *)title
    withRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon
withRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp;
- (int)addTabWithNSString:(NSString *)name
             withNSString:(NSString *)title
    withRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon
          withRAREiViewer:(id<RAREiViewer>)v;
- (int)addTabWithNSString:(NSString *)name
             withNSString:(NSString *)title
    withRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon
           withJavaNetURL:(JavaNetURL *)url
              withBoolean:(BOOL)load_;
- (void)addTabPaneListenerWithRAREiTabPaneListener:(id<RAREiTabPaneListener>)l;
- (void)clearContents;
- (void)closeAll;
- (void)closeAllButWithInt:(int)pos;
- (void)closeAllButWithNSString:(NSString *)name;
- (void)closeTabWithInt:(int)pos;
- (void)closeTabWithNSString:(NSString *)name;
- (void)configureWithRARESPOTViewer:(RARESPOTViewer *)vcfg;
+ (void)configureTabOptionsWithRARESPOTTabPane:(RARESPOTTabPane *)tc
             withRAREaTabPaneViewer_TabOptions:(RAREaTabPaneViewer_TabOptions *)tabOptions
                               withRAREiViewer:(id<RAREiViewer>)context
                    withRAREiPlatformComponent:(id<RAREiPlatformComponent>)container;
- (void)setReloadTimeoutWithLong:(long long int)reloadTimeout;
- (long long int)getReloadTimeout;
+ (void)configureTabVisualsWithRAREiViewer:(id<RAREiViewer>)context
                      withRAREiTabDocument:(id<RAREiTabDocument>)doc
                           withRARESPOTTab:(RARESPOTTab *)tab
         withRAREaTabPaneViewer_TabOptions:(RAREaTabPaneViewer_TabOptions *)tabOptions;
- (void)dispose;
- (int)indexForIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon;
- (int)indexForNameWithNSString:(NSString *)name;
- (int)indexOfWithId:(id)o;
- (int)indexForTabViewerWithRAREiViewer:(id<RAREiViewer>)v;
- (int)indexForTitleWithNSString:(NSString *)title;
- (int)indexOfWithRAREiTabDocument:(id<RAREiTabDocument>)doc;
- (void)onConfigurationChangedWithBoolean:(BOOL)reset;
- (void)targetAcquiredWithRAREiTarget:(id<RAREiTarget>)target;
- (void)register__;
- (void)reloadWithBoolean:(BOOL)context;
- (void)removeTabPaneListenerWithRAREiTabPaneListener:(id<RAREiTabPaneListener>)l;
- (void)unregisterWithBoolean:(BOOL)disposing;
- (void)setCloseButtonWithInt:(int)location;
- (void)setSelectedTabWithInt:(int)index;
- (void)setSelectedTabNameWithNSString:(NSString *)name;
- (void)setTabChangeCancelableWithBoolean:(BOOL)tabChangeCancelable;
- (void)setTabEnabledWithInt:(int)pos
                 withBoolean:(BOOL)enable;
- (void)setTabIconWithInt:(int)pos
    withRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon;
- (void)setTabNameWithInt:(int)pos
             withNSString:(NSString *)name;
- (void)setTabPlacementWithRARELocationEnum:(RARELocationEnum *)location;
- (void)setTabStyleWithInt:(int)style;
- (void)setTabTitleWithInt:(int)pos
              withNSString:(NSString *)title;
- (void)setTabToolTipWithInt:(int)pos
                withNSString:(NSString *)tooltip;
- (void)setTabViewerWithInt:(int)pos
            withRAREiViewer:(id<RAREiViewer>)v;
- (void)setTransitionAnimatorWithRAREiTransitionAnimator:(id<RAREiTransitionAnimator>)ca;
- (id<RAREiTransitionAnimator>)getTransitionAnimator;
- (id<RAREiPlatformIcon>)getIcon;
- (int)getItemCount;
- (int)getSelectedTab;
- (id<RAREiTabDocument>)getSelectedTabDocument;
- (NSString *)getSelectedTabName;
- (id<RAREiViewer>)getSelectedTabViewer;
- (id<RAREiViewer>)getSelectedTabViewerWithRAREiFunctionCallback:(id<RAREiFunctionCallback>)cb;
- (id)getSelection;
- (NSString *)getSelectionAsString;
- (id)getSelectionData;
- (int)getTabCount;
- (id<RAREiTabDocument>)getTabDocumentWithInt:(int)pos;
- (id<RAREiPlatformIcon>)getTabIconWithInt:(int)pos;
- (RARELocationEnum *)getTabPlacement;
- (RAREUIRectangle *)getTabStripBounds;
- (NSString *)getTabTitleWithInt:(int)pos;
- (id<RAREiViewer>)getTabViewerWithInt:(int)pos;
- (id<RAREiViewer>)getTabViewerWithInt:(int)index
             withRAREiFunctionCallback:(id<RAREiFunctionCallback>)cb;
- (id<RAREiTabPaneComponent>)getTabPaneComponent;
- (id)getValue;
- (int)getWidgetCount;
- (id<JavaUtilList>)getWidgetListEx;
- (BOOL)hasPopupMenu;
- (BOOL)hasSelection;
- (BOOL)isTabChangeCancelable;
- (RAREUIDimension *)adjustPreferredSizeWithRAREUIDimension:(RAREUIDimension *)d;
- (void)clearConfigurationWithBoolean:(BOOL)dispose;
- (void)configureExWithRARESPOTTabPane:(RARESPOTTabPane *)cfg;
- (void)configureTabsWithRARESPOTViewer:(RARESPOTViewer *)cfg
                            withSPOTSet:(SPOTSet *)tabs;
- (id<RAREiTabDocument>)createDocumentWithRAREiTabPaneViewer:(id<RAREiTabPaneViewer>)tpv
                                             withRARESPOTTab:(RARESPOTTab *)tab
                                    withRAREUTMutableInteger:(RAREUTMutableInteger *)count;
- (id<RAREiTabDocument>)createNewDocumentWithNSString:(NSString *)name
                                         withNSString:(NSString *)title
                                withRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon;
- (id<RAREiTabPaneComponent>)createTabPaneComponentWithRARESPOTTabPane:(RARESPOTTabPane *)cfg;
- (void)handleInitialSelectionWithInt:(int)index;
+ (RAREPaintBucket *)newColorHolderWithRAREaTabPaneViewer_TabOptions:(RAREaTabPaneViewer_TabOptions *)tabOptions OBJC_METHOD_FAMILY_NONE;
- (int)getDefaultTabStyle;
+ (RAREiTabPaneViewer_ShapeEnum *)getTabShapeFromStyleWithInt:(int)style;
- (void)copyAllFieldsTo:(RAREaTabPaneViewer *)other;
@end

J2OBJC_FIELD_SETTER(RAREaTabPaneViewer, selectedDocument_, id<RAREiTabDocument>)
J2OBJC_FIELD_SETTER(RAREaTabPaneViewer, tabOptions_, RAREaTabPaneViewer_TabOptions *)
J2OBJC_FIELD_SETTER(RAREaTabPaneViewer, tabPane_, id<RAREiTabPaneComponent>)
J2OBJC_FIELD_SETTER(RAREaTabPaneViewer, unique_, RAREUTMutableInteger *)

typedef RAREaTabPaneViewer ComAppnativaRareViewerATabPaneViewer;

@interface RAREaTabPaneViewer_TabOptions : NSObject {
 @public
  int tabPosition_;
  BOOL boldSelectedTab_;
  BOOL canCloseTabs_;
  BOOL editingAllowed_;
  RAREPaintBucket *selectedTabColorHolder_;
  BOOL showIconsOnTab_;
  RAREUIInsets *tabAreaMargin_;
  RAREPaintBucket *tabAreaPainter_;
  int tabClosePosition_;
  RAREPaintBucket *tabColorHolder_;
  RAREUIFont *tabFont_;
  int tabStyle_;
}

- (void)clear;
- (void)customizePaneWithRAREiTabPaneViewer:(id<RAREiTabPaneViewer>)tv
                  withRAREiTabPaneComponent:(id<RAREiTabPaneComponent>)tabPane;
+ (void)setCloseButtonWithRAREiTabPaneComponent:(id<RAREiTabPaneComponent>)tabPane
                                        withInt:(int)location;
+ (void)setTabPositionWithRAREiTabPaneViewer:(id<RAREiTabPaneViewer>)v
                   withRAREiTabPaneComponent:(id<RAREiTabPaneComponent>)tabPane
                                     withInt:(int)position;
+ (void)setTabStyleWithRAREiTabPaneComponent:(id<RAREiTabPaneComponent>)tabPane
                                     withInt:(int)style;
- (id)init;
- (void)copyAllFieldsTo:(RAREaTabPaneViewer_TabOptions *)other;
@end

J2OBJC_FIELD_SETTER(RAREaTabPaneViewer_TabOptions, selectedTabColorHolder_, RAREPaintBucket *)
J2OBJC_FIELD_SETTER(RAREaTabPaneViewer_TabOptions, tabAreaMargin_, RAREUIInsets *)
J2OBJC_FIELD_SETTER(RAREaTabPaneViewer_TabOptions, tabAreaPainter_, RAREPaintBucket *)
J2OBJC_FIELD_SETTER(RAREaTabPaneViewer_TabOptions, tabColorHolder_, RAREPaintBucket *)
J2OBJC_FIELD_SETTER(RAREaTabPaneViewer_TabOptions, tabFont_, RAREUIFont *)

#endif // _RAREaTabPaneViewer_H_
