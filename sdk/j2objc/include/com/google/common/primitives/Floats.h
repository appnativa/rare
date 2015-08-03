//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/guava/sources/com/google/common/primitives/Floats.java
//
//  Created by tball on 11/23/13.
//

#import "JreEmulation.h"

#if !ComGoogleCommonPrimitivesFloats_RESTRICT
#define ComGoogleCommonPrimitivesFloats_INCLUDE_ALL 1
#endif
#undef ComGoogleCommonPrimitivesFloats_RESTRICT

#if !defined (_ComGoogleCommonPrimitivesFloats_) && (ComGoogleCommonPrimitivesFloats_INCLUDE_ALL || ComGoogleCommonPrimitivesFloats_INCLUDE)
#define _ComGoogleCommonPrimitivesFloats_

@class IOSFloatArray;
@class IOSObjectArray;
@class JavaLangFloat;
@protocol JavaUtilCollection;
@protocol JavaUtilComparator;
@protocol JavaUtilList;

#define ComGoogleCommonPrimitivesFloats_BYTES 4

@interface ComGoogleCommonPrimitivesFloats : NSObject {
}

+ (int)BYTES;
- (id)init;
+ (int)hashCodeWithFloat:(float)value;
+ (int)compareWithFloat:(float)a
              withFloat:(float)b;
+ (BOOL)isFiniteWithFloat:(float)value;
+ (BOOL)containsWithFloatArray:(IOSFloatArray *)array
                     withFloat:(float)target;
+ (int)indexOfWithFloatArray:(IOSFloatArray *)array
                   withFloat:(float)target;
+ (int)indexOfWithFloatArray:(IOSFloatArray *)array
                   withFloat:(float)target
                     withInt:(int)start
                     withInt:(int)end;
+ (int)indexOfWithFloatArray:(IOSFloatArray *)array
              withFloatArray:(IOSFloatArray *)target;
+ (int)lastIndexOfWithFloatArray:(IOSFloatArray *)array
                       withFloat:(float)target;
+ (int)lastIndexOfWithFloatArray:(IOSFloatArray *)array
                       withFloat:(float)target
                         withInt:(int)start
                         withInt:(int)end;
+ (float)minWithFloatArray:(IOSFloatArray *)array;
+ (float)maxWithFloatArray:(IOSFloatArray *)array;
+ (IOSFloatArray *)concatWithFloatArray2:(IOSObjectArray *)arrays;
+ (IOSFloatArray *)ensureCapacityWithFloatArray:(IOSFloatArray *)array
                                        withInt:(int)minLength
                                        withInt:(int)padding;
+ (IOSFloatArray *)copyOfWithFloatArray:(IOSFloatArray *)original
                                withInt:(int)length OBJC_METHOD_FAMILY_NONE;
+ (NSString *)joinWithNSString:(NSString *)separator
                withFloatArray:(IOSFloatArray *)array;
+ (id<JavaUtilComparator>)lexicographicalComparator;
+ (IOSFloatArray *)toArrayWithJavaUtilCollection:(id<JavaUtilCollection>)collection;
+ (id<JavaUtilList>)asListWithFloatArray:(IOSFloatArray *)backingArray;
+ (JavaLangFloat *)tryParseWithNSString:(NSString *)string;
@end
#endif

#if !defined (_ComGoogleCommonPrimitivesFloats_LexicographicalComparatorEnum_) && (ComGoogleCommonPrimitivesFloats_INCLUDE_ALL || ComGoogleCommonPrimitivesFloats_LexicographicalComparatorEnum_INCLUDE)
#define _ComGoogleCommonPrimitivesFloats_LexicographicalComparatorEnum_

@class IOSFloatArray;

#define JavaLangEnum_RESTRICT 1
#define JavaLangEnum_INCLUDE 1
#include "java/lang/Enum.h"

#define JavaUtilComparator_RESTRICT 1
#define JavaUtilComparator_INCLUDE 1
#include "java/util/Comparator.h"

typedef enum {
  ComGoogleCommonPrimitivesFloats_LexicographicalComparator_INSTANCE = 0,
} ComGoogleCommonPrimitivesFloats_LexicographicalComparator;

@interface ComGoogleCommonPrimitivesFloats_LexicographicalComparatorEnum : JavaLangEnum < NSCopying, JavaUtilComparator > {
}
+ (ComGoogleCommonPrimitivesFloats_LexicographicalComparatorEnum *)INSTANCE;
+ (IOSObjectArray *)values;
+ (ComGoogleCommonPrimitivesFloats_LexicographicalComparatorEnum *)valueOfWithNSString:(NSString *)name;
- (id)copyWithZone:(NSZone *)zone;
- (int)compareWithId:(IOSFloatArray *)left
              withId:(IOSFloatArray *)right;
- (id)initWithNSString:(NSString *)__name withInt:(int)__ordinal;
@end
#endif

#if !defined (_ComGoogleCommonPrimitivesFloats_FloatArrayAsList_) && (ComGoogleCommonPrimitivesFloats_INCLUDE_ALL || ComGoogleCommonPrimitivesFloats_FloatArrayAsList_INCLUDE)
#define _ComGoogleCommonPrimitivesFloats_FloatArrayAsList_

@class IOSFloatArray;
@class JavaLangFloat;
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

#define ComGoogleCommonPrimitivesFloats_FloatArrayAsList_serialVersionUID 0

@interface ComGoogleCommonPrimitivesFloats_FloatArrayAsList : JavaUtilAbstractList < JavaUtilRandomAccess, JavaIoSerializable > {
 @public
  IOSFloatArray *array_;
  int start_;
  int end_;
}

- (id)initWithFloatArray:(IOSFloatArray *)array;
- (id)initWithFloatArray:(IOSFloatArray *)array
                 withInt:(int)start
                 withInt:(int)end;
- (int)size;
- (BOOL)isEmpty;
- (JavaLangFloat *)getWithInt:(int)index;
- (BOOL)containsWithId:(id)target;
- (int)indexOfWithId:(id)target;
- (int)lastIndexOfWithId:(id)target;
- (JavaLangFloat *)setWithInt:(int)index
                       withId:(JavaLangFloat *)element;
- (id<JavaUtilList>)subListWithInt:(int)fromIndex
                           withInt:(int)toIndex;
- (BOOL)isEqual:(id)object;
- (NSUInteger)hash;
- (NSString *)description;
- (IOSFloatArray *)toFloatArray;
- (void)copyAllFieldsTo:(ComGoogleCommonPrimitivesFloats_FloatArrayAsList *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonPrimitivesFloats_FloatArrayAsList, array_, IOSFloatArray *)
#endif
