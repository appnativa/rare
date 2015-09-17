//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/LaterFunctionCallback.java
//
//  Created by decoteaud on 9/15/15.
//

#include "com/appnativa/rare/LaterFunctionCallback.h"
#include "com/appnativa/rare/Platform.h"
#include "com/appnativa/rare/iFunctionCallback.h"

@implementation RARELaterFunctionCallback

- (id)initWithRAREiFunctionCallback:(id<RAREiFunctionCallback>)callback {
  if (self = [super init]) {
    self->callback_ = callback;
  }
  return self;
}

- (void)finishedWithBoolean:(BOOL)canceled
                     withId:(id)returnValue {
  self->canceled_ = canceled;
  self->returnValue_ = returnValue;
  [RAREPlatform invokeLaterWithJavaLangRunnable:self];
}

- (void)run {
  @try {
    [((id<RAREiFunctionCallback>) nil_chk(callback_)) finishedWithBoolean:canceled_ withId:returnValue_];
  }
  @finally {
    returnValue_ = nil;
  }
}

- (void)copyAllFieldsTo:(RARELaterFunctionCallback *)other {
  [super copyAllFieldsTo:other];
  other->callback_ = callback_;
  other->canceled_ = canceled_;
  other->returnValue_ = returnValue_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcFieldInfo fields[] = {
    { "callback_", NULL, 0x12, "LRAREiFunctionCallback" },
  };
  static J2ObjcClassInfo _RARELaterFunctionCallback = { "LaterFunctionCallback", "com.appnativa.rare", NULL, 0x1, 0, NULL, 1, fields, 0, NULL};
  return &_RARELaterFunctionCallback;
}

@end
