//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../spot/src/com/appnativa/spot/SPOTException.java
//
//  Created by decoteaud on 12/8/15.
//

#include "IOSClass.h"
#include "IOSObjectArray.h"
#include "com/appnativa/spot/SPOTException.h"
#include "com/appnativa/spot/iSPOTConstants.h"
#include "com/appnativa/util/Helper.h"
#include "java/lang/Exception.h"
#include "java/lang/Throwable.h"

@implementation SPOTException

+ (int)UNEXPECTED_EXCEPTION {
  return SPOTException_UNEXPECTED_EXCEPTION;
}

+ (int)TOMANY_ELEMENTS {
  return SPOTException_TOMANY_ELEMENTS;
}

+ (int)READ_ONLY {
  return SPOTException_READ_ONLY;
}

+ (int)NULL_VALUE {
  return SPOTException_NULL_VALUE;
}

+ (int)NO_CREATE {
  return SPOTException_NO_CREATE;
}

+ (int)NOT_SUPPORTED {
  return SPOTException_NOT_SUPPORTED;
}

+ (int)NOT_CLASS {
  return SPOTException_NOT_CLASS;
}

+ (int)MISSING_ELEMENTS {
  return SPOTException_MISSING_ELEMENTS;
}

+ (int)INVALID_VALUE {
  return SPOTException_INVALID_VALUE;
}

+ (int)INVALID_ELEMENT {
  return SPOTException_INVALID_ELEMENT;
}

+ (int)ILLEGAL_VALUE {
  return SPOTException_ILLEGAL_VALUE;
}

- (id)init {
  if (self = [super init]) {
    initiatingException_ = nil;
    exceptionType_ = SPOTException_INVALID_VALUE;
  }
  return self;
}

- (id)initWithJavaLangThrowable:(JavaLangThrowable *)throwable {
  if (self = [super initWithJavaLangThrowable:throwable]) {
    initiatingException_ = nil;
    exceptionType_ = SPOTException_INVALID_VALUE;
    exceptionType_ = SPOTException_UNEXPECTED_EXCEPTION;
    initiatingException_ = throwable;
  }
  return self;
}

- (id)initWithNSString:(NSString *)message
 withJavaLangException:(JavaLangException *)e {
  if (self = [super initWithNSString:message withJavaLangThrowable:e]) {
    initiatingException_ = nil;
    exceptionType_ = SPOTException_INVALID_VALUE;
    exceptionType_ = SPOTException_UNEXPECTED_EXCEPTION;
    initiatingException_ = e;
  }
  return self;
}

- (id)initWithInt:(int)type
     withNSString:(NSString *)message
withJavaLangException:(JavaLangException *)e {
  if (self = [super initWithNSString:message withJavaLangThrowable:e]) {
    initiatingException_ = nil;
    exceptionType_ = SPOTException_INVALID_VALUE;
    exceptionType_ = type;
    initiatingException_ = e;
  }
  return self;
}

- (id)initWithNSString:(NSString *)message {
  if (self = [super initWithNSString:message]) {
    initiatingException_ = nil;
    exceptionType_ = SPOTException_INVALID_VALUE;
  }
  return self;
}

- (id)initWithInt:(int)type
     withNSString:(NSString *)message {
  if (self = [super initWithNSString:message]) {
    initiatingException_ = nil;
    exceptionType_ = SPOTException_INVALID_VALUE;
    exceptionType_ = type;
  }
  return self;
}

- (id)initWithNSString:(NSString *)msgspec
          withNSString:(NSString *)msgparam {
  if (self = [super initWithNSString:[RAREUTHelper expandStringWithNSString:msgspec withNSStringArray:[IOSObjectArray arrayWithObjects:(id[]){ msgparam } count:1 type:[IOSClass classWithClass:[NSString class]]]]]) {
    initiatingException_ = nil;
    exceptionType_ = SPOTException_INVALID_VALUE;
  }
  return self;
}

