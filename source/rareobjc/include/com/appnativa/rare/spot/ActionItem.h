//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/spot/ActionItem.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RARESPOTActionItem_H_
#define _RARESPOTActionItem_H_

@class RARESPOTLink;
@class SPOTBoolean;
@class SPOTPrintableString;
@protocol iSPOTElement;

#import "JreEmulation.h"
#include "com/appnativa/spot/SPOTSequence.h"

@interface RARESPOTActionItem : SPOTSequence {
 @public
  SPOTPrintableString *name_;
  SPOTPrintableString *value_;
  SPOTPrintableString *tooltip_;
  SPOTPrintableString *icon_;
  SPOTPrintableString *disabledIcon_;
  SPOTPrintableString *selectedIcon_;
  SPOTBoolean *enabled_;
  SPOTBoolean *focusedAction_;
  SPOTBoolean *enabledOnSelectionOnly_;
  SPOTBoolean *enabledIfHasValueOnly_;
  SPOTPrintableString *groupName_;
  SPOTPrintableString *shortcutKeystroke_;
  SPOTPrintableString *linkedData_;
  RARESPOTLink *actionLink_;
}

- (id)init;
- (id)initWithBoolean:(BOOL)optional;
- (id)initWithBoolean:(BOOL)optional
          withBoolean:(BOOL)setElements;
- (void)spot_setElements;
- (RARESPOTLink *)getActionLink;
- (RARESPOTLink *)getActionLinkReference;
- (void)setActionLinkWithISPOTElement:(id<iSPOTElement>)reference;
- (void)copyAllFieldsTo:(RARESPOTActionItem *)other;
@end

J2OBJC_FIELD_SETTER(RARESPOTActionItem, name_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTActionItem, value_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTActionItem, tooltip_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTActionItem, icon_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTActionItem, disabledIcon_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTActionItem, selectedIcon_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTActionItem, enabled_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTActionItem, focusedAction_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTActionItem, enabledOnSelectionOnly_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTActionItem, enabledIfHasValueOnly_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTActionItem, groupName_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTActionItem, shortcutKeystroke_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTActionItem, linkedData_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTActionItem, actionLink_, RARESPOTLink *)

typedef RARESPOTActionItem ComAppnativaRareSpotActionItem;

#endif // _RARESPOTActionItem_H_
