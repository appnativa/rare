//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/effects/iAnimator.java
//
//  Created by decoteaud on 12/8/15.
//

#ifndef _RAREiAnimator_H_
#define _RAREiAnimator_H_

@class RAREiAnimator_DirectionEnum;
@protocol JavaUtilMap;
@protocol RAREiAnimatorListener;
@protocol RAREiPlatformComponent;

#import "JreEmulation.h"
#include "java/lang/Enum.h"

#define RAREiAnimator_INFINITE -1

@protocol RAREiAnimator < NSCopying, NSObject, JavaObject >
- (void)addListenerWithRAREiAnimatorListener:(id<RAREiAnimatorListener>)l;
- (void)animateWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c
                                   withId:(id)postAnimateAction;
- (void)cancel;
- (void)dispose;
- (void)handlePropertiesWithJavaUtilMap:(id<JavaUtilMap>)map;
- (void)removeListenerWithRAREiAnimatorListener:(id<RAREiAnimatorListener>)l;
- (void)stop;
- (void)setAccelerationWithFloat:(float)acceleration;
- (void)setAutoReverseWithBoolean:(BOOL)booleanValue;
- (void)setDecelerationWithFloat:(float)deceleration;
- (void)setDirectionWithRAREiAnimator_DirectionEnum:(RAREiAnimator_DirectionEnum *)direction;
- (void)setDurationWithInt:(int)duration;
- (void)setRepeatCountWithInt:(int)repeatCount;
- (BOOL)isRunning;
- (RAREiAnimator_DirectionEnum *)getDirection;
- (id)clone;
- (BOOL)isAutoOrient;
- (void)setAutoOrientWithBoolean:(BOOL)auto_;
- (BOOL)isSizingAnimation;
- (void)restoreComponentWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp;
- (void)setViewEventsEnabledWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c
                                           withBoolean:(BOOL)enabled;
- (void)setAnimatingPropertyWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c
                                           withBoolean:(BOOL)animating;
- (id)copyWithZone:(NSZone *)zone;
@end

@interface RAREiAnimator : NSObject {
}
+ (int)INFINITE;
@end

#define ComAppnativaRareUiEffectsIAnimator RAREiAnimator

typedef enum {
  RAREiAnimator_Direction_FORWARD = 0,
  RAREiAnimator_Direction_BACKWARD = 1,
} RAREiAnimator_Direction;

@interface RAREiAnimator_DirectionEnum : JavaLangEnum < NSCopying > {
}
+ (RAREiAnimator_DirectionEnum *)FORWARD;
+ (RAREiAnimator_DirectionEnum *)BACKWARD;
+ (IOSObjectArray *)values;
+ (RAREiAnimator_DirectionEnum *)valueOfWithNSString:(NSString *)name;
- (id)copyWithZone:(NSZone *)zone;
- (id)initWithNSString:(NSString *)__name withInt:(int)__ordinal;
@end

#endif // _RAREiAnimator_H_
