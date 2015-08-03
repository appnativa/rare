//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: android/libcore/luni/src/main/java/java/text/ChoiceFormat.java
//
//  Created by tball on 11/23/13.
//

#ifndef _JavaTextChoiceFormat_H_
#define _JavaTextChoiceFormat_H_

@class IOSDoubleArray;
@class IOSObjectArray;
@class JavaLangStringBuffer;
@class JavaTextFieldPosition;
@class JavaTextParsePosition;

#import "JreEmulation.h"
#include "java/text/NumberFormat.h"

#define JavaTextChoiceFormat_serialVersionUID 1795184449645032964

@interface JavaTextChoiceFormat : JavaTextNumberFormat {
 @public
  IOSDoubleArray *choiceLimits_;
  IOSObjectArray *choiceFormats_;
}

- (id)initWithDoubleArray:(IOSDoubleArray *)limits
        withNSStringArray:(IOSObjectArray *)formats;
- (id)initWithNSString:(NSString *)template_;
- (void)applyPatternWithNSString:(NSString *)template_;
- (id)clone;
- (BOOL)isEqual:(id)object;
- (JavaLangStringBuffer *)formatWithDouble:(double)value
                  withJavaLangStringBuffer:(JavaLangStringBuffer *)buffer
                 withJavaTextFieldPosition:(JavaTextFieldPosition *)field;
- (JavaLangStringBuffer *)formatWithLong:(long long int)value
                withJavaLangStringBuffer:(JavaLangStringBuffer *)buffer
               withJavaTextFieldPosition:(JavaTextFieldPosition *)field;
- (IOSObjectArray *)getFormats;
- (IOSDoubleArray *)getLimits;
- (NSUInteger)hash;
+ (double)nextDoubleWithDouble:(double)value;
+ (double)nextDoubleWithDouble:(double)value
                   withBoolean:(BOOL)increment;
- (NSNumber *)parseWithNSString:(NSString *)string
      withJavaTextParsePosition:(JavaTextParsePosition *)position;
+ (double)previousDoubleWithDouble:(double)value;
- (void)setChoicesWithDoubleArray:(IOSDoubleArray *)limits
                withNSStringArray:(IOSObjectArray *)formats;
- (int)skipWhitespaceWithNSString:(NSString *)string
                          withInt:(int)index;
- (NSString *)toPattern;
- (void)copyAllFieldsTo:(JavaTextChoiceFormat *)other;
@end

J2OBJC_FIELD_SETTER(JavaTextChoiceFormat, choiceLimits_, IOSDoubleArray *)
J2OBJC_FIELD_SETTER(JavaTextChoiceFormat, choiceFormats_, IOSObjectArray *)

#endif // _JavaTextChoiceFormat_H_