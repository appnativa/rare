//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/guava/sources/com/google/common/base/Predicates.java
//
//  Created by tball on 11/23/13.
//

#import "JreEmulation.h"

#if !ComGoogleCommonBasePredicates_RESTRICT
#define ComGoogleCommonBasePredicates_INCLUDE_ALL 1
#endif
#undef ComGoogleCommonBasePredicates_RESTRICT
#if ComGoogleCommonBasePredicates_ObjectPredicateEnum_$4_INCLUDE
#define ComGoogleCommonBasePredicates_ObjectPredicateEnum_INCLUDE 1
#endif
#if ComGoogleCommonBasePredicates_ObjectPredicateEnum_$3_INCLUDE
#define ComGoogleCommonBasePredicates_ObjectPredicateEnum_INCLUDE 1
#endif
#if ComGoogleCommonBasePredicates_ObjectPredicateEnum_$2_INCLUDE
#define ComGoogleCommonBasePredicates_ObjectPredicateEnum_INCLUDE 1
#endif
#if ComGoogleCommonBasePredicates_ObjectPredicateEnum_$1_INCLUDE
#define ComGoogleCommonBasePredicates_ObjectPredicateEnum_INCLUDE 1
#endif

#if !defined (_ComGoogleCommonBasePredicates_) && (ComGoogleCommonBasePredicates_INCLUDE_ALL || ComGoogleCommonBasePredicates_INCLUDE)
#define _ComGoogleCommonBasePredicates_

@class ComGoogleCommonBaseJoiner;
@class IOSClass;
@class IOSObjectArray;
@class JavaUtilRegexPattern;
@protocol ComGoogleCommonBaseFunction;
@protocol ComGoogleCommonBasePredicate;
@protocol JavaLangIterable;
@protocol JavaUtilCollection;
@protocol JavaUtilList;

@interface ComGoogleCommonBasePredicates : NSObject {
}

+ (ComGoogleCommonBaseJoiner *)COMMA_JOINER;
- (id)init;
+ (id<ComGoogleCommonBasePredicate>)alwaysTrue;
+ (id<ComGoogleCommonBasePredicate>)alwaysFalse;
+ (id<ComGoogleCommonBasePredicate>)isNull;
+ (id<ComGoogleCommonBasePredicate>)notNull;
+ (id<ComGoogleCommonBasePredicate>)not__WithComGoogleCommonBasePredicate:(id<ComGoogleCommonBasePredicate>)predicate;
+ (id<ComGoogleCommonBasePredicate>)and__WithJavaLangIterable:(id<JavaLangIterable>)components;
+ (id<ComGoogleCommonBasePredicate>)and__WithComGoogleCommonBasePredicateArray:(IOSObjectArray *)components;
+ (id<ComGoogleCommonBasePredicate>)and__WithComGoogleCommonBasePredicate:(id<ComGoogleCommonBasePredicate>)first
                                         withComGoogleCommonBasePredicate:(id<ComGoogleCommonBasePredicate>)second;
+ (id<ComGoogleCommonBasePredicate>)or__WithJavaLangIterable:(id<JavaLangIterable>)components;
+ (id<ComGoogleCommonBasePredicate>)or__WithComGoogleCommonBasePredicateArray:(IOSObjectArray *)components;
+ (id<ComGoogleCommonBasePredicate>)or__WithComGoogleCommonBasePredicate:(id<ComGoogleCommonBasePredicate>)first
                                        withComGoogleCommonBasePredicate:(id<ComGoogleCommonBasePredicate>)second;
+ (id<ComGoogleCommonBasePredicate>)equalToWithId:(id)target;
+ (id<ComGoogleCommonBasePredicate>)instanceOfWithIOSClass:(IOSClass *)clazz;
+ (id<ComGoogleCommonBasePredicate>)assignableFromWithIOSClass:(IOSClass *)clazz;
+ (id<ComGoogleCommonBasePredicate>)inWithJavaUtilCollection:(id<JavaUtilCollection>)target;
+ (id<ComGoogleCommonBasePredicate>)composeWithComGoogleCommonBasePredicate:(id<ComGoogleCommonBasePredicate>)predicate
                                            withComGoogleCommonBaseFunction:(id<ComGoogleCommonBaseFunction>)function;
+ (id<ComGoogleCommonBasePredicate>)containsPatternWithNSString:(NSString *)pattern;
+ (id<ComGoogleCommonBasePredicate>)containsWithJavaUtilRegexPattern:(JavaUtilRegexPattern *)pattern;
+ (id<JavaUtilList>)asListWithComGoogleCommonBasePredicate:(id<ComGoogleCommonBasePredicate>)first
                          withComGoogleCommonBasePredicate:(id<ComGoogleCommonBasePredicate>)second;
