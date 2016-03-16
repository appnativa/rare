//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/spot/Widget.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RARESPOTWidget_H_
#define _RARESPOTWidget_H_

@class IOSIntArray;
@class IOSObjectArray;
@class JavaLangInteger;
@class RARESPOTAccessibleContext;
@class RARESPOTDataItem;
@class RARESPOTFont;
@class RARESPOTMargin;
@class RARESPOTMenuItem;
@class RARESPOTOverlayInfo;
@class RARESPOTRectangle;
@class RARESPOTWidget_CBorder;
@class RARESPOTWidget_CDropTracking;
@class RARESPOTWidget_CHorizontalAlign;
@class RARESPOTWidget_CTextDirection;
@class RARESPOTWidget_CTitleLocation;
@class RARESPOTWidget_CVerticalAlign;
@class SPOTBoolean;
@class SPOTInteger;
@class SPOTPrintableString;
@class SPOTSet;
@protocol JavaUtilMap;
@protocol iSPOTElement;

#import "JreEmulation.h"
#include "com/appnativa/spot/SPOTEnumerated.h"
#include "com/appnativa/spot/SPOTSequence.h"

@interface RARESPOTWidget : SPOTSequence {
 @public
  SPOTPrintableString *name_;
  SPOTPrintableString *title_;
  RARESPOTWidget_CTitleLocation *titleLocation_;
  SPOTSet *borders_;
  SPOTInteger *columnSpan_;
  SPOTInteger *rowSpan_;
  RARESPOTWidget_CVerticalAlign *verticalAlign_;
  RARESPOTWidget_CHorizontalAlign *horizontalAlign_;
  RARESPOTFont *font_;
  SPOTPrintableString *fgColor_;
  SPOTPrintableString *bgColor_;
  RARESPOTRectangle *bounds_;
  RARESPOTMargin *contentPadding_;
  RARESPOTMargin *cellPadding_;
  SPOTBoolean *required_;
  SPOTBoolean *enabled_;
  SPOTBoolean *visible_;
  SPOTBoolean *focusable_;
  SPOTBoolean *focusPainted_;
  SPOTBoolean *submitable_;
  SPOTBoolean *copyingAllowed_;
  SPOTBoolean *pastingAllowed_;
  SPOTBoolean *draggingAllowed_;
  SPOTBoolean *deletingAllowed_;
  SPOTBoolean *defaultContextMenu_;
  SPOTPrintableString *tooltip_;
  SPOTPrintableString *propertyChangeHandlers_;
  SPOTPrintableString *keystrokeMappings_;
  SPOTPrintableString *dataURL_;
  RARESPOTWidget_CDropTracking *dropTracking_;
  SPOTSet *importDataFlavors_;
  SPOTSet *exportDataFlavors_;
  SPOTSet *supportedActions_;
  SPOTSet *popupMenu_;
  SPOTPrintableString *bgImageURL_;
  SPOTPrintableString *overlayImageURL_;
  RARESPOTOverlayInfo *overlayWidget_;
  RARESPOTAccessibleContext *accessibleContext_;
  SPOTPrintableString *templateName_;
  SPOTPrintableString *cursorName_;
  SPOTPrintableString *customProperties_;
  RARESPOTWidget_CTextDirection *textDirection_;
}

- (id)init;
- (id)initWithBoolean:(BOOL)optional;
- (id)initWithBoolean:(BOOL)optional
          withBoolean:(BOOL)setElements;
