//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: android/libcore/luni/src/main/java/java/util/concurrent/Semaphore.java
//
//  Created by tball on 11/23/13.
//

#ifndef _JavaUtilConcurrentSemaphore_H_
#define _JavaUtilConcurrentSemaphore_H_

@class JavaUtilConcurrentSemaphore_Sync;
@class JavaUtilConcurrentTimeUnitEnum;
@protocol JavaUtilCollection;

#import "JreEmulation.h"
#include "java/io/Serializable.h"
#include "java/util/concurrent/locks/AbstractQueuedSynchronizer.h"

#define JavaUtilConcurrentSemaphore_serialVersionUID -3222578661600680210

@interface JavaUtilConcurrentSemaphore : NSObject < JavaIoSerializable > {
 @public
  JavaUtilConcurrentSemaphore_Sync *sync_;
}

- (id)initWithInt:(int)permits;
- (id)initWithInt:(int)permits
      withBoolean:(BOOL)fair;
- (void)acquire;
- (void)acquireUninterruptibly;
- (BOOL)tryAcquire;
- (BOOL)tryAcquireWithLong:(long long int)timeout
withJavaUtilConcurrentTimeUnitEnum:(JavaUtilConcurrentTimeUnitEnum *)unit;
- (void)release__;
- (void)acquireWithInt:(int)permits;
- (void)acquireUninterruptiblyWithInt:(int)permits;
- (BOOL)tryAcquireWithInt:(int)permits;
- (BOOL)tryAcquireWithInt:(int)permits
                 withLong:(long long int)timeout
withJavaUtilConcurrentTimeUnitEnum:(JavaUtilConcurrentTimeUnitEnum *)unit;
- (void)release__WithInt:(int)permits;
- (int)availablePermits;
- (int)drainPermits;
- (void)reducePermitsWithInt:(int)reduction;
- (BOOL)isFair;
- (BOOL)hasQueuedThreads;
- (int)getQueueLength;
- (id<JavaUtilCollection>)getQueuedThreads;
- (NSString *)description;
- (void)copyAllFieldsTo:(JavaUtilConcurrentSemaphore *)other;
@end

J2OBJC_FIELD_SETTER(JavaUtilConcurrentSemaphore, sync_, JavaUtilConcurrentSemaphore_Sync *)

#define JavaUtilConcurrentSemaphore_Sync_serialVersionUID 1192457210091910933

@interface JavaUtilConcurrentSemaphore_Sync : JavaUtilConcurrentLocksAbstractQueuedSynchronizer {
}

- (id)initWithInt:(int)permits;
- (int)getPermits;
- (int)nonfairTryAcquireSharedWithInt:(int)acquires;
- (BOOL)tryReleaseSharedWithInt:(int)releases;
- (void)reducePermitsWithInt:(int)reductions;
- (int)drainPermits;
@end

#define JavaUtilConcurrentSemaphore_NonfairSync_serialVersionUID -2694183684443567898

@interface JavaUtilConcurrentSemaphore_NonfairSync : JavaUtilConcurrentSemaphore_Sync {
}

- (id)initWithInt:(int)permits;
- (int)tryAcquireSharedWithInt:(int)acquires;
@end

#define JavaUtilConcurrentSemaphore_FairSync_serialVersionUID 2014338818796000944

@interface JavaUtilConcurrentSemaphore_FairSync : JavaUtilConcurrentSemaphore_Sync {
}

- (id)initWithInt:(int)permits;
- (int)tryAcquireSharedWithInt:(int)acquires;
@end

#endif // _JavaUtilConcurrentSemaphore_H_