//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/widget/CheckBoxWidget.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RARECheckBoxWidget_H_
#define _RARECheckBoxWidget_H_

@class RARESPOTButton;
@class RAREaCheckBoxWidget_StateEnum;
@protocol RAREiActionComponent;
@protocol RAREiContainer;

#import "JreEmulation.h"
#include "com/appnativa/rare/widget/aCheckBoxWidget.h"

@interface RARECheckBoxWidget : RAREaCheckBoxWidget {
}

- (id)initWithRAREiContainer:(id<RAREiContainer>)parent;
- (void)setStateWithRAREaCheckBoxWidget_StateEnum:(RAREaCheckBoxWidget_StateEnum *)state;
- (RAREaCheckBoxWidget_StateEnum *)getState;
- (id<RAREiActionComponent>)createButtonWithRARESPOTButton:(RARESPOTButton *)cfg;
- (void)postConfigureWithRARESPOTButton:(RARESPOTButton *)vcfg;
@end

typedef RARECheckBoxWidget ComAppnativaRareWidgetCheckBoxWidget;

#endif // _RARECheckBoxWidget_H_
