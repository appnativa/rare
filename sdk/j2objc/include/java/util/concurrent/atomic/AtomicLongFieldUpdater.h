//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: android/libcore/luni/src/main/java/java/util/concurrent/atomic/AtomicLongFieldUpdater.java
//
//  Created by tball on 11/23/13.
//

#ifndef _JavaUtilConcurrentAtomicAtomicLongFieldUpdater_H_
#define _JavaUtilConcurrentAtomicAtomicLongFieldUpdater_H_

@class IOSClass;
@class SunMiscUnsafe;

#import "JreEmulation.h"

@interface JavaUtilConcurrentAtomicAtomicLongFieldUpdater : NSObject {
}

+ (JavaUtilConcurrentAtomicAtomicLongFieldUpdater *)newUpdaterWithIOSClass:(IOSClass *)tclass
                                                              withNSString:(NSString *)fieldName OBJC_METHOD_FAMILY_NONE;
- (id)init;
- (BOOL)compareAndSetWithId:(id)obj
                   withLong:(long long int)expect
                   withLong:(long long int)update;
- (BOOL)weakCompareAndSetWithId:(id)obj
                       withLong:(long long int)expect
                       withLong:(long long int)update;
- (void)setWithId:(id)obj
         withLong:(long long int)newValue;
- (void)lazySetWithId:(id)obj
             withLong:(long long int)newValue;
- (long long int)getWithId:(id)obj;
- (long long int)getAndSetWithId:(id)obj
                        withLong:(long long int)newValue;
- (long long int)getAndIncrementWithId:(id)obj;
- (long long int)getAndDecrementWithId:(id)obj;
- (long long int)getAndAddWithId:(id)obj
                        withLong:(long long int)delta;
- (long long int)incrementAndGetWithId:(id)obj;
- (long long int)decrementAndGetWithId:(id)obj;
- (long long int)addAndGetWithId:(id)obj
                        withLong:(long long int)delta;
@end

@interface JavaUtilConcurrentAtomicAtomicLongFieldUpdater_CASUpdater : JavaUtilConcurrentAtomicAtomicLongFieldUpdater {
 @public
  long long int offset_;
  IOSClass *tclass_;
  IOSClass *cclass_;
}

+ (SunMiscUnsafe *)unsafe;
- (id)initWithIOSClass:(IOSClass *)tclass
          withNSString:(NSString *)fieldName;
- (void)fullCheckWithId:(id)obj;
- (BOOL)compareAndSetWithId:(id)obj
                   withLong:(long long int)expect
                   withLong:(long long int)update;
- (BOOL)weakCompareAndSetWithId:(id)obj
                       withLong:(long long int)expect
                       withLong:(long long int)update;
- (void)setWithId:(id)obj
         withLong:(long long int)newValue;
- (void)lazySetWithId:(id)obj
             withLong:(long long int)newValue;
- (long long int)getWithId:(id)obj;
- (void)ensureProtectedAccessWithId:(id)obj;
- (void)copyAllFieldsTo:(JavaUtilConcurrentAtomicAtomicLongFieldUpdater_CASUpdater *)other;
@end

J2OBJC_FIELD_SETTER(JavaUtilConcurrentAtomicAtomicLongFieldUpdater_CASUpdater, tclass_, IOSClass *)
J2OBJC_FIELD_SETTER(JavaUtilConcurrentAtomicAtomicLongFieldUpdater_CASUpdater, cclass_, IOSClass *)

@interface JavaUtilConcurrentAtomicAtomicLongFieldUpdater_LockedUpdater : JavaUtilConcurrentAtomicAtomicLongFieldUpdater {
 @public
  long long int offset_;
  IOSClass *tclass_;
  IOSClass *cclass_;
}

+ (SunMiscUnsafe *)unsafe;
- (id)initWithIOSClass:(IOSClass *)tclass
          withNSString:(NSString *)fieldName;
- (void)fullCheckWithId:(id)obj;
- (BOOL)compareAndSetWithId:(id)obj
                   withLong:(long long int)expect
                   withLong:(long long int)update;
- (BOOL)weakCompareAndSetWithId:(id)obj
                       withLong:(long long int)expect
                       withLong:(long long int)update;
- (void)setWithId:(id)obj
         withLong:(long long int)newValue;
- (void)lazySetWithId:(id)obj
             withLong:(long long int)newValue;
- (long long int)getWithId:(id)obj;
- (void)ensureProtectedAccessWithId:(id)obj;
- (void)copyAllFieldsTo:(JavaUtilConcurrentAtomicAtomicLongFieldUpdater_LockedUpdater *)other;
@end

J2OBJC_FIELD_SETTER(JavaUtilConcurrentAtomicAtomicLongFieldUpdater_LockedUpdater, tclass_, IOSClass *)
J2OBJC_FIELD_SETTER(JavaUtilConcurrentAtomicAtomicLongFieldUpdater_LockedUpdater, cclass_, IOSClass *)

#endif // _JavaUtilConcurrentAtomicAtomicLongFieldUpdater_H_