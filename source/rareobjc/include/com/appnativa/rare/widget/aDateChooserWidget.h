//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core-datechooser/com/appnativa/rare/widget/aDateChooserWidget.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RAREaDateChooserWidget_H_
#define _RAREaDateChooserWidget_H_

@class IOSObjectArray;
@class JavaUtilCalendar;
@class JavaUtilDate;
@class RAREDateChooserWidget;
@class RARESPOTDateChooser;
@class RARESPOTWidget;
@class RAREaWidgetListener;
@protocol RAREiActionComponent;
@protocol RAREiActionListener;
@protocol RAREiContainer;
@protocol RAREiDateViewManager;
@protocol RAREiPlatformComponent;
@protocol RAREiPopupMenuListener;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/iActionable.h"
#include "com/appnativa/rare/widget/aPlatformWidget.h"

@interface RAREaDateChooserWidget : RAREaPlatformWidget < RAREiActionable > {
 @public
  id<RAREiDateViewManager> dateViewManager_;
}

- (id)initWithRAREiContainer:(id<RAREiContainer>)parent;
- (void)addActionListenerWithRAREiActionListener:(id<RAREiActionListener>)l;
- (void)addPopupMenuListenerWithRAREiPopupMenuListener:(id<RAREiPopupMenuListener>)l;
- (void)clearContents;
- (void)configureWithRARESPOTDateChooser:(RARESPOTDateChooser *)cfg;
- (void)configureWithRARESPOTWidget:(RARESPOTWidget *)cfg;
+ (RAREDateChooserWidget *)createWithRAREiContainer:(id<RAREiContainer>)parent
                            withRARESPOTDateChooser:(RARESPOTDateChooser *)cfg;
- (void)dispose;
- (void)removeActionListenerWithRAREiActionListener:(id<RAREiActionListener>)l;
- (void)removePopupMenuListenerWithRAREiPopupMenuListener:(id<RAREiPopupMenuListener>)l;
- (void)setDateWithJavaUtilDate:(JavaUtilDate *)date;
- (BOOL)setDisplayedMonthWithInt:(int)year
                         withInt:(int)month
                         withInt:(int)index;
- (void)setMaxDateWithJavaUtilCalendar:(JavaUtilCalendar *)date;
- (void)setMaxDateWithJavaUtilDate:(JavaUtilDate *)date;
- (void)setMinDateWithJavaUtilCalendar:(JavaUtilCalendar *)date;
- (void)setMinDateWithJavaUtilDate:(JavaUtilDate *)date;
- (void)setValueWithId:(id)value;
- (void)setValueAsStringWithNSString:(NSString *)value;
- (JavaUtilCalendar *)getCalendar;
- (JavaUtilDate *)getDate;
- (id)getHTTPFormValue;
- (id)getSelection;
- (NSString *)getSelectionAsString;
- (IOSObjectArray *)getSelectionsAsStrings;
- (NSString *)getValueAsString;
- (void)configureWithRAREiDateViewManager:(id<RAREiDateViewManager>)dvm
                  withRARESPOTDateChooser:(RARESPOTDateChooser *)cfg;
- (void)configurePopupButtonWithRAREiActionComponent:(id<RAREiActionComponent>)ac
                             withRARESPOTDateChooser:(RARESPOTDateChooser *)cfg;
- (id<RAREiActionComponent>)createButtonWithRARESPOTDateChooser:(RARESPOTDateChooser *)cfg;
- (id<RAREiPlatformComponent>)createComboBoxWithRARESPOTDateChooser:(RARESPOTDateChooser *)cfg;
- (id<RAREiPlatformComponent>)createMultiPanelWithRARESPOTDateChooser:(RARESPOTDateChooser *)cfg;
- (id<RAREiPlatformComponent>)createSinglePanelWithRARESPOTDateChooser:(RARESPOTDateChooser *)cfg;
- (void)initializeListenersWithRAREaWidgetListener:(RAREaWidgetListener *)l OBJC_METHOD_FAMILY_NONE;
- (void)uninitializeListenersWithRAREaWidgetListener:(RAREaWidgetListener *)l;
- (void)setMinMaxDateWithJavaUtilCalendar:(JavaUtilCalendar *)date
                              withBoolean:(BOOL)min;
- (IOSObjectArray *)toStringArrayWithJavaUtilDateArray:(IOSObjectArray *)a;
- (void)copyAllFieldsTo:(RAREaDateChooserWidget *)other;
@end

J2OBJC_FIELD_SETTER(RAREaDateChooserWidget, dateViewManager_, id<RAREiDateViewManager>)

typedef RAREaDateChooserWidget ComAppnativaRareWidgetADateChooserWidget;

#endif // _RAREaDateChooserWidget_H_
