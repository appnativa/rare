//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/converters/StringConverter.java
//
//  Created by decoteaud on 12/8/15.
//

#include "IOSCharArray.h"
#include "IOSClass.h"
#include "IOSObjectArray.h"
#include "com/appnativa/rare/Platform.h"
#include "com/appnativa/rare/converters/ConverterContext.h"
#include "com/appnativa/rare/converters/StringConverter.h"
#include "com/appnativa/rare/iPlatformAppContext.h"
#include "com/appnativa/rare/platform/PlatformHelper.h"
#include "com/appnativa/rare/ui/Utils.h"
#include "com/appnativa/rare/widget/iWidget.h"
#include "com/appnativa/util/CharArray.h"
#include "com/appnativa/util/CharScanner.h"
#include "com/appnativa/util/FormatException.h"
#include "java/lang/CharSequence.h"
#include "java/lang/Character.h"
#include "java/lang/Integer.h"
#include "java/lang/ThreadLocal.h"
#include "java/text/ParseException.h"

@implementation RAREStringConverter

static RAREConverterContext * RAREStringConverter_EXPANDER_CONTEXT_;
static RAREConverterContext * RAREStringConverter_PASSWORD_CONTEXT_;
static RAREConverterContext * RAREStringConverter_TITLECASE_CONTEXT_;
static RAREConverterContext * RAREStringConverter_TITLECASE_CLEAN_CONTEXT_;
static RAREConverterContext * RAREStringConverter_RESOURCE_CONTEXT_;
static RAREConverterContext * RAREStringConverter_HTML_CONTEXT_;
static RAREConverterContext * RAREStringConverter_CAPITALIZE_CONTEXT_;
static RAREConverterContext * RAREStringConverter_CAPITALIZE_CLEAN_CONTEXT_;
static JavaLangThreadLocal * RAREStringConverter_perThreadCharArray_;

+ (RAREConverterContext *)EXPANDER_CONTEXT {
  return RAREStringConverter_EXPANDER_CONTEXT_;
}

+ (void)setEXPANDER_CONTEXT:(RAREConverterContext *)EXPANDER_CONTEXT {
  RAREStringConverter_EXPANDER_CONTEXT_ = EXPANDER_CONTEXT;
}

+ (RAREConverterContext *)PASSWORD_CONTEXT {
  return RAREStringConverter_PASSWORD_CONTEXT_;
}

+ (void)setPASSWORD_CONTEXT:(RAREConverterContext *)PASSWORD_CONTEXT {
  RAREStringConverter_PASSWORD_CONTEXT_ = PASSWORD_CONTEXT;
}

+ (RAREConverterContext *)TITLECASE_CONTEXT {
  return RAREStringConverter_TITLECASE_CONTEXT_;
}

+ (void)setTITLECASE_CONTEXT:(RAREConverterContext *)TITLECASE_CONTEXT {
  RAREStringConverter_TITLECASE_CONTEXT_ = TITLECASE_CONTEXT;
}

+ (RAREConverterContext *)TITLECASE_CLEAN_CONTEXT {
  return RAREStringConverter_TITLECASE_CLEAN_CONTEXT_;
}

+ (void)setTITLECASE_CLEAN_CONTEXT:(RAREConverterContext *)TITLECASE_CLEAN_CONTEXT {
  RAREStringConverter_TITLECASE_CLEAN_CONTEXT_ = TITLECASE_CLEAN_CONTEXT;
}

+ (RAREConverterContext *)RESOURCE_CONTEXT {
  return RAREStringConverter_RESOURCE_CONTEXT_;
}

+ (void)setRESOURCE_CONTEXT:(RAREConverterContext *)RESOURCE_CONTEXT {
  RAREStringConverter_RESOURCE_CONTEXT_ = RESOURCE_CONTEXT;
}

+ (RAREConverterContext *)HTML_CONTEXT {
  return RAREStringConverter_HTML_CONTEXT_;
}

+ (void)setHTML_CONTEXT:(RAREConverterContext *)HTML_CONTEXT {
  RAREStringConverter_HTML_CONTEXT_ = HTML_CONTEXT;
}

+ (RAREConverterContext *)CAPITALIZE_CONTEXT {
  return RAREStringConverter_CAPITALIZE_CONTEXT_;
}

