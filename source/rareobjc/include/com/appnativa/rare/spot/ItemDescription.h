//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/spot/ItemDescription.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RARESPOTItemDescription_H_
#define _RARESPOTItemDescription_H_

@class IOSIntArray;
@class IOSObjectArray;
@class JavaLangInteger;
@class RARERenderableDataItem_HorizontalAlignEnum;
@class RARERenderableDataItem_IconPositionEnum;
@class RARERenderableDataItem_VerticalAlignEnum;
@class RARESPOTFont;
@class RARESPOTGridCell;
@class RARESPOTItemDescription_CHeaderHorizontalAlign;
@class RARESPOTItemDescription_CHeaderIconPosition;
@class RARESPOTItemDescription_CHeaderVerticalAlign;
@class RARESPOTItemDescription_CHorizontalAlign;
@class RARESPOTItemDescription_CIconPosition;
@class RARESPOTItemDescription_COrientation;
@class RARESPOTItemDescription_CRenderDetail;
@class RARESPOTItemDescription_CRenderType;
@class RARESPOTItemDescription_CValueType;
@class RARESPOTItemDescription_CVerticalAlign;
@class RARESPOTLink;
@class RARESPOTMenuItem;
@class SPOTAny;
@class SPOTBoolean;
@class SPOTPrintableString;
@class SPOTSet;
@protocol iSPOTElement;

#import "JreEmulation.h"
#include "com/appnativa/spot/SPOTEnumerated.h"
#include "com/appnativa/spot/SPOTSequence.h"

@interface RARESPOTItemDescription : SPOTSequence {
 @public
  SPOTPrintableString *title_;
  SPOTPrintableString *name_;
  SPOTPrintableString *description__;
  SPOTPrintableString *category_;
  RARESPOTItemDescription_CValueType *valueType_;
  SPOTPrintableString *customTypeClass_;
  SPOTPrintableString *value_;
  SPOTPrintableString *valueContext_;
  SPOTPrintableString *linkedData_;
  SPOTPrintableString *linkedDataContext_;
  RARESPOTItemDescription_CIconPosition *iconPosition_;
  SPOTBoolean *scaleIcon_;
  RARESPOTItemDescription_CVerticalAlign *verticalAlign_;
  RARESPOTItemDescription_CHorizontalAlign *horizontalAlign_;
  RARESPOTItemDescription_CRenderType *renderType_;
  RARESPOTItemDescription_CRenderDetail *renderDetail_;
  SPOTPrintableString *width_;
  SPOTBoolean *editable_;
  SPOTBoolean *visible_;
  SPOTBoolean *hideable_;
  SPOTBoolean *sortable_;
  SPOTBoolean *moveable_;
  SPOTBoolean *showable_;
  SPOTBoolean *wordWrap_;
  SPOTBoolean *overrideSelectionBackground_;
  SPOTPrintableString *rendererClass_;
  SPOTPrintableString *converterClass_;
  SPOTPrintableString *linkedDataconverterClass_;
  SPOTPrintableString *editorClass_;
  SPOTAny *editorWidget_;
  RARESPOTFont *font_;
  RARESPOTGridCell *gridCell_;
  RARESPOTGridCell *selectionGridCell_;
  SPOTPrintableString *fgColor_;
  RARESPOTLink *actionLink_;
  SPOTPrintableString *icon_;
  SPOTPrintableString *cursorName_;
  SPOTBoolean *headerWordWrap_;
  RARESPOTFont *headerFont_;
  SPOTPrintableString *headerIcon_;
  SPOTPrintableString *headerColor_;
  RARESPOTGridCell *headerCell_;
  RARESPOTGridCell *headerRolloverCell_;
  RARESPOTGridCell *headerSelectionCell_;
  RARESPOTItemDescription_CHeaderHorizontalAlign *headerHorizontalAlign_;
  RARESPOTItemDescription_CHeaderVerticalAlign *headerVerticalAlign_;
  RARESPOTItemDescription_CHeaderIconPosition *headerIconPosition_;
  SPOTBoolean *headerScaleIcon_;
  RARESPOTItemDescription_COrientation *orientation_;
  SPOTPrintableString *templateName_;
  SPOTSet *popupMenu_;
  SPOTSet *subItems_;
  SPOTPrintableString *customProperties_;
}

