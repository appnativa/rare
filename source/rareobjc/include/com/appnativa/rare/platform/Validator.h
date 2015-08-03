//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/platform/Validator.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RAREValidator_H_
#define _RAREValidator_H_

@class RAREUTCharArray;
@class RAREUTCharScanner;
@class RAREaRare;
@protocol JavaUtilMap;

#import "JreEmulation.h"
#include "com/appnativa/util/OrderedProperties.h"

@interface RAREValidator : NSObject < RAREUTOrderedProperties_iValidator > {
 @public
  RAREUTCharScanner *scanner_;
  id<JavaUtilMap> options_;
  RAREaRare *rare_;
}

- (id)initWithRAREaRare:(RAREaRare *)rare;
- (void)clear;
- (BOOL)isValidWithRAREUTCharArray:(RAREUTCharArray *)ca
                           withInt:(int)start;
- (void)copyAllFieldsTo:(RAREValidator *)other;
@end

J2OBJC_FIELD_SETTER(RAREValidator, scanner_, RAREUTCharScanner *)
J2OBJC_FIELD_SETTER(RAREValidator, options_, id<JavaUtilMap>)
J2OBJC_FIELD_SETTER(RAREValidator, rare_, RAREaRare *)

typedef RAREValidator ComAppnativaRarePlatformValidator;

#endif // _RAREValidator_H_