//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/spot/TextField.java
//
//  Created by decoteaud on 3/11/16.
//

#include "IOSClass.h"
#include "IOSIntArray.h"
#include "IOSObjectArray.h"
#include "com/appnativa/rare/spot/EmptyText.h"
#include "com/appnativa/rare/spot/TextField.h"
#include "com/appnativa/spot/SPOTBoolean.h"
#include "com/appnativa/spot/SPOTEnumerated.h"
#include "com/appnativa/spot/SPOTInteger.h"
#include "com/appnativa/spot/SPOTPrintableString.h"
#include "com/appnativa/spot/SPOTSequence.h"
#include "com/appnativa/spot/aSPOTElement.h"
#include "com/appnativa/spot/iSPOTElement.h"
#include "java/lang/Boolean.h"
#include "java/lang/ClassCastException.h"
#include "java/lang/Integer.h"

@implementation RARESPOTTextField

- (id)init {
  return [self initRARESPOTTextFieldWithBoolean:YES];
}

- (id)initRARESPOTTextFieldWithBoolean:(BOOL)optional {
  if (self = [super initWithBoolean:optional withBoolean:NO]) {
    visibleCharacters_ = [[SPOTInteger alloc] initWithJavaLangInteger:nil withJavaLangInteger:[JavaLangInteger valueOfWithInt:0] withJavaLangInteger:nil withBoolean:YES];
    value_ = [[SPOTPrintableString alloc] init];
    emptyText_ = nil;
    errorMessage_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:80 withBoolean:YES];
    inputMask_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:64 withBoolean:YES];
    inputValidator_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:64 withBoolean:YES];
    validCharacters_ = [[SPOTPrintableString alloc] init];
    editable_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:YES] withBoolean:NO];
    minCharacters_ = [[SPOTInteger alloc] initWithJavaLangInteger:nil withJavaLangInteger:[JavaLangInteger valueOfWithInt:0] withJavaLangInteger:nil withBoolean:YES];
    maxCharacters_ = [[SPOTInteger alloc] initWithJavaLangInteger:nil withJavaLangInteger:[JavaLangInteger valueOfWithInt:0] withJavaLangInteger:nil withBoolean:YES];
    undoLimit_ = [[SPOTInteger alloc] initWithNSNumber:nil withNSNumber:[JavaLangInteger valueOfWithInt:-1] withNSNumber:nil withNSNumber:[JavaLangInteger valueOfWithInt:100] withBoolean:NO];
    speechInputSupported_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:YES] withBoolean:NO];
    allowDefaultSuggestions_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:NO] withBoolean:NO];
    keyboardType_ = [[RARESPOTTextField_CKeyboardType alloc] initWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:[JavaLangInteger valueOfWithInt:RARESPOTTextField_CKeyboardType_default_type] withNSString:@"default_type" withBoolean:NO];
    keyboardReturnButtonType_ = [[RARESPOTTextField_CKeyboardReturnButtonType alloc] initWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:[JavaLangInteger valueOfWithInt:RARESPOTTextField_CKeyboardReturnButtonType_default_type] withNSString:@"default_type" withBoolean:NO];
    [self spot_setElements];
  }
  return self;
}

- (id)initWithBoolean:(BOOL)optional {
  return [self initRARESPOTTextFieldWithBoolean:optional];
}

