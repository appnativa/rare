//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../util/src/com/appnativa/util/ConcurrentHashMapEx.java
//
//  Created by decoteaud on 12/8/15.
//

#ifndef _RAREUTConcurrentHashMapEx_H_
#define _RAREUTConcurrentHashMapEx_H_

@protocol JavaUtilMap;

#import "JreEmulation.h"
#include "java/util/concurrent/ConcurrentHashMap.h"

#define RAREUTConcurrentHashMapEx_serialVersionUID 1

@interface RAREUTConcurrentHashMapEx : JavaUtilConcurrentConcurrentHashMap {
}

+ (id)getNULL;
+ (void)setNULL:(id)NULL_;
- (id)init;
- (id)initWithInt:(int)initialCapacity;
- (id)initWithJavaUtilMap:(id<JavaUtilMap>)map;
- (id)initWithInt:(int)initialCapacity
        withFloat:(float)loadFactor;
- (id)initWithInt:(int)initialCapacity
        withFloat:(float)loadFactor
          withInt:(int)concurrencyLevel;
- (id)putWithId:(id)key
         withId:(id)value;
- (id)removeWithId:(id)key;
- (id)getWithId:(id)key;
@end

typedef RAREUTConcurrentHashMapEx ComAppnativaUtilConcurrentHashMapEx;

#endif // _RAREUTConcurrentHashMapEx_H_
