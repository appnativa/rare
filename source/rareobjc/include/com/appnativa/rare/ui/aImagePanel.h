//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/aImagePanel.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREaImagePanel_H_
#define _RAREaImagePanel_H_

@class JavaNetURL;
@class RAREAnimationComponent;
@class RAREPinchZoomHandler;
@class RAREUIColor;
@class RAREUIDimension;
@class RAREUIImage;
@class RAREUIInsets;
@class RAREUIRectangle;
@class RAREUIStroke;
@class RAREaAnimator_BoundsChanger;
@class RAREiImagePainter_ScalingTypeEnum;
@protocol RAREiAnimatorListener;
@protocol RAREiComposite;
@protocol RAREiPlatformBorder;
@protocol RAREiPlatformComponent;
@protocol RAREiPlatformGraphics;
@protocol RAREiPlatformShape;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/XPContainer.h"
#include "com/appnativa/rare/ui/iImageObserver.h"
#include "com/appnativa/rare/ui/painter/iPainterSupport.h"
#include "java/lang/Runnable.h"

@interface RAREaImagePanel : RAREXPContainer < RAREiPainterSupport, RAREiImageObserver > {
 @public
  float maximumScale_;
  float minimumScale_;
  int moveIncrement_;
  int rotation_;
  RAREiImagePainter_ScalingTypeEnum *scalingType_;
  BOOL movingAllowed_;
  RAREUIRectangle *destBounds_;
  RAREUIRectangle *srcBounds_;
  RAREAnimationComponent *animationComponent_;
  BOOL autoScale_;
  BOOL center_;
  BOOL centerInitially_;
  id<RAREiPlatformBorder> imageBorder_;
  RAREUIInsets *imageBorderInsets_;
  RAREPinchZoomHandler *pinchZoom_;
  BOOL preserveAspectRatio_;
  BOOL rotatingAllowed_;
  float scaleIncrement_;
  id<RAREiPlatformShape> selection_;
  RAREUIColor *selectionColor_;
  RAREUIStroke *selectionStroke_;
  BOOL showZoomTooltip_;
  id<RAREiPlatformComponent> spinnerComponent_;
  RAREUIImage *theImage_;
  RAREUIImage *originalImage_;
  float theScale_;
  BOOL useSpinner_;
  BOOL userSelectionAllowed_;
  BOOL usingTransforms_;
  BOOL zoomingAllowed_;
  int oldWidth_;
  int oldHeight_;
  BOOL movingAllowedWhenToLarge_;
  BOOL fillViewport_;
  BOOL animateBoundsChange_;
  BOOL animatingSizeChange_;
  BOOL animatingRotationChange_;
  id<RAREiAnimatorListener> animateSizeChangeListener_;
  id<RAREiComposite> imageComposite_;
  BOOL isContextFlipped_;
  BOOL disposeImageOnChange_;
  BOOL pinchZoomPanelOnly_;
}

+ (NSString *)PROPERTY_PINCHZOOM;
+ (void)setPROPERTY_PINCHZOOM:(NSString *)PROPERTY_PINCHZOOM;
+ (NSString *)PROPERTY_ZOOM;
+ (void)setPROPERTY_ZOOM:(NSString *)PROPERTY_ZOOM;
+ (NSString *)PROPERTY_ROTATION;
+ (void)setPROPERTY_ROTATION:(NSString *)PROPERTY_ROTATION;
- (id)init;
- (id)initWithId:(id)view;
- (BOOL)canZoomIn;
- (BOOL)canZoomOut;
- (void)cancelLoading;
- (void)centerImage;
- (void)fitImage;
- (void)centerOrFitImageWithBoolean:(BOOL)center;
- (void)centerOnWithInt:(int)x
                withInt:(int)y;
- (void)clear;
- (void)dispose;
- (void)imageLoadedWithRAREUIImage:(RAREUIImage *)image;
- (void)moveImageWithFloat:(float)dx
                 withFloat:(float)dy;
- (void)pinchZoomHandlerInitialize;
- (void)pinchZoomHandlerStartWithFloat:(float)x
                             withFloat:(float)y;
- (void)resetImage;
- (void)setRotationWithInt:(int)rotation;
- (void)rotateLeft;
- (void)rotateRight;
- (void)zoomWithInt:(int)percent;
- (void)zoomIn;
- (void)zoomOut;
- (void)zoomToWithFloat:(float)scale_
              withFloat:(float)x
              withFloat:(float)y;
- (void)setAutoScaleWithBoolean:(BOOL)auto_;
- (void)setCenterInitiallyWithBoolean:(BOOL)centerInitially;
- (void)setDisposeImageOnChangeWithBoolean:(BOOL)dispose;
- (BOOL)isDisposeImageOnChange;
- (void)setImageWithRAREUIImage:(RAREUIImage *)img;
- (void)setImageWithJavaNetURL:(JavaNetURL *)url;
- (void)setImageWithRAREUIImage:(RAREUIImage *)img
                        withInt:(int)width
                        withInt:(int)height;