- (id)initWithBoolean:(BOOL)optional
          withBoolean:(BOOL)setElements {
  if (self = [super initWithBoolean:optional withBoolean:setElements]) {
    visibleCharacters_ = [[SPOTInteger alloc] initWithJavaLangInteger:nil withJavaLangInteger:[JavaLangInteger valueOfWithInt:0] withJavaLangInteger:nil withBoolean:YES];
    value_ = [[SPOTPrintableString alloc] init];
    emptyText_ = nil;
    errorMessage_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:80 withBoolean:YES];
    inputMask_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:64 withBoolean:YES];
    inputValidator_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:64 withBoolean:YES];
    validCharacters_ = [[SPOTPrintableString alloc] init];
    editable_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:YES] withBoolean:NO];
    minCharacters_ = [[SPOTInteger alloc] initWithJavaLangInteger:nil withJavaLangInteger:[JavaLangInteger valueOfWithInt:0] withJavaLangInteger:nil withBoolean:YES];
    maxCharacters_ = [[SPOTInteger alloc] initWithJavaLangInteger:nil withJavaLangInteger:[JavaLangInteger valueOfWithInt:0] withJavaLangInteger:nil withBoolean:YES];
    undoLimit_ = [[SPOTInteger alloc] initWithNSNumber:nil withNSNumber:[JavaLangInteger valueOfWithInt:-1] withNSNumber:nil withNSNumber:[JavaLangInteger valueOfWithInt:100] withBoolean:NO];
    speechInputSupported_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:YES] withBoolean:NO];
    allowDefaultSuggestions_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:NO] withBoolean:NO];
    keyboardType_ = [[RARESPOTTextField_CKeyboardType alloc] initWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:[JavaLangInteger valueOfWithInt:RARESPOTTextField_CKeyboardType_default_type] withNSString:@"default_type" withBoolean:NO];
    keyboardReturnButtonType_ = [[RARESPOTTextField_CKeyboardReturnButtonType alloc] initWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:[JavaLangInteger valueOfWithInt:RARESPOTTextField_CKeyboardReturnButtonType_default_type] withNSString:@"default_type" withBoolean:NO];
  }
  return self;
}

- (void)spot_setElements {
  self->elementsSizeHint_ += 15;
  self->attributeSizeHint_ += 2;
  [super spot_setElements];
  [self spot_defineAttributeWithNSString:@"onAction" withNSString:nil];
  [self spot_defineAttributeWithNSString:@"onChange" withNSString:nil];
  [self spot_addElementWithNSString:@"visibleCharacters" withISPOTElement:visibleCharacters_];
  [self spot_addElementWithNSString:@"value" withISPOTElement:value_];
  [self spot_addElementWithNSString:@"emptyText" withISPOTElement:emptyText_];
  [self spot_addElementWithNSString:@"errorMessage" withISPOTElement:errorMessage_];
  [((SPOTPrintableString *) nil_chk(errorMessage_)) spot_defineAttributeWithNSString:@"displayWidget" withNSString:nil];
  [self spot_addElementWithNSString:@"inputMask" withISPOTElement:inputMask_];
  [((SPOTPrintableString *) nil_chk(inputMask_)) spot_defineAttributeWithNSString:@"placeHolder" withNSString:nil];
  [inputMask_ spot_defineAttributeWithNSString:@"preserveLiteralCharacters" withNSString:@"false"];
  [self spot_addElementWithNSString:@"inputValidator" withISPOTElement:inputValidator_];
  [((SPOTPrintableString *) nil_chk(inputValidator_)) spot_defineAttributeWithNSString:@"valueType" withNSString:@"number"];
  [inputValidator_ spot_defineAttributeWithNSString:@"reformat" withNSString:nil];
  [inputValidator_ spot_defineAttributeWithNSString:@"validateOnLostFocus" withNSString:@"true"];
  [inputValidator_ spot_defineAttributeWithNSString:@"submitRawValue" withNSString:nil];
  [inputValidator_ spot_defineAttributeWithNSString:@"maximum" withNSString:nil];
  [inputValidator_ spot_defineAttributeWithNSString:@"minimum" withNSString:nil];
  [self spot_addElementWithNSString:@"validCharacters" withISPOTElement:validCharacters_];
  [self spot_addElementWithNSString:@"editable" withISPOTElement:editable_];
  [self spot_addElementWithNSString:@"minCharacters" withISPOTElement:minCharacters_];
  [self spot_addElementWithNSString:@"maxCharacters" withISPOTElement:maxCharacters_];
  [self spot_addElementWithNSString:@"undoLimit" withISPOTElement:undoLimit_];
  [self spot_addElementWithNSString:@"speechInputSupported" withISPOTElement:speechInputSupported_];
  [self spot_addElementWithNSString:@"allowDefaultSuggestions" withISPOTElement:allowDefaultSuggestions_];
  [self spot_addElementWithNSString:@"keyboardType" withISPOTElement:keyboardType_];
  [((RARESPOTTextField_CKeyboardType *) nil_chk(keyboardType_)) spot_defineAttributeWithNSString:@"autoCorrect" withNSString:nil];
  [keyboardType_ spot_defineAttributeWithNSString:@"autoComplete" withNSString:nil];
  [keyboardType_ spot_defineAttributeWithNSString:@"autoShow" withNSString:@"true"];
  [keyboardType_ spot_defineAttributeWithNSString:@"autoCapatilize" withNSString:@"sentences"];
  [keyboardType_ spot_defineAttributeWithNSString:@"spellCheck" withNSString:nil];
  [self spot_addElementWithNSString:@"keyboardReturnButtonType" withISPOTElement:keyboardReturnButtonType_];
  [((RARESPOTTextField_CKeyboardReturnButtonType *) nil_chk(keyboardReturnButtonType_)) spot_defineAttributeWithNSString:@"autoEnable" withNSString:@"true"];
  [keyboardReturnButtonType_ spot_defineAttributeWithNSString:@"text" withNSString:nil];
}

