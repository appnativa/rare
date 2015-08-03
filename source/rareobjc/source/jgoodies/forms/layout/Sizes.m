//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/jgoodies/forms/layout/Sizes.java
//
//  Created by decoteaud on 5/11/15.
//

#include "IOSClass.h"
#include "IOSObjectArray.h"
#include "com/appnativa/rare/ui/iParentComponent.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "com/jgoodies/forms/layout/BoundedSize.h"
#include "com/jgoodies/forms/layout/ConstantSize.h"
#include "com/jgoodies/forms/layout/FormLayout.h"
#include "com/jgoodies/forms/layout/Size.h"
#include "com/jgoodies/forms/layout/Sizes.h"
#include "com/jgoodies/forms/util/DefaultUnitConverter.h"
#include "com/jgoodies/forms/util/UnitConverter.h"
#include "java/lang/IllegalArgumentException.h"
#include "java/lang/Math.h"
#include "java/util/Iterator.h"
#include "java/util/List.h"
#include "java/util/Locale.h"

@implementation RAREJGSizes

static RAREJGConstantSize * RAREJGSizes_ZERO_;
static RAREJGConstantSize * RAREJGSizes_DLUX1_;
static RAREJGConstantSize * RAREJGSizes_DLUX2_;
static RAREJGConstantSize * RAREJGSizes_DLUX3_;
static RAREJGConstantSize * RAREJGSizes_DLUX4_;
static RAREJGConstantSize * RAREJGSizes_DLUX5_;
static RAREJGConstantSize * RAREJGSizes_DLUX6_;
static RAREJGConstantSize * RAREJGSizes_DLUX7_;
static RAREJGConstantSize * RAREJGSizes_DLUX8_;
static RAREJGConstantSize * RAREJGSizes_DLUX9_;
static RAREJGConstantSize * RAREJGSizes_DLUX11_;
static RAREJGConstantSize * RAREJGSizes_DLUX14_;
static RAREJGConstantSize * RAREJGSizes_DLUX21_;
static RAREJGConstantSize * RAREJGSizes_DLUY1_;
static RAREJGConstantSize * RAREJGSizes_DLUY2_;
static RAREJGConstantSize * RAREJGSizes_DLUY3_;
static RAREJGConstantSize * RAREJGSizes_DLUY4_;
static RAREJGConstantSize * RAREJGSizes_DLUY5_;
static RAREJGConstantSize * RAREJGSizes_DLUY6_;
static RAREJGConstantSize * RAREJGSizes_DLUY7_;
static RAREJGConstantSize * RAREJGSizes_DLUY8_;
static RAREJGConstantSize * RAREJGSizes_DLUY9_;
static RAREJGConstantSize * RAREJGSizes_DLUY11_;
static RAREJGConstantSize * RAREJGSizes_DLUY14_;
static RAREJGConstantSize * RAREJGSizes_DLUY21_;
static RAREJGSizes_ComponentSize * RAREJGSizes_MINIMUM_;
static RAREJGSizes_ComponentSize * RAREJGSizes_PREFERRED_;
static RAREJGSizes_ComponentSize * RAREJGSizes_DEFAULT_;
static IOSObjectArray * RAREJGSizes_VALUES_;
static id<RAREJGUnitConverter> RAREJGSizes_unitConverter_;
static RAREJGConstantSize_Unit * RAREJGSizes_defaultUnit_;

+ (RAREJGConstantSize *)ZERO {
  return RAREJGSizes_ZERO_;
}

+ (RAREJGConstantSize *)DLUX1 {
  return RAREJGSizes_DLUX1_;
}

+ (RAREJGConstantSize *)DLUX2 {
  return RAREJGSizes_DLUX2_;
}

+ (RAREJGConstantSize *)DLUX3 {
  return RAREJGSizes_DLUX3_;
}

+ (RAREJGConstantSize *)DLUX4 {
  return RAREJGSizes_DLUX4_;
}

+ (RAREJGConstantSize *)DLUX5 {
  return RAREJGSizes_DLUX5_;
}

+ (RAREJGConstantSize *)DLUX6 {
  return RAREJGSizes_DLUX6_;
}

+ (RAREJGConstantSize *)DLUX7 {
  return RAREJGSizes_DLUX7_;
}

+ (RAREJGConstantSize *)DLUX8 {
  return RAREJGSizes_DLUX8_;
}

+ (RAREJGConstantSize *)DLUX9 {
  return RAREJGSizes_DLUX9_;
}

+ (RAREJGConstantSize *)DLUX11 {
  return RAREJGSizes_DLUX11_;
}

