//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/guava/sources/com/google/common/primitives/Bytes.java
//
//  Created by tball on 11/23/13.
//

#import "JreEmulation.h"

#if !ComGoogleCommonPrimitivesBytes_RESTRICT
#define ComGoogleCommonPrimitivesBytes_INCLUDE_ALL 1
#endif
#undef ComGoogleCommonPrimitivesBytes_RESTRICT

#if !defined (_ComGoogleCommonPrimitivesBytes_) && (ComGoogleCommonPrimitivesBytes_INCLUDE_ALL || ComGoogleCommonPrimitivesBytes_INCLUDE)
#define _ComGoogleCommonPrimitivesBytes_

@class IOSByteArray;
@class IOSObjectArray;
@protocol JavaUtilCollection;
@protocol JavaUtilList;

@interface ComGoogleCommonPrimitivesBytes : NSObject {
}

- (id)init;
+ (int)hashCodeWithByte:(char)value;
+ (BOOL)containsWithByteArray:(IOSByteArray *)array
                     withByte:(char)target;
+ (int)indexOfWithByteArray:(IOSByteArray *)array
                   withByte:(char)target;
+ (int)indexOfWithByteArray:(IOSByteArray *)array
                   withByte:(char)target
                    withInt:(int)start
                    withInt:(int)end;
+ (int)indexOfWithByteArray:(IOSByteArray *)array
              withByteArray:(IOSByteArray *)target;
+ (int)lastIndexOfWithByteArray:(IOSByteArray *)array
                       withByte:(char)target;
+ (int)lastIndexOfWithByteArray:(IOSByteArray *)array
                       withByte:(char)target
                        withInt:(int)start
                        withInt:(int)end;
+ (IOSByteArray *)concatWithByteArray2:(IOSObjectArray *)arrays;
+ (IOSByteArray *)ensureCapacityWithByteArray:(IOSByteArray *)array
                                      withInt:(int)minLength
                                      withInt:(int)padding;
+ (IOSByteArray *)copyOfWithByteArray:(IOSByteArray *)original
                              withInt:(int)length OBJC_METHOD_FAMILY_NONE;
+ (IOSByteArray *)toArrayWithJavaUtilCollection:(id<JavaUtilCollection>)collection;
+ (id<JavaUtilList>)asListWithByteArray:(IOSByteArray *)backingArray;
@end
#endif

#if !defined (_ComGoogleCommonPrimitivesBytes_ByteArrayAsList_) && (ComGoogleCommonPrimitivesBytes_INCLUDE_ALL || ComGoogleCommonPrimitivesBytes_ByteArrayAsList_INCLUDE)
#define _ComGoogleCommonPrimitivesBytes_ByteArrayAsList_

@class IOSByteArray;
@class JavaLangByte;
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

#define ComGoogleCommonPrimitivesBytes_ByteArrayAsList_serialVersionUID 0

@interface ComGoogleCommonPrimitivesBytes_ByteArrayAsList : JavaUtilAbstractList < JavaUtilRandomAccess, JavaIoSerializable > {
 @public
  IOSByteArray *array_;
  int start_;
  int end_;
}

- (id)initWithByteArray:(IOSByteArray *)array;
- (id)initWithByteArray:(IOSByteArray *)array
                withInt:(int)start
                withInt:(int)end;
- (int)size;
- (BOOL)isEmpty;
- (JavaLangByte *)getWithInt:(int)index;
- (BOOL)containsWithId:(id)target;
- (int)indexOfWithId:(id)target;
- (int)lastIndexOfWithId:(id)target;
- (JavaLangByte *)setWithInt:(int)index
                      withId:(JavaLangByte *)element;
- (id<JavaUtilList>)subListWithInt:(int)fromIndex
                           withInt:(int)toIndex;
- (BOOL)isEqual:(id)object;
- (NSUInteger)hash;
- (NSString *)description;
- (IOSByteArray *)toByteArray;
- (void)copyAllFieldsTo:(ComGoogleCommonPrimitivesBytes_ByteArrayAsList *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonPrimitivesBytes_ByteArrayAsList, array_, IOSByteArray *)
#endif