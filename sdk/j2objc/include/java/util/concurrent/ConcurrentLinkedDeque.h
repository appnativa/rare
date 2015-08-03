//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: android/libcore/luni/src/main/java/java/util/concurrent/ConcurrentLinkedDeque.java
//
//  Created by tball on 11/23/13.
//

#ifndef _JavaUtilConcurrentConcurrentLinkedDeque_H_
#define _JavaUtilConcurrentConcurrentLinkedDeque_H_

@class IOSObjectArray;
@class JavaUtilArrayList;
@class JavaUtilConcurrentConcurrentLinkedDeque_Node;
@class SunMiscUnsafe;
@protocol JavaUtilCollection;

#import "JreEmulation.h"
#include "java/io/Serializable.h"
#include "java/util/AbstractCollection.h"
#include "java/util/Deque.h"
#include "java/util/Iterator.h"

#define JavaUtilConcurrentConcurrentLinkedDeque_HOPS 2
#define JavaUtilConcurrentConcurrentLinkedDeque_serialVersionUID 876323262645176354

@interface JavaUtilConcurrentConcurrentLinkedDeque : JavaUtilAbstractCollection < JavaUtilDeque, JavaIoSerializable > {
 @public
  JavaUtilConcurrentConcurrentLinkedDeque_Node *head_;
  JavaUtilConcurrentConcurrentLinkedDeque_Node *tail_;
}

+ (JavaUtilConcurrentConcurrentLinkedDeque_Node *)PREV_TERMINATOR;
+ (JavaUtilConcurrentConcurrentLinkedDeque_Node *)NEXT_TERMINATOR;
+ (SunMiscUnsafe *)UNSAFE;
+ (long long int)headOffset;
+ (long long int)tailOffset;
- (JavaUtilConcurrentConcurrentLinkedDeque_Node *)prevTerminator;
- (JavaUtilConcurrentConcurrentLinkedDeque_Node *)nextTerminator;
- (void)linkFirstWithId:(id)e;
- (void)linkLastWithId:(id)e;
- (void)unlinkWithJavaUtilConcurrentConcurrentLinkedDeque_Node:(JavaUtilConcurrentConcurrentLinkedDeque_Node *)x;
- (void)unlinkFirstWithJavaUtilConcurrentConcurrentLinkedDeque_Node:(JavaUtilConcurrentConcurrentLinkedDeque_Node *)first
                   withJavaUtilConcurrentConcurrentLinkedDeque_Node:(JavaUtilConcurrentConcurrentLinkedDeque_Node *)next;
- (void)unlinkLastWithJavaUtilConcurrentConcurrentLinkedDeque_Node:(JavaUtilConcurrentConcurrentLinkedDeque_Node *)last
                  withJavaUtilConcurrentConcurrentLinkedDeque_Node:(JavaUtilConcurrentConcurrentLinkedDeque_Node *)prev;
- (void)updateHead;
- (void)updateTail;
- (void)skipDeletedPredecessorsWithJavaUtilConcurrentConcurrentLinkedDeque_Node:(JavaUtilConcurrentConcurrentLinkedDeque_Node *)x;
- (void)skipDeletedSuccessorsWithJavaUtilConcurrentConcurrentLinkedDeque_Node:(JavaUtilConcurrentConcurrentLinkedDeque_Node *)x;
- (JavaUtilConcurrentConcurrentLinkedDeque_Node *)succWithJavaUtilConcurrentConcurrentLinkedDeque_Node:(JavaUtilConcurrentConcurrentLinkedDeque_Node *)p;
- (JavaUtilConcurrentConcurrentLinkedDeque_Node *)predWithJavaUtilConcurrentConcurrentLinkedDeque_Node:(JavaUtilConcurrentConcurrentLinkedDeque_Node *)p;
- (JavaUtilConcurrentConcurrentLinkedDeque_Node *)first;
- (JavaUtilConcurrentConcurrentLinkedDeque_Node *)last;
+ (void)checkNotNullWithId:(id)v;
- (id)screenNullResultWithId:(id)v;
- (JavaUtilArrayList *)toArrayList;
- (id)init;
- (id)initWithJavaUtilCollection:(id<JavaUtilCollection>)c;
- (void)initHeadTailWithJavaUtilConcurrentConcurrentLinkedDeque_Node:(JavaUtilConcurrentConcurrentLinkedDeque_Node *)h
                    withJavaUtilConcurrentConcurrentLinkedDeque_Node:(JavaUtilConcurrentConcurrentLinkedDeque_Node *)t OBJC_METHOD_FAMILY_NONE;
