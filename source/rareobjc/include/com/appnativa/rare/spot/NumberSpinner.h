//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/spot/NumberSpinner.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RARESPOTNumberSpinner_H_
#define _RARESPOTNumberSpinner_H_

@class SPOTBoolean;
@class SPOTPrintableString;
@class SPOTReal;

#import "JreEmulation.h"
#include "com/appnativa/rare/spot/Spinner.h"

@interface RARESPOTNumberSpinner : RARESPOTSpinner {
 @public
  SPOTReal *value_;
  SPOTReal *minValue_;
  SPOTReal *maxValue_;
  SPOTReal *incrementValue_;
  SPOTBoolean *supportDecimalValues_;
  SPOTPrintableString *format_;
}

- (id)init;
- (id)initWithBoolean:(BOOL)optional;
- (id)initWithBoolean:(BOOL)optional
          withBoolean:(BOOL)setElements;
- (void)spot_setElements;
- (void)copyAllFieldsTo:(RARESPOTNumberSpinner *)other;
@end

J2OBJC_FIELD_SETTER(RARESPOTNumberSpinner, value_, SPOTReal *)
J2OBJC_FIELD_SETTER(RARESPOTNumberSpinner, minValue_, SPOTReal *)
J2OBJC_FIELD_SETTER(RARESPOTNumberSpinner, maxValue_, SPOTReal *)
J2OBJC_FIELD_SETTER(RARESPOTNumberSpinner, incrementValue_, SPOTReal *)
J2OBJC_FIELD_SETTER(RARESPOTNumberSpinner, supportDecimalValues_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTNumberSpinner, format_, SPOTPrintableString *)

typedef RARESPOTNumberSpinner ComAppnativaRareSpotNumberSpinner;

#endif // _RARESPOTNumberSpinner_H_
