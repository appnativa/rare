//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/UIInsets.java
//
//  Created by decoteaud on 12/8/15.
//

#include "com/appnativa/rare/ui/UIInsets.h"
#include "java/lang/CloneNotSupportedException.h"
#include "java/lang/InternalError.h"
#include "java/lang/Math.h"
#include "java/lang/StringBuilder.h"

@implementation RAREUIInsets

- (id)init {
  return [super init];
}

- (id)initWithFloat:(float)size {
  if (self = [super init]) {
    self->top_ = size;
    self->left_ = size;
    self->bottom_ = size;
    self->right_ = size;
  }
  return self;
}

- (id)initWithRAREUIInsets:(RAREUIInsets *)insets {
  if (self = [super init]) {
    (void) [self setWithRAREUIInsets:insets];
  }
  return self;
}

- (id)initWithDouble:(double)top
          withDouble:(double)right
          withDouble:(double)bottom
          withDouble:(double)left {
  if (self = [super init]) {
    (void) [self setWithDouble:top withDouble:right withDouble:bottom withDouble:left];
  }
  return self;
}

- (id)initWithInt:(int)top
          withInt:(int)right
          withInt:(int)bottom
          withInt:(int)left {
  if (self = [super init]) {
    (void) [self setWithInt:top withInt:right withInt:bottom withInt:left];
  }
  return self;
}

- (RAREUIInsets *)addInsetsWithRAREUIInsets:(RAREUIInsets *)inArg {
  (void) [self addInsetsWithFloat:((RAREUIInsets *) nil_chk(inArg))->top_ withFloat:inArg->right_ withFloat:inArg->bottom_ withFloat:inArg->left_];
  return self;
}

- (RAREUIInsets *)addInsetsWithFloat:(float)top
                           withFloat:(float)right
                           withFloat:(float)bottom
                           withFloat:(float)left {
  self->top_ += top;
  self->left_ += left;
  self->bottom_ += bottom;
  self->right_ += right;
  return self;
}

- (id)clone {
  @try {
    return [super clone];
  }
  @catch (JavaLangCloneNotSupportedException *e) {
    @throw [[JavaLangInternalError alloc] init];
  }
}

- (BOOL)isEqual:(id)obj {
  if ([obj isKindOfClass:[RAREUIInsets class]]) {
    RAREUIInsets *insets = (RAREUIInsets *) check_class_cast(obj, [RAREUIInsets class]);
    return ((top_ == ((RAREUIInsets *) nil_chk(insets))->top_) && (left_ == insets->left_) && (bottom_ == insets->bottom_) && (right_ == insets->right_));
  }
  return NO;
}

- (NSUInteger)hash {
  float sum1 = left_ + bottom_;
  float sum2 = right_ + top_;
  float val1 = sum1 * (sum1 + 1) / 2 + left_;
  float val2 = sum2 * (sum2 + 1) / 2 + top_;
  float sum3 = val1 + val2;
  return (int) (sum3 * (sum3 + 1) / 2 + val2);
}

- (NSString *)toCSSWithBoolean:(BOOL)fx {
  NSString *name = fx ? @"-fx-padding: " : @"padding: ";
  return [NSString stringWithFormat:@"%@%fpx %fpx %fpx %fpx;", name, top_, right_, bottom_, left_];
}

- (JavaLangStringBuilder *)toCSSWithNSString:(NSString *)name
                   withJavaLangStringBuilder:(JavaLangStringBuilder *)sb {
  (void) [((JavaLangStringBuilder *) nil_chk([((JavaLangStringBuilder *) nil_chk([((JavaLangStringBuilder *) nil_chk([((JavaLangStringBuilder *) nil_chk([((JavaLangStringBuilder *) nil_chk(sb)) appendWithNSString:name])) appendWithNSString:@": "])) appendWithFloat:top_])) appendWithNSString:@"px "])) appendWithFloat:right_];
  (void) [((JavaLangStringBuilder *) nil_chk([((JavaLangStringBuilder *) nil_chk([((JavaLangStringBuilder *) nil_chk([((JavaLangStringBuilder *) nil_chk([sb appendWithNSString:@"px "])) appendWithFloat:bottom_])) appendWithNSString:@"px "])) appendWithFloat:left_])) appendWithNSString:@"px;"];
  return sb;
}

- (NSString *)description {
  return [NSString stringWithFormat:@"[top=%f,right=%f,bottom=%f,left=%f]", top_, right_, bottom_, left_];
}

- (void)setWithInt:(int)all {
  self->top_ = all;
  self->left_ = all;
  self->bottom_ = all;
  self->right_ = all;
}

- (RAREUIInsets *)setWithRAREUIInsets:(RAREUIInsets *)inArg {
  (void) [self setWithDouble:((RAREUIInsets *) nil_chk(inArg))->top_ withDouble:inArg->right_ withDouble:inArg->bottom_ withDouble:inArg->left_];
  return self;
}

- (RAREUIInsets *)setWithDouble:(double)top
                     withDouble:(double)right
                     withDouble:(double)bottom
                     withDouble:(double)left {
  self->top_ = (int) [JavaLangMath roundWithDouble:top];
  self->left_ = (int) [JavaLangMath roundWithDouble:left];
  self->bottom_ = (int) [JavaLangMath roundWithDouble:bottom];
  self->right_ = (int) [JavaLangMath roundWithDouble:right];
  return self;
}

