//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/effects/aTransitionAnimator.java
//
//  Created by decoteaud on 9/15/15.
//

#include "IOSClass.h"
#include "com/appnativa/rare/Platform.h"
#include "com/appnativa/rare/iFunctionCallback.h"
#include "com/appnativa/rare/platform/PlatformHelper.h"
#include "com/appnativa/rare/ui/AnimationComponent.h"
#include "com/appnativa/rare/ui/UIImage.h"
#include "com/appnativa/rare/ui/UIRectangle.h"
#include "com/appnativa/rare/ui/UIScreen.h"
#include "com/appnativa/rare/ui/effects/aAnimator.h"
#include "com/appnativa/rare/ui/effects/aTransitionAnimator.h"
#include "com/appnativa/rare/ui/effects/iAnimator.h"
#include "com/appnativa/rare/ui/effects/iPlatformAnimator.h"
#include "com/appnativa/rare/ui/effects/iTransitionSupport.h"
#include "com/appnativa/rare/ui/iParentComponent.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "com/appnativa/rare/viewer/iViewer.h"
#include "com/appnativa/rare/widget/iWidget.h"
#include "java/lang/Boolean.h"
#include "java/lang/Exception.h"
#include "java/lang/NullPointerException.h"

@implementation RAREaTransitionAnimator

- (id)initWithRAREiPlatformAnimator:(id<RAREiPlatformAnimator>)inAnimator
          withRAREiPlatformAnimator:(id<RAREiPlatformAnimator>)outAnimator {
  if (self = [super init]) {
    direction_ = [RAREiAnimator_DirectionEnum FORWARD];
    self->inAnimator_ = inAnimator;
    self->outAnimator_ = outAnimator;
    if (inAnimator == nil) {
      @throw [[JavaLangNullPointerException alloc] initWithNSString:@"the inAnimator parameter cannot be null"];
    }
  }
  return self;
}

- (void)setOutgoingPersistsWithBoolean:(BOOL)persists {
  outgoingPersists_ = persists;
}

- (void)setOutgoingHiddenWithBoolean:(BOOL)hidden {
  outgoingHidden_ = hidden;
}

- (void)animateWithRAREiParentComponent:(id<RAREiParentComponent>)target
                    withRAREUIRectangle:(RAREUIRectangle *)bounds
              withRAREiFunctionCallback:(id<RAREiFunctionCallback>)cb {
  if (outComponent_ == nil) {
    outComponent_ = imageComponent_;
  }
  if (outgoingHidden_ && (outComponent_ != nil)) {
    [outComponent_ setVisibleWithBoolean:NO];
  }
  RAREiTransitionSupport_TransitionTypeEnum *type = [RAREiTransitionSupport_TransitionTypeEnum STACK];
  if ([(id) inAnimator_ conformsToProtocol: @protocol(RAREiTransitionSupport)]) {
    type = [((id<RAREiTransitionSupport>) check_protocol_cast(inAnimator_, @protocol(RAREiTransitionSupport))) getTransitionType];
  }
  BOOL sizing = ((inAnimator_ != nil) && [inAnimator_ isSizingAnimation]) || ((outAnimator_ != nil) && [outAnimator_ isSizingAnimation]);
  if ((type == [RAREiTransitionSupport_TransitionTypeEnum STACK]) && !sizing) {
    animationHandler_ = [[RAREaTransitionAnimator_StackAnimationHandler alloc] initWithRAREaTransitionAnimator:self withRAREiParentComponent:target withRAREiFunctionCallback:cb withRAREiTransitionSupport_TransitionTypeEnum:type];
  }
  else {
    animationHandler_ = [[RAREaTransitionAnimator_AnimationHandler alloc] initWithRAREaTransitionAnimator:self withRAREiParentComponent:target withRAREiFunctionCallback:cb];
  }
  [((RAREaTransitionAnimator_AnimationHandler *) nil_chk(animationHandler_)) animateWithRAREUIRectangle:bounds];
}

- (void)cancel {
  if (animationHandler_ != nil) {
    [animationHandler_ cancel];
  }
  animationHandler_ = nil;
}