- (void)spot_setElements;
- (SPOTSet *)getBorders;
- (SPOTSet *)getBordersReference;
- (void)setBordersWithISPOTElement:(id<iSPOTElement>)reference;
- (RARESPOTMargin *)getContentPadding;
- (RARESPOTMargin *)getContentPaddingReference;
- (void)setContentPaddingWithISPOTElement:(id<iSPOTElement>)reference;
- (RARESPOTMargin *)getCellPadding;
- (RARESPOTMargin *)getCellPaddingReference;
- (void)setCellPaddingWithISPOTElement:(id<iSPOTElement>)reference;
- (SPOTSet *)getImportDataFlavors;
- (SPOTSet *)getImportDataFlavorsReference;
- (void)setImportDataFlavorsWithISPOTElement:(id<iSPOTElement>)reference;
- (SPOTSet *)getExportDataFlavors;
- (SPOTSet *)getExportDataFlavorsReference;
- (void)setExportDataFlavorsWithISPOTElement:(id<iSPOTElement>)reference;
- (SPOTSet *)getSupportedActions;
- (SPOTSet *)getSupportedActionsReference;
- (void)setSupportedActionsWithISPOTElement:(id<iSPOTElement>)reference;
- (SPOTSet *)getPopupMenu;
- (SPOTSet *)getPopupMenuReference;
- (void)setPopupMenuWithISPOTElement:(id<iSPOTElement>)reference;
- (RARESPOTOverlayInfo *)getOverlayWidget;
- (RARESPOTOverlayInfo *)getOverlayWidgetReference;
- (void)setOverlayWidgetWithISPOTElement:(id<iSPOTElement>)reference;
- (RARESPOTAccessibleContext *)getAccessibleContext;
- (RARESPOTAccessibleContext *)getAccessibleContextReference;
- (void)setAccessibleContextWithISPOTElement:(id<iSPOTElement>)reference;
- (void)setBoundsWithNSString:(NSString *)x
                 withNSString:(NSString *)y
                 withNSString:(NSString *)width
                 withNSString:(NSString *)height;
- (void)setLocationWithInt:(int)x
                   withInt:(int)y;
- (void)setSizeWithNSString:(NSString *)width
               withNSString:(NSString *)height;
- (RARESPOTWidget_CBorder *)setBorderWithInt:(int)border;
- (RARESPOTWidget_CBorder *)setBorderWithNSString:(NSString *)border;
- (RARESPOTWidget_CBorder *)getBorder;
- (void)setBorderAttributeWithNSString:(NSString *)name
                          withNSString:(NSString *)value;
- (void)setTitleWithNSString:(NSString *)title;
- (void)setDataURLWithNSString:(NSString *)url;
- (void)setContentPaddingWithInt:(int)top
                         withInt:(int)left
                         withInt:(int)bottom
                         withInt:(int)right;
- (void)setNameWithNSString:(NSString *)name;
- (void)setVerticalAlignmentWithInt:(int)align;
- (void)setEventHandlerWithNSString:(NSString *)event
                       withNSString:(NSString *)code;
- (void)setHorizontalAlignmentWithInt:(int)align;
- (void)setColumnSpanWithInt:(int)span;
- (void)setRowSpanWithInt:(int)span;
- (RARESPOTMenuItem *)createMenuItemWithNSString:(NSString *)value
                                    withNSString:(NSString *)code;
- (RARESPOTMenuItem *)createMenuItemWithNSString:(NSString *)value
                                    withNSString:(NSString *)url
                                         withInt:(int)target;
- (RARESPOTMenuItem *)createMenuItemWithNSString:(NSString *)value
                                    withNSString:(NSString *)url
                                    withNSString:(NSString *)target;
- (RARESPOTDataItem *)createDataItemWithNSString:(NSString *)value;
- (RARESPOTDataItem *)createDataItemWithNSString:(NSString *)value
                                    withNSString:(NSString *)data;
- (RARESPOTDataItem *)createDataItemWithNSString:(NSString *)value
                                    withNSString:(NSString *)data
                                    withNSString:(NSString *)icon;
- (void)copy__WithSPOTSequence:(SPOTSequence *)s OBJC_METHOD_FAMILY_NONE;
- (RARESPOTWidget *)findWidgetWithNSString:(NSString *)name
                               withBoolean:(BOOL)useNameMap;
- (RARESPOTWidget *)findWidgetWithNSString:(NSString *)name;
- (id<JavaUtilMap>)createWidgetMap;
+ (SPOTSet *)getBordersSet;
+ (int)getBorderTypeWithNSString:(NSString *)name;
- (RARESPOTWidget_CBorder *)addBorderWithInt:(int)border;
- (RARESPOTWidget_CBorder *)addBorderWithNSString:(NSString *)border;
- (id<JavaUtilMap>)getSPOTAttributes;
- (void)copyAllFieldsTo:(RARESPOTWidget *)other;
@end

