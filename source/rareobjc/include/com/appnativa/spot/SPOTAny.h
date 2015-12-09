//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../spot/src/com/appnativa/spot/SPOTAny.java
//
//  Created by decoteaud on 12/8/15.
//

#ifndef _SPOTAny_H_
#define _SPOTAny_H_

@class IOSByteArray;
@class IOSClass;
@class JavaIoInputStream;
@class JavaIoOutputStream;
@class JavaIoWriter;
@class RAREUTSNumber;
@class SDFNode;
@protocol RAREUTiStructuredNode;
@protocol iSPOTElement;

#import "JreEmulation.h"
#include "com/appnativa/spot/aSPOTElement.h"

@interface SPOTAny : aSPOTElement {
 @public
  IOSClass *_clsDefinedBy_;
  id<iSPOTElement> _objectValue_;
  NSString *_strDefinedBy_;
}

- (id)init;
- (id)initWithBoolean:(BOOL)optional;
- (id)initWithISPOTElement:(id<iSPOTElement>)val;
- (id)initWithNSString:(NSString *)definedby;
- (id)initWithISPOTElement:(id<iSPOTElement>)val
               withBoolean:(BOOL)optional;
- (id)initWithNSString:(NSString *)definedby
           withBoolean:(BOOL)optional;
- (id)initWithSPOTAny:(SPOTAny *)prototype;
- (BOOL)booleanValue;
- (IOSByteArray *)byteArrayValue;
- (id)clone;
- (double)doubleValue;
- (BOOL)equalsWithASPOTElement:(aSPOTElement *)e;
- (BOOL)fromSDFWithSDFNode:(SDFNode *)node;
- (void)fromStreamWithJavaIoInputStream:(JavaIoInputStream *)inArg;
- (BOOL)fromStructuredNodeWithRAREUTiStructuredNode:(id<RAREUTiStructuredNode>)node;
- (NSUInteger)hash;
- (int)intValue;
- (long long int)longValue;
- (RAREUTSNumber *)numberValue;
- (NSString *)spot_checkRangeValidityStr;
- (void)spot_clear;
- (void)spot_copyWithISPOTElement:(id<iSPOTElement>)element
                      withBoolean:(BOOL)newinstance;
- (id<iSPOTElement>)spot_elementValue;
- (NSString *)spot_getDefinedByType;
- (NSString *)spot_getName;
- (int)spot_getType;
- (NSString *)spot_getValidityRange;
- (id)spot_getValue;
- (BOOL)spot_isContainer;
- (void)spot_makeReadOnly;
- (void)spot_setNameWithNSString:(NSString *)name;
- (void)spot_setOptionalWithBoolean:(BOOL)optional;
- (NSString *)spot_stringValue;
- (NSString *)spot_stringValueEx;
- (BOOL)toSDFWithJavaIoWriter:(JavaIoWriter *)outArg
                 withNSString:(NSString *)tag
                      withInt:(int)depth
                  withBoolean:(BOOL)outputempty
                  withBoolean:(BOOL)outputComments;
- (void)toStreamWithJavaIoOutputStream:(JavaIoOutputStream *)outArg;
- (NSString *)description;
- (void)setValueWithBoolean:(BOOL)val;
- (void)setValueWithDouble:(double)val;
- (void)setValueWithISPOTElement:(id<iSPOTElement>)val;
- (void)setValueWithLong:(long long int)val;
- (void)setValueWithRAREUTSNumber:(RAREUTSNumber *)val;
- (void)setValueWithNSString:(NSString *)val;
- (id<iSPOTElement>)getValue;
- (int)spot_checkRangeValidityEx;
- (void)spot_checkReadOnly;
- (void)spot_setCanMakeReadOnlyWithBoolean:(BOOL)canmakero;
- (void)copyAllFieldsTo:(SPOTAny *)other;
@end

J2OBJC_FIELD_SETTER(SPOTAny, _clsDefinedBy_, IOSClass *)
J2OBJC_FIELD_SETTER(SPOTAny, _objectValue_, id<iSPOTElement>)
J2OBJC_FIELD_SETTER(SPOTAny, _strDefinedBy_, NSString *)

typedef SPOTAny ComAppnativaSpotSPOTAny;

#endif // _SPOTAny_H_
