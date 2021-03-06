//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/viewer/aViewer.java
//
//  Created by decoteaud on 3/14/16.
//

#ifndef _RAREaViewer_H_
#define _RAREaViewer_H_

@class JavaIoWriter;
@class JavaNetURL;
@class JavaUtilHashMap;
@class RAREActionLink;
@class RAREDropInformation;
@class RAREExpansionEvent;
@class RARERenderTypeEnum;
@class RARESPOTViewer;
@class RARESPOTWidget;
@class RAREUIDimension;
@class RAREUIRectangle;
@class RAREUTIdentityArrayList;
@class RAREWindowViewer;
@class RAREaPlatformViewer;
@class RAREaViewer_ViewerListener;
@class RAREiViewer_DisableBehaviorEnum;
@protocol JavaUtilList;
@protocol JavaUtilMap;
@protocol RAREiAnimator;
@protocol RAREiCollapsible;
@protocol RAREiContainer;
@protocol RAREiPageSetup;
@protocol RAREiPlatformAnimator;
@protocol RAREiPlatformAppContext;
@protocol RAREiPlatformComponent;
@protocol RAREiPlatformIcon;
@protocol RAREiPopup;
@protocol RAREiScrollerSupport;
@protocol RAREiTarget;
@protocol RAREiWidget;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/event/iExpandedListener.h"
#include "com/appnativa/rare/ui/event/iPopupMenuListener.h"
#include "com/appnativa/rare/ui/iCollapsible.h"
#include "com/appnativa/rare/viewer/iViewer.h"
#include "com/appnativa/rare/widget/aPlatformWidget.h"

@interface RAREaViewer : RAREaPlatformWidget < RAREiViewer > {
 @public
  id<RAREiPlatformAppContext> appContext_;
  JavaNetURL *contextURL_;
  BOOL local_;
  NSString *registeredName_;
  RAREActionLink *viewerActionLink_;
  BOOL wasReset_;
  BOOL autoUnregister_;
  BOOL disposable_;
  RAREiViewer_DisableBehaviorEnum *disableBehavior_;
  BOOL autoDispose_;
  NSString *collapsedTitle_;
  BOOL loadFired_;
  id<RAREiTarget> myTarget_;
  JavaUtilHashMap *nameMap_;
  RAREUTIdentityArrayList *orphanWidgets_;
  BOOL registered_;
  RARERenderTypeEnum *renderType_;
  BOOL unloadFired_;
}

+ (RAREaViewer_ViewerListener *)viewerListener;
- (id)initWithRAREiContainer:(id<RAREiContainer>)parent;
- (BOOL)canPrint;
- (BOOL)canSave;
- (void)downArrow;
- (void)leftArrow;
- (void)pageDown;
- (void)pageEnd;
- (void)pageHome;
- (void)pageHomeHorizontal;
- (void)pageEndHorizontal;
- (void)pageLeft;
- (void)pageRight;
- (void)pageSetupWithRAREiPageSetup:(id<RAREiPageSetup>)ps;
- (void)pageUp;
- (void)rightArrow;
- (void)upArrow;
- (void)configureWithRARESPOTViewer:(RARESPOTViewer *)vcfg;
- (void)configureWithRARESPOTWidget:(RARESPOTWidget *)cfg;
- (BOOL)containsNamedItemWithNSString:(NSString *)name;
- (void)dispose;
- (void)onConfigurationChangedWithBoolean:(BOOL)reset;
- (void)onConfigurationWillChangeWithId:(id)newConfig;
- (void)register__;
- (id)registerNamedItemWithNSString:(NSString *)name
                             withId:(id)object;
- (void)reloadWithBoolean:(BOOL)context;
- (void)reset;
- (void)saveWithJavaIoWriter:(JavaIoWriter *)w;
- (RAREWindowViewer *)showAsDialogWithJavaUtilMap:(id<JavaUtilMap>)winoptions;
- (RAREWindowViewer *)showAsDialogWithNSString:(NSString *)title
                                   withBoolean:(BOOL)modal;
- (id<RAREiPopup>)showAsPopupWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)owner
                                        withJavaUtilMap:(id<JavaUtilMap>)options;
- (id<RAREiPopup>)showAsPopupWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)owner
                                              withFloat:(float)x
                                              withFloat:(float)y
                              withRAREiPlatformAnimator:(id<RAREiPlatformAnimator>)animator
                                        withJavaUtilMap:(id<JavaUtilMap>)options;
