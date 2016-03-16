//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/ui/Transform.java
//
//  Created by decoteaud on 3/11/16.
//

#include "com/appnativa/rare/platform/apple/ui/view/View.h"
#include "com/appnativa/rare/ui/Transform.h"
#include "com/appnativa/rare/ui/UIPoint.h"
#include "com/appnativa/rare/ui/iTransform.h"
#include "java/lang/CloneNotSupportedException.h"
#include "java/lang/InternalError.h"

@implementation RARETransform

- (id)init {
  if (self = [super init]) {
    a_ = 1;
    d_ = 1;
    [self makeItentity];
  }
  return self;
}

- (void)makeItentity {
  CGAffineTransform t=CGAffineTransformIdentity;
  a_=t.a;
  b_=t.b;
  c_=t.c;
  d_=t.d;
  tx_=t.tx;
  ty_=t.ty;
}

- (void)concatenateWithRAREiTransform:(id<RAREiTransform>)transform {
  RARETransform* tx=(RARETransform*)transform;
  CGAffineTransform t1=CGAffineTransformMake (a_,b_,c_,d_,tx_,ty_);
  CGAffineTransform t2=CGAffineTransformMake (tx->a_,tx->b_,tx->c_,tx->d_,tx->tx_,tx->ty_);
  CGAffineTransform t=CGAffineTransformConcat(t1,t2);
  a_=t.a;
  b_=t.b;
  c_=t.c;
  d_=t.d;
  tx_=t.tx;
  ty_=t.ty;
}

- (void)concatenateWithFloat:(float)m00
                   withFloat:(float)m10
                   withFloat:(float)m01
                   withFloat:(float)m11
                   withFloat:(float)m02
                   withFloat:(float)m12 {
  RARETransform *t = [[RARETransform alloc] init];
  [t setTransformWithFloat:m00 withFloat:m10 withFloat:m01 withFloat:m11 withFloat:m02 withFloat:m12];
  [self concatenateWithRAREiTransform:t];
}

- (void)rotateWithFloat:(float)angle {
  CGAffineTransform t=CGAffineTransformMake (a_,b_,c_,d_,tx_,ty_);
  t=CGAffineTransformRotate(t,angle);
  a_=t.a;
  b_=t.b;
  c_=t.c;
  d_=t.d;
  tx_=t.tx;
  ty_=t.ty;
}

- (void)applyWithRAREView:(RAREView *)v {
  CGAffineTransform t=CGAffineTransformMake (a_,b_,c_,d_,tx_,ty_);
  ((UIView*)v.getProxy).transform=t;
}

- (void)scale__WithFloat:(float)sx
               withFloat:(float)sy {
  CGAffineTransform t=CGAffineTransformMake (a_,b_,c_,d_,tx_,ty_);
  t=CGAffineTransformScale(t,sx,sy);
  a_=t.a;
  b_=t.b;
  c_=t.c;
  d_=t.d;
  tx_=t.tx;
  ty_=t.ty;
}

- (void)shearWithFloat:(float)shx
             withFloat:(float)shy {
  CGAffineTransform t=CGAffineTransformMake (a_,b_,c_,d_,tx_,ty_);
  CGAffineTransform shear=CGAffineTransformMake(1.f, shy, shx, 1.f, 0.f, 0.f);
  t=CGAffineTransformConcat(t, shear);
  a_=t.a;
  b_=t.b;
  c_=t.c;
  d_=t.d;
  tx_=t.tx;
  ty_=t.ty;
}

- (RAREUIPoint *)transformWithFloat:(float)x
                          withFloat:(float)y {
  CGAffineTransform t=CGAffineTransformMake (a_,b_,c_,d_,tx_,ty_);
  CGPoint p=CGPointMake(x,y);
  CGPointApplyAffineTransform (p,t);
  return [[RAREUIPoint alloc] initWithFloat: x withFloat: y];
}

- (void)translateWithFloat:(float)x
                 withFloat:(float)y {
  CGAffineTransform t=CGAffineTransformMake (a_,b_,c_,d_,tx_,ty_);
  t=CGAffineTransformTranslate(t,x,y);
  a_=t.a;
  b_=t.b;
  c_=t.c;
  d_=t.d;
  tx_=t.tx;
  ty_=t.ty;
}

- (void)setTransformWithFloat:(float)m00
                    withFloat:(float)m10
                    withFloat:(float)m01
                    withFloat:(float)m11
                    withFloat:(float)m02
                    withFloat:(float)m12 {
  a_ = m00;
  b_ = m01;
  c_ = m10;
  d_ = m11;
  tx_ = m02;
  ty_ = m12;
}

- (id)clone {
  @try {
    return [super clone];
  }
  @catch (JavaLangCloneNotSupportedException *e) {
    @throw [[JavaLangInternalError alloc] init];
  }
}

- (id)getPlatformTransform {
  return self;
}

- (id<RAREiTransform>)cloneCopy {
  return (id<RAREiTransform>) check_protocol_cast([self clone], @protocol(RAREiTransform));
}

- (id)copyWithZone:(NSZone *)zone {
  return [self clone];
}

- (void)copyAllFieldsTo:(RARETransform *)other {
  [super copyAllFieldsTo:other];
  other->a_ = a_;
  other->b_ = b_;
  other->c_ = c_;
  other->d_ = d_;
  other->tx_ = tx_;
  other->ty_ = ty_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "makeItentity", NULL, "V", 0x101, NULL },
    { "concatenateWithRAREiTransform:", NULL, "V", 0x101, NULL },
    { "rotateWithFloat:", NULL, "V", 0x101, NULL },
    { "applyWithRAREView:", NULL, "V", 0x101, NULL },
    { "scale__WithFloat:withFloat:", NULL, "V", 0x101, NULL },
    { "shearWithFloat:withFloat:", NULL, "V", 0x101, NULL },
    { "transformWithFloat:withFloat:", NULL, "LRAREUIPoint", 0x101, NULL },
    { "translateWithFloat:withFloat:", NULL, "V", 0x101, NULL },
    { "clone", NULL, "LNSObject", 0x1, NULL },
    { "getPlatformTransform", NULL, "LNSObject", 0x1, NULL },
    { "cloneCopy", NULL, "LRAREiTransform", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "a_", NULL, 0x0, "F" },
    { "d_", NULL, 0x0, "F" },
    { "b_", NULL, 0x0, "F" },
    { "c_", NULL, 0x0, "F" },
    { "tx_", NULL, 0x0, "F" },
    { "ty_", NULL, 0x0, "F" },
  };
  static J2ObjcClassInfo _RARETransform = { "Transform", "com.appnativa.rare.ui", NULL, 0x1, 11, methods, 6, fields, 0, NULL};
  return &_RARETransform;
}

@end
