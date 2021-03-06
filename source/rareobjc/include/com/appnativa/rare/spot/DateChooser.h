//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/spot/DateChooser.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RARESPOTDateChooser_H_
#define _RARESPOTDateChooser_H_

@class IOSIntArray;
@class IOSObjectArray;
@class JavaLangInteger;
@class RARESPOTDateChooser_CDisplayType;
@class RARESPOTDateChooser_CSelectionMode;
@class RARESPOTDateChooser_CSelectionType;
@class RARESPOTGridCell;
@class SPOTBoolean;
@class SPOTDateTime;
@class SPOTInteger;
@class SPOTPrintableString;
@protocol iSPOTElement;

#import "JreEmulation.h"
#include "com/appnativa/rare/spot/Widget.h"
#include "com/appnativa/spot/SPOTEnumerated.h"

@interface RARESPOTDateChooser : RARESPOTWidget {
 @public
  SPOTBoolean *editable_;
  SPOTDateTime *value_;
  SPOTDateTime *minValue_;
  SPOTDateTime *maxValue_;
  RARESPOTDateChooser_CDisplayType *displayType_;
  SPOTBoolean *showPopupButton_;
  SPOTBoolean *showPopupAsDialog_;
  RARESPOTGridCell *popupPainter_;
  SPOTPrintableString *format_;
  SPOTInteger *monthDisplayCols_;
  SPOTInteger *monthDisplayRows_;
  SPOTBoolean *autoResizeRowsColumns_;
  SPOTBoolean *showOkButton_;
  SPOTBoolean *showNoneButton_;
  SPOTBoolean *showTodayButton_;
  SPOTBoolean *showTime_;
  SPOTBoolean *showNavigationButtons_;
  RARESPOTDateChooser_CSelectionMode *selectionMode_;
  RARESPOTDateChooser_CSelectionType *selectionType_;
  SPOTPrintableString *converterClass_;
  SPOTPrintableString *valueContext_;
}

- (id)init;
- (id)initWithBoolean:(BOOL)optional;
- (id)initWithBoolean:(BOOL)optional
          withBoolean:(BOOL)setElements;
- (void)spot_setElements;
- (RARESPOTGridCell *)getPopupPainter;
- (RARESPOTGridCell *)getPopupPainterReference;
- (void)setPopupPainterWithISPOTElement:(id<iSPOTElement>)reference;
- (void)copyAllFieldsTo:(RARESPOTDateChooser *)other;
@end

J2OBJC_FIELD_SETTER(RARESPOTDateChooser, editable_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTDateChooser, value_, SPOTDateTime *)
J2OBJC_FIELD_SETTER(RARESPOTDateChooser, minValue_, SPOTDateTime *)
J2OBJC_FIELD_SETTER(RARESPOTDateChooser, maxValue_, SPOTDateTime *)
J2OBJC_FIELD_SETTER(RARESPOTDateChooser, displayType_, RARESPOTDateChooser_CDisplayType *)
J2OBJC_FIELD_SETTER(RARESPOTDateChooser, showPopupButton_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTDateChooser, showPopupAsDialog_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTDateChooser, popupPainter_, RARESPOTGridCell *)
J2OBJC_FIELD_SETTER(RARESPOTDateChooser, format_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTDateChooser, monthDisplayCols_, SPOTInteger *)
J2OBJC_FIELD_SETTER(RARESPOTDateChooser, monthDisplayRows_, SPOTInteger *)
J2OBJC_FIELD_SETTER(RARESPOTDateChooser, autoResizeRowsColumns_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTDateChooser, showOkButton_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTDateChooser, showNoneButton_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTDateChooser, showTodayButton_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTDateChooser, showTime_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTDateChooser, showNavigationButtons_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTDateChooser, selectionMode_, RARESPOTDateChooser_CSelectionMode *)
J2OBJC_FIELD_SETTER(RARESPOTDateChooser, selectionType_, RARESPOTDateChooser_CSelectionType *)
J2OBJC_FIELD_SETTER(RARESPOTDateChooser, converterClass_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTDateChooser, valueContext_, SPOTPrintableString *)

typedef RARESPOTDateChooser ComAppnativaRareSpotDateChooser;

#define RARESPOTDateChooser_CDisplayType_button 4
#define RARESPOTDateChooser_CDisplayType_combo_box 0
#define RARESPOTDateChooser_CDisplayType_multiple_calendar 3
#define RARESPOTDateChooser_CDisplayType_single_calendar 2

@interface RARESPOTDateChooser_CDisplayType : SPOTEnumerated {
}

+ (int)combo_box;
+ (int)single_calendar;
+ (int)multiple_calendar;
+ (int)button;
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

#define RARESPOTDateChooser_CSelectionMode_block 3
#define RARESPOTDateChooser_CSelectionMode_multiple 2
#define RARESPOTDateChooser_CSelectionMode_none 0
#define RARESPOTDateChooser_CSelectionMode_single 1

@interface RARESPOTDateChooser_CSelectionMode : SPOTEnumerated {
}

+ (int)none;
+ (int)single;
+ (int)multiple;
+ (int)block;
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

#define RARESPOTDateChooser_CSelectionType_all 0
#define RARESPOTDateChooser_CSelectionType_weekdays 1
#define RARESPOTDateChooser_CSelectionType_weekend 2

@interface RARESPOTDateChooser_CSelectionType : SPOTEnumerated {
}

+ (int)all;
+ (int)weekdays;
+ (int)weekend;
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

#endif // _RARESPOTDateChooser_H_
