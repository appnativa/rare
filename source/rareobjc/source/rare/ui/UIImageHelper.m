//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/UIImageHelper.java
//
//  Created by decoteaud on 7/29/15.
//

#include "IOSClass.h"
#include "com/appnativa/rare/Platform.h"
#include "com/appnativa/rare/iConstants.h"
#include "com/appnativa/rare/iPlatformAppContext.h"
#include "com/appnativa/rare/net/iURLConnection.h"
#include "com/appnativa/rare/platform/PlatformHelper.h"
#include "com/appnativa/rare/ui/Transform.h"
#include "com/appnativa/rare/ui/UIDimension.h"
#include "com/appnativa/rare/ui/UIImage.h"
#include "com/appnativa/rare/ui/UIImageHelper.h"
#include "com/appnativa/rare/ui/UIImageIcon.h"
#include "com/appnativa/rare/ui/UIRectangle.h"
#include "com/appnativa/rare/ui/Utils.h"
#include "com/appnativa/rare/ui/iPlatformGraphics.h"
#include "com/appnativa/rare/ui/iPlatformIcon.h"
#include "com/appnativa/rare/ui/painter/UIImagePainter.h"
#include "com/appnativa/rare/ui/painter/iImagePainter.h"
#include "com/appnativa/rare/viewer/iViewer.h"
#include "com/appnativa/rare/widget/iWidget.h"
#include "com/appnativa/spot/SPOTPrintableString.h"
#include "java/io/IOException.h"
#include "java/lang/Exception.h"
#include "java/lang/Math.h"
#include "java/net/URL.h"
#include "java/util/Map.h"

@implementation RAREUIImageHelper

static RAREUIImageIcon * RAREUIImageHelper__animatedSpinner_;
static RAREUIImageIcon * RAREUIImageHelper__animatedSpinnerSmall_;

+ (RAREUIImageIcon *)_animatedSpinner {
  return RAREUIImageHelper__animatedSpinner_;
}

+ (void)set_animatedSpinner:(RAREUIImageIcon *)_animatedSpinner {
  RAREUIImageHelper__animatedSpinner_ = _animatedSpinner;
}

+ (RAREUIImageIcon *)_animatedSpinnerSmall {
  return RAREUIImageHelper__animatedSpinnerSmall_;
}

+ (void)set_animatedSpinnerSmall:(RAREUIImageIcon *)_animatedSpinnerSmall {
  RAREUIImageHelper__animatedSpinnerSmall_ = _animatedSpinnerSmall;
}

+ (id<RAREiImagePainter>)configureImageWithRAREiWidget:(id<RAREiWidget>)context
                                 withRAREiImagePainter:(id<RAREiImagePainter>)painter
                               withSPOTPrintableString:(SPOTPrintableString *)imgURL
                                           withBoolean:(BOOL)emptyOk {
  return [RAREUIImageHelper configureImageWithRAREiWidget:context withRAREiImagePainter:painter withNSString:[((SPOTPrintableString *) nil_chk(imgURL)) getValue] withJavaUtilMap:[imgURL spot_getAttributesEx] withBoolean:emptyOk];
}

+ (id<RAREiImagePainter>)configureImageWithRAREiWidget:(id<RAREiWidget>)context
                                 withRAREiImagePainter:(id<RAREiImagePainter>)painter
                                          withNSString:(NSString *)url
                                       withJavaUtilMap:(id<JavaUtilMap>)attrs
                                           withBoolean:(BOOL)emptyOk {
  return [RAREUtils configureImageWithRAREiWidget:context withRAREiImagePainter:painter withNSString:url withJavaUtilMap:attrs withBoolean:emptyOk];
}

+ (id<RAREiPlatformIcon>)createDisabledIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon {
  return [RAREaPlatformHelper createDisabledIconWithRAREiPlatformIcon:icon];
}

