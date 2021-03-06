//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core-canvas/com/appnativa/rare/ui/canvas/Uint8ClampedArray.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREUint8ClampedArray_H_
#define _RAREUint8ClampedArray_H_

@class IOSByteArray;
@class JavaNioByteBuffer;

#import "JreEmulation.h"

@interface RAREUint8ClampedArray : NSObject {
 @public
  JavaNioByteBuffer *buffer_;
}

+ (long long int)BYTES_PER_ELEMENT;
+ (long long int *)BYTES_PER_ELEMENTRef;
- (id)initWithJavaNioByteBuffer:(JavaNioByteBuffer *)bb;
- (RAREUint8ClampedArray *)subarrayWithInt:(int)begin
                                   withInt:(int)end;
- (void)setWithByteArray:(IOSByteArray *)array
                 withInt:(int)offset;
- (void)setWithInt:(int)index
          withByte:(char)value;
- (char)getWithInt:(int)index;
- (int)getLength;
- (void)copyAllFieldsTo:(RAREUint8ClampedArray *)other;
@end

J2OBJC_FIELD_SETTER(RAREUint8ClampedArray, buffer_, JavaNioByteBuffer *)

typedef RAREUint8ClampedArray ComAppnativaRareUiCanvasUint8ClampedArray;

#endif // _RAREUint8ClampedArray_H_
