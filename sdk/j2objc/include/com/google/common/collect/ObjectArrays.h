//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/guava/sources/com/google/common/collect/ObjectArrays.java
//
//  Created by tball on 11/23/13.
//

#import "JreEmulation.h"

#if !ComGoogleCommonCollectObjectArrays_RESTRICT
#define ComGoogleCommonCollectObjectArrays_INCLUDE_ALL 1
#endif
#undef ComGoogleCommonCollectObjectArrays_RESTRICT

#if !defined (_ComGoogleCommonCollectObjectArrays_) && (ComGoogleCommonCollectObjectArrays_INCLUDE_ALL || ComGoogleCommonCollectObjectArrays_INCLUDE)
#define _ComGoogleCommonCollectObjectArrays_

@class IOSClass;
@class IOSObjectArray;
@protocol JavaLangIterable;
@protocol JavaUtilCollection;

@interface ComGoogleCommonCollectObjectArrays : NSObject {
}

+ (IOSObjectArray *)EMPTY_ARRAY;
- (id)init;
+ (IOSObjectArray *)newArrayWithIOSClass:(IOSClass *)type
                                 withInt:(int)length OBJC_METHOD_FAMILY_NONE;
+ (IOSObjectArray *)newArrayWithNSObjectArray:(IOSObjectArray *)reference
                                      withInt:(int)length OBJC_METHOD_FAMILY_NONE;
+ (IOSObjectArray *)concatWithNSObjectArray:(IOSObjectArray *)first
                          withNSObjectArray:(IOSObjectArray *)second
                               withIOSClass:(IOSClass *)type;
+ (IOSObjectArray *)concatWithId:(id)element
               withNSObjectArray:(IOSObjectArray *)array;
+ (IOSObjectArray *)concatWithNSObjectArray:(IOSObjectArray *)array
                                     withId:(id)element;
+ (IOSObjectArray *)arraysCopyOfWithNSObjectArray:(IOSObjectArray *)original
                                          withInt:(int)newLength;
+ (IOSObjectArray *)toArrayImplWithJavaUtilCollection:(id<JavaUtilCollection>)c
                                    withNSObjectArray:(IOSObjectArray *)array;
+ (IOSObjectArray *)toArrayImplWithJavaUtilCollection:(id<JavaUtilCollection>)c;
+ (IOSObjectArray *)fillArrayWithJavaLangIterable:(id<JavaLangIterable>)elements
                                withNSObjectArray:(IOSObjectArray *)array;
+ (void)swapWithNSObjectArray:(IOSObjectArray *)array
                      withInt:(int)i
                      withInt:(int)j;
+ (id)checkElementNotNullWithId:(id)element
                        withInt:(int)index;
@end
#endif
