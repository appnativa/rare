//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/spot/AccessibleContext.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RARESPOTAccessibleContext_H_
#define _RARESPOTAccessibleContext_H_

@class SPOTPrintableString;

#import "JreEmulation.h"
#include "com/appnativa/spot/SPOTSequence.h"

@interface RARESPOTAccessibleContext : SPOTSequence {
 @public
  SPOTPrintableString *name_;
  SPOTPrintableString *description__;
}

- (id)init;
- (id)initWithBoolean:(BOOL)optional;
- (id)initWithBoolean:(BOOL)optional
          withBoolean:(BOOL)setElements;
- (void)spot_setElements;
- (void)copyAllFieldsTo:(RARESPOTAccessibleContext *)other;
@end

J2OBJC_FIELD_SETTER(RARESPOTAccessibleContext, name_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTAccessibleContext, description__, SPOTPrintableString *)

typedef RARESPOTAccessibleContext ComAppnativaRareSpotAccessibleContext;

#endif // _RARESPOTAccessibleContext_H_
