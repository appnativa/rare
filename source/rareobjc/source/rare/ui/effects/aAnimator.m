//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/effects/aAnimator.java
//
//  Created by decoteaud on 12/8/15.
//

#include "IOSClass.h"
#include "IOSObjectArray.h"
#include "com/appnativa/rare/Platform.h"
#include "com/appnativa/rare/iFunctionCallback.h"
#include "com/appnativa/rare/iPlatformAppContext.h"
#include "com/appnativa/rare/ui/Location.h"
#include "com/appnativa/rare/ui/RenderType.h"
#include "com/appnativa/rare/ui/UIDimension.h"
#include "com/appnativa/rare/ui/UIImage.h"
#include "com/appnativa/rare/ui/UIRectangle.h"
#include "com/appnativa/rare/ui/effects/TransitionAnimator.h"
#include "com/appnativa/rare/ui/effects/aAnimator.h"
#include "com/appnativa/rare/ui/effects/aSizeAnimation.h"
#include "com/appnativa/rare/ui/effects/iAnimator.h"
#include "com/appnativa/rare/ui/effects/iAnimatorListener.h"
#include "com/appnativa/rare/ui/effects/iAnimatorValueListener.h"
#include "com/appnativa/rare/ui/effects/iPlatformAnimator.h"
#include "com/appnativa/rare/ui/effects/iTransitionAnimator.h"
#include "com/appnativa/rare/ui/effects/iTransitionSupport.h"
#include "com/appnativa/rare/ui/event/ActionEvent.h"
#include "com/appnativa/rare/ui/event/EventListenerList.h"
#include "com/appnativa/rare/ui/event/iActionListener.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "com/appnativa/rare/ui/painter/iBackgroundPainter.h"
#include "com/appnativa/rare/viewer/WindowViewer.h"
#include "com/appnativa/rare/widget/iWidget.h"
#include "com/appnativa/spot/SPOTPrintableString.h"
#include "com/appnativa/util/CharScanner.h"
#include "com/appnativa/util/SNumber.h"
#include "java/lang/Boolean.h"
#include "java/lang/CloneNotSupportedException.h"
#include "java/lang/InternalError.h"
#include "java/lang/Math.h"
#include "java/util/Map.h"

@implementation RAREaAnimator

static NSString * RAREaAnimator_ANIMATOR_DISABLE_VIEW_EVENTS_KEY_ = @"ANIMATOR_DISABLE_VIEW_EVENTS_KEY";
static NSString * RAREaAnimator_ANIMATOR_KEY_ = @"RARE_COMPONENT_ANIMATOR";

+ (NSString *)ANIMATOR_DISABLE_VIEW_EVENTS_KEY {
  return RAREaAnimator_ANIMATOR_DISABLE_VIEW_EVENTS_KEY_;
}

+ (void)setANIMATOR_DISABLE_VIEW_EVENTS_KEY:(NSString *)ANIMATOR_DISABLE_VIEW_EVENTS_KEY {
  RAREaAnimator_ANIMATOR_DISABLE_VIEW_EVENTS_KEY_ = ANIMATOR_DISABLE_VIEW_EVENTS_KEY;
}

+ (NSString *)ANIMATOR_KEY {
  return RAREaAnimator_ANIMATOR_KEY_;
}

+ (void)setANIMATOR_KEY:(NSString *)ANIMATOR_KEY {
  RAREaAnimator_ANIMATOR_KEY_ = ANIMATOR_KEY;
}

- (void)addListenerWithRAREiAnimatorListener:(id<RAREiAnimatorListener>)l {
  if (listenerList_ == nil) {
    listenerList_ = [[RAREEventListenerList alloc] init];
  }
  [((RAREEventListenerList *) nil_chk(listenerList_)) addWithIOSClass:[IOSClass classWithProtocol:@protocol(RAREiAnimatorListener)] withId:l];
}

- (void)restoreComponentWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp {
}