J2OBJC_FIELD_SETTER(RARESPOTWidget, name_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTWidget, title_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTWidget, titleLocation_, RARESPOTWidget_CTitleLocation *)
J2OBJC_FIELD_SETTER(RARESPOTWidget, borders_, SPOTSet *)
J2OBJC_FIELD_SETTER(RARESPOTWidget, columnSpan_, SPOTInteger *)
J2OBJC_FIELD_SETTER(RARESPOTWidget, rowSpan_, SPOTInteger *)
J2OBJC_FIELD_SETTER(RARESPOTWidget, verticalAlign_, RARESPOTWidget_CVerticalAlign *)
J2OBJC_FIELD_SETTER(RARESPOTWidget, horizontalAlign_, RARESPOTWidget_CHorizontalAlign *)
J2OBJC_FIELD_SETTER(RARESPOTWidget, font_, RARESPOTFont *)
J2OBJC_FIELD_SETTER(RARESPOTWidget, fgColor_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTWidget, bgColor_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTWidget, bounds_, RARESPOTRectangle *)
J2OBJC_FIELD_SETTER(RARESPOTWidget, contentPadding_, RARESPOTMargin *)
J2OBJC_FIELD_SETTER(RARESPOTWidget, cellPadding_, RARESPOTMargin *)
J2OBJC_FIELD_SETTER(RARESPOTWidget, required_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTWidget, enabled_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTWidget, visible_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTWidget, focusable_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTWidget, focusPainted_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTWidget, submitable_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTWidget, copyingAllowed_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTWidget, pastingAllowed_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTWidget, draggingAllowed_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTWidget, deletingAllowed_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTWidget, defaultContextMenu_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTWidget, tooltip_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTWidget, propertyChangeHandlers_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTWidget, keystrokeMappings_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTWidget, dataURL_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTWidget, dropTracking_, RARESPOTWidget_CDropTracking *)
J2OBJC_FIELD_SETTER(RARESPOTWidget, importDataFlavors_, SPOTSet *)
J2OBJC_FIELD_SETTER(RARESPOTWidget, exportDataFlavors_, SPOTSet *)
J2OBJC_FIELD_SETTER(RARESPOTWidget, supportedActions_, SPOTSet *)
J2OBJC_FIELD_SETTER(RARESPOTWidget, popupMenu_, SPOTSet *)
J2OBJC_FIELD_SETTER(RARESPOTWidget, bgImageURL_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTWidget, overlayImageURL_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTWidget, overlayWidget_, RARESPOTOverlayInfo *)
J2OBJC_FIELD_SETTER(RARESPOTWidget, accessibleContext_, RARESPOTAccessibleContext *)
J2OBJC_FIELD_SETTER(RARESPOTWidget, templateName_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTWidget, cursorName_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTWidget, customProperties_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTWidget, textDirection_, RARESPOTWidget_CTextDirection *)

typedef RARESPOTWidget ComAppnativaRareSpotWidget;

#define RARESPOTWidget_CTitleLocation_auto 1
#define RARESPOTWidget_CTitleLocation_bottom_center 6
#define RARESPOTWidget_CTitleLocation_bottom_left 5
#define RARESPOTWidget_CTitleLocation_bottom_right 7
#define RARESPOTWidget_CTitleLocation_center_left 20
#define RARESPOTWidget_CTitleLocation_collapsible_frame 21
#define RARESPOTWidget_CTitleLocation_frame_bottom_center 12
#define RARESPOTWidget_CTitleLocation_frame_bottom_left 11
#define RARESPOTWidget_CTitleLocation_frame_bottom_right 13
#define RARESPOTWidget_CTitleLocation_frame_top_center 9
#define RARESPOTWidget_CTitleLocation_frame_top_left 8
#define RARESPOTWidget_CTitleLocation_frame_top_right 10
#define RARESPOTWidget_CTitleLocation_inside_bottom_center 18
#define RARESPOTWidget_CTitleLocation_inside_bottom_left 17
#define RARESPOTWidget_CTitleLocation_inside_bottom_right 19
#define RARESPOTWidget_CTitleLocation_inside_top_center 16
#define RARESPOTWidget_CTitleLocation_inside_top_left 14
#define RARESPOTWidget_CTitleLocation_inside_top_right 15
#define RARESPOTWidget_CTitleLocation_not_displayed 0
#define RARESPOTWidget_CTitleLocation_top_center 4
#define RARESPOTWidget_CTitleLocation_top_left 2
#define RARESPOTWidget_CTitleLocation_top_right 3

@interface RARESPOTWidget_CTitleLocation : SPOTEnumerated {
}

