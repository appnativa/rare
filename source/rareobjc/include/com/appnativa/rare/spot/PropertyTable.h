//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/spot/PropertyTable.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RARESPOTPropertyTable_H_
#define _RARESPOTPropertyTable_H_

@class IOSIntArray;
@class IOSObjectArray;
@class JavaLangInteger;
@class RARESPOTPropertyTable_CCategorySortOrder;
@class RARESPOTPropertyTable_CPropertiesOrder;
@class SPOTBoolean;
@class SPOTPrintableString;
@class SPOTSet;
@protocol iSPOTElement;

#import "JreEmulation.h"
#include "com/appnativa/rare/spot/TreeTable.h"
#include "com/appnativa/spot/SPOTEnumerated.h"

@interface RARESPOTPropertyTable : RARESPOTTreeTable {
 @public
  SPOTBoolean *usePane_;
  SPOTSet *paneToolbarActions_;
  RARESPOTPropertyTable_CPropertiesOrder *propertiesOrder_;
  RARESPOTPropertyTable_CCategorySortOrder *categorySortOrder_;
  SPOTBoolean *paintMargin_;
  SPOTPrintableString *miscCategoryName_;
  SPOTPrintableString *expandedCategories_;
}

- (id)init;
- (id)initWithBoolean:(BOOL)optional;
- (id)initWithBoolean:(BOOL)optional
          withBoolean:(BOOL)setElements;
- (void)spot_setElements;
- (SPOTSet *)getPaneToolbarActions;
- (SPOTSet *)getPaneToolbarActionsReference;
- (void)setPaneToolbarActionsWithISPOTElement:(id<iSPOTElement>)reference;
- (void)copyAllFieldsTo:(RARESPOTPropertyTable *)other;
@end

J2OBJC_FIELD_SETTER(RARESPOTPropertyTable, usePane_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTPropertyTable, paneToolbarActions_, SPOTSet *)
J2OBJC_FIELD_SETTER(RARESPOTPropertyTable, propertiesOrder_, RARESPOTPropertyTable_CPropertiesOrder *)
J2OBJC_FIELD_SETTER(RARESPOTPropertyTable, categorySortOrder_, RARESPOTPropertyTable_CCategorySortOrder *)
J2OBJC_FIELD_SETTER(RARESPOTPropertyTable, paintMargin_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTPropertyTable, miscCategoryName_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTPropertyTable, expandedCategories_, SPOTPrintableString *)

typedef RARESPOTPropertyTable ComAppnativaRareSpotPropertyTable;

#define RARESPOTPropertyTable_CPropertiesOrder_categorized 2
#define RARESPOTPropertyTable_CPropertiesOrder_sorted 1
#define RARESPOTPropertyTable_CPropertiesOrder_unsorted 0

@interface RARESPOTPropertyTable_CPropertiesOrder : SPOTEnumerated {
}

+ (int)unsorted;
+ (int)sorted;
+ (int)categorized;
+ (IOSIntArray *)_nchoices;
+ (IOSObjectArray *)_schoices;
- (id)init;
- (id)initWithInt:(int)val;
- (id)initWithJavaLangInteger:(JavaLangInteger *)ival
                 withNSString:(NSString *)sval
          withJavaLangInteger:(JavaLangInteger *)idefaultval
                 withNSString:(NSString *)sdefaultval
                  withBoolean:(BOOL)optional;
- (NSString *)spot_getValidityRange;
@end

#define RARESPOTPropertyTable_CCategorySortOrder_ascending 1
#define RARESPOTPropertyTable_CCategorySortOrder_descending -1
#define RARESPOTPropertyTable_CCategorySortOrder_unsorted 0

@interface RARESPOTPropertyTable_CCategorySortOrder : SPOTEnumerated {
}

+ (int)descending;
+ (int)unsorted;
+ (int)ascending;
+ (IOSIntArray *)_nchoices;
+ (IOSObjectArray *)_schoices;
- (id)init;
- (id)initWithInt:(int)val;
- (id)initWithJavaLangInteger:(JavaLangInteger *)ival
                 withNSString:(NSString *)sval
          withJavaLangInteger:(JavaLangInteger *)idefaultval
                 withNSString:(NSString *)sdefaultval
                  withBoolean:(BOOL)optional;
- (NSString *)spot_getValidityRange;
@end

#endif // _RARESPOTPropertyTable_H_
