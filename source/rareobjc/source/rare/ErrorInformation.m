//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ErrorInformation.java
//
//  Created by decoteaud on 3/11/16.
//

#include "com/appnativa/rare/ErrorInformation.h"
#include "com/appnativa/rare/exception/ApplicationException.h"
#include "java/lang/CloneNotSupportedException.h"
#include "java/lang/Exception.h"
#include "java/lang/InternalError.h"
#include "java/lang/StringBuilder.h"
#include "java/lang/Throwable.h"

@implementation RAREErrorInformation

- (id)initWithJavaLangThrowable:(JavaLangThrowable *)error {
  return [self initRAREErrorInformationWithNSString:nil withJavaLangThrowable:error];
}

- (id)initWithNSString:(NSString *)title
          withNSString:(NSString *)message {
  if (self = [super init]) {
    self->title_ = title;
    self->message_ = message;
  }
  return self;
}

- (id)initRAREErrorInformationWithNSString:(NSString *)title
                     withJavaLangThrowable:(JavaLangThrowable *)error {
  if (self = [super init]) {
    self->title_ = title;
    self->error_ = error;
    self->message_ = [RAREApplicationException getMessageExWithJavaLangThrowable:error];
    self->javaStackTrace_ = [RAREApplicationException getStackTraceWithJavaLangThrowable:error];
  }
  return self;
}

- (id)initWithNSString:(NSString *)title
 withJavaLangThrowable:(JavaLangThrowable *)error {
  return [self initRAREErrorInformationWithNSString:title withJavaLangThrowable:error];
}

- (id)initWithId:(id)error
    withNSString:(NSString *)title
    withNSString:(NSString *)message {
  if (self = [super init]) {
    self->title_ = title;
    self->error_ = error;
    self->message_ = message;
    if ([error isKindOfClass:[JavaLangThrowable class]]) {
      self->javaStackTrace_ = [RAREApplicationException getStackTraceWithJavaLangThrowable:(JavaLangThrowable *) check_class_cast(error, [JavaLangThrowable class])];
    }
  }
  return self;
}

- (id)initWithId:(id)error
    withNSString:(NSString *)title
    withNSString:(NSString *)message
    withNSString:(NSString *)stackTrace {
  if (self = [super init]) {
    self->title_ = title;
    self->error_ = error;
    self->message_ = message;
    self->javaStackTrace_ = stackTrace;
  }
  return self;
}

- (id)initWithId:(id)error
    withNSString:(NSString *)title
    withNSString:(NSString *)message
    withNSString:(NSString *)stackTrace
    withNSString:(NSString *)scriptStackTrace {
  if (self = [super init]) {
    self->title_ = title;
    self->error_ = error;
    self->javaStackTrace_ = stackTrace;
    self->scriptStackTrace_ = scriptStackTrace;
    self->message_ = message;
  }
  return self;
}

- (id)clone {
  @try {
    return [super clone];
  }
  @catch (JavaLangCloneNotSupportedException *ex) {
    @throw [[JavaLangInternalError alloc] init];
  }
}

- (NSString *)description {
  JavaLangStringBuilder *sw = [[JavaLangStringBuilder alloc] init];
  if ([self okWithNSString:title_]) {
    (void) [((JavaLangStringBuilder *) nil_chk([sw appendWithNSString:title_])) appendWithNSString:@"\n\n"];
  }
  if ([self okWithNSString:message_]) {
    (void) [((JavaLangStringBuilder *) nil_chk([sw appendWithNSString:message_])) appendWithNSString:@"\n\n"];
  }
  if ([self okWithNSString:scriptStackTrace_]) {
    (void) [((JavaLangStringBuilder *) nil_chk([sw appendWithNSString:@"\n"])) appendWithNSString:scriptStackTrace_];
  }
  else if ([self okWithNSString:javaStackTrace_]) {
    (void) [((JavaLangStringBuilder *) nil_chk([sw appendWithNSString:@"\n"])) appendWithNSString:javaStackTrace_];
  }
  return [sw description];
}

