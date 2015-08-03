//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../spot/src/com/appnativa/spot/iSPOTConstants.java
//
//  Created by decoteaud on 7/29/15.
//

#include "com/appnativa/spot/iSPOTConstants.h"


@implementation iSPOTConstants

static NSString * iSPOTConstants_STR_BAD_ELEMENT_ = @"Element [%s] for %s is invalid { %s }";
static NSString * iSPOTConstants_STR_GREATER_THAN_ = @"greater than";
static NSString * iSPOTConstants_STR_ILLEGAL_VALUE_ = @"illegal value (%s)";
static NSString * iSPOTConstants_STR_INVALID_ = @"The value of element [%s] is invalid.\nThe value { %s } is %s the acceptable range ( %s )";
static NSString * iSPOTConstants_STR_INVALID_ATTRIBUTE_ = @"Element [%s] is not defined to have the attribute [%s].";
static NSString * iSPOTConstants_STR_INVALID_ELEMENT_ = @"Element [%s] was expected; element [%s] found.";
static NSString * iSPOTConstants_STR_INVALID_RANGE_ = @"the value { %s } is %s than the specified range ( %s )";
static NSString * iSPOTConstants_STR_INVALID_VALUE_ = @"The value of element [%s] is invalid";
static NSString * iSPOTConstants_STR_LESS_THAN_ = @"less than";
static NSString * iSPOTConstants_STR_MISSING_ELEMENTS_ = @"The expected element count for [%s] was (%s); the actual count was (%s)";
static NSString * iSPOTConstants_STR_NOTONEOF_ = @"not one of";
static NSString * iSPOTConstants_STR_NOT_CLASS_ = @" This SPOTSet or SPOTAny object is restricted to objects of type (%s); classes of type (%s) are not valid";
static NSString * iSPOTConstants_STR_NOT_EXIST_ = @"element [%s] does not exist";
static NSString * iSPOTConstants_STR_NOT_ONEOF_ = @"The value specified (%s) is not one of %s";
static NSString * iSPOTConstants_STR_NOT_SUPPORTED_ = @"The requested operation is not supported or %s objects";
static NSString * iSPOTConstants_STR_NO_CREATE_ = @"Failed to create object (%s)";
static NSString * iSPOTConstants_STR_NULL_ = @"The value of element [%s] is invalid.\nThe value is null for a required element";
static NSString * iSPOTConstants_STR_NULL_VALUE_ = @"The value of element [%s] is a null value";
static NSString * iSPOTConstants_STR_READ_ONLY_ = @"The element [%s] is READ-ONLY and cannot be modified";
static NSString * iSPOTConstants_STR_TOFEW_ELEMENTS_ = @"The element count for [%s] is (%s) and the minimum size of the SET is (%s)";
static NSString * iSPOTConstants_STR_TOMANY_ELEMENTS_ = @"The element count  for [%s] is (%s) and the maximum size of the SET is (%s)";
static NSString * iSPOTConstants_STR_MISSING_ATTRIBUTES_ = @"The element is missing the following required attributes { %s}";
static NSString * iSPOTConstants_classPrefix_ = @"SPOT";
static NSString * iSPOTConstants_SPOT_PACKAGE_NAME_ = @"com.appnativa.spot";
static NSString * iSPOTConstants_emptyString_ = @"";

+ (int)ILLEGAL_VALUE {
  return iSPOTConstants_ILLEGAL_VALUE;
}

+ (int)INVALID_ELEMENT {
  return iSPOTConstants_INVALID_ELEMENT;
}

+ (int)INVALID_VALUE {
  return iSPOTConstants_INVALID_VALUE;
}

+ (int)MISSING_ELEMENTS {
  return iSPOTConstants_MISSING_ELEMENTS;
}

+ (int)NOT_CLASS {
  return iSPOTConstants_NOT_CLASS;
}

+ (int)NOT_SUPPORTED {
  return iSPOTConstants_NOT_SUPPORTED;
}

+ (int)NO_CREATE {
  return iSPOTConstants_NO_CREATE;
}

