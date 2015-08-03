//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/aUIProperties.java
//
//  Created by decoteaud on 7/29/15.
//

#include "IOSClass.h"
#include "com/appnativa/rare/ui/ScreenUtils.h"
#include "com/appnativa/rare/ui/UIColor.h"
#include "com/appnativa/rare/ui/UIColorShade.h"
#include "com/appnativa/rare/ui/UIFont.h"
#include "com/appnativa/rare/ui/UIImage.h"
#include "com/appnativa/rare/ui/UIImageIcon.h"
#include "com/appnativa/rare/ui/UIInsets.h"
#include "com/appnativa/rare/ui/UIProperties.h"
#include "com/appnativa/rare/ui/aUIProperties.h"
#include "com/appnativa/rare/ui/iPlatformBorder.h"
#include "com/appnativa/rare/ui/iPlatformIcon.h"
#include "com/appnativa/rare/ui/painter/PaintBucket.h"
#include "com/appnativa/rare/ui/painter/UISimpleBackgroundPainter.h"
#include "com/appnativa/rare/ui/painter/iBackgroundPainter.h"
#include "com/appnativa/rare/ui/painter/iPlatformPainter.h"
#include "java/lang/Boolean.h"
#include "java/lang/Float.h"
#include "java/lang/Integer.h"
#include "java/util/HashMap.h"
#include "java/util/Map.h"
#include "java/util/Set.h"

@implementation RAREaUIProperties

- (id)init {
  return [self initRAREaUIPropertiesWithJavaUtilMap:[[JavaUtilHashMap alloc] init]];
}

- (id)initRAREaUIPropertiesWithJavaUtilMap:(id<JavaUtilMap>)map {
  if (self = [super init]) {
    self->map_ = map;
  }
  return self;
}

- (id)initWithJavaUtilMap:(id<JavaUtilMap>)map {
  return [self initRAREaUIPropertiesWithJavaUtilMap:map];
}

- (void)clear {
  [((id<JavaUtilMap>) nil_chk(map_)) clear];
}

- (id<JavaUtilSet>)entrySet {
  return [((id<JavaUtilMap>) nil_chk(map_)) entrySet];
}

- (void)putWithNSString:(NSString *)key
                 withId:(id)value {
  (void) [((id<JavaUtilMap>) nil_chk(map_)) putWithId:key withId:value];
}

- (void)putAllWithRAREUIProperties:(RAREUIProperties *)defs {
  [((id<JavaUtilMap>) nil_chk(map_)) putAllWithJavaUtilMap:((RAREUIProperties *) nil_chk(defs))->map_];
}

- (id)getWithNSString:(NSString *)key {
  return [((id<JavaUtilMap>) nil_chk(map_)) getWithId:key];
}

- (id<RAREiBackgroundPainter>)getBackgroundPainterWithNSString:(NSString *)key {
  id o = [self getPainterWithNSString:key];
  if ([o conformsToProtocol: @protocol(RAREiBackgroundPainter)]) {
    return (id<RAREiBackgroundPainter>) check_protocol_cast(o, @protocol(RAREiBackgroundPainter));
  }
  if ([o isKindOfClass:[RAREUIColorShade class]]) {
    id<RAREiBackgroundPainter> p = [((RAREUIColorShade *) check_class_cast(o, [RAREUIColorShade class])) getBackgroundPainter];
    if (p != nil) {
      return p;
    }
  }
  return nil;
}

- (BOOL)getBooleanWithNSString:(NSString *)key
                   withBoolean:(BOOL)def {
  JavaLangBoolean *b = (JavaLangBoolean *) check_class_cast([((id<JavaUtilMap>) nil_chk(map_)) getWithId:key], [JavaLangBoolean class]);
  return (b == nil) ? def : [b booleanValue];
}

- (id<RAREiPlatformBorder>)getBorderWithNSString:(NSString *)key {
  return (id<RAREiPlatformBorder>) check_protocol_cast([((id<JavaUtilMap>) nil_chk(map_)) getWithId:key], @protocol(RAREiPlatformBorder));
}

- (RAREUIColor *)getColorWithNSString:(NSString *)key {
  return (RAREUIColor *) check_class_cast([((id<JavaUtilMap>) nil_chk(map_)) getWithId:key], [RAREUIColor class]);
}

- (RAREUIFont *)getFontWithNSString:(NSString *)key {
  return (RAREUIFont *) check_class_cast([((id<JavaUtilMap>) nil_chk(map_)) getWithId:key], [RAREUIFont class]);
}

