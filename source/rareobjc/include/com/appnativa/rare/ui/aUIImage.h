//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/aUIImage.java
//
//  Created by decoteaud on 9/15/15.
//

#ifndef _RAREaUIImage_H_
#define _RAREaUIImage_H_

@class IOSIntArray;
@class JavaNioByteBuffer;
@class RARENinePatch;
@class RAREUIColor;
@class RAREUIImage;
@class RAREUIRectangle;
@class RAREUTIdentityArrayList;
@class RAREiImagePainter_ScalingTypeEnum;
@protocol RAREiImageObserver;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/iPlatformImage.h"

@interface RAREaUIImage : NSObject < RAREiPlatformImage > {
 @public
  RAREiImagePainter_ScalingTypeEnum *scalingType_;
  RAREUIColor *background_;
  BOOL canceled_;
  int constraintHeight_;
  int constraintType_;
  int constraintWidth_;
  id<RAREiImageObserver> imageObserver_;
  BOOL isninePatch_;
  NSString *location_;
  RARENinePatch *ninePatch_;
  RAREUTIdentityArrayList *observersList_;
  BOOL ninePatchChecked_;
  NSString *resourceName_;
}

- (id)init;
- (void)addReflectionImageWithInt:(int)y
                          withInt:(int)height
                        withFloat:(float)opacity
                          withInt:(int)gap;
- (void)blurImage;
- (id)clone;
- (BOOL)changeColorWithRAREUIColor:(RAREUIColor *)newColor
                   withRAREUIColor:(RAREUIColor *)oldColor;
- (void)contstrainWithInt:(int)width
                  withInt:(int)height
                  withInt:(int)constraints
          withRAREUIColor:(RAREUIColor *)bg
withRAREiImagePainter_ScalingTypeEnum:(RAREiImagePainter_ScalingTypeEnum *)st;
- (RAREaUIImage *)createDisabledImage;
- (RAREaUIImage *)createReflectionImageWithInt:(int)y
                                       withInt:(int)height
                                     withFloat:(float)opacity
                                       withInt:(int)gap;
- (void)dispose;
- (JavaNioByteBuffer *)getImageBytesWithInt:(int)x
                                    withInt:(int)y
                                    withInt:(int)width
                                    withInt:(int)height;
- (void)setImageBytesWithInt:(int)x
                     withInt:(int)y
                     withInt:(int)width
                     withInt:(int)height
       withJavaNioByteBuffer:(JavaNioByteBuffer *)bb;
- (NSString *)description;
- (void)scale__WithInt:(int)size;
- (void)scale__WithInt:(int)width
               withInt:(int)height;
- (void)setNinePatchWithRARENinePatch:(RARENinePatch *)ninePatch;
- (void)setPixelWithInt:(int)x
                withInt:(int)y
                withInt:(int)value;
- (void)setPixelsWithIntArray:(IOSIntArray *)pixels
                      withInt:(int)x
                      withInt:(int)y
                      withInt:(int)width
                      withInt:(int)height;
- (void)setResourceNameWithNSString:(NSString *)resourceName;
- (void)setScalingTypeWithRAREiImagePainter_ScalingTypeEnum:(RAREiImagePainter_ScalingTypeEnum *)scalingType;
- (void)setSliceWithRAREUIRectangle:(RAREUIRectangle *)rect;
- (void)setSliceWithInt:(int)pos
                withInt:(int)size;
- (void)setSliceWithInt:(int)x
                withInt:(int)y
                withInt:(int)width
                withInt:(int)height;
- (int)getHeight;
- (NSString *)getLocation;
- (RARENinePatch *)getNinePatchWithBoolean:(BOOL)force;
- (int)getPixelWithInt:(int)x
               withInt:(int)y;
- (IOSIntArray *)getPixelsWithIntArray:(IOSIntArray *)pixels
                               withInt:(int)x
                               withInt:(int)y
                               withInt:(int)width
                               withInt:(int)height;
- (NSString *)getResourceName;
- (RAREiImagePainter_ScalingTypeEnum *)getScalingType;
- (RAREaUIImage *)getSliceWithRAREUIRectangle:(RAREUIRectangle *)rect;
- (RAREaUIImage *)getSliceWithInt:(int)pos
                          withInt:(int)size;
- (RAREaUIImage *)getSliceWithInt:(int)x
                          withInt:(int)y
                          withInt:(int)width
                          withInt:(int)height;
- (RAREaUIImage *)getSubimageWithInt:(int)x
                             withInt:(int)y
                             withInt:(int)width
                             withInt:(int)height;
- (int)getWidth;
- (BOOL)isCanceled;
- (BOOL)isLoaded;
- (BOOL)isLoadedWithRAREiImageObserver:(id<RAREiImageObserver>)is;
- (BOOL)isNinePatch;
- (void)notifyObserverWithRAREUIImage:(RAREUIImage *)img
               withRAREiImageObserver:(id<RAREiImageObserver>)is;
- (void)notifyObserversWithRAREUIImage:(RAREUIImage *)img;
- (BOOL)isLoadedExWithBoolean:(BOOL)hasObserver;
- (BOOL)isNinePatchEx;
- (void)copyAllFieldsTo:(RAREaUIImage *)other;
@end

J2OBJC_FIELD_SETTER(RAREaUIImage, scalingType_, RAREiImagePainter_ScalingTypeEnum *)
J2OBJC_FIELD_SETTER(RAREaUIImage, background_, RAREUIColor *)
J2OBJC_FIELD_SETTER(RAREaUIImage, imageObserver_, id<RAREiImageObserver>)
J2OBJC_FIELD_SETTER(RAREaUIImage, location_, NSString *)
J2OBJC_FIELD_SETTER(RAREaUIImage, ninePatch_, RARENinePatch *)
J2OBJC_FIELD_SETTER(RAREaUIImage, observersList_, RAREUTIdentityArrayList *)
J2OBJC_FIELD_SETTER(RAREaUIImage, resourceName_, NSString *)

typedef RAREaUIImage ComAppnativaRareUiAUIImage;

#endif // _RAREaUIImage_H_
