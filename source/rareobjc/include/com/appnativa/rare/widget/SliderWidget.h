//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple-slider/com/appnativa/rare/widget/SliderWidget.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RARESliderWidget_H_
#define _RARESliderWidget_H_

@class RARESPOTSlider;
@class RAREaSliderComponent;
@protocol RAREiActionComponent;
@protocol RAREiContainer;

#import "JreEmulation.h"
#include "com/appnativa/rare/widget/aSliderWidget.h"

@interface RARESliderWidget : RAREaSliderWidget {
}

- (id)initWithRAREiContainer:(id<RAREiContainer>)parent;
- (id<RAREiActionComponent>)createLabel;
- (RAREaSliderComponent *)createSliderAndComponentsWithRARESPOTSlider:(RARESPOTSlider *)cfg;
+ (void)registerForUse;
@end

typedef RARESliderWidget ComAppnativaRareWidgetSliderWidget;

#endif // _RARESliderWidget_H_
