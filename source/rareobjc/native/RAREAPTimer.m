//
// Created by Don DeCoteau on 9/26/13.
// Copyright (c) 2013 SparseWare. All rights reserved.
//
// To change the template use AppCode | Preferences | File Templates.
//


#import <java/lang/RuntimeException.h>
#import "RAREAPTimer.h"
#include "java/lang/Runnable.h"


@implementation RAREAPTimer {
  dispatch_source_t timer_;
  BOOL useMainQueue_;
  CADisplayLink *displayLink_;
  id <JavaLangRunnable>runnable_;
}
static dispatch_queue_t timerQueue_ = nil;

- (id)init {
  if (self = [super init]) {
  }
  return self;
}
-(id) initWithMainQueue:(BOOL) useMainQueue; {
  if (self = [super init]) {
    useMainQueue_=useMainQueue;
  }
  return self;
}

- (id)initWithDelay:(u_int64_t)delay interval:(u_int64_t)interval runnable:(id <JavaLangRunnable>)runnable {
  if (self = [super init]) {
    [self scheduleWithDelay:delay interval:interval runnable:runnable];
  }
  return self;
}

- (void)scheduleWithDelay:(u_int64_t)delay interval:(u_int64_t)interval runnable:(id <JavaLangRunnable>)runnable {
  if (timer_ || displayLink_) {
    [self cancel];
  }
  if(useMainQueue_) {
    displayLink_=[CADisplayLink displayLinkWithTarget:self selector:@selector(onDisplayLink:)];
    [displayLink_ setFrameInterval:1];
    if([NSThread isMainThread]) {
      [displayLink_ addToRunLoop:[NSRunLoop currentRunLoop] forMode:NSDefaultRunLoopMode];
    }
    else {
      dispatch_async(dispatch_get_main_queue(), ^{
        [displayLink_ addToRunLoop:[NSRunLoop currentRunLoop] forMode:NSDefaultRunLoopMode];
      });//end block
    }
    runnable_=runnable;
  }
  else {
    if (!timerQueue_) {
      timerQueue_ = dispatch_queue_create("com.appnativa.rare.TimerQueue", NULL);
    }
    dispatch_queue_t queue=useMainQueue_ ? dispatch_get_main_queue() : timerQueue_;
    if(interval<1) {
      dispatch_after(dispatch_time(DISPATCH_TIME_NOW, delay * 1000000), queue, ^{
        @try {
          [runnable run];
        }
        @finally {
          done_=YES;
        }
      });
    }
    else {
      timer_ = dispatch_source_create(DISPATCH_SOURCE_TYPE_TIMER,
          0, 0, queue);
      if (timer_) {
        if (delay <= 0) {
          delay=0;
        }
        dispatch_source_set_timer(timer_, dispatch_time(DISPATCH_TIME_NOW, delay * 1000000), interval* 1000000, 0);
        dispatch_source_set_event_handler(timer_, ^{
          [runnable run];
        });
        dispatch_resume(timer_);
      }
      else {
        @throw [[JavaLangRuntimeException alloc] initWithNSString:@"Unexpected exception. Could not create timer"];

      }
    }
  }
}
-(BOOL) isDone {
  return done_;
}

- (void)cancel {
  if (timer_) {
    dispatch_source_cancel(timer_);
    timer_ = nil;
  }
  else if(displayLink_) {
    [displayLink_ invalidate];
    displayLink_=nil;
  }
}
-(void) onDisplayLink: (id) sender {
  if(runnable_) [runnable_ run];
}

@end