//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/effects/aSizeAnimation.java
//
//  Created by decoteaud on 12/8/15.
//

#include "IOSClass.h"
#include "com/appnativa/rare/Platform.h"
#include "com/appnativa/rare/platform/PlatformHelper.h"
#include "com/appnativa/rare/ui/AnimationComponent.h"
#include "com/appnativa/rare/ui/Location.h"
#include "com/appnativa/rare/ui/RenderType.h"
#include "com/appnativa/rare/ui/UIImage.h"
#include "com/appnativa/rare/ui/UIRectangle.h"
#include "com/appnativa/rare/ui/effects/aAnimator.h"
#include "com/appnativa/rare/ui/effects/aPlatformAnimator.h"
#include "com/appnativa/rare/ui/effects/aSizeAnimation.h"
#include "com/appnativa/rare/ui/effects/iAnimator.h"
#include "com/appnativa/rare/ui/effects/iTransitionSupport.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "com/appnativa/rare/widget/iWidget.h"
#include "java/lang/Boolean.h"
#include "java/lang/Exception.h"
#include "java/util/Locale.h"
#include "java/util/Map.h"

@implementation RAREaSizeAnimation

- (id)init {
  return [self initRAREaSizeAnimationWithBoolean:YES withBoolean:NO withRARERenderTypeEnum:[RARERenderTypeEnum CENTERED]];
}

- (id)initWithBoolean:(BOOL)horizontal {
  return [self initRAREaSizeAnimationWithBoolean:horizontal withBoolean:NO withRARERenderTypeEnum:[RARERenderTypeEnum CENTERED]];
}

- (id)initRAREaSizeAnimationWithBoolean:(BOOL)horizontal
                            withBoolean:(BOOL)diagonal
                 withRARERenderTypeEnum:(RARERenderTypeEnum *)anchor {
  if (self = [super init]) {
    horizontal_ = YES;
    percent_ = 1.0f;
    diagonalAnchor_ = [RARERenderTypeEnum CENTERED];
    diagonal_ = YES;
    self->horizontal_ = horizontal;
    self->diagonal_ = diagonal;
    [self setDurationWithInt:100];
    if (anchor != nil) {
      diagonalAnchor_ = anchor;
    }
  }
  return self;
}

- (id)initWithBoolean:(BOOL)horizontal
          withBoolean:(BOOL)diagonal
withRARERenderTypeEnum:(RARERenderTypeEnum *)anchor {
  return [self initRAREaSizeAnimationWithBoolean:horizontal withBoolean:diagonal withRARERenderTypeEnum:anchor];
}

