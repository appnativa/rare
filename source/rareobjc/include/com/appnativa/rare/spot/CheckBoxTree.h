//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/spot/CheckBoxTree.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RARESPOTCheckBoxTree_H_
#define _RARESPOTCheckBoxTree_H_

@class SPOTBoolean;

#import "JreEmulation.h"
#include "com/appnativa/rare/spot/Tree.h"

@interface RARESPOTCheckBoxTree : RARESPOTTree {
 @public
  SPOTBoolean *linkSelection_;
  SPOTBoolean *checkboxTrailing_;
  SPOTBoolean *manageChildNodeSelections_;
}

- (id)init;
- (id)initWithBoolean:(BOOL)optional;
- (id)initWithBoolean:(BOOL)optional
          withBoolean:(BOOL)setElements;
- (void)spot_setElements;
- (void)copyAllFieldsTo:(RARESPOTCheckBoxTree *)other;
@end

J2OBJC_FIELD_SETTER(RARESPOTCheckBoxTree, linkSelection_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTCheckBoxTree, checkboxTrailing_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTCheckBoxTree, manageChildNodeSelections_, SPOTBoolean *)

typedef RARESPOTCheckBoxTree ComAppnativaRareSpotCheckBoxTree;

#endif // _RARESPOTCheckBoxTree_H_
