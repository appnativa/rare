//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/guava/sources/com/google/common/math/LongMath.java
//
//  Created by tball on 11/23/13.
//

#import "JreEmulation.h"

#if !ComGoogleCommonMathLongMath_RESTRICT
#define ComGoogleCommonMathLongMath_INCLUDE_ALL 1
#endif
#undef ComGoogleCommonMathLongMath_RESTRICT

#if !defined (_ComGoogleCommonMathLongMath_) && (ComGoogleCommonMathLongMath_INCLUDE_ALL || ComGoogleCommonMathLongMath_INCLUDE)
#define _ComGoogleCommonMathLongMath_

@class IOSByteArray;
@class IOSIntArray;
@class IOSLongArray;
@class JavaMathRoundingModeEnum;

#define ComGoogleCommonMathLongMath_FLOOR_SQRT_MAX_LONG 3037000499
#define ComGoogleCommonMathLongMath_MAX_POWER_OF_SQRT2_UNSIGNED -5402926248376769404

@interface ComGoogleCommonMathLongMath : NSObject {
}

+ (long long int)MAX_POWER_OF_SQRT2_UNSIGNED;
+ (IOSByteArray *)maxLog10ForLeadingZeros;
+ (IOSLongArray *)powersOf10;
+ (IOSLongArray *)halfPowersOf10;
+ (long long int)FLOOR_SQRT_MAX_LONG;
+ (IOSLongArray *)factorials;
+ (IOSIntArray *)biggestBinomials;
+ (IOSIntArray *)biggestSimpleBinomials;
+ (BOOL)isPowerOfTwoWithLong:(long long int)x;
+ (int)log2WithLong:(long long int)x
withJavaMathRoundingModeEnum:(JavaMathRoundingModeEnum *)mode;
+ (int)log10WithLong:(long long int)x
withJavaMathRoundingModeEnum:(JavaMathRoundingModeEnum *)mode;
+ (int)log10FloorWithLong:(long long int)x;
+ (long long int)powWithLong:(long long int)b
                     withInt:(int)k;
+ (long long int)sqrtWithLong:(long long int)x
 withJavaMathRoundingModeEnum:(JavaMathRoundingModeEnum *)mode;
+ (long long int)sqrtFloorWithLong:(long long int)x;
+ (long long int)divideWithLong:(long long int)p
                       withLong:(long long int)q
   withJavaMathRoundingModeEnum:(JavaMathRoundingModeEnum *)mode;
+ (int)modWithLong:(long long int)x
           withInt:(int)m;
+ (long long int)modWithLong:(long long int)x
                    withLong:(long long int)m;
+ (long long int)gcdWithLong:(long long int)a
                    withLong:(long long int)b;
+ (long long int)checkedAddWithLong:(long long int)a
                           withLong:(long long int)b;
+ (long long int)checkedSubtractWithLong:(long long int)a
                                withLong:(long long int)b;
+ (long long int)checkedMultiplyWithLong:(long long int)a
                                withLong:(long long int)b;
+ (long long int)checkedPowWithLong:(long long int)b
                            withInt:(int)k;
+ (long long int)factorialWithInt:(int)n;
+ (long long int)binomialWithInt:(int)n
                         withInt:(int)k;
+ (long long int)multiplyFractionWithLong:(long long int)x
                                 withLong:(long long int)numerator
                                 withLong:(long long int)denominator;
+ (BOOL)fitsInIntWithLong:(long long int)x;
+ (long long int)meanWithLong:(long long int)x
                     withLong:(long long int)y;
- (id)init;
@end
#endif
