//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/jgoodies/forms/layout/FormSpec.java
//
//  Created by decoteaud on 12/8/15.
//

#include "IOSClass.h"
#include "IOSObjectArray.h"
#include "com/appnativa/rare/ui/iParentComponent.h"
#include "com/jgoodies/forms/layout/BoundedSize.h"
#include "com/jgoodies/forms/layout/ColumnSpec.h"
#include "com/jgoodies/forms/layout/ConstantSize.h"
#include "com/jgoodies/forms/layout/FormLayout.h"
#include "com/jgoodies/forms/layout/FormSpec.h"
#include "com/jgoodies/forms/layout/PrototypeSize.h"
#include "com/jgoodies/forms/layout/RowSpec.h"
#include "com/jgoodies/forms/layout/Size.h"
#include "com/jgoodies/forms/layout/Sizes.h"
#include "com/jgoodies/forms/util/FormUtils.h"
#include "java/lang/Double.h"
#include "java/lang/IllegalArgumentException.h"
#include "java/lang/NullPointerException.h"
#include "java/lang/StringBuffer.h"
#include "java/util/List.h"
#include "java/util/Locale.h"
#include "java/util/regex/Pattern.h"

@implementation RAREJGFormSpec

static RAREJGFormSpec_DefaultAlignment * RAREJGFormSpec_LEFT_ALIGN_;
static RAREJGFormSpec_DefaultAlignment * RAREJGFormSpec_RIGHT_ALIGN_;
static RAREJGFormSpec_DefaultAlignment * RAREJGFormSpec_TOP_ALIGN_;
static RAREJGFormSpec_DefaultAlignment * RAREJGFormSpec_BOTTOM_ALIGN_;
static RAREJGFormSpec_DefaultAlignment * RAREJGFormSpec_CENTER_ALIGN_;
static RAREJGFormSpec_DefaultAlignment * RAREJGFormSpec_FILL_ALIGN_;
static IOSObjectArray * RAREJGFormSpec_VALUES_;
static JavaUtilRegexPattern * RAREJGFormSpec_TOKEN_SEPARATOR_PATTERN_;
static JavaUtilRegexPattern * RAREJGFormSpec_BOUNDS_SEPARATOR_PATTERN_;

+ (RAREJGFormSpec_DefaultAlignment *)LEFT_ALIGN {
  return RAREJGFormSpec_LEFT_ALIGN_;
}

+ (RAREJGFormSpec_DefaultAlignment *)RIGHT_ALIGN {
  return RAREJGFormSpec_RIGHT_ALIGN_;
}

+ (RAREJGFormSpec_DefaultAlignment *)TOP_ALIGN {
  return RAREJGFormSpec_TOP_ALIGN_;
}

+ (RAREJGFormSpec_DefaultAlignment *)BOTTOM_ALIGN {
  return RAREJGFormSpec_BOTTOM_ALIGN_;
}

+ (RAREJGFormSpec_DefaultAlignment *)CENTER_ALIGN {
  return RAREJGFormSpec_CENTER_ALIGN_;
}

+ (RAREJGFormSpec_DefaultAlignment *)FILL_ALIGN {
  return RAREJGFormSpec_FILL_ALIGN_;
}

+ (IOSObjectArray *)VALUES {
  return RAREJGFormSpec_VALUES_;
}

+ (double)NO_GROW {
  return RAREJGFormSpec_NO_GROW;
}

+ (double)DEFAULT_GROW {
  return RAREJGFormSpec_DEFAULT_GROW;
}

+ (JavaUtilRegexPattern *)TOKEN_SEPARATOR_PATTERN {
  return RAREJGFormSpec_TOKEN_SEPARATOR_PATTERN_;
}

+ (JavaUtilRegexPattern *)BOUNDS_SEPARATOR_PATTERN {
  return RAREJGFormSpec_BOUNDS_SEPARATOR_PATTERN_;
}

