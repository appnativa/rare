//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: android/libcore/luni/src/main/java/libcore/icu/NativeDecimalFormat.java
//
//  Created by tball on 11/23/13.
//

#ifndef _LibcoreIcuNativeDecimalFormat_H_
#define _LibcoreIcuNativeDecimalFormat_H_

@class IOSCharArray;
@class IOSIntArray;
@class IOSObjectArray;
@class JavaMathBigDecimal;
@class JavaMathBigInteger;
@class JavaMathRoundingModeEnum;
@class JavaTextDecimalFormatSymbols;
@class JavaTextFieldPosition;
@class JavaTextFormat_Field;
@class JavaTextParsePosition;
@class JavaUtilCurrency;
@class LibcoreIcuLocaleData;
@class LibcoreIcuNativeDecimalFormat_FieldPositionIterator;
@protocol JavaTextAttributedCharacterIterator;

#import "JreEmulation.h"

@interface LibcoreIcuNativeDecimalFormat : NSObject < NSCopying > {
 @public
  id nsFormatter_;
  NSString *lastPattern_;
  BOOL negPrefNull_;
  BOOL negSuffNull_;
  BOOL posPrefNull_;
  BOOL posSuffNull_;
  BOOL parseBigDecimal_;
  JavaMathBigDecimal *multiplierBigDecimal_;
}

- (id)initWithNSString:(NSString *)pattern
withJavaTextDecimalFormatSymbols:(JavaTextDecimalFormatSymbols *)dfs;
- (id)initWithNSString:(NSString *)pattern
withLibcoreIcuLocaleData:(LibcoreIcuLocaleData *)data;
- (void)close;
- (void)dealloc;
- (id)clone;
- (BOOL)isEqual:(id)object;
- (void)setDecimalFormatSymbolsWithJavaTextDecimalFormatSymbols:(JavaTextDecimalFormatSymbols *)dfs;
- (void)setDecimalFormatSymbolsWithLibcoreIcuLocaleData:(LibcoreIcuLocaleData *)localeData;
- (IOSCharArray *)formatBigDecimalWithJavaMathBigDecimal:(JavaMathBigDecimal *)value
                               withJavaTextFieldPosition:(JavaTextFieldPosition *)field;
- (IOSCharArray *)formatBigIntegerWithJavaMathBigInteger:(JavaMathBigInteger *)value
                               withJavaTextFieldPosition:(JavaTextFieldPosition *)field;
- (IOSCharArray *)formatLongWithLong:(long long int)value
           withJavaTextFieldPosition:(JavaTextFieldPosition *)field;
- (IOSCharArray *)formatDoubleWithDouble:(double)value
               withJavaTextFieldPosition:(JavaTextFieldPosition *)field;
- (void)applyLocalizedPatternWithNSString:(NSString *)pattern;
- (void)applyPatternWithNSString:(NSString *)pattern;
- (id<JavaTextAttributedCharacterIterator>)formatToCharacterIteratorWithId:(id)object;
- (NSString *)toLocalizedPattern;
- (NSString *)toPattern;
- (NSNumber *)parseWithNSString:(NSString *)string
      withJavaTextParsePosition:(JavaTextParsePosition *)position;
- (int)getMaximumFractionDigits;
- (int)getMaximumIntegerDigits;
- (int)getMinimumFractionDigits;
- (int)getMinimumIntegerDigits;
- (int)getGroupingSize;
- (int)getMultiplier;
- (NSString *)getNegativePrefix;
- (NSString *)getNegativeSuffix;
- (NSString *)getPositivePrefix;
- (NSString *)getPositiveSuffix;
- (BOOL)isDecimalSeparatorAlwaysShown;
- (BOOL)isParseBigDecimal;
- (BOOL)isParseIntegerOnly;
- (BOOL)isGroupingUsed;
- (void)setDecimalSeparatorAlwaysShownWithBoolean:(BOOL)value;
- (void)setCurrencyWithJavaUtilCurrency:(JavaUtilCurrency *)currency;
- (void)setGroupingSizeWithInt:(int)value;
- (void)setGroupingUsedWithBoolean:(BOOL)value;
- (void)setMaximumFractionDigitsWithInt:(int)value;
- (void)setMaximumIntegerDigitsWithInt:(int)value;
- (void)setMinimumFractionDigitsWithInt:(int)value;
- (void)setMinimumIntegerDigitsWithInt:(int)value;
- (void)setMultiplierWithInt:(int)value;
- (void)setNegativePrefixWithNSString:(NSString *)value;
- (void)setNegativeSuffixWithNSString:(NSString *)value;
- (void)setPositivePrefixWithNSString:(NSString *)value;
- (void)setPositiveSuffixWithNSString:(NSString *)value;
- (void)setParseBigDecimalWithBoolean:(BOOL)value;
- (void)setParseIntegerOnlyWithBoolean:(BOOL)value;
+ (void)applyPatternWithId:(id)nativeFormatter
               withBoolean:(BOOL)localized
              withNSString:(NSString *)pattern;
