//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/ios/com/appnativa/rare/platform/apple/ui/util/BezierPath.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREBezierPath_H_
#define _RAREBezierPath_H_

@class RAREUIRectangle;
@protocol RAREiPath;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/iPlatformPath.h"

@interface RAREBezierPath : NSObject < RAREiPlatformPath > {
 @public
  id proxy_;
  float x_;
  float y_;
}

- (id)init;
- (id)initWithId:(id)proxy;
- (RAREUIRectangle *)getBounds;
- (id<RAREiPath>)arcWithFloat:(float)x
                    withFloat:(float)y
                    withFloat:(float)radius
                    withFloat:(float)startAngle
                    withFloat:(float)endAngle
                  withBoolean:(BOOL)counterClockwise;
- (id<RAREiPath>)arcToWithFloat:(float)x1
                      withFloat:(float)y1
                      withFloat:(float)x2
                      withFloat:(float)y2
                      withFloat:(float)radius;
- (void)close;
- (id<RAREiPlatformPath>)copy__ OBJC_METHOD_FAMILY_NONE;
- (RAREBezierPath *)copyEx OBJC_METHOD_FAMILY_NONE;
- (id<RAREiPath>)cubicToWithFloat:(float)x1
                        withFloat:(float)y1
                        withFloat:(float)x2
                        withFloat:(float)y2
                        withFloat:(float)x3
                        withFloat:(float)y3;
- (void)drawLineWithFloat:(float)x1
                withFloat:(float)y1
                withFloat:(float)x2
                withFloat:(float)y2;
- (void)drawRectWithFloat:(float)x
                withFloat:(float)y
                withFloat:(float)width
                withFloat:(float)height;
- (void)drawRoundedRectWithFloat:(float)x
                       withFloat:(float)y
                       withFloat:(float)width
                       withFloat:(float)height
                       withFloat:(float)arcWidth
                       withFloat:(float)arcHeight;
- (id<RAREiPath>)lineToWithFloat:(float)x
                       withFloat:(float)y;
- (id<RAREiPath>)moveToWithFloat:(float)x
                       withFloat:(float)y;
- (id<RAREiPath>)quadToWithFloat:(float)x1
                       withFloat:(float)y1
                       withFloat:(float)x2
                       withFloat:(float)y2;
- (void)reset;
- (void)rewind;
- (id<RAREiPath>)startLineDrawingWithFloat:(float)x
                                 withFloat:(float)y
                               withBoolean:(BOOL)move;
- (BOOL)isEmpty;
- (BOOL)isPointInPathWithFloat:(float)x
                     withFloat:(float)y;
- (id)getPath;
+ (id)createProxy;
- (RAREUIRectangle *)getRectangle;
- (RAREBezierPath *)getBezierPath;
- (void)translateWithFloat:(float)x
                 withFloat:(float)y;
- (void)copyAllFieldsTo:(RAREBezierPath *)other;
@end

J2OBJC_FIELD_SETTER(RAREBezierPath, proxy_, id)

typedef RAREBezierPath ComAppnativaRarePlatformAppleUiUtilBezierPath;

#endif // _RAREBezierPath_H_
