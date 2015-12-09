//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/spot/TabPane.java
//
//  Created by decoteaud on 12/8/15.
//

#ifndef _RARESPOTTabPane_H_
#define _RARESPOTTabPane_H_

@class IOSIntArray;
@class IOSObjectArray;
@class JavaLangInteger;
@class RARESPOTGridCell;
@class RARESPOTTab;
@class RARESPOTTabPane_CCloseButton;
@class RARESPOTTabPane_CMoreIconType;
@class RARESPOTTabPane_CTabPosition;
@class RARESPOTTabPane_CTabStyle;
@class RARESPOTTabPane_CTextHAlignment;
@class SPOTAny;
@class SPOTBoolean;
@class SPOTInteger;
@class SPOTPrintableString;
@class SPOTReal;
@class SPOTSet;
@protocol iSPOTElement;

#import "JreEmulation.h"
#include "com/appnativa/rare/spot/Viewer.h"
#include "com/appnativa/spot/SPOTEnumerated.h"

@interface RARESPOTTabPane : RARESPOTViewer {
 @public
  RARESPOTTabPane_CTabPosition *tabPosition_;
  RARESPOTTabPane_CCloseButton *closeButton_;
  RARESPOTTabPane_CTabStyle *tabStyle_;
  RARESPOTTabPane_CMoreIconType *moreIconType_;
  RARESPOTGridCell *tabPainter_;
  RARESPOTGridCell *tabAreaPainter_;
  RARESPOTGridCell *selectedTabPainter_;
  SPOTBoolean *boldSelectedTab_;
  SPOTBoolean *showIconsOnTab_;
  SPOTBoolean *tabEditingAllowed_;
  SPOTPrintableString *tabHeight_;
  SPOTInteger *selectedIndex_;
  SPOTSet *tabs_;
  SPOTPrintableString *defaultTabIcon_;
  SPOTBoolean *actAsFormViewer_;
  SPOTAny *leadingHeaderWidget_;
  SPOTAny *trailingHeaderWidget_;
  SPOTPrintableString *transitionAnimator_;
  RARESPOTTabPane_CTextHAlignment *textHAlignment_;
  SPOTInteger *visibleCount_;
  SPOTBoolean *resizable_;
  SPOTReal *smallIconFraction_;
  RARESPOTGridCell *rolloverTabPainter_;
}

- (id)init;
- (id)initWithBoolean:(BOOL)optional;
- (id)initWithBoolean:(BOOL)optional
          withBoolean:(BOOL)setElements;
- (void)spot_setElements;
- (RARESPOTGridCell *)getTabPainter;
- (RARESPOTGridCell *)getTabPainterReference;
- (void)setTabPainterWithISPOTElement:(id<iSPOTElement>)reference;
- (RARESPOTGridCell *)getTabAreaPainter;
- (RARESPOTGridCell *)getTabAreaPainterReference;
- (void)setTabAreaPainterWithISPOTElement:(id<iSPOTElement>)reference;
- (RARESPOTGridCell *)getSelectedTabPainter;
- (RARESPOTGridCell *)getSelectedTabPainterReference;
- (void)setSelectedTabPainterWithISPOTElement:(id<iSPOTElement>)reference;
- (RARESPOTGridCell *)getRolloverTabPainter;
- (RARESPOTGridCell *)getRolloverTabPainterReference;
- (void)setRolloverTabPainterWithISPOTElement:(id<iSPOTElement>)reference;
- (int)indexOfTabWithNSString:(NSString *)name;
- (RARESPOTTab *)getTabWithNSString:(NSString *)name;
- (void)copyAllFieldsTo:(RARESPOTTabPane *)other;
@end

