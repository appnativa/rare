//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/iPlatformAppContext.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RAREiPlatformAppContext_H_
#define _RAREiPlatformAppContext_H_

@protocol RAREiPlatformComponentFactory;

#import "JreEmulation.h"
#include "com/appnativa/rare/iAppContext.h"

@protocol RAREiPlatformAppContext < RAREiAppContext, NSObject, JavaObject >
- (id<RAREiPlatformComponentFactory>)getComponentCreator;
- (BOOL)alwaysUseHeavyTargets;
@end

#define ComAppnativaRareIPlatformAppContext RAREiPlatformAppContext

#endif // _RAREiPlatformAppContext_H_