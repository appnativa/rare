//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../spot/src/com/appnativa/spot/SPOTReal.java
//
//  Created by decoteaud on 3/11/16.
//

#include "IOSClass.h"
#include "IOSObjectArray.h"
#include "com/appnativa/spot/SPOTException.h"
#include "com/appnativa/spot/SPOTReal.h"
#include "com/appnativa/spot/aSPOTElement.h"
#include "com/appnativa/spot/iSPOTConstants.h"
#include "com/appnativa/util/SNumber.h"
#include "com/appnativa/util/aStreamer.h"
#include "java/io/IOException.h"
#include "java/io/InputStream.h"
#include "java/io/OutputStream.h"
#include "java/lang/NumberFormatException.h"

@implementation SPOTReal

- (id)init {
  return [self initSPOTRealWithBoolean:YES];
}

- (id)initSPOTRealWithBoolean:(BOOL)optional {
  if (self = [super init]) {
    _isOptional_ = optional;
  }
  return self;
}

- (id)initWithBoolean:(BOOL)optional {
  return [self initSPOTRealWithBoolean:optional];
}

- (id)initWithDouble:(double)val {
  if (self = [super init]) {
    _isOptional_ = NO;
    _numValue_SPOTReal_ = [[RAREUTSNumber alloc] initWithDouble:val];
  }
  return self;
}

- (id)initWithRAREUTSNumber:(RAREUTSNumber *)val {
  if (self = [super init]) {
    _isOptional_ = NO;
    _numValue_SPOTReal_ = [[RAREUTSNumber alloc] initWithRAREUTSNumber:val];
  }
  return self;
}

- (id)initWithDouble:(double)val
         withBoolean:(BOOL)optional {
  if (self = [super init]) {
    _isOptional_ = optional;
    _numValue_SPOTReal_ = [[RAREUTSNumber alloc] initWithDouble:val];
  }
  return self;
}

- (id)initWithDouble:(double)val
          withDouble:(double)min {
  if (self = [super init]) {
    _isOptional_ = NO;
    _nRangeMin_ = [[RAREUTSNumber alloc] initWithDouble:min];
    _numValue_SPOTReal_ = [[RAREUTSNumber alloc] initWithDouble:val];
  }
  return self;
}

- (id)initWithDouble:(double)val
          withDouble:(double)defaultval
         withBoolean:(BOOL)optional {
  if (self = [super init]) {
    _isOptional_ = optional;
    _numDefValue_ = [[RAREUTSNumber alloc] initWithDouble:defaultval];
    _numValue_SPOTReal_ = [[RAREUTSNumber alloc] initWithDouble:val];
  }
  return self;
}

- (id)initWithDouble:(double)val
          withDouble:(double)min
          withDouble:(double)max {
  return [self initSPOTRealWithDouble:val withDouble:min withDouble:max withBoolean:NO];
}

- (id)initSPOTRealWithDouble:(double)val
                  withDouble:(double)min
                  withDouble:(double)max
                 withBoolean:(BOOL)optional {
  if (self = [super init]) {
    _isOptional_ = optional;
    _nRangeMin_ = [[RAREUTSNumber alloc] initWithDouble:min];
    _nRangeMax_ = [[RAREUTSNumber alloc] initWithDouble:max];
    _numValue_SPOTReal_ = [[RAREUTSNumber alloc] initWithDouble:val];
  }
  return self;
}

- (id)initWithDouble:(double)val
          withDouble:(double)min
          withDouble:(double)max
         withBoolean:(BOOL)optional {
  return [self initSPOTRealWithDouble:val withDouble:min withDouble:max withBoolean:optional];
}

- (id)initWithNSString:(NSString *)val
          withNSString:(NSString *)min
          withNSString:(NSString *)max
           withBoolean:(BOOL)optional {
  return [self initSPOTRealWithNSString:val withNSString:min withNSString:max withNSString:val withBoolean:optional];
}

- (id)initWithNSNumber:(NSNumber *)val
          withNSNumber:(NSNumber *)min
          withNSNumber:(NSNumber *)max
          withNSNumber:(NSNumber *)defaultval
           withBoolean:(BOOL)optional {
  if (self = [super init]) {
    _isOptional_ = optional;
    _nRangeMin_ = [RAREUTSNumber valueOfWithNSNumber:min];
    _nRangeMax_ = [RAREUTSNumber valueOfWithNSNumber:max];
    _numDefValue_ = [RAREUTSNumber valueOfWithNSNumber:defaultval];
    if (val != nil) {
      if ([val isKindOfClass:[RAREUTSNumber class]]) {
        _numValue_SPOTReal_ = [[RAREUTSNumber alloc] initWithRAREUTSNumber:(RAREUTSNumber *) check_class_cast(val, [RAREUTSNumber class])];
      }
      else {
        _numValue_SPOTReal_ = [[RAREUTSNumber alloc] initWithDouble:[val doubleValue]];
      }
    }
  }
  return self;
}

