//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/guava/sources/com/google/common/primitives/UnsignedLong.java
//
//  Created by tball on 11/23/13.
//

#import "JreEmulation.h"

#if !ComGoogleCommonPrimitivesUnsignedLong_RESTRICT
#define ComGoogleCommonPrimitivesUnsignedLong_INCLUDE_ALL 1
#endif
#undef ComGoogleCommonPrimitivesUnsignedLong_RESTRICT

#if !defined (_ComGoogleCommonPrimitivesUnsignedLong_) && (ComGoogleCommonPrimitivesUnsignedLong_INCLUDE_ALL || ComGoogleCommonPrimitivesUnsignedLong_INCLUDE)
#define _ComGoogleCommonPrimitivesUnsignedLong_

@class JavaMathBigInteger;

#define JavaLangComparable_RESTRICT 1
#define JavaLangComparable_INCLUDE 1
#include "java/lang/Comparable.h"

#define JavaIoSerializable_RESTRICT 1
#define JavaIoSerializable_INCLUDE 1
#include "java/io/Serializable.h"

#define ComGoogleCommonPrimitivesUnsignedLong_UNSIGNED_MASK 9223372036854775807

@interface ComGoogleCommonPrimitivesUnsignedLong : NSNumber < JavaLangComparable, JavaIoSerializable > {
 @public
  long long int value_;
}

+ (ComGoogleCommonPrimitivesUnsignedLong *)ZERO;
+ (ComGoogleCommonPrimitivesUnsignedLong *)ONE;
+ (ComGoogleCommonPrimitivesUnsignedLong *)MAX_VALUE;
- (id)initWithLong:(long long int)value;
+ (ComGoogleCommonPrimitivesUnsignedLong *)asUnsignedWithLong:(long long int)value;
+ (ComGoogleCommonPrimitivesUnsignedLong *)fromLongBitsWithLong:(long long int)bits;
+ (ComGoogleCommonPrimitivesUnsignedLong *)valueOfWithLong:(long long int)value;
+ (ComGoogleCommonPrimitivesUnsignedLong *)valueOfWithJavaMathBigInteger:(JavaMathBigInteger *)value;
+ (ComGoogleCommonPrimitivesUnsignedLong *)valueOfWithNSString:(NSString *)string;
+ (ComGoogleCommonPrimitivesUnsignedLong *)valueOfWithNSString:(NSString *)string
                                                       withInt:(int)radix;
- (ComGoogleCommonPrimitivesUnsignedLong *)addWithComGoogleCommonPrimitivesUnsignedLong:(ComGoogleCommonPrimitivesUnsignedLong *)val;
- (ComGoogleCommonPrimitivesUnsignedLong *)plusWithComGoogleCommonPrimitivesUnsignedLong:(ComGoogleCommonPrimitivesUnsignedLong *)val;
- (ComGoogleCommonPrimitivesUnsignedLong *)subtractWithComGoogleCommonPrimitivesUnsignedLong:(ComGoogleCommonPrimitivesUnsignedLong *)val;
- (ComGoogleCommonPrimitivesUnsignedLong *)minusWithComGoogleCommonPrimitivesUnsignedLong:(ComGoogleCommonPrimitivesUnsignedLong *)val;
- (ComGoogleCommonPrimitivesUnsignedLong *)multiplyWithComGoogleCommonPrimitivesUnsignedLong:(ComGoogleCommonPrimitivesUnsignedLong *)val;
- (ComGoogleCommonPrimitivesUnsignedLong *)timesWithComGoogleCommonPrimitivesUnsignedLong:(ComGoogleCommonPrimitivesUnsignedLong *)val;
- (ComGoogleCommonPrimitivesUnsignedLong *)divideWithComGoogleCommonPrimitivesUnsignedLong:(ComGoogleCommonPrimitivesUnsignedLong *)val;
- (ComGoogleCommonPrimitivesUnsignedLong *)dividedByWithComGoogleCommonPrimitivesUnsignedLong:(ComGoogleCommonPrimitivesUnsignedLong *)val;
- (ComGoogleCommonPrimitivesUnsignedLong *)remainderWithComGoogleCommonPrimitivesUnsignedLong:(ComGoogleCommonPrimitivesUnsignedLong *)val;
- (ComGoogleCommonPrimitivesUnsignedLong *)modWithComGoogleCommonPrimitivesUnsignedLong:(ComGoogleCommonPrimitivesUnsignedLong *)val;
- (int)intValue;
- (long long int)longLongValue;
- (float)floatValue;
- (double)doubleValue;
- (JavaMathBigInteger *)bigIntegerValue;
- (int)compareToWithId:(ComGoogleCommonPrimitivesUnsignedLong *)o;
- (NSUInteger)hash;
- (BOOL)isEqual:(id)obj;
- (NSString *)description;
- (NSString *)toStringWithInt:(int)radix;
- (void)copyAllFieldsTo:(ComGoogleCommonPrimitivesUnsignedLong *)other;
@end
#endif
