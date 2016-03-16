//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/spot/ListBox.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RARESPOTListBox_H_
#define _RARESPOTListBox_H_

@class IOSIntArray;
@class IOSObjectArray;
@class JavaLangInteger;
@class RARESPOTGridCell;
@class RARESPOTItemDescription;
@class RARESPOTListBox_CDividerLineStyle;
@class RARESPOTListBox_CEditingMode;
@class RARESPOTListBox_CSelectionMode;
@class RARESPOTListBox_CSubmitValue;
@class RARESPOTScrollPane;
@class SPOTBoolean;
@class SPOTInteger;
@class SPOTPrintableString;
@protocol iSPOTElement;

#import "JreEmulation.h"
#include "com/appnativa/rare/spot/Viewer.h"
#include "com/appnativa/spot/SPOTEnumerated.h"

@interface RARESPOTListBox : RARESPOTViewer {
 @public
  RARESPOTListBox_CSelectionMode *selectionMode_;
  RARESPOTItemDescription *itemDescription_;
  SPOTInteger *selectedIndex_;
  SPOTBoolean *handleFirstFocusSelection_;
  RARESPOTListBox_CSubmitValue *submitValue_;
  SPOTInteger *minVisibleRowCount_;
  SPOTInteger *visibleRowCount_;
  SPOTBoolean *changeSelColorOnLostFocus_;
  SPOTPrintableString *alternatingHighlightColor_;
  SPOTBoolean *deselectEventsEnabled_;
  SPOTBoolean *itemCursorsSupported_;
  SPOTBoolean *autoSizeRowsToFit_;
  RARESPOTListBox_CEditingMode *editingMode_;
  SPOTBoolean *showDividerLine_;
  RARESPOTListBox_CDividerLineStyle *dividerLineStyle_;
  SPOTPrintableString *dividerLineColor_;
  SPOTBoolean *singleClickActionEnabled_;
  SPOTBoolean *indexForFiltering_;
  RARESPOTGridCell *indexSectionPainter_;
  SPOTPrintableString *rowHeight_;
  SPOTPrintableString *cellWidth_;
  SPOTPrintableString *selectionGroupName_;
  SPOTBoolean *overlapAutoToolTips_;
  RARESPOTScrollPane *scrollPane_;
  RARESPOTGridCell *selectionPainter_;
  RARESPOTGridCell *lostFocusSelectionPainter_;
}

- (id)init;
- (id)initWithBoolean:(BOOL)optional;
- (id)initWithBoolean:(BOOL)optional
          withBoolean:(BOOL)setElements;
- (void)spot_setElements;
- (RARESPOTItemDescription *)getItemDescription;
- (RARESPOTItemDescription *)getItemDescriptionReference;
- (void)setItemDescriptionWithISPOTElement:(id<iSPOTElement>)reference;
- (RARESPOTGridCell *)getIndexSectionPainter;
- (RARESPOTGridCell *)getIndexSectionPainterReference;
- (void)setIndexSectionPainterWithISPOTElement:(id<iSPOTElement>)reference;
- (RARESPOTScrollPane *)getScrollPane;
- (RARESPOTScrollPane *)getScrollPaneReference;
- (void)setScrollPaneWithISPOTElement:(id<iSPOTElement>)reference;
- (RARESPOTGridCell *)getSelectionPainter;
- (RARESPOTGridCell *)getSelectionPainterReference;
- (void)setSelectionPainterWithISPOTElement:(id<iSPOTElement>)reference;
- (RARESPOTGridCell *)getLostFocusSelectionPainter;
- (RARESPOTGridCell *)getLostFocusSelectionPainterReference;
- (void)setLostFocusSelectionPainterWithISPOTElement:(id<iSPOTElement>)reference;
- (void)copyAllFieldsTo:(RARESPOTListBox *)other;
@end

J2OBJC_FIELD_SETTER(RARESPOTListBox, selectionMode_, RARESPOTListBox_CSelectionMode *)
J2OBJC_FIELD_SETTER(RARESPOTListBox, itemDescription_, RARESPOTItemDescription *)
J2OBJC_FIELD_SETTER(RARESPOTListBox, selectedIndex_, SPOTInteger *)
J2OBJC_FIELD_SETTER(RARESPOTListBox, handleFirstFocusSelection_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTListBox, submitValue_, RARESPOTListBox_CSubmitValue *)
J2OBJC_FIELD_SETTER(RARESPOTListBox, minVisibleRowCount_, SPOTInteger *)
J2OBJC_FIELD_SETTER(RARESPOTListBox, visibleRowCount_, SPOTInteger *)
J2OBJC_FIELD_SETTER(RARESPOTListBox, changeSelColorOnLostFocus_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTListBox, alternatingHighlightColor_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTListBox, deselectEventsEnabled_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTListBox, itemCursorsSupported_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTListBox, autoSizeRowsToFit_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTListBox, editingMode_, RARESPOTListBox_CEditingMode *)
J2OBJC_FIELD_SETTER(RARESPOTListBox, showDividerLine_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTListBox, dividerLineStyle_, RARESPOTListBox_CDividerLineStyle *)
J2OBJC_FIELD_SETTER(RARESPOTListBox, dividerLineColor_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTListBox, singleClickActionEnabled_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTListBox, indexForFiltering_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTListBox, indexSectionPainter_, RARESPOTGridCell *)
J2OBJC_FIELD_SETTER(RARESPOTListBox, rowHeight_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTListBox, cellWidth_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTListBox, selectionGroupName_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTListBox, overlapAutoToolTips_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTListBox, scrollPane_, RARESPOTScrollPane *)
J2OBJC_FIELD_SETTER(RARESPOTListBox, selectionPainter_, RARESPOTGridCell *)
J2OBJC_FIELD_SETTER(RARESPOTListBox, lostFocusSelectionPainter_, RARESPOTGridCell *)

