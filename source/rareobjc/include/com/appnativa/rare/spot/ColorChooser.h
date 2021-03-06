//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/spot/ColorChooser.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RARESPOTColorChooser_H_
#define _RARESPOTColorChooser_H_

@class IOSIntArray;
@class IOSObjectArray;
@class JavaLangInteger;
@class RARESPOTColorChooser_CColorPalette;
@class RARESPOTColorChooser_CDropDownType;
@class RARESPOTGridCell;
@class SPOTBoolean;
@class SPOTPrintableString;
@protocol iSPOTElement;

#import "JreEmulation.h"
#include "com/appnativa/rare/spot/Widget.h"
#include "com/appnativa/spot/SPOTEnumerated.h"

@interface RARESPOTColorChooser : RARESPOTWidget {
 @public
  SPOTBoolean *editable_;
  SPOTPrintableString *value_;
  RARESPOTColorChooser_CDropDownType *dropDownType_;
  SPOTBoolean *deselectEventsEnabled_;
  SPOTBoolean *showPopupAsDialog_;
  SPOTBoolean *showOkButton_;
  SPOTBoolean *showNoneButton_;
  SPOTBoolean *showValueAsHex_;
  RARESPOTColorChooser_CColorPalette *colorPalette_;
  SPOTBoolean *showPopupButton_;
  RARESPOTGridCell *popupPainter_;
  SPOTPrintableString *buttonIcon_;
}

- (id)init;
- (id)initWithBoolean:(BOOL)optional;
- (id)initWithBoolean:(BOOL)optional
          withBoolean:(BOOL)setElements;
- (void)spot_setElements;
- (RARESPOTGridCell *)getPopupPainter;
- (RARESPOTGridCell *)getPopupPainterReference;
- (void)setPopupPainterWithISPOTElement:(id<iSPOTElement>)reference;
- (void)copyAllFieldsTo:(RARESPOTColorChooser *)other;
@end

J2OBJC_FIELD_SETTER(RARESPOTColorChooser, editable_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTColorChooser, value_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTColorChooser, dropDownType_, RARESPOTColorChooser_CDropDownType *)
J2OBJC_FIELD_SETTER(RARESPOTColorChooser, deselectEventsEnabled_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTColorChooser, showPopupAsDialog_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTColorChooser, showOkButton_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTColorChooser, showNoneButton_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTColorChooser, showValueAsHex_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTColorChooser, colorPalette_, RARESPOTColorChooser_CColorPalette *)
J2OBJC_FIELD_SETTER(RARESPOTColorChooser, showPopupButton_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTColorChooser, popupPainter_, RARESPOTGridCell *)
J2OBJC_FIELD_SETTER(RARESPOTColorChooser, buttonIcon_, SPOTPrintableString *)

typedef RARESPOTColorChooser ComAppnativaRareSpotColorChooser;

#define RARESPOTColorChooser_CDropDownType_button 2
#define RARESPOTColorChooser_CDropDownType_combo_box 1
#define RARESPOTColorChooser_CDropDownType_none 0

@interface RARESPOTColorChooser_CDropDownType : SPOTEnumerated {
}

+ (int)none;
+ (int)combo_box;
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

#define RARESPOTColorChooser_CColorPalette_color_16 1
#define RARESPOTColorChooser_CColorPalette_color_40 0
#define RARESPOTColorChooser_CColorPalette_custom 3
#define RARESPOTColorChooser_CColorPalette_gray_16 2

@interface RARESPOTColorChooser_CColorPalette : SPOTEnumerated {
}

+ (int)color_40;
+ (int)color_16;
+ (int)gray_16;
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

#endif // _RARESPOTColorChooser_H_