+ (RAREJGConstantSize *)DLUX14 {
  return RAREJGSizes_DLUX14_;
}

+ (RAREJGConstantSize *)DLUX21 {
  return RAREJGSizes_DLUX21_;
}

+ (RAREJGConstantSize *)DLUY1 {
  return RAREJGSizes_DLUY1_;
}

+ (RAREJGConstantSize *)DLUY2 {
  return RAREJGSizes_DLUY2_;
}

+ (RAREJGConstantSize *)DLUY3 {
  return RAREJGSizes_DLUY3_;
}

+ (RAREJGConstantSize *)DLUY4 {
  return RAREJGSizes_DLUY4_;
}

+ (RAREJGConstantSize *)DLUY5 {
  return RAREJGSizes_DLUY5_;
}

+ (RAREJGConstantSize *)DLUY6 {
  return RAREJGSizes_DLUY6_;
}

+ (RAREJGConstantSize *)DLUY7 {
  return RAREJGSizes_DLUY7_;
}

+ (RAREJGConstantSize *)DLUY8 {
  return RAREJGSizes_DLUY8_;
}

+ (RAREJGConstantSize *)DLUY9 {
  return RAREJGSizes_DLUY9_;
}

+ (RAREJGConstantSize *)DLUY11 {
  return RAREJGSizes_DLUY11_;
}

+ (RAREJGConstantSize *)DLUY14 {
  return RAREJGSizes_DLUY14_;
}

+ (RAREJGConstantSize *)DLUY21 {
  return RAREJGSizes_DLUY21_;
}

+ (RAREJGSizes_ComponentSize *)MINIMUM {
  return RAREJGSizes_MINIMUM_;
}

+ (RAREJGSizes_ComponentSize *)PREFERRED {
  return RAREJGSizes_PREFERRED_;
}

+ (RAREJGSizes_ComponentSize *)DEFAULT {
  return RAREJGSizes_DEFAULT_;
}

+ (IOSObjectArray *)VALUES {
  return RAREJGSizes_VALUES_;
}

+ (id<RAREJGUnitConverter>)unitConverter {
  return RAREJGSizes_unitConverter_;
}

+ (void)setUnitConverter:(id<RAREJGUnitConverter>)unitConverter {
  RAREJGSizes_unitConverter_ = unitConverter;
}

+ (RAREJGConstantSize_Unit *)defaultUnit {
  return RAREJGSizes_defaultUnit_;
}

+ (void)setDefaultUnit:(RAREJGConstantSize_Unit *)defaultUnit {
  RAREJGSizes_defaultUnit_ = defaultUnit;
}

- (id)init {
  return [super init];
}

+ (RAREJGConstantSize *)constantWithNSString:(NSString *)encodedValueAndUnit
                                 withBoolean:(BOOL)horizontal {
  NSString *lowerCase = [((NSString *) nil_chk(encodedValueAndUnit)) lowercaseStringWithJRELocale:[JavaUtilLocale ENGLISH]];
  NSString *trimmed = [((NSString *) nil_chk(lowerCase)) trim];
  return [RAREJGConstantSize valueOfWithNSString:trimmed withBoolean:horizontal];
}

+ (RAREJGConstantSize *)dluXWithInt:(int)value {
  return [RAREJGConstantSize dluXWithInt:value];
}

+ (RAREJGConstantSize *)dluYWithInt:(int)value {
  return [RAREJGConstantSize dluYWithInt:value];
}

+ (RAREJGConstantSize *)pixelWithInt:(int)value {
  return [[RAREJGConstantSize alloc] initWithInt:value withRAREJGConstantSize_Unit:[RAREJGConstantSize PIXEL]];
}

+ (id<RAREJGSize>)boundedWithRAREJGSize:(id<RAREJGSize>)basis
                         withRAREJGSize:(id<RAREJGSize>)lowerBound
                         withRAREJGSize:(id<RAREJGSize>)upperBound {
  return [[RAREJGBoundedSize alloc] initWithRAREJGSize:basis withRAREJGSize:lowerBound withRAREJGSize:upperBound];
}

+ (int)inchAsPixelWithDouble:(double)inArg
  withRAREiPlatformComponent:(id<RAREiPlatformComponent>)component {
  return inArg == 0.0 ? 0 : [((id<RAREJGUnitConverter>) nil_chk([RAREJGSizes getUnitConverter])) inchAsPixelWithDouble:inArg withRAREiPlatformComponent:component];
}

