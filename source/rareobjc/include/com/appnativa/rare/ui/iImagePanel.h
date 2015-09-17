//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/iImagePanel.java
//
//  Created by decoteaud on 9/15/15.
//

#ifndef _RAREiImagePanel_H_
#define _RAREiImagePanel_H_

@class RAREUIColor;
@class RAREUIImage;
@class RAREiImagePainter_ScalingTypeEnum;
@protocol RAREiAnimatorListener;
@protocol RAREiPlatformBorder;
@protocol RAREiPlatformComponent;
@protocol RAREiPlatformShape;

#import "JreEmulation.h"

@protocol RAREiImagePanel < NSObject, JavaObject >
- (BOOL)canZoomIn;
- (BOOL)canZoomOut;
- (void)centerImage;
- (void)clear;
- (void)resetImage;
- (void)rotateLeft;
- (void)rotateRight;
- (void)zoomWithInt:(int)percent;
- (void)zoomIn;
- (BOOL)isImageFitted;
- (void)zoomOut;
- (void)fitImage;
- (BOOL)isFillViewport;
- (void)setFillViewportWithBoolean:(BOOL)fillViewport;
- (void)setMovingOnlyAllowedWhenToLargeWithBoolean:(BOOL)allowed;
- (void)setAutoScaleWithBoolean:(BOOL)autoScale;
- (void)setCenterInitiallyWithBoolean:(BOOL)centerInitially;
- (void)setDisposeImageOnChangeWithBoolean:(BOOL)dispose;
- (BOOL)isDisposeImageOnChange;
- (void)setImageWithRAREUIImage:(RAREUIImage *)image;
- (void)setImageWithRAREUIImage:(RAREUIImage *)image
                        withInt:(int)width
                        withInt:(int)height;
- (void)setImageBorderWithRAREiPlatformBorder:(id<RAREiPlatformBorder>)border;
- (void)setMaximumZoomWithInt:(int)maxZoom;
- (void)setMinimumZoomWithInt:(int)minZoom;
- (void)setMovingAllowedWithBoolean:(BOOL)allowed;
- (void)setPreserveAspectRatioWithBoolean:(BOOL)preserve;
- (void)setScalingTypeWithRAREiImagePainter_ScalingTypeEnum:(RAREiImagePainter_ScalingTypeEnum *)type;
- (void)setSelectionColorWithRAREUIColor:(RAREUIColor *)color;
- (void)setUserSelectionAllowedWithBoolean:(BOOL)allowed;
- (void)setZoomIncrementPercentWithInt:(int)zoomIncrement;
- (void)setZoomingAllowedWithBoolean:(BOOL)allowed;
- (id<RAREiPlatformComponent>)getComponent;
- (RAREUIImage *)getImage;
- (id<RAREiPlatformBorder>)getImageBorder;
- (int)getImageHeight;
- (int)getImageRotation;
- (int)getImageWidth;
- (id<RAREiPlatformShape>)getSelection;
- (NSString *)getSource;
- (RAREUIImage *)getRenderedImage;
- (int)getZoomPercent;
- (BOOL)isAnimateBoundsChange;
- (void)setAnimateBoundsChangeWithBoolean:(BOOL)animateSizeChanges
                withRAREiAnimatorListener:(id<RAREiAnimatorListener>)listener;
@end

#define ComAppnativaRareUiIImagePanel RAREiImagePanel

#endif // _RAREiImagePanel_H_
