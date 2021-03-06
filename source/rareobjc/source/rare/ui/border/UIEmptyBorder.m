//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/ui/border/UIEmptyBorder.java
//
//  Created by decoteaud on 3/11/16.
//

#include "com/appnativa/rare/ui/UIInsets.h"
#include "com/appnativa/rare/ui/border/UIEmptyBorder.h"
#include "com/appnativa/rare/ui/iPlatformBorder.h"
#include "com/appnativa/rare/ui/iPlatformGraphics.h"

@implementation RAREUIEmptyBorder

- (id)initWithFloat:(float)size {
  if (self = [super init]) {
    adjustForOrientation_ = NO;
    insets_ = [[RAREUIInsets alloc] init];
    (void) [insets_ setWithDouble:size withDouble:size withDouble:size withDouble:size];
  }
  return self;
}

- (id)initWithRAREUIInsets:(RAREUIInsets *)insets {
  return [self initRAREUIEmptyBorderWithRAREUIInsets:insets withBoolean:NO];
}

- (id)initRAREUIEmptyBorderWithRAREUIInsets:(RAREUIInsets *)insets
                                withBoolean:(BOOL)adjustForOrientation {
  if (self = [super init]) {
    adjustForOrientation_ = NO;
    insets_ = [[RAREUIInsets alloc] init];
    if (insets != nil) {
      [self setInsetsWithRAREUIInsets:insets];
    }
    self->adjustForOrientation_ = adjustForOrientation;
  }
  return self;
}

- (id)initWithRAREUIInsets:(RAREUIInsets *)insets
               withBoolean:(BOOL)adjustForOrientation {
  return [self initRAREUIEmptyBorderWithRAREUIInsets:insets withBoolean:adjustForOrientation];
}

- (id)initWithFloat:(float)top
          withFloat:(float)right
          withFloat:(float)bottom
          withFloat:(float)left {
  return [self initRAREUIEmptyBorderWithFloat:top withFloat:right withFloat:bottom withFloat:left withBoolean:NO];
}

- (id)initRAREUIEmptyBorderWithFloat:(float)top
                           withFloat:(float)right
                           withFloat:(float)bottom
                           withFloat:(float)left
                         withBoolean:(BOOL)adjustForOrientation {
  if (self = [super init]) {
    adjustForOrientation_ = NO;
    insets_ = [[RAREUIInsets alloc] init];
    [self setInsetsWithRAREUIInsets:[[RAREUIInsets alloc] initWithDouble:top withDouble:right withDouble:bottom withDouble:left]];
    self->adjustForOrientation_ = adjustForOrientation;
  }
  return self;
}

- (id)initWithFloat:(float)top
          withFloat:(float)right
          withFloat:(float)bottom
          withFloat:(float)left
        withBoolean:(BOOL)adjustForOrientation {
  return [self initRAREUIEmptyBorderWithFloat:top withFloat:right withFloat:bottom withFloat:left withBoolean:adjustForOrientation];
}

- (void)setAdjustForOrientationWithBoolean:(BOOL)adjustForOrientation {
  self->adjustForOrientation_ = adjustForOrientation;
  modCount_++;
}

- (void)setInsetsWithRAREUIInsets:(RAREUIInsets *)borderInsets {
  (void) [((RAREUIInsets *) nil_chk(insets_)) setWithRAREUIInsets:borderInsets];
  modCount_++;
}

- (void)setInsetsWithInt:(int)top
                 withInt:(int)right
                 withInt:(int)bottom
                 withInt:(int)left {
  (void) [((RAREUIInsets *) nil_chk(insets_)) setWithInt:top withInt:right withInt:bottom withInt:left];
}

- (BOOL)isAdjustForOrientation {
  return adjustForOrientation_;
}

- (BOOL)isPaintLast {
  return YES;
}

- (void)resetFromRealBorderWithRAREiPlatformBorder:(id<RAREiPlatformBorder>)b {
  if (b == nil) {
    (void) [((RAREUIInsets *) nil_chk(insets_)) setWithInt:0 withInt:0 withInt:0 withInt:0];
  }
  else {
    insets_ = [b getBorderInsetsWithRAREUIInsets:insets_];
  }
}

- (void)clipWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                            withFloat:(float)x
                            withFloat:(float)y
                            withFloat:(float)width
                            withFloat:(float)height {
}

- (RAREUIInsets *)getBorderInsetsWithRAREUIInsets:(RAREUIInsets *)insets {
  if (insets == nil) {
    return [[RAREUIInsets alloc] initWithRAREUIInsets:self->insets_];
  }
  else {
    (void) [insets setWithRAREUIInsets:self->insets_];
    return insets;
  }
}

- (RAREUIInsets *)getBorderInsetsExWithRAREUIInsets:(RAREUIInsets *)insets {
  if (insets == nil) {
    insets = [[RAREUIInsets alloc] init];
  }
  else {
    (void) [insets setWithInt:0 withInt:0 withInt:0 withInt:0];
  }
  return insets;
}

- (BOOL)canUseMainLayer {
  return YES;
}

- (void)paintWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                             withFloat:(float)x
                             withFloat:(float)y
                             withFloat:(float)width
                             withFloat:(float)height
                           withBoolean:(BOOL)end {
}

- (void)copyAllFieldsTo:(RAREUIEmptyBorder *)other {
  [super copyAllFieldsTo:other];
  other->adjustForOrientation_ = adjustForOrientation_;
  other->insets_ = insets_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "isAdjustForOrientation", NULL, "Z", 0x1, NULL },
    { "isPaintLast", NULL, "Z", 0x1, NULL },
    { "getBorderInsetsWithRAREUIInsets:", NULL, "LRAREUIInsets", 0x1, NULL },
    { "getBorderInsetsExWithRAREUIInsets:", NULL, "LRAREUIInsets", 0x1, NULL },
    { "canUseMainLayer", NULL, "Z", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "insets_", NULL, 0x4, "LRAREUIInsets" },
  };
  static J2ObjcClassInfo _RAREUIEmptyBorder = { "UIEmptyBorder", "com.appnativa.rare.ui.border", NULL, 0x1, 5, methods, 1, fields, 0, NULL};
  return &_RAREUIEmptyBorder;
}

@end
