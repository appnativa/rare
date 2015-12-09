//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core-datechooser/com/appnativa/rare/ui/calendar/DateComboBox.java
//
//  Created by decoteaud on 12/8/15.
//

#ifndef _RAREDateComboBox_H_
#define _RAREDateComboBox_H_

@class JavaUtilEventObject;
@class RAREDatePanel;
@class RAREDateViewManager;
@class RARESPOTWidget;
@class RAREUIDimension;
@class RAREUIRectangle;
@class RAREaListItemRenderer;
@class RAREaWidget;
@protocol RAREiDateViewManager;
@protocol RAREiPlatformComponent;
@protocol RAREiWidget;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/ComboBoxComponent.h"
#include "com/appnativa/rare/ui/aNonListListHandler.h"
#include "com/appnativa/rare/ui/event/iChangeListener.h"

@interface RAREDateComboBox : RAREComboBoxComponent < RAREiChangeListener > {
 @public
  RAREDatePanel *panel_;
  RAREDateViewManager *dateViewManager_;
}

- (id)initWithRAREiWidget:(id<RAREiWidget>)widget;
- (void)configurationCompletedWithRAREaWidget:(RAREaWidget *)w
                           withRARESPOTWidget:(RARESPOTWidget *)cfg;
- (void)dispose;
- (void)showPopup;
- (void)stateChangedWithJavaUtilEventObject:(JavaUtilEventObject *)e;
- (void)setContent;
- (id<RAREiDateViewManager>)getDateViewManager;
- (id<RAREiPlatformComponent>)getPanel;
- (id<RAREiWidget>)getPopupWidget;
- (void)getProposedPopupBoundsWithRAREUIRectangle:(RAREUIRectangle *)r;
- (void)copyAllFieldsTo:(RAREDateComboBox *)other;
@end

J2OBJC_FIELD_SETTER(RAREDateComboBox, panel_, RAREDatePanel *)
J2OBJC_FIELD_SETTER(RAREDateComboBox, dateViewManager_, RAREDateViewManager *)

typedef RAREDateComboBox ComAppnativaRareUiCalendarDateComboBox;

@interface RAREDateComboBox_DateComboBoxListHandler : RAREaNonListListHandler {
 @public
  RAREDateComboBox *this$0_;
}

- (id)initWithRAREDateComboBox:(RAREDateComboBox *)outer$;
- (void)clear;
- (void)setSelectedIndexWithInt:(int)index;
- (RAREaListItemRenderer *)getItemRenderer;
- (id<RAREiPlatformComponent>)getListComponent;
- (RAREUIDimension *)getPreferredSize;
- (float)getPreferredWidth;
- (int)getRowHeight;
@end

J2OBJC_FIELD_SETTER(RAREDateComboBox_DateComboBoxListHandler, this$0_, RAREDateComboBox *)

#endif // _RAREDateComboBox_H_
