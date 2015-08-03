//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/spot/TextArea.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RARESPOTTextArea_H_
#define _RARESPOTTextArea_H_

@class SPOTBoolean;
@class SPOTInteger;

#import "JreEmulation.h"
#include "com/appnativa/rare/spot/TextField.h"

@interface RARESPOTTextArea : RARESPOTTextField {
 @public
  SPOTInteger *visibleLines_;
  SPOTBoolean *wordWrap_;
  SPOTBoolean *supportScrolling_;
}

- (id)init;
- (id)initWithBoolean:(BOOL)optional;
- (id)initWithBoolean:(BOOL)optional
          withBoolean:(BOOL)setElements;
- (void)spot_setElements;
- (void)copyAllFieldsTo:(RARESPOTTextArea *)other;
@end

J2OBJC_FIELD_SETTER(RARESPOTTextArea, visibleLines_, SPOTInteger *)
J2OBJC_FIELD_SETTER(RARESPOTTextArea, wordWrap_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTTextArea, supportScrolling_, SPOTBoolean *)

typedef RARESPOTTextArea ComAppnativaRareSpotTextArea;

#endif // _RARESPOTTextArea_H_