- (id)initRAREJGFormSpecWithRAREJGFormSpec_DefaultAlignment:(RAREJGFormSpec_DefaultAlignment *)defaultAlignment
                                             withRAREJGSize:(id<RAREJGSize>)size
                                                 withDouble:(double)resizeWeight {
  if (self = [super init]) {
    if (size == nil) @throw [[JavaLangNullPointerException alloc] initWithNSString:@"The size must not be null."];
    self->defaultAlignment_ = defaultAlignment;
    self->size_ = size;
    self->resizeWeight_ = resizeWeight;
    if (resizeWeight < 0) @throw [[JavaLangIllegalArgumentException alloc] initWithNSString:@"The resize weight must be non-negative."];
  }
  return self;
}

- (id)initWithRAREJGFormSpec_DefaultAlignment:(RAREJGFormSpec_DefaultAlignment *)defaultAlignment
                               withRAREJGSize:(id<RAREJGSize>)size
                                   withDouble:(double)resizeWeight {
  return [self initRAREJGFormSpecWithRAREJGFormSpec_DefaultAlignment:defaultAlignment withRAREJGSize:size withDouble:resizeWeight];
}

- (id)initWithRAREJGFormSpec_DefaultAlignment:(RAREJGFormSpec_DefaultAlignment *)defaultAlignment
                                 withNSString:(NSString *)encodedDescription {
  if (self = [self initRAREJGFormSpecWithRAREJGFormSpec_DefaultAlignment:defaultAlignment withRAREJGSize:[RAREJGSizes DEFAULT] withDouble:RAREJGFormSpec_NO_GROW]) {
    [self parseAndInitValuesWithNSString:[((NSString *) nil_chk(encodedDescription)) lowercaseStringWithJRELocale:[JavaUtilLocale ENGLISH]]];
  }
  return self;
}

- (RAREJGFormSpec_DefaultAlignment *)getDefaultAlignment {
  return defaultAlignment_;
}

- (id<RAREJGSize>)getSize {
  return size_;
}

- (double)getResizeWeight {
  return resizeWeight_;
}

- (BOOL)canGrow {
  return [self getResizeWeight] != RAREJGFormSpec_NO_GROW;
}

- (BOOL)isHorizontal {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
  return 0;
}

- (void)setDefaultAlignmentWithRAREJGFormSpec_DefaultAlignment:(RAREJGFormSpec_DefaultAlignment *)defaultAlignment {
  self->defaultAlignment_ = defaultAlignment;
}

- (void)setSizeWithRAREJGSize:(id<RAREJGSize>)size {
  self->size_ = size;
}

- (void)setResizeWeightWithDouble:(double)resizeWeight {
  self->resizeWeight_ = resizeWeight;
}

- (void)parseAndInitValuesWithNSString:(NSString *)encodedDescription {
  [RAREJGFormUtils assertNotBlankWithNSString:encodedDescription withNSString:@"encoded form specification"];
  IOSObjectArray *token = [((JavaUtilRegexPattern *) nil_chk(RAREJGFormSpec_TOKEN_SEPARATOR_PATTERN_)) splitWithJavaLangCharSequence:encodedDescription];
  if ((int) [((IOSObjectArray *) nil_chk(token)) count] == 0) {
    @throw [[JavaLangIllegalArgumentException alloc] initWithNSString:@"The form spec must not be empty."];
  }
  int nextIndex = 0;
  NSString *next = IOSObjectArray_Get(token, nextIndex++);
  RAREJGFormSpec_DefaultAlignment *alignment = [RAREJGFormSpec_DefaultAlignment valueOfWithNSString:next withBoolean:[self isHorizontal]];
  if (alignment != nil) {
    [self setDefaultAlignmentWithRAREJGFormSpec_DefaultAlignment:alignment];
    if ((int) [token count] == 1) {
      @throw [[JavaLangIllegalArgumentException alloc] initWithNSString:@"The form spec must provide a size."];
    }
    next = IOSObjectArray_Get(token, nextIndex++);
  }
  [self setSizeWithRAREJGSize:[self parseSizeWithNSString:next]];
  if (nextIndex < (int) [token count]) {
    [self setResizeWeightWithDouble:[RAREJGFormSpec parseResizeWeightWithNSString:IOSObjectArray_Get(token, nextIndex)]];
  }
}

