//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/guava/sources/com/google/common/collect/LexicographicalOrdering.java
//
//  Created by tball on 11/23/13.
//

#import "JreEmulation.h"

#if !ComGoogleCommonCollectLexicographicalOrdering_RESTRICT
#define ComGoogleCommonCollectLexicographicalOrdering_INCLUDE_ALL 1
#endif
#undef ComGoogleCommonCollectLexicographicalOrdering_RESTRICT

#if !defined (_ComGoogleCommonCollectLexicographicalOrdering_) && (ComGoogleCommonCollectLexicographicalOrdering_INCLUDE_ALL || ComGoogleCommonCollectLexicographicalOrdering_INCLUDE)
#define _ComGoogleCommonCollectLexicographicalOrdering_

@protocol JavaLangIterable;

#define ComGoogleCommonCollectOrdering_RESTRICT 1
#define ComGoogleCommonCollectOrdering_INCLUDE 1
#include "com/google/common/collect/Ordering.h"

#define JavaIoSerializable_RESTRICT 1
#define JavaIoSerializable_INCLUDE 1
#include "java/io/Serializable.h"

#define ComGoogleCommonCollectLexicographicalOrdering_serialVersionUID 0

@interface ComGoogleCommonCollectLexicographicalOrdering : ComGoogleCommonCollectOrdering < JavaIoSerializable > {
 @public
  ComGoogleCommonCollectOrdering *elementOrder_;
}

- (id)initWithComGoogleCommonCollectOrdering:(ComGoogleCommonCollectOrdering *)elementOrder;
- (int)compareWithId:(id<JavaLangIterable>)leftIterable
              withId:(id<JavaLangIterable>)rightIterable;
- (BOOL)isEqual:(id)object;
- (NSUInteger)hash;
- (NSString *)description;
- (void)copyAllFieldsTo:(ComGoogleCommonCollectLexicographicalOrdering *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectLexicographicalOrdering, elementOrder_, ComGoogleCommonCollectOrdering *)
#endif