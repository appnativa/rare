//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/platform/apple/Timer.java
//
//  Created by decoteaud on 7/29/15.
//

#include "com/appnativa/rare/platform/apple/Timer.h"
#include "com/appnativa/util/iCancelable.h"
#include "java/lang/Runnable.h"
#include "java/lang/System.h"
#import "RAREAPTimer.h"

@implementation RARETimer

- (id)init {
  return [self initRARETimerWithNSString:nil];
}

- (id)initRARETimerWithNSString:(NSString *)name {
  if (self = [super init]) {
    self->name_ = name;
  }
  return self;
}

- (id)initWithNSString:(NSString *)name {
  return [self initRARETimerWithNSString:name];
}

- (void)cancel {
  [self cancelWithBoolean:YES];
}

- (void)cancelWithBoolean:(BOOL)canInterrupt {
  if (proxy_ != nil) {
    [self cancelEx];
    proxy_ = nil;
  }
  canceled_ = YES;
}

- (id<RAREUTiCancelable>)scheduleWithJavaLangRunnable:(id<JavaLangRunnable>)task
                                             withLong:(long long int)delay {
  return [self scheduleWithJavaLangRunnable:task withLong:delay withLong:0];
}

- (id<RAREUTiCancelable>)scheduleWithJavaLangRunnable:(id<JavaLangRunnable>)task
                                             withLong:(long long int)delay
                                             withLong:(long long int)interval {
  if(!proxy_) {
    proxy_=[[RAREAPTimer alloc] initWithMainQueue: useMainQueue_];
  }
  [((RAREAPTimer*) proxy_) scheduleWithDelay:delay interval: interval runnable: task];
  return self;
}

- (NSString *)description {
  if (name_ == nil) {
    return [NSString stringWithFormat:@"Timer:%@", [NSString valueOfInt:[JavaLangSystem identityHashCodeWithId:self]]];
  }
  return [NSString stringWithFormat:@"Timer:%@", name_];
}

- (void)setUseMainQuueWithBoolean:(BOOL)useMainQuue {
  self->useMainQueue_ = useMainQuue;
}

- (NSString *)getName {
  return name_;
}

- (BOOL)isCanceled {
  return canceled_;
}

- (BOOL)isDone {
  if (canceled_) {
    return YES;
  }
  if (proxy_ != nil) {
    return [self isDoneEx];
  }
  return NO;
}

- (void)cancelEx {
  [((RAREAPTimer*) proxy_) cancel];
}

- (BOOL)isDoneEx {
  return [((RAREAPTimer*) proxy_) isDone ];
}

- (void)copyAllFieldsTo:(RARETimer *)other {
  [super copyAllFieldsTo:other];
  other->canceled_ = canceled_;
  other->name_ = name_;
  other->proxy_ = proxy_;
  other->useMainQueue_ = useMainQueue_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "scheduleWithJavaLangRunnable:withLong:", NULL, "LRAREUTiCancelable", 0x1, NULL },
    { "scheduleWithJavaLangRunnable:withLong:withLong:", NULL, "LRAREUTiCancelable", 0x101, NULL },
    { "getName", NULL, "LNSString", 0x1, NULL },
    { "isCanceled", NULL, "Z", 0x1, NULL },
    { "isDone", NULL, "Z", 0x1, NULL },
    { "cancelEx", NULL, "V", 0x104, NULL },
    { "isDoneEx", NULL, "Z", 0x104, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "canceled_", NULL, 0x0, "Z" },
    { "name_", NULL, 0x0, "LNSString" },
    { "proxy_", NULL, 0x0, "LNSObject" },
  };
  static J2ObjcClassInfo _RARETimer = { "Timer", "com.appnativa.rare.platform.apple", NULL, 0x1, 7, methods, 3, fields, 0, NULL};
  return &_RARETimer;
}

@end
