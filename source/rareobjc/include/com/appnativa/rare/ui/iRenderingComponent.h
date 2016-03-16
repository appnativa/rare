//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/iRenderingComponent.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREiRenderingComponent_H_
#define _RAREiRenderingComponent_H_

@class RARERenderableDataItem_HorizontalAlignEnum;
@class RARERenderableDataItem_IconPositionEnum;
@class RARERenderableDataItem_OrientationEnum;
@class RARERenderableDataItem_VerticalAlignEnum;
@class RAREUIColor;
@class RAREUIFont;
@class RAREUIInsets;
@protocol JavaUtilMap;
@protocol RAREiPlatformBorder;
@protocol RAREiPlatformComponent;
@protocol RAREiPlatformComponentPainter;
@protocol RAREiPlatformIcon;

#import "JreEmulation.h"

@protocol RAREiRenderingComponent < NSObject, JavaObject >
- (void)setBackgroundWithRAREUIColor:(RAREUIColor *)bg;
- (void)setBorderWithRAREiPlatformBorder:(id<RAREiPlatformBorder>)b;
- (void)setComponentPainterWithRAREiPlatformComponentPainter:(id<RAREiPlatformComponentPainter>)cp;
- (void)setEnabledWithBoolean:(BOOL)enabled;
- (void)setFontWithRAREUIFont:(RAREUIFont *)font;
- (void)setForegroundWithRAREUIColor:(RAREUIColor *)fg;
- (void)setIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon;
- (void)setIconPositionWithRARERenderableDataItem_IconPositionEnum:(RARERenderableDataItem_IconPositionEnum *)position;
- (void)setOptionsWithJavaUtilMap:(id<JavaUtilMap>)options;
- (void)dispose;
- (void)setAlignmentWithRARERenderableDataItem_HorizontalAlignEnum:(RARERenderableDataItem_HorizontalAlignEnum *)ha
                      withRARERenderableDataItem_VerticalAlignEnum:(RARERenderableDataItem_VerticalAlignEnum *)va;
- (void)setMarginWithRAREUIInsets:(RAREUIInsets *)insets;
- (void)setOrientationWithRARERenderableDataItem_OrientationEnum:(RARERenderableDataItem_OrientationEnum *)o;
- (void)setWordWrapWithBoolean:(BOOL)wrap;
- (id<RAREiPlatformComponent>)getComponent;
- (void)clearRenderer;
- (void)setScaleIconWithBoolean:(BOOL)scale_
                      withFloat:(float)scaleFactor;
@end

#define ComAppnativaRareUiIRenderingComponent RAREiRenderingComponent

#endif // _RAREiRenderingComponent_H_
