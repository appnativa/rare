//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/widget/LabelWidget.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RARELabelWidget_H_
#define _RARELabelWidget_H_

@class RARESPOTLabel;
@protocol RAREiActionComponent;
@protocol RAREiContainer;

#import "JreEmulation.h"
#include "com/appnativa/rare/widget/aLabelWidget.h"

@interface RARELabelWidget : RAREaLabelWidget {
}

- (id)initWithRAREiContainer:(id<RAREiContainer>)parent;
- (id<RAREiActionComponent>)createActionComponentWithRARESPOTLabel:(RARESPOTLabel *)cfg;
@end

typedef RARELabelWidget ComAppnativaRareWidgetLabelWidget;

#endif // _RARELabelWidget_H_