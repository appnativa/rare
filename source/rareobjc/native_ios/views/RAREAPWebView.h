//
// Created by Don DeCoteau on 7/16/13.
// Copyright (c) 2013 SparseWare. All rights reserved.
//
// To change the template use AppCode | Preferences | File Templates.
//


#import <Foundation/Foundation.h>


@interface RAREAPWebView : UIWebView  <UIWebViewDelegate>
- (NSString *)getHREF;

- (void)loadWithHREF:(NSString *)url;

- (void)loadWithContent:(NSString *)content contentType:(NSString *)type baseHREF:(NSString *)HREF;

-(void) setScaleToFit: (BOOL) scaleToFit;
-(void) clearContents;
-(void) setWindowProperty: (NSString*) property value: (NSObject*) value;
-(void) adjustForRotation;

@end