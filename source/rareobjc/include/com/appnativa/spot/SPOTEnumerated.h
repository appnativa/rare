//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../spot/src/com/appnativa/spot/SPOTEnumerated.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _SPOTEnumerated_H_
#define _SPOTEnumerated_H_

@class IOSIntArray;
@class IOSLongArray;
@class IOSObjectArray;
@class JavaIoInputStream;
@class JavaIoOutputStream;
@class JavaLangInteger;
@class RAREUTMutableInteger;
@class RAREUTSNumber;
@protocol JavaUtilMap;

#import "JreEmulation.h"
#include "com/appnativa/spot/aSPOTElement.h"
#include "java/lang/Comparable.h"

@interface SPOTEnumerated : aSPOTElement < JavaLangComparable > {
 @public
  IOSIntArray *_nChoices_;
  IOSObjectArray *_sChoices_;
  RAREUTMutableInteger *_iValue_;
  BOOL _bValSet_;
  RAREUTMutableInteger *_iDefValue_;
  NSString *_sDefaultValue_;
  NSString *_sValidRange_;
  NSString *_sValue_;
}

- (id)init;
- (id)initWithBoolean:(BOOL)optional;
- (id)initWithInt:(int)val;
- (id)initWithInt:(int)val
      withBoolean:(BOOL)optional;
- (id)initWithInt:(int)val
     withNSString:(NSString *)defaultval
      withBoolean:(BOOL)optional;
- (id)initWithJavaLangInteger:(JavaLangInteger *)ival
                 withNSString:(NSString *)sval
          withJavaLangInteger:(JavaLangInteger *)idefaultval
                 withNSString:(NSString *)sdefaultval
                  withBoolean:(BOOL)optional;
- (BOOL)booleanValue;
- (id)clone;
- (int)compareToWithId:(id)o;
- (int)compareToWithSPOTEnumerated:(SPOTEnumerated *)o;
- (double)doubleValue;
- (BOOL)equalsWithASPOTElement:(aSPOTElement *)e;
- (void)fromStreamWithJavaIoInputStream:(JavaIoInputStream *)inArg;
- (NSUInteger)hash;
- (long long int)longValue;
- (RAREUTSNumber *)numberValue;
- (NSString *)spot_checkRangeValidityStr;
- (void)spot_clear;
- (void)spot_generatePropertiesWithJavaUtilMap:(id<JavaUtilMap>)map
                                  withNSString:(NSString *)prefix
                                       withInt:(int)propcase;
- (void)spot_generatePropertiesStrWithJavaUtilMap:(id<JavaUtilMap>)map
                                     withNSString:(NSString *)prefix
                                          withInt:(int)propcase;
- (IOSIntArray *)spot_getCopyOfIntChoices;
- (IOSObjectArray *)spot_getCopyOfStrChoices;
- (NSString *)spot_getDefaultValue;
- (IOSObjectArray *)spot_getRange;
- (int)spot_getType;
- (NSString *)spot_getValidityRange;
- (id)spot_getValue;
- (void)spot_setChoicesWithNSStringArray:(IOSObjectArray *)val
                            withIntArray:(IOSIntArray *)lval;
- (void)spot_setChoicesWithNSStringArray:(IOSObjectArray *)val
                           withLongArray:(IOSLongArray *)lval;
- (void)spot_setDefaultValueWithInt:(int)val;
- (void)spot_setDefaultValueWithLong:(long long int)val;
- (void)spot_setDefaultValueWithNSString:(NSString *)val;
- (void)spot_setDefaultValueWithInt:(int)ival
                       withNSString:(NSString *)sval;
- (int)spot_stringToIntWithNSString:(NSString *)val;
- (NSString *)spot_stringValue;
- (NSString *)spot_stringValueEx;
- (void)toStreamWithJavaIoOutputStream:(JavaIoOutputStream *)outArg;
- (void)setValueWithBoolean:(BOOL)val;
- (void)setValueWithDouble:(double)val;
- (void)setValueWithInt:(int)val;
- (void)setValueWithLong:(long long int)val;
- (void)setValueWithRAREUTSNumber:(RAREUTSNumber *)val;
- (void)setValueWithSPOTEnumerated:(SPOTEnumerated *)val;
- (void)setValueWithNSString:(NSString *)val;
- (int)getChoiceByIndexWithInt:(int)index;
- (int)getChoiceIndexByNameWithNSString:(NSString *)name;
- (int)getValue;
- (int)spot_checkRangeValidityEx;
- (void)copyAllFieldsTo:(SPOTEnumerated *)other;
@end

J2OBJC_FIELD_SETTER(SPOTEnumerated, _nChoices_, IOSIntArray *)
J2OBJC_FIELD_SETTER(SPOTEnumerated, _sChoices_, IOSObjectArray *)
J2OBJC_FIELD_SETTER(SPOTEnumerated, _iValue_, RAREUTMutableInteger *)
J2OBJC_FIELD_SETTER(SPOTEnumerated, _iDefValue_, RAREUTMutableInteger *)
J2OBJC_FIELD_SETTER(SPOTEnumerated, _sDefaultValue_, NSString *)
J2OBJC_FIELD_SETTER(SPOTEnumerated, _sValidRange_, NSString *)
J2OBJC_FIELD_SETTER(SPOTEnumerated, _sValue_, NSString *)

typedef SPOTEnumerated ComAppnativaSpotSPOTEnumerated;

#endif // _SPOTEnumerated_H_
