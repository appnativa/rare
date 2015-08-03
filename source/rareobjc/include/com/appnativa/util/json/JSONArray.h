//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../util/src-json/com/appnativa/util/json/JSONArray.java
//
//  Created by decoteaud on 7/14/15.
//

#ifndef _RAREUTJSONArray_H_
#define _RAREUTJSONArray_H_

@class IOSObjectArray;
@class JavaIoWriter;
@class JavaUtilArrayList;
@class RAREUTJSONObject;
@class RAREUTJSONTokener;
@protocol JavaUtilCollection;
@protocol JavaUtilComparator;
@protocol JavaUtilIterator;
@protocol JavaUtilListIterator;
@protocol JavaUtilMap;
@protocol RAREUTiFilter;
@protocol RAREUTiFilterableList;

#import "JreEmulation.h"
#include "java/util/List.h"

@interface RAREUTJSONArray : NSObject < JavaUtilList, NSCopying > {
 @public
  id<RAREUTiFilterableList> filterableList_;
  JavaUtilArrayList *myArrayList_;
  NSString *name_;
}

- (id)init;
- (id)initWithJavaUtilCollection:(id<JavaUtilCollection>)collection;
- (id)initWithRAREUTJSONTokener:(RAREUTJSONTokener *)x;
- (id)initWithJavaUtilList:(id<JavaUtilList>)array;
- (id)initWithNSString:(NSString *)source;
- (id)initWithNSString:(NSString *)name
 withRAREUTJSONTokener:(RAREUTJSONTokener *)x;
- (void)addWithInt:(int)index
            withId:(id)element;
- (BOOL)addWithId:(id)e;
- (BOOL)addAllWithJavaUtilCollection:(id<JavaUtilCollection>)c;
- (BOOL)addAllWithInt:(int)index
withJavaUtilCollection:(id<JavaUtilCollection>)c;
- (int)chopWithInt:(int)len;
- (void)clear;
- (id)clone;
- (BOOL)containsWithId:(id)o;
- (BOOL)containsAllWithJavaUtilCollection:(id<JavaUtilCollection>)c;
- (BOOL)isEqual:(id)o;
- (RAREUTJSONObject *)findJSONObjectWithNSString:(NSString *)key
                               withRAREUTiFilter:(id<RAREUTiFilter>)filter;
- (RAREUTJSONObject *)findJSONObjectWithNSString:(NSString *)key
                               withRAREUTiFilter:(id<RAREUTiFilter>)filter
                                         withInt:(int)startIndex;
- (RAREUTJSONObject *)findJSONObjectWithNSString:(NSString *)key
                                    withNSString:(NSString *)value;
- (RAREUTJSONObject *)findJSONObjectWithNSString:(NSString *)key
                                    withNSString:(NSString *)value
                                         withInt:(int)startIndex;
- (int)findJSONObjectIndexWithNSString:(NSString *)key
                     withRAREUTiFilter:(id<RAREUTiFilter>)filter;
- (int)findJSONObjectIndexWithNSString:(NSString *)key
                     withRAREUTiFilter:(id<RAREUTiFilter>)filter
                               withInt:(int)startIndex;
- (int)findJSONObjectIndexWithNSString:(NSString *)key
                          withNSString:(NSString *)value
                               withInt:(int)startIndex;
- (id)getWithInt:(int)index;
- (BOOL)getBooleanWithInt:(int)index;
- (double)getDoubleWithInt:(int)index;
- (id<RAREUTiFilterableList>)getiFilterableList;
- (int)getIntWithInt:(int)index;
- (RAREUTJSONArray *)getJSONArrayWithInt:(int)index;
- (RAREUTJSONObject *)getJSONObjectWithInt:(int)index;
- (int)getLength;
- (long long int)getLongWithInt:(int)index;
- (NSString *)getName;
- (id<JavaUtilList>)getObjectList;
- (NSString *)getStringWithInt:(int)index;
- (NSUInteger)hash;
- (int)indexOfWithId:(id)o;
- (BOOL)isEmpty;
- (BOOL)isNullWithInt:(int)index;
- (id<JavaUtilIterator>)iterator;
- (NSString *)join;
- (NSString *)joinWithNSString:(NSString *)separator;
- (int)lastIndexOfWithId:(id)o;
- (int)length;
- (id<JavaUtilListIterator>)listIterator;
- (id<JavaUtilListIterator>)listIteratorWithInt:(int)index;
- (id)optWithInt:(int)index;
- (BOOL)optBooleanWithInt:(int)index;
- (BOOL)optBooleanWithInt:(int)index
              withBoolean:(BOOL)defaultValue;
