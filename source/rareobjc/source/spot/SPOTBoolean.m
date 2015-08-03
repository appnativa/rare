//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../spot/src/com/appnativa/spot/SPOTBoolean.java
//
//  Created by decoteaud on 7/29/15.
//

#include "IOSClass.h"
#include "com/appnativa/spot/SPOTBoolean.h"
#include "com/appnativa/spot/SPOTException.h"
#include "com/appnativa/spot/aSPOTElement.h"
#include "com/appnativa/spot/iSPOTConstants.h"
#include "com/appnativa/util/SNumber.h"
#include "com/appnativa/util/aStreamer.h"
#include "java/io/IOException.h"
#include "java/io/InputStream.h"
#include "java/io/OutputStream.h"
#include "java/lang/Boolean.h"

@implementation SPOTBoolean

- (id)init {
  if (self = [super init]) {
    _isOptional_ = YES;
  }
  return self;
}

- (id)initWithBoolean:(BOOL)val
          withBoolean:(BOOL)optional {
  if (self = [super init]) {
    _isOptional_ = optional;
    _bValue_ = [JavaLangBoolean valueOfWithBoolean:val];
  }
  return self;
}

- (id)initWithJavaLangBoolean:(JavaLangBoolean *)val
          withJavaLangBoolean:(JavaLangBoolean *)defaultval
                  withBoolean:(BOOL)optional {
  if (self = [super init]) {
    _isOptional_ = optional;
    _bValue_ = val;
    _bDefaultValue_ = defaultval;
  }
  return self;
}

- (id)initWithBoolean:(BOOL)val
          withBoolean:(BOOL)defaultval
          withBoolean:(BOOL)optional {
  if (self = [super init]) {
    _isOptional_ = optional;
    _bValue_ = [JavaLangBoolean valueOfWithBoolean:val];
    _bDefaultValue_ = [JavaLangBoolean valueOfWithBoolean:defaultval];
  }
  return self;
}

- (BOOL)booleanValue {
  return [self getValue];
}

- (int)compareToWithId:(id)o {
  return [self compareToWithSPOTBoolean:(SPOTBoolean *) check_class_cast(o, [SPOTBoolean class])];
}

- (int)compareToWithSPOTBoolean:(SPOTBoolean *)o {
  if (o == nil) {
    return 1;
  }
  if ((_bValue_ == nil) || (((SPOTBoolean *) nil_chk(o))->_bValue_ == nil)) {
    return (_bValue_ == ((SPOTBoolean *) nil_chk(o))->_bValue_) ? 0 : ((_bValue_ != nil) ? 1 : -1);
  }
  return (_bValue_ == ((SPOTBoolean *) nil_chk(o))->_bValue_) ? 0 : ([((JavaLangBoolean *) nil_chk(_bValue_)) booleanValue] ? 1 : -1);
}

- (double)doubleValue {
  return [self getValue] ? 1.0 : 0;
}

- (BOOL)equalsWithASPOTElement:(aSPOTElement *)e {
  if (e == self) {
    return YES;
  }
  if (!([e isKindOfClass:[SPOTBoolean class]])) {
    return NO;
  }
  SPOTBoolean *o = (SPOTBoolean *) check_class_cast(e, [SPOTBoolean class]);
  if (_bValue_ != ((SPOTBoolean *) nil_chk(o))->_bValue_) {
    return NO;
  }
  ;
  return [aSPOTElement spot_attributesEqualWithISPOTElement:self withISPOTElement:o];
}

- (void)fromStreamWithJavaIoInputStream:(JavaIoInputStream *)inArg {
  if (![aSPOTElement OPTIMIZE_RUNTIME]) {
    [self checkReadOnly];
  }
  if ([RAREUTaStreamer fromStreamWithBoolean:[((JavaLangBoolean *) nil_chk(_bValue_)) booleanValue] withJavaIoInputStream:inArg]) {
    _bValue_ = [JavaLangBoolean valueOfWithBoolean:[RAREUTaStreamer fromStreamWithBoolean:[_bValue_ booleanValue] withJavaIoInputStream:inArg]];
  }
}

- (NSUInteger)hash {
  return (_bValue_ == nil) ? 0 : [_bValue_ hash];
}

- (long long int)longValue {
  return [self getValue] ? 1 : 0;
}

- (RAREUTSNumber *)numberValue {
  return [self getValue] ? [((RAREUTSNumber *) nil_chk([self numValueNumber])) setValueWithInt:1] : [((RAREUTSNumber *) nil_chk([self numValueNumber])) setValueWithInt:0];
}

- (void)spot_clear {
  [super spot_clear];
  _bValue_ = nil;
}

- (int)spot_getType {
  return iSPOTConstants_SPOT_TYPE_BOOLEAN;
}

- (NSString *)spot_getValidityRange {
  return @"{ true(1), false(0) }";
}

- (id)spot_getValue {
  return [JavaLangBoolean valueOfWithBoolean:[self getValue]];
}

- (void)spot_setDefaultValueWithBoolean:(BOOL)val {
  if (![aSPOTElement OPTIMIZE_RUNTIME]) {
    [self checkReadOnly];
  }
  _bDefaultValue_ = [JavaLangBoolean valueOfWithBoolean:val];
}

