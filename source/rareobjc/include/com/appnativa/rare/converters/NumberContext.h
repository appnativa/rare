//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/converters/NumberContext.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RARENumberContext_H_
#define _RARENumberContext_H_

@class IOSObjectArray;
@class JavaTextNumberFormat;

#import "JreEmulation.h"
#include "com/appnativa/rare/converters/ConverterContext.h"

@interface RARENumberContext : RAREConverterContext {
 @public
  JavaTextNumberFormat *displayFormat_;
  IOSObjectArray *itemFormats_;
  BOOL range_;
}

+ (RARENumberContext *)RANGE_CONTEXT;
- (id)init;
- (id)initWithJavaTextNumberFormat:(JavaTextNumberFormat *)iformat
          withJavaTextNumberFormat:(JavaTextNumberFormat *)dformat;
- (id)initWithJavaTextNumberFormatArray:(IOSObjectArray *)iformats
               withJavaTextNumberFormat:(JavaTextNumberFormat *)dformat;
- (void)setRangeWithBoolean:(BOOL)range;
- (JavaTextNumberFormat *)getDisplayFormat;
- (JavaTextNumberFormat *)getItemFormat;
- (IOSObjectArray *)getItemFormats;
- (BOOL)hasMultiplePattens;
- (BOOL)isRange;
- (void)copyAllFieldsTo:(RARENumberContext *)other;
@end

J2OBJC_FIELD_SETTER(RARENumberContext, displayFormat_, JavaTextNumberFormat *)
J2OBJC_FIELD_SETTER(RARENumberContext, itemFormats_, IOSObjectArray *)

typedef RARENumberContext ComAppnativaRareConvertersNumberContext;

@interface RARENumberContext_$1 : RARENumberContext {
}

- (BOOL)isRange;
- (JavaTextNumberFormat *)getDisplayFormat;
- (JavaTextNumberFormat *)getItemFormat;
- (id)init;
@end

#endif // _RARENumberContext_H_
