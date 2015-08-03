//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: android/libcore/luni/src/main/java/java/util/concurrent/TimeUnit.java
//
//  Created by tball on 11/23/13.
//

#ifndef _JavaUtilConcurrentTimeUnit_H_
#define _JavaUtilConcurrentTimeUnit_H_

@class JavaLangThread;

#import "JreEmulation.h"
#include "java/lang/Enum.h"

#define JavaUtilConcurrentTimeUnitEnum_C0 1
#define JavaUtilConcurrentTimeUnitEnum_C1 1000
#define JavaUtilConcurrentTimeUnitEnum_C2 1000000
#define JavaUtilConcurrentTimeUnitEnum_C3 1000000000
#define JavaUtilConcurrentTimeUnitEnum_C4 60000000000
#define JavaUtilConcurrentTimeUnitEnum_C5 3600000000000
#define JavaUtilConcurrentTimeUnitEnum_C6 86400000000000
#define JavaUtilConcurrentTimeUnitEnum_MAX 9223372036854775807

typedef enum {
  JavaUtilConcurrentTimeUnit_NANOSECONDS = 0,
  JavaUtilConcurrentTimeUnit_MICROSECONDS = 1,
  JavaUtilConcurrentTimeUnit_MILLISECONDS = 2,
  JavaUtilConcurrentTimeUnit_SECONDS = 3,
  JavaUtilConcurrentTimeUnit_MINUTES = 4,
  JavaUtilConcurrentTimeUnit_HOURS = 5,
  JavaUtilConcurrentTimeUnit_DAYS = 6,
} JavaUtilConcurrentTimeUnit;

@interface JavaUtilConcurrentTimeUnitEnum : JavaLangEnum < NSCopying > {
}
+ (JavaUtilConcurrentTimeUnitEnum *)NANOSECONDS;
+ (JavaUtilConcurrentTimeUnitEnum *)MICROSECONDS;
+ (JavaUtilConcurrentTimeUnitEnum *)MILLISECONDS;
+ (JavaUtilConcurrentTimeUnitEnum *)SECONDS;
+ (JavaUtilConcurrentTimeUnitEnum *)MINUTES;
+ (JavaUtilConcurrentTimeUnitEnum *)HOURS;
+ (JavaUtilConcurrentTimeUnitEnum *)DAYS;
+ (IOSObjectArray *)values;
+ (JavaUtilConcurrentTimeUnitEnum *)valueOfWithNSString:(NSString *)name;
- (id)copyWithZone:(NSZone *)zone;
+ (long long int)C0;
+ (long long int)C1;
+ (long long int)C2;
+ (long long int)C3;
+ (long long int)C4;
+ (long long int)C5;
+ (long long int)C6;
+ (long long int)MAX;
+ (long long int)xWithLong:(long long int)d
                  withLong:(long long int)m
                  withLong:(long long int)over;
- (long long int)convertWithLong:(long long int)sourceDuration
withJavaUtilConcurrentTimeUnitEnum:(JavaUtilConcurrentTimeUnitEnum *)sourceUnit;
- (long long int)toNanosWithLong:(long long int)duration;
- (long long int)toMicrosWithLong:(long long int)duration;
- (long long int)toMillisWithLong:(long long int)duration;
- (long long int)toSecondsWithLong:(long long int)duration;
- (long long int)toMinutesWithLong:(long long int)duration;
- (long long int)toHoursWithLong:(long long int)duration;
- (long long int)toDaysWithLong:(long long int)duration;
- (int)excessNanosWithLong:(long long int)d
                  withLong:(long long int)m;
- (void)timedWaitWithId:(id)obj
               withLong:(long long int)timeout;
- (void)timedJoinWithJavaLangThread:(JavaLangThread *)thread
                           withLong:(long long int)timeout;
- (void)sleepWithLong:(long long int)timeout;
- (id)initWithNSString:(NSString *)__name withInt:(int)__ordinal;
@end

@interface JavaUtilConcurrentTimeUnitEnum_$1 : JavaUtilConcurrentTimeUnitEnum {
}

- (long long int)toNanosWithLong:(long long int)d;
- (long long int)toMicrosWithLong:(long long int)d;
- (long long int)toMillisWithLong:(long long int)d;
- (long long int)toSecondsWithLong:(long long int)d;
- (long long int)toMinutesWithLong:(long long int)d;
- (long long int)toHoursWithLong:(long long int)d;
- (long long int)toDaysWithLong:(long long int)d;
- (long long int)convertWithLong:(long long int)d
withJavaUtilConcurrentTimeUnitEnum:(JavaUtilConcurrentTimeUnitEnum *)u;
- (int)excessNanosWithLong:(long long int)d
                  withLong:(long long int)m;
- (id)initWithNSString:(NSString *)__name withInt:(int)__ordinal;
@end

@interface JavaUtilConcurrentTimeUnitEnum_$2 : JavaUtilConcurrentTimeUnitEnum {
}