- (void)setImageBorderWithRAREiPlatformBorder:(id<RAREiPlatformBorder>)imageBorder;
- (void)setImageAlphaWithFloat:(float)alpha;
- (void)setImageCompositeWithRAREiComposite:(id<RAREiComposite>)composite;
- (float)getImageAlpha;
- (void)setMaximumZoomWithInt:(int)percent;
- (void)setMinimumZoomWithInt:(int)percent;
- (void)setMoveIncrementWithInt:(int)increment;
- (void)setMovingAllowedWithBoolean:(BOOL)allowed;
- (void)setMovingOnlyAllowedWhenToLargeWithBoolean:(BOOL)allowed;
- (void)setPreserveAspectRatioWithBoolean:(BOOL)preserveAspectRatio;
- (void)setRotatingAllowedWithBoolean:(BOOL)allowed;
- (void)setScalingTypeWithRAREiImagePainter_ScalingTypeEnum:(RAREiImagePainter_ScalingTypeEnum *)type;
- (void)setBoundsWithFloat:(float)x
                 withFloat:(float)y
                 withFloat:(float)width
                 withFloat:(float)height;
- (void)setSelectionColorWithRAREUIColor:(RAREUIColor *)color;
- (void)setSelectionShapeWithRAREiPlatformShape:(id<RAREiPlatformShape>)selection;
- (void)setSelectionStrokeWithRAREUIStroke:(RAREUIStroke *)selectionStroke;
- (void)setUseSpinnerWithBoolean:(BOOL)spinner;
- (void)setUserSelectionAllowedWithBoolean:(BOOL)allowed;
- (void)setZoomIncrementPercentWithInt:(int)percent;
- (void)setZoomingAllowedWithBoolean:(BOOL)allowed;
- (id<RAREiPlatformComponent>)getComponent;
- (RAREUIImage *)getImage;
- (id<RAREiPlatformBorder>)getImageBorder;
- (int)getImageHeight;
- (int)getImageRotation;
- (float)getImageScale;
- (RAREUIImage *)getImageWithCurrentRotation;
- (int)getImageWidth;
- (int)getImageScaleWidth;
- (int)getImageScaleHeight;
- (int)getMaximumZoom;
- (int)getMinimumZoom;
- (int)getMoveIncrement;
- (RAREiImagePainter_ScalingTypeEnum *)getScalingType;
- (id<RAREiPlatformShape>)getSelection;
- (RAREUIColor *)getSelectionColor;
- (id<RAREiPlatformShape>)getSelectionShape;
- (RAREUIStroke *)getSelectionStroke;
- (NSString *)getSource;
- (RAREUIImage *)getRenderedImage;
- (void)setupRenderedImageGraphicsWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                                                    withInt:(int)width
                                                    withInt:(int)height;
- (RAREUIImage *)getSubImageWithRAREiPlatformShape:(id<RAREiPlatformShape>)shape;
- (int)getZoomPercent;
- (BOOL)hasImage;
- (BOOL)hasValue;
- (BOOL)isAutoScale;
- (BOOL)isMovingAllowed;
- (BOOL)isPanningAllowed;
- (BOOL)isRotatingAllowed;
- (BOOL)isShowZoomTooltip;
- (BOOL)isTextSelectionAllowed;
- (BOOL)isUserSelectionAllowed;
- (BOOL)isZoomingAllowed;
- (void)addSpinnerComponentWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c;
- (void)adjustDestForAspectRatioWithFloat:(float)width
                                withFloat:(float)height
                              withBoolean:(BOOL)fit
                              withBoolean:(BOOL)move;
- (void)centerOnExWithFloat:(float)x
                  withFloat:(float)y;
- (void)imageLoaded;
- (void)imageNoYetLoaded;
- (void)paintWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                             withFloat:(float)x
                             withFloat:(float)y
                             withFloat:(float)width
                             withFloat:(float)height;
- (void)pinchZoomHandlerEndWithFloat:(float)x
                           withFloat:(float)y;
- (void)pinchZoomHandlerScaleWithFloat:(float)x
                             withFloat:(float)y
                             withFloat:(float)scale_;
- (void)removeSpinnerComponentWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c;
- (BOOL)setScaleWithFloat:(float)scale_
              withBoolean:(BOOL)repaint;
- (void)fireZoomChangeWithInt:(int)oldZoom
                      withInt:(int)newZoom;
- (void)update;
- (void)updateFromPinchZoomHandlerWithRAREPinchZoomHandler:(RAREPinchZoomHandler *)h;
- (void)updateSelection;
- (void)updateTransforms;
- (BOOL)isImageLargerThanViewPort;
- (void)zoomOnPointWithFloat:(float)x
                   withFloat:(float)y;
- (void)handleSizeChangeAnimationWithRAREaAnimator_BoundsChanger:(RAREaAnimator_BoundsChanger *)bc;
- (void)setImageExWithRAREUIImage:(RAREUIImage *)img
                          withInt:(int)width
                          withInt:(int)height;
- (RAREUIImage *)getPaintImage;
- (RAREUIInsets *)getPaintInsets;
- (void)getPreferredSizeExWithRAREUIDimension:(RAREUIDimension *)size
                                    withFloat:(float)maxWidth;
