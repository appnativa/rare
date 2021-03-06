//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/ui/renderer/UIFormsLayoutRenderer.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREUIFormsLayoutRenderer_H_
#define _RAREUIFormsLayoutRenderer_H_

@class RAREFormsView;
@class RARERenderableDataItem_HorizontalAlignEnum;
@class RARERenderableDataItem_VerticalAlignEnum;
@class RAREUIColor;
@class RAREUICompoundBorder;
@class RAREUIEmptyBorder;
@class RAREView;
@class RAREaListItemRenderer;
@protocol RAREiPlatformComponent;
@protocol RAREiPlatformRenderingComponent;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/renderer/aFormsLayoutRenderer.h"

@interface RAREUIFormsLayoutRenderer : RAREaFormsLayoutRenderer {
 @public
  RAREUICompoundBorder *cborder_;
  RAREUIEmptyBorder *paddingBorder_;
}

- (id)init;
- (id)initWithNSString:(NSString *)columns
          withNSString:(NSString *)rows;
- (id)createNewNativeView;
- (void)dispose;
- (void)clearRenderer;
- (id<RAREiPlatformRenderingComponent>)newCopy OBJC_METHOD_FAMILY_NONE;
- (void)prepareForReuseWithInt:(int)row
                       withInt:(int)column;
- (void)setAlignmentWithRARERenderableDataItem_HorizontalAlignEnum:(RARERenderableDataItem_HorizontalAlignEnum *)ha
                      withRARERenderableDataItem_VerticalAlignEnum:(RARERenderableDataItem_VerticalAlignEnum *)va;
- (void)setNativeViewWithId:(id)proxy;
- (void)setRenderingViewWithRAREView:(RAREView *)view;
- (id<RAREiPlatformRenderingComponent>)createComponentRendererWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c;
- (id<RAREiPlatformRenderingComponent>)createLabelRenderer;
- (RAREaListItemRenderer *)createListItemRenderer;
- (id<RAREiPlatformRenderingComponent>)createRendererWithNSString:(NSString *)className_;
- (void)copyAllFieldsTo:(RAREUIFormsLayoutRenderer *)other;
@end

J2OBJC_FIELD_SETTER(RAREUIFormsLayoutRenderer, cborder_, RAREUICompoundBorder *)
J2OBJC_FIELD_SETTER(RAREUIFormsLayoutRenderer, paddingBorder_, RAREUIEmptyBorder *)

typedef RAREUIFormsLayoutRenderer ComAppnativaRareUiRendererUIFormsLayoutRenderer;

@interface RAREUIFormsLayoutRenderer_$1 : RAREaFormsLayoutRenderer_FormsPanelEx {
}

- (void)setBackgroundWithRAREUIColor:(RAREUIColor *)bg;
- (id)initWithRAREFormsView:(RAREFormsView *)arg$0;
@end

#endif // _RAREUIFormsLayoutRenderer_H_
