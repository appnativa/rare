//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../spot/src/com/appnativa/spot/SPOTByteString.java
//
//  Created by decoteaud on 9/15/15.
//

#ifndef _SPOTByteString_H_
#define _SPOTByteString_H_

@class IOSByteArray;
@class IOSObjectArray;
@class JavaIoInputStream;
@class JavaIoOutputStream;
@class RAREUTByteArrayHolder;
@protocol RAREUTiCharsetHelper;

#import "JreEmulation.h"
#include "com/appnativa/spot/aSPOTElement.h"
#include "java/lang/Comparable.h"

@interface SPOTByteString : aSPOTElement < JavaLangComparable > {
 @public
  IOSByteArray *_baDefValue_;
  IOSByteArray *_baValue_;
  NSString *_sValue_;
  id<RAREUTiCharsetHelper> _csh_;
  int _len_;
  long long int _nRangeMax_;
  long long int _nRangeMin_;
  RAREUTByteArrayHolder *_outHolder_;
  int _pos_;
}

- (id)init;
- (id)initWithBoolean:(BOOL)optional;
- (id)initWithByteArray:(IOSByteArray *)val;
- (id)initWithNSString:(NSString *)val;
- (id)initWithNSString:(NSString *)val
              withLong:(long long int)max;
- (id)initWithNSString:(NSString *)val
              withLong:(long long int)min
              withLong:(long long int)max;
- (id)initWithByteArray:(IOSByteArray *)val
           withNSString:(NSString *)min
           withNSString:(NSString *)max
            withBoolean:(BOOL)optional;
- (id)initWithNSString:(NSString *)val
              withLong:(long long int)min
              withLong:(long long int)max
           withBoolean:(BOOL)optional;
- (id)initWithNSString:(NSString *)val
          withNSString:(NSString *)min
          withNSString:(NSString *)max
           withBoolean:(BOOL)optional;
- (id)initWithNSString:(NSString *)val
          withNSString:(NSString *)min
          withNSString:(NSString *)max
          withNSString:(NSString *)defaultval
           withBoolean:(BOOL)optional;
- (int)compareToWithId:(id)o;
- (int)compareToWithSPOTByteString:(SPOTByteString *)o;
- (BOOL)isEqual:(id)o;
- (BOOL)equalsWithASPOTElement:(aSPOTElement *)e;
- (void)fromStreamWithJavaIoInputStream:(JavaIoInputStream *)inArg;
- (NSUInteger)hash;
- (int)spot_checkRangeValidityEx;
- (void)spot_clear;
- (IOSObjectArray *)spot_getRange;
- (int)spot_getType;
- (NSString *)spot_getValidityRange;
- (void)spot_setRangeWithLong:(long long int)min
                     withLong:(long long int)max;
- (void)spot_setRangeWithNSString:(NSString *)min
                     withNSString:(NSString *)max;
- (NSString *)spot_stringValue;
- (NSString *)spot_stringValueEx;
- (void)toStreamWithJavaIoOutputStream:(JavaIoOutputStream *)outArg;
- (void)setCharsetHelperWithRAREUTiCharsetHelper:(id<RAREUTiCharsetHelper>)csh;
- (void)setDefaultValueWithNSString:(NSString *)val;
- (void)setValueWithByteArray:(IOSByteArray *)val;
- (void)setValueWithNSString:(NSString *)val;
- (void)setValueWithByteArray:(IOSByteArray *)val
                      withInt:(int)pos
                      withInt:(int)len;
- (id)spot_getValue;
- (RAREUTByteArrayHolder *)getValue;
- (void)setValuesWithByteArray:(IOSByteArray *)val
                      withLong:(long long int)min
                      withLong:(long long int)max
                   withBoolean:(BOOL)optional;
- (void)setValuesWithNSString:(NSString *)val
                 withNSString:(NSString *)min
                 withNSString:(NSString *)max
                  withBoolean:(BOOL)optional;
- (void)copyAllFieldsTo:(SPOTByteString *)other;
@end

J2OBJC_FIELD_SETTER(SPOTByteString, _baDefValue_, IOSByteArray *)
J2OBJC_FIELD_SETTER(SPOTByteString, _baValue_, IOSByteArray *)
J2OBJC_FIELD_SETTER(SPOTByteString, _sValue_, NSString *)
J2OBJC_FIELD_SETTER(SPOTByteString, _csh_, id<RAREUTiCharsetHelper>)
J2OBJC_FIELD_SETTER(SPOTByteString, _outHolder_, RAREUTByteArrayHolder *)

typedef SPOTByteString ComAppnativaSpotSPOTByteString;

#endif // _SPOTByteString_H_
