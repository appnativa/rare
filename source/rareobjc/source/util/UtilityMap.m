//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../util/src/com/appnativa/util/UtilityMap.java
//
//  Created by decoteaud on 7/29/15.
//

#include "com/appnativa/util/SNumber.h"
#include "com/appnativa/util/UtilityMap.h"
#include "java/lang/Boolean.h"
#include "java/util/Collection.h"
#include "java/util/Map.h"
#include "java/util/Set.h"

@implementation RAREUTUtilityMap

- (id)initWithJavaUtilMap:(id<JavaUtilMap>)map {
  if (self = [super init]) {
    self->map_ = map;
  }
  return self;
}

- (id)initWithJavaUtilMap:(id<JavaUtilMap>)map
             withNSString:(NSString *)keyPrefix {
  if (self = [super init]) {
    self->map_ = map;
    self->keyPrefix_ = keyPrefix;
  }
  return self;
}

- (void)clear {
  [((id<JavaUtilMap>) nil_chk(map_)) clear];
}

- (BOOL)containsKeyWithId:(id)key {
  if (keyPrefix_ != nil) {
    key = [NSString stringWithFormat:@"%@%@", keyPrefix_, [nil_chk(key) description]];
  }
  return [((id<JavaUtilMap>) nil_chk(map_)) containsKeyWithId:key];
}

- (BOOL)containsValueWithId:(id)value {
  return [((id<JavaUtilMap>) nil_chk(map_)) containsValueWithId:value];
}

- (id<JavaUtilSet>)entrySet {
  return [((id<JavaUtilMap>) nil_chk(map_)) entrySet];
}

- (BOOL)isEqual:(id)o {
  return [((id<JavaUtilMap>) nil_chk(map_)) isEqual:o];
}

- (NSUInteger)hash {
  return [((id<JavaUtilMap>) nil_chk(map_)) hash];
}

- (id<JavaUtilSet>)keySet {
  return [((id<JavaUtilMap>) nil_chk(map_)) keySet];
}

- (id)putWithId:(id)key
         withId:(id)value {
  return [((id<JavaUtilMap>) nil_chk([self getMap])) putWithId:key withId:value];
}

- (void)putAllWithJavaUtilMap:(id<JavaUtilMap>)m {
  [((id<JavaUtilMap>) nil_chk([self getMap])) putAllWithJavaUtilMap:m];
}

- (id)removeWithId:(id)key {
  if (keyPrefix_ != nil) {
    key = [NSString stringWithFormat:@"%@%@", keyPrefix_, [nil_chk(key) description]];
  }
  return [((id<JavaUtilMap>) nil_chk([self getMap])) removeWithId:key];
}

- (BOOL)removeBooleanWithId:(id)key
                withBoolean:(BOOL)def {
  id o = [self removeWithId:key];
  if (o == nil) {
    return def;
  }
  if ([o isKindOfClass:[JavaLangBoolean class]]) {
    return [((JavaLangBoolean *) check_class_cast(o, [JavaLangBoolean class])) booleanValue];
  }
  return [nil_chk(o) isEqual:@"true"];
}

- (float)removeFloatWithId:(id)key
                 withFloat:(float)def {
  id o = [self removeWithId:key];
  if (o == nil) {
    return def;
  }
  if ([o isKindOfClass:[NSNumber class]]) {
    return [((NSNumber *) check_class_cast(o, [NSNumber class])) floatValue];
  }
  return [RAREUTSNumber floatValueWithNSString:[nil_chk(o) description]];
}

- (double)removeIntWithId:(id)key
               withDouble:(double)def {
  id o = [self removeWithId:key];
  if (o == nil) {
    return def;
  }
  if ([o isKindOfClass:[NSNumber class]]) {
    return [((NSNumber *) check_class_cast(o, [NSNumber class])) doubleValue];
  }
  return [RAREUTSNumber doubleValueWithNSString:[nil_chk(o) description]];
}

- (int)removeIntWithId:(id)key
               withInt:(int)def {
  id o = [self removeWithId:key];
  if (o == nil) {
    return def;
  }
  if ([o isKindOfClass:[NSNumber class]]) {
    return [((NSNumber *) check_class_cast(o, [NSNumber class])) intValue];
  }
  return [RAREUTSNumber intValueWithNSString:[nil_chk(o) description]];
}

- (long long int)removeLongWithId:(id)key
                         withLong:(long long int)def {
  id o = [self removeWithId:key];
  if (o == nil) {
    return def;
  }
  if ([o isKindOfClass:[NSNumber class]]) {
    return [((NSNumber *) check_class_cast(o, [NSNumber class])) longLongValue];
  }
  return [RAREUTSNumber longValueWithNSString:[nil_chk(o) description]];
}

- (NSString *)removeStringWithId:(id)key {
  id o = [self removeWithId:key];
  if (o == nil) {
    return nil;
  }
  if ([o isKindOfClass:[NSString class]]) {
    return ((NSString *) check_class_cast(o, [NSString class]));
  }
  return [nil_chk(o) description];
}

