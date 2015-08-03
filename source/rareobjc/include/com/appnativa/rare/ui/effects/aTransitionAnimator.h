//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/effects/aTransitionAnimator.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RAREaTransitionAnimator_H_
#define _RAREaTransitionAnimator_H_

@class RAREAnimationComponent;
@class RAREUIRectangle;
@class RAREaTransitionAnimator_AnimationHandler;
@class RAREiAnimator_DirectionEnum;
@class RAREiTransitionSupport_TransitionTypeEnum;
@protocol RAREiFunctionCallback;
@protocol RAREiParentComponent;
@protocol RAREiPlatformAnimator;
@protocol RAREiPlatformComponent;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/effects/iAnimatorListener.h"
#include "com/appnativa/rare/ui/effects/iTransitionAnimator.h"
#include "java/lang/Runnable.h"

@interface RAREaTransitionAnimator : NSObject < RAREiTransitionAnimator > {
 @public
  RAREiAnimator_DirectionEnum *direction_;
  RAREaTransitionAnimator_AnimationHandler *animationHandler_;
  RAREAnimationComponent *imageComponent_;
  id<RAREiPlatformComponent> inAniComponent_;
  id<RAREiPlatformAnimator> inAnimator_;
  id<RAREiPlatformComponent> inComponent_;
  id<RAREiPlatformAnimator> outAnimator_;
  id<RAREiPlatformComponent> outComponent_;
  BOOL autoDispose_;
  id<RAREiFunctionCallback> thirdPartyCallback_;
  BOOL outgoingPersists_;
  BOOL outgoingHidden_;
}

- (id)initWithRAREiPlatformAnimator:(id<RAREiPlatformAnimator>)inAnimator
          withRAREiPlatformAnimator:(id<RAREiPlatformAnimator>)outAnimator;
- (void)setOutgoingPersistsWithBoolean:(BOOL)persists;
- (void)setOutgoingHiddenWithBoolean:(BOOL)hidden;
- (void)animateWithRAREiParentComponent:(id<RAREiParentComponent>)target
                    withRAREUIRectangle:(RAREUIRectangle *)bounds
              withRAREiFunctionCallback:(id<RAREiFunctionCallback>)cb;
- (void)cancel;
- (void)dispose;
+ (id<RAREiPlatformComponent>)resolveTransitionComponentWithRAREiParentComponent:(id<RAREiParentComponent>)target
                                                      withRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp;
- (void)setAutoDisposeWithBoolean:(BOOL)autoDispose;
- (void)setCallbackWithRAREiFunctionCallback:(id<RAREiFunctionCallback>)callback;
- (void)setDirectionWithRAREiAnimator_DirectionEnum:(RAREiAnimator_DirectionEnum *)direction;
- (void)setIncommingComponentWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp;
- (void)setOutgoingComponentWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp;
- (id<RAREiFunctionCallback>)getCallback;
- (RAREiAnimator_DirectionEnum *)getDirection;
- (id<RAREiPlatformAnimator>)getInAnimator;
- (id<RAREiPlatformAnimator>)getOutAnimator;
- (BOOL)isAutoDispose;
- (BOOL)isRunning;
- (void)notifyThirdPartyWithBoolean:(BOOL)ended;
- (void)setAnimatorPropertyWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c
                                          withBoolean:(BOOL)set;
- (void)setViewEventsDisabledWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c
                                            withBoolean:(BOOL)set;
- (void)copyAllFieldsTo:(RAREaTransitionAnimator *)other;
@end

J2OBJC_FIELD_SETTER(RAREaTransitionAnimator, direction_, RAREiAnimator_DirectionEnum *)
J2OBJC_FIELD_SETTER(RAREaTransitionAnimator, animationHandler_, RAREaTransitionAnimator_AnimationHandler *)
J2OBJC_FIELD_SETTER(RAREaTransitionAnimator, imageComponent_, RAREAnimationComponent *)
J2OBJC_FIELD_SETTER(RAREaTransitionAnimator, inAniComponent_, id<RAREiPlatformComponent>)
J2OBJC_FIELD_SETTER(RAREaTransitionAnimator, inAnimator_, id<RAREiPlatformAnimator>)
J2OBJC_FIELD_SETTER(RAREaTransitionAnimator, inComponent_, id<RAREiPlatformComponent>)
J2OBJC_FIELD_SETTER(RAREaTransitionAnimator, outAnimator_, id<RAREiPlatformAnimator>)
J2OBJC_FIELD_SETTER(RAREaTransitionAnimator, outComponent_, id<RAREiPlatformComponent>)
J2OBJC_FIELD_SETTER(RAREaTransitionAnimator, thirdPartyCallback_, id<RAREiFunctionCallback>)