+ (void)adjustAnimationWithRAREiAnimator:(id<RAREiAnimator>)a
                             withBoolean:(BOOL)forward
                  withRARERenderTypeEnum:(RARERenderTypeEnum *)sizingAnchor
                    withRARELocationEnum:(RARELocationEnum *)loc {
  do {
    if ((a == nil) || ![a isAutoOrient]) {
      break;
    }
    if ([(id) a conformsToProtocol: @protocol(RAREiTransitionSupport)]) {
      id<RAREiTransitionSupport> ts = (id<RAREiTransitionSupport>) check_protocol_cast(a, @protocol(RAREiTransitionSupport));
      if ([((id<RAREiTransitionSupport>) nil_chk(ts)) getTransitionType] == [RAREiTransitionSupport_TransitionTypeEnum STACK]) {
        if ([(id) a isKindOfClass:[RAREaSizeAnimation class]]) {
          RAREaSizeAnimation *sa = (RAREaSizeAnimation *) check_class_cast(a, [RAREaSizeAnimation class]);
          if (sizingAnchor != nil) {
            [((RAREaSizeAnimation *) nil_chk(sa)) setDiagonalAnchorWithId:[RAREaSizeAnimation getComplementarySideAnchorWithRARERenderTypeEnum:sizingAnchor withRARELocationEnum:loc]];
          }
          else {
            switch ([loc ordinal]) {
              case RARELocation_TOP:
              case RARELocation_BOTTOM:
              [((RAREaSizeAnimation *) nil_chk(sa)) setHorizontalWithBoolean:NO];
              break;
              default:
              [((RAREaSizeAnimation *) nil_chk(sa)) setHorizontalWithBoolean:YES];
              break;
            }
          }
        }
      }
      else {
        switch ([loc ordinal]) {
          case RARELocation_TOP:
          [ts setTransitionTypeWithRAREiTransitionSupport_TransitionTypeEnum:[RAREiTransitionSupport_TransitionTypeEnum VERTICAL]];
          forward = !forward;
          break;
          case RARELocation_BOTTOM:
          [ts setTransitionTypeWithRAREiTransitionSupport_TransitionTypeEnum:[RAREiTransitionSupport_TransitionTypeEnum VERTICAL]];
          break;
          case RARELocation_LEFT:
          forward = !forward;
          [ts setTransitionTypeWithRAREiTransitionSupport_TransitionTypeEnum:[RAREiTransitionSupport_TransitionTypeEnum HORIZONTAL]];
          break;
          case RARELocation_RIGHT:
          [ts setTransitionTypeWithRAREiTransitionSupport_TransitionTypeEnum:[RAREiTransitionSupport_TransitionTypeEnum HORIZONTAL]];
          break;
          default:
          break;
        }
      }
    }
    [((id<RAREiAnimator>) nil_chk(a)) setDirectionWithRAREiAnimator_DirectionEnum:forward ? [RAREiAnimator_DirectionEnum FORWARD] : [RAREiAnimator_DirectionEnum BACKWARD]];
  }
  while (NO);
}

- (void)cancel {
  [self stop];
}

- (id)clone {
  @try {
    RAREaAnimator *a = (RAREaAnimator *) check_class_cast([super clone], [RAREaAnimator class]);
    ((RAREaAnimator *) nil_chk(a))->listenerList_ = nil;
    a->postAnimateAction_ = nil;
    return a;
  }
  @catch (JavaLangCloneNotSupportedException *e) {
    @throw [[JavaLangInternalError alloc] init];
  }
}

+ (id<RAREiPlatformAnimator>)createAnimatorWithRAREiWidget:(id<RAREiWidget>)context
                                   withSPOTPrintableString:(SPOTPrintableString *)ps {
  return [RAREaAnimator createAnimatorWithRAREiWidget:context withNSString:[((SPOTPrintableString *) nil_chk(ps)) getValue] withJavaUtilMap:[ps spot_getAttributesEx]];
}

+ (id<RAREiPlatformAnimator>)createAnimatorWithRAREiWidget:(id<RAREiWidget>)context
                                              withNSString:(NSString *)s
                                           withJavaUtilMap:(id<JavaUtilMap>)options {
  if ((s == nil) || ([s sequenceLength] == 0)) {
    return nil;
  }
  id<RAREiPlatformAnimator> animator = nil;
  int n = [((NSString *) nil_chk(s)) indexOf:';'];
  if (n != -1) {
    s = [((NSString *) nil_chk([s substring:0 endIndex:n])) trim];
  }
  animator = [((RAREWindowViewer *) nil_chk([RAREPlatform getWindowViewer])) createAnimatorWithNSString:s];
  if (animator != nil) {
    [animator handlePropertiesWithJavaUtilMap:options];
  }
  return animator;
}

