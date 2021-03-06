//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/util/NSArrayList.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RARENSArrayList_H_
#define _RARENSArrayList_H_

#import "JreEmulation.h"
#include "java/util/AbstractList.h"

@interface RARENSArrayList : JavaUtilAbstractList {
 @public
  BOOL mutable__;
  id proxy_;
}

+ (id)nillObject;
+ (void)setNillObject:(id)nillObject;
- (id)init;
- (id)initWithInt:(int)initialCapacity;
- (id)initWithId:(id)nsarray
     withBoolean:(BOOL)mutable_;
- (void)addWithInt:(int)index
            withId:(id)element;
- (void)clear;
- (int)indexOfWithId:(id)element;
+ (RARENSArrayList *)listWithBackingArrayWithId:(id)nsarray
                                    withBoolean:(BOOL)mutable_;
- (id)removeWithInt:(int)index;
- (BOOL)removeWithId:(id)o;
- (int)size;
- (id)setWithInt:(int)index
          withId:(id)element;
- (id)getWithInt:(int)index;
- (id)getProxy;
- (void)clearEx;
- (id)removeExWithInt:(int)index;
- (id)setExWithInt:(int)index
            withId:(id)element;
- (void)addExWithInt:(int)index
              withId:(id)element;
- (void)copyAllFieldsTo:(RARENSArrayList *)other;
@end

J2OBJC_FIELD_SETTER(RARENSArrayList, proxy_, id)

typedef RARENSArrayList ComAppnativaRareUtilNSArrayList;

#endif // _RARENSArrayList_H_
