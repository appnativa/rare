//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: android/libcore/luni/src/main/java/java/util/concurrent/locks/ReentrantLock.java
//
//  Created by tball on 11/23/13.
//

#ifndef _JavaUtilConcurrentLocksReentrantLock_H_
#define _JavaUtilConcurrentLocksReentrantLock_H_

@class JavaLangThread;
@class JavaUtilConcurrentLocksAbstractQueuedSynchronizer_ConditionObject;
@class JavaUtilConcurrentLocksReentrantLock_Sync;
@class JavaUtilConcurrentTimeUnitEnum;
@protocol JavaUtilCollection;
@protocol JavaUtilConcurrentLocksCondition;

#import "JreEmulation.h"
#include "java/io/Serializable.h"
#include "java/util/concurrent/locks/AbstractQueuedSynchronizer.h"
#include "java/util/concurrent/locks/Lock.h"

#define JavaUtilConcurrentLocksReentrantLock_serialVersionUID 7373984872572414699

@interface JavaUtilConcurrentLocksReentrantLock : NSObject < JavaUtilConcurrentLocksLock, JavaIoSerializable > {
 @public
  JavaUtilConcurrentLocksReentrantLock_Sync *sync_;
}

- (id)init;
- (id)initWithBoolean:(BOOL)fair;
- (void)lock;
- (void)lockInterruptibly;
- (BOOL)tryLock;
- (BOOL)tryLockWithLong:(long long int)timeout
withJavaUtilConcurrentTimeUnitEnum:(JavaUtilConcurrentTimeUnitEnum *)unit;
- (void)unlock;
- (id<JavaUtilConcurrentLocksCondition>)newCondition OBJC_METHOD_FAMILY_NONE;
- (int)getHoldCount;
- (BOOL)isHeldByCurrentThread;
- (BOOL)isLocked;
- (BOOL)isFair;
- (JavaLangThread *)getOwner;
- (BOOL)hasQueuedThreads;
- (BOOL)hasQueuedThreadWithJavaLangThread:(JavaLangThread *)thread;
- (int)getQueueLength;
- (id<JavaUtilCollection>)getQueuedThreads;
- (BOOL)hasWaitersWithJavaUtilConcurrentLocksCondition:(id<JavaUtilConcurrentLocksCondition>)condition;
- (int)getWaitQueueLengthWithJavaUtilConcurrentLocksCondition:(id<JavaUtilConcurrentLocksCondition>)condition;
- (id<JavaUtilCollection>)getWaitingThreadsWithJavaUtilConcurrentLocksCondition:(id<JavaUtilConcurrentLocksCondition>)condition;
- (NSString *)description;
- (void)copyAllFieldsTo:(JavaUtilConcurrentLocksReentrantLock *)other;
@end

J2OBJC_FIELD_SETTER(JavaUtilConcurrentLocksReentrantLock, sync_, JavaUtilConcurrentLocksReentrantLock_Sync *)

#define JavaUtilConcurrentLocksReentrantLock_Sync_serialVersionUID -5179523762034025860

@interface JavaUtilConcurrentLocksReentrantLock_Sync : JavaUtilConcurrentLocksAbstractQueuedSynchronizer {
}

- (void)lock;
- (BOOL)nonfairTryAcquireWithInt:(int)acquires;
- (BOOL)tryReleaseWithInt:(int)releases;
- (BOOL)isHeldExclusively;
- (JavaUtilConcurrentLocksAbstractQueuedSynchronizer_ConditionObject *)newCondition OBJC_METHOD_FAMILY_NONE;
- (JavaLangThread *)getOwner;
- (int)getHoldCount;
- (BOOL)isLocked;
- (id)init;
@end

#define JavaUtilConcurrentLocksReentrantLock_NonfairSync_serialVersionUID 7316153563782823691

@interface JavaUtilConcurrentLocksReentrantLock_NonfairSync : JavaUtilConcurrentLocksReentrantLock_Sync {
}

- (void)lock;
- (BOOL)tryAcquireWithInt:(int)acquires;
- (id)init;
@end

#define JavaUtilConcurrentLocksReentrantLock_FairSync_serialVersionUID -3000897897090466540

@interface JavaUtilConcurrentLocksReentrantLock_FairSync : JavaUtilConcurrentLocksReentrantLock_Sync {
}

- (void)lock;
- (BOOL)tryAcquireWithInt:(int)acquires;
- (id)init;
@end

#endif // _JavaUtilConcurrentLocksReentrantLock_H_