- (void)dispose {
  if (animationHandler_ != nil) {
    [animationHandler_ dispose];
  }
  if (imageComponent_ != nil) {
    [imageComponent_ dispose];
  }
  if (inAnimator_ != nil) {
    [inAnimator_ dispose];
  }
  if (outAnimator_ != nil) {
    [outAnimator_ dispose];
  }
  inAnimator_ = nil;
  outAnimator_ = nil;
  inComponent_ = nil;
  outComponent_ = nil;
  inAniComponent_ = nil;
  imageComponent_ = nil;
}

+ (id<RAREiPlatformComponent>)resolveTransitionComponentWithRAREiParentComponent:(id<RAREiParentComponent>)target
                                                      withRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp {
  if (((comp == nil) || ([comp getWidth] == 0) || ([comp getHeight] == 0))) {
    comp = [RAREaPlatformHelper createAnimationComponentWithRAREiWidget:[RAREPlatform getContextRootViewer]];
    [((id<RAREiPlatformComponent>) nil_chk(comp)) setBoundsWithRAREUIRectangle:[((id<RAREiParentComponent>) nil_chk(target)) getInnerBoundsWithRAREUIRectangle:nil]];
  }
  return comp;
}

- (void)setAutoDisposeWithBoolean:(BOOL)autoDispose {
  self->autoDispose_ = autoDispose;
}

- (void)setCallbackWithRAREiFunctionCallback:(id<RAREiFunctionCallback>)callback {
  self->thirdPartyCallback_ = callback;
}

- (void)setDirectionWithRAREiAnimator_DirectionEnum:(RAREiAnimator_DirectionEnum *)direction {
  self->direction_ = direction;
}

- (void)setIncommingComponentWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp {
  inComponent_ = comp;
  inAniComponent_ = comp;
  [self setViewEventsDisabledWithRAREiPlatformComponent:comp withBoolean:YES];
  if ([(id) inAnimator_ conformsToProtocol: @protocol(RAREiTransitionSupport)]) {
    id<RAREiPlatformComponent> c = [((id<RAREiTransitionSupport>) check_protocol_cast(inAnimator_, @protocol(RAREiTransitionSupport))) setIncommingComponentWithRAREiPlatformComponent:comp];
    if (c != nil) {
      inAniComponent_ = c;
    }
  }
}

- (void)setOutgoingComponentWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp {
  if (animationHandler_ != nil) {
    [animationHandler_ cancel];
  }
  RAREUIImage *img = nil;
  if (outgoingPersists_) {
    outComponent_ = comp;
  }
  else {
    if (imageComponent_ == nil) {
      imageComponent_ = [RAREaPlatformHelper createAnimationComponentWithRAREiWidget:[((id<RAREiPlatformComponent>) nil_chk(comp)) getWidget]];
    }
    outComponent_ = imageComponent_;
    img = (comp == nil) ? nil : [comp capture];
    [((RAREAnimationComponent *) nil_chk(imageComponent_)) setImageWithRAREUIImage:img];
  }
  if ([(id) outAnimator_ conformsToProtocol: @protocol(RAREiTransitionSupport)]) {
    [((id<RAREiTransitionSupport>) check_protocol_cast(outAnimator_, @protocol(RAREiTransitionSupport))) setOutgoingComponentWithRAREiPlatformComponent:outComponent_ withRAREUIImage:img];
  }
  else if ([(id) inAnimator_ conformsToProtocol: @protocol(RAREiTransitionSupport)]) {
    [((id<RAREiTransitionSupport>) check_protocol_cast(inAnimator_, @protocol(RAREiTransitionSupport))) setOutgoingComponentWithRAREiPlatformComponent:outComponent_ withRAREUIImage:img];
  }
}

- (id<RAREiFunctionCallback>)getCallback {
  return thirdPartyCallback_;
}

- (RAREiAnimator_DirectionEnum *)getDirection {
  return direction_;
}

- (id<RAREiPlatformAnimator>)getInAnimator {
  return inAnimator_;
}

- (id<RAREiPlatformAnimator>)getOutAnimator {
  return outAnimator_;
}

- (BOOL)isAutoDispose {
  return autoDispose_;
}

- (BOOL)isRunning {
  if ((inAnimator_ != nil) && [inAnimator_ isRunning]) {
    return YES;
  }
  if ((outAnimator_ != nil) && [outAnimator_ isRunning]) {
    return YES;
  }
  return NO;
}

