//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/effects/aAnimator.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RAREaAnimator_H_
#define _RAREaAnimator_H_

@class RAREActionEvent;
@class RAREEventListenerList;
@class RARELocationEnum;
@class RARERenderTypeEnum;
@class RAREUIImage;
@class RAREUIRectangle;
@class RAREiAnimator_DirectionEnum;
@class SPOTPrintableString;
@protocol JavaUtilMap;
@protocol RAREiActionListener;
@protocol RAREiAnimator;
@protocol RAREiAnimatorListener;
@protocol RAREiAnimatorValueListener;
@protocol RAREiBackgroundPainter;
@protocol RAREiFunctionCallback;
@protocol RAREiPlatformComponent;
@protocol RAREiTransitionAnimator;
@protocol RAREiWidget;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/effects/iPlatformAnimator.h"
#include "java/lang/Runnable.h"

@interface RAREaAnimator : NSObject < RAREiPlatformAnimator, NSCopying > {
 @public
  RAREiAnimator_DirectionEnum *direction_;
  BOOL autoOrient_;
  BOOL fadeIn_;
  BOOL fadeOut_;
  RAREEventListenerList *listenerList_;
  id postAnimateAction_;
}

+ (NSString *)ANIMATOR_DISABLE_VIEW_EVENTS_KEY;
+ (void)setANIMATOR_DISABLE_VIEW_EVENTS_KEY:(NSString *)ANIMATOR_DISABLE_VIEW_EVENTS_KEY;
+ (NSString *)ANIMATOR_KEY;
+ (void)setANIMATOR_KEY:(NSString *)ANIMATOR_KEY;
- (void)addListenerWithRAREiAnimatorListener:(id<RAREiAnimatorListener>)l;
- (void)restoreComponentWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp;
+ (void)adjustAnimationWithRAREiAnimator:(id<RAREiAnimator>)a
                             withBoolean:(BOOL)forward
                  withRARERenderTypeEnum:(RARERenderTypeEnum *)sizingAnchor
                    withRARELocationEnum:(RARELocationEnum *)loc;
- (void)cancel;
- (id)clone;
+ (id<RAREiPlatformAnimator>)createAnimatorWithRAREiWidget:(id<RAREiWidget>)context
                                   withSPOTPrintableString:(SPOTPrintableString *)ps;
+ (id<RAREiPlatformAnimator>)createAnimatorWithRAREiWidget:(id<RAREiWidget>)context
                                              withNSString:(NSString *)s
                                           withJavaUtilMap:(id<JavaUtilMap>)options;
+ (id<RAREiTransitionAnimator>)createTransitionAnimatorWithRAREiWidget:(id<RAREiWidget>)context
                                                          withNSString:(NSString *)s
                                                       withJavaUtilMap:(id<JavaUtilMap>)options;
- (void)dispose;
- (void)handlePropertiesWithJavaUtilMap:(id<JavaUtilMap>)map;
- (void)removeListenerWithRAREiAnimatorListener:(id<RAREiAnimatorListener>)l;
+ (void)setupTogglingAnimatorWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c
                              withRAREiPlatformAnimator:(id<RAREiPlatformAnimator>)a
                                            withBoolean:(BOOL)visible
                                            withBoolean:(BOOL)visibleIsforward;
- (void)setAnimatingPropertyWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c
                                           withBoolean:(BOOL)animating;
- (void)setAutoOrientWithBoolean:(BOOL)autoOrient;
- (void)setDirectionWithRAREiAnimator_DirectionEnum:(RAREiAnimator_DirectionEnum *)direction;
- (id<RAREiPlatformComponent>)setIncommingComponentWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)inComponent;
- (void)setOutgoingComponentWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)outComponent
                                       withRAREUIImage:(RAREUIImage *)outImage;
- (void)setPostAnimateActionWithId:(id)postAnimateAction;
- (void)setViewEventsEnabledWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c
                                           withBoolean:(BOOL)enabled;
