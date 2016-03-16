//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/viewer/WebBrowser.java
//
//  Created by decoteaud on 3/15/16.
//

#ifndef _RAREWebBrowser_H_
#define _RAREWebBrowser_H_

@class RARESPOTBrowser;
@class RAREWebView;
@protocol RAREiContainer;
@protocol RAREiFunctionCallback;
@protocol RAREiPlatformComponent;

#import "JreEmulation.h"
#include "com/appnativa/rare/platform/apple/ui/view/WebView.h"
#include "com/appnativa/rare/viewer/aWebBrowser.h"

@interface RAREWebBrowser : RAREaWebBrowser {
 @public
  RAREWebView *webView_;
}

- (id)init;
- (id)initWithRAREiContainer:(id<RAREiContainer>)parent;
- (void)dispose;
- (void)executeScriptWithNSString:(NSString *)script
        withRAREiFunctionCallback:(id<RAREiFunctionCallback>)cb;
- (void)setWindowPropertyWithNSString:(NSString *)name
                               withId:(id)value;
- (void)setScaleToFitWithBoolean:(BOOL)scaleToFit;
- (void)handleViewerConfigurationChangedWithBoolean:(BOOL)reset;
- (id<RAREaWebBrowser_iBrowser>)createWebViewWithRARESPOTBrowser:(RARESPOTBrowser *)cfg;
- (void)copyAllFieldsTo:(RAREWebBrowser *)other;
@end

J2OBJC_FIELD_SETTER(RAREWebBrowser, webView_, RAREWebView *)

typedef RAREWebBrowser ComAppnativaRareViewerWebBrowser;

@interface RAREWebBrowser_AppleBrowser : NSObject < RAREaWebBrowser_iBrowser, RAREWebView_iLoadListener > {
 @public
  RAREWebBrowser *this$0_;
  NSString *loadingUrl_;
}

- (id)initWithRAREWebBrowser:(RAREWebBrowser *)outer$;
- (void)clearContents;
- (void)dispose;
- (void)load__WithNSString:(NSString *)url;
- (void)loadContentWithNSString:(NSString *)content
                   withNSString:(NSString *)contentType
                   withNSString:(NSString *)baseHref;
- (void)loadFailedWithRAREWebView:(RAREWebView *)view
                     withNSString:(NSString *)reason;
- (void)loadFinishedWithRAREWebView:(RAREWebView *)view;
- (void)loadFinishedWithRAREWebView:(RAREWebView *)view
                        withBoolean:(BOOL)failed
                       withNSString:(NSString *)reason;
- (void)loadStartedWithRAREWebView:(RAREWebView *)view;
- (BOOL)loadWillStartWithRAREWebView:(RAREWebView *)view
                        withNSString:(NSString *)url
                             withInt:(int)loadType;
- (void)reload;
- (void)stopLoading;
- (id<RAREiPlatformComponent>)getComponent;
- (NSString *)getLocation;
- (id)getNativeBrowser;
- (void)copyAllFieldsTo:(RAREWebBrowser_AppleBrowser *)other;
@end

J2OBJC_FIELD_SETTER(RAREWebBrowser_AppleBrowser, this$0_, RAREWebBrowser *)
J2OBJC_FIELD_SETTER(RAREWebBrowser_AppleBrowser, loadingUrl_, NSString *)

#endif // _RAREWebBrowser_H_
