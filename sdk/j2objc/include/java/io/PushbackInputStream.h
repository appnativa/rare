//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: apache_harmony/classlib/modules/luni/src/main/java/java/io/PushbackInputStream.java
//
//  Created by tball on 11/23/13.
//

#ifndef _JavaIoPushbackInputStream_H_
#define _JavaIoPushbackInputStream_H_

@class IOSByteArray;
@class JavaIoInputStream;

#import "JreEmulation.h"
#include "java/io/FilterInputStream.h"

@interface JavaIoPushbackInputStream : JavaIoFilterInputStream {
 @public
  IOSByteArray *buf_;
  int pos_;
}

- (id)initWithJavaIoInputStream:(JavaIoInputStream *)inArg;
- (id)initWithJavaIoInputStream:(JavaIoInputStream *)inArg
                        withInt:(int)size;
- (int)available;
- (void)close;
- (BOOL)markSupported;
- (int)read;
- (int)readWithByteArray:(IOSByteArray *)buffer
                 withInt:(int)offset
                 withInt:(int)length;
- (long long int)skipWithLong:(long long int)count;
- (void)unreadWithByteArray:(IOSByteArray *)buffer;
- (void)unreadWithByteArray:(IOSByteArray *)buffer
                    withInt:(int)offset
                    withInt:(int)length;
- (void)unreadWithInt:(int)oneByte;
- (void)markWithInt:(int)readlimit;
- (void)reset;
- (void)copyAllFieldsTo:(JavaIoPushbackInputStream *)other;
@end

J2OBJC_FIELD_SETTER(JavaIoPushbackInputStream, buf_, IOSByteArray *)

#endif // _JavaIoPushbackInputStream_H_