- (void)notifyThirdPartyWithBoolean:(BOOL)ended {
  id<RAREiFunctionCallback> cb = thirdPartyCallback_;
  if (cb != nil) {
    [RAREPlatform invokeLaterWithJavaLangRunnable:[[RAREaTransitionAnimator_$1 alloc] initWithRAREaTransitionAnimator:self withRAREiFunctionCallback:cb withBoolean:ended]];
  }
}

- (void)setAnimatorPropertyWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c
                                          withBoolean:(BOOL)set {
  [((id<RAREiPlatformComponent>) nil_chk(c)) putClientPropertyWithNSString:[RAREaAnimator ANIMATOR_KEY] withId:set ? self : nil];
  [c putClientPropertyWithNSString:[RAREaAnimator ANIMATOR_DISABLE_VIEW_EVENTS_KEY] withId:set ? [JavaLangBoolean getTRUE] : nil];
}

- (void)setViewEventsDisabledWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c
                                            withBoolean:(BOOL)set {
  [((id<RAREiPlatformComponent>) nil_chk(c)) putClientPropertyWithNSString:[RAREaAnimator ANIMATOR_DISABLE_VIEW_EVENTS_KEY] withId:set ? [JavaLangBoolean getTRUE] : nil];
}

- (void)copyAllFieldsTo:(RAREaTransitionAnimator *)other {
  [super copyAllFieldsTo:other];
  other->animationHandler_ = animationHandler_;
  other->autoDispose_ = autoDispose_;
  other->direction_ = direction_;
  other->imageComponent_ = imageComponent_;
  other->inAniComponent_ = inAniComponent_;
  other->inAnimator_ = inAnimator_;
  other->inComponent_ = inComponent_;
  other->outAnimator_ = outAnimator_;
  other->outComponent_ = outComponent_;
  other->outgoingHidden_ = outgoingHidden_;
  other->outgoingPersists_ = outgoingPersists_;
  other->thirdPartyCallback_ = thirdPartyCallback_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "resolveTransitionComponentWithRAREiParentComponent:withRAREiPlatformComponent:", NULL, "LRAREiPlatformComponent", 0x9, NULL },
    { "getCallback", NULL, "LRAREiFunctionCallback", 0x1, NULL },
    { "getDirection", NULL, "LRAREiAnimator_DirectionEnum", 0x1, NULL },
    { "getInAnimator", NULL, "LRAREiPlatformAnimator", 0x1, NULL },
    { "getOutAnimator", NULL, "LRAREiPlatformAnimator", 0x1, NULL },
    { "isAutoDispose", NULL, "Z", 0x1, NULL },
    { "isRunning", NULL, "Z", 0x1, NULL },
    { "notifyThirdPartyWithBoolean:", NULL, "V", 0x4, NULL },
    { "setAnimatorPropertyWithRAREiPlatformComponent:withBoolean:", NULL, "V", 0x4, NULL },
    { "setViewEventsDisabledWithRAREiPlatformComponent:withBoolean:", NULL, "V", 0x4, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "direction_", NULL, 0x4, "LRAREiAnimator_DirectionEnum" },
    { "animationHandler_", NULL, 0x4, "LRAREaTransitionAnimator_AnimationHandler" },
    { "imageComponent_", NULL, 0x4, "LRAREAnimationComponent" },
    { "inAniComponent_", NULL, 0x4, "LRAREiPlatformComponent" },
    { "inAnimator_", NULL, 0x4, "LRAREiPlatformAnimator" },
    { "inComponent_", NULL, 0x4, "LRAREiPlatformComponent" },
    { "outAnimator_", NULL, 0x4, "LRAREiPlatformAnimator" },
    { "outComponent_", NULL, 0x4, "LRAREiPlatformComponent" },
    { "autoDispose_", NULL, 0x4, "Z" },
    { "thirdPartyCallback_", NULL, 0x4, "LRAREiFunctionCallback" },
    { "outgoingPersists_", NULL, 0x4, "Z" },
    { "outgoingHidden_", NULL, 0x4, "Z" },
  };
  static J2ObjcClassInfo _RAREaTransitionAnimator = { "aTransitionAnimator", "com.appnativa.rare.ui.effects", NULL, 0x401, 10, methods, 12, fields, 0, NULL};
  return &_RAREaTransitionAnimator;
}