+ (id<RAREiTransitionAnimator>)createTransitionAnimatorWithRAREiWidget:(id<RAREiWidget>)context
                                                          withNSString:(NSString *)s
                                                       withJavaUtilMap:(id<JavaUtilMap>)options {
  if ((s == nil) || ([s sequenceLength] == 0)) {
    return nil;
  }
  NSString *s1 = s;
  NSString *s2 = nil;
  id<RAREiPlatformAnimator> in = nil;
  id<RAREiPlatformAnimator> out = nil;
  int n = [((NSString *) nil_chk(s)) indexOf:';'];
  if (n != -1) {
    s2 = [((NSString *) nil_chk([s substring:n + 1])) trim];
    s1 = [((NSString *) nil_chk([s substring:0 endIndex:n])) trim];
  }
  if (s1 != nil) {
    in = [RAREaAnimator createAnimatorWithRAREiWidget:context withNSString:s1 withJavaUtilMap:options];
  }
  if (s2 != nil) {
    out = [RAREaAnimator createAnimatorWithRAREiWidget:context withNSString:s2 withJavaUtilMap:options];
  }
  if ((in == nil) && (out == nil)) {
    return nil;
  }
  if (in == nil) {
    in = out;
    out = nil;
  }
  if ((options == nil) || ([options getWithId:@"autoOrient"] == nil)) {
    if (out != nil) {
      [out setAutoOrientWithBoolean:YES];
    }
    if (in != nil) {
      [in setAutoOrientWithBoolean:YES];
    }
  }
  RARETransitionAnimator *ta = [[RARETransitionAnimator alloc] initWithRAREiPlatformAnimator:in withRAREiPlatformAnimator:out];
  [ta setAutoDisposeWithBoolean:YES];
  return ta;
}

- (void)dispose {
  [self clear];
  if (listenerList_ != nil) {
    [listenerList_ clear];
    listenerList_ = nil;
  }
}

- (void)handlePropertiesWithJavaUtilMap:(id<JavaUtilMap>)map {
  if (map == nil) {
    return;
  }
  id o = [((id<JavaUtilMap>) nil_chk(map)) getWithId:@"duration"];
  if ([o isKindOfClass:[NSNumber class]]) {
    [self setDurationWithInt:[((NSNumber *) check_class_cast(o, [NSNumber class])) intValue]];
  }
  else if ([o isKindOfClass:[NSString class]]) {
    [self setDurationWithInt:[RAREUTSNumber intValueWithNSString:(NSString *) check_class_cast(o, [NSString class])]];
  }
  o = [map getWithId:@"direction"];
  if ([o isKindOfClass:[RAREiAnimator_DirectionEnum class]]) {
    [self setDirectionWithRAREiAnimator_DirectionEnum:(RAREiAnimator_DirectionEnum *) o];
  }
  else if ([o isKindOfClass:[NSString class]]) {
    [self setDirectionWithRAREiAnimator_DirectionEnum:[@"backward" equalsIgnoreCase:(NSString *) check_class_cast(o, [NSString class])] ? [RAREiAnimator_DirectionEnum BACKWARD] : [RAREiAnimator_DirectionEnum FORWARD]];
  }
  o = [map getWithId:@"acceleration"];
  if ([o isKindOfClass:[NSNumber class]]) {
    [self setAccelerationWithFloat:[((NSNumber *) check_class_cast(o, [NSNumber class])) floatValue]];
  }
  else if ([o isKindOfClass:[NSString class]]) {
    [self setAccelerationWithFloat:[RAREUTSNumber floatValueWithNSString:(NSString *) check_class_cast(o, [NSString class])]];
  }
  o = [map getWithId:@"deceleration"];
  if ([o isKindOfClass:[NSNumber class]]) {
    [self setDecelerationWithFloat:[((NSNumber *) check_class_cast(o, [NSNumber class])) floatValue]];
  }
  else if ([o isKindOfClass:[NSString class]]) {
    [self setDecelerationWithFloat:[RAREUTSNumber floatValueWithNSString:(NSString *) check_class_cast(o, [NSString class])]];
  }
  o = [map getWithId:@"fadeIn"];
  if ([o isKindOfClass:[JavaLangBoolean class]]) {
    fadeIn_ = [((JavaLangBoolean *) check_class_cast(o, [JavaLangBoolean class])) booleanValue];
  }
  else if ([o isKindOfClass:[NSString class]]) {
    fadeIn_ = [@"true" equalsIgnoreCase:(NSString *) check_class_cast(o, [NSString class])];
  }
  o = [map getWithId:@"autoOrient"];
  if ([o isKindOfClass:[JavaLangBoolean class]]) {
    autoOrient_ = [((JavaLangBoolean *) check_class_cast(o, [JavaLangBoolean class])) booleanValue];
  }
  else if ([o isKindOfClass:[NSString class]]) {
    autoOrient_ = [@"true" equalsIgnoreCase:(NSString *) check_class_cast(o, [NSString class])];
  }
  o = [map getWithId:@"fadeOut"];
  if ([o isKindOfClass:[JavaLangBoolean class]]) {
    fadeOut_ = [((JavaLangBoolean *) check_class_cast(o, [JavaLangBoolean class])) booleanValue];
  }
  else if ([o isKindOfClass:[NSString class]]) {
    fadeOut_ = [@"true" equalsIgnoreCase:(NSString *) check_class_cast(o, [NSString class])];
  }
  o = [map getWithId:@"customProperties"];
  if ([o conformsToProtocol: @protocol(JavaUtilMap)]) {
    [self handleCustomPropertiesWithJavaUtilMap:map];
  }
  else if (([o isKindOfClass:[NSString class]]) && [((NSString *) check_class_cast(o, [NSString class])) sequenceLength] > 0) {
    [self handleCustomPropertiesWithJavaUtilMap:[RAREUTCharScanner parseOptionStringWithNSString:(NSString *) check_class_cast(o, [NSString class]) withChar:',']];
  }
}