- (RAREWindowViewer *)showAsWindowWithJavaUtilMap:(id<JavaUtilMap>)winoptions;
- (RAREWindowViewer *)showAsWindowWithNSString:(NSString *)title;
- (void)targetAcquiredWithRAREiTarget:(id<RAREiTarget>)target;
- (void)targetLostWithRAREiTarget:(id<RAREiTarget>)target;
- (NSString *)description;
- (void)unregisterWithBoolean:(BOOL)disposing;
- (id)unregisterNamedItemWithNSString:(NSString *)name;
- (void)setAppContextWithRAREiPlatformAppContext:(id<RAREiPlatformAppContext>)context;
- (void)setAutoDisposeWithBoolean:(BOOL)autoDispose;
- (void)setAutoUnregisterWithBoolean:(BOOL)autoUnregister;
- (void)setCollapsedTitleWithNSString:(NSString *)title;
- (void)setCollapsiblePaneTitleIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon;
- (void)setCollapsiblePaneTitleTextWithNSString:(NSString *)text;
- (void)setContextURLWithJavaNetURL:(JavaNetURL *)url;
- (void)setDisableBehaviorWithRAREiViewer_DisableBehaviorEnum:(RAREiViewer_DisableBehaviorEnum *)behavior;
- (void)setDisposableWithBoolean:(BOOL)disposable;
- (void)setEnabledWithBoolean:(BOOL)enabled;
- (void)setFocusableWithBoolean:(BOOL)focusable;
- (void)setItemDataWithNSString:(NSString *)name
                         withId:(id)value;
- (void)setItemEnabledWithNSString:(NSString *)name
                       withBoolean:(BOOL)enabled;
- (void)setItemIconWithNSString:(NSString *)name
          withRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon;
- (void)setItemSelectedWithNSString:(NSString *)name
                        withBoolean:(BOOL)selected;
- (void)setItemTooltipWithNSString:(NSString *)name
                      withNSString:(NSString *)tooltip;
- (void)setItemValueWithNSString:(NSString *)name
                          withId:(id)value;
- (void)setItemVisibleWithNSString:(NSString *)name
                       withBoolean:(BOOL)visible;
- (void)setLoadAnimatorWithRAREiAnimator:(id<RAREiAnimator>)wa;
- (void)setNameWithNSString:(NSString *)name;
- (void)setRenderTypeWithRARERenderTypeEnum:(RARERenderTypeEnum *)renderType;
- (void)setSourceURLWithJavaNetURL:(JavaNetURL *)url;
- (void)setViewerActionLinkWithRAREActionLink:(RAREActionLink *)link;
- (id)getWithNSString:(NSString *)name;
- (id<RAREiPlatformAppContext>)getAppContext;
- (JavaNetURL *)getBaseURL;
- (NSString *)getCollapsedTitle;
- (id<RAREiCollapsible>)getCollapsiblePane;
- (id<RAREiContainer>)getContainerViewer;
- (JavaNetURL *)getContextURL;
- (id<RAREiPlatformComponent>)getDataComponent;
- (RAREiViewer_DisableBehaviorEnum *)getDisableBehavior;
- (id<RAREiExpandedListener>)getExpandedListener;
- (id)getItemDataWithNSString:(NSString *)name;
- (NSString *)getItemDataStringWithNSString:(NSString *)name;
- (id)getItemValueWithNSString:(NSString *)name;
- (id)getNamedItemWithNSString:(NSString *)name;
- (id)getNamedItemExWithNSString:(NSString *)name;
- (id<JavaUtilList>)getNames;
- (id<RAREiTarget>)getParentTarget;
- (id<RAREiPlatformComponent>)getPrintComponent;
- (RARERenderTypeEnum *)getRenderType;
- (id<RAREiWidget>)getScrollPaneWidgetWithNSString:(NSString *)name;
- (id)getSelection;
- (JavaNetURL *)getSourceURL;
- (id<RAREiTarget>)getTarget;
- (NSString *)getTitle;
- (id<RAREiViewer>)getViewer;
- (RAREActionLink *)getViewerActionLink;
- (JavaNetURL *)getViewerURL;
- (BOOL)hasSelection;
- (BOOL)isAutoDispose;
- (BOOL)isAutoUnregister;
- (BOOL)isBackPressedHandled;
- (BOOL)isDisposable;
- (BOOL)isExternalViewer;
- (BOOL)isItemEnabledWithNSString:(NSString *)name;
- (BOOL)isItemSelectedWithNSString:(NSString *)name;
- (BOOL)isItemVisibleWithNSString:(NSString *)name;
- (BOOL)isRegistered;
- (BOOL)isTabPaneViewer;
- (BOOL)isViewer;
- (BOOL)isWindowOnlyViewer;
- (void)registerOrphanWidgetWithRAREiWidget:(id<RAREiWidget>)widget;
- (void)unregisterOrphanWidgetWithRAREiWidget:(id<RAREiWidget>)widget;
- (void)addPopupMenuListenerWithRAREiPopup:(id<RAREiPopup>)popup
                           withJavaUtilMap:(id<JavaUtilMap>)options;
- (void)clearConfigurationWithBoolean:(BOOL)dispose;
- (void)clearNameMap;
- (void)configureExWithRARESPOTViewer:(RARESPOTViewer *)cfg
                          withBoolean:(BOOL)border
                          withBoolean:(BOOL)textMenus
                          withBoolean:(BOOL)margin;