- (id)init;
- (id)initWithBoolean:(BOOL)optional;
- (id)initWithBoolean:(BOOL)optional
          withBoolean:(BOOL)setElements;
- (void)spot_setElements;
- (RARESPOTGridCell *)getGridCell;
- (RARESPOTGridCell *)getGridCellReference;
- (void)setGridCellWithISPOTElement:(id<iSPOTElement>)reference;
- (RARESPOTGridCell *)getSelectionGridCell;
- (RARESPOTGridCell *)getSelectionGridCellReference;
- (void)setSelectionGridCellWithISPOTElement:(id<iSPOTElement>)reference;
- (RARESPOTLink *)getActionLink;
- (RARESPOTLink *)getActionLinkReference;
- (void)setActionLinkWithISPOTElement:(id<iSPOTElement>)reference;
- (RARESPOTGridCell *)getHeaderCell;
- (RARESPOTGridCell *)getHeaderCellReference;
- (void)setHeaderCellWithISPOTElement:(id<iSPOTElement>)reference;
- (RARESPOTGridCell *)getHeaderRolloverCell;
- (RARESPOTGridCell *)getHeaderRolloverCellReference;
- (void)setHeaderRolloverCellWithISPOTElement:(id<iSPOTElement>)reference;
- (RARESPOTGridCell *)getHeaderSelectionCell;
- (RARESPOTGridCell *)getHeaderSelectionCellReference;
- (void)setHeaderSelectionCellWithISPOTElement:(id<iSPOTElement>)reference;
- (SPOTSet *)getPopupMenu;
- (SPOTSet *)getPopupMenuReference;
- (void)setPopupMenuWithISPOTElement:(id<iSPOTElement>)reference;
- (SPOTSet *)getSubItems;
- (SPOTSet *)getSubItemsReference;
- (void)setSubItemsWithISPOTElement:(id<iSPOTElement>)reference;
- (id)initWithNSString:(NSString *)title;
- (id)initWithNSString:(NSString *)title
          withNSString:(NSString *)size;
- (id)initWithNSString:(NSString *)title
          withNSString:(NSString *)size
          withNSString:(NSString *)name;
- (id)initWithNSString:(NSString *)name
          withNSString:(NSString *)size
          withNSString:(NSString *)title
               withInt:(int)type;
- (void)setColumnNameWithNSString:(NSString *)name;
- (void)setTitleWithNSString:(NSString *)title;
- (void)setColumnTypeWithInt:(int)type;
- (void)setColumnSizeWithInt:(int)size;
- (void)setActionLinkWithNSString:(NSString *)url
                          withInt:(int)target;
- (void)setActionLinkWithNSString:(NSString *)url
                     withNSString:(NSString *)target;
- (void)setHeaderIconWithNSString:(NSString *)icon;
- (void)setItemIconWithNSString:(NSString *)icon;
- (void)setValueContextWithNSString:(NSString *)context;
- (RARESPOTMenuItem *)addPopupMenuWithRARESPOTMenuItem:(RARESPOTMenuItem *)item;
- (RARESPOTMenuItem *)addPopupMenuWithNSString:(NSString *)value
                                  withNSString:(NSString *)code;
- (RARESPOTMenuItem *)addPopupMenuWithNSString:(NSString *)value
                                  withNSString:(NSString *)url
                                       withInt:(int)target;
- (RARESPOTMenuItem *)addPopupMenuWithNSString:(NSString *)value
                                  withNSString:(NSString *)url
                                  withNSString:(NSString *)target;
- (RARERenderableDataItem_HorizontalAlignEnum *)getHorizontalAlignment;
- (RARERenderableDataItem_HorizontalAlignEnum *)getHeaderHorizontalAlignment;
- (RARERenderableDataItem_VerticalAlignEnum *)getVerticalAlignment;
- (RARERenderableDataItem_VerticalAlignEnum *)getHeaderVerticalAlignment;
- (RARERenderableDataItem_IconPositionEnum *)getIconPosition;
- (RARERenderableDataItem_IconPositionEnum *)getHeaderIconPosition;
- (void)copyAllFieldsTo:(RARESPOTItemDescription *)other;
@end

