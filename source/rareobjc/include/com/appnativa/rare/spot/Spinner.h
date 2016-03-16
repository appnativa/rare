//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/spot/Spinner.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RARESPOTSpinner_H_
#define _RARESPOTSpinner_H_

@class SPOTBoolean;
@class SPOTInteger;

#import "JreEmulation.h"
#include "com/appnativa/rare/spot/Widget.h"

@interface RARESPOTSpinner : RARESPOTWidget {
 @public
  SPOTInteger *visibleCharacters_;
  SPOTBoolean *editable_;
  SPOTBoolean *isCircular_;
  SPOTBoolean *buttonsVisible_;
  SPOTInteger *selectedIndex_;
  SPOTBoolean *autoSelect_;
  SPOTBoolean *useDesktopStyle_;
}

- (id)init;
- (id)initWithBoolean:(BOOL)optional;
- (id)initWithBoolean:(BOOL)optional
          withBoolean:(BOOL)setElements;
- (void)spot_setElements;
- (void)copyAllFieldsTo:(RARESPOTSpinner *)other;
@end

J2OBJC_FIELD_SETTER(RARESPOTSpinner, visibleCharacters_, SPOTInteger *)
J2OBJC_FIELD_SETTER(RARESPOTSpinner, editable_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTSpinner, isCircular_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTSpinner, buttonsVisible_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTSpinner, selectedIndex_, SPOTInteger *)
J2OBJC_FIELD_SETTER(RARESPOTSpinner, autoSelect_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTSpinner, useDesktopStyle_, SPOTBoolean *)

typedef RARESPOTSpinner ComAppnativaRareSpotSpinner;

#endif // _RARESPOTSpinner_H_
