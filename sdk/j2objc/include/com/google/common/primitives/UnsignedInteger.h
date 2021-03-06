//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/guava/sources/com/google/common/primitives/UnsignedInteger.java
//
//  Created by tball on 11/23/13.
//

#import "JreEmulation.h"

#if !ComGoogleCommonPrimitivesUnsignedInteger_RESTRICT
#define ComGoogleCommonPrimitivesUnsignedInteger_INCLUDE_ALL 1
#endif
#undef ComGoogleCommonPrimitivesUnsignedInteger_RESTRICT

#if !defined (_ComGoogleCommonPrimitivesUnsignedInteger_) && (ComGoogleCommonPrimitivesUnsignedInteger_INCLUDE_ALL || ComGoogleCommonPrimitivesUnsignedInteger_INCLUDE)
#define _ComGoogleCommonPrimitivesUnsignedInteger_

@class JavaMathBigInteger;

#define JavaLangComparable_RESTRICT 1
#define JavaLangComparable_INCLUDE 1
#include "java/lang/Comparable.h"

@interface ComGoogleCommonPrimitivesUnsignedInteger : NSNumber < JavaLangComparable > {
 @public
  int value_;
}

+ (ComGoogleCommonPrimitivesUnsignedInteger *)ZERO;
+ (ComGoogleCommonPrimitivesUnsignedInteger *)ONE;
+ (ComGoogleCommonPrimitivesUnsignedInteger *)MAX_VALUE;
- (id)initWithInt:(int)value;
+ (ComGoogleCommonPrimitivesUnsignedInteger *)asUnsignedWithInt:(int)value;
+ (ComGoogleCommonPrimitivesUnsignedInteger *)fromIntBitsWithInt:(int)bits;
+ (ComGoogleCommonPrimitivesUnsignedInteger *)valueOfWithLong:(long long int)value;
+ (ComGoogleCommonPrimitivesUnsignedInteger *)valueOfWithJavaMathBigInteger:(JavaMathBigInteger *)value;
+ (ComGoogleCommonPrimitivesUnsignedInteger *)valueOfWithNSString:(NSString *)string;
+ (ComGoogleCommonPrimitivesUnsignedInteger *)valueOfWithNSString:(NSString *)string
                                                          withInt:(int)radix;
- (ComGoogleCommonPrimitivesUnsignedInteger *)addWithComGoogleCommonPrimitivesUnsignedInteger:(ComGoogleCommonPrimitivesUnsignedInteger *)val;
- (ComGoogleCommonPrimitivesUnsignedInteger *)plusWithComGoogleCommonPrimitivesUnsignedInteger:(ComGoogleCommonPrimitivesUnsignedInteger *)val;
- (ComGoogleCommonPrimitivesUnsignedInteger *)subtractWithComGoogleCommonPrimitivesUnsignedInteger:(ComGoogleCommonPrimitivesUnsignedInteger *)val;
- (ComGoogleCommonPrimitivesUnsignedInteger *)minusWithComGoogleCommonPrimitivesUnsignedInteger:(ComGoogleCommonPrimitivesUnsignedInteger *)val;
- (ComGoogleCommonPrimitivesUnsignedInteger *)multiplyWithComGoogleCommonPrimitivesUnsignedInteger:(ComGoogleCommonPrimitivesUnsignedInteger *)val;
- (ComGoogleCommonPrimitivesUnsignedInteger *)timesWithComGoogleCommonPrimitivesUnsignedInteger:(ComGoogleCommonPrimitivesUnsignedInteger *)val;
- (ComGoogleCommonPrimitivesUnsignedInteger *)divideWithComGoogleCommonPrimitivesUnsignedInteger:(ComGoogleCommonPrimitivesUnsignedInteger *)val;
- (ComGoogleCommonPrimitivesUnsignedInteger *)dividedByWithComGoogleCommonPrimitivesUnsignedInteger:(ComGoogleCommonPrimitivesUnsignedInteger *)val;
- (ComGoogleCommonPrimitivesUnsignedInteger *)remainderWithComGoogleCommonPrimitivesUnsignedInteger:(ComGoogleCommonPrimitivesUnsignedInteger *)val;
- (ComGoogleCommonPrimitivesUnsignedInteger *)modWithComGoogleCommonPrimitivesUnsignedInteger:(ComGoogleCommonPrimitivesUnsignedInteger *)val;
- (int)intValue;
- (long long int)longLongValue;
- (float)floatValue;
- (double)doubleValue;
- (JavaMathBigInteger *)bigIntegerValue;
- (int)compareToWithId:(ComGoogleCommonPrimitivesUnsignedInteger *)other;
- (NSUInteger)hash;
- (BOOL)isEqual:(id)obj;
- (NSString *)description;
- (NSString *)toStringWithInt:(int)radix;
- (void)copyAllFieldsTo:(ComGoogleCommonPrimitivesUnsignedInteger *)other;
@end
#endif