- (void)handleSpinnerWithBoolean:(BOOL)show;
- (void)init__WithInt:(int)iw
              withInt:(int)ih
          withBoolean:(BOOL)reset OBJC_METHOD_FAMILY_NONE;
- (void)initValues OBJC_METHOD_FAMILY_NONE;
- (void)postRotate;
- (void)postRotateEx;
- (BOOL)isImageFitted;
- (BOOL)isFillViewport;
- (void)setFillViewportWithBoolean:(BOOL)fillViewport;
- (BOOL)isAnimateBoundsChange;
- (void)setAnimateBoundsChangeWithBoolean:(BOOL)animateBoundsChange
                withRAREiAnimatorListener:(id<RAREiAnimatorListener>)listener;
- (BOOL)isAnimatingRotationChange;
- (void)setAnimatingRotationChangeWithBoolean:(BOOL)animatingRotationChange;
- (void)copyAllFieldsTo:(RAREaImagePanel *)other;
@end

J2OBJC_FIELD_SETTER(RAREaImagePanel, scalingType_, RAREiImagePainter_ScalingTypeEnum *)
J2OBJC_FIELD_SETTER(RAREaImagePanel, destBounds_, RAREUIRectangle *)
J2OBJC_FIELD_SETTER(RAREaImagePanel, srcBounds_, RAREUIRectangle *)
J2OBJC_FIELD_SETTER(RAREaImagePanel, animationComponent_, RAREAnimationComponent *)
J2OBJC_FIELD_SETTER(RAREaImagePanel, imageBorder_, id<RAREiPlatformBorder>)
J2OBJC_FIELD_SETTER(RAREaImagePanel, imageBorderInsets_, RAREUIInsets *)
J2OBJC_FIELD_SETTER(RAREaImagePanel, pinchZoom_, RAREPinchZoomHandler *)
J2OBJC_FIELD_SETTER(RAREaImagePanel, selection_, id<RAREiPlatformShape>)
J2OBJC_FIELD_SETTER(RAREaImagePanel, selectionColor_, RAREUIColor *)
J2OBJC_FIELD_SETTER(RAREaImagePanel, selectionStroke_, RAREUIStroke *)
J2OBJC_FIELD_SETTER(RAREaImagePanel, spinnerComponent_, id<RAREiPlatformComponent>)
J2OBJC_FIELD_SETTER(RAREaImagePanel, theImage_, RAREUIImage *)
J2OBJC_FIELD_SETTER(RAREaImagePanel, originalImage_, RAREUIImage *)
J2OBJC_FIELD_SETTER(RAREaImagePanel, animateSizeChangeListener_, id<RAREiAnimatorListener>)
J2OBJC_FIELD_SETTER(RAREaImagePanel, imageComposite_, id<RAREiComposite>)

typedef RAREaImagePanel ComAppnativaRareUiAImagePanel;

@interface RAREaImagePanel_$1 : NSObject < JavaLangRunnable > {
 @public
  RAREaImagePanel *this$0_;
}

- (void)run;
- (id)initWithRAREaImagePanel:(RAREaImagePanel *)outer$;
@end

J2OBJC_FIELD_SETTER(RAREaImagePanel_$1, this$0_, RAREaImagePanel *)

@interface RAREaImagePanel_$2 : NSObject < JavaLangRunnable > {
 @public
  RAREaImagePanel *this$0_;
  int val$oldRotation_;
  int val$rotation_;
}

- (void)run;
- (id)initWithRAREaImagePanel:(RAREaImagePanel *)outer$
                      withInt:(int)capture$0
                      withInt:(int)capture$1;
@end

J2OBJC_FIELD_SETTER(RAREaImagePanel_$2, this$0_, RAREaImagePanel *)

@interface RAREaImagePanel_$3 : NSObject < JavaLangRunnable > {
 @public
  RAREaImagePanel *this$0_;
  RAREUIImage *val$img_;
  int val$width_;
  int val$height_;
}

- (void)run;
- (id)initWithRAREaImagePanel:(RAREaImagePanel *)outer$
              withRAREUIImage:(RAREUIImage *)capture$0
                      withInt:(int)capture$1
                      withInt:(int)capture$2;
@end

J2OBJC_FIELD_SETTER(RAREaImagePanel_$3, this$0_, RAREaImagePanel *)
J2OBJC_FIELD_SETTER(RAREaImagePanel_$3, val$img_, RAREUIImage *)

@interface RAREaImagePanel_$4 : NSObject < JavaLangRunnable > {
 @public
  RAREaImagePanel *this$0_;
  int val$oldZoom_;
  int val$newZoom_;
}

- (void)run;
- (id)initWithRAREaImagePanel:(RAREaImagePanel *)outer$
                      withInt:(int)capture$0
                      withInt:(int)capture$1;
@end

J2OBJC_FIELD_SETTER(RAREaImagePanel_$4, this$0_, RAREaImagePanel *)

#endif // _RAREaImagePanel_H_
