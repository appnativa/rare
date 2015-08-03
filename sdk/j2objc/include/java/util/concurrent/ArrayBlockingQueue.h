//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: android/libcore/luni/src/main/java/java/util/concurrent/ArrayBlockingQueue.java
//
//  Created by tball on 11/23/13.
//

#ifndef _JavaUtilConcurrentArrayBlockingQueue_H_
#define _JavaUtilConcurrentArrayBlockingQueue_H_

@class IOSObjectArray;
@class JavaUtilConcurrentArrayBlockingQueue_Itr;
@class JavaUtilConcurrentArrayBlockingQueue_Itrs;
@class JavaUtilConcurrentArrayBlockingQueue_Itrs_Node;
@class JavaUtilConcurrentLocksReentrantLock;
@class JavaUtilConcurrentTimeUnitEnum;
@protocol JavaUtilCollection;
@protocol JavaUtilConcurrentLocksCondition;

#import "JreEmulation.h"
#include "java/io/Serializable.h"
#include "java/lang/ref/WeakReference.h"
#include "java/util/AbstractQueue.h"
#include "java/util/Iterator.h"
#include "java/util/concurrent/BlockingQueue.h"

#define JavaUtilConcurrentArrayBlockingQueue_serialVersionUID -817911632652898426

@interface JavaUtilConcurrentArrayBlockingQueue : JavaUtilAbstractQueue < JavaUtilConcurrentBlockingQueue, JavaIoSerializable > {
 @public
  IOSObjectArray *items_;
  int takeIndex_;
  int putIndex_;
  int count_;
  JavaUtilConcurrentLocksReentrantLock *lock_;
  id<JavaUtilConcurrentLocksCondition> notEmpty_;
  id<JavaUtilConcurrentLocksCondition> notFull_;
  JavaUtilConcurrentArrayBlockingQueue_Itrs *itrs_;
}

- (int)incWithInt:(int)i;
- (int)decWithInt:(int)i;
- (id)itemAtWithInt:(int)i;
+ (void)checkNotNullWithId:(id)v;
- (void)enqueueWithId:(id)x;
- (id)dequeue;
- (void)removeAtWithInt:(int)removeIndex;
- (id)initWithInt:(int)capacity;
- (id)initWithInt:(int)capacity
      withBoolean:(BOOL)fair;
- (id)initWithInt:(int)capacity
      withBoolean:(BOOL)fair
withJavaUtilCollection:(id<JavaUtilCollection>)c;
- (BOOL)addWithId:(id)e;
- (BOOL)offerWithId:(id)e;
- (void)putWithId:(id)e;
- (BOOL)offerWithId:(id)e
           withLong:(long long int)timeout
withJavaUtilConcurrentTimeUnitEnum:(JavaUtilConcurrentTimeUnitEnum *)unit;
- (id)poll;
- (id)take;
- (id)pollWithLong:(long long int)timeout
withJavaUtilConcurrentTimeUnitEnum:(JavaUtilConcurrentTimeUnitEnum *)unit;
- (id)peek;
- (int)size;
- (int)remainingCapacity;
- (BOOL)removeWithId:(id)o;
- (BOOL)containsWithId:(id)o;
- (IOSObjectArray *)toArray;
- (IOSObjectArray *)toArrayWithNSObjectArray:(IOSObjectArray *)a;
- (NSString *)description;
- (void)clear;
- (int)drainToWithJavaUtilCollection:(id<JavaUtilCollection>)c;
- (int)drainToWithJavaUtilCollection:(id<JavaUtilCollection>)c
                             withInt:(int)maxElements;
- (id<JavaUtilIterator>)iterator;
- (void)copyAllFieldsTo:(JavaUtilConcurrentArrayBlockingQueue *)other;
@end

J2OBJC_FIELD_SETTER(JavaUtilConcurrentArrayBlockingQueue, items_, IOSObjectArray *)
J2OBJC_FIELD_SETTER(JavaUtilConcurrentArrayBlockingQueue, lock_, JavaUtilConcurrentLocksReentrantLock *)
J2OBJC_FIELD_SETTER(JavaUtilConcurrentArrayBlockingQueue, notEmpty_, id<JavaUtilConcurrentLocksCondition>)
J2OBJC_FIELD_SETTER(JavaUtilConcurrentArrayBlockingQueue, notFull_, id<JavaUtilConcurrentLocksCondition>)
J2OBJC_FIELD_SETTER(JavaUtilConcurrentArrayBlockingQueue, itrs_, JavaUtilConcurrentArrayBlockingQueue_Itrs *)

#define JavaUtilConcurrentArrayBlockingQueue_Itrs_LONG_SWEEP_PROBES 16
#define JavaUtilConcurrentArrayBlockingQueue_Itrs_SHORT_SWEEP_PROBES 4