- (id)initSPOTRealWithNSString:(NSString *)val
                  withNSString:(NSString *)min
                  withNSString:(NSString *)max
                  withNSString:(NSString *)defaultval
                   withBoolean:(BOOL)optional {
  if (self = [super init]) {
    _isOptional_ = optional;
    _nRangeMin_ = (min == nil) ? nil : [[RAREUTSNumber alloc] initWithNSString:min];
    _nRangeMax_ = (max == nil) ? nil : [[RAREUTSNumber alloc] initWithNSString:max];
    _numDefValue_ = (defaultval == nil) ? nil : [[RAREUTSNumber alloc] initWithNSString:defaultval];
    if (val != nil) {
      _numValue_SPOTReal_ = [[RAREUTSNumber alloc] initWithNSString:val];
    }
  }
  return self;
}

- (id)initWithNSString:(NSString *)val
          withNSString:(NSString *)min
          withNSString:(NSString *)max
          withNSString:(NSString *)defaultval
           withBoolean:(BOOL)optional {
  return [self initSPOTRealWithNSString:val withNSString:min withNSString:max withNSString:defaultval withBoolean:optional];
}

- (id)initWithNSString:(NSString *)val
          withNSString:(NSString *)defaultval
           withBoolean:(BOOL)optional {
  if (self = [super init]) {
    _isOptional_ = optional;
    _numDefValue_ = (defaultval == nil) ? nil : [[RAREUTSNumber alloc] initWithNSString:defaultval];
    if (val != nil) {
      _numValue_SPOTReal_ = [[RAREUTSNumber alloc] initWithNSString:val];
    }
  }
  return self;
}

- (BOOL)booleanValue {
  return ![((RAREUTSNumber *) nil_chk([self getValue])) equalsWithInt:0];
}

- (id)clone {
  SPOTReal *e = (SPOTReal *) check_class_cast([super clone], [SPOTReal class]);
  if (_numValue_SPOTReal_ != nil) {
    ((SPOTReal *) nil_chk(e))->_numValue_SPOTReal_ = [[RAREUTSNumber alloc] initWithRAREUTSNumber:_numValue_SPOTReal_];
  }
  return e;
}

- (int)compareToWithId:(id)o {
  return [self compareToWithSPOTReal:(SPOTReal *) check_class_cast(o, [SPOTReal class])];
}

- (int)compareToWithSPOTReal:(SPOTReal *)o {
  if (o == nil) {
    return 1;
  }
  RAREUTSNumber *num1 = (_numValue_SPOTReal_ != nil) ? _numValue_SPOTReal_ : _numDefValue_;
  RAREUTSNumber *num2 = (((SPOTReal *) nil_chk(o))->_numValue_SPOTReal_ != nil) ? o->_numValue_SPOTReal_ : o->_numDefValue_;
  if ((num1 == nil) || (num2 == nil)) {
    return (num1 == num2) ? 0 : ((num1 != nil) ? 1 : -1);
  }
  return [num1 compareToWithRAREUTSNumber:num2];
}

- (double)doubleValue {
  return [((RAREUTSNumber *) nil_chk([self getValue])) doubleValue];
}

- (BOOL)isEqual:(id)o {
  if (!([o isKindOfClass:[SPOTReal class]])) {
    return NO;
  }
  return [self equalsWithASPOTElement:(SPOTReal *) check_class_cast(o, [SPOTReal class])];
}

- (BOOL)equalsWithRAREUTSNumber:(RAREUTSNumber *)o {
  RAREUTSNumber *num1 = (_numValue_SPOTReal_ != nil) ? _numValue_SPOTReal_ : _numDefValue_;
  if ((num1 == nil) || (o == nil)) {
    return (num1 == o) ? YES : NO;
  }
  return [((RAREUTSNumber *) nil_chk(_numValue_SPOTReal_)) equalsWithRAREUTSNumber:o];
}

