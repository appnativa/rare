//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/border/aUICompoundBorder.java
//
//  Created by decoteaud on 3/11/16.
//

#include "IOSClass.h"
#include "IOSObjectArray.h"
#include "com/appnativa/rare/ui/UIInsets.h"
#include "com/appnativa/rare/ui/border/aUICompoundBorder.h"
#include "com/appnativa/rare/ui/iPlatformBorder.h"
#include "com/appnativa/rare/ui/iPlatformGraphics.h"
#include "com/appnativa/rare/ui/iPlatformShape.h"
#include "java/lang/StringBuilder.h"

@implementation RAREaUICompoundBorder

- (id)initRAREaUICompoundBorder {
  return [self initRAREaUICompoundBorderWithRAREiPlatformBorderArray:[IOSObjectArray arrayWithLength:2 type:[IOSClass classWithProtocol:@protocol(RAREiPlatformBorder)]]];
}

- (id)init {
  return [self initRAREaUICompoundBorder];
}

- (id)initRAREaUICompoundBorderWithRAREiPlatformBorderArray:(IOSObjectArray *)borders {
  if (self = [super init]) {
    self->borders_ = borders;
  }
  return self;
}

- (id)initWithRAREiPlatformBorderArray:(IOSObjectArray *)borders {
  return [self initRAREaUICompoundBorderWithRAREiPlatformBorderArray:borders];
}

- (id)initWithRAREiPlatformBorder:(id<RAREiPlatformBorder>)outsideBorder
          withRAREiPlatformBorder:(id<RAREiPlatformBorder>)insideBorder {
  if (self = [self initRAREaUICompoundBorder]) {
    [self setOutsideBorderWithRAREiPlatformBorder:outsideBorder];
    [self setInsideBorderWithRAREiPlatformBorder:insideBorder];
  }
  return self;
}

- (void)clipWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                            withFloat:(float)x
                            withFloat:(float)y
                            withFloat:(float)width
                            withFloat:(float)height {
  [self paintOrClipWithRAREiPlatformGraphics:g withFloat:x withFloat:y withFloat:width withFloat:height withBoolean:NO withBoolean:NO];
}

- (BOOL)clipsContents {
  for (int i = 0; i < (int) [((IOSObjectArray *) nil_chk(borders_)) count]; i++) {
    if ((IOSObjectArray_Get(borders_, i) != nil) && [((id<RAREiPlatformBorder>) IOSObjectArray_Get(borders_, i)) clipsContents]) {
      return YES;
    }
  }
  return NO;
}

- (void)updateModCount {
  modCount_++;
}

- (void)paintWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                             withFloat:(float)x
                             withFloat:(float)y
                             withFloat:(float)width
                             withFloat:(float)height
                           withBoolean:(BOOL)end {
  if ([self isPaintLast] != end) {
    return;
  }
  [self paintOrClipWithRAREiPlatformGraphics:g withFloat:x withFloat:y withFloat:width withFloat:height withBoolean:YES withBoolean:end];
}

- (NSString *)description {
  JavaLangStringBuilder *sb = [[JavaLangStringBuilder alloc] init];
  (void) [sb appendWithNSString:@"UICompoundBorder {\n"];
  int n = 0;
  for (int i = 0; i < (int) [((IOSObjectArray *) nil_chk(borders_)) count]; i++) {
    if (IOSObjectArray_Get(borders_, i) != nil) {
      (void) [sb appendWithNSString:[((id<RAREiPlatformBorder>) IOSObjectArray_Get(borders_, i)) description]];
      n++;
    }
    if (n > 1) {
      (void) [sb appendWithNSString:@"\n"];
    }
  }
  (void) [sb appendWithNSString:@"}"];
  return [sb description];
}

- (void)setInsideBorderWithRAREiPlatformBorder:(id<RAREiPlatformBorder>)b {
  (void) IOSObjectArray_Set(borders_, (int) [((IOSObjectArray *) nil_chk(borders_)) count] - 1, b);
  modCount_++;
}

- (void)setOutsideBorderWithRAREiPlatformBorder:(id<RAREiPlatformBorder>)b {
  (void) IOSObjectArray_Set(nil_chk(borders_), 0, b);
  modCount_++;
}

- (void)setPadForArcWithBoolean:(BOOL)pad {
  if (IOSObjectArray_Get(nil_chk(borders_), 1) != nil) {
    [((id<RAREiPlatformBorder>) IOSObjectArray_Get(borders_, 1)) setPadForArcWithBoolean:pad];
  }
  if (IOSObjectArray_Get(borders_, 0) != nil) {
    [((id<RAREiPlatformBorder>) IOSObjectArray_Get(borders_, 0)) setPadForArcWithBoolean:pad];
  }
  modCount_++;
}

