//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core-slider/com/appnativa/rare/widget/aSliderWidget.java
//
//  Created by decoteaud on 9/15/15.
//

#ifndef _RAREaSliderWidget_H_
#define _RAREaSliderWidget_H_

@class RAREBorderPanel;
@class RARESPOTSlider;
@class RARESPOTWidget;
@class RARESliderWidget;
@class RAREaSliderComponent;
@class RAREaWidgetListener;
@protocol JavaLangCharSequence;
@protocol RAREiActionComponent;
@protocol RAREiChangeListener;
@protocol RAREiContainer;
@protocol RAREiPlatformIcon;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/iChangeable.h"
#include "com/appnativa/rare/widget/aPlatformWidget.h"

@interface RAREaSliderWidget : RAREaPlatformWidget < RAREiChangeable > {
 @public
  int initialValue_;
  RAREBorderPanel *borderPanel_;
  id<RAREiActionComponent> leftLabel_;
  id<RAREiActionComponent> rightLabel_;
  RAREaSliderComponent *slider_;
}

- (id)initWithRAREiContainer:(id<RAREiContainer>)parent;
- (void)addChangeListenerWithRAREiChangeListener:(id<RAREiChangeListener>)l;
- (void)clearContents;
- (void)configureWithRARESPOTWidget:(RARESPOTWidget *)cfg;
+ (RARESliderWidget *)createWithRAREiContainer:(id<RAREiContainer>)parent
                            withRARESPOTSlider:(RARESPOTSlider *)cfg;
- (void)dispose;
- (void)removeChangeListenerWithRAREiChangeListener:(id<RAREiChangeListener>)l;
- (void)reset;
- (void)setHorizontalWithBoolean:(BOOL)horizontal;
- (void)setLeftIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon;
- (void)setLeftTextWithJavaLangCharSequence:(id<JavaLangCharSequence>)text;
- (void)setMajorTickSpacingWithInt:(int)value;
- (void)setMaxValueWithInt:(int)value;
- (void)setMaximumWithInt:(int)value;
- (void)setMinValueWithInt:(int)value;
- (void)setMinimumWithInt:(int)value;
- (void)setMinorTickSpacingWithInt:(int)value;
- (void)setRightIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon;
- (void)setRightTextWithJavaLangCharSequence:(id<JavaLangCharSequence>)text;
- (void)setShowTicksWithBoolean:(BOOL)show;
- (void)setValueWithInt:(int)value;
- (void)setValueWithId:(id)value;
- (id<RAREiPlatformIcon>)getLeftIcon;
- (id<RAREiActionComponent>)getLeftLabelComponent;
- (NSString *)getLeftText;
- (float)getMaxValue;
- (float)getMaximum;
- (float)getMinValue;
- (float)getMinimum;
- (id<RAREiPlatformIcon>)getRightIcon;
- (id<RAREiActionComponent>)getRightLabelComponent;
- (NSString *)getRightText;
- (id)getSelection;
- (id)getValue;
- (double)getValueAsDouble;
- (int)getValueAsInt;
- (BOOL)isHorizontal;
- (void)configureExWithRARESPOTSlider:(RARESPOTSlider *)cfg;
- (void)configureLabelWithRAREiActionComponent:(id<RAREiActionComponent>)l
                                  withNSString:(NSString *)text
                         withRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon
                                   withBoolean:(BOOL)left;
- (id<RAREiActionComponent>)createLabel;
- (RAREaSliderComponent *)createSliderAndComponentsWithRARESPOTSlider:(RARESPOTSlider *)cfg;
- (void)initializeListenersWithRAREaWidgetListener:(RAREaWidgetListener *)l OBJC_METHOD_FAMILY_NONE;
- (void)copyAllFieldsTo:(RAREaSliderWidget *)other;
@end

J2OBJC_FIELD_SETTER(RAREaSliderWidget, borderPanel_, RAREBorderPanel *)
J2OBJC_FIELD_SETTER(RAREaSliderWidget, leftLabel_, id<RAREiActionComponent>)
J2OBJC_FIELD_SETTER(RAREaSliderWidget, rightLabel_, id<RAREiActionComponent>)
J2OBJC_FIELD_SETTER(RAREaSliderWidget, slider_, RAREaSliderComponent *)

typedef RAREaSliderWidget ComAppnativaRareWidgetASliderWidget;

#endif // _RAREaSliderWidget_H_
