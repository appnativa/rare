//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/iTransform.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREiTransform_H_
#define _RAREiTransform_H_

#import "JreEmulation.h"

@protocol RAREiTransform < NSObject, JavaObject >
- (id)getPlatformTransform;
- (void)concatenateWithRAREiTransform:(id<RAREiTransform>)tx;
- (void)rotateWithFloat:(float)angle;
- (void)scale__WithFloat:(float)sx
               withFloat:(float)sy;
- (void)shearWithFloat:(float)shx
             withFloat:(float)shy;
- (void)translateWithFloat:(float)x
                 withFloat:(float)y;
- (void)setTransformWithFloat:(float)m00
                    withFloat:(float)m10
                    withFloat:(float)m01
                    withFloat:(float)m11
                    withFloat:(float)m02
                    withFloat:(float)m12;
- (void)concatenateWithFloat:(float)m00
                   withFloat:(float)m10
                   withFloat:(float)m01
                   withFloat:(float)m11
                   withFloat:(float)m02
                   withFloat:(float)m12;
- (id<RAREiTransform>)cloneCopy;
@end

#define ComAppnativaRareUiITransform RAREiTransform

#endif // _RAREiTransform_H_
