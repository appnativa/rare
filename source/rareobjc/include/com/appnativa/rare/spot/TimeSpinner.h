//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/spot/TimeSpinner.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RARESPOTTimeSpinner_H_
#define _RARESPOTTimeSpinner_H_

@class SPOTPrintableString;
@class SPOTTime;

#import "JreEmulation.h"
#include "com/appnativa/rare/spot/Spinner.h"

@interface RARESPOTTimeSpinner : RARESPOTSpinner {
 @public
  SPOTTime *value_;
  SPOTTime *minValue_;
  SPOTTime *maxValue_;
  SPOTPrintableString *format_;
}

- (id)init;
- (id)initWithBoolean:(BOOL)optional;
- (id)initWithBoolean:(BOOL)optional
          withBoolean:(BOOL)setElements;
- (void)spot_setElements;
- (void)copyAllFieldsTo:(RARESPOTTimeSpinner *)other;
@end

J2OBJC_FIELD_SETTER(RARESPOTTimeSpinner, value_, SPOTTime *)
J2OBJC_FIELD_SETTER(RARESPOTTimeSpinner, minValue_, SPOTTime *)
J2OBJC_FIELD_SETTER(RARESPOTTimeSpinner, maxValue_, SPOTTime *)
J2OBJC_FIELD_SETTER(RARESPOTTimeSpinner, format_, SPOTPrintableString *)

typedef RARESPOTTimeSpinner ComAppnativaRareSpotTimeSpinner;

#endif // _RARESPOTTimeSpinner_H_
