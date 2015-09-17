//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../util/src-apple-porting/com/appnativa/util/PropertyResourceBundle.java
//
//  Created by decoteaud on 9/15/15.
//

#include "com/appnativa/util/OrderedProperties.h"
#include "com/appnativa/util/PropertyResourceBundle.h"
#include "java/util/Enumeration.h"
#include "java/util/ResourceBundle.h"
#include "java/util/Set.h"

@implementation RAREUTPropertyResourceBundle

- (id)initWithRAREUTOrderedProperties:(RAREUTOrderedProperties *)properties {
  if (self = [super init]) {
    resources_ = properties;
  }
  return self;
}

- (id)handleGetObjectWithNSString:(NSString *)key {
  return [((RAREUTOrderedProperties *) nil_chk(resources_)) getWithId:key];
}

- (id<JavaUtilEnumeration>)getKeys {
  if (parent_ == nil) {
    return [self getLocalKeys];
  }
  return [[RAREUTPropertyResourceBundle_$1 alloc] initWithRAREUTPropertyResourceBundle:self];
}

- (id<JavaUtilEnumeration>)getLocalKeys {
  return (id<JavaUtilEnumeration>) check_protocol_cast([((RAREUTOrderedProperties *) nil_chk(resources_)) keySet], @protocol(JavaUtilEnumeration));
}

- (void)copyAllFieldsTo:(RAREUTPropertyResourceBundle *)other {
  [super copyAllFieldsTo:other];
  other->resources_ = resources_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "handleGetObjectWithNSString:", NULL, "LNSObject", 0x1, NULL },
    { "getKeys", NULL, "LJavaUtilEnumeration", 0x1, NULL },
    { "getLocalKeys", NULL, "LJavaUtilEnumeration", 0x2, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "resources_", NULL, 0x0, "LRAREUTOrderedProperties" },
  };
  static J2ObjcClassInfo _RAREUTPropertyResourceBundle = { "PropertyResourceBundle", "com.appnativa.util", NULL, 0x1, 3, methods, 1, fields, 0, NULL};
  return &_RAREUTPropertyResourceBundle;
}

@end
@implementation RAREUTPropertyResourceBundle_$1

- (BOOL)findNext {
  if (nextElement__ != nil) {
    return YES;
  }
  while ([((id<JavaUtilEnumeration>) nil_chk(pEnum_)) hasMoreElements]) {
    NSString *next = [pEnum_ nextElement];
    if (![((RAREUTOrderedProperties *) nil_chk(this$0_->resources_)) containsKeyWithId:next]) {
      nextElement__ = next;
      return YES;
    }
  }
  return NO;
}

- (BOOL)hasMoreElements {
  if ([((id<JavaUtilEnumeration>) nil_chk(local_)) hasMoreElements]) {
    return YES;
  }
  return [self findNext];
}

- (NSString *)nextElement {
  if ([((id<JavaUtilEnumeration>) nil_chk(local_)) hasMoreElements]) {
    return [local_ nextElement];
  }
  if ([self findNext]) {
    NSString *result = nextElement__;
    nextElement__ = nil;
    return result;
  }
  return [((id<JavaUtilEnumeration>) nil_chk(pEnum_)) nextElement];
}

- (id)initWithRAREUTPropertyResourceBundle:(RAREUTPropertyResourceBundle *)outer$ {
  this$0_ = outer$;
  if (self = [super init]) {
    local_ = [outer$ getLocalKeys];
    pEnum_ = [((JavaUtilResourceBundle *) nil_chk(outer$->parent_)) getKeys];
  }
  return self;
}

- (void)copyAllFieldsTo:(RAREUTPropertyResourceBundle_$1 *)other {
  [super copyAllFieldsTo:other];
  other->local_ = local_;
  other->nextElement__ = nextElement__;
  other->pEnum_ = pEnum_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "findNext", NULL, "Z", 0x2, NULL },
    { "hasMoreElements", NULL, "Z", 0x1, NULL },
    { "nextElement", NULL, "LNSString", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "this$0_", NULL, 0x1012, "LRAREUTPropertyResourceBundle" },
    { "local_", NULL, 0x0, "LJavaUtilEnumeration" },
    { "pEnum_", NULL, 0x0, "LJavaUtilEnumeration" },
    { "nextElement__", "nextElement", 0x0, "LNSString" },
  };
  static J2ObjcClassInfo _RAREUTPropertyResourceBundle_$1 = { "$1", "com.appnativa.util", "PropertyResourceBundle", 0x8000, 3, methods, 4, fields, 0, NULL};
  return &_RAREUTPropertyResourceBundle_$1;
}

@end