typedef RARESPOTListBox ComAppnativaRareSpotListBox;

#define RARESPOTListBox_CSelectionMode_block 3
#define RARESPOTListBox_CSelectionMode_invisible 5
#define RARESPOTListBox_CSelectionMode_multiple 2
#define RARESPOTListBox_CSelectionMode_none 0
#define RARESPOTListBox_CSelectionMode_single 1
#define RARESPOTListBox_CSelectionMode_single_auto 4

@interface RARESPOTListBox_CSelectionMode : SPOTEnumerated {
}

+ (int)none;
+ (int)single;
+ (int)multiple;
+ (int)block;
+ (int)single_auto;
+ (int)invisible;
+ (IOSIntArray *)_nchoices;
+ (IOSObjectArray *)_schoices;
- (id)init;
- (id)initWithInt:(int)val;
- (id)initWithJavaLangInteger:(JavaLangInteger *)ival
                 withNSString:(NSString *)sval
          withJavaLangInteger:(JavaLangInteger *)idefaultval
                 withNSString:(NSString *)sdefaultval
                  withBoolean:(BOOL)optional;
- (NSString *)spot_getValidityRange;
@end

#define RARESPOTListBox_CSubmitValue_checked_index 5
#define RARESPOTListBox_CSubmitValue_checked_linked_data 6
#define RARESPOTListBox_CSubmitValue_checked_value_text 4
#define RARESPOTListBox_CSubmitValue_selected_index 3
#define RARESPOTListBox_CSubmitValue_selected_linked_data 2
#define RARESPOTListBox_CSubmitValue_selected_value 1
#define RARESPOTListBox_CSubmitValue_selected_value_text 0

@interface RARESPOTListBox_CSubmitValue : SPOTEnumerated {
}

+ (int)selected_value_text;
+ (int)selected_value;
+ (int)selected_linked_data;
+ (int)selected_index;
+ (int)checked_value_text;
+ (int)checked_index;
+ (int)checked_linked_data;
+ (IOSIntArray *)_nchoices;
+ (IOSObjectArray *)_schoices;
- (id)init;
- (id)initWithInt:(int)val;
- (id)initWithJavaLangInteger:(JavaLangInteger *)ival
                 withNSString:(NSString *)sval
          withJavaLangInteger:(JavaLangInteger *)idefaultval
                 withNSString:(NSString *)sdefaultval
                  withBoolean:(BOOL)optional;
- (NSString *)spot_getValidityRange;
@end

#define RARESPOTListBox_CEditingMode_deleting 3
#define RARESPOTListBox_CEditingMode_none 0
#define RARESPOTListBox_CEditingMode_reordering 1
#define RARESPOTListBox_CEditingMode_reordering_and_deleting 5
#define RARESPOTListBox_CEditingMode_reordering_and_selection 4
#define RARESPOTListBox_CEditingMode_selection 2

@interface RARESPOTListBox_CEditingMode : SPOTEnumerated {
}

+ (int)none;
+ (int)reordering;
+ (int)selection;
+ (int)deleting;
+ (int)reordering_and_selection;
+ (int)reordering_and_deleting;
+ (IOSIntArray *)_nchoices;
+ (IOSObjectArray *)_schoices;
- (id)init;
- (id)initWithInt:(int)val;
- (id)initWithJavaLangInteger:(JavaLangInteger *)ival
                 withNSString:(NSString *)sval
          withJavaLangInteger:(JavaLangInteger *)idefaultval
                 withNSString:(NSString *)sdefaultval
                  withBoolean:(BOOL)optional;
- (NSString *)spot_getValidityRange;
- (void)spot_setAttributes;
@end

#define RARESPOTListBox_CDividerLineStyle_dashed 2
#define RARESPOTListBox_CDividerLineStyle_dotted 1
#define RARESPOTListBox_CDividerLineStyle_solid 3

@interface RARESPOTListBox_CDividerLineStyle : SPOTEnumerated {
}

+ (int)dotted;
+ (int)dashed;
+ (int)solid;
+ (IOSIntArray *)_nchoices;
+ (IOSObjectArray *)_schoices;
- (id)init;
- (id)initWithInt:(int)val;
- (id)initWithJavaLangInteger:(JavaLangInteger *)ival
                 withNSString:(NSString *)sval
          withJavaLangInteger:(JavaLangInteger *)idefaultval
                 withNSString:(NSString *)sdefaultval
                  withBoolean:(BOOL)optional;
- (NSString *)spot_getValidityRange;
@end

#endif // _RARESPOTListBox_H_
