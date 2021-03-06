//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core-tabpane/com/appnativa/rare/ui/tabpane/TabDocument.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RARETabDocument_H_
#define _RARETabDocument_H_

@class JavaLangException;
@class JavaLangThrowable;
@class RAREActionLink;
@class RAREEventListenerList;
@class RAREPaintBucket;
@class RARESPOTViewer;
@protocol RAREiFunctionCallback;
@protocol RAREiPlatformAppContext;
@protocol RAREiPlatformComponent;
@protocol RAREiPlatformIcon;
@protocol RAREiTabDocument_iCanChangeCallback;
@protocol RAREiTabDocument_iDocumentListener;
@protocol RAREiTabPaneViewer;
@protocol RAREiTarget;
@protocol RAREiTarget_iListener;
@protocol RAREiViewer;
@protocol RAREiWidget;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/HeadlessTarget.h"
#include "com/appnativa/rare/ui/ViewerCreator.h"
#include "com/appnativa/rare/ui/iTabDocument.h"
#include "java/lang/Runnable.h"

@interface RARETabDocument : NSObject < RAREiTabDocument, RAREViewerCreator_iCallback > {
 @public
  BOOL canceled_;
  RAREActionLink *httpLink_;
  __weak id<RAREiTabPaneViewer> parentViewer_;
  BOOL reloadOnActivation_;
  id<RAREiTarget> theTarget_;
  BOOL wasReset_;
  BOOL _canClose_;
  id<RAREiPlatformIcon> alternateIcon_;
  id<RAREiPlatformIcon> disabledIcon_;
  id<RAREiPlatformIcon> displayIcon_;
  id<RAREiTabDocument_iDocumentListener> manager_;
  BOOL showingCursor_;
  id<RAREiTabDocument_iCanChangeCallback> callback_;
  id linkedData_;
  BOOL loading_;
  RAREPaintBucket *tabColors_;
  NSString *tabTitle_;
  id<RAREiFunctionCallback> waitingCallback_;
  long long int timeReloaded_;
  long long int reloadTimeout_;
  BOOL deactivated_;
}

- (id)initWithRAREiPlatformAppContext:(id<RAREiPlatformAppContext>)app
                         withNSString:(NSString *)name
withRAREiTabDocument_iDocumentListener:(id<RAREiTabDocument_iDocumentListener>)listener;
- (id)initWithRAREiPlatformAppContext:(id<RAREiPlatformAppContext>)app
                         withNSString:(NSString *)name
                         withNSString:(NSString *)title
                withRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon
withRAREiTabDocument_iDocumentListener:(id<RAREiTabDocument_iDocumentListener>)listener;
- (BOOL)asyncCanChangeWithRAREiWidget:(id<RAREiWidget>)context
withRAREiTabDocument_iCanChangeCallback:(id<RAREiTabDocument_iCanChangeCallback>)cb;
- (void)cancelWithBoolean:(BOOL)canInterrupt;
- (void)configCreatedWithRAREiWidget:(id<RAREiWidget>)context
                  withRAREActionLink:(RAREActionLink *)link
                  withRARESPOTViewer:(RARESPOTViewer *)config;
- (void)errorHappenedWithRAREiWidget:(id<RAREiWidget>)context
                  withRAREActionLink:(RAREActionLink *)link
               withJavaLangException:(JavaLangException *)e;
+ (void)fireTabActivatedWithRAREiTabDocument:(id<RAREiTabDocument>)doc
                   withRAREEventListenerList:(RAREEventListenerList *)listenerList;
+ (void)fireTabClosedWithRAREiTabDocument:(id<RAREiTabDocument>)doc
                withRAREEventListenerList:(RAREEventListenerList *)listenerList;
+ (void)fireTabDeactivatedWithRAREiTabDocument:(id<RAREiTabDocument>)doc
                     withRAREEventListenerList:(RAREEventListenerList *)listenerList;
+ (void)fireTabOpenedWithRAREiTabDocument:(id<RAREiTabDocument>)doc
                withRAREEventListenerList:(RAREEventListenerList *)listenerList;
+ (void)fireTabWillCloseWithRAREiTabDocument:(id<RAREiTabDocument>)doc
                   withRAREEventListenerList:(RAREEventListenerList *)listenerList;
- (void)reloadWithRAREiFunctionCallback:(id<RAREiFunctionCallback>)cb;
- (void)reset;
- (void)startingOperationWithRAREiWidget:(id<RAREiWidget>)context
                      withRAREActionLink:(RAREActionLink *)link;
- (void)tabActivated;
- (void)dispose;
- (void)tabClosed;
- (void)tabDeactivated;
- (void)unloadViewer;
- (void)tabOpened;
- (void)viewerCreatedWithRAREiWidget:(id<RAREiWidget>)context
                  withRAREActionLink:(RAREActionLink *)link
                     withRAREiViewer:(id<RAREiViewer>)viewer;