+ (RARERenderTypeEnum *)getComplementarySideAnchorWithRARERenderTypeEnum:(RARERenderTypeEnum *)anchor
                                                    withRARELocationEnum:(RARELocationEnum *)parentLocation {
  switch ([anchor ordinal]) {
    case RARERenderType_UPPER_RIGHT:
    switch ([parentLocation ordinal]) {
      case RARELocation_TOP:
      return [RARERenderTypeEnum LOWER_RIGHT];
      case RARELocation_RIGHT:
      return [RARERenderTypeEnum LOWER_LEFT];
      default:
      return anchor;
    }
    case RARERenderType_LOWER_LEFT:
    switch ([parentLocation ordinal]) {
      case RARELocation_TOP:
      return [RARERenderTypeEnum UPPER_LEFT];
      case RARELocation_RIGHT:
      return [RARERenderTypeEnum UPPER_RIGHT];
      default:
      return anchor;
    }
    case RARERenderType_UPPER_LEFT:
    switch ([parentLocation ordinal]) {
      case RARELocation_TOP:
      return [RARERenderTypeEnum LOWER_LEFT];
      case RARELocation_LEFT:
      return [RARERenderTypeEnum LOWER_RIGHT];
      default:
      return anchor;
    }
    case RARERenderType_LOWER_RIGHT:
    switch ([parentLocation ordinal]) {
      case RARELocation_TOP:
      return [RARERenderTypeEnum UPPER_LEFT];
      case RARELocation_LEFT:
      return [RARERenderTypeEnum UPPER_RIGHT];
      default:
      return anchor;
    }
    case RARERenderType_LOWER_MIDDLE:
    switch ([parentLocation ordinal]) {
      case RARELocation_TOP:
      return [RARERenderTypeEnum UPPER_MIDDLE];
      case RARELocation_LEFT:
      return [RARERenderTypeEnum LEFT_MIDDLE];
      case RARELocation_RIGHT:
      return [RARERenderTypeEnum RIGHT_MIDDLE];
      default:
      return anchor;
    }
    case RARERenderType_UPPER_MIDDLE:
    switch ([parentLocation ordinal]) {
      case RARELocation_TOP:
      return [RARERenderTypeEnum LOWER_MIDDLE];
      case RARELocation_LEFT:
      return [RARERenderTypeEnum RIGHT_MIDDLE];
      case RARELocation_RIGHT:
      return [RARERenderTypeEnum LEFT_MIDDLE];
      default:
      return anchor;
    }
    case RARERenderType_RIGHT_MIDDLE:
    switch ([parentLocation ordinal]) {
      case RARELocation_TOP:
      return [RARERenderTypeEnum LEFT_MIDDLE];
      case RARELocation_LEFT:
      return [RARERenderTypeEnum LOWER_MIDDLE];
      case RARELocation_RIGHT:
      return [RARERenderTypeEnum UPPER_MIDDLE];
      default:
      return anchor;
    }
    case RARERenderType_LEFT_MIDDLE:
    switch ([parentLocation ordinal]) {
      case RARELocation_TOP:
      return [RARERenderTypeEnum RIGHT_MIDDLE];
      case RARELocation_LEFT:
      return [RARERenderTypeEnum UPPER_MIDDLE];
      case RARELocation_RIGHT:
      return [RARERenderTypeEnum LOWER_MIDDLE];
      default:
      return anchor;
    }
    default:
    return anchor;
  }
}

- (void)animateWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c
                                   withId:(id)postAnimateAction {
  [self setSizingValuesWithRAREiPlatformComponent:c];
  [super animateWithRAREiPlatformComponent:c withId:postAnimateAction];
}

- (void)animateToWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c
                        withRAREUIRectangle:(RAREUIRectangle *)to {
  RAREUIRectangle *from = [((id<RAREiPlatformComponent>) nil_chk(c)) getBounds];
  boundsChanger_ = [[RAREaAnimator_BoundsChanger alloc] initWithRAREUIRectangle:from withRAREUIRectangle:to];
  width_ = ((RAREUIRectangle *) nil_chk(to))->width_;
  height_ = to->height_;
  [super animateWithRAREiPlatformComponent:c withId:nil];
}

- (void)dispose {
  if (outComponent_ != nil) {
    [self setAnimatingPropertyWithRAREiPlatformComponent:outComponent_ withBoolean:NO];
  }
  if (inComponent_ != nil) {
    [self setAnimatingPropertyWithRAREiPlatformComponent:inComponent_ withBoolean:NO];
  }
  if (animationComponent_ != nil) {
    [self setAnimatingPropertyWithRAREiPlatformComponent:animationComponent_ withBoolean:NO];
    [animationComponent_ dispose];
    animationComponent_ = nil;
  }
  outComponent_ = nil;
  inComponent_ = nil;
  [super dispose];
}

- (void)handlePropertiesWithJavaUtilMap:(id<JavaUtilMap>)map {
  [super handlePropertiesWithJavaUtilMap:map];
  if (map == nil) {
    return;
  }
  id o = [((id<JavaUtilMap>) nil_chk(map)) getWithId:@"horizontal"];
  if ([o isKindOfClass:[JavaLangBoolean class]]) {
    [self setHorizontalWithBoolean:[((JavaLangBoolean *) check_class_cast(o, [JavaLangBoolean class])) booleanValue]];
  }
  else if ([o isKindOfClass:[NSString class]]) {
    [self setHorizontalWithBoolean:[@"true" equalsIgnoreCase:(NSString *) check_class_cast(o, [NSString class])]];
  }
  o = [map getWithId:@"diagonal"];
  if ([o isKindOfClass:[JavaLangBoolean class]]) {
    diagonal_ = [((JavaLangBoolean *) check_class_cast(o, [JavaLangBoolean class])) booleanValue];
  }
  else if ([o isKindOfClass:[NSString class]]) {
    diagonal_ = [@"true" equalsIgnoreCase:(NSString *) check_class_cast(o, [NSString class])];
  }
  o = [map getWithId:@"diagonalAnchor"];
  if (o != nil) {
    [self setDiagonalAnchorWithId:o];
  }
}

