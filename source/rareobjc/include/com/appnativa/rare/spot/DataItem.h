//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/spot/DataItem.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RARESPOTDataItem_H_
#define _RARESPOTDataItem_H_

@class IOSIntArray;
@class IOSObjectArray;
@class JavaLangInteger;
@class RARERenderableDataItem_HorizontalAlignEnum;
@class RARERenderableDataItem_IconPositionEnum;
@class RARERenderableDataItem_VerticalAlignEnum;
@class RARESPOTDataItem_CHorizontalAlign;
@class RARESPOTDataItem_CIconPosition;
@class RARESPOTDataItem_COrientation;
@class RARESPOTDataItem_CValueType;
@class RARESPOTDataItem_CVerticalAlign;
@class RARESPOTFont;
@class RARESPOTGridCell;
@class RARESPOTLink;
@class SPOTBoolean;
@class SPOTInteger;
@class SPOTPrintableString;
@class SPOTSet;
@protocol iSPOTElement;

#import "JreEmulation.h"
#include "com/appnativa/spot/SPOTEnumerated.h"
#include "com/appnativa/spot/SPOTSequence.h"

@interface RARESPOTDataItem : SPOTSequence {
 @public
  RARESPOTDataItem_CValueType *valueType_;
  SPOTPrintableString *customTypeClass_;
  SPOTPrintableString *value_;
  SPOTPrintableString *valueContext_;
  SPOTPrintableString *linkedData_;
  SPOTPrintableString *linkedDataContext_;
  RARESPOTDataItem_CHorizontalAlign *horizontalAlign_;
  RARESPOTDataItem_CVerticalAlign *verticalAlign_;
  RARESPOTDataItem_CIconPosition *iconPosition_;
  SPOTBoolean *scaleIcon_;
  RARESPOTDataItem_COrientation *orientation_;
  SPOTBoolean *enabled_;
  SPOTBoolean *visible_;
  RARESPOTLink *actionLink_;
  SPOTPrintableString *icon_;
  SPOTPrintableString *disabledIcon_;
  SPOTPrintableString *alternateIcon_;
  SPOTPrintableString *tooltip_;
  SPOTPrintableString *cursorName_;
  RARESPOTFont *font_;
  SPOTPrintableString *fgColor_;
  RARESPOTGridCell *gridCell_;
  SPOTInteger *rowSpan_;
  SPOTInteger *columnSpan_;
  SPOTPrintableString *converterClass_;
  SPOTPrintableString *linkedDataconverterClass_;
  SPOTBoolean *draggingAllowed_;
  SPOTSet *importDataFlavors_;
  SPOTSet *exportDataFlavors_;
  SPOTPrintableString *templateName_;
  SPOTSet *subItems_;
  SPOTPrintableString *customProperties_;
}

- (id)init;
- (id)initWithBoolean:(BOOL)optional;
- (id)initWithBoolean:(BOOL)optional
          withBoolean:(BOOL)setElements;
- (void)spot_setElements;
- (RARESPOTLink *)getActionLink;
- (RARESPOTLink *)getActionLinkReference;
- (void)setActionLinkWithISPOTElement:(id<iSPOTElement>)reference;
- (RARESPOTGridCell *)getGridCell;
- (RARESPOTGridCell *)getGridCellReference;
- (void)setGridCellWithISPOTElement:(id<iSPOTElement>)reference;
- (SPOTSet *)getImportDataFlavors;
- (SPOTSet *)getImportDataFlavorsReference;
- (void)setImportDataFlavorsWithISPOTElement:(id<iSPOTElement>)reference;
- (SPOTSet *)getExportDataFlavors;
- (SPOTSet *)getExportDataFlavorsReference;
- (void)setExportDataFlavorsWithISPOTElement:(id<iSPOTElement>)reference;
- (SPOTSet *)getSubItems;
- (SPOTSet *)getSubItemsReference;
- (void)setSubItemsWithISPOTElement:(id<iSPOTElement>)reference;
- (id)initWithNSString:(NSString *)value;
- (id)initWithNSString:(NSString *)value
          withNSString:(NSString *)data;
- (id)initWithNSString:(NSString *)value
          withNSString:(NSString *)data
          withNSString:(NSString *)icon;
- (void)setValueWithNSString:(NSString *)value;
- (void)setLinkedObjectWithNSString:(NSString *)data;
- (void)setValuesWithNSString:(NSString *)value
                 withNSString:(NSString *)data
                 withNSString:(NSString *)icon;
- (RARESPOTDataItem *)addSubItem;
- (RARESPOTDataItem *)addSubItemWithRARESPOTDataItem:(RARESPOTDataItem *)item;
- (RARESPOTDataItem *)addSubItemWithNSString:(NSString *)value;
- (RARESPOTDataItem *)addSubItemWithNSString:(NSString *)value
                                withNSString:(NSString *)data;
- (RARESPOTDataItem *)addSubItemWithNSString:(NSString *)value
                                withNSString:(NSString *)data
                                withNSString:(NSString *)icon;
- (RARERenderableDataItem_HorizontalAlignEnum *)getHorizontalAlignment;
- (RARERenderableDataItem_VerticalAlignEnum *)getVerticalAlignment;
- (RARERenderableDataItem_IconPositionEnum *)getIconPosition;
- (void)copyAllFieldsTo:(RARESPOTDataItem *)other;
@end

