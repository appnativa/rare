//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core-spinner/com/appnativa/rare/widget/aSpinnerWidget.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREaSpinnerWidget_H_
#define _RAREaSpinnerWidget_H_

@class JavaTextDateFormat;
@class JavaUtilDate;
@class RARESPOTNumberSpinner;
@class RARESPOTSpinner;
@class RARESPOTWidget;
@class RAREaWidgetListener;
@protocol RAREiChangeListener;
@protocol RAREiContainer;
@protocol RAREiSpinner;
@protocol RAREiSpinnerEditor;
@protocol RAREiSpinnerModel;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/iChangeable.h"
#include "com/appnativa/rare/widget/aPlatformWidget.h"

@interface RAREaSpinnerWidget : RAREaPlatformWidget < RAREiChangeable > {
 @public
  BOOL changeEventsEnabled_;
  id initialValue_;
  id<RAREiSpinner> spinner_;
}

- (id)initWithRAREiContainer:(id<RAREiContainer>)parent;
- (void)addChangeListenerWithRAREiChangeListener:(id<RAREiChangeListener>)l;
- (void)clearContents;
- (void)commitEdit;
- (void)configureWithRARESPOTSpinner:(RARESPOTSpinner *)cfg;
- (void)configureWithRARESPOTWidget:(RARESPOTWidget *)cfg;
- (id<RAREiSpinnerModel>)getModel;
- (id)getNextValue;
- (id)getPreviousValue;
- (int)getSelectedIndex;
- (id)getSelection;
- (int)getValueAsInt;
- (BOOL)isButtonsSideBySide;
- (BOOL)isButtonsVisible;
- (BOOL)isChangeEventsEnabled;
- (BOOL)isEditorVisible;
- (void)makeButtonsToolbarStyle;
- (BOOL)nextValue;
- (BOOL)previousValue;
- (void)removeChangeListenerWithRAREiChangeListener:(id<RAREiChangeListener>)l;
- (id)removeSelectedDataWithBoolean:(BOOL)returnData;
- (void)reset;
- (void)setButtonsSideBySideWithBoolean:(BOOL)sideBySide;
- (void)setButtonsVisibleWithBoolean:(BOOL)visible;
- (void)setChangeEventsEnabledWithBoolean:(BOOL)enabled;
- (void)setContinuousActionWithBoolean:(BOOL)continuous;
- (void)setEditorWithRAREiSpinnerEditor:(id<RAREiSpinnerEditor>)editor;
- (void)setEditorVisibleWithBoolean:(BOOL)visible;
- (void)setIncrementWithNSNumber:(NSNumber *)increment;
- (void)selectAll;
- (void)setMaximumWithJavaUtilDate:(JavaUtilDate *)maximum;
- (void)setMaximumWithDouble:(double)maximum;
- (void)setMinimumWithJavaUtilDate:(JavaUtilDate *)minimum;
- (void)setMinimumWithDouble:(double)minimum;
- (void)setModelWithRAREiSpinnerModel:(id<RAREiSpinnerModel>)model;
- (void)setSelectedIndexWithInt:(int)index;
- (void)setValueWithId:(id)value;
- (void)setVisibleCharactersWithInt:(int)count;
- (void)swapButtonIcons;
- (id<RAREiSpinner>)createSpinnerAndComponentsWithRARESPOTSpinner:(RARESPOTSpinner *)cfg;
- (void)initializeListenersWithRAREaWidgetListener:(RAREaWidgetListener *)listener OBJC_METHOD_FAMILY_NONE;
- (void)registerEditorWithWidgetWithRAREiSpinnerEditor:(id<RAREiSpinnerEditor>)editor;
- (void)setupDateSpinnerWithRAREiSpinner:(id<RAREiSpinner>)spinner
                        withJavaUtilDate:(JavaUtilDate *)min
                        withJavaUtilDate:(JavaUtilDate *)max
                        withJavaUtilDate:(JavaUtilDate *)value
                            withNSString:(NSString *)format
                  withJavaTextDateFormat:(JavaTextDateFormat *)defaultdf
                     withRARESPOTSpinner:(RARESPOTSpinner *)cfg;
- (void)setupNumericSpinnerWithRAREiSpinner:(id<RAREiSpinner>)spinner
                  withRARESPOTNumberSpinner:(RARESPOTNumberSpinner *)cfg;
- (void)uninitializeListenersWithRAREaWidgetListener:(RAREaWidgetListener *)listener;
- (void)unregisterEditorWithWidgetWithRAREiSpinnerEditor:(id<RAREiSpinnerEditor>)editor;
- (void)copyAllFieldsTo:(RAREaSpinnerWidget *)other;
@end

J2OBJC_FIELD_SETTER(RAREaSpinnerWidget, initialValue_, id)
J2OBJC_FIELD_SETTER(RAREaSpinnerWidget, spinner_, id<RAREiSpinner>)

typedef RAREaSpinnerWidget ComAppnativaRareWidgetASpinnerWidget;

#endif // _RAREaSpinnerWidget_H_
