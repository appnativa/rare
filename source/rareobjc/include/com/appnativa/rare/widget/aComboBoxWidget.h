//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core-combobox/com/appnativa/rare/widget/aComboBoxWidget.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREaComboBoxWidget_H_
#define _RAREaComboBoxWidget_H_

@class RAREActionEvent;
@class RAREComboBoxWidget;
@class RARERenderableDataItem;
@class RARERenderableDataItem_HorizontalAlignEnum;
@class RARESPOTComboBox;
@class RARESPOTWidget;
@class RAREUIColor;
@class RAREUIDimension;
@class RAREUIRectangle;
@class RAREaComboBoxComponent;
@class RAREaWidgetListener;
@protocol JavaUtilMap;
@protocol RAREiActionComponent;
@protocol RAREiComboBox;
@protocol RAREiContainer;
@protocol RAREiPlatformBorder;
@protocol RAREiPlatformComponent;
@protocol RAREiPlatformIcon;
@protocol RAREiPlatformItemRenderer;
@protocol RAREiPlatformListDataModel;
@protocol RAREiPlatformListHandler;
@protocol RAREiPopupMenuListener;
@protocol RAREiWidget;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/aNonListListHandler.h"
#include "com/appnativa/rare/ui/event/iActionListener.h"
#include "com/appnativa/rare/widget/aListWidget.h"

@interface RAREaComboBoxWidget : RAREaListWidget {
 @public
  BOOL hideWhenReset_;
  id<RAREiPlatformListDataModel> listModel_;
  NSString *originalValue_;
  float popupFraction_;
  NSString *popupHeight_;
  id<RAREiWidget> popupWidget_;
  NSString *popupX_;
  NSString *popupY_;
  NSString *valueAttribute_;
  RARERenderableDataItem_HorizontalAlignEnum *popupHorizontalAlignment_;
  id<RAREiWidget> valueWidget_;
}

- (id)init;
- (id)initWithRAREiContainer:(id<RAREiContainer>)parent;
- (void)addParsedRowWithRARERenderableDataItem:(RARERenderableDataItem *)row;
- (void)addPopupMenuListenerWithRAREiPopupMenuListener:(id<RAREiPopupMenuListener>)l;
- (void)cancelPopup;
- (void)configureWithRARESPOTWidget:(RARESPOTWidget *)cfg;
- (BOOL)isKeepSelectionVisible;
- (void)setKeepSelectionVisibleWithBoolean:(BOOL)keepSelectionVisible;
+ (RAREComboBoxWidget *)createWithRAREiContainer:(id<RAREiContainer>)parent
                            withRARESPOTComboBox:(RARESPOTComboBox *)cfg;
- (void)dispose;
- (id)removeSelectedDataWithBoolean:(BOOL)returnData;
- (void)removePopupMenuListenerWithRAREiPopupMenuListener:(id<RAREiPopupMenuListener>)l;
- (void)reset;
- (void)rowChangedWithInt:(int)index;
- (void)rowChangedWithRARERenderableDataItem:(RARERenderableDataItem *)item;
- (void)rowsChangedWithInt:(int)firstRow
                   withInt:(int)lastRow;
- (void)setAlternatingRowColorWithRAREUIColor:(RAREUIColor *)color;
- (void)setAutoHilightWithBoolean:(BOOL)autoHilight;
- (void)setDataEventsEnabledWithBoolean:(BOOL)enabled;
- (void)setDeselectEventsDisabledWithBoolean:(BOOL)disabled;
- (void)setEditableWithBoolean:(BOOL)editable;
- (void)setEmptyFieldTextWithNSString:(NSString *)text;
- (void)setFromHTTPFormValueWithId:(id)value;
- (void)setHideWhenResetWithBoolean:(BOOL)hide;
- (void)setPopupBorderWithRAREiPlatformBorder:(id<RAREiPlatformBorder>)b;
- (void)setPopupFractionWithFloat:(float)fraction;
- (void)setPopupHorizontalAlignmentWithRARERenderableDataItem_HorizontalAlignEnum:(RARERenderableDataItem_HorizontalAlignEnum *)popupHorizontalAlignment;
- (void)setPopupVisibleWithBoolean:(BOOL)visible;
- (void)setShowDividerWithBoolean:(BOOL)show;
- (void)setSingleClickActionWithBoolean:(BOOL)singleClick;
- (void)setUseDialogButtonWithBoolean:(BOOL)dialog;
- (void)setValueWithId:(id)value;
- (RAREUIColor *)getAlternatingRowColor;
- (int)getFirstVisibleIndex;
- (id)getHTTPFormValue;
- (int)getLastVisibleIndex;
- (id<RAREiActionComponent>)getPopupButton;
- (RARERenderableDataItem_HorizontalAlignEnum *)getPopupHorizontalAlignment;
- (id<RAREiWidget>)getPopupWidget;
- (void)getWillBecomeVisibleBoundsWithRAREUIRectangle:(RAREUIRectangle *)rect;
- (void)getProposedPopupBoundsWithRAREUIDimension:(RAREUIDimension *)contentSize
                              withRAREUIRectangle:(RAREUIRectangle *)r;
