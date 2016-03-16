//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/spot/DocumentPane.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RARESPOTDocumentPane_H_
#define _RARESPOTDocumentPane_H_

@class IOSIntArray;
@class IOSObjectArray;
@class JavaLangInteger;
@class RARESPOTDocumentPane_CCursorShown;
@class RARESPOTDocumentPane_CKeyboardReturnButtonType;
@class RARESPOTDocumentPane_CKeyboardType;
@class RARESPOTDocumentPane_CStyle;
@class RARESPOTScrollPane;
@class SPOTBoolean;
@protocol iSPOTElement;

#import "JreEmulation.h"
#include "com/appnativa/rare/spot/Viewer.h"
#include "com/appnativa/spot/SPOTEnumerated.h"

@interface RARESPOTDocumentPane : RARESPOTViewer {
 @public
  SPOTBoolean *editable_;
  SPOTBoolean *wordWrap_;
  SPOTBoolean *speechInputSupported_;
  RARESPOTDocumentPane_CStyle *style_;
  RARESPOTDocumentPane_CCursorShown *cursorShown_;
  RARESPOTDocumentPane_CKeyboardType *keyboardType_;
  RARESPOTDocumentPane_CKeyboardReturnButtonType *keyboardReturnButtonType_;
  RARESPOTScrollPane *scrollPane_;
}

- (id)init;
- (id)initWithBoolean:(BOOL)optional;
- (id)initWithBoolean:(BOOL)optional
          withBoolean:(BOOL)setElements;
- (void)spot_setElements;
- (RARESPOTScrollPane *)getScrollPane;
- (RARESPOTScrollPane *)getScrollPaneReference;
- (void)setScrollPaneWithISPOTElement:(id<iSPOTElement>)reference;
- (void)copyAllFieldsTo:(RARESPOTDocumentPane *)other;
@end

J2OBJC_FIELD_SETTER(RARESPOTDocumentPane, editable_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTDocumentPane, wordWrap_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTDocumentPane, speechInputSupported_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTDocumentPane, style_, RARESPOTDocumentPane_CStyle *)
J2OBJC_FIELD_SETTER(RARESPOTDocumentPane, cursorShown_, RARESPOTDocumentPane_CCursorShown *)
J2OBJC_FIELD_SETTER(RARESPOTDocumentPane, keyboardType_, RARESPOTDocumentPane_CKeyboardType *)
J2OBJC_FIELD_SETTER(RARESPOTDocumentPane, keyboardReturnButtonType_, RARESPOTDocumentPane_CKeyboardReturnButtonType *)
J2OBJC_FIELD_SETTER(RARESPOTDocumentPane, scrollPane_, RARESPOTScrollPane *)

typedef RARESPOTDocumentPane ComAppnativaRareSpotDocumentPane;

#define RARESPOTDocumentPane_CStyle_html_editor 1
#define RARESPOTDocumentPane_CStyle_text_editor 0

@interface RARESPOTDocumentPane_CStyle : SPOTEnumerated {
}

+ (int)text_editor;
+ (int)html_editor;
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

#define RARESPOTDocumentPane_CCursorShown_always 1
#define RARESPOTDocumentPane_CCursorShown_always_for_non_html 2
#define RARESPOTDocumentPane_CCursorShown_never 3
#define RARESPOTDocumentPane_CCursorShown_when_editable 0

@interface RARESPOTDocumentPane_CCursorShown : SPOTEnumerated {
}

+ (int)when_editable;
+ (int)always;
+ (int)always_for_non_html;
+ (int)never;
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

#define RARESPOTDocumentPane_CKeyboardType_decimal_punctuation_type 5
#define RARESPOTDocumentPane_CKeyboardType_decimal_type 4
#define RARESPOTDocumentPane_CKeyboardType_default_type 0
#define RARESPOTDocumentPane_CKeyboardType_email_address_type 8
#define RARESPOTDocumentPane_CKeyboardType_name_phone_number_type 7
#define RARESPOTDocumentPane_CKeyboardType_number_punctuation_type 3
#define RARESPOTDocumentPane_CKeyboardType_number_type 2
#define RARESPOTDocumentPane_CKeyboardType_phone_number_type 6
#define RARESPOTDocumentPane_CKeyboardType_text_type 1
#define RARESPOTDocumentPane_CKeyboardType_url_type 9

@interface RARESPOTDocumentPane_CKeyboardType : SPOTEnumerated {
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

#define RARESPOTDocumentPane_CKeyboardReturnButtonType_default_type 0
#define RARESPOTDocumentPane_CKeyboardReturnButtonType_done_type 7
#define RARESPOTDocumentPane_CKeyboardReturnButtonType_go_type 1
#define RARESPOTDocumentPane_CKeyboardReturnButtonType_join_type 2
#define RARESPOTDocumentPane_CKeyboardReturnButtonType_next_type 3
#define RARESPOTDocumentPane_CKeyboardReturnButtonType_none_type 6
#define RARESPOTDocumentPane_CKeyboardReturnButtonType_search_type 4
#define RARESPOTDocumentPane_CKeyboardReturnButtonType_send_type 5

@interface RARESPOTDocumentPane_CKeyboardReturnButtonType : SPOTEnumerated {
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

#endif // _RARESPOTDocumentPane_H_
