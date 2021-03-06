//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../util/src/com/appnativa/util/SNumber.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREUTSNumber_H_
#define _RAREUTSNumber_H_

@class IOSByteArray;
@class IOSCharArray;
@class IOSDoubleArray;
@class IOSFloatArray;
@class IOSIntArray;
@class IOSLongArray;
@class IOSObjectArray;
@class JavaIoInputStream;
@class JavaIoOutputStream;
@class JavaLangDouble;
@class JavaLangFloat;
@class JavaLangInteger;
@class JavaLangLong;
@class JavaMathBigDecimal;
@class JavaMathBigInteger;
@class RAREUTByteArray;
@class RAREUTCharArray;
@class RAREUTSDecimal;

#import "JreEmulation.h"
#include "java/lang/Comparable.h"
#include "java/lang/ThreadLocal.h"

#define RAREUTSNumber_ALLOW_EXPONENT 1
#define RAREUTSNumber_ALLOW_OPS 8
#define RAREUTSNumber_ALLOW_TRAILING_E 2
#define RAREUTSNumber_ALLOW_ZEROS 4
#define RAREUTSNumber_dot 1
#define RAREUTSNumber_exponent 2
#define RAREUTSNumber_tenlen 11

@interface RAREUTSNumber : NSNumber < JavaLangComparable, NSCopying > {
 @public
  RAREUTSDecimal *bigNumber_;
  int decplaces_;
  long long int fraction_;
  long long int mantissa_;
  RAREUTCharArray *strBuffer_;
  BOOL immutable_;
  RAREUTSDecimal *sdecimal_;
}

+ (int)ALLOW_EXPONENT;
+ (int)ALLOW_OPS;
+ (int)ALLOW_TRAILING_E;
+ (int)ALLOW_ZEROS;
+ (RAREUTSNumber *)ZERO;
+ (RAREUTSNumber *)ONE;
+ (unichar)ZERO_DIGIT;
+ (unichar)MINUS_SIGN;
+ (unichar)PLUS_SIGN;
+ (unichar)DECIMAL_POINT;
+ (unichar)EXPONENT_LOWERCASE;
+ (unichar)EXPONENT_UPPERCASE;
+ (IOSCharArray *)EXPONENT_CHARS_LOWERCASE;
+ (IOSCharArray *)EXPONENT_CHARS_UPPERCASE;
+ (int)maxDigits;
+ (int *)maxDigitsRef;
+ (IOSDoubleArray *)fractens;
+ (IOSLongArray *)tens;
+ (NSString *)NO_POWER_FRACTION_MSG;
+ (void)setNO_POWER_FRACTION_MSG:(NSString *)NO_POWER_FRACTION_MSG;
+ (NSString *)POINT_ONE;
+ (NSString *)POINT_ZERO_ONE;
+ (NSString *)POINT_ZERO_ZERO_ONE;
+ (NSString *)POINT_ZERO_ZERO_ZERO_ONE;
+ (IOSCharArray *)DigitOnes;
+ (IOSCharArray *)DigitTens;
+ (IOSCharArray *)digits;
+ (NSString *)divideByZero;
+ (IOSCharArray *)hexDigits;
+ (JavaLangThreadLocal *)perThreadNumber;
+ (void)setPerThreadNumber:(JavaLangThreadLocal *)perThreadNumber;
+ (JavaLangThreadLocal *)perThreadBuffer;
+ (void)setPerThreadBuffer:(JavaLangThreadLocal *)perThreadBuffer;
- (id)init;
- (id)initWithJavaMathBigDecimal:(JavaMathBigDecimal *)bd;
- (id)initWithJavaMathBigInteger:(JavaMathBigInteger *)bd;
- (id)initWithDouble:(double)num;
- (id)initWithInt:(int)num;
- (id)initWithLong:(long long int)num;
- (id)initWithRAREUTSNumber:(RAREUTSNumber *)snum;
- (id)initWithNSString:(NSString *)str;
- (id)initWithNSString:(NSString *)str
           withBoolean:(BOOL)javaparsecompat;
- (id)initWithCharArray:(IOSCharArray *)chars
                withInt:(int)pos
                withInt:(int)len;
- (id)initWithLong:(long long int)mantissa
          withLong:(long long int)fraction
           withInt:(int)decplaces;
- (id)initWithCharArray:(IOSCharArray *)chars
                withInt:(int)pos
                withInt:(int)len
            withBoolean:(BOOL)javaparsecompat;
