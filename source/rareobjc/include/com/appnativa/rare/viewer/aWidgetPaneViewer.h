//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/viewer/aWidgetPaneViewer.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RAREaWidgetPaneViewer_H_
#define _RAREaWidgetPaneViewer_H_

@class RAREActionLink;
@class RARERenderTypeEnum;
@class RARERenderableDataItem;
@class RARESPOTDataItem;
@class RARESPOTViewer;
@class RARESPOTWidget;
@class RARESPOTWidgetPane;
@class RAREUITarget;
@class RAREiContainer_LayoutEnum;
@protocol RAREiContainer;
@protocol RAREiParentComponent;
@protocol RAREiPlatformComponent;
@protocol RAREiTransitionAnimator;
@protocol RAREiViewer;
@protocol RAREiWidget;

#import "JreEmulation.h"
#include "com/appnativa/rare/iFunctionCallback.h"
#include "com/appnativa/rare/viewer/aContainer.h"

@interface RAREaWidgetPaneViewer : RAREaContainer {
 @public
  RARERenderTypeEnum *widgetRenderType_;
  BOOL autoDisposeViewers_;
  BOOL autoResizeWidgets_;
  BOOL manualUpdate_;
  RAREUITarget *paneTarget_;
  NSString *scriptFunctionPrefix_;
  id<RAREiWidget> theWidget_;
  id<RAREiParentComponent> widgetPanel_;
  id<RAREiTransitionAnimator> transitionAnimator_;
}

- (id)init;
- (id)initWithRAREiContainer:(id<RAREiContainer>)parent;
- (id)initWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp;
- (id)initWithRAREiContainer:(id<RAREiContainer>)parent
  withRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp;
- (void)clearContents;
- (void)configueForComponentWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp
                                    withRARESPOTViewer:(RARESPOTViewer *)cfg;
- (void)configueViaScriptWithNSString:(NSString *)functionPrefix
                   withRARESPOTViewer:(RARESPOTViewer *)cfg;
- (void)configureWithRARESPOTViewer:(RARESPOTViewer *)vcfg;
- (void)handleActionLinkWithRAREActionLink:(RAREActionLink *)link
                               withBoolean:(BOOL)deferred;
- (void)register__;
- (void)reloadWithBoolean:(BOOL)context;
- (BOOL)requestFocus;
- (int)size;
- (void)unregisterWithBoolean:(BOOL)disposing;
- (void)setAutoResizeWidgetsWithBoolean:(BOOL)autoResizeWidgets;
- (void)setComponentWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp;
- (void)setContainerPanelWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)component;
- (void)setDataItemsWithRARESPOTDataItem:(RARESPOTDataItem *)items;
- (void)setManualUpdateWithBoolean:(BOOL)manual;
- (void)setOpaqueWithBoolean:(BOOL)opaque;
- (void)setScriptFunctionPrefixWithNSString:(NSString *)prefix;
- (void)setTransitionAnimatorWithRAREiTransitionAnimator:(id<RAREiTransitionAnimator>)transitionAnimator;
- (void)setTransitionAnimatorWithNSString:(NSString *)inAnimation;
- (void)setValueWithId:(id)value;
- (void)setWidgetWithRAREActionLink:(RAREActionLink *)link;
- (void)setWidgetWithRAREiWidget:(id<RAREiWidget>)w;
- (void)setWidgetWithNSString:(NSString *)url;
- (void)setWidgetWithRAREiWidget:(id<RAREiWidget>)w
                     withBoolean:(BOOL)disposeCurrent;
- (void)setWidgetConfigWithRARESPOTWidget:(RARESPOTWidget *)cfg;
- (void)setWidgetPanelWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)component;
- (void)setWidgetRenderTypeWithRARERenderTypeEnum:(RARERenderTypeEnum *)type;
- (RARERenderableDataItem *)getWithInt:(int)index;
- (id)getAttributeWithNSString:(NSString *)key;
- (id<RAREiPlatformComponent>)getComponent;
- (id)getHTTPFormValue;
- (id)getSelection;
- (id)getSelectionData;
- (id<RAREiTransitionAnimator>)getTransitionAnimator;
- (id)getValue;
- (id<RAREiWidget>)getWidget;
- (id<RAREiParentComponent>)getWidgetPanel;
- (BOOL)isAutoDisposeViewers;
- (BOOL)isAutoResizeWidgets;
- (BOOL)isManualUpdate;
- (BOOL)isSubmittable;
- (BOOL)isValidForSubmissionWithBoolean:(BOOL)showerror;
- (id<RAREiWidget>)addWidgetExWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)panel
                                      withRARESPOTWidget:(RARESPOTWidget *)cfg
                           withRAREiContainer_LayoutEnum:(RAREiContainer_LayoutEnum *)layout;
- (void)adjustWidgetForPlatformWithRAREiWidget:(id<RAREiWidget>)w;
- (void)clearConfigurationWithBoolean:(BOOL)dispose;
- (void)configureExWithRARESPOTWidgetPane:(RARESPOTWidgetPane *)cfg;
- (void)configureExWithRAREiParentComponent:(id<RAREiParentComponent>)container
                   withRAREiParentComponent:(id<RAREiParentComponent>)wpanel
                     withRARESPOTWidgetPane:(RARESPOTWidgetPane *)cfg;
- (id<RAREiWidget>)removeWidget;
- (id<RAREiParentComponent>)createWidgetPanel;
- (void)removeViewerExWithBoolean:(BOOL)dispose;
- (void)setViewerWithRAREiViewer:(id<RAREiViewer>)v;
- (void)setWidgetRenderTypeExWithRARERenderTypeEnum:(RARERenderTypeEnum *)type;
- (void)setupRenderTypeWithRARESPOTWidget:(RARESPOTWidget *)cfg;
- (void)copyAllFieldsTo:(RAREaWidgetPaneViewer *)other;
@end

J2OBJC_FIELD_SETTER(RAREaWidgetPaneViewer, widgetRenderType_, RARERenderTypeEnum *)
J2OBJC_FIELD_SETTER(RAREaWidgetPaneViewer, paneTarget_, RAREUITarget *)
J2OBJC_FIELD_SETTER(RAREaWidgetPaneViewer, scriptFunctionPrefix_, NSString *)
J2OBJC_FIELD_SETTER(RAREaWidgetPaneViewer, theWidget_, id<RAREiWidget>)
J2OBJC_FIELD_SETTER(RAREaWidgetPaneViewer, widgetPanel_, id<RAREiParentComponent>)
J2OBJC_FIELD_SETTER(RAREaWidgetPaneViewer, transitionAnimator_, id<RAREiTransitionAnimator>)

typedef RAREaWidgetPaneViewer ComAppnativaRareViewerAWidgetPaneViewer;

@interface RAREaWidgetPaneViewer_$1 : NSObject < RAREiFunctionCallback > {
 @public
  RAREaWidgetPaneViewer *this$0_;
}

- (void)finishedWithBoolean:(BOOL)canceled
                     withId:(id)returnValue;
- (id)initWithRAREaWidgetPaneViewer:(RAREaWidgetPaneViewer *)outer$;
@end

J2OBJC_FIELD_SETTER(RAREaWidgetPaneViewer_$1, this$0_, RAREaWidgetPaneViewer *)

#endif // _RAREaWidgetPaneViewer_H_