//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/ui/iPlatformListHandler.java
//
//  Created by decoteaud on 12/8/15.
//

#ifndef _RAREiPlatformListHandler_H_
#define _RAREiPlatformListHandler_H_

@class RAREUIDimension;
@protocol RAREiDataModelListener;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/iListHandler.h"
#include "com/appnativa/rare/ui/iScrollerSupport.h"

@protocol RAREiPlatformListHandler < RAREiListHandler, RAREiScrollerSupport, NSObject, JavaObject >
- (float)getPreferredWidth;
- (RAREUIDimension *)getPreferredSize;
- (void)removeDataModelListenerWithRAREiDataModelListener:(id<RAREiDataModelListener>)l;
- (void)addDataModelListenerWithRAREiDataModelListener:(id<RAREiDataModelListener>)l;
@end

#define ComAppnativaRareUiIPlatformListHandler RAREiPlatformListHandler

#endif // _RAREiPlatformListHandler_H_