typedef RAREaTransitionAnimator ComAppnativaRareUiEffectsATransitionAnimator;

@interface RAREaTransitionAnimator_AnimationHandler : NSObject < RAREiAnimatorListener > {
 @public
  __weak RAREaTransitionAnimator *this$0_;
  int animatorCount_;
  int animationCompleteCount_;
  RAREAnimationComponent *animationComponent_;
  RAREAnimationComponent *animationSBSComponent_;
  id<RAREiFunctionCallback> callback_;
  BOOL canceled_;
  id<RAREiParentComponent> target_;
  RAREUIRectangle *bounds_;
}

- (id)initWithRAREaTransitionAnimator:(RAREaTransitionAnimator *)outer$
             withRAREiParentComponent:(id<RAREiParentComponent>)target
            withRAREiFunctionCallback:(id<RAREiFunctionCallback>)cb;
- (void)animateWithRAREUIRectangle:(RAREUIRectangle *)bounds;
- (void)restoreOutComponent;
- (void)animationEndedWithRAREiPlatformAnimator:(id<RAREiPlatformAnimator>)source;
- (void)animationStartedWithRAREiPlatformAnimator:(id<RAREiPlatformAnimator>)source;
- (void)cancel;
- (void)dispose;
- (void)copyAllFieldsTo:(RAREaTransitionAnimator_AnimationHandler *)other;
@end

J2OBJC_FIELD_SETTER(RAREaTransitionAnimator_AnimationHandler, animationComponent_, RAREAnimationComponent *)
J2OBJC_FIELD_SETTER(RAREaTransitionAnimator_AnimationHandler, animationSBSComponent_, RAREAnimationComponent *)
J2OBJC_FIELD_SETTER(RAREaTransitionAnimator_AnimationHandler, callback_, id<RAREiFunctionCallback>)
J2OBJC_FIELD_SETTER(RAREaTransitionAnimator_AnimationHandler, target_, id<RAREiParentComponent>)
J2OBJC_FIELD_SETTER(RAREaTransitionAnimator_AnimationHandler, bounds_, RAREUIRectangle *)

@interface RAREaTransitionAnimator_StackAnimationHandler : RAREaTransitionAnimator_AnimationHandler {
 @public
  __weak RAREaTransitionAnimator *this$1_;
  RAREiTransitionSupport_TransitionTypeEnum *type_;
}

- (id)initWithRAREaTransitionAnimator:(RAREaTransitionAnimator *)outer$
             withRAREiParentComponent:(id<RAREiParentComponent>)target
            withRAREiFunctionCallback:(id<RAREiFunctionCallback>)cb
withRAREiTransitionSupport_TransitionTypeEnum:(RAREiTransitionSupport_TransitionTypeEnum *)type;
- (void)stackedComponents;
- (void)sideBySideComponents;
- (void)animateWithRAREUIRectangle:(RAREUIRectangle *)bounds;
- (void)animationEndedWithRAREiPlatformAnimator:(id<RAREiPlatformAnimator>)source;
- (void)copyAllFieldsTo:(RAREaTransitionAnimator_StackAnimationHandler *)other;
@end

J2OBJC_FIELD_SETTER(RAREaTransitionAnimator_StackAnimationHandler, type_, RAREiTransitionSupport_TransitionTypeEnum *)

@interface RAREaTransitionAnimator_$1 : NSObject < JavaLangRunnable > {
 @public
  RAREaTransitionAnimator *this$0_;
  id<RAREiFunctionCallback> val$cb_;
  BOOL val$ended_;
}

- (void)run;
- (id)initWithRAREaTransitionAnimator:(RAREaTransitionAnimator *)outer$
            withRAREiFunctionCallback:(id<RAREiFunctionCallback>)capture$0
                          withBoolean:(BOOL)capture$1;
@end

J2OBJC_FIELD_SETTER(RAREaTransitionAnimator_$1, this$0_, RAREaTransitionAnimator *)
J2OBJC_FIELD_SETTER(RAREaTransitionAnimator_$1, val$cb_, id<RAREiFunctionCallback>)

#endif // _RAREaTransitionAnimator_H_
