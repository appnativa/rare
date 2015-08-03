//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/guava/sources/com/google/common/collect/ImmutableEnumSet.java
//
//  Created by tball on 11/23/13.
//

#import "JreEmulation.h"

#if !ComGoogleCommonCollectImmutableEnumSet_RESTRICT
#define ComGoogleCommonCollectImmutableEnumSet_INCLUDE_ALL 1
#endif
#undef ComGoogleCommonCollectImmutableEnumSet_RESTRICT

#if !defined (_ComGoogleCommonCollectImmutableEnumSet_) && (ComGoogleCommonCollectImmutableEnumSet_INCLUDE_ALL || ComGoogleCommonCollectImmutableEnumSet_INCLUDE)
#define _ComGoogleCommonCollectImmutableEnumSet_

@class ComGoogleCommonCollectUnmodifiableIterator;
@class IOSObjectArray;
@class JavaUtilEnumSet;
@protocol JavaUtilCollection;

#define ComGoogleCommonCollectImmutableSet_RESTRICT 1
#define ComGoogleCommonCollectImmutableSet_INCLUDE 1
#include "com/google/common/collect/ImmutableSet.h"

@interface ComGoogleCommonCollectImmutableEnumSet : ComGoogleCommonCollectImmutableSet {
 @public
  JavaUtilEnumSet *delegate_;
  int hashCode__;
}

+ (ComGoogleCommonCollectImmutableSet *)asImmutableWithJavaUtilEnumSet:(JavaUtilEnumSet *)set;
- (id)initWithJavaUtilEnumSet:(JavaUtilEnumSet *)delegate;
- (BOOL)isPartialView;
- (ComGoogleCommonCollectUnmodifiableIterator *)iterator;
- (int)size;
- (BOOL)containsWithId:(id)object;
- (BOOL)containsAllWithJavaUtilCollection:(id<JavaUtilCollection>)collection;
- (BOOL)isEmpty;
- (IOSObjectArray *)toArray;
- (IOSObjectArray *)toArrayWithNSObjectArray:(IOSObjectArray *)array;
- (BOOL)isEqual:(id)object;
- (NSUInteger)hash;
- (NSString *)description;
- (id)writeReplace;
- (void)copyAllFieldsTo:(ComGoogleCommonCollectImmutableEnumSet *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectImmutableEnumSet, delegate_, JavaUtilEnumSet *)
#endif

#if !defined (_ComGoogleCommonCollectImmutableEnumSet_EnumSerializedForm_) && (ComGoogleCommonCollectImmutableEnumSet_INCLUDE_ALL || ComGoogleCommonCollectImmutableEnumSet_EnumSerializedForm_INCLUDE)
#define _ComGoogleCommonCollectImmutableEnumSet_EnumSerializedForm_

@class JavaUtilEnumSet;

#define JavaIoSerializable_RESTRICT 1
#define JavaIoSerializable_INCLUDE 1
#include "java/io/Serializable.h"

#define ComGoogleCommonCollectImmutableEnumSet_EnumSerializedForm_serialVersionUID 0

@interface ComGoogleCommonCollectImmutableEnumSet_EnumSerializedForm : NSObject < JavaIoSerializable > {
 @public
  JavaUtilEnumSet *delegate_;
}

- (id)initWithJavaUtilEnumSet:(JavaUtilEnumSet *)delegate;
- (id)readResolve;
- (void)copyAllFieldsTo:(ComGoogleCommonCollectImmutableEnumSet_EnumSerializedForm *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectImmutableEnumSet_EnumSerializedForm, delegate_, JavaUtilEnumSet *)
#endif
