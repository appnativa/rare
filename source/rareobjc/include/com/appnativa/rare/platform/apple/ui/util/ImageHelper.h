//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/platform/apple/ui/util/ImageHelper.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREImageHelper_H_
#define _RAREImageHelper_H_

@class JavaNetURL;
@class RAREActionLink;
@class RARENSURLConnectionHelper;
@class RAREUIColor;
@class RAREUIImageIcon;
@class RAREiImagePainter_ScalingTypeEnum;
@protocol RAREiPlatformAppContext;
@protocol RAREiPlatformIcon;
@protocol RAREiWidget;

#import "JreEmulation.h"
#include "com/appnativa/rare/iFunctionCallback.h"
#include "com/appnativa/rare/ui/UIImage.h"
#include "com/appnativa/util/iCancelable.h"
#include "java/lang/Runnable.h"
#include "java/util/concurrent/Callable.h"

@interface RAREImageHelper : NSObject {
}

+ (RAREUIImage *)constrainWithRAREUIImage:(RAREUIImage *)img
                                  withInt:(int)width
                                  withInt:(int)height
                                  withInt:(int)constraints
                          withRAREUIColor:(RAREUIColor *)bg
    withRAREiImagePainter_ScalingTypeEnum:(RAREiImagePainter_ScalingTypeEnum *)scalingType;
+ (RAREUIImageIcon *)createDisabledIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon;
+ (RAREUIImage *)createDisabledImageWithRAREUIImage:(RAREUIImage *)image;
+ (RAREUIImageIcon *)createIconWithJavaNetURL:(JavaNetURL *)url
                                  withBoolean:(BOOL)defer
                                    withFloat:(float)density;
+ (RAREUIImageIcon *)createIconWithJavaNetURL:(JavaNetURL *)url
                                 withNSString:(NSString *)description_
                                 withNSString:(NSString *)name
                                  withBoolean:(BOOL)defer
                                    withFloat:(float)density;
+ (RAREUIImage *)createImageWithRAREiWidget:(id<RAREiWidget>)context
                         withRAREActionLink:(RAREActionLink *)link
                                withBoolean:(BOOL)defer
                                  withFloat:(float)density;
+ (RAREUIImage *)createImageWithJavaNetURL:(JavaNetURL *)url
                               withBoolean:(BOOL)defer
                                 withFloat:(float)density;
+ (RAREUIImage *)createImageWithJavaNetURL:(JavaNetURL *)url
                               withBoolean:(BOOL)defer
                                   withInt:(int)size
                                   withInt:(int)constraints
     withRAREiImagePainter_ScalingTypeEnum:(RAREiImagePainter_ScalingTypeEnum *)st
                           withRAREUIColor:(RAREUIColor *)bg
                                 withFloat:(float)density;
+ (RAREUIImage *)createImageWithJavaNetURL:(JavaNetURL *)url
                                    withId:(id)proxy
                               withBoolean:(BOOL)densityScale
                                   withInt:(int)size
                                   withInt:(int)constraints
     withRAREiImagePainter_ScalingTypeEnum:(RAREiImagePainter_ScalingTypeEnum *)st
                           withRAREUIColor:(RAREUIColor *)bg
                                 withFloat:(float)density;
+ (RAREUIImage *)ensureImageLoadedWithRAREUIImage:(RAREUIImage *)img;
+ (RAREUIImage *)scaleImageWithRAREUIImage:(RAREUIImage *)image
                                   withInt:(int)width
                                   withInt:(int)height;
- (id)init;
@end

typedef RAREImageHelper ComAppnativaRarePlatformAppleUiUtilImageHelper;

@interface RAREImageHelper_DelayedImage : RAREUIImage < JavaUtilConcurrentCallable, RAREUTiCancelable, RAREiFunctionCallback > {
 @public
  int height_;
  int width_;
  int constraints_;
  float density_;
  RARENSURLConnectionHelper *future_;
  RAREUIImage *image_;
  int size_;
  JavaNetURL *url_;
  BOOL hasWaiters_;
}

- (id)initWithRAREiPlatformAppContext:(id<RAREiPlatformAppContext>)app
                       withJavaNetURL:(JavaNetURL *)url;
- (id)initWithRAREiPlatformAppContext:(id<RAREiPlatformAppContext>)app
                       withJavaNetURL:(JavaNetURL *)url
                              withInt:(int)size
                              withInt:(int)constraints
withRAREiImagePainter_ScalingTypeEnum:(RAREiImagePainter_ScalingTypeEnum *)scalingType
                      withRAREUIColor:(RAREUIColor *)background
                            withFloat:(float)density;
- (RAREUIImage *)call;
- (void)cancelWithBoolean:(BOOL)canInterrupt;
- (void)finishedWithBoolean:(BOOL)canceled
                     withId:(id)returnValue;
- (void)flush;
- (void)setHeightWithInt:(int)height;
- (void)setWidthWithInt:(int)width;
- (void)load__;
- (int)getHeight;
- (RAREUIImage *)getRealImage;
- (RAREUIImage *)getSubimageWithInt:(int)x
                            withInt:(int)y
                            withInt:(int)w
                            withInt:(int)h;
- (int)getWidth;
- (BOOL)isCanceled;
- (BOOL)isDone;
- (BOOL)isLoadedExWithBoolean:(BOOL)hasObserver;
- (void)copyAllFieldsTo:(RAREImageHelper_DelayedImage *)other;
@end

J2OBJC_FIELD_SETTER(RAREImageHelper_DelayedImage, future_, RARENSURLConnectionHelper *)
J2OBJC_FIELD_SETTER(RAREImageHelper_DelayedImage, image_, RAREUIImage *)
J2OBJC_FIELD_SETTER(RAREImageHelper_DelayedImage, url_, JavaNetURL *)

@interface RAREImageHelper_DelayedImage_$1 : NSObject < JavaLangRunnable > {
 @public
  RAREImageHelper_DelayedImage *this$0_;
}

- (void)run;
- (id)initWithRAREImageHelper_DelayedImage:(RAREImageHelper_DelayedImage *)outer$;
@end

J2OBJC_FIELD_SETTER(RAREImageHelper_DelayedImage_$1, this$0_, RAREImageHelper_DelayedImage *)

@interface RAREImageHelper_DelayedImage_$2 : NSObject < JavaLangRunnable > {
 @public
  RAREImageHelper_DelayedImage *this$0_;
}

- (void)run;
- (id)initWithRAREImageHelper_DelayedImage:(RAREImageHelper_DelayedImage *)outer$;
@end

J2OBJC_FIELD_SETTER(RAREImageHelper_DelayedImage_$2, this$0_, RAREImageHelper_DelayedImage *)

#endif // _RAREImageHelper_H_
