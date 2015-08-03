//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/android/util/SparseArray.java
//
//  Created by decoteaud on 5/11/15.
//

#ifndef _AndroidUtilSparseArray_H_
#define _AndroidUtilSparseArray_H_

@class IOSIntArray;
@class IOSObjectArray;

#import "JreEmulation.h"

@interface AndroidUtilSparseArray : NSObject < NSCopying > {
 @public
  BOOL mGarbage_;
  IOSIntArray *mKeys_;
  IOSObjectArray *mValues_;
  int mSize_;
}

+ (id)DELETED;
- (id)init;
- (id)initWithInt:(int)initialCapacity;
- (AndroidUtilSparseArray *)clone;
- (id)getWithInt:(int)key;
- (id)getWithInt:(int)key
          withId:(id)valueIfKeyNotFound;
- (void)delete__WithInt:(int)key;
- (void)removeWithInt:(int)key;
- (void)removeAtWithInt:(int)index;
- (void)gc;
- (void)putWithInt:(int)key
            withId:(id)value;
- (int)size;
- (int)keyAtWithInt:(int)index;
- (id)valueAtWithInt:(int)index;
- (void)setValueAtWithInt:(int)index
                   withId:(id)value;
- (int)indexOfKeyWithInt:(int)key;
- (int)indexOfValueWithId:(id)value;
- (void)clear;
- (void)appendWithInt:(int)key
               withId:(id)value;
+ (int)binarySearchWithIntArray:(IOSIntArray *)a
                        withInt:(int)start
                        withInt:(int)len
                        withInt:(int)key;
- (id)copyWithZone:(NSZone *)zone;
- (void)copyAllFieldsTo:(AndroidUtilSparseArray *)other;
@end

J2OBJC_FIELD_SETTER(AndroidUtilSparseArray, mKeys_, IOSIntArray *)
J2OBJC_FIELD_SETTER(AndroidUtilSparseArray, mValues_, IOSObjectArray *)

#endif // _AndroidUtilSparseArray_H_
