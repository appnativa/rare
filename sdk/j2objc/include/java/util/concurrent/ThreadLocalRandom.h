//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: android/libcore/luni/src/main/java/java/util/concurrent/ThreadLocalRandom.java
//
//  Created by tball on 11/23/13.
//

#ifndef _JavaUtilConcurrentThreadLocalRandom_H_
#define _JavaUtilConcurrentThreadLocalRandom_H_

#import "JreEmulation.h"
#include "java/lang/ThreadLocal.h"
#include "java/util/Random.h"

#define JavaUtilConcurrentThreadLocalRandom_addend 11
#define JavaUtilConcurrentThreadLocalRandom_mask 281474976710655
#define JavaUtilConcurrentThreadLocalRandom_multiplier 25214903917
#define JavaUtilConcurrentThreadLocalRandom_serialVersionUID -5851777807851030925

@interface JavaUtilConcurrentThreadLocalRandom : JavaUtilRandom {
 @public
  long long int rnd_;
  BOOL initialized_;
  long long int pad0_, pad1_, pad2_, pad3_, pad4_, pad5_, pad6_, pad7_;
}

+ (JavaLangThreadLocal *)localRandom;
- (id)init;
+ (JavaUtilConcurrentThreadLocalRandom *)current;
- (void)setSeedWithLong:(long long int)seed;
- (int)nextWithInt:(int)bits;
- (int)nextIntWithInt:(int)least
              withInt:(int)bound;
- (long long int)nextLongWithLong:(long long int)n;
- (long long int)nextLongWithLong:(long long int)least
                         withLong:(long long int)bound;
- (double)nextDoubleWithDouble:(double)n;
- (double)nextDoubleWithDouble:(double)least
                    withDouble:(double)bound;
- (void)copyAllFieldsTo:(JavaUtilConcurrentThreadLocalRandom *)other;
@end

@interface JavaUtilConcurrentThreadLocalRandom_$1 : JavaLangThreadLocal {
}

- (JavaUtilConcurrentThreadLocalRandom *)initialValue OBJC_METHOD_FAMILY_NONE;
- (id)init;
@end

#endif // _JavaUtilConcurrentThreadLocalRandom_H_