J2OBJC_FIELD_SETTER(RARESPOTItemDescription, title_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTItemDescription, name_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTItemDescription, description__, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTItemDescription, category_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTItemDescription, valueType_, RARESPOTItemDescription_CValueType *)
J2OBJC_FIELD_SETTER(RARESPOTItemDescription, customTypeClass_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTItemDescription, value_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTItemDescription, valueContext_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTItemDescription, linkedData_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTItemDescription, linkedDataContext_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTItemDescription, iconPosition_, RARESPOTItemDescription_CIconPosition *)
J2OBJC_FIELD_SETTER(RARESPOTItemDescription, scaleIcon_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTItemDescription, verticalAlign_, RARESPOTItemDescription_CVerticalAlign *)
J2OBJC_FIELD_SETTER(RARESPOTItemDescription, horizontalAlign_, RARESPOTItemDescription_CHorizontalAlign *)
J2OBJC_FIELD_SETTER(RARESPOTItemDescription, renderType_, RARESPOTItemDescription_CRenderType *)
J2OBJC_FIELD_SETTER(RARESPOTItemDescription, renderDetail_, RARESPOTItemDescription_CRenderDetail *)
J2OBJC_FIELD_SETTER(RARESPOTItemDescription, width_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTItemDescription, editable_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTItemDescription, visible_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTItemDescription, hideable_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTItemDescription, sortable_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTItemDescription, moveable_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTItemDescription, showable_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTItemDescription, wordWrap_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTItemDescription, overrideSelectionBackground_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTItemDescription, rendererClass_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTItemDescription, converterClass_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTItemDescription, linkedDataconverterClass_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTItemDescription, editorClass_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTItemDescription, editorWidget_, SPOTAny *)
J2OBJC_FIELD_SETTER(RARESPOTItemDescription, font_, RARESPOTFont *)
J2OBJC_FIELD_SETTER(RARESPOTItemDescription, gridCell_, RARESPOTGridCell *)
J2OBJC_FIELD_SETTER(RARESPOTItemDescription, selectionGridCell_, RARESPOTGridCell *)
J2OBJC_FIELD_SETTER(RARESPOTItemDescription, fgColor_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTItemDescription, actionLink_, RARESPOTLink *)
J2OBJC_FIELD_SETTER(RARESPOTItemDescription, icon_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTItemDescription, cursorName_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTItemDescription, headerWordWrap_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTItemDescription, headerFont_, RARESPOTFont *)
J2OBJC_FIELD_SETTER(RARESPOTItemDescription, headerIcon_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTItemDescription, headerColor_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTItemDescription, headerCell_, RARESPOTGridCell *)
J2OBJC_FIELD_SETTER(RARESPOTItemDescription, headerRolloverCell_, RARESPOTGridCell *)
J2OBJC_FIELD_SETTER(RARESPOTItemDescription, headerSelectionCell_, RARESPOTGridCell *)
J2OBJC_FIELD_SETTER(RARESPOTItemDescription, headerHorizontalAlign_, RARESPOTItemDescription_CHeaderHorizontalAlign *)
J2OBJC_FIELD_SETTER(RARESPOTItemDescription, headerVerticalAlign_, RARESPOTItemDescription_CHeaderVerticalAlign *)
J2OBJC_FIELD_SETTER(RARESPOTItemDescription, headerIconPosition_, RARESPOTItemDescription_CHeaderIconPosition *)
J2OBJC_FIELD_SETTER(RARESPOTItemDescription, headerScaleIcon_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTItemDescription, orientation_, RARESPOTItemDescription_COrientation *)
J2OBJC_FIELD_SETTER(RARESPOTItemDescription, templateName_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTItemDescription, popupMenu_, SPOTSet *)
J2OBJC_FIELD_SETTER(RARESPOTItemDescription, subItems_, SPOTSet *)
J2OBJC_FIELD_SETTER(RARESPOTItemDescription, customProperties_, SPOTPrintableString *)

typedef RARESPOTItemDescription ComAppnativaRareSpotItemDescription;

