//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: android/libcore/luni/src/main/java/java/util/WeakHashMap.java
//
//  Created by tball on 11/23/13.
//

#ifndef _JavaUtilWeakHashMap_H_
#define _JavaUtilWeakHashMap_H_

@class IOSObjectArray;
@class JavaLangRefReferenceQueue;
@class JavaUtilWeakHashMap_Entry;
@protocol JavaUtilCollection;
@protocol JavaUtilSet;
@protocol JavaUtilWeakHashMap_Entry_Type;

#import "JreEmulation.h"
#include "java/lang/ref/WeakReference.h"
#include "java/util/AbstractCollection.h"
#include "java/util/AbstractMap.h"
#include "java/util/AbstractSet.h"
#include "java/util/Iterator.h"
#include "java/util/Map.h"

#define JavaUtilWeakHashMap_DEFAULT_SIZE 16

@interface JavaUtilWeakHashMap : JavaUtilAbstractMap < JavaUtilMap > {
 @public
  JavaLangRefReferenceQueue *referenceQueue_;
  int elementCount_;
  IOSObjectArray *elementData_;
  int loadFactor_;
  int threshold_;
  int modCount_;
}

+ (IOSObjectArray *)newEntryArrayWithInt:(int)size OBJC_METHOD_FAMILY_NONE;
- (id)init;
- (id)initWithInt:(int)capacity;
- (id)initWithInt:(int)capacity
        withFloat:(float)loadFactor;
- (id)initWithJavaUtilMap:(id<JavaUtilMap>)map;
- (void)clear;
- (void)computeMaxSize;
- (BOOL)containsKeyWithId:(id)key;
- (id<JavaUtilSet>)entrySet;
- (id<JavaUtilSet>)keySet;
- (id<JavaUtilCollection>)values;
- (id)getWithId:(id)key;
- (JavaUtilWeakHashMap_Entry *)getEntryWithId:(id)key;
- (BOOL)containsValueWithId:(id)value;
- (BOOL)isEmpty;
- (void)poll;
- (void)removeEntryWithJavaUtilWeakHashMap_Entry:(JavaUtilWeakHashMap_Entry *)toRemove;
- (id)putWithId:(id)key
         withId:(id)value;
- (void)rehash;
- (void)putAllWithJavaUtilMap:(id<JavaUtilMap>)map;
- (id)removeWithId:(id)key;
- (int)size;
- (void)putAllImplWithJavaUtilMap:(id<JavaUtilMap>)map;
- (void)copyAllFieldsTo:(JavaUtilWeakHashMap *)other;
@end

J2OBJC_FIELD_SETTER(JavaUtilWeakHashMap, referenceQueue_, JavaLangRefReferenceQueue *)
J2OBJC_FIELD_SETTER(JavaUtilWeakHashMap, elementData_, IOSObjectArray *)

@interface JavaUtilWeakHashMap_Entry : JavaLangRefWeakReference < JavaUtilMap_Entry > {
 @public
  int hash__;
  BOOL isNull_;
  id value_;
  JavaUtilWeakHashMap_Entry *next_;
}

- (id)initWithId:(id)key
          withId:(id)object
withJavaLangRefReferenceQueue:(JavaLangRefReferenceQueue *)queue;
- (id)getKey;
- (id)getValue;
- (id)setValueWithId:(id)object;
- (BOOL)isEqual:(id)other;
- (NSUInteger)hash;
- (NSString *)description;
- (void)copyAllFieldsTo:(JavaUtilWeakHashMap_Entry *)other;
@end

J2OBJC_FIELD_SETTER(JavaUtilWeakHashMap_Entry, value_, id)
J2OBJC_FIELD_SETTER(JavaUtilWeakHashMap_Entry, next_, JavaUtilWeakHashMap_Entry *)

