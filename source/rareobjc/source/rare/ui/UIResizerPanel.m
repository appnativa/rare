//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/UIResizerPanel.java
//
//  Created by decoteaud on 7/29/15.
//

#include "IOSClass.h"
#include "com/appnativa/rare/ui/ImagePanel.h"
#include "com/appnativa/rare/ui/UIImage.h"
#include "com/appnativa/rare/ui/UIRectangle.h"
#include "com/appnativa/rare/ui/UIResizerPanel.h"
#include "com/appnativa/rare/ui/aImagePanel.h"
#include "com/appnativa/rare/ui/iImageObserver.h"
#include "com/appnativa/rare/widget/iWidget.h"
#include "java/io/IOException.h"
#include "java/net/URL.h"

@implementation RAREUIResizerPanel

- (id)initWithRAREiWidget:(id<RAREiWidget>)context {
  if (self = [super initWithRAREiWidget:context]) {
    fakeImage_ = [[RAREUIResizerPanel_FakeImage alloc] initWithInt:50 withInt:50];
    [self setImageExWithRAREUIImage:fakeImage_ withInt:-1 withInt:-1];
    [self setMovingAllowedWithBoolean:YES];
    [self setZoomingAllowedWithBoolean:YES];
    [self setRotatingAllowedWithBoolean:NO];
  }
  return self;
}

- (void)setBandBoundsWithInt:(int)x
                     withInt:(int)y
                     withInt:(int)width
                     withInt:(int)height {
  [((RAREUIResizerPanel_FakeImage *) nil_chk(fakeImage_)) setSizeWithInt:width withInt:height];
  [self init__WithInt:-1 withInt:-1 withBoolean:YES];
  [self moveImageWithFloat:x withFloat:y];
}

- (RAREUIRectangle *)getBanddBounds {
  return [[RAREUIRectangle alloc] initWithRAREUIRectangle:destBounds_];
}

- (void)setImageWithRAREUIImage:(RAREUIImage *)img {
}

- (void)setImageWithRAREUIImage:(RAREUIImage *)img
                        withInt:(int)width
                        withInt:(int)height {
}

- (void)setImageWithJavaNetURL:(JavaNetURL *)url {
}

- (void)copyAllFieldsTo:(RAREUIResizerPanel *)other {
  [super copyAllFieldsTo:other];
  other->fakeImage_ = fakeImage_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getBanddBounds", NULL, "LRAREUIRectangle", 0x1, NULL },
    { "setImageWithJavaNetURL:", NULL, "V", 0x1, "JavaIoIOException" },
  };
  static J2ObjcFieldInfo fields[] = {
    { "fakeImage_", NULL, 0x0, "LRAREUIResizerPanel_FakeImage" },
  };
  static J2ObjcClassInfo _RAREUIResizerPanel = { "UIResizerPanel", "com.appnativa.rare.ui", NULL, 0x1, 2, methods, 1, fields, 0, NULL};
  return &_RAREUIResizerPanel;
}

@end
@implementation RAREUIResizerPanel_FakeImage

- (id)initWithInt:(int)width
          withInt:(int)height {
  return [super init];
}

- (NSString *)description {
  return @"FakeImage";
}

- (NSString *)getLocation {
  return @"FakeImage";
}

- (BOOL)isLoaded {
  return YES;
}

- (BOOL)isLoadedWithRAREiImageObserver:(id<RAREiImageObserver>)is {
  return YES;
}

- (void)setSizeWithInt:(int)width
               withInt:(int)height {
  self->width_ = width;
  self->height_ = height;
}

- (int)getHeight {
  return height_;
}

- (int)getWidth {
  return width_;
}

- (RAREUIImage *)getSliceWithInt:(int)x
                         withInt:(int)y
                         withInt:(int)width
                         withInt:(int)height {
  return [[RAREUIResizerPanel_FakeImage alloc] initWithInt:width withInt:height];
}

- (RAREUIImage *)getSubimageWithInt:(int)x
                            withInt:(int)y
                            withInt:(int)width
                            withInt:(int)height {
  return [[RAREUIResizerPanel_FakeImage alloc] initWithInt:width withInt:height];
}

- (void)dispose {
}

- (void)copyAllFieldsTo:(RAREUIResizerPanel_FakeImage *)other {
  [super copyAllFieldsTo:other];
  other->height_ = height_;
  other->width_ = width_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getLocation", NULL, "LNSString", 0x1, NULL },
    { "isLoaded", NULL, "Z", 0x1, NULL },
    { "isLoadedWithRAREiImageObserver:", NULL, "Z", 0x1, NULL },
    { "getSliceWithInt:withInt:withInt:withInt:", NULL, "LRAREUIImage", 0x1, NULL },
    { "getSubimageWithInt:withInt:withInt:withInt:", NULL, "LRAREUIImage", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "width_", NULL, 0x0, "I" },
    { "height_", NULL, 0x0, "I" },
  };
  static J2ObjcClassInfo _RAREUIResizerPanel_FakeImage = { "FakeImage", "com.appnativa.rare.ui", "UIResizerPanel", 0x8, 5, methods, 2, fields, 0, NULL};
  return &_RAREUIResizerPanel_FakeImage;
}

@end