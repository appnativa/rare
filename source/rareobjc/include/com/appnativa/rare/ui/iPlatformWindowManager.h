//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/ui/iPlatformWindowManager.java
//
//  Created by decoteaud on 9/15/15.
//

#ifndef _RAREiPlatformWindowManager_H_
#define _RAREiPlatformWindowManager_H_

@protocol RAREiPlatformComponentFactory;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/iWindowManager.h"

@protocol RAREiPlatformWindowManager < RAREiWindowManager, NSObject, JavaObject >
- (id<RAREiPlatformComponentFactory>)getComponentCreator;
@end

#define ComAppnativaRareUiIPlatformWindowManager RAREiPlatformWindowManager

#endif // _RAREiPlatformWindowManager_H_
