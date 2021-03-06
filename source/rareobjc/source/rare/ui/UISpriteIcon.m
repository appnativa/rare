//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/UISpriteIcon.java
//
//  Created by decoteaud on 3/11/16.
//

#include "IOSClass.h"
#include "IOSObjectArray.h"
#include "com/appnativa/rare/Platform.h"
#include "com/appnativa/rare/platform/PlatformHelper.h"
#include "com/appnativa/rare/ui/ScreenUtils.h"
#include "com/appnativa/rare/ui/UIImage.h"
#include "com/appnativa/rare/ui/UIImageIcon.h"
#include "com/appnativa/rare/ui/UIRectangle.h"
#include "com/appnativa/rare/ui/UISpriteIcon.h"
#include "com/appnativa/rare/ui/effects/ValueRangeAnimator.h"
#include "com/appnativa/rare/ui/effects/iPlatformAnimator.h"
#include "com/appnativa/rare/ui/event/ChangeEvent.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "com/appnativa/rare/ui/iPlatformGraphics.h"
#include "com/appnativa/rare/ui/iPlatformIcon.h"
#include "java/lang/CloneNotSupportedException.h"
#include "java/lang/InternalError.h"
#include "java/lang/Math.h"

@implementation RAREUISpriteIcon

- (id)initWithRAREUIImage:(RAREUIImage *)sprite {
  return [self initRAREUISpriteIconWithRAREUIImage:sprite withBoolean:YES withInt:[((RAREUIImage *) nil_chk(sprite)) getHeight] withInt:2000 withBoolean:NO withBoolean:YES];
}

- (void)dispose {
  if (animator_ != nil) {
    [animator_ removeListenerWithRAREiAnimatorListener:self];
    [animator_ removeValueListenerWithRAREiAnimatorValueListener:self];
    [animator_ stop];
  }
  id<RAREiPlatformComponent> c = component_;
  if ((c != nil) && ![c isDisposed]) {
    [c removeViewListenerWithRAREiViewListener:self];
  }
  component_ = nil;
  sprite_ = nil;
  icons_ = nil;
}

- (id)initWithRAREUIImage:(RAREUIImage *)sprite
              withBoolean:(BOOL)horizontal {
  return [self initRAREUISpriteIconWithRAREUIImage:sprite withBoolean:horizontal withInt:horizontal ? [((RAREUIImage *) nil_chk(sprite)) getHeight] : [((RAREUIImage *) nil_chk(sprite)) getWidth] withInt:200 withBoolean:NO withBoolean:YES];
}

- (id)initRAREUISpriteIconWithRAREUIImage:(RAREUIImage *)sprite
                              withBoolean:(BOOL)horizontal
                                  withInt:(int)size
                                  withInt:(int)duration
                              withBoolean:(BOOL)reverse
                              withBoolean:(BOOL)adjustForDensity {
  if (self = [super init]) {
    self->horizontal_ = horizontal;
    self->sprite_ = sprite;
    if (horizontal) {
      iconWidth_ = size;
      iconHeight_ = [((RAREUIImage *) nil_chk(sprite)) getHeight];
      iconCount_ = [sprite getWidth] / size;
      destRect_ = [[RAREUIRectangle alloc] initWithFloat:0 withFloat:0 withFloat:size withFloat:iconHeight_];
    }
    else {
      iconHeight_ = size;
      iconWidth_ = [((RAREUIImage *) nil_chk(sprite)) getWidth];
      iconCount_ = [sprite getHeight] / size;
      destRect_ = [[RAREUIRectangle alloc] initWithFloat:0 withFloat:0 withFloat:iconWidth_ withFloat:size];
    }
    srcRect_ = [[RAREUIRectangle alloc] initWithRAREUIRectangle:destRect_];
    if (adjustForDensity && [RAREPlatform isIOS]) {
      float density = [RAREScreenUtils getDensity];
      srcRect_->width_ *= density;
      srcRect_->height_ *= density;
    }
    [self createAnimatorWithInt:duration withBoolean:reverse];
  }
  return self;
}

- (id)initWithRAREUIImage:(RAREUIImage *)sprite
              withBoolean:(BOOL)horizontal
                  withInt:(int)size
                  withInt:(int)duration
              withBoolean:(BOOL)reverse
              withBoolean:(BOOL)adjustForDensity {
  return [self initRAREUISpriteIconWithRAREUIImage:sprite withBoolean:horizontal withInt:size withInt:duration withBoolean:reverse withBoolean:adjustForDensity];
}

