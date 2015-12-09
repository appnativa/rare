//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/spot/DataCollection.java
//
//  Created by decoteaud on 12/8/15.
//

#ifndef _RARESPOTDataCollection_H_
#define _RARESPOTDataCollection_H_

@class SPOTBoolean;
@class SPOTPrintableString;

#import "JreEmulation.h"
#include "com/appnativa/spot/SPOTSequence.h"

@interface RARESPOTDataCollection : SPOTSequence {
 @public
  SPOTPrintableString *name_;
  SPOTPrintableString *handler_;
  SPOTPrintableString *dataURL_;
  SPOTPrintableString *attributes_;
  SPOTBoolean *tabular_;
}

- (id)init;
- (id)initWithBoolean:(BOOL)optional;
- (id)initWithBoolean:(BOOL)optional
          withBoolean:(BOOL)setElements;
- (void)spot_setElements;
- (void)copyAllFieldsTo:(RARESPOTDataCollection *)other;
@end

J2OBJC_FIELD_SETTER(RARESPOTDataCollection, name_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTDataCollection, handler_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTDataCollection, dataURL_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTDataCollection, attributes_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTDataCollection, tabular_, SPOTBoolean *)

typedef RARESPOTDataCollection ComAppnativaRareSpotDataCollection;

#endif // _RARESPOTDataCollection_H_
