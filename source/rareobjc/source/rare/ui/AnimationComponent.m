//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/AnimationComponent.java
//
//  Created by decoteaud on 3/11/16.
//

#include "com/appnativa/rare/ui/AnimationComponent.h"
#include "com/appnativa/rare/ui/Component.h"
#include "com/appnativa/rare/ui/Container.h"
#include "com/appnativa/rare/ui/UIDimension.h"
#include "com/appnativa/rare/ui/UIImage.h"
#include "com/appnativa/rare/ui/effects/iAnimationImageView.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "java/lang/Math.h"

@implementation RAREAnimationComponent

- (id)initWithRAREiAnimationImageView:(id<RAREiAnimationImageView>)view {
  if (self = [super initWithId:view]) {
    size_ = [[RAREUIDimension alloc] init];
    forward_ = YES;
    imageView_ = view;
  }
  return self;
}

- (void)layoutContainerWithFloat:(float)width
                       withFloat:(float)height {
  [self setInitialLayoutWithFloat:((RAREUIDimension *) nil_chk(size_))->width_ withFloat:size_->height_];
}

- (void)removeAll {
  [super removeAll];
  inComponent_ = nil;
  outComponent_ = nil;
}

- (void)setComponentWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)inComponent
                                     withFloat:(float)width
                                     withFloat:(float)height {
  [self removeAll];
  stacked_ = YES;
  self->inComponent_ = inComponent;
  [((RAREUIDimension *) nil_chk(size_)) setSizeWithDouble:width withDouble:height];
  if (minSize_ != nil) {
    [minSize_ setSizeWithInt:0 withInt:0];
  }
  [self addWithRAREiPlatformComponent:inComponent];
  [((id<RAREiPlatformComponent>) nil_chk(inComponent)) setBoundsWithFloat:0 withFloat:0 withFloat:width withFloat:height];
}

- (void)setImageWithRAREUIImage:(RAREUIImage *)image {
  [((id<RAREiAnimationImageView>) nil_chk(imageView_)) setImageWithRAREUIImage:image];
  if (minSize_ != nil) {
    [minSize_ setSizeWithInt:0 withInt:0];
  }
  if (image != nil) {
    [((RAREUIDimension *) nil_chk(size_)) setSizeWithInt:[image getWidth] withInt:[image getHeight]];
  }
}

- (void)setMinimumSizeWithRAREUIDimension:(RAREUIDimension *)minimumSize {
  if (!useBoundsForPreferredSize_) {
    if (minSize_ == nil) {
      minSize_ = [[RAREUIDimension alloc] init];
    }
    [((RAREUIDimension *) nil_chk(minSize_)) setSizeWithRAREUIDimension:minimumSize];
  }
}

- (void)setSideBySideComponentsWithBoolean:(BOOL)forward
                               withBoolean:(BOOL)horizontal
                withRAREiPlatformComponent:(id<RAREiPlatformComponent>)outComponent
                withRAREiPlatformComponent:(id<RAREiPlatformComponent>)inComponent
                                   withInt:(int)width
                                   withInt:(int)height
                               withBoolean:(BOOL)singleAnimator {
  [self removeAll];
  stacked_ = NO;
  self->forward_ = forward;
  self->singleAnimator_ = singleAnimator;
  self->horizontal_ = horizontal;
  self->outComponent_ = outComponent;
  self->inComponent_ = inComponent;
  [((RAREUIDimension *) nil_chk(size_)) setSizeWithInt:width withInt:height];
  if (inComponent != nil) {
    [self addWithRAREiPlatformComponent:inComponent];
  }
  if (outComponent != nil) {
    [self addWithRAREiPlatformComponent:outComponent];
  }
  [self setInitialLayoutWithFloat:width withFloat:height];
}

- (void)setStackedComponentsWithBoolean:(BOOL)forward
             withRAREiPlatformComponent:(id<RAREiPlatformComponent>)outComponent
             withRAREiPlatformComponent:(id<RAREiPlatformComponent>)inComponent
                                withInt:(int)width
                                withInt:(int)height {
  [self removeAll];
  stacked_ = YES;
  self->forward_ = forward;
  self->outComponent_ = outComponent;
  self->inComponent_ = inComponent;
  [((RAREUIDimension *) nil_chk(size_)) setSizeWithInt:width withInt:height];
  if (inComponent != nil) {
    [self addWithRAREiPlatformComponent:inComponent];
  }
  if (outComponent != nil) {
    [self addWithRAREiPlatformComponent:outComponent];
  }
  if (forward) {
    if (inComponent != nil) {
      [inComponent bringToFront];
    }
  }
  else {
    if (outComponent != nil) {
      [outComponent bringToFront];
    }
  }
  [self setInitialLayoutWithFloat:width withFloat:height];
}

- (void)setUseBoundsForPreferredSizeWithBoolean:(BOOL)useBoundsForPreferredSize {
  self->useBoundsForPreferredSize_ = useBoundsForPreferredSize;
}

- (RAREUIImage *)getImage {
  return [((id<RAREiAnimationImageView>) nil_chk(imageView_)) getImage];
}

- (id<RAREiPlatformComponent>)getInComponentAnClear {
  id<RAREiPlatformComponent> c = inComponent_;
  [self removeAll];
  return c;
}

- (BOOL)isUseBoundsForPreferredSize {
  return useBoundsForPreferredSize_;
}

