//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/viewer/aWebBrowser.java
//
//  Created by decoteaud on 3/15/16.
//

#ifndef _RAREaWebBrowser_H_
#define _RAREaWebBrowser_H_

@class JavaNetURL;
@class RAREActionLink;
@class RARESPOTBrowser;
@class RARESPOTViewer;
@class RARESPOTWidget;
@class RAREWindowViewer;
@protocol JavaUtilMap;
@protocol RAREaWebBrowser_iBrowser;
@protocol RAREiCancelableFuture;
@protocol RAREiContainer;
@protocol RAREiPlatformComponent;

#import "JreEmulation.h"
#include "com/appnativa/rare/iFunctionCallback.h"
#include "com/appnativa/rare/viewer/aPlatformViewer.h"
#include "java/lang/Runnable.h"

@interface RAREaWebBrowser : RAREaPlatformViewer {
 @public
  BOOL generateBrowserExceptions_;
  BOOL handleWaitCursor_;
  BOOL stackWaitCursors_;
  int waitCursors_;
  id<RAREaWebBrowser_iBrowser> theBrowser_;
  BOOL autoInsertMetaContent_;
  BOOL useRuntimeToLoadContent_;
}

- (id)init;
- (id)initWithRAREiContainer:(id<RAREiContainer>)parent;
- (void)clearContents;
- (void)configureWithRARESPOTViewer:(RARESPOTViewer *)vcfg;
- (void)handleActionLinkWithRAREActionLink:(RAREActionLink *)link
                               withBoolean:(BOOL)deferred;
- (void)hideWaitCursorIfShowing;
- (void)reloadWithBoolean:(BOOL)context;
- (void)stopLoading;
- (id<RAREiCancelableFuture>)setDataLinkWithRAREActionLink:(RAREActionLink *)link
                                               withBoolean:(BOOL)defer;
- (void)setDataURLWithJavaNetURL:(JavaNetURL *)url;
- (void)setGenerateBrowserExceptionsWithBoolean:(BOOL)generate;
- (void)setHTMLWithNSString:(NSString *)html;
- (void)setHTMLWithNSString:(NSString *)baseHref
               withNSString:(NSString *)html;
- (void)setHandleWaitCursorWithBoolean:(BOOL)handleWaitCursor;
- (void)setPlainTextWithNSString:(NSString *)text;
- (void)setScaleToFitWithBoolean:(BOOL)scaleToFit;
- (void)setURLWithNSString:(NSString *)url;
- (void)setURLWithJavaNetURL:(JavaNetURL *)url;
- (void)setValueWithId:(id)value;
- (BOOL)getGenerateBrowserExceptions;
- (NSString *)getLocation;
- (id)getSelection;
- (JavaNetURL *)getURL;
- (BOOL)hasValue;
- (void)handleCustomPropertiesWithRARESPOTWidget:(RARESPOTWidget *)cfg
                                 withJavaUtilMap:(id<JavaUtilMap>)properties;
- (BOOL)isHandleWaitCursor;
- (void)startedLoadingEx;
- (void)finishedLoadingEx;
- (void)checkConfigure;
- (void)clearConfigurationWithBoolean:(BOOL)dispose;
- (void)configureExWithRARESPOTBrowser:(RARESPOTBrowser *)cfg;
- (id<RAREaWebBrowser_iBrowser>)createWebViewWithRARESPOTBrowser:(RARESPOTBrowser *)cfg;
- (void)loadContentWithNSString:(NSString *)content
                   withNSString:(NSString *)contentType
                   withNSString:(NSString *)baseHref;
- (void)showWaitCursor;
- (void)hideWaitCursor;
- (void)setURLExWithNSString:(NSString *)url;
- (id)getNativeBrowserView;
- (BOOL)isAutoInsertMetaContent;
- (void)setAutoInsertMetaContentWithBoolean:(BOOL)autoInsertMetaContent;
- (void)copyAllFieldsTo:(RAREaWebBrowser *)other;
@end

J2OBJC_FIELD_SETTER(RAREaWebBrowser, theBrowser_, id<RAREaWebBrowser_iBrowser>)

typedef RAREaWebBrowser ComAppnativaRareViewerAWebBrowser;

@protocol RAREaWebBrowser_iBrowser < NSObject, JavaObject >
- (void)clearContents;
- (void)dispose;
- (void)load__WithNSString:(NSString *)url;
- (void)loadContentWithNSString:(NSString *)content
                   withNSString:(NSString *)contentType
                   withNSString:(NSString *)baseHref;
- (void)reload;
- (void)stopLoading;
- (id<RAREiPlatformComponent>)getComponent;
- (id)getNativeBrowser;
- (NSString *)getLocation;
@end

@interface RAREaWebBrowser_$1 : NSObject < RAREiFunctionCallback > {
 @public
  RAREaWebBrowser *this$0_;
  RAREWindowViewer *val$w_;
  NSString *val$url_;
}

- (void)finishedWithBoolean:(BOOL)canceled
                     withId:(id)returnValue;
- (id)initWithRAREaWebBrowser:(RAREaWebBrowser *)outer$
         withRAREWindowViewer:(RAREWindowViewer *)capture$0
                 withNSString:(NSString *)capture$1;
@end

J2OBJC_FIELD_SETTER(RAREaWebBrowser_$1, this$0_, RAREaWebBrowser *)
J2OBJC_FIELD_SETTER(RAREaWebBrowser_$1, val$w_, RAREWindowViewer *)
J2OBJC_FIELD_SETTER(RAREaWebBrowser_$1, val$url_, NSString *)

@interface RAREaWebBrowser_$2 : NSObject < JavaLangRunnable > {
 @public
  RAREaWebBrowser *this$0_;
  NSString *val$url_;
}

- (void)run;
- (id)initWithRAREaWebBrowser:(RAREaWebBrowser *)outer$
                 withNSString:(NSString *)capture$0;
@end

J2OBJC_FIELD_SETTER(RAREaWebBrowser_$2, this$0_, RAREaWebBrowser *)
J2OBJC_FIELD_SETTER(RAREaWebBrowser_$2, val$url_, NSString *)

@interface RAREaWebBrowser_$3 : NSObject < JavaLangRunnable > {
 @public
  RAREaWebBrowser *this$0_;
  NSString *val$s_;
  NSString *val$contentType_;
  NSString *val$baseHref_;
}

- (void)run;
- (id)initWithRAREaWebBrowser:(RAREaWebBrowser *)outer$
                 withNSString:(NSString *)capture$0
                 withNSString:(NSString *)capture$1
                 withNSString:(NSString *)capture$2;
@end

J2OBJC_FIELD_SETTER(RAREaWebBrowser_$3, this$0_, RAREaWebBrowser *)
J2OBJC_FIELD_SETTER(RAREaWebBrowser_$3, val$s_, NSString *)
J2OBJC_FIELD_SETTER(RAREaWebBrowser_$3, val$contentType_, NSString *)
J2OBJC_FIELD_SETTER(RAREaWebBrowser_$3, val$baseHref_, NSString *)

#endif // _RAREaWebBrowser_H_
