//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: android/libcore/luni/src/main/java/java/lang/RealToString.java
//
//  Created by tball on 11/23/13.
//

#ifndef _JavaLangRealToString_H_
#define _JavaLangRealToString_H_

@class IOSIntArray;
@class JavaLangAbstractStringBuilder;

#import "JreEmulation.h"
#include "java/lang/ThreadLocal.h"

@interface JavaLangRealToString : NSObject {
 @public
  int firstK_;
  IOSIntArray *digits_;
  int digitCount_;
}

+ (JavaLangThreadLocal *)INSTANCE;
+ (double)invLogOfTenBaseTwo;
- (id)init;
+ (JavaLangRealToString *)getInstance;
+ (NSString *)resultOrSideEffectWithJavaLangAbstractStringBuilder:(JavaLangAbstractStringBuilder *)sb
                                                     withNSString:(NSString *)s;
- (NSString *)doubleToStringWithDouble:(double)d;
- (void)appendDoubleWithJavaLangAbstractStringBuilder:(JavaLangAbstractStringBuilder *)sb
                                           withDouble:(double)d;
- (NSString *)convertDoubleWithJavaLangAbstractStringBuilder:(JavaLangAbstractStringBuilder *)sb
                                                  withDouble:(double)inputNumber;
- (NSString *)floatToStringWithFloat:(float)f;
- (void)appendFloatWithJavaLangAbstractStringBuilder:(JavaLangAbstractStringBuilder *)sb
                                           withFloat:(float)f;
- (NSString *)convertFloatWithJavaLangAbstractStringBuilder:(JavaLangAbstractStringBuilder *)sb
                                                  withFloat:(float)inputNumber;
- (void)freeFormatExponentialWithJavaLangAbstractStringBuilder:(JavaLangAbstractStringBuilder *)sb
                                                   withBoolean:(BOOL)positive;
- (void)freeFormatWithJavaLangAbstractStringBuilder:(JavaLangAbstractStringBuilder *)sb
                                        withBoolean:(BOOL)positive;
- (void)bigIntDigitGeneratorWithLong:(long long int)f
                             withInt:(int)e
                         withBoolean:(BOOL)isDenormalized
                             withInt:(int)p;
- (void)longDigitGeneratorWithLong:(long long int)f
                           withInt:(int)e
                       withBoolean:(BOOL)isDenormalized
                       withBoolean:(BOOL)mantissaIsZero
                           withInt:(int)p;
- (void)copyAllFieldsTo:(JavaLangRealToString *)other;
@end

J2OBJC_FIELD_SETTER(JavaLangRealToString, digits_, IOSIntArray *)

@interface JavaLangRealToString_$1 : JavaLangThreadLocal {
}

- (JavaLangRealToString *)initialValue OBJC_METHOD_FAMILY_NONE;
- (id)init;
@end

#endif // _JavaLangRealToString_H_