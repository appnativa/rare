//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/spot/TreeTable.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RARESPOTTreeTable_H_
#define _RARESPOTTreeTable_H_

@class SPOTBoolean;
@class SPOTInteger;

#import "JreEmulation.h"
#include "com/appnativa/rare/spot/Table.h"

@interface RARESPOTTreeTable : RARESPOTTable {
 @public
  SPOTInteger *expandableColumn_;
  SPOTBoolean *expandAll_;
  SPOTBoolean *showRootHandles_;
  SPOTBoolean *parentItemsSelectable_;
  SPOTInteger *indentBy_;
  SPOTBoolean *autoScrollOnExpansion_;
}

- (id)init;
- (id)initWithBoolean:(BOOL)optional;
- (id)initWithBoolean:(BOOL)optional
          withBoolean:(BOOL)setElements;
- (void)spot_setElements;
- (void)copyAllFieldsTo:(RARESPOTTreeTable *)other;
@end

J2OBJC_FIELD_SETTER(RARESPOTTreeTable, expandableColumn_, SPOTInteger *)
J2OBJC_FIELD_SETTER(RARESPOTTreeTable, expandAll_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTTreeTable, showRootHandles_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTTreeTable, parentItemsSelectable_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTTreeTable, indentBy_, SPOTInteger *)
J2OBJC_FIELD_SETTER(RARESPOTTreeTable, autoScrollOnExpansion_, SPOTBoolean *)

typedef RARESPOTTreeTable ComAppnativaRareSpotTreeTable;

#endif // _RARESPOTTreeTable_H_