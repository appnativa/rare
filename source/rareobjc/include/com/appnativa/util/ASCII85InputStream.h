//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../util/src/com/appnativa/util/ASCII85InputStream.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREUTASCII85InputStream_H_
#define _RAREUTASCII85InputStream_H_

@class IOSIntArray;
@class JavaIoInputStream;

#import "JreEmulation.h"
#include "java/io/FilterInputStream.h"

@interface RAREUTASCII85InputStream : JavaIoFilterInputStream {
 @public
  int nextByte_, markNextByte_;
  int count_, markCount_;
  BOOL decoding_, markDecoding_;
  BOOL maybeStarting_, markMaybeStarting_;
  BOOL maybeStopping_, markMaybeStopping_;
  BOOL preserveUnencoded_;
  int tuple_, markTuple_;
  int tupleBytesRemaining_, markTupleBytesRemaining_;
  int tupleSendStartBytes_, markTupleSendStartBytes_;
}

+ (IOSIntArray *)POW85;
- (id)initWithJavaIoInputStream:(JavaIoInputStream *)inArg;
- (id)initWithJavaIoInputStream:(JavaIoInputStream *)inArg
                    withBoolean:(BOOL)preserveUnencoded;
- (void)markWithInt:(int)readlimit;
- (int)read;
- (void)reset;
- (long long int)skipWithLong:(long long int)n;
- (void)copyAllFieldsTo:(RAREUTASCII85InputStream *)other;
@end

typedef RAREUTASCII85InputStream ComAppnativaUtilASCII85InputStream;

#endif // _RAREUTASCII85InputStream_H_
