//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: android/libcore/luni/src/main/java/java/util/concurrent/locks/ReentrantReadWriteLock.java
//
//  Created by tball on 11/23/13.
//

#ifndef _JavaUtilConcurrentLocksReentrantReadWriteLock_H_
#define _JavaUtilConcurrentLocksReentrantReadWriteLock_H_

@class JavaLangIllegalMonitorStateException;
@class JavaLangThread;
@class JavaUtilConcurrentLocksAbstractQueuedSynchronizer_ConditionObject;
@class JavaUtilConcurrentLocksReentrantReadWriteLock_ReadLock;
@class JavaUtilConcurrentLocksReentrantReadWriteLock_Sync;
@class JavaUtilConcurrentLocksReentrantReadWriteLock_WriteLock;
@class JavaUtilConcurrentTimeUnitEnum;
@protocol JavaUtilCollection;
@protocol JavaUtilConcurrentLocksCondition;

#import "JreEmulation.h"
#include "java/io/Serializable.h"
#include "java/lang/ThreadLocal.h"
#include "java/util/concurrent/locks/AbstractQueuedSynchronizer.h"
#include "java/util/concurrent/locks/Lock.h"
#include "java/util/concurrent/locks/ReadWriteLock.h"

#define JavaUtilConcurrentLocksReentrantReadWriteLock_serialVersionUID -6992448646407690164

@interface JavaUtilConcurrentLocksReentrantReadWriteLock : NSObject < JavaUtilConcurrentLocksReadWriteLock, JavaIoSerializable > {
 @public
  JavaUtilConcurrentLocksReentrantReadWriteLock_ReadLock *readerLock_;
  JavaUtilConcurrentLocksReentrantReadWriteLock_WriteLock *writerLock_;
  JavaUtilConcurrentLocksReentrantReadWriteLock_Sync *sync_;
}

- (id)init;
- (id)initWithBoolean:(BOOL)fair;
- (JavaUtilConcurrentLocksReentrantReadWriteLock_WriteLock *)writeLock;
- (JavaUtilConcurrentLocksReentrantReadWriteLock_ReadLock *)readLock;
- (BOOL)isFair;
- (JavaLangThread *)getOwner;
- (int)getReadLockCount;
- (BOOL)isWriteLocked;
- (BOOL)isWriteLockedByCurrentThread;
- (int)getWriteHoldCount;
- (int)getReadHoldCount;
- (id<JavaUtilCollection>)getQueuedWriterThreads;
- (id<JavaUtilCollection>)getQueuedReaderThreads;
- (BOOL)hasQueuedThreads;
- (BOOL)hasQueuedThreadWithJavaLangThread:(JavaLangThread *)thread;
- (int)getQueueLength;
- (id<JavaUtilCollection>)getQueuedThreads;
- (BOOL)hasWaitersWithJavaUtilConcurrentLocksCondition:(id<JavaUtilConcurrentLocksCondition>)condition;
- (int)getWaitQueueLengthWithJavaUtilConcurrentLocksCondition:(id<JavaUtilConcurrentLocksCondition>)condition;
- (id<JavaUtilCollection>)getWaitingThreadsWithJavaUtilConcurrentLocksCondition:(id<JavaUtilConcurrentLocksCondition>)condition;
- (NSString *)description;
- (void)copyAllFieldsTo:(JavaUtilConcurrentLocksReentrantReadWriteLock *)other;
@end

J2OBJC_FIELD_SETTER(JavaUtilConcurrentLocksReentrantReadWriteLock, readerLock_, JavaUtilConcurrentLocksReentrantReadWriteLock_ReadLock *)
J2OBJC_FIELD_SETTER(JavaUtilConcurrentLocksReentrantReadWriteLock, writerLock_, JavaUtilConcurrentLocksReentrantReadWriteLock_WriteLock *)
J2OBJC_FIELD_SETTER(JavaUtilConcurrentLocksReentrantReadWriteLock, sync_, JavaUtilConcurrentLocksReentrantReadWriteLock_Sync *)

@interface JavaUtilConcurrentLocksReentrantReadWriteLock_Sync_HoldCounter : NSObject {
 @public
  int count_;
  long long int tid_;
}

- (id)init;
- (void)copyAllFieldsTo:(JavaUtilConcurrentLocksReentrantReadWriteLock_Sync_HoldCounter *)other;
@end

@interface JavaUtilConcurrentLocksReentrantReadWriteLock_Sync_ThreadLocalHoldCounter : JavaLangThreadLocal {
}

- (JavaUtilConcurrentLocksReentrantReadWriteLock_Sync_HoldCounter *)initialValue OBJC_METHOD_FAMILY_NONE;
- (id)init;
@end

#define JavaUtilConcurrentLocksReentrantReadWriteLock_Sync_EXCLUSIVE_MASK 65535
#define JavaUtilConcurrentLocksReentrantReadWriteLock_Sync_MAX_COUNT 65535
#define JavaUtilConcurrentLocksReentrantReadWriteLock_Sync_SHARED_SHIFT 16
#define JavaUtilConcurrentLocksReentrantReadWriteLock_Sync_SHARED_UNIT 65536
#define JavaUtilConcurrentLocksReentrantReadWriteLock_Sync_serialVersionUID 6317671515068378041

@interface JavaUtilConcurrentLocksReentrantReadWriteLock_Sync : JavaUtilConcurrentLocksAbstractQueuedSynchronizer {
 @public
  JavaUtilConcurrentLocksReentrantReadWriteLock_Sync_ThreadLocalHoldCounter *readHolds_;
  JavaUtilConcurrentLocksReentrantReadWriteLock_Sync_HoldCounter *cachedHoldCounter_;
  JavaLangThread *firstReader_;
  int firstReaderHoldCount_;
}