+ (void)setMaxDigitsWithInt:(int)max;
+ (int)getMaxDigits;
- (RAREUTSNumber *)addWithDouble:(double)num;
- (RAREUTSNumber *)addWithInt:(int)num;
- (RAREUTSNumber *)addWithLong:(long long int)num;
- (RAREUTSNumber *)addWithRAREUTSNumber:(RAREUTSNumber *)snum;
- (RAREUTSNumber *)addWithNSString:(NSString *)str;
- (RAREUTSNumber *)and__WithRAREUTSNumber:(RAREUTSNumber *)snum;
- (RAREUTSNumber *)and__WithNSString:(NSString *)str;
+ (BOOL)booleanValueWithId:(id)bol;
+ (BOOL)booleanValueWithNSString:(NSString *)str;
+ (NSString *)bytesToHexStringWithByteArray:(IOSByteArray *)b;
+ (NSString *)bytesToHexStringWithByteArray:(IOSByteArray *)b
                                    withInt:(int)pos
                                    withInt:(int)len;
+ (NSString *)charsToHexStringWithCharArray:(IOSCharArray *)b;
+ (NSString *)charsToHexStringWithCharArray:(IOSCharArray *)b
                                    withInt:(int)pos
                                    withInt:(int)len;
- (id)clone;
- (int)compareToWithId:(id)obj;
- (int)compareToWithRAREUTSNumber:(RAREUTSNumber *)num;
- (RAREUTSNumber *)createReusableInternalBuffers;
- (int)decimalPlaces;
- (RAREUTSNumber *)divideWithDouble:(double)num;
- (RAREUTSNumber *)divideWithInt:(int)num;
- (RAREUTSNumber *)divideWithLong:(long long int)num;
- (RAREUTSNumber *)divideWithRAREUTSDecimal:(RAREUTSDecimal *)snum;
- (RAREUTSNumber *)divideWithRAREUTSNumber:(RAREUTSNumber *)snum;
- (RAREUTSNumber *)divideWithNSString:(NSString *)str;
- (RAREUTSNumber *)divideIntegerWithRAREUTSNumber:(RAREUTSNumber *)snum;
- (double)doubleValue;
+ (double)doubleValueWithNSString:(NSString *)str;
+ (double)doubleValueWithNSString:(NSString *)str
                      withBoolean:(BOOL)javaparsecompat;
+ (double)doubleValueWithLong:(long long int)mantissa
                     withLong:(long long int)fraction
                      withInt:(int)decplaces;
- (BOOL)equalsWithDouble:(double)num;
- (BOOL)equalsWithInt:(int)num;
- (BOOL)equalsWithLong:(long long int)num;
- (BOOL)isEqual:(id)obj;
- (BOOL)equalsWithRAREUTSNumber:(RAREUTSNumber *)snum;
- (BOOL)equalsWithLong:(long long int)mantissa
              withLong:(long long int)fraction
               withInt:(int)decplaces;
- (float)floatValue;
+ (float)floatValueWithNSString:(NSString *)str;
+ (float)floatValueWithNSString:(NSString *)str
                    withBoolean:(BOOL)javaparsecompat;
+ (float)floatValueWithCharArray:(IOSCharArray *)chars
                         withInt:(int)pos
                         withInt:(int)len
                     withBoolean:(BOOL)javaparsecompat;
+ (int)fractionToStringWithLong:(long long int)fraction
                        withInt:(int)decplaces
                  withCharArray:(IOSCharArray *)chars
                        withInt:(int)pos;
- (long long int)fractionValue;
- (NSString *)fractionValueString;
- (long long int)fractionalPart;
+ (RAREUTSNumber *)fromStreamWithRAREUTSNumber:(RAREUTSNumber *)val
                         withJavaIoInputStream:(JavaIoInputStream *)inArg;
- (NSNumber *)getNumber;
- (BOOL)gtWithDouble:(double)num;
- (BOOL)gtWithInt:(int)num;
- (BOOL)gtWithLong:(long long int)num;
- (BOOL)gtWithRAREUTSNumber:(RAREUTSNumber *)snum;
- (NSUInteger)hash;
+ (IOSByteArray *)hexStringToBytesWithNSString:(NSString *)str;
+ (IOSByteArray *)hexStringToBytesWithCharArray:(IOSCharArray *)chars
                                        withInt:(int)pos
                                        withInt:(int)len;