- (id)initWithInt:(int)type
     withNSString:(NSString *)msgspec
     withNSString:(NSString *)msgparam {
  if (self = [super initWithNSString:[RAREUTHelper expandStringWithNSString:msgspec withNSStringArray:[IOSObjectArray arrayWithObjects:(id[]){ msgparam } count:1 type:[IOSClass classWithClass:[NSString class]]]]]) {
    initiatingException_ = nil;
    exceptionType_ = SPOTException_INVALID_VALUE;
    exceptionType_ = type;
  }
  return self;
}

- (id)initWithNSString:(NSString *)msgspec
          withNSString:(NSString *)msgparam
          withNSString:(NSString *)msgparam2 {
  if (self = [super initWithNSString:[RAREUTHelper expandStringWithNSString:msgspec withNSStringArray:[IOSObjectArray arrayWithObjects:(id[]){ msgparam, msgparam2 } count:2 type:[IOSClass classWithClass:[NSString class]]]]]) {
    initiatingException_ = nil;
    exceptionType_ = SPOTException_INVALID_VALUE;
  }
  return self;
}

- (id)initWithInt:(int)type
     withNSString:(NSString *)msgspec
     withNSString:(NSString *)msgparam
     withNSString:(NSString *)msgparam2 {
  if (self = [super initWithNSString:[RAREUTHelper expandStringWithNSString:msgspec withNSStringArray:[IOSObjectArray arrayWithObjects:(id[]){ msgparam, msgparam2 } count:2 type:[IOSClass classWithClass:[NSString class]]]]]) {
    initiatingException_ = nil;
    exceptionType_ = SPOTException_INVALID_VALUE;
    exceptionType_ = type;
  }
  return self;
}

- (id)initWithNSString:(NSString *)msgspec
          withNSString:(NSString *)msgparam
          withNSString:(NSString *)msgparam2
          withNSString:(NSString *)msgparam3 {
  if (self = [super initWithNSString:[RAREUTHelper expandStringWithNSString:msgspec withNSStringArray:[IOSObjectArray arrayWithObjects:(id[]){ msgparam, msgparam2, msgparam3 } count:3 type:[IOSClass classWithClass:[NSString class]]]]]) {
    initiatingException_ = nil;
    exceptionType_ = SPOTException_INVALID_VALUE;
  }
  return self;
}

- (JavaLangThrowable *)getInitiatingException {
  return initiatingException_;
}

- (int)getType {
  return exceptionType_;
}

- (void)copyAllFieldsTo:(SPOTException *)other {
  [super copyAllFieldsTo:other];
  other->exceptionType_ = exceptionType_;
  other->initiatingException_ = initiatingException_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getInitiatingException", NULL, "LJavaLangThrowable", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "UNEXPECTED_EXCEPTION_", NULL, 0x19, "I" },
    { "TOMANY_ELEMENTS_", NULL, 0x19, "I" },
    { "READ_ONLY_", NULL, 0x19, "I" },
    { "NULL_VALUE_", NULL, 0x19, "I" },
    { "NO_CREATE_", NULL, 0x19, "I" },
    { "NOT_SUPPORTED_", NULL, 0x19, "I" },
    { "NOT_CLASS_", NULL, 0x19, "I" },
    { "MISSING_ELEMENTS_", NULL, 0x19, "I" },
    { "INVALID_VALUE_", NULL, 0x19, "I" },
    { "INVALID_ELEMENT_", NULL, 0x19, "I" },
    { "ILLEGAL_VALUE_", NULL, 0x19, "I" },
    { "initiatingException_", NULL, 0x1, "LJavaLangThrowable" },
    { "exceptionType_", NULL, 0x0, "I" },
  };
  static J2ObjcClassInfo _SPOTException = { "SPOTException", "com.appnativa.spot", NULL, 0x1, 1, methods, 13, fields, 0, NULL};
  return &_SPOTException;
}

@end