@end
@implementation RAREaTransitionAnimator_AnimationHandler

- (id)initWithRAREaTransitionAnimator:(RAREaTransitionAnimator *)outer$
             withRAREiParentComponent:(id<RAREiParentComponent>)target
            withRAREiFunctionCallback:(id<RAREiFunctionCallback>)cb {
  this$0_ = outer$;
  if (self = [super init]) {
    animatorCount_ = 0;
    self->target_ = target;
    callback_ = cb;
  }
  return self;
}

- (void)animateWithRAREUIRectangle:(RAREUIRectangle *)bounds {
  self->bounds_ = bounds;
  int width = [RAREUIScreen snapToSizeWithFloat:((RAREUIRectangle *) nil_chk(bounds))->width_];
  int height = [RAREUIScreen snapToSizeWithFloat:bounds->height_];
  animationComponent_ = [RAREaPlatformHelper createAnimationComponentWithRAREiWidget:[((id<RAREiParentComponent>) nil_chk(target_)) getWidget]];
  if ((animationComponent_ == nil) || ((this$0_->inAnimator_ == nil) && (this$0_->outAnimator_ == nil))) {
    [((id<RAREiFunctionCallback>) nil_chk(callback_)) finishedWithBoolean:NO withId:nil];
    [self dispose];
    return;
  }
  [this$0_ setAnimatorPropertyWithRAREiPlatformComponent:target_ withBoolean:YES];
  [target_ removeWithRAREiPlatformComponent:this$0_->inComponent_];
  [((RAREAnimationComponent *) nil_chk(animationComponent_)) setBoundsWithFloat:bounds->x_ withFloat:bounds->y_ withFloat:bounds->width_ withFloat:bounds->height_];
  BOOL forward = (this$0_->inAnimator_ != nil) && ([this$0_->inAnimator_ getDirection] == [RAREiAnimator_DirectionEnum FORWARD]);
  BOOL singleAnimator = this$0_->outAnimator_ == nil;
  RAREiTransitionSupport_TransitionTypeEnum *type = [RAREiTransitionSupport_TransitionTypeEnum STACK];
  if ([(id) this$0_->inAnimator_ conformsToProtocol: @protocol(RAREiTransitionSupport)]) {
    type = [((id<RAREiTransitionSupport>) check_protocol_cast(this$0_->inAnimator_, @protocol(RAREiTransitionSupport))) getTransitionType];
  }
  id<RAREiPlatformComponent> c = animationComponent_;
  int x = [RAREUIScreen snapToPositionWithFloat:bounds->x_];
  int y = [RAREUIScreen snapToPositionWithFloat:bounds->y_];
  if (type != [RAREiTransitionSupport_TransitionTypeEnum STACK]) {
    animationSBSComponent_ = [RAREaPlatformHelper createAnimationComponentWithRAREiWidget:[target_ getWidget]];
    [((RAREAnimationComponent *) nil_chk(animationSBSComponent_)) setBoundsWithFloat:bounds->x_ withFloat:bounds->y_ withFloat:bounds->width_ withFloat:bounds->height_];
    [animationSBSComponent_ addWithRAREiPlatformComponent:animationComponent_];
    [animationComponent_ setBoundsWithFloat:0 withFloat:0 withFloat:bounds->width_ withFloat:bounds->height_];
    c = animationSBSComponent_;
    x = 0;
    y = 0;
  }
  switch ([type ordinal]) {
    case RAREiTransitionSupport_TransitionType_STACK:
    [animationComponent_ setStackedComponentsWithBoolean:forward withRAREiPlatformComponent:this$0_->outComponent_ withRAREiPlatformComponent:this$0_->inAniComponent_ withInt:width withInt:height];
    break;
    case RAREiTransitionSupport_TransitionType_VERTICAL:
    [animationComponent_ setSideBySideComponentsWithBoolean:forward withBoolean:NO withRAREiPlatformComponent:this$0_->outComponent_ withRAREiPlatformComponent:this$0_->inAniComponent_ withInt:width withInt:height withBoolean:singleAnimator];
    if (singleAnimator) {
      if (!forward) {
        y -= height;
      }
      [animationComponent_ setBoundsWithInt:x withInt:y withInt:width withInt:height * 2];
    }
    break;
    default:
    [animationComponent_ setSideBySideComponentsWithBoolean:forward withBoolean:YES withRAREiPlatformComponent:this$0_->outComponent_ withRAREiPlatformComponent:this$0_->inAniComponent_ withInt:width withInt:height withBoolean:singleAnimator];
    if (singleAnimator) {
      if (!forward) {
        x -= width;
      }
      [animationComponent_ setBoundsWithInt:x withInt:y withInt:width * 2 withInt:height];
    }
    break;
  }
  [target_ addWithRAREiPlatformComponent:c];
  if ((this$0_->inAnimator_ == nil) || (this$0_->outAnimator_ == nil)) {
    id<RAREiPlatformAnimator> a = (this$0_->inAnimator_ == nil) ? this$0_->outAnimator_ : this$0_->inAnimator_;
    animatorCount_ = 1;
    [a addListenerWithRAREiAnimatorListener:self];
    [a animateWithRAREiPlatformComponent:animationComponent_ withId:nil];
  }
  else {
    animatorCount_ = 2;
    [this$0_->outAnimator_ addListenerWithRAREiAnimatorListener:self];
    [this$0_->outAnimator_ animateWithRAREiPlatformComponent:this$0_->outComponent_ withId:self];
    [this$0_->inAnimator_ addListenerWithRAREiAnimatorListener:self];
    [this$0_->inAnimator_ animateWithRAREiPlatformComponent:this$0_->inAniComponent_ withId:nil];
  }
}