#define RARESPOTItemDescription_CValueType_array_type 10
#define RARESPOTItemDescription_CValueType_boolean_type 7
#define RARESPOTItemDescription_CValueType_bytes_base64_type 8
#define RARESPOTItemDescription_CValueType_custom_type 99
#define RARESPOTItemDescription_CValueType_date_time_type 4
#define RARESPOTItemDescription_CValueType_date_type 5
#define RARESPOTItemDescription_CValueType_decimal_type 3
#define RARESPOTItemDescription_CValueType_integer_type 2
#define RARESPOTItemDescription_CValueType_string_type 1
#define RARESPOTItemDescription_CValueType_struct_type 9
#define RARESPOTItemDescription_CValueType_time_type 6
#define RARESPOTItemDescription_CValueType_widget_type 11

@interface RARESPOTItemDescription_CValueType : SPOTEnumerated {
}

+ (int)string_type;
+ (int)integer_type;
+ (int)decimal_type;
+ (int)date_time_type;
+ (int)date_type;
+ (int)time_type;
+ (int)boolean_type;
+ (int)bytes_base64_type;
+ (int)struct_type;
+ (int)array_type;
+ (int)widget_type;
+ (int)custom_type;
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

#define RARESPOTItemDescription_CIconPosition_auto 0
#define RARESPOTItemDescription_CIconPosition_bottom_center 8
#define RARESPOTItemDescription_CIconPosition_bottom_left 9
#define RARESPOTItemDescription_CIconPosition_bottom_right 10
#define RARESPOTItemDescription_CIconPosition_leading 3
#define RARESPOTItemDescription_CIconPosition_left 1
#define RARESPOTItemDescription_CIconPosition_right 2
#define RARESPOTItemDescription_CIconPosition_right_justified 11
#define RARESPOTItemDescription_CIconPosition_top_center 5
#define RARESPOTItemDescription_CIconPosition_top_left 6
#define RARESPOTItemDescription_CIconPosition_top_right 7
#define RARESPOTItemDescription_CIconPosition_trailing 4

@interface RARESPOTItemDescription_CIconPosition : SPOTEnumerated {
}

+ (int)getAuto;
+ (int)left;
+ (int)right;
+ (int)leading;
+ (int)trailing;
+ (int)top_center;
+ (int)top_left;
+ (int)top_right;
+ (int)bottom_center;
+ (int)bottom_left;
+ (int)bottom_right;
+ (int)right_justified;
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

#define RARESPOTItemDescription_CVerticalAlign_auto 0
#define RARESPOTItemDescription_CVerticalAlign_bottom 2
#define RARESPOTItemDescription_CVerticalAlign_center 5
#define RARESPOTItemDescription_CVerticalAlign_top 1

@interface RARESPOTItemDescription_CVerticalAlign : SPOTEnumerated {
}

+ (int)getAuto;
+ (int)top;
+ (int)bottom;
+ (int)center;
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

#define RARESPOTItemDescription_CHorizontalAlign_auto 0
#define RARESPOTItemDescription_CHorizontalAlign_center 5
#define RARESPOTItemDescription_CHorizontalAlign_leading 3
#define RARESPOTItemDescription_CHorizontalAlign_left 1
#define RARESPOTItemDescription_CHorizontalAlign_right 2
#define RARESPOTItemDescription_CHorizontalAlign_trailing 4

@interface RARESPOTItemDescription_CHorizontalAlign : SPOTEnumerated {
}

+ (int)getAuto;
+ (int)left;
+ (int)right;
+ (int)leading;
+ (int)trailing;
+ (int)center;
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

#define RARESPOTItemDescription_CRenderType_footer 2
#define RARESPOTItemDescription_CRenderType_footer_index 4
#define RARESPOTItemDescription_CRenderType_footer_index_normal 8
#define RARESPOTItemDescription_CRenderType_footer_normal 6
#define RARESPOTItemDescription_CRenderType_header 1
#define RARESPOTItemDescription_CRenderType_header_index 3
#define RARESPOTItemDescription_CRenderType_header_index_normal 7
#define RARESPOTItemDescription_CRenderType_header_normal 5
#define RARESPOTItemDescription_CRenderType_normal 0
#define RARESPOTItemDescription_CRenderType_normal_index 9