+ (IOSCharArray *)hexStringToCharsWithNSString:(NSString *)str;
+ (IOSCharArray *)hexStringToCharsWithCharArray:(IOSCharArray *)chars
                                        withInt:(int)pos
                                        withInt:(int)len;
+ (long long int)hexStringToLongWithCharArray:(IOSCharArray *)chars
                                      withInt:(int)pos
                                      withInt:(int)len;
- (int)intValue;
+ (int)intValueWithNSString:(NSString *)str;
+ (int)intValueWithNSString:(NSString *)str
                withBoolean:(BOOL)javaparsecompat;
+ (int)intValueWithCharArray:(IOSCharArray *)chars
                     withInt:(int)pos
                     withInt:(int)len
                 withBoolean:(BOOL)javaparsecompat;
+ (int)intValueExWithRAREUTSNumber:(RAREUTSNumber *)snum
                     withCharArray:(IOSCharArray *)chars
                           withInt:(int)len
                     withCharArray:(IOSCharArray *)ops
                           withInt:(int)oppos
                           withInt:(int)oplen;
- (int)intValueMax;
+ (int)intValueMaxWithNSString:(NSString *)str;
- (BOOL)isBigNumber;
- (BOOL)isInteger;
- (BOOL)isNegative;
+ (BOOL)isNumericWithNSString:(NSString *)str;
+ (BOOL)isNumericWithNSString:(NSString *)str
                      withInt:(int)flags;
+ (BOOL)isNumericWithCharArray:(IOSCharArray *)chars
                       withInt:(int)pos
                       withInt:(int)len
                       withInt:(int)flags;
- (BOOL)isRational;
- (BOOL)isRationalOrBigNumber;
- (BOOL)isZero;
- (long long int)longLongValue;
+ (long long int)longValueWithNSString:(NSString *)str;
+ (long long int)longValueWithNSString:(NSString *)str
                           withBoolean:(BOOL)javaparsecompat;
+ (long long int)longValueWithCharArray:(IOSCharArray *)chars
                                withInt:(int)pos
                                withInt:(int)len
                            withBoolean:(BOOL)javaparsecompat;
- (BOOL)ltWithDouble:(double)num;
- (BOOL)ltWithInt:(int)num;
- (BOOL)ltWithLong:(long long int)num;
- (BOOL)ltWithRAREUTSNumber:(RAREUTSNumber *)snum;
- (RAREUTSNumber *)makeImmutable;
- (RAREUTSNumber *)modWithRAREUTSNumber:(RAREUTSNumber *)snum;
- (RAREUTSNumber *)modWithRAREUTSNumber:(RAREUTSNumber *)snum
                            withBoolean:(BOOL)special;
- (RAREUTSNumber *)multiOperationWithCharArray:(IOSCharArray *)ops
                                       withInt:(int)pos
                                       withInt:(int)len;
- (RAREUTSNumber *)multiplyWithDouble:(double)num;
- (RAREUTSNumber *)multiplyWithInt:(int)num;
- (RAREUTSNumber *)multiplyWithLong:(long long int)num;
- (RAREUTSNumber *)multiplyWithRAREUTSNumber:(RAREUTSNumber *)snum;
- (RAREUTSNumber *)multiplyWithNSString:(NSString *)str;
- (RAREUTSNumber *)negate;
+ (JavaMathBigDecimal *)newBigDecimalWithNSString:(NSString *)s OBJC_METHOD_FAMILY_NONE;
+ (JavaMathBigInteger *)newBigIntegerWithNSString:(NSString *)s
                                          withInt:(int)radix OBJC_METHOD_FAMILY_NONE;
+ (RAREUTSDecimal *)newSDecimalWithNSString:(NSString *)s OBJC_METHOD_FAMILY_NONE;
- (RAREUTSNumber *)not__;
- (RAREUTSNumber *)oneOver;
- (RAREUTSNumber *)or__WithDouble:(double)num;
- (RAREUTSNumber *)or__WithInt:(int)num;
- (RAREUTSNumber *)or__WithLong:(long long int)num;
- (RAREUTSNumber *)or__WithRAREUTSNumber:(RAREUTSNumber *)snum;
- (RAREUTSNumber *)or__WithNSString:(NSString *)str;
+ (double)parseDoubleWithNSString:(NSString *)str;
+ (IOSDoubleArray *)parseDoublesWithNSString:(NSString *)s;
+ (IOSFloatArray *)parseFloatsWithNSString:(NSString *)s;
+ (int)parseIntWithNSString:(NSString *)str;
+ (IOSIntArray *)parseIntegersWithNSString:(NSString *)s;
- (RAREUTSNumber *)powWithRAREUTSDecimal:(RAREUTSDecimal *)snum;
- (RAREUTSNumber *)powWithRAREUTSNumber:(RAREUTSNumber *)snum;
+ (RAREUTSNumber *)readSNumberWithJavaIoInputStream:(JavaIoInputStream *)inArg;
+ (void)readSNumberWithRAREUTSNumber:(RAREUTSNumber *)use
               withJavaIoInputStream:(JavaIoInputStream *)inArg;