- (BOOL)equalsWithASPOTElement:(aSPOTElement *)e {
  if (!([e isKindOfClass:[SPOTReal class]])) {
    return NO;
  }
  SPOTReal *o = (SPOTReal *) check_class_cast(e, [SPOTReal class]);
  if (o == self) {
    return YES;
  }
  RAREUTSNumber *num1 = (_numValue_SPOTReal_ != nil) ? _numValue_SPOTReal_ : _numDefValue_;
  RAREUTSNumber *num2 = (((SPOTReal *) nil_chk(o))->_numValue_SPOTReal_ != nil) ? o->_numValue_SPOTReal_ : o->_numDefValue_;
  if ((num1 == nil) || (num2 == nil)) {
    if (num1 != num2) {
      return NO;
    }
  }
  else if (![num2 equalsWithRAREUTSNumber:num2]) {
    return NO;
  }
  return [aSPOTElement spot_attributesEqualWithISPOTElement:self withISPOTElement:o];
}

- (float)floatValue {
  return [((RAREUTSNumber *) nil_chk([self getValue])) floatValue];
}

- (void)fromStreamWithJavaIoInputStream:(JavaIoInputStream *)inArg {
  if (![aSPOTElement OPTIMIZE_RUNTIME]) {
    [self checkReadOnly];
  }
  if ([RAREUTaStreamer readBooleanWithJavaIoInputStream:inArg]) {
    (void) [((RAREUTSNumber *) nil_chk(_numValue_SPOTReal_)) setValueWithLong:[RAREUTaStreamer readLongWithJavaIoInputStream:inArg] withLong:[RAREUTaStreamer readLongWithJavaIoInputStream:inArg] withInt:[RAREUTaStreamer readIntWithJavaIoInputStream:inArg]];
  }
  else {
    _numValue_SPOTReal_ = nil;
  }
}

- (NSUInteger)hash {
  RAREUTSNumber *num = (_numValue_SPOTReal_ != nil) ? _numValue_SPOTReal_ : _numDefValue_;
  return (num != nil) ? [num hash] : [super hash];
}

- (long long int)longValue {
  if (_numValue_SPOTReal_ == nil) {
    if (_numDefValue_ == nil) {
      @throw [[SPOTException alloc] initWithNSString:[iSPOTConstants STR_NULL_VALUE] withNSString:(_theName_ == nil) ? [[self getClass] getName] : _theName_];
    }
    return [((RAREUTSNumber *) nil_chk(_numDefValue_)) longLongValue];
  }
  return [((RAREUTSNumber *) nil_chk(_numValue_SPOTReal_)) longLongValue];
}

- (RAREUTSNumber *)numberValue {
  if (_numValue_SPOTReal_ == nil) {
    return (_numDefValue_ != nil) ? [((RAREUTSNumber *) nil_chk([self numValueNumber])) setValueWithRAREUTSNumber:_numDefValue_] : nil;
  }
  return [((RAREUTSNumber *) nil_chk([self numValueNumber])) setValueWithRAREUTSNumber:_numValue_SPOTReal_];
}

- (void)spot_clear {
  [super spot_clear];
  _numValue_SPOTReal_ = nil;
  _sValue_ = nil;
}

- (IOSObjectArray *)spot_getRange {
  if ((_nRangeMin_ == nil) && (_nRangeMax_ == nil)) {
    return nil;
  }
  return [IOSObjectArray arrayWithObjects:(id[]){ _nRangeMin_, _nRangeMax_ } count:2 type:[IOSClass classWithClass:[NSObject class]]];
}

- (int)spot_getType {
  return iSPOTConstants_SPOT_TYPE_REAL;
}

- (NSString *)spot_getValidityRange {
  if ((_nRangeMin_ != nil) || (_nRangeMax_ != nil)) {
    NSString *s = @"";
    s = (_nRangeMin_ != nil) ? ([NSString stringWithFormat:@"%@..", [_nRangeMin_ description]]) : @"..";
    if (_nRangeMax_ != nil) {
      s = [NSString stringWithFormat:@"%@%@", s, [_nRangeMax_ description]];
    }
    return s;
  }
  else {
    return @"";
  }
}

- (id)spot_getValue {
  return [self getValue];
}

- (void)spot_setRangeWithNSString:(NSString *)min
                     withNSString:(NSString *)max {
  if (![aSPOTElement OPTIMIZE_RUNTIME]) {
    [self checkReadOnly];
  }
  if (min != nil) {
    _nRangeMin_ = [[RAREUTSNumber alloc] initWithNSString:min];
  }
  if (max != nil) {
    _nRangeMax_ = [[RAREUTSNumber alloc] initWithNSString:max];
  }
}

- (NSString *)spot_stringValue {
  if (_numValue_SPOTReal_ == nil) {
    return (_numDefValue_ != nil) ? [_numDefValue_ description] : nil;
  }
  if (_sValue_ == nil) {
    _sValue_ = [((RAREUTSNumber *) nil_chk(_numValue_SPOTReal_)) description];
  }
  return _sValue_;
}