- (RAREaViewer_ViewerListener *)createViewerListener;
- (void)fireLoadEventWithBoolean:(BOOL)loaded;
- (NSString *)generateTargetName;
- (NSString *)generateTargetNameWithNSString:(NSString *)prefix;
- (NSString *)generateViewerName;
- (NSString *)generateViewerNameWithNSString:(NSString *)prefix;
- (void)handleItemHasCollapsedWithRAREExpansionEvent:(RAREExpansionEvent *)event;
- (void)handleItemHasExpandedWithRAREExpansionEvent:(RAREExpansionEvent *)event;
- (void)handleViewerConfigurationChangedWithBoolean:(BOOL)reset;
- (void)handleViewerConfigurationWillChangeWithId:(id)newConfig;
- (BOOL)importURLWithJavaUtilList:(id<JavaUtilList>)urls
          withRAREDropInformation:(RAREDropInformation *)drop;
- (void)loaded;
- (RAREWindowViewer *)showAsWindowOrDialogWithJavaUtilMap:(id<JavaUtilMap>)winoptions
                                              withBoolean:(BOOL)dialog;
- (void)unloaded;
- (id)getAttributeExWithChar:(unichar)firstChar
                withNSString:(NSString *)key;
- (BOOL)isContainer;
- (id<RAREiPopup>)createPopupWithInt:(int)width
                             withInt:(int)height;
- (void)getProposedPopupBoundsWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)owner
                                     withRAREUIDimension:(RAREUIDimension *)contentSize
                                     withRAREUIRectangle:(RAREUIRectangle *)outBounds
                                             withBoolean:(BOOL)adjustBallonBorder;
- (id<RAREiCollapsible>)getCollapsible;
- (id<RAREiScrollerSupport>)getScrollerSupport;
- (id<RAREiPageSetup>)createPageSetup;
- (void)copyAllFieldsTo:(RAREaViewer *)other;
@end

J2OBJC_FIELD_SETTER(RAREaViewer, appContext_, id<RAREiPlatformAppContext>)
J2OBJC_FIELD_SETTER(RAREaViewer, contextURL_, JavaNetURL *)
J2OBJC_FIELD_SETTER(RAREaViewer, registeredName_, NSString *)
J2OBJC_FIELD_SETTER(RAREaViewer, viewerActionLink_, RAREActionLink *)
J2OBJC_FIELD_SETTER(RAREaViewer, disableBehavior_, RAREiViewer_DisableBehaviorEnum *)
J2OBJC_FIELD_SETTER(RAREaViewer, collapsedTitle_, NSString *)
J2OBJC_FIELD_SETTER(RAREaViewer, myTarget_, id<RAREiTarget>)
J2OBJC_FIELD_SETTER(RAREaViewer, nameMap_, JavaUtilHashMap *)
J2OBJC_FIELD_SETTER(RAREaViewer, orphanWidgets_, RAREUTIdentityArrayList *)
J2OBJC_FIELD_SETTER(RAREaViewer, renderType_, RARERenderTypeEnum *)

typedef RAREaViewer ComAppnativaRareViewerAViewer;

@interface RAREaViewer_ViewerListener : NSObject < RAREiExpandedListener, RAREiCollapsible_iTitleProvider > {
}

- (void)itemHasCollapsedWithRAREExpansionEvent:(RAREExpansionEvent *)event;
- (void)itemHasExpandedWithRAREExpansionEvent:(RAREExpansionEvent *)event;
- (NSString *)getCollapsedTitleWithRAREiCollapsible:(id<RAREiCollapsible>)pane
                         withRAREiPlatformComponent:(id<RAREiPlatformComponent>)c;
- (NSString *)getExpandedTitleWithRAREiCollapsible:(id<RAREiCollapsible>)pane
                        withRAREiPlatformComponent:(id<RAREiPlatformComponent>)c;
- (RAREaPlatformViewer *)getViewerWithId:(id)o;
- (id)init;
@end

@interface RAREaViewer_$1 : NSObject < RAREiPopupMenuListener > {
 @public
  RAREaViewer *this$0_;
  id val$expand_;
  id val$collapse_;
  id<RAREiPopup> val$popup_;
}

- (void)popupMenuWillBecomeVisibleWithRAREExpansionEvent:(RAREExpansionEvent *)e;
- (void)popupMenuWillBecomeInvisibleWithRAREExpansionEvent:(RAREExpansionEvent *)e;
- (void)popupMenuCanceledWithRAREExpansionEvent:(RAREExpansionEvent *)e;
- (id)initWithRAREaViewer:(RAREaViewer *)outer$
                   withId:(id)capture$0
                   withId:(id)capture$1
           withRAREiPopup:(id<RAREiPopup>)capture$2;
@end

J2OBJC_FIELD_SETTER(RAREaViewer_$1, this$0_, RAREaViewer *)
J2OBJC_FIELD_SETTER(RAREaViewer_$1, val$expand_, id)
J2OBJC_FIELD_SETTER(RAREaViewer_$1, val$collapse_, id)
J2OBJC_FIELD_SETTER(RAREaViewer_$1, val$popup_, id<RAREiPopup>)

#endif // _RAREaViewer_H_