- (id)initWithRAREiPlatformIconArray:(IOSObjectArray *)icons
                             withInt:(int)duration
                         withBoolean:(BOOL)reverse {
  if (self = [super init]) {
    self->icons_ = icons;
    {
      IOSObjectArray *a__ = icons;
      id const *b__ = ((IOSObjectArray *) nil_chk(a__))->buffer_;
      id const *e__ = b__ + a__->size_;
      while (b__ < e__) {
        id<RAREiPlatformIcon> ic = (*b__++);
        iconWidth_ = [JavaLangMath maxWithInt:iconWidth_ withInt:[((id<RAREiPlatformIcon>) nil_chk(ic)) getIconWidth]];
        iconHeight_ = [JavaLangMath maxWithInt:iconHeight_ withInt:[ic getIconHeight]];
      }
    }
    [self createAnimatorWithInt:duration withBoolean:reverse];
  }
  return self;
}

- (RAREValueRangeAnimator *)getAnimator {
  return animator_;
}

- (void)createAnimatorWithInt:(int)duration
                  withBoolean:(BOOL)reverse {
  self->duration_ = duration;
  self->reverse_ = reverse;
  animator_ = [[RAREValueRangeAnimator alloc] initWithInt:0 withInt:iconCount_];
  [animator_ setDurationWithInt:duration];
  [animator_ setAutoReverseWithBoolean:reverse];
  [animator_ setRepeatCountWithInt:-1];
}

+ (RAREUISpriteIcon *)getDefaultSpinner {
  RAREUIImageIcon *icon = (RAREUIImageIcon *) check_class_cast([RAREPlatform getResourceAsIconWithNSString:@"Rare.icon.spinner"], [RAREUIImageIcon class]);
  return [[RAREUISpriteIcon alloc] initWithRAREUIImage:[((RAREUIImageIcon *) nil_chk(icon)) getImage] withBoolean:YES withInt:[icon getIconHeight] withInt:1000 withBoolean:NO withBoolean:YES];
}

- (id)clone {
  RAREUISpriteIcon *icon;
  @try {
    icon = (RAREUISpriteIcon *) check_class_cast([super clone], [RAREUISpriteIcon class]);
    ((RAREUISpriteIcon *) nil_chk(icon))->component_ = nil;
    [icon createAnimatorWithInt:duration_ withBoolean:reverse_];
    return icon;
  }
  @catch (JavaLangCloneNotSupportedException *e) {
    @throw [[JavaLangInternalError alloc] init];
  }
}

- (id<RAREiPlatformComponent>)getOwner {
  return component_;
}

- (void)setOwnerWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp {
  if (self->component_ != comp) {
    if (self->component_ != nil) {
      id<RAREiPlatformComponent> c = component_;
      component_ = nil;
      [c removeViewListenerWithRAREiViewListener:self];
      [((RAREValueRangeAnimator *) nil_chk(self->animator_)) stop];
    }
    self->component_ = comp;
    if (comp != nil) {
      [((RAREValueRangeAnimator *) nil_chk(animator_)) addListenerWithRAREiAnimatorListener:self];
      [animator_ addValueListenerWithRAREiAnimatorValueListener:self];
      [comp addViewListenerWithRAREiViewListener:self];
      if ([comp isShowing]) {
        [self->animator_ start];
      }
    }
    else {
      [((RAREValueRangeAnimator *) nil_chk(animator_)) removeListenerWithRAREiAnimatorListener:self];
      [animator_ removeValueListenerWithRAREiAnimatorValueListener:self];
    }
  }
}

- (int)getIconHeight {
  return iconHeight_;
}

- (int)getIconWidth {
  return iconWidth_;
}

- (id<RAREiPlatformIcon>)getDisabledVersion {
  int op = position_;
  position_ = iconCount_ - 1;
  id<RAREiPlatformIcon> ic = [RAREaPlatformHelper createDisabledIconWithRAREiPlatformIcon:self];
  position_ = op;
  return ic;
}

- (void)paintWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                             withFloat:(float)x
                             withFloat:(float)y
                             withFloat:(float)width
                             withFloat:(float)height {
  if (sprite_ != nil) {
    ((RAREUIRectangle *) nil_chk(destRect_))->x_ = x;
    destRect_->y_ = y;
    if (horizontal_) {
      ((RAREUIRectangle *) nil_chk(srcRect_))->x_ = position_ * srcRect_->width_;
    }
    else {
      ((RAREUIRectangle *) nil_chk(srcRect_))->y_ = position_ * srcRect_->height_;
    }
    [((id<RAREiPlatformGraphics>) nil_chk(g)) drawImageWithRAREiPlatformImage:sprite_ withRAREUIRectangle:srcRect_ withRAREUIRectangle:destRect_ withRAREiImagePainter_ScalingTypeEnum:nil withRAREiComposite:nil];
  }
  else if (icons_ != nil) {
    id<RAREiPlatformIcon> ic = IOSObjectArray_Get(icons_, position_);
    x += (iconWidth_ - [((id<RAREiPlatformIcon>) nil_chk(ic)) getIconWidth]) / 2;
    y += (iconHeight_ - [ic getIconHeight]) / 2;
    [ic paintWithRAREiPlatformGraphics:g withFloat:x withFloat:y withFloat:iconWidth_ withFloat:iconHeight_];
  }
}

