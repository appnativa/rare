//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: android/libcore/luni/src/main/java/java/util/concurrent/LinkedBlockingDeque.java
//
//  Created by tball on 11/23/13.
//

#ifndef _JavaUtilConcurrentLinkedBlockingDeque_H_
#define _JavaUtilConcurrentLinkedBlockingDeque_H_

@class IOSObjectArray;
@class JavaUtilConcurrentLinkedBlockingDeque_Node;
@class JavaUtilConcurrentLocksReentrantLock;
@class JavaUtilConcurrentTimeUnitEnum;
@protocol JavaUtilCollection;
@protocol JavaUtilConcurrentLocksCondition;

#import "JreEmulation.h"
#include "java/io/Serializable.h"
#include "java/util/AbstractQueue.h"
#include "java/util/Iterator.h"
#include "java/util/concurrent/BlockingDeque.h"

#define JavaUtilConcurrentLinkedBlockingDeque_serialVersionUID -387911632671998426

@interface JavaUtilConcurrentLinkedBlockingDeque : JavaUtilAbstractQueue < JavaUtilConcurrentBlockingDeque, JavaIoSerializable > {
 @public
  JavaUtilConcurrentLinkedBlockingDeque_Node *first_;
  JavaUtilConcurrentLinkedBlockingDeque_Node *last_;
  int count_;
  int capacity_;
  JavaUtilConcurrentLocksReentrantLock *lock_;
  id<JavaUtilConcurrentLocksCondition> notEmpty_;
  id<JavaUtilConcurrentLocksCondition> notFull_;
}

- (id)init;
- (id)initWithInt:(int)capacity;
- (id)initWithJavaUtilCollection:(id<JavaUtilCollection>)c;
- (BOOL)linkFirstWithJavaUtilConcurrentLinkedBlockingDeque_Node:(JavaUtilConcurrentLinkedBlockingDeque_Node *)node;
- (BOOL)linkLastWithJavaUtilConcurrentLinkedBlockingDeque_Node:(JavaUtilConcurrentLinkedBlockingDeque_Node *)node;
- (id)unlinkFirst;
- (id)unlinkLast;
- (void)unlinkWithJavaUtilConcurrentLinkedBlockingDeque_Node:(JavaUtilConcurrentLinkedBlockingDeque_Node *)x;
- (void)addFirstWithId:(id)e;
- (void)addLastWithId:(id)e;
- (BOOL)offerFirstWithId:(id)e;
- (BOOL)offerLastWithId:(id)e;
- (void)putFirstWithId:(id)e;
- (void)putLastWithId:(id)e;
- (BOOL)offerFirstWithId:(id)e
                withLong:(long long int)timeout
withJavaUtilConcurrentTimeUnitEnum:(JavaUtilConcurrentTimeUnitEnum *)unit;
- (BOOL)offerLastWithId:(id)e
               withLong:(long long int)timeout
withJavaUtilConcurrentTimeUnitEnum:(JavaUtilConcurrentTimeUnitEnum *)unit;
- (id)removeFirst;
- (id)removeLast;
- (id)pollFirst;
- (id)pollLast;
- (id)takeFirst;
- (id)takeLast;
- (id)pollFirstWithLong:(long long int)timeout
withJavaUtilConcurrentTimeUnitEnum:(JavaUtilConcurrentTimeUnitEnum *)unit;
- (id)pollLastWithLong:(long long int)timeout
withJavaUtilConcurrentTimeUnitEnum:(JavaUtilConcurrentTimeUnitEnum *)unit;
- (id)getFirst;
- (id)getLast;
- (id)peekFirst;
- (id)peekLast;
- (BOOL)removeFirstOccurrenceWithId:(id)o;
- (BOOL)removeLastOccurrenceWithId:(id)o;
- (BOOL)addWithId:(id)e;
- (BOOL)offerWithId:(id)e;
- (void)putWithId:(id)e;
- (BOOL)offerWithId:(id)e
           withLong:(long long int)timeout
withJavaUtilConcurrentTimeUnitEnum:(JavaUtilConcurrentTimeUnitEnum *)unit;
- (id)remove;
- (id)poll;
- (id)take;
- (id)pollWithLong:(long long int)timeout
withJavaUtilConcurrentTimeUnitEnum:(JavaUtilConcurrentTimeUnitEnum *)unit;
- (id)element;
- (id)peek;
- (int)remainingCapacity;
- (int)drainToWithJavaUtilCollection:(id<JavaUtilCollection>)c;
- (int)drainToWithJavaUtilCollection:(id<JavaUtilCollection>)c
                             withInt:(int)maxElements;
- (void)pushWithId:(id)e;
- (id)pop;
- (BOOL)removeWithId:(id)o;
- (int)size;
- (BOOL)containsWithId:(id)o;
- (IOSObjectArray *)toArray;
- (IOSObjectArray *)toArrayWithNSObjectArray:(IOSObjectArray *)a;
- (NSString *)description;
- (void)clear;
- (id<JavaUtilIterator>)iterator;
- (id<JavaUtilIterator>)descendingIterator;
- (void)copyAllFieldsTo:(JavaUtilConcurrentLinkedBlockingDeque *)other;
@end

