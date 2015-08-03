//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/guava/sources/com/google/common/collect/ImmutableCollection.java
//
//  Created by tball on 11/23/13.
//

#import "JreEmulation.h"

#if !ComGoogleCommonCollectImmutableCollection_RESTRICT
#define ComGoogleCommonCollectImmutableCollection_INCLUDE_ALL 1
#endif
#undef ComGoogleCommonCollectImmutableCollection_RESTRICT
#if ComGoogleCommonCollectImmutableCollection_ArrayImmutableCollection_INCLUDE
#define ComGoogleCommonCollectImmutableCollection_INCLUDE 1
#endif
#if ComGoogleCommonCollectImmutableCollection_EmptyImmutableCollection_INCLUDE
#define ComGoogleCommonCollectImmutableCollection_INCLUDE 1
#endif

#if !defined (_ComGoogleCommonCollectImmutableCollection_) && (ComGoogleCommonCollectImmutableCollection_INCLUDE_ALL || ComGoogleCommonCollectImmutableCollection_INCLUDE)
#define _ComGoogleCommonCollectImmutableCollection_

@class ComGoogleCommonCollectImmutableList;
@class ComGoogleCommonCollectUnmodifiableIterator;
@class IOSObjectArray;

#define JavaUtilCollection_RESTRICT 1
#define JavaUtilCollection_INCLUDE 1
#include "java/util/Collection.h"

#define JavaIoSerializable_RESTRICT 1
#define JavaIoSerializable_INCLUDE 1
#include "java/io/Serializable.h"

@interface ComGoogleCommonCollectImmutableCollection : NSObject < JavaUtilCollection, JavaIoSerializable > {
 @public
  ComGoogleCommonCollectImmutableList *asList__;
}

+ (ComGoogleCommonCollectImmutableCollection *)EMPTY_IMMUTABLE_COLLECTION;
- (id)init;
- (ComGoogleCommonCollectUnmodifiableIterator *)iterator;
- (IOSObjectArray *)toArray;
- (IOSObjectArray *)toArrayWithNSObjectArray:(IOSObjectArray *)other;
- (BOOL)containsWithId:(id)object;
- (BOOL)containsAllWithJavaUtilCollection:(id<JavaUtilCollection>)targets;
- (BOOL)isEmpty;
- (NSString *)description;
- (BOOL)addWithId:(id)e;
- (BOOL)removeWithId:(id)object;
- (BOOL)addAllWithJavaUtilCollection:(id<JavaUtilCollection>)newElements;
- (BOOL)removeAllWithJavaUtilCollection:(id<JavaUtilCollection>)oldElements;
- (BOOL)retainAllWithJavaUtilCollection:(id<JavaUtilCollection>)elementsToKeep;
- (void)clear;
- (ComGoogleCommonCollectImmutableList *)asList;
- (ComGoogleCommonCollectImmutableList *)createAsList;
- (BOOL)isPartialView;
- (id)writeReplace;
- (int)size;
- (void)copyAllFieldsTo:(ComGoogleCommonCollectImmutableCollection *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectImmutableCollection, asList__, ComGoogleCommonCollectImmutableList *)
#endif

#if !defined (_ComGoogleCommonCollectImmutableCollection_EmptyImmutableCollection_) && (ComGoogleCommonCollectImmutableCollection_INCLUDE_ALL || ComGoogleCommonCollectImmutableCollection_EmptyImmutableCollection_INCLUDE)
#define _ComGoogleCommonCollectImmutableCollection_EmptyImmutableCollection_

@class ComGoogleCommonCollectImmutableList;
@class ComGoogleCommonCollectUnmodifiableIterator;
@class IOSObjectArray;

@interface ComGoogleCommonCollectImmutableCollection_EmptyImmutableCollection : ComGoogleCommonCollectImmutableCollection {
}

+ (IOSObjectArray *)EMPTY_ARRAY;
- (int)size;
- (BOOL)isEmpty;
- (BOOL)containsWithId:(id)object;
- (ComGoogleCommonCollectUnmodifiableIterator *)iterator;
- (IOSObjectArray *)toArray;
- (IOSObjectArray *)toArrayWithNSObjectArray:(IOSObjectArray *)array;
- (ComGoogleCommonCollectImmutableList *)createAsList;
- (BOOL)isPartialView;
- (id)init;
@end
#endif