+ (id<JavaUtilList>)defensiveCopyWithNSObjectArray:(IOSObjectArray *)array;
+ (id<JavaUtilList>)defensiveCopyWithJavaLangIterable:(id<JavaLangIterable>)iterable;
@end
#endif

#if !defined (_ComGoogleCommonBasePredicates_ObjectPredicateEnum_) && (ComGoogleCommonBasePredicates_INCLUDE_ALL || ComGoogleCommonBasePredicates_ObjectPredicateEnum_INCLUDE)
#define _ComGoogleCommonBasePredicates_ObjectPredicateEnum_

#define JavaLangEnum_RESTRICT 1
#define JavaLangEnum_INCLUDE 1
#include "java/lang/Enum.h"

#define ComGoogleCommonBasePredicate_RESTRICT 1
#define ComGoogleCommonBasePredicate_INCLUDE 1
#include "com/google/common/base/Predicate.h"

typedef enum {
  ComGoogleCommonBasePredicates_ObjectPredicate_ALWAYS_TRUE = 0,
  ComGoogleCommonBasePredicates_ObjectPredicate_ALWAYS_FALSE = 1,
  ComGoogleCommonBasePredicates_ObjectPredicate_IS_NULL = 2,
  ComGoogleCommonBasePredicates_ObjectPredicate_NOT_NULL = 3,
} ComGoogleCommonBasePredicates_ObjectPredicate;

@interface ComGoogleCommonBasePredicates_ObjectPredicateEnum : JavaLangEnum < NSCopying, ComGoogleCommonBasePredicate > {
}
+ (ComGoogleCommonBasePredicates_ObjectPredicateEnum *)ALWAYS_TRUE;
+ (ComGoogleCommonBasePredicates_ObjectPredicateEnum *)ALWAYS_FALSE;
+ (ComGoogleCommonBasePredicates_ObjectPredicateEnum *)IS_NULL;
+ (ComGoogleCommonBasePredicates_ObjectPredicateEnum *)NOT_NULL;
+ (IOSObjectArray *)values;
+ (ComGoogleCommonBasePredicates_ObjectPredicateEnum *)valueOfWithNSString:(NSString *)name;
- (id)copyWithZone:(NSZone *)zone;
- (id<ComGoogleCommonBasePredicate>)withNarrowedType;
- (BOOL)applyWithId:(id)param0;
- (id)initWithNSString:(NSString *)__name withInt:(int)__ordinal;
@end
#endif

#if !defined (_ComGoogleCommonBasePredicates_ObjectPredicateEnum_$1_) && (ComGoogleCommonBasePredicates_INCLUDE_ALL || ComGoogleCommonBasePredicates_ObjectPredicateEnum_$1_INCLUDE)
#define _ComGoogleCommonBasePredicates_ObjectPredicateEnum_$1_

@interface ComGoogleCommonBasePredicates_ObjectPredicateEnum_$1 : ComGoogleCommonBasePredicates_ObjectPredicateEnum {
}

- (BOOL)applyWithId:(id)o;
- (id)initWithNSString:(NSString *)__name withInt:(int)__ordinal;
@end
#endif

#if !defined (_ComGoogleCommonBasePredicates_ObjectPredicateEnum_$2_) && (ComGoogleCommonBasePredicates_INCLUDE_ALL || ComGoogleCommonBasePredicates_ObjectPredicateEnum_$2_INCLUDE)
#define _ComGoogleCommonBasePredicates_ObjectPredicateEnum_$2_

@interface ComGoogleCommonBasePredicates_ObjectPredicateEnum_$2 : ComGoogleCommonBasePredicates_ObjectPredicateEnum {
}

- (BOOL)applyWithId:(id)o;
- (id)initWithNSString:(NSString *)__name withInt:(int)__ordinal;
@end
#endif

#if !defined (_ComGoogleCommonBasePredicates_ObjectPredicateEnum_$3_) && (ComGoogleCommonBasePredicates_INCLUDE_ALL || ComGoogleCommonBasePredicates_ObjectPredicateEnum_$3_INCLUDE)
#define _ComGoogleCommonBasePredicates_ObjectPredicateEnum_$3_

@interface ComGoogleCommonBasePredicates_ObjectPredicateEnum_$3 : ComGoogleCommonBasePredicates_ObjectPredicateEnum {
}

- (BOOL)applyWithId:(id)o;
- (id)initWithNSString:(NSString *)__name withInt:(int)__ordinal;
@end
#endif

