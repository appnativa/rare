//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/Projects/rare/core-apple/javax/script/SimpleBindings.java
//
//  Created by decoteaud on 6/13/14.
//

#include "java/lang/ClassCastException.h"
#include "java/lang/IllegalArgumentException.h"
#include "java/lang/NullPointerException.h"
#include "java/util/Collection.h"
#include "java/util/HashMap.h"
#include "java/util/Iterator.h"
#include "java/util/Map.h"
#include "java/util/Set.h"
#include "javax/script/SimpleBindings.h"

@implementation JavaxScriptSimpleBindings

- (id)init {
  if (self = [super init]) {
    map_ = [[JavaUtilHashMap alloc] init];
  }
  return self;
}

- (id)initWithJavaUtilMap:(id<JavaUtilMap>)map {
  if (self = [super init]) {
    if (map == nil) {
      @throw [[JavaLangNullPointerException alloc] initWithNSString:@"parameter must not be null"];
    }
    self->map_ = map;
  }
  return self;
}

- (void)validateKeyWithId:(id)key {
  if (key == nil) {
    @throw [[JavaLangNullPointerException alloc] initWithNSString:@"key must not be null"];
  }
  if (!([key isKindOfClass:[NSString class]])) {
    @throw [[JavaLangClassCastException alloc] initWithNSString:@"key must be a String"];
  }
  if ([((NSString *) check_class_cast(key, [NSString class])) sequenceLength] == 0) {
    @throw [[JavaLangIllegalArgumentException alloc] initWithNSString:@"key must not be the empty string"];
  }
}

- (id)putWithId:(NSString *)key
         withId:(id)value {
  [self validateKeyWithId:key];
  return [((id<JavaUtilMap>) nil_chk(map_)) putWithId:key withId:value];
}

- (void)putAllWithJavaUtilMap:(id<JavaUtilMap>)toMerge {
  id<JavaUtilSet> keySet = [((id<JavaUtilMap>) nil_chk(toMerge)) keySet];
  id<JavaUtilIterator> keys = [((id<JavaUtilSet>) nil_chk(keySet)) iterator];
  while ([((id<JavaUtilIterator>) nil_chk(keys)) hasNext]) {
    [self validateKeyWithId:[keys next]];
  }
  [((id<JavaUtilMap>) nil_chk(map_)) putAllWithJavaUtilMap:toMerge];
}

- (int)size {
  return [((id<JavaUtilMap>) nil_chk(map_)) size];
}

- (void)clear {
  [((id<JavaUtilMap>) nil_chk(map_)) clear];
}

- (BOOL)isEmpty {
  return [((id<JavaUtilMap>) nil_chk(map_)) isEmpty];
}

- (BOOL)containsKeyWithId:(id)key {
  [self validateKeyWithId:key];
  return [((id<JavaUtilMap>) nil_chk(map_)) containsKeyWithId:key];
}

- (BOOL)containsValueWithId:(id)value {
  return [((id<JavaUtilMap>) nil_chk(map_)) containsValueWithId:value];
}

- (id<JavaUtilCollection>)values {
  return [((id<JavaUtilMap>) nil_chk(map_)) values];
}

- (id<JavaUtilSet>)entrySet {
  return [((id<JavaUtilMap>) nil_chk(map_)) entrySet];
}

- (id)getWithId:(id)key {
  [self validateKeyWithId:key];
  return [((id<JavaUtilMap>) nil_chk(map_)) getWithId:key];
}

- (id<JavaUtilSet>)keySet {
  return [((id<JavaUtilMap>) nil_chk(map_)) keySet];
}

- (id)removeWithId:(id)key {
  [self validateKeyWithId:key];
  return [((id<JavaUtilMap>) nil_chk(map_)) removeWithId:key];
}

- (void)copyAllFieldsTo:(JavaxScriptSimpleBindings *)other {
  [super copyAllFieldsTo:other];
  other->map_ = map_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "validateKeyWithId:", NULL, "V", 0x2, NULL },
    { "putWithNSString:withId:", NULL, "LNSObject", 0x1, NULL },
    { "isEmpty", NULL, "Z", 0x1, NULL },
    { "containsKeyWithId:", NULL, "Z", 0x1, NULL },
    { "containsValueWithId:", NULL, "Z", 0x1, NULL },
    { "values", NULL, "LJavaUtilCollection", 0x1, NULL },
    { "entrySet", NULL, "LJavaUtilSet", 0x1, NULL },
    { "getWithId:", NULL, "LNSObject", 0x1, NULL },
    { "keySet", NULL, "LJavaUtilSet", 0x1, NULL },
    { "removeWithId:", NULL, "LNSObject", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "map_", NULL, 0x12, "LJavaUtilMap" },
  };
  static J2ObjcClassInfo _JavaxScriptSimpleBindings = { "SimpleBindings", "javax.script", NULL, 0x1, 10, methods, 1, fields, 0, NULL};
  return &_JavaxScriptSimpleBindings;
}

@end