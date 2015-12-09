//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/ui/UIImage.java
//
//  Created by decoteaud on 12/8/15.
//

#include "IOSClass.h"
#include "IOSIntArray.h"
#include "com/appnativa/rare/platform/apple/ui/util/AppleGraphics.h"
#include "com/appnativa/rare/platform/apple/ui/util/ImageUtils.h"
#include "com/appnativa/rare/ui/UIColor.h"
#include "com/appnativa/rare/ui/UIImage.h"
#include "com/appnativa/rare/ui/iPlatformGraphics.h"
#include "com/appnativa/rare/ui/painter/iImagePainter.h"
#include "java/lang/IllegalArgumentException.h"

@implementation RAREUIImage

- (id)initWithId:(id)image {
  if (self = [super init]) {
    smoothScale_ = YES;
    self->proxy_ = image;
  }
  return self;
}

- (id)initWithInt:(int)width
          withInt:(int)height {
  if (self = [super init]) {
    smoothScale_ = YES;
    self->proxy_ = [RAREImageUtils createImageProxyWithInt:width withInt:height];
  }
  return self;
}

- (id)initWithId:(id)image
    withNSString:(NSString *)location {
  if (self = [super init]) {
    smoothScale_ = YES;
    self->proxy_ = image;
    self->location_ = location;
  }
  return self;
}

- (id)init {
  if (self = [super init]) {
    smoothScale_ = YES;
  }
  return self;
}

- (void)addReflectionImageWithInt:(int)y
                          withInt:(int)height
                        withFloat:(float)opacity
                          withInt:(int)gap {
  if ([self getHeight] < y + gap + height) {
    @throw [[JavaLangIllegalArgumentException alloc] initWithNSString:@"Image not tall enough to support adding a reflection with the specified dimensions.\nMight want to use createReflection"];
  }
  proxy_ = [RAREImageUtils addReflectionWithId:proxy_ withInt:y withInt:height withFloat:opacity withInt:gap];
}

- (void)blurImage {
  proxy_ = [RAREImageUtils blurImageWithId:proxy_];
}

- (void)cancel {
  if (proxy_ != nil) {
    canceled_ = YES;
  }
}

- (id)clone {
  RAREUIImage *img = (RAREUIImage *) check_class_cast([super clone], [RAREUIImage class]);
  ((RAREUIImage *) nil_chk(img))->proxy_ = [RAREImageUtils copyImageWithId:proxy_];
  return img;
}

- (void)contstrainWithInt:(int)width
                  withInt:(int)height
                  withInt:(int)constraints
          withRAREUIColor:(RAREUIColor *)bg
withRAREiImagePainter_ScalingTypeEnum:(RAREiImagePainter_ScalingTypeEnum *)st {
  constraintWidth_ = width;
  constraintHeight_ = height;
  constraintType_ = constraints;
  if (st != nil) {
    switch ([st ordinal]) {
      case RAREiImagePainter_ScalingType_NEAREST_NEIGHBOR:
      case RAREiImagePainter_ScalingType_NEAREST_NEIGHBOR_CACHED:
      smoothScale_ = NO;
      break;
      default:
      break;
    }
  }
}

- (RAREUIImage *)createDisabledImage {
  return [[RAREUIImage alloc] initWithId:[RAREImageUtils createDisabledImageWithId:proxy_]];
}

- (id<RAREiPlatformGraphics>)createGraphics {
  return [[RAREAppleGraphics alloc] initWithId:[RAREImageUtils createContextWithId:proxy_] withRAREView:nil];
}

- (RAREUIImage *)createReflectionImageWithInt:(int)y
                                      withInt:(int)height
                                    withFloat:(float)opacity
                                      withInt:(int)gap {
  return [[RAREUIImage alloc] initWithId:[RAREImageUtils createReflectionWithId:proxy_ withInt:y withInt:height withFloat:opacity withInt:gap]];
}

- (void)dispose {
  [super dispose];
  proxy_ = nil;
}

+ (RAREUIImage *)fromNativeImageWithId:(id)nativeImage {
  return [[RAREUIImage alloc] initWithId:[RAREImageUtils createImageProxyWithId:nativeImage]];
}

- (void)scale__WithInt:(int)width
               withInt:(int)height {
  constraintWidth_ = width;
  constraintHeight_ = height;
  smoothScale_ = YES;
}

- (void)setPixelWithInt:(int)x
                withInt:(int)y
                withInt:(int)value {
  [RAREImageUtils setPixelWithId:proxy_ withInt:x withInt:y withInt:value];
}

- (void)setPixelsWithIntArray:(IOSIntArray *)pixels
                      withInt:(int)x
                      withInt:(int)y
                      withInt:(int)width
                      withInt:(int)height {
  [RAREImageUtils setPixelsWithId:proxy_ withIntArray:pixels withInt:x withInt:y withInt:width withInt:height];
}

