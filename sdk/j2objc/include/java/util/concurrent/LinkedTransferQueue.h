//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: android/libcore/luni/src/main/java/java/util/concurrent/LinkedTransferQueue.java
//
//  Created by tball on 11/23/13.
//

#ifndef _JavaUtilConcurrentLinkedTransferQueue_H_
#define _JavaUtilConcurrentLinkedTransferQueue_H_

@class JavaLangThread;
@class JavaUtilConcurrentLinkedTransferQueue_Node;
@class JavaUtilConcurrentTimeUnitEnum;
@class SunMiscUnsafe;
@protocol JavaUtilCollection;

#import "JreEmulation.h"
#include "java/io/Serializable.h"
#include "java/util/AbstractQueue.h"
#include "java/util/Iterator.h"
#include "java/util/concurrent/TransferQueue.h"

#define JavaUtilConcurrentLinkedTransferQueue_ASYNC 1
#define JavaUtilConcurrentLinkedTransferQueue_CHAINED_SPINS 64
#define JavaUtilConcurrentLinkedTransferQueue_FRONT_SPINS 128
#define JavaUtilConcurrentLinkedTransferQueue_NOW 0
#define JavaUtilConcurrentLinkedTransferQueue_SWEEP_THRESHOLD 32
#define JavaUtilConcurrentLinkedTransferQueue_SYNC 2
#define JavaUtilConcurrentLinkedTransferQueue_TIMED 3
#define JavaUtilConcurrentLinkedTransferQueue_serialVersionUID -3223113410248163686

@interface JavaUtilConcurrentLinkedTransferQueue : JavaUtilAbstractQueue < JavaUtilConcurrentTransferQueue, JavaIoSerializable > {
 @public
  JavaUtilConcurrentLinkedTransferQueue_Node *head_;
  JavaUtilConcurrentLinkedTransferQueue_Node *tail_;
  int sweepVotes_;
}

+ (BOOL)MP;
+ (int)SWEEP_THRESHOLD;
+ (SunMiscUnsafe *)UNSAFE;
+ (long long int)headOffset;
+ (long long int)tailOffset;
+ (long long int)sweepVotesOffset;
- (BOOL)casTailWithJavaUtilConcurrentLinkedTransferQueue_Node:(JavaUtilConcurrentLinkedTransferQueue_Node *)cmp
               withJavaUtilConcurrentLinkedTransferQueue_Node:(JavaUtilConcurrentLinkedTransferQueue_Node *)val;
- (BOOL)casHeadWithJavaUtilConcurrentLinkedTransferQueue_Node:(JavaUtilConcurrentLinkedTransferQueue_Node *)cmp
               withJavaUtilConcurrentLinkedTransferQueue_Node:(JavaUtilConcurrentLinkedTransferQueue_Node *)val;
- (BOOL)casSweepVotesWithInt:(int)cmp
                     withInt:(int)val;
+ (id)castWithId:(id)item;
- (id)xferWithId:(id)e
     withBoolean:(BOOL)haveData
         withInt:(int)how
        withLong:(long long int)nanos;
- (JavaUtilConcurrentLinkedTransferQueue_Node *)tryAppendWithJavaUtilConcurrentLinkedTransferQueue_Node:(JavaUtilConcurrentLinkedTransferQueue_Node *)s
                                                                                            withBoolean:(BOOL)haveData;
- (id)awaitMatchWithJavaUtilConcurrentLinkedTransferQueue_Node:(JavaUtilConcurrentLinkedTransferQueue_Node *)s
                withJavaUtilConcurrentLinkedTransferQueue_Node:(JavaUtilConcurrentLinkedTransferQueue_Node *)pred
                                                        withId:(id)e
                                                   withBoolean:(BOOL)timed
                                                      withLong:(long long int)nanos;
+ (int)spinsForWithJavaUtilConcurrentLinkedTransferQueue_Node:(JavaUtilConcurrentLinkedTransferQueue_Node *)pred
                                                  withBoolean:(BOOL)haveData;
- (JavaUtilConcurrentLinkedTransferQueue_Node *)succWithJavaUtilConcurrentLinkedTransferQueue_Node:(JavaUtilConcurrentLinkedTransferQueue_Node *)p;
- (JavaUtilConcurrentLinkedTransferQueue_Node *)firstOfModeWithBoolean:(BOOL)isData;
- (id)firstDataItem;
- (int)countOfModeWithBoolean:(BOOL)data;
- (void)unspliceWithJavaUtilConcurrentLinkedTransferQueue_Node:(JavaUtilConcurrentLinkedTransferQueue_Node *)pred
                withJavaUtilConcurrentLinkedTransferQueue_Node:(JavaUtilConcurrentLinkedTransferQueue_Node *)s;
- (void)sweep;
- (BOOL)findAndRemoveWithId:(id)e;
- (id)init;
- (id)initWithJavaUtilCollection:(id<JavaUtilCollection>)c;
- (void)putWithId:(id)e;
- (BOOL)offerWithId:(id)e
           withLong:(long long int)timeout
withJavaUtilConcurrentTimeUnitEnum:(JavaUtilConcurrentTimeUnitEnum *)unit;
- (BOOL)offerWithId:(id)e;
- (BOOL)addWithId:(id)e;
- (BOOL)tryTransferWithId:(id)e;
- (void)transferWithId:(id)e;
- (BOOL)tryTransferWithId:(id)e
                 withLong:(long long int)timeout