@interface JavaUtilConcurrentArrayBlockingQueue_Itrs : NSObject {
 @public
  __weak JavaUtilConcurrentArrayBlockingQueue *this$0_;
  int cycles_;
  JavaUtilConcurrentArrayBlockingQueue_Itrs_Node *head_;
  JavaUtilConcurrentArrayBlockingQueue_Itrs_Node *sweeper_;
}

- (id)initWithJavaUtilConcurrentArrayBlockingQueue:(JavaUtilConcurrentArrayBlockingQueue *)outer$
      withJavaUtilConcurrentArrayBlockingQueue_Itr:(JavaUtilConcurrentArrayBlockingQueue_Itr *)initial;
- (void)doSomeSweepingWithBoolean:(BOOL)tryHarder;
- (void)register__WithJavaUtilConcurrentArrayBlockingQueue_Itr:(JavaUtilConcurrentArrayBlockingQueue_Itr *)itr;
- (void)takeIndexWrapped;
- (void)removedAtWithInt:(int)removedIndex;
- (void)queueIsEmpty;
- (void)elementDequeued;
- (void)copyAllFieldsTo:(JavaUtilConcurrentArrayBlockingQueue_Itrs *)other;
@end

J2OBJC_FIELD_SETTER(JavaUtilConcurrentArrayBlockingQueue_Itrs, head_, JavaUtilConcurrentArrayBlockingQueue_Itrs_Node *)
J2OBJC_FIELD_SETTER(JavaUtilConcurrentArrayBlockingQueue_Itrs, sweeper_, JavaUtilConcurrentArrayBlockingQueue_Itrs_Node *)

@interface JavaUtilConcurrentArrayBlockingQueue_Itrs_Node : JavaLangRefWeakReference {
 @public
  JavaUtilConcurrentArrayBlockingQueue_Itrs_Node *next_;
}

- (id)initWithJavaUtilConcurrentArrayBlockingQueue_Itrs:(JavaUtilConcurrentArrayBlockingQueue_Itrs *)outer$
           withJavaUtilConcurrentArrayBlockingQueue_Itr:(JavaUtilConcurrentArrayBlockingQueue_Itr *)iterator
     withJavaUtilConcurrentArrayBlockingQueue_Itrs_Node:(JavaUtilConcurrentArrayBlockingQueue_Itrs_Node *)next;
- (void)copyAllFieldsTo:(JavaUtilConcurrentArrayBlockingQueue_Itrs_Node *)other;
@end

J2OBJC_FIELD_SETTER(JavaUtilConcurrentArrayBlockingQueue_Itrs_Node, next_, JavaUtilConcurrentArrayBlockingQueue_Itrs_Node *)

#define JavaUtilConcurrentArrayBlockingQueue_Itr_DETACHED -3
#define JavaUtilConcurrentArrayBlockingQueue_Itr_NONE -1
#define JavaUtilConcurrentArrayBlockingQueue_Itr_REMOVED -2

@interface JavaUtilConcurrentArrayBlockingQueue_Itr : NSObject < JavaUtilIterator > {
 @public
  JavaUtilConcurrentArrayBlockingQueue *this$0_;
  int cursor_;
  id nextItem_;
  int nextIndex_;
  id lastItem_;
  int lastRet_;
  int prevTakeIndex_;
  int prevCycles_;
}

- (id)initWithJavaUtilConcurrentArrayBlockingQueue:(JavaUtilConcurrentArrayBlockingQueue *)outer$;
- (BOOL)isDetached;
- (int)incCursorWithInt:(int)index;
- (BOOL)invalidatedWithInt:(int)index
                   withInt:(int)prevTakeIndex
                  withLong:(long long int)dequeues
                   withInt:(int)length;
- (void)incorporateDequeues;
- (void)detach;
- (BOOL)hasNext;
- (void)noNext;
- (id)next;
- (void)remove;
- (void)shutdown;
- (int)distanceWithInt:(int)index
               withInt:(int)prevTakeIndex
               withInt:(int)length;
- (BOOL)removedAtWithInt:(int)removedIndex;
- (BOOL)takeIndexWrapped;
- (void)copyAllFieldsTo:(JavaUtilConcurrentArrayBlockingQueue_Itr *)other;
@end

J2OBJC_FIELD_SETTER(JavaUtilConcurrentArrayBlockingQueue_Itr, this$0_, JavaUtilConcurrentArrayBlockingQueue *)
J2OBJC_FIELD_SETTER(JavaUtilConcurrentArrayBlockingQueue_Itr, nextItem_, id)
J2OBJC_FIELD_SETTER(JavaUtilConcurrentArrayBlockingQueue_Itr, lastItem_, id)

#endif // _JavaUtilConcurrentArrayBlockingQueue_H_