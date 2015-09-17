//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/converters/ColorConverter.java
//
//  Created by decoteaud on 9/15/15.
//

#include "IOSClass.h"
#include "com/appnativa/rare/converters/ColorConverter.h"
#include "com/appnativa/rare/converters/Conversions.h"
#include "com/appnativa/rare/converters/ConverterContext.h"
#include "com/appnativa/rare/ui/UIColor.h"
#include "com/appnativa/rare/ui/UIColorHelper.h"
#include "com/appnativa/rare/ui/UIColorShade.h"
#include "com/appnativa/rare/widget/iWidget.h"
#include "java/lang/CharSequence.h"

@implementation RAREColorConverter

static RAREConverterContext * RAREColorConverter_HEX_CONTEXT_;
static RAREConverterContext * RAREColorConverter_RGB_CONTEXT_;
static RAREConverterContext * RAREColorConverter_ICON_RGB_CONTEXT_;
static RAREConverterContext * RAREColorConverter_ICON_HEX_CONTEXT_;

+ (RAREConverterContext *)HEX_CONTEXT {
  return RAREColorConverter_HEX_CONTEXT_;
}

+ (void)setHEX_CONTEXT:(RAREConverterContext *)HEX_CONTEXT {
  RAREColorConverter_HEX_CONTEXT_ = HEX_CONTEXT;
}

+ (RAREConverterContext *)RGB_CONTEXT {
  return RAREColorConverter_RGB_CONTEXT_;
}

+ (void)setRGB_CONTEXT:(RAREConverterContext *)RGB_CONTEXT {
  RAREColorConverter_RGB_CONTEXT_ = RGB_CONTEXT;
}

+ (RAREConverterContext *)ICON_RGB_CONTEXT {
  return RAREColorConverter_ICON_RGB_CONTEXT_;
}

+ (void)setICON_RGB_CONTEXT:(RAREConverterContext *)ICON_RGB_CONTEXT {
  RAREColorConverter_ICON_RGB_CONTEXT_ = ICON_RGB_CONTEXT;
}

+ (RAREConverterContext *)ICON_HEX_CONTEXT {
  return RAREColorConverter_ICON_HEX_CONTEXT_;
}

+ (void)setICON_HEX_CONTEXT:(RAREConverterContext *)ICON_HEX_CONTEXT {
  RAREColorConverter_ICON_HEX_CONTEXT_ = ICON_HEX_CONTEXT;
}

- (id)init {
  return [super init];
}

- (id)createContextWithRAREiWidget:(id<RAREiWidget>)widget
                      withNSString:(NSString *)value {
  if ((value != nil) && [value equalsIgnoreCase:@"rgb"]) {
    return RAREColorConverter_RGB_CONTEXT_;
  }
  if ((value != nil) && [value equalsIgnoreCase:@"rgb_icon"]) {
    return RAREColorConverter_ICON_RGB_CONTEXT_;
  }
  if ((value != nil) && [value equalsIgnoreCase:@"hex_icon"]) {
    return RAREColorConverter_ICON_HEX_CONTEXT_;
  }
  return RAREColorConverter_HEX_CONTEXT_;
}

- (id)objectFromStringWithRAREiWidget:(id<RAREiWidget>)widget
                         withNSString:(NSString *)value
                               withId:(id)context {
  if ((context == RAREColorConverter_RGB_CONTEXT_) || (context == RAREColorConverter_ICON_RGB_CONTEXT_)) {
    return [RAREConversions colorFromRGBStringWithNSString:value];
  }
  return [RAREUIColorHelper getColorWithNSString:value];
}

- (id<JavaLangCharSequence>)objectToStringWithRAREiWidget:(id<RAREiWidget>)widget
                                                   withId:(id)o
                                                   withId:(id)context {
  if (!([o isKindOfClass:[RAREUIColor class]])) {
    return @"";
  }
  RAREUIColor *c = (RAREUIColor *) check_class_cast(o, [RAREUIColor class]);
  if (context == RAREColorConverter_RGB_CONTEXT_) {
    return [RAREConversions colorToRGBStringWithRAREUIColor:c];
  }
  if ([c isKindOfClass:[RAREUIColorShade class]]) {
    return [((RAREUIColor *) nil_chk(c)) description];
  }
  return [RAREConversions colorToHEXStringWithRAREUIColor:c];
}

- (BOOL)objectsAreImmutableWithId:(id)contex {
  return YES;
}

- (IOSClass *)getObjectClassWithId:(id)context {
  return [IOSClass classWithClass:[RAREUIColor class]];
}

+ (void)initialize {
  if (self == [RAREColorConverter class]) {
    RAREColorConverter_HEX_CONTEXT_ = [[RAREConverterContext alloc] initWithNSString:@"HEX_CONTEXT"];
    RAREColorConverter_RGB_CONTEXT_ = [[RAREConverterContext alloc] initWithNSString:@"RGB_CONTEXT"];
    RAREColorConverter_ICON_RGB_CONTEXT_ = [[RAREConverterContext alloc] initWithNSString:@"ICON_RGB_CONTEXT"];
    RAREColorConverter_ICON_HEX_CONTEXT_ = [[RAREConverterContext alloc] initWithNSString:@"ICON_HEX_CONTEXT"];
  }
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "createContextWithRAREiWidget:withNSString:", NULL, "LNSObject", 0x1, NULL },
    { "objectFromStringWithRAREiWidget:withNSString:withId:", NULL, "LNSObject", 0x1, NULL },
    { "objectToStringWithRAREiWidget:withId:withId:", NULL, "LJavaLangCharSequence", 0x1, NULL },
    { "objectsAreImmutableWithId:", NULL, "Z", 0x1, NULL },
    { "getObjectClassWithId:", NULL, "LIOSClass", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "HEX_CONTEXT_", NULL, 0x9, "LRAREConverterContext" },
    { "RGB_CONTEXT_", NULL, 0x9, "LRAREConverterContext" },
    { "ICON_RGB_CONTEXT_", NULL, 0x9, "LRAREConverterContext" },
    { "ICON_HEX_CONTEXT_", NULL, 0x9, "LRAREConverterContext" },
  };
  static J2ObjcClassInfo _RAREColorConverter = { "ColorConverter", "com.appnativa.rare.converters", NULL, 0x1, 5, methods, 4, fields, 0, NULL};
  return &_RAREColorConverter;
}

@end
