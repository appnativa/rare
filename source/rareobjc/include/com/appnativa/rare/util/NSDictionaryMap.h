//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/util/NSDictionaryMap.java
//
//  Created by decoteaud on 12/8/15.
//

#ifndef _RARENSDictionaryMap_H_
#define _RARENSDictionaryMap_H_

@protocol JavaUtilCollection;
@protocol JavaUtilMap;
@protocol JavaUtilSet;

#import "JreEmulation.h"
#include "java/lang/Enum.h"
#include "java/util/AbstractMap.h"
#include "java/util/AbstractSet.h"
#include "java/util/Iterator.h"
#include "java/util/Map.h"

@interface RARENSDictionaryMap : JavaUtilAbstractMap {
 @public
  id proxy_;
}

- (id)init;
- (id)initWithInt:(int)initialCapacity;
- (id)initWithRARENSDictionaryMap:(RARENSDictionaryMap *)map;
- (id)initWithJavaUtilMap:(id<JavaUtilMap>)map;
- (id)getWithId:(id)key;
- (id)initWithId:(id)nsdictionary;
+ (RARENSDictionaryMap *)mapUsingBackingDictionaryWithId:(id)nsdictionary;
- (id)getProxy;
- (id<JavaUtilSet>)entrySet;
- (id<JavaUtilSet>)keySet;
- (id<JavaUtilCollection>)values;
- (id)putWithId:(id)key
         withId:(id)value;
- (id)removeWithId:(id)key;
+ (id)createEnumeratorWithId:(id)proxy;
+ (id)createDictionaryWithId:(id)proxy;
+ (id)createDictionaryWithInt:(int)size;
- (void)copyAllFieldsTo:(RARENSDictionaryMap *)other;
@end

J2OBJC_FIELD_SETTER(RARENSDictionaryMap, proxy_, id)

typedef RARENSDictionaryMap ComAppnativaRareUtilNSDictionaryMap;

typedef enum {
  RARENSDictionaryMap_IteratorType_KEY = 0,
  RARENSDictionaryMap_IteratorType_VALUE = 1,
  RARENSDictionaryMap_IteratorType_ENTRY = 2,
} RARENSDictionaryMap_IteratorType;

@interface RARENSDictionaryMap_IteratorTypeEnum : JavaLangEnum < NSCopying > {
}
+ (RARENSDictionaryMap_IteratorTypeEnum *)KEY;
+ (RARENSDictionaryMap_IteratorTypeEnum *)VALUE;
+ (RARENSDictionaryMap_IteratorTypeEnum *)ENTRY;
+ (IOSObjectArray *)values;
+ (RARENSDictionaryMap_IteratorTypeEnum *)valueOfWithNSString:(NSString *)name;
- (id)copyWithZone:(NSZone *)zone;
- (id)initWithNSString:(NSString *)__name withInt:(int)__ordinal;
@end

@interface RARENSDictionaryMap_DictionaryEntry : NSObject < JavaUtilMap_Entry > {
 @public
  id key_;
  RARENSDictionaryMap *map_;
}

- (id)initWithRARENSDictionaryMap:(RARENSDictionaryMap *)map
                           withId:(id)key;
- (id)setValueWithId:(id)value;
- (id)getKey;
- (id)getValue;
- (void)copyAllFieldsTo:(RARENSDictionaryMap_DictionaryEntry *)other;
@end

J2OBJC_FIELD_SETTER(RARENSDictionaryMap_DictionaryEntry, key_, id)
J2OBJC_FIELD_SETTER(RARENSDictionaryMap_DictionaryEntry, map_, RARENSDictionaryMap *)

@interface RARENSDictionaryMap_DictionarySet : JavaUtilAbstractSet {
 @public
  RARENSDictionaryMap *map_;
  RARENSDictionaryMap_IteratorTypeEnum *type_;
}

- (id)initWithRARENSDictionaryMap:(RARENSDictionaryMap *)map
withRARENSDictionaryMap_IteratorTypeEnum:(RARENSDictionaryMap_IteratorTypeEnum *)type;
- (id<JavaUtilIterator>)iterator;
- (int)size;
- (void)copyAllFieldsTo:(RARENSDictionaryMap_DictionarySet *)other;
@end

J2OBJC_FIELD_SETTER(RARENSDictionaryMap_DictionarySet, map_, RARENSDictionaryMap *)
J2OBJC_FIELD_SETTER(RARENSDictionaryMap_DictionarySet, type_, RARENSDictionaryMap_IteratorTypeEnum *)

@interface RARENSDictionaryMap_DictionaryIterator : NSObject < JavaUtilIterator > {
 @public
  id current_;
  id enumerator_;
  RARENSDictionaryMap *map_;
  id next__;
  RARENSDictionaryMap_IteratorTypeEnum *type_;
}

- (id)initWithRARENSDictionaryMap:(RARENSDictionaryMap *)map
withRARENSDictionaryMap_IteratorTypeEnum:(RARENSDictionaryMap_IteratorTypeEnum *)type;
- (id)next;
- (void)remove;
- (BOOL)hasNext;
- (id)getNext;
- (void)copyAllFieldsTo:(RARENSDictionaryMap_DictionaryIterator *)other;
@end

J2OBJC_FIELD_SETTER(RARENSDictionaryMap_DictionaryIterator, current_, id)
J2OBJC_FIELD_SETTER(RARENSDictionaryMap_DictionaryIterator, enumerator_, id)
J2OBJC_FIELD_SETTER(RARENSDictionaryMap_DictionaryIterator, map_, RARENSDictionaryMap *)
J2OBJC_FIELD_SETTER(RARENSDictionaryMap_DictionaryIterator, next__, id)
J2OBJC_FIELD_SETTER(RARENSDictionaryMap_DictionaryIterator, type_, RARENSDictionaryMap_IteratorTypeEnum *)

#endif // _RARENSDictionaryMap_H_