withJavaUtilConcurrentTimeUnitEnum:(JavaUtilConcurrentTimeUnitEnum *)unit;
- (id)take;
- (id)pollWithLong:(long long int)timeout
withJavaUtilConcurrentTimeUnitEnum:(JavaUtilConcurrentTimeUnitEnum *)unit;
- (id)poll;
- (int)drainToWithJavaUtilCollection:(id<JavaUtilCollection>)c;
- (int)drainToWithJavaUtilCollection:(id<JavaUtilCollection>)c
                             withInt:(int)maxElements;
- (id<JavaUtilIterator>)iterator;
- (id)peek;
- (BOOL)isEmpty;
- (BOOL)hasWaitingConsumer;
- (int)size;
- (int)getWaitingConsumerCount;
- (BOOL)removeWithId:(id)o;
- (BOOL)containsWithId:(id)o;
- (int)remainingCapacity;
- (void)copyAllFieldsTo:(JavaUtilConcurrentLinkedTransferQueue *)other;
@end

J2OBJC_FIELD_SETTER(JavaUtilConcurrentLinkedTransferQueue, head_, JavaUtilConcurrentLinkedTransferQueue_Node *)
J2OBJC_FIELD_SETTER(JavaUtilConcurrentLinkedTransferQueue, tail_, JavaUtilConcurrentLinkedTransferQueue_Node *)

#define JavaUtilConcurrentLinkedTransferQueue_Node_serialVersionUID -3375979862319811754

@interface JavaUtilConcurrentLinkedTransferQueue_Node : NSObject {
 @public
  BOOL isData_;
  id item_;
  JavaUtilConcurrentLinkedTransferQueue_Node *next_;
  JavaLangThread *waiter_;
}

+ (SunMiscUnsafe *)UNSAFE;
+ (long long int)itemOffset;
+ (long long int)nextOffset;
+ (long long int)waiterOffset;
- (BOOL)casNextWithJavaUtilConcurrentLinkedTransferQueue_Node:(JavaUtilConcurrentLinkedTransferQueue_Node *)cmp
               withJavaUtilConcurrentLinkedTransferQueue_Node:(JavaUtilConcurrentLinkedTransferQueue_Node *)val;
- (BOOL)casItemWithId:(id)cmp
               withId:(id)val;
- (id)initWithId:(id)item
     withBoolean:(BOOL)isData;
- (void)forgetNext;
- (void)forgetContents;
- (BOOL)isMatched;
- (BOOL)isUnmatchedRequest;
- (BOOL)cannotPrecedeWithBoolean:(BOOL)haveData;
- (BOOL)tryMatchData;
- (void)copyAllFieldsTo:(JavaUtilConcurrentLinkedTransferQueue_Node *)other;
@end

J2OBJC_FIELD_SETTER(JavaUtilConcurrentLinkedTransferQueue_Node, item_, id)
J2OBJC_FIELD_SETTER(JavaUtilConcurrentLinkedTransferQueue_Node, next_, JavaUtilConcurrentLinkedTransferQueue_Node *)
J2OBJC_FIELD_SETTER(JavaUtilConcurrentLinkedTransferQueue_Node, waiter_, JavaLangThread *)

@interface JavaUtilConcurrentLinkedTransferQueue_Itr : NSObject < JavaUtilIterator > {
 @public
  JavaUtilConcurrentLinkedTransferQueue *this$0_;
  JavaUtilConcurrentLinkedTransferQueue_Node *nextNode_;
  id nextItem_;
  JavaUtilConcurrentLinkedTransferQueue_Node *lastRet_;
  JavaUtilConcurrentLinkedTransferQueue_Node *lastPred_;
}

- (void)advanceWithJavaUtilConcurrentLinkedTransferQueue_Node:(JavaUtilConcurrentLinkedTransferQueue_Node *)prev;
- (id)initWithJavaUtilConcurrentLinkedTransferQueue:(JavaUtilConcurrentLinkedTransferQueue *)outer$;
- (BOOL)hasNext;
- (id)next;
- (void)remove;
- (void)copyAllFieldsTo:(JavaUtilConcurrentLinkedTransferQueue_Itr *)other;
@end

J2OBJC_FIELD_SETTER(JavaUtilConcurrentLinkedTransferQueue_Itr, this$0_, JavaUtilConcurrentLinkedTransferQueue *)
J2OBJC_FIELD_SETTER(JavaUtilConcurrentLinkedTransferQueue_Itr, nextNode_, JavaUtilConcurrentLinkedTransferQueue_Node *)
J2OBJC_FIELD_SETTER(JavaUtilConcurrentLinkedTransferQueue_Itr, nextItem_, id)
J2OBJC_FIELD_SETTER(JavaUtilConcurrentLinkedTransferQueue_Itr, lastRet_, JavaUtilConcurrentLinkedTransferQueue_Node *)
J2OBJC_FIELD_SETTER(JavaUtilConcurrentLinkedTransferQueue_Itr, lastPred_, JavaUtilConcurrentLinkedTransferQueue_Node *)

#endif // _JavaUtilConcurrentLinkedTransferQueue_H_
