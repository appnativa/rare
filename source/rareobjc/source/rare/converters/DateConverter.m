//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/converters/DateConverter.java
//
//  Created by decoteaud on 9/15/15.
//

#include "IOSObjectArray.h"
#include "com/appnativa/rare/Platform.h"
#include "com/appnativa/rare/converters/DateContext.h"
#include "com/appnativa/rare/converters/DateConverter.h"
#include "com/appnativa/rare/iPlatformAppContext.h"
#include "com/appnativa/rare/widget/iWidget.h"
#include "java/text/DateFormat.h"

@implementation RAREDateConverter

- (id)objectFromStringWithRAREiWidget:(id<RAREiWidget>)widget
                         withNSString:(NSString *)value
                               withId:(id)context
                          withBoolean:(BOOL)ignoreExceptions {
  if (value == nil) {
    return nil;
  }
  return [super objectFromStringWithRAREiWidget:widget withNSString:value withId:context withBoolean:ignoreExceptions];
}

- (RAREDateContext *)getDateContextWithRAREiWidget:(id<RAREiWidget>)widget
                                            withId:(id)context {
  if ([context isKindOfClass:[RAREDateContext class]]) {
    return (RAREDateContext *) check_class_cast(context, [RAREDateContext class]);
  }
  return [((id<RAREiPlatformAppContext>) nil_chk([RAREPlatform getAppContext])) getDefaultDateContext];
}

- (JavaTextDateFormat *)getDateFormatWithRAREiWidget:(id<RAREiWidget>)widget
                                              withId:(id)context
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
      df = [((RAREDateContext *) nil_chk([((id<RAREiPlatformAppContext>) nil_chk([RAREPlatform getAppContext])) getDefaultDateContext])) getDisplayFormat];
    }
    else {
      df = [((RAREDateContext *) nil_chk([((id<RAREiPlatformAppContext>) nil_chk([RAREPlatform getAppContext])) getDefaultDateContext])) getItemFormat];
    }
  }
  return df;
}

- (IOSObjectArray *)getItemFormatsWithRAREiWidget:(id<RAREiWidget>)widget
                              withRAREDateContext:(RAREDateContext *)context {
  IOSObjectArray *formats = (context == nil) ? nil : [context getItemFormats];
  return (formats == nil) ? [((RAREDateContext *) nil_chk([((id<RAREiPlatformAppContext>) nil_chk([RAREPlatform getAppContext])) getDefaultDateContext])) getItemFormats] : formats;
}

- (id)init {
  return [super init];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "objectFromStringWithRAREiWidget:withNSString:withId:withBoolean:", NULL, "LNSObject", 0x1, NULL },
    { "getDateContextWithRAREiWidget:withId:", NULL, "LRAREDateContext", 0x4, NULL },
    { "getDateFormatWithRAREiWidget:withId:withBoolean:", NULL, "LJavaTextDateFormat", 0x4, NULL },
    { "getItemFormatsWithRAREiWidget:withRAREDateContext:", NULL, "LIOSObjectArray", 0x4, NULL },
  };
  static J2ObjcClassInfo _RAREDateConverter = { "DateConverter", "com.appnativa.rare.converters", NULL, 0x1, 4, methods, 0, NULL, 0, NULL};
  return &_RAREDateConverter;
}

@end