- (void)addFirstWithId:(id)e;
- (void)addLastWithId:(id)e;
- (BOOL)offerFirstWithId:(id)e;
- (BOOL)offerLastWithId:(id)e;
- (id)peekFirst;
- (id)peekLast;
- (id)getFirst;
- (id)getLast;
- (id)pollFirst;
- (id)pollLast;
- (id)removeFirst;
- (id)removeLast;
- (BOOL)offerWithId:(id)e;
- (BOOL)addWithId:(id)e;
- (id)poll;
- (id)remove;
- (id)peek;
- (id)element;
- (void)pushWithId:(id)e;
- (id)pop;
- (BOOL)removeFirstOccurrenceWithId:(id)o;
- (BOOL)removeLastOccurrenceWithId:(id)o;
- (BOOL)containsWithId:(id)o;
- (BOOL)isEmpty;
- (int)size;
- (BOOL)removeWithId:(id)o;
- (BOOL)addAllWithJavaUtilCollection:(id<JavaUtilCollection>)c;
- (void)clear;
- (IOSObjectArray *)toArray;
- (IOSObjectArray *)toArrayWithNSObjectArray:(IOSObjectArray *)a;
- (id<JavaUtilIterator>)iterator;
- (id<JavaUtilIterator>)descendingIterator;
- (BOOL)casHeadWithJavaUtilConcurrentConcurrentLinkedDeque_Node:(JavaUtilConcurrentConcurrentLinkedDeque_Node *)cmp
               withJavaUtilConcurrentConcurrentLinkedDeque_Node:(JavaUtilConcurrentConcurrentLinkedDeque_Node *)val;
- (BOOL)casTailWithJavaUtilConcurrentConcurrentLinkedDeque_Node:(JavaUtilConcurrentConcurrentLinkedDeque_Node *)cmp
               withJavaUtilConcurrentConcurrentLinkedDeque_Node:(JavaUtilConcurrentConcurrentLinkedDeque_Node *)val;
- (void)copyAllFieldsTo:(JavaUtilConcurrentConcurrentLinkedDeque *)other;
@end

J2OBJC_FIELD_SETTER(JavaUtilConcurrentConcurrentLinkedDeque, head_, JavaUtilConcurrentConcurrentLinkedDeque_Node *)
J2OBJC_FIELD_SETTER(JavaUtilConcurrentConcurrentLinkedDeque, tail_, JavaUtilConcurrentConcurrentLinkedDeque_Node *)

@interface JavaUtilConcurrentConcurrentLinkedDeque_Node : NSObject {
 @public
  JavaUtilConcurrentConcurrentLinkedDeque_Node *prev_;
  id item_;
  JavaUtilConcurrentConcurrentLinkedDeque_Node *next_;
}

+ (SunMiscUnsafe *)UNSAFE;
+ (long long int)prevOffset;
+ (long long int)itemOffset;
+ (long long int)nextOffset;
- (id)init;
- (id)initWithId:(id)item;
- (BOOL)casItemWithId:(id)cmp
               withId:(id)val;
- (void)lazySetNextWithJavaUtilConcurrentConcurrentLinkedDeque_Node:(JavaUtilConcurrentConcurrentLinkedDeque_Node *)val;
- (BOOL)casNextWithJavaUtilConcurrentConcurrentLinkedDeque_Node:(JavaUtilConcurrentConcurrentLinkedDeque_Node *)cmp
               withJavaUtilConcurrentConcurrentLinkedDeque_Node:(JavaUtilConcurrentConcurrentLinkedDeque_Node *)val;
- (void)lazySetPrevWithJavaUtilConcurrentConcurrentLinkedDeque_Node:(JavaUtilConcurrentConcurrentLinkedDeque_Node *)val;
- (BOOL)casPrevWithJavaUtilConcurrentConcurrentLinkedDeque_Node:(JavaUtilConcurrentConcurrentLinkedDeque_Node *)cmp
               withJavaUtilConcurrentConcurrentLinkedDeque_Node:(JavaUtilConcurrentConcurrentLinkedDeque_Node *)val;
- (void)copyAllFieldsTo:(JavaUtilConcurrentConcurrentLinkedDeque_Node *)other;
@end