J2OBJC_FIELD_SETTER(RARESPOTDataItem, valueType_, RARESPOTDataItem_CValueType *)
J2OBJC_FIELD_SETTER(RARESPOTDataItem, customTypeClass_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTDataItem, value_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTDataItem, valueContext_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTDataItem, linkedData_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTDataItem, linkedDataContext_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTDataItem, horizontalAlign_, RARESPOTDataItem_CHorizontalAlign *)
J2OBJC_FIELD_SETTER(RARESPOTDataItem, verticalAlign_, RARESPOTDataItem_CVerticalAlign *)
J2OBJC_FIELD_SETTER(RARESPOTDataItem, iconPosition_, RARESPOTDataItem_CIconPosition *)
J2OBJC_FIELD_SETTER(RARESPOTDataItem, scaleIcon_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTDataItem, orientation_, RARESPOTDataItem_COrientation *)
J2OBJC_FIELD_SETTER(RARESPOTDataItem, enabled_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTDataItem, visible_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTDataItem, actionLink_, RARESPOTLink *)
J2OBJC_FIELD_SETTER(RARESPOTDataItem, icon_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTDataItem, disabledIcon_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTDataItem, alternateIcon_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTDataItem, tooltip_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTDataItem, cursorName_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTDataItem, font_, RARESPOTFont *)
J2OBJC_FIELD_SETTER(RARESPOTDataItem, fgColor_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTDataItem, gridCell_, RARESPOTGridCell *)
J2OBJC_FIELD_SETTER(RARESPOTDataItem, rowSpan_, SPOTInteger *)
J2OBJC_FIELD_SETTER(RARESPOTDataItem, columnSpan_, SPOTInteger *)
J2OBJC_FIELD_SETTER(RARESPOTDataItem, converterClass_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTDataItem, linkedDataconverterClass_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTDataItem, draggingAllowed_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTDataItem, importDataFlavors_, SPOTSet *)
J2OBJC_FIELD_SETTER(RARESPOTDataItem, exportDataFlavors_, SPOTSet *)
J2OBJC_FIELD_SETTER(RARESPOTDataItem, templateName_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTDataItem, subItems_, SPOTSet *)
J2OBJC_FIELD_SETTER(RARESPOTDataItem, customProperties_, SPOTPrintableString *)

typedef RARESPOTDataItem ComAppnativaRareSpotDataItem;

#define RARESPOTDataItem_CValueType_array_type 10
#define RARESPOTDataItem_CValueType_auto_type 0
#define RARESPOTDataItem_CValueType_boolean_type 7
#define RARESPOTDataItem_CValueType_bytes_base64_type 8
#define RARESPOTDataItem_CValueType_custom_type 99
#define RARESPOTDataItem_CValueType_date_time_type 4
#define RARESPOTDataItem_CValueType_date_type 5
#define RARESPOTDataItem_CValueType_decimal_type 3
#define RARESPOTDataItem_CValueType_integer_type 2
#define RARESPOTDataItem_CValueType_string_type 1
#define RARESPOTDataItem_CValueType_struct_type 9
#define RARESPOTDataItem_CValueType_time_type 6
#define RARESPOTDataItem_CValueType_widget_type 11

@interface RARESPOTDataItem_CValueType : SPOTEnumerated {
}

+ (int)auto_type;
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

#define RARESPOTDataItem_CHorizontalAlign_auto 0
#define RARESPOTDataItem_CHorizontalAlign_center 5
#define RARESPOTDataItem_CHorizontalAlign_leading 3
#define RARESPOTDataItem_CHorizontalAlign_left 1
#define RARESPOTDataItem_CHorizontalAlign_right 2
#define RARESPOTDataItem_CHorizontalAlign_trailing 4

@interface RARESPOTDataItem_CHorizontalAlign : SPOTEnumerated {
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

#define RARESPOTDataItem_CVerticalAlign_auto 0
#define RARESPOTDataItem_CVerticalAlign_bottom 2
#define RARESPOTDataItem_CVerticalAlign_center 5
#define RARESPOTDataItem_CVerticalAlign_top 1

@interface RARESPOTDataItem_CVerticalAlign : SPOTEnumerated {
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

#define RARESPOTDataItem_CIconPosition_auto 0
#define RARESPOTDataItem_CIconPosition_bottom_center 8
#define RARESPOTDataItem_CIconPosition_bottom_left 9
#define RARESPOTDataItem_CIconPosition_bottom_right 10
#define RARESPOTDataItem_CIconPosition_leading 3
#define RARESPOTDataItem_CIconPosition_left 1
#define RARESPOTDataItem_CIconPosition_right 2
#define RARESPOTDataItem_CIconPosition_right_justified 11
#define RARESPOTDataItem_CIconPosition_top_center 5
#define RARESPOTDataItem_CIconPosition_top_left 6
#define RARESPOTDataItem_CIconPosition_top_right 7
#define RARESPOTDataItem_CIconPosition_trailing 4

@interface RARESPOTDataItem_CIconPosition : SPOTEnumerated {
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

#define RARESPOTDataItem_COrientation_auto 0
#define RARESPOTDataItem_COrientation_horizontal 1
#define RARESPOTDataItem_COrientation_vertical_down 3
#define RARESPOTDataItem_COrientation_vertical_up 2

@interface RARESPOTDataItem_COrientation : SPOTEnumerated {
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

#endif // _RARESPOTDataItem_H_