- (void)restoreOutComponent {
  if ((this$0_->outComponent_ != nil) && ![this$0_->outComponent_ isDisposed]) {
    if (this$0_->outAnimator_ != nil) {
      [this$0_->outAnimator_ restoreComponentWithRAREiPlatformComponent:this$0_->outComponent_];
    }
    else if (this$0_->inAnimator_ != nil) {
      [this$0_->inAnimator_ restoreComponentWithRAREiPlatformComponent:this$0_->outComponent_];
    }
  }
}

- (void)animationEndedWithRAREiPlatformAnimator:(id<RAREiPlatformAnimator>)source {
  if ((target_ != nil) && (this$0_->inComponent_ != nil)) {
    animationCompleteCount_++;
    if (animatorCount_ <= animationCompleteCount_) {
      if (animationComponent_ != nil) {
        [animationComponent_ removeFromParent];
      }
      if (animationSBSComponent_ != nil) {
        [animationSBSComponent_ removeFromParent];
      }
      if ((this$0_->outComponent_ != nil) && this$0_->outgoingHidden_ && ![this$0_->outComponent_ isDisposed]) {
        [this$0_->outComponent_ setVisibleWithBoolean:YES];
      }
      if (![this$0_->inComponent_ isDisposed]) {
        [this$0_->inComponent_ removeFromParent];
        [((id<RAREiPlatformComponent>) nil_chk(this$0_->inAniComponent_)) removeFromParent];
        if (this$0_->inAniComponent_ != this$0_->inComponent_) {
          [this$0_->inAniComponent_ dispose];
        }
        [this$0_ setAnimatorPropertyWithRAREiPlatformComponent:this$0_->inComponent_ withBoolean:NO];
        [target_ addWithRAREiPlatformComponent:this$0_->inComponent_];
      }
      if ((callback_ != nil) && !canceled_) {
        [callback_ finishedWithBoolean:NO withId:nil];
      }
      if (this$0_->outgoingPersists_) {
        [self restoreOutComponent];
      }
    }
    [this$0_ setAnimatorPropertyWithRAREiPlatformComponent:target_ withBoolean:NO];
    [target_ revalidate];
  }
  [this$0_ notifyThirdPartyWithBoolean:canceled_];
  [self dispose];
}

- (void)animationStartedWithRAREiPlatformAnimator:(id<RAREiPlatformAnimator>)source {
}

- (void)cancel {
  if (canceled_) {
    return;
  }
  canceled_ = YES;
  id<RAREiFunctionCallback> cb = callback_;
  if (this$0_->inAnimator_ != nil) {
    [this$0_->inAnimator_ cancel];
  }
  if (this$0_->outAnimator_ != nil) {
    [this$0_->outAnimator_ cancel];
  }
  callback_ = nil;
  if (cb != nil) {
    [cb finishedWithBoolean:YES withId:nil];
  }
  [this$0_ notifyThirdPartyWithBoolean:YES];
  [self dispose];
}

