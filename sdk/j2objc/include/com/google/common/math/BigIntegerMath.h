//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/guava/sources/com/google/common/math/BigIntegerMath.java
//
//  Created by tball on 11/23/13.
//

#import "JreEmulation.h"

#if !ComGoogleCommonMathBigIntegerMath_RESTRICT
#define ComGoogleCommonMathBigIntegerMath_INCLUDE_ALL 1
#endif
#undef ComGoogleCommonMathBigIntegerMath_RESTRICT

#if !defined (_ComGoogleCommonMathBigIntegerMath_) && (ComGoogleCommonMathBigIntegerMath_INCLUDE_ALL || ComGoogleCommonMathBigIntegerMath_INCLUDE)
#define _ComGoogleCommonMathBigIntegerMath_

@class JavaMathBigInteger;
@class JavaMathRoundingModeEnum;
@protocol JavaUtilList;

#define ComGoogleCommonMathBigIntegerMath_SQRT2_PRECOMPUTE_THRESHOLD 256

@interface ComGoogleCommonMathBigIntegerMath : NSObject {
}

+ (int)SQRT2_PRECOMPUTE_THRESHOLD;
+ (JavaMathBigInteger *)SQRT2_PRECOMPUTED_BITS;
+ (double)LN_10;
+ (double)LN_2;
+ (BOOL)isPowerOfTwoWithJavaMathBigInteger:(JavaMathBigInteger *)x;
+ (int)log2WithJavaMathBigInteger:(JavaMathBigInteger *)x
     withJavaMathRoundingModeEnum:(JavaMathRoundingModeEnum *)mode;
+ (int)log10WithJavaMathBigInteger:(JavaMathBigInteger *)x
      withJavaMathRoundingModeEnum:(JavaMathRoundingModeEnum *)mode;
+ (JavaMathBigInteger *)sqrtWithJavaMathBigInteger:(JavaMathBigInteger *)x
                      withJavaMathRoundingModeEnum:(JavaMathRoundingModeEnum *)mode;
+ (JavaMathBigInteger *)sqrtFloorWithJavaMathBigInteger:(JavaMathBigInteger *)x;
+ (JavaMathBigInteger *)sqrtApproxWithDoublesWithJavaMathBigInteger:(JavaMathBigInteger *)x;
+ (JavaMathBigInteger *)divideWithJavaMathBigInteger:(JavaMathBigInteger *)p
                              withJavaMathBigInteger:(JavaMathBigInteger *)q
                        withJavaMathRoundingModeEnum:(JavaMathRoundingModeEnum *)mode;
+ (JavaMathBigInteger *)factorialWithInt:(int)n;
+ (JavaMathBigInteger *)listProductWithJavaUtilList:(id<JavaUtilList>)nums;
+ (JavaMathBigInteger *)listProductWithJavaUtilList:(id<JavaUtilList>)nums
                                            withInt:(int)start
                                            withInt:(int)end;
+ (JavaMathBigInteger *)binomialWithInt:(int)n
                                withInt:(int)k;
+ (BOOL)fitsInLongWithJavaMathBigInteger:(JavaMathBigInteger *)x;
- (id)init;
@end
#endif
