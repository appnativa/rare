//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/guava/sources/com/google/common/collect/GeneralRange.java
//
//  Created by tball on 11/23/13.
//

#import "JreEmulation.h"

#if !ComGoogleCommonCollectGeneralRange_RESTRICT
#define ComGoogleCommonCollectGeneralRange_INCLUDE_ALL 1
#endif
#undef ComGoogleCommonCollectGeneralRange_RESTRICT

#if !defined (_ComGoogleCommonCollectGeneralRange_) && (ComGoogleCommonCollectGeneralRange_INCLUDE_ALL || ComGoogleCommonCollectGeneralRange_INCLUDE)
#define _ComGoogleCommonCollectGeneralRange_

@class ComGoogleCommonCollectBoundTypeEnum;
@class ComGoogleCommonCollectRange;
@protocol JavaUtilComparator;

#define JavaIoSerializable_RESTRICT 1
#define JavaIoSerializable_INCLUDE 1
#include "java/io/Serializable.h"

@interface ComGoogleCommonCollectGeneralRange : NSObject < JavaIoSerializable > {
 @public
  id<JavaUtilComparator> comparator__;
  BOOL hasLowerBound__;
  id lowerEndpoint_;
  ComGoogleCommonCollectBoundTypeEnum *lowerBoundType_;
  BOOL hasUpperBound__;
  id upperEndpoint_;
  ComGoogleCommonCollectBoundTypeEnum *upperBoundType_;
  ComGoogleCommonCollectGeneralRange *reverse__;
}

+ (ComGoogleCommonCollectGeneralRange *)fromWithComGoogleCommonCollectRange:(ComGoogleCommonCollectRange *)range;
+ (ComGoogleCommonCollectGeneralRange *)allWithJavaUtilComparator:(id<JavaUtilComparator>)comparator;
+ (ComGoogleCommonCollectGeneralRange *)downToWithJavaUtilComparator:(id<JavaUtilComparator>)comparator
                                                              withId:(id)endpoint
                             withComGoogleCommonCollectBoundTypeEnum:(ComGoogleCommonCollectBoundTypeEnum *)boundType;
+ (ComGoogleCommonCollectGeneralRange *)upToWithJavaUtilComparator:(id<JavaUtilComparator>)comparator
                                                            withId:(id)endpoint
                           withComGoogleCommonCollectBoundTypeEnum:(ComGoogleCommonCollectBoundTypeEnum *)boundType;
+ (ComGoogleCommonCollectGeneralRange *)rangeWithJavaUtilComparator:(id<JavaUtilComparator>)comparator
                                                             withId:(id)lower
                            withComGoogleCommonCollectBoundTypeEnum:(ComGoogleCommonCollectBoundTypeEnum *)lowerType
                                                             withId:(id)upper
                            withComGoogleCommonCollectBoundTypeEnum:(ComGoogleCommonCollectBoundTypeEnum *)upperType;
- (id)initWithJavaUtilComparator:(id<JavaUtilComparator>)comparator
                     withBoolean:(BOOL)hasLowerBound
                          withId:(id)lowerEndpoint
withComGoogleCommonCollectBoundTypeEnum:(ComGoogleCommonCollectBoundTypeEnum *)lowerBoundType
                     withBoolean:(BOOL)hasUpperBound
                          withId:(id)upperEndpoint
withComGoogleCommonCollectBoundTypeEnum:(ComGoogleCommonCollectBoundTypeEnum *)upperBoundType;
- (id<JavaUtilComparator>)comparator;
- (BOOL)hasLowerBound;
- (BOOL)hasUpperBound;
- (BOOL)isEmpty;
- (BOOL)tooLowWithId:(id)t;
- (BOOL)tooHighWithId:(id)t;
- (BOOL)containsWithId:(id)t;
- (ComGoogleCommonCollectGeneralRange *)intersectWithComGoogleCommonCollectGeneralRange:(ComGoogleCommonCollectGeneralRange *)other;
- (BOOL)isEqual:(id)obj;
- (NSUInteger)hash;
- (ComGoogleCommonCollectGeneralRange *)reverse;
- (NSString *)description;
- (id)getLowerEndpoint;
- (ComGoogleCommonCollectBoundTypeEnum *)getLowerBoundType;
- (id)getUpperEndpoint;
- (ComGoogleCommonCollectBoundTypeEnum *)getUpperBoundType;
- (void)copyAllFieldsTo:(ComGoogleCommonCollectGeneralRange *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectGeneralRange, comparator__, id<JavaUtilComparator>)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectGeneralRange, lowerEndpoint_, id)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectGeneralRange, lowerBoundType_, ComGoogleCommonCollectBoundTypeEnum *)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectGeneralRange, upperEndpoint_, id)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectGeneralRange, upperBoundType_, ComGoogleCommonCollectBoundTypeEnum *)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectGeneralRange, reverse__, ComGoogleCommonCollectGeneralRange *)
#endif
