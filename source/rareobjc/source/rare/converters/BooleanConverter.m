//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/converters/BooleanConverter.java
//
//  Created by decoteaud on 9/15/15.
//

#include "IOSClass.h"
#include "com/appnativa/rare/converters/BooleanConverter.h"
#include "com/appnativa/rare/widget/iWidget.h"
#include "com/appnativa/util/SNumber.h"
#include "java/lang/Boolean.h"
#include "java/lang/CharSequence.h"

@implementation RAREBooleanConverter

- (id)init {
  return [super init];
}

- (IOSClass *)getObjectClassWithId:(id)context {
  return [IOSClass classWithClass:[JavaLangBoolean class]];
}

- (id)objectFromStringWithRAREiWidget:(id<RAREiWidget>)widget
                         withNSString:(NSString *)value
                               withId:(id)context {
  return [JavaLangBoolean valueOfWithBoolean:[RAREUTSNumber booleanValueWithNSString:value]];
}

- (id<JavaLangCharSequence>)objectToStringWithRAREiWidget:(id<RAREiWidget>)widget
                                                   withId:(id)object
                                                   withId:(id)context {
  return (object == nil) ? nil : [object description];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getObjectClassWithId:", NULL, "LIOSClass", 0x1, NULL },
    { "objectFromStringWithRAREiWidget:withNSString:withId:", NULL, "LNSObject", 0x1, NULL },
    { "objectToStringWithRAREiWidget:withId:withId:", NULL, "LJavaLangCharSequence", 0x1, NULL },
  };
  static J2ObjcClassInfo _RAREBooleanConverter = { "BooleanConverter", "com.appnativa.rare.converters", NULL, 0x1, 3, methods, 0, NULL, 0, NULL};
  return &_RAREBooleanConverter;
}

@end