- (id<RAREiPlatformIcon>)getIconWithNSString:(NSString *)key {
  id o = [((id<JavaUtilMap>) nil_chk(map_)) getWithId:key];
  if ([o conformsToProtocol: @protocol(RAREiPlatformIcon)]) {
    return (id<RAREiPlatformIcon>) check_protocol_cast(o, @protocol(RAREiPlatformIcon));
  }
  return nil;
}

- (RAREUIImage *)getImageWithNSString:(NSString *)key {
  id o = [((id<JavaUtilMap>) nil_chk(map_)) getWithId:key];
  if ([o isKindOfClass:[RAREUIImage class]]) {
    return (RAREUIImage *) check_class_cast(o, [RAREUIImage class]);
  }
  if ([o isKindOfClass:[RAREUIImageIcon class]]) {
    return [((RAREUIImageIcon *) check_class_cast(o, [RAREUIImageIcon class])) getUIImage];
  }
  return nil;
}

- (RAREUIImageIcon *)getImageIconWithNSString:(NSString *)key {
  id o = [((id<JavaUtilMap>) nil_chk(map_)) getWithId:key];
  if ([o isKindOfClass:[RAREUIImageIcon class]]) {
    return (RAREUIImageIcon *) check_class_cast(o, [RAREUIImageIcon class]);
  }
  if ([o isKindOfClass:[RAREUIImage class]]) {
    return [[RAREUIImageIcon alloc] initWithRAREUIImage:(RAREUIImage *) check_class_cast(o, [RAREUIImage class])];
  }
  return nil;
}

- (RAREUIInsets *)getInsetsWithNSString:(NSString *)key {
  id o = [((id<JavaUtilMap>) nil_chk(map_)) getWithId:key];
  return ([o isKindOfClass:[RAREUIInsets class]]) ? (RAREUIInsets *) check_class_cast(o, [RAREUIInsets class]) : nil;
}

- (int)getIntWithNSString:(NSString *)key
                  withInt:(int)def {
  id o = [((id<JavaUtilMap>) nil_chk(map_)) getWithId:key];
  if ([o isKindOfClass:[JavaLangInteger class]]) {
    return [(JavaLangInteger *) check_class_cast(o, [JavaLangInteger class]) intValue];
  }
  if ([o isKindOfClass:[NSString class]]) {
    return [[JavaLangInteger valueOfWithNSString:(NSString *) check_class_cast(o, [NSString class])] intValue];
  }
  return def;
}

- (JavaLangInteger *)getIntegerWithNSString:(NSString *)key {
  id o = [((id<JavaUtilMap>) nil_chk(map_)) getWithId:key];
  if ([o isKindOfClass:[JavaLangInteger class]]) {
    return (JavaLangInteger *) check_class_cast(o, [JavaLangInteger class]);
  }
  if ([o isKindOfClass:[NSString class]]) {
    int n = [RAREScreenUtils toPlatformPixelWidthWithNSString:(NSString *) check_class_cast(o, [NSString class]) withRAREiPlatformComponent:nil withFloat:100];
    return [JavaLangInteger valueOfWithInt:n];
  }
  return nil;
}

- (JavaLangInteger *)getPixelsWithNSString:(NSString *)key {
  id o = [((id<JavaUtilMap>) nil_chk(map_)) getWithId:key];
  if ([o isKindOfClass:[JavaLangInteger class]]) {
    float f = [((JavaLangInteger *) check_class_cast(o, [JavaLangInteger class])) floatValue];
    f *= [RAREScreenUtils getPixelMultiplier];
    return [JavaLangInteger valueOfWithInt:(int) f];
  }
  if ([o isKindOfClass:[NSString class]]) {
    int n = [RAREScreenUtils toPlatformPixelWidthWithNSString:(NSString *) check_class_cast(o, [NSString class]) withRAREiPlatformComponent:nil withFloat:100];
    return [JavaLangInteger valueOfWithInt:n];
  }
  return nil;
}

- (float)getFloatWithNSString:(NSString *)key
                    withFloat:(float)def {
  id o = [((id<JavaUtilMap>) nil_chk(map_)) getWithId:key];
  if ([o isKindOfClass:[JavaLangFloat class]]) {
    return [((JavaLangFloat *) check_class_cast(o, [JavaLangFloat class])) intValue];
  }
  if ([o isKindOfClass:[JavaLangInteger class]]) {
    return [((JavaLangInteger *) check_class_cast(o, [JavaLangInteger class])) floatValue];
  }
  if ([o isKindOfClass:[NSString class]]) {
    return [RAREScreenUtils toPlatformPixelWidthWithNSString:(NSString *) check_class_cast(o, [NSString class]) withRAREiPlatformComponent:nil withFloat:100];
  }
  return def;
}