- (void)reset {
  outComponent_ = nil;
  inComponent_ = nil;
}

- (void)setDiagonalWithBoolean:(BOOL)diagonal {
  self->diagonal_ = diagonal;
}

- (void)setDiagonalAnchorWithId:(id)anchor {
  RARERenderTypeEnum *a = nil;
  if ([anchor isKindOfClass:[RARERenderTypeEnum class]]) {
    a = (RARERenderTypeEnum *) anchor;
  }
  else if ([anchor isKindOfClass:[NSString class]]) {
    @try {
      a = [RARERenderTypeEnum valueOfWithNSString:[((NSString *) check_class_cast(anchor, [NSString class])) uppercaseStringWithJRELocale:[JavaUtilLocale US]]];
    }
    @catch (JavaLangException *e) {
      [RAREPlatform ignoreExceptionWithNSString:nil withJavaLangThrowable:e];
    }
  }
  if (a != nil) {
    self->diagonalAnchor_ = a;
  }
}

- (void)setFadeInWithBoolean:(BOOL)fade {
  self->fadeIn_ = fade;
}

- (void)setFadeOutWithBoolean:(BOOL)fade {
  self->fadeOut_ = fade;
}

- (void)setHorizontalWithBoolean:(BOOL)horizontal {
  self->horizontal_ = horizontal;
}

- (id<RAREiPlatformComponent>)setIncommingComponentWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)inComponent {
  self->inComponent_ = inComponent;
  if (direction_ == [RAREiAnimator_DirectionEnum FORWARD]) {
    [self setViewEventsEnabledWithRAREiPlatformComponent:inComponent withBoolean:NO];
    [((id<RAREiPlatformComponent>) nil_chk(inComponent)) removeFromParent];
    [((RAREAnimationComponent *) nil_chk(animationComponent_)) setComponentWithRAREiPlatformComponent:inComponent withFloat:(int) width_ withFloat:(int) height_];
    self->inComponent_ = animationComponent_;
  }
  return self->inComponent_;
}

- (void)setOutgoingComponentWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)outComponent
                                       withRAREUIImage:(RAREUIImage *)outImage {
  [super setOutgoingComponentWithRAREiPlatformComponent:outComponent withRAREUIImage:outImage];
  if (direction_ == [RAREiAnimator_DirectionEnum FORWARD]) {
    if (animationComponent_ == nil) {
      animationComponent_ = [RAREaPlatformHelper createAnimationComponentWithRAREiWidget:[((id<RAREiPlatformComponent>) nil_chk(outComponent)) getWidget]];
      [self setAnimatingPropertyWithRAREiPlatformComponent:animationComponent_ withBoolean:YES];
    }
  }
  width_ = [((RAREUIImage *) nil_chk(outImage)) getWidth];
  height_ = [outImage getHeight];
  self->outComponent_ = outComponent;
}

- (void)setPercentWithFloat:(float)percent {
  self->percent_ = percent;
}

- (void)setTransitionTypeWithRAREiTransitionSupport_TransitionTypeEnum:(RAREiTransitionSupport_TransitionTypeEnum *)type {
  horizontal_ = type == [RAREiTransitionSupport_TransitionTypeEnum HORIZONTAL];
}

- (RARERenderTypeEnum *)getDiagonalAnchor {
  return diagonal_ ? diagonalAnchor_ : nil;
}

- (RAREiTransitionSupport_TransitionTypeEnum *)getTransitionType {
  return [RAREiTransitionSupport_TransitionTypeEnum STACK];
}

- (BOOL)isDiagonal {
  return diagonal_;
}

- (BOOL)isHorizontal {
  return horizontal_;
}

- (BOOL)isSizingAnimation {
  return YES;
}

- (void)clear {
  [super clear];
  if ((inComponent_ != nil) && ![inComponent_ isDisposed]) {
    [inComponent_ setAlphaWithFloat:1];
    [inComponent_ repaint];
  }
  outComponent_ = nil;
  inComponent_ = nil;
}

