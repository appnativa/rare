//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/FunctionCallbackChainner.java
//
//  Created by decoteaud on 9/15/15.
//

#include "com/appnativa/rare/FunctionCallbackChainner.h"
#include "com/appnativa/rare/iFunctionCallback.h"
#include "com/appnativa/rare/widget/iWidget.h"
#include "com/appnativa/util/IdentityArrayList.h"
#include "java/lang/Exception.h"
#include "java/util/NoSuchElementException.h"

@implementation RAREFunctionCallbackChainner

- (id)init {
  if (self = [super init]) {
    callbacks_ = [[RAREUTIdentityArrayList alloc] initWithInt:3];
  }
  return self;
}

- (RAREFunctionCallbackChainner *)addCallbackWithRAREiFunctionCallback:(id<RAREiFunctionCallback>)callback {
  [((RAREUTIdentityArrayList *) nil_chk(callbacks_)) addWithId:callback];
  return self;
}

- (void)finishedWithBoolean:(BOOL)canceled
                     withId:(id)returnValue {
  [((id<RAREiFunctionCallback>) nil_chk([self nextCallback])) finishedWithBoolean:canceled withId:returnValue];
}

- (id<RAREiFunctionCallback>)nextCallback {
  int len = (callbacks_ == nil) ? 0 : [callbacks_ size];
  if (position_ < len) {
    id<RAREiFunctionCallback> cb = [((RAREUTIdentityArrayList *) nil_chk(callbacks_)) getWithInt:position_++];
    if (position_ == len) {
      [callbacks_ clear];
      callbacks_ = nil;
    }
    return cb;
  }
  else {
    @throw [[JavaUtilNoSuchElementException alloc] init];
  }
}

- (int)getPosition {
  return position_;
}

- (BOOL)isDone {
  return callbacks_ == nil;
}

- (BOOL)isErrorWithRAREiWidget:(id<RAREiWidget>)errorHandler
                        withId:(id)value {
  if ([value isKindOfClass:[JavaLangException class]]) {
    if (errorHandler != nil) {
      [errorHandler handleExceptionWithJavaLangThrowable:(JavaLangException *) check_class_cast(value, [JavaLangException class])];
    }
    return YES;
  }
  return NO;
}

- (void)copyAllFieldsTo:(RAREFunctionCallbackChainner *)other {
  [super copyAllFieldsTo:other];
  other->callbacks_ = callbacks_;
  other->position_ = position_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "addCallbackWithRAREiFunctionCallback:", NULL, "LRAREFunctionCallbackChainner", 0x1, NULL },
    { "nextCallback", NULL, "LRAREiFunctionCallback", 0x1, NULL },
    { "isDone", NULL, "Z", 0x1, NULL },
    { "isErrorWithRAREiWidget:withId:", NULL, "Z", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "callbacks_", NULL, 0x4, "LRAREUTIdentityArrayList" },
    { "position_", NULL, 0x4, "I" },
  };
  static J2ObjcClassInfo _RAREFunctionCallbackChainner = { "FunctionCallbackChainner", "com.appnativa.rare", NULL, 0x1, 4, methods, 2, fields, 0, NULL};
  return &_RAREFunctionCallbackChainner;
}

@end
