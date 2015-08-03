//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/platform/apple/ui/view/ImageView.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RAREImageView_H_
#define _RAREImageView_H_

@class RAREUIColor;
@class RAREUIImage;
@class RAREUIRectangle;
@class RAREUIStroke;
@protocol RAREiPlatformShape;

#import "JreEmulation.h"
#include "com/appnativa/rare/platform/apple/ui/view/ParentView.h"
#include "com/appnativa/rare/ui/iImageObserver.h"

@interface RAREImageView : RAREParentView < RAREiImageObserver > {
 @public
  RAREUIImage *image_;
  BOOL manageVisibility_;
}

- (id)init;
- (void)setPreserveAspectRatioWithBoolean:(BOOL)preserveAspectRatio
                              withBoolean:(BOOL)fill;
- (void)setImageWithRAREUIImage:(RAREUIImage *)image;
- (void)revalidate;
- (RAREUIImage *)getImage;
- (RAREUIImage *)capture;
- (RAREUIImage *)captureInRectWithRAREUIRectangle:(RAREUIRectangle *)rect
                                        withFloat:(float)width
                                        withFloat:(float)height;
- (void)setSelectionWithRAREiPlatformShape:(id<RAREiPlatformShape>)shape;
- (void)setSelectionStrokeWithRAREUIStroke:(RAREUIStroke *)stroke;
- (void)setSelectionColorWithRAREUIColor:(RAREUIColor *)color;
- (void)setImageExWithRAREUIImage:(RAREUIImage *)image;
+ (id)createAPImageView;
- (void)imageLoadedWithRAREUIImage:(RAREUIImage *)image;
- (BOOL)isManageVisibility;
- (void)setManageVisibilityWithBoolean:(BOOL)manageVisibility;
- (void)copyAllFieldsTo:(RAREImageView *)other;
@end

J2OBJC_FIELD_SETTER(RAREImageView, image_, RAREUIImage *)

typedef RAREImageView ComAppnativaRarePlatformAppleUiViewImageView;

#endif // _RAREImageView_H_