//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/renderer/aRendererContainer.java
//
//  Created by decoteaud on 12/8/15.
//

#ifndef _RAREaRendererContainer_H_
#define _RAREaRendererContainer_H_

@class RAREColumn;
@class RARERenderableDataItem;
@class RARERenderableDataItem_HorizontalAlignEnum;
@class RARERenderableDataItem_IconPositionEnum;
@class RARERenderableDataItem_OrientationEnum;
@class RARERenderableDataItem_VerticalAlignEnum;
@class RAREUIColor;
@class RAREUIDimension;
@class RAREUIFont;
@class RAREUIInsets;
@class RAREView;
@protocol JavaLangCharSequence;
@protocol JavaUtilMap;
@protocol RAREiPlatformComponent;
@protocol RAREiPlatformIcon;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/XPContainer.h"
#include "com/appnativa/rare/ui/iPlatformRenderingComponent.h"

@interface RAREaRendererContainer : RAREXPContainer < RAREiPlatformRenderingComponent > {
 @public
  id<RAREiPlatformRenderingComponent> renderingComponent_;
  int columnWidth_;
}

- (id)initWithRAREiPlatformRenderingComponent:(id<RAREiPlatformRenderingComponent>)rc;
- (void)dispose;
- (id<RAREiPlatformRenderingComponent>)newCopy OBJC_METHOD_FAMILY_NONE;
- (void)setAlignmentWithRARERenderableDataItem_HorizontalAlignEnum:(RARERenderableDataItem_HorizontalAlignEnum *)ha
                      withRARERenderableDataItem_VerticalAlignEnum:(RARERenderableDataItem_VerticalAlignEnum *)va;
- (void)setEnabledWithBoolean:(BOOL)enabled;
- (void)clearRenderer;
- (void)setFontWithRAREUIFont:(RAREUIFont *)font;
- (void)setForegroundWithRAREUIColor:(RAREUIColor *)fg;
- (void)setIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon;
- (void)setIconPositionWithRARERenderableDataItem_IconPositionEnum:(RARERenderableDataItem_IconPositionEnum *)position;
- (void)setMarginWithRAREUIInsets:(RAREUIInsets *)insets;
- (void)setOptionsWithJavaUtilMap:(id<JavaUtilMap>)options;
- (void)setOrientationWithRARERenderableDataItem_OrientationEnum:(RARERenderableDataItem_OrientationEnum *)o;
- (void)setWordWrapWithBoolean:(BOOL)wrap;
- (void)setColumnWidthWithInt:(int)width;
- (id<RAREiPlatformComponent>)getComponent;
- (id<RAREiPlatformComponent>)getComponentWithJavaLangCharSequence:(id<JavaLangCharSequence>)value
                                        withRARERenderableDataItem:(RARERenderableDataItem *)item;
- (id<RAREiPlatformComponent>)getComponentWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)list
                                                              withId:(id)value
                                          withRARERenderableDataItem:(RARERenderableDataItem *)item
                                                             withInt:(int)row
                                                         withBoolean:(BOOL)isSelected
                                                         withBoolean:(BOOL)hasFocus
                                                      withRAREColumn:(RAREColumn *)col
                                          withRARERenderableDataItem:(RARERenderableDataItem *)rowItem
                                                         withBoolean:(BOOL)handleAll;
- (void)getMinimumSizeExWithRAREUIDimension:(RAREUIDimension *)size
                                  withFloat:(float)maxWidth;
- (void)getPreferredSizeExWithRAREUIDimension:(RAREUIDimension *)size
                                    withFloat:(float)maxWidth;
- (id)createNewNativeView;
- (void)prepareForReuseWithInt:(int)param0
                       withInt:(int)param1;
- (void)setNativeViewWithId:(id)param0;
- (void)setRenderingViewWithRAREView:(RAREView *)param0;
- (void)copyAllFieldsTo:(RAREaRendererContainer *)other;
@end

J2OBJC_FIELD_SETTER(RAREaRendererContainer, renderingComponent_, id<RAREiPlatformRenderingComponent>)

typedef RAREaRendererContainer ComAppnativaRareUiRendererARendererContainer;

#endif // _RAREaRendererContainer_H_
