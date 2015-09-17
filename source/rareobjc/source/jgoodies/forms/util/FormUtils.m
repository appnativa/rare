//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/jgoodies/forms/util/FormUtils.java
//
//  Created by decoteaud on 9/15/15.
//

#include "com/jgoodies/forms/util/DefaultUnitConverter.h"
#include "com/jgoodies/forms/util/FormUtils.h"
#include "java/lang/Character.h"
#include "java/lang/IllegalArgumentException.h"
#include "java/lang/NullPointerException.h"

@implementation RAREJGFormUtils

- (id)init {
  return [super init];
}

+ (void)assertNotBlankWithNSString:(NSString *)text
                      withNSString:(NSString *)description_ {
  if (text == nil) {
    @throw [[JavaLangNullPointerException alloc] initWithNSString:[NSString stringWithFormat:@"The %@ must not be null.", description_]];
  }
  if ([RAREJGFormUtils isBlankWithNSString:text]) {
    @throw [[JavaLangIllegalArgumentException alloc] initWithNSString:[NSString stringWithFormat:@"The %@ must not be empty, or whitespace. See FormUtils.isBlank(String)", description_]];
  }
}

+ (void)assertNotNullWithId:(id)object
               withNSString:(NSString *)description_ {
  if (object == nil) {
    @throw [[JavaLangNullPointerException alloc] initWithNSString:[NSString stringWithFormat:@"The %@ must not be null.", description_]];
  }
}

+ (BOOL)equalsWithId:(id)o1
              withId:(id)o2 {
  return (o1 != nil && o2 != nil && [o1 isEqual:o2]) || (o1 == nil && o2 == nil);
}

+ (BOOL)isBlankWithNSString:(NSString *)str {
  int length;
  if (str == nil || (length = [str sequenceLength]) == 0) {
    return YES;
  }
  for (int i = length - 1; i >= 0; i--) {
    if (![JavaLangCharacter isWhitespaceWithChar:[((NSString *) nil_chk(str)) charAtWithInt:i]]) {
      return NO;
    }
  }
  return YES;
}

+ (BOOL)isNotBlankWithNSString:(NSString *)str {
  int length;
  if (str == nil || (length = [str sequenceLength]) == 0) {
    return NO;
  }
  for (int i = length - 1; i >= 0; i--) {
    if (![JavaLangCharacter isWhitespaceWithChar:[((NSString *) nil_chk(str)) charAtWithInt:i]]) {
      return YES;
    }
  }
  return NO;
}

+ (void)clearLookAndFeelBasedCaches {
  [((RAREJGDefaultUnitConverter *) nil_chk([RAREJGDefaultUnitConverter getInstance])) clearCache];
}

+ (void)ensureValidCache {
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "init", NULL, NULL, 0x2, NULL },
    { "equalsWithId:withId:", NULL, "Z", 0x9, NULL },
    { "isBlankWithNSString:", NULL, "Z", 0x9, NULL },
    { "isNotBlankWithNSString:", NULL, "Z", 0x9, NULL },
    { "ensureValidCache", NULL, "V", 0x8, NULL },
  };
  static J2ObjcClassInfo _RAREJGFormUtils = { "FormUtils", "com.jgoodies.forms.util", NULL, 0x11, 5, methods, 0, NULL, 0, NULL};
  return &_RAREJGFormUtils;
}

@end
