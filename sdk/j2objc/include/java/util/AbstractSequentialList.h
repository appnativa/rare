//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: apache_harmony/classlib/modules/luni/src/main/java/java/util/AbstractSequentialList.java
//
//  Created by tball on 11/23/13.
//

#ifndef _JavaUtilAbstractSequentialList_H_
#define _JavaUtilAbstractSequentialList_H_

@protocol JavaUtilCollection;
@protocol JavaUtilIterator;
@protocol JavaUtilListIterator;

#import "JreEmulation.h"
#include "java/util/AbstractList.h"

@interface JavaUtilAbstractSequentialList : JavaUtilAbstractList {
}

- (id)init;
- (void)addWithInt:(int)location
            withId:(id)object;
- (BOOL)addAllWithInt:(int)location
withJavaUtilCollection:(id<JavaUtilCollection>)collection;
- (id)getWithInt:(int)location;
- (id<JavaUtilIterator>)iterator;
- (id<JavaUtilListIterator>)listIteratorWithInt:(int)location;
- (id)removeWithInt:(int)location;
- (id)setWithInt:(int)location
          withId:(id)object;
@end

#endif // _JavaUtilAbstractSequentialList_H_
