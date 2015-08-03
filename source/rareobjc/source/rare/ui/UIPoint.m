//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/UIPoint.java
//
//  Created by decoteaud on 7/29/15.
//

#include "com/appnativa/rare/ui/UIPoint.h"
#include "java/lang/Math.h"

@implementation RAREUIPoint

- (id)init {
  return [super init];
}

- (id)initWithDouble:(double)x
          withDouble:(double)y {
  if (self = [super init]) {
    self->x_ = (int) [JavaLangMath roundWithDouble:x];
    self->y_ = (int) [JavaLangMath roundWithDouble:y];
  }
  return self;
}

- (id)initWithFloat:(float)x
          withFloat:(float)y {
  if (self = [super init]) {
    self->x_ = x;
    self->y_ = y;
  }
  return self;
}

- (BOOL)isEqual:(id)o {
  if (o == self) {
    return YES;
  }
  if ([o isKindOfClass:[RAREUIPoint class]]) {
    RAREUIPoint *p = (RAREUIPoint *) check_class_cast(o, [RAREUIPoint class]);
    return ([JavaLangMath absWithFloat:((RAREUIPoint *) nil_chk(p))->x_ - x_] < .0000001) && ([JavaLangMath absWithFloat:p->y_ - y_] < .0000001);
  }
  return NO;
}

- (NSUInteger)hash {
  return (int) (x_ * y_);
}

- (NSString *)description {
  return [NSString stringWithFormat:@"[x=%f, y=%f]", x_, y_];
}

- (void)setWithRAREUIPoint:(RAREUIPoint *)p {
  self->x_ = ((RAREUIPoint *) nil_chk(p))->x_;
  self->y_ = p->y_;
}

- (void)setWithDouble:(double)x
           withDouble:(double)y {
  self->x_ = (float) x;
  self->y_ = (float) y;
}

- (void)setWithInt:(int)x
           withInt:(int)y {
  self->x_ = x;
  self->y_ = y;
}

- (void)setLocationWithDouble:(double)x
                   withDouble:(double)y {
  self->x_ = (float) x;
  self->y_ = (float) y;
}

- (void)setLocationWithFloat:(float)x
                   withFloat:(float)y {
  self->x_ = x;
  self->y_ = y;
}

- (void)setXWithFloat:(float)x {
  self->x_ = x;
}

- (void)setYWithFloat:(float)y {
  self->y_ = y;
}

- (RAREUIPoint *)getLocation {
  return [[RAREUIPoint alloc] initWithFloat:x_ withFloat:y_];
}

- (float)getX {
  return x_;
}

- (float)getY {
  return y_;
}

- (int)intY {
  return [JavaLangMath roundWithFloat:y_];
}

- (int)intX {
  return [JavaLangMath roundWithFloat:x_];
}

- (void)copyAllFieldsTo:(RAREUIPoint *)other {
  [super copyAllFieldsTo:other];
  other->x_ = x_;
  other->y_ = y_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getLocation", NULL, "LRAREUIPoint", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "x_", NULL, 0x1, "F" },
    { "y_", NULL, 0x1, "F" },
  };
  static J2ObjcClassInfo _RAREUIPoint = { "UIPoint", "com.appnativa.rare.ui", NULL, 0x1, 1, methods, 2, fields, 0, NULL};
  return &_RAREUIPoint;
}

@end