+ (RAREUIImage *)createImageWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon {
  RAREUIImage *img = [[RAREUIImage alloc] initWithInt:[((id<RAREiPlatformIcon>) nil_chk(icon)) getIconWidth] withInt:[icon getIconHeight]];
  id<RAREiPlatformGraphics> g = [img createGraphics];
  if ([RAREPlatform isIOS] || ([RAREPlatform isMac] && ![RAREPlatform isJava])) {
    RARETransform *t = [[RARETransform alloc] init];
    [t scale__WithFloat:1 withFloat:-1];
    [t translateWithFloat:0 withFloat:-[img getHeight]];
    [((id<RAREiPlatformGraphics>) nil_chk(g)) setTransformWithRAREiTransform:t];
  }
  [((id<RAREiPlatformGraphics>) nil_chk(g)) clearRectWithRAREUIColor:nil withFloat:0 withFloat:0 withFloat:[icon getIconWidth] withFloat:[icon getIconHeight]];
  [icon paintWithRAREiPlatformGraphics:g withFloat:0 withFloat:0 withFloat:[icon getIconWidth] withFloat:[icon getIconHeight]];
  [g dispose];
  return img;
}

+ (RAREUIImageIcon *)createIconWithJavaNetURL:(JavaNetURL *)url
                                  withBoolean:(BOOL)defer {
  return [RAREUIImageHelper createIconWithJavaNetURL:url withNSString:nil withBoolean:defer withFloat:0];
}

+ (RAREUIImageIcon *)createIconWithJavaNetURL:(JavaNetURL *)url
                                  withBoolean:(BOOL)defer
                                    withFloat:(float)density {
  return [RAREUIImageHelper createIconWithJavaNetURL:url withNSString:nil withBoolean:defer withFloat:density];
}

+ (RAREUIImageIcon *)createIconWithJavaNetURL:(JavaNetURL *)url
                                 withNSString:(NSString *)description_
                                  withBoolean:(BOOL)defer
                                    withFloat:(float)density {
  return [RAREaPlatformHelper createIconWithJavaNetURL:url withNSString:description_ withBoolean:defer withFloat:density];
}

+ (RAREUIImage *)createImageWithJavaNetURL:(JavaNetURL *)url
                               withBoolean:(BOOL)defer {
  return [RAREaPlatformHelper createImageWithJavaNetURL:url withBoolean:defer withFloat:0];
}

+ (RAREUIImage *)createImageWithJavaNetURL:(JavaNetURL *)url
                               withBoolean:(BOOL)defer
                                 withFloat:(float)density {
  return [RAREaPlatformHelper createImageWithJavaNetURL:url withBoolean:defer withFloat:density];
}

+ (id<RAREiImagePainter>)createImagePainterWithRAREUIImage:(RAREUIImage *)image {
  return [[RAREUIImagePainter alloc] initWithRAREUIImage:image];
}

+ (id<RAREiPlatformIcon>)createStateListIconWithNSString:(NSString *)icon
                                         withRAREiWidget:(id<RAREiWidget>)context {
  return [RAREaPlatformHelper createStateListIconWithNSString:icon withRAREiWidget:context];
}

+ (RAREUIImage *)scaleImageWithRAREUIImage:(RAREUIImage *)img
                                   withInt:(int)width
                                   withInt:(int)height {
  return [RAREaPlatformHelper scaleImageWithRAREUIImage:img withInt:width withInt:height];
}

+ (void)setAnimatedSpinnerWithRAREUIImageIcon:(RAREUIImageIcon *)icon {
  RAREUIImageHelper__animatedSpinner_ = icon;
}

+ (void)setAnimatedSpinnerSmallWithRAREUIImageIcon:(RAREUIImageIcon *)icon {
  RAREUIImageHelper__animatedSpinnerSmall_ = icon;
}

+ (RAREUIImageIcon *)getAnimatedSpinner {
  if (RAREUIImageHelper__animatedSpinner_ == nil) {
    return [((id<RAREiPlatformAppContext>) nil_chk([RAREPlatform getAppContext])) getResourceAsIconWithNSString:@"animated_spinner"];
  }
  return RAREUIImageHelper__animatedSpinner_;
}

+ (RAREUIImageIcon *)getAnimatedSpinnerSmall {
  if (RAREUIImageHelper__animatedSpinnerSmall_ == nil) {
    return [((id<RAREiPlatformAppContext>) nil_chk([RAREPlatform getAppContext])) getResourceAsIconWithNSString:@"animated_spinner_small"];
  }
  return RAREUIImageHelper__animatedSpinnerSmall_;
}

