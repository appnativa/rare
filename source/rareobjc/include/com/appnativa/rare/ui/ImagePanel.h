//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/ui/ImagePanel.java
//
//  Created by decoteaud on 12/8/15.
//

#ifndef _RAREImagePanel_H_
#define _RAREImagePanel_H_

@class RAREAppleGraphics;
@class RAREImagePanel_MouseHandler;
@class RAREImagePanel_ScaleHandler;
@class RAREImageView;
@class RAREMouseEvent;
@class RAREUIColor;
@class RAREUIImage;
@class RAREUIRectangle;
@class RAREUIStroke;
@class RAREView;
@class RAREaAnimator_BoundsChanger;
@protocol RAREiPlatformAnimator;
@protocol RAREiPlatformBorder;
@protocol RAREiPlatformComponent;
@protocol RAREiPlatformShape;
@protocol RAREiWidget;

#import "JreEmulation.h"
#include "com/appnativa/rare/platform/apple/ui/view/ParentView.h"
#include "com/appnativa/rare/ui/aImagePanel.h"
#include "com/appnativa/rare/ui/iGestureListener.h"
#include "com/appnativa/rare/ui/listener/iMouseListener.h"
#include "com/appnativa/rare/ui/listener/iMouseMotionListener.h"

@interface RAREImagePanel : RAREaImagePanel {
 @public
  RAREImagePanel_MouseHandler *mouseHandler_;
  RAREImagePanel_ScaleHandler *scaleHandler_;
}

- (id)initWithRAREiWidget:(id<RAREiWidget>)context;
- (id)initWithBoolean:(BOOL)useTransforms;
- (RAREUIImage *)getRenderedImage;
- (void)handleSizeChangeAnimationWithRAREaAnimator_BoundsChanger:(RAREaAnimator_BoundsChanger *)bc;
- (void)animateToWithFloat:(float)x
                 withFloat:(float)y
                 withFloat:(float)width
                 withFloat:(float)height
                    withId:(id)uiview
 withRAREiPlatformAnimator:(id<RAREiPlatformAnimator>)animator;
- (void)animateAndRotateToWithFloat:(float)x
                          withFloat:(float)y
                          withFloat:(float)width
                          withFloat:(float)height
                          withFloat:(float)rotation
                             withId:(id)uiview
          withRAREiPlatformAnimator:(id<RAREiPlatformAnimator>)animator;
- (void)imageLoaded;
- (void)imageNoYetLoaded;
- (void)setAutoScaleWithBoolean:(BOOL)auto_;
- (void)setMovingAllowedWithBoolean:(BOOL)allowed;
- (void)setMovingOnlyAllowedWhenToLargeWithBoolean:(BOOL)allowed;
- (void)setImageBorderWithRAREiPlatformBorder:(id<RAREiPlatformBorder>)imageBorder;
- (void)setPreserveAspectRatioWithBoolean:(BOOL)preserveAspectRatio;
- (void)setFillViewportWithBoolean:(BOOL)fillViewport;
- (void)setSelectionColorWithRAREUIColor:(RAREUIColor *)color;
- (void)setSelectionShapeWithRAREiPlatformShape:(id<RAREiPlatformShape>)selection;
- (void)setSelectionStrokeWithRAREUIStroke:(RAREUIStroke *)selectionStroke;
- (void)setZoomingAllowedWithBoolean:(BOOL)allowed;
- (void)addSpinnerComponentWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c;
- (void)removeSpinnerComponentWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c;
- (void)adjustDestForAspectRatioWithFloat:(float)width
                                withFloat:(float)height
                              withBoolean:(BOOL)fit
                              withBoolean:(BOOL)move;
- (void)updateTransforms;
- (void)updateTransformsExWithRAREaAnimator_BoundsChanger:(RAREaAnimator_BoundsChanger *)bc;
- (RAREImageView *)getImageView;
- (void)copyAllFieldsTo:(RAREImagePanel *)other;
@end

J2OBJC_FIELD_SETTER(RAREImagePanel, mouseHandler_, RAREImagePanel_MouseHandler *)
J2OBJC_FIELD_SETTER(RAREImagePanel, scaleHandler_, RAREImagePanel_ScaleHandler *)

typedef RAREImagePanel ComAppnativaRareUiImagePanel;

@interface RAREImagePanel_ImageViewEx : RAREParentView {
 @public
  RAREImageView *imageView_;
  __weak RAREImagePanel *panel_;
  BOOL useTransforms_;
}

- (id)initWithBoolean:(BOOL)useTransforms;
- (void)disposeEx;
- (void)paintBackgroundWithRAREAppleGraphics:(RAREAppleGraphics *)g
                                withRAREView:(RAREView *)v
                         withRAREUIRectangle:(RAREUIRectangle *)rect;
- (void)copyAllFieldsTo:(RAREImagePanel_ImageViewEx *)other;
@end

J2OBJC_FIELD_SETTER(RAREImagePanel_ImageViewEx, imageView_, RAREImageView *)

@interface RAREImagePanel_MouseHandler : NSObject < RAREiMouseMotionListener, RAREiMouseListener > {
 @public
  __weak RAREImagePanel *this$0_;
  float lastX_;
  float lastY_;
  float startX_;
  float startY_;
  float slop_;
}

- (void)mouseDraggedWithRAREMouseEvent:(RAREMouseEvent *)e;
- (void)mouseEnteredWithRAREMouseEvent:(RAREMouseEvent *)e;
- (void)mouseExitedWithRAREMouseEvent:(RAREMouseEvent *)e;
- (void)mouseMovedWithRAREMouseEvent:(RAREMouseEvent *)e;
- (void)mousePressedWithRAREMouseEvent:(RAREMouseEvent *)e;
- (void)mouseReleasedWithRAREMouseEvent:(RAREMouseEvent *)e;
- (void)pressCanceledWithRAREMouseEvent:(RAREMouseEvent *)e;
- (BOOL)wantsLongPress;
- (BOOL)wantsMouseMovedEvents;
- (id)initWithRAREImagePanel:(RAREImagePanel *)outer$;
- (void)copyAllFieldsTo:(RAREImagePanel_MouseHandler *)other;
@end

@interface RAREImagePanel_ScaleHandler : NSObject < RAREiGestureListener > {
 @public
  __weak RAREImagePanel *this$0_;
}

- (void)onFlingWithId:(id)view
   withRAREMouseEvent:(RAREMouseEvent *)e1
   withRAREMouseEvent:(RAREMouseEvent *)e2
            withFloat:(float)velocityX
            withFloat:(float)velocityY;
- (void)onLongPressWithId:(id)view
       withRAREMouseEvent:(RAREMouseEvent *)e;
- (void)onRotateWithId:(id)view
               withInt:(int)type
             withFloat:(float)rotation
             withFloat:(float)velocity
             withFloat:(float)focusX
             withFloat:(float)focusY;
- (void)onScaleEventWithId:(id)view
                   withInt:(int)type
                    withId:(id)sgd
                 withFloat:(float)factor;
- (id)initWithRAREImagePanel:(RAREImagePanel *)outer$;
@end

#endif // _RAREImagePanel_H_
