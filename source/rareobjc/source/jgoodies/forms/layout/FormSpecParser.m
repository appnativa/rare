//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/jgoodies/forms/layout/FormSpecParser.java
//
//  Created by decoteaud on 9/15/15.
//

#include "IOSClass.h"
#include "IOSObjectArray.h"
#include "com/jgoodies/forms/layout/ColumnSpec.h"
#include "com/jgoodies/forms/layout/FormSpecParser.h"
#include "com/jgoodies/forms/layout/LayoutMap.h"
#include "com/jgoodies/forms/layout/RowSpec.h"
#include "com/jgoodies/forms/util/FormUtils.h"
#include "java/lang/Character.h"
#include "java/lang/Integer.h"
#include "java/lang/NumberFormatException.h"
#include "java/lang/StringBuffer.h"
#include "java/lang/Throwable.h"
#include "java/util/ArrayList.h"
#include "java/util/List.h"
#include "java/util/regex/Matcher.h"
#include "java/util/regex/Pattern.h"

@implementation RAREJGFormSpecParser

static JavaUtilRegexPattern * RAREJGFormSpecParser_MULTIPLIER_PREFIX_PATTERN_;
static JavaUtilRegexPattern * RAREJGFormSpecParser_DIGIT_PATTERN_;

+ (JavaUtilRegexPattern *)MULTIPLIER_PREFIX_PATTERN {
  return RAREJGFormSpecParser_MULTIPLIER_PREFIX_PATTERN_;
}

+ (JavaUtilRegexPattern *)DIGIT_PATTERN {
  return RAREJGFormSpecParser_DIGIT_PATTERN_;
}

- (id)initWithNSString:(NSString *)source
          withNSString:(NSString *)description_
   withRAREJGLayoutMap:(RAREJGLayoutMap *)layoutMap
           withBoolean:(BOOL)horizontal {
  if (self = [super init]) {
    [RAREJGFormUtils assertNotNullWithId:source withNSString:description_];
    [RAREJGFormUtils assertNotNullWithId:layoutMap withNSString:@"LayoutMap"];
    self->layoutMap_ = layoutMap;
    self->source_ = [((RAREJGLayoutMap *) nil_chk(self->layoutMap_)) expandWithNSString:source withBoolean:horizontal];
  }
  return self;
}

+ (IOSObjectArray *)parseColumnSpecsWithNSString:(NSString *)encodedColumnSpecs
                             withRAREJGLayoutMap:(RAREJGLayoutMap *)layoutMap {
  RAREJGFormSpecParser *parser = [[RAREJGFormSpecParser alloc] initWithNSString:encodedColumnSpecs withNSString:@"encoded column specifications" withRAREJGLayoutMap:layoutMap withBoolean:YES];
  return [parser parseColumnSpecs];
}

+ (IOSObjectArray *)parseRowSpecsWithNSString:(NSString *)encodedRowSpecs
                          withRAREJGLayoutMap:(RAREJGLayoutMap *)layoutMap {
  RAREJGFormSpecParser *parser = [[RAREJGFormSpecParser alloc] initWithNSString:encodedRowSpecs withNSString:@"encoded column specifications" withRAREJGLayoutMap:layoutMap withBoolean:NO];
  return [parser parseRowSpecs];
}

- (IOSObjectArray *)parseColumnSpecs {
  id<JavaUtilList> encodedColumnSpecs = [self splitWithNSString:source_ withInt:0];
  int columnCount = [((id<JavaUtilList>) nil_chk(encodedColumnSpecs)) size];
  IOSObjectArray *columnSpecs = [IOSObjectArray arrayWithLength:columnCount type:[IOSClass classWithClass:[RAREJGColumnSpec class]]];
  for (int i = 0; i < columnCount; i++) {
    NSString *encodedSpec = (NSString *) check_class_cast([encodedColumnSpecs getWithInt:i], [NSString class]);
    (void) IOSObjectArray_Set(columnSpecs, i, [RAREJGColumnSpec decodeExpandedWithNSString:encodedSpec]);
  }
  return columnSpecs;
}