#if !defined (_ComGoogleCommonCollectImmutableCollection_ArrayImmutableCollection_) && (ComGoogleCommonCollectImmutableCollection_INCLUDE_ALL || ComGoogleCommonCollectImmutableCollection_ArrayImmutableCollection_INCLUDE)
#define _ComGoogleCommonCollectImmutableCollection_ArrayImmutableCollection_

@class ComGoogleCommonCollectImmutableList;
@class ComGoogleCommonCollectUnmodifiableIterator;
@class IOSObjectArray;

@interface ComGoogleCommonCollectImmutableCollection_ArrayImmutableCollection : ComGoogleCommonCollectImmutableCollection {
 @public
  IOSObjectArray *elements_;
}

- (id)initWithNSObjectArray:(IOSObjectArray *)elements;
- (int)size;
- (BOOL)isEmpty;
- (ComGoogleCommonCollectUnmodifiableIterator *)iterator;
- (ComGoogleCommonCollectImmutableList *)createAsList;
- (BOOL)isPartialView;
- (void)copyAllFieldsTo:(ComGoogleCommonCollectImmutableCollection_ArrayImmutableCollection *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectImmutableCollection_ArrayImmutableCollection, elements_, IOSObjectArray *)
#endif

#if !defined (_ComGoogleCommonCollectImmutableCollection_SerializedForm_) && (ComGoogleCommonCollectImmutableCollection_INCLUDE_ALL || ComGoogleCommonCollectImmutableCollection_SerializedForm_INCLUDE)
#define _ComGoogleCommonCollectImmutableCollection_SerializedForm_

@class IOSObjectArray;

#define JavaIoSerializable_RESTRICT 1
#define JavaIoSerializable_INCLUDE 1
#include "java/io/Serializable.h"

#define ComGoogleCommonCollectImmutableCollection_SerializedForm_serialVersionUID 0

@interface ComGoogleCommonCollectImmutableCollection_SerializedForm : NSObject < JavaIoSerializable > {
 @public
  IOSObjectArray *elements_;
}

- (id)initWithNSObjectArray:(IOSObjectArray *)elements;
- (id)readResolve;
- (void)copyAllFieldsTo:(ComGoogleCommonCollectImmutableCollection_SerializedForm *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectImmutableCollection_SerializedForm, elements_, IOSObjectArray *)
#endif

#if !defined (_ComGoogleCommonCollectImmutableCollection_Builder_) && (ComGoogleCommonCollectImmutableCollection_INCLUDE_ALL || ComGoogleCommonCollectImmutableCollection_Builder_INCLUDE)
#define _ComGoogleCommonCollectImmutableCollection_Builder_

@class ComGoogleCommonCollectImmutableCollection;
@class IOSObjectArray;
@protocol JavaLangIterable;
@protocol JavaUtilIterator;

#define ComGoogleCommonCollectImmutableCollection_Builder_DEFAULT_INITIAL_CAPACITY 4

@interface ComGoogleCommonCollectImmutableCollection_Builder : NSObject {
}

+ (int)DEFAULT_INITIAL_CAPACITY;
+ (int)expandedCapacityWithInt:(int)oldCapacity
                       withInt:(int)minCapacity;
- (id)init;
- (ComGoogleCommonCollectImmutableCollection_Builder *)addWithId:(id)element;
- (ComGoogleCommonCollectImmutableCollection_Builder *)addWithNSObjectArray:(IOSObjectArray *)elements;
- (ComGoogleCommonCollectImmutableCollection_Builder *)addAllWithJavaLangIterable:(id<JavaLangIterable>)elements;
- (ComGoogleCommonCollectImmutableCollection_Builder *)addAllWithJavaUtilIterator:(id<JavaUtilIterator>)elements;
- (ComGoogleCommonCollectImmutableCollection *)build;
@end
#endif