- (void)removeListenerWithRAREiAnimatorListener:(id<RAREiAnimatorListener>)l {
  if (listenerList_ != nil) {
    [listenerList_ removeWithIOSClass:[IOSClass classWithProtocol:@protocol(RAREiAnimatorListener)] withId:l];
  }
}

+ (void)setupTogglingAnimatorWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c
                              withRAREiPlatformAnimator:(id<RAREiPlatformAnimator>)a
                                            withBoolean:(BOOL)visible
                                            withBoolean:(BOOL)visibleIsforward {
  id<RAREiTransitionSupport> ts = ([(id) a conformsToProtocol: @protocol(RAREiTransitionSupport)]) ? (id<RAREiTransitionSupport>) check_protocol_cast(a, @protocol(RAREiTransitionSupport)) : nil;
  if (visible) {
    [((id<RAREiPlatformAnimator>) nil_chk(a)) setDirectionWithRAREiAnimator_DirectionEnum:visibleIsforward ? [RAREiAnimator_DirectionEnum FORWARD] : [RAREiAnimator_DirectionEnum BACKWARD]];
  }
  else {
    [((id<RAREiPlatformAnimator>) nil_chk(a)) setDirectionWithRAREiAnimator_DirectionEnum:visibleIsforward ? [RAREiAnimator_DirectionEnum BACKWARD] : [RAREiAnimator_DirectionEnum FORWARD]];
  }
  if (ts != nil) {
    RAREUIDimension *d = [((id<RAREiPlatformComponent>) nil_chk(c)) getSize];
    {
      int x;
      int y;
      switch ([[ts getTransitionType] ordinal]) {
        case RAREiTransitionSupport_TransitionType_HORIZONTAL:
        x = [c getX];
        if (visible) {
          x += ([((id<RAREiPlatformAnimator>) nil_chk(a)) getDirection] == [RAREiAnimator_DirectionEnum FORWARD]) ? ((RAREUIDimension *) nil_chk(d))->width_ : -((RAREUIDimension *) nil_chk(d))->width_;
        }
        [c setBoundsWithFloat:x withFloat:[c getY] withFloat:((RAREUIDimension *) nil_chk(d))->width_ withFloat:d->height_];
        break;
        case RAREiTransitionSupport_TransitionType_VERTICAL:
        y = [c getY];
        if (visible) {
          y += ([((id<RAREiPlatformAnimator>) nil_chk(a)) getDirection] == [RAREiAnimator_DirectionEnum FORWARD]) ? ((RAREUIDimension *) nil_chk(d))->height_ : -((RAREUIDimension *) nil_chk(d))->height_;
        }
        [c setBoundsWithFloat:[c getX] withFloat:y withFloat:((RAREUIDimension *) nil_chk(d))->width_ withFloat:d->height_];
        break;
        default:
        break;
      }
    }
  }
}

- (void)setAnimatingPropertyWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c
                                           withBoolean:(BOOL)animating {
  [((id<RAREiPlatformComponent>) nil_chk(c)) putClientPropertyWithNSString:RAREaAnimator_ANIMATOR_KEY_ withId:animating ? self : nil];
  [c putClientPropertyWithNSString:RAREaAnimator_ANIMATOR_DISABLE_VIEW_EVENTS_KEY_ withId:animating ? [JavaLangBoolean getTRUE] : nil];
}

- (void)setAutoOrientWithBoolean:(BOOL)autoOrient {
  self->autoOrient_ = autoOrient;
}

