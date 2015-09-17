//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../util/src/com/appnativa/util/RegularExpressionFilter.java
//
//  Created by decoteaud on 9/15/15.
//

#include "IOSCharArray.h"
#include "com/appnativa/util/CharArray.h"
#include "com/appnativa/util/RegularExpressionFilter.h"
#include "com/appnativa/util/iStringConverter.h"
#include "java/util/regex/Matcher.h"
#include "java/util/regex/Pattern.h"

@implementation RAREUTRegularExpressionFilter

static NSString * RAREUTRegularExpressionFilter_regexpEscapeChars_ = @"$^*()+?.{}[]\\-|";
static IOSCharArray * RAREUTRegularExpressionFilter_patternGroup_;

+ (NSString *)regexpEscapeChars {
  return RAREUTRegularExpressionFilter_regexpEscapeChars_;
}

+ (IOSCharArray *)patternGroup {
  return RAREUTRegularExpressionFilter_patternGroup_;
}

- (id)initWithJavaUtilRegexPattern:(JavaUtilRegexPattern *)pattern {
  if (self = [super init]) {
    emptyPasses_ = NO;
    nullPasses_ = NO;
    thePattern_ = pattern;
  }
  return self;
}

- (id)initWithNSString:(NSString *)pattern {
  if (self = [super init]) {
    emptyPasses_ = NO;
    nullPasses_ = NO;
    thePattern_ = [JavaUtilRegexPattern compileWithNSString:pattern withInt:JavaUtilRegexPattern_CASE_INSENSITIVE];
  }
  return self;
}

+ (RAREUTRegularExpressionFilter *)createFilterWithNSString:(NSString *)s {
  return [[RAREUTRegularExpressionFilter alloc] initWithJavaUtilRegexPattern:[RAREUTRegularExpressionFilter createPatternWithNSString:s]];
}

+ (JavaUtilRegexPattern *)createPatternWithNSString:(NSString *)s {
  if ([((NSString *) nil_chk(s)) hasPrefix:@"/"] && [s hasSuffix:@"/"]) {
    s = [s substring:1 endIndex:[s sequenceLength] - 1];
    return [JavaUtilRegexPattern compileWithNSString:s withInt:JavaUtilRegexPattern_CASE_INSENSITIVE];
  }
  else {
    s = [RAREUTRegularExpressionFilter parseWildcardFilterWithNSString:s];
    return [JavaUtilRegexPattern compileWithNSString:s withInt:JavaUtilRegexPattern_CASE_INSENSITIVE];
  }
}

+ (NSString *)parseStringFilterWithNSString:(NSString *)s
                                withBoolean:(BOOL)wholeword {
  IOSCharArray *a = [((NSString *) nil_chk(s)) toCharArray];
  return [RAREUTRegularExpressionFilter parseStringFilterWithCharArray:a withInt:0 withInt:(int) [((IOSCharArray *) nil_chk(a)) count] withBoolean:wholeword];
}

+ (NSString *)parseWildcardFilterWithNSString:(NSString *)filter {
  IOSCharArray *a = [((NSString *) nil_chk(filter)) toCharArray];
  return [RAREUTRegularExpressionFilter parseWildcardFilterWithCharArray:a withInt:0 withInt:(int) [((IOSCharArray *) nil_chk(a)) count]];
}

+ (NSString *)parseWildcardFilterWithCharArray:(IOSCharArray *)chars
                                       withInt:(int)pos
                                       withInt:(int)len {
  RAREUTCharArray *cb = [[RAREUTCharArray alloc] init];
  RAREUTCharArray *cb2 = nil;
  len += pos;
  unichar c;
  while (pos < len) {
    c = IOSCharArray_Get(nil_chk(chars), pos++);
    if (c == '*') {
      (void) [cb appendWithChar:'.'];
      (void) [cb appendWithChar:'*'];
    }
    else if ((c == ';') || (c == '|')) {
      if (cb2 == nil) {
        cb2 = [[RAREUTCharArray alloc] init];
      }
      else {
        (void) [cb2 appendWithChar:'|'];
      }
      (void) [((RAREUTCharArray *) nil_chk(cb2)) appendWithCharArray:RAREUTRegularExpressionFilter_patternGroup_];
      (void) [cb2 appendWithRAREUTCharArray:cb];
      (void) [cb2 appendWithChar:')'];
      cb->_length_ = 0;
    }
    else if ([((NSString *) nil_chk(RAREUTRegularExpressionFilter_regexpEscapeChars_)) indexOf:c] != -1) {
      (void) [cb appendWithChar:'\\'];
      (void) [cb appendWithChar:c];
    }
    else {
      (void) [cb appendWithChar:c];
    }
  }
  if (cb2 != nil) {
    if (cb->_length_ > 0) {
      (void) [cb2 appendWithChar:'|'];
      (void) [cb2 appendWithCharArray:RAREUTRegularExpressionFilter_patternGroup_];
      (void) [cb2 appendWithRAREUTCharArray:cb];
      (void) [cb2 appendWithChar:')'];
    }
    cb = cb2;
  }
  return [cb description];
}