+ (void)updateBoundsForAnchorWithRAREaAnimator_BoundsChanger:(RAREaAnimator_BoundsChanger *)bc
                                      withRARERenderTypeEnum:(RARERenderTypeEnum *)da {
  float width;
  float height;
  RAREUIRectangle *rect;
  BOOL small = [((RAREaAnimator_BoundsChanger *) nil_chk(bc)) isSmaller];
  if (small) {
    width = ((RAREUIRectangle *) nil_chk(bc->from_))->width_;
    height = bc->from_->height_;
    rect = bc->to_;
  }
  else {
    rect = bc->from_;
    width = ((RAREUIRectangle *) nil_chk(bc->to_))->width_;
    height = bc->to_->height_;
  }
  switch ([da ordinal]) {
    case RARERenderType_UPPER_RIGHT:
    ((RAREUIRectangle *) nil_chk(rect))->x_ += width;
    break;
    case RARERenderType_LOWER_LEFT:
    ((RAREUIRectangle *) nil_chk(rect))->y_ += height;
    break;
    case RARERenderType_LOWER_RIGHT:
    ((RAREUIRectangle *) nil_chk(rect))->x_ += width;
    rect->y_ += height;
    break;
    case RARERenderType_LOWER_MIDDLE:
    ((RAREUIRectangle *) nil_chk(rect))->x_ += width / 2 + rect->x_;
    rect->y_ += height;
    break;
    case RARERenderType_UPPER_MIDDLE:
    ((RAREUIRectangle *) nil_chk(rect))->x_ += width / 2 + rect->x_;
    break;
    case RARERenderType_RIGHT_MIDDLE:
    ((RAREUIRectangle *) nil_chk(rect))->x_ += width;
    rect->y_ += height / 2 + rect->y_;
    break;
    case RARERenderType_LEFT_MIDDLE:
    ((RAREUIRectangle *) nil_chk(rect))->y_ += height / 2 + rect->y_;
    break;
    case RARERenderType_UPPER_LEFT:
    break;
    default:
    ((RAREUIRectangle *) nil_chk(rect))->x_ += width / 2 + rect->x_;
    rect->y_ += height / 2 + rect->y_;
    break;
  }
  [bc updateDiffs];
}

- (void)updateWithFloat:(float)fraction {
  float width = [((RAREaAnimator_BoundsChanger *) nil_chk(boundsChanger_)) getWidthWithFloat:fraction];
  float height = [boundsChanger_ getHeightWithFloat:fraction];
  float x = [boundsChanger_ getXWithFloat:fraction];
  float y = [boundsChanger_ getYWithFloat:fraction];
  BOOL small = direction_ == [RAREiAnimator_DirectionEnum BACKWARD];
  [self updateComponentWithFloat:x withFloat:y withFloat:width withFloat:height withBoolean:small withFloat:fraction];
}

- (void)updateComponentWithFloat:(float)x
                       withFloat:(float)y
                       withFloat:(float)width
                       withFloat:(float)height
                     withBoolean:(BOOL)small
                       withFloat:(float)fraction {
  if (outComponent_ != nil) {
    if (small) {
      [outComponent_ setBoundsWithFloat:x withFloat:y withFloat:width withFloat:height];
      [outComponent_ repaint];
    }
    else {
      [((id<RAREiPlatformComponent>) nil_chk(inComponent_)) setBoundsWithFloat:x withFloat:y withFloat:width withFloat:height];
    }
    if (fadeIn_) {
      [((id<RAREiPlatformComponent>) nil_chk(inComponent_)) setAlphaWithFloat:small ? 1 - fraction : fraction];
      [inComponent_ repaint];
    }
    if (fadeOut_ && (outComponent_ != nil)) {
      [outComponent_ setAlphaWithFloat:1 - fraction];
      [outComponent_ repaint];
    }
  }
  else {
    [((id<RAREiPlatformComponent>) nil_chk(component_)) setBoundsWithFloat:x withFloat:y withFloat:width withFloat:height];
    [component_ repaint];
    BOOL fade = (fadeIn_ && !small) || (fadeOut_ && small);
    if (fade) {
      [component_ setAlphaWithFloat:small ? 1 - fraction : fraction];
    }
  }
}