- (IOSObjectArray *)parseRowSpecs {
  id<JavaUtilList> encodedRowSpecs = [self splitWithNSString:source_ withInt:0];
  int rowCount = [((id<JavaUtilList>) nil_chk(encodedRowSpecs)) size];
  IOSObjectArray *rowSpecs = [IOSObjectArray arrayWithLength:rowCount type:[IOSClass classWithClass:[RAREJGRowSpec class]]];
  for (int i = 0; i < rowCount; i++) {
    NSString *encodedSpec = (NSString *) check_class_cast([encodedRowSpecs getWithInt:i], [NSString class]);
    (void) IOSObjectArray_Set(rowSpecs, i, [RAREJGRowSpec decodeExpandedWithNSString:encodedSpec]);
  }
  return rowSpecs;
}

- (id<JavaUtilList>)splitWithNSString:(NSString *)expression
                              withInt:(int)offset {
  id<JavaUtilList> encodedSpecs = [[JavaUtilArrayList alloc] init];
  int parenthesisLevel = 0;
  int bracketLevel = 0;
  int quoteLevel = 0;
  int length = [((NSString *) nil_chk(expression)) sequenceLength];
  int specStart = 0;
  unichar c;
  BOOL lead = YES;
  for (int i = 0; i < length; i++) {
    c = [expression charAtWithInt:i];
    if (lead && [JavaLangCharacter isWhitespaceWithChar:c]) {
      specStart++;
      continue;
    }
    lead = NO;
    if (c == ',' && parenthesisLevel == 0 && bracketLevel == 0 && quoteLevel == 0) {
      NSString *token = [expression substring:specStart endIndex:i];
      [self addSpecWithJavaUtilList:encodedSpecs withNSString:token withInt:offset + specStart];
      specStart = i + 1;
      lead = YES;
    }
    else if (c == '(') {
      if (bracketLevel > 0) {
        [self failWithInt:offset + i withNSString:@"illegal '(' in [...]"];
      }
      parenthesisLevel++;
    }
    else if (c == ')') {
      if (bracketLevel > 0) {
        [self failWithInt:offset + i withNSString:@"illegal ')' in [...]"];
      }
      parenthesisLevel--;
      if (parenthesisLevel < 0) {
        [self failWithInt:offset + i withNSString:@"missing '('"];
      }
    }
    else if (c == '[') {
      if (bracketLevel > 0) {
        [self failWithInt:offset + i withNSString:@"too many '['"];
      }
      bracketLevel++;
    }
    else if (c == ']') {
      bracketLevel--;
      if (bracketLevel < 0) {
        [self failWithInt:offset + i withNSString:@"missing '['"];
      }
    }
    else if (c == '\'') {
      if (quoteLevel == 0) {
        quoteLevel++;
      }
      else if (quoteLevel == 1) {
        quoteLevel--;
      }
    }
  }
  if (parenthesisLevel > 0) {
    [self failWithInt:offset + length withNSString:@"missing ')'"];
  }
  if (bracketLevel > 0) {
    [self failWithInt:offset + length withNSString:@"missing ']"];
  }
  if (specStart < length) {
    NSString *token = [expression substring:specStart];
    [self addSpecWithJavaUtilList:encodedSpecs withNSString:token withInt:offset + specStart];
  }
  return encodedSpecs;
}

- (void)addSpecWithJavaUtilList:(id<JavaUtilList>)encodedSpecs
                   withNSString:(NSString *)expression
                        withInt:(int)offset {
  NSString *trimmedExpression = [((NSString *) nil_chk(expression)) trim];
  RAREJGFormSpecParser_Multiplier *multiplier = [self multiplierWithNSString:trimmedExpression withInt:offset];
  if (multiplier == nil) {
    [((id<JavaUtilList>) nil_chk(encodedSpecs)) addWithId:trimmedExpression];
    return;
  }
  id<JavaUtilList> subTokenList = [self splitWithNSString:((RAREJGFormSpecParser_Multiplier *) nil_chk(multiplier))->expression_ withInt:offset + multiplier->offset_];
  for (int i = 0; i < multiplier->multiplier_; i++) {
    [((id<JavaUtilList>) nil_chk(encodedSpecs)) addAllWithJavaUtilCollection:subTokenList];
  }
}