J2OBJC_FIELD_SETTER(RARESPOTTabPane, tabPosition_, RARESPOTTabPane_CTabPosition *)
J2OBJC_FIELD_SETTER(RARESPOTTabPane, closeButton_, RARESPOTTabPane_CCloseButton *)
J2OBJC_FIELD_SETTER(RARESPOTTabPane, tabStyle_, RARESPOTTabPane_CTabStyle *)
J2OBJC_FIELD_SETTER(RARESPOTTabPane, moreIconType_, RARESPOTTabPane_CMoreIconType *)
J2OBJC_FIELD_SETTER(RARESPOTTabPane, tabPainter_, RARESPOTGridCell *)
J2OBJC_FIELD_SETTER(RARESPOTTabPane, tabAreaPainter_, RARESPOTGridCell *)
J2OBJC_FIELD_SETTER(RARESPOTTabPane, selectedTabPainter_, RARESPOTGridCell *)
J2OBJC_FIELD_SETTER(RARESPOTTabPane, boldSelectedTab_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTTabPane, showIconsOnTab_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTTabPane, tabEditingAllowed_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTTabPane, tabHeight_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTTabPane, selectedIndex_, SPOTInteger *)
J2OBJC_FIELD_SETTER(RARESPOTTabPane, tabs_, SPOTSet *)
J2OBJC_FIELD_SETTER(RARESPOTTabPane, defaultTabIcon_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTTabPane, actAsFormViewer_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTTabPane, leadingHeaderWidget_, SPOTAny *)
J2OBJC_FIELD_SETTER(RARESPOTTabPane, trailingHeaderWidget_, SPOTAny *)
J2OBJC_FIELD_SETTER(RARESPOTTabPane, transitionAnimator_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTTabPane, textHAlignment_, RARESPOTTabPane_CTextHAlignment *)
J2OBJC_FIELD_SETTER(RARESPOTTabPane, visibleCount_, SPOTInteger *)
J2OBJC_FIELD_SETTER(RARESPOTTabPane, resizable_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTTabPane, smallIconFraction_, SPOTReal *)
J2OBJC_FIELD_SETTER(RARESPOTTabPane, rolloverTabPainter_, RARESPOTGridCell *)

typedef RARESPOTTabPane ComAppnativaRareSpotTabPane;

#define RARESPOTTabPane_CTabPosition_bottom 2
#define RARESPOTTabPane_CTabPosition_left 3
#define RARESPOTTabPane_CTabPosition_right 4
#define RARESPOTTabPane_CTabPosition_top 1

@interface RARESPOTTabPane_CTabPosition : SPOTEnumerated {
}

+ (int)top;
+ (int)bottom;
+ (int)left;
+ (int)right;
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

#define RARESPOTTabPane_CCloseButton_corner 2
#define RARESPOTTabPane_CCloseButton_none 0
#define RARESPOTTabPane_CCloseButton_on_tab 1

@interface RARESPOTTabPane_CCloseButton : SPOTEnumerated {
}

+ (int)none;
+ (int)on_tab;
+ (int)corner;
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

#define RARESPOTTabPane_CTabStyle_auto 0
#define RARESPOTTabPane_CTabStyle_box 1
#define RARESPOTTabPane_CTabStyle_chrome 2
#define RARESPOTTabPane_CTabStyle_flat 3
#define RARESPOTTabPane_CTabStyle_office2003 4
#define RARESPOTTabPane_CTabStyle_rounded_flat 5
#define RARESPOTTabPane_CTabStyle_stacked 7
#define RARESPOTTabPane_CTabStyle_windows 6

@interface RARESPOTTabPane_CTabStyle : SPOTEnumerated {
}

+ (int)getAuto;
+ (int)box;
+ (int)chrome;
+ (int)flat;
+ (int)office2003;
+ (int)rounded_flat;
+ (int)windows;
+ (int)stacked;
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

#define RARESPOTTabPane_CMoreIconType_custom 3
#define RARESPOTTabPane_CMoreIconType_dots 2
#define RARESPOTTabPane_CMoreIconType_menu 1

@interface RARESPOTTabPane_CMoreIconType : SPOTEnumerated {
}

+ (int)menu;
+ (int)dots;
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

#define RARESPOTTabPane_CTextHAlignment_auto 0
#define RARESPOTTabPane_CTextHAlignment_center 5
#define RARESPOTTabPane_CTextHAlignment_leading 3
#define RARESPOTTabPane_CTextHAlignment_left 1
#define RARESPOTTabPane_CTextHAlignment_right 2
#define RARESPOTTabPane_CTextHAlignment_trailing 4

@interface RARESPOTTabPane_CTextHAlignment : SPOTEnumerated {
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

#endif // _RARESPOTTabPane_H_
