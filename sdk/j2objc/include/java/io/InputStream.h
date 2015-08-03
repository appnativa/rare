//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: apache_harmony/classlib/modules/luni/src/main/java/java/io/InputStream.java
//
//  Created by tball on 11/23/13.
//

#ifndef _JavaIoInputStream_H_
#define _JavaIoInputStream_H_

@class IOSByteArray;

#import "JreEmulation.h"
#include "java/io/Closeable.h"

@interface JavaIoInputStream : NSObject < JavaIoCloseable > {
}

+ (IOSByteArray *)skipBuf;
+ (void)setSkipBuf:(IOSByteArray *)skipBuf;
- (id)init;
- (int)available;
- (void)close;
- (void)markWithInt:(int)readlimit;
- (BOOL)markSupported;
- (int)read;
- (int)readWithByteArray:(IOSByteArray *)b;
- (int)readWithByteArray:(IOSByteArray *)b
                 withInt:(int)offset
                 withInt:(int)length;
- (void)reset;
- (long long int)skipWithLong:(long long int)n;
@end

#endif // _JavaIoInputStream_H_