- (void)dispose {
  canceled_ = YES;
  @try {
    if (animationSBSComponent_ != nil) {
      [animationSBSComponent_ removeAll];
      [animationSBSComponent_ dispose];
    }
    if (animationComponent_ != nil) {
      [animationComponent_ removeAll];
      [animationComponent_ dispose];
    }
    if (this$0_->inAnimator_ != nil) {
      [this$0_->inAnimator_ removeListenerWithRAREiAnimatorListener:self];
    }
    if (this$0_->outAnimator_ != nil) {
      [this$0_->outAnimator_ removeListenerWithRAREiAnimatorListener:self];
    }
    if (this$0_->imageComponent_ != nil) {
      [this$0_->imageComponent_ dispose];
    }
    animationSBSComponent_ = nil;
    animationComponent_ = nil;
    callback_ = nil;
    target_ = nil;
    this$0_->inComponent_ = nil;
    this$0_->outComponent_ = nil;
    this$0_->inAniComponent_ = nil;
    this$0_->imageComponent_ = nil;
    bounds_ = nil;
  }
  @catch (JavaLangException *e) {
    [RAREPlatform ignoreExceptionWithNSString:nil withJavaLangThrowable:e];
  }
}

- (void)copyAllFieldsTo:(RAREaTransitionAnimator_AnimationHandler *)other {
  [super copyAllFieldsTo:other];
  other->animationCompleteCount_ = animationCompleteCount_;
  other->animationComponent_ = animationComponent_;
  other->animationSBSComponent_ = animationSBSComponent_;
  other->animatorCount_ = animatorCount_;
  other->bounds_ = bounds_;
  other->callback_ = callback_;
  other->canceled_ = canceled_;
  other->target_ = target_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "restoreOutComponent", NULL, "V", 0x4, NULL },
    { "cancel", NULL, "V", 0x0, NULL },
    { "dispose", NULL, "V", 0x4, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "this$0_", NULL, 0x1012, "LRAREaTransitionAnimator" },
    { "animatorCount_", NULL, 0x4, "I" },
    { "animationCompleteCount_", NULL, 0x4, "I" },
    { "animationComponent_", NULL, 0x4, "LRAREAnimationComponent" },
    { "animationSBSComponent_", NULL, 0x4, "LRAREAnimationComponent" },
    { "callback_", NULL, 0x4, "LRAREiFunctionCallback" },
    { "canceled_", NULL, 0x4, "Z" },
    { "target_", NULL, 0x4, "LRAREiParentComponent" },
    { "bounds_", NULL, 0x4, "LRAREUIRectangle" },
  };
  static J2ObjcClassInfo _RAREaTransitionAnimator_AnimationHandler = { "AnimationHandler", "com.appnativa.rare.ui.effects", "aTransitionAnimator", 0x0, 3, methods, 9, fields, 0, NULL};
  return &_RAREaTransitionAnimator_AnimationHandler;
}

@end
@implementation RAREaTransitionAnimator_StackAnimationHandler

- (id)initWithRAREaTransitionAnimator:(RAREaTransitionAnimator *)outer$
             withRAREiParentComponent:(id<RAREiParentComponent>)target
            withRAREiFunctionCallback:(id<RAREiFunctionCallback>)cb
withRAREiTransitionSupport_TransitionTypeEnum:(RAREiTransitionSupport_TransitionTypeEnum *)type {
  this$1_ = outer$;
  if (self = [super initWithRAREaTransitionAnimator:outer$ withRAREiParentComponent:target withRAREiFunctionCallback:cb]) {
    type_ = [RAREiTransitionSupport_TransitionTypeEnum STACK];
    self->target_ = target;
    callback_ = cb;
    self->type_ = type;
  }
  return self;
}

