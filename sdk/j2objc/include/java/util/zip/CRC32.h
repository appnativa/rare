//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: Classes/java/util/zip/CRC32.java
//
//  Created by tball on 11/23/13.
//

#ifndef _JavaUtilZipCRC32_H_
#define _JavaUtilZipCRC32_H_

@class IOSByteArray;

#import "JreEmulation.h"
#include "java/util/zip/Checksum.h"

@interface JavaUtilZipCRC32 : NSObject < JavaUtilZipChecksum > {
 @public
  long long int crc_;
  long long int tbytes_;
}

- (long long int)getValue;
- (void)reset;
- (void)updateWithInt:(int)val;
- (void)updateWithByteArray:(IOSByteArray *)buf;
- (void)updateWithByteArray:(IOSByteArray *)buf
                    withInt:(int)offset
                    withInt:(int)byteCount;
- (long long int)updateImplWithByteArray:(IOSByteArray *)buf
                                 withInt:(int)offset
                                 withInt:(int)byteCount
                                withLong:(long long int)crc1;
- (long long int)updateByteImplWithByte:(char)val
                               withLong:(long long int)crc1;
- (id)init;
- (void)copyAllFieldsTo:(JavaUtilZipCRC32 *)other;
@end

#endif // _JavaUtilZipCRC32_H_