- (RAREPaintBucket *)getPaintBucketWithNSString:(NSString *)key {
  id o = [((id<JavaUtilMap>) nil_chk(map_)) getWithId:key];
  if ([o isKindOfClass:[RAREPaintBucket class]]) {
    return (RAREPaintBucket *) check_class_cast(o, [RAREPaintBucket class]);
  }
  if ([o conformsToProtocol: @protocol(RAREiBackgroundPainter)]) {
    return [[RAREPaintBucket alloc] initWithRAREiBackgroundPainter:(id<RAREiBackgroundPainter>) check_protocol_cast(o, @protocol(RAREiBackgroundPainter))];
  }
  if ([o isKindOfClass:[RAREUIColor class]]) {
    return [[RAREPaintBucket alloc] initWithRAREUIColor:nil withRAREUIColor:(RAREUIColor *) check_class_cast(o, [RAREUIColor class])];
  }
  return nil;
}

- (id<RAREiPlatformPainter>)getPainterWithNSString:(NSString *)key {
  id o = [((id<JavaUtilMap>) nil_chk(map_)) getWithId:key];
  if ([o conformsToProtocol: @protocol(RAREiPlatformPainter)]) {
    return (id<RAREiPlatformPainter>) check_protocol_cast(o, @protocol(RAREiPlatformPainter));
  }
  if ([o isKindOfClass:[RAREUIColorShade class]]) {
    id<RAREiPlatformPainter> p = [((RAREUIColorShade *) check_class_cast(o, [RAREUIColorShade class])) getBackgroundPainter];
    if (p != nil) {
      return p;
    }
  }
  if ([o isKindOfClass:[RAREUIColor class]]) {
    return [[RAREUISimpleBackgroundPainter alloc] initWithRAREUIColor:(RAREUIColor *) check_class_cast(o, [RAREUIColor class])];
  }
  return nil;
}

- (NSString *)getStringWithNSString:(NSString *)key {
  return (NSString *) check_class_cast([((id<JavaUtilMap>) nil_chk(map_)) getWithId:key], [NSString class]);
}

- (BOOL)isEmpty {
  return [((id<JavaUtilMap>) nil_chk(map_)) isEmpty];
}

- (void)copyAllFieldsTo:(RAREaUIProperties *)other {
  [super copyAllFieldsTo:other];
  other->map_ = map_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "entrySet", NULL, "LJavaUtilSet", 0x1, NULL },
    { "getWithNSString:", NULL, "LNSObject", 0x1, NULL },
    { "getBackgroundPainterWithNSString:", NULL, "LRAREiBackgroundPainter", 0x1, NULL },
    { "getBooleanWithNSString:withBoolean:", NULL, "Z", 0x1, NULL },
    { "getBorderWithNSString:", NULL, "LRAREiPlatformBorder", 0x1, NULL },
    { "getColorWithNSString:", NULL, "LRAREUIColor", 0x1, NULL },
    { "getFontWithNSString:", NULL, "LRAREUIFont", 0x1, NULL },
    { "getIconWithNSString:", NULL, "LRAREiPlatformIcon", 0x1, NULL },
    { "getImageWithNSString:", NULL, "LRAREUIImage", 0x1, NULL },
    { "getImageIconWithNSString:", NULL, "LRAREUIImageIcon", 0x1, NULL },
    { "getInsetsWithNSString:", NULL, "LRAREUIInsets", 0x1, NULL },
    { "getIntegerWithNSString:", NULL, "LJavaLangInteger", 0x1, NULL },
    { "getPixelsWithNSString:", NULL, "LJavaLangInteger", 0x1, NULL },
    { "getPaintBucketWithNSString:", NULL, "LRAREPaintBucket", 0x1, NULL },
    { "getPainterWithNSString:", NULL, "LRAREiPlatformPainter", 0x1, NULL },
    { "getStringWithNSString:", NULL, "LNSString", 0x1, NULL },
    { "isEmpty", NULL, "Z", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "map_", NULL, 0x4, "LJavaUtilMap" },
  };
  static J2ObjcClassInfo _RAREaUIProperties = { "aUIProperties", "com.appnativa.rare.ui", NULL, 0x401, 17, methods, 1, fields, 0, NULL};
  return &_RAREaUIProperties;
}

@end
