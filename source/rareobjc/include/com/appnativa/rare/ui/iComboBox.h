//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/iComboBox.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RAREiComboBox_H_
#define _RAREiComboBox_H_

@class RAREUIRectangle;
@protocol JavaLangCharSequence;
@protocol RAREiActionComponent;
@protocol RAREiPlatformBorder;
@protocol RAREiPlatformIcon;
@protocol RAREiPlatformTextEditor;
@protocol RAREiPopupMenuListener;

#import "JreEmulation.h"

@protocol RAREiComboBox < NSObject, JavaObject >
- (void)addPopupMenuListenerWithRAREiPopupMenuListener:(id<RAREiPopupMenuListener>)l;
- (void)cancelPopup;
- (void)removePopupMenuListenerWithRAREiPopupMenuListener:(id<RAREiPopupMenuListener>)l;
- (void)setButtonVisibleWithBoolean:(BOOL)visible;
- (void)setEditableWithBoolean:(BOOL)editable;
- (void)setEditorValueWithJavaLangCharSequence:(id<JavaLangCharSequence>)value;
- (void)setEditorIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon;
- (void)setPopupBorderWithRAREiPlatformBorder:(id<RAREiPlatformBorder>)b;
- (void)setPopupVisibleWithBoolean:(BOOL)visible;
- (void)setUseDialogButtonWithBoolean:(BOOL)dialog;
- (NSString *)getEditorValue;
- (id<RAREiPlatformTextEditor>)getTextEditor;
- (id<RAREiPlatformBorder>)getPopupBorder;
- (id<RAREiActionComponent>)getPopupButton;
- (BOOL)isButtonVisible;
- (BOOL)isEditable;
- (BOOL)isPopupVisible;
- (void)getWillBecomeVisibleBoundsWithRAREUIRectangle:(RAREUIRectangle *)rect;
@end

#define ComAppnativaRareUiIComboBox RAREiComboBox

#endif // _RAREiComboBox_H_