- (void)setRoundingModeWithJavaMathRoundingModeEnum:(JavaMathRoundingModeEnum *)roundingMode
                                         withDouble:(double)roundingIncrement;
+ (id)openWithNSString:(NSString *)pattern
          withNSString:(NSString *)currencySymbol
              withChar:(unichar)decimalSeparator
              withChar:(unichar)digit
          withNSString:(NSString *)exponentSeparator
              withChar:(unichar)groupingSeparator
          withNSString:(NSString *)infinity
          withNSString:(NSString *)internationalCurrencySymbol
              withChar:(unichar)minusSign
              withChar:(unichar)monetaryDecimalSeparator
          withNSString:(NSString *)nan
              withChar:(unichar)patternSeparator
              withChar:(unichar)percent
              withChar:(unichar)perMill
              withChar:(unichar)zeroDigit;
+ (void)setDecimalFormatSymbolsWithId:(id)nativeFormatter
                         withNSString:(NSString *)currencySymbol
                             withChar:(unichar)decimalSeparator
                             withChar:(unichar)digit
                         withNSString:(NSString *)exponentSeparator
                             withChar:(unichar)groupingSeparator
                         withNSString:(NSString *)infinity
                         withNSString:(NSString *)internationalCurrencySymbol
                             withChar:(unichar)minusSign
                             withChar:(unichar)monetaryDecimalSeparator
                         withNSString:(NSString *)nan
                             withChar:(unichar)patternSeparator
                             withChar:(unichar)percent
                             withChar:(unichar)perMill
                             withChar:(unichar)zeroDigit;
+ (void)applyPatternImplWithId:(id)nativeFormatter
                   withBoolean:(BOOL)localized
                  withNSString:(NSString *)pattern;
+ (id)cloneImplWithId:(id)nativeFormatter;
+ (void)closeWithId:(id)nativeFormatter;
+ (IOSCharArray *)formatDoubleWithId:(id)nativeFormatter
                          withDouble:(double)value
withLibcoreIcuNativeDecimalFormat_FieldPositionIterator:(LibcoreIcuNativeDecimalFormat_FieldPositionIterator *)iter;
+ (IOSCharArray *)formatLongWithId:(id)nativeFormatter
                          withLong:(long long int)value
withLibcoreIcuNativeDecimalFormat_FieldPositionIterator:(LibcoreIcuNativeDecimalFormat_FieldPositionIterator *)iter;
+ (NSNumber *)parseWithId:(id)nativeFormatter
             withNSString:(NSString *)string
withJavaTextParsePosition:(JavaTextParsePosition *)position
              withBoolean:(BOOL)parseBigDecimal;
+ (NSString *)toPatternImplWithId:(id)nativeFormatter
                      withBoolean:(BOOL)localized;
- (id)copyWithZone:(NSZone *)zone;
- (void)copyAllFieldsTo:(LibcoreIcuNativeDecimalFormat *)other;
@end

J2OBJC_FIELD_SETTER(LibcoreIcuNativeDecimalFormat, nsFormatter_, id)
J2OBJC_FIELD_SETTER(LibcoreIcuNativeDecimalFormat, lastPattern_, NSString *)
J2OBJC_FIELD_SETTER(LibcoreIcuNativeDecimalFormat, multiplierBigDecimal_, JavaMathBigDecimal *)

@interface LibcoreIcuNativeDecimalFormat_FieldPositionIterator : NSObject {
 @public
  IOSIntArray *data_;
  int pos_;
}

+ (IOSObjectArray *)fields;
+ (void)setFields:(IOSObjectArray *)fields;
- (id)init;
+ (LibcoreIcuNativeDecimalFormat_FieldPositionIterator *)forFieldPositionWithJavaTextFieldPosition:(JavaTextFieldPosition *)fp;
+ (int)getNativeFieldPositionIdWithJavaTextFieldPosition:(JavaTextFieldPosition *)fp;
+ (void)setFieldPositionWithLibcoreIcuNativeDecimalFormat_FieldPositionIterator:(LibcoreIcuNativeDecimalFormat_FieldPositionIterator *)fpi
                                                      withJavaTextFieldPosition:(JavaTextFieldPosition *)fp;
- (BOOL)next;
- (void)checkValid;
- (int)fieldId;
- (JavaTextFormat_Field *)field;
- (int)start;
- (int)limit;
- (void)setDataWithIntArray:(IOSIntArray *)data;
- (void)copyAllFieldsTo:(LibcoreIcuNativeDecimalFormat_FieldPositionIterator *)other;
@end

J2OBJC_FIELD_SETTER(LibcoreIcuNativeDecimalFormat_FieldPositionIterator, data_, IOSIntArray *)

#endif // _LibcoreIcuNativeDecimalFormat_H_