- (RARESPOTEmptyText *)getEmptyText {
  return emptyText_;
}

- (RARESPOTEmptyText *)getEmptyTextReference {
  if (emptyText_ == nil) {
    emptyText_ = [[RARESPOTEmptyText alloc] initWithBoolean:YES];
    (void) [super spot_setReferenceWithNSString:@"emptyText" withISPOTElement:emptyText_];
  }
  return emptyText_;
}

- (void)setEmptyTextWithISPOTElement:(id<iSPOTElement>)reference {
  emptyText_ = (RARESPOTEmptyText *) check_class_cast(reference, [RARESPOTEmptyText class]);
  (void) [self spot_setReferenceWithNSString:@"emptyText" withISPOTElement:reference];
}

- (void)copyAllFieldsTo:(RARESPOTTextField *)other {
  [super copyAllFieldsTo:other];
  other->allowDefaultSuggestions_ = allowDefaultSuggestions_;
  other->editable_ = editable_;
  other->emptyText_ = emptyText_;
  other->errorMessage_ = errorMessage_;
  other->inputMask_ = inputMask_;
  other->inputValidator_ = inputValidator_;
  other->keyboardReturnButtonType_ = keyboardReturnButtonType_;
  other->keyboardType_ = keyboardType_;
  other->maxCharacters_ = maxCharacters_;
  other->minCharacters_ = minCharacters_;
  other->speechInputSupported_ = speechInputSupported_;
  other->undoLimit_ = undoLimit_;
  other->validCharacters_ = validCharacters_;
  other->value_ = value_;
  other->visibleCharacters_ = visibleCharacters_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "initWithBoolean:withBoolean:", NULL, NULL, 0x4, NULL },
    { "spot_setElements", NULL, "V", 0x4, NULL },
    { "getEmptyText", NULL, "LRARESPOTEmptyText", 0x1, NULL },
    { "getEmptyTextReference", NULL, "LRARESPOTEmptyText", 0x1, NULL },
    { "setEmptyTextWithISPOTElement:", NULL, "V", 0x1, "JavaLangClassCastException" },
  };
  static J2ObjcFieldInfo fields[] = {
    { "visibleCharacters_", NULL, 0x1, "LSPOTInteger" },
    { "value_", NULL, 0x1, "LSPOTPrintableString" },
    { "emptyText_", NULL, 0x4, "LRARESPOTEmptyText" },
    { "errorMessage_", NULL, 0x1, "LSPOTPrintableString" },
    { "inputMask_", NULL, 0x1, "LSPOTPrintableString" },
    { "inputValidator_", NULL, 0x1, "LSPOTPrintableString" },
    { "validCharacters_", NULL, 0x1, "LSPOTPrintableString" },
    { "editable_", NULL, 0x1, "LSPOTBoolean" },
    { "minCharacters_", NULL, 0x1, "LSPOTInteger" },
    { "maxCharacters_", NULL, 0x1, "LSPOTInteger" },
    { "undoLimit_", NULL, 0x1, "LSPOTInteger" },
    { "speechInputSupported_", NULL, 0x1, "LSPOTBoolean" },
    { "allowDefaultSuggestions_", NULL, 0x1, "LSPOTBoolean" },
    { "keyboardType_", NULL, 0x1, "LRARESPOTTextField_CKeyboardType" },
    { "keyboardReturnButtonType_", NULL, 0x1, "LRARESPOTTextField_CKeyboardReturnButtonType" },
  };
  static J2ObjcClassInfo _RARESPOTTextField = { "TextField", "com.appnativa.rare.spot", NULL, 0x1, 5, methods, 15, fields, 0, NULL};
  return &_RARESPOTTextField;
}