- (void)setActionLinkWithRAREActionLink:(RAREActionLink *)link;
- (void)setAlternateIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon;
- (void)setCanCloseWithBoolean:(BOOL)can;
- (void)setDisabledIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon;
- (void)setIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon;
- (void)setIconExWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon;
- (void)setLinkedDataWithId:(id)obj;
- (void)setReloadOnActivationWithBoolean:(BOOL)reload;
- (void)setTabColorsWithRAREPaintBucket:(RAREPaintBucket *)tabColors;
- (void)setTabNameWithNSString:(NSString *)name;
- (void)setTabPaneViewerWithRAREiTabPaneViewer:(id<RAREiTabPaneViewer>)parent;
- (void)setTitleWithNSString:(NSString *)title;
- (void)setTitleExWithNSString:(NSString *)title;
- (id<RAREiPlatformIcon>)getAlternateIcon;
- (id<RAREiPlatformIcon>)getDisabledIcon;
- (id<RAREiPlatformComponent>)getDocComponent;
- (id<RAREiPlatformIcon>)getIcon;
- (id)getLinkedData;
- (RAREPaintBucket *)getTabColors;
- (id<RAREiViewer>)getTabContent;
- (int)getTabIndex;
- (NSString *)getTabName;
- (id<RAREiTabPaneViewer>)getTabPaneViewer;
- (id<RAREiTarget>)getTarget;
- (NSString *)getTitle;
- (id<RAREiViewer>)getViewer;
- (id<RAREiViewer>)getViewerWithRAREiFunctionCallback:(id<RAREiFunctionCallback>)cb;
- (BOOL)isCanceled;
- (BOOL)isClosingAllowed;
- (void)loadCompletedWithRAREiWidget:(id<RAREiWidget>)context
               withJavaLangThrowable:(JavaLangThrowable *)error;
- (void)loadStartedWithRAREiWidget:(id<RAREiWidget>)context;
- (id<RAREiViewer>)getViewerExWithRAREiFunctionCallback:(id<RAREiFunctionCallback>)cb;
- (long long int)getReloadTimeout;
- (void)setReloadTimeoutWithLong:(long long int)reloadTimeout;
- (void)copyAllFieldsTo:(RARETabDocument *)other;
@end

J2OBJC_FIELD_SETTER(RARETabDocument, httpLink_, RAREActionLink *)
J2OBJC_FIELD_SETTER(RARETabDocument, theTarget_, id<RAREiTarget>)
J2OBJC_FIELD_SETTER(RARETabDocument, alternateIcon_, id<RAREiPlatformIcon>)
J2OBJC_FIELD_SETTER(RARETabDocument, disabledIcon_, id<RAREiPlatformIcon>)
J2OBJC_FIELD_SETTER(RARETabDocument, displayIcon_, id<RAREiPlatformIcon>)
J2OBJC_FIELD_SETTER(RARETabDocument, manager_, id<RAREiTabDocument_iDocumentListener>)
J2OBJC_FIELD_SETTER(RARETabDocument, callback_, id<RAREiTabDocument_iCanChangeCallback>)
J2OBJC_FIELD_SETTER(RARETabDocument, linkedData_, id)
J2OBJC_FIELD_SETTER(RARETabDocument, tabColors_, RAREPaintBucket *)
J2OBJC_FIELD_SETTER(RARETabDocument, tabTitle_, NSString *)
J2OBJC_FIELD_SETTER(RARETabDocument, waitingCallback_, id<RAREiFunctionCallback>)

typedef RARETabDocument ComAppnativaRareUiTabpaneTabDocument;

@interface RARETabDocument_DocumentTarget : RAREHeadlessTarget {
 @public
  __weak RARETabDocument *this$0_;
}

- (id)initWithRARETabDocument:(RARETabDocument *)outer$
  withRAREiPlatformAppContext:(id<RAREiPlatformAppContext>)appContext
                 withNSString:(NSString *)targetName
    withRAREiTarget_iListener:(id<RAREiTarget_iListener>)listener;
- (void)reloadViewer;
@end

@interface RARETabDocument_$1 : NSObject < JavaLangRunnable > {
 @public
  id<RAREiFunctionCallback> val$cb_;
  id<RAREiViewer> val$vv_;
}

- (void)run;
- (id)initWithRAREiFunctionCallback:(id<RAREiFunctionCallback>)capture$0
                    withRAREiViewer:(id<RAREiViewer>)capture$1;
@end

J2OBJC_FIELD_SETTER(RARETabDocument_$1, val$cb_, id<RAREiFunctionCallback>)
J2OBJC_FIELD_SETTER(RARETabDocument_$1, val$vv_, id<RAREiViewer>)

#endif // _RARETabDocument_H_
