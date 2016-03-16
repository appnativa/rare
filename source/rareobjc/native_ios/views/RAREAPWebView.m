//
// Created by Don DeCoteau on 7/16/13.
// Copyright (c) 2013 SparseWare. All rights reserved.
//
// To change the template use AppCode | Preferences | File Templates.
//


#import <com/appnativa/rare/platform/apple/ui/view/WebView.h>
#import "RAREAPWebView.h"
#import "APView+Component.h"


@implementation RAREAPWebView

+ (Class)layerClass
{
  return [RARECAGradientLayer class];
}
- (id)init {
  self = [super init];
  if (self) {
    self.delegate=self;
    self.scalesPageToFit=YES;
  }
  
  return self;
}

- (NSString *)getHREF {
  NSURLRequest *req=self.request;
  if(req) {
    return req.URL.absoluteString;
  }
  return nil;
}
-(void)sparDispose {
  self.delegate =nil;
  [super sparDispose];
}
- (void)dealloc {
  self.delegate = nil;
}
- (void)loadWithHREF:(NSString *)url {
  NSURL* nsurl=[NSURL URLWithString:url];
  NSURLRequest *req= [[NSURLRequest alloc] initWithURL:nsurl];
  [self loadRequest:req];
}

-(void) setWindowProperty: (NSString*) property value: (NSObject*) value {
  JSContext* js= (JSContext*)[self valueForKeyPath:@"documentView.webView.mainFrame.javaScriptContext"];
  JSValue* window = js[@"window"];
  [window setValue:value forKey:property];
}

-(void) setScaleToFit: (BOOL) scaleToFit {
  self.scalesPageToFit=scaleToFit;
}

- (void)loadWithContent:(NSString *)content contentType:(NSString *)type baseHREF:(NSString *) baseHref {
  NSURL* nsurl=nil;
  if(baseHref) {
    nsurl=[NSURL URLWithString:baseHref];
  }
  if([type rangeOfString:@"text/html"].location==0) {
    [self loadHTMLString:content baseURL:nsurl];
  }
  else {
    NSData *data = [content dataUsingEncoding:NSUTF8StringEncoding];
    [self loadData:data MIMEType:type textEncodingName:@"utf-8" baseURL:nsurl];
  }
}

- (BOOL)webView:(UIWebView *)webView shouldStartLoadWithRequest:(NSURLRequest *)request navigationType:(UIWebViewNavigationType)navigationType {
  RAREWebView* view=(RAREWebView*)self.sparView;
  if(view->loadListener_) {
    return [view->loadListener_ loadWillStartWithRAREWebView:view withNSString:request.URL.absoluteString withInt:navigationType];
  }
  return YES;
}
-(void) clearContents {
  @try {
  [ self stringByEvaluatingJavaScriptFromString:@"document.body.innerHTML = \"\";"];
  }
  @catch(NSException* ignore){}
 }
- (void)webViewDidStartLoad:(UIWebView *)webView {
  RAREWebView* view=(RAREWebView*)self.sparView;
  if(view->loadListener_) {
    [view->loadListener_ loadStartedWithRAREWebView:view];
  }
}

- (void)webViewDidFinishLoad:(UIWebView *)webView {
  if(self.scalesPageToFit) {
    UIScrollView *scroll=[self scrollView];
    
    float zoom=self.bounds.size.width/scroll.contentSize.width;
    [scroll setZoomScale:zoom animated:NO];
  }
  RAREWebView* view=(RAREWebView*)self.sparView;
  if(view->loadListener_) {
    [view->loadListener_ loadFinishedWithRAREWebView:view];
  }
}

- (void)webView:(UIWebView *)webView didFailLoadWithError:(NSError *)error {
  RAREWebView* view=(RAREWebView*)self.sparView;
  if(view->loadListener_) {
    [view->loadListener_ loadFailedWithRAREWebView:view withNSString:error.localizedDescription];
  }
  
}

-(void)adjustForRotation {
 [self.scrollView setZoomScale:0 animated:YES];
}

-(void)setHidden:(BOOL)hidden {
  BOOL changed=self.hidden!=hidden;
  [super setHidden:hidden];
  if(changed && self.window) {
    RAREView *view = self.sparView;
    if (view && view->viewListener_ ) {
      [view visibilityChangedWithBoolean:!hidden];
    }
  }
}
- (void)willMoveToWindow:(UIWindow *)newWindow {
  [super willMoveToWindow:newWindow];
  RAREView* view=self.sparView;
  if(view && view->viewListener_ && self.window!=newWindow) {
    [view visibilityChangedWithBoolean:newWindow!=nil];
  }
}
@end