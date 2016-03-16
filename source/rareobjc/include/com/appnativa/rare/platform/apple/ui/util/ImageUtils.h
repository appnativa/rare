//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/platform/apple/ui/util/ImageUtils.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREImageUtils_H_
#define _RAREImageUtils_H_

@class IOSIntArray;
@class JavaNetURL;
@class RAREUIImage;
@class RAREUTByteArray;
@class RAREView;
@class RAREiImagePainter_ScalingTypeEnum;
@protocol RAREiPlatformComponent;
@protocol RAREiPlatformIcon;

#import "JreEmulation.h"

@interface RAREImageUtils : NSObject {
}

+ (id)addReflectionWithId:(id)proxy
                  withInt:(int)y
                  withInt:(int)height
                withFloat:(float)opacity
                  withInt:(int)gap;
+ (id)blurImageWithId:(id)proxy;
+ (id)rotateLeftWithId:(id)proxy;
+ (id)rotateRightWithId:(id)proxy;
+ (id)copyImageWithId:(id)proxy OBJC_METHOD_FAMILY_NONE;
+ (id)createColorProxyWithId:(id)proxy;
+ (NSString *)base64StringWithId:(id)proxy
                     withBoolean:(BOOL)png;
+ (RAREUIImage *)createCompatibleImageWithInt:(int)width
                                      withInt:(int)height;
+ (id)createContextWithId:(id)proxy;
+ (id)createDisabledImageWithId:(id)image;
+ (RAREUIImage *)createImageWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c;
+ (RAREUIImage *)createImageWithRAREView:(RAREView *)view;
+ (RAREUIImage *)createImageWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon;
+ (RAREUIImage *)createImageExWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon;
+ (id)createImageProxyWithRAREView:(RAREView *)v;
+ (id)createImageProxyWithInt:(int)width
                      withInt:(int)height;
+ (id)createImageProxyWithId:(id)nativeimage;
+ (id)createImageProxyWithRAREUTByteArray:(RAREUTByteArray *)ba
                                withFloat:(float)dscale;
+ (id)createImageProxyWithId:(id)data
                   withFloat:(float)dscale;
+ (id)createImageProxyWithJavaNetURL:(JavaNetURL *)url
                             withInt:(int)timeout
                           withFloat:(float)dscale;
+ (id)createReflectionWithId:(id)proxy
                     withInt:(int)height
                   withFloat:(float)opacity
                     withInt:(int)gap;
+ (id)createCopyWithReflectionWithId:(id)proxy
                             withInt:(int)height
                           withFloat:(float)opacity
                             withInt:(int)gap;
+ (void)setPixelWithId:(id)proxy
               withInt:(int)x
               withInt:(int)y
               withInt:(int)color;
+ (void)setPixelsWithId:(id)proxy
           withIntArray:(IOSIntArray *)pixels
                withInt:(int)x
                withInt:(int)y
                withInt:(int)width
                withInt:(int)height;
+ (void)setResourceNameWithId:(id)proxy
                 withNSString:(NSString *)name;
+ (int)getHeightWithId:(id)nsimage;
+ (int)getPixelWithId:(id)proxy
              withInt:(int)x
              withInt:(int)y;
+ (void)getPixelsWithId:(id)proxy
                withInt:(int)x
                withInt:(int)y
                withInt:(int)width
                withInt:(int)height
           withIntArray:(IOSIntArray *)pixels;
+ (RAREUIImage *)getScaledImageWithRAREUIImage:(RAREUIImage *)image
                                       withInt:(int)width
                                       withInt:(int)height
         withRAREiImagePainter_ScalingTypeEnum:(RAREiImagePainter_ScalingTypeEnum *)scalingtype;
+ (id)getScaledInstanceWithId:(id)proxy
                      withInt:(int)width
                      withInt:(int)height
withRAREiImagePainter_ScalingTypeEnum:(RAREiImagePainter_ScalingTypeEnum *)scalingtype;
+ (id)getSubImageWithId:(id)proxy
                withInt:(int)x
                withInt:(int)y
                withInt:(int)width
                withInt:(int)height;
+ (int)getWidthWithId:(id)proxy;
+ (id)getNativeImageWithId:(id)proxy;
+ (RAREUIImage *)flipImageWithRAREUIImage:(RAREUIImage *)image;
+ (id)loadAssetCatalogImageProxyWithNSString:(NSString *)name;
- (id)init;
@end

typedef RAREImageUtils ComAppnativaRarePlatformAppleUiUtilImageUtils;

#endif // _RAREImageUtils_H_