- (double)optDoubleWithInt:(int)index;
- (double)optDoubleWithInt:(int)index
                withDouble:(double)defaultValue;
- (int)optIntWithInt:(int)index;
- (int)optIntWithInt:(int)index
             withInt:(int)defaultValue;
- (RAREUTJSONArray *)optJSONArrayWithInt:(int)index;
- (RAREUTJSONObject *)optJSONObjectWithInt:(int)index;
- (long long int)optLongWithInt:(int)index;
- (long long int)optLongWithInt:(int)index
                       withLong:(long long int)defaultValue;
- (NSString *)optStringWithInt:(int)index;
- (NSString *)optStringWithInt:(int)index
                  withNSString:(NSString *)defaultValue;
- (RAREUTJSONArray *)putWithBoolean:(BOOL)value;
- (RAREUTJSONArray *)putWithJavaUtilCollection:(id<JavaUtilCollection>)value;
- (RAREUTJSONArray *)putWithDouble:(double)value;
- (RAREUTJSONArray *)putWithInt:(int)value;
- (RAREUTJSONArray *)putWithInt:(int)index
                    withBoolean:(BOOL)value;
- (RAREUTJSONArray *)putWithInt:(int)index
         withJavaUtilCollection:(id<JavaUtilCollection>)value;
- (RAREUTJSONArray *)putWithInt:(int)index
                     withDouble:(double)value;
- (RAREUTJSONArray *)putWithInt:(int)index
                        withInt:(int)value;
- (RAREUTJSONArray *)putWithInt:(int)index
                       withLong:(long long int)value;
- (RAREUTJSONArray *)putWithInt:(int)index
                withJavaUtilMap:(id<JavaUtilMap>)value;
- (RAREUTJSONArray *)putWithInt:(int)index
                         withId:(id)value;
- (RAREUTJSONArray *)putWithLong:(long long int)value;
- (RAREUTJSONArray *)putWithJavaUtilMap:(id<JavaUtilMap>)value;
- (RAREUTJSONArray *)putWithId:(id)value;
- (id)removeWithInt:(int)index;
- (BOOL)removeWithId:(id)o;
- (BOOL)removeAllWithJavaUtilCollection:(id<JavaUtilCollection>)c;
- (BOOL)retainAllWithJavaUtilCollection:(id<JavaUtilCollection>)c;
- (id)setWithInt:(int)index
          withId:(id)element;
- (void)setNameWithNSString:(NSString *)name;
- (int)size;
- (RAREUTJSONArray *)sliceWithInt:(int)start;
- (RAREUTJSONArray *)sliceWithInt:(int)start
                          withInt:(int)end;
- (void)sort;
- (void)sortWithJavaUtilComparator:(id<JavaUtilComparator>)c;
- (RAREUTJSONArray *)spliceWithInt:(int)index
                           withInt:(int)howMany;
- (RAREUTJSONArray *)spliceWithInt:(int)index
                           withInt:(int)howMany
                 withNSObjectArray:(IOSObjectArray *)e;
- (RAREUTJSONArray *)spliceListWithInt:(int)index
                               withInt:(int)howMany
                      withJavaUtilList:(id<JavaUtilList>)e;
- (id<JavaUtilList>)subListWithInt:(int)fromIndex
                           withInt:(int)toIndex;
- (IOSObjectArray *)toArray;
- (IOSObjectArray *)toArrayWithNSObjectArray:(IOSObjectArray *)a;
- (RAREUTJSONObject *)toJSONObjectWithRAREUTJSONArray:(RAREUTJSONArray *)names;
- (NSString *)description;
- (NSString *)toStringWithInt:(int)indentFactor;
- (JavaIoWriter *)writeWithJavaIoWriter:(JavaIoWriter *)writer;
- (NSString *)toStringWithInt:(int)indentFactor
                      withInt:(int)indent;
- (id)copyWithZone:(NSZone *)zone;
- (void)copyAllFieldsTo:(RAREUTJSONArray *)other;
@end

J2OBJC_FIELD_SETTER(RAREUTJSONArray, filterableList_, id<RAREUTiFilterableList>)
J2OBJC_FIELD_SETTER(RAREUTJSONArray, myArrayList_, JavaUtilArrayList *)
J2OBJC_FIELD_SETTER(RAREUTJSONArray, name_, NSString *)

typedef RAREUTJSONArray ComAppnativaUtilJsonJSONArray;

#endif // _RAREUTJSONArray_H_
