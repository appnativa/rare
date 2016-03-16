//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/jgoodies/forms/layout/FormSpecParser.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREFormSpecParser_H_
#define _RAREFormSpecParser_H_

@class IOSObjectArray;
@class JavaLangNumberFormatException;
@class JavaLangThrowable;
@class JavaUtilRegexPattern;
@class RAREFormSpecParser_Multiplier;
@class RARELayoutMap;
@protocol JavaUtilList;

#import "JreEmulation.h"
#include "java/lang/RuntimeException.h"

@interface RAREFormSpecParser : NSObject {
 @public
  NSString *source_;
  RARELayoutMap *layoutMap_;
}

+ (JavaUtilRegexPattern *)MULTIPLIER_PREFIX_PATTERN;
+ (JavaUtilRegexPattern *)DIGIT_PATTERN;
- (id)initWithNSString:(NSString *)source
          withNSString:(NSString *)description_
     withRARELayoutMap:(RARELayoutMap *)layoutMap
           withBoolean:(BOOL)horizontal;
+ (IOSObjectArray *)parseColumnSpecsWithNSString:(NSString *)encodedColumnSpecs
                               withRARELayoutMap:(RARELayoutMap *)layoutMap;
+ (IOSObjectArray *)parseRowSpecsWithNSString:(NSString *)encodedRowSpecs
                            withRARELayoutMap:(RARELayoutMap *)layoutMap;
- (IOSObjectArray *)parseColumnSpecs;
- (IOSObjectArray *)parseRowSpecs;
- (id<JavaUtilList>)splitWithNSString:(NSString *)expression
                              withInt:(int)offset;
- (void)addSpecWithJavaUtilList:(id<JavaUtilList>)encodedSpecs
                   withNSString:(NSString *)expression
                        withInt:(int)offset;
- (RAREFormSpecParser_Multiplier *)multiplierWithNSString:(NSString *)expression
                                                  withInt:(int)offset;
+ (void)failWithNSString:(NSString *)source
                 withInt:(int)index
            withNSString:(NSString *)description_;
- (void)failWithInt:(int)index
       withNSString:(NSString *)description_;
- (void)failWithInt:(int)index
withJavaLangNumberFormatException:(JavaLangNumberFormatException *)cause;
+ (NSString *)messageWithNSString:(NSString *)source
                          withInt:(int)index
                     withNSString:(NSString *)description_;
- (void)copyAllFieldsTo:(RAREFormSpecParser *)other;
@end

J2OBJC_FIELD_SETTER(RAREFormSpecParser, source_, NSString *)
J2OBJC_FIELD_SETTER(RAREFormSpecParser, layoutMap_, RARELayoutMap *)

typedef RAREFormSpecParser ComAppnativaJgoodiesFormsLayoutFormSpecParser;

@interface RAREFormSpecParser_FormLayoutParseException : JavaLangRuntimeException {
}

- (id)initWithNSString:(NSString *)message;
- (id)initWithNSString:(NSString *)message
 withJavaLangThrowable:(JavaLangThrowable *)cause;
@end

@interface RAREFormSpecParser_Multiplier : NSObject {
 @public
  int multiplier_;
  NSString *expression_;
  int offset_;
}

- (id)initWithInt:(int)multiplier
     withNSString:(NSString *)expression
          withInt:(int)offset;
- (void)copyAllFieldsTo:(RAREFormSpecParser_Multiplier *)other;
@end

J2OBJC_FIELD_SETTER(RAREFormSpecParser_Multiplier, expression_, NSString *)

#endif // _RAREFormSpecParser_H_