- (NSString *)spot_stringValueEx {
  return ((_numValue_SPOTReal_ == nil) && ![self spot_attributesWereSet]) ? nil : [self spot_stringValue];
}

- (void)toStreamWithJavaIoOutputStream:(JavaIoOutputStream *)outArg {
  [RAREUTaStreamer toStreamWithBoolean:_numValue_SPOTReal_ != nil withJavaIoOutputStream:outArg];
  if (_numValue_SPOTReal_ != nil) {
    [_numValue_SPOTReal_ toStreamWithJavaIoOutputStream:outArg];
  }
}

- (NSString *)description {
  NSString *s = [self spot_stringValue];
  return (s == nil) ? @"" : s;
}

- (void)setDefaultValueWithDouble:(double)val {
  if (![aSPOTElement OPTIMIZE_RUNTIME]) {
    [self checkReadOnly];
  }
  _numDefValue_ = [[RAREUTSNumber alloc] initWithDouble:val];
}

- (void)setRangeWithDouble:(double)min
                withDouble:(double)max {
  if (![aSPOTElement OPTIMIZE_RUNTIME]) {
    [self checkReadOnly];
  }
  _nRangeMin_ = [[RAREUTSNumber alloc] initWithDouble:min];
  _nRangeMax_ = [[RAREUTSNumber alloc] initWithDouble:max];
}

- (void)setRangeWithRAREUTSNumber:(RAREUTSNumber *)min
                withRAREUTSNumber:(RAREUTSNumber *)max {
  if (![aSPOTElement OPTIMIZE_RUNTIME]) {
    [self checkReadOnly];
  }
  if (min != nil) {
    _nRangeMin_ = [[RAREUTSNumber alloc] initWithRAREUTSNumber:min];
  }
  if (max != nil) {
    _nRangeMax_ = [[RAREUTSNumber alloc] initWithRAREUTSNumber:max];
  }
}

- (void)setValueWithBoolean:(BOOL)val {
  [self setValueWithLong:val ? 1 : 0];
}

- (void)setValueWithDouble:(double)val {
  if (![aSPOTElement OPTIMIZE_RUNTIME]) {
    [self checkReadOnly];
  }
  if (_numValue_SPOTReal_ == nil) {
    _numValue_SPOTReal_ = [[RAREUTSNumber alloc] initWithDouble:val];
  }
  else {
    (void) [_numValue_SPOTReal_ setValueWithDouble:val];
  }
  _sValue_ = nil;
}

- (void)setValueWithLong:(long long int)val {
  if (![aSPOTElement OPTIMIZE_RUNTIME]) {
    [self checkReadOnly];
  }
  if (_numValue_SPOTReal_ == nil) {
    _numValue_SPOTReal_ = [[RAREUTSNumber alloc] initWithLong:val];
  }
  else {
    (void) [_numValue_SPOTReal_ setValueWithLong:val];
  }
  _sValue_ = nil;
}

- (void)setValueWithRAREUTSNumber:(RAREUTSNumber *)val {
  if (![aSPOTElement OPTIMIZE_RUNTIME]) {
    [self checkReadOnly];
  }
  if (_numValue_SPOTReal_ == nil) {
    _numValue_SPOTReal_ = [[RAREUTSNumber alloc] initWithRAREUTSNumber:val];
  }
  else {
    (void) [_numValue_SPOTReal_ setValueWithRAREUTSNumber:val];
  }
  _sValue_ = nil;
}

- (void)setValueWithSPOTReal:(SPOTReal *)val {
  if (![aSPOTElement OPTIMIZE_RUNTIME]) {
    [self checkReadOnly];
  }
  _sValue_ = nil;
  RAREUTSNumber *num = ((SPOTReal *) nil_chk(val))->_numValue_SPOTReal_;
  if (num == nil) {
    num = val->_numDefValue_;
  }
  if (num == nil) {
    _numValue_SPOTReal_ = nil;
    return;
  }
  if (_numValue_SPOTReal_ == nil) {
    _numValue_SPOTReal_ = [[RAREUTSNumber alloc] initWithRAREUTSNumber:num];
  }
  else {
    (void) [_numValue_SPOTReal_ setValueWithRAREUTSNumber:num];
  }
}

