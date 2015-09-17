//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/ui/Transform.java
//
//  Created by decoteaud on 9/15/15.
//

#ifndef _RARETransform_H_
#define _RARETransform_H_

@class RAREUIPoint;
@class RAREView;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/iTransform.h"

@interface RARETransform : NSObject < RAREiTransform, NSCopying > {
 @public
  float a_;
  float d_;
  float b_;
  float c_;
  float tx_;
  float ty_;
}

- (id)init;
- (void)makeItentity;
- (void)concatenateWithRAREiTransform:(id<RAREiTransform>)transform;
- (void)concatenateWithFloat:(float)m00
                   withFloat:(float)m10
                   withFloat:(float)m01
                   withFloat:(float)m11
                   withFloat:(float)m02
                   withFloat:(float)m12;
- (void)rotateWithFloat:(float)angle;
- (void)applyWithRAREView:(RAREView *)v;
- (void)scale__WithFloat:(float)sx
               withFloat:(float)sy;
- (void)shearWithFloat:(float)shx
             withFloat:(float)shy;
- (RAREUIPoint *)transformWithFloat:(float)x
                          withFloat:(float)y;
- (void)translateWithFloat:(float)x
                 withFloat:(float)y;
- (void)setTransformWithFloat:(float)m00
                    withFloat:(float)m10
                    withFloat:(float)m01
                    withFloat:(float)m11
                    withFloat:(float)m02
                    withFloat:(float)m12;
- (id)clone;
- (id)getPlatformTransform;
- (id<RAREiTransform>)cloneCopy;
- (id)copyWithZone:(NSZone *)zone;
- (void)copyAllFieldsTo:(RARETransform *)other;
@end

typedef RARETransform ComAppnativaRareUiTransform;

#endif // _RARETransform_H_