+ (int)not_displayed;
+ (int)getAuto;
+ (int)top_left;
+ (int)top_right;
+ (int)top_center;
+ (int)bottom_left;
+ (int)bottom_center;
+ (int)bottom_right;
+ (int)frame_top_left;
+ (int)frame_top_center;
+ (int)frame_top_right;
+ (int)frame_bottom_left;
+ (int)frame_bottom_center;
+ (int)frame_bottom_right;
+ (int)inside_top_left;
+ (int)inside_top_right;
+ (int)inside_top_center;
+ (int)inside_bottom_left;
+ (int)inside_bottom_center;
+ (int)inside_bottom_right;
+ (int)center_left;
+ (int)collapsible_frame;
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

#define RARESPOTWidget_CBorder_back 17
#define RARESPOTWidget_CBorder_balloon 18
#define RARESPOTWidget_CBorder_bevel_lowered 7
#define RARESPOTWidget_CBorder_bevel_raised 6
#define RARESPOTWidget_CBorder_custom 31
#define RARESPOTWidget_CBorder_drop_shadow 12
#define RARESPOTWidget_CBorder_empty 3
#define RARESPOTWidget_CBorder_etched_lowered 9
#define RARESPOTWidget_CBorder_etched_raised 8
#define RARESPOTWidget_CBorder_frame_lowered 11
#define RARESPOTWidget_CBorder_frame_raised 10
#define RARESPOTWidget_CBorder_group_box 14
#define RARESPOTWidget_CBorder_icon 15
#define RARESPOTWidget_CBorder_line 2
#define RARESPOTWidget_CBorder_lowered 5
#define RARESPOTWidget_CBorder_matte 16
#define RARESPOTWidget_CBorder_none 0
#define RARESPOTWidget_CBorder_raised 4
#define RARESPOTWidget_CBorder_shadow 13
#define RARESPOTWidget_CBorder_standard 1
#define RARESPOTWidget_CBorder_titled 19

@interface RARESPOTWidget_CBorder : SPOTEnumerated {
}

+ (int)none;
+ (int)standard;
+ (int)line;
+ (int)empty;
+ (int)raised;
+ (int)lowered;
+ (int)bevel_raised;
+ (int)bevel_lowered;
+ (int)etched_raised;
+ (int)etched_lowered;
+ (int)frame_raised;
+ (int)frame_lowered;
+ (int)drop_shadow;
+ (int)shadow;
+ (int)group_box;
+ (int)icon;
+ (int)matte;
+ (int)back;
+ (int)balloon;
+ (int)titled;
+ (int)custom;
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

#define RARESPOTWidget_CVerticalAlign_auto 0
#define RARESPOTWidget_CVerticalAlign_bottom 3
#define RARESPOTWidget_CVerticalAlign_center 1
#define RARESPOTWidget_CVerticalAlign_full 4
#define RARESPOTWidget_CVerticalAlign_top 2

@interface RARESPOTWidget_CVerticalAlign : SPOTEnumerated {
}

+ (int)getAuto;
+ (int)center;
+ (int)top;
+ (int)bottom;
+ (int)full;
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

#define RARESPOTWidget_CHorizontalAlign_auto 0
#define RARESPOTWidget_CHorizontalAlign_center 1
#define RARESPOTWidget_CHorizontalAlign_full 4
#define RARESPOTWidget_CHorizontalAlign_left 2
#define RARESPOTWidget_CHorizontalAlign_right 3

@interface RARESPOTWidget_CHorizontalAlign : SPOTEnumerated {
}

+ (int)getAuto;
+ (int)center;
+ (int)left;
+ (int)right;
+ (int)full;
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

#define RARESPOTWidget_CDropTracking_auto 1
#define RARESPOTWidget_CDropTracking_insert_item 3
#define RARESPOTWidget_CDropTracking_none 0
#define RARESPOTWidget_CDropTracking_on_item 2
#define RARESPOTWidget_CDropTracking_on_or_insert 4

@interface RARESPOTWidget_CDropTracking : SPOTEnumerated {
}

+ (int)none;
+ (int)getAuto;
+ (int)on_item;
+ (int)insert_item;
+ (int)on_or_insert;
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

#define RARESPOTWidget_CTextDirection_auto 0
#define RARESPOTWidget_CTextDirection_ltr 1
#define RARESPOTWidget_CTextDirection_rtl 2

@interface RARESPOTWidget_CTextDirection : SPOTEnumerated {
}

+ (int)getAuto;
+ (int)ltr;
+ (int)rtl;
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

#endif // _RARESPOTWidget_H_
