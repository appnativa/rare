//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: android/libcore/luni/src/main/java/java/util/concurrent/locks/AbstractQueuedSynchronizer.java
//
//  Created by tball on 11/23/13.
//

#ifndef _JavaUtilConcurrentLocksAbstractQueuedSynchronizer_H_
#define _JavaUtilConcurrentLocksAbstractQueuedSynchronizer_H_

@class JavaLangThread;
@class JavaUtilConcurrentLocksAbstractQueuedSynchronizer_ConditionObject;
@class JavaUtilConcurrentLocksAbstractQueuedSynchronizer_Node;
@class JavaUtilConcurrentTimeUnitEnum;
@class JavaUtilDate;
@class SunMiscUnsafe;
@protocol JavaUtilCollection;

#import "JreEmulation.h"
#include "java/io/Serializable.h"
#include "java/util/concurrent/locks/AbstractOwnableSynchronizer.h"
#include "java/util/concurrent/locks/Condition.h"

#define JavaUtilConcurrentLocksAbstractQueuedSynchronizer_serialVersionUID 7373984972572414691
#define JavaUtilConcurrentLocksAbstractQueuedSynchronizer_spinForTimeoutThreshold 1000

@interface JavaUtilConcurrentLocksAbstractQueuedSynchronizer : JavaUtilConcurrentLocksAbstractOwnableSynchronizer < JavaIoSerializable > {
 @public
  JavaUtilConcurrentLocksAbstractQueuedSynchronizer_Node *head_;
  JavaUtilConcurrentLocksAbstractQueuedSynchronizer_Node *tail_;
  int state_;
}

+ (long long int)spinForTimeoutThreshold;
+ (SunMiscUnsafe *)unsafe;
+ (long long int)stateOffset;
+ (long long int)headOffset;
+ (long long int)tailOffset;
+ (long long int)waitStatusOffset;
+ (long long int)nextOffset;
- (id)init;
- (int)getState;
- (void)setStateWithInt:(int)newState;
- (BOOL)compareAndSetStateWithInt:(int)expect
                          withInt:(int)update;
- (JavaUtilConcurrentLocksAbstractQueuedSynchronizer_Node *)enqWithJavaUtilConcurrentLocksAbstractQueuedSynchronizer_Node:(JavaUtilConcurrentLocksAbstractQueuedSynchronizer_Node *)node;
- (JavaUtilConcurrentLocksAbstractQueuedSynchronizer_Node *)addWaiterWithJavaUtilConcurrentLocksAbstractQueuedSynchronizer_Node:(JavaUtilConcurrentLocksAbstractQueuedSynchronizer_Node *)mode;
- (void)setHeadWithJavaUtilConcurrentLocksAbstractQueuedSynchronizer_Node:(JavaUtilConcurrentLocksAbstractQueuedSynchronizer_Node *)node;
- (void)unparkSuccessorWithJavaUtilConcurrentLocksAbstractQueuedSynchronizer_Node:(JavaUtilConcurrentLocksAbstractQueuedSynchronizer_Node *)node;
- (void)doReleaseShared;
- (void)setHeadAndPropagateWithJavaUtilConcurrentLocksAbstractQueuedSynchronizer_Node:(JavaUtilConcurrentLocksAbstractQueuedSynchronizer_Node *)node
                                                                              withInt:(int)propagate;
- (void)cancelAcquireWithJavaUtilConcurrentLocksAbstractQueuedSynchronizer_Node:(JavaUtilConcurrentLocksAbstractQueuedSynchronizer_Node *)node;
+ (BOOL)shouldParkAfterFailedAcquireWithJavaUtilConcurrentLocksAbstractQueuedSynchronizer_Node:(JavaUtilConcurrentLocksAbstractQueuedSynchronizer_Node *)pred
                                    withJavaUtilConcurrentLocksAbstractQueuedSynchronizer_Node:(JavaUtilConcurrentLocksAbstractQueuedSynchronizer_Node *)node;
+ (void)selfInterrupt;
- (BOOL)parkAndCheckInterrupt;
- (BOOL)acquireQueuedWithJavaUtilConcurrentLocksAbstractQueuedSynchronizer_Node:(JavaUtilConcurrentLocksAbstractQueuedSynchronizer_Node *)node
                                                                        withInt:(int)arg;
