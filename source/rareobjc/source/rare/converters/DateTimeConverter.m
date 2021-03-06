//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/converters/DateTimeConverter.java
//
//  Created by decoteaud on 3/11/16.
//

#include "IOSClass.h"
#include "IOSObjectArray.h"
#include "com/appnativa/rare/Platform.h"
#include "com/appnativa/rare/converters/Conversions.h"
#include "com/appnativa/rare/converters/DateContext.h"
#include "com/appnativa/rare/converters/DateTimeConverter.h"
#include "com/appnativa/rare/converters/aConverter.h"
#include "com/appnativa/rare/iPlatformAppContext.h"
#include "com/appnativa/rare/viewer/iViewer.h"
#include "com/appnativa/rare/widget/iWidget.h"
#include "com/appnativa/util/FormatException.h"
#include "com/appnativa/util/Helper.h"
#include "java/lang/CharSequence.h"
#include "java/lang/Character.h"
#include "java/lang/RuntimeException.h"
#include "java/text/DateFormat.h"
#include "java/text/ParsePosition.h"
#include "java/util/Calendar.h"
#include "java/util/Date.h"

@implementation RAREDateTimeConverter

- (id)init {
  return [super init];
}

- (id)createContextWithRAREiWidget:(id<RAREiWidget>)widget
                      withNSString:(NSString *)value {
  return [RAREConversions createDateContextWithNSString:value withBoolean:[((id<RAREiPlatformAppContext>) nil_chk([RAREPlatform getAppContext])) getAutoLocalizeDateFormats]];
}

- (id)objectFromStringWithRAREiWidget:(id<RAREiWidget>)widget
                         withNSString:(NSString *)value
                               withId:(id)context {
  return [self objectFromStringWithRAREiWidget:widget withNSString:value withId:context withBoolean:[((id<RAREiPlatformAppContext>) nil_chk([RAREPlatform getAppContext])) ignoreFormatExceptions]];
}

- (id)objectFromStringWithRAREiWidget:(id<RAREiWidget>)widget
                         withNSString:(NSString *)value
                               withId:(id)context
                          withBoolean:(BOOL)ignoreExceptions {
  RAREDateContext *dc = [self getDateContextWithId:context];
  if ([((RAREDateContext *) nil_chk(dc)) isCustomConverter]) {
    return [dc dateFromStringWithNSString:value];
  }
  int len = [((NSString *) nil_chk(value)) sequenceLength];
  if (len > 1) {
    if ([JavaLangCharacter isLetterWithChar:[value charAtWithInt:0]]) {
      unichar c = [value charAtWithInt:1];
      if ((c == '-') || (c == '+') || (c == '@')) {
        return [RAREUTHelper createDateWithNSString:value];
      }
    }
  }
  else {
    if (len == 1) {
      return [RAREUTHelper createDateWithNSString:value];
    }
    return nil;
  }
  IOSObjectArray *formats = [self getItemFormatsWithRAREiWidget:widget withRAREDateContext:dc];
  JavaUtilDate *date = nil;
  JavaTextParsePosition *p = [[JavaTextParsePosition alloc] initWithInt:0];
  if (formats == nil) {
    JavaTextDateFormat *df = [self getDateFormatWithId:dc withBoolean:NO];
    @try {
      @synchronized (df) {
        [p setIndexWithInt:0];
        date = [((JavaTextDateFormat *) nil_chk(df)) parseWithNSString:value withJavaTextParsePosition:p];
        if ([p getIndex] != 0) {
          return date;
        }
      }
    }
    @catch (JavaLangRuntimeException *e) {
    }
  }
  else {
    {
      IOSObjectArray *a__ = formats;
      id const *b__ = a__->buffer_;
      id const *e__ = b__ + a__->size_;
      while (b__ < e__) {
        JavaTextDateFormat *df = (*b__++);
        @try {
          @synchronized (df) {
            [p setIndexWithInt:0];
            date = [((JavaTextDateFormat *) nil_chk(df)) parseWithNSString:value withJavaTextParsePosition:p];
            if ([p getIndex] != 0) {
              return date;
            }
          }
        }
        @catch (JavaLangRuntimeException *e) {
        }
      }
    }
  }
  if (widget == nil) {
    widget = [RAREPlatform getContextRootViewer];
  }
  if (ignoreExceptions) {
    return [[RAREDateTimeConverter_BadValueDate alloc] initWithNSString:value];
  }
  @throw [RAREaConverter formatExceptionWithRAREiWidget:widget withNSString:[RAREUTHelper toStringWithNSObjectArray:formats withNSString:@";"] withNSString:value];
}

