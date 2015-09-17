//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/iAsyncLoadStatusHandler.java
//
//  Created by decoteaud on 9/15/15.
//

#ifndef _RAREiAsyncLoadStatusHandler_H_
#define _RAREiAsyncLoadStatusHandler_H_

@class JavaLangThrowable;
@class RAREActionLink;
@protocol RAREUTiCancelable;
@protocol RAREiWidget;

#import "JreEmulation.h"

@protocol RAREiAsyncLoadStatusHandler < NSObject, JavaObject >
- (void)loadStartedWithRAREiWidget:(id<RAREiWidget>)context
                withRAREActionLink:(RAREActionLink *)link
             withRAREUTiCancelable:(id<RAREUTiCancelable>)cancelable;
- (void)loadCompletedWithRAREiWidget:(id<RAREiWidget>)context
                  withRAREActionLink:(RAREActionLink *)link;
- (void)errorOccuredWithRAREiWidget:(id<RAREiWidget>)context
                 withRAREActionLink:(RAREActionLink *)link
              withJavaLangThrowable:(JavaLangThrowable *)error;
@end

#define ComAppnativaRareIAsyncLoadStatusHandler RAREiAsyncLoadStatusHandler

#endif // _RAREiAsyncLoadStatusHandler_H_
