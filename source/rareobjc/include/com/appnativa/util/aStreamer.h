//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../util/src/com/appnativa/util/aStreamer.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREUTaStreamer_H_
#define _RAREUTaStreamer_H_

@class IOSBooleanArray;
@class IOSByteArray;
@class IOSCharArray;
@class IOSIntArray;
@class IOSLongArray;
@class IOSObjectArray;
@class JavaIoInputStream;
@class JavaIoOutputStream;
@class JavaIoReader;
@class JavaIoWriter;
@class JavaLangStringBuilder;
@class RAREUTByteArray;
@class RAREUTCharArray;
@class RAREUTSNumber;
@protocol RAREUTiCharsetHelper;

#import "JreEmulation.h"
#include "com/appnativa/util/iStreamable.h"

#define RAREUTaStreamer_LONG_16BIT_VALUE 65535
#define RAREUTaStreamer_LONG_24BIT_VALUE 16777215
#define RAREUTaStreamer_LONG_32BIT_VALUE 4294967295
#define RAREUTaStreamer_LONG_40BIT_VALUE 1099511627775
#define RAREUTaStreamer_LONG_48BIT_VALUE 281474976710655
#define RAREUTaStreamer_LONG_56BIT_VALUE 72057594037927935
#define RAREUTaStreamer_LONG_8BIT_VALUE 255

@interface RAREUTaStreamer : NSObject < RAREUTiStreamable, NSCopying > {
}

+ (long long int)LONG_16BIT_VALUE;
+ (long long int)LONG_24BIT_VALUE;
+ (long long int)LONG_32BIT_VALUE;
+ (long long int)LONG_40BIT_VALUE;
+ (long long int)LONG_48BIT_VALUE;
+ (long long int)LONG_56BIT_VALUE;
+ (long long int)LONG_8BIT_VALUE;
+ (IOSCharArray *)padding;
+ (NSString *)lineSeparator;
+ (NSString *)emptyString;
+ (BOOL)is64BitLong;
+ (int)decode2BLengthWithByteArray:(IOSByteArray *)A
                           withInt:(int)pos;
+ (int)decode4BLengthWithByteArray:(IOSByteArray *)A
                           withInt:(int)pos;
+ (void)encode2BLengthWithInt:(int)val
                withByteArray:(IOSByteArray *)outArg
                      withInt:(int)pos;
+ (void)encode4BLengthWithInt:(int)val
                withByteArray:(IOSByteArray *)outArg
                      withInt:(int)pos;
+ (int)encodeVarLengthWithInt:(int)val
    withJavaLangStringBuilder:(JavaLangStringBuilder *)outArg;
+ (int)encodeVarLengthWithInt:(int)val
             withJavaIoWriter:(JavaIoWriter *)outArg;
+ (int)getVarLengthWithInt:(int)val;
+ (int)getVarLengthWithLong:(long long int)val;
+ (int)encodeVarLengthWithInt:(int)val
                withByteArray:(IOSByteArray *)outArg
                      withInt:(int)pos;
+ (int)encodeVarLengthWithInt:(int)val
                withCharArray:(IOSCharArray *)outArg
                      withInt:(int)pos;
- (void)fromStreamWithJavaIoInputStream:(JavaIoInputStream *)inArg;
+ (BOOL)fromStreamWithBoolean:(BOOL)val
        withJavaIoInputStream:(JavaIoInputStream *)inArg;
+ (IOSBooleanArray *)fromStreamWithBooleanArray:(IOSBooleanArray *)a
                          withJavaIoInputStream:(JavaIoInputStream *)inArg;
+ (IOSByteArray *)fromStreamWithByteArray:(IOSByteArray *)a
                    withJavaIoInputStream:(JavaIoInputStream *)inArg;
+ (int)fromStreamWithInt:(int)val
   withJavaIoInputStream:(JavaIoInputStream *)inArg;
+ (IOSIntArray *)fromStreamWithIntArray:(IOSIntArray *)a
                  withJavaIoInputStream:(JavaIoInputStream *)inArg;
+ (void)fromStreamWithRAREUTiStreamable:(id<RAREUTiStreamable>)what
                  withJavaIoInputStream:(JavaIoInputStream *)inArg;
+ (long long int)fromStreamWithLong:(long long int)val
              withJavaIoInputStream:(JavaIoInputStream *)inArg;
+ (IOSLongArray *)fromStreamWithLongArray:(IOSLongArray *)a
                    withJavaIoInputStream:(JavaIoInputStream *)inArg;
