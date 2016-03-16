//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/widget/aLineWidget.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREaLineWidget_H_
#define _RAREaLineWidget_H_

@class RAREBorderPanel;
@class RARESPOTLine;
@class RARESPOTWidget;
@class RAREaLineHelper;
@protocol JavaLangCharSequence;
@protocol RAREiActionComponent;
@protocol RAREiContainer;
@protocol RAREiPlatformComponent;
@protocol RAREiPlatformIcon;

#import "JreEmulation.h"
#include "com/appnativa/rare/widget/aPlatformWidget.h"

@interface RAREaLineWidget : RAREaPlatformWidget {
 @public
  RAREBorderPanel *borderPanel_;
  id<RAREiActionComponent> leftLabel_;
  RAREaLineHelper *lineHelper_;
  id<RAREiActionComponent> rightLabel_;
}

- (id)initWithRAREiContainer:(id<RAREiContainer>)parent;
- (void)configureWithRARESPOTWidget:(RARESPOTWidget *)cfg;
- (void)dispose;
- (void)setHorizontalWithBoolean:(BOOL)horizontal;
- (void)setLeftIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon;
- (void)setLeftTextWithJavaLangCharSequence:(id<JavaLangCharSequence>)text;
- (void)setRightIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon;
- (void)setRightTextWithJavaLangCharSequence:(id<JavaLangCharSequence>)text;
- (void)setValueWithId:(id)value;
- (id<RAREiPlatformIcon>)getRightIcon;
- (id<RAREiActionComponent>)getRightLabelComponent;
- (NSString *)getRightText;
- (id)getSelection;
- (BOOL)isHorizontal;
- (void)configureExWithRARESPOTLine:(RARESPOTLine *)cfg;
- (RAREBorderPanel *)createFormComponentWithBoolean:(BOOL)horizontal
                           withRAREiActionComponent:(id<RAREiActionComponent>)leftLabel
                         withRAREiPlatformComponent:(id<RAREiPlatformComponent>)line
                           withRAREiActionComponent:(id<RAREiActionComponent>)rightLabel;
- (id<RAREiActionComponent>)createLabelWithNSString:(NSString *)text
                              withRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon
                                        withBoolean:(BOOL)left;
- (RAREaLineHelper *)createLineHelperAndComponentsWithRARESPOTLine:(RARESPOTLine *)cfg;
- (void)copyAllFieldsTo:(RAREaLineWidget *)other;
@end

J2OBJC_FIELD_SETTER(RAREaLineWidget, borderPanel_, RAREBorderPanel *)
J2OBJC_FIELD_SETTER(RAREaLineWidget, leftLabel_, id<RAREiActionComponent>)
J2OBJC_FIELD_SETTER(RAREaLineWidget, lineHelper_, RAREaLineHelper *)
J2OBJC_FIELD_SETTER(RAREaLineWidget, rightLabel_, id<RAREiActionComponent>)

typedef RAREaLineWidget ComAppnativaRareWidgetALineWidget;

#endif // _RAREaLineWidget_H_
