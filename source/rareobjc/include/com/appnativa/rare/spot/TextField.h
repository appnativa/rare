//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/spot/TextField.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RARESPOTTextField_H_
#define _RARESPOTTextField_H_

@class IOSIntArray;
@class IOSObjectArray;
@class JavaLangInteger;
@class RARESPOTEmptyText;
@class RARESPOTTextField_CKeyboardReturnButtonType;
@class RARESPOTTextField_CKeyboardType;
@class SPOTBoolean;
@class SPOTInteger;
@class SPOTPrintableString;
@protocol iSPOTElement;

#import "JreEmulation.h"
#include "com/appnativa/rare/spot/Widget.h"
#include "com/appnativa/spot/SPOTEnumerated.h"

@interface RARESPOTTextField : RARESPOTWidget {
 @public
  SPOTInteger *visibleCharacters_;
  SPOTPrintableString *value_;
  RARESPOTEmptyText *emptyText_;
  SPOTPrintableString *errorMessage_;
  SPOTPrintableString *inputMask_;
  SPOTPrintableString *inputValidator_;
  SPOTPrintableString *validCharacters_;
  SPOTBoolean *editable_;
  SPOTInteger *minCharacters_;
  SPOTInteger *maxCharacters_;
  SPOTInteger *undoLimit_;
  SPOTBoolean *speechInputSupported_;
  SPOTBoolean *allowDefaultSuggestions_;
  RARESPOTTextField_CKeyboardType *keyboardType_;
  RARESPOTTextField_CKeyboardReturnButtonType *keyboardReturnButtonType_;
}

- (id)init;
- (id)initWithBoolean:(BOOL)optional;
- (id)initWithBoolean:(BOOL)optional
          withBoolean:(BOOL)setElements;
- (void)spot_setElements;
- (RARESPOTEmptyText *)getEmptyText;
- (RARESPOTEmptyText *)getEmptyTextReference;
- (void)setEmptyTextWithISPOTElement:(id<iSPOTElement>)reference;
- (void)copyAllFieldsTo:(RARESPOTTextField *)other;
@end

J2OBJC_FIELD_SETTER(RARESPOTTextField, visibleCharacters_, SPOTInteger *)
J2OBJC_FIELD_SETTER(RARESPOTTextField, value_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTTextField, emptyText_, RARESPOTEmptyText *)
J2OBJC_FIELD_SETTER(RARESPOTTextField, errorMessage_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTTextField, inputMask_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTTextField, inputValidator_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTTextField, validCharacters_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTTextField, editable_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTTextField, minCharacters_, SPOTInteger *)
J2OBJC_FIELD_SETTER(RARESPOTTextField, maxCharacters_, SPOTInteger *)
J2OBJC_FIELD_SETTER(RARESPOTTextField, undoLimit_, SPOTInteger *)
J2OBJC_FIELD_SETTER(RARESPOTTextField, speechInputSupported_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTTextField, allowDefaultSuggestions_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTTextField, keyboardType_, RARESPOTTextField_CKeyboardType *)
J2OBJC_FIELD_SETTER(RARESPOTTextField, keyboardReturnButtonType_, RARESPOTTextField_CKeyboardReturnButtonType *)

typedef RARESPOTTextField ComAppnativaRareSpotTextField;

#define RARESPOTTextField_CKeyboardType_decimal_punctuation_type 5
#define RARESPOTTextField_CKeyboardType_decimal_type 4
#define RARESPOTTextField_CKeyboardType_default_type 0
#define RARESPOTTextField_CKeyboardType_email_address_type 8
#define RARESPOTTextField_CKeyboardType_name_phone_number_type 7
#define RARESPOTTextField_CKeyboardType_number_punctuation_type 3
#define RARESPOTTextField_CKeyboardType_number_type 2
#define RARESPOTTextField_CKeyboardType_phone_number_type 6
#define RARESPOTTextField_CKeyboardType_text_type 1
#define RARESPOTTextField_CKeyboardType_url_type 9

@interface RARESPOTTextField_CKeyboardType : SPOTEnumerated {
}

+ (int)default_type;
+ (int)text_type;
+ (int)number_type;
+ (int)number_punctuation_type;
+ (int)decimal_type;
+ (int)decimal_punctuation_type;
+ (int)phone_number_type;
+ (int)name_phone_number_type;
+ (int)email_address_type;
+ (int)url_type;
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

#define RARESPOTTextField_CKeyboardReturnButtonType_default_type 0
#define RARESPOTTextField_CKeyboardReturnButtonType_done_type 7
#define RARESPOTTextField_CKeyboardReturnButtonType_go_type 1
#define RARESPOTTextField_CKeyboardReturnButtonType_join_type 2
#define RARESPOTTextField_CKeyboardReturnButtonType_next_type 3
#define RARESPOTTextField_CKeyboardReturnButtonType_none_type 6
#define RARESPOTTextField_CKeyboardReturnButtonType_search_type 4
#define RARESPOTTextField_CKeyboardReturnButtonType_send_type 5

@interface RARESPOTTextField_CKeyboardReturnButtonType : SPOTEnumerated {
}

+ (int)default_type;
+ (int)go_type;
+ (int)join_type;
+ (int)next_type;
+ (int)search_type;
+ (int)send_type;
+ (int)none_type;
+ (int)done_type;
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

#endif // _RARESPOTTextField_H_
