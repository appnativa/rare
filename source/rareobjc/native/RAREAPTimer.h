//
// Created by Don DeCoteau on 9/26/13.
// Copyright (c) 2013 SparseWare. All rights reserved.
//
// To change the template use AppCode | Preferences | File Templates.
//


#import <Foundation/Foundation.h>

@protocol JavaLangRunnable;
@interface RAREAPTimer : NSObject {
  BOOL done_;
}
-(id) init;
-(id) initWithMainQueue:(BOOL) useMainQueue;
-(id) initWithDelay: (u_int64_t) delay interval: (u_int64_t) interval runnable: (id<JavaLangRunnable>) runnable;
-(void)scheduleWithDelay:(u_int64_t)delay interval:(u_int64_t)interval runnable:(id <JavaLangRunnable>)runnable;
-(void) cancel;
-(BOOL) isDone;
@end