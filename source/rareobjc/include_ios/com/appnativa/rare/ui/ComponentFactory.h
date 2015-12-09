//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/ios/com/appnativa/rare/ui/ComponentFactory.java
//
//  Created by decoteaud on 12/8/15.
//

#ifndef _RAREComponentFactory_H_
#define _RAREComponentFactory_H_

@class IOSClass;
@class RARECheckBoxView;
@class RARELabelView;
@class RARELineView;
@class RAREListView;
@class RARERadioButtonView;
@class RARESPOTBean;
@class RARESPOTCheckBox;
@class RARESPOTCollapsibleInfo;
@class RARESPOTDocumentPane;
@class RARESPOTLabel;
@class RARESPOTLine;
@class RARESPOTListBox;
@class RARESPOTPasswordField;
@class RARESPOTPushButton;
@class RARESPOTRadioButton;
@class RARESPOTTextField;
@class RARETextAreaView;
@class RARETextFieldView;
@class RAREView;
@protocol RAREiCollapsible;
@protocol RAREiPlatformAppContext;
@protocol RAREiPlatformTextEditor;
@protocol RAREiWidget;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/iPlatformComponentFactory.h"

@interface RAREComponentFactory : NSObject < RAREiPlatformComponentFactory > {
 @public
  id<RAREiPlatformAppContext> appContext_;
  IOSClass *collapsibleClass_;
}

- (void)setAppContextWithRAREiPlatformAppContext:(id<RAREiPlatformAppContext>)app;
- (id)getBeanWithRAREiWidget:(id<RAREiWidget>)context
            withRARESPOTBean:(RARESPOTBean *)cfg;
- (RAREView *)getButtonWithRAREiWidget:(id<RAREiWidget>)context
                withRARESPOTPushButton:(RARESPOTPushButton *)cfg;
- (RARECheckBoxView *)getCheckBoxWithRAREiWidget:(id<RAREiWidget>)context
                            withRARESPOTCheckBox:(RARESPOTCheckBox *)cfg;
- (id<RAREiCollapsible>)getCollapsibleWithRAREiWidget:(id<RAREiWidget>)context
                          withRARESPOTCollapsibleInfo:(RARESPOTCollapsibleInfo *)cfg;
- (id<RAREiPlatformTextEditor>)getDocumentPaneWithRAREiWidget:(id<RAREiWidget>)widget
                                     withRARESPOTDocumentPane:(RARESPOTDocumentPane *)cfg;
- (RARELabelView *)getLabelWithRAREiWidget:(id<RAREiWidget>)context
                         withRARESPOTLabel:(RARESPOTLabel *)cfg;
- (RARELineView *)getLineWithRAREiWidget:(id<RAREiWidget>)context
                        withRARESPOTLine:(RARESPOTLine *)cfg;
- (RAREListView *)getListWithRAREiWidget:(id<RAREiWidget>)context
                     withRARESPOTListBox:(RARESPOTListBox *)cfg;
- (RARETextFieldView *)getPasswordTextFieldWithRAREiWidget:(id<RAREiWidget>)context
                                 withRARESPOTPasswordField:(RARESPOTPasswordField *)cfg;
- (RARERadioButtonView *)getRadioButtonWithRAREiWidget:(id<RAREiWidget>)context
                               withRARESPOTRadioButton:(RARESPOTRadioButton *)cfg;
- (RARETextAreaView *)getTextAreaWithRAREiWidget:(id<RAREiWidget>)context
                           withRARESPOTTextField:(RARESPOTTextField *)cfg;
- (RARETextFieldView *)getTextFieldWithRAREiWidget:(id<RAREiWidget>)context
                             withRARESPOTTextField:(RARESPOTTextField *)cfg;
- (RAREView *)getToolbarButtonWithRAREiWidget:(id<RAREiWidget>)context
                       withRARESPOTPushButton:(RARESPOTPushButton *)cfg;
- (RAREView *)getButtonExWithRAREiWidget:(id<RAREiWidget>)context
                  withRARESPOTPushButton:(RARESPOTPushButton *)cfg
                             withBoolean:(BOOL)toolbar;
- (id)init;
- (void)copyAllFieldsTo:(RAREComponentFactory *)other;
@end

J2OBJC_FIELD_SETTER(RAREComponentFactory, appContext_, id<RAREiPlatformAppContext>)
J2OBJC_FIELD_SETTER(RAREComponentFactory, collapsibleClass_, IOSClass *)

typedef RAREComponentFactory ComAppnativaRareUiComponentFactory;

#endif // _RAREComponentFactory_H_