+ (int)SHARED_SHIFT;
+ (int)SHARED_UNIT;
+ (int)MAX_COUNT;
+ (int)EXCLUSIVE_MASK;
+ (int)sharedCountWithInt:(int)c;
+ (int)exclusiveCountWithInt:(int)c;
- (id)init;
- (BOOL)readerShouldBlock;
- (BOOL)writerShouldBlock;
- (BOOL)tryReleaseWithInt:(int)releases;
- (BOOL)tryAcquireWithInt:(int)acquires;
- (BOOL)tryReleaseSharedWithInt:(int)unused;
- (JavaLangIllegalMonitorStateException *)unmatchedUnlockException;
- (int)tryAcquireSharedWithInt:(int)unused;
- (int)fullTryAcquireSharedWithJavaLangThread:(JavaLangThread *)current;
- (BOOL)tryWriteLock;
- (BOOL)tryReadLock;
- (BOOL)isHeldExclusively;
- (JavaUtilConcurrentLocksAbstractQueuedSynchronizer_ConditionObject *)newCondition OBJC_METHOD_FAMILY_NONE;
- (JavaLangThread *)getOwner;
- (int)getReadLockCount;
- (BOOL)isWriteLocked;
- (int)getWriteHoldCount;
- (int)getReadHoldCount;
- (int)getCount;
- (void)copyAllFieldsTo:(JavaUtilConcurrentLocksReentrantReadWriteLock_Sync *)other;
@end

J2OBJC_FIELD_SETTER(JavaUtilConcurrentLocksReentrantReadWriteLock_Sync, readHolds_, JavaUtilConcurrentLocksReentrantReadWriteLock_Sync_ThreadLocalHoldCounter *)
J2OBJC_FIELD_SETTER(JavaUtilConcurrentLocksReentrantReadWriteLock_Sync, cachedHoldCounter_, JavaUtilConcurrentLocksReentrantReadWriteLock_Sync_HoldCounter *)
J2OBJC_FIELD_SETTER(JavaUtilConcurrentLocksReentrantReadWriteLock_Sync, firstReader_, JavaLangThread *)

#define JavaUtilConcurrentLocksReentrantReadWriteLock_NonfairSync_serialVersionUID -8159625535654395037

@interface JavaUtilConcurrentLocksReentrantReadWriteLock_NonfairSync : JavaUtilConcurrentLocksReentrantReadWriteLock_Sync {
}

- (BOOL)writerShouldBlock;
- (BOOL)readerShouldBlock;
- (id)init;
@end

#define JavaUtilConcurrentLocksReentrantReadWriteLock_FairSync_serialVersionUID -2274990926593161451

@interface JavaUtilConcurrentLocksReentrantReadWriteLock_FairSync : JavaUtilConcurrentLocksReentrantReadWriteLock_Sync {
}

- (BOOL)writerShouldBlock;
- (BOOL)readerShouldBlock;
- (id)init;
@end

#define JavaUtilConcurrentLocksReentrantReadWriteLock_ReadLock_serialVersionUID -5992448646407690164

@interface JavaUtilConcurrentLocksReentrantReadWriteLock_ReadLock : NSObject < JavaUtilConcurrentLocksLock, JavaIoSerializable > {
 @public
  JavaUtilConcurrentLocksReentrantReadWriteLock_Sync *sync_;
}

- (id)initWithJavaUtilConcurrentLocksReentrantReadWriteLock:(JavaUtilConcurrentLocksReentrantReadWriteLock *)lock;
- (void)lock;
- (void)lockInterruptibly;
- (BOOL)tryLock;
- (BOOL)tryLockWithLong:(long long int)timeout
withJavaUtilConcurrentTimeUnitEnum:(JavaUtilConcurrentTimeUnitEnum *)unit;
- (void)unlock;
- (id<JavaUtilConcurrentLocksCondition>)newCondition OBJC_METHOD_FAMILY_NONE;
- (NSString *)description;
- (void)copyAllFieldsTo:(JavaUtilConcurrentLocksReentrantReadWriteLock_ReadLock *)other;
@end

J2OBJC_FIELD_SETTER(JavaUtilConcurrentLocksReentrantReadWriteLock_ReadLock, sync_, JavaUtilConcurrentLocksReentrantReadWriteLock_Sync *)

#define JavaUtilConcurrentLocksReentrantReadWriteLock_WriteLock_serialVersionUID -4992448646407690164

@interface JavaUtilConcurrentLocksReentrantReadWriteLock_WriteLock : NSObject < JavaUtilConcurrentLocksLock, JavaIoSerializable > {
 @public
  JavaUtilConcurrentLocksReentrantReadWriteLock_Sync *sync_;
}

- (id)initWithJavaUtilConcurrentLocksReentrantReadWriteLock:(JavaUtilConcurrentLocksReentrantReadWriteLock *)lock;
- (void)lock;
- (void)lockInterruptibly;
- (BOOL)tryLock;
- (BOOL)tryLockWithLong:(long long int)timeout
withJavaUtilConcurrentTimeUnitEnum:(JavaUtilConcurrentTimeUnitEnum *)unit;
- (void)unlock;
- (id<JavaUtilConcurrentLocksCondition>)newCondition OBJC_METHOD_FAMILY_NONE;
- (NSString *)description;
- (BOOL)isHeldByCurrentThread;
- (int)getHoldCount;
- (void)copyAllFieldsTo:(JavaUtilConcurrentLocksReentrantReadWriteLock_WriteLock *)other;
@end

J2OBJC_FIELD_SETTER(JavaUtilConcurrentLocksReentrantReadWriteLock_WriteLock, sync_, JavaUtilConcurrentLocksReentrantReadWriteLock_Sync *)

#endif // _JavaUtilConcurrentLocksReentrantReadWriteLock_H_