@end
@implementation RARESPOTTextField_CKeyboardType

static IOSIntArray * RARESPOTTextField_CKeyboardType__nchoices_;
static IOSObjectArray * RARESPOTTextField_CKeyboardType__schoices_;

+ (int)default_type {
  return RARESPOTTextField_CKeyboardType_default_type;
}

+ (int)text_type {
  return RARESPOTTextField_CKeyboardType_text_type;
}

+ (int)number_type {
  return RARESPOTTextField_CKeyboardType_number_type;
}

+ (int)number_punctuation_type {
  return RARESPOTTextField_CKeyboardType_number_punctuation_type;
}

+ (int)decimal_type {
  return RARESPOTTextField_CKeyboardType_decimal_type;
}

+ (int)decimal_punctuation_type {
  return RARESPOTTextField_CKeyboardType_decimal_punctuation_type;
}

+ (int)phone_number_type {
  return RARESPOTTextField_CKeyboardType_phone_number_type;
}

+ (int)name_phone_number_type {
  return RARESPOTTextField_CKeyboardType_name_phone_number_type;
}

+ (int)email_address_type {
  return RARESPOTTextField_CKeyboardType_email_address_type;
}

+ (int)url_type {
  return RARESPOTTextField_CKeyboardType_url_type;
}

+ (IOSIntArray *)_nchoices {
  return RARESPOTTextField_CKeyboardType__nchoices_;
}

+ (IOSObjectArray *)_schoices {
  return RARESPOTTextField_CKeyboardType__schoices_;
}

- (id)init {
  return [self initRARESPOTTextField_CKeyboardTypeWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:nil withNSString:nil withBoolean:YES];
}

- (id)initWithInt:(int)val {
  if (self = [super init]) {
    _sChoices_ = RARESPOTTextField_CKeyboardType__schoices_;
    _nChoices_ = RARESPOTTextField_CKeyboardType__nchoices_;
    [self spot_setAttributes];
    [self setValueWithInt:val];
  }
  return self;
}

- (id)initRARESPOTTextField_CKeyboardTypeWithJavaLangInteger:(JavaLangInteger *)ival
                                                withNSString:(NSString *)sval
                                         withJavaLangInteger:(JavaLangInteger *)idefaultval
                                                withNSString:(NSString *)sdefaultval
                                                 withBoolean:(BOOL)optional {
  if (self = [super initWithJavaLangInteger:ival withNSString:sval withJavaLangInteger:idefaultval withNSString:sdefaultval withBoolean:optional]) {
    _sChoices_ = RARESPOTTextField_CKeyboardType__schoices_;
    _nChoices_ = RARESPOTTextField_CKeyboardType__nchoices_;
    [self spot_setAttributes];
  }
  return self;
}

- (id)initWithJavaLangInteger:(JavaLangInteger *)ival
                 withNSString:(NSString *)sval
          withJavaLangInteger:(JavaLangInteger *)idefaultval
                 withNSString:(NSString *)sdefaultval
                  withBoolean:(BOOL)optional {
  return [self initRARESPOTTextField_CKeyboardTypeWithJavaLangInteger:ival withNSString:sval withJavaLangInteger:idefaultval withNSString:sdefaultval withBoolean:optional];
}