- (id<RAREJGSize>)parseSizeWithNSString:(NSString *)token {
  if ([((NSString *) nil_chk(token)) hasPrefix:@"["] && [token hasSuffix:@"]"]) {
    return [self parseBoundedSizeWithNSString:token];
  }
  if ([token hasPrefix:@"max("] && [token hasSuffix:@")"]) {
    return [self parseOldBoundedSizeWithNSString:token withBoolean:NO];
  }
  if ([token hasPrefix:@"min("] && [token hasSuffix:@")"]) {
    return [self parseOldBoundedSizeWithNSString:token withBoolean:YES];
  }
  return [self parseAtomicSizeWithNSString:token];
}

- (id<RAREJGSize>)parseBoundedSizeWithNSString:(NSString *)token {
  NSString *content = [token substring:1 endIndex:[((NSString *) nil_chk(token)) sequenceLength] - 1];
  IOSObjectArray *subtoken = [((JavaUtilRegexPattern *) nil_chk(RAREJGFormSpec_BOUNDS_SEPARATOR_PATTERN_)) splitWithJavaLangCharSequence:content];
  id<RAREJGSize> basis = nil;
  id<RAREJGSize> lower = nil;
  id<RAREJGSize> upper = nil;
  if ((int) [((IOSObjectArray *) nil_chk(subtoken)) count] == 2) {
    id<RAREJGSize> size1 = [self parseAtomicSizeWithNSString:IOSObjectArray_Get(subtoken, 0)];
    id<RAREJGSize> size2 = [self parseAtomicSizeWithNSString:IOSObjectArray_Get(subtoken, 1)];
    if ([RAREJGFormSpec isConstantWithRAREJGSize:size1]) {
      if ([RAREJGFormSpec isConstantWithRAREJGSize:size2]) {
        lower = size1;
        basis = size2;
        upper = size2;
      }
      else {
        lower = size1;
        basis = size2;
      }
    }
    else {
      basis = size1;
      upper = size2;
    }
  }
  else if ((int) [subtoken count] == 3) {
    lower = [self parseAtomicSizeWithNSString:IOSObjectArray_Get(subtoken, 0)];
    basis = [self parseAtomicSizeWithNSString:IOSObjectArray_Get(subtoken, 1)];
    upper = [self parseAtomicSizeWithNSString:IOSObjectArray_Get(subtoken, 2)];
  }
  if (((lower == nil) || ([RAREJGFormSpec isConstantWithRAREJGSize:lower])) && ((upper == nil) || ([RAREJGFormSpec isConstantWithRAREJGSize:upper]))) {
    return [[RAREJGBoundedSize alloc] initWithRAREJGSize:basis withRAREJGSize:lower withRAREJGSize:upper];
  }
  @throw [[JavaLangIllegalArgumentException alloc] initWithNSString:[NSString stringWithFormat:@"Illegal bounded size '%@'. Must be one of:\n[<constant size>,<logical size>]                 // lower bound\n[<logical size>,<constant size>]                 // upper bound\n[<constant size>,<logical size>,<constant size>] // lower and upper bound.\nExamples:\n[50dlu,pref]                                     // lower bound\n[pref,200dlu]                                    // upper bound\n[50dlu,pref,200dlu]                              // lower and upper bound.", token]];
}

- (id<RAREJGSize>)parseOldBoundedSizeWithNSString:(NSString *)token
                                      withBoolean:(BOOL)setMax {
  int semicolonIndex = [((NSString *) nil_chk(token)) indexOf:';'];
  NSString *sizeToken1 = [token substring:4 endIndex:semicolonIndex];
  NSString *sizeToken2 = [token substring:semicolonIndex + 1 endIndex:[token sequenceLength] - 1];
  id<RAREJGSize> size1 = [self parseAtomicSizeWithNSString:sizeToken1];
  id<RAREJGSize> size2 = [self parseAtomicSizeWithNSString:sizeToken2];
  if ([RAREJGFormSpec isConstantWithRAREJGSize:size1]) {
    if ([(id) size2 isKindOfClass:[RAREJGSizes_ComponentSize class]]) {
      return [[RAREJGBoundedSize alloc] initWithRAREJGSize:size2 withRAREJGSize:setMax ? nil : size1 withRAREJGSize:setMax ? size1 : nil];
    }
    @throw [[JavaLangIllegalArgumentException alloc] initWithNSString:@"Bounded sizes must not be both constants."];
  }
  if ([RAREJGFormSpec isConstantWithRAREJGSize:size2]) {
    return [[RAREJGBoundedSize alloc] initWithRAREJGSize:size1 withRAREJGSize:setMax ? nil : size2 withRAREJGSize:setMax ? size2 : nil];
  }
  @throw [[JavaLangIllegalArgumentException alloc] initWithNSString:@"Bounded sizes must not be both logical."];
}

