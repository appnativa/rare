//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: apache_harmony/classlib/modules/luni/src/main/java/java/util/LinkedList.java
//
//  Created by tball on 11/23/13.
//

#ifndef _JavaUtilLinkedList_H_
#define _JavaUtilLinkedList_H_

@class JavaUtilLinkedList_Link;
@protocol JavaUtilCollection;

#import "JreEmulation.h"
#include "java/io/Serializable.h"
#include "java/util/AbstractSequentialList.h"
#include "java/util/List.h"
#include "java/util/ListIterator.h"
#include "java/util/Queue.h"

#define JavaUtilLinkedList_serialVersionUID 876323262645176354

@interface JavaUtilLinkedList : JavaUtilAbstractSequentialList < JavaUtilList, JavaUtilQueue, NSCopying, JavaIoSerializable > {
 @public
  int size__;
  JavaUtilLinkedList_Link *voidLink_;
}

- (id)init;
- (id)initWithJavaUtilCollection:(id<JavaUtilCollection>)collection;
- (void)addWithInt:(int)location
            withId:(id)object;
- (BOOL)addWithId:(id)object;
- (BOOL)addAllWithInt:(int)location
withJavaUtilCollection:(id<JavaUtilCollection>)collection;
- (BOOL)addAllWithJavaUtilCollection:(id<JavaUtilCollection>)collection;
- (void)addFirstWithId:(id)object;
- (void)addLastWithId:(id)object;
- (void)clear;
- (id)clone;
- (BOOL)containsWithId:(id)object;
- (id)getWithInt:(int)location;
- (id)getFirst;
- (id)getLast;
- (int)indexOfWithId:(id)object;
- (int)lastIndexOfWithId:(id)object;
- (id<JavaUtilListIterator>)listIteratorWithInt:(int)location;
- (id)removeWithInt:(int)location;
- (BOOL)removeWithId:(id)object;
- (id)removeFirst;
- (id)removeLast;
- (id)setWithInt:(int)location
          withId:(id)object;
- (int)size;
- (BOOL)offerWithId:(id)o;
- (id)poll;
- (id)remove;
- (id)peek;
- (id)element;
- (void)dealloc;
- (id)copyWithZone:(NSZone *)zone;
- (void)copyAllFieldsTo:(JavaUtilLinkedList *)other;
@end

J2OBJC_FIELD_SETTER(JavaUtilLinkedList, voidLink_, JavaUtilLinkedList_Link *)

@interface JavaUtilLinkedList_Link : NSObject {
 @public
  id data_;
  __weak JavaUtilLinkedList_Link *previous_;
  JavaUtilLinkedList_Link *next_;
}

- (id)initWithId:(id)o
withJavaUtilLinkedList_Link:(JavaUtilLinkedList_Link *)p
withJavaUtilLinkedList_Link:(JavaUtilLinkedList_Link *)n;
- (void)copyAllFieldsTo:(JavaUtilLinkedList_Link *)other;
@end

J2OBJC_FIELD_SETTER(JavaUtilLinkedList_Link, data_, id)
J2OBJC_FIELD_SETTER(JavaUtilLinkedList_Link, next_, JavaUtilLinkedList_Link *)

@interface JavaUtilLinkedList_LinkIterator : NSObject < JavaUtilListIterator > {
 @public
  int pos_, expectedModCount_;
  JavaUtilLinkedList *list_;
  __weak JavaUtilLinkedList_Link *link_, *lastLink_;
}

- (id)initWithJavaUtilLinkedList:(JavaUtilLinkedList *)object
                         withInt:(int)location;
- (void)addWithId:(id)object;
- (BOOL)hasNext;
- (BOOL)hasPrevious;
- (id)next;
- (int)nextIndex;
- (id)previous;
- (int)previousIndex;
- (void)remove;
- (void)setWithId:(id)object;
- (void)copyAllFieldsTo:(JavaUtilLinkedList_LinkIterator *)other;
@end

J2OBJC_FIELD_SETTER(JavaUtilLinkedList_LinkIterator, list_, JavaUtilLinkedList *)

#endif // _JavaUtilLinkedList_H_