@interface RARESPOTItemDescription_CRenderType : SPOTEnumerated {
}

+ (int)normal;
+ (int)header;
+ (int)footer;
+ (int)header_index;
+ (int)footer_index;
+ (int)header_normal;
+ (int)footer_normal;
+ (int)header_index_normal;
+ (int)footer_index_normal;
+ (int)normal_index;
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

#define RARESPOTItemDescription_CRenderDetail_all 1
#define RARESPOTItemDescription_CRenderDetail_auto 0
#define RARESPOTItemDescription_CRenderDetail_icon_only 4
#define RARESPOTItemDescription_CRenderDetail_none 5
#define RARESPOTItemDescription_CRenderDetail_text_and_icon 2
#define RARESPOTItemDescription_CRenderDetail_text_only 3

@interface RARESPOTItemDescription_CRenderDetail : SPOTEnumerated {
}

+ (int)getAuto;
+ (int)all;
+ (int)text_and_icon;
+ (int)text_only;
+ (int)icon_only;
+ (int)none;
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

#define RARESPOTItemDescription_CHeaderHorizontalAlign_auto 0
#define RARESPOTItemDescription_CHeaderHorizontalAlign_center 5
#define RARESPOTItemDescription_CHeaderHorizontalAlign_leading 3
#define RARESPOTItemDescription_CHeaderHorizontalAlign_left 1
#define RARESPOTItemDescription_CHeaderHorizontalAlign_right 2
#define RARESPOTItemDescription_CHeaderHorizontalAlign_trailing 4

@interface RARESPOTItemDescription_CHeaderHorizontalAlign : SPOTEnumerated {
}

+ (int)getAuto;
+ (int)left;
+ (int)right;
+ (int)leading;
+ (int)trailing;
+ (int)center;
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

#define RARESPOTItemDescription_CHeaderVerticalAlign_auto 0
#define RARESPOTItemDescription_CHeaderVerticalAlign_bottom 2
#define RARESPOTItemDescription_CHeaderVerticalAlign_center 5
#define RARESPOTItemDescription_CHeaderVerticalAlign_top 1

@interface RARESPOTItemDescription_CHeaderVerticalAlign : SPOTEnumerated {
}

+ (int)getAuto;
+ (int)top;
+ (int)bottom;
+ (int)center;
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

#define RARESPOTItemDescription_CHeaderIconPosition_auto 0
#define RARESPOTItemDescription_CHeaderIconPosition_bottom_center 8
#define RARESPOTItemDescription_CHeaderIconPosition_bottom_left 9
#define RARESPOTItemDescription_CHeaderIconPosition_bottom_right 10
#define RARESPOTItemDescription_CHeaderIconPosition_leading 3
#define RARESPOTItemDescription_CHeaderIconPosition_left 1
#define RARESPOTItemDescription_CHeaderIconPosition_right 2
#define RARESPOTItemDescription_CHeaderIconPosition_right_justified 11
#define RARESPOTItemDescription_CHeaderIconPosition_top_center 5
#define RARESPOTItemDescription_CHeaderIconPosition_top_left 6
#define RARESPOTItemDescription_CHeaderIconPosition_top_right 7
#define RARESPOTItemDescription_CHeaderIconPosition_trailing 4

@interface RARESPOTItemDescription_CHeaderIconPosition : SPOTEnumerated {
}

+ (int)getAuto;
+ (int)left;
+ (int)right;
+ (int)leading;
+ (int)trailing;
+ (int)top_center;
+ (int)top_left;
+ (int)top_right;
+ (int)bottom_center;
+ (int)bottom_left;
+ (int)bottom_right;
+ (int)right_justified;
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

#define RARESPOTItemDescription_COrientation_auto 0
#define RARESPOTItemDescription_COrientation_horizontal 1
#define RARESPOTItemDescription_COrientation_vertical_down 3
#define RARESPOTItemDescription_COrientation_vertical_up 2

@interface RARESPOTItemDescription_COrientation : SPOTEnumerated {
}

+ (int)getAuto;
+ (int)horizontal;
+ (int)vertical_up;
+ (int)vertical_down;
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

#endif // _RARESPOTItemDescription_H_