#if !defined (_ComGoogleCommonBasePredicates_ObjectPredicateEnum_$4_) && (ComGoogleCommonBasePredicates_INCLUDE_ALL || ComGoogleCommonBasePredicates_ObjectPredicateEnum_$4_INCLUDE)
#define _ComGoogleCommonBasePredicates_ObjectPredicateEnum_$4_

@interface ComGoogleCommonBasePredicates_ObjectPredicateEnum_$4 : ComGoogleCommonBasePredicates_ObjectPredicateEnum {
}

- (BOOL)applyWithId:(id)o;
- (id)initWithNSString:(NSString *)__name withInt:(int)__ordinal;
@end
#endif

#if !defined (_ComGoogleCommonBasePredicates_NotPredicate_) && (ComGoogleCommonBasePredicates_INCLUDE_ALL || ComGoogleCommonBasePredicates_NotPredicate_INCLUDE)
#define _ComGoogleCommonBasePredicates_NotPredicate_

#define ComGoogleCommonBasePredicate_RESTRICT 1
#define ComGoogleCommonBasePredicate_INCLUDE 1
#include "com/google/common/base/Predicate.h"

#define JavaIoSerializable_RESTRICT 1
#define JavaIoSerializable_INCLUDE 1
#include "java/io/Serializable.h"

#define ComGoogleCommonBasePredicates_NotPredicate_serialVersionUID 0

@interface ComGoogleCommonBasePredicates_NotPredicate : NSObject < ComGoogleCommonBasePredicate, JavaIoSerializable > {
 @public
  id<ComGoogleCommonBasePredicate> predicate_;
}

- (id)initWithComGoogleCommonBasePredicate:(id<ComGoogleCommonBasePredicate>)predicate;
- (BOOL)applyWithId:(id)t;
- (NSUInteger)hash;
- (BOOL)isEqual:(id)obj;
- (NSString *)description;
- (void)copyAllFieldsTo:(ComGoogleCommonBasePredicates_NotPredicate *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonBasePredicates_NotPredicate, predicate_, id<ComGoogleCommonBasePredicate>)
#endif

#if !defined (_ComGoogleCommonBasePredicates_AndPredicate_) && (ComGoogleCommonBasePredicates_INCLUDE_ALL || ComGoogleCommonBasePredicates_AndPredicate_INCLUDE)
#define _ComGoogleCommonBasePredicates_AndPredicate_

@protocol JavaUtilList;

#define ComGoogleCommonBasePredicate_RESTRICT 1
#define ComGoogleCommonBasePredicate_INCLUDE 1
#include "com/google/common/base/Predicate.h"

#define JavaIoSerializable_RESTRICT 1
#define JavaIoSerializable_INCLUDE 1
#include "java/io/Serializable.h"

#define ComGoogleCommonBasePredicates_AndPredicate_serialVersionUID 0

@interface ComGoogleCommonBasePredicates_AndPredicate : NSObject < ComGoogleCommonBasePredicate, JavaIoSerializable > {
 @public
  id<JavaUtilList> components_;
}

- (id)initWithJavaUtilList:(id<JavaUtilList>)components;
- (BOOL)applyWithId:(id)t;
- (NSUInteger)hash;
- (BOOL)isEqual:(id)obj;
- (NSString *)description;
- (void)copyAllFieldsTo:(ComGoogleCommonBasePredicates_AndPredicate *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonBasePredicates_AndPredicate, components_, id<JavaUtilList>)
#endif

#if !defined (_ComGoogleCommonBasePredicates_OrPredicate_) && (ComGoogleCommonBasePredicates_INCLUDE_ALL || ComGoogleCommonBasePredicates_OrPredicate_INCLUDE)
#define _ComGoogleCommonBasePredicates_OrPredicate_

@protocol JavaUtilList;

#define ComGoogleCommonBasePredicate_RESTRICT 1
#define ComGoogleCommonBasePredicate_INCLUDE 1
#include "com/google/common/base/Predicate.h"

#define JavaIoSerializable_RESTRICT 1
#define JavaIoSerializable_INCLUDE 1
#include "java/io/Serializable.h"

#define ComGoogleCommonBasePredicates_OrPredicate_serialVersionUID 0

@interface ComGoogleCommonBasePredicates_OrPredicate : NSObject < ComGoogleCommonBasePredicate, JavaIoSerializable > {
 @public
  id<JavaUtilList> components_;
}