- (float)getArcHeight {
  if ((IOSObjectArray_Get(nil_chk(borders_), 1) != nil) && ![((id<RAREiPlatformBorder>) IOSObjectArray_Get(borders_, 1)) isRectangular]) {
    return [((id<RAREiPlatformBorder>) IOSObjectArray_Get(borders_, 1)) getArcHeight];
  }
  if (IOSObjectArray_Get(borders_, 0) != nil) {
    return [((id<RAREiPlatformBorder>) IOSObjectArray_Get(borders_, 0)) getArcHeight];
  }
  return 0;
}

- (float)getArcWidth {
  if ((IOSObjectArray_Get(nil_chk(borders_), 1) != nil) && ![((id<RAREiPlatformBorder>) IOSObjectArray_Get(borders_, 1)) isRectangular]) {
    return [((id<RAREiPlatformBorder>) IOSObjectArray_Get(borders_, 1)) getArcWidth];
  }
  if (IOSObjectArray_Get(borders_, 0) != nil) {
    return [((id<RAREiPlatformBorder>) IOSObjectArray_Get(borders_, 0)) getArcWidth];
  }
  return 0;
}

- (RAREUIInsets *)getBorderInsetsWithRAREUIInsets:(RAREUIInsets *)insets {
  int left = 0;
  int right = 0;
  int top = 0;
  int bottom = 0;
  int len = (int) [((IOSObjectArray *) nil_chk(borders_)) count];
  for (int i = 0; i < len; i++) {
    if (IOSObjectArray_Get(borders_, i) != nil) {
      insets = [((id<RAREiPlatformBorder>) IOSObjectArray_Get(borders_, i)) getBorderInsetsWithRAREUIInsets:insets];
      left += ((RAREUIInsets *) nil_chk(insets))->left_;
      top += insets->top_;
      right += insets->right_;
      bottom += insets->bottom_;
    }
  }
  if (insets == nil) {
    insets = [[RAREUIInsets alloc] initWithInt:top withInt:right withInt:bottom withInt:left];
  }
  else {
    (void) [insets setWithInt:top withInt:right withInt:bottom withInt:left];
  }
  return insets;
}

- (RAREUIInsets *)getBorderInsetsExWithRAREUIInsets:(RAREUIInsets *)insets {
  int left = 0;
  int right = 0;
  int top = 0;
  int bottom = 0;
  int len = (int) [((IOSObjectArray *) nil_chk(borders_)) count];
  id<RAREiPlatformBorder> b;
  id<RAREiPlatformBorder> lb = nil;
  for (int i = 0; i < len; i++) {
    b = IOSObjectArray_Get(borders_, i);
    if (b != nil) {
      if (lb == nil) {
        lb = b;
        continue;
      }
      insets = [((id<RAREiPlatformBorder>) nil_chk(lb)) getBorderInsetsWithRAREUIInsets:insets];
      left += ((RAREUIInsets *) nil_chk(insets))->left_;
      top += insets->top_;
      right += insets->right_;
      bottom += insets->bottom_;
      lb = b;
    }
  }
  if (lb != nil) {
    insets = [lb getBorderInsetsExWithRAREUIInsets:insets];
    left += ((RAREUIInsets *) nil_chk(insets))->left_;
    top += insets->top_;
    right += insets->right_;
    bottom += insets->bottom_;
  }
  if (insets == nil) {
    insets = [[RAREUIInsets alloc] initWithInt:top withInt:right withInt:bottom withInt:left];
  }
  else {
    (void) [insets setWithInt:top withInt:right withInt:bottom withInt:left];
  }
  return insets;
}

- (id<RAREiPlatformBorder>)getInsideBorder {
  return IOSObjectArray_Get(borders_, (int) [((IOSObjectArray *) nil_chk(borders_)) count] - 1);
}

- (id<RAREiPlatformBorder>)getOutsideBorder {
  return IOSObjectArray_Get(nil_chk(borders_), 0);
}

- (id<RAREiPlatformShape>)getShapeWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                                                  withFloat:(float)x
                                                  withFloat:(float)y
                                                  withFloat:(float)width
                                                  withFloat:(float)height {
  int len = (int) [((IOSObjectArray *) nil_chk(borders_)) count];
  RAREUIInsets *nextInsets = nil;
  float px, py, pw, ph;
  px = x;
  py = y;
  pw = width;
  ph = height;
  id<RAREiPlatformShape> shape;
  for (int i = 0; i < len; i++) {
    id<RAREiPlatformBorder> b = IOSObjectArray_Get(borders_, i);
    if (b != nil) {
      shape = [b getShapeWithRAREiPlatformGraphics:g withFloat:px withFloat:py withFloat:pw withFloat:ph];
      if (shape != nil) {
        return shape;
      }
      nextInsets = [b getBorderInsetsWithRAREUIInsets:nextInsets];
      px += ((RAREUIInsets *) nil_chk(nextInsets))->left_;
      py += nextInsets->top_;
      pw = pw - nextInsets->right_ - nextInsets->left_;
      ph = ph - nextInsets->bottom_ - nextInsets->top_;
    }
  }
  return nil;
}

