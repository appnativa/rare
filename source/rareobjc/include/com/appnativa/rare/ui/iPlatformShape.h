//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/ui/iPlatformShape.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RAREiPlatformShape_H_
#define _RAREiPlatformShape_H_

@class RAREUIRectangle;
@protocol RAREiPlatformPath;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/iShape.h"

@protocol RAREiPlatformShape < RAREiShape, NSObject, JavaObject >
- (RAREUIRectangle *)getRectangle;
- (id<RAREiPlatformPath>)getBezierPath;
@end

#define ComAppnativaRareUiIPlatformShape RAREiPlatformShape

#endif // _RAREiPlatformShape_H_