+ (RAREUIImage *)getImageWithRAREiWidget:(id<RAREiWidget>)context
                            withNSString:(NSString *)image
                             withBoolean:(BOOL)deferred
                     withRAREUIDimension:(RAREUIDimension *)size
   withRAREiImagePainter_ScalingTypeEnum:(RAREiImagePainter_ScalingTypeEnum *)st
                               withFloat:(float)density {
  if (image == nil) {
    return nil;
  }
  if (context == nil) {
    context = [RAREPlatform getContextRootViewer];
  }
  id<RAREiPlatformAppContext> app = [((id<RAREiWidget>) nil_chk(context)) getAppContext];
  RAREUIRectangle *rect = nil;
  if ([((NSString *) nil_chk(image)) hasSuffix:@"]"]) {
    int n = [image lastIndexOf:'['];
    if (n > 0) {
      NSString *s = [image substring:n + 1 endIndex:[image sequenceLength] - 1];
      image = [image substring:0 endIndex:n];
      rect = [RAREUtils getRectangleWithNSString:s];
    }
  }
  RAREUIImage *img = nil;
  if ([((NSString *) nil_chk(image)) hasPrefix:[RAREiConstants RESOURCE_PREFIX]]) {
    img = [((id<RAREiPlatformAppContext>) nil_chk(app)) getResourceAsImageWithNSString:[image substring:9]];
  }
  else {
    @try {
      JavaNetURL *url = [context getURLWithNSString:image];
      if (url != nil) {
        if ([image hasPrefix:[RAREiConstants SCRIPT_PREFIX]]) {
          img = (RAREUIImage *) check_class_cast([((id<RAREiURLConnection>) nil_chk([((id<RAREiPlatformAppContext>) nil_chk(app)) openConnectionWithJavaNetURL:url])) getContent], [RAREUIImage class]);
        }
        else {
          img = [RAREUIImageHelper createImageWithJavaNetURL:url withBoolean:deferred withFloat:density];
        }
      }
    }
    @catch (JavaLangException *e) {
      [RAREPlatform ignoreExceptionWithNSString:nil withJavaLangThrowable:e];
    }
  }
  if (img != nil) {
    if (st != nil) {
      [img setScalingTypeWithRAREiImagePainter_ScalingTypeEnum:st];
    }
    if (rect != nil) {
      img = [RAREUIImageHelper getSliceWithRAREUIImage:img withRAREUIRectangle:rect];
    }
    if (size != nil) {
      img = [RAREUIImageHelper scaleImageWithRAREUIImage:img withInt:[size intWidth] withInt:[size intHeight]];
    }
  }
  return img;
}

+ (RAREUIImage *)getImageWithRAREiWidget:(id<RAREiWidget>)context
                          withJavaNetURL:(JavaNetURL *)url
                             withBoolean:(BOOL)deferred
                     withRAREUIDimension:(RAREUIDimension *)size
   withRAREiImagePainter_ScalingTypeEnum:(RAREiImagePainter_ScalingTypeEnum *)st
                               withFloat:(float)density {
  if (url == nil) {
    return nil;
  }
  RAREUIImage *img = nil;
  @try {
    img = [RAREaPlatformHelper createImageWithJavaNetURL:url withBoolean:deferred withFloat:density];
    if (st != nil) {
      [((RAREUIImage *) nil_chk(img)) setScalingTypeWithRAREiImagePainter_ScalingTypeEnum:st];
    }
    if (size != nil) {
      img = [RAREaPlatformHelper scaleImageWithRAREUIImage:img withInt:[size intWidth] withInt:[size intHeight]];
    }
  }
  @catch (JavaLangException *e) {
    [RAREPlatform ignoreExceptionWithNSString:nil withJavaLangThrowable:e];
  }
  return img;
}

+ (RAREUIImage *)getSliceWithRAREUIImage:(RAREUIImage *)image
                     withRAREUIRectangle:(RAREUIRectangle *)rect {
  return [RAREUtils getSliceWithRAREUIImage:image withRAREUIRectangle:rect];
}

+ (RAREUIImage *)getSliceWithRAREUIImage:(RAREUIImage *)image
                                 withInt:(int)pos
                                 withInt:(int)size {
  return [RAREUtils getSliceWithRAREUIImage:image withInt:pos withInt:size];
}

