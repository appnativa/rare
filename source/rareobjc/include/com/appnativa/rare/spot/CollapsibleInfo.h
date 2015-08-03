//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/spot/CollapsibleInfo.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RARESPOTCollapsibleInfo_H_
#define _RARESPOTCollapsibleInfo_H_

@class IOSIntArray;
@class IOSObjectArray;
@class JavaLangInteger;
@class RARESPOTCollapsibleInfo_CExpander;
@class RARESPOTFont;
@class RARESPOTGridCell;
@class SPOTBoolean;
@class SPOTPrintableString;
@protocol iSPOTElement;

#import "JreEmulation.h"
#include "com/appnativa/spot/SPOTEnumerated.h"
#include "com/appnativa/spot/SPOTSequence.h"

@interface RARESPOTCollapsibleInfo : SPOTSequence {
 @public
  SPOTPrintableString *icon_;
  SPOTBoolean *initiallyCollapsed_;
  SPOTPrintableString *title_;
  SPOTPrintableString *collapsedTitle_;
  SPOTPrintableString *collapseTip_;
  SPOTPrintableString *expandTip_;
  RARESPOTGridCell *titleCell_;
  RARESPOTFont *titleFont_;
  SPOTBoolean *expandOnDragover_;
  RARESPOTCollapsibleInfo_CExpander *expander_;
  SPOTBoolean *animateTransitions_;
  SPOTBoolean *toggleOnTitleSingleClick_;
  SPOTBoolean *userControllable_;
  SPOTBoolean *showTitleBar_;
  SPOTBoolean *opaqueTitleBar_;
  SPOTPrintableString *templateName_;
}

- (id)init;
- (id)initWithBoolean:(BOOL)optional;
- (id)initWithBoolean:(BOOL)optional
          withBoolean:(BOOL)setElements;
- (void)spot_setElements;
- (RARESPOTGridCell *)getTitleCell;
- (RARESPOTGridCell *)getTitleCellReference;
- (void)setTitleCellWithISPOTElement:(id<iSPOTElement>)reference;
- (void)copyAllFieldsTo:(RARESPOTCollapsibleInfo *)other;
@end

J2OBJC_FIELD_SETTER(RARESPOTCollapsibleInfo, icon_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTCollapsibleInfo, initiallyCollapsed_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTCollapsibleInfo, title_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTCollapsibleInfo, collapsedTitle_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTCollapsibleInfo, collapseTip_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTCollapsibleInfo, expandTip_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTCollapsibleInfo, titleCell_, RARESPOTGridCell *)
J2OBJC_FIELD_SETTER(RARESPOTCollapsibleInfo, titleFont_, RARESPOTFont *)
J2OBJC_FIELD_SETTER(RARESPOTCollapsibleInfo, expandOnDragover_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTCollapsibleInfo, expander_, RARESPOTCollapsibleInfo_CExpander *)
J2OBJC_FIELD_SETTER(RARESPOTCollapsibleInfo, animateTransitions_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTCollapsibleInfo, toggleOnTitleSingleClick_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTCollapsibleInfo, userControllable_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTCollapsibleInfo, showTitleBar_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTCollapsibleInfo, opaqueTitleBar_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTCollapsibleInfo, templateName_, SPOTPrintableString *)

typedef RARESPOTCollapsibleInfo ComAppnativaRareSpotCollapsibleInfo;

#define RARESPOTCollapsibleInfo_CExpander_chevron 1
#define RARESPOTCollapsibleInfo_CExpander_custom 2
#define RARESPOTCollapsibleInfo_CExpander_twisty 0

@interface RARESPOTCollapsibleInfo_CExpander : SPOTEnumerated {
}

+ (int)twisty;
+ (int)chevron;
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

#endif // _RARESPOTCollapsibleInfo_H_