- (BOOL)isPadForArc {
  return ([self getInsideBorder] == nil) ? [((id<RAREiPlatformBorder>) nil_chk([self getOutsideBorder])) isPadForArc] : [((id<RAREiPlatformBorder>) nil_chk([self getInsideBorder])) isPadForArc];
}

- (BOOL)isPaintLast {
  for (int i = 0; i < (int) [((IOSObjectArray *) nil_chk(borders_)) count]; i++) {
    if ((IOSObjectArray_Get(borders_, i) != nil) && ![((id<RAREiPlatformBorder>) IOSObjectArray_Get(borders_, i)) isPaintLast]) {
      return NO;
    }
  }
  return YES;
}

- (BOOL)isRectangular {
  for (int i = 0; i < (int) [((IOSObjectArray *) nil_chk(borders_)) count]; i++) {
    if ((IOSObjectArray_Get(borders_, i) != nil) && ![((id<RAREiPlatformBorder>) IOSObjectArray_Get(borders_, i)) isRectangular]) {
      return NO;
    }
  }
  return YES;
}

- (BOOL)isFocusAware {
  for (int i = 0; i < (int) [((IOSObjectArray *) nil_chk(borders_)) count]; i++) {
    if ((IOSObjectArray_Get(borders_, i) != nil) && ![((id<RAREiPlatformBorder>) IOSObjectArray_Get(borders_, i)) isFocusAware]) {
      return YES;
    }
  }
  return NO;
}

- (void)paintOrClipWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                                   withFloat:(float)x
                                   withFloat:(float)y
                                   withFloat:(float)width
                                   withFloat:(float)height
                                 withBoolean:(BOOL)paint
                                 withBoolean:(BOOL)end {
  int len = (int) [((IOSObjectArray *) nil_chk(borders_)) count];
  RAREUIInsets *nextInsets = nil;
  float px, py, pw, ph;
  px = x;
  py = y;
  pw = width;
  ph = height;
  for (int i = 0; i < len; i++) {
    id<RAREiPlatformBorder> b = IOSObjectArray_Get(borders_, i);
    if (b != nil) {
      if (paint) {
        [b paintWithRAREiPlatformGraphics:g withFloat:px withFloat:py withFloat:pw withFloat:ph withBoolean:[b isPaintLast]];
      }
      else {
        [b clipWithRAREiPlatformGraphics:g withFloat:px withFloat:py withFloat:pw withFloat:ph];
      }
      nextInsets = [b getBorderInsetsWithRAREUIInsets:nextInsets];
      px += ((RAREUIInsets *) nil_chk(nextInsets))->left_;
      py += nextInsets->top_;
      pw = pw - nextInsets->right_ - nextInsets->left_;
      ph = ph - nextInsets->bottom_ - nextInsets->top_;
    }
  }
}

- (void)copyAllFieldsTo:(RAREaUICompoundBorder *)other {
  [super copyAllFieldsTo:other];
  other->borders_ = borders_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "clipsContents", NULL, "Z", 0x1, NULL },
    { "getBorderInsetsWithRAREUIInsets:", NULL, "LRAREUIInsets", 0x1, NULL },
    { "getBorderInsetsExWithRAREUIInsets:", NULL, "LRAREUIInsets", 0x1, NULL },
    { "getInsideBorder", NULL, "LRAREiPlatformBorder", 0x1, NULL },
    { "getOutsideBorder", NULL, "LRAREiPlatformBorder", 0x1, NULL },
    { "getShapeWithRAREiPlatformGraphics:withFloat:withFloat:withFloat:withFloat:", NULL, "LRAREiPlatformShape", 0x1, NULL },
    { "isPadForArc", NULL, "Z", 0x1, NULL },
    { "isPaintLast", NULL, "Z", 0x1, NULL },
    { "isRectangular", NULL, "Z", 0x1, NULL },
    { "isFocusAware", NULL, "Z", 0x1, NULL },
    { "paintOrClipWithRAREiPlatformGraphics:withFloat:withFloat:withFloat:withFloat:withBoolean:withBoolean:", NULL, "V", 0x4, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "borders_", NULL, 0x14, "LIOSObjectArray" },
  };
  static J2ObjcClassInfo _RAREaUICompoundBorder = { "aUICompoundBorder", "com.appnativa.rare.ui.border", NULL, 0x401, 11, methods, 1, fields, 0, NULL};
  return &_RAREaUICompoundBorder;
}

@end