- (RAREUIInsets *)setWithInt:(int)top
                     withInt:(int)right
                     withInt:(int)bottom
                     withInt:(int)left {
  self->top_ = top;
  self->left_ = left;
  self->bottom_ = bottom;
  self->right_ = right;
  return self;
}

- (RAREUIInsets *)setBottomWithInt:(int)bottom {
  self->bottom_ = bottom;
  return self;
}

- (RAREUIInsets *)setLeftWithInt:(int)left {
  self->left_ = left;
  return self;
}

- (RAREUIInsets *)setRightWithInt:(int)right {
  self->right_ = right;
  return self;
}

- (RAREUIInsets *)setTopWithInt:(int)top {
  self->top_ = top;
  return self;
}

- (float)getBottom {
  return bottom_;
}

- (float)getHeight {
  return top_ + bottom_;
}

- (float)getLeft {
  return left_;
}

- (float)getRight {
  return right_;
}

- (float)getTop {
  return top_;
}

- (float)getWidth {
  return left_ + right_;
}

- (BOOL)isAllSidesEqual {
  if ([JavaLangMath absWithFloat:left_ - top_] > 0.00001) {
    return NO;
  }
  if ([JavaLangMath absWithFloat:left_ - right_] > 0.00001) {
    return NO;
  }
  if ([JavaLangMath absWithFloat:left_ - bottom_] > 0.00001) {
    return NO;
  }
  return YES;
}

- (BOOL)isAllOne {
  return ((int) left_ == 1) && ((int) top_ == 1) && ((int) right_ == 1) && ((int) bottom_ == 1);
}

- (BOOL)isAllTwo {
  return ((int) left_ == 2) && ((int) top_ == 2) && ((int) right_ == 2) && ((int) bottom_ == 2);
}

- (BOOL)isEmpty {
  return ((int) left_ == 0) && ((int) top_ == 0) && ((int) right_ == 0) && ((int) bottom_ == 0);
}

- (BOOL)isOnlyLeftOne {
  return ((int) left_ == 1) && ((int) top_ == 0) && ((int) right_ == 0) && ((int) bottom_ == 0);
}

- (BOOL)isOnlyLeftThree {
  return ((int) left_ == 3) && ((int) top_ == 0) && ((int) right_ == 0) && ((int) bottom_ == 0);
}

- (BOOL)isOnlyLeftTwo {
  return ((int) left_ == 2) && ((int) top_ == 0) && ((int) right_ == 0) && ((int) bottom_ == 0);
}

- (int)intTop {
  return (int) [JavaLangMath ceilWithDouble:top_];
}

- (int)intLeft {
  return (int) [JavaLangMath ceilWithDouble:left_];
}

- (int)intBottom {
  return (int) [JavaLangMath ceilWithDouble:bottom_];
}

- (int)intRight {
  return (int) [JavaLangMath ceilWithDouble:right_];
}

- (id)copyWithZone:(NSZone *)zone {
  return [self clone];
}

- (void)copyAllFieldsTo:(RAREUIInsets *)other {
  [super copyAllFieldsTo:other];
  other->bottom_ = bottom_;
  other->left_ = left_;
  other->right_ = right_;
  other->top_ = top_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "addInsetsWithRAREUIInsets:", NULL, "LRAREUIInsets", 0x1, NULL },
    { "addInsetsWithFloat:withFloat:withFloat:withFloat:", NULL, "LRAREUIInsets", 0x1, NULL },
    { "clone", NULL, "LNSObject", 0x1, NULL },
    { "toCSSWithBoolean:", NULL, "LNSString", 0x1, NULL },
    { "toCSSWithNSString:withJavaLangStringBuilder:", NULL, "LJavaLangStringBuilder", 0x1, NULL },
    { "setWithRAREUIInsets:", NULL, "LRAREUIInsets", 0x1, NULL },
    { "setWithDouble:withDouble:withDouble:withDouble:", NULL, "LRAREUIInsets", 0x1, NULL },
    { "setWithInt:withInt:withInt:withInt:", NULL, "LRAREUIInsets", 0x1, NULL },
    { "setBottomWithInt:", NULL, "LRAREUIInsets", 0x1, NULL },
    { "setLeftWithInt:", NULL, "LRAREUIInsets", 0x1, NULL },
    { "setRightWithInt:", NULL, "LRAREUIInsets", 0x1, NULL },
    { "setTopWithInt:", NULL, "LRAREUIInsets", 0x1, NULL },
    { "isAllSidesEqual", NULL, "Z", 0x11, NULL },
    { "isAllOne", NULL, "Z", 0x11, NULL },
    { "isAllTwo", NULL, "Z", 0x11, NULL },
    { "isEmpty", NULL, "Z", 0x1, NULL },
    { "isOnlyLeftOne", NULL, "Z", 0x11, NULL },
    { "isOnlyLeftThree", NULL, "Z", 0x11, NULL },
    { "isOnlyLeftTwo", NULL, "Z", 0x11, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "bottom_", NULL, 0x1, "F" },
    { "left_", NULL, 0x1, "F" },
    { "right_", NULL, 0x1, "F" },
    { "top_", NULL, 0x1, "F" },
  };
  static J2ObjcClassInfo _RAREUIInsets = { "UIInsets", "com.appnativa.rare.ui", NULL, 0x1, 19, methods, 4, fields, 0, NULL};
  return &_RAREUIInsets;
}

@end