+ (BOOL)fromStreamWithRAREUTSNumber:(RAREUTSNumber *)num
              withJavaIoInputStream:(JavaIoInputStream *)inArg;
+ (NSString *)fromStreamWithNSString:(NSString *)val
               withJavaIoInputStream:(JavaIoInputStream *)inArg;
+ (IOSObjectArray *)fromStreamWithNSStringArray:(IOSObjectArray *)a
                          withJavaIoInputStream:(JavaIoInputStream *)inArg;
+ (int)readWithJavaIoInputStream:(JavaIoInputStream *)inArg
                   withByteArray:(IOSByteArray *)use
                         withInt:(int)len;
+ (int)readWithJavaIoInputStream:(JavaIoInputStream *)inArg
             withRAREUTByteArray:(RAREUTByteArray *)use
                         withInt:(int)len;
+ (int)read2BBytesWithJavaIoInputStream:(JavaIoInputStream *)inArg
                    withRAREUTByteArray:(RAREUTByteArray *)use;
+ (int)read4BBytesWithJavaIoInputStream:(JavaIoInputStream *)inArg
                    withRAREUTByteArray:(RAREUTByteArray *)use;
+ (BOOL)readBooleanWithJavaIoInputStream:(JavaIoInputStream *)inArg;
+ (IOSBooleanArray *)readBooleanArrayWithJavaIoInputStream:(JavaIoInputStream *)inArg;
+ (IOSByteArray *)readBytesWithJavaIoInputStream:(JavaIoInputStream *)inArg;
+ (int)readBytesWithJavaIoInputStream:(JavaIoInputStream *)inArg
                  withRAREUTByteArray:(RAREUTByteArray *)use;
+ (int)readBytesExWithJavaIoInputStream:(JavaIoInputStream *)inArg
                    withRAREUTByteArray:(RAREUTByteArray *)use;
+ (int)readCharArrayWithRAREUTCharArray:(RAREUTCharArray *)outArg
                  withJavaIoInputStream:(JavaIoInputStream *)inArg
               withRAREUTiCharsetHelper:(id<RAREUTiCharsetHelper>)csh;
+ (int)readIntWithJavaIoInputStream:(JavaIoInputStream *)inArg;
+ (int)readIntWithJavaIoReader:(JavaIoReader *)inArg;
+ (IOSIntArray *)readIntArrayWithJavaIoInputStream:(JavaIoInputStream *)inArg;
+ (long long int)readLongWithJavaIoInputStream:(JavaIoInputStream *)inArg;
+ (IOSLongArray *)readLongArrayWithJavaIoInputStream:(JavaIoInputStream *)inArg;
+ (RAREUTSNumber *)readSNumberWithJavaIoInputStream:(JavaIoInputStream *)inArg;
+ (NSString *)readStringWithJavaIoInputStream:(JavaIoInputStream *)inArg;
+ (NSString *)readStringWithJavaIoReader:(JavaIoReader *)inArg;
+ (NSString *)readStringWithJavaIoInputStream:(JavaIoInputStream *)inArg
                     withRAREUTiCharsetHelper:(id<RAREUTiCharsetHelper>)csh;
+ (NSString *)readStringWithJavaIoInputStream:(JavaIoInputStream *)inArg
                          withRAREUTByteArray:(RAREUTByteArray *)ba
                     withRAREUTiCharsetHelper:(id<RAREUTiCharsetHelper>)csh;
+ (IOSObjectArray *)readStringArrayWithJavaIoInputStream:(JavaIoInputStream *)inArg
                                withRAREUTiCharsetHelper:(id<RAREUTiCharsetHelper>)csh;
+ (int)readVarLengthWithJavaIoInputStream:(JavaIoInputStream *)inArg;
+ (int)readVarLengthWithJavaIoReader:(JavaIoReader *)inArg;
+ (int)readVarLengthWithJavaIoInputStream:(JavaIoInputStream *)inArg
                      withRAREUTByteArray:(RAREUTByteArray *)ba;
- (void)toStreamWithJavaIoOutputStream:(JavaIoOutputStream *)outArg;
+ (void)toStreamWithBoolean:(BOOL)val
     withJavaIoOutputStream:(JavaIoOutputStream *)outArg;
+ (void)toStreamWithBooleanArray:(IOSBooleanArray *)a
          withJavaIoOutputStream:(JavaIoOutputStream *)outArg;