- (void)valueChangedWithRAREiPlatformAnimator:(id<RAREiPlatformAnimator>)animator
                                    withFloat:(float)value {
  position_ = (int) value;
  if (position_ >= iconCount_) {
    position_ = iconCount_;
  }
  id<RAREiPlatformComponent> c = component_;
  if ((c != nil) && ![c isDisposed] && [c isShowing]) {
    if ([c isEnabled]) {
      [c repaint];
    }
  }
  else {
    [((RAREValueRangeAnimator *) nil_chk(self->animator_)) stop];
  }
}

- (void)animationEndedWithRAREiPlatformAnimator:(id<RAREiPlatformAnimator>)animator {
  if ((animator != nil) && (component_ != nil) && [component_ isShowing]) {
    if (!frozen_) {
      if (!reverse_) {
        position_ = 0;
      }
      [((RAREValueRangeAnimator *) nil_chk(self->animator_)) start];
    }
  }
}

- (void)animationStartedWithRAREiPlatformAnimator:(id<RAREiPlatformAnimator>)animator {
}

- (void)viewResizedWithRAREChangeEvent:(RAREChangeEvent *)e {
}

- (void)viewHiddenWithRAREChangeEvent:(RAREChangeEvent *)e {
  if (animator_ != nil) {
    [self->animator_ stop];
  }
}

- (void)viewShownWithRAREChangeEvent:(RAREChangeEvent *)e {
  if (!frozen_) {
    position_ = 0;
    if (animator_ != nil) {
      [self->animator_ start];
    }
  }
}

- (BOOL)wantsResizeEvent {
  return NO;
}

- (BOOL)isFrozen {
  return frozen_;
}

- (void)setFrozenWithBoolean:(BOOL)frozen {
  self->frozen_ = frozen;
  if (animator_ != nil) {
    if (frozen) {
      [self->animator_ stop];
    }
    else if (![animator_ isRunning]) {
      if ((component_ != nil) && [component_ isShowing]) {
        [self->animator_ start];
      }
    }
  }
}

- (id)copyWithZone:(NSZone *)zone {
  return [self clone];
}

- (void)copyAllFieldsTo:(RAREUISpriteIcon *)other {
  [super copyAllFieldsTo:other];
  other->animator_ = animator_;
  other->component_ = component_;
  other->destRect_ = destRect_;
  other->duration_ = duration_;
  other->frozen_ = frozen_;
  other->horizontal_ = horizontal_;
  other->iconCount_ = iconCount_;
  other->iconHeight_ = iconHeight_;
  other->iconWidth_ = iconWidth_;
  other->icons_ = icons_;
  other->position_ = position_;
  other->reverse_ = reverse_;
  other->sprite_ = sprite_;
  other->srcRect_ = srcRect_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getAnimator", NULL, "LRAREValueRangeAnimator", 0x1, NULL },
    { "createAnimatorWithInt:withBoolean:", NULL, "V", 0x4, NULL },
    { "getDefaultSpinner", NULL, "LRAREUISpriteIcon", 0x9, NULL },
    { "clone", NULL, "LNSObject", 0x1, NULL },
    { "getOwner", NULL, "LRAREiPlatformComponent", 0x1, NULL },
    { "getDisabledVersion", NULL, "LRAREiPlatformIcon", 0x1, NULL },
    { "wantsResizeEvent", NULL, "Z", 0x1, NULL },
    { "isFrozen", NULL, "Z", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "icons_", NULL, 0x4, "LIOSObjectArray" },
    { "animator_", NULL, 0x4, "LRAREValueRangeAnimator" },
    { "reverse_", NULL, 0x4, "Z" },
    { "duration_", NULL, 0x4, "I" },
    { "position_", NULL, 0x4, "I" },
    { "iconWidth_", NULL, 0x4, "I" },
    { "iconHeight_", NULL, 0x4, "I" },
    { "iconCount_", NULL, 0x4, "I" },
    { "component_", NULL, 0x4, "LRAREiPlatformComponent" },
    { "sprite_", NULL, 0x4, "LRAREUIImage" },
    { "srcRect_", NULL, 0x4, "LRAREUIRectangle" },
    { "destRect_", NULL, 0x4, "LRAREUIRectangle" },
    { "horizontal_", NULL, 0x4, "Z" },
    { "frozen_", NULL, 0x4, "Z" },
  };
  static J2ObjcClassInfo _RAREUISpriteIcon = { "UISpriteIcon", "com.appnativa.rare.ui", NULL, 0x1, 8, methods, 14, fields, 0, NULL};
  return &_RAREUISpriteIcon;
}

@end