+ (void)setCAPITALIZE_CONTEXT:(RAREConverterContext *)CAPITALIZE_CONTEXT {
  RAREStringConverter_CAPITALIZE_CONTEXT_ = CAPITALIZE_CONTEXT;
}

+ (RAREConverterContext *)CAPITALIZE_CLEAN_CONTEXT {
  return RAREStringConverter_CAPITALIZE_CLEAN_CONTEXT_;
}

+ (void)setCAPITALIZE_CLEAN_CONTEXT:(RAREConverterContext *)CAPITALIZE_CLEAN_CONTEXT {
  RAREStringConverter_CAPITALIZE_CLEAN_CONTEXT_ = CAPITALIZE_CLEAN_CONTEXT;
}

+ (JavaLangThreadLocal *)perThreadCharArray {
  return RAREStringConverter_perThreadCharArray_;
}

+ (void)setPerThreadCharArray:(JavaLangThreadLocal *)perThreadCharArray {
  RAREStringConverter_perThreadCharArray_ = perThreadCharArray;
}

- (id)init {
  return [super init];
}

- (id)createContextWithRAREiWidget:(id<RAREiWidget>)widget
                      withNSString:(NSString *)value {
  if (value == nil) {
    return nil;
  }
  if ([((NSString *) nil_chk(value)) sequenceLength] == 0) {
    return RAREStringConverter_EXPANDER_CONTEXT_;
  }
  if ([value equalsIgnoreCase:@"password"]) {
    return RAREStringConverter_PASSWORD_CONTEXT_;
  }
  if ([value equalsIgnoreCase:@"titlecase"]) {
    return RAREStringConverter_TITLECASE_CONTEXT_;
  }
  if ([value equalsIgnoreCase:@"titlecase-clean"]) {
    return RAREStringConverter_TITLECASE_CLEAN_CONTEXT_;
  }
  if ([value equalsIgnoreCase:@"capitalize"]) {
    return RAREStringConverter_CAPITALIZE_CONTEXT_;
  }
  if ([value equalsIgnoreCase:@"capitalize-clean"]) {
    return RAREStringConverter_CAPITALIZE_CLEAN_CONTEXT_;
  }
  if ([value equalsIgnoreCase:@"html"]) {
    return RAREStringConverter_HTML_CONTEXT_;
  }
  if ([value equalsIgnoreCase:@"html"]) {
    return RAREStringConverter_HTML_CONTEXT_;
  }
  if ([value hasPrefix:@"html"]) {
    int n = [value indexOf:' '];
    if (n == -1) {
      return RAREStringConverter_HTML_CONTEXT_;
    }
    value = [value substring:n + 1];
    if ([((NSString *) nil_chk([((NSString *) nil_chk(value)) trim])) sequenceLength] != 0) {
      return [[RAREConverterContext alloc] initWithNSString:@"html" withId:value];
    }
    else {
      return RAREStringConverter_HTML_CONTEXT_;
    }
  }
  if ([((NSString *) nil_chk(value)) equalsIgnoreCase:@"resource"]) {
    return RAREStringConverter_RESOURCE_CONTEXT_;
  }
  return [[RAREConverterContext alloc] initWithNSString:value];
}

