//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: apache_harmony/classlib/modules/nio/src/main/java/common/java/nio/ShortArrayBuffer.java
//
//  Created by tball on 11/23/13.
//

#ifndef _JavaNioShortArrayBuffer_H_
#define _JavaNioShortArrayBuffer_H_

@class IOSShortArray;
@class JavaNioByteOrder;

#import "JreEmulation.h"
#include "java/nio/ShortBuffer.h"

@interface JavaNioShortArrayBuffer : JavaNioShortBuffer {
 @public
  IOSShortArray *backingArray_;
  int offset_;
}

- (id)initWithShortArray:(IOSShortArray *)array;
- (id)initWithInt:(int)capacity;
- (id)initWithInt:(int)capacity
   withShortArray:(IOSShortArray *)backingArray
          withInt:(int)offset;
- (short int)get;
- (short int)getWithInt:(int)index;
- (JavaNioShortBuffer *)getWithShortArray:(IOSShortArray *)dest
                                  withInt:(int)off
                                  withInt:(int)len;
- (BOOL)isDirect;
- (JavaNioByteOrder *)order;
- (void)copyAllFieldsTo:(JavaNioShortArrayBuffer *)other;
@end

J2OBJC_FIELD_SETTER(JavaNioShortArrayBuffer, backingArray_, IOSShortArray *)

#endif // _JavaNioShortArrayBuffer_H_
