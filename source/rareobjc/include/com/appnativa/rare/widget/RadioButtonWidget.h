//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/widget/RadioButtonWidget.java
//
//  Created by decoteaud on 9/15/15.
//

#ifndef _RARERadioButtonWidget_H_
#define _RARERadioButtonWidget_H_

@class RARESPOTButton;
@class RARESPOTWidget;
@protocol RAREiActionComponent;
@protocol RAREiContainer;

#import "JreEmulation.h"
#include "com/appnativa/rare/widget/aGroupableButton.h"

@interface RARERadioButtonWidget : RAREaGroupableButton {
 @public
  BOOL initiallySelected_RadioButtonWidget_;
}

- (id)initWithRAREiContainer:(id<RAREiContainer>)parent;
- (void)configureWithRARESPOTWidget:(RARESPOTWidget *)cfg;
- (id)getHTTPFormValue;
- (void)reset;
- (id<RAREiActionComponent>)createButtonWithRARESPOTButton:(RARESPOTButton *)cfg;
- (void)copyAllFieldsTo:(RARERadioButtonWidget *)other;
@end

typedef RARERadioButtonWidget ComAppnativaRareWidgetRadioButtonWidget;

#endif // _RARERadioButtonWidget_H_