- (id)objectFromStringWithRAREiWidget:(id<RAREiWidget>)widget
                         withNSString:(NSString *)value
                               withId:(id)context {
  if (context == RAREStringConverter_TITLECASE_CONTEXT_) {
    value = ((value == nil) || ([value sequenceLength] == 0)) ? value : [((RAREUTCharArray *) nil_chk([((RAREUTCharArray *) nil_chk([((RAREUTCharArray *) nil_chk([((JavaLangThreadLocal *) nil_chk(RAREStringConverter_perThreadCharArray_)) get])) setWithNSString:value])) toTitleCase])) description];
  }
  else if (context == RAREStringConverter_TITLECASE_CLEAN_CONTEXT_) {
    if ((value != nil) && ([value sequenceLength] > 0)) {
      RAREUTCharArray *ca = [((JavaLangThreadLocal *) nil_chk(RAREStringConverter_perThreadCharArray_)) get];
      @try {
        (void) [RAREUTCharScanner cleanQuotedWithNSString:value withRAREUTCharArray:ca];
      }
      @catch (JavaTextParseException *ex) {
        (void) [((RAREUTCharArray *) nil_chk(ca)) setWithNSString:value];
      }
      value = [((RAREUTCharArray *) nil_chk([((RAREUTCharArray *) nil_chk(ca)) toTitleCase])) description];
    }
  }
  else if ((context != nil) && [((NSString *) nil_chk([((RAREConverterContext *) check_class_cast(context, [RAREConverterContext class])) getName])) isEqual:@"html"]) {
    NSString *style = (NSString *) check_class_cast([((RAREConverterContext *) check_class_cast(context, [RAREConverterContext class])) getUserObject], [NSString class]);
    if (value == nil) {
      return (style == nil) ? nil : @"<html></html>";
    }
    return (style == nil) ? [NSString stringWithFormat:@"<html>%@</html>", value] : [NSString stringWithFormat:@"<html>%@%@</html>", style, value];
  }
  else if (context == RAREStringConverter_CAPITALIZE_CONTEXT_) {
    value = ((value == nil) || ([value sequenceLength] == 0)) ? value : [((RAREUTCharArray *) nil_chk([((RAREUTCharArray *) nil_chk([((RAREUTCharArray *) nil_chk([((JavaLangThreadLocal *) nil_chk(RAREStringConverter_perThreadCharArray_)) get])) setWithNSString:value])) toTitleCase])) description];
  }
  else if (context == RAREStringConverter_CAPITALIZE_CLEAN_CONTEXT_) {
    if ((value != nil) && ([value sequenceLength] > 0)) {
      RAREUTCharArray *ca = [((JavaLangThreadLocal *) nil_chk(RAREStringConverter_perThreadCharArray_)) get];
      @try {
        (void) [RAREUTCharScanner cleanQuotedWithNSString:value withRAREUTCharArray:ca];
      }
      @catch (JavaTextParseException *ex) {
        (void) [((RAREUTCharArray *) nil_chk(ca)) setWithNSString:value];
      }
      (void) [((RAREUTCharArray *) nil_chk(ca)) toLowerCase];
      (*IOSCharArray_GetRef(nil_chk(ca->A_), 0)) = [JavaLangCharacter toUpperCaseWithChar:IOSCharArray_Get(ca->A_, 0)];
      value = [ca description];
    }
  }
  else if (([context isKindOfClass:[RAREConverterContext class]]) && (context != RAREStringConverter_PASSWORD_CONTEXT_)) {
    RAREConverterContext *sc = (RAREConverterContext *) check_class_cast(context, [RAREConverterContext class]);
    @try {
      value = [RAREUTCharScanner cleanQuotedWithNSString:value];
    }
    @catch (JavaTextParseException *e) {
      [RAREPlatform ignoreExceptionWithNSString:nil withJavaLangThrowable:e];
    }
    if (sc == RAREStringConverter_RESOURCE_CONTEXT_) {
      NSString *s = [((id<RAREiPlatformAppContext>) nil_chk([((id<RAREiWidget>) nil_chk(widget)) getAppContext])) getResourceAsStringWithNSString:value];
      if ((s != nil) && ([s sequenceLength] > 0)) {
        value = s;
      }
    }
    else if (sc != RAREStringConverter_EXPANDER_CONTEXT_) {
      NSString *format = [((RAREConverterContext *) nil_chk(sc)) getName];
      if ((format != nil) && ([format indexOfString:@"%s"] != -1)) {
        value = [RAREaPlatformHelper formatWithNSString:format withNSObjectArray:[IOSObjectArray arrayWithObjects:(id[]){ value } count:1 type:[IOSClass classWithClass:[NSObject class]]]];
        int min = ([(id) minValue_ isKindOfClass:[NSNumber class]]) ? [((NSNumber *) check_class_cast(minValue_, [NSNumber class])) intValue] : -1;
        int max = ([(id) maxValue_ isKindOfClass:[NSNumber class]]) ? [((NSNumber *) check_class_cast(maxValue_, [NSNumber class])) intValue] : JavaLangInteger_MAX_VALUE;
        if (([((NSString *) nil_chk(value)) sequenceLength] < min) || ([value sequenceLength] > max)) {
          @throw [[RAREUTFormatException alloc] initWithNSString:[RAREUtils makeInvalidRangeStringWithInt:min withInt:max]];
        }
      }
    }
  }
  return value;
}

