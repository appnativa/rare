//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/renderer/aListItemRenderer.java
//
//  Created by decoteaud on 12/8/15.
//

#ifndef _RAREaListItemRenderer_H_
#define _RAREaListItemRenderer_H_

@class RAREColumn;
@class RAREPaintBucket;
@class RARERenderableDataItem;
@class RAREUIColor;
@class RAREUIComponentPainter;
@class RAREUIFont;
@class RAREUIInsets;
@protocol JavaLangCharSequence;
@protocol RAREUTiStringConverter;
@protocol RAREiPlatformComponent;
@protocol RAREiPlatformIcon;
@protocol RAREiPlatformRenderingComponent;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/iPlatformItemRenderer.h"

@interface RAREaListItemRenderer : NSObject < RAREiPlatformItemRenderer > {
 @public
  BOOL selectionPainted_;
  BOOL paintRowBackground_;
  BOOL handlesSelection_;
  RAREPaintBucket *autoHilightPaint_;
  RAREUIComponentPainter *componentPainter_;
  id<RAREUTiStringConverter> converter_;
  id<RAREiPlatformIcon> delayedIcon_;
  RAREUIInsets *insets_;
  RAREUIInsets *insetsAdd_;
  RAREUIInsets *selectedInsets_;
  RAREColumn *itemDescription_;
  RAREPaintBucket *lostFocusSelectionPaint_;
  BOOL noicon_;
  BOOL alwaysCallSetBorder_;
  RAREPaintBucket *pressedPaint_;
  RAREPaintBucket *selectionPaint_;
  int hilightIndex_;
  BOOL hilightSelected_;
  BOOL ignoreSelection_;
}

+ (RARERenderableDataItem *)defaultItem;
+ (void)setDefaultItem:(RARERenderableDataItem *)defaultItem;
- (id)init;
- (id)initWithBoolean:(BOOL)handlesSelection;
- (id<JavaLangCharSequence>)configureRenderingComponentWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)list
                                              withRAREiPlatformRenderingComponent:(id<RAREiPlatformRenderingComponent>)rc
                                                       withRARERenderableDataItem:(RARERenderableDataItem *)item
                                                                          withInt:(int)row
                                                                      withBoolean:(BOOL)isSelected
                                                                      withBoolean:(BOOL)hasFocus
                                                                   withRAREColumn:(RAREColumn *)col
                                                       withRARERenderableDataItem:(RARERenderableDataItem *)rowItem;
- (void)dispose;
- (void)setAutoHilightPaintWithRAREPaintBucket:(RAREPaintBucket *)autoHilightPaint;
- (void)setConverterWithRAREUTiStringConverter:(id<RAREUTiStringConverter>)converter;
- (void)setDelayedIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)delayedIcon;
- (void)setIconAndAlignmentWithRAREiPlatformRenderingComponent:(id<RAREiPlatformRenderingComponent>)rc
                                    withRARERenderableDataItem:(RARERenderableDataItem *)item
                                    withRARERenderableDataItem:(RARERenderableDataItem *)row
                                                withRAREColumn:(RAREColumn *)col
                                                   withBoolean:(BOOL)enabled
                                                   withBoolean:(BOOL)center
                                                   withBoolean:(BOOL)top
                                                   withBoolean:(BOOL)seticon
                                                   withBoolean:(BOOL)expanded;
- (void)setInsetsWithRAREUIInsets:(RAREUIInsets *)insets;
- (void)setItemDescriptionWithRAREColumn:(RAREColumn *)itemDescription;
- (void)setLostFocusSelectionPaintWithRAREPaintBucket:(RAREPaintBucket *)pb;
- (void)setPaintRowBackgroundWithBoolean:(BOOL)useRowBackground;
- (void)setPressedPaintWithRAREPaintBucket:(RAREPaintBucket *)pb;
- (void)setSelectionPaintWithRAREPaintBucket:(RAREPaintBucket *)pb;
- (void)setSelectionPaintedWithBoolean:(BOOL)painted;
- (void)setHandlesSelectionWithBoolean:(BOOL)handlesSelection;
- (RAREPaintBucket *)getAutoHilightPaint;
- (id<RAREUTiStringConverter>)getConverter;
- (id<RAREiPlatformIcon>)getDelayedIcon;
- (RAREUIInsets *)getInsets;
- (RAREColumn *)getItemDescription;
- (RAREPaintBucket *)getLostFocusSelectionPaint;
- (RAREPaintBucket *)getPressedPaint;
- (RAREUIFont *)getSelectionFontWithBoolean:(BOOL)focused;
- (RAREUIColor *)getSelectionForegroundWithBoolean:(BOOL)focused;
- (RAREPaintBucket *)getSelectionPaint;
- (RAREPaintBucket *)getSelectionPaintForExternalPainterWithBoolean:(BOOL)focused;
- (RAREPaintBucket *)getSelectionPaintWithBoolean:(BOOL)focused;
- (BOOL)isPaintRowBackground;
- (BOOL)isSelectionPainted;
- (BOOL)isHandlesSelection;
- (RAREUIInsets *)getSelectesInsets;
- (BOOL)getHilightSelected;
- (void)setHilightSelectedWithBoolean:(BOOL)hilightSelected;
- (int)getHilightIndex;
- (void)setHilightIndexWithInt:(int)hilightIndex;
- (BOOL)isIgnoreSelection;
- (void)setIgnoreSelectionWithBoolean:(BOOL)ignoreSelection;
- (void)copyAllFieldsTo:(RAREaListItemRenderer *)other;
@end

J2OBJC_FIELD_SETTER(RAREaListItemRenderer, autoHilightPaint_, RAREPaintBucket *)
J2OBJC_FIELD_SETTER(RAREaListItemRenderer, componentPainter_, RAREUIComponentPainter *)
J2OBJC_FIELD_SETTER(RAREaListItemRenderer, converter_, id<RAREUTiStringConverter>)
J2OBJC_FIELD_SETTER(RAREaListItemRenderer, delayedIcon_, id<RAREiPlatformIcon>)
J2OBJC_FIELD_SETTER(RAREaListItemRenderer, insets_, RAREUIInsets *)
J2OBJC_FIELD_SETTER(RAREaListItemRenderer, insetsAdd_, RAREUIInsets *)
J2OBJC_FIELD_SETTER(RAREaListItemRenderer, selectedInsets_, RAREUIInsets *)
J2OBJC_FIELD_SETTER(RAREaListItemRenderer, itemDescription_, RAREColumn *)
J2OBJC_FIELD_SETTER(RAREaListItemRenderer, lostFocusSelectionPaint_, RAREPaintBucket *)
J2OBJC_FIELD_SETTER(RAREaListItemRenderer, pressedPaint_, RAREPaintBucket *)
J2OBJC_FIELD_SETTER(RAREaListItemRenderer, selectionPaint_, RAREPaintBucket *)

typedef RAREaListItemRenderer ComAppnativaRareUiRendererAListItemRenderer;

#endif // _RAREaListItemRenderer_H_