- (RAREiAnimator_DirectionEnum *)getDirection;
- (id<RAREiBackgroundPainter>)getPainter;
- (id)getPostAnimateAction;
+ (BOOL)isAnimatingWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c;
- (BOOL)isAutoOrient;
- (BOOL)isPaintBased;
- (BOOL)isSizingAnimation;
+ (BOOL)isViewEventsDisabledWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c;
- (void)addValueListenerWithRAREiAnimatorValueListener:(id<RAREiAnimatorValueListener>)l;
- (void)clear;
- (void)handleCustomPropertiesWithJavaUtilMap:(id<JavaUtilMap>)map;
- (void)handlePostAnimateAction;
- (void)notifyListenersWithRAREiPlatformAnimator:(id<RAREiPlatformAnimator>)animator
                                     withBoolean:(BOOL)ended;
- (void)notifyValueListenersWithRAREiPlatformAnimator:(id<RAREiPlatformAnimator>)animator
                                            withFloat:(float)value;
- (void)removeValueListenerWithRAREiAnimatorValueListener:(id<RAREiAnimatorValueListener>)l;
- (id)getProxy;
- (void)animateWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)param0
                                   withId:(id)param1;
- (BOOL)isRunning;
- (void)setAccelerationWithFloat:(float)param0;
- (void)setAutoReverseWithBoolean:(BOOL)param0;
- (void)setDecelerationWithFloat:(float)param0;
- (void)setDurationWithInt:(int)param0;
- (void)setRepeatCountWithInt:(int)param0;
- (void)stop;
- (id)init;
- (id)copyWithZone:(NSZone *)zone;
- (void)copyAllFieldsTo:(RAREaAnimator *)other;
@end

J2OBJC_FIELD_SETTER(RAREaAnimator, direction_, RAREiAnimator_DirectionEnum *)
J2OBJC_FIELD_SETTER(RAREaAnimator, listenerList_, RAREEventListenerList *)
J2OBJC_FIELD_SETTER(RAREaAnimator, postAnimateAction_, id)

typedef RAREaAnimator ComAppnativaRareUiEffectsAAnimator;

@interface RAREaAnimator_BoundsChanger : NSObject {
 @public
  RAREUIRectangle *from_;
  RAREUIRectangle *to_;
  float wdiff_;
  float hdiff_;
  float xdiff_;
  float ydiff_;
}

- (id)initWithRAREUIRectangle:(RAREUIRectangle *)from
          withRAREUIRectangle:(RAREUIRectangle *)to;
- (void)updateDiffs;
- (BOOL)isSmaller;
- (BOOL)isSizeDifferent;
- (float)getFromCenterX;
- (float)getToCenterX;
- (float)getFromCenterY;
- (float)getToCenterY;
- (float)getHeightWithFloat:(float)fraction;
- (float)getWidthWithFloat:(float)fraction;
- (float)getXWithFloat:(float)fraction;
- (float)getYWithFloat:(float)fraction;
- (void)copyAllFieldsTo:(RAREaAnimator_BoundsChanger *)other;
@end

J2OBJC_FIELD_SETTER(RAREaAnimator_BoundsChanger, from_, RAREUIRectangle *)
J2OBJC_FIELD_SETTER(RAREaAnimator_BoundsChanger, to_, RAREUIRectangle *)

@interface RAREaAnimator_$1 : NSObject < JavaLangRunnable > {
 @public
  id<RAREiActionListener> val$al_;
  RAREActionEvent *val$ae_;
}

- (void)run;
- (id)initWithRAREiActionListener:(id<RAREiActionListener>)capture$0
              withRAREActionEvent:(RAREActionEvent *)capture$1;
@end

J2OBJC_FIELD_SETTER(RAREaAnimator_$1, val$al_, id<RAREiActionListener>)
J2OBJC_FIELD_SETTER(RAREaAnimator_$1, val$ae_, RAREActionEvent *)

@interface RAREaAnimator_$2 : NSObject < JavaLangRunnable > {
 @public
  RAREaAnimator *this$0_;
  id<RAREiFunctionCallback> val$cb_;
}

- (void)run;
- (id)initWithRAREaAnimator:(RAREaAnimator *)outer$
  withRAREiFunctionCallback:(id<RAREiFunctionCallback>)capture$0;
@end

J2OBJC_FIELD_SETTER(RAREaAnimator_$2, this$0_, RAREaAnimator *)
J2OBJC_FIELD_SETTER(RAREaAnimator_$2, val$cb_, id<RAREiFunctionCallback>)

#endif // _RAREaAnimator_H_