+ (int)NULL_VALUE {
  return iSPOTConstants_NULL_VALUE;
}

+ (int)READ_ONLY {
  return iSPOTConstants_READ_ONLY;
}

+ (int)SPOT_TYPE_ANY {
  return iSPOTConstants_SPOT_TYPE_ANY;
}

+ (int)SPOT_TYPE_BOOLEAN {
  return iSPOTConstants_SPOT_TYPE_BOOLEAN;
}

+ (int)SPOT_TYPE_BYTESTRING {
  return iSPOTConstants_SPOT_TYPE_BYTESTRING;
}

+ (int)SPOT_TYPE_DATE {
  return iSPOTConstants_SPOT_TYPE_DATE;
}

+ (int)SPOT_TYPE_DATETIME {
  return iSPOTConstants_SPOT_TYPE_DATETIME;
}

+ (int)SPOT_TYPE_ENUMERATED {
  return iSPOTConstants_SPOT_TYPE_ENUMERATED;
}

+ (int)SPOT_TYPE_EXTENDS {
  return iSPOTConstants_SPOT_TYPE_EXTENDS;
}

+ (int)SPOT_TYPE_INTEGER {
  return iSPOTConstants_SPOT_TYPE_INTEGER;
}

+ (int)SPOT_TYPE_OCTETSTRING {
  return iSPOTConstants_SPOT_TYPE_OCTETSTRING;
}

+ (int)SPOT_TYPE_PRINTABLESTRING {
  return iSPOTConstants_SPOT_TYPE_PRINTABLESTRING;
}

+ (int)SPOT_TYPE_REAL {
  return iSPOTConstants_SPOT_TYPE_REAL;
}

+ (int)SPOT_TYPE_REFINE {
  return iSPOTConstants_SPOT_TYPE_REFINE;
}

+ (int)SPOT_TYPE_SEQUENCE {
  return iSPOTConstants_SPOT_TYPE_SEQUENCE;
}

+ (int)SPOT_TYPE_SET {
  return iSPOTConstants_SPOT_TYPE_SET;
}

+ (int)SPOT_TYPE_TIME {
  return iSPOTConstants_SPOT_TYPE_TIME;
}

+ (int)SPOT_TYPE_USERCLASS {
  return iSPOTConstants_SPOT_TYPE_USERCLASS;
}

+ (NSString *)STR_BAD_ELEMENT {
  return iSPOTConstants_STR_BAD_ELEMENT_;
}

+ (NSString *)STR_GREATER_THAN {
  return iSPOTConstants_STR_GREATER_THAN_;
}

+ (NSString *)STR_ILLEGAL_VALUE {
  return iSPOTConstants_STR_ILLEGAL_VALUE_;
}

+ (NSString *)STR_INVALID {
  return iSPOTConstants_STR_INVALID_;
}

+ (NSString *)STR_INVALID_ATTRIBUTE {
  return iSPOTConstants_STR_INVALID_ATTRIBUTE_;
}

+ (NSString *)STR_INVALID_ELEMENT {
  return iSPOTConstants_STR_INVALID_ELEMENT_;
}

+ (NSString *)STR_INVALID_RANGE {
  return iSPOTConstants_STR_INVALID_RANGE_;
}

+ (NSString *)STR_INVALID_VALUE {
  return iSPOTConstants_STR_INVALID_VALUE_;
}

+ (NSString *)STR_LESS_THAN {
  return iSPOTConstants_STR_LESS_THAN_;
}

+ (NSString *)STR_MISSING_ELEMENTS {
  return iSPOTConstants_STR_MISSING_ELEMENTS_;
}

+ (NSString *)STR_NOTONEOF {
  return iSPOTConstants_STR_NOTONEOF_;
}

+ (NSString *)STR_NOT_CLASS {
  return iSPOTConstants_STR_NOT_CLASS_;
}

+ (NSString *)STR_NOT_EXIST {
  return iSPOTConstants_STR_NOT_EXIST_;
}