- (void)setInitialLayoutWithFloat:(float)width
                        withFloat:(float)height {
  if (minSize_ != nil) {
    [minSize_ setSizeWithInt:0 withInt:0];
  }
  if (stacked_) {
    if (inComponent_ != nil) {
      [inComponent_ setBoundsWithFloat:0 withFloat:0 withFloat:width withFloat:height];
    }
    if (outComponent_ != nil) {
      [outComponent_ setBoundsWithFloat:0 withFloat:0 withFloat:width withFloat:height];
    }
  }
  else {
    RAREUIDimension *d = nil;
    if ([self isUseIncommingPreferredSize] && (inComponent_ != nil)) {
      d = [inComponent_ getPreferredSize];
      ((RAREUIDimension *) nil_chk(d))->width_ = [JavaLangMath minWithFloat:d->width_ withFloat:width];
      d->height_ = [JavaLangMath minWithFloat:d->width_ withFloat:height];
    }
    if (horizontal_) {
      if (outComponent_ != nil) {
        if (forward_) {
          [outComponent_ setBoundsWithFloat:0 withFloat:0 withFloat:width withFloat:height];
        }
        else {
          [outComponent_ setBoundsWithFloat:width withFloat:0 withFloat:width withFloat:height];
        }
      }
      if (inComponent_ != nil) {
        if (forward_) {
          [inComponent_ setBoundsWithFloat:width withFloat:0 withFloat:(d == nil) ? width : d->width_ withFloat:height];
        }
        else {
          [inComponent_ setBoundsWithFloat:(d == nil) ? 0 : width - d->width_ withFloat:0 withFloat:(d == nil) ? width : d->width_ withFloat:height];
        }
      }
    }
    else {
      if (outComponent_ != nil) {
        if (forward_) {
          [outComponent_ setBoundsWithFloat:0 withFloat:0 withFloat:width withFloat:height];
        }
        else {
          [outComponent_ setBoundsWithFloat:0 withFloat:height withFloat:width withFloat:height];
        }
      }
      if (inComponent_ != nil) {
        if (forward_) {
          [inComponent_ setBoundsWithFloat:0 withFloat:height withFloat:width withFloat:(d == nil) ? height : d->height_];
        }
        else {
          [inComponent_ setBoundsWithFloat:0 withFloat:(d == nil) ? 0 : height - d->height_ withFloat:width withFloat:(d == nil) ? height : d->height_];
        }
      }
    }
  }
}

- (void)getMinimumSizeExWithRAREUIDimension:(RAREUIDimension *)size
                                  withFloat:(float)maxWidth {
  if (!useBoundsForPreferredSize_ && (minSize_ != nil)) {
    [((RAREUIDimension *) nil_chk(size)) setSizeWithRAREUIDimension:minSize_];
  }
  else {
    ((RAREUIDimension *) nil_chk(size))->width_ = 0;
    size->height_ = 0;
  }
}

- (void)getPreferredSizeExWithRAREUIDimension:(RAREUIDimension *)size
                                    withFloat:(float)maxWidth {
  if (useBoundsForPreferredSize_) {
    (void) [self getSizeWithRAREUIDimension:size];
  }
  else {
    [((RAREUIDimension *) nil_chk(size)) setSizeWithRAREUIDimension:self->size_];
  }
}

- (BOOL)isUseIncommingPreferredSize {
  return useIncommingPreferredSize_;
}

- (void)setUseIncommingPreferredSizeWithBoolean:(BOOL)useIncommingPreferredSize {
  self->useIncommingPreferredSize_ = useIncommingPreferredSize;
}

- (void)copyAllFieldsTo:(RAREAnimationComponent *)other {
  [super copyAllFieldsTo:other];
  other->forward_ = forward_;
  other->horizontal_ = horizontal_;
  other->imageView_ = imageView_;
  other->inComponent_ = inComponent_;
  other->minSize_ = minSize_;
  other->outComponent_ = outComponent_;
  other->singleAnimator_ = singleAnimator_;
  other->size_ = size_;
  other->stacked_ = stacked_;
  other->useBoundsForPreferredSize_ = useBoundsForPreferredSize_;
  other->useIncommingPreferredSize_ = useIncommingPreferredSize_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getImage", NULL, "LRAREUIImage", 0x1, NULL },
    { "getInComponentAnClear", NULL, "LRAREiPlatformComponent", 0x1, NULL },
    { "isUseBoundsForPreferredSize", NULL, "Z", 0x1, NULL },
    { "setInitialLayoutWithFloat:withFloat:", NULL, "V", 0x4, NULL },
    { "getMinimumSizeExWithRAREUIDimension:withFloat:", NULL, "V", 0x4, NULL },
    { "getPreferredSizeExWithRAREUIDimension:withFloat:", NULL, "V", 0x4, NULL },
    { "isUseIncommingPreferredSize", NULL, "Z", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "size_", NULL, 0x0, "LRAREUIDimension" },
    { "horizontal_", NULL, 0x0, "Z" },
    { "imageView_", NULL, 0x0, "LRAREiAnimationImageView" },
    { "singleAnimator_", NULL, 0x4, "Z" },
    { "stacked_", NULL, 0x4, "Z" },
  };
  static J2ObjcClassInfo _RAREAnimationComponent = { "AnimationComponent", "com.appnativa.rare.ui", NULL, 0x1, 7, methods, 5, fields, 0, NULL};
  return &_RAREAnimationComponent;
}

@end
