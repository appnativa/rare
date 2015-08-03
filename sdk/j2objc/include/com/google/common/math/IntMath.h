//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/guava/sources/com/google/common/math/IntMath.java
//
//  Created by tball on 11/23/13.
//

#import "JreEmulation.h"

#if !ComGoogleCommonMathIntMath_RESTRICT
#define ComGoogleCommonMathIntMath_INCLUDE_ALL 1
#endif
#undef ComGoogleCommonMathIntMath_RESTRICT

#if !defined (_ComGoogleCommonMathIntMath_) && (ComGoogleCommonMathIntMath_INCLUDE_ALL || ComGoogleCommonMathIntMath_INCLUDE)
#define _ComGoogleCommonMathIntMath_

@class IOSByteArray;
@class IOSIntArray;
@class JavaMathRoundingModeEnum;

#define ComGoogleCommonMathIntMath_FLOOR_SQRT_MAX_INT 46340
#define ComGoogleCommonMathIntMath_MAX_POWER_OF_SQRT2_UNSIGNED -1257966797

@interface ComGoogleCommonMathIntMath : NSObject {
}

+ (int)MAX_POWER_OF_SQRT2_UNSIGNED;
+ (IOSByteArray *)maxLog10ForLeadingZeros;
+ (IOSIntArray *)powersOf10;
+ (IOSIntArray *)halfPowersOf10;
+ (int)FLOOR_SQRT_MAX_INT;
+ (IOSIntArray *)factorials;
+ (IOSIntArray *)biggestBinomials;
+ (void)setBiggestBinomials:(IOSIntArray *)biggestBinomials;
+ (BOOL)isPowerOfTwoWithInt:(int)x;
+ (int)log2WithInt:(int)x
withJavaMathRoundingModeEnum:(JavaMathRoundingModeEnum *)mode;
+ (int)log10WithInt:(int)x
withJavaMathRoundingModeEnum:(JavaMathRoundingModeEnum *)mode;
+ (int)log10FloorWithInt:(int)x;
+ (int)powWithInt:(int)b
          withInt:(int)k;
+ (int)sqrtWithInt:(int)x
withJavaMathRoundingModeEnum:(JavaMathRoundingModeEnum *)mode;
+ (int)sqrtFloorWithInt:(int)x;
+ (int)divideWithInt:(int)p
             withInt:(int)q
withJavaMathRoundingModeEnum:(JavaMathRoundingModeEnum *)mode;
+ (int)modWithInt:(int)x
          withInt:(int)m;
+ (int)gcdWithInt:(int)a
          withInt:(int)b;
+ (int)checkedAddWithInt:(int)a
                 withInt:(int)b;
+ (int)checkedSubtractWithInt:(int)a
                      withInt:(int)b;
+ (int)checkedMultiplyWithInt:(int)a
                      withInt:(int)b;
+ (int)checkedPowWithInt:(int)b
                 withInt:(int)k;
+ (int)factorialWithInt:(int)n;
+ (int)binomialWithInt:(int)n
               withInt:(int)k;
+ (int)meanWithInt:(int)x
           withInt:(int)y;
- (id)init;
@end
#endif