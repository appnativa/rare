//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: android/libcore/luni/src/main/java/org/w3c/dom/ls/LSInput.java
//
//  Created by tball on 11/23/13.
//

#ifndef _OrgW3cDomLsLSInput_H_
#define _OrgW3cDomLsLSInput_H_

@class JavaIoInputStream;
@class JavaIoReader;

#import "JreEmulation.h"

@protocol OrgW3cDomLsLSInput < NSObject, JavaObject >
- (JavaIoReader *)getCharacterStream;
- (void)setCharacterStreamWithJavaIoReader:(JavaIoReader *)characterStream;
- (JavaIoInputStream *)getByteStream;
- (void)setByteStreamWithJavaIoInputStream:(JavaIoInputStream *)byteStream;
- (NSString *)getStringData;
- (void)setStringDataWithNSString:(NSString *)stringData;
- (NSString *)getSystemId;
- (void)setSystemIdWithNSString:(NSString *)systemId;
- (NSString *)getPublicId;
- (void)setPublicIdWithNSString:(NSString *)publicId;
- (NSString *)getBaseURI;
- (void)setBaseURIWithNSString:(NSString *)baseURI;
- (NSString *)getEncoding;
- (void)setEncodingWithNSString:(NSString *)encoding;
- (BOOL)getCertifiedText;
- (void)setCertifiedTextWithBoolean:(BOOL)certifiedText;
@end

#endif // _OrgW3cDomLsLSInput_H_