+ (IOSObjectArray *)readSNumberArrayWithJavaIoInputStream:(JavaIoInputStream *)inArg;
- (RAREUTSNumber *)roundWithInt:(int)places
                    withBoolean:(BOOL)up;
- (int)scale__;
- (RAREUTSNumber *)setScaleWithInt:(int)places;
- (RAREUTSNumber *)setValueWithJavaMathBigDecimal:(JavaMathBigDecimal *)num;
- (RAREUTSNumber *)setValueWithJavaMathBigInteger:(JavaMathBigInteger *)num;
- (RAREUTSNumber *)setValueWithDouble:(double)num;
- (RAREUTSNumber *)setValueWithDouble:(double)num
                              withInt:(int)maxDecPlaces;
- (RAREUTSNumber *)setValueWithInt:(int)num;
- (RAREUTSNumber *)setValueWithLong:(long long int)num;
- (RAREUTSNumber *)setValueWithRAREUTSNumber:(RAREUTSNumber *)num;
- (RAREUTSNumber *)setValueWithNSString:(NSString *)str;
- (RAREUTSNumber *)setValueWithNSString:(NSString *)str
                            withBoolean:(BOOL)javaparsecompat;
- (RAREUTSNumber *)setValueWithCharArray:(IOSCharArray *)chars
                                 withInt:(int)pos
                                 withInt:(int)len;
- (RAREUTSNumber *)setValueWithLong:(long long int)mantissa
                           withLong:(long long int)fraction
                            withInt:(int)decplaces;
- (RAREUTSNumber *)setValueWithCharArray:(IOSCharArray *)chars
                                 withInt:(int)pos
                                 withInt:(int)len
                             withBoolean:(BOOL)javaparsecompat;
- (BOOL)setValueExWithNSString:(NSString *)str
                   withBoolean:(BOOL)javaparsecompat
                   withBoolean:(BOOL)trailinge;
- (BOOL)setValueExWithCharArray:(IOSCharArray *)chars
                        withInt:(int)pos
                        withInt:(int)len
                    withBoolean:(BOOL)javaparsecompat
                    withBoolean:(BOOL)trailinge;
- (RAREUTSNumber *)shiftDecimalWithInt:(int)places;
- (RAREUTSNumber *)subtractWithDouble:(double)num;
- (RAREUTSNumber *)subtractWithInt:(int)num;
- (RAREUTSNumber *)subtractWithLong:(long long int)num;
- (RAREUTSNumber *)subtractWithRAREUTSNumber:(RAREUTSNumber *)snum;
- (RAREUTSNumber *)subtractWithNSString:(NSString *)str;
+ (long long int)tenpowWithLong:(long long int)p;
- (NSString *)toFormattedStringWithNSString:(NSString *)format
                                    withInt:(int)places;
- (void)toStreamWithJavaIoOutputStream:(JavaIoOutputStream *)outArg;
- (NSString *)description;
+ (NSString *)toStringWithBoolean:(BOOL)val;
- (RAREUTCharArray *)toStringWithRAREUTCharArray:(RAREUTCharArray *)outArg;
+ (NSString *)toStringWithDouble:(double)num;
+ (NSString *)toStringWithInt:(int)num;
+ (NSString *)toStringWithLong:(long long int)num;
- (RAREUTCharArray *)toStringWithRAREUTCharArray:(RAREUTCharArray *)outArg
                                     withBoolean:(BOOL)append;
- (NSString *)toStringExWithRAREUTCharArray:(RAREUTCharArray *)tmp;
+ (int)toUnsignedStringWithLong:(long long int)i
                  withCharArray:(IOSCharArray *)buf
                        withInt:(int)pos;
+ (int)toUnsignedStringWithLong:(long long int)i
                        withInt:(int)radix
            withRAREUTCharArray:(RAREUTCharArray *)ca
                        withInt:(int)pos;