- (void)doAcquireInterruptiblyWithInt:(int)arg;
- (BOOL)doAcquireNanosWithInt:(int)arg
                     withLong:(long long int)nanosTimeout;
- (void)doAcquireSharedWithInt:(int)arg;
- (void)doAcquireSharedInterruptiblyWithInt:(int)arg;
- (BOOL)doAcquireSharedNanosWithInt:(int)arg
                           withLong:(long long int)nanosTimeout;
- (BOOL)tryAcquireWithInt:(int)arg;
- (BOOL)tryReleaseWithInt:(int)arg;
- (int)tryAcquireSharedWithInt:(int)arg;
- (BOOL)tryReleaseSharedWithInt:(int)arg;
- (BOOL)isHeldExclusively;
- (void)acquireWithInt:(int)arg;
- (void)acquireInterruptiblyWithInt:(int)arg;
- (BOOL)tryAcquireNanosWithInt:(int)arg
                      withLong:(long long int)nanosTimeout;
- (BOOL)release__WithInt:(int)arg;
- (void)acquireSharedWithInt:(int)arg;
- (void)acquireSharedInterruptiblyWithInt:(int)arg;
- (BOOL)tryAcquireSharedNanosWithInt:(int)arg
                            withLong:(long long int)nanosTimeout;
- (BOOL)releaseSharedWithInt:(int)arg;
- (BOOL)hasQueuedThreads;
- (BOOL)hasContended;
- (JavaLangThread *)getFirstQueuedThread;
- (JavaLangThread *)fullGetFirstQueuedThread;
- (BOOL)isQueuedWithJavaLangThread:(JavaLangThread *)thread;
- (BOOL)apparentlyFirstQueuedIsExclusive;
- (BOOL)hasQueuedPredecessors;
- (int)getQueueLength;
- (id<JavaUtilCollection>)getQueuedThreads;
- (id<JavaUtilCollection>)getExclusiveQueuedThreads;
- (id<JavaUtilCollection>)getSharedQueuedThreads;
- (NSString *)description;
- (BOOL)isOnSyncQueueWithJavaUtilConcurrentLocksAbstractQueuedSynchronizer_Node:(JavaUtilConcurrentLocksAbstractQueuedSynchronizer_Node *)node;
- (BOOL)findNodeFromTailWithJavaUtilConcurrentLocksAbstractQueuedSynchronizer_Node:(JavaUtilConcurrentLocksAbstractQueuedSynchronizer_Node *)node;
- (BOOL)transferForSignalWithJavaUtilConcurrentLocksAbstractQueuedSynchronizer_Node:(JavaUtilConcurrentLocksAbstractQueuedSynchronizer_Node *)node;
- (BOOL)transferAfterCancelledWaitWithJavaUtilConcurrentLocksAbstractQueuedSynchronizer_Node:(JavaUtilConcurrentLocksAbstractQueuedSynchronizer_Node *)node;
- (int)fullyReleaseWithJavaUtilConcurrentLocksAbstractQueuedSynchronizer_Node:(JavaUtilConcurrentLocksAbstractQueuedSynchronizer_Node *)node;
- (BOOL)ownsWithJavaUtilConcurrentLocksAbstractQueuedSynchronizer_ConditionObject:(JavaUtilConcurrentLocksAbstractQueuedSynchronizer_ConditionObject *)condition;
- (BOOL)hasWaitersWithJavaUtilConcurrentLocksAbstractQueuedSynchronizer_ConditionObject:(JavaUtilConcurrentLocksAbstractQueuedSynchronizer_ConditionObject *)condition;
- (int)getWaitQueueLengthWithJavaUtilConcurrentLocksAbstractQueuedSynchronizer_ConditionObject:(JavaUtilConcurrentLocksAbstractQueuedSynchronizer_ConditionObject *)condition;
- (id<JavaUtilCollection>)getWaitingThreadsWithJavaUtilConcurrentLocksAbstractQueuedSynchronizer_ConditionObject:(JavaUtilConcurrentLocksAbstractQueuedSynchronizer_ConditionObject *)condition;
- (BOOL)compareAndSetHeadWithJavaUtilConcurrentLocksAbstractQueuedSynchronizer_Node:(JavaUtilConcurrentLocksAbstractQueuedSynchronizer_Node *)update;
- (BOOL)compareAndSetTailWithJavaUtilConcurrentLocksAbstractQueuedSynchronizer_Node:(JavaUtilConcurrentLocksAbstractQueuedSynchronizer_Node *)expect
                         withJavaUtilConcurrentLocksAbstractQueuedSynchronizer_Node:(JavaUtilConcurrentLocksAbstractQueuedSynchronizer_Node *)update;