J2OBJC_FIELD_SETTER(JavaUtilConcurrentConcurrentLinkedDeque_Node, prev_, JavaUtilConcurrentConcurrentLinkedDeque_Node *)
J2OBJC_FIELD_SETTER(JavaUtilConcurrentConcurrentLinkedDeque_Node, item_, id)
J2OBJC_FIELD_SETTER(JavaUtilConcurrentConcurrentLinkedDeque_Node, next_, JavaUtilConcurrentConcurrentLinkedDeque_Node *)

@interface JavaUtilConcurrentConcurrentLinkedDeque_AbstractItr : NSObject < JavaUtilIterator > {
 @public
  JavaUtilConcurrentConcurrentLinkedDeque *this$0_;
  JavaUtilConcurrentConcurrentLinkedDeque_Node *nextNode__;
  id nextItem_;
  JavaUtilConcurrentConcurrentLinkedDeque_Node *lastRet_;
}

- (JavaUtilConcurrentConcurrentLinkedDeque_Node *)startNode;
- (JavaUtilConcurrentConcurrentLinkedDeque_Node *)nextNodeWithJavaUtilConcurrentConcurrentLinkedDeque_Node:(JavaUtilConcurrentConcurrentLinkedDeque_Node *)p;
- (id)initWithJavaUtilConcurrentConcurrentLinkedDeque:(JavaUtilConcurrentConcurrentLinkedDeque *)outer$;
- (void)advance;
- (BOOL)hasNext;
- (id)next;
- (void)remove;
- (void)copyAllFieldsTo:(JavaUtilConcurrentConcurrentLinkedDeque_AbstractItr *)other;
@end

J2OBJC_FIELD_SETTER(JavaUtilConcurrentConcurrentLinkedDeque_AbstractItr, this$0_, JavaUtilConcurrentConcurrentLinkedDeque *)
J2OBJC_FIELD_SETTER(JavaUtilConcurrentConcurrentLinkedDeque_AbstractItr, nextNode__, JavaUtilConcurrentConcurrentLinkedDeque_Node *)
J2OBJC_FIELD_SETTER(JavaUtilConcurrentConcurrentLinkedDeque_AbstractItr, nextItem_, id)
J2OBJC_FIELD_SETTER(JavaUtilConcurrentConcurrentLinkedDeque_AbstractItr, lastRet_, JavaUtilConcurrentConcurrentLinkedDeque_Node *)

@interface JavaUtilConcurrentConcurrentLinkedDeque_Itr : JavaUtilConcurrentConcurrentLinkedDeque_AbstractItr {
 @public
  JavaUtilConcurrentConcurrentLinkedDeque *this$1_;
}

- (JavaUtilConcurrentConcurrentLinkedDeque_Node *)startNode;
- (JavaUtilConcurrentConcurrentLinkedDeque_Node *)nextNodeWithJavaUtilConcurrentConcurrentLinkedDeque_Node:(JavaUtilConcurrentConcurrentLinkedDeque_Node *)p;
- (id)initWithJavaUtilConcurrentConcurrentLinkedDeque:(JavaUtilConcurrentConcurrentLinkedDeque *)outer$;
@end

J2OBJC_FIELD_SETTER(JavaUtilConcurrentConcurrentLinkedDeque_Itr, this$1_, JavaUtilConcurrentConcurrentLinkedDeque *)

@interface JavaUtilConcurrentConcurrentLinkedDeque_DescendingItr : JavaUtilConcurrentConcurrentLinkedDeque_AbstractItr {
 @public
  JavaUtilConcurrentConcurrentLinkedDeque *this$1_;
}

- (JavaUtilConcurrentConcurrentLinkedDeque_Node *)startNode;
- (JavaUtilConcurrentConcurrentLinkedDeque_Node *)nextNodeWithJavaUtilConcurrentConcurrentLinkedDeque_Node:(JavaUtilConcurrentConcurrentLinkedDeque_Node *)p;
- (id)initWithJavaUtilConcurrentConcurrentLinkedDeque:(JavaUtilConcurrentConcurrentLinkedDeque *)outer$;
@end

J2OBJC_FIELD_SETTER(JavaUtilConcurrentConcurrentLinkedDeque_DescendingItr, this$1_, JavaUtilConcurrentConcurrentLinkedDeque *)

#endif // _JavaUtilConcurrentConcurrentLinkedDeque_H_
