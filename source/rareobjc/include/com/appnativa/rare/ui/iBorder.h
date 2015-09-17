//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/iBorder.java
//
//  Created by decoteaud on 9/15/15.
//

#ifndef _RAREiBorder_H_
#define _RAREiBorder_H_

@class RAREUIInsets;
@protocol JavaUtilMap;
@protocol RAREiPlatformGraphics;
@protocol RAREiPlatformPath;
@protocol RAREiPlatformShape;

#import "JreEmulation.h"

@protocol RAREiBorder < NSObject, JavaObject >
- (void)clipWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                            withFloat:(float)x
                            withFloat:(float)y
                            withFloat:(float)width
                            withFloat:(float)height;
- (BOOL)clipsContents;
- (void)paintWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                             withFloat:(float)x
                             withFloat:(float)y
                             withFloat:(float)width
                             withFloat:(float)height
                           withBoolean:(BOOL)end;
- (void)setPadForArcWithBoolean:(BOOL)pad;
- (float)getArcHeight;
- (float)getArcWidth;
- (RAREUIInsets *)getBorderInsetsWithRAREUIInsets:(RAREUIInsets *)insets;
- (RAREUIInsets *)getBorderInsetsExWithRAREUIInsets:(RAREUIInsets *)insets;
- (BOOL)isPadForArc;
- (BOOL)isPaintLast;
- (BOOL)isRectangular;
- (id<RAREiPlatformShape>)getShapeWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                                                  withFloat:(float)x
                                                  withFloat:(float)y
                                                  withFloat:(float)width
                                                  withFloat:(float)height;
- (id<RAREiPlatformPath>)getPathWithRAREiPlatformPath:(id<RAREiPlatformPath>)p
                                            withFloat:(float)x
                                            withFloat:(float)y
                                            withFloat:(float)width
                                            withFloat:(float)height
                                          withBoolean:(BOOL)forClip;
- (void)handleCustomPropertiesWithJavaUtilMap:(id<JavaUtilMap>)map;
@end

#define ComAppnativaRareUiIBorder RAREiBorder

#endif // _RAREiBorder_H_
