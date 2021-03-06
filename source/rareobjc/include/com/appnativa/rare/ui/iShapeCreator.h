//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/iShapeCreator.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREiShapeCreator_H_
#define _RAREiShapeCreator_H_

@protocol RAREiPlatformPath;

#import "JreEmulation.h"

@protocol RAREiShapeCreator < NSObject, JavaObject >
- (id<RAREiPlatformPath>)createClipShapeWithRAREiPlatformPath:(id<RAREiPlatformPath>)shape
                                                    withFloat:(float)width
                                                    withFloat:(float)height
                                                  withBoolean:(BOOL)forBorder;
- (id<RAREiPlatformPath>)createShapeWithFloat:(float)width
                                    withFloat:(float)height;
- (id<RAREiPlatformPath>)resizeClipWithRAREiPlatformPath:(id<RAREiPlatformPath>)clip
                                               withFloat:(float)width
                                               withFloat:(float)height;
- (id<RAREiPlatformPath>)resizeShapeWithRAREiPlatformPath:(id<RAREiPlatformPath>)shape
                                                withFloat:(float)width
                                                withFloat:(float)height;
@end

#define ComAppnativaRareUiIShapeCreator RAREiShapeCreator

#endif // _RAREiShapeCreator_H_