- (void)setSliceWithInt:(int)x
                withInt:(int)y
                withInt:(int)width
                withInt:(int)height {
  proxy_ = [RAREImageUtils getSubImageWithId:proxy_ withInt:x withInt:y withInt:width withInt:height];
}

- (int)getHeight {
  return (proxy_ == nil) ? -1 : (int) [RAREImageUtils getHeightWithId:proxy_];
}

- (int)getPixelWithInt:(int)x
               withInt:(int)y {
  return [RAREImageUtils getPixelWithId:proxy_ withInt:x withInt:y];
}

- (IOSIntArray *)getPixelsWithIntArray:(IOSIntArray *)pixels
                               withInt:(int)x
                               withInt:(int)y
                               withInt:(int)width
                               withInt:(int)height {
  if (pixels == nil) {
    pixels = [IOSIntArray arrayWithLength:width * height];
  }
  else if ((int) [pixels count] < width * height) {
    @throw [[JavaLangIllegalArgumentException alloc] initWithNSString:@"Pixels array must have a length >= w * h"];
  }
  [RAREImageUtils getPixelsWithId:proxy_ withInt:x withInt:y withInt:width withInt:height withIntArray:pixels];
  return pixels;
}

- (id)getProxy {
  return proxy_;
}

- (id)getNativeImage {
  return (proxy_ == nil) ? nil : [RAREImageUtils getNativeImageWithId:proxy_];
}

- (void)setProxyWithId:(id)proxy {
  self->proxy_ = proxy;
}

+ (id)createImageProxyFromNativeImageWithId:(id)nativeImage {
  return [RAREImageUtils createImageProxyWithId:nativeImage];
}

- (RAREUIImage *)getSliceWithInt:(int)x
                         withInt:(int)y
                         withInt:(int)width
                         withInt:(int)height {
  RAREUIImage *img = (RAREUIImage *) check_class_cast([self clone], [RAREUIImage class]);
  [((RAREUIImage *) nil_chk(img)) setSliceWithInt:x withInt:y withInt:width withInt:height];
  return img;
}

- (RAREUIImage *)getSubimageWithInt:(int)x
                            withInt:(int)y
                            withInt:(int)width
                            withInt:(int)height {
  return [[RAREUIImage alloc] initWithId:[RAREImageUtils getSubImageWithId:proxy_ withInt:x withInt:y withInt:width withInt:height]];
}

- (int)getWidth {
  return (proxy_ == nil) ? -1 : (int) [RAREImageUtils getWidthWithId:proxy_];
}

- (BOOL)isNinePatchEx {
  return (proxy_ != nil) && [super isNinePatchEx];
}

- (BOOL)isLoadedExWithBoolean:(BOOL)hasObserver {
  return proxy_ != nil;
}

- (id)copyWithZone:(NSZone *)zone {
  return [self clone];
}

- (void)copyAllFieldsTo:(RAREUIImage *)other {
  [super copyAllFieldsTo:other];
  other->proxy_ = proxy_;
  other->smoothScale_ = smoothScale_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "init", NULL, NULL, 0x4, NULL },
    { "clone", NULL, "LNSObject", 0x1, NULL },
    { "createDisabledImage", NULL, "LRAREUIImage", 0x1, NULL },
    { "createGraphics", NULL, "LRAREiPlatformGraphics", 0x1, NULL },
    { "createReflectionImageWithInt:withInt:withFloat:withInt:", NULL, "LRAREUIImage", 0x1, NULL },
    { "fromNativeImageWithId:", NULL, "LRAREUIImage", 0x9, NULL },
    { "getPixelsWithIntArray:withInt:withInt:withInt:withInt:", NULL, "LIOSIntArray", 0x1, NULL },
    { "getProxy", NULL, "LNSObject", 0x1, NULL },
    { "getNativeImage", NULL, "LNSObject", 0x1, NULL },
    { "createImageProxyFromNativeImageWithId:", NULL, "LNSObject", 0x9, NULL },
    { "getSliceWithInt:withInt:withInt:withInt:", NULL, "LRAREUIImage", 0x1, NULL },
    { "getSubimageWithInt:withInt:withInt:withInt:", NULL, "LRAREUIImage", 0x1, NULL },
    { "isNinePatchEx", NULL, "Z", 0x1, NULL },
    { "isLoadedExWithBoolean:", NULL, "Z", 0x4, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "smoothScale_", NULL, 0x0, "Z" },
    { "proxy_", NULL, 0x4, "LNSObject" },
  };
  static J2ObjcClassInfo _RAREUIImage = { "UIImage", "com.appnativa.rare.ui", NULL, 0x1, 14, methods, 2, fields, 0, NULL};
  return &_RAREUIImage;
}

@end
