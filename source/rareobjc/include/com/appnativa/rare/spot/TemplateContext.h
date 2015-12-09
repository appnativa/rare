//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/spot/TemplateContext.java
//
//  Created by decoteaud on 12/8/15.
//

#ifndef _RARESPOTTemplateContext_H_
#define _RARESPOTTemplateContext_H_

@class SPOTBoolean;
@class SPOTPrintableString;
@class SPOTSet;

#import "JreEmulation.h"
#include "com/appnativa/spot/SPOTSequence.h"

@interface RARESPOTTemplateContext : SPOTSequence {
 @public
  SPOTPrintableString *name_;
  SPOTBoolean *autoSkin_;
  SPOTSet *widgets_;
  SPOTSet *columns_;
  SPOTSet *cellPainters_;
  SPOTSet *dataItems_;
  SPOTSet *collapsibles_;
  SPOTSet *regions_;
  SPOTSet *scrollPanes_;
  SPOTSet *plots_;
}

- (id)init;
- (id)initWithBoolean:(BOOL)optional;
- (id)initWithBoolean:(BOOL)optional
          withBoolean:(BOOL)setElements;
- (void)spot_setElements;
- (void)copyAllFieldsTo:(RARESPOTTemplateContext *)other;
@end

J2OBJC_FIELD_SETTER(RARESPOTTemplateContext, name_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTTemplateContext, autoSkin_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTTemplateContext, widgets_, SPOTSet *)
J2OBJC_FIELD_SETTER(RARESPOTTemplateContext, columns_, SPOTSet *)
J2OBJC_FIELD_SETTER(RARESPOTTemplateContext, cellPainters_, SPOTSet *)
J2OBJC_FIELD_SETTER(RARESPOTTemplateContext, dataItems_, SPOTSet *)
J2OBJC_FIELD_SETTER(RARESPOTTemplateContext, collapsibles_, SPOTSet *)
J2OBJC_FIELD_SETTER(RARESPOTTemplateContext, regions_, SPOTSet *)
J2OBJC_FIELD_SETTER(RARESPOTTemplateContext, scrollPanes_, SPOTSet *)
J2OBJC_FIELD_SETTER(RARESPOTTemplateContext, plots_, SPOTSet *)

typedef RARESPOTTemplateContext ComAppnativaRareSpotTemplateContext;

#endif // _RARESPOTTemplateContext_H_
