//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/painter/aUICellPainter.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RAREaUICellPainter_H_
#define _RAREaUICellPainter_H_

@class RAREDisplayedEnum;
@class RARERenderSpaceEnum;
@class RARERenderTypeEnum;
@class RAREUIColor;
@protocol RAREiPlatformBorder;
@protocol RAREiPlatformGraphics;
@protocol RAREiPlatformPaint;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/painter/PaintBucket.h"
#include "com/appnativa/rare/ui/painter/iPlatformPainter.h"

@interface RAREaUICellPainter : RAREPaintBucket < RAREiPlatformPainter > {
 @public
  int height_;
  int width_;
  int x_;
  int y_;
  int column_;
  int row_;
  int columnSpan_;
  int rowSpan_;
  RAREDisplayedEnum *displayed_;
  BOOL enabled_;
  RARERenderSpaceEnum *renderSpace_;
  RARERenderTypeEnum *renderType_;
}

- (id)init;
- (void)setBackgroundWithRAREUIColor:(RAREUIColor *)bg;
- (void)setDisplayedWithRAREDisplayedEnum:(RAREDisplayedEnum *)displayed;
- (void)setEnabledWithBoolean:(BOOL)enabled;
- (void)setRenderSpaceWithRARERenderSpaceEnum:(RARERenderSpaceEnum *)space;
- (void)setRenderTypeWithRARERenderTypeEnum:(RARERenderTypeEnum *)type;
- (RAREUIColor *)getBackgroundColor;
- (id<RAREiPlatformBorder>)getBorder;
- (RAREDisplayedEnum *)getDisplayed;
- (RARERenderSpaceEnum *)getRenderSpace;
- (RARERenderTypeEnum *)getRenderType;
- (BOOL)hasValue;
- (BOOL)isEnabled;
- (void)dispose;
- (void)setDisposableWithBoolean:(BOOL)disposable;
- (BOOL)isDisposed;
- (BOOL)isDisposable;
- (void)paintWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                             withFloat:(float)x
                             withFloat:(float)y
                             withFloat:(float)width
                             withFloat:(float)height
                               withInt:(int)orientation;
- (id<RAREiPlatformPainter>)reference;
- (id<RAREiPlatformPaint>)getPaintWithFloat:(float)width
                                  withFloat:(float)height;
- (BOOL)canUseLayer;
- (BOOL)canUseMainLayer;
- (int)getModCount;
- (BOOL)isSingleColorPainter;
- (void)copyAllFieldsTo:(RAREaUICellPainter *)other;
@end

J2OBJC_FIELD_SETTER(RAREaUICellPainter, displayed_, RAREDisplayedEnum *)
J2OBJC_FIELD_SETTER(RAREaUICellPainter, renderSpace_, RARERenderSpaceEnum *)
J2OBJC_FIELD_SETTER(RAREaUICellPainter, renderType_, RARERenderTypeEnum *)

typedef RAREaUICellPainter ComAppnativaRareUiPainterAUICellPainter;

#endif // _RAREaUICellPainter_H_