+ (BOOL)compareAndSetWaitStatusWithJavaUtilConcurrentLocksAbstractQueuedSynchronizer_Node:(JavaUtilConcurrentLocksAbstractQueuedSynchronizer_Node *)node
                                                                                  withInt:(int)expect
                                                                                  withInt:(int)update;
+ (BOOL)compareAndSetNextWithJavaUtilConcurrentLocksAbstractQueuedSynchronizer_Node:(JavaUtilConcurrentLocksAbstractQueuedSynchronizer_Node *)node
                         withJavaUtilConcurrentLocksAbstractQueuedSynchronizer_Node:(JavaUtilConcurrentLocksAbstractQueuedSynchronizer_Node *)expect
                         withJavaUtilConcurrentLocksAbstractQueuedSynchronizer_Node:(JavaUtilConcurrentLocksAbstractQueuedSynchronizer_Node *)update;
- (void)copyAllFieldsTo:(JavaUtilConcurrentLocksAbstractQueuedSynchronizer *)other;
@end

J2OBJC_FIELD_SETTER(JavaUtilConcurrentLocksAbstractQueuedSynchronizer, head_, JavaUtilConcurrentLocksAbstractQueuedSynchronizer_Node *)
J2OBJC_FIELD_SETTER(JavaUtilConcurrentLocksAbstractQueuedSynchronizer, tail_, JavaUtilConcurrentLocksAbstractQueuedSynchronizer_Node *)

#define JavaUtilConcurrentLocksAbstractQueuedSynchronizer_Node_CANCELLED 1
#define JavaUtilConcurrentLocksAbstractQueuedSynchronizer_Node_CONDITION -2
#define JavaUtilConcurrentLocksAbstractQueuedSynchronizer_Node_PROPAGATE -3
#define JavaUtilConcurrentLocksAbstractQueuedSynchronizer_Node_SIGNAL -1

@interface JavaUtilConcurrentLocksAbstractQueuedSynchronizer_Node : NSObject {
 @public
  int waitStatus_;
  JavaUtilConcurrentLocksAbstractQueuedSynchronizer_Node *prev_;
  JavaUtilConcurrentLocksAbstractQueuedSynchronizer_Node *next_;
  JavaLangThread *thread_;
  JavaUtilConcurrentLocksAbstractQueuedSynchronizer_Node *nextWaiter_;
}

+ (JavaUtilConcurrentLocksAbstractQueuedSynchronizer_Node *)SHARED;
+ (JavaUtilConcurrentLocksAbstractQueuedSynchronizer_Node *)EXCLUSIVE;
+ (int)CANCELLED;
+ (int)SIGNAL;
+ (int)CONDITION;
+ (int)PROPAGATE;
- (BOOL)isShared;
- (JavaUtilConcurrentLocksAbstractQueuedSynchronizer_Node *)predecessor;
- (id)init;
- (id)initWithJavaLangThread:(JavaLangThread *)thread
withJavaUtilConcurrentLocksAbstractQueuedSynchronizer_Node:(JavaUtilConcurrentLocksAbstractQueuedSynchronizer_Node *)mode;
- (id)initWithJavaLangThread:(JavaLangThread *)thread
                     withInt:(int)waitStatus;
- (void)copyAllFieldsTo:(JavaUtilConcurrentLocksAbstractQueuedSynchronizer_Node *)other;
@end