@interface JavaUtilWeakHashMap_HashIterator : NSObject < JavaUtilIterator > {
 @public
  JavaUtilWeakHashMap *this$0_;
  int position_, expectedModCount_;
  JavaUtilWeakHashMap_Entry *currentEntry_, *nextEntry_;
  id nextKey_;
  id<JavaUtilWeakHashMap_Entry_Type> type_;
}

- (id)initWithJavaUtilWeakHashMap:(JavaUtilWeakHashMap *)outer$
withJavaUtilWeakHashMap_Entry_Type:(id<JavaUtilWeakHashMap_Entry_Type>)type;
- (BOOL)hasNext;
- (id)next;
- (void)remove;
- (void)copyAllFieldsTo:(JavaUtilWeakHashMap_HashIterator *)other;
@end

J2OBJC_FIELD_SETTER(JavaUtilWeakHashMap_HashIterator, this$0_, JavaUtilWeakHashMap *)
J2OBJC_FIELD_SETTER(JavaUtilWeakHashMap_HashIterator, currentEntry_, JavaUtilWeakHashMap_Entry *)
J2OBJC_FIELD_SETTER(JavaUtilWeakHashMap_HashIterator, nextEntry_, JavaUtilWeakHashMap_Entry *)
J2OBJC_FIELD_SETTER(JavaUtilWeakHashMap_HashIterator, nextKey_, id)
J2OBJC_FIELD_SETTER(JavaUtilWeakHashMap_HashIterator, type_, id<JavaUtilWeakHashMap_Entry_Type>)

@interface JavaUtilWeakHashMap_KeySet : JavaUtilAbstractSet {
 @public
  __weak JavaUtilWeakHashMap *this$0_;
}

- (BOOL)containsWithId:(id)object;
- (int)size;
- (void)clear;
- (BOOL)removeWithId:(id)key;
- (id<JavaUtilIterator>)iterator;
- (id)initWithJavaUtilWeakHashMap:(JavaUtilWeakHashMap *)outer$;
@end

@protocol JavaUtilWeakHashMap_Entry_Type < NSObject, JavaObject >
- (id)getWithJavaUtilMap_Entry:(id<JavaUtilMap_Entry>)entry_;
@end

@interface JavaUtilWeakHashMap_KeySet_$1 : NSObject < JavaUtilWeakHashMap_Entry_Type > {
}

- (id)getWithJavaUtilMap_Entry:(id<JavaUtilMap_Entry>)entry_;
- (id)init;
@end

@interface JavaUtilWeakHashMap_ValuesCollection : JavaUtilAbstractCollection {
 @public
  __weak JavaUtilWeakHashMap *this$0_;
}

- (int)size;
- (void)clear;
- (BOOL)containsWithId:(id)object;
- (id<JavaUtilIterator>)iterator;
- (id)initWithJavaUtilWeakHashMap:(JavaUtilWeakHashMap *)outer$;
@end

@interface JavaUtilWeakHashMap_ValuesCollection_$1 : NSObject < JavaUtilWeakHashMap_Entry_Type > {
}

- (id)getWithJavaUtilMap_Entry:(id<JavaUtilMap_Entry>)entry_;
- (id)init;
@end

@interface JavaUtilWeakHashMap_$1 : JavaUtilAbstractSet {
 @public
  JavaUtilWeakHashMap *this$0_;
}

- (int)size;
- (void)clear;
- (BOOL)removeWithId:(id)object;
- (BOOL)containsWithId:(id)object;
- (id<JavaUtilIterator>)iterator;
- (id)initWithJavaUtilWeakHashMap:(JavaUtilWeakHashMap *)outer$;
@end

J2OBJC_FIELD_SETTER(JavaUtilWeakHashMap_$1, this$0_, JavaUtilWeakHashMap *)

@interface JavaUtilWeakHashMap_$1_$1 : NSObject < JavaUtilWeakHashMap_Entry_Type > {
}

- (id<JavaUtilMap_Entry>)getWithJavaUtilMap_Entry:(id<JavaUtilMap_Entry>)entry_;
- (id)init;
@end

#endif // _JavaUtilWeakHashMap_H_