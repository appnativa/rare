//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/guava/sources/com/google/common/collect/EnumMultiset.java
//
//  Created by tball on 11/23/13.
//

#import "JreEmulation.h"

#if !ComGoogleCommonCollectEnumMultiset_RESTRICT
#define ComGoogleCommonCollectEnumMultiset_INCLUDE_ALL 1
#endif
#undef ComGoogleCommonCollectEnumMultiset_RESTRICT

#if !defined (_ComGoogleCommonCollectEnumMultiset_) && (ComGoogleCommonCollectEnumMultiset_INCLUDE_ALL || ComGoogleCommonCollectEnumMultiset_INCLUDE)
#define _ComGoogleCommonCollectEnumMultiset_

@class IOSClass;
@protocol JavaLangIterable;

#define ComGoogleCommonCollectAbstractMapBasedMultiset_RESTRICT 1
#define ComGoogleCommonCollectAbstractMapBasedMultiset_INCLUDE 1
#include "com/google/common/collect/AbstractMapBasedMultiset.h"

#define ComGoogleCommonCollectEnumMultiset_serialVersionUID 0

@interface ComGoogleCommonCollectEnumMultiset : ComGoogleCommonCollectAbstractMapBasedMultiset {
 @public
  IOSClass *type_;
}

+ (ComGoogleCommonCollectEnumMultiset *)createWithIOSClass:(IOSClass *)type;
+ (ComGoogleCommonCollectEnumMultiset *)createWithJavaLangIterable:(id<JavaLangIterable>)elements;
+ (ComGoogleCommonCollectEnumMultiset *)createWithJavaLangIterable:(id<JavaLangIterable>)elements
                                                      withIOSClass:(IOSClass *)type;
- (id)initWithIOSClass:(IOSClass *)type;
- (void)copyAllFieldsTo:(ComGoogleCommonCollectEnumMultiset *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectEnumMultiset, type_, IOSClass *)
#endif