- (id<RAREJGSize>)parseAtomicSizeWithNSString:(NSString *)token {
  NSString *trimmedToken = [((NSString *) nil_chk(token)) trim];
  if ([((NSString *) nil_chk(trimmedToken)) hasPrefix:@"'"] && [trimmedToken hasSuffix:@"'"]) {
    int length = [trimmedToken sequenceLength];
    if (length < 2) {
      @throw [[JavaLangIllegalArgumentException alloc] initWithNSString:@"Missing closing \"'\" for prototype."];
    }
    return [[RAREJGPrototypeSize alloc] initWithNSString:[trimmedToken substring:1 endIndex:length - 1]];
  }
  RAREJGSizes_ComponentSize *componentSize = [RAREJGSizes_ComponentSize valueOfWithNSString:trimmedToken];
  if (componentSize != nil) return componentSize;
  return [RAREJGConstantSize valueOfWithNSString:trimmedToken withBoolean:[self isHorizontal]];
}

+ (double)parseResizeWeightWithNSString:(NSString *)token {
  if ([((NSString *) nil_chk(token)) isEqual:@"g"] || [token isEqual:@"grow"]) {
    return RAREJGFormSpec_DEFAULT_GROW;
  }
  if ([token isEqual:@"n"] || [token isEqual:@"nogrow"] || [token isEqual:@"none"]) {
    return RAREJGFormSpec_NO_GROW;
  }
  if (([token hasPrefix:@"grow("] || [token hasPrefix:@"g("]) && [token hasSuffix:@")"]) {
    int leftParen = [token indexOf:'('];
    int rightParen = [token indexOf:')'];
    NSString *substring = [token substring:leftParen + 1 endIndex:rightParen];
    return [JavaLangDouble parseDoubleWithNSString:substring];
  }
  @throw [[JavaLangIllegalArgumentException alloc] initWithNSString:[NSString stringWithFormat:@"The resize argument '%@' is invalid.  Must be one of: grow, g, none, n, grow(<double>), g(<double>)", token]];
}

+ (BOOL)isConstantWithRAREJGSize:(id<RAREJGSize>)aSize {
  return ([(id) aSize isKindOfClass:[RAREJGConstantSize class]]) || ([(id) aSize isKindOfClass:[RAREJGPrototypeSize class]]);
}

- (NSString *)description {
  JavaLangStringBuffer *buffer = [[JavaLangStringBuffer alloc] init];
  (void) [buffer appendWithId:defaultAlignment_];
  (void) [buffer appendWithNSString:@":"];
  (void) [buffer appendWithNSString:[((id<RAREJGSize>) nil_chk(size_)) description]];
  (void) [buffer appendWithChar:':'];
  if (resizeWeight_ == RAREJGFormSpec_NO_GROW) {
    (void) [buffer appendWithNSString:@"noGrow"];
  }
  else if (resizeWeight_ == RAREJGFormSpec_DEFAULT_GROW) {
    (void) [buffer appendWithNSString:@"grow"];
  }
  else {
    (void) [buffer appendWithNSString:@"grow("];
    (void) [buffer appendWithDouble:resizeWeight_];
    (void) [buffer appendWithChar:')'];
  }
  return [buffer description];
}

