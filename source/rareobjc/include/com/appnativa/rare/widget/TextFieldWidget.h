//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/widget/TextFieldWidget.java
//
//  Created by decoteaud on 12/8/15.
//

#ifndef _RARETextFieldWidget_H_
#define _RARETextFieldWidget_H_

@class RAREKeyboardReturnButtonTypeEnum;
@class RAREKeyboardTypeEnum;
@class RARESPOTPasswordField;
@class RARESPOTTextField;
@class RARESPOTWidget;
@class RAREaWidgetListener;
@protocol RAREiContainer;
@protocol RAREiPlatformComponent;
@protocol RAREiPlatformTextEditor;
@protocol RAREiViewer;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/iActionable.h"
#include "com/appnativa/rare/widget/aTextFieldWidget.h"

@interface RARETextFieldWidget : RAREaTextFieldWidget < RAREiActionable > {
}

- (id)initWithRAREiContainer:(id<RAREiContainer>)parent;
- (void)setKeyboardReturnButtonTypeWithRAREKeyboardReturnButtonTypeEnum:(RAREKeyboardReturnButtonTypeEnum *)type
                                                           withNSString:(NSString *)text
                                                            withBoolean:(BOOL)autoEnable;
- (void)setKeyboardTypeWithRAREKeyboardTypeEnum:(RAREKeyboardTypeEnum *)type;
- (void)setShowPasswordWithBoolean:(BOOL)show;
- (void)setVisibleCharactersWithInt:(int)characters;
- (void)configureExWithRARESPOTTextField:(RARESPOTTextField *)cfg;
- (void)configureMenusWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp
                              withRARESPOTWidget:(RARESPOTWidget *)cfg
                                     withBoolean:(BOOL)textMenus;
- (id<RAREiPlatformTextEditor>)createEditorAndComponentsWithRAREiViewer:(id<RAREiViewer>)viewer
                                              withRARESPOTPasswordField:(RARESPOTPasswordField *)cfg;
- (id<RAREiPlatformTextEditor>)createEditorAndComponentsWithRAREiViewer:(id<RAREiViewer>)viewer
                                                  withRARESPOTTextField:(RARESPOTTextField *)cfg;
- (void)initializeListenersWithRAREaWidgetListener:(RAREaWidgetListener *)l OBJC_METHOD_FAMILY_NONE;
@end

typedef RARETextFieldWidget ComAppnativaRareWidgetTextFieldWidget;

#endif // _RARETextFieldWidget_H_
