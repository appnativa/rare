//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/testing/mockito/build_result/java/org/mockito/internal/matchers/apachecommons/EqualsBuilder.java
//
//  Created by tball on 11/23/13.
//

#ifndef _OrgMockitoInternalMatchersApachecommonsEqualsBuilder_H_
#define _OrgMockitoInternalMatchersApachecommonsEqualsBuilder_H_

@class IOSBooleanArray;
@class IOSByteArray;
@class IOSCharArray;
@class IOSClass;
@class IOSDoubleArray;
@class IOSFloatArray;
@class IOSIntArray;
@class IOSLongArray;
@class IOSObjectArray;
@class IOSShortArray;

#import "JreEmulation.h"

@interface OrgMockitoInternalMatchersApachecommonsEqualsBuilder : NSObject {
 @public
  BOOL isEquals__;
}

- (id)init;
+ (BOOL)reflectionEqualsWithId:(id)lhs
                        withId:(id)rhs;
+ (BOOL)reflectionEqualsWithId:(id)lhs
                        withId:(id)rhs
             withNSStringArray:(IOSObjectArray *)excludeFields;
+ (BOOL)reflectionEqualsWithId:(id)lhs
                        withId:(id)rhs
                   withBoolean:(BOOL)testTransients;
+ (BOOL)reflectionEqualsWithId:(id)lhs
                        withId:(id)rhs
                   withBoolean:(BOOL)testTransients
                  withIOSClass:(IOSClass *)reflectUpToClass;
+ (BOOL)reflectionEqualsWithId:(id)lhs
                        withId:(id)rhs
                   withBoolean:(BOOL)testTransients
                  withIOSClass:(IOSClass *)reflectUpToClass
             withNSStringArray:(IOSObjectArray *)excludeFields;
+ (void)reflectionAppendWithId:(id)lhs
                        withId:(id)rhs
                  withIOSClass:(IOSClass *)clazz
withOrgMockitoInternalMatchersApachecommonsEqualsBuilder:(OrgMockitoInternalMatchersApachecommonsEqualsBuilder *)builder
                   withBoolean:(BOOL)useTransients
             withNSStringArray:(IOSObjectArray *)excludeFields;
- (OrgMockitoInternalMatchersApachecommonsEqualsBuilder *)appendSuperWithBoolean:(BOOL)superEquals;
- (OrgMockitoInternalMatchersApachecommonsEqualsBuilder *)appendWithId:(id)lhs
                                                                withId:(id)rhs;
- (OrgMockitoInternalMatchersApachecommonsEqualsBuilder *)appendWithLong:(long long int)lhs
                                                                withLong:(long long int)rhs;
- (OrgMockitoInternalMatchersApachecommonsEqualsBuilder *)appendWithInt:(int)lhs
                                                                withInt:(int)rhs;
- (OrgMockitoInternalMatchersApachecommonsEqualsBuilder *)appendWithShort:(short int)lhs
                                                                withShort:(short int)rhs;
- (OrgMockitoInternalMatchersApachecommonsEqualsBuilder *)appendWithChar:(unichar)lhs
                                                                withChar:(unichar)rhs;
- (OrgMockitoInternalMatchersApachecommonsEqualsBuilder *)appendWithByte:(char)lhs
                                                                withByte:(char)rhs;
- (OrgMockitoInternalMatchersApachecommonsEqualsBuilder *)appendWithDouble:(double)lhs
                                                                withDouble:(double)rhs;
- (OrgMockitoInternalMatchersApachecommonsEqualsBuilder *)appendWithFloat:(float)lhs
                                                                withFloat:(float)rhs;
- (OrgMockitoInternalMatchersApachecommonsEqualsBuilder *)appendWithBoolean:(BOOL)lhs
                                                                withBoolean:(BOOL)rhs;
- (OrgMockitoInternalMatchersApachecommonsEqualsBuilder *)appendWithNSObjectArray:(IOSObjectArray *)lhs
                                                                withNSObjectArray:(IOSObjectArray *)rhs;
- (OrgMockitoInternalMatchersApachecommonsEqualsBuilder *)appendWithLongArray:(IOSLongArray *)lhs
                                                                withLongArray:(IOSLongArray *)rhs;
- (OrgMockitoInternalMatchersApachecommonsEqualsBuilder *)appendWithIntArray:(IOSIntArray *)lhs
                                                                withIntArray:(IOSIntArray *)rhs;
- (OrgMockitoInternalMatchersApachecommonsEqualsBuilder *)appendWithShortArray:(IOSShortArray *)lhs
                                                                withShortArray:(IOSShortArray *)rhs;
- (OrgMockitoInternalMatchersApachecommonsEqualsBuilder *)appendWithCharArray:(IOSCharArray *)lhs
                                                                withCharArray:(IOSCharArray *)rhs;
- (OrgMockitoInternalMatchersApachecommonsEqualsBuilder *)appendWithByteArray:(IOSByteArray *)lhs
                                                                withByteArray:(IOSByteArray *)rhs;
- (OrgMockitoInternalMatchersApachecommonsEqualsBuilder *)appendWithDoubleArray:(IOSDoubleArray *)lhs
                                                                withDoubleArray:(IOSDoubleArray *)rhs;
- (OrgMockitoInternalMatchersApachecommonsEqualsBuilder *)appendWithFloatArray:(IOSFloatArray *)lhs
                                                                withFloatArray:(IOSFloatArray *)rhs;
- (OrgMockitoInternalMatchersApachecommonsEqualsBuilder *)appendWithBooleanArray:(IOSBooleanArray *)lhs
                                                                withBooleanArray:(IOSBooleanArray *)rhs;
- (BOOL)isEquals;
- (void)setEqualsWithBoolean:(BOOL)isEquals;
- (void)copyAllFieldsTo:(OrgMockitoInternalMatchersApachecommonsEqualsBuilder *)other;
@end

#endif // _OrgMockitoInternalMatchersApachecommonsEqualsBuilder_H_
