//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core-canvas/com/appnativa/rare/ui/canvas/BytePixelData.java
//
//  Created by decoteaud on 3/11/16.
//

#include "IOSByteArray.h"
#include "com/appnativa/rare/ui/UIImage.h"
#include "com/appnativa/rare/ui/canvas/BytePixelData.h"
#include "com/appnativa/rare/ui/canvas/Uint8ClampedArray.h"
#include "java/nio/ByteBuffer.h"

@implementation RAREBytePixelData

- (id)initWithJavaNioByteBuffer:(JavaNioByteBuffer *)bb
                        withInt:(int)width
                        withInt:(int)height {
  if (self = [super init]) {
    self->data_ = [[RAREUint8ClampedArray alloc] initWithJavaNioByteBuffer:bb];
    self->width_ = width;
    self->height_ = height;
  }
  return self;
}

- (void)updateImageWithRAREUIImage:(RAREUIImage *)image
                           withInt:(int)dx
                           withInt:(int)dy
                           withInt:(int)dirtyWidth
                           withInt:(int)dirtyHeight {
  [((RAREUIImage *) nil_chk(image)) setImageBytesWithInt:dx withInt:dy withInt:dirtyWidth withInt:dirtyHeight withJavaNioByteBuffer:((RAREUint8ClampedArray *) nil_chk(data_))->buffer_];
}

- (RAREUint8ClampedArray *)getData {
  return data_;
}

- (int)getHeight {
  return height_;
}

- (int)getWidth {
  return width_;
}

- (void)copyAllFieldsTo:(RAREBytePixelData *)other {
  [super copyAllFieldsTo:other];
  other->byteData_ = byteData_;
  other->data_ = data_;
  other->height_ = height_;
  other->width_ = width_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getData", NULL, "LRAREUint8ClampedArray", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "data_", NULL, 0x1, "LRAREUint8ClampedArray" },
    { "height_", NULL, 0x11, "I" },
    { "width_", NULL, 0x11, "I" },
    { "byteData_", NULL, 0x0, "LIOSByteArray" },
  };
  static J2ObjcClassInfo _RAREBytePixelData = { "BytePixelData", "com.appnativa.rare.ui.canvas", NULL, 0x1, 1, methods, 4, fields, 0, NULL};
  return &_RAREBytePixelData;
}

@end
