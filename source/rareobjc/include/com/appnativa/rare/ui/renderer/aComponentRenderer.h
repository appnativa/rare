//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/renderer/aComponentRenderer.java
//
//  Created by decoteaud on 12/8/15.
//

#ifndef _RAREaComponentRenderer_H_
#define _RAREaComponentRenderer_H_

@class RAREColumn;
@class RARERenderableDataItem;
@class RARERenderableDataItem_HorizontalAlignEnum;
@class RARERenderableDataItem_IconPositionEnum;
@class RARERenderableDataItem_OrientationEnum;
@class RARERenderableDataItem_VerticalAlignEnum;
@class RARESPOTWidget;
@class RAREUIColor;
@class RAREUIFont;
@class RAREUIInsets;
@class RAREView;
@protocol JavaLangCharSequence;
@protocol JavaUtilMap;
@protocol RAREiPlatformBorder;
@protocol RAREiPlatformComponent;
@protocol RAREiPlatformComponentPainter;
@protocol RAREiPlatformIcon;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/iPlatformRenderingComponent.h"

@interface RAREaComponentRenderer : NSObject < RAREiPlatformRenderingComponent > {
 @public
  RARESPOTWidget *config_;
  id<RAREiPlatformComponent> renderingComponent_;
}

- (id)init;
- (id)initWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp;
- (id)initWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp
                  withRARESPOTWidget:(RARESPOTWidget *)config;
- (void)dispose;
- (void)setAlignmentWithRARERenderableDataItem_HorizontalAlignEnum:(RARERenderableDataItem_HorizontalAlignEnum *)ha
                      withRARERenderableDataItem_VerticalAlignEnum:(RARERenderableDataItem_VerticalAlignEnum *)va;
- (void)setBackgroundWithRAREUIColor:(RAREUIColor *)bg;
- (void)setBorderWithRAREiPlatformBorder:(id<RAREiPlatformBorder>)b;
- (void)setColumnWidthWithInt:(int)width;
- (void)setComponentWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp;
- (void)setComponentPainterWithRAREiPlatformComponentPainter:(id<RAREiPlatformComponentPainter>)cp;
- (void)setEnabledWithBoolean:(BOOL)enabled;
- (void)setFontWithRAREUIFont:(RAREUIFont *)f;
- (void)setForegroundWithRAREUIColor:(RAREUIColor *)fg;
- (void)setIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon;
- (void)setScaleIconWithBoolean:(BOOL)scale_
                      withFloat:(float)scaleFactor;
- (void)setIconPositionWithRARERenderableDataItem_IconPositionEnum:(RARERenderableDataItem_IconPositionEnum *)position;
- (void)clearRenderer;
- (void)setMarginWithRAREUIInsets:(RAREUIInsets *)insets;
- (void)setOptionsWithJavaUtilMap:(id<JavaUtilMap>)options;
- (void)setOrientationWithRARERenderableDataItem_OrientationEnum:(RARERenderableDataItem_OrientationEnum *)o;
- (void)setTextAlignmentWithRARERenderableDataItem_HorizontalAlignEnum:(RARERenderableDataItem_HorizontalAlignEnum *)ha
                          withRARERenderableDataItem_VerticalAlignEnum:(RARERenderableDataItem_VerticalAlignEnum *)va;
- (void)setWordWrapWithBoolean:(BOOL)wrap;
- (id<RAREiPlatformComponent>)getComponent;
- (id<RAREiPlatformComponent>)getComponentWithJavaLangCharSequence:(id<JavaLangCharSequence>)value
                                        withRARERenderableDataItem:(RARERenderableDataItem *)item;
- (id<RAREiPlatformComponent>)getComponentWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp
                                                              withId:(id)value
                                          withRARERenderableDataItem:(RARERenderableDataItem *)item
                                                             withInt:(int)row
                                                         withBoolean:(BOOL)isSelected
                                                         withBoolean:(BOOL)hasFocus
                                                      withRAREColumn:(RAREColumn *)col
                                          withRARERenderableDataItem:(RARERenderableDataItem *)rowItem
                                                         withBoolean:(BOOL)handleAll;
- (id)createNewNativeView;
- (id<RAREiPlatformRenderingComponent>)newCopy OBJC_METHOD_FAMILY_NONE;
- (void)prepareForReuseWithInt:(int)param0
                       withInt:(int)param1;
- (void)setNativeViewWithId:(id)param0;
- (void)setRenderingViewWithRAREView:(RAREView *)param0;
- (void)copyAllFieldsTo:(RAREaComponentRenderer *)other;
@end

J2OBJC_FIELD_SETTER(RAREaComponentRenderer, config_, RARESPOTWidget *)
J2OBJC_FIELD_SETTER(RAREaComponentRenderer, renderingComponent_, id<RAREiPlatformComponent>)

typedef RAREaComponentRenderer ComAppnativaRareUiRendererAComponentRenderer;

#endif // _RAREaComponentRenderer_H_