- (int)getRowHeight;
- (RARERenderableDataItem *)getSelectedItem;
- (id)getSelection;
- (int)getTextLength;
- (NSString *)getValueAsString;
- (BOOL)hasPopupWidget;
- (BOOL)isButtonVisible;
- (BOOL)isDataEventsEnabled;
- (BOOL)isEditable;
- (BOOL)isHideWhenReset;
- (BOOL)isPopupVisible;
- (void)initializeListenersWithRAREaWidgetListener:(RAREaWidgetListener *)listener OBJC_METHOD_FAMILY_NONE;
- (void)configureExWithRARESPOTComboBox:(RARESPOTComboBox *)cfg;
- (void)configurePopupActionWidgetsWithRAREiWidget:(id<RAREiWidget>)ok
                                   withRAREiWidget:(id<RAREiWidget>)cancel;
- (id<RAREiPlatformListHandler>)createListHandlerWithRAREaComboBoxComponent:(RAREaComboBoxComponent *)cb
                                                       withRARESPOTComboBox:(RARESPOTComboBox *)cfg;
- (RAREaComboBoxComponent *)createModelAndComponentsWithRARESPOTComboBox:(RARESPOTComboBox *)cfg;
- (void)createPopupWidgetWithRARESPOTWidget:(RARESPOTWidget *)cfg
                            withJavaUtilMap:(id<JavaUtilMap>)attributes;
- (void)setIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon;
- (void)handleInitialStuff;
- (void)setEditorValueWithId:(id)value;
- (void)setValueExWithId:(id)value;
- (id<RAREiComboBox>)getComboBox;
- (void)copyAllFieldsTo:(RAREaComboBoxWidget *)other;
@end

J2OBJC_FIELD_SETTER(RAREaComboBoxWidget, listModel_, id<RAREiPlatformListDataModel>)
J2OBJC_FIELD_SETTER(RAREaComboBoxWidget, originalValue_, NSString *)
J2OBJC_FIELD_SETTER(RAREaComboBoxWidget, popupHeight_, NSString *)
J2OBJC_FIELD_SETTER(RAREaComboBoxWidget, popupWidget_, id<RAREiWidget>)
J2OBJC_FIELD_SETTER(RAREaComboBoxWidget, popupX_, NSString *)
J2OBJC_FIELD_SETTER(RAREaComboBoxWidget, popupY_, NSString *)
J2OBJC_FIELD_SETTER(RAREaComboBoxWidget, valueAttribute_, NSString *)
J2OBJC_FIELD_SETTER(RAREaComboBoxWidget, popupHorizontalAlignment_, RARERenderableDataItem_HorizontalAlignEnum *)
J2OBJC_FIELD_SETTER(RAREaComboBoxWidget, valueWidget_, id<RAREiWidget>)

typedef RAREaComboBoxWidget ComAppnativaRareWidgetAComboBoxWidget;

@interface RAREaComboBoxWidget_WidgetComboBoxComponent : RAREaNonListListHandler {
 @public
  __weak id<RAREiPlatformComponent> component_;
}

- (id)initWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)component;
- (id<RAREiPlatformItemRenderer>)getItemRenderer;
- (id<RAREiPlatformComponent>)getListComponent;
- (RAREUIDimension *)getPreferredSize;
- (float)getPreferredWidth;
- (int)getRowHeight;
- (void)copyAllFieldsTo:(RAREaComboBoxWidget_WidgetComboBoxComponent *)other;
@end

@interface RAREaComboBoxWidget_$1 : NSObject < RAREiActionListener > {
 @public
  RAREaComboBoxWidget *this$0_;
}

- (void)actionPerformedWithRAREActionEvent:(RAREActionEvent *)e;
- (id)initWithRAREaComboBoxWidget:(RAREaComboBoxWidget *)outer$;
@end

J2OBJC_FIELD_SETTER(RAREaComboBoxWidget_$1, this$0_, RAREaComboBoxWidget *)

@interface RAREaComboBoxWidget_$2 : NSObject < RAREiActionListener > {
 @public
  RAREaComboBoxWidget *this$0_;
}

- (void)actionPerformedWithRAREActionEvent:(RAREActionEvent *)e;
- (id)initWithRAREaComboBoxWidget:(RAREaComboBoxWidget *)outer$;
@end

J2OBJC_FIELD_SETTER(RAREaComboBoxWidget_$2, this$0_, RAREaComboBoxWidget *)

#endif // _RAREaComboBoxWidget_H_
