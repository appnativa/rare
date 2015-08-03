//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/guava/sources/com/google/common/primitives/Chars.java
//
//  Created by tball on 11/23/13.
//

#import "JreEmulation.h"

#if !ComGoogleCommonPrimitivesChars_RESTRICT
#define ComGoogleCommonPrimitivesChars_INCLUDE_ALL 1
#endif
#undef ComGoogleCommonPrimitivesChars_RESTRICT

#if !defined (_ComGoogleCommonPrimitivesChars_) && (ComGoogleCommonPrimitivesChars_INCLUDE_ALL || ComGoogleCommonPrimitivesChars_INCLUDE)
#define _ComGoogleCommonPrimitivesChars_

@class IOSByteArray;
@class IOSCharArray;
@class IOSObjectArray;
@protocol JavaUtilCollection;
@protocol JavaUtilComparator;
@protocol JavaUtilList;

#define ComGoogleCommonPrimitivesChars_BYTES 2

@interface ComGoogleCommonPrimitivesChars : NSObject {
}

+ (int)BYTES;
- (id)init;
+ (int)hashCodeWithChar:(unichar)value;
+ (unichar)checkedCastWithLong:(long long int)value;
+ (unichar)saturatedCastWithLong:(long long int)value;
+ (int)compareWithChar:(unichar)a
              withChar:(unichar)b;
+ (BOOL)containsWithCharArray:(IOSCharArray *)array
                     withChar:(unichar)target;
+ (int)indexOfWithCharArray:(IOSCharArray *)array
                   withChar:(unichar)target;
+ (int)indexOfWithCharArray:(IOSCharArray *)array
                   withChar:(unichar)target
                    withInt:(int)start
                    withInt:(int)end;
+ (int)indexOfWithCharArray:(IOSCharArray *)array
              withCharArray:(IOSCharArray *)target;
+ (int)lastIndexOfWithCharArray:(IOSCharArray *)array
                       withChar:(unichar)target;
+ (int)lastIndexOfWithCharArray:(IOSCharArray *)array
                       withChar:(unichar)target
                        withInt:(int)start
                        withInt:(int)end;
+ (unichar)minWithCharArray:(IOSCharArray *)array;
+ (unichar)maxWithCharArray:(IOSCharArray *)array;
+ (IOSCharArray *)concatWithCharArray2:(IOSObjectArray *)arrays;
+ (IOSByteArray *)toByteArrayWithChar:(unichar)value;
+ (unichar)fromByteArrayWithByteArray:(IOSByteArray *)bytes;
+ (unichar)fromBytesWithByte:(char)b1
                    withByte:(char)b2;
+ (IOSCharArray *)ensureCapacityWithCharArray:(IOSCharArray *)array
                                      withInt:(int)minLength
                                      withInt:(int)padding;
+ (IOSCharArray *)copyOfWithCharArray:(IOSCharArray *)original
                              withInt:(int)length OBJC_METHOD_FAMILY_NONE;
+ (NSString *)joinWithNSString:(NSString *)separator
                 withCharArray:(IOSCharArray *)array;
+ (id<JavaUtilComparator>)lexicographicalComparator;
+ (IOSCharArray *)toArrayWithJavaUtilCollection:(id<JavaUtilCollection>)collection;
+ (id<JavaUtilList>)asListWithCharArray:(IOSCharArray *)backingArray;
@end
#endif

#if !defined (_ComGoogleCommonPrimitivesChars_LexicographicalComparatorEnum_) && (ComGoogleCommonPrimitivesChars_INCLUDE_ALL || ComGoogleCommonPrimitivesChars_LexicographicalComparatorEnum_INCLUDE)
#define _ComGoogleCommonPrimitivesChars_LexicographicalComparatorEnum_

@class IOSCharArray;

#define JavaLangEnum_RESTRICT 1
#define JavaLangEnum_INCLUDE 1
#include "java/lang/Enum.h"

#define JavaUtilComparator_RESTRICT 1
#define JavaUtilComparator_INCLUDE 1
#include "java/util/Comparator.h"

typedef enum {
  ComGoogleCommonPrimitivesChars_LexicographicalComparator_INSTANCE = 0,
} ComGoogleCommonPrimitivesChars_LexicographicalComparator;

@interface ComGoogleCommonPrimitivesChars_LexicographicalComparatorEnum : JavaLangEnum < NSCopying, JavaUtilComparator > {
}
+ (ComGoogleCommonPrimitivesChars_LexicographicalComparatorEnum *)INSTANCE;
+ (IOSObjectArray *)values;
+ (ComGoogleCommonPrimitivesChars_LexicographicalComparatorEnum *)valueOfWithNSString:(NSString *)name;
- (id)copyWithZone:(NSZone *)zone;
- (int)compareWithId:(IOSCharArray *)left
              withId:(IOSCharArray *)right;
- (id)initWithNSString:(NSString *)__name withInt:(int)__ordinal;
@end
#endif

#if !defined (_ComGoogleCommonPrimitivesChars_CharArrayAsList_) && (ComGoogleCommonPrimitivesChars_INCLUDE_ALL || ComGoogleCommonPrimitivesChars_CharArrayAsList_INCLUDE)
#define _ComGoogleCommonPrimitivesChars_CharArrayAsList_

@class IOSCharArray;
@class JavaLangCharacter;
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

#define ComGoogleCommonPrimitivesChars_CharArrayAsList_serialVersionUID 0

@interface ComGoogleCommonPrimitivesChars_CharArrayAsList : JavaUtilAbstractList < JavaUtilRandomAccess, JavaIoSerializable > {
 @public
  IOSCharArray *array_;
  int start_;
  int end_;
}

- (id)initWithCharArray:(IOSCharArray *)array;
- (id)initWithCharArray:(IOSCharArray *)array
                withInt:(int)start
                withInt:(int)end;
- (int)size;
- (BOOL)isEmpty;
- (JavaLangCharacter *)getWithInt:(int)index;
- (BOOL)containsWithId:(id)target;
- (int)indexOfWithId:(id)target;
- (int)lastIndexOfWithId:(id)target;
- (JavaLangCharacter *)setWithInt:(int)index
                           withId:(JavaLangCharacter *)element;
- (id<JavaUtilList>)subListWithInt:(int)fromIndex
                           withInt:(int)toIndex;
- (BOOL)isEqual:(id)object;
- (NSUInteger)hash;
- (NSString *)description;
- (IOSCharArray *)toCharArray;
- (void)copyAllFieldsTo:(ComGoogleCommonPrimitivesChars_CharArrayAsList *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonPrimitivesChars_CharArrayAsList, array_, IOSCharArray *)
#endif
