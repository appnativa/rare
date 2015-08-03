//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: apache_harmony/classlib/modules/luni/src/main/java/java/io/BufferedInputStream.java
//
//  Created by tball on 11/23/13.
//

#ifndef _JavaIoBufferedInputStream_H_
#define _JavaIoBufferedInputStream_H_

@class IOSByteArray;
@class JavaIoInputStream;

#import "JreEmulation.h"
#include "java/io/FilterInputStream.h"

@interface JavaIoBufferedInputStream : JavaIoFilterInputStream {
 @public
  IOSByteArray *buf_;
  int count_;
  int marklimit_;
  int markpos_;
  int pos_;
}

- (id)initWithJavaIoInputStream:(JavaIoInputStream *)inArg;
- (id)initWithJavaIoInputStream:(JavaIoInputStream *)inArg
                        withInt:(int)size;
- (int)available;
- (void)close;
- (int)fillbufWithJavaIoInputStream:(JavaIoInputStream *)localIn
                      withByteArray:(IOSByteArray *)localBuf;
- (void)markWithInt:(int)readlimit;
- (BOOL)markSupported;
- (int)read;
- (int)readWithByteArray:(IOSByteArray *)buffer
                 withInt:(int)offset
                 withInt:(int)length;
- (void)reset;
- (long long int)skipWithLong:(long long int)amount;
- (void)copyAllFieldsTo:(JavaIoBufferedInputStream *)other;
@end

J2OBJC_FIELD_SETTER(JavaIoBufferedInputStream, buf_, IOSByteArray *)

#endif // _JavaIoBufferedInputStream_H_
