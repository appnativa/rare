//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/guava/sources/com/google/common/primitives/Doubles.java
//
//  Created by tball on 11/23/13.
//

#import "JreEmulation.h"

#if !ComGoogleCommonPrimitivesDoubles_RESTRICT
#define ComGoogleCommonPrimitivesDoubles_INCLUDE_ALL 1
#endif
#undef ComGoogleCommonPrimitivesDoubles_RESTRICT

#if !defined (_ComGoogleCommonPrimitivesDoubles_) && (ComGoogleCommonPrimitivesDoubles_INCLUDE_ALL || ComGoogleCommonPrimitivesDoubles_INCLUDE)
#define _ComGoogleCommonPrimitivesDoubles_

@class IOSDoubleArray;
@class IOSObjectArray;
@class JavaLangDouble;
@class JavaUtilRegexPattern;
@protocol JavaUtilCollection;
@protocol JavaUtilComparator;
@protocol JavaUtilList;

#define ComGoogleCommonPrimitivesDoubles_BYTES 8

@interface ComGoogleCommonPrimitivesDoubles : NSObject {
}

+ (int)BYTES;
+ (JavaUtilRegexPattern *)FLOATING_POINT_PATTERN;
- (id)init;
+ (int)hashCodeWithDouble:(double)value;
+ (int)compareWithDouble:(double)a
              withDouble:(double)b;
+ (BOOL)isFiniteWithDouble:(double)value;
+ (BOOL)containsWithDoubleArray:(IOSDoubleArray *)array
                     withDouble:(double)target;
+ (int)indexOfWithDoubleArray:(IOSDoubleArray *)array
                   withDouble:(double)target;
+ (int)indexOfWithDoubleArray:(IOSDoubleArray *)array
                   withDouble:(double)target
                      withInt:(int)start
                      withInt:(int)end;
+ (int)indexOfWithDoubleArray:(IOSDoubleArray *)array
              withDoubleArray:(IOSDoubleArray *)target;
+ (int)lastIndexOfWithDoubleArray:(IOSDoubleArray *)array
                       withDouble:(double)target;
+ (int)lastIndexOfWithDoubleArray:(IOSDoubleArray *)array
                       withDouble:(double)target
                          withInt:(int)start
                          withInt:(int)end;
+ (double)minWithDoubleArray:(IOSDoubleArray *)array;
+ (double)maxWithDoubleArray:(IOSDoubleArray *)array;
+ (IOSDoubleArray *)concatWithDoubleArray2:(IOSObjectArray *)arrays;
+ (IOSDoubleArray *)ensureCapacityWithDoubleArray:(IOSDoubleArray *)array
                                          withInt:(int)minLength
                                          withInt:(int)padding;
+ (IOSDoubleArray *)copyOfWithDoubleArray:(IOSDoubleArray *)original
                                  withInt:(int)length OBJC_METHOD_FAMILY_NONE;
+ (NSString *)joinWithNSString:(NSString *)separator
               withDoubleArray:(IOSDoubleArray *)array;
+ (id<JavaUtilComparator>)lexicographicalComparator;
+ (IOSDoubleArray *)toArrayWithJavaUtilCollection:(id<JavaUtilCollection>)collection;
+ (id<JavaUtilList>)asListWithDoubleArray:(IOSDoubleArray *)backingArray;
+ (JavaUtilRegexPattern *)fpPattern;
+ (JavaLangDouble *)tryParseWithNSString:(NSString *)string;
@end
#endif

#if !defined (_ComGoogleCommonPrimitivesDoubles_LexicographicalComparatorEnum_) && (ComGoogleCommonPrimitivesDoubles_INCLUDE_ALL || ComGoogleCommonPrimitivesDoubles_LexicographicalComparatorEnum_INCLUDE)
#define _ComGoogleCommonPrimitivesDoubles_LexicographicalComparatorEnum_

@class IOSDoubleArray;

#define JavaLangEnum_RESTRICT 1
#define JavaLangEnum_INCLUDE 1
#include "java/lang/Enum.h"

#define JavaUtilComparator_RESTRICT 1
#define JavaUtilComparator_INCLUDE 1
#include "java/util/Comparator.h"

typedef enum {
  ComGoogleCommonPrimitivesDoubles_LexicographicalComparator_INSTANCE = 0,
} ComGoogleCommonPrimitivesDoubles_LexicographicalComparator;

@interface ComGoogleCommonPrimitivesDoubles_LexicographicalComparatorEnum : JavaLangEnum < NSCopying, JavaUtilComparator > {
}
+ (ComGoogleCommonPrimitivesDoubles_LexicographicalComparatorEnum *)INSTANCE;
+ (IOSObjectArray *)values;
+ (ComGoogleCommonPrimitivesDoubles_LexicographicalComparatorEnum *)valueOfWithNSString:(NSString *)name;
- (id)copyWithZone:(NSZone *)zone;
- (int)compareWithId:(IOSDoubleArray *)left
              withId:(IOSDoubleArray *)right;
- (id)initWithNSString:(NSString *)__name withInt:(int)__ordinal;
@end
#endif

#if !defined (_ComGoogleCommonPrimitivesDoubles_DoubleArrayAsList_) && (ComGoogleCommonPrimitivesDoubles_INCLUDE_ALL || ComGoogleCommonPrimitivesDoubles_DoubleArrayAsList_INCLUDE)
#define _ComGoogleCommonPrimitivesDoubles_DoubleArrayAsList_

@class IOSDoubleArray;
@class JavaLangDouble;
@protocol JavaUtilList;

#define JavaUtilAbstractList_RESTRICT 1
#define JavaUtilAbstractList_INCLUDE 1
#include "java/util/AbstractList.h"

#define JavaUtilRandomAccess_RESTRICT 1
#define JavaUtilRandomAccess_INCLUDE 1
#include "java/util/RandomAccess.h"

#define JavaIoSerializable_RESTRICT 1
#define JavaIoSerializable_INCLUDE 1
#include "java/io/Serializable.h"

#define ComGoogleCommonPrimitivesDoubles_DoubleArrayAsList_serialVersionUID 0

@interface ComGoogleCommonPrimitivesDoubles_DoubleArrayAsList : JavaUtilAbstractList < JavaUtilRandomAccess, JavaIoSerializable > {
 @public
  IOSDoubleArray *array_;
  int start_;
  int end_;
}

- (id)initWithDoubleArray:(IOSDoubleArray *)array;
- (id)initWithDoubleArray:(IOSDoubleArray *)array
                  withInt:(int)start
                  withInt:(int)end;
- (int)size;
- (BOOL)isEmpty;
- (JavaLangDouble *)getWithInt:(int)index;
- (BOOL)containsWithId:(id)target;
- (int)indexOfWithId:(id)target;
- (int)lastIndexOfWithId:(id)target;
- (JavaLangDouble *)setWithInt:(int)index
                        withId:(JavaLangDouble *)element;
- (id<JavaUtilList>)subListWithInt:(int)fromIndex
                           withInt:(int)toIndex;
- (BOOL)isEqual:(id)object;
- (NSUInteger)hash;
- (NSString *)description;
- (IOSDoubleArray *)toDoubleArray;
- (void)copyAllFieldsTo:(ComGoogleCommonPrimitivesDoubles_DoubleArrayAsList *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonPrimitivesDoubles_DoubleArrayAsList, array_, IOSDoubleArray *)
#endif