J2OBJC_FIELD_SETTER(JavaUtilConcurrentLinkedBlockingDeque, first_, JavaUtilConcurrentLinkedBlockingDeque_Node *)
J2OBJC_FIELD_SETTER(JavaUtilConcurrentLinkedBlockingDeque, last_, JavaUtilConcurrentLinkedBlockingDeque_Node *)
J2OBJC_FIELD_SETTER(JavaUtilConcurrentLinkedBlockingDeque, lock_, JavaUtilConcurrentLocksReentrantLock *)
J2OBJC_FIELD_SETTER(JavaUtilConcurrentLinkedBlockingDeque, notEmpty_, id<JavaUtilConcurrentLocksCondition>)
J2OBJC_FIELD_SETTER(JavaUtilConcurrentLinkedBlockingDeque, notFull_, id<JavaUtilConcurrentLocksCondition>)

@interface JavaUtilConcurrentLinkedBlockingDeque_Node : NSObject {
 @public
  id item_;
  JavaUtilConcurrentLinkedBlockingDeque_Node *prev_;
  JavaUtilConcurrentLinkedBlockingDeque_Node *next_;
}

- (id)initWithId:(id)x;
- (void)copyAllFieldsTo:(JavaUtilConcurrentLinkedBlockingDeque_Node *)other;
@end

J2OBJC_FIELD_SETTER(JavaUtilConcurrentLinkedBlockingDeque_Node, item_, id)
J2OBJC_FIELD_SETTER(JavaUtilConcurrentLinkedBlockingDeque_Node, prev_, JavaUtilConcurrentLinkedBlockingDeque_Node *)
J2OBJC_FIELD_SETTER(JavaUtilConcurrentLinkedBlockingDeque_Node, next_, JavaUtilConcurrentLinkedBlockingDeque_Node *)

@interface JavaUtilConcurrentLinkedBlockingDeque_AbstractItr : NSObject < JavaUtilIterator > {
 @public
  JavaUtilConcurrentLinkedBlockingDeque *this$0_;
  JavaUtilConcurrentLinkedBlockingDeque_Node *next__;
  id nextItem_;
  JavaUtilConcurrentLinkedBlockingDeque_Node *lastRet_;
}

- (JavaUtilConcurrentLinkedBlockingDeque_Node *)firstNode;
- (JavaUtilConcurrentLinkedBlockingDeque_Node *)nextNodeWithJavaUtilConcurrentLinkedBlockingDeque_Node:(JavaUtilConcurrentLinkedBlockingDeque_Node *)n;
- (id)initWithJavaUtilConcurrentLinkedBlockingDeque:(JavaUtilConcurrentLinkedBlockingDeque *)outer$;
- (JavaUtilConcurrentLinkedBlockingDeque_Node *)succWithJavaUtilConcurrentLinkedBlockingDeque_Node:(JavaUtilConcurrentLinkedBlockingDeque_Node *)n;
- (void)advance;
- (BOOL)hasNext;
- (id)next;
- (void)remove;
- (void)copyAllFieldsTo:(JavaUtilConcurrentLinkedBlockingDeque_AbstractItr *)other;
@end

J2OBJC_FIELD_SETTER(JavaUtilConcurrentLinkedBlockingDeque_AbstractItr, this$0_, JavaUtilConcurrentLinkedBlockingDeque *)
J2OBJC_FIELD_SETTER(JavaUtilConcurrentLinkedBlockingDeque_AbstractItr, next__, JavaUtilConcurrentLinkedBlockingDeque_Node *)
J2OBJC_FIELD_SETTER(JavaUtilConcurrentLinkedBlockingDeque_AbstractItr, nextItem_, id)
J2OBJC_FIELD_SETTER(JavaUtilConcurrentLinkedBlockingDeque_AbstractItr, lastRet_, JavaUtilConcurrentLinkedBlockingDeque_Node *)

@interface JavaUtilConcurrentLinkedBlockingDeque_Itr : JavaUtilConcurrentLinkedBlockingDeque_AbstractItr {
 @public
  JavaUtilConcurrentLinkedBlockingDeque *this$1_;
}

- (JavaUtilConcurrentLinkedBlockingDeque_Node *)firstNode;
- (JavaUtilConcurrentLinkedBlockingDeque_Node *)nextNodeWithJavaUtilConcurrentLinkedBlockingDeque_Node:(JavaUtilConcurrentLinkedBlockingDeque_Node *)n;
- (id)initWithJavaUtilConcurrentLinkedBlockingDeque:(JavaUtilConcurrentLinkedBlockingDeque *)outer$;
@end

J2OBJC_FIELD_SETTER(JavaUtilConcurrentLinkedBlockingDeque_Itr, this$1_, JavaUtilConcurrentLinkedBlockingDeque *)

@interface JavaUtilConcurrentLinkedBlockingDeque_DescendingItr : JavaUtilConcurrentLinkedBlockingDeque_AbstractItr {
 @public
  JavaUtilConcurrentLinkedBlockingDeque *this$1_;
}

- (JavaUtilConcurrentLinkedBlockingDeque_Node *)firstNode;
- (JavaUtilConcurrentLinkedBlockingDeque_Node *)nextNodeWithJavaUtilConcurrentLinkedBlockingDeque_Node:(JavaUtilConcurrentLinkedBlockingDeque_Node *)n;
- (id)initWithJavaUtilConcurrentLinkedBlockingDeque:(JavaUtilConcurrentLinkedBlockingDeque *)outer$;
@end

J2OBJC_FIELD_SETTER(JavaUtilConcurrentLinkedBlockingDeque_DescendingItr, this$1_, JavaUtilConcurrentLinkedBlockingDeque *)

#endif // _JavaUtilConcurrentLinkedBlockingDeque_H_
