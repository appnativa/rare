//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: apache_harmony/classlib/modules/luni/src/main/java/java/lang/StrictMath.java
//
//  Created by tball on 11/23/13.
//

#ifndef _JavaLangStrictMath_H_
#define _JavaLangStrictMath_H_

@class JavaUtilRandom;

#import "JreEmulation.h"

#define JavaLangStrictMath_E 2.718281828459045
#define JavaLangStrictMath_PI 3.141592653589793

@interface JavaLangStrictMath : NSObject {
}

+ (double)E;
+ (double)PI;
+ (JavaUtilRandom *)random_;
+ (void)setRandom_:(JavaUtilRandom *)random_;
- (id)init;
+ (double)absWithDouble:(double)d;
+ (float)absWithFloat:(float)f;
+ (int)absWithInt:(int)i;
+ (long long int)absWithLong:(long long int)l;
+ (double)acosWithDouble:(double)d;
+ (double)asinWithDouble:(double)d;
+ (double)atanWithDouble:(double)d;
+ (double)atan2WithDouble:(double)y
               withDouble:(double)x;
+ (double)cbrtWithDouble:(double)d;
+ (double)ceilWithDouble:(double)d;
+ (double)coshWithDouble:(double)d;
+ (double)cosWithDouble:(double)d;
+ (double)expWithDouble:(double)d;
+ (double)expm1WithDouble:(double)d;
+ (double)floorWithDouble:(double)d;
+ (double)hypotWithDouble:(double)x
               withDouble:(double)y;
+ (double)IEEEremainderWithDouble:(double)x
                       withDouble:(double)y;
+ (double)logWithDouble:(double)d;
+ (double)log10WithDouble:(double)d;
+ (double)log1pWithDouble:(double)d;
+ (double)maxWithDouble:(double)d1
             withDouble:(double)d2;
+ (float)maxWithFloat:(float)f1
            withFloat:(float)f2;
+ (int)maxWithInt:(int)i1
          withInt:(int)i2;
+ (long long int)maxWithLong:(long long int)l1
                    withLong:(long long int)l2;
+ (double)minWithDouble:(double)d1
             withDouble:(double)d2;
+ (float)minWithFloat:(float)f1
            withFloat:(float)f2;
+ (int)minWithInt:(int)i1
          withInt:(int)i2;
+ (long long int)minWithLong:(long long int)l1
                    withLong:(long long int)l2;
+ (double)powWithDouble:(double)x
             withDouble:(double)y;
+ (double)random;
+ (double)rintWithDouble:(double)d;
+ (long long int)roundWithDouble:(double)d;
+ (int)roundWithFloat:(float)f;
+ (double)signumWithDouble:(double)d;
+ (float)signumWithFloat:(float)f;
+ (double)sinhWithDouble:(double)d;
+ (double)sinWithDouble:(double)d;
+ (double)sqrtWithDouble:(double)d;
+ (double)tanWithDouble:(double)d;
+ (double)tanhWithDouble:(double)d;
+ (double)toDegreesWithDouble:(double)angrad;
+ (double)toRadiansWithDouble:(double)angdeg;
+ (double)ulpWithDouble:(double)d;
+ (float)ulpWithFloat:(float)f;
+ (double)nextafterWithDouble:(double)x
                   withDouble:(double)y;
+ (float)nextafterfWithFloat:(float)x
                   withFloat:(float)y;
@end

#endif // _JavaLangStrictMath_H_