- (int)size {
  return [((id<JavaUtilMap>) nil_chk(map_)) size];
}

- (id<JavaUtilCollection>)values {
  return [((id<JavaUtilMap>) nil_chk(map_)) values];
}

- (void)setKeyPrefixWithNSString:(NSString *)keyPrefix {
  self->keyPrefix_ = keyPrefix;
}

- (void)setMapWithJavaUtilMap:(id<JavaUtilMap>)map {
  self->map_ = map;
}

- (id)getWithId:(id)key {
  if (keyPrefix_ != nil) {
    key = [NSString stringWithFormat:@"%@%@", keyPrefix_, [nil_chk(key) description]];
  }
  return [((id<JavaUtilMap>) nil_chk(map_)) getWithId:key];
}

- (BOOL)getBooleanWithId:(id)key
             withBoolean:(BOOL)def {
  id o = [self getWithId:key];
  if (o == nil) {
    return def;
  }
  if ([o isKindOfClass:[JavaLangBoolean class]]) {
    return [((JavaLangBoolean *) check_class_cast(o, [JavaLangBoolean class])) booleanValue];
  }
  return [nil_chk(o) isEqual:@"true"];
}

- (float)getFloatWithId:(id)key
              withFloat:(float)def {
  id o = [self getWithId:key];
  if (o == nil) {
    return def;
  }
  if ([o isKindOfClass:[NSNumber class]]) {
    return [((NSNumber *) check_class_cast(o, [NSNumber class])) floatValue];
  }
  return [RAREUTSNumber floatValueWithNSString:[nil_chk(o) description]];
}

- (double)getIntWithId:(id)key
            withDouble:(double)def {
  id o = [self getWithId:key];
  if (o == nil) {
    return def;
  }
  if ([o isKindOfClass:[NSNumber class]]) {
    return [((NSNumber *) check_class_cast(o, [NSNumber class])) doubleValue];
  }
  return [RAREUTSNumber doubleValueWithNSString:[nil_chk(o) description]];
}

- (int)getIntWithId:(id)key
            withInt:(int)def {
  id o = [self getWithId:key];
  if (o == nil) {
    return def;
  }
  if ([o isKindOfClass:[NSNumber class]]) {
    return [((NSNumber *) check_class_cast(o, [NSNumber class])) intValue];
  }
  return [RAREUTSNumber intValueWithNSString:[nil_chk(o) description]];
}

- (NSString *)getKeyPrefix {
  return keyPrefix_;
}

- (long long int)getLongWithId:(id)key
                      withLong:(long long int)def {
  id o = [self getWithId:key];
  if (o == nil) {
    return def;
  }
  if ([o isKindOfClass:[NSNumber class]]) {
    return [((NSNumber *) check_class_cast(o, [NSNumber class])) longLongValue];
  }
  return [RAREUTSNumber longValueWithNSString:[nil_chk(o) description]];
}

- (id<JavaUtilMap>)getMap {
  return map_;
}

- (NSString *)getStringWithId:(id)key {
  id o = [self getWithId:key];
  if (o == nil) {
    return nil;
  }
  if ([o isKindOfClass:[NSString class]]) {
    return ((NSString *) check_class_cast(o, [NSString class]));
  }
  return [nil_chk(o) description];
}

- (BOOL)isEmpty {
  return [((id<JavaUtilMap>) nil_chk([self getMap])) isEmpty];
}

- (void)copyAllFieldsTo:(RAREUTUtilityMap *)other {
  [super copyAllFieldsTo:other];
  other->keyPrefix_ = keyPrefix_;
  other->map_ = map_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "containsKeyWithId:", NULL, "Z", 0x1, NULL },
    { "containsValueWithId:", NULL, "Z", 0x1, NULL },
    { "entrySet", NULL, "LJavaUtilSet", 0x1, NULL },
    { "keySet", NULL, "LJavaUtilSet", 0x1, NULL },
    { "putWithId:withId:", NULL, "LNSObject", 0x1, NULL },
    { "removeWithId:", NULL, "LNSObject", 0x1, NULL },
    { "removeBooleanWithId:withBoolean:", NULL, "Z", 0x1, NULL },
    { "removeStringWithId:", NULL, "LNSString", 0x1, NULL },
    { "values", NULL, "LJavaUtilCollection", 0x1, NULL },
    { "getWithId:", NULL, "LNSObject", 0x1, NULL },
    { "getBooleanWithId:withBoolean:", NULL, "Z", 0x1, NULL },
    { "getKeyPrefix", NULL, "LNSString", 0x1, NULL },
    { "getMap", NULL, "LJavaUtilMap", 0x1, NULL },
    { "getStringWithId:", NULL, "LNSString", 0x1, NULL },
    { "isEmpty", NULL, "Z", 0x1, NULL },
  };
  static J2ObjcClassInfo _RAREUTUtilityMap = { "UtilityMap", "com.appnativa.util", NULL, 0x1, 15, methods, 0, NULL, 0, NULL};
  return &_RAREUTUtilityMap;
}

@end