- (void)setDirectionWithRAREiAnimator_DirectionEnum:(RAREiAnimator_DirectionEnum *)direction {
  self->direction_ = direction;
}

- (id<RAREiPlatformComponent>)setIncommingComponentWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)inComponent {
  return inComponent;
}

- (void)setOutgoingComponentWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)outComponent
                                       withRAREUIImage:(RAREUIImage *)outImage {
}

- (void)setPostAnimateActionWithId:(id)postAnimateAction {
  self->postAnimateAction_ = postAnimateAction;
}

- (void)setViewEventsEnabledWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c
                                           withBoolean:(BOOL)enabled {
  [((id<RAREiPlatformComponent>) nil_chk(c)) putClientPropertyWithNSString:RAREaAnimator_ANIMATOR_DISABLE_VIEW_EVENTS_KEY_ withId:!enabled ? [JavaLangBoolean getTRUE] : nil];
}

- (RAREiAnimator_DirectionEnum *)getDirection {
  return direction_;
}

- (id<RAREiBackgroundPainter>)getPainter {
  return nil;
}

- (id)getPostAnimateAction {
  return postAnimateAction_;
}

+ (BOOL)isAnimatingWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c {
  id o = [((id<RAREiPlatformComponent>) nil_chk(c)) getClientPropertyWithNSString:RAREaAnimator_ANIMATOR_KEY_];
  if ([o conformsToProtocol: @protocol(RAREiAnimator)]) {
    return [((id<RAREiAnimator>) check_protocol_cast(o, @protocol(RAREiAnimator))) isRunning];
  }
  if ([o conformsToProtocol: @protocol(RAREiTransitionAnimator)]) {
    return [((id<RAREiTransitionAnimator>) check_protocol_cast(o, @protocol(RAREiTransitionAnimator))) isRunning];
  }
  return NO;
}

- (BOOL)isAutoOrient {
  return autoOrient_;
}

- (BOOL)isPaintBased {
  return NO;
}

- (BOOL)isSizingAnimation {
  return NO;
}

+ (BOOL)isViewEventsDisabledWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c {
  return [((id<RAREiPlatformComponent>) nil_chk(c)) getClientPropertyWithNSString:RAREaAnimator_ANIMATOR_DISABLE_VIEW_EVENTS_KEY_] == [JavaLangBoolean getTRUE];
}

- (void)addValueListenerWithRAREiAnimatorValueListener:(id<RAREiAnimatorValueListener>)l {
  if (listenerList_ == nil) {
    listenerList_ = [[RAREEventListenerList alloc] init];
  }
  [((RAREEventListenerList *) nil_chk(listenerList_)) addWithIOSClass:[IOSClass classWithProtocol:@protocol(RAREiAnimatorValueListener)] withId:l];
}

- (void)clear {
  postAnimateAction_ = nil;
}

- (void)handleCustomPropertiesWithJavaUtilMap:(id<JavaUtilMap>)map {
  id o = [((id<JavaUtilMap>) nil_chk(map)) getWithId:@"autoReverse"];
  if ([o isKindOfClass:[JavaLangBoolean class]]) {
    [self setAutoReverseWithBoolean:[((JavaLangBoolean *) check_class_cast(o, [JavaLangBoolean class])) booleanValue]];
  }
  else if ([o isKindOfClass:[NSString class]]) {
    [self setAutoReverseWithBoolean:[@"true" equalsIgnoreCase:(NSString *) check_class_cast(o, [NSString class])]];
  }
}

- (void)handlePostAnimateAction {
  if (postAnimateAction_ != nil) {
    if ([postAnimateAction_ conformsToProtocol: @protocol(RAREiActionListener)]) {
      id<RAREiActionListener> al = (id<RAREiActionListener>) check_protocol_cast(postAnimateAction_, @protocol(RAREiActionListener));
      RAREActionEvent *ae = [[RAREActionEvent alloc] initWithId:self withNSString:@"endAnimation"];
      [RAREPlatform invokeLaterWithJavaLangRunnable:[[RAREaAnimator_$1 alloc] initWithRAREiActionListener:al withRAREActionEvent:ae]];
    }
    else if ([postAnimateAction_ conformsToProtocol: @protocol(RAREiFunctionCallback)]) {
      id<RAREiFunctionCallback> cb = (id<RAREiFunctionCallback>) check_protocol_cast(postAnimateAction_, @protocol(RAREiFunctionCallback));
      [RAREPlatform invokeLaterWithJavaLangRunnable:[[RAREaAnimator_$2 alloc] initWithRAREaAnimator:self withRAREiFunctionCallback:cb]];
    }
    else {
      [((RAREWindowViewer *) nil_chk([((id<RAREiPlatformAppContext>) nil_chk([RAREPlatform getAppContext])) getWindowViewer])) invokeLaterWithId:postAnimateAction_];
    }
  }
}