- (id<JavaLangCharSequence>)objectToStringWithRAREiWidget:(id<RAREiWidget>)widget
                                                   withId:(id)object
                                                   withId:(id)context {
  if ([object conformsToProtocol: @protocol(JavaLangCharSequence)]) {
    return (id<JavaLangCharSequence>) check_protocol_cast(object, @protocol(JavaLangCharSequence));
  }
  return (object == nil) ? nil : [object description];
}

- (BOOL)objectsAreImmutable {
  return NO;
}

- (IOSClass *)getObjectClassWithId:(id)context {
  return [IOSClass classWithClass:[NSString class]];
}

+ (void)initialize {
  if (self == [RAREStringConverter class]) {
    RAREStringConverter_EXPANDER_CONTEXT_ = [[RAREConverterContext alloc] initWithNSString:@""];
    RAREStringConverter_PASSWORD_CONTEXT_ = [[RAREConverterContext alloc] initWithNSString:@"password"];
    RAREStringConverter_TITLECASE_CONTEXT_ = [[RAREConverterContext alloc] initWithNSString:@"titlecase"];
    RAREStringConverter_TITLECASE_CLEAN_CONTEXT_ = [[RAREConverterContext alloc] initWithNSString:@"titlecase-clean"];
    RAREStringConverter_RESOURCE_CONTEXT_ = [[RAREConverterContext alloc] initWithNSString:@"resource"];
    RAREStringConverter_HTML_CONTEXT_ = [[RAREConverterContext alloc] initWithNSString:@"html"];
    RAREStringConverter_CAPITALIZE_CONTEXT_ = [[RAREConverterContext alloc] initWithNSString:@"capitalize"];
    RAREStringConverter_CAPITALIZE_CLEAN_CONTEXT_ = [[RAREConverterContext alloc] initWithNSString:@"capitalize-clean"];
    RAREStringConverter_perThreadCharArray_ = [[RAREStringConverter_$1 alloc] init];
  }
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "createContextWithRAREiWidget:withNSString:", NULL, "LNSObject", 0x1, NULL },
    { "objectFromStringWithRAREiWidget:withNSString:withId:", NULL, "LNSObject", 0x1, NULL },
    { "objectToStringWithRAREiWidget:withId:withId:", NULL, "LJavaLangCharSequence", 0x1, NULL },
    { "objectsAreImmutable", NULL, "Z", 0x1, NULL },
    { "getObjectClassWithId:", NULL, "LIOSClass", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "EXPANDER_CONTEXT_", NULL, 0x9, "LRAREConverterContext" },
    { "PASSWORD_CONTEXT_", NULL, 0x9, "LRAREConverterContext" },
    { "TITLECASE_CONTEXT_", NULL, 0x9, "LRAREConverterContext" },
    { "TITLECASE_CLEAN_CONTEXT_", NULL, 0x9, "LRAREConverterContext" },
    { "RESOURCE_CONTEXT_", NULL, 0x9, "LRAREConverterContext" },
    { "HTML_CONTEXT_", NULL, 0x9, "LRAREConverterContext" },
    { "CAPITALIZE_CONTEXT_", NULL, 0x9, "LRAREConverterContext" },
    { "CAPITALIZE_CLEAN_CONTEXT_", NULL, 0x9, "LRAREConverterContext" },
    { "perThreadCharArray_", NULL, 0xa, "LJavaLangThreadLocal" },
  };
  static J2ObjcClassInfo _RAREStringConverter = { "StringConverter", "com.appnativa.rare.converters", NULL, 0x1, 5, methods, 9, fields, 0, NULL};
  return &_RAREStringConverter;
}

@end
@implementation RAREStringConverter_$1

- (RAREUTCharArray *)initialValue {
  @synchronized(self) {
    {
      return [[RAREUTCharArray alloc] initWithInt:32];
    }
  }
}

- (id)init {
  return [super init];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "initialValue", NULL, "LRAREUTCharArray", 0x24, NULL },
  };
  static const char *superclass_type_args[] = {"LRAREUTCharArray"};
  static J2ObjcClassInfo _RAREStringConverter_$1 = { "$1", "com.appnativa.rare.converters", "StringConverter", 0x8000, 1, methods, 0, NULL, 1, superclass_type_args};
  return &_RAREStringConverter_$1;
}

@end
