//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/FunctionCallbackWaiter.java
//
//  Created by decoteaud on 3/11/16.
//

#include "IOSClass.h"
#include "com/appnativa/rare/FunctionCallbackWaiter.h"
#include "com/appnativa/rare/Platform.h"
#include "com/appnativa/rare/iFunctionCallback.h"
#include "com/appnativa/rare/widget/iWidget.h"
#include "com/appnativa/util/ObjectHolder.h"
#include "java/lang/Exception.h"
#include "java/lang/InterruptedException.h"
#include "java/util/HashMap.h"
#include "java/util/Map.h"

@implementation RAREFunctionCallbackWaiter

- (id)init {
  if (self = [super init]) {
    resultsMap_ = [[JavaUtilHashMap alloc] initWithInt:3];
  }
  return self;
}

- (id<RAREiFunctionCallback>)createCallbackWithId:(id)id_ {
  callbackCount_++;
  return [[RAREFunctionCallbackWaiter_TaggedCallbackWrapper alloc] initWithId:id_ withRAREFunctionCallbackWaiter:self];
}

- (void)startWaitingWithRAREiFunctionCallback:(id<RAREiFunctionCallback>)completionCallback {
  self->completionCallback_ = completionCallback;
  [self checkForCompletion];
}

- (id<JavaUtilMap>)blockUntilComplete {
  self->completionCallback_ = nil;
  while (YES) {
    @synchronized (resultsMap_) {
      if ([((JavaUtilHashMap *) nil_chk(resultsMap_)) size] == callbackCount_) {
        break;
      }
      [resultsMap_ wait];
    }
  }
  return resultsMap_;
}

- (void)callbackFinishedWithId:(id)id_
                   withBoolean:(BOOL)canceled
                        withId:(id)returnValue {
  (void) [((JavaUtilHashMap *) nil_chk(resultsMap_)) putWithId:id_ withId:[[RAREFunctionCallbackWaiter_CallbackResult alloc] initWithBoolean:canceled withId:returnValue]];
  [self checkForCompletion];
}

- (void)checkForCompletion {
  if ([((JavaUtilHashMap *) nil_chk(resultsMap_)) size] == callbackCount_) {
    if (completionCallback_ == nil) {
      @synchronized (resultsMap_) {
        [resultsMap_ notify];
        return;
      }
    }
    RAREUTObjectHolder *h = [[RAREUTObjectHolder alloc] initWithId:self withId:nil withId:resultsMap_];
    id<RAREiFunctionCallback> cb = completionCallback_;
    resultsMap_ = nil;
    completionCallback_ = nil;
    if ([RAREPlatform isUIThread]) {
      [((id<RAREiFunctionCallback>) nil_chk(cb)) finishedWithBoolean:NO withId:h];
    }
    else {
      [RAREPlatform invokeLaterWithJavaLangRunnable:[[RAREFunctionCallbackWaiter_$1 alloc] initWithRAREiFunctionCallback:cb withRAREUTObjectHolder:h]];
    }
  }
}

- (void)copyAllFieldsTo:(RAREFunctionCallbackWaiter *)other {
  [super copyAllFieldsTo:other];
  other->callbackCount_ = callbackCount_;
  other->completionCallback_ = completionCallback_;
  other->resultsMap_ = resultsMap_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "createCallbackWithId:", NULL, "LRAREiFunctionCallback", 0x1, NULL },
    { "blockUntilComplete", NULL, "LJavaUtilMap", 0x1, "JavaLangInterruptedException" },
    { "callbackFinishedWithId:withBoolean:withId:", NULL, "V", 0x4, NULL },
    { "checkForCompletion", NULL, "V", 0x4, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "resultsMap_", NULL, 0x4, "LJavaUtilHashMap" },
    { "callbackCount_", NULL, 0x4, "I" },
    { "completionCallback_", NULL, 0x4, "LRAREiFunctionCallback" },
  };
  static J2ObjcClassInfo _RAREFunctionCallbackWaiter = { "FunctionCallbackWaiter", "com.appnativa.rare", NULL, 0x1, 4, methods, 3, fields, 0, NULL};
  return &_RAREFunctionCallbackWaiter;
}

@end
@implementation RAREFunctionCallbackWaiter_CallbackResult

- (id)initWithBoolean:(BOOL)canceled
               withId:(id)returnValue {
  if (self = [super init]) {
    self->canceled_ = canceled;
    self->returnValue_ = returnValue;
  }
  return self;
}