- (NSString *)toShortString {
  JavaLangStringBuffer *buffer = [[JavaLangStringBuffer alloc] init];
  (void) [buffer appendWithChar:[((RAREJGFormSpec_DefaultAlignment *) nil_chk(defaultAlignment_)) abbreviation]];
  (void) [buffer appendWithNSString:@":"];
  (void) [buffer appendWithNSString:[((id<RAREJGSize>) nil_chk(size_)) description]];
  (void) [buffer appendWithChar:':'];
  if (resizeWeight_ == RAREJGFormSpec_NO_GROW) {
    (void) [buffer appendWithNSString:@"n"];
  }
  else if (resizeWeight_ == RAREJGFormSpec_DEFAULT_GROW) {
    (void) [buffer appendWithNSString:@"g"];
  }
  else {
    (void) [buffer appendWithNSString:@"g("];
    (void) [buffer appendWithDouble:resizeWeight_];
    (void) [buffer appendWithChar:')'];
  }
  return [buffer description];
}

- (NSString *)encode {
  JavaLangStringBuffer *buffer = [[JavaLangStringBuffer alloc] init];
  RAREJGFormSpec_DefaultAlignment *alignmentDefault = [self isHorizontal] ? [RAREJGColumnSpec DEFAULT] : [RAREJGRowSpec DEFAULT];
  if (![alignmentDefault isEqual:defaultAlignment_]) {
    (void) [buffer appendWithChar:[((RAREJGFormSpec_DefaultAlignment *) nil_chk(defaultAlignment_)) abbreviation]];
    (void) [buffer appendWithNSString:@":"];
  }
  (void) [buffer appendWithNSString:[((id<RAREJGSize>) nil_chk(size_)) encode]];
  if (resizeWeight_ == RAREJGFormSpec_NO_GROW) {
  }
  else if (resizeWeight_ == RAREJGFormSpec_DEFAULT_GROW) {
    (void) [buffer appendWithChar:':'];
    (void) [buffer appendWithNSString:@"g"];
  }
  else {
    (void) [buffer appendWithChar:':'];
    (void) [buffer appendWithNSString:@"g("];
    (void) [buffer appendWithDouble:resizeWeight_];
    (void) [buffer appendWithChar:')'];
  }
  return [buffer description];
}

- (NSString *)encodeEx {
  JavaLangStringBuffer *buffer = [[JavaLangStringBuffer alloc] init];
  RAREJGFormSpec_DefaultAlignment *alignmentDefault = [self isHorizontal] ? [RAREJGColumnSpec DEFAULT] : [RAREJGRowSpec DEFAULT];
  if (![alignmentDefault isEqual:defaultAlignment_]) {
    (void) [buffer appendWithChar:[((RAREJGFormSpec_DefaultAlignment *) nil_chk(defaultAlignment_)) abbreviation]];
    (void) [buffer appendWithNSString:@":"];
  }
  (void) [buffer appendWithNSString:[((id<RAREJGSize>) nil_chk(size_)) encodeEx]];
  if (resizeWeight_ == RAREJGFormSpec_NO_GROW) {
  }
  else if (resizeWeight_ == RAREJGFormSpec_DEFAULT_GROW) {
    (void) [buffer appendWithChar:':'];
    (void) [buffer appendWithNSString:@"g"];
  }
  else {
    (void) [buffer appendWithChar:':'];
    (void) [buffer appendWithNSString:@"g("];
    (void) [buffer appendWithDouble:resizeWeight_];
    (void) [buffer appendWithChar:')'];
  }
  return [buffer description];
}

- (int)maximumSizeWithRAREiParentComponent:(id<RAREiParentComponent>)container
                          withJavaUtilList:(id<JavaUtilList>)components
              withRAREJGFormLayout_Measure:(id<RAREJGFormLayout_Measure>)minMeasure
              withRAREJGFormLayout_Measure:(id<RAREJGFormLayout_Measure>)prefMeasure
              withRAREJGFormLayout_Measure:(id<RAREJGFormLayout_Measure>)defaultMeasure {
  return [((id<RAREJGSize>) nil_chk(size_)) maximumSizeWithRAREiParentComponent:container withJavaUtilList:components withRAREJGFormLayout_Measure:minMeasure withRAREJGFormLayout_Measure:prefMeasure withRAREJGFormLayout_Measure:defaultMeasure];
}