+ (int)toUnsignedStringExWithLong:(long long int)i
                          withInt:(int)radix
                    withCharArray:(IOSCharArray *)buf;
- (RAREUTSNumber *)xor__WithInt:(int)num;
- (RAREUTSNumber *)xor__WithLong:(long long int)num;
- (RAREUTSNumber *)xor__WithRAREUTSNumber:(RAREUTSNumber *)snum;
- (RAREUTSNumber *)xor__WithNSString:(NSString *)str;
- (RAREUTSNumber *)zero;
+ (void)getCharsWithLong:(long long int)i
                 withInt:(int)index
           withCharArray:(IOSCharArray *)buf;
+ (long long int)multiplyWithLong:(long long int)mantissa
                         withLong:(long long int)fraction
                          withInt:(int)decplaces
                         withLong:(long long int)num;
+ (double)negtenpowWithLong:(long long int)p;
+ (int)stringSizeWithLong:(long long int)x;
- (RAREUTSNumber *)addWithRAREUTSDecimal:(RAREUTSDecimal *)num;
- (RAREUTSNumber *)divideIntegerWithRAREUTSDecimal:(RAREUTSDecimal *)snum;
- (RAREUTSNumber *)modWithRAREUTSDecimal:(RAREUTSDecimal *)snum
                             withBoolean:(BOOL)special;
- (RAREUTSNumber *)multiplyWithRAREUTSDecimal:(RAREUTSDecimal *)num;
- (RAREUTSNumber *)setValueWithRAREUTSDecimal:(RAREUTSDecimal *)num;
- (RAREUTSNumber *)subtractWithRAREUTSDecimal:(RAREUTSDecimal *)num;
- (void)fromSDecimalWithRAREUTSDecimal:(RAREUTSDecimal *)num;
+ (int)multiOperationWithInt:(int)sval
               withCharArray:(IOSCharArray *)ops
                     withInt:(int)pos
                     withInt:(int)len;
- (BOOL)setBNValueExWithCharArray:(IOSCharArray *)chars
                          withInt:(int)pos
                          withInt:(int)len
                      withBoolean:(BOOL)javaparsecompat;
- (RAREUTSDecimal *)toSDecimal;
+ (RAREUTSNumber *)valueOfWithJavaLangInteger:(JavaLangInteger *)number;
+ (RAREUTSNumber *)valueOfWithJavaLangLong:(JavaLangLong *)number;
+ (RAREUTSNumber *)valueOfWithJavaLangDouble:(JavaLangDouble *)number;
+ (RAREUTSNumber *)valueOfWithJavaLangFloat:(JavaLangFloat *)number;
+ (RAREUTSNumber *)valueOfWithNSNumber:(NSNumber *)number;
- (RAREUTSDecimal *)valueOfWithDouble:(double)val;
+ (BOOL)isNumericWithChar:(unichar)c;
+ (BOOL)isEqualWithFloat:(float)a
               withFloat:(float)b;
+ (BOOL)isEqualWithDouble:(double)a
               withDouble:(double)b;
+ (BOOL)isExponentSymbolWithChar:(unichar)c
                   withCharArray:(IOSCharArray *)chars
                         withInt:(int)pos
                         withInt:(int)len;
+ (BOOL)isTrailingExponentWithChar:(unichar)c;
+ (JavaMathBigDecimal *)toBigDecimalWithByteArray:(IOSByteArray *)bytes
                                          withInt:(int)pos
                                          withInt:(int)len;
+ (int)toEncodedBytesWithJavaMathBigDecimal:(JavaMathBigDecimal *)value
                              withByteArray:(IOSByteArray *)outArg
                                    withInt:(int)offset
                                    withInt:(int)outDecimalPlaces
                        withRAREUTByteArray:(RAREUTByteArray *)outa;
+ (int)toEncodedBytesWithDouble:(double)value
                  withByteArray:(IOSByteArray *)outArg
                        withInt:(int)offset
                        withInt:(int)outDecimalPlaces
            withRAREUTByteArray:(RAREUTByteArray *)outa;
+ (int)toEncodedBytesWithNSString:(NSString *)value
                    withByteArray:(IOSByteArray *)outArg
                          withInt:(int)offset
                          withInt:(int)outDecimalPlaces
              withRAREUTByteArray:(RAREUTByteArray *)outa;
