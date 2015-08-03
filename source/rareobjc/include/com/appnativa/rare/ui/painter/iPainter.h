//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/painter/iPainter.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RAREiPainter_H_
#define _RAREiPainter_H_

@class RAREDisplayedEnum;
@class RARERenderSpaceEnum;
@class RARERenderTypeEnum;
@class RAREUIColor;
@protocol RAREiPlatformGraphics;
@protocol RAREiPlatformPaint;
@protocol RAREiPlatformPainter;

#import "JreEmulation.h"

#define RAREiPainter_HORIZONTAL 2
#define RAREiPainter_UNKNOWN 0
#define RAREiPainter_VERTICAL 1

@protocol RAREiPainter < NSObject, JavaObject >
- (void)dispose;
- (void)paintWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                             withFloat:(float)x
                             withFloat:(float)y
                             withFloat:(float)width
                             withFloat:(float)height
                               withInt:(int)orientation;
- (id<RAREiPlatformPainter>)reference;
- (void)setDisplayedWithRAREDisplayedEnum:(RAREDisplayedEnum *)displayed;
- (void)setDisposableWithBoolean:(BOOL)disposable;
- (void)setEnabledWithBoolean:(BOOL)enabled;
- (void)setRenderSpaceWithRARERenderSpaceEnum:(RARERenderSpaceEnum *)space;
- (void)setRenderTypeWithRARERenderTypeEnum:(RARERenderTypeEnum *)type;
- (RAREUIColor *)getBackgroundColor;
- (RAREDisplayedEnum *)getDisplayed;
- (id<RAREiPlatformPaint>)getPaintWithFloat:(float)width
                                  withFloat:(float)height;
- (RARERenderSpaceEnum *)getRenderSpace;
- (RARERenderTypeEnum *)getRenderType;
- (BOOL)isDisposable;
- (BOOL)isDisposed;
- (BOOL)isEnabled;
@end

@interface RAREiPainter : NSObject {
}
+ (int)HORIZONTAL;
+ (int)UNKNOWN;
+ (int)VERTICAL;
@end

#define ComAppnativaRareUiPainterIPainter RAREiPainter

#endif // _RAREiPainter_H_