- (id<JavaLangCharSequence>)objectToStringWithRAREiWidget:(id<RAREiWidget>)widget
                                                   withId:(id)object
                                                   withId:(id)context {
  JavaUtilDate *date = nil;
  if ([object isKindOfClass:[JavaUtilCalendar class]]) {
    date = [((JavaUtilCalendar *) check_class_cast(object, [JavaUtilCalendar class])) getTime];
  }
  else if ([object isKindOfClass:[RAREDateTimeConverter_BadValueDate class]]) {
    return [nil_chk(object) description];
  }
  else if ([object isKindOfClass:[JavaUtilDate class]]) {
    date = (JavaUtilDate *) check_class_cast(object, [JavaUtilDate class]);
  }
  else if ([object isKindOfClass:[NSNumber class]]) {
    date = [[JavaUtilDate alloc] initWithLong:[((NSNumber *) check_class_cast(object, [NSNumber class])) longLongValue]];
  }
  if (date == nil) {
    return @"";
  }
  RAREDateContext *dc = [self getDateContextWithId:context];
  if ([((RAREDateContext *) nil_chk(dc)) isCustomConverter]) {
    return [dc dateToStringWithJavaUtilDate:date];
  }
  return [((JavaTextDateFormat *) nil_chk([self getDateFormatWithId:dc withBoolean:YES])) formatWithJavaUtilDate:date];
}

- (BOOL)objectsAreImmutableWithId:(id)context {
  return YES;
}

- (IOSClass *)getObjectClassWithId:(id)context {
  return [IOSClass classWithClass:[JavaUtilDate class]];
}

- (RAREDateContext *)getDateContextWithId:(id)context {
  if ([context isKindOfClass:[RAREDateContext class]]) {
    return (RAREDateContext *) check_class_cast(context, [RAREDateContext class]);
  }
  return [((id<RAREiPlatformAppContext>) nil_chk([RAREPlatform getAppContext])) getDefaultDateTimeContext];
}

- (JavaTextDateFormat *)getDateFormatWithId:(id)context
                                withBoolean:(BOOL)display {
  JavaTextDateFormat *df = nil;
  if ([context isKindOfClass:[RAREDateContext class]]) {
    if (display) {
      df = [((RAREDateContext *) check_class_cast(context, [RAREDateContext class])) getDisplayFormat];
    }
    else {
      df = [((RAREDateContext *) check_class_cast(context, [RAREDateContext class])) getItemFormat];
    }
  }
  if (df == nil) {
    if (display) {
      df = [((RAREDateContext *) nil_chk([((id<RAREiPlatformAppContext>) nil_chk([RAREPlatform getAppContext])) getDefaultDateTimeContext])) getDisplayFormat];
    }
    else {
      df = [((RAREDateContext *) nil_chk([((id<RAREiPlatformAppContext>) nil_chk([RAREPlatform getAppContext])) getDefaultDateTimeContext])) getItemFormat];
    }
  }
  return df;
}

- (IOSObjectArray *)getItemFormatsWithRAREiWidget:(id<RAREiWidget>)widget
                              withRAREDateContext:(RAREDateContext *)context {
  IOSObjectArray *formats = (context == nil) ? nil : [context getItemFormats];
  return (formats == nil) ? [((RAREDateContext *) nil_chk([((id<RAREiPlatformAppContext>) nil_chk([RAREPlatform getAppContext])) getDefaultDateTimeContext])) getItemFormats] : formats;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "createContextWithRAREiWidget:withNSString:", NULL, "LNSObject", 0x1, NULL },
    { "objectFromStringWithRAREiWidget:withNSString:withId:", NULL, "LNSObject", 0x1, NULL },
    { "objectFromStringWithRAREiWidget:withNSString:withId:withBoolean:", NULL, "LNSObject", 0x1, NULL },
    { "objectToStringWithRAREiWidget:withId:withId:", NULL, "LJavaLangCharSequence", 0x1, NULL },
    { "objectsAreImmutableWithId:", NULL, "Z", 0x1, NULL },
    { "getObjectClassWithId:", NULL, "LIOSClass", 0x1, NULL },
    { "getDateContextWithId:", NULL, "LRAREDateContext", 0x4, NULL },
    { "getDateFormatWithId:withBoolean:", NULL, "LJavaTextDateFormat", 0x4, NULL },
    { "getItemFormatsWithRAREiWidget:withRAREDateContext:", NULL, "LIOSObjectArray", 0x4, NULL },
  };
  static J2ObjcClassInfo _RAREDateTimeConverter = { "DateTimeConverter", "com.appnativa.rare.converters", NULL, 0x1, 9, methods, 0, NULL, 0, NULL};
  return &_RAREDateTimeConverter;
}

@end
@implementation RAREDateTimeConverter_BadValueDate

- (id)initWithNSString:(NSString *)value {
  if (self = [super initWithLong:0]) {
    stringValue_ = value;
  }
  return self;
}

- (NSString *)description {
  return stringValue_;
}

- (void)copyAllFieldsTo:(RAREDateTimeConverter_BadValueDate *)other {
  [super copyAllFieldsTo:other];
  other->stringValue_ = stringValue_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcClassInfo _RAREDateTimeConverter_BadValueDate = { "BadValueDate", "com.appnativa.rare.converters", "DateTimeConverter", 0x8, 0, NULL, 0, NULL, 0, NULL};
  return &_RAREDateTimeConverter_BadValueDate;
}

@end