- (void)notifyListenersWithRAREiPlatformAnimator:(id<RAREiPlatformAnimator>)animator
                                     withBoolean:(BOOL)ended {
  if (listenerList_ != nil) {
    IOSObjectArray *listeners = [listenerList_ getListenerList];
    for (int i = (int) [((IOSObjectArray *) nil_chk(listeners)) count] - 2; i >= 0; i -= 2) {
      if (IOSObjectArray_Get(listeners, i) == [IOSClass classWithProtocol:@protocol(RAREiAnimatorListener)]) {
        if (ended) {
          [((id<RAREiAnimatorListener>) check_protocol_cast(IOSObjectArray_Get(listeners, i + 1), @protocol(RAREiAnimatorListener))) animationEndedWithRAREiPlatformAnimator:animator];
        }
        else {
          [((id<RAREiAnimatorListener>) check_protocol_cast(IOSObjectArray_Get(listeners, i + 1), @protocol(RAREiAnimatorListener))) animationStartedWithRAREiPlatformAnimator:animator];
        }
      }
    }
  }
}

- (void)notifyValueListenersWithRAREiPlatformAnimator:(id<RAREiPlatformAnimator>)animator
                                            withFloat:(float)value {
  if (listenerList_ != nil) {
    IOSObjectArray *listeners = [listenerList_ getListenerList];
    for (int i = (int) [((IOSObjectArray *) nil_chk(listeners)) count] - 2; i >= 0; i -= 2) {
      if (IOSObjectArray_Get(listeners, i) == [IOSClass classWithProtocol:@protocol(RAREiAnimatorValueListener)]) {
        [((id<RAREiAnimatorValueListener>) check_protocol_cast(IOSObjectArray_Get(listeners, i + 1), @protocol(RAREiAnimatorValueListener))) valueChangedWithRAREiPlatformAnimator:animator withFloat:value];
      }
    }
  }
}

- (void)removeValueListenerWithRAREiAnimatorValueListener:(id<RAREiAnimatorValueListener>)l {
  if (listenerList_ != nil) {
    [listenerList_ removeWithIOSClass:[IOSClass classWithProtocol:@protocol(RAREiAnimatorValueListener)] withId:l];
  }
}

- (id)getProxy {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
  return 0;
}

- (void)animateWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)param0
                                   withId:(id)param1 {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
}

- (BOOL)isRunning {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
  return 0;
}

- (void)setAccelerationWithFloat:(float)param0 {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
}

- (void)setAutoReverseWithBoolean:(BOOL)param0 {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
}

- (void)setDecelerationWithFloat:(float)param0 {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
}

- (void)setDurationWithInt:(int)param0 {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
}

- (void)setRepeatCountWithInt:(int)param0 {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
}

- (void)stop {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
}

- (id)init {
  if (self = [super init]) {
    direction_ = [RAREiAnimator_DirectionEnum FORWARD];
  }
  return self;
}

- (id)copyWithZone:(NSZone *)zone {
  return [self clone];
}

