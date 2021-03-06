//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../util/src/com/appnativa/util/SimpleDateFormatEx.java
//
//  Created by decoteaud on 3/11/16.
//

#include "com/appnativa/util/SimpleDateFormatEx.h"
#include "java/lang/StringBuffer.h"
#include "java/text/DateFormatSymbols.h"
#include "java/text/FieldPosition.h"
#include "java/util/Date.h"
#include "java/util/Locale.h"

@implementation RAREUTSimpleDateFormatEx

static BOOL RAREUTSimpleDateFormatEx_checkForISOPattern_ = YES;

+ (BOOL)checkForISOPattern {
  return RAREUTSimpleDateFormatEx_checkForISOPattern_;
}

+ (BOOL *)checkForISOPatternRef {
  return &RAREUTSimpleDateFormatEx_checkForISOPattern_;
}

- (id)initWithNSString:(NSString *)pattern
withJavaTextDateFormatSymbols:(JavaTextDateFormatSymbols *)symbols {
  if (self = [super initWithNSString:pattern withJavaTextDateFormatSymbols:symbols]) {
    [self checkPatternWithNSString:pattern];
  }
  return self;
}

- (id)initWithNSString:(NSString *)pattern
    withJavaUtilLocale:(JavaUtilLocale *)arg1 {
  if (self = [super initWithNSString:pattern withJavaUtilLocale:arg1]) {
    [self checkPatternWithNSString:pattern];
  }
  return self;
}

- (id)initWithNSString:(NSString *)pattern {
  if (self = [super initWithNSString:pattern]) {
    [self checkPatternWithNSString:pattern];
  }
  return self;
}

- (id)init {
  return [super init];
}

- (void)applyLocalizedPatternWithNSString:(NSString *)pattern {
  [super applyLocalizedPatternWithNSString:pattern];
  [self checkPatternWithNSString:pattern];
}

- (void)applyPatternWithNSString:(NSString *)pattern {
  [super applyPatternWithNSString:pattern];
  [self checkPatternWithNSString:pattern];
}

- (void)checkPatternWithNSString:(NSString *)pattern {
  if (RAREUTSimpleDateFormatEx_checkForISOPattern_) {
    isISOPattern_ = NO;
    if ([((NSString *) nil_chk(pattern)) hasPrefix:@"yyyy-MM-dd'T'HH:mm:ss"] || [pattern hasPrefix:@"yyyy-MM-dd'T'HH:mm:ss"]) {
      if ([pattern hasSuffix:@"Z"] || [pattern hasSuffix:@"z"]) {
        isISOPattern_ = YES;
      }
    }
  }
}

- (JavaLangStringBuffer *)formatWithJavaUtilDate:(JavaUtilDate *)date
                        withJavaLangStringBuffer:(JavaLangStringBuffer *)toAppendTo
                       withJavaTextFieldPosition:(JavaTextFieldPosition *)pos {
  JavaLangStringBuffer *sb = [super formatWithJavaUtilDate:date withJavaLangStringBuffer:toAppendTo withJavaTextFieldPosition:pos];
  if (isISOPattern_) {
    int n = [((JavaLangStringBuffer *) nil_chk(sb)) lastIndexOfWithNSString:@"+"];
    if (n == -1) {
      n = [sb lastIndexOfWithNSString:@"-"];
    }
    if ((n > 18) && ([sb indexOfWithNSString:@":" withInt:n] == -1)) {
      (void) [sb insertWithInt:n + 3 withChar:':'];
    }
  }
  return sb;
}

- (void)copyAllFieldsTo:(RAREUTSimpleDateFormatEx *)other {
  [super copyAllFieldsTo:other];
  other->isISOPattern_ = isISOPattern_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "checkPatternWithNSString:", NULL, "V", 0x2, NULL },
    { "formatWithJavaUtilDate:withJavaLangStringBuffer:withJavaTextFieldPosition:", NULL, "LJavaLangStringBuffer", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "checkForISOPattern_", NULL, 0x9, "Z" },
    { "isISOPattern_", NULL, 0x0, "Z" },
  };
  static J2ObjcClassInfo _RAREUTSimpleDateFormatEx = { "SimpleDateFormatEx", "com.appnativa.util", NULL, 0x1, 2, methods, 2, fields, 0, NULL};
  return &_RAREUTSimpleDateFormatEx;
}

@end