- (NSString *)spot_stringValue {
  if (_bValue_ != nil) {
    return [_bValue_ description];
  }
  return (_bDefaultValue_ == nil) ? nil : [_bDefaultValue_ description];
}

- (NSString *)spot_stringValueEx {
  return ((_bValue_ == nil) && ![self spot_attributesWereSet]) ? nil : [self spot_stringValue];
}

- (void)toStreamWithJavaIoOutputStream:(JavaIoOutputStream *)outArg {
  [RAREUTaStreamer toStreamWithBoolean:_bValue_ != nil withJavaIoOutputStream:outArg];
  if (_bValue_ != nil) {
    [RAREUTaStreamer toStreamWithBoolean:[_bValue_ booleanValue] withJavaIoOutputStream:outArg];
  }
}

- (void)setValueWithBoolean:(BOOL)val {
  if (![aSPOTElement OPTIMIZE_RUNTIME]) {
    [self checkReadOnly];
  }
  _bValue_ = [JavaLangBoolean valueOfWithBoolean:val];
}

- (void)setValueWithDouble:(double)val {
  if (![aSPOTElement OPTIMIZE_RUNTIME]) {
    [self checkReadOnly];
  }
  [self setValueWithBoolean:val != 0];
}

- (void)setValueWithLong:(long long int)val {
  if (![aSPOTElement OPTIMIZE_RUNTIME]) {
    [self checkReadOnly];
  }
  [self setValueWithBoolean:val != 0];
}

- (void)setValueWithSPOTBoolean:(SPOTBoolean *)val {
  if (![aSPOTElement OPTIMIZE_RUNTIME]) {
    [self checkReadOnly];
  }
  _bValue_ = ((SPOTBoolean *) nil_chk(val))->_bValue_;
}

- (void)setValueWithNSString:(NSString *)val {
  if (![aSPOTElement OPTIMIZE_RUNTIME]) {
    [self checkReadOnly];
  }
  _bValue_ = nil;
  if (val == nil) {
    return;
  }
  if ([((NSString *) nil_chk(val)) isEqual:@"1"] || [val equalsIgnoreCase:@"true"]) {
    [self setValueWithBoolean:YES];
  }
  else if ([val isEqual:@"0"] || [val equalsIgnoreCase:@"false"]) {
    [self setValueWithBoolean:NO];
  }
  else {
    NSString *s = [NSString stringWithFormat:@"The value specified (%@) is not one of %@.", val, [self spot_getValidityRange]];
    @throw [[SPOTException alloc] initWithNSString:s];
  }
}

- (BOOL)getValue {
  if ((_bValue_ == nil) && (_bDefaultValue_ == nil)) {
    @throw [[SPOTException alloc] initWithNSString:[iSPOTConstants STR_NULL_VALUE] withNSString:(_theName_ == nil) ? [[self getClass] getName] : _theName_];
  }
  return [(_bValue_ == nil) ? _bDefaultValue_ : _bValue_ booleanValue];
}

- (int)spot_checkRangeValidityEx {
  if ((_bValue_ == nil) && (_bDefaultValue_ != nil)) {
    return iSPOTConstants_VALUE_NULL_WITH_DEFAULT;
  }
  if ((_bValue_ == nil) && _isOptional_) {
    return iSPOTConstants_VALUE_NULL_AND_OPTIONAL;
  }
  if (_bValue_ == nil) {
    return iSPOTConstants_VALUE_NULL;
  }
  return iSPOTConstants_VALUE_OK;
}

- (void)copyAllFieldsTo:(SPOTBoolean *)other {
  [super copyAllFieldsTo:other];
  other->_bDefaultValue_ = _bDefaultValue_;
  other->_bValue_ = _bValue_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "booleanValue", NULL, "Z", 0x1, NULL },
    { "equalsWithASPOTElement:", NULL, "Z", 0x1, NULL },
    { "fromStreamWithJavaIoInputStream:", NULL, "V", 0x1, "JavaIoIOException" },
    { "numberValue", NULL, "LRAREUTSNumber", 0x1, NULL },
    { "spot_getType", NULL, "I", 0x11, NULL },
    { "spot_getValidityRange", NULL, "LNSString", 0x1, NULL },
    { "spot_getValue", NULL, "LNSObject", 0x1, NULL },
    { "spot_stringValue", NULL, "LNSString", 0x1, NULL },
    { "spot_stringValueEx", NULL, "LNSString", 0x1, NULL },
    { "toStreamWithJavaIoOutputStream:", NULL, "V", 0x1, "JavaIoIOException" },
    { "getValue", NULL, "Z", 0x1, NULL },
    { "spot_checkRangeValidityEx", NULL, "I", 0x4, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "_bDefaultValue_", NULL, 0x4, "LJavaLangBoolean" },
    { "_bValue_", NULL, 0x4, "LJavaLangBoolean" },
  };
  static J2ObjcClassInfo _SPOTBoolean = { "SPOTBoolean", "com.appnativa.spot", NULL, 0x1, 12, methods, 2, fields, 0, NULL};
  return &_SPOTBoolean;
}

@end
