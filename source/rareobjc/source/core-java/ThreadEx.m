//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core-java/com/appnativa/rare/ThreadEx.java
//
//  Created by decoteaud on 9/15/14.
//

#include "com/appnativa/rare/ThreadEx.h"
#include "com/appnativa/util/iCancelable.h"
#include "java/lang/Runnable.h"
#include "java/lang/ThreadGroup.h"
#include "java/lang/Throwable.h"

@implementation RAREThreadEx

- (id)init {
  return [super init];
}

- (id)initWithJavaLangRunnable:(id<JavaLangRunnable>)target
                  withNSString:(NSString *)name {
  if (self = [super initWithJavaLangRunnable:target withNSString:name]) {
    self->runnable_ = target;
  }
  return self;
}

- (id)initWithJavaLangRunnable:(id<JavaLangRunnable>)target {
  if (self = [super initWithJavaLangRunnable:target]) {
    self->runnable_ = target;
  }
  return self;
}

- (id)initWithNSString:(NSString *)name {
  return [super initWithNSString:name];
}

- (id)initWithJavaLangThreadGroup:(JavaLangThreadGroup *)group
             withJavaLangRunnable:(id<JavaLangRunnable>)target
                     withNSString:(NSString *)name
                         withLong:(long long int)stackSize {
  if (self = [super initWithJavaLangThreadGroup:group withJavaLangRunnable:target withNSString:name withLong:stackSize]) {
    self->runnable_ = target;
  }
  return self;
}

- (id)initWithJavaLangThreadGroup:(JavaLangThreadGroup *)group
             withJavaLangRunnable:(id<JavaLangRunnable>)target
                     withNSString:(NSString *)name {
  return [super initWithJavaLangThreadGroup:group withJavaLangRunnable:target withNSString:name];
}

- (id)initWithJavaLangThreadGroup:(JavaLangThreadGroup *)group
             withJavaLangRunnable:(id<JavaLangRunnable>)target {
  if (self = [super initWithJavaLangThreadGroup:group withJavaLangRunnable:target]) {
    self->runnable_ = target;
  }
  return self;
}

- (id)initWithJavaLangThreadGroup:(JavaLangThreadGroup *)group
                     withNSString:(NSString *)name {
  return [super initWithJavaLangThreadGroup:group withNSString:name];
}

- (void)interrupt {
  if ([(id) runnable_ conformsToProtocol: @protocol(RAREUTiCancelable)]) {
    @try {
      [((id<RAREUTiCancelable>) check_protocol_cast(runnable_, @protocol(RAREUTiCancelable))) cancelWithBoolean:YES];
    }
    @catch (JavaLangThrowable *e) {
    }
  }
  [super interrupt];
}

- (void)copyAllFieldsTo:(RAREThreadEx *)other {
  [super copyAllFieldsTo:other];
  other->runnable_ = runnable_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcFieldInfo fields[] = {
    { "runnable_", NULL, 0x4, "LJavaLangRunnable" },
  };
  static J2ObjcClassInfo _RAREThreadEx = { "ThreadEx", "com.appnativa.rare", NULL, 0x1, 0, NULL, 1, fields, 0, NULL};
  return &_RAREThreadEx;
}

@end