- (NSString *)spot_getValidityRange {
  return @"{default_type(0), text_type(1), number_type(2), number_punctuation_type(3), decimal_type(4), decimal_punctuation_type(5), phone_number_type(6), name_phone_number_type(7), email_address_type(8), url_type(9) }";
}

- (void)spot_setAttributes {
  self->attributeSizeHint_ += 5;
  [self spot_defineAttributeWithNSString:@"autoCorrect" withNSString:nil];
  [self spot_defineAttributeWithNSString:@"autoComplete" withNSString:nil];
  [self spot_defineAttributeWithNSString:@"autoShow" withNSString:@"true"];
  [self spot_defineAttributeWithNSString:@"autoCapatilize" withNSString:@"sentences"];
  [self spot_defineAttributeWithNSString:@"spellCheck" withNSString:nil];
}

+ (void)initialize {
  if (self == [RARESPOTTextField_CKeyboardType class]) {
    RARESPOTTextField_CKeyboardType__nchoices_ = [IOSIntArray arrayWithInts:(int[]){ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 } count:10];
    RARESPOTTextField_CKeyboardType__schoices_ = [IOSObjectArray arrayWithObjects:(id[]){ @"default_type", @"text_type", @"number_type", @"number_punctuation_type", @"decimal_type", @"decimal_punctuation_type", @"phone_number_type", @"name_phone_number_type", @"email_address_type", @"url_type" } count:10 type:[IOSClass classWithClass:[NSString class]]];
  }
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "spot_getValidityRange", NULL, "LNSString", 0x1, NULL },
    { "spot_setAttributes", NULL, "V", 0x2, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "default_type_", NULL, 0x19, "I" },
    { "text_type_", NULL, 0x19, "I" },
    { "number_type_", NULL, 0x19, "I" },
    { "number_punctuation_type_", NULL, 0x19, "I" },
    { "decimal_type_", NULL, 0x19, "I" },
    { "decimal_punctuation_type_", NULL, 0x19, "I" },
    { "phone_number_type_", NULL, 0x19, "I" },
    { "name_phone_number_type_", NULL, 0x19, "I" },
    { "email_address_type_", NULL, 0x19, "I" },
    { "url_type_", NULL, 0x19, "I" },
    { "_nchoices_", NULL, 0x1a, "LIOSIntArray" },
    { "_schoices_", NULL, 0x1a, "LIOSObjectArray" },
  };
  static J2ObjcClassInfo _RARESPOTTextField_CKeyboardType = { "CKeyboardType", "com.appnativa.rare.spot", "TextField", 0x9, 2, methods, 12, fields, 0, NULL};
  return &_RARESPOTTextField_CKeyboardType;
}

@end
@implementation RARESPOTTextField_CKeyboardReturnButtonType

static IOSIntArray * RARESPOTTextField_CKeyboardReturnButtonType__nchoices_;
static IOSObjectArray * RARESPOTTextField_CKeyboardReturnButtonType__schoices_;

+ (int)default_type {
  return RARESPOTTextField_CKeyboardReturnButtonType_default_type;
}

+ (int)go_type {
  return RARESPOTTextField_CKeyboardReturnButtonType_go_type;
}

+ (int)join_type {
  return RARESPOTTextField_CKeyboardReturnButtonType_join_type;
}

+ (int)next_type {
  return RARESPOTTextField_CKeyboardReturnButtonType_next_type;
}

+ (int)search_type {
  return RARESPOTTextField_CKeyboardReturnButtonType_search_type;
}

+ (int)send_type {
  return RARESPOTTextField_CKeyboardReturnButtonType_send_type;
}

+ (int)none_type {
  return RARESPOTTextField_CKeyboardReturnButtonType_none_type;
}

+ (int)done_type {
  return RARESPOTTextField_CKeyboardReturnButtonType_done_type;
}

+ (IOSIntArray *)_nchoices {
  return RARESPOTTextField_CKeyboardReturnButtonType__nchoices_;
}

+ (IOSObjectArray *)_schoices {
  return RARESPOTTextField_CKeyboardReturnButtonType__schoices_;
}

