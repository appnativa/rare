//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/junit/build_result/java/org/hamcrest/CoreMatchers.java
//
//  Created by tball on 11/23/13.
//

#ifndef _OrgHamcrestCoreMatchers_H_
#define _OrgHamcrestCoreMatchers_H_

@class IOSClass;
@class IOSObjectArray;
@protocol JavaLangIterable;
@protocol OrgHamcrestMatcher;

#import "JreEmulation.h"

@interface OrgHamcrestCoreMatchers : NSObject {
}

+ (id<OrgHamcrestMatcher>)isWithOrgHamcrestMatcher:(id<OrgHamcrestMatcher>)matcher;
+ (id<OrgHamcrestMatcher>)isWithId:(id)value;
+ (id<OrgHamcrestMatcher>)isWithIOSClass:(IOSClass *)type;
+ (id<OrgHamcrestMatcher>)not__WithOrgHamcrestMatcher:(id<OrgHamcrestMatcher>)matcher;
+ (id<OrgHamcrestMatcher>)not__WithId:(id)value;
+ (id<OrgHamcrestMatcher>)equalToWithId:(id)operand;
+ (id<OrgHamcrestMatcher>)instanceOfWithIOSClass:(IOSClass *)type;
+ (id<OrgHamcrestMatcher>)allOfWithOrgHamcrestMatcherArray:(IOSObjectArray *)matchers;
+ (id<OrgHamcrestMatcher>)allOfWithJavaLangIterable:(id<JavaLangIterable>)matchers;
+ (id<OrgHamcrestMatcher>)anyOfWithOrgHamcrestMatcherArray:(IOSObjectArray *)matchers;
+ (id<OrgHamcrestMatcher>)anyOfWithJavaLangIterable:(id<JavaLangIterable>)matchers;
+ (id<OrgHamcrestMatcher>)sameInstanceWithId:(id)object;
+ (id<OrgHamcrestMatcher>)anything;
+ (id<OrgHamcrestMatcher>)anythingWithNSString:(NSString *)description_;
+ (id<OrgHamcrestMatcher>)anyWithIOSClass:(IOSClass *)type;
+ (id<OrgHamcrestMatcher>)nullValue;
+ (id<OrgHamcrestMatcher>)nullValueWithIOSClass:(IOSClass *)type;
+ (id<OrgHamcrestMatcher>)notNullValue;
+ (id<OrgHamcrestMatcher>)notNullValueWithIOSClass:(IOSClass *)type;
+ (id<OrgHamcrestMatcher>)describedAsWithNSString:(NSString *)description_
                           withOrgHamcrestMatcher:(id<OrgHamcrestMatcher>)matcher
                                withNSObjectArray:(IOSObjectArray *)values;
- (id)init;
@end

#endif // _OrgHamcrestCoreMatchers_H_