+ (void)toStreamWithByteArray:(IOSByteArray *)a
       withJavaIoOutputStream:(JavaIoOutputStream *)outArg;
+ (void)toStreamWithInt:(int)val
 withJavaIoOutputStream:(JavaIoOutputStream *)outArg;
+ (void)toStreamWithIntArray:(IOSIntArray *)a
      withJavaIoOutputStream:(JavaIoOutputStream *)outArg;
+ (void)toStreamWithRAREUTiStreamable:(id<RAREUTiStreamable>)what
               withJavaIoOutputStream:(JavaIoOutputStream *)outArg;
+ (void)toStreamWithLong:(long long int)val
  withJavaIoOutputStream:(JavaIoOutputStream *)outArg;
+ (void)toStreamWithLongArray:(IOSLongArray *)a
       withJavaIoOutputStream:(JavaIoOutputStream *)outArg;
+ (void)toStreamWithRAREUTSNumber:(RAREUTSNumber *)val
           withJavaIoOutputStream:(JavaIoOutputStream *)outArg;
+ (void)toStreamWithNSString:(NSString *)val
      withJavaIoOutputStream:(JavaIoOutputStream *)outArg;
+ (void)toStreamWithNSStringArray:(IOSObjectArray *)a
           withJavaIoOutputStream:(JavaIoOutputStream *)outArg;
+ (void)toStreamWithNSString:(NSString *)val
      withJavaIoOutputStream:(JavaIoOutputStream *)outArg
    withRAREUTiCharsetHelper:(id<RAREUTiCharsetHelper>)csh;
+ (void)toStreamWithNSStringArray:(IOSObjectArray *)a
           withJavaIoOutputStream:(JavaIoOutputStream *)outArg
         withRAREUTiCharsetHelper:(id<RAREUTiCharsetHelper>)csh;
+ (NSString *)toStringExWithIntArray:(IOSIntArray *)val
                             withInt:(int)depth;
+ (NSString *)toStringExWithLongArray:(IOSLongArray *)val
                              withInt:(int)depth;
+ (NSString *)toStringExWithNSStringArray:(IOSObjectArray *)val
                                  withInt:(int)depth;
+ (void)writeBytesWithRAREUTByteArray:(RAREUTByteArray *)outArg
                        withByteArray:(IOSByteArray *)b;
+ (int)writeBytesWithRAREUTByteArray:(RAREUTByteArray *)outArg
                       withByteArray:(IOSByteArray *)b
                             withInt:(int)pos
                             withInt:(int)len;
+ (int)writeBytesWithJavaIoOutputStream:(JavaIoOutputStream *)outArg
                          withByteArray:(IOSByteArray *)b
                                withInt:(int)pos
                                withInt:(int)len;
+ (int)writeIntWithInt:(int)val
withJavaIoOutputStream:(JavaIoOutputStream *)outArg;
+ (int)writeIntWithInt:(int)val
      withJavaIoWriter:(JavaIoWriter *)outArg;
+ (int)writeLongWithLong:(long long int)val
  withJavaIoOutputStream:(JavaIoOutputStream *)outArg;
+ (void)writePaddingWithRAREUTCharArray:(RAREUTCharArray *)outArg
                                withInt:(int)depth;
+ (void)writePaddingWithJavaLangStringBuilder:(JavaLangStringBuilder *)outArg
                                      withInt:(int)depth;
+ (void)writePaddingWithJavaIoWriter:(JavaIoWriter *)outArg
                             withInt:(int)depth;
+ (void)writeStringWithNSString:(NSString *)val
         withJavaIoOutputStream:(JavaIoOutputStream *)outArg;
+ (void)writeStringWithNSString:(NSString *)val
               withJavaIoWriter:(JavaIoWriter *)outArg;
+ (void)writeStringWithNSString:(NSString *)val
         withJavaIoOutputStream:(JavaIoOutputStream *)outArg
       withRAREUTiCharsetHelper:(id<RAREUTiCharsetHelper>)csh;
+ (void)writeStringWithCharArray:(IOSCharArray *)val
                         withInt:(int)pos
                         withInt:(int)len
                withJavaIoWriter:(JavaIoWriter *)outArg;
+ (int)writeVarLengthWithInt:(int)val
      withJavaIoOutputStream:(JavaIoOutputStream *)outArg;
- (id)init;
- (id)copyWithZone:(NSZone *)zone;
@end

typedef RAREUTaStreamer ComAppnativaUtilAStreamer;

#endif // _RAREUTaStreamer_H_