- (BOOL)passesWithId:(id)value
withRAREUTiStringConverter:(id<RAREUTiStringConverter>)converter {
  if ((converter != nil) && (value != nil)) {
    value = [converter toStringWithId:value];
  }
  if (value == nil) {
    return nullPasses_;
  }
  NSString *s = ([value isKindOfClass:[NSString class]]) ? (NSString *) check_class_cast(value, [NSString class]) : [nil_chk(value) description];
  if (emptyPasses_ && ([s sequenceLength] == 0)) {
    return YES;
  }
  return (value != nil) && [((JavaUtilRegexMatcher *) nil_chk([((JavaUtilRegexPattern *) nil_chk(thePattern_)) matcherWithJavaLangCharSequence:s])) matches];
}

- (void)setEmptyStringPassesWithBoolean:(BOOL)passes {
  emptyPasses_ = passes;
}

- (void)setNullPassesWithBoolean:(BOOL)passes {
  nullPasses_ = passes;
}

- (BOOL)isEmptyStringPasses {
  return emptyPasses_;
}

- (BOOL)isNullPasses {
  return nullPasses_;
}

+ (NSString *)parseStringFilterWithCharArray:(IOSCharArray *)chars
                                     withInt:(int)pos
                                     withInt:(int)len
                                 withBoolean:(BOOL)wholeword {
  RAREUTCharArray *cb = [[RAREUTCharArray alloc] init];
  len += pos;
  unichar c;
  if (wholeword) {
    (void) [cb appendWithNSString:@"\\b"];
  }
  while (pos < len) {
    c = IOSCharArray_Get(nil_chk(chars), pos++);
    if ([((NSString *) nil_chk(RAREUTRegularExpressionFilter_regexpEscapeChars_)) indexOf:c] != -1) {
      (void) [cb appendWithChar:'\\'];
      (void) [cb appendWithChar:c];
    }
    else {
      (void) [cb appendWithChar:c];
    }
  }
  if (wholeword) {
    (void) [cb appendWithNSString:@"\\b"];
  }
  return [cb description];
}

+ (void)initialize {
  if (self == [RAREUTRegularExpressionFilter class]) {
    RAREUTRegularExpressionFilter_patternGroup_ = [@"(?:" toCharArray];
  }
}

- (void)copyAllFieldsTo:(RAREUTRegularExpressionFilter *)other {
  [super copyAllFieldsTo:other];
  other->emptyPasses_ = emptyPasses_;
  other->nullPasses_ = nullPasses_;
  other->thePattern_ = thePattern_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "createFilterWithNSString:", NULL, "LRAREUTRegularExpressionFilter", 0x9, NULL },
    { "createPatternWithNSString:", NULL, "LJavaUtilRegexPattern", 0x9, NULL },
    { "parseStringFilterWithNSString:withBoolean:", NULL, "LNSString", 0x9, NULL },
    { "parseWildcardFilterWithNSString:", NULL, "LNSString", 0x9, NULL },
    { "parseWildcardFilterWithCharArray:withInt:withInt:", NULL, "LNSString", 0x9, NULL },
    { "passesWithId:withRAREUTiStringConverter:", NULL, "Z", 0x1, NULL },
    { "isEmptyStringPasses", NULL, "Z", 0x1, NULL },
    { "isNullPasses", NULL, "Z", 0x1, NULL },
    { "parseStringFilterWithCharArray:withInt:withInt:withBoolean:", NULL, "LNSString", 0xa, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "regexpEscapeChars_", NULL, 0x18, "LNSString" },
    { "patternGroup_", NULL, 0x18, "LIOSCharArray" },
    { "emptyPasses_", NULL, 0x0, "Z" },
    { "nullPasses_", NULL, 0x0, "Z" },
    { "thePattern_", NULL, 0x0, "LJavaUtilRegexPattern" },
  };
  static J2ObjcClassInfo _RAREUTRegularExpressionFilter = { "RegularExpressionFilter", "com.appnativa.util", NULL, 0x1, 9, methods, 5, fields, 0, NULL};
  return &_RAREUTRegularExpressionFilter;
}

@end