- (int)toEncodedBytesWithByteArray:(IOSByteArray *)outArg
                           withInt:(int)offset
               withRAREUTByteArray:(RAREUTByteArray *)outa;
+ (int)toEncodedBytesWithLong:(long long int)mantissa
                     withLong:(long long int)fraction
                      withInt:(int)decplaces
                withByteArray:(IOSByteArray *)outArg
                      withInt:(int)offset
          withRAREUTByteArray:(RAREUTByteArray *)outa;
+ (int)toEncodedBytesWithCharArray:(IOSCharArray *)chars
                           withInt:(int)pos
                           withInt:(int)len
                     withByteArray:(IOSByteArray *)outArg
                           withInt:(int)offset
                           withInt:(int)outDecimalPlaces
               withRAREUTByteArray:(RAREUTByteArray *)outa;
- (int)toEncodedCharsWithCharArray:(IOSCharArray *)outArg
                           withInt:(int)offset
               withRAREUTCharArray:(RAREUTCharArray *)outa;
- (int)toEncodedCharsWithRAREUTCharArray:(RAREUTCharArray *)outArg;
+ (int)toEncodedCharsWithLong:(long long int)mantissa
                     withLong:(long long int)fraction
                      withInt:(int)decplaces
                withCharArray:(IOSCharArray *)outArg
                      withInt:(int)offset
          withRAREUTCharArray:(RAREUTCharArray *)outa;
+ (int)toEncodedCharsWithCharArray:(IOSCharArray *)chars
                           withInt:(int)pos
                           withInt:(int)len
                     withCharArray:(IOSCharArray *)outArg
                           withInt:(int)offset
                           withInt:(int)outDecimalPlaces
               withRAREUTCharArray:(RAREUTCharArray *)outa;
+ (RAREUTSNumber *)fromEncodedBytesWithByteArray:(IOSByteArray *)bytes
                                         withInt:(int)pos
                                         withInt:(int)len;
+ (RAREUTSNumber *)fromEncodedBytesWithByteArray:(IOSByteArray *)bytes
                                         withInt:(int)pos
                                         withInt:(int)len
                               withRAREUTSNumber:(RAREUTSNumber *)outArg;
+ (RAREUTSNumber *)fromEncodedCharsWithCharArray:(IOSCharArray *)chars
                                         withInt:(int)pos
                                         withInt:(int)len;
+ (RAREUTSNumber *)fromEncodedCharsWithCharArray:(IOSCharArray *)chars
                                         withInt:(int)pos
                                         withInt:(int)len
                               withRAREUTSNumber:(RAREUTSNumber *)outArg;
+ (NSString *)encodedBytesToStringWithByteArray:(IOSByteArray *)bytes
                                        withInt:(int)pos
                                        withInt:(int)len;
+ (NSString *)encodedCharsToStringWithCharArray:(IOSCharArray *)chars
                                        withInt:(int)pos
                                        withInt:(int)len;
+ (int)encodedBytesToStringWithByteArray:(IOSByteArray *)bytes
                                 withInt:(int)pos
                                 withInt:(int)len
                           withCharArray:(IOSCharArray *)outArg
                                 withInt:(int)offset
                     withRAREUTCharArray:(RAREUTCharArray *)ca;
+ (int)encodedCharsToStringWithCharArray:(IOSCharArray *)chars
                                 withInt:(int)pos
                                 withInt:(int)len
                           withCharArray:(IOSCharArray *)outArg
                                 withInt:(int)offset
                     withRAREUTCharArray:(RAREUTCharArray *)ca;
- (id)copyWithZone:(NSZone *)zone;
- (void)copyAllFieldsTo:(RAREUTSNumber *)other;
@end

J2OBJC_FIELD_SETTER(RAREUTSNumber, bigNumber_, RAREUTSDecimal *)
J2OBJC_FIELD_SETTER(RAREUTSNumber, strBuffer_, RAREUTCharArray *)
J2OBJC_FIELD_SETTER(RAREUTSNumber, sdecimal_, RAREUTSDecimal *)

typedef RAREUTSNumber ComAppnativaUtilSNumber;

@interface RAREUTSNumber_$1 : JavaLangThreadLocal {
}

- (id)initialValue OBJC_METHOD_FAMILY_NONE;
- (id)init;
@end

@interface RAREUTSNumber_$2 : JavaLangThreadLocal {
}

- (id)initialValue OBJC_METHOD_FAMILY_NONE;
- (id)init;
@end

#endif // _RAREUTSNumber_H_
