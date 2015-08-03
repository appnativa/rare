//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/testing/mockito/build_result/java/org/mockito/internal/util/collections/HashCodeAndEqualsSafeSet.java
//
//  Created by tball on 11/23/13.
//

#ifndef _OrgMockitoInternalUtilCollectionsHashCodeAndEqualsSafeSet_H_
#define _OrgMockitoInternalUtilCollectionsHashCodeAndEqualsSafeSet_H_

@class IOSObjectArray;
@class JavaUtilHashSet;
@protocol JavaLangIterable;
@protocol JavaUtilCollection;

#import "JreEmulation.h"
#include "java/util/Iterator.h"
#include "java/util/Set.h"

@interface OrgMockitoInternalUtilCollectionsHashCodeAndEqualsSafeSet : NSObject < JavaUtilSet > {
 @public
  JavaUtilHashSet *backingHashSet_;
}

- (id<JavaUtilIterator>)iterator;
- (int)size;
- (BOOL)isEmpty;
- (BOOL)containsWithId:(id)mock;
- (BOOL)addWithId:(id)mock;
- (BOOL)removeWithId:(id)mock;
- (void)clear;
- (id)clone;
- (BOOL)isEqual:(id)o;
- (NSUInteger)hash;
- (IOSObjectArray *)toArray;
- (IOSObjectArray *)unwrapToWithNSObjectArray:(IOSObjectArray *)array;
- (IOSObjectArray *)toArrayWithNSObjectArray:(IOSObjectArray *)typedArray;
- (BOOL)removeAllWithJavaUtilCollection:(id<JavaUtilCollection>)mocks;
- (BOOL)containsAllWithJavaUtilCollection:(id<JavaUtilCollection>)mocks;
- (BOOL)addAllWithJavaUtilCollection:(id<JavaUtilCollection>)mocks;
- (BOOL)retainAllWithJavaUtilCollection:(id<JavaUtilCollection>)mocks;
- (JavaUtilHashSet *)asWrappedMocksWithJavaUtilCollection:(id<JavaUtilCollection>)mocks;
- (NSString *)description;
+ (OrgMockitoInternalUtilCollectionsHashCodeAndEqualsSafeSet *)ofWithNSObjectArray:(IOSObjectArray *)mocks;
+ (OrgMockitoInternalUtilCollectionsHashCodeAndEqualsSafeSet *)ofWithJavaLangIterable:(id<JavaLangIterable>)objects;
- (id)init;
- (void)copyAllFieldsTo:(OrgMockitoInternalUtilCollectionsHashCodeAndEqualsSafeSet *)other;
@end

J2OBJC_FIELD_SETTER(OrgMockitoInternalUtilCollectionsHashCodeAndEqualsSafeSet, backingHashSet_, JavaUtilHashSet *)

@interface OrgMockitoInternalUtilCollectionsHashCodeAndEqualsSafeSet_$1 : NSObject < JavaUtilIterator > {
 @public
  id<JavaUtilIterator> iterator_;
}

- (BOOL)hasNext;
- (id)next;
- (void)remove;
- (id)initWithOrgMockitoInternalUtilCollectionsHashCodeAndEqualsSafeSet:(OrgMockitoInternalUtilCollectionsHashCodeAndEqualsSafeSet *)outer$;
- (void)copyAllFieldsTo:(OrgMockitoInternalUtilCollectionsHashCodeAndEqualsSafeSet_$1 *)other;
@end

J2OBJC_FIELD_SETTER(OrgMockitoInternalUtilCollectionsHashCodeAndEqualsSafeSet_$1, iterator_, id<JavaUtilIterator>)

#endif // _OrgMockitoInternalUtilCollectionsHashCodeAndEqualsSafeSet_H_