- (RAREJGFormSpecParser_Multiplier *)multiplierWithNSString:(NSString *)expression
                                                    withInt:(int)offset {
  JavaUtilRegexMatcher *matcher = [((JavaUtilRegexPattern *) nil_chk(RAREJGFormSpecParser_MULTIPLIER_PREFIX_PATTERN_)) matcherWithJavaLangCharSequence:expression];
  if (![((JavaUtilRegexMatcher *) nil_chk(matcher)) find]) {
    return nil;
  }
  if ([matcher start] > 0) {
    [self failWithInt:offset + [matcher start] withNSString:@"illegal multiplier position"];
  }
  JavaUtilRegexMatcher *digitMatcher = [((JavaUtilRegexPattern *) nil_chk(RAREJGFormSpecParser_DIGIT_PATTERN_)) matcherWithJavaLangCharSequence:expression];
  if (![((JavaUtilRegexMatcher *) nil_chk(digitMatcher)) find]) {
    return nil;
  }
  NSString *digitStr = [((NSString *) nil_chk(expression)) substring:0 endIndex:[digitMatcher end]];
  int number = 0;
  @try {
    number = [JavaLangInteger parseIntWithNSString:digitStr];
  }
  @catch (JavaLangNumberFormatException *e) {
    [self failWithInt:offset withJavaLangNumberFormatException:e];
  }
  if (number <= 0) {
    [self failWithInt:offset withNSString:@"illegal 0 multiplier"];
  }
  NSString *subexpression = [expression substring:[matcher end] endIndex:[expression sequenceLength] - 1];
  return [[RAREJGFormSpecParser_Multiplier alloc] initWithInt:number withNSString:subexpression withInt:[matcher end]];
}

+ (void)failWithNSString:(NSString *)source
                 withInt:(int)index
            withNSString:(NSString *)description_ {
  @throw [[RAREJGFormSpecParser_FormLayoutParseException alloc] initWithNSString:[RAREJGFormSpecParser messageWithNSString:source withInt:index withNSString:description_]];
}

- (void)failWithInt:(int)index
       withNSString:(NSString *)description_ {
  @throw [[RAREJGFormSpecParser_FormLayoutParseException alloc] initWithNSString:[RAREJGFormSpecParser messageWithNSString:source_ withInt:index withNSString:description_]];
}

- (void)failWithInt:(int)index
withJavaLangNumberFormatException:(JavaLangNumberFormatException *)cause {
  @throw [[RAREJGFormSpecParser_FormLayoutParseException alloc] initWithNSString:[RAREJGFormSpecParser messageWithNSString:source_ withInt:index withNSString:@"Invalid multiplier"] withJavaLangThrowable:cause];
}

+ (NSString *)messageWithNSString:(NSString *)source
                          withInt:(int)index
                     withNSString:(NSString *)description_ {
  JavaLangStringBuffer *buffer = [[JavaLangStringBuffer alloc] initWithInt:0x000a];
  (void) [buffer appendWithChar:0x000a];
  (void) [buffer appendWithNSString:source];
  (void) [buffer appendWithChar:0x000a];
  for (int i = 0; i < index; i++) {
    (void) [buffer appendWithChar:' '];
  }
  (void) [buffer appendWithChar:'^'];
  (void) [buffer appendWithNSString:description_];
  NSString *message = [buffer description];
  @throw [[RAREJGFormSpecParser_FormLayoutParseException alloc] initWithNSString:message];
}

+ (void)initialize {
  if (self == [RAREJGFormSpecParser class]) {
    RAREJGFormSpecParser_MULTIPLIER_PREFIX_PATTERN_ = [JavaUtilRegexPattern compileWithNSString:@"\\d+\\s*\\*\\s*\\("];
    RAREJGFormSpecParser_DIGIT_PATTERN_ = [JavaUtilRegexPattern compileWithNSString:@"\\d+"];
  }
}