+ (void)initialize {
  if (self == [RAREJGFormSpec class]) {
    RAREJGFormSpec_LEFT_ALIGN_ = [[RAREJGFormSpec_DefaultAlignment alloc] initWithNSString:@"left"];
    RAREJGFormSpec_RIGHT_ALIGN_ = [[RAREJGFormSpec_DefaultAlignment alloc] initWithNSString:@"right"];
    RAREJGFormSpec_TOP_ALIGN_ = [[RAREJGFormSpec_DefaultAlignment alloc] initWithNSString:@"top"];
    RAREJGFormSpec_BOTTOM_ALIGN_ = [[RAREJGFormSpec_DefaultAlignment alloc] initWithNSString:@"bottom"];
    RAREJGFormSpec_CENTER_ALIGN_ = [[RAREJGFormSpec_DefaultAlignment alloc] initWithNSString:@"center"];
    RAREJGFormSpec_FILL_ALIGN_ = [[RAREJGFormSpec_DefaultAlignment alloc] initWithNSString:@"fill"];
    RAREJGFormSpec_VALUES_ = [IOSObjectArray arrayWithObjects:(id[]){ RAREJGFormSpec_LEFT_ALIGN_, RAREJGFormSpec_RIGHT_ALIGN_, RAREJGFormSpec_TOP_ALIGN_, RAREJGFormSpec_BOTTOM_ALIGN_, RAREJGFormSpec_CENTER_ALIGN_, RAREJGFormSpec_FILL_ALIGN_ } count:6 type:[IOSClass classWithClass:[RAREJGFormSpec_DefaultAlignment class]]];
    RAREJGFormSpec_TOKEN_SEPARATOR_PATTERN_ = [JavaUtilRegexPattern compileWithNSString:@":"];
    RAREJGFormSpec_BOUNDS_SEPARATOR_PATTERN_ = [JavaUtilRegexPattern compileWithNSString:@"\\s*,\\s*"];
  }
}

- (void)copyAllFieldsTo:(RAREJGFormSpec *)other {
  [super copyAllFieldsTo:other];
  other->defaultAlignment_ = defaultAlignment_;
  other->resizeWeight_ = resizeWeight_;
  other->size_ = size_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "initWithRAREJGFormSpec_DefaultAlignment:withRAREJGSize:withDouble:", NULL, NULL, 0x4, NULL },
    { "initWithRAREJGFormSpec_DefaultAlignment:withNSString:", NULL, NULL, 0x4, NULL },
    { "getDefaultAlignment", NULL, "LRAREJGFormSpec_DefaultAlignment", 0x11, NULL },
    { "getSize", NULL, "LRAREJGSize", 0x11, NULL },
    { "getResizeWeight", NULL, "D", 0x11, NULL },
    { "canGrow", NULL, "Z", 0x10, NULL },
    { "isHorizontal", NULL, "Z", 0x400, NULL },
    { "setDefaultAlignmentWithRAREJGFormSpec_DefaultAlignment:", NULL, "V", 0x0, NULL },
    { "setSizeWithRAREJGSize:", NULL, "V", 0x0, NULL },
    { "setResizeWeightWithDouble:", NULL, "V", 0x0, NULL },
    { "parseAndInitValuesWithNSString:", NULL, "V", 0x2, NULL },
    { "parseSizeWithNSString:", NULL, "LRAREJGSize", 0x2, NULL },
    { "parseBoundedSizeWithNSString:", NULL, "LRAREJGSize", 0x2, NULL },
    { "parseOldBoundedSizeWithNSString:withBoolean:", NULL, "LRAREJGSize", 0x2, NULL },
    { "parseAtomicSizeWithNSString:", NULL, "LRAREJGSize", 0x2, NULL },
    { "parseResizeWeightWithNSString:", NULL, "D", 0xa, NULL },
    { "isConstantWithRAREJGSize:", NULL, "Z", 0xa, NULL },
    { "toShortString", NULL, "LNSString", 0x11, NULL },
    { "encode", NULL, "LNSString", 0x11, NULL },
    { "encodeEx", NULL, "LNSString", 0x11, NULL },
    { "maximumSizeWithRAREiParentComponent:withJavaUtilList:withRAREJGFormLayout_Measure:withRAREJGFormLayout_Measure:withRAREJGFormLayout_Measure:", NULL, "I", 0x10, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "LEFT_ALIGN_", NULL, 0x18, "LRAREJGFormSpec_DefaultAlignment" },
    { "RIGHT_ALIGN_", NULL, 0x18, "LRAREJGFormSpec_DefaultAlignment" },
    { "TOP_ALIGN_", NULL, 0x18, "LRAREJGFormSpec_DefaultAlignment" },
    { "BOTTOM_ALIGN_", NULL, 0x18, "LRAREJGFormSpec_DefaultAlignment" },
    { "CENTER_ALIGN_", NULL, 0x18, "LRAREJGFormSpec_DefaultAlignment" },
    { "FILL_ALIGN_", NULL, 0x18, "LRAREJGFormSpec_DefaultAlignment" },
    { "VALUES_", NULL, 0x1a, "LIOSObjectArray" },
    { "NO_GROW_", NULL, 0x19, "D" },
    { "DEFAULT_GROW_", NULL, 0x19, "D" },
    { "TOKEN_SEPARATOR_PATTERN_", NULL, 0x1a, "LJavaUtilRegexPattern" },
    { "BOUNDS_SEPARATOR_PATTERN_", NULL, 0x1a, "LJavaUtilRegexPattern" },
  };
  static J2ObjcClassInfo _RAREJGFormSpec = { "FormSpec", "com.jgoodies.forms.layout", NULL, 0x401, 21, methods, 11, fields, 0, NULL};
  return &_RAREJGFormSpec;
}