- (NSString *)toAlertPanelString {
  JavaLangStringBuilder *sw = [[JavaLangStringBuilder alloc] init];
  if ([self okWithNSString:message_]) {
    (void) [((JavaLangStringBuilder *) nil_chk([sw appendWithNSString:message_])) appendWithNSString:@"\n"];
  }
  if ([self okWithNSString:scriptStackTrace_]) {
    (void) [((JavaLangStringBuilder *) nil_chk([sw appendWithNSString:@"\n"])) appendWithNSString:scriptStackTrace_];
  }
  else if ([self okWithNSString:javaStackTrace_]) {
    (void) [((JavaLangStringBuilder *) nil_chk([sw appendWithNSString:@"\n"])) appendWithNSString:javaStackTrace_];
  }
  int count = 0;
  int len = [sw sequenceLength];
  for (int i = 0; i < len; i++) {
    if ([sw charAtWithInt:i] == 0x000a) {
      count++;
      if (count == 40) {
        [sw setLengthWithInt:i - 3];
        (void) [sw appendWithNSString:@"..."];
        break;
      }
    }
  }
  return [sw description];
}

- (void)setErrorWithId:(id)error {
  self->error_ = error;
}

- (void)setMessageWithNSString:(NSString *)message {
  self->message_ = message;
}

- (void)setScriptStackTraceWithNSString:(NSString *)scriptStackTrace {
  self->scriptStackTrace_ = scriptStackTrace;
}

- (void)setStackTraceWithNSString:(NSString *)stackTrace {
  self->javaStackTrace_ = stackTrace;
}

- (void)setTitleWithNSString:(NSString *)title {
  self->title_ = title;
}

- (id)getError {
  return error_;
}

- (NSString *)getStackTrace {
  return javaStackTrace_;
}

- (NSString *)getMessage {
  if ((message_ == nil) && (javaException_ != nil)) {
    return [RAREApplicationException getMessageExWithJavaLangThrowable:javaException_];
  }
  return message_;
}

- (NSString *)getScriptStackTrace {
  return scriptStackTrace_;
}

- (NSString *)getTitle {
  return title_;
}

- (BOOL)okWithNSString:(NSString *)s {
  return (s != nil) && ([s sequenceLength] > 0);
}

- (JavaLangException *)getJavaException {
  return javaException_;
}

- (void)setJavaExceptionWithJavaLangException:(JavaLangException *)e {
  self->javaException_ = e;
}

- (id)copyWithZone:(NSZone *)zone {
  return [self clone];
}

- (void)copyAllFieldsTo:(RAREErrorInformation *)other {
  [super copyAllFieldsTo:other];
  other->error_ = error_;
  other->javaException_ = javaException_;
  other->javaStackTrace_ = javaStackTrace_;
  other->message_ = message_;
  other->scriptStackTrace_ = scriptStackTrace_;
  other->title_ = title_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "clone", NULL, "LNSObject", 0x1, NULL },
    { "toAlertPanelString", NULL, "LNSString", 0x1, NULL },
    { "getError", NULL, "LNSObject", 0x1, NULL },
    { "getStackTrace", NULL, "LNSString", 0x1, NULL },
    { "getMessage", NULL, "LNSString", 0x1, NULL },
    { "getScriptStackTrace", NULL, "LNSString", 0x1, NULL },
    { "getTitle", NULL, "LNSString", 0x1, NULL },
    { "okWithNSString:", NULL, "Z", 0x2, NULL },
    { "getJavaException", NULL, "LJavaLangException", 0x1, NULL },
  };
  static J2ObjcClassInfo _RAREErrorInformation = { "ErrorInformation", "com.appnativa.rare", NULL, 0x1, 9, methods, 0, NULL, 0, NULL};
  return &_RAREErrorInformation;
}

@end