- (void)setValueWithNSString:(NSString *)val {
  if (![aSPOTElement OPTIMIZE_RUNTIME]) {
    [self checkReadOnly];
  }
  if (val == nil) {
    _numValue_SPOTReal_ = nil;
    _sValue_ = nil;
    return;
  }
  @try {
    if (_numValue_SPOTReal_ == nil) {
      _numValue_SPOTReal_ = [[RAREUTSNumber alloc] initWithNSString:val];
    }
    else {
      (void) [_numValue_SPOTReal_ setValueWithNSString:val];
    }
    _sValue_ = nil;
  }
  @catch (JavaLangNumberFormatException *e) {
    @throw [[SPOTException alloc] initWithJavaLangThrowable:e];
  }
}

- (NSString *)getDefaultValue {
  if (_numDefValue_ != nil) {
    return [_numDefValue_ description];
  }
  return nil;
}

- (RAREUTSNumber *)getValue {
  if (_numValue_SPOTReal_ == nil) {
    return (_numDefValue_ != nil) ? [((RAREUTSNumber *) nil_chk([self numValueNumber])) setValueWithRAREUTSNumber:_numDefValue_] : nil;
  }
  return [((RAREUTSNumber *) nil_chk([self numValueNumber])) setValueWithRAREUTSNumber:_numValue_SPOTReal_];
}

- (int)spot_checkRangeValidityEx {
  if ((_numValue_SPOTReal_ == nil) && (_numDefValue_ != nil)) {
    return iSPOTConstants_VALUE_NULL_WITH_DEFAULT;
  }
  if ((_numValue_SPOTReal_ == nil) && _isOptional_) {
    return iSPOTConstants_VALUE_NULL_AND_OPTIONAL;
  }
  if (_numValue_SPOTReal_ == nil) {
    return iSPOTConstants_VALUE_NULL;
  }
  if ((_nRangeMin_ != nil) && ([((RAREUTSNumber *) nil_chk(_numValue_SPOTReal_)) ltWithRAREUTSNumber:_nRangeMin_])) {
    return iSPOTConstants_VALUE_TO_SMALL;
  }
  if ((_nRangeMax_ != nil) && ([((RAREUTSNumber *) nil_chk(_numValue_SPOTReal_)) gtWithRAREUTSNumber:_nRangeMax_])) {
    return iSPOTConstants_VALUE_TO_BIG;
  }
  return iSPOTConstants_VALUE_OK;
}

- (void)copyAllFieldsTo:(SPOTReal *)other {
  [super copyAllFieldsTo:other];
  other->_nRangeMax_ = _nRangeMax_;
  other->_nRangeMin_ = _nRangeMin_;
  other->_numDefValue_ = _numDefValue_;
  other->_numValue_SPOTReal_ = _numValue_SPOTReal_;
  other->_sValue_ = _sValue_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "booleanValue", NULL, "Z", 0x1, NULL },
    { "clone", NULL, "LNSObject", 0x1, NULL },
    { "equalsWithRAREUTSNumber:", NULL, "Z", 0x1, NULL },
    { "equalsWithASPOTElement:", NULL, "Z", 0x1, NULL },
    { "fromStreamWithJavaIoInputStream:", NULL, "V", 0x1, "JavaIoIOException" },
    { "numberValue", NULL, "LRAREUTSNumber", 0x1, NULL },
    { "spot_getRange", NULL, "LIOSObjectArray", 0x1, NULL },
    { "spot_getType", NULL, "I", 0x11, NULL },
    { "spot_getValidityRange", NULL, "LNSString", 0x1, NULL },
    { "spot_getValue", NULL, "LNSObject", 0x1, NULL },
    { "spot_stringValue", NULL, "LNSString", 0x1, NULL },
    { "spot_stringValueEx", NULL, "LNSString", 0x1, NULL },
    { "toStreamWithJavaIoOutputStream:", NULL, "V", 0x1, "JavaIoIOException" },
    { "getDefaultValue", NULL, "LNSString", 0x1, NULL },
    { "getValue", NULL, "LRAREUTSNumber", 0x1, NULL },
    { "spot_checkRangeValidityEx", NULL, "I", 0x4, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "_nRangeMax_", NULL, 0x4, "LRAREUTSNumber" },
    { "_nRangeMin_", NULL, 0x4, "LRAREUTSNumber" },
    { "_numDefValue_", NULL, 0x4, "LRAREUTSNumber" },
    { "_numValue_SPOTReal_", "_numValue", 0x4, "LRAREUTSNumber" },
    { "_sValue_", NULL, 0x4, "LNSString" },
  };
  static J2ObjcClassInfo _SPOTReal = { "SPOTReal", "com.appnativa.spot", NULL, 0x1, 16, methods, 5, fields, 0, NULL};
  return &_SPOTReal;
}

@end
