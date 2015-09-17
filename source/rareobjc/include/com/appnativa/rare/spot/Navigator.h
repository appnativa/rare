//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/spot/Navigator.java
//
//  Created by decoteaud on 9/15/15.
//

#ifndef _RARESPOTNavigator_H_
#define _RARESPOTNavigator_H_

@class IOSIntArray;
@class IOSObjectArray;
@class JavaLangInteger;
@class RARESPOTGridCell;
@class RARESPOTNavigator_CIconPosition;
@class RARESPOTNavigator_CTextHAlignment;
@class RARESPOTNavigator_CTextVAlignment;
@class RARESPOTNavigator_CType;
@class SPOTBoolean;
@class SPOTInteger;
@class SPOTPrintableString;
@class SPOTSet;
@protocol iSPOTElement;

#import "JreEmulation.h"
#include "com/appnativa/rare/spot/Widget.h"
#include "com/appnativa/spot/SPOTEnumerated.h"

@interface RARESPOTNavigator : RARESPOTWidget {
 @public
  RARESPOTNavigator_CType *type_;
  SPOTInteger *selectedIndex_;
  SPOTBoolean *showBackButton_;
  SPOTBoolean *useTextForTooltip_;
  SPOTBoolean *showIconsOnly_;
  SPOTBoolean *buttonsSameSize_;
  SPOTPrintableString *separatorLineColor_;
  RARESPOTGridCell *pressedPainter_;
  RARESPOTGridCell *selectionPainter_;
  RARESPOTNavigator_CTextHAlignment *textHAlignment_;
  RARESPOTNavigator_CTextVAlignment *textVAlignment_;
  RARESPOTNavigator_CIconPosition *iconPosition_;
  SPOTBoolean *scaleIcon_;
  SPOTSet *actions_;
}

- (id)init;
- (id)initWithBoolean:(BOOL)optional;
- (id)initWithBoolean:(BOOL)optional
          withBoolean:(BOOL)setElements;
- (void)spot_setElements;
- (RARESPOTGridCell *)getPressedPainter;
- (RARESPOTGridCell *)getPressedPainterReference;
- (void)setPressedPainterWithISPOTElement:(id<iSPOTElement>)reference;
- (RARESPOTGridCell *)getSelectionPainter;
- (RARESPOTGridCell *)getSelectionPainterReference;
- (void)setSelectionPainterWithISPOTElement:(id<iSPOTElement>)reference;
- (void)copyAllFieldsTo:(RARESPOTNavigator *)other;
@end

J2OBJC_FIELD_SETTER(RARESPOTNavigator, type_, RARESPOTNavigator_CType *)
J2OBJC_FIELD_SETTER(RARESPOTNavigator, selectedIndex_, SPOTInteger *)
J2OBJC_FIELD_SETTER(RARESPOTNavigator, showBackButton_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTNavigator, useTextForTooltip_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTNavigator, showIconsOnly_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTNavigator, buttonsSameSize_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTNavigator, separatorLineColor_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTNavigator, pressedPainter_, RARESPOTGridCell *)
J2OBJC_FIELD_SETTER(RARESPOTNavigator, selectionPainter_, RARESPOTGridCell *)
J2OBJC_FIELD_SETTER(RARESPOTNavigator, textHAlignment_, RARESPOTNavigator_CTextHAlignment *)
J2OBJC_FIELD_SETTER(RARESPOTNavigator, textVAlignment_, RARESPOTNavigator_CTextVAlignment *)
J2OBJC_FIELD_SETTER(RARESPOTNavigator, iconPosition_, RARESPOTNavigator_CIconPosition *)
J2OBJC_FIELD_SETTER(RARESPOTNavigator, scaleIcon_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTNavigator, actions_, SPOTSet *)

typedef RARESPOTNavigator ComAppnativaRareSpotNavigator;

#define RARESPOTNavigator_CType_hiearchical 0
#define RARESPOTNavigator_CType_option 2
#define RARESPOTNavigator_CType_toggle 1

@interface RARESPOTNavigator_CType : SPOTEnumerated {
}

+ (int)hiearchical;
+ (int)toggle;
+ (int)option;
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

#define RARESPOTNavigator_CTextHAlignment_auto 0
#define RARESPOTNavigator_CTextHAlignment_center 5
#define RARESPOTNavigator_CTextHAlignment_leading 3
#define RARESPOTNavigator_CTextHAlignment_left 1
#define RARESPOTNavigator_CTextHAlignment_right 2
#define RARESPOTNavigator_CTextHAlignment_trailing 4

@interface RARESPOTNavigator_CTextHAlignment : SPOTEnumerated {
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

#define RARESPOTNavigator_CTextVAlignment_auto 0
#define RARESPOTNavigator_CTextVAlignment_bottom 2
#define RARESPOTNavigator_CTextVAlignment_center 5
#define RARESPOTNavigator_CTextVAlignment_top 1

@interface RARESPOTNavigator_CTextVAlignment : SPOTEnumerated {
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

#define RARESPOTNavigator_CIconPosition_auto 0
#define RARESPOTNavigator_CIconPosition_bottom_center 8
#define RARESPOTNavigator_CIconPosition_bottom_left 9
#define RARESPOTNavigator_CIconPosition_bottom_right 10
#define RARESPOTNavigator_CIconPosition_leading 3
#define RARESPOTNavigator_CIconPosition_left 1
#define RARESPOTNavigator_CIconPosition_right 2
#define RARESPOTNavigator_CIconPosition_top_center 5
#define RARESPOTNavigator_CIconPosition_top_left 6
#define RARESPOTNavigator_CIconPosition_top_right 7
#define RARESPOTNavigator_CIconPosition_trailing 4

@interface RARESPOTNavigator_CIconPosition : SPOTEnumerated {
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

#endif // _RARESPOTNavigator_H_