- (id)initWithJavaUtilList:(id<JavaUtilList>)components;
- (BOOL)applyWithId:(id)t;
- (NSUInteger)hash;
- (BOOL)isEqual:(id)obj;
- (NSString *)description;
- (void)copyAllFieldsTo:(ComGoogleCommonBasePredicates_OrPredicate *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonBasePredicates_OrPredicate, components_, id<JavaUtilList>)
#endif

#if !defined (_ComGoogleCommonBasePredicates_IsEqualToPredicate_) && (ComGoogleCommonBasePredicates_INCLUDE_ALL || ComGoogleCommonBasePredicates_IsEqualToPredicate_INCLUDE)
#define _ComGoogleCommonBasePredicates_IsEqualToPredicate_

#define ComGoogleCommonBasePredicate_RESTRICT 1
#define ComGoogleCommonBasePredicate_INCLUDE 1
#include "com/google/common/base/Predicate.h"

#define JavaIoSerializable_RESTRICT 1
#define JavaIoSerializable_INCLUDE 1
#include "java/io/Serializable.h"

#define ComGoogleCommonBasePredicates_IsEqualToPredicate_serialVersionUID 0

@interface ComGoogleCommonBasePredicates_IsEqualToPredicate : NSObject < ComGoogleCommonBasePredicate, JavaIoSerializable > {
 @public
  id target_;
}

- (id)initWithId:(id)target;
- (BOOL)applyWithId:(id)t;
- (NSUInteger)hash;
- (BOOL)isEqual:(id)obj;
- (NSString *)description;
- (void)copyAllFieldsTo:(ComGoogleCommonBasePredicates_IsEqualToPredicate *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonBasePredicates_IsEqualToPredicate, target_, id)
#endif

#if !defined (_ComGoogleCommonBasePredicates_InstanceOfPredicate_) && (ComGoogleCommonBasePredicates_INCLUDE_ALL || ComGoogleCommonBasePredicates_InstanceOfPredicate_INCLUDE)
#define _ComGoogleCommonBasePredicates_InstanceOfPredicate_

@class IOSClass;

#define ComGoogleCommonBasePredicate_RESTRICT 1
#define ComGoogleCommonBasePredicate_INCLUDE 1
#include "com/google/common/base/Predicate.h"

#define JavaIoSerializable_RESTRICT 1
#define JavaIoSerializable_INCLUDE 1
#include "java/io/Serializable.h"

#define ComGoogleCommonBasePredicates_InstanceOfPredicate_serialVersionUID 0

@interface ComGoogleCommonBasePredicates_InstanceOfPredicate : NSObject < ComGoogleCommonBasePredicate, JavaIoSerializable > {
 @public
  IOSClass *clazz_;
}

- (id)initWithIOSClass:(IOSClass *)clazz;
- (BOOL)applyWithId:(id)o;
- (NSUInteger)hash;
- (BOOL)isEqual:(id)obj;
- (NSString *)description;
- (void)copyAllFieldsTo:(ComGoogleCommonBasePredicates_InstanceOfPredicate *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonBasePredicates_InstanceOfPredicate, clazz_, IOSClass *)
#endif

#if !defined (_ComGoogleCommonBasePredicates_AssignableFromPredicate_) && (ComGoogleCommonBasePredicates_INCLUDE_ALL || ComGoogleCommonBasePredicates_AssignableFromPredicate_INCLUDE)
#define _ComGoogleCommonBasePredicates_AssignableFromPredicate_

@class IOSClass;

#define ComGoogleCommonBasePredicate_RESTRICT 1
#define ComGoogleCommonBasePredicate_INCLUDE 1
#include "com/google/common/base/Predicate.h"

#define JavaIoSerializable_RESTRICT 1
#define JavaIoSerializable_INCLUDE 1
#include "java/io/Serializable.h"

#define ComGoogleCommonBasePredicates_AssignableFromPredicate_serialVersionUID 0

@interface ComGoogleCommonBasePredicates_AssignableFromPredicate : NSObject < ComGoogleCommonBasePredicate, JavaIoSerializable > {
 @public
  IOSClass *clazz_;
}

- (id)initWithIOSClass:(IOSClass *)clazz;
- (BOOL)applyWithId:(IOSClass *)input;
- (NSUInteger)hash;
- (BOOL)isEqual:(id)obj;
- (NSString *)description;
- (void)copyAllFieldsTo:(ComGoogleCommonBasePredicates_AssignableFromPredicate *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonBasePredicates_AssignableFromPredicate, clazz_, IOSClass *)
#endif

#if !defined (_ComGoogleCommonBasePredicates_InPredicate_) && (ComGoogleCommonBasePredicates_INCLUDE_ALL || ComGoogleCommonBasePredicates_InPredicate_INCLUDE)
#define _ComGoogleCommonBasePredicates_InPredicate_

