//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/jgoodies/forms/layout/ColumnSpec.java
//
//  Created by decoteaud on 3/11/16.
//

#include "IOSClass.h"
#include "IOSObjectArray.h"
#include "com/appnativa/jgoodies/forms/layout/ColumnSpec.h"
#include "com/appnativa/jgoodies/forms/layout/ConstantSize.h"
#include "com/appnativa/jgoodies/forms/layout/FormSpec.h"
#include "com/appnativa/jgoodies/forms/layout/FormSpecParser.h"
#include "com/appnativa/jgoodies/forms/layout/LayoutMap.h"
#include "com/appnativa/jgoodies/forms/layout/Size.h"
#include "com/appnativa/jgoodies/forms/util/FormUtils.h"
#include "java/lang/Deprecated.h"
#include "java/util/HashMap.h"
#include "java/util/Locale.h"
#include "java/util/Map.h"

@implementation RAREColumnSpec

static RAREFormSpec_DefaultAlignment * RAREColumnSpec_LEFT_;
static RAREFormSpec_DefaultAlignment * RAREColumnSpec_CENTER_;
static RAREFormSpec_DefaultAlignment * RAREColumnSpec_MIDDLE_;
static RAREFormSpec_DefaultAlignment * RAREColumnSpec_RIGHT_;
static RAREFormSpec_DefaultAlignment * RAREColumnSpec_FILL_;
static RAREFormSpec_DefaultAlignment * RAREColumnSpec_DEFAULT_;
static id<JavaUtilMap> RAREColumnSpec_CACHE_;

+ (RAREFormSpec_DefaultAlignment *)LEFT {
  return RAREColumnSpec_LEFT_;
}

+ (RAREFormSpec_DefaultAlignment *)CENTER {
  return RAREColumnSpec_CENTER_;
}

+ (RAREFormSpec_DefaultAlignment *)MIDDLE {
  return RAREColumnSpec_MIDDLE_;
}

+ (RAREFormSpec_DefaultAlignment *)RIGHT {
  return RAREColumnSpec_RIGHT_;
}

+ (RAREFormSpec_DefaultAlignment *)FILL {
  return RAREColumnSpec_FILL_;
}

+ (RAREFormSpec_DefaultAlignment *)DEFAULT {
  return RAREColumnSpec_DEFAULT_;
}

+ (id<JavaUtilMap>)CACHE {
  return RAREColumnSpec_CACHE_;
}

- (id)initWithRAREFormSpec_DefaultAlignment:(RAREFormSpec_DefaultAlignment *)defaultAlignment
                               withRARESize:(id<RARESize>)size
                                 withDouble:(double)resizeWeight {
  return [super initWithRAREFormSpec_DefaultAlignment:defaultAlignment withRARESize:size withDouble:resizeWeight];
}

- (id)initWithRARESize:(id<RARESize>)size {
  return [super initWithRAREFormSpec_DefaultAlignment:RAREColumnSpec_DEFAULT_ withRARESize:size withDouble:RAREFormSpec_NO_GROW];
}

- (id)initWithNSString:(NSString *)encodedDescription {
  return [super initWithRAREFormSpec_DefaultAlignment:RAREColumnSpec_DEFAULT_ withNSString:encodedDescription];
}

+ (RAREColumnSpec *)createGapWithRAREConstantSize:(RAREConstantSize *)gapWidth {
  return [[RAREColumnSpec alloc] initWithRAREFormSpec_DefaultAlignment:RAREColumnSpec_DEFAULT_ withRARESize:gapWidth withDouble:RAREFormSpec_NO_GROW];
}

+ (RAREColumnSpec *)decodeWithNSString:(NSString *)encodedColumnSpec {
  return [RAREColumnSpec decodeWithNSString:encodedColumnSpec withRARELayoutMap:[RARELayoutMap getRoot]];
}

+ (RAREColumnSpec *)decodeWithNSString:(NSString *)encodedColumnSpec
                     withRARELayoutMap:(RARELayoutMap *)layoutMap {
  [RAREFormUtils assertNotBlankWithNSString:encodedColumnSpec withNSString:@"encoded column specification"];
  [RAREFormUtils assertNotNullWithId:layoutMap withNSString:@"LayoutMap"];
  NSString *trimmed = [((NSString *) nil_chk(encodedColumnSpec)) trim];
  NSString *lower = [((NSString *) nil_chk(trimmed)) lowercaseStringWithJRELocale:[JavaUtilLocale ENGLISH]];
  return [RAREColumnSpec decodeExpandedWithNSString:[((RARELayoutMap *) nil_chk(layoutMap)) expandWithNSString:lower withBoolean:YES]];
}