- (long long int)toNanosWithLong:(long long int)d;
- (long long int)toMicrosWithLong:(long long int)d;
- (long long int)toMillisWithLong:(long long int)d;
- (long long int)toSecondsWithLong:(long long int)d;
- (long long int)toMinutesWithLong:(long long int)d;
- (long long int)toHoursWithLong:(long long int)d;
- (long long int)toDaysWithLong:(long long int)d;
- (long long int)convertWithLong:(long long int)d
withJavaUtilConcurrentTimeUnitEnum:(JavaUtilConcurrentTimeUnitEnum *)u;
- (int)excessNanosWithLong:(long long int)d
                  withLong:(long long int)m;
- (id)initWithNSString:(NSString *)__name withInt:(int)__ordinal;
@end

@interface JavaUtilConcurrentTimeUnitEnum_$3 : JavaUtilConcurrentTimeUnitEnum {
}

- (long long int)toNanosWithLong:(long long int)d;
- (long long int)toMicrosWithLong:(long long int)d;
- (long long int)toMillisWithLong:(long long int)d;
- (long long int)toSecondsWithLong:(long long int)d;
- (long long int)toMinutesWithLong:(long long int)d;
- (long long int)toHoursWithLong:(long long int)d;
- (long long int)toDaysWithLong:(long long int)d;
- (long long int)convertWithLong:(long long int)d
withJavaUtilConcurrentTimeUnitEnum:(JavaUtilConcurrentTimeUnitEnum *)u;
- (int)excessNanosWithLong:(long long int)d
                  withLong:(long long int)m;
- (id)initWithNSString:(NSString *)__name withInt:(int)__ordinal;
@end

@interface JavaUtilConcurrentTimeUnitEnum_$4 : JavaUtilConcurrentTimeUnitEnum {
}

- (long long int)toNanosWithLong:(long long int)d;
- (long long int)toMicrosWithLong:(long long int)d;
- (long long int)toMillisWithLong:(long long int)d;
- (long long int)toSecondsWithLong:(long long int)d;
- (long long int)toMinutesWithLong:(long long int)d;
- (long long int)toHoursWithLong:(long long int)d;
- (long long int)toDaysWithLong:(long long int)d;
- (long long int)convertWithLong:(long long int)d
withJavaUtilConcurrentTimeUnitEnum:(JavaUtilConcurrentTimeUnitEnum *)u;
- (int)excessNanosWithLong:(long long int)d
                  withLong:(long long int)m;
- (id)initWithNSString:(NSString *)__name withInt:(int)__ordinal;
@end

@interface JavaUtilConcurrentTimeUnitEnum_$5 : JavaUtilConcurrentTimeUnitEnum {
}

- (long long int)toNanosWithLong:(long long int)d;
- (long long int)toMicrosWithLong:(long long int)d;
- (long long int)toMillisWithLong:(long long int)d;
- (long long int)toSecondsWithLong:(long long int)d;
- (long long int)toMinutesWithLong:(long long int)d;
- (long long int)toHoursWithLong:(long long int)d;
- (long long int)toDaysWithLong:(long long int)d;
- (long long int)convertWithLong:(long long int)d
withJavaUtilConcurrentTimeUnitEnum:(JavaUtilConcurrentTimeUnitEnum *)u;
- (int)excessNanosWithLong:(long long int)d
                  withLong:(long long int)m;
- (id)initWithNSString:(NSString *)__name withInt:(int)__ordinal;
@end

@interface JavaUtilConcurrentTimeUnitEnum_$6 : JavaUtilConcurrentTimeUnitEnum {
}

- (long long int)toNanosWithLong:(long long int)d;
- (long long int)toMicrosWithLong:(long long int)d;
- (long long int)toMillisWithLong:(long long int)d;
- (long long int)toSecondsWithLong:(long long int)d;
- (long long int)toMinutesWithLong:(long long int)d;
- (long long int)toHoursWithLong:(long long int)d;
- (long long int)toDaysWithLong:(long long int)d;
- (long long int)convertWithLong:(long long int)d
withJavaUtilConcurrentTimeUnitEnum:(JavaUtilConcurrentTimeUnitEnum *)u;
- (int)excessNanosWithLong:(long long int)d
                  withLong:(long long int)m;
- (id)initWithNSString:(NSString *)__name withInt:(int)__ordinal;
@end

@interface JavaUtilConcurrentTimeUnitEnum_$7 : JavaUtilConcurrentTimeUnitEnum {
}

- (long long int)toNanosWithLong:(long long int)d;
- (long long int)toMicrosWithLong:(long long int)d;
- (long long int)toMillisWithLong:(long long int)d;
- (long long int)toSecondsWithLong:(long long int)d;
- (long long int)toMinutesWithLong:(long long int)d;
- (long long int)toHoursWithLong:(long long int)d;
- (long long int)toDaysWithLong:(long long int)d;
- (long long int)convertWithLong:(long long int)d
withJavaUtilConcurrentTimeUnitEnum:(JavaUtilConcurrentTimeUnitEnum *)u;
- (int)excessNanosWithLong:(long long int)d
                  withLong:(long long int)m;
- (id)initWithNSString:(NSString *)__name withInt:(int)__ordinal;
@end

#endif // _JavaUtilConcurrentTimeUnit_H_