@protocol JavaUtilCollection;

#define ComGoogleCommonBasePredicate_RESTRICT 1
#define ComGoogleCommonBasePredicate_INCLUDE 1
#include "com/google/common/base/Predicate.h"

#define JavaIoSerializable_RESTRICT 1
#define JavaIoSerializable_INCLUDE 1
#include "java/io/Serializable.h"

#define ComGoogleCommonBasePredicates_InPredicate_serialVersionUID 0

@interface ComGoogleCommonBasePredicates_InPredicate : NSObject < ComGoogleCommonBasePredicate, JavaIoSerializable > {
 @public
  id<JavaUtilCollection> target_;
}

- (id)initWithJavaUtilCollection:(id<JavaUtilCollection>)target;
- (BOOL)applyWithId:(id)t;
- (BOOL)isEqual:(id)obj;
- (NSUInteger)hash;
- (NSString *)description;
- (void)copyAllFieldsTo:(ComGoogleCommonBasePredicates_InPredicate *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonBasePredicates_InPredicate, target_, id<JavaUtilCollection>)
#endif

#if !defined (_ComGoogleCommonBasePredicates_CompositionPredicate_) && (ComGoogleCommonBasePredicates_INCLUDE_ALL || ComGoogleCommonBasePredicates_CompositionPredicate_INCLUDE)
#define _ComGoogleCommonBasePredicates_CompositionPredicate_

@protocol ComGoogleCommonBaseFunction;

#define ComGoogleCommonBasePredicate_RESTRICT 1
#define ComGoogleCommonBasePredicate_INCLUDE 1
#include "com/google/common/base/Predicate.h"

#define JavaIoSerializable_RESTRICT 1
#define JavaIoSerializable_INCLUDE 1
#include "java/io/Serializable.h"

#define ComGoogleCommonBasePredicates_CompositionPredicate_serialVersionUID 0

@interface ComGoogleCommonBasePredicates_CompositionPredicate : NSObject < ComGoogleCommonBasePredicate, JavaIoSerializable > {
 @public
  id<ComGoogleCommonBasePredicate> p_;
  id<ComGoogleCommonBaseFunction> f_;
}

- (id)initWithComGoogleCommonBasePredicate:(id<ComGoogleCommonBasePredicate>)p
           withComGoogleCommonBaseFunction:(id<ComGoogleCommonBaseFunction>)f;
- (BOOL)applyWithId:(id)a;
- (BOOL)isEqual:(id)obj;
- (NSUInteger)hash;
- (NSString *)description;
- (void)copyAllFieldsTo:(ComGoogleCommonBasePredicates_CompositionPredicate *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonBasePredicates_CompositionPredicate, p_, id<ComGoogleCommonBasePredicate>)
J2OBJC_FIELD_SETTER(ComGoogleCommonBasePredicates_CompositionPredicate, f_, id<ComGoogleCommonBaseFunction>)
#endif

#if !defined (_ComGoogleCommonBasePredicates_ContainsPatternPredicate_) && (ComGoogleCommonBasePredicates_INCLUDE_ALL || ComGoogleCommonBasePredicates_ContainsPatternPredicate_INCLUDE)
#define _ComGoogleCommonBasePredicates_ContainsPatternPredicate_

@class JavaUtilRegexPattern;
@protocol JavaLangCharSequence;

#define ComGoogleCommonBasePredicate_RESTRICT 1
#define ComGoogleCommonBasePredicate_INCLUDE 1
#include "com/google/common/base/Predicate.h"

#define JavaIoSerializable_RESTRICT 1
#define JavaIoSerializable_INCLUDE 1
#include "java/io/Serializable.h"

#define ComGoogleCommonBasePredicates_ContainsPatternPredicate_serialVersionUID 0

@interface ComGoogleCommonBasePredicates_ContainsPatternPredicate : NSObject < ComGoogleCommonBasePredicate, JavaIoSerializable > {
 @public
  JavaUtilRegexPattern *pattern_;
}

- (id)initWithJavaUtilRegexPattern:(JavaUtilRegexPattern *)pattern;
- (id)initWithNSString:(NSString *)patternStr;
- (BOOL)applyWithId:(id<JavaLangCharSequence>)t;
- (NSUInteger)hash;
- (BOOL)isEqual:(id)obj;
- (NSString *)description;
- (void)copyAllFieldsTo:(ComGoogleCommonBasePredicates_ContainsPatternPredicate *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonBasePredicates_ContainsPatternPredicate, pattern_, JavaUtilRegexPattern *)
#endif