- (BOOL)resultIsError {
  return [returnValue_ isKindOfClass:[JavaLangException class]];
}

- (BOOL)resultIsErrorWithRAREiWidget:(id<RAREiWidget>)errorHandler {
  if ([returnValue_ isKindOfClass:[JavaLangException class]]) {
    if (errorHandler != nil) {
      [errorHandler handleExceptionWithJavaLangThrowable:(JavaLangException *) check_class_cast(returnValue_, [JavaLangException class])];
    }
    return YES;
  }
  return NO;
}

- (id)getContent {
  if (returnValue_ == nil) {
    return nil;
  }
  if ([returnValue_ isKindOfClass:[RAREUTObjectHolder class]]) {
    return ((RAREUTObjectHolder *) nil_chk(returnValue_))->value_;
  }
  return returnValue_;
}

- (id)getReturnValue {
  return returnValue_;
}

- (BOOL)isCanceled {
  return canceled_;
}

- (void)copyAllFieldsTo:(RAREFunctionCallbackWaiter_CallbackResult *)other {
  [super copyAllFieldsTo:other];
  other->canceled_ = canceled_;
  other->returnValue_ = returnValue_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "resultIsError", NULL, "Z", 0x1, NULL },
    { "resultIsErrorWithRAREiWidget:", NULL, "Z", 0x1, NULL },
    { "getContent", NULL, "LNSObject", 0x1, NULL },
    { "getReturnValue", NULL, "LNSObject", 0x1, NULL },
    { "isCanceled", NULL, "Z", 0x1, NULL },
  };
  static J2ObjcClassInfo _RAREFunctionCallbackWaiter_CallbackResult = { "CallbackResult", "com.appnativa.rare", "FunctionCallbackWaiter", 0x9, 5, methods, 0, NULL, 0, NULL};
  return &_RAREFunctionCallbackWaiter_CallbackResult;
}

@end
@implementation RAREFunctionCallbackWaiter_TaggedCallbackWrapper

- (id)initWithId:(id)id_
withRAREFunctionCallbackWaiter:(RAREFunctionCallbackWaiter *)waiter {
  if (self = [super init]) {
    self->id__ = id_;
    self->waiter_ = waiter;
  }
  return self;
}

- (void)finishedWithBoolean:(BOOL)canceled
                     withId:(id)returnValue {
  [((RAREFunctionCallbackWaiter *) nil_chk(waiter_)) callbackFinishedWithId:id__ withBoolean:canceled withId:returnValue];
  waiter_ = nil;
  id__ = nil;
}

- (void)copyAllFieldsTo:(RAREFunctionCallbackWaiter_TaggedCallbackWrapper *)other {
  [super copyAllFieldsTo:other];
  other->callback_ = callback_;
  other->id__ = id__;
  other->waiter_ = waiter_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcFieldInfo fields[] = {
    { "callback_", NULL, 0x4, "LRAREiFunctionCallback" },
    { "id__", "id", 0x4, "LNSObject" },
    { "waiter_", NULL, 0x4, "LRAREFunctionCallbackWaiter" },
  };
  static J2ObjcClassInfo _RAREFunctionCallbackWaiter_TaggedCallbackWrapper = { "TaggedCallbackWrapper", "com.appnativa.rare", "FunctionCallbackWaiter", 0xc, 0, NULL, 3, fields, 0, NULL};
  return &_RAREFunctionCallbackWaiter_TaggedCallbackWrapper;
}

@end
@implementation RAREFunctionCallbackWaiter_$1

- (void)run {
  [((id<RAREiFunctionCallback>) nil_chk(val$cb_)) finishedWithBoolean:NO withId:val$h_];
}

- (id)initWithRAREiFunctionCallback:(id<RAREiFunctionCallback>)capture$0
             withRAREUTObjectHolder:(RAREUTObjectHolder *)capture$1 {
  val$cb_ = capture$0;
  val$h_ = capture$1;
  return [super init];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcFieldInfo fields[] = {
    { "val$cb_", NULL, 0x1012, "LRAREiFunctionCallback" },
    { "val$h_", NULL, 0x1012, "LRAREUTObjectHolder" },
  };
  static J2ObjcClassInfo _RAREFunctionCallbackWaiter_$1 = { "$1", "com.appnativa.rare", "FunctionCallbackWaiter", 0x8000, 0, NULL, 2, fields, 0, NULL};
  return &_RAREFunctionCallbackWaiter_$1;
}

@end
