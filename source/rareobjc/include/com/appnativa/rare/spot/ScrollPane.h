//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/spot/ScrollPane.java
//
//  Created by decoteaud on 12/8/15.
//

#ifndef _RARESPOTScrollPane_H_
#define _RARESPOTScrollPane_H_

@class IOSIntArray;
@class IOSObjectArray;
@class JavaLangInteger;
@class RARESPOTScrollPane_CHorizontalScrollbar;
@class RARESPOTScrollPane_CVerticalScrollbar;
@class SPOTAny;
@class SPOTBoolean;
@class SPOTPrintableString;

#import "JreEmulation.h"
#include "com/appnativa/spot/SPOTEnumerated.h"
#include "com/appnativa/spot/SPOTSequence.h"

@interface RARESPOTScrollPane : SPOTSequence {
 @public
  RARESPOTScrollPane_CHorizontalScrollbar *horizontalScrollbar_;
  RARESPOTScrollPane_CVerticalScrollbar *verticalScrollbar_;
  SPOTAny *columnHeader_;
  SPOTAny *rowHeader_;
  SPOTAny *columnFooter_;
  SPOTAny *rowFooter_;
  SPOTAny *urCorner_;
  SPOTAny *lrCorner_;
  SPOTAny *ulCorner_;
  SPOTAny *llCorner_;
  SPOTBoolean *wheelScrollingAllowed_;
  SPOTBoolean *extendVerticalScrollbar_;
  SPOTBoolean *extendHorizontalScrollbar_;
  SPOTPrintableString *templateName_;
}

- (id)init;
- (id)initWithBoolean:(BOOL)optional;
- (id)initWithBoolean:(BOOL)optional
          withBoolean:(BOOL)setElements;
- (void)spot_setElements;
- (BOOL)isAlwaysHidden;
- (BOOL)isAlwaysVisible;
- (BOOL)isHorizontalScrollEnabled;
- (BOOL)isVerticalScrollEnabled;
- (BOOL)hasColumnWidgets;
- (BOOL)hasRowWidgets;
- (void)copyAllFieldsTo:(RARESPOTScrollPane *)other;
@end

J2OBJC_FIELD_SETTER(RARESPOTScrollPane, horizontalScrollbar_, RARESPOTScrollPane_CHorizontalScrollbar *)
J2OBJC_FIELD_SETTER(RARESPOTScrollPane, verticalScrollbar_, RARESPOTScrollPane_CVerticalScrollbar *)
J2OBJC_FIELD_SETTER(RARESPOTScrollPane, columnHeader_, SPOTAny *)
J2OBJC_FIELD_SETTER(RARESPOTScrollPane, rowHeader_, SPOTAny *)
J2OBJC_FIELD_SETTER(RARESPOTScrollPane, columnFooter_, SPOTAny *)
J2OBJC_FIELD_SETTER(RARESPOTScrollPane, rowFooter_, SPOTAny *)
J2OBJC_FIELD_SETTER(RARESPOTScrollPane, urCorner_, SPOTAny *)
J2OBJC_FIELD_SETTER(RARESPOTScrollPane, lrCorner_, SPOTAny *)
J2OBJC_FIELD_SETTER(RARESPOTScrollPane, ulCorner_, SPOTAny *)
J2OBJC_FIELD_SETTER(RARESPOTScrollPane, llCorner_, SPOTAny *)
J2OBJC_FIELD_SETTER(RARESPOTScrollPane, wheelScrollingAllowed_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTScrollPane, extendVerticalScrollbar_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTScrollPane, extendHorizontalScrollbar_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTScrollPane, templateName_, SPOTPrintableString *)

typedef RARESPOTScrollPane ComAppnativaRareSpotScrollPane;

#define RARESPOTScrollPane_CHorizontalScrollbar_hidden 0
#define RARESPOTScrollPane_CHorizontalScrollbar_show_always 2
#define RARESPOTScrollPane_CHorizontalScrollbar_show_as_needed 1

@interface RARESPOTScrollPane_CHorizontalScrollbar : SPOTEnumerated {
}

+ (int)hidden;
+ (int)show_as_needed;
+ (int)show_always;
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
- (void)spot_setAttributes;
@end

#define RARESPOTScrollPane_CVerticalScrollbar_hidden 0
#define RARESPOTScrollPane_CVerticalScrollbar_show_always 2
#define RARESPOTScrollPane_CVerticalScrollbar_show_as_needed 1

@interface RARESPOTScrollPane_CVerticalScrollbar : SPOTEnumerated {
}

+ (int)hidden;
+ (int)show_as_needed;
+ (int)show_always;
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
- (void)spot_setAttributes;
@end

#endif // _RARESPOTScrollPane_H_