J2OBJC_FIELD_SETTER(JavaUtilConcurrentLocksAbstractQueuedSynchronizer_Node, prev_, JavaUtilConcurrentLocksAbstractQueuedSynchronizer_Node *)
J2OBJC_FIELD_SETTER(JavaUtilConcurrentLocksAbstractQueuedSynchronizer_Node, next_, JavaUtilConcurrentLocksAbstractQueuedSynchronizer_Node *)
J2OBJC_FIELD_SETTER(JavaUtilConcurrentLocksAbstractQueuedSynchronizer_Node, thread_, JavaLangThread *)
J2OBJC_FIELD_SETTER(JavaUtilConcurrentLocksAbstractQueuedSynchronizer_Node, nextWaiter_, JavaUtilConcurrentLocksAbstractQueuedSynchronizer_Node *)

#define JavaUtilConcurrentLocksAbstractQueuedSynchronizer_ConditionObject_REINTERRUPT 1
#define JavaUtilConcurrentLocksAbstractQueuedSynchronizer_ConditionObject_THROW_IE -1
#define JavaUtilConcurrentLocksAbstractQueuedSynchronizer_ConditionObject_serialVersionUID 1173984872572414699

@interface JavaUtilConcurrentLocksAbstractQueuedSynchronizer_ConditionObject : NSObject < JavaUtilConcurrentLocksCondition, JavaIoSerializable > {
 @public
  JavaUtilConcurrentLocksAbstractQueuedSynchronizer *this$0_;
  JavaUtilConcurrentLocksAbstractQueuedSynchronizer_Node *firstWaiter_;
  JavaUtilConcurrentLocksAbstractQueuedSynchronizer_Node *lastWaiter_;
}

- (id)initWithJavaUtilConcurrentLocksAbstractQueuedSynchronizer:(JavaUtilConcurrentLocksAbstractQueuedSynchronizer *)outer$;
- (JavaUtilConcurrentLocksAbstractQueuedSynchronizer_Node *)addConditionWaiter;
- (void)doSignalWithJavaUtilConcurrentLocksAbstractQueuedSynchronizer_Node:(JavaUtilConcurrentLocksAbstractQueuedSynchronizer_Node *)first;
- (void)doSignalAllWithJavaUtilConcurrentLocksAbstractQueuedSynchronizer_Node:(JavaUtilConcurrentLocksAbstractQueuedSynchronizer_Node *)first;
- (void)unlinkCancelledWaiters;
- (void)signal;
- (void)signalAll;
- (void)awaitUninterruptibly;
- (int)checkInterruptWhileWaitingWithJavaUtilConcurrentLocksAbstractQueuedSynchronizer_Node:(JavaUtilConcurrentLocksAbstractQueuedSynchronizer_Node *)node;
- (void)reportInterruptAfterWaitWithInt:(int)interruptMode;
- (void)await;
- (long long int)awaitNanosWithLong:(long long int)nanosTimeout;
- (BOOL)awaitUntilWithJavaUtilDate:(JavaUtilDate *)deadline;
- (BOOL)awaitWithLong:(long long int)time
withJavaUtilConcurrentTimeUnitEnum:(JavaUtilConcurrentTimeUnitEnum *)unit;
- (BOOL)isOwnedByWithJavaUtilConcurrentLocksAbstractQueuedSynchronizer:(JavaUtilConcurrentLocksAbstractQueuedSynchronizer *)sync;
- (BOOL)hasWaiters;
- (int)getWaitQueueLength;
- (id<JavaUtilCollection>)getWaitingThreads;
- (void)copyAllFieldsTo:(JavaUtilConcurrentLocksAbstractQueuedSynchronizer_ConditionObject *)other;
@end

J2OBJC_FIELD_SETTER(JavaUtilConcurrentLocksAbstractQueuedSynchronizer_ConditionObject, this$0_, JavaUtilConcurrentLocksAbstractQueuedSynchronizer *)
J2OBJC_FIELD_SETTER(JavaUtilConcurrentLocksAbstractQueuedSynchronizer_ConditionObject, firstWaiter_, JavaUtilConcurrentLocksAbstractQueuedSynchronizer_Node *)
J2OBJC_FIELD_SETTER(JavaUtilConcurrentLocksAbstractQueuedSynchronizer_ConditionObject, lastWaiter_, JavaUtilConcurrentLocksAbstractQueuedSynchronizer_Node *)

#endif // _JavaUtilConcurrentLocksAbstractQueuedSynchronizer_H_