+ (int)millimeterAsPixelWithDouble:(double)mm
        withRAREiPlatformComponent:(id<RAREiPlatformComponent>)component {
  return mm == 0.0 ? 0 : [((id<RAREJGUnitConverter>) nil_chk([RAREJGSizes getUnitConverter])) millimeterAsPixelWithDouble:mm withRAREiPlatformComponent:component];
}

+ (int)centimeterAsPixelWithDouble:(double)cm
        withRAREiPlatformComponent:(id<RAREiPlatformComponent>)component {
  return cm == 0.0 ? 0 : [((id<RAREJGUnitConverter>) nil_chk([RAREJGSizes getUnitConverter])) centimeterAsPixelWithDouble:cm withRAREiPlatformComponent:component];
}

+ (int)pointAsPixelWithInt:(int)pt
withRAREiPlatformComponent:(id<RAREiPlatformComponent>)component {
  return pt == 0 ? 0 : [((id<RAREJGUnitConverter>) nil_chk([RAREJGSizes getUnitConverter])) pointAsPixelWithInt:pt withRAREiPlatformComponent:component];
}

+ (int)dialogUnitXAsPixelWithInt:(int)dluX
      withRAREiPlatformComponent:(id<RAREiPlatformComponent>)component {
  return dluX == 0 ? 0 : [((id<RAREJGUnitConverter>) nil_chk([RAREJGSizes getUnitConverter])) dialogUnitXAsPixelWithInt:dluX withRAREiPlatformComponent:component];
}

+ (int)dialogUnitYAsPixelWithInt:(int)dluY
      withRAREiPlatformComponent:(id<RAREiPlatformComponent>)component {
  return dluY == 0 ? 0 : [((id<RAREJGUnitConverter>) nil_chk([RAREJGSizes getUnitConverter])) dialogUnitYAsPixelWithInt:dluY withRAREiPlatformComponent:component];
}

+ (id<RAREJGUnitConverter>)getUnitConverter {
  if (RAREJGSizes_unitConverter_ == nil) {
    RAREJGSizes_unitConverter_ = [RAREJGDefaultUnitConverter getInstance];
  }
  return RAREJGSizes_unitConverter_;
}

+ (void)setUnitConverterWithRAREJGUnitConverter:(id<RAREJGUnitConverter>)newUnitConverter {
  RAREJGSizes_unitConverter_ = newUnitConverter;
}

+ (RAREJGConstantSize_Unit *)getDefaultUnit {
  return RAREJGSizes_defaultUnit_;
}

+ (void)setDefaultUnitWithRAREJGConstantSize_Unit:(RAREJGConstantSize_Unit *)unit {
  if ((unit == [RAREJGConstantSize DLUX]) || (unit == [RAREJGConstantSize DLUY])) {
    @throw [[JavaLangIllegalArgumentException alloc] initWithNSString:@"The unit must not be DLUX or DLUY. To use DLU as default unit, invoke this method with null."];
  }
  RAREJGSizes_defaultUnit_ = unit;
}

