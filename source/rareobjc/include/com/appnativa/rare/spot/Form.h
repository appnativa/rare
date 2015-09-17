//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/spot/Form.java
//
//  Created by decoteaud on 9/15/15.
//

#ifndef _RARESPOTForm_H_
#define _RARESPOTForm_H_

@class SPOTBoolean;
@class SPOTPrintableString;

#import "JreEmulation.h"
#include "com/appnativa/rare/spot/GroupBox.h"

@interface RARESPOTForm : RARESPOTGroupBox {
 @public
  SPOTBoolean *actAsFormViewer_;
  SPOTPrintableString *submitAttributes_;
  SPOTBoolean *retainInitialFieldValues_;
  SPOTPrintableString *defaultButtonName_;
}

- (id)init;
- (id)initWithBoolean:(BOOL)optional;
- (id)initWithBoolean:(BOOL)optional
          withBoolean:(BOOL)setElements;
- (void)spot_setElements;
- (void)copyAllFieldsTo:(RARESPOTForm *)other;
@end

J2OBJC_FIELD_SETTER(RARESPOTForm, actAsFormViewer_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTForm, submitAttributes_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTForm, retainInitialFieldValues_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTForm, defaultButtonName_, SPOTPrintableString *)

typedef RARESPOTForm ComAppnativaRareSpotForm;

#endif // _RARESPOTForm_H_