- (void)copyAllFieldsTo:(RAREJGFormSpecParser *)other {
  [super copyAllFieldsTo:other];
  other->layoutMap_ = layoutMap_;
  other->source_ = source_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "initWithNSString:withNSString:withRAREJGLayoutMap:withBoolean:", NULL, NULL, 0x2, NULL },
    { "parseColumnSpecsWithNSString:withRAREJGLayoutMap:", NULL, "LIOSObjectArray", 0x8, NULL },
    { "parseRowSpecsWithNSString:withRAREJGLayoutMap:", NULL, "LIOSObjectArray", 0x8, NULL },
    { "parseColumnSpecs", NULL, "LIOSObjectArray", 0x2, NULL },
    { "parseRowSpecs", NULL, "LIOSObjectArray", 0x2, NULL },
    { "splitWithNSString:withInt:", NULL, "LJavaUtilList", 0x2, NULL },
    { "addSpecWithJavaUtilList:withNSString:withInt:", NULL, "V", 0x2, NULL },
    { "multiplierWithNSString:withInt:", NULL, "LRAREJGFormSpecParser_Multiplier", 0x2, NULL },
    { "failWithInt:withNSString:", NULL, "V", 0x2, NULL },
    { "failWithInt:withJavaLangNumberFormatException:", NULL, "V", 0x2, NULL },
    { "messageWithNSString:withInt:withNSString:", NULL, "LNSString", 0xa, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "MULTIPLIER_PREFIX_PATTERN_", NULL, 0x1a, "LJavaUtilRegexPattern" },
    { "DIGIT_PATTERN_", NULL, 0x1a, "LJavaUtilRegexPattern" },
    { "source_", NULL, 0x12, "LNSString" },
    { "layoutMap_", NULL, 0x12, "LRAREJGLayoutMap" },
  };
  static J2ObjcClassInfo _RAREJGFormSpecParser = { "FormSpecParser", "com.jgoodies.forms.layout", NULL, 0x11, 11, methods, 4, fields, 0, NULL};
  return &_RAREJGFormSpecParser;
}

@end
@implementation RAREJGFormSpecParser_FormLayoutParseException

- (id)initWithNSString:(NSString *)message {
  return [super initWithNSString:message];
}

- (id)initWithNSString:(NSString *)message
 withJavaLangThrowable:(JavaLangThrowable *)cause {
  return [super initWithNSString:message withJavaLangThrowable:cause];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "initWithNSString:", NULL, NULL, 0x0, NULL },
    { "initWithNSString:withJavaLangThrowable:", NULL, NULL, 0x0, NULL },
  };
  static J2ObjcClassInfo _RAREJGFormSpecParser_FormLayoutParseException = { "FormLayoutParseException", "com.jgoodies.forms.layout", "FormSpecParser", 0x19, 2, methods, 0, NULL, 0, NULL};
  return &_RAREJGFormSpecParser_FormLayoutParseException;
}

@end
@implementation RAREJGFormSpecParser_Multiplier

- (id)initWithInt:(int)multiplier
     withNSString:(NSString *)expression
          withInt:(int)offset {
  if (self = [super init]) {
    self->multiplier_ = multiplier;
    self->expression_ = expression;
    self->offset_ = offset;
  }
  return self;
}

- (void)copyAllFieldsTo:(RAREJGFormSpecParser_Multiplier *)other {
  [super copyAllFieldsTo:other];
  other->expression_ = expression_;
  other->multiplier_ = multiplier_;
  other->offset_ = offset_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "initWithInt:withNSString:withInt:", NULL, NULL, 0x0, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "multiplier_", NULL, 0x10, "I" },
    { "expression_", NULL, 0x10, "LNSString" },
    { "offset_", NULL, 0x10, "I" },
  };
  static J2ObjcClassInfo _RAREJGFormSpecParser_Multiplier = { "Multiplier", "com.jgoodies.forms.layout", "FormSpecParser", 0x18, 1, methods, 3, fields, 0, NULL};
  return &_RAREJGFormSpecParser_Multiplier;
}

@end