+ (RAREUIImage *)rotateImageWithRAREUIImage:(RAREUIImage *)image
                                    withInt:(int)rotation {
  int iw = [((RAREUIImage *) nil_chk(image)) getWidth];
  int ih = [image getHeight];
  int width = iw;
  int height = ih;
  int n;
  RARETransform *t = [[RARETransform alloc] init];
  switch (rotation) {
    case 90:
    [t rotateWithFloat:(float) [JavaLangMath toRadiansWithDouble:90]];
    [t translateWithFloat:0 withFloat:-ih];
    n = width;
    width = height;
    height = n;
    break;
    case 180:
    case -180:
    if (![RAREaPlatformHelper areImagesUpsideDown]) {
      [t scale__WithFloat:1 withFloat:-1];
      [t translateWithFloat:0 withFloat:-ih];
    }
    break;
    case 270:
    case -90:
    [t rotateWithFloat:(float) [JavaLangMath toRadiansWithDouble:-90]];
    [t translateWithFloat:-iw withFloat:0];
    n = width;
    width = height;
    height = n;
    break;
    default:
    return image;
  }
  RAREUIImage *img = [[RAREUIImage alloc] initWithInt:width withInt:height];
  id<RAREiPlatformGraphics> g = [img createGraphics];
  [((id<RAREiPlatformGraphics>) nil_chk(g)) setTransformWithRAREiTransform:t];
  [g drawImageWithRAREiPlatformImage:image withFloat:0 withFloat:0];
  [g dispose];
  return img;
}

- (id)init {
  return [super init];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "configureImageWithRAREiWidget:withRAREiImagePainter:withSPOTPrintableString:withBoolean:", NULL, "LRAREiImagePainter", 0x9, NULL },
    { "configureImageWithRAREiWidget:withRAREiImagePainter:withNSString:withJavaUtilMap:withBoolean:", NULL, "LRAREiImagePainter", 0x9, NULL },
    { "createDisabledIconWithRAREiPlatformIcon:", NULL, "LRAREiPlatformIcon", 0x9, NULL },
    { "createImageWithRAREiPlatformIcon:", NULL, "LRAREUIImage", 0x9, NULL },
    { "createIconWithJavaNetURL:withBoolean:", NULL, "LRAREUIImageIcon", 0x9, NULL },
    { "createIconWithJavaNetURL:withBoolean:withFloat:", NULL, "LRAREUIImageIcon", 0x9, NULL },
    { "createIconWithJavaNetURL:withNSString:withBoolean:withFloat:", NULL, "LRAREUIImageIcon", 0x9, NULL },
    { "createImageWithJavaNetURL:withBoolean:", NULL, "LRAREUIImage", 0x9, "JavaIoIOException" },
    { "createImageWithJavaNetURL:withBoolean:withFloat:", NULL, "LRAREUIImage", 0x9, "JavaIoIOException" },
    { "createImagePainterWithRAREUIImage:", NULL, "LRAREiImagePainter", 0x9, NULL },
    { "createStateListIconWithNSString:withRAREiWidget:", NULL, "LRAREiPlatformIcon", 0x9, NULL },
    { "scaleImageWithRAREUIImage:withInt:withInt:", NULL, "LRAREUIImage", 0x9, NULL },
    { "getAnimatedSpinner", NULL, "LRAREUIImageIcon", 0x9, NULL },
    { "getAnimatedSpinnerSmall", NULL, "LRAREUIImageIcon", 0x9, NULL },
    { "getImageWithRAREiWidget:withNSString:withBoolean:withRAREUIDimension:withRAREiImagePainter_ScalingTypeEnum:withFloat:", NULL, "LRAREUIImage", 0x9, NULL },
    { "getImageWithRAREiWidget:withJavaNetURL:withBoolean:withRAREUIDimension:withRAREiImagePainter_ScalingTypeEnum:withFloat:", NULL, "LRAREUIImage", 0x9, NULL },
    { "getSliceWithRAREUIImage:withRAREUIRectangle:", NULL, "LRAREUIImage", 0x9, NULL },
    { "getSliceWithRAREUIImage:withInt:withInt:", NULL, "LRAREUIImage", 0x9, NULL },
    { "rotateImageWithRAREUIImage:withInt:", NULL, "LRAREUIImage", 0x9, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "_animatedSpinner_", NULL, 0x9, "LRAREUIImageIcon" },
    { "_animatedSpinnerSmall_", NULL, 0x9, "LRAREUIImageIcon" },
  };
  static J2ObjcClassInfo _RAREUIImageHelper = { "UIImageHelper", "com.appnativa.rare.ui", NULL, 0x1, 19, methods, 2, fields, 0, NULL};
  return &_RAREUIImageHelper;
}

@end