+ (NSString *)STR_NOT_ONEOF {
  return iSPOTConstants_STR_NOT_ONEOF_;
}

+ (NSString *)STR_NOT_SUPPORTED {
  return iSPOTConstants_STR_NOT_SUPPORTED_;
}

+ (NSString *)STR_NO_CREATE {
  return iSPOTConstants_STR_NO_CREATE_;
}

+ (NSString *)STR_NULL {
  return iSPOTConstants_STR_NULL_;
}

+ (NSString *)STR_NULL_VALUE {
  return iSPOTConstants_STR_NULL_VALUE_;
}

+ (NSString *)STR_READ_ONLY {
  return iSPOTConstants_STR_READ_ONLY_;
}

+ (NSString *)STR_TOFEW_ELEMENTS {
  return iSPOTConstants_STR_TOFEW_ELEMENTS_;
}

+ (NSString *)STR_TOMANY_ELEMENTS {
  return iSPOTConstants_STR_TOMANY_ELEMENTS_;
}

+ (NSString *)STR_MISSING_ATTRIBUTES {
  return iSPOTConstants_STR_MISSING_ATTRIBUTES_;
}

+ (int)TOMANY_ELEMENTS {
  return iSPOTConstants_TOMANY_ELEMENTS;
}

+ (int)UNEXPECTED_EXCEPTION {
  return iSPOTConstants_UNEXPECTED_EXCEPTION;
}

+ (int)VALUE_MISSING_REQUIRED_ATTTRIBUTES {
  return iSPOTConstants_VALUE_MISSING_REQUIRED_ATTTRIBUTES;
}

+ (int)VALUE_INVALID_CHILD {
  return iSPOTConstants_VALUE_INVALID_CHILD;
}

+ (int)VALUE_NULL {
  return iSPOTConstants_VALUE_NULL;
}

+ (int)VALUE_NULL_AND_OPTIONAL {
  return iSPOTConstants_VALUE_NULL_AND_OPTIONAL;
}

+ (int)VALUE_NULL_WITH_DEFAULT {
  return iSPOTConstants_VALUE_NULL_WITH_DEFAULT;
}

+ (int)VALUE_OK {
  return iSPOTConstants_VALUE_OK;
}

+ (int)VALUE_TO_BIG {
  return iSPOTConstants_VALUE_TO_BIG;
}

+ (int)VALUE_TO_SMALL {
  return iSPOTConstants_VALUE_TO_SMALL;
}

+ (NSString *)classPrefix {
  return iSPOTConstants_classPrefix_;
}

+ (NSString *)SPOT_PACKAGE_NAME {
  return iSPOTConstants_SPOT_PACKAGE_NAME_;
}

+ (NSString *)emptyString {
  return iSPOTConstants_emptyString_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcFieldInfo fields[] = {
    { "ILLEGAL_VALUE_", NULL, 0x19, "I" },
    { "INVALID_ELEMENT_", NULL, 0x19, "I" },
    { "INVALID_VALUE_", NULL, 0x19, "I" },
    { "MISSING_ELEMENTS_", NULL, 0x19, "I" },
    { "NOT_CLASS_", NULL, 0x19, "I" },
    { "NOT_SUPPORTED_", NULL, 0x19, "I" },
    { "NO_CREATE_", NULL, 0x19, "I" },
    { "NULL_VALUE_", NULL, 0x19, "I" },
    { "READ_ONLY_", NULL, 0x19, "I" },
    { "SPOT_TYPE_ANY_", NULL, 0x19, "I" },
    { "SPOT_TYPE_BOOLEAN_", NULL, 0x19, "I" },
    { "SPOT_TYPE_BYTESTRING_", NULL, 0x19, "I" },
    { "SPOT_TYPE_DATE_", NULL, 0x19, "I" },
    { "SPOT_TYPE_DATETIME_", NULL, 0x19, "I" },
    { "SPOT_TYPE_ENUMERATED_", NULL, 0x19, "I" },
    { "SPOT_TYPE_EXTENDS_", NULL, 0x19, "I" },
    { "SPOT_TYPE_INTEGER_", NULL, 0x19, "I" },
    { "SPOT_TYPE_OCTETSTRING_", NULL, 0x19, "I" },
    { "SPOT_TYPE_PRINTABLESTRING_", NULL, 0x19, "I" },
    { "SPOT_TYPE_REAL_", NULL, 0x19, "I" },
    { "SPOT_TYPE_REFINE_", NULL, 0x19, "I" },
    { "SPOT_TYPE_SEQUENCE_", NULL, 0x19, "I" },
    { "SPOT_TYPE_SET_", NULL, 0x19, "I" },
    { "SPOT_TYPE_TIME_", NULL, 0x19, "I" },
    { "SPOT_TYPE_USERCLASS_", NULL, 0x19, "I" },
    { "STR_BAD_ELEMENT_", NULL, 0x19, "LNSString" },
    { "STR_GREATER_THAN_", NULL, 0x19, "LNSString" },
    { "STR_ILLEGAL_VALUE_", NULL, 0x19, "LNSString" },
    { "STR_INVALID_", NULL, 0x19, "LNSString" },
    { "STR_INVALID_ATTRIBUTE_", NULL, 0x19, "LNSString" },
    { "STR_INVALID_ELEMENT_", NULL, 0x19, "LNSString" },
    { "STR_INVALID_RANGE_", NULL, 0x19, "LNSString" },
    { "STR_INVALID_VALUE_", NULL, 0x19, "LNSString" },
    { "STR_LESS_THAN_", NULL, 0x19, "LNSString" },
    { "STR_MISSING_ELEMENTS_", NULL, 0x19, "LNSString" },
    { "STR_NOTONEOF_", NULL, 0x19, "LNSString" },
    { "STR_NOT_CLASS_", NULL, 0x19, "LNSString" },
    { "STR_NOT_EXIST_", NULL, 0x19, "LNSString" },
    { "STR_NOT_ONEOF_", NULL, 0x19, "LNSString" },
    { "STR_NOT_SUPPORTED_", NULL, 0x19, "LNSString" },
    { "STR_NO_CREATE_", NULL, 0x19, "LNSString" },
    { "STR_NULL_", NULL, 0x19, "LNSString" },
    { "STR_NULL_VALUE_", NULL, 0x19, "LNSString" },
    { "STR_READ_ONLY_", NULL, 0x19, "LNSString" },
    { "STR_TOFEW_ELEMENTS_", NULL, 0x19, "LNSString" },
    { "STR_TOMANY_ELEMENTS_", NULL, 0x19, "LNSString" },
    { "STR_MISSING_ATTRIBUTES_", NULL, 0x19, "LNSString" },
    { "TOMANY_ELEMENTS_", NULL, 0x19, "I" },
    { "UNEXPECTED_EXCEPTION_", NULL, 0x19, "I" },
    { "VALUE_MISSING_REQUIRED_ATTTRIBUTES_", NULL, 0x19, "I" },
    { "VALUE_INVALID_CHILD_", NULL, 0x19, "I" },
    { "VALUE_NULL_", NULL, 0x19, "I" },
    { "VALUE_NULL_AND_OPTIONAL_", NULL, 0x19, "I" },
    { "VALUE_NULL_WITH_DEFAULT_", NULL, 0x19, "I" },
    { "VALUE_OK_", NULL, 0x19, "I" },
    { "VALUE_TO_BIG_", NULL, 0x19, "I" },
    { "VALUE_TO_SMALL_", NULL, 0x19, "I" },
    { "classPrefix_", NULL, 0x19, "LNSString" },
    { "SPOT_PACKAGE_NAME_", NULL, 0x19, "LNSString" },
    { "emptyString_", NULL, 0x19, "LNSString" },
  };
  static J2ObjcClassInfo _iSPOTConstants = { "iSPOTConstants", "com.appnativa.spot", NULL, 0x201, 0, NULL, 60, fields, 0, NULL};
  return &_iSPOTConstants;
}

@end