//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/border/aSharedLineBorder.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RAREaSharedLineBorder_H_
#define _RAREaSharedLineBorder_H_

@class RAREChangeEvent;
@class RAREUIColor;
@class RAREUIDimension;
@class RAREUIInsets;
@class RAREUIPoint;
@class RAREUIRectangle;
@protocol RAREiPlatformComponent;
@protocol RAREiPlatformGraphics;
@protocol RAREiPlatformPath;
@protocol RAREiPlatformShape;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/border/UILineBorder.h"
#include "com/appnativa/rare/ui/listener/iViewListener.h"

@interface RAREaSharedLineBorder : RAREUILineBorder < RAREiViewListener > {
 @public
  RAREUIDimension *size_;
  RAREUIColor *backgroundColor_;
  __weak id<RAREiPlatformComponent> bottomComponent_;
  RAREUIRectangle *tempRect1_;
  RAREUIRectangle *tempRect2_;
  RAREUIPoint *tempPoint1_;
  RAREUIPoint *tempPoint2_;
  __weak id<RAREiPlatformComponent> topComponent_;
}

- (id)initWithRAREUIColor:(RAREUIColor *)color;
- (id)initWithRAREUIColor:(RAREUIColor *)color
                withFloat:(float)thickness;
- (id)initWithRAREUIColor:(RAREUIColor *)color
                withFloat:(float)thickness
              withBoolean:(BOOL)roundedCorners;
- (id)initWithRAREUIColor:(RAREUIColor *)color
                withFloat:(float)thickness
                  withInt:(int)arc;
- (id)initWithRAREUIColor:(RAREUIColor *)color
                withFloat:(float)thickness
                  withInt:(int)arcWidth
                  withInt:(int)arcHeight;
- (id<RAREiPlatformPath>)createBorderPathWithRAREiPlatformPath:(id<RAREiPlatformPath>)p
                                                     withFloat:(float)x
                                                     withFloat:(float)y
                                                     withFloat:(float)width
                                                     withFloat:(float)height
                                                     withFloat:(float)aw
                                                     withFloat:(float)ah
                                                   withBoolean:(BOOL)clip;
- (void)clipWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                            withFloat:(float)x
                            withFloat:(float)y
                            withFloat:(float)width
                            withFloat:(float)height;
- (BOOL)clipsContents;
- (id)clone;
- (void)paintWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                             withFloat:(float)x
                             withFloat:(float)y
                             withFloat:(float)width
                             withFloat:(float)height
                           withBoolean:(BOOL)end;
- (void)repaintTopComponent;
- (BOOL)usesPath;
- (void)viewHiddenWithRAREChangeEvent:(RAREChangeEvent *)e;
- (void)viewResizedWithRAREChangeEvent:(RAREChangeEvent *)e;
- (void)viewShownWithRAREChangeEvent:(RAREChangeEvent *)e;
- (BOOL)wantsResizeEvent;
- (void)setBackgroundColorWithRAREUIColor:(RAREUIColor *)background;
- (void)setBottomComponentWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c;
- (void)setSharersWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)top
                  withRAREiPlatformComponent:(id<RAREiPlatformComponent>)bottom;
- (RAREUIColor *)getBackgroundColor;
- (id<RAREiPlatformComponent>)getBottomComponent;
- (id<RAREiPlatformShape>)getShapeWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                                                  withFloat:(float)x
                                                  withFloat:(float)y
                                                  withFloat:(float)width
                                                  withFloat:(float)height;
- (id<RAREiPlatformComponent>)getTopComponent;
- (float)getRoundedOffset;
- (void)paintPartialLineWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                                        withFloat:(float)x
                                        withFloat:(float)y
                                        withFloat:(float)width
                                        withFloat:(float)height;
- (RAREUIInsets *)getBorderInsetsExWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c
                                             withRAREUIInsets:(RAREUIInsets *)insets;
- (BOOL)updateShapeWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c
                                    withFloat:(float)width
                                    withFloat:(float)height;
- (void)copyAllFieldsTo:(RAREaSharedLineBorder *)other;
@end

J2OBJC_FIELD_SETTER(RAREaSharedLineBorder, size_, RAREUIDimension *)
J2OBJC_FIELD_SETTER(RAREaSharedLineBorder, backgroundColor_, RAREUIColor *)
J2OBJC_FIELD_SETTER(RAREaSharedLineBorder, tempRect1_, RAREUIRectangle *)
J2OBJC_FIELD_SETTER(RAREaSharedLineBorder, tempRect2_, RAREUIRectangle *)
J2OBJC_FIELD_SETTER(RAREaSharedLineBorder, tempPoint1_, RAREUIPoint *)
J2OBJC_FIELD_SETTER(RAREaSharedLineBorder, tempPoint2_, RAREUIPoint *)

typedef RAREaSharedLineBorder ComAppnativaRareUiBorderASharedLineBorder;

#endif // _RAREaSharedLineBorder_H_