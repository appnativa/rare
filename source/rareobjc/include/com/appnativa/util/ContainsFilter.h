//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../util/src/com/appnativa/util/ContainsFilter.java
//
//  Created by decoteaud on 9/15/15.
//

#ifndef _RAREUTContainsFilter_H_
#define _RAREUTContainsFilter_H_

@protocol RAREUTiStringConverter;

#import "JreEmulation.h"
#include "com/appnativa/util/iFilter.h"

@interface RAREUTContainsFilter : NSObject < RAREUTiFilter > {
 @public
  BOOL ignoreCase_;
  BOOL startsWith_;
  BOOL nullPasses_;
  BOOL emptyPasses_;
  NSString *originalValue_;
  NSString *theValue_;
}

- (id)init;
- (id)initWithNSString:(NSString *)value;
- (id)initWithNSString:(NSString *)value
           withBoolean:(BOOL)ignoreCase;
- (id)initWithNSString:(NSString *)value
           withBoolean:(BOOL)ignoreCase
           withBoolean:(BOOL)startsWith;
- (id)initWithNSString:(NSString *)value
           withBoolean:(BOOL)ignoreCase
           withBoolean:(BOOL)startsWith
           withBoolean:(BOOL)nullPasses
           withBoolean:(BOOL)emptyPasses;
- (BOOL)passesWithId:(id)value
withRAREUTiStringConverter:(id<RAREUTiStringConverter>)converter;
- (void)setEmptyStringPassesWithBoolean:(BOOL)passes;
- (void)setIgnoreCaseWithBoolean:(BOOL)ignoreCase;
- (void)setNullPassesWithBoolean:(BOOL)passes;
- (void)setStartsWithWithBoolean:(BOOL)startsWith;
- (void)setValueWithNSString:(NSString *)theValue;
- (NSString *)getValue;
- (BOOL)isEmptyStringPasses;
- (BOOL)isIgnoreCase;
- (BOOL)isNullPasses;
- (BOOL)isStartsWith;
- (void)copyAllFieldsTo:(RAREUTContainsFilter *)other;
@end

J2OBJC_FIELD_SETTER(RAREUTContainsFilter, originalValue_, NSString *)
J2OBJC_FIELD_SETTER(RAREUTContainsFilter, theValue_, NSString *)

typedef RAREUTContainsFilter ComAppnativaUtilContainsFilter;

#endif // _RAREUTContainsFilter_H_
