//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/painter/aUIImagePainter.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREaUIImagePainter_H_
#define _RAREaUIImagePainter_H_

@class JavaNetURL;
@class RAREDisplayedEnum;
@class RARERenderSpaceEnum;
@class RARERenderTypeEnum;
@class RAREUIColor;
@class RAREUIImage;
@class RAREaUIImagePainter_ConfigListener;
@class RAREiImagePainter_ScalingTypeEnum;
@protocol RAREiComposite;
@protocol RAREiPlatformGraphics;
@protocol RAREiWeakReference;

#import "JreEmulation.h"
#include "com/appnativa/rare/platform/iConfigurationListener.h"
#include "com/appnativa/rare/ui/iImageObserver.h"
#include "com/appnativa/rare/ui/iPlatformPaint.h"
#include "com/appnativa/rare/ui/painter/aUIPlatformPainter.h"
#include "com/appnativa/rare/ui/painter/iBackgroundPainter.h"
#include "com/appnativa/rare/ui/painter/iImagePainter.h"
#include "java/lang/Runnable.h"

@interface RAREaUIImagePainter : RAREaUIPlatformPainter < RAREiImagePainter, RAREiBackgroundPainter, NSCopying, RAREiImageObserver, RAREiPlatformPaint > {
 @public
  id<RAREiComposite> composite_;
  RAREiImagePainter_ScalingTypeEnum *scalingType_;
  NSString *_toString_;
  BOOL checkAlternate_;
  BOOL isNinePatch_;
  NSString *sourceLocation_;
  RAREUIImage *theImage_;
  int toStringModCount_;
  RAREaUIImagePainter_ConfigListener *configListener_;
}

- (id)init;
- (id)initWithRAREUIImage:(RAREUIImage *)image;
- (id)initWithRAREUIImage:(RAREUIImage *)image
                  withInt:(int)alpha
   withRARERenderTypeEnum:(RARERenderTypeEnum *)type;
- (id)initWithRAREUIImage:(RAREUIImage *)image
                  withInt:(int)alpha
   withRARERenderTypeEnum:(RARERenderTypeEnum *)type
    withRAREDisplayedEnum:(RAREDisplayedEnum *)displayed;
- (id<RAREiBackgroundPainter>)alphaWithInt:(int)alpha;
- (void)clear;
+ (void)drawTiledImageWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                                withRAREUIImage:(RAREUIImage *)img
                                        withInt:(int)width
                                        withInt:(int)height
                             withRAREiComposite:(id<RAREiComposite>)composite;
+ (void)drawTiledImageWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                                withRAREUIImage:(RAREUIImage *)img
                                      withFloat:(float)baseX
                                      withFloat:(float)baseY
                                      withFloat:(float)x
                                      withFloat:(float)y
                                      withFloat:(float)width
                                      withFloat:(float)height
                             withRAREiComposite:(id<RAREiComposite>)composite;
- (BOOL)isEqual:(id)obj;
- (NSUInteger)hash;
- (void)imageLoadedWithRAREUIImage:(RAREUIImage *)image;
- (void)paintWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                             withFloat:(float)x
                             withFloat:(float)y
                             withFloat:(float)w
                             withFloat:(float)h
                               withInt:(int)orientation;
- (NSString *)description;
- (NSString *)toStringEx;
- (void)setCompositeWithRAREiComposite:(id<RAREiComposite>)composite;
- (void)setDisplayedWithRAREDisplayedEnum:(RAREDisplayedEnum *)imageDisplayCriteria;
- (void)setImageWithRAREUIImage:(RAREUIImage *)image;
- (void)setImageAlphaWithFloat:(float)alpha;
- (void)setImageURLWithJavaNetURL:(JavaNetURL *)url;
- (void)setRenderSpaceWithRARERenderSpaceEnum:(RARERenderSpaceEnum *)space;
- (void)setRenderTypeWithRARERenderTypeEnum:(RARERenderTypeEnum *)type;
- (void)setScalingTypeWithRAREiImagePainter_ScalingTypeEnum:(RAREiImagePainter_ScalingTypeEnum *)type;
- (void)setSourceLocationWithNSString:(NSString *)location;
- (id<RAREiComposite>)getComposite;
- (int)getIconHeight;
- (int)getIconWidth;
- (RAREUIImage *)getImage;
- (id<RAREiPlatformPaint>)getPaintWithFloat:(float)width
                                  withFloat:(float)height;
- (RAREUIColor *)getPlatformPaintColor;
- (RAREiImagePainter_ScalingTypeEnum *)getScalingType;
- (NSString *)getSourceLocation;
- (BOOL)isPainter;
- (BOOL)isSingleColorPainter;
- (void)disposeEx;
- (void)onConfigurationWillChangeWithId:(id)newConfig;
- (void)copyAllFieldsTo:(RAREaUIImagePainter *)other;
@end

J2OBJC_FIELD_SETTER(RAREaUIImagePainter, composite_, id<RAREiComposite>)
J2OBJC_FIELD_SETTER(RAREaUIImagePainter, scalingType_, RAREiImagePainter_ScalingTypeEnum *)
J2OBJC_FIELD_SETTER(RAREaUIImagePainter, _toString_, NSString *)
J2OBJC_FIELD_SETTER(RAREaUIImagePainter, sourceLocation_, NSString *)
J2OBJC_FIELD_SETTER(RAREaUIImagePainter, theImage_, RAREUIImage *)
J2OBJC_FIELD_SETTER(RAREaUIImagePainter, configListener_, RAREaUIImagePainter_ConfigListener *)

typedef RAREaUIImagePainter ComAppnativaRareUiPainterAUIImagePainter;

@interface RAREaUIImagePainter_ConfigListener : NSObject < RAREiConfigurationListener > {
 @public
  id<RAREiWeakReference> painter_;
}

- (id)initWithRAREaUIImagePainter:(RAREaUIImagePainter *)painter;
- (void)onConfigurationChangedWithId:(id)changes;
- (void)onConfigurationWillChangeWithId:(id)newConfig;
- (void)copyAllFieldsTo:(RAREaUIImagePainter_ConfigListener *)other;
@end

J2OBJC_FIELD_SETTER(RAREaUIImagePainter_ConfigListener, painter_, id<RAREiWeakReference>)

@interface RAREaUIImagePainter_ConfigListener_$1 : NSObject < JavaLangRunnable > {
 @public
  id<RAREiConfigurationListener> val$l_;
}

- (void)run;
- (id)initWithRAREiConfigurationListener:(id<RAREiConfigurationListener>)capture$0;
@end

J2OBJC_FIELD_SETTER(RAREaUIImagePainter_ConfigListener_$1, val$l_, id<RAREiConfigurationListener>)

#endif // _RAREaUIImagePainter_H_