- (BOOL)isComponentDisposed {
  if (((component_ != nil) && [component_ isDisposed])) {
    return YES;
  }
  if (((inComponent_ != nil) && [inComponent_ isDisposed])) {
    return YES;
  }
  if (((outComponent_ != nil) && [outComponent_ isDisposed])) {
    return YES;
  }
  return NO;
}

- (void)setSizingValuesWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c {
  RAREUIRectangle *from = [((id<RAREiPlatformComponent>) nil_chk(c)) getBounds];
  RAREUIRectangle *to = [[RAREUIRectangle alloc] initWithRAREUIRectangle:from];
  BOOL small = direction_ == [RAREiAnimator_DirectionEnum BACKWARD];
  if (percent_ < 1) {
    to->width_ *= percent_;
    to->height_ *= percent_;
  }
  else {
    to->width_ = 0;
    to->height_ = 0;
  }
  if (small) {
    boundsChanger_ = [[RAREaAnimator_BoundsChanger alloc] initWithRAREUIRectangle:from withRAREUIRectangle:to];
  }
  else {
    boundsChanger_ = [[RAREaAnimator_BoundsChanger alloc] initWithRAREUIRectangle:to withRAREUIRectangle:from];
  }
  if (diagonal_ && (diagonalAnchor_ != nil)) {
    [RAREaSizeAnimation updateBoundsForAnchorWithRAREaAnimator_BoundsChanger:boundsChanger_ withRARERenderTypeEnum:diagonalAnchor_];
  }
  width_ = [c getWidth];
  height_ = [c getHeight];
}

- (void)copyAllFieldsTo:(RAREaSizeAnimation *)other {
  [super copyAllFieldsTo:other];
  other->animationComponent_ = animationComponent_;
  other->boundsChanger_ = boundsChanger_;
  other->diagonal_ = diagonal_;
  other->diagonalAnchor_ = diagonalAnchor_;
  other->height_ = height_;
  other->horizontal_ = horizontal_;
  other->inComponent_ = inComponent_;
  other->originalSize_ = originalSize_;
  other->outComponent_ = outComponent_;
  other->percent_ = percent_;
  other->width_ = width_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getComplementarySideAnchorWithRARERenderTypeEnum:withRARELocationEnum:", NULL, "LRARERenderTypeEnum", 0x9, NULL },
    { "setIncommingComponentWithRAREiPlatformComponent:", NULL, "LRAREiPlatformComponent", 0x1, NULL },
    { "getDiagonalAnchor", NULL, "LRARERenderTypeEnum", 0x1, NULL },
    { "getTransitionType", NULL, "LRAREiTransitionSupport_TransitionTypeEnum", 0x1, NULL },
    { "isDiagonal", NULL, "Z", 0x1, NULL },
    { "isHorizontal", NULL, "Z", 0x1, NULL },
    { "isSizingAnimation", NULL, "Z", 0x1, NULL },
    { "clear", NULL, "V", 0x4, NULL },
    { "updateWithFloat:", NULL, "V", 0x4, NULL },
    { "updateComponentWithFloat:withFloat:withFloat:withFloat:withBoolean:withFloat:", NULL, "V", 0x4, NULL },
    { "isComponentDisposed", NULL, "Z", 0x4, NULL },
    { "setSizingValuesWithRAREiPlatformComponent:", NULL, "V", 0x4, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "horizontal_", NULL, 0x4, "Z" },
    { "percent_", NULL, 0x4, "F" },
    { "diagonalAnchor_", NULL, 0x4, "LRARERenderTypeEnum" },
    { "diagonal_", NULL, 0x4, "Z" },
    { "animationComponent_", NULL, 0x4, "LRAREAnimationComponent" },
    { "height_", NULL, 0x4, "F" },
    { "inComponent_", NULL, 0x4, "LRAREiPlatformComponent" },
    { "originalSize_", NULL, 0x4, "F" },
    { "outComponent_", NULL, 0x4, "LRAREiPlatformComponent" },
    { "width_", NULL, 0x4, "F" },
    { "boundsChanger_", NULL, 0x4, "LRAREaAnimator_BoundsChanger" },
  };
  static J2ObjcClassInfo _RAREaSizeAnimation = { "aSizeAnimation", "com.appnativa.rare.ui.effects", NULL, 0x1, 12, methods, 11, fields, 0, NULL};
  return &_RAREaSizeAnimation;
}

@end