- (void)copyAllFieldsTo:(RAREaAnimator *)other {
  [super copyAllFieldsTo:other];
  other->autoOrient_ = autoOrient_;
  other->direction_ = direction_;
  other->fadeIn_ = fadeIn_;
  other->fadeOut_ = fadeOut_;
  other->listenerList_ = listenerList_;
  other->postAnimateAction_ = postAnimateAction_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "clone", NULL, "LNSObject", 0x1, NULL },
    { "createAnimatorWithRAREiWidget:withSPOTPrintableString:", NULL, "LRAREiPlatformAnimator", 0x9, NULL },
    { "createAnimatorWithRAREiWidget:withNSString:withJavaUtilMap:", NULL, "LRAREiPlatformAnimator", 0x9, NULL },
    { "createTransitionAnimatorWithRAREiWidget:withNSString:withJavaUtilMap:", NULL, "LRAREiTransitionAnimator", 0x9, NULL },
    { "setIncommingComponentWithRAREiPlatformComponent:", NULL, "LRAREiPlatformComponent", 0x1, NULL },
    { "getDirection", NULL, "LRAREiAnimator_DirectionEnum", 0x1, NULL },
    { "getPainter", NULL, "LRAREiBackgroundPainter", 0x1, NULL },
    { "getPostAnimateAction", NULL, "LNSObject", 0x1, NULL },
    { "isAnimatingWithRAREiPlatformComponent:", NULL, "Z", 0x9, NULL },
    { "isAutoOrient", NULL, "Z", 0x1, NULL },
    { "isPaintBased", NULL, "Z", 0x1, NULL },
    { "isSizingAnimation", NULL, "Z", 0x1, NULL },
    { "isViewEventsDisabledWithRAREiPlatformComponent:", NULL, "Z", 0x9, NULL },
    { "addValueListenerWithRAREiAnimatorValueListener:", NULL, "V", 0x4, NULL },
    { "clear", NULL, "V", 0x4, NULL },
    { "handleCustomPropertiesWithJavaUtilMap:", NULL, "V", 0x4, NULL },
    { "handlePostAnimateAction", NULL, "V", 0x4, NULL },
    { "notifyListenersWithRAREiPlatformAnimator:withBoolean:", NULL, "V", 0x4, NULL },
    { "notifyValueListenersWithRAREiPlatformAnimator:withFloat:", NULL, "V", 0x4, NULL },
    { "removeValueListenerWithRAREiAnimatorValueListener:", NULL, "V", 0x4, NULL },
    { "getProxy", NULL, "LNSObject", 0x401, NULL },
    { "animateWithRAREiPlatformComponent:withId:", NULL, "V", 0x401, NULL },
    { "isRunning", NULL, "Z", 0x401, NULL },
    { "setAccelerationWithFloat:", NULL, "V", 0x401, NULL },
    { "setAutoReverseWithBoolean:", NULL, "V", 0x401, NULL },
    { "setDecelerationWithFloat:", NULL, "V", 0x401, NULL },
    { "setDurationWithInt:", NULL, "V", 0x401, NULL },
    { "setRepeatCountWithInt:", NULL, "V", 0x401, NULL },
    { "stop", NULL, "V", 0x401, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "ANIMATOR_DISABLE_VIEW_EVENTS_KEY_", NULL, 0x9, "LNSString" },
    { "ANIMATOR_KEY_", NULL, 0x9, "LNSString" },
    { "direction_", NULL, 0x4, "LRAREiAnimator_DirectionEnum" },
    { "autoOrient_", NULL, 0x4, "Z" },
    { "fadeIn_", NULL, 0x4, "Z" },
    { "fadeOut_", NULL, 0x4, "Z" },
    { "listenerList_", NULL, 0x4, "LRAREEventListenerList" },
    { "postAnimateAction_", NULL, 0x4, "LNSObject" },
  };
  static J2ObjcClassInfo _RAREaAnimator = { "aAnimator", "com.appnativa.rare.ui.effects", NULL, 0x401, 29, methods, 8, fields, 0, NULL};
  return &_RAREaAnimator;
}

@end
@implementation RAREaAnimator_BoundsChanger

- (id)initWithRAREUIRectangle:(RAREUIRectangle *)from
          withRAREUIRectangle:(RAREUIRectangle *)to {
  if (self = [super init]) {
    self->from_ = from;
    self->to_ = to;
    wdiff_ = (float) [JavaLangMath floorWithDouble:((RAREUIRectangle *) nil_chk(to))->width_ - ((RAREUIRectangle *) nil_chk(from))->width_];
    hdiff_ = (float) [JavaLangMath floorWithDouble:to->height_ - from->height_];
    xdiff_ = (float) [JavaLangMath floorWithDouble:to->x_ - from->x_];
    ydiff_ = (float) [JavaLangMath floorWithDouble:to->y_ - from->y_];
  }
  return self;
}

- (void)updateDiffs {
  wdiff_ = (float) [JavaLangMath floorWithDouble:((RAREUIRectangle *) nil_chk(to_))->width_ - ((RAREUIRectangle *) nil_chk(from_))->width_];
  hdiff_ = (float) [JavaLangMath floorWithDouble:to_->height_ - from_->height_];
  xdiff_ = (float) [JavaLangMath floorWithDouble:to_->x_ - from_->x_];
  ydiff_ = (float) [JavaLangMath floorWithDouble:to_->y_ - from_->y_];
}

- (BOOL)isSmaller {
  return (wdiff_ < 0) || (hdiff_ < 0);
}

- (BOOL)isSizeDifferent {
  return (wdiff_ != 0) || (hdiff_ != 0);
}

- (float)getFromCenterX {
  return (((RAREUIRectangle *) nil_chk(from_))->width_ / 2) + from_->x_;
}