- (id)init {
  return [self initRARESPOTTextField_CKeyboardReturnButtonTypeWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:nil withNSString:nil withBoolean:YES];
}

- (id)initWithInt:(int)val {
  if (self = [super init]) {
    _sChoices_ = RARESPOTTextField_CKeyboardReturnButtonType__schoices_;
    _nChoices_ = RARESPOTTextField_CKeyboardReturnButtonType__nchoices_;
    [self spot_setAttributes];
    [self setValueWithInt:val];
  }
  return self;
}

- (id)initRARESPOTTextField_CKeyboardReturnButtonTypeWithJavaLangInteger:(JavaLangInteger *)ival
                                                            withNSString:(NSString *)sval
                                                     withJavaLangInteger:(JavaLangInteger *)idefaultval
                                                            withNSString:(NSString *)sdefaultval
                                                             withBoolean:(BOOL)optional {
  if (self = [super initWithJavaLangInteger:ival withNSString:sval withJavaLangInteger:idefaultval withNSString:sdefaultval withBoolean:optional]) {
    _sChoices_ = RARESPOTTextField_CKeyboardReturnButtonType__schoices_;
    _nChoices_ = RARESPOTTextField_CKeyboardReturnButtonType__nchoices_;
    [self spot_setAttributes];
  }
  return self;
}

- (id)initWithJavaLangInteger:(JavaLangInteger *)ival
                 withNSString:(NSString *)sval
          withJavaLangInteger:(JavaLangInteger *)idefaultval
                 withNSString:(NSString *)sdefaultval
                  withBoolean:(BOOL)optional {
  return [self initRARESPOTTextField_CKeyboardReturnButtonTypeWithJavaLangInteger:ival withNSString:sval withJavaLangInteger:idefaultval withNSString:sdefaultval withBoolean:optional];
}

- (NSString *)spot_getValidityRange {
  return @"{default_type(0), go_type(1), join_type(2), next_type(3), search_type(4), send_type(5), none_type(6), done_type(7) }";
}

- (void)spot_setAttributes {
  self->attributeSizeHint_ += 2;
  [self spot_defineAttributeWithNSString:@"autoEnable" withNSString:@"true"];
  [self spot_defineAttributeWithNSString:@"text" withNSString:nil];
}

+ (void)initialize {
  if (self == [RARESPOTTextField_CKeyboardReturnButtonType class]) {
    RARESPOTTextField_CKeyboardReturnButtonType__nchoices_ = [IOSIntArray arrayWithInts:(int[]){ 0, 1, 2, 3, 4, 5, 6, 7 } count:8];
    RARESPOTTextField_CKeyboardReturnButtonType__schoices_ = [IOSObjectArray arrayWithObjects:(id[]){ @"default_type", @"go_type", @"join_type", @"next_type", @"search_type", @"send_type", @"none_type", @"done_type" } count:8 type:[IOSClass classWithClass:[NSString class]]];
  }
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "spot_getValidityRange", NULL, "LNSString", 0x1, NULL },
    { "spot_setAttributes", NULL, "V", 0x2, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "default_type_", NULL, 0x19, "I" },
    { "go_type_", NULL, 0x19, "I" },
    { "join_type_", NULL, 0x19, "I" },
    { "next_type_", NULL, 0x19, "I" },
    { "search_type_", NULL, 0x19, "I" },
    { "send_type_", NULL, 0x19, "I" },
    { "none_type_", NULL, 0x19, "I" },
    { "done_type_", NULL, 0x19, "I" },
    { "_nchoices_", NULL, 0x1a, "LIOSIntArray" },
    { "_schoices_", NULL, 0x1a, "LIOSObjectArray" },
  };
  static J2ObjcClassInfo _RARESPOTTextField_CKeyboardReturnButtonType = { "CKeyboardReturnButtonType", "com.appnativa.rare.spot", "TextField", 0x9, 2, methods, 10, fields, 0, NULL};
  return &_RARESPOTTextField_CKeyboardReturnButtonType;
}

@end