+ (RAREColumnSpec *)decodeExpandedWithNSString:(NSString *)expandedTrimmedLowerCaseSpec {
  RAREColumnSpec *spec = (RAREColumnSpec *) check_class_cast([((id<JavaUtilMap>) nil_chk(RAREColumnSpec_CACHE_)) getWithId:expandedTrimmedLowerCaseSpec], [RAREColumnSpec class]);
  if (spec == nil) {
    spec = [[RAREColumnSpec alloc] initWithNSString:expandedTrimmedLowerCaseSpec];
    (void) [RAREColumnSpec_CACHE_ putWithId:expandedTrimmedLowerCaseSpec withId:spec];
  }
  return spec;
}

+ (IOSObjectArray *)decodeSpecsWithNSString:(NSString *)encodedColumnSpecs {
  return [RAREColumnSpec decodeSpecsWithNSString:encodedColumnSpecs withRARELayoutMap:[RARELayoutMap getRoot]];
}

+ (IOSObjectArray *)decodeSpecsWithNSString:(NSString *)encodedColumnSpecs
                          withRARELayoutMap:(RARELayoutMap *)layoutMap {
  return [RAREFormSpecParser parseColumnSpecsWithNSString:encodedColumnSpecs withRARELayoutMap:layoutMap];
}

- (BOOL)isHorizontal {
  return YES;
}

+ (void)initialize {
  if (self == [RAREColumnSpec class]) {
    RAREColumnSpec_LEFT_ = [RAREFormSpec LEFT_ALIGN];
    RAREColumnSpec_CENTER_ = [RAREFormSpec CENTER_ALIGN];
    RAREColumnSpec_MIDDLE_ = RAREColumnSpec_CENTER_;
    RAREColumnSpec_RIGHT_ = [RAREFormSpec RIGHT_ALIGN];
    RAREColumnSpec_FILL_ = [RAREFormSpec FILL_ALIGN];
    RAREColumnSpec_DEFAULT_ = RAREColumnSpec_FILL_;
    RAREColumnSpec_CACHE_ = [[JavaUtilHashMap alloc] init];
  }
}

+ (IOSObjectArray *)__annotations_RAREColumnSpecWithNSString_ {
  return [IOSObjectArray arrayWithObjects:(id[]) { [[JavaLangDeprecated alloc] init] } count:1 type:[IOSClass classWithProtocol:@protocol(JavaLangAnnotationAnnotation)]];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "createGapWithRAREConstantSize:", NULL, "LRAREColumnSpec", 0x9, NULL },
    { "decodeWithNSString:", NULL, "LRAREColumnSpec", 0x9, NULL },
    { "decodeWithNSString:withRARELayoutMap:", NULL, "LRAREColumnSpec", 0x9, NULL },
    { "decodeExpandedWithNSString:", NULL, "LRAREColumnSpec", 0x8, NULL },
    { "decodeSpecsWithNSString:", NULL, "LIOSObjectArray", 0x9, NULL },
    { "decodeSpecsWithNSString:withRARELayoutMap:", NULL, "LIOSObjectArray", 0x9, NULL },
    { "isHorizontal", NULL, "Z", 0x4, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "LEFT_", NULL, 0x19, "LRAREFormSpec_DefaultAlignment" },
    { "CENTER_", NULL, 0x19, "LRAREFormSpec_DefaultAlignment" },
    { "MIDDLE_", NULL, 0x19, "LRAREFormSpec_DefaultAlignment" },
    { "RIGHT_", NULL, 0x19, "LRAREFormSpec_DefaultAlignment" },
    { "FILL_", NULL, 0x19, "LRAREFormSpec_DefaultAlignment" },
    { "DEFAULT_", NULL, 0x19, "LRAREFormSpec_DefaultAlignment" },
    { "CACHE_", NULL, 0x1a, "LJavaUtilMap" },
  };
  static J2ObjcClassInfo _RAREColumnSpec = { "ColumnSpec", "com.appnativa.jgoodies.forms.layout", NULL, 0x11, 7, methods, 7, fields, 0, NULL};
  return &_RAREColumnSpec;
}

@end