- (void)stackedComponents {
  BOOL forward = (this$1_->inAnimator_ != nil) && ([this$1_->inAnimator_ getDirection] == [RAREiAnimator_DirectionEnum FORWARD]);
  if (forward) {
    if (this$1_->inAniComponent_ != nil) {
      [this$1_->inAniComponent_ bringToFront];
    }
  }
  else {
    if (this$1_->outComponent_ != nil) {
      [this$1_->outComponent_ bringToFront];
    }
  }
  if (this$1_->inAniComponent_ != nil) {
    [this$1_->inAniComponent_ setBoundsWithRAREUIRectangle:bounds_];
  }
  if (this$1_->outComponent_ != nil) {
    [this$1_->outComponent_ setBoundsWithRAREUIRectangle:bounds_];
  }
}

- (void)sideBySideComponents {
  float width = ((RAREUIRectangle *) nil_chk(bounds_))->width_;
  float height = bounds_->height_;
  float x = bounds_->x_;
  float y = bounds_->y_;
  BOOL horizontal = type_ == [RAREiTransitionSupport_TransitionTypeEnum HORIZONTAL];
  BOOL forward = (this$1_->inAnimator_ != nil) && ([this$1_->inAnimator_ getDirection] == [RAREiAnimator_DirectionEnum FORWARD]);
  if (this$1_->outComponent_ != nil) {
    [this$1_->outComponent_ setBoundsWithFloat:x withFloat:y withFloat:width withFloat:height];
  }
  if (horizontal) {
    if (this$1_->inComponent_ != nil) {
      if (forward) {
        [this$1_->inComponent_ setBoundsWithFloat:x + width withFloat:y withFloat:width withFloat:height];
      }
      else {
        [this$1_->inComponent_ setBoundsWithFloat:x - width withFloat:y withFloat:width withFloat:height];
      }
    }
  }
  else {
    if (this$1_->inComponent_ != nil) {
      if (forward) {
        [this$1_->inComponent_ setBoundsWithFloat:x withFloat:y + height withFloat:width withFloat:height];
      }
      else {
        [this$1_->inComponent_ setBoundsWithFloat:x withFloat:y - height withFloat:width withFloat:height];
      }
    }
  }
}

- (void)animateWithRAREUIRectangle:(RAREUIRectangle *)bounds {
  if (((this$1_->inAnimator_ == nil) && (this$1_->outAnimator_ == nil))) {
    [((id<RAREiFunctionCallback>) nil_chk(callback_)) finishedWithBoolean:NO withId:nil];
    [self dispose];
    return;
  }
  self->bounds_ = bounds;
  [this$1_ setAnimatorPropertyWithRAREiPlatformComponent:target_ withBoolean:YES];
  if (this$1_->outComponent_ != nil) {
    [((id<RAREiParentComponent>) nil_chk(target_)) addWithRAREiPlatformComponent:this$1_->outComponent_];
  }
  if (type_ == [RAREiTransitionSupport_TransitionTypeEnum STACK]) {
    [self stackedComponents];
  }
  else {
    [self sideBySideComponents];
  }
  if (this$1_->inComponent_ != nil) {
    [this$1_ setAnimatorPropertyWithRAREiPlatformComponent:this$1_->inComponent_ withBoolean:YES];
  }
  if ((this$1_->inAnimator_ == nil) || (this$1_->outAnimator_ == nil)) {
    animatorCount_ = 1;
    if ((this$1_->inAnimator_ != nil) && (this$1_->inAniComponent_ != nil)) {
      [this$1_->inAnimator_ addListenerWithRAREiAnimatorListener:self];
      if ((type_ == [RAREiTransitionSupport_TransitionTypeEnum STACK]) || (this$1_->outComponent_ == nil)) {
        [this$1_->inAnimator_ animateWithRAREiPlatformComponent:this$1_->inAniComponent_ withId:nil];
      }
      else {
        [this$1_->inAnimator_ animateWithRAREiPlatformComponent:this$1_->outComponent_ withId:nil];
      }
    }
    else if ((this$1_->outAnimator_ != nil) && (this$1_->outComponent_ != nil)) {
      [this$1_->outAnimator_ addListenerWithRAREiAnimatorListener:self];
      [this$1_->outAnimator_ animateWithRAREiPlatformComponent:this$1_->outComponent_ withId:nil];
    }
    else {
      [((id<RAREiFunctionCallback>) nil_chk(callback_)) finishedWithBoolean:NO withId:nil];
      [self dispose];
      return;
    }
  }
  else {
    animatorCount_ = 2;
    [this$1_->outAnimator_ addListenerWithRAREiAnimatorListener:self];
    [this$1_->outAnimator_ animateWithRAREiPlatformComponent:this$1_->outComponent_ withId:self];
    [this$1_->inAnimator_ addListenerWithRAREiAnimatorListener:self];
    [this$1_->inAnimator_ animateWithRAREiPlatformComponent:this$1_->inAniComponent_ withId:nil];
  }
}