@end
@implementation RAREJGFormSpec_DefaultAlignment

static int RAREJGFormSpec_DefaultAlignment_nextOrdinal_ = 0;

+ (int)nextOrdinal {
  return RAREJGFormSpec_DefaultAlignment_nextOrdinal_;
}

+ (int *)nextOrdinalRef {
  return &RAREJGFormSpec_DefaultAlignment_nextOrdinal_;
}

- (id)initWithNSString:(NSString *)name {
  if (self = [super init]) {
    ordinal_ = RAREJGFormSpec_DefaultAlignment_nextOrdinal_++;
    self->name_ = name;
  }
  return self;
}

+ (RAREJGFormSpec_DefaultAlignment *)valueOfWithNSString:(NSString *)str
                                             withBoolean:(BOOL)isHorizontal {
  if ([((NSString *) nil_chk(str)) isEqual:@"f"] || [str isEqual:@"fill"]) return [RAREJGFormSpec FILL_ALIGN];
  else if ([str isEqual:@"c"] || [str isEqual:@"center"]) return [RAREJGFormSpec CENTER_ALIGN];
  else if (isHorizontal) {
    if ([str isEqual:@"r"] || [str isEqual:@"right"]) return [RAREJGFormSpec RIGHT_ALIGN];
    else if ([str isEqual:@"l"] || [str isEqual:@"left"]) return [RAREJGFormSpec LEFT_ALIGN];
    else return nil;
  }
  else {
    if ([str isEqual:@"t"] || [str isEqual:@"top"]) return [RAREJGFormSpec TOP_ALIGN];
    else if ([str isEqual:@"b"] || [str isEqual:@"bottom"]) return [RAREJGFormSpec BOTTOM_ALIGN];
    else return nil;
  }
}

- (NSString *)description {
  return name_;
}

- (unichar)abbreviation {
  return [((NSString *) nil_chk(name_)) charAtWithInt:0];
}

- (void)copyAllFieldsTo:(RAREJGFormSpec_DefaultAlignment *)other {
  [super copyAllFieldsTo:other];
  other->name_ = name_;
  other->ordinal_ = ordinal_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "initWithNSString:", NULL, NULL, 0x2, NULL },
    { "valueOfWithNSString:withBoolean:", NULL, "LRAREJGFormSpec_DefaultAlignment", 0xa, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "name_", NULL, 0x92, "LNSString" },
    { "nextOrdinal_", NULL, 0xa, "I" },
    { "ordinal_", NULL, 0x12, "I" },
  };
  static J2ObjcClassInfo _RAREJGFormSpec_DefaultAlignment = { "DefaultAlignment", "com.jgoodies.forms.layout", "FormSpec", 0x19, 2, methods, 3, fields, 0, NULL};
  return &_RAREJGFormSpec_DefaultAlignment;
}

@end
