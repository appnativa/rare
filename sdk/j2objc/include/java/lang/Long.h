//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: apache_harmony/classlib/modules/luni/src/main/java/java/lang/Long.java
//
//  Created by tball on 11/23/13.
//

#ifndef _JavaLangLong_H_
#define _JavaLangLong_H_

@class IOSClass;
@class IOSObjectArray;

#import "JreEmulation.h"
#include "java/lang/Comparable.h"

#define JavaLangLong_MAX_VALUE 9223372036854775807
#define JavaLangLong_MIN_VALUE ((long long) 0x8000000000000000LL)
#define JavaLangLong_SIZE 64

@interface JavaLangLong : NSNumber < JavaLangComparable > {
 @public
  long long int value_;
}

+ (long long int)MAX_VALUE;
+ (long long int)MIN_VALUE;
+ (IOSClass *)TYPE;
+ (int)SIZE;
- (id)initWithLong:(long long int)value;
- (id)initWithNSString:(NSString *)string;
- (char)charValue;
- (int)compareToWithId:(JavaLangLong *)object;
+ (int)compareWithLong:(long long int)lhs
              withLong:(long long int)rhs;
+ (JavaLangLong *)decodeWithNSString:(NSString *)string;
- (double)doubleValue;
- (BOOL)isEqual:(id)o;
- (float)floatValue;
+ (JavaLangLong *)getLongWithNSString:(NSString *)string;
+ (JavaLangLong *)getLongWithNSString:(NSString *)string
                             withLong:(long long int)defaultValue;
+ (JavaLangLong *)getLongWithNSString:(NSString *)string
                     withJavaLangLong:(JavaLangLong *)defaultValue;
- (NSUInteger)hash;
- (int)intValue;
- (long long int)longLongValue;
+ (long long int)parseLongWithNSString:(NSString *)string;
+ (long long int)parseLongWithNSString:(NSString *)string
                               withInt:(int)radix;
+ (long long int)parseWithNSString:(NSString *)string
                           withInt:(int)offset
                           withInt:(int)radix
                       withBoolean:(BOOL)negative;
- (short int)shortValue;
+ (NSString *)toBinaryStringWithLong:(long long int)l;
+ (NSString *)toHexStringWithLong:(long long int)l;
+ (NSString *)toOctalStringWithLong:(long long int)l;
- (NSString *)description;
+ (NSString *)toStringWithLong:(long long int)l;
+ (NSString *)toStringWithLong:(long long int)l
                       withInt:(int)radix;
+ (JavaLangLong *)valueOfWithNSString:(NSString *)string;
+ (JavaLangLong *)valueOfWithNSString:(NSString *)string
                              withInt:(int)radix;
+ (long long int)highestOneBitWithLong:(long long int)lng;
+ (long long int)lowestOneBitWithLong:(long long int)lng;
+ (int)numberOfLeadingZerosWithLong:(long long int)lng;
+ (int)numberOfTrailingZerosWithLong:(long long int)lng;
+ (int)bitCountWithLong:(long long int)lng;
+ (long long int)rotateLeftWithLong:(long long int)lng
                            withInt:(int)distance;
+ (long long int)rotateRightWithLong:(long long int)lng
                             withInt:(int)distance;
+ (long long int)reverseBytesWithLong:(long long int)lng;
+ (long long int)reverseWithLong:(long long int)lng;
+ (int)signumWithLong:(long long int)lng;
+ (JavaLangLong *)valueOfWithLong:(long long int)lng;
- (void)copyAllFieldsTo:(JavaLangLong *)other;
@end

BOXED_INC_AND_DEC(Long, longLongValue, JavaLangLong)

@interface JavaLangLong_valueOfCache : NSObject {
}

+ (IOSObjectArray *)CACHE;
- (id)init;
@end

#endif // _JavaLangLong_H_