- (void)animationEndedWithRAREiPlatformAnimator:(id<RAREiPlatformAnimator>)source {
  if ((target_ != nil) && (this$1_->inComponent_ != nil)) {
    animationCompleteCount_++;
    if (animatorCount_ <= animationCompleteCount_) {
      if (animationComponent_ != nil) {
        [animationComponent_ removeFromParent];
      }
      if (animationSBSComponent_ != nil) {
        [animationSBSComponent_ removeFromParent];
      }
      if (this$1_->outgoingHidden_ && (this$1_->outComponent_ != nil)) {
        [this$1_->outComponent_ setVisibleWithBoolean:YES];
      }
      if (![this$1_->inComponent_ isDisposed]) {
        if (this$1_->inAniComponent_ != this$1_->inComponent_) {
          [((id<RAREiPlatformComponent>) nil_chk(this$1_->inAniComponent_)) removeFromParent];
          [this$1_->inAniComponent_ dispose];
        }
        if ([this$1_->inComponent_ getParent] != target_) {
          [this$1_->inComponent_ removeFromParent];
          [target_ addWithRAREiPlatformComponent:this$1_->inComponent_];
        }
        [this$1_ setAnimatorPropertyWithRAREiPlatformComponent:this$1_->inComponent_ withBoolean:NO];
        [this$1_->inComponent_ setBoundsWithFloat:((RAREUIRectangle *) nil_chk(bounds_))->x_ withFloat:bounds_->y_ withFloat:bounds_->width_ withFloat:bounds_->height_];
      }
      if ((callback_ != nil) && !canceled_) {
        [callback_ finishedWithBoolean:NO withId:nil];
      }
    }
    [this$1_ setAnimatorPropertyWithRAREiPlatformComponent:target_ withBoolean:NO];
    [target_ revalidate];
  }
  [this$1_ notifyThirdPartyWithBoolean:canceled_];
  [self dispose];
}

- (void)copyAllFieldsTo:(RAREaTransitionAnimator_StackAnimationHandler *)other {
  [super copyAllFieldsTo:other];
  other->type_ = type_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "stackedComponents", NULL, "V", 0x0, NULL },
    { "sideBySideComponents", NULL, "V", 0x0, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "this$1_", NULL, 0x1012, "LRAREaTransitionAnimator" },
    { "type_", NULL, 0x0, "LRAREiTransitionSupport_TransitionTypeEnum" },
  };
  static J2ObjcClassInfo _RAREaTransitionAnimator_StackAnimationHandler = { "StackAnimationHandler", "com.appnativa.rare.ui.effects", "aTransitionAnimator", 0x0, 2, methods, 2, fields, 0, NULL};
  return &_RAREaTransitionAnimator_StackAnimationHandler;
}

@end
@implementation RAREaTransitionAnimator_$1

- (void)run {
  [((id<RAREiFunctionCallback>) nil_chk(val$cb_)) finishedWithBoolean:val$ended_ withId:this$0_];
}

- (id)initWithRAREaTransitionAnimator:(RAREaTransitionAnimator *)outer$
            withRAREiFunctionCallback:(id<RAREiFunctionCallback>)capture$0
                          withBoolean:(BOOL)capture$1 {
  this$0_ = outer$;
  val$cb_ = capture$0;
  val$ended_ = capture$1;
  return [super init];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcFieldInfo fields[] = {
    { "this$0_", NULL, 0x1012, "LRAREaTransitionAnimator" },
    { "val$cb_", NULL, 0x1012, "LRAREiFunctionCallback" },
    { "val$ended_", NULL, 0x1012, "Z" },
  };
  static J2ObjcClassInfo _RAREaTransitionAnimator_$1 = { "$1", "com.appnativa.rare.ui.effects", "aTransitionAnimator", 0x8000, 0, NULL, 3, fields, 0, NULL};
  return &_RAREaTransitionAnimator_$1;
}

@end