- (float)getToCenterX {
  return (((RAREUIRectangle *) nil_chk(to_))->width_ / 2) + to_->x_;
}

- (float)getFromCenterY {
  return (((RAREUIRectangle *) nil_chk(from_))->height_ / 2) + from_->y_;
}

- (float)getToCenterY {
  return (((RAREUIRectangle *) nil_chk(to_))->height_ / 2) + to_->y_;
}

- (float)getHeightWithFloat:(float)fraction {
  if (hdiff_ == 0) {
    return ((RAREUIRectangle *) nil_chk(from_))->height_;
  }
  return ((RAREUIRectangle *) nil_chk(from_))->height_ + (hdiff_ * fraction);
}

- (float)getWidthWithFloat:(float)fraction {
  if (wdiff_ == 0) {
    return ((RAREUIRectangle *) nil_chk(from_))->width_;
  }
  return ((RAREUIRectangle *) nil_chk(from_))->width_ + (wdiff_ * fraction);
}

- (float)getXWithFloat:(float)fraction {
  if (xdiff_ == 0) {
    return ((RAREUIRectangle *) nil_chk(from_))->x_;
  }
  return ((RAREUIRectangle *) nil_chk(from_))->x_ + (xdiff_ * fraction);
}

- (float)getYWithFloat:(float)fraction {
  if (ydiff_ == 0) {
    return ((RAREUIRectangle *) nil_chk(from_))->y_;
  }
  return ((RAREUIRectangle *) nil_chk(from_))->y_ + (ydiff_ * fraction);
}

- (void)copyAllFieldsTo:(RAREaAnimator_BoundsChanger *)other {
  [super copyAllFieldsTo:other];
  other->from_ = from_;
  other->hdiff_ = hdiff_;
  other->to_ = to_;
  other->wdiff_ = wdiff_;
  other->xdiff_ = xdiff_;
  other->ydiff_ = ydiff_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "isSmaller", NULL, "Z", 0x1, NULL },
    { "isSizeDifferent", NULL, "Z", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "from_", NULL, 0x1, "LRAREUIRectangle" },
    { "to_", NULL, 0x1, "LRAREUIRectangle" },
    { "wdiff_", NULL, 0x1, "F" },
    { "hdiff_", NULL, 0x1, "F" },
    { "xdiff_", NULL, 0x1, "F" },
    { "ydiff_", NULL, 0x1, "F" },
  };
  static J2ObjcClassInfo _RAREaAnimator_BoundsChanger = { "BoundsChanger", "com.appnativa.rare.ui.effects", "aAnimator", 0x9, 2, methods, 6, fields, 0, NULL};
  return &_RAREaAnimator_BoundsChanger;
}

@end
@implementation RAREaAnimator_$1

- (void)run {
  [((id<RAREiActionListener>) nil_chk(val$al_)) actionPerformedWithRAREActionEvent:val$ae_];
}

- (id)initWithRAREiActionListener:(id<RAREiActionListener>)capture$0
              withRAREActionEvent:(RAREActionEvent *)capture$1 {
  val$al_ = capture$0;
  val$ae_ = capture$1;
  return [super init];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcFieldInfo fields[] = {
    { "val$al_", NULL, 0x1012, "LRAREiActionListener" },
    { "val$ae_", NULL, 0x1012, "LRAREActionEvent" },
  };
  static J2ObjcClassInfo _RAREaAnimator_$1 = { "$1", "com.appnativa.rare.ui.effects", "aAnimator", 0x8000, 0, NULL, 2, fields, 0, NULL};
  return &_RAREaAnimator_$1;
}

@end
@implementation RAREaAnimator_$2

- (void)run {
  [((id<RAREiFunctionCallback>) nil_chk(val$cb_)) finishedWithBoolean:NO withId:this$0_];
}

- (id)initWithRAREaAnimator:(RAREaAnimator *)outer$
  withRAREiFunctionCallback:(id<RAREiFunctionCallback>)capture$0 {
  this$0_ = outer$;
  val$cb_ = capture$0;
  return [super init];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcFieldInfo fields[] = {
    { "this$0_", NULL, 0x1012, "LRAREaAnimator" },
    { "val$cb_", NULL, 0x1012, "LRAREiFunctionCallback" },
  };
  static J2ObjcClassInfo _RAREaAnimator_$2 = { "$2", "com.appnativa.rare.ui.effects", "aAnimator", 0x8000, 0, NULL, 2, fields, 0, NULL};
  return &_RAREaAnimator_$2;
}

@end
