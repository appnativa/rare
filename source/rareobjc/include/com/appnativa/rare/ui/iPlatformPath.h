//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/ui/iPlatformPath.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREiPlatformPath_H_
#define _RAREiPlatformPath_H_

@class RAREUIRectangle;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/iPath.h"

@protocol RAREiPlatformPath < RAREiPath, NSObject, JavaObject >
- (id<RAREiPlatformPath>)copy__ OBJC_METHOD_FAMILY_NONE;
- (RAREUIRectangle *)getBounds;
- (id)getPath;
@end

#define ComAppnativaRareUiIPlatformPath RAREiPlatformPath

#endif // _RAREiPlatformPath_H_