+ (void)initialize {
  if (self == [RAREJGSizes class]) {
    RAREJGSizes_ZERO_ = [RAREJGSizes pixelWithInt:0];
    RAREJGSizes_DLUX1_ = [RAREJGSizes dluXWithInt:1];
    RAREJGSizes_DLUX2_ = [RAREJGSizes dluXWithInt:2];
    RAREJGSizes_DLUX3_ = [RAREJGSizes dluXWithInt:3];
    RAREJGSizes_DLUX4_ = [RAREJGSizes dluXWithInt:4];
    RAREJGSizes_DLUX5_ = [RAREJGSizes dluXWithInt:5];
    RAREJGSizes_DLUX6_ = [RAREJGSizes dluXWithInt:6];
    RAREJGSizes_DLUX7_ = [RAREJGSizes dluXWithInt:7];
    RAREJGSizes_DLUX8_ = [RAREJGSizes dluXWithInt:8];
    RAREJGSizes_DLUX9_ = [RAREJGSizes dluXWithInt:9];
    RAREJGSizes_DLUX11_ = [RAREJGSizes dluXWithInt:11];
    RAREJGSizes_DLUX14_ = [RAREJGSizes dluXWithInt:14];
    RAREJGSizes_DLUX21_ = [RAREJGSizes dluXWithInt:21];
    RAREJGSizes_DLUY1_ = [RAREJGSizes dluYWithInt:1];
    RAREJGSizes_DLUY2_ = [RAREJGSizes dluYWithInt:2];
    RAREJGSizes_DLUY3_ = [RAREJGSizes dluYWithInt:3];
    RAREJGSizes_DLUY4_ = [RAREJGSizes dluYWithInt:4];
    RAREJGSizes_DLUY5_ = [RAREJGSizes dluYWithInt:5];
    RAREJGSizes_DLUY6_ = [RAREJGSizes dluYWithInt:6];
    RAREJGSizes_DLUY7_ = [RAREJGSizes dluYWithInt:7];
    RAREJGSizes_DLUY8_ = [RAREJGSizes dluYWithInt:8];
    RAREJGSizes_DLUY9_ = [RAREJGSizes dluYWithInt:9];
    RAREJGSizes_DLUY11_ = [RAREJGSizes dluYWithInt:11];
    RAREJGSizes_DLUY14_ = [RAREJGSizes dluYWithInt:14];
    RAREJGSizes_DLUY21_ = [RAREJGSizes dluYWithInt:21];
    RAREJGSizes_MINIMUM_ = [[RAREJGSizes_ComponentSize alloc] initWithNSString:@"minimum"];
    RAREJGSizes_PREFERRED_ = [[RAREJGSizes_ComponentSize alloc] initWithNSString:@"preferred"];
    RAREJGSizes_DEFAULT_ = [[RAREJGSizes_ComponentSize alloc] initWithNSString:@"default"];
    RAREJGSizes_VALUES_ = [IOSObjectArray arrayWithObjects:(id[]){ RAREJGSizes_MINIMUM_, RAREJGSizes_PREFERRED_, RAREJGSizes_DEFAULT_ } count:3 type:[IOSClass classWithClass:[RAREJGSizes_ComponentSize class]]];
    RAREJGSizes_defaultUnit_ = [RAREJGConstantSize PIXEL];
  }
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "init", NULL, NULL, 0x2, NULL },
    { "constantWithNSString:withBoolean:", NULL, "LRAREJGConstantSize", 0x9, NULL },
    { "dluXWithInt:", NULL, "LRAREJGConstantSize", 0x9, NULL },
    { "dluYWithInt:", NULL, "LRAREJGConstantSize", 0x9, NULL },
    { "pixelWithInt:", NULL, "LRAREJGConstantSize", 0x9, NULL },
    { "boundedWithRAREJGSize:withRAREJGSize:withRAREJGSize:", NULL, "LRAREJGSize", 0x9, NULL },
    { "getUnitConverter", NULL, "LRAREJGUnitConverter", 0x9, NULL },
    { "getDefaultUnit", NULL, "LRAREJGConstantSize_Unit", 0x9, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "ZERO_", NULL, 0x19, "LRAREJGConstantSize" },
    { "DLUX1_", NULL, 0x19, "LRAREJGConstantSize" },
    { "DLUX2_", NULL, 0x19, "LRAREJGConstantSize" },
    { "DLUX3_", NULL, 0x19, "LRAREJGConstantSize" },
    { "DLUX4_", NULL, 0x19, "LRAREJGConstantSize" },
    { "DLUX5_", NULL, 0x19, "LRAREJGConstantSize" },
    { "DLUX6_", NULL, 0x19, "LRAREJGConstantSize" },
    { "DLUX7_", NULL, 0x19, "LRAREJGConstantSize" },
    { "DLUX8_", NULL, 0x19, "LRAREJGConstantSize" },
    { "DLUX9_", NULL, 0x19, "LRAREJGConstantSize" },
    { "DLUX11_", NULL, 0x19, "LRAREJGConstantSize" },
    { "DLUX14_", NULL, 0x19, "LRAREJGConstantSize" },
    { "DLUX21_", NULL, 0x19, "LRAREJGConstantSize" },
    { "DLUY1_", NULL, 0x19, "LRAREJGConstantSize" },
    { "DLUY2_", NULL, 0x19, "LRAREJGConstantSize" },
    { "DLUY3_", NULL, 0x19, "LRAREJGConstantSize" },
    { "DLUY4_", NULL, 0x19, "LRAREJGConstantSize" },
    { "DLUY5_", NULL, 0x19, "LRAREJGConstantSize" },
    { "DLUY6_", NULL, 0x19, "LRAREJGConstantSize" },
    { "DLUY7_", NULL, 0x19, "LRAREJGConstantSize" },
    { "DLUY8_", NULL, 0x19, "LRAREJGConstantSize" },
    { "DLUY9_", NULL, 0x19, "LRAREJGConstantSize" },
    { "DLUY11_", NULL, 0x19, "LRAREJGConstantSize" },
    { "DLUY14_", NULL, 0x19, "LRAREJGConstantSize" },
    { "DLUY21_", NULL, 0x19, "LRAREJGConstantSize" },
    { "MINIMUM_", NULL, 0x19, "LRAREJGSizes_ComponentSize" },
    { "PREFERRED_", NULL, 0x19, "LRAREJGSizes_ComponentSize" },
    { "DEFAULT_", NULL, 0x19, "LRAREJGSizes_ComponentSize" },
    { "VALUES_", NULL, 0x1a, "LIOSObjectArray" },
    { "unitConverter_", NULL, 0xa, "LRAREJGUnitConverter" },
    { "defaultUnit_", NULL, 0xa, "LRAREJGConstantSize_Unit" },
  };
  static J2ObjcClassInfo _RAREJGSizes = { "Sizes", "com.jgoodies.forms.layout", NULL, 0x11, 8, methods, 31, fields, 0, NULL};
  return &_RAREJGSizes;
}

@end
@implementation RAREJGSizes_ComponentSize

static int RAREJGSizes_ComponentSize_nextOrdinal_ = 0;

+ (int)nextOrdinal {
  return RAREJGSizes_ComponentSize_nextOrdinal_;
}

+ (int *)nextOrdinalRef {
  return &RAREJGSizes_ComponentSize_nextOrdinal_;
}

- (id)initWithNSString:(NSString *)name {
  if (self = [super init]) {
    ordinal_ = RAREJGSizes_ComponentSize_nextOrdinal_++;
    self->name_ = name;
  }
  return self;
}

- (NSString *)encodeEx {
  return [self encode];
}

+ (RAREJGSizes_ComponentSize *)valueOfWithNSString:(NSString *)str {
  if ([((NSString *) nil_chk(str)) isEqual:@"m"] || [str isEqual:@"min"]) return [RAREJGSizes MINIMUM];
  if ([str isEqual:@"p"] || [str isEqual:@"pref"]) return [RAREJGSizes PREFERRED];
  if ([str isEqual:@"d"] || [str isEqual:@"default"]) return [RAREJGSizes DEFAULT];
  return nil;
}

- (int)maximumSizeWithRAREiParentComponent:(id<RAREiParentComponent>)container
                          withJavaUtilList:(id<JavaUtilList>)components
              withRAREJGFormLayout_Measure:(id<RAREJGFormLayout_Measure>)minMeasure
              withRAREJGFormLayout_Measure:(id<RAREJGFormLayout_Measure>)prefMeasure
              withRAREJGFormLayout_Measure:(id<RAREJGFormLayout_Measure>)defaultMeasure {
  id<RAREJGFormLayout_Measure> measure = self == [RAREJGSizes MINIMUM] ? minMeasure : (self == [RAREJGSizes PREFERRED] ? prefMeasure : defaultMeasure);
  int maximum = 0;
  for (id<JavaUtilIterator> i = [((id<JavaUtilList>) nil_chk(components)) iterator]; [((id<JavaUtilIterator>) nil_chk(i)) hasNext]; ) {
    id<RAREiPlatformComponent> c = (id<RAREiPlatformComponent>) check_protocol_cast([i next], @protocol(RAREiPlatformComponent));
    maximum = [JavaLangMath maxWithInt:maximum withInt:[measure sizeOfWithRAREiPlatformComponent:c]];
  }
  return maximum > 65535 ? 65535 : maximum;
}

- (BOOL)compressible {
  return self == [RAREJGSizes DEFAULT];
}

- (NSString *)description {
  return [self encode];
}

- (NSString *)encode {
  return [((NSString *) nil_chk(name_)) substring:0 endIndex:1];
}

- (void)copyAllFieldsTo:(RAREJGSizes_ComponentSize *)other {
  [super copyAllFieldsTo:other];
  other->name_ = name_;
  other->ordinal_ = ordinal_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "initWithNSString:", NULL, NULL, 0x2, NULL },
    { "encodeEx", NULL, "LNSString", 0x1, NULL },
    { "valueOfWithNSString:", NULL, "LRAREJGSizes_ComponentSize", 0x8, NULL },
    { "compressible", NULL, "Z", 0x1, NULL },
    { "encode", NULL, "LNSString", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "name_", NULL, 0x92, "LNSString" },
    { "nextOrdinal_", NULL, 0xa, "I" },
    { "ordinal_", NULL, 0x12, "I" },
  };
  static J2ObjcClassInfo _RAREJGSizes_ComponentSize = { "ComponentSize", "com.jgoodies.forms.layout", "Sizes", 0x18, 5, methods, 3, fields, 0, NULL};
  return &_RAREJGSizes